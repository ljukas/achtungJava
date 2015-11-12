package engine;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import models.players.Line;
import models.players.Player;
import models.Position;
import models.powerups.*;
import models.powerups.PowerUpEffect.PowerUpType;

import javax.imageio.ImageIO;


/**
 * Handles everything for every round.
 */
public class Round
{

	/**
	 * Default width of player in pixels
	 */
	public static final int DEFAULT_PLAYER_WIDTH = 5;
	private BufferedImage lineImage = null;
	private BufferedImage powerUpImage = null;
	private Player[] players;
	private CollisionHelper collisionHelper;
	private final List<PowerUpEntity> powerUpEntities;
	private final List<PowerUp<RoundPowerUpEffect>> activeRoundEffects;
	private boolean wallsActive;


	private final List<BufferedImage> powerUpImages;
	// Naming of powerUpImgDir is fine
	private static final File powerUpImgDir = new File("src/resources/icons/");


	private final static double DEFAULT_SPEED = 1.75;
	private final static int SPAWN_LOCATION_LIMIT = 150;
	private final static float POWER_UP_CHANCE = 0.005f;

	/**
	 * Constructor
	 */
	public Round(BufferedImage lineImage, BufferedImage powerUpImage, Player[] players) {
		this.lineImage = lineImage;
		this.players = players;
		this.powerUpImage = powerUpImage;
		this.powerUpEntities = new ArrayList<>();
		this.activeRoundEffects = new ArrayList<>();
		this.collisionHelper = new CollisionHelper(lineImage);
		this.wallsActive = true;
		this.powerUpImages = new ArrayList<>();

		loadPowerUpImages();
	}

	/**
	 * Filename filter so our imageLoader only loads png files.
	 */
	static final FilenameFilter PNG_IMAGE_FILTER = (dir, name) -> {
		if (name.endsWith(".png")) {
			return true;
		}
		return false;
	};

	/**
	 * Load all powerUp images.
	 */
	private void loadPowerUpImages() {
		if (powerUpImgDir.isDirectory()) {
			for (final File file : powerUpImgDir.listFiles(PNG_IMAGE_FILTER)) {

				try {
					BufferedImage img = ImageIO.read(file);
					powerUpImages.add(img);
					System.out.println(powerUpImages.size());

				} catch (final IOException ignored) {
					System.out.println("Failed to load img");
				}
			}
		} else {
			System.out.println("Not a dir");
		}
	}

	/**
	 * Update the round, one tick
	 *
	 * @param time - current game time
	 */
	void update(int time) {
		updatePlayers(time);

		// Max 5 powerups out on the board at any given time
		if (Math.random() <= POWER_UP_CHANCE && powerUpEntities.size() < 5) {
			PowerUpEntity newPowerUp = PowerUpFactory.getRandomEntity();
			this.powerUpEntities.add(newPowerUp);
			addPowerUpToImage(newPowerUp);
		}

		updateRoundPowerUps();
	}


	/**
	 * Update the active round power ups
	 */
	private void updateRoundPowerUps() {
		Iterator<PowerUp<RoundPowerUpEffect>> iterator = this.activeRoundEffects.iterator();

		while (iterator.hasNext()) {
			PowerUp<RoundPowerUpEffect> power = iterator.next();
			power.update();
			if (!power.isActive()) {
				power.getEffect().removeEffect(this);
				iterator.remove();
			}
		}
	}

	/**
	 * Remove all active round power ups
	 */
	public void removeAllRoundPowerups() {
		Iterator<PowerUp<RoundPowerUpEffect>> iterator = this.activeRoundEffects.iterator();

		while (iterator.hasNext()) {
			PowerUp<RoundPowerUpEffect> power = iterator.next();

			power.getEffect().removeEffect(this);
			iterator.remove();
		}
	}

