package game;

import java.util.LinkedList;
import java.util.List;

public class GameController {
	
	private MapManager mapManager;
	
	private List<Player> players;
	private Player currentPlayer;

	public GameController(int player_count) {
		players = new LinkedList<Player>();
		for (int i=0; i<player_count; i++) {
			players.add(new Player("Player " + (i+1)));
		}
		mapManager = new MapManager(4, players);
		currentPlayer = players.get(0);
	}

	public void nextTurn() {
		currentPlayer = players.get(players.indexOf(currentPlayer)+1);
		
	}

	public void runTurn() {
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
}
