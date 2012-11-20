package game;

import java.awt.Point;

public class Teleporter extends Projectile {
	public Teleporter(Point position, Point velocity) {
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
