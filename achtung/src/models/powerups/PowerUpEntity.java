package models.powerups;

import models.Position;
import models.powerups.PowerUpEffect.PowerUpType;

/**
 * Contains all the info about a powerup.
 */
public class PowerUpEntity {

    private final Position position;
    private final PowerUpEffect powerUpEffect;
    private final PowerUpType powerUpType;
    private final float diameter;
    private static final float DEFAULT_DIAMETER = 20;

    public PowerUpEntity(Position position, float diameter, PowerUpEffect powerUpEffect, PowerUpType powerUpType ) {
        this.position = position;
        this.powerUpEffect = powerUpEffect;
        this.powerUpType = powerUpType;
        this.diameter = diameter;
    }


    public Position getPosition() {
        return position;
    }

    public float getDiameter() {
        return diameter;
    }

    public PowerUpEffect getPowerUpEffect() {
        return powerUpEffect;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public static float getDefaultDiameter() {
        return DEFAULT_DIAMETER;
    }
}
