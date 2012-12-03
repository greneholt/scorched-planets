package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class SceneComponent extends JComponent implements MouseListener {
	private KeyListener keyListener;
	private Scene scene;

	public SceneComponent() {
		setFocusable(true);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		requestFocusInWindow();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		if (scene != null) {
			scene.render(g2d, getSize());
		}
	}

	public void setKeyListener(KeyListener keyListener) {
		removeKeyListener(this.keyListener);
		addKeyListener(keyListener);
		this.keyListener = keyListener;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
