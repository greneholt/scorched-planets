package game;

import java.awt.Point;

public class Missile extends Projectile {
	public Missile(Point position, Point velocity) {
		super(position, velocity);
	}

	@Override
	public void hit(Planet planet, Scene scene) {
		// TODO Auto-generated method stub
	}

	@Override
	public float getMass() {
		// TODO Auto-generated method stub
		return 0;
	}
}
