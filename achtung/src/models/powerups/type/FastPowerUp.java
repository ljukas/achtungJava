package models.powerups.type;

import models.players.Line;
import models.powerups.LinePowerUpEffect;

/**
 * Created by Lukas on 2015-11-09.
 */
public class FastPowerUp implements LinePowerUpEffect
{

    private static final String NAME = "fast";
    private static final int DURATION = 180;
    private static final boolean STACKABLE = true;


    @Override
    public void applyEffect(Line line) {
        line.setSpeed(line.getSpeed() * 2);
    }

    @Override
    public void removeEffect(Line line) {
        line.setSpeed(line.getSpeed() / 2);
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isStackable() {
        return STACKABLE;
    }

    @Override
    public PowerUpType[] getAllowedTypes() {
        return new PowerUpType[] {PowerUpType.SELF, PowerUpType.EVERYONE_ELSE, PowerUpType.EVERYONE};
    }
}
