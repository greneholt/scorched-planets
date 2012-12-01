package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Lander implements StaticObject, Renderable {

	public void setPosition(Vector position) {
		this.position = position;
	}

	public static int FULL_HEALTH = 100;

	private int health;
	private Planet currentPlanet;
	private double angle;
	private int power;
	private Vector position;
	private float gunAngle;

	public Lander() {
	}

	public Lander(Planet planet, Vector position, float angle) {
		this.currentPlanet = planet;
		this.position = position;
		this.health = FULL_HEALTH;
		this.angle = angle;
		this.power = 50;
		this.gunAngle = (float) Math.PI / 2;
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

	public void setAngle(double angle) {
		this.angle = angle;
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
		Shape shape = new Rectangle2D.Float(-WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);

		Shape gun = new Rectangle2D.Float(0, -1.5f, 40, 3);
		AffineTransform xf = new AffineTransform();
		xf.rotate(gunAngle);
		gun = xf.createTransformedShape(gun);

		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		// we draw the lander in in the positive Y direction, so we need to rotate it clockwise 90 degrees for the angles to work out
		g.rotate(angle - Math.PI/2);

		g.setColor(Color.RED);
		g.fill(shape);
		g.setColor(Color.GREEN);
		g.fill(gun);

		g.setTransform(savedXf);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		// TODO Auto-generated method stub
	}

	@Override
	public Rectangle2D getBounds() {
		Shape shape = new Rectangle2D.Float(0, 0, WIDTH + 40*2, HEIGHT + 40);
		AffineTransform xf = new AffineTransform();
		xf.translate(position.x, position.y);
		xf.rotate(angle);
		shape = xf.createTransformedShape(shape);
		return shape.getBounds2D();
	}

	private static final float WIDTH = 10;
	private static final float HEIGHT = 5;
}
