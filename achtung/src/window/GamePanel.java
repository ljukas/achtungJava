package window;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import engine.Game;
import models.Line;
import models.Player;

public class GamePanel extends JPanel
{
    private BufferedImage lineImage = null;
    private Player[] players = null;

    public GamePanel() {
	setBackground(Color.BLACK);
	setFocusable(true);
	setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    // Draw players
    @Override
    public void paint(Graphics g) {
	super.paint(g);

	Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(0, getHeight());
        g2.scale(1.0, -1.0);

	g2.drawImage(this.lineImage, null, 0, 0);

        if(this.players == null) return;

        for(Player p : this.players) {
            Line pLine = p.getLine();
            g2.setColor(p.getPlayerColor());
            g2.fillOval((int) (pLine.getX() - pLine.getWidth() / 2), (int) (pLine.getY() - pLine.getWidth() / 2), (int) pLine
                    .getWidth(), (int) pLine.getWidth());
        }
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
    }

    public void setLineImage(BufferedImage lineImage) {
	this.lineImage = lineImage;
    }

    public void addPlayers(Player[] players) {
        this.players = players;
    }
}
