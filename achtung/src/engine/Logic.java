package engine;

import models.Player;

public class Logic
{
    public void startRightTurn(Player player) {
	player.setTurnRight(true);
    }

    public void startLeftTurn(Player player) {
	player.setTurnLeft(true);
    }

    public void stopRightTurn(Player player) {
	player.setTurnRight(false);
    }

    public void stopLeftTurn(Player player) {
	player.setTurnLeft(false);
    }

}
