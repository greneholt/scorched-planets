package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public abstract class Rocket extends Projectile {
	private static final int BOUNDING_RADIUS = 4;
	private static final float DRAG_COEFFICIENT = 5f;
	private static final float MASS = 1;
	static final float HEIGHT = 30;
	static final float WIDTH = 14;

	public Rocket(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public float getBoundingRadius() {
		return BOUNDING_RADIUS;
	}

	@Override
	public Rectangle2D getBounds() {
		Shape shape = new Rectangle2D.Float(-HEIGHT / 2, -WIDTH / 2, HEIGHT, WIDTH);
		AffineTransform xf = new AffineTransform();
		xf.translate(position.x, position.y);
		xf.rotate(getAngle());
		shape = xf.createTransformedShape(shape);
		return shape.getBounds2D();
	}

	@Override
	public float getMass() {
		return MASS;
	}

	@Override
	public void render(Graphics2D g) {
		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		g.rotate(getAngle());

		g.setColor(getColor());

		Path2D.Float path = new Path2D.Float();
		path.moveTo(HEIGHT / 2 - 8, -WIDTH / 2 + 5);
		path.lineTo(HEIGHT / 2, 0);
		path.lineTo(HEIGHT / 2 - 8, WIDTH / 2 - 5);
		path.lineTo(-HEIGHT / 2 + 6, WIDTH / 2 - 5);
		path.lineTo(-HEIGHT / 2, WIDTH / 2);
		path.lineTo(-HEIGHT / 2 - 4, 0);
		path.lineTo(-HEIGHT / 2, -WIDTH / 2);
		path.lineTo(-HEIGHT / 2 + 6, -WIDTH / 2 + 5);
		path.closePath();
		g.fill(path);

		g.setTransform(savedXf);
	}

	@Override
	public void simulateStep(Vector force, float timeStep) {
		Vector dragForce = velocity.multiply(-DRAG_COEFFICIENT);
		super.simulateStep(force.add(dragForce), timeStep);
	}

	private float getAngle() {
		return velocity.angle();
	}

	protected abstract Color getColor();
}