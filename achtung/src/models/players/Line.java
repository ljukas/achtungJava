package models.players;

import models.Position;
import models.powerups.LinePowerUpEffect;
import models.powerups.PowerUp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The line that each player draws behind them is handled by this class.
 */
public class Line {
    private Position position = null;
    private float pi;
    private float width;
    private double speed;
    private boolean isHole;
    private int timeWhenHole;
    private int okToHole;
    // Dont understand inspection, but it works as intended
    private final List<PowerUp<LinePowerUpEffect>> powerUps = new ArrayList<>();

    private final static int TURN_SHARPNESS = 60;
    private final static double HOLE_TIME_CONSTANT = 2.2;
    private final static int HOLE_LENGTH = 60;
    private final static float HOLE_CHANCE = 0.01f;

    /**
     * Updates a player
     *
     * @param player - The player that is to be updates
     * @param time - The current gametime
     */
    public void update(Player player, int time) {
        movePlayer(player);
        updatePowerUps();
        timeForHole(time);
    }

    /**
     * Moves a player ahead one step
     *
     * @param player - the player that is to be moved
     */
    private void movePlayer(Player player) {
        if (player.isTurnRight()) {
            this.turnRight();
        } else if (player.isTurnLeft()) {
            this.turnLeft();
        }

        this.position.setX(position.getX() + (float) (Math.cos(pi) * speed));
        this.position.setY(position.getY() + (float) (Math.sin(pi) * speed));
    }


    /**
     * Check if its time for a hole
     *
     * @param time - game time, used to determine when a hole is to be cancled
     */
    private void timeForHole(int time) {

        // If the player is to make a hole, stop doing after 13 frames
        if (this.isHole) {
            if ((time - timeWhenHole) >= (width * HOLE_TIME_CONSTANT)) {
                this.isHole = false;
                this.timeWhenHole = 0;
                this.okToHole = HOLE_LENGTH;
            }
            return;
        }

        if (okToHole > 0) {
            this.okToHole = okToHole - 1;
        }

        // Dont let holes be created before 1 second passed since last hole
        if (okToHole == 0) {

            // 1% chance per tick to create hole atm.
            if (Math.random() <= HOLE_CHANCE) {
                this.isHole = true;
                this.timeWhenHole = time;
            }

        }
    }

    /**
     * Add a powerup to a line
     *
     * @param effect - the effect that is to be added to the line
     */
    public void addPowerUp(LinePowerUpEffect effect) {
        if (!effect.isStackable()) {
            for (PowerUp<LinePowerUpEffect> powerUp : this.powerUps) {
                if (powerUp.getEffect().getClass() == effect.getClass()) {
                    powerUp.resetTimer();
                    return;
                }
            }
        }

        effect.applyEffect(this);
        this.powerUps.add(new PowerUp<>(effect));
    }

    /**
     * Updates the powerups that are active. One might need to be removed because its duration has passed
     */
    public void updatePowerUps() {
        Iterator<PowerUp<LinePowerUpEffect>> iterator = this.powerUps.iterator();

        while (iterator.hasNext()) {
            PowerUp<LinePowerUpEffect> power = iterator.next();
            power.update();
            if (!power.isActive()) {
                power.getEffect().removeEffect(this);
                iterator.remove();
            }
        }
    }

    /**
     * Removes all current powerups from a line
     */
    public void removeAllPowerups() {
        Iterator<PowerUp<LinePowerUpEffect>> iterator = this.powerUps.iterator();

        while (iterator.hasNext()) {
            PowerUp<LinePowerUpEffect> power = iterator.next();

            power.getEffect().removeEffect(this);
            iterator.remove();
        }
    }


    public void turnLeft() {
        this.pi = (float) (pi + Math.PI / TURN_SHARPNESS);
    }

    public void turnRight() {
        this.pi = (float) (pi - Math.PI / TURN_SHARPNESS);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getPi() {
        return pi;
    }

    public void setPi(final float pi) {
        this.pi = pi;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(final float width) {
        this.width = width;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    public boolean isHole() {
        return this.isHole;
    }

    // Maybe not optimal name but is easy to understand when reading function that uses it
    public void setHole(final boolean hole) {
        this.isHole = hole;
    }

    public void setTimeWhenHole(final int timeWhenHole) {
        this.timeWhenHole = timeWhenHole;
    }

    public void setOkToHole(final int okToHole) {
        this.okToHole = okToHole;
    }
}
