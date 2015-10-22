package window;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SidePanel extends JPanel
{
    private JLabel pointsLabel;
    private JLabel pointsLabel2;

    public SidePanel() {
	super(new GridLayout(0, 1));

	setBackground(Color.BLACK);


	/**
	 * TESTS HOW TO SHOW THE POINTS
	 */
	this.pointsLabel = new JLabel("5", SwingConstants.CENTER);
	// new font size is 40
	pointsLabel.setFont(new Font(pointsLabel.getFont().getName(), pointsLabel.getFont().getStyle(), 40));
	pointsLabel.setBorder(new LineBorder(Color.DARK_GRAY));
	pointsLabel.setForeground(Color.WHITE);
	// change label size
	pointsLabel.setPreferredSize(new Dimension(150, 150));
	this.add(pointsLabel);

	this.pointsLabel2 = new JLabel("6", SwingConstants.CENTER);
	// new font size is 40
	pointsLabel2.setFont(new Font(pointsLabel2.getFont().getName(), pointsLabel2.getFont().getStyle(), 40));
	pointsLabel2.setBorder(new LineBorder(Color.DARK_GRAY));
	pointsLabel2.setForeground(Color.WHITE);
	// change label size
	pointsLabel2.setPreferredSize(new Dimension(150, 150));
	this.add(pointsLabel2);





    }

}
