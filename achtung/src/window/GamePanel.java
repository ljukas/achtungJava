package window;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import engine.Game;

public class GamePanel extends JPanel
{
    private BufferedImage lineImage;

    public GamePanel() {
	setBackground(Color.BLACK);
	setFocusable(true);
	setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);

	Graphics2D g2 = (Graphics2D) g;

	g2.drawImage(this.lineImage, null, 0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
    }

    public void setLineImage(BufferedImage lineImage) {
	this.lineImage = lineImage;
    }
}
