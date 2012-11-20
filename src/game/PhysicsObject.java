package game;

public interface PhysicsObject {
	public Vector getPosition();

	public float getBoundingRadius();
	
	public void collidedWith(PhysicsObject other);
}
