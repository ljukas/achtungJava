package engine;

import java.awt.image.BufferedImage;

import models.Line;
import models.Player;


public class Logic
{

    public boolean movePlayer(BufferedImage lineImage, Player player) {
            if(player.isDead()) {
                return false;
            }

            Line pLine = player.getLine();

            if(player.isTurnRight()) {
                pLine.turnRight();
            } else if(player.isTurnLeft()) {
                pLine.turnLeft();
            }

            // check if collission
            boolean col = checkCollission(lineImage, player);

            if(col) {
                return false;
            }

            pLine.movePlayer();

            if(pLine.x < 0) {
                pLine.x = Game.GAME_WIDTH;
                pLine.changeSide = true;
            } else if (pLine.x > Game.GAME_WIDTH) {
                pLine.x = 0;
                pLine.changeSide = true;
            }

            if(pLine.y < 0) {
                pLine.y = Game.GAME_HEIGHT;
                pLine.changeSide = true;
            } else if (pLine.y > Game.GAME_HEIGHT) {
                pLine.y = 0;
                pLine.changeSide = true;
            }

        return true;
    }

    private boolean checkCollission(BufferedImage lineImage, Player player) {
        Line pLine = player.getLine();

        float dx = pLine.x + (float) (Math.cos(pLine.pi) * pLine.speed * 2.75);
        float dy = pLine.y + (float) (Math.sin(pLine.pi) * pLine.speed * 2.75);

        if(lineImage.getRGB((int)dx, (int)dy) != 0) {
            collission(player);
            return true;
        }
        return false;
    }

    private void collission(Player player) {
        player.setDead(true);
        player.setPoints(player.getPoints() + 1);

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

    public void placePlayers(Player[] players) {
        for(Player p : players) {
            Line pLine = p.getLine();
            pLine.x = (float) (Math.random() * (Game.GAME_WIDTH - 150) + 100);
            pLine.y = (float) (Math.random() * (Game.GAME_HEIGHT - 150) + 100);
            pLine.pi = (float) (Math.random() * Math.PI);
            pLine.width = 5; // set witdh of player to 5 pixels
            pLine.speed = 1.5; // set speed of player
            p.setDead(false);
        }
    }

}
