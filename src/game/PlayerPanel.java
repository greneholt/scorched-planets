package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 *  Displays the angle, power, and allow the user to
 *  move to the next player, or if the last player, start
 *  the launch.
 */

public class PlayerPanel extends JPanel {

	private JSpinner angleSpinner;
	private JSpinner powerSpinner;
	private GameController gameController;
	private JButton fireButton;

	private JPanel infoPanel;
	private JLabel playerName, healthLabel, scoreLabel;

	public PlayerPanel() {
		setLayout(new GridLayout(0, 1));

		fireButton = new JButton("Fire");
		fireButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.nextPlayer();
			}
		});

		add(createPlayerInfoPanel());
		add(createGunControlsPanel());
		add(fireButton);
	}

	private JPanel createGunControlsPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 2));

		angleSpinner = new JSpinner(new SpinnerNumberModel(90f, 0f, 180f, 1f));
		angleSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float angle = ((Number) angleSpinner.getValue()).floatValue();

				if (gameController != null) {
					gameController.changeCurrentGunAngle(angle / 180 * (float) Math.PI);
				}
			}
		});

		powerSpinner = new JSpinner(new SpinnerNumberModel(Lander.MAX_POWER, 0, Lander.MAX_POWER, 1));
		powerSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int power = (Integer) powerSpinner.getValue();

				if (gameController != null) {
					gameController.changeCurrentPower(power);
				}
			}
		});
		
		panel.add(new JLabel("Angle: "));
		panel.add(angleSpinner);
		panel.add(new JLabel("Power: "));
		panel.add(powerSpinner);
		
		return panel;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	/*
	 * private class ButtonListener implements ActionListener { public void actionPerformed(ActionEvent e) { if (e.getSource() == nextTurn) {
	 * gameController.getCurrentPlayer().getLander().setAngle(getAngle()); gameController.getCurrentPlayer().getLander().setPower(getPower()); gameController.nextTurn(); } } }
	 */

	private JPanel createPlayerInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1));
		playerName = new JLabel("Current Player: None");
		healthLabel = new JLabel("Health: NA");
		scoreLabel = new JLabel("Score: NA");

		add(playerName);
		add(healthLabel);
		add(scoreLabel);

		return infoPanel;
	}

	public void updatePlayerInfo() {
		if (gameController != null) {
			String name = gameController.getCurrentPlayer().getName();
			playerName.setText("Current Player: " + name);
			int health = gameController.getCurrentPlayer().getLander().getHealth();
			healthLabel.setText("Health: " + health + "/" + Lander.FULL_HEALTH);
			int score = gameController.getCurrentPlayer().getScore();
			scoreLabel.setText("Score: " + score);

			setAngle(gameController.getCurrentPlayer().getLander().getGunAngle());
			setPower(gameController.getCurrentPlayer().getLander().getPower());
		}
	}

	public void setPower(int power) {
		powerSpinner.setValue(power);
	}

	public void setAngle(float angle) {
		angleSpinner.setValue(angle * 180 / (float) Math.PI);
	}
}
