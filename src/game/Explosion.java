package game;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/*
 * Explosions are created at the point of impact and added to the scene. They render the animation of the explosion, and cause damage.
 */
public class Explosion implements Renderable {
	private int animationStep;
	private Vector position;

	public Explosion(Vector position) {
		super();
		this.position = position;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(position.x, position.y, WIDTH, HEIGHT);
	}

	public static final float WIDTH = 10;
	public static final float HEIGHT = 10;

	@Override
	public void animationTick() {
		// TODO Auto-generated method stub
		
	}
}
