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
			getFiredBy().setCurrentPlanet((Planet) other);

			Vector newPosition = getPosition().subtract(other.getPosition());
			float angleOnPlanet = (float) (Math.atan2(newPosition.y, newPosition.x));

			getFiredBy().setAngleAndPosition(angleOnPlanet, (Planet) other);
		}
		getMapManager().removeProjectile(this);
	}

	@Override
	public void explode() {
		getMapManager().removeProjectile(this);
		getMapManager().addRenderable(new Explosion(getFiredBy().getPlayer(), getPosition(), BLAST_RADIUS, YIELD, getMapManager()));
	}

	@Override
	protected Color getColor() {
		return Color.GREEN;
	}
}
