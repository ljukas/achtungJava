package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import models.Player;
import window.MainFrame;

public class Game implements KeyListener, ActionListener
{
    public BufferedImage lineImage;
    private MainFrame frame;
    private Player[] players;
    private Logic logic;

    /**
     * The width of the gameboard.
     */
    public static final int GAME_WIDTH = 1050;
    /**
     * The Height of the gameboard.
     */
    public static final int GAME_HEIGHT = 900;

    public static Game CURRENT_GAME = null;

    public Game(Player[] players, Logic logic, MainFrame frame) {
	this.frame = frame;
        this.players = players;
        this.logic = logic;

	frame.getGamePanel().addKeyListener(this);
    }

    public void start() {
	CURRENT_GAME = this;

	lineImage = new BufferedImage(Game.GAME_WIDTH, Game.GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        frame.getGamePanel().setLineImage(lineImage);
        frame.getGamePanel().setPlayers(players);

	this.frame.pack();
    }

    @Override public void actionPerformed(final ActionEvent e) {

        }

    @Override public void keyPressed(final KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());

        for(Player p : players) {
            if(p.getLeftKey() == c) {
                logic.startLeftTurn(p);
            } else if(p.getRightKey() == c) {
                logic.startRightTurn(p);
            }
        }
    }

    @Override public void keyReleased(final KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());

        for(Player p : players) {
            if(p.getLeftKey() == c) {
                logic.stopLeftTurn(p);
            } else if(p.getRightKey() == c) {
                logic.stopRightTurn(p);
            }
        }
    }

    @Override public void keyTyped(final KeyEvent e) {

    }

}
