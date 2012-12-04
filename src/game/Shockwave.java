package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public abstract class Shockwave implements Animatable {
	private int animationStep = 0;
	private MapManager mapManager;
	private Vector position;

	public Shockwave(Vector position, MapManager mapManager) {
		this.position = position;
		this.mapManager = mapManager;
	}
	
	public MapManager getMapManager() {
		return mapManager;
	}

	public Vector getPosition() {
		return position;
	}

	@Override
	public void animationTick() {
		animationStep++;

		if (animationStep == getExpandSteps()) {
			reachedMaxSize();
		}

		if (animationStep == getExpandSteps() + getContractSteps()) {
			mapManager.removeRenderable(this);
		}
	}

	@Override
	public Rectangle2D getBounds() {
		float radius = getCurrentRadius();

		return new Rectangle2D.Float(position.x - radius, position.y - radius, radius * 2, radius * 2);
	}

	@Override
	public void render(Graphics2D g) {
		float radius = getCurrentRadius();

		Shape shape = new Ellipse2D.Float(-radius, -radius, radius * 2, radius * 2);

		AffineTransform savedXf = g.getTransform();

		g.translate(position.x, position.y);
		g.setColor(getColor());
		g.fill(shape);

		g.setTransform(savedXf);
	}

	private float getCurrentRadius() {
		if (animationStep <= getExpandSteps()) {
			return getBlastRadius() * animationStep / getExpandSteps();
		} else {
			return getBlastRadius() * (getExpandSteps() + getContractSteps() - animationStep) / getContractSteps();
		}
	}

	protected abstract float getBlastRadius();

	protected abstract Color getColor();

	protected abstract int getContractSteps();

	protected abstract int getExpandSteps();

	protected abstract void reachedMaxSize();
}