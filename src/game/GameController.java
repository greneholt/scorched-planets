package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameController {
	private MapManager map;
	private List<Player> players;
	private Timer timer;
	private SceneComponent sceneComponent;
	
	public GameController(int playerCount, SceneComponent sceneComponent) {
		this.sceneComponent = sceneComponent;
		
		players = new ArrayList<Player>(playerCount);
		
		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		
		map = new MapManager(playerCount + 3, players);
		
		sceneComponent.setScene(map.getScene());
	}

	public MapManager getMap() {
		return map;
	}

	public void nextTurn() {
	}

	public void runTurn() {
	}
}
