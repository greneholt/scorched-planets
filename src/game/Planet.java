package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Planet implements StaticObject, Renderable {
	private float radius;
	private Color color;
	private float mass;
	private Vector position;

	public Planet(float mass, Vector position, float radius, Color color) {
		this.mass = mass;
		this.position = position;
		this.radius = radius;
		this.color = color;
	}

	public float getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public float getBoundingRadius() {
		return radius;
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
