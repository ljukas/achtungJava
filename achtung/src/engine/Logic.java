package engine;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

import models.Line;
import models.Player;


/**
 * Contains all the game logic like collission, points and when to create holes etc.
 */
public class Logic
{
    private final static double HOLE_TIME_CONSTANT = 2.2;
    private final static int HOLE_LENGTH = 60;
    private final static int TIME_BEFORE_FIRST_HOLE = 90;

    private final static int HOLE_CHANCE_LIMIT = 300;
    private final static int HOLE_CHANCE = 297;

    private final static double COLLISION_CONSTANT = 1.75;
    private final static double SPEED_CONSTANT = 1.75;

    private final static int SPAWN_LOCATION_LIMIT = 150;

    // Checks if its time for a player to start a hole and if a player is ahole check if its time to stop.
    public void timeForHole(Player player, int time) {
        Line pLine = player.getLine();

        // If the player is to make a hole, stop doing after 13 frames
        if(pLine.isHoleInLine()) {
            if((time - pLine.getTimeWhenHole()) >= (pLine.getWidth() * HOLE_TIME_CONSTANT)) {
                pLine.setHoleInLine(false);
                pLine.setTimeWhenHole(0);
                pLine.setOkToHole(HOLE_LENGTH);
            }
            return;
        }

        if(pLine.getOkToHole() > 0) {
            pLine.setOkToHole(pLine.getOkToHole() - 1);
        }

        // Dont let holes be created first 1.5 seconds of the game or before 1 second passed since last hole
        if(time > TIME_BEFORE_FIRST_HOLE && pLine.getOkToHole() == 0) {
            Random rand = new Random();

            int startHole = rand.nextInt(HOLE_CHANCE_LIMIT) + 1;

            if(startHole > HOLE_CHANCE) {
                pLine.setHoleInLine(true);
                pLine.setTimeWhenHole(time);
            }

        }


    }

    // Checks if its time to start a new round
    public boolean newRoundCheck(Player[] players) {
        int stop = players.length - 1;
        int dead = 0;
        for(Player p : players) {
            if(p.isDead()) {
                dead += 1;
            }
        }

        if(stop == dead) {
            return true;
        }
        return false;
    }

    // Moves a player one step forward. Also checks collision
    public boolean movePlayer(BufferedImage lineImage, Player player) {
        Line pLine = player.getLine();

        /* Dont draw the player
         *  - If player is dead.
         */
        if(player.isDead()) {
            return false;
        }

        /*
         * Check if the player needs to change side and if so change it.
         * If the player needs to change dont draw it
         */
        // if(checkAndChangeSide(pLine)) return false;

        /*
         * Check collission
         *  - It checks in three directions. Forward, forward-left, and forward-right.
         *  - This is because otherwise you can kind of drive over other people with your
         *  - snake body.
         */
        float dx = pLine.getX() + (float) (Math.cos(pLine.getPi()) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));
        float dy = pLine.getY() + (float) (Math.sin(pLine.getPi()) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));

        float dlx = pLine.getX() + (float) (Math.cos(pLine.getPi() + (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));
        float dly = pLine.getY() + (float) (Math.sin(pLine.getPi() + (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));

        float drx = pLine.getX() + (float) (Math.cos(pLine.getPi() - (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));
        float dry = pLine.getY() + (float) (Math.sin(pLine.getPi() - (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / COLLISION_CONSTANT));


        // Check if the snake is driving out of bounds. If so it dies.
        if(dx > Game.GAME_WIDTH || dx < 0 || dy < 0 || dy > Game.GAME_HEIGHT) {
            collision(player);
            return false;
        }
        if(dlx > Game.GAME_WIDTH || dlx < 0 || dly < 0 || dly > Game.GAME_HEIGHT) {
            collision(player);
            return false;
        }
        if(drx > Game.GAME_WIDTH || drx < 0 || dry < 0 || dry > Game.GAME_HEIGHT) {
            collision(player);
            return false;
        }


        if(lineImage.getRGB((int)dx, (int)dy) != 0 ||
           lineImage.getRGB((int)dlx, (int)dly) != 0  ||
           lineImage.getRGB((int)drx, (int)dry) != 0) {
            collision(player);
            return false;
        }

        /*
         * Calculate the move for the player, starting with the turning.
         * Then move it
         */
        if(player.isTurnRight()) {
            pLine.turnRight();
        } else if(player.isTurnLeft()) {
            pLine.turnLeft();
        }
        pLine.movePlayer();

        if(pLine.isHoleInLine()) {
            return false;
        }

        return true;
    }

    // Give all other players one point if player collided with wall/player.
    public void givePoints(Player[] players, Player p) {
        if(p.didCollide()) {
            for(Player oP : players) {
                if(!Objects.equals(oP.getPlayerColor(), p.getPlayerColor()) && !oP.isDead()) {
                    oP.setPoints(oP.getPoints() + 1);
                }
            }
            p.setCollided(false);
        }
    }

    // Kills a player and sets collision constant used in other functions
    private void collision(Player player) {
            player.setDead(true);
            player.setCollided(true);
        }

    // Can be used for superpower that makes it possible to drive through outer walls.
    private boolean checkAndChangeSide(final Line pLine) {
        if(pLine.getX() < 0) {
            pLine.setX(Game.GAME_WIDTH);
            pLine.setChangeSide(true);
            return true;
        } else if (pLine.getX() > Game.GAME_WIDTH) {
            pLine.setX(0);
            pLine.setChangeSide(true);
            return true;
        }

        if(pLine.getY() < 0) {
            pLine.setY(Game.GAME_HEIGHT);
            pLine.setChangeSide(true);
            return true;
        } else if (pLine.getY() > Game.GAME_HEIGHT) {
            pLine.setY(0);
            pLine.setChangeSide(true);
            return true;
        }
        return false;
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

    // Place players on the board and also initializes their lines.
    public void placePlayers(Player[] players) {
        for(Player p : players) {
            Line pLine = p.getLine();
            pLine.setX((float) (Math.random() * (Game.GAME_WIDTH - SPAWN_LOCATION_LIMIT) + 100));
            pLine.setY((float) (Math.random() * (Game.GAME_HEIGHT - SPAWN_LOCATION_LIMIT) + 100));
            pLine.setPi((float) (Math.random() * Math.PI));
            pLine.setWidth(5); // set width of player to 5 pixels
            pLine.setSpeed(SPEED_CONSTANT); // set speed of player
            pLine.setHoleInLine(false);
            pLine.setTimeWhenHole(0);
            pLine.setOkToHole(0);
            p.setDead(false);
        }
    }

}
