package game;

import java.awt.Color;
import java.awt.Point;

public class Planet extends StaticObject{
	private int radius;
	private Color color;
	
	public Planet() {}
	
	public Planet(int radius, Color color, int mass, Point position) {
		super(mass, position);
		this.radius = radius;
		this.color = color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
