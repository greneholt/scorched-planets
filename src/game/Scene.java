package game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scene {
	private List<Renderable> objects;

	public Scene() {
		objects = new LinkedList<Renderable>();
	}

	public void render(Graphics2D g, Dimension dimension) {
		Rectangle2D bounds = getBounds();
		
		AffineTransform xf = new AffineTransform();
		xf.translate(-bounds.getMinX(), -bounds.getMinY());
		xf.scale(dimension.getWidth()/bounds.getWidth(), dimension.getHeight()/bounds.getHeight());
		
		AffineTransform savedXf = g.getTransform();
		
		g.transform(xf);
		
		for (Renderable object : objects) {
			object.render(g);
		}
		
		g.setTransform(savedXf);
	}
	
	public Rectangle2D getBounds() {
		Iterator<Renderable> iter = objects.iterator();
		
		if (!iter.hasNext()) {
			return null;
		}
		
		Rectangle2D bounds = iter.next().getBounds();
		
		while (iter.hasNext()) {
			bounds.add(iter.next().getBounds());
		}
		
		return bounds;
	}

	public void addObject(Renderable object) {
		objects.add(object);
	}

	public void removeObject(Renderable object) {
		objects.remove(object);
	}
}
