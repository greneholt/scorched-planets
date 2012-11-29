package game;

import java.awt.Graphics2D;

public class Missile extends Projectile {
	public Missile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
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
		mapManager.makeExplosion(position, BLAST_RADIUS, YIELD);
		mapManager.removePhysicsObject(this);
		mapManager.removeRenderable(this);
	}

	private static final float BLAST_RADIUS = 20;
	private static final float YIELD = 40;
}
