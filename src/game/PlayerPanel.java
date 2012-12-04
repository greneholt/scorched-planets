package game;

import game.Lander.ProjectileType;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JRadioButton;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/*
 *  Displays the angle, power, and allow the user to
 *  move to the next player, or if the last player, start
 *  the launch.
 */

public class PlayerPanel extends JPanel {

	private JSpinner angleSpinner;

	private JButton fireButton;
	private GameController gameController;

	private JPanel infoPanel;
	private JPanel listPanel;
	private List<JLabel> playerHealthLabels = null;

	private JLabel playerName, healthLabel, scoreLabel;
	private List<JLabel> playerScoreLabels = null;

	private JTable table;
	private TableModel tableModel;
	private List<String[]> playerInfoList = null;

	private JRadioButton missile;
	private JRadioButton teleporter;

	private JSpinner powerSpinner;

	public PlayerPanel() {

		setLayout(new GridLayout(0, 1));

		fireButton = new JButton("Fire");
		fireButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.nextPlayer();
			}
		});

		add(createPlayerListPanel());
		add(createPlayerInfoPanel());
		add(createGunControlsPanel());
		add(fireButton);
	}

	public void setAngle(float angle) {
		angleSpinner.setValue(angle * 180 / (float) Math.PI);
	}

	/*
	 * private class ButtonListener implements ActionListener { public void actionPerformed(ActionEvent e) { if (e.getSource() == nextTurn) {
	 * gameController.getCurrentPlayer().getLander().setAngle(getAngle()); gameController.getCurrentPlayer().getLander().setPower(getPower()); gameController.nextTurn(); } } }
	 */

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setInputEnabled(boolean enabled) {
		fireButton.setEnabled(enabled);
		powerSpinner.setEnabled(enabled);
		angleSpinner.setEnabled(enabled);
		missile.setEnabled(enabled);
		teleporter.setEnabled(enabled);
	}

	public void setPower(int power) {
		powerSpinner.setValue(power);
	}

	public void setProjectileType(ProjectileType pt) {
		if (pt == Lander.ProjectileType.MISSILE) {
			missile.setSelected(true);
		} else {
			teleporter.setSelected(true);
		}
	}

	public void updatePlayerInfo() {
		if (gameController != null) {
			String name = gameController.getCurrentPlayer().getName();
			playerName.setText("Current Player: " + name);
			int health = gameController.getCurrentPlayer().getLander().getHealth();
			if(health < 0)
				health = 0;
			healthLabel.setText("Health: " + health + "/" + Lander.FULL_HEALTH);
			int score = gameController.getCurrentPlayer().getScore();
			scoreLabel.setText("Score: " + score);

			setAngle(gameController.getCurrentPlayer().getLander().getGunAngle());
			setPower(gameController.getCurrentPlayer().getLander().getPower());
			setProjectileType(gameController.getCurrentPlayer().getLander().getProjectileType());

			updatePlayerListInfo();
		}
	}

	public void updatePlayerListInfo() {
		if (gameController != null) {
			if (playerInfoList != null) {
				int index = 0;
				for (Player p : gameController.getPlayers()) {
					int health = p.getLander().getHealth();
					if(health < 0)
						health = 0;
					((DefaultTableModel) tableModel).setValueAt(("" + health), index + 1, 1);
					int score = p.getScore();
					((DefaultTableModel) tableModel).setValueAt(("" + score), index + 1, 2);
					++index;
				}

			} else { // Load initial info
				playerInfoList = new LinkedList<String[]>();
				int index = 0;
				for (Player p : gameController.getPlayers()) {
					// get the name, health, and score
					int health = p.getLander().getHealth();
					int score = p.getScore();
					String[] pInfo = { p.getName(), ("" + health), ("" + score) };

					((DefaultTableModel) tableModel).addRow(pInfo);
					++index;
				}
			}
		}
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

		missile = new JRadioButton("Missile");
		teleporter = new JRadioButton("Teleporter");
		missile.setSelected(true);
		ButtonGroup projectileType = new ButtonGroup();
		projectileType.add(missile);
		projectileType.add(teleporter);

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (missile.isSelected()) {
					gameController.setProjectileType(ProjectileType.MISSILE);
				} else {
					gameController.setProjectileType(ProjectileType.TELEPORTER);
				}
			}
		};
		missile.addActionListener(listener);
		teleporter.addActionListener(listener);

		panel.add(new JLabel("Angle: "));
		panel.add(angleSpinner);
		panel.add(new JLabel("Power: "));
		panel.add(powerSpinner);
		panel.add(missile);
		panel.add(teleporter);

		return panel;
	}

	private JPanel createPlayerInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1));
		infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Current Player Info"));
		playerName = new JLabel("Current Player: None");
		healthLabel = new JLabel("Health: NA");
		scoreLabel = new JLabel("Score: NA");

		infoPanel.add(playerName);
		infoPanel.add(healthLabel);
		infoPanel.add(scoreLabel);

		return infoPanel;
	}

	private JPanel createPlayerListPanel() {
		String[] headers = { "Player:", "Health:", "Score:" };
		String[][] data = { headers };
		tableModel = new DefaultTableModel(data, headers);
		table = new JTable(tableModel) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
			
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row)) {
					for(Player p : gameController.getPlayers()) {
						int modelRow = convertRowIndexToModel(row);
						String type = (String)getModel().getValueAt(modelRow, 0);
						if (p.getName().equals(type)) {
							c.setBackground(p.getColor());
						}
					}
				}

				return c;
			}
		};

		listPanel = new JPanel();
		listPanel.setBorder(new TitledBorder(new EtchedBorder(), "Player Info"));
		listPanel.add(table);

		return listPanel;
	}
}