	/**
	 * Adds a power up to the powerUpImage and draws it, so that it can be seen by players
	 *
	 * @param newPowerUp - The playerup to be added to the bufferedImage
	 */
	private void addPowerUpToImage(PowerUpEntity newPowerUp) {
		String name = newPowerUp.getPowerUpEffect().getName();
		String toWho;

		if (newPowerUp.getPowerUpType() == PowerUpType.SELF) {
			toWho = "self";
		} else if (newPowerUp.getPowerUpType() == PowerUpType.EVERYONE_ELSE) {
			toWho = "everyoneelse";
		} else {
			toWho = "everyone";
		}

		String fileName = name + toWho;

		BufferedImage image = null;

		switch (fileName) {
			case "fasteveryone":
				image = powerUpImages.get(0);
				break;
			case "fasteveryoneelse":
				image = powerUpImages.get(1);
				break;
			case "fastself":
				image = powerUpImages.get(2);
				break;
			case "thickeveryoneelse":
				image = powerUpImages.get(3);
				break;
			case "thickself":
				image = powerUpImages.get(4);
				break;
			case "wallseveryone":
				image = powerUpImages.get(5);
				break;
			default:
		}

		Graphics2D g2 = powerUpImage.createGraphics();

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g2.drawImage(image, null, (int) (newPowerUp.getPosition().getX() - (newPowerUp.getDiameter() / 2)),
		             (int) (newPowerUp.getPosition().getY() - (newPowerUp.getDiameter() / 2)));

		g2.dispose();
	}

	/**
	 * Remove a powerUp from the Image, used when a player picks up a powerup
	 *
	 * @param powerUp - the powerup to be removed
	 */
	private void removePowerUpFromImage(PowerUpEntity powerUp) {
		Graphics2D g2 = powerUpImage.createGraphics();

		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect((int) (powerUp.getPosition().getX() - powerUp.getDiameter() / 2),
		            (int) (powerUp.getPosition().getY() - powerUp.getDiameter() / 2), (int) powerUp.getDiameter(),
		            (int) powerUp.getDiameter());
		g2.setComposite(AlphaComposite.SrcOver);

		g2.dispose();
	}

	/**
	 * Updates all players one tick forward
	 *
	 * @param time - the current game time
	 */
	private void updatePlayers(int time) {
		for (Player p : this.players) {
			if (!p.isDead()) {

				Position preMove = p.getLine().getPosition();

				p.getLine().update(p, time);

				handleCollisions(p);

				// If player didn't die during his move or is a hole, draw the line
				if (!p.isDead() && !p.getLine().isHole() && !p.isChangingSide()) {
					drawLine(p, preMove);

				}
				// if player is changing side reset it here.
				if (p.isChangingSide()) {
					p.setChangingSide(false);
				}
			}
		}
	}

