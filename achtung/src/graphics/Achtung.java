package graphics;

import engine.Game;

public class Achtung
{
    public static void main(String[] args) {


	MainFrame frame = new MainFrame();
	frame.pack();
	frame.setVisible(true);

	new Game(frame);
    }
}
