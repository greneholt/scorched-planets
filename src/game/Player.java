package game;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class Player {

	public static int KILL_BONUS = 100;
	private Color color;
	private List<String> killsList;
	private Lander lander;
	private String name;

	private int score;

	public Player(String name) {
		this.name = name;

		Random rand = new Random();
		color = Color.getHSBColor(rand.nextFloat(), 1, 1);
	}
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void addToKillsList(String kill) {
		killsList.add(kill);
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

	public Color getColor() {
		return color;
	}

	public List<String> getKillsList() {
		return killsList;
	}

	public Lander getLander() {
		return lander;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setLander(Lander lander) {
		this.lander = lander;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
