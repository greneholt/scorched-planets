package game;

import java.util.List;

public class Player {

	private Lander lander;
	private int score;
	private List<String> killsList;
	private String name;

	public Player() {
	}
	
	public Player(Lander lander, String name) {
		this.lander = lander;
		this.name = name;
		this.lander.setPlayer(this);
	}

	public Player(String name) {
		this.name = name;
	}

	public Lander getLander() {
		return lander;
	}

	public void setLander(Lander lander) {
		this.lander = lander;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getKillsList() {
		return killsList;
	}

	public void addToKillsList(String kill) {
		killsList.add(kill);
	}

	public String getName() {
		return name;
	}

}
