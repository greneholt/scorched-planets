package game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PhysicsSolver {

	private List<DynamicObject> dynamicObjects;
	private List<StaticObject> staticObjects;

	public PhysicsSolver() {
		dynamicObjects = new LinkedList<DynamicObject>();
		staticObjects = new LinkedList<StaticObject>();
	}

	public void addObject(PhysicsObject object) {
		if (object instanceof DynamicObject) {
			dynamicObjects.add((DynamicObject) object);
		} else if (object instanceof StaticObject) {
			staticObjects.add((StaticObject) object);
		}
	}

	public void removeObject(PhysicsObject object) {
		if (object instanceof DynamicObject) {
			dynamicObjects.remove((DynamicObject) object);
		} else if (object instanceof StaticObject) {
			staticObjects.remove((StaticObject) object);
		}
	}

	public void simulateStep(float timeStep) {
		for (DynamicObject object : dynamicObjects) {
			// sum force on them
			Vector force = new Vector();

			object.simulateStep(force, timeStep);
		}

		for (DynamicObject a : dynamicObjects) {
			checkCollisions(a, dynamicObjects);
			checkCollisions(a, staticObjects);
		}
	}
	
	private void checkCollisions(DynamicObject object, List<? extends PhysicsObject> objects) {
		Set<Collission> collissions = new HashSet<Collission>();
		
		for (PhysicsObject b : objects) {
			if (object == b) {
				continue;
			}
			
			if (object.getPosition().subtract(b.getPosition()).magnitude() <= (object.getBoundingRadius()+b.getBoundingRadius())) {
				collissions.add(new Collission(object, b));
			}
		}
		
		for (Collission collission : collissions) {
			collission.a.collidedWith(collission.b);
			collission.b.collidedWith(collission.a);
		}
	}
	
	private class Collission {
		public PhysicsObject a, b;
		
		public Collission(PhysicsObject a, PhysicsObject b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			
			if (other instanceof Collission) {
				Collission o = (Collission) other;
				
				if ((a == o.a && b == o.b) ||(b == o.a && a == o.b)) {
					return true;
				}
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return a.hashCode() ^ b.hashCode();
		}
	}
}
