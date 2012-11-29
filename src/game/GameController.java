package game;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private MapManager map;
	private List<Player> players;
	private SceneComponent sceneComponent;
	private int currentPlayerIndex;
	
	public GameController(int playerCount, SceneComponent sceneComponent) {
		this.sceneComponent = sceneComponent;
		
		players = new ArrayList<Player>(playerCount);
		
		for (int i = 0; i < playerCount; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		
		map = new MapManager(playerCount + 3, players);
		
		sceneComponent.setScene(map.getScene());
		
		currentPlayerIndex = 0;
	}

	public void nextTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
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
