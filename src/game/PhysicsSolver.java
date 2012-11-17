package game;

import java.util.ArrayList;

public class PhysicsSolver {
	
	private ArrayList<DynamicObject> dynamicObjects;
	private ArrayList<StaticObject> staticObjects;
	
	public PhysicsSolver() {}
	
	public PhysicsSolver(ArrayList<DynamicObject> dObject, 
			ArrayList<StaticObject> sObject) {
		this.dynamicObjects = dObject;
		this.staticObjects = sObject;
	}
	
	public void removeObject(Object object) {}
	
	public void simulateStep() {}

}
