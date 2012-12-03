package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

public class Lander implements StaticObject, Renderable {

	public static int FULL_HEALTH = 100;
	public static int MAX_POWER = 100;
	private static final float ANGLE_INCREMENT = (float) Math.PI / 80;

	private static final int DESTROYED_BLAST_RADIUS = 30;

	private static final int DESTROYED_YIELD = 30;
	private static final int GUN_LENGTH = 40;

	private static final float HEIGHT = 8;
	private static final float POWER_MULTIPLIER = 200f;
	private static final float WIDTH = 20;
	
	private float angle;
	private Planet currentPlanet;
	private float gunAngle;
	private int health;
	private boolean highlight;
	private MapManager mapManager;

	private Player player;

	private Vector position;

	private int power;
	
	private ProjectileType projectileType;
	
	public enum ProjectileType {MISSILE, TELEPORTER }
	
	

	public Lander(Player player, Planet planet, Vector position, float angle, MapManager mapManager) {
		this.player = player;
		this.currentPlanet = planet;
		this.position = position;
		this.health = FULL_HEALTH;
		this.angle = angle;
		this.power = MAX_POWER / 2;
		this.gunAngle = (float) Math.PI / 2;
		this.mapManager = mapManager;
		projectileType = ProjectileType.MISSILE;
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		//Do nothing
	}

	public void damage(int damage, Player causedBy) {
		health -= damage;
		player.damage(damage, causedBy);

		if (health <= 0) {
			mapManager.addRenderable(new Explosion(getPlayer(), position, DESTROYED_BLAST_RADIUS, DESTROYED_YIELD, mapManager));
			mapManager.removeLander(this);
		}
	}

	public void decreasePower() {
		power--;
		if (power < 0) {
			power = 0;
		}
	}

	public void fireProjectile(MapManager map) {
		// when the gun is pointing straight up from the surface it is at an angle of PI/2, so we must subtract PI/2
		float traj = gunAngle + angle - (float) Math.PI / 2;

		Vector start = position.add(Vector.polar(30, traj));

		if(projectileType == ProjectileType.MISSILE) {
			map.addProjectile(new Missile(this, start, 
					Vector.polar(power * POWER_MULTIPLIER, traj), map));
		}
		else if(projectileType == ProjectileType.TELEPORTER) {
			map.addProjectile(new Teleporter(this, start, 
					Vector.polar(power * POWER_MULTIPLIER, traj), map));
		}
	}

	public double getAngle() {
		return angle;
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return 0;
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

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public float getGunAngle() {
		return gunAngle;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public float getMass() {
		return 0;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	public int getPower() {
		return power;
	}

	public void increasePower() {
		power++;
		if (power > MAX_POWER) {
			power = MAX_POWER;
		}
	}

	@Override
	public void render(Graphics2D g) {
		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		// we draw the lander in in the positive Y direction, so we need to rotate it -PI/2 (towards the positive X axis) for the angles to work out
		g.rotate(angle - Math.PI / 2);

		if (highlight) {
			g.setColor(Color.GRAY);

			Shape arc = new Arc2D.Float(-GUN_LENGTH, -GUN_LENGTH, GUN_LENGTH * 2, GUN_LENGTH * 2, 180, 180, Arc2D.OPEN);
			g.setStroke(new BasicStroke(3));
			g.draw(arc);
		}

		Shape base = new Rectangle2D.Float(-WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT);

		Shape gun = new Rectangle2D.Float(0, -1.5f, GUN_LENGTH * power / MAX_POWER, 3);
		AffineTransform xf = new AffineTransform();
		xf.rotate(gunAngle);
		gun = xf.createTransformedShape(gun);

		if (highlight) {
			Shape aim = new Rectangle2D.Float(0, -1.5f, GUN_LENGTH, 3);
			aim = xf.createTransformedShape(aim);
			g.setColor(Color.GRAY);
			g.fill(aim);
		}

		g.setColor(player.getColor());
		g.fill(gun);
		g.fill(base);

		Shape healthBar = new Rectangle2D.Float(-WIDTH / 2, -15, WIDTH, 5);
		g.setColor(Color.GREEN);
		g.fill(healthBar);

		Shape damageBar = new Rectangle2D.Float(-WIDTH / 2, -15, WIDTH * (FULL_HEALTH - this.health) / FULL_HEALTH, 5);
		g.setColor(Color.RED);
		g.fill(damageBar);

		g.setTransform(savedXf);
	}

	public void rotateClockwise() {
		gunAngle += ANGLE_INCREMENT;
		if (gunAngle > Math.PI) {
			gunAngle = (float) Math.PI;
		}
	}

	public void rotateCounterClockwise() {
		gunAngle -= ANGLE_INCREMENT;
		if (gunAngle < 0) {
			gunAngle = 0;
		}
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setCurrentPlanet(Planet planet) {
		currentPlanet = planet;
	}

	public void setGunAngle(float gunAngle) {
		this.gunAngle = gunAngle;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public ProjectileType getProjectileType() {
		return projectileType;
	}
	
	public void setProjectileType(ProjectileType pt) {
		projectileType = pt;
	}
}
