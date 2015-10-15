package models;

import java.awt.Color;

public class Player
{
    private Color playerColor;
    private char leftKey;
    private char rightKey;
    private Line line;
    private boolean dead;

    public Player(Color playerColor, char leftKey, char rightKey) {
        this.playerColor = playerColor;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.line = new Line();
        this.dead = false;
    }


    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(final Color playerColor) {
        this.playerColor = playerColor;
    }

    public char getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(final char leftKey) {
        this.leftKey = leftKey;
    }

    public char getRightKey() {
        return rightKey;
    }

    public void setRightKey(final char rightKey) {
        this.rightKey = rightKey;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }
}

