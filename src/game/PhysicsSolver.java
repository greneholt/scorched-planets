package game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PhysicsSolver {

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

				if ((a == o.a && b == o.b) || (b == o.a && a == o.b)) {
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

	public static final float G = 2E7f;

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
		Set<Collission> collissions = new HashSet<Collission>();

		List<DynamicObject> dynamicObjectsTemp = new LinkedList<DynamicObject>(dynamicObjects);
		for (DynamicObject object : dynamicObjectsTemp) {
			Vector totalForce = sumForcesOn(object);

			object.simulateStep(totalForce, timeStep);
		}

		for (DynamicObject a : dynamicObjects) {
			checkCollisions(collissions, a, dynamicObjects);
			checkCollisions(collissions, a, staticObjects);
		}

		for (Collission collission : collissions) {
			collission.a.collidedWith(collission.b);
			collission.b.collidedWith(collission.a);
		}
	}

	private void checkCollisions(Set<Collission> collissions, DynamicObject dynamicObject, List<? extends PhysicsObject> objects) {
		for (PhysicsObject b : objects) {
			if (dynamicObject == b) {
				continue;
			}

			if (Vector.distanceBetween(dynamicObject.getPosition(), b.getPosition())
					<= (dynamicObject.getBoundingRadius() + b.getBoundingRadius())) {
				collissions.add(new Collission(dynamicObject, b));
			}
		}
	}

	// calculates the gravitational force of source on target
	private Vector gravitationalForce(PhysicsObject target, PhysicsObject source) {
		Vector direction = source.getPosition().subtract(target.getPosition());
		float distance = direction.magnitude();

		return direction.multiply(G * target.getMass() * source.getMass() / (distance * distance * distance));
	}

	private Vector sumForcesOn(DynamicObject object) {
		Vector totalForce = new Vector();

		for (DynamicObject b : dynamicObjects) {
			if (object == b) {
				continue;
			}

			totalForce = totalForce.add(gravitationalForce(object, b));
		}

		for (StaticObject b : staticObjects) {
			totalForce = totalForce.add(gravitationalForce(object, b));
		}

		return totalForce;
	}
}
