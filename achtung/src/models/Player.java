package models;

import java.awt.Color;

/**
 * Contains info about a player, like which keys is used to stear it and if its dead etc.
 * Also its color is stored here.
 */
public class Player
{
    private Color playerColor;
    private char leftKey;
    private char rightKey;
    private boolean turnRight;
    private boolean turnLeft;
    private Line line;
    private boolean dead;
    private boolean collided;
    private int points;

    // Constructor
    public Player(Color playerColor, char leftKey, char rightKey) {
        this.playerColor = playerColor;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.line = new Line();
        this.dead = false;
        this.points = 0;
        this.collided = false;
    }


    // Getters and setters

    public Color getPlayerColor() {
        return playerColor;
    }

    public char getLeftKey() {
        return leftKey;
    }

    public char getRightKey() {
        return rightKey;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }

    public void setTurnRight(final boolean turnRight) {
        this.turnRight = turnRight;
    }

    public void setTurnLeft(final boolean turnLeft) {
        this.turnLeft = turnLeft;
    }

    public boolean isTurnRight() {
        return turnRight;
    }

    public boolean isTurnLeft() {
        return turnLeft;
    }

    public Line getLine() {
        return line;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public void setCollided(final boolean collided) {
        this.collided = collided;
    }

    public boolean didCollide() {
        return collided;
    }
}


