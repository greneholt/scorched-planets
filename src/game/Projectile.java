package game;

public abstract class Projectile implements DynamicObject {
	public Projectile(Vector position, Vector velocity) {
		this.position = position;
		this.velocity = velocity;
	}

	private Vector position;
	private Vector velocity;

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public void simulateStep(Vector force, float timeStep) {
		Vector acceleration = new Vector(force.x/getMass(), force.y/getMass());
		
		velocity = velocity.add(acceleration.multiply(timeStep));
		position = position.add(velocity.multiply(timeStep));
	}
	
	public void collidedWith(PhysicsObject other) {
		if (other instanceof Planet) {
			//call hit
		}
	}

	public abstract void hit(Planet planet, Scene scene);
}
