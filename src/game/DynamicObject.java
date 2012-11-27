package game;

public interface DynamicObject extends PhysicsObject {

	public Vector getVelocity();

	public void simulateStep(Vector force, float timeStep);
}
