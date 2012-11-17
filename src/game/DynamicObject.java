package game;

import java.awt.Point;



public abstract class DynamicObject {
	private int mass;
	private Point position;
	private Point velocity;
	
	public DynamicObject() {}
	
	public DynamicObject(int mass, Point position, Point velocity) {
		this.mass = mass;
		this.position = position;
		this.velocity = velocity;
	}
	
	public void hit(Planet planet, Scene scene) {
		
	}

}
