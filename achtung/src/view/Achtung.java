package view;

/**
 * Class containing Main function. Seperated from other classes for easier to read code
 */
public final class Achtung
{

	private Achtung() {}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();

		frame.initGame();

		frame.pack();
		frame.setVisible(true);
    }
}
