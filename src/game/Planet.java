package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Planet implements StaticObject, Renderable {
	private Color color;
	private float mass;
	private Vector position;
	private float radius;

	public Planet(float mass, Vector position, float radius, Color color) {
		this.mass = mass;
		this.position = position;
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void collidedWith(PhysicsObject other) {

	}

	@Override
	public float getBoundingRadius() {
		return radius;
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(position.x - radius, position.y - radius, radius * 2, radius * 2);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public void render(Graphics2D g) {
		Ellipse2D circle = new Ellipse2D.Float(-radius, -radius, radius * 2, radius * 2);

		AffineTransform savedXf = g.getTransform();
		g.translate(position.x, position.y);

		g.setColor(color);
		g.fill(circle);
		g.setColor(Color.WHITE);

		String label = String.format("%.0f", mass);

		// borrowed from here: http://explodingpixels.wordpress.com/2009/01/29/drawing-text-about-its-visual-center/
		Font font = g.getFont();
		FontRenderContext renderContext = g.getFontRenderContext();
		GlyphVector glyphVector = font.createGlyphVector(renderContext, label);
		Rectangle visualBounds = glyphVector.getVisualBounds().getBounds();

		float textX = -visualBounds.width / 2;
		float textY = visualBounds.height / 2;

		g.drawGlyphVector(glyphVector, textX, textY);

		g.setTransform(savedXf);
	}
}
