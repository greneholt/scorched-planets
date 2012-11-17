package game;

import java.util.ArrayList;

public class Player {
	
	private Lander lander;
	private int score;
	private ArrayList<String> killsList;
	private String name;
	
	public Player() {}
	
	public Player(Lander lander, int score, String name) {
		this.lander = lander;
		this.score = score;
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

	public ArrayList<String> getKillsList() {
		return killsList;
	}
	
	public void addToKillsList(String kill) {
		killsList.add(kill);
	}

	public String getName() {
		return name;
	}

}
