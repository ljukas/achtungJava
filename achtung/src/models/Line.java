package models;

public class Line
{
    public float x;
    public float y;
    public float pi;
    public float width;
    public double speed;
    public boolean changeSide;

    private static final int TURN_SHARPNESS = 60;


    public Line() {
    }

    public void movePlayer() {
        x += (float) (Math.cos(pi) * speed);
        y += (float) (Math.sin(pi) * speed);
    }

    public void turnLeft() {
        pi += Math.PI / TURN_SHARPNESS;
    }

    public void turnRight() {
        pi -= Math.PI / TURN_SHARPNESS;
    }
}
