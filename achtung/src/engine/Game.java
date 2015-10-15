package engine;

import window.MainFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Game implements KeyListener
{
    public BufferedImage lineImage;
    private MainFrame frame;

    /**
     * The width of the gameboard.
     */
    public static final int GAME_WIDTH = 1050;
    /**
     * The Height of the gameboard.
     */
    public static final int GAME_HEIGHT = 900;

    public static Game CURRENT_GAME = null;

    public Game(MainFrame frame) {
	this.frame = frame;

	frame.getGamePanel().addKeyListener(this);
    }

    public void start() {
	CURRENT_GAME = this;

	lineImage = new BufferedImage(Game.GAME_WIDTH, Game.GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        frame.getGamePanel().setLineImage(lineImage);

	this.frame.pack();
    }

    @Override public void keyTyped(final KeyEvent e) {

    }

    @Override public void keyPressed(final KeyEvent e) {

    }

    @Override public void keyReleased(final KeyEvent e) {

    }
}
