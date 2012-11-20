package game;

import java.awt.Color;
import java.awt.Point;

public class Planet implements StaticObject {
	private int radius;
	private Color color;
	private float mass;
	private Point position;

	public Planet(float mass, Point position, int radius, Color color) {
		this.mass = mass;
		this.position = position;
		this.radius = radius;
		this.color = color;
	}

	public int getRadius() {
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
	public Point getPosition() {
		return position;
	}

}
