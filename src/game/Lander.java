package game;

import java.awt.Point;

public class Lander extends StaticObject {
	
	private int health;
	private Planet planetReference;
	private int angle;
	private int power;
	
	public Lander() {}
	
	public Lander(int health, Planet planet, int angle,
			int power, int mass, Point point) {
		super(mass, point);
		this.health = health;
		this.planetReference = planet;
		this.angle = angle;
		this.power = power;
	}

	public int getHealth() {
		return health;
	}

	public Planet getPlanetReference() {
		return planetReference;
	}

	public int getAngle() {
		return angle;
	}

	public int getPower() {
		return power;
	}

}
