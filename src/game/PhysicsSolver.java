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

	public void removeStaticObject(StaticObject object) {
		staticObjects.remove(object);
	}

	public void removeDynamicObject(DynamicObject object) {
		dynamicObjects.remove(object);
	}

	public void simulateStep() {
	}
}
