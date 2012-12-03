package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

public class GameController implements KeyListener {
	private MapManager map;
	private List<Player> players;
	private SceneComponent sceneComponent;
	private PlayerPanel playerPanel;
	private int currentPlayerIndex;
	private Timer upTimer, downTimer, rightTimer, leftTimer;
	private Timer simulationTimer;
	private boolean enableInput = true;

	private static final int KEY_REPEAT_INTERVAL = 50;
	private static final int ANIMATION_INTERVAL = 20;
	private static final float TIME_STEP = 0.0005f;

	public GameController(int playerCount, SceneComponent sceneComponent, PlayerPanel playerPanel) {
		this.sceneComponent = sceneComponent;
		this.playerPanel = playerPanel;

		players = new ArrayList<Player>(playerCount);

		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}

		map = new MapManager(playerCount + 3, players);

		sceneComponent.setScene(map.getScene());
		sceneComponent.setKeyListener(this);

		playerPanel.setGameController(this);

		currentPlayerIndex = 0;

		updateCurrentPlayer();

		sceneComponent.repaint();

		makeTimers();
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
				repaint();
				boolean cont = map.runStep(TIME_STEP);
				if (!cont) {
					simulationTimer.stop();
					enableInput = true;
					updateCurrentPlayer();
				}
			}
		});
	}

	public void nextPlayer() {
		getCurrentPlayer().getLander().setHighlight(false);

		do {
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		} while (getCurrentPlayer().getLander().getHealth() <= 0);

		if (currentPlayerIndex == 0) {
			enableInput = false;
			runSimulation();
		} else {
			getCurrentPlayer().getLander().setHighlight(true);
			playerPanel.updatePlayerInfo();
		}
	}

	private void updateCurrentPlayer() {
		getCurrentPlayer().getLander().setHighlight(true);
		playerPanel.updatePlayerInfo();
	}

	public void runSimulation() {
		// create projectiles for each lander
		for (Lander lander : map.getLanders()) {
			lander.fireProjectile(map);
		}

		simulationTimer.start();
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
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
