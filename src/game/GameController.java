package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameController implements KeyListener {
	private MapManager map;
	private List<Player> players;
	private SceneComponent sceneComponent;
	private PlayerPanel playerPanel;
	private Player currentPlayer;
	private Iterator<Player> playerIterator;
	private Timer upTimer, downTimer, rightTimer, leftTimer;
	private Timer simulationTimer;
	private boolean enableInput = true;
	private float maxVelocityLastStep;

	private static final int KEY_REPEAT_INTERVAL = 50;
	private static final int ANIMATION_INTERVAL = 20;
	private static final float TIME_STEP = 0.0005f;
	protected static final float MIN_VELOCITY = 5E3f;

	public GameController(int playerCount, SceneComponent sceneComponent, PlayerPanel playerPanel) {
		this.sceneComponent = sceneComponent;
		this.playerPanel = playerPanel;

		players = new ArrayList<Player>(playerCount);

		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}

		playerPanel.setGameController(this);

		makeTimers();

		newGame();
	}

	private void newGame() {
		map = new MapManager(players.size() + 3, players);

		sceneComponent.setScene(map.getScene());
		sceneComponent.setKeyListener(this);

		startTurn();
	}

	private void makeTimers() {
		upTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getCurrentPlayer().getLander().increasePower();
				playerPanel.setPower(getCurrentPlayer().getLander().getPower());
				repaint();
			}
		});

		downTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().decreasePower();
				playerPanel.setPower(getCurrentPlayer().getLander().getPower());
				repaint();
			}
		});

		leftTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().rotateCounterClockwise();
				playerPanel.setAngle(getCurrentPlayer().getLander().getGunAngle());
				repaint();
			}
		});

		rightTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().rotateClockwise();
				playerPanel.setAngle(getCurrentPlayer().getLander().getGunAngle());
				repaint();
			}
		});

		simulationTimer = new Timer(ANIMATION_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (maxVelocityLastStep < MIN_VELOCITY) {
					maxVelocityLastStep = map.runStep(MIN_VELOCITY / maxVelocityLastStep * TIME_STEP);
				} else {
					maxVelocityLastStep = map.runStep(TIME_STEP);
				}
				
				repaint();
				if (!map.nextStepNeeded()) {
					simulationTimer.stop();
					setInputEnabled(true);
					startTurn();
				}
			}
		});
	}

	private void startTurn() {
		LinkedList<Player> activePlayers = new LinkedList<Player>();

		for (Player player : players) {
			if (player.getLander().getHealth() > 0) {
				activePlayers.add(player);
			}
		}

		if (activePlayers.size() <= 1) {
			String message;

			if (activePlayers.size() == 1) {
				message = activePlayers.getFirst().getName() + " wins! Continue?";
			} else {
				message = "No winner. Continue?";
			}

			int n = JOptionPane.showConfirmDialog(sceneComponent, message, "Game over", JOptionPane.YES_NO_OPTION);

			// if they say yes
			if (n == 0) {
				newGame();
				return;
			} else {
				System.exit(0);
			}
		}

		playerIterator = activePlayers.iterator();
		currentPlayer = playerIterator.next();
		updateCurrentPlayer();
	}

	public void nextPlayer() {
		currentPlayer.getLander().setHighlight(false);

		if (playerIterator.hasNext()) {
			currentPlayer = playerIterator.next();
			updateCurrentPlayer();
		} else {
			setInputEnabled(false);
			runSimulation();
		}
	}

	private void setInputEnabled(boolean enabled) {
		playerPanel.setEnabled(enabled);
		enableInput = enabled;
	}

	private void updateCurrentPlayer() {
		getCurrentPlayer().getLander().setHighlight(true);
		playerPanel.updatePlayerInfo();
		repaint();
	}

	public void runSimulation() {
		// create projectiles for each lander
		for (Lander lander : map.getLanders()) {
			lander.fireProjectile(map);
		}

		maxVelocityLastStep = Float.MAX_VALUE;
		simulationTimer.start();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (enableInput) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upTimer.start();
				break;
			case KeyEvent.VK_DOWN:
				downTimer.start();
				break;
			case KeyEvent.VK_RIGHT:
				rightTimer.start();
				break;
			case KeyEvent.VK_LEFT:
				leftTimer.start();
				break;
			case KeyEvent.VK_SPACE:
				nextPlayer();
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (enableInput) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upTimer.stop();
				break;
			case KeyEvent.VK_DOWN:
				downTimer.stop();
				break;
			case KeyEvent.VK_RIGHT:
				rightTimer.stop();
				break;
			case KeyEvent.VK_LEFT:
				leftTimer.stop();
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	public void repaint() {
		sceneComponent.repaint();
	}

	public void changeCurrentGunAngle(float angle) {
		if (enableInput) {
			getCurrentPlayer().getLander().setGunAngle(angle);
			repaint();
		}
	}

	public void changeCurrentPower(int power) {
		if (enableInput) {
			getCurrentPlayer().getLander().setPower(power);
			repaint();
		}
	}

	public boolean getEnableInput() {
		return enableInput;
	}
}
