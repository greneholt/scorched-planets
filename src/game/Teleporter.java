package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Teleporter extends Projectile {
	public static final float MASS = 1;
	private static final float BOUNDING_RADIUS = 4;

	private static final float HEIGHT = 30;

	private static final float WIDTH = 14;

	public Teleporter(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public void collidedWith(PhysicsObject other) {

		// change lander location if it hits a planet
		if (other instanceof Planet) {
			firedBy.setCurrentPlanet((Planet) other);

			Vector newPosition = position.subtract(other.getPosition());
			float angleOnPlanet = (float) (Math.atan2(newPosition.y, newPosition.x));

			firedBy.setAngleAndPosition(angleOnPlanet, (Planet) other);
		}
		mapManager.removeProjectile(this);

	}

	@Override
	public void explode() {
		mapManager.removeProjectile(this);
		mapManager.addRenderable(new Explosion(firedBy.getPlayer(), position, Missile.BLAST_RADIUS, YIELD, mapManager));

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
		/*Shape shape = new Rectangle2D.Float(position.x, position.y, WIDTH, HEIGHT);
		AffineTransform xf = new AffineTransform();
		xf.rotate(getAngle());
		shape = xf.createTransformedShape(shape);
		g.setColor(Color.BLUE);
		g.fill(shape);
		*/
		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		g.rotate(getAngle());

		g.setColor(Color.GREEN);

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

	private double getAngle() {
		return Math.atan2(velocity.y, velocity.x);
	}
}
