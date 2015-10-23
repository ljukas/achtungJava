package engine;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

import models.Line;
import models.Player;


public class Logic
{
    public void timeForHole(Player player, int time) {
        Line pLine = player.getLine();

        // If the player is to make a hole, stop doing after 13 frames
        if(pLine.isHoleInLine()) {
            if((time - pLine.getTimeWhenHole()) >= (pLine.getWidth() * 2.5)) {
                pLine.setHoleInLine(false);
                pLine.setTimeWhenHole(0);
                pLine.setOkToHole(60);
            }
            return;
        }

        if(pLine.getOkToHole() > 0) {
            pLine.setOkToHole(pLine.getOkToHole() - 1);
        }

        // Dont let holes be created first 1.5 seconds of the game or before 1 second passed since last hole
        if(time > 90 && pLine.getOkToHole() == 0) {
            Random rand = new Random();

            int startHole = rand.nextInt(200) + 1;

            if(startHole > 197) {
                pLine.setHoleInLine(true);
                pLine.setTimeWhenHole(time);
            }

        }


    }

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
        float dx = pLine.getX() + (float) (Math.cos(pLine.getPi()) * pLine.getSpeed() * (pLine.getWidth() / 1.75));
        float dy = pLine.getY() + (float) (Math.sin(pLine.getPi()) * pLine.getSpeed() * (pLine.getWidth() / 1.75));

        float dlx = pLine.getX() + (float) (Math.cos(pLine.getPi() + (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / 1.75));
        float dly = pLine.getY() + (float) (Math.sin(pLine.getPi() + (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / 1.75));

        float drx = pLine.getX() + (float) (Math.cos(pLine.getPi() - (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / 1.75));
        float dry = pLine.getY() + (float) (Math.sin(pLine.getPi() - (Math.PI/4)) * pLine.getSpeed() * (pLine.getWidth() / 1.75));


        // Check if the collission check is out of bonds. If it is we dont want to check there
        if(dx > Game.GAME_WIDTH || dx < 0 || dy < 0 || dy > Game.GAME_HEIGHT) {
            colission(player);
            return false;
        }
        if(dlx > Game.GAME_WIDTH || dlx < 0 || dly < 0 || dly > Game.GAME_HEIGHT) {
            colission(player);
            return false;
        }
        if(drx > Game.GAME_WIDTH || drx < 0 || dry < 0 || dry > Game.GAME_HEIGHT) {
            colission(player);
            return false;
        }


        if(lineImage.getRGB((int)dx, (int)dy) != 0 ||
           lineImage.getRGB((int)dlx, (int)dly) != 0  ||
           lineImage.getRGB((int)drx, (int)dry) != 0) {
            colission(player);
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

    private void colission(Player player) {
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
            pLine.setX((float) (Math.random() * (Game.GAME_WIDTH - 150) + 100));
            pLine.setY((float) (Math.random() * (Game.GAME_HEIGHT - 150) + 100));
            pLine.setPi((float) (Math.random() * Math.PI));
            pLine.setWidth(5); // set witdh of player to 5 pixels
            pLine.setSpeed(1.75); // set speed of player
            pLine.setHoleInLine(false);
            pLine.setTimeWhenHole(0);
            pLine.setOkToHole(0);
            p.setDead(false);
        }
    }

}
