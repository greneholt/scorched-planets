package game;

public interface PhysicsObject {
	public void collidedWith(PhysicsObject other);

	public float getBoundingRadius();

	public float getMass();

	public Vector getPosition();
}
