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

        float dx = getDx(pLine, pPos);
        float dy = getDy(pLine, pPos);

        float dlx = getDlx(pLine, pPos);
        float dly = getDly(pLine, pPos);

        float drx = getDrx(pLine, pPos);
        float dry = getDry(pLine, pPos);

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

        float dx = getDx(pLine, pPos);
        float dy = getDy(pLine, pPos);

        float dlx = getDlx(pLine, pPos);
        float dly = getDly(pLine, pPos);

        float drx = getDrx(pLine, pPos);
        float dry = getDry(pLine, pPos);


        // Works as intended, can collapse but loses readability.
        if (dx > Game.GAME_WIDTH || dx < 0 || dy < 0 || dy > Game.GAME_HEIGHT) {
            return true;
        } else if (dlx > Game.GAME_WIDTH || dlx < 0 || dly < 0 || dly > Game.GAME_HEIGHT) {
            return true;
        } else if (drx > Game.GAME_WIDTH || drx < 0 || dry < 0 || dry > Game.GAME_HEIGHT) {
            return true;
        }

        return false;
    }

    private float getDry(final Line pLine, final Position pPos) {return pPos.getY() + (float) (Math.sin(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));}

    private float getDrx(final Line pLine, final Position pPos) {return pPos.getX() + (float) (Math.cos(pLine.getPi() - (Math.PI / 4)) * (pLine.getWidth()));}

    private float getDly(final Line pLine, final Position pPos) {return pPos.getY() + (float) (Math.sin(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));}

    private float getDlx(final Line pLine, final Position pPos) {return pPos.getX() + (float) (Math.cos(pLine.getPi() + (Math.PI / 4)) * (pLine.getWidth()));}

    private float getDy(final Line pLine, final Position pPos) {return pPos.getY() + (float) (Math.sin(pLine.getPi()) * (pLine.getWidth()));}

    private float getDx(final Line pLine, final Position pPos) {return pPos.getX() + (float) (Math.cos(pLine.getPi()) * (pLine.getWidth()));}

    public boolean hasCollidedWithPowerUp(Player player, PowerUpEntity powerUp) {
        Line pLine = player.getLine();
        Position pPos = pLine.getPosition();

        float playerDiam = pLine.getWidth();
        float powerUpDiam = powerUp.getDiameter();

        return pPos.distanceFrom(powerUp.getPosition()) < ((powerUpDiam / 2) + (playerDiam / 2));

    }

    public void mirrorPlayerPosition(Player player) {
        player.setChangingSide(true);
        Position pPos = player.getLine().getPosition();

        float curX = pPos.getX();
        float curY = pPos.getY();

        float newX = curX;
        float newY = curY;

        if(leftSideExit(curX)) {
            newX = Game.GAME_WIDTH;
        } else if(rightSideExit(curX)) {
            newX = 0;
        } else if(topSideExit(curY)) {
            newY = 0;
        } else if(bottomSideExit(curY)) {
            newY = Game.GAME_HEIGHT;
        } else {
            return;
        }
        Position newPos = new Position(newX, newY);

        player.getLine().setPosition(newPos);
    }

    private boolean bottomSideExit(final float curY) {
        return curY < 0;
    }

    private boolean topSideExit(final float curY) {
        return curY > Game.GAME_HEIGHT;
    }

    private boolean rightSideExit(final float curX) {
        return curX > Game.GAME_WIDTH;
    }

    private boolean leftSideExit(final float curX) {
        return curX < 0;
    }


}
