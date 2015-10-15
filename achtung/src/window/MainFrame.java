package window;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame
{
    private GamePanel gamePanel = null;
    public SidePanel sidePanel = null;

    public MainFrame() {
	super("Achtung av Lukas");

	init();
    }

    public void init() {
	setResizable(false);

	JPanel mainPanel = (JPanel) getContentPane();
	mainPanel.setLayout(new BorderLayout());		// BorderLayout used since there are only two different panels.

	gamePanel = new GamePanel();
	sidePanel = new SidePanel();

	mainPanel.add(gamePanel, BorderLayout.CENTER);
	mainPanel.add(sidePanel, BorderLayout.LINE_END);

	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public GamePanel getGamePanel() {
	return gamePanel;
    }

    public SidePanel getSidePanel() {
	return sidePanel;
    }
}
