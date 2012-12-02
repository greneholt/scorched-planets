package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Missile extends Projectile {
	public Missile(Lander firedBy, Vector position, Vector velocity, MapManager mapManager) {
		super(firedBy, position, velocity, mapManager);
	}

	@Override
	public float getMass() {
		return 20;
	}

	@Override
	public float getBoundingRadius() {
		return 4;
	}

	@Override
	public void render(Graphics2D g) {
		Shape shape = new Rectangle2D.Float(-HEIGHT/2, -WIDTH/2, HEIGHT, WIDTH);
		
		AffineTransform savedXf = g.getTransform();
		
		g.translate(position.x, position.y);
		g.rotate(getAngle());
		g.setColor(Color.YELLOW);
		g.fill(shape);
		
		g.setTransform(savedXf);
	}

	@Override
	public void collidedWith(PhysicsObject other) {
		mapManager.makeExplosion(this, BLAST_RADIUS, YIELD);
		mapManager.removePhysicsObject(this);
		mapManager.removeRenderable(this);
	}
	
	@Override
	public Rectangle2D getBounds() {
		Shape shape = new Rectangle2D.Float(0, 0, WIDTH, HEIGHT);
		AffineTransform xf = new AffineTransform();
		xf.translate(position.x, position.y);
		xf.rotate(getAngle());
		shape = xf.createTransformedShape(shape);
		return shape.getBounds2D();
	}
	
	private float getAngle() {
		return velocity.angle();
	}

	private static final float WIDTH = 5;
	private static final float HEIGHT = 20;

	private static final float BLAST_RADIUS = 20;
	private static final float YIELD = 40;
	
	@Override
	public void animationTick() {
		// TODO Auto-generated method stub
		
	}
}
