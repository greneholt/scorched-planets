package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class GameController implements KeyListener {
	private MapManager map;
	private List<Player> players;
	private SceneComponent sceneComponent;
	private MainWindow mainWindow;
	private int currentPlayerIndex;
	private Timer upTimer, downTimer, rightTimer, leftTimer;

	private static final int KEY_REPEAT_INTERVAL = 50;

	public GameController(int playerCount, SceneComponent sceneComponent, MainWindow mw) {
		this.sceneComponent = sceneComponent;
		mainWindow = mw;

		players = new ArrayList<Player>(playerCount);

		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}

		map = new MapManager(playerCount + 3, players);

		sceneComponent.setScene(map.getScene());
		sceneComponent.setKeyListener(this);

		currentPlayerIndex = 0;

		redraw();

		upTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getCurrentPlayer().getLander().increasePower();
				redraw();
			}
		});

		downTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().decreasePower();
				redraw();
			}
		});

		leftTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().rotateCounterClockwise();
				redraw();
			}
		});

		rightTimer = new Timer(KEY_REPEAT_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().getLander().rotateClockwise();
				redraw();
			}
		});
	}

	private void redraw() {
		sceneComponent.repaint();
	}

	/*
	 * This should update the current players values for angle and power, and then switch to the next player (unless all the players have had their turn, in which case it goes to run turn).
	 */
	public void nextTurn() {
		double angle = mainWindow.getPlayerPanel().getAngle();
		int power = mainWindow.getPlayerPanel().getPower();
		players.get(currentPlayerIndex).getLander().setAngle(angle);
		players.get(currentPlayerIndex).getLander().setPower(power);
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		if (currentPlayerIndex == 0) {
			runTurn();
		}
		mainWindow.getPlayerPanel().setAngleAndPower();
	}

	/*
	 * This should run the simulation. The simulation should quit out under certain conditions, which are the following: 1: All of the rockets have exploded 2: All of the rockets are very far away 3:
	 * One or all of the rockets are stuck in a loop 4: Some combination thereof
	 */
	public void runTurn() {
		List<Projectile> movers;
		movers = map.getProjectiles();
		boolean notOutOfPlay;
		do {
			notOutOfPlay = false;
			map.getPhysicsSolver().simulateStep((float) 0.1);
			Rectangle2D bounds = map.getScene().getBounds();
			for (Projectile p : movers) {
				if (p.getPosition().x <= bounds.getMaxX() + 100 || p.getPosition().x >= bounds.getMinX() - 100) {
					if (p.getPosition().y <= bounds.getMaxY() + 100 || p.getPosition().y >= bounds.getMinY() - 100) {

					}
				}
			}
		} while (notOutOfPlay);
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public void keyPressed(KeyEvent e) {
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
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

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
}
