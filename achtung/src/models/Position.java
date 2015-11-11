package models;

/**
 * Created by Lukas on 2015-11-09.
 */
public class Position {

    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float distanceFrom(Position otherPosition) {
        return (float) Math.sqrt(Math.pow(this.x - otherPosition.getX(), 2)
                + Math.pow(this.y - otherPosition.getY(), 2));
    }

    public static Position getRandomPosition(float minX, float minY, float maxX, float maxY) {
        float randX = (float) (minX + Math.random() * (maxX - minX));
        float randY = (float) (minY + Math.random() * (maxY - minY));

        return new Position(randX, randY);
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
