package engine;

import java.awt.event.KeyAdapter;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import models.Line;
import models.Player;
import window.MainFrame;

import javax.swing.*;

public class Game extends KeyAdapter implements ActionListener
{
    /**
     * Game width is 1050 pixels
     */
    public static final int GAME_WIDTH = 1050;
    /**
     * Game height is 900 pixels
     */
    public static final int GAME_HEIGHT = 900;
    /**
     * The delay between each round starts is 2 seconds
     */
    private static final int INITIAL_DELAY = 2000;
    /**
     * The game speed is 60 frames per second
     */
    private static final int FPS = 60;

    private BufferedImage lineImage = null;
    private MainFrame frame;
    private Player[] players;
    private Logic logic;
    private Timer timer = null;
    private boolean pause = true;
    private int winCondition;
    private int time;

    public Game(Player[] players, Logic logic, MainFrame frame) {
        this.frame = frame;
        this.players = players;
        this.logic = logic;

        frame.getGamePanel().addKeyListener(this);
    }

    public void start() {

        lineImage = new BufferedImage(Game.GAME_WIDTH, Game.GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

        frame.getGamePanel().setLineImage(lineImage);
        frame.getGamePanel().addPlayers(players);
        frame.getSidePanel().addPlayers(players);
        this.frame.pack();

        // Place the players on the board
        logic.placePlayers(players);

        // Repaint here so when a new round start the old game isn't shown in the 2 sec starting period
        frame.getGamePanel().repaint();

        // The rules of the game state that to win you have to get points equal to 10 times number of players minus 10
        winCondition = this.players.length * 10 - 10;

        time = 0;
        timer = new Timer(1000 / FPS, this);    // Fires of the actionPerformed-event on each timer tick
        timer.setInitialDelay(INITIAL_DELAY);            // Start game after 2 sec delay
        timer.start();                          // Start game
    }

    // If a player is alive, move it, check if it dies when moved etc.
    @Override public void actionPerformed(final ActionEvent e) {
        Graphics2D g2 = lineImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);


        for(Player p : this.players) {
            logic.timeForHole(p, time);
            Line pLine = p.getLine();

            float x = pLine.getX();
            float y = pLine.getY();

            // Move the current player one step ahead and check if we want to draw the line behind the player
            boolean drawLine = logic.movePlayer(lineImage, p);

            // Draw the line behind the player
            if(drawLine) {
                g2.setColor(p.getPlayerColor());
                g2.setStroke(new BasicStroke(pLine.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Line2D.Float(x, y, pLine.getX(), pLine.getY()));
            }

            // If the player collided with something give the other players one points
            logic.givePoints(this.players, p);

        }
        g2.dispose();
        frame.getSidePanel().updatePoints();
        frame.getGamePanel().repaint();
        time += 1;


        // Check if all but one players are dead. If so, start new round, also check if a player has won
        if(logic.newRoundCheck(this.players)) {
            checkForWinner();
            startNewRound();
        }
    }

    // Checks for a winner, if winner found set all points to zero and announce winner
    private void checkForWinner() {
        boolean winnerFound = false;
        for(Player p : players) {
	    if(p.getPoints() >= winCondition) {
		frame.getSidePanel().winner(p);
                winnerFound = true;
	    }
	}
        // If winner found set all points to zero
        if(winnerFound) {
            for(Player p : players) {
                p.setPoints(0);
            }
        }
    }

    private void startNewRound() {
        pause = true;
        timer.stop();
        time = 0;
    }


    @Override public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);
        
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
        super.keyReleased(e);

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

}
