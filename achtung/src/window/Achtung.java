package window;

import engine.Game;
import engine.Logic;
import java.awt.Color;
import models.Player;

public class Achtung
{
    public static void main(String[] args) {


	MainFrame frame = new MainFrame();
	frame.pack();
	frame.setVisible(true);

	Logic logic = new Logic();
	Player[] players = new Player[] {
			new Player(Color.CYAN, 'a', 's'),
			// new Player(Color.RED, 'k', 'l'),
	};

	new Game(players, logic, frame);
    }
}
