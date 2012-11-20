package game;

public class Vector {
	public float x;
	public float y;
	
	public Vector() {
		
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}
	
	public Vector multiply(float other) {
		return new Vector(x*other, y*other);
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
}
