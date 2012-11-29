package game;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private GameController game;

	public MainWindow() {
		
	}
	
	public void newGame() {
		int player_count = 0;
		
		while (true) {
			JOptionPane pane = new JOptionPane("Number of players:", JOptionPane.PLAIN_MESSAGE);
			pane.setInitialSelectionValue("4");
			pane.setWantsInput(true);
			JDialog dialog = pane.createDialog(this, "Players");
			dialog.setVisible(true);
			
			try {
				player_count = Integer.parseInt((String) pane.getInputValue());
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			break;
		}
		
		game = new GameController(player_count);
	}

	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		} catch (Exception e) {
			// the program isn't running on a Mac, too bad for them
		}

		MainWindow window = new MainWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(700, 700));
		window.setVisible(true);
		
		window.newGame();
	}
}
