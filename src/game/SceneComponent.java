package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class SceneComponent extends JComponent {
	private Scene scene;
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (scene != null) {
			scene.render(g2d, getSize());
		}
	}
}
