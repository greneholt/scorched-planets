package game;

public abstract class Projectile extends MovableObject implements Renderable {
	private Lander firedBy;
	private MapManager mapManager;

	public Projectile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(position, velocity);
		this.firedBy = firedBy;
		this.mapManager = mapManager;
	}

	public abstract void explode();

	public Lander getFiredBy() {
		return firedBy;
	}

	public MapManager getMapManager() {
		return mapManager;
	}
}
