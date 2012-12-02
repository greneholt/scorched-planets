package game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface Renderable {
	public void render(Graphics2D g);
	
	public Rectangle2D getBounds();
}
