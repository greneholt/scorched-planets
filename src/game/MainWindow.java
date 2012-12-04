package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		} catch (Exception e) {
			// the program isn't running on a Mac, too bad for them
		}

		MainWindow window = new MainWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(900, 700));
		window.setVisible(true);
	}

	private GameController game;
	private String[] helpMessage;
	private PlayerPanel playerPanel;

	private SceneComponent sceneComponent;

	public MainWindow() {
		setLayout(new BorderLayout());

		sceneComponent = new SceneComponent();
		add(sceneComponent, BorderLayout.CENTER);
		playerPanel = new PlayerPanel();
		add(playerPanel, BorderLayout.EAST);

		this.newGame();

		setHelpMessage();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}

	public PlayerPanel getPlayerPanel() {
		return playerPanel;
	}

	public void newGame() {
		int playerCount = 0;

		while (true) {
			JOptionPane pane = new JOptionPane("Number of players:", JOptionPane.PLAIN_MESSAGE);
			pane.setInitialSelectionValue("4");
			pane.setWantsInput(true);
			JDialog dialog = pane.createDialog(this, "Players");
			dialog.setVisible(true);

			try {
				playerCount = Integer.parseInt((String) pane.getInputValue());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (playerCount < 2) {
				JOptionPane.showMessageDialog(this, "There must be at least two players", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			break;
		}

		game = new GameController(playerCount, sceneComponent, playerPanel);
		playerPanel.updatePlayerListInfo();
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return item;
	}

	private JMenuItem createFileHelpItem() {
		JMenuItem item = new JMenuItem("Help");

		class MenuItemListener implements ActionListener {
			int count;
			String[] helpMessage;
			MainWindow mw;
			String[] options;

			public MenuItemListener(MainWindow mw) {
				this.mw = mw;
				helpMessage = mw.getHelpMessage();
				options = new String[2];
				options[0] = "Next";
				options[1] = "Previous";

				count = 0;
			}

			public void actionPerformed(ActionEvent e) {
				int change;
				do {
					change = JOptionPane.showOptionDialog(mw, helpMessage[count], "Game Directions", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (change == JOptionPane.YES_OPTION)
						count = (count + 1) % helpMessage.length;
					if (change == JOptionPane.NO_OPTION)
						if (count == 0)
							count = helpMessage.length - 1;
						else
							count = (count - 1) % helpMessage.length;
				} while (change != JOptionPane.CLOSED_OPTION);
			}
		}
		item.addActionListener(new MenuItemListener(this));
		return item;
	}

	private JMenu createFileMenu() {

		JMenu menu = new JMenu("File");
		menu.add(createFileHelpItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private String[] getHelpMessage() {
		return helpMessage;
	}

	private void setHelpMessage() {
		helpMessage = new String[13];
		helpMessage[0] = "Every player has their own Lander, which is the base" + " of operations.";
		helpMessage[1] = "The different Landers are color coded according to" + " each player, as seen on the right of the screen.";
		helpMessage[2] = "The information for the current player is directly" + " below that.";
		helpMessage[3] = "The goal of the game is to destroy the other players" + " Landers while protecting your own.";
		helpMessage[4] = "Each player takes a turn setting the desired angle," + " power, and which type of projectile to use.";
		helpMessage[5] = "A missile will hurt other Landers, but a teleporter" + " will transport your Lander to the planet that it hits.";
		helpMessage[6] = "To end a players turn, press the fire button." + " This will move to the next players turn until everyone" + " has input their own values.";
		helpMessage[7] = "When the last player has hit the fire button, all" + " the projectiles will fire at once!";
		helpMessage[8] = "Missiles can destroy each other in midair, so use" + " them defensively as well as offensively!";
		helpMessage[9] = "The game continues until there is only one Lander" + " left, or if all the Landers have been destroyed on the same turn";
		helpMessage[10] = "The player that has a Lander left at the end of the" + " game automatically wins!";
		helpMessage[11] = "If there are no Landers left, there is no winner.";
		helpMessage[12] = "Points are scored based on damage done to another player's" + " lander, and extra points are rewarded for destroying a player's Lander or winning a round.";
	}
}
