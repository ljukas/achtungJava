package engine;

import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import models.Line;
import models.Player;
import window.MainFrame;

import javax.swing.*;

public class Game implements KeyListener, ActionListener
{
    public BufferedImage lineImage;
    private MainFrame frame;
    private Player[] players;
    private Logic logic;
    private Timer timer;
    public boolean pause = true;


    public static final int GAME_WIDTH = 1050;
    public static final int GAME_HEIGHT = 900;
    private static int FPS = 60;

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
        frame.getGamePanel().addPlayers(players);
        frame.getSidePanel().addPlayers(players);
	this.frame.pack();


        logic.placePlayers(players);

        timer = new Timer(1000 / FPS, this); // Fires of the actionPerformed-event on each timer tick

        //actionPerformed(null);


        timer.start();
    }

    // If a player is alive, move it, check if it dies when moved etc.
    @Override public void actionPerformed(final ActionEvent e) {
        Graphics2D g2 = lineImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);


        for(Player p : this.players) {
            Line pLine = p.getLine();

            float x = pLine.x;
            float y = pLine.y;

            boolean drawPlayer = logic.movePlayer(lineImage, p);

            if(drawPlayer && !pLine.changeSide) {
                g2.setColor(p.getPlayerColor());
                g2.setStroke(new BasicStroke(pLine.width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Line2D.Float(x, y, pLine.x, pLine.y));
            }
            pLine.changeSide = false;
        }

        g2.dispose();

        frame.getSidePanel().updatePoints();
        frame.getGamePanel().repaint();

        }

    @Override public void keyPressed(final KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());

        for(Player p : this.players) {
            if(p.getLeftKey() == c) {
                logic.startLeftTurn(p);
            } else if(p.getRightKey() == c) {
                logic.startRightTurn(p);
            }
        }
    }

    @Override public void keyReleased(final KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());


        if(c == ' ' && pause) {
            pause = false;
            start();
        }

        for(Player p : this.players) {
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
