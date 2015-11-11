package models.powerups;

/**
 * Created by Lukas on 2015-11-09.
 */

/**
 * Represents a power up in its applied state.
 *
 * It keeps track of how long a power has lasted anw ehn it should be removed.
 *
 * @param <T> the type of effect this keeps track of
 */
public class PowerUp<T extends PowerUpEffect> {

    private final T effect;
    private int timeElapsed;

    public PowerUp(T effect) {
        this.effect = effect;
    }

    /**
     * @return true if power up is still active
     */
    public boolean isActive() {
        return this.effect.getDuration() - this.timeElapsed > 0;
    }

    /**
     * Update the power up. Called every tick
     */
    public void update() {
        this.timeElapsed++;
    }

    /**
     * @return the effect of the powerUp
     */
    public T getEffect() {
        return this.effect;
    }

    /**
     * @return the duration left of power up.
     */
    public int getTimeLeft() {
        return this.effect.getDuration() - this.timeElapsed;
    }

    /**
     * Reset the timer of a powerup so that its duration is extended.
     */
    public void resetTimer() {
        this.timeElapsed = 0;
    }

}
