package game;

public interface PhysicsObject {
	public float getMass();
	
	public Vector getPosition();

	public float getBoundingRadius();
	
	public void collidedWith(PhysicsObject other);
}
