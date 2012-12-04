package game;

import java.awt.Color;

public class Teleporter extends Rocket {
	private static final float BLAST_RADIUS = 20;
	private static final float YIELD = 10;
	private static final Color COLOR = new Color(0x42E0FF);

	public Teleporter(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		// change lander location if it hits a planet
		if (other instanceof Planet) {
			getMapManager().addRenderable(new TeleporterSource(getFiredBy().getPosition(), getMapManager()));
			getMapManager().addRenderable(new TeleporterDestination(getFiredBy(), (Planet) other, getPosition(), getMapManager()));
		}
		else {
			makeExplosion();
		}
		getMapManager().removeProjectile(this);
	}

	@Override
	public void explode() {
		getMapManager().removeProjectile(this);
		makeExplosion();
	}

	private void makeExplosion() {
		getMapManager().addRenderable(new Explosion(getFiredBy().getPlayer(), getPosition(), BLAST_RADIUS, YIELD, getMapManager()));
	}

	@Override
	protected Color getColor() {
		return COLOR;
	}
}
