package models;

public class Line
{
    private float x;
    private float y;
    private float pi;
    private float width;
    private double speed;
    private boolean changeSide;
    private boolean holeInLine;
    private int timeWhenHole;
    private int okToHole;

    private static final int TURN_SHARPNESS = 60;


    public void movePlayer() {
        this.x = x + (float) (Math.cos(pi) * speed);
        this.y = y + (float) (Math.sin(pi) * speed);
    }

    public void turnLeft() {
        this.pi = (float) (pi + Math.PI / TURN_SHARPNESS);
    }

    public void turnRight() {
        this.pi = (float) (pi - Math.PI / TURN_SHARPNESS);
    }

    public float getX() {
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(final float y) {
        this.y = y;
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

    public boolean isChangeSide() {
        return changeSide;
    }

    public void setChangeSide(final boolean changeSide) {
        this.changeSide = changeSide;
    }

    public boolean isHoleInLine() {
        return holeInLine;
    }

    public void setHoleInLine(final boolean holeInLine) {
        this.holeInLine = holeInLine;
    }

    public int getTimeWhenHole() {
        return timeWhenHole;
    }

    public void setTimeWhenHole(final int timeWhenHole) {
        this.timeWhenHole = timeWhenHole;
    }

    public int getOkToHole() {
        return okToHole;
    }

    public void setOkToHole(final int okToHole) {
        this.okToHole = okToHole;
    }
}
