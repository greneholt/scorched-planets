package game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scene {
	private static final float MARGIN = 20;
	private List<Animatable> animations;
	private List<Renderable> renderables;

	public Scene() {
		renderables = new LinkedList<Renderable>();
		animations = new LinkedList<Animatable>();
	}

	public void addObject(Renderable object) {
		renderables.add(object);
		if (object instanceof Animatable) {
			animations.add((Animatable) object);
		}
	}

	public void animationTick() {
		List<Animatable> animationsTemp = new LinkedList<Animatable>(animations);
		for (Animatable object : animationsTemp) {
			object.animationTick();
		}
	}

	public Rectangle2D getBounds() {
		Iterator<Renderable> iter = renderables.iterator();

		if (!iter.hasNext()) {
			return null;
		}

		Rectangle2D bounds = iter.next().getBounds();

		while (iter.hasNext()) {
			bounds.add(iter.next().getBounds());
		}

		return bounds;
	}

	public boolean hasAnimations() {
		return animations.size() > 0;
	}

	public void removeObject(Renderable object) {
		renderables.remove(object);
		if (object instanceof Animatable) {
			animations.remove((Animatable) object);
		}
	}

	public void render(Graphics2D g, Dimension dimension) {
		Rectangle2D bounds = getBounds();

		AffineTransform savedXf = g.getTransform();

		double scaleX = dimension.getWidth() / (bounds.getWidth() + MARGIN * 2);
		double scaleY = dimension.getHeight() / (bounds.getHeight() + MARGIN * 2);

		if (scaleX < scaleY) {
			g.scale(scaleX, scaleX);
		} else {
			g.scale(scaleY, scaleY);
		}

		g.translate(MARGIN - bounds.getMinX(), MARGIN - bounds.getMinY());

		List<Renderable> renderablesTemp = new LinkedList<Renderable>(renderables);
		for (Renderable object : renderablesTemp) {
			object.render(g);
		}

		g.setTransform(savedXf);
	}
}
