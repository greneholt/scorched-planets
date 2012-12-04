package game;

public abstract class MovableObject implements DynamicObject {

	private Vector position;
	private Vector velocity;

	public MovableObject(Vector position, Vector velocity) {
		this.position = position;
		this.velocity = velocity;
	}

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
		Vector acceleration = new Vector(force.x / getMass(), force.y / getMass());

		velocity = velocity.add(acceleration.multiply(timeStep));
		position = position.add(velocity.multiply(timeStep));
	}

}