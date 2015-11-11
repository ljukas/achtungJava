package view;

import engine.Game;
import models.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;


/**
 * Modified JFrame that holds the gameboard and the scoreboard. Also contains all the menus needed to start/stop game and add players.
 */
public class MainFrame extends JFrame
{
	private GamePanel gamePanel = null;
	private SidePanel sidePanel = null;
	private static Game game = null; 		// Game name is appropriate length
	private static Player[] players = new Player[0];

	// Constructor
	public MainFrame() {
		super("Achtung av Lukas");
	}

	// Initializes the game, sets the gameboard and scoreboard and creates the menu so one can add players/start game
	public void initGame() {
		setResizable(false);

		createMenuBar();

		JPanel mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new BorderLayout());		// BorderLayout used since there are only two different panels.

		gamePanel = new GamePanel();
		sidePanel = new SidePanel();

		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(sidePanel, BorderLayout.LINE_END);

		// this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	// Creates the menubar on the top of the screen
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Exit Achtung");
		exitMenuItem.addActionListener(e -> System.exit(0));

		JMenu game = new JMenu("Game");
		game.setMnemonic(KeyEvent.VK_G);

		JMenuItem addPlayerItem = new JMenuItem("Add Player");
		addPlayerItem.setMnemonic(KeyEvent.VK_A);
		addPlayerItem.setToolTipText("Add another player to the game");
		addPlayerItem.addActionListener(e -> addPlayer());

		JMenuItem startGameItem = new JMenuItem("Start Game");
		startGameItem.setMnemonic(KeyEvent.VK_S);
		startGameItem.setToolTipText("Start the game");
		startGameItem.addActionListener(e -> runGame());

		JMenuItem stopGameItem = new JMenuItem("Stop Game");
		stopGameItem.setMnemonic(KeyEvent.VK_P);
		stopGameItem.setToolTipText("Exit the current game");
		stopGameItem.addActionListener(e -> stopGame());

		JMenuItem resetPlayersItem = new JMenuItem("Reset players");
		resetPlayersItem.setMnemonic(KeyEvent.VK_R);
		resetPlayersItem.setToolTipText("Removes all current players");
		resetPlayersItem.addActionListener(e -> resetPlayers());

		file.add(exitMenuItem);
		game.add(addPlayerItem);
		game.add(startGameItem);
		game.add(stopGameItem);
		game.add(resetPlayersItem);

		menuBar.add(file);
		menuBar.add(game);

		setJMenuBar(menuBar);
	}

	// Starts a game with the players created
	private void runGame() {
		if(players.length == 0) {
			return;
		}

		// Assignment of static - G�rs f�r att det bara ska finnas ett game ig�ng samtidigt.
		game = new Game(players, this);

		game.start();

	}

	// Stops a game totally, cant be started again without adding new players.
	private void stopGame() {
		game.stopGame();
		resetPlayers();
	}

	// Add a player to the game
	private void addPlayer() {
		String[] comboBoxColors = new String[] {"Red", "Cyan", "Green", "Magenta", "Orange", "Yellow"};
		Color finalColor;

		JPanel newPlayerPanel = new JPanel();
		newPlayerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));

		JComboBox<String> playerColors = new JComboBox<>(comboBoxColors);
		JTextField leftTurn = new JTextField(1);
		JTextField rightTurn = new JTextField(1);

		newPlayerPanel.add(playerColors);
		newPlayerPanel.add(leftTurn);
		newPlayerPanel.add(rightTurn);
		playerColors.setMaximumRowCount(6);

		int value = JOptionPane.showConfirmDialog(this, newPlayerPanel, "Create Player", JOptionPane.OK_CANCEL_OPTION);
		if(value == JOptionPane.OK_OPTION) {
			switch((String) playerColors.getSelectedItem()) {
				case "Red":
					finalColor = Color.RED;
					break;
				case "Cyan":
					finalColor = Color.CYAN;
					break;
				case "Green":
					finalColor = Color.GREEN;
					break;
				case "Magenta":
					finalColor = Color.MAGENTA;
					break;
				case "Orange":
					finalColor = Color.ORANGE;
					break;
				default:
					finalColor = Color.YELLOW;
			}
		} else {
			return;
		}

		// Assignment to static - Ska bara finnas en array med spelare
		players = addPlayerToArray(players, new Player(finalColor, leftTurn.getText().charAt(0), rightTurn.getText().charAt(0)));

	}

	// Cant dynamically add players to array so its copied and extended with one spot for the new player when a new player is added
	private Player[] addPlayerToArray(Player[] org, Player added) {
		Player[] result = Arrays.copyOf(org, org.length+1);
		result[org.length] = added;
		return result;
	}

	// Resets the players int the game if you want to, like if a game is stopped or if an error was made in the adding of players
	private void resetPlayers() {
		//Assignment to static - Ska bara finnas en array med spelare
		players = new Player[0];
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public SidePanel getSidePanel() {
		return sidePanel;
	}
}
