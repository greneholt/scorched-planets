package game;

public abstract class Projectile extends MovableObject implements Renderable {
	protected MapManager mapManager;
	protected Lander firedBy;

	public Projectile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(position, velocity);
		this.firedBy = firedBy;
		this.mapManager = mapManager;
	}
	
	public Lander getFiredBy() {
		return firedBy;
	}
	
	@Override
	public void simulateStep(Vector force, float timeStep) {
		Vector dragForce = velocity.multiply(-DRAG_COEFFICIENT);
		super.simulateStep(force.add(dragForce), timeStep);
	}
	
	private static final float DRAG_COEFFICIENT = 5f;
}
