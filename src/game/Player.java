package game;

import java.util.List;

public class Player {

	private Lander lander;
	private int score;
	private List<String> killsList;
	private String name;

	public Player(Lander lander, String name) {
		this.lander = lander;
		this.name = name;
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

	public void addScore(int score) {
		this.score += score;
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
	
	public void damage(int damage, Player causedBy) {
		int score = 0;

		// apply kill bonus
		if (lander.getHealth() <= 0) {
			if (this != causedBy) {
				score += KILL_BONUS;
			}
		}

		// give negative score for hitting yourself, or just add damage to score if you hit someone else
		if (this == causedBy) {
			score -= damage;
		} else {
			score += damage;
		}

		causedBy.addScore(score);
	}
	
	public static int KILL_BONUS = 100;
}
