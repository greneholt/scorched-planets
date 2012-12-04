package game;

import java.awt.Color;

public class Teleporter extends Rocket {
	private static final float BLAST_RADIUS = 20;
	private static final float YIELD = 10;

	public Teleporter(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public void collidedWith(PhysicsObject other) {

		// change lander location if it hits a planet
		if (other instanceof Planet) {
			firedBy.setCurrentPlanet((Planet) other);

			Vector newPosition = position.subtract(other.getPosition());
			float angleOnPlanet = (float) (Math.atan2(newPosition.y, newPosition.x));

			firedBy.setAngleAndPosition(angleOnPlanet, (Planet) other);
		}
		mapManager.removeProjectile(this);

	}

	@Override
	public void explode() {
		mapManager.removeProjectile(this);
		mapManager.addRenderable(new Explosion(firedBy.getPlayer(), position, BLAST_RADIUS, YIELD, mapManager));

	}

	@Override
	protected Color getColor() {
		return Color.GREEN;
	}
}
