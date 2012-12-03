package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Teleporter extends Projectile {
	public static final float MASS = 1;
	private static final float BOUNDING_RADIUS = 1;

	private static final float HEIGHT = 7;

	private static final float WIDTH = 3;

	public Teleporter(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		// TODO Do we want to check for another lander closeby on the same planet?
		// By the game rules, it would be dumb to land next to someone and try to attack them, because you would lose points
		// Should we allow people to be stupid?

		// Connor: yes, suicides should be allowed

		// change lander location if it hits a planet
		if (other instanceof Planet) {
			firedBy.setCurrentPlanet((Planet) other);

			float deltaX = position.x - other.getPosition().x;
			float deltaY = position.y - other.getPosition().y;
			float angleOnPlanet = (float) Math.toRadians(Math.atan2(deltaY, deltaX));
			// Connor: you should use the methods built into the Vector class for doing this
			firedBy.setAngle(angleOnPlanet);

			// Connor: this code is the same as what's in mapgenerator, it should be refactored into the Lander class
			float x = (float) Math.cos(angleOnPlanet) * ((Planet) other).getRadius() + other.getPosition().x;
			float y = (float) Math.sin(angleOnPlanet) * ((Planet) other).getRadius() + other.getPosition().y;
			firedBy.setPosition(new Vector(x, y));
		}

	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub

	}

	@Override
	public float getBoundingRadius() {
		return BOUNDING_RADIUS;
	}

	@Override
	public Rectangle2D getBounds() {
		Shape shape = new Rectangle2D.Float(position.x, position.y, WIDTH, HEIGHT);
		AffineTransform xf = new AffineTransform();
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
		Shape shape = new Rectangle2D.Float(position.x, position.y, WIDTH, HEIGHT);
		AffineTransform xf = new AffineTransform();
		xf.rotate(getAngle());
		shape = xf.createTransformedShape(shape);
		g.setColor(Color.BLUE);
		g.fill(shape);
	}

	private double getAngle() {
		return Math.atan2(velocity.y, velocity.x);
	}
}
