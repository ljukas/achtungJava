package engine;

import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import models.players.Player;
import view.MainFrame;

import javax.swing.*;

/**
 * Holds the entire game. Reads the key presses and runs the game.
 */
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
    private BufferedImage powerUpImage = null;
    private MainFrame frame;
    private Player[] players;
    private Round round;
    private Timer timer = null;
    private boolean pause = false;
    private int winCondition;
    private int time;

    // Constructor
    public Game(Player[] players, MainFrame frame) {
        this.frame = frame;
        this.players = players;

        frame.getGamePanel().addKeyListener(this);
    }


    // Starts a game from scratch
    public void start() {

        lineImage = new BufferedImage(Game.GAME_WIDTH, Game.GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        powerUpImage = new BufferedImage(Game.GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

        round = new Round(lineImage, powerUpImage, players);
        frame.getGamePanel().setLineImage(lineImage);
        frame.getGamePanel().setPowerUpImage(powerUpImage);
        frame.getGamePanel().addPlayers(players);
        frame.getSidePanel().addPlayers(players);
        this.frame.pack();

        // remove all powerups from players so they dont persist through rounds.
        for(Player p : players) {
            p.getLine().removeAllPowerups();
        }

        // Place the players on the board
        round.placePlayers(players);

        // Repaint here so when a new round start the old game isn't shown in the 2 sec starting period
        frame.getGamePanel().repaint();

        // The rules of the game state that to win you have to get points equal to 10 times number of players minus 10
        winCondition = this.players.length * 10 - 10;

        time = 0;
        timer = new Timer(1000 / FPS, this);    // Fires of the actionPerformed-event on each timer tick
        timer.setInitialDelay(INITIAL_DELAY);            // Start game after 2 sec delay
        timer.start();                          // Start game
    }

    // If a player is alive, move it, check if it dies when moved etc. Using game round from Round class.
    @Override public void actionPerformed(final ActionEvent e) {


        round.update(time);

        frame.getSidePanel().updatePoints();
        frame.getGamePanel().repaint();

        time += 1;


        // Check if all but one players are dead. If so, start new round, also check if a player has won
        if(round.newRoundCheck(this.players)) {
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

    // Stops the round
    public void stopGame() {
        pause = false;
        timer.stop();
    }

    // Starts a new round
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
                round.startLeftTurn(p);
            } else if(p.getRightKey() == c) {
                round.startRightTurn(p);
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
                round.stopLeftTurn(p);
            } else if(p.getRightKey() == c) {
                round.stopRightTurn(p);
            }
        }
    }

}
