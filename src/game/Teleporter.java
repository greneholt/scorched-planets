package game;

import java.awt.Graphics2D;

public class Teleporter extends Projectile {
	public Teleporter(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public float getMass() {
		// TODO Auto-generated method stub
		return 0;
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
