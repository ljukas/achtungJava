package models.powerups.type;

import engine.Round;
import models.powerups.RoundPowerUpEffect;


/**
 * Powerup effect which makes it possible to go through the outer walls.
 */
public class DeactivateWallsPowerUP implements RoundPowerUpEffect
{
    private static final String NAME = "walls";
        private static final int DURATION = 300;
        private static final boolean STACKABLE = false;

    @Override public void applyEffect(final Round round) {
	round.setWallsActive(false);
    }

    @Override public void removeEffect(final Round round) {
        round.setWallsActive(true);
    }

    @Override public int getDuration() {
	return DURATION;
    }

    @Override public String getName() {
	return NAME;
    }

    @Override public boolean isStackable() {
	return STACKABLE;
    }

    @Override public PowerUpType[] getAllowedTypes() {
	return new PowerUpType[] {PowerUpType.EVERYONE};
    }
}
