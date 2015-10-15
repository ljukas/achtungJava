package graphics;

import java.awt.BorderLayout;

import javax.swing.*;

public class MainFrame extends JFrame
{
    private GamePanel gamePanel;

    public MainFrame() {
	super("Achtung, ...");

	init();
    }

    public void init() {
	setResizable(false);

	JPanel mainPanel = (JPanel) getContentPane();
	mainPanel.setLayout(new BorderLayout());		// BorderLayout used since there are only two different panels.

	gamePanel = new GamePanel();
    }
}
