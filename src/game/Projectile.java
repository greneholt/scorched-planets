package game;

import java.awt.Point;

public abstract class Projectile implements DynamicObject {
	public Projectile(Point position, Point velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	private Point position;
	private Point velocity;

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public Point getVelocity() {
		return velocity;
	}

	public abstract void hit(Planet planet, Scene scene);
}
