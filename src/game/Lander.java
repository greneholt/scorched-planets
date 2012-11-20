package game;

import java.awt.Point;

public class Lander implements StaticObject {
	
	private int health;
	private Planet currentPlanet;
	private int angle;
	private int power;
	private Point position;
	
	public Lander() {}
	
	public Lander(int health, Planet planet, int angle,
			int power, Point position) {
		this.health = health;
		this.currentPlanet = planet;
		this.angle = angle;
		this.power = power;
	}

	public int getHealth() {
		return health;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public int getAngle() {
		return angle;
	}

	public int getPower() {
		return power;
	}

	@Override
	public float getMass() {
		return 0;
	}

	@Override
	public Point getPosition() {
		return position;
	}

}
