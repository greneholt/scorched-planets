package game;

import game.Lander.ProjectileType;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameController implements KeyListener {
	private static final int ANIMATION_INTERVAL = 20;
	private static final int BOREDOM_INTERVAL = 30000; // 30 seconds
	private static final int KEY_REPEAT_INTERVAL = 50;
	private static final float TIME_STEP = 0.0005f;
	private Timer boredomTimer;
	private Player currentPlayer;
	private boolean enableInput = true;
	private MapManager map;
	private Iterator<Player> playerIterator;
	private PlayerPanel playerPanel;

	private List<Player> players;
	private SceneComponent sceneComponent;
	private Timer simulationTimer;
	private Timer upTimer, downTimer, rightTimer, leftTimer;

	public GameController(int playerCount, SceneComponent sceneComponent, PlayerPanel playerPanel) {
		this.sceneComponent = sceneComponent;
		this.playerPanel = playerPanel;

		players = new ArrayList<Player>(playerCount);

		Random rand = new Random();
		for (int i = 0; i < playerCount; i++) {
			Color color = Color.getHSBColor((float) i / playerCount, 1, rand.nextFloat()*0.5f + 0.5f);
			players.add(new Player("Player " + (i + 1), color));
		}

		playerPanel.setGameController(this);

		makeTimers();

		newGame();
	}

	public void changeCurrentGunAngle(float angle) {
		getCurrentPlayer().getLander().setGunAngle(angle);
		repaint();
	}

	public void changeCurrentPower(int power) {
		getCurrentPlayer().getLander().setPower(power);
		repaint();
	}
	
	public void setProjectileType(ProjectileType type) {
		getCurrentPlayer().getLander().setProjectileType(type);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getEnableInput() {
		return enableInput;
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

	public void repaint() {
		sceneComponent.repaint();
	}

	public void runSimulation() {
		// create projectiles for each lander
		for (Lander lander : map.getLanders()) {
			lander.fireProjectile(map);
		}

		simulationTimer.start();
		boredomTimer.start();
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
				map.runStep(TIME_STEP);
				repaint();

				if (!map.nextStepNeeded()) {
					simulationTimer.stop();
					boredomTimer.stop();
					setInputEnabled(true);
					startTurn();
				}
			}
		});

		boredomTimer = new Timer(BOREDOM_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				map.explodeProjectiles();
			}
		});
	}

	private void newGame() {
		map = new MapManager(players.size() + 3, players);

		sceneComponent.setScene(map.getScene());
		sceneComponent.setKeyListener(this);

		startTurn();
	}

	private void setInputEnabled(boolean enabled) {
		playerPanel.setInputEnabled(enabled);
		enableInput = enabled;
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
				message = activePlayers.getFirst().getName() + " wins! New game?";
			} else {
				Player winner = players.get(0);
				for (Player p : players) {
					if(winner.getScore() < p.getScore())
						winner = p;
				}
				message = winner.getName() + " wins! New game?";
			}

			int n = JOptionPane.showConfirmDialog(sceneComponent, message, "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

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

	private void updateCurrentPlayer() {
		getCurrentPlayer().getLander().setHighlight(true);
		playerPanel.updatePlayerInfo();
		repaint();
	}
}
