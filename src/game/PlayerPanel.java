package game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
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
	
	private JPanel listPanel;
	private List<JLabel> playerHealthLabels = null;
	private List<JLabel> playerScoreLabels = null;

	private JCheckBox easyPlayButton;
	public boolean easyPlay;

	public PlayerPanel() {
		setLayout(new GridLayout(0, 1));

		fireButton = new JButton("Fire");
		fireButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(gameController.getEnableInput()) {
					gameController.nextPlayer();
				}
			}
		});
		
		add(createPlayerListPanel());
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
		infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Current Player Info"));
		playerName = new JLabel("Current Player: None");
		healthLabel = new JLabel("Health: NA");
		scoreLabel = new JLabel("Score: NA");
		easyPlayButton = new JCheckBox("Easy Play");
		
		easyPlayButton.addActionListener(new CheckBoxListener());

		infoPanel.add(playerName);
		infoPanel.add(healthLabel);
		infoPanel.add(scoreLabel);
		infoPanel.add(easyPlayButton);

		return infoPanel;
	}
	
	private JPanel createPlayerListPanel() {
		listPanel = new JPanel();
		listPanel.setLayout(new FlowLayout());
		listPanel.setBorder(new TitledBorder(new EtchedBorder(), "Player Info"));
		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(3,0));
		JLabel playerName = new JLabel ("Player:");
		JLabel healthName = new JLabel ("Health ( /" + Lander.FULL_HEALTH + "):");
		JLabel scoreName = new JLabel ("Score:");
		labels.add(playerName);
		labels.add(healthName);
		labels.add(scoreName);
		listPanel.add(labels);
		
		return listPanel;
	}
	
	private class CheckBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(easyPlayButton.isSelected())
				easyPlay = true;
			else
				easyPlay = false;
		}
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
			
			updatePlayerListInfo();
		}
	}
	
	public void updatePlayerListInfo() {
		if (gameController != null) {
			if (playerHealthLabels != null && playerScoreLabels != null) {
				int index = 0;
				for(Player p : gameController.getPlayers()) {
					int health = p.getLander().getHealth();
					playerHealthLabels.get(index).setText("" + health);
					int score = p.getScore();
					playerScoreLabels.get(index).setText("" + score);
					++index;
				}
			} else {	// add the player information
				playerHealthLabels = new LinkedList<JLabel>();
				playerScoreLabels = new LinkedList<JLabel>();
				JPanel panelContent = new JPanel();
				panelContent.setLayout(new GridLayout(3,0));
				for(Player p : gameController.getPlayers()) {
					JLabel nameP = new JLabel(" " + p.getName() + " ");
					JLabel healthP = new JLabel("NA");
					playerHealthLabels.add(healthP);
					JLabel scoreP = new JLabel("NA");
					playerScoreLabels.add(scoreP);
					panelContent.add(nameP);
				}
				for(JLabel health : playerHealthLabels) {
					panelContent.add(health);
				}
				for(JLabel score : playerScoreLabels) {
					panelContent.add(score);
				}
				listPanel.add(panelContent);
			}
		}
	}

	public void setPower(int power) {
		powerSpinner.setValue(power);
	}

	public void setAngle(float angle) {
		angleSpinner.setValue(angle * 180 / (float) Math.PI);
	}
}
