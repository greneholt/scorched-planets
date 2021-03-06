package game;

public class Vector {
	public static float distanceBetween(Vector a, Vector b) {
		return a.subtract(b).magnitude();
	}

	public static Vector polar(float r, float theta) {
		float x = r * (float) Math.cos(theta);
		float y = r * (float) Math.sin(theta);

		return new Vector(x, y);
	}

	public float x = 0;

	public float y = 0;

	public Vector() {
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}

	public float angle() {
		return (float) Math.atan2(y, x);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (other instanceof Vector) {
			Vector v = (Vector) other;

			return x == v.x && y == v.y;
		}

		return false;
	}

	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vector multiply(float other) {
		return new Vector(x * other, y * other);
	}

	public Vector subtract(Vector other) {
		return new Vector(x - other.x, y - other.y);
	}

	@Override
	public String toString() {
		return "x=" + x + " y=" + y;
	}
}
