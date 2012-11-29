package game;

import java.awt.Graphics2D;

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

}
