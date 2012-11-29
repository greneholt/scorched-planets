package game;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private MapManager map;
	private List<Player> players;
	private SceneComponent sceneComponent;
	private MainWindow mainWindow;
	private int currentPlayerIndex;
	
	public GameController(int playerCount, SceneComponent sceneComponent,
			MainWindow mw) {
		this.sceneComponent = sceneComponent;
		mainWindow = mw;
		
		players = new ArrayList<Player>(playerCount);
		
		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		
		map = new MapManager(playerCount + 3, players);
		
		sceneComponent.setScene(map.getScene());
		
		currentPlayerIndex = 0;
		
		sceneComponent.repaint();
	}

	public void nextTurn() {
		double angle = mainWindow.getPlayerPanel().getAngle();
		int power = mainWindow.getPlayerPanel().getPower();
		players.get(currentPlayerIndex).getLander().setAngle(angle);
		players.get(currentPlayerIndex).getLander().setPower(power);
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		mainWindow.getPlayerPanel().setAngleAndPower();
	}

	public void runTurn() {
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
}
