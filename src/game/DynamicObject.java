package game;

public interface DynamicObject extends PhysicsObject {
	public float getMass();

	public Vector getVelocity();

	public void simulateStep(Vector force, float timeStep);
	
	public void collidedWith(PhysicsObject other);
}
