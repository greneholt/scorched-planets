package game;

public abstract class Projectile extends MovableObject implements Renderable {
	protected MapManager mapManager;
	protected Lander firedBy;

	public Projectile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(position, velocity);
		this.firedBy = firedBy;
		this.mapManager = mapManager;
	}
}
