package models.powerups.type;

import models.players.Line;
import models.powerups.LinePowerUpEffect;

/**
 * Created by Lukas on 2015-11-10.
 */
public class ThickPowerUp implements LinePowerUpEffect {

    private static final String NAME = "thick";
    private static final int DURATION = 250;
    private static final boolean STACKABLE = false;

    @Override
    public void applyEffect(Line line) {
        line.setWidth(line.getWidth() * 2);
    }

    @Override
    public void removeEffect(Line line) {
        line.setWidth(line.getWidth() / 2);
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
        return new PowerUpType[] {PowerUpType.SELF, PowerUpType.EVERYONE_ELSE};
    }
}
