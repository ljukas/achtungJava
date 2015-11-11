package models.powerups;

import engine.Game;
import models.Position;
import models.powerups.type.FastPowerUp;
import models.powerups.type.ThickPowerUp;

import java.util.Random;

/**
 * A Factory that generates a random powerup with a random powerUpType depending on what is
 * allowed for that powerup.
 */
public class PowerUpFactory {
    private static final PowerUpEffect[] effects = new PowerUpEffect[] {
            new FastPowerUp(), new ThickPowerUp()
    };

    private PowerUpFactory() {
    }

    /**
     * @return a random PowerUpEntity
     */
    public static PowerUpEntity getRandomEntity() {

        // Gets a random effect
        PowerUpEffect effect = getRandomEffect();

        float diameter = PowerUpEntity.getDefaultDiameter();

        float minX = diameter;
        float maxX = Game.GAME_WIDTH - diameter;
        float minY = diameter;
        float maxY = Game.GAME_HEIGHT - diameter;

        Position randPos = Position.getRandomPosition(minX, minY, maxX, maxY);

        PowerUpType[] allowedTypes = effect.getAllowedTypes();
        PowerUpType powerUpType = allowedTypes[(int) (allowedTypes.length * Math.random())];

        PowerUpEntity entity = new PowerUpEntity(randPos, diameter, effect, powerUpType);

        return entity;
    }

    /**
     * @return a random effect, used by getRandomEntity
     */
    private static PowerUpEffect getRandomEffect() {
        return effects[new Random().nextInt(effects.length)];
    }
}
