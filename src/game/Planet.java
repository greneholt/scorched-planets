package game;

import java.awt.Color;

public class Planet implements StaticObject {
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

}
