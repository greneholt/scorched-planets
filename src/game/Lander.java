package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

public class Lander implements StaticObject, Renderable {

	private static final int DESTROYED_YIELD = 30;
	private static final int DESTROYED_BLAST_RADIUS = 30;
	private static final int GUN_LENGTH = 30;

	public void setPosition(Vector position) {
		this.position = position;
	}

	public static int FULL_HEALTH = 100;
	public static int MAX_POWER = 100;

	private int health;
	private Planet currentPlanet;
	private float angle;
	private int power;
	private Vector position;
	private float gunAngle;
	private Player player;
	private boolean highlight;
	private MapManager mapManager;

	public Lander(Player player, Planet planet, Vector position, float angle, MapManager mapManager) {
		this.player = player;
		this.currentPlanet = planet;
		this.position = position;
		this.health = FULL_HEALTH;
		this.angle = angle;
		this.power = MAX_POWER / 2;
		this.gunAngle = (float) Math.PI / 2;
		this.mapManager = mapManager;
	}

	public Player getPlayer() {
		return player;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void damage(int damage, Player causedBy) {
		health -= damage;
		player.damage(damage, causedBy);

		if (health <= 0) {
			mapManager.addRenderable(new Explosion(getPlayer(), position, DESTROYED_BLAST_RADIUS, DESTROYED_YIELD, mapManager));
			mapManager.removeLander(this);
		}
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public float getMass() {
		return 0;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(Graphics2D g) {
		Shape base = new Rectangle2D.Float(-WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT);

		Shape gun = new Rectangle2D.Float(0, -1.5f, GUN_LENGTH, 3);
		AffineTransform xf = new AffineTransform();
		xf.rotate(gunAngle);
		gun = xf.createTransformedShape(gun);

		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		// we draw the lander in in the positive Y direction, so we need to rotate it -PI/2 (towards the positive X axis) for the angles to work out
		g.rotate(angle - Math.PI / 2);

		if (highlight) {
			g.setColor(Color.WHITE);

			Shape arc = new Arc2D.Float(-GUN_LENGTH, -GUN_LENGTH, GUN_LENGTH * 2, GUN_LENGTH * 2, 180, 180, Arc2D.OPEN);
			g.setStroke(new BasicStroke(3));
			g.draw(arc);
		}
		
		g.setColor(player.getColor());
		g.fill(base);
		g.fill(gun);

		Shape health = new Rectangle2D.Float(-WIDTH / 2, -15, WIDTH, 5);
		g.setColor(Color.GREEN);
		g.fill(health);

		Shape damage = new Rectangle2D.Float(-WIDTH / 2, -15, WIDTH * (FULL_HEALTH - this.health) / FULL_HEALTH, 5);
		g.setColor(Color.RED);
		g.fill(damage);

		g.setTransform(savedXf);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		// do nothing
	}

	public void increasePower() {
		power++;
		if (power > MAX_POWER) {
			power = MAX_POWER;
		}
	}

	public void decreasePower() {
		power--;
		if (power < 0) {
			power = 0;
		}
	}

	public void rotateCounterClockwise() {
		gunAngle -= ANGLE_INCREMENT;
		if (gunAngle < 0) {
			gunAngle = 0;
		}
	}

	public void rotateClockwise() {
		gunAngle += ANGLE_INCREMENT;
		if (gunAngle > Math.PI) {
			gunAngle = (float) Math.PI;
		}
	}

	public void setGunAngle(float gunAngle) {
		this.gunAngle = gunAngle;
	}

	public float getGunAngle() {
		return gunAngle;
	}

	public void fireProjectile(MapManager map) {
		// when the gun is pointing straight up from the surface it is at an angle of PI/2, so we must subtract PI/2
		float traj = gunAngle + angle - (float) Math.PI / 2;

		Vector start = position.add(Vector.polar(30, traj));

		map.addProjectile(new Missile(this, start, Vector.polar(power * POWER_MULTIPLIER, traj), map));
	}

	@Override
	public Rectangle2D getBounds() {
		Shape shape = new Rectangle2D.Float(0, 0, WIDTH + GUN_LENGTH * 2, HEIGHT + GUN_LENGTH);
		AffineTransform xf = new AffineTransform();
		xf.translate(position.x, position.y);
		xf.rotate(angle);
		shape = xf.createTransformedShape(shape);
		return shape.getBounds2D();
	}

	private static final float WIDTH = 20;
	private static final float HEIGHT = 8;
	private static final float ANGLE_INCREMENT = (float) Math.PI / 80;
	private static final float POWER_MULTIPLIER = 200f;

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
