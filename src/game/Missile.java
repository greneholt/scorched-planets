package game;

import java.awt.Point;

public class Missile extends DynamicObject {
	
	public Missile() {
		super();
	}
	
	public Missile(int mass, Point position, Point velocity) {
		super(mass, position, velocity);
	}

}
