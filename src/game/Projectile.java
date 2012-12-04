package game;

public abstract class Projectile extends MovableObject implements Renderable {
	private static final float DRAG_COEFFICIENT = 5f;
	
	protected static final float BLAST_RADIUS = 50;
	protected static final float YIELD = 40;
	private static final float BOUNDING_RADIUS = 4;
	
	protected Lander firedBy;

	protected MapManager mapManager;

	public Projectile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(position, velocity);
		this.firedBy = firedBy;
		this.mapManager = mapManager;
	}

	public abstract void explode();

	public Lander getFiredBy() {
		return firedBy;
	}
	
	public float getBoundingRadius() {
		return BOUNDING_RADIUS;
	}

	@Override
	public void simulateStep(Vector force, float timeStep) {
		Vector dragForce = velocity.multiply(-DRAG_COEFFICIENT);
		super.simulateStep(force.add(dragForce), timeStep);
	}
	
	protected float getAngle() {
		return velocity.angle();
	}
}
