package game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface Renderable {
	public Rectangle2D getBounds();

	public void render(Graphics2D g);
}
