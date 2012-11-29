package game;

import java.awt.Graphics2D;

public class Lander implements StaticObject, Renderable {

	public void setPosition(Vector position) {
		this.position = position;
	}

	public static int FULL_HEALTH = 100;

	private int health;
	private Planet currentPlanet;
	private double angle;
	private int power;
	private Vector position;

	public Lander() {
	}

	public Lander(Planet planet, Vector position) {
		this.currentPlanet = planet;
		this.position = position;
		this.health = FULL_HEALTH;
		this.angle = 100;
		this.power = 50;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
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
