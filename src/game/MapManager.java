package game;

import java.util.List;

public class MapManager {
	private Scene scene;
	private PhysicsSolver physicsSolver;
	private List<Planet> planets;
	private List<Lander> landers;

	public void setupMap(int planetCount, int landerCount) {
		MapGenerator gen = new MapGenerator();
		planets = gen.generatePlanets(planetCount);
		landers = gen.generateLanders(planets, landerCount);
		
		// generate planets
		// generate landers
		// assign landers to players
	}

	public void makeExplosion(Vector position, float blastRadius, float yield) {

	}

	public void removeRenderable(Renderable object) {
		scene.removeObject(object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		physicsSolver.removeObject(object);
	}
}
