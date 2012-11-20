package game;

import java.util.LinkedList;
import java.util.List;

public class PhysicsSolver {

	private List<DynamicObject> dynamicObjects;
	private List<StaticObject> staticObjects;

	public PhysicsSolver() {
		dynamicObjects = new LinkedList<DynamicObject>();
		staticObjects = new LinkedList<StaticObject>();
	}
	
	public void addStaticObject(StaticObject object) {
		staticObjects.add(object);
	}
	
	public void addDynamicObject(DynamicObject object) {
		dynamicObjects.add(object);
	}

	public void removeStaticObject(StaticObject object) {
		staticObjects.remove(object);
	}

	public void removeDynamicObject(DynamicObject object) {
		dynamicObjects.remove(object);
	}

	public void simulateStep(float timeStep) {
		for (DynamicObject object : dynamicObjects) {
			// sum force on them
			Vector force = new Vector();
			
			object.simulateStep(force, timeStep);
		}
	}
}
