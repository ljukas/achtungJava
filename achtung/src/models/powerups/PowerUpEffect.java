package models.powerups;

import models.players.Line;

/**
 * Interface over the different functions a powerUp must have.
 */
public interface PowerUpEffect {

    /**
     * Add the effect of the power up to a line.
     *
     * @param line - the line that gets the power up added
     */
    void applyEffect(Line line);

    /**
     * Remove the effect of the power up from a line.
     *
     * @param line - the line that gets the power up removed.
     */
    void removeEffect(Line line);

    /**
     * @return the duration of this power up
     */
    int getDuration();

    /**
     * @return the name of this powerup-type
     */
    String getName();

    /**
     * If a powerup is stackable the effect is addad again, if its not
     * the timer is reset instead.
     *
     * @return true if the power up is stackable
     */
    boolean isStackable();

    /**
     * Returns trueif the type is allowed for the particular power up
     *
     * @param - who the power up affects
     *
     * @return - if type is allowed
     */
    PowerUpType[] getAllowedTypes();

}
