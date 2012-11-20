package game;

public abstract class Projectile extends MovableObject {
	public Projectile(Vector position, Vector velocity) {
		super(position, velocity);
	}

	public void collidedWith(PhysicsObject other) {
		if (other instanceof Planet) {
			//call hit
		}
	}

	public abstract void hit(Planet planet, Scene scene);
}
