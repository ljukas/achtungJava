package models.powerups;

import models.players.Line;

/**
 * Interface for Power Ups affecting players
 */
public interface LinePowerUpEffect extends PowerUpEffect
{
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
}
