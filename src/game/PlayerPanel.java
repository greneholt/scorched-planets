package game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/*
 *  Displays the angle, power, and allow the user to
 *  move to the next player, or if the last player, start
 *  the launch.
 */
 

public class PlayerPanel extends JPanel {
	
	private JSpinner angle;
	private JSlider power;
	private GameController gameController;
	private JButton nextTurn;
	
	private JPanel infoPanel;
	private JLabel playerName, healthLabel, scoreLabel;
	
	public PlayerPanel(GameController gc) {
		setLayout(new GridLayout(1,0));
		
		gameController = gc;
		angle = new JSpinner(new SpinnerNumberModel(180, 0, 360, 0.25));
		power = new JSlider(0, 100, 50);
		nextTurn = new JButton("Next Turn");
		nextTurn.addActionListener(new ButtonListener());
		
		add(createPlayerInfoPanel());
		add(angle);
		add(power);
		add(nextTurn);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == nextTurn) {
				gameController.getCurrentPlayer().getLander().setAngle(getAngle());
				gameController.getCurrentPlayer().getLander().setPower(getPower());
				gameController.nextTurn();
			}
		}
	}
	
	public JPanel createPlayerInfoPanel() {
		infoPanel = new JPanel();
		setLayout(new GridLayout(0,1));
		String name = gameController.getCurrentPlayer().getName();
		playerName = new JLabel("Current Player: " + name);
		int health = gameController.getCurrentPlayer().getLander().getHealth();
		healthLabel = new JLabel("Health: " + health + "/" + Lander.FULL_HEALTH);
		int score = gameController.getCurrentPlayer().getScore();
		scoreLabel = new JLabel("Score: " + score);
		
		add(playerName);
		add(healthLabel);
		add(scoreLabel);
		
		return infoPanel;
	}
	
	public void setAngleAndPower() {
		double playerAngle = gameController.getCurrentPlayer().getLander().getAngle();
		int playerPower = gameController.getCurrentPlayer().getLander().getPower();
		angle.setValue(playerAngle);
		power.setValue(playerPower);
	}
	
	public void updatePlayerInfoPanel() {
		String name = gameController.getCurrentPlayer().getName();
		playerName.setName("Current Player: " + name);
		int health = gameController.getCurrentPlayer().getLander().getHealth();
		healthLabel.setName("Health: " + health + "/" + Lander.FULL_HEALTH);
		int score = gameController.getCurrentPlayer().getScore();
		scoreLabel.setName("Score: " + score);	
	}

	public double getAngle() {
		return (Double) angle.getValue();
	}

	public int getPower() {
		return power.getValue();
	}
	
	

}
