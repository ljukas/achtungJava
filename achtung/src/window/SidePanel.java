package window;

import models.Player;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SidePanel extends JPanel
{
    private JLabel[] points = null;
    private Player[] players = null;

    public SidePanel() {
	super(new GridLayout(0, 1));

	setBackground(Color.BLACK);
    }

    public void addPlayers(Player[] players) {
	this.players = players;

	this.removeAll();

	this.points = new JLabel[players.length];

	int i = 0;
	for(Player p : players) {
	    JLabel playerLabel = new JLabel(String.valueOf(p.getPoints()), SwingConstants.CENTER);
	    playerLabel.setFont(new Font(playerLabel.getFont().getName(), playerLabel.getFont().getStyle(), 40));
	    playerLabel.setBorder(new LineBorder(Color.DARK_GRAY));
	    playerLabel.setForeground(p.getPlayerColor());
	    playerLabel.setPreferredSize(new Dimension(250, 100));
	    this.add(playerLabel);
	    points[i] = playerLabel;
	    i++;

	}

    }

    public void updatePoints() {
	for(int i = 0; i < players.length; i++) {
	    points[i].setText(String.valueOf(players[i].getPoints()));
	}
    }

    public void winner(Player player) {
	for(int i = 0; i < players.length; i++) {
	    if(Objects.equals(players[i].getPlayerColor(), player.getPlayerColor())) {
		points[i].setText("WINNER");
	    }
	}
    }

}
