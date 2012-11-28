package game;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

public class Scene {
	private List<Renderable> objects;
	
	public Scene() {
		objects = new LinkedList<Renderable>();
	}
	
	public void render(Graphics2D g) {
		for (Renderable object : objects) {
			object.render(g);
		}
	}
	
	public void addObject(Renderable object) {
		objects.add(object);
	}
	
	public void removeObject(Renderable object) {
		objects.remove(object);
	}
}
