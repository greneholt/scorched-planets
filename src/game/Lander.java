package game;

import java.awt.Graphics2D;

public class Lander implements StaticObject, Renderable {

	private int health;
	private Planet currentPlanet;
	private int angle;
	private int power;
	private Vector position;

	public Lander() {
	}

	public Lander(int health, Planet planet, int angle, int power, Vector position) {
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
	public Vector getPosition() {
		return position;
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		// TODO Auto-generated method stub
	}

}
