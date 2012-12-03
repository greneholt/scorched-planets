package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
	private GameController game;
	private SceneComponent sceneComponent;
	private PlayerPanel playerPanel;
	private String [] helpMessage;

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
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			break;
		}
		
		game = new GameController(playerCount, sceneComponent, playerPanel);
	}
	
	private JMenu createFileMenu() {
		
		JMenu menu = new JMenu("File"); 
		menu.add(createFileExitItem());
		menu.add(createFileHelpItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createFileHelpItem() {
		JMenuItem item = new JMenuItem("Help");
		
		class MenuItemListener implements ActionListener {
			MainWindow mw;
			String [] helpMessage;
			String[] options;
			int count;
			public MenuItemListener(MainWindow mw) {
				this.mw = mw;
				helpMessage = mw.getHelpMessage();
				options = new String[3];
				options[0] = "Close";
	            options[1] = "Next";
	            options[2] = "Previous";
	            
	            count = 0;
			}
			public void actionPerformed(ActionEvent e) {
				int change;
				do {
					change = JOptionPane.showOptionDialog(mw,
							helpMessage[count],
							"Game Directions",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[2]);;
					if(change == JOptionPane.YES_OPTION+1)
						count = (count+1)%helpMessage.length;
					if(change == JOptionPane.NO_OPTION+1)
						if(count == 0)
							count = helpMessage.length-1;
						else 
							count = (count-1)%helpMessage.length;
				} while(change >= JOptionPane.CLOSED_OPTION+1);
			}
		}
		item.addActionListener(new MenuItemListener(this));
		return item;
	}
	
	public PlayerPanel getPlayerPanel() {
		return playerPanel;
	}
	
	private String [] getHelpMessage() {
		return helpMessage;
	}
	
	private void setHelpMessage() {
		helpMessage = new String [9];
		helpMessage[0] = "Each Lander is controlled by"
			+ " a seperate player.";
		helpMessage[1] = "The current players Lander is" 
			+" highlighted white.";
		helpMessage[2] = "All of the current players info"
			+" is on the right of the screen.";
		helpMessage[3] = "To change the angle, use the box"
			+" on the right labeled angle, by pressing up"
			+" or down to change the angle, or typing in a "
			+" new angle";
		helpMessage[4] = "To change the power, use the box"
			+" on the right labeled power, by pressing up"
			+" or down to change the power, or typing in a "
			+" new power";
		helpMessage[5] = "Use integers (regular numbers, no"
			+" decimals) for the angle and power!";
		helpMessage[6] = "After you have adjusted the angle"
			+" and power to what you would like, to end your"
			+" turn, press the fire button.";
		helpMessage[7] = "The rockets will"
			+" not fire until ever player has set their angle"
			+" and power, and after the last player has pressed"
			+" the fire button, all the missiles will fire at"
			+" once.";
		helpMessage[8] = "To make the game easier, use the check"
			+"box on the right, and the players will be able to"
			+" see where the rocket will go";
			
		
	}

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
}
