package view;

import models.players.Player;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Used to hold and show the scores of all the players in the game.
 */
public class SidePanel extends JPanel
{
	private final static int WINNER_FONT_SIZE = 40;
	private final static int POINTS_FONT_SIZE = 70;
	private final static int POINTS_LABEL_WIDTH = 250;
	private final static int POINTS_LABEL_HEIGHT = 100;


    private JLabel[] points = null;
    private Player[] players = null;

	// Constructor
	public SidePanel() {
		super(new GridLayout(0, 1));

		setBackground(Color.BLACK);
	}

	// Adds the labels for each player so their score is shown
    public void addPlayers(Player[] players) {
	this.players = players;

	this.removeAll();

	this.points = new JLabel[players.length];

	int i = 0;
	for(Player p : players) {
	    JLabel playerLabel = new JLabel(String.valueOf(p.getPoints()), SwingConstants.CENTER);
	    playerLabel.setFont(new Font(playerLabel.getFont().getName(), playerLabel.getFont().getStyle(), POINTS_FONT_SIZE));
	    playerLabel.setBorder(new LineBorder(Color.DARK_GRAY));
	    playerLabel.setForeground(p.getPlayerColor());
	    playerLabel.setPreferredSize(new Dimension(POINTS_LABEL_WIDTH, POINTS_LABEL_HEIGHT));
	    this.add(playerLabel);
	    points[i] = playerLabel;
	    i++;

	}

    }

	// Update the points shown for each player
    public void updatePoints() {
	for(int i = 0; i < players.length; i++) {
	    points[i].setText(String.valueOf(players[i].getPoints()));
	}
    }

	// Display the winner
	public void winner(Player player) {
		for(int i = 0; i < players.length; i++) {
			if(Objects.equals(players[i].getPlayerColor(), player.getPlayerColor())) {
				points[i].setFont(new Font(points[i].getFont().getName(), points[i].getFont().getStyle(), WINNER_FONT_SIZE));
				points[i].setText("WINNER");
			}
		}
	}

}
