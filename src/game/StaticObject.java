package game;

import java.awt.Point;

public abstract class StaticObject {
	private int mass;
	private Point position;
	
	public StaticObject() {}
	
	public StaticObject(int mass, Point position) {
		this.mass = mass;
		this.position = position;
	}
	
	public int getMass() {
		return mass;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	
	
}
