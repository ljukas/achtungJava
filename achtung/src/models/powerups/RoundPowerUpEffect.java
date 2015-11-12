package models.powerups;

import engine.Round;

/**
 * Power up effects that affects the round and not the players.
 */
public interface RoundPowerUpEffect extends PowerUpEffect
{

    /**
     * Applies the given effect to the Round
     *
     * @param round - the round to apply effect to
     */
    void applyEffect(Round round);


    /**
     * Removes the given effect from the Round
     *
     * @param round - the round to remove the effect from
     */
    void removeEffect(Round round);
}