	/**
	 * Draws a line between the player and its last step.
	 *
	 * @param player  - The player whose line is to be drawn
	 * @param preMove - The Position the player had before the move
	 */
	private void drawLine(Player player, Position preMove) {
		Graphics2D g2 = lineImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(player.getPlayerColor());
		g2.setStroke(new BasicStroke(player.getLine().getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		g2.draw(new Line2D.Float(preMove.getX(), preMove.getY(), player.getLine().getPosition().getX(),
		                         player.getLine().getPosition().getY()));

		g2.dispose();
	}


	/**
	 * Checks if its time for a new round
	 *
	 * @param players - All players in the game
	 *
	 * @return - True if all but one player is dead
	 */
	public boolean newRoundCheck(Player[] players) {
		int stop = players.length - 1;
		int dead = 0;
		for (Player p : players) {
			if (p.isDead()) {
				dead += 1;
			}
		}

		if (stop == dead) {
			return true;
		}
		return false;
	}


	/**
	 * Collision checks for a specific player
	 *
	 * @param player - the player to check
	 */
	public void handleCollisions(Player player) {
		if (collisionHelper.playerOutOfBounds(player)) {
			if (wallsActive) {
				System.out.println("out of bounds");
				killPlayer(player);
			} else {
				collisionHelper.mirrorPlayerPosition(player);
			}
		} else if (collisionHelper.hasPlayerCollided(player)) {
			System.out.println("Collided");
			killPlayer(player);
		} else {
			handleCollisionWithPowerUp(player);
		}
	}

	/**
	 * Check if a player has collided with a powerUp
	 *
	 * @param player - the player to check
	 */
	private void handleCollisionWithPowerUp(Player player) {

		Iterator<PowerUpEntity> iterator = this.powerUpEntities.iterator();

		while (iterator.hasNext()) {
			PowerUpEntity powerUp = iterator.next();

			if (this.collisionHelper.hasCollidedWithPowerUp(player, powerUp)) {
				System.out.println("Collided with Powerup");
				iterator.remove();
				distributePowerUp(player, powerUp, powerUp.getPowerUpType());

				removePowerUpFromImage(powerUp);
			}
		}
	}

	/**
	 * Distribute the pickedup powerUp to the players who should get it
	 *
	 * @param pickedUpBy - the player who picked it up
	 * @param powerUp    - the specific powerUp
	 * @param type       - who are to be affected by the powerup
	 */
	private void distributePowerUp(Player pickedUpBy, PowerUpEntity powerUp, PowerUpType type) {
		PowerUpEffect effect = powerUp.getPowerUpEffect();

		// Abstraction issue that I don't know how to solve.
		if (effect instanceof LinePowerUpEffect) {
			distributePlayerPowerUp(pickedUpBy, type, (LinePowerUpEffect) effect);
		} else if (effect instanceof RoundPowerUpEffect) {
			distributeRoundPowerUp((RoundPowerUpEffect) effect);
		}
	}

	private void distributeRoundPowerUp(RoundPowerUpEffect effect) {
		if (!effect.isStackable()) {
			for (PowerUp<RoundPowerUpEffect> powerUp : this.activeRoundEffects) {
				if (powerUp.getEffect().getClass() == effect.getClass()) {
					powerUp.resetTimer();
					return;
				}
			}
		}
		effect.applyEffect(this);
		this.activeRoundEffects.add(new PowerUp<>(effect));
	}

	private void distributePlayerPowerUp(final Player pickedUpBy, final PowerUpType type,
	                                     final LinePowerUpEffect effect)
	{
		if (type == PowerUpType.SELF) {
			addPowerUpToSelf(pickedUpBy, effect);
		} else if (type == PowerUpType.EVERYONE_ELSE) {
			addPowerUpToEveryoneElse(pickedUpBy, effect);
		} else {
			addPowerUpToEveryone(effect);
		}
	}

	/**
	 * Add a powerup to one self
	 *
	 * @param addEffectTo - who to add the effect to
	 * @param effect      - what the effect is
	 */
	private void addPowerUpToSelf(Player addEffectTo, LinePowerUpEffect effect) {
		addEffectTo.getLine().addPowerUp(effect);
	}

	/**
	 * Add a powerup to everyone but the one who picked it up
	 *
	 * @param addToAllExcept - who not to had it to
	 * @param effect         - what the effect is
	 */
	private void addPowerUpToEveryoneElse(Player addToAllExcept, LinePowerUpEffect effect) {
		for (Player p : this.players) {
			if (!Objects.equals(addToAllExcept, p) && !p.isDead()) {
				p.getLine().addPowerUp(effect);
			}
		}
	}

	/**
	 * Add a powerup to everyone
	 *
	 * @param effect - what the effect is
	 */
	private void addPowerUpToEveryone(LinePowerUpEffect effect) {
		for (Player p : this.players) {
			if (!p.isDead()) {
				p.getLine().addPowerUp(effect);
			}
		}
	}

	/**
	 * Give all alive players a point
	 */
	public void incPointsOnAlivePlayers() {
		for (Player p : players) {
			if (!p.isDead()) {
				p.setPoints(p.getPoints() + 1);
			}
		}

	}

	/**
	 * Kills a player
	 *
	 * @param player - the player who is to be killed
	 */
	private void killPlayer(Player player) {
		player.setDead(true);
		incPointsOnAlivePlayers();
	}


	public void startRightTurn(Player player) {
		player.setTurnRight(true);
	}

	public void startLeftTurn(Player player) {
		player.setTurnLeft(true);
	}

	public void stopRightTurn(Player player) {
		player.setTurnRight(false);
	}

	public void stopLeftTurn(Player player) {
		player.setTurnLeft(false);
	}

	/**
	 * Place all players on random positions on the board
	 *
	 * @param players - the players to place randomly on the board
	 */
	public void placePlayers(Player[] players) {
		for (Player p : players) {
			Line pLine = p.getLine();
			pLine.setPosition(Position.getRandomPosition(SPAWN_LOCATION_LIMIT, SPAWN_LOCATION_LIMIT,
			                                             Game.GAME_WIDTH - SPAWN_LOCATION_LIMIT,
			                                             Game.GAME_HEIGHT - SPAWN_LOCATION_LIMIT));
			pLine.setPi((float) (Math.random() * Math.PI));
			pLine.setWidth(DEFAULT_PLAYER_WIDTH);
			pLine.setSpeed(DEFAULT_SPEED); // set speed of player
			pLine.setHole(false);
			pLine.setTimeWhenHole(0);
			pLine.setOkToHole(0);
			p.setDead(false);
		}
	}

	public void setWallsActive(final boolean wallsActive) {
		this.wallsActive = wallsActive;
	}

}

