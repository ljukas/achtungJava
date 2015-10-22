package models;

import engine.Game;

public class Line
{
    public float x;
    public float y;
    public float pi;
    public float width;
    public boolean changeSide;


    public Line() {
    }

    public void movePlayer() {
        x += (float) (Math.cos(pi) * 1.5);
        y += (float) (Math.sin(pi) * 1.5);
    }

    public void turnLeft() {
        pi += Math.PI / 60;
    }

    public void turnRight() {
        pi -= Math.PI / 60;
    }
}
