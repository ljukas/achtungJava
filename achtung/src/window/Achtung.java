package window;

import engine.Game;
import engine.Logic;
import java.awt.Color;
import models.Player;

public final class Achtung
{
    private Achtung() {}

    public static void main(String[] args) {


	MainFrame frame = new MainFrame();
	frame.pack();
	frame.setVisible(true);

	Logic logic = new Logic();
	Player[] players = new Player[] {
			new Player(Color.CYAN, 'q', 'w'),
			new Player(Color.RED, 'o', 'p'),
			//new Player(Color.YELLOW, 'z', 'x'),
			//new Player(Color.GREEN, 'b', 'n'),
	};

	final Game game = new Game(players, logic, frame);
    }
}
