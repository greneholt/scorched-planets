package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

/*
 * Explosions are created at the point of impact and added to the scene. They render the animation of the explosion, and cause damage.
 */
public class Explosion implements Animatable {
	private int animationStep = 0;
	private Vector position;
	private MapManager mapManager;
	private float blastRadius;
	private float yield;
	private Player causedBy;

	public Explosion(Player causedBy, Vector position, float blastRadius, float yield, MapManager mapManager) {
		this.causedBy = causedBy;
		this.position = position;
		this.blastRadius = blastRadius;
		this.yield = yield;
		this.mapManager = mapManager;
	}

	@Override
	public void render(Graphics2D g) {
		float radius = getCurrentRadius();

		Shape shape = new Ellipse2D.Float(-radius, -radius, radius * 2, radius * 2);

		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		g.setColor(new Color(0xFC410D));
		g.fill(shape);

		g.setTransform(savedXf);
	}

	@Override
	public Rectangle2D getBounds() {
		float radius = getCurrentRadius();

		return new Rectangle2D.Float(position.x - radius, position.y - radius, radius * 2, radius * 2);
	}

	private float getCurrentRadius() {
		if (animationStep <= EXPAND_STEPS) {
			return blastRadius * animationStep / EXPAND_STEPS;
		}
		else {
			return blastRadius * (EXPAND_STEPS + CONTRACT_STEPS - animationStep) / CONTRACT_STEPS;
		}
	}

	@Override
	public void animationTick() {
		animationStep++;
		
		if (animationStep == EXPAND_STEPS) {
			List<Lander> landersTemp = new LinkedList<Lander>(mapManager.getLanders());
			for (Lander lander : landersTemp) {
				float distance = Vector.distanceBetween(lander.getPosition(), position);
				
				if (distance < blastRadius) {
					int damage = (int) ((1 - (distance / blastRadius) * (distance / blastRadius)) * yield);

					lander.damage(damage, causedBy);
				}
			}
		}
		
		if (animationStep == EXPAND_STEPS + CONTRACT_STEPS) {
			mapManager.removeRenderable(this);
		}
	}
	
	private static final int EXPAND_STEPS = 10;
	private static final int CONTRACT_STEPS = 30;
}
