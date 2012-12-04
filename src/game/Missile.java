package game;

import java.awt.Color;

public class Missile extends Rocket {
	private static final float BLAST_RADIUS = 50;
	private static final float YIELD = 40;

	public Missile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		explode();
	}

	@Override
	public void explode() {
		mapManager.removeProjectile(this);
		mapManager.addRenderable(new Explosion(firedBy.getPlayer(), position, BLAST_RADIUS, YIELD, mapManager));
	}

	@Override
	protected Color getColor() {
		return Color.RED;
	}
}