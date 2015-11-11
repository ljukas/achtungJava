package engine;

import models.players.Line;
import models.players.Player;
import models.Position;
import models.powerups.PowerUpEntity;

import java.awt.image.BufferedImage;

/**
 * Created by Lukas on 2015-11-09.
 */
public class CollisionHelper {

    private BufferedImage lineImage;


    public CollisionHelper(BufferedImage lineImage) {
        this.lineImage = lineImage;
    }

    public boolean hasPlayerCollided(Player player) {
        Line pLine = player.getLine();
        Position pPos = pLine.getPosition();

        float dx = pPos.getX() + (float) (Math.cos(pLine.getPi()) * (pLine.getWidth()));
        float dy = pPos.getY() + (float) (Math.sin(pLine.getPi()) * (pLine.getWidth()));

        float dlx = pPos.getX() + (float) (Math.cos(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));
        float dly = pPos.getY() + (float) (Math.sin(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));

        float drx = pPos.getX() + (float) (Math.cos(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));
        float dry = pPos.getY() + (float) (Math.sin(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));

        if (lineImage.getRGB((int) dx, (int) dy) != 0 ||
                lineImage.getRGB((int) dlx, (int) dly) != 0 ||
                lineImage.getRGB((int) drx, (int) dry) != 0) {
            return true;
        }

        return false;
    }

    public boolean playerOutOfBounds(Player player) {
        Line pLine = player.getLine();
        Position pPos = pLine.getPosition();

        float dx = pPos.getX() + (float) (Math.cos(pLine.getPi()) * (pLine.getWidth()));
        float dy = pPos.getY() + (float) (Math.sin(pLine.getPi()) * (pLine.getWidth()));

        float dlx = pPos.getX() + (float) (Math.cos(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));
        float dly = pPos.getY() + (float) (Math.sin(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));

        float drx = pPos.getX() + (float) (Math.cos(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));
        float dry = pPos.getY() + (float) (Math.sin(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));


        if (dx > Game.GAME_WIDTH || dx < 0 || dy < 0 || dy > Game.GAME_HEIGHT) {
            return true;
        } else if (dlx > Game.GAME_WIDTH || dlx < 0 || dly < 0 || dly > Game.GAME_HEIGHT) {
            return true;
        } else if (drx > Game.GAME_WIDTH || drx < 0 || dry < 0 || dry > Game.GAME_HEIGHT) {
            return true;
        }

        return false;
    }

    public boolean hasCollidedWithPowerUp(Player player, PowerUpEntity powerUp) {
        Line pLine = player.getLine();
        Position pPos = pLine.getPosition();

        float playerDiam = pLine.getWidth();
        float powerUpDiam = powerUp.getDiameter();

        return pPos.distanceFrom(powerUp.getPosition()) < ((powerUpDiam / 2) + (playerDiam / 2));

    }
}
