package models.powerups;

/**
 * Interface over the different functions a powerUp must have.
 */
public interface PowerUpEffect {

    // Wants javadoc for enums, not needed.
    enum PowerUpType {
        SELF, EVERYONE, EVERYONE_ELSE
    }

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
     * @return - if type is allowed
     */
    PowerUpType[] getAllowedTypes();

}
