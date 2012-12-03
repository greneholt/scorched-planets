package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MapManager {
	private Scene scene = new Scene();
	private PhysicsSolver physicsSolver = new PhysicsSolver();
	private List<Planet> planets = new LinkedList<Planet>();
	private List<Lander> landers = new LinkedList<Lander>();
	private List<Projectile> projectiles = new LinkedList<Projectile>();

	public Scene getScene() {
		return scene;
	}

	public PhysicsSolver getPhysicsSolver() {
		return physicsSolver;
	}

	public List<Planet> getPlanets() {
		return planets;
	}

	public List<Lander> getLanders() {
		return landers;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public MapManager(int planetCount, List<Player> players) {
		MapGenerator gen = new MapGenerator();
		List<Planet> planets = gen.generatePlanets(planetCount);
		List<Lander> landers = gen.generateLanders(planets, players, this);

		for (Planet planet : planets) {
			addPlanet(planet);
		}

		for (Lander lander : landers) {
			addLander(lander);
		}
	}

	// returns true if the simulation needs to continue
	public float runStep(float timeStep) {
		scene.animationTick();
		return physicsSolver.simulateStep(timeStep);
	}
	
	public boolean nextStepNeeded() {
		return projectiles.size() > 0 || scene.hasAnimations();
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
		addRenderable(projectile);
		addPhysicsObject(projectile);
	}
	
	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
		removeRenderable(projectile);
		removePhysicsObject(projectile);
	}
	
	public void addPlanet(Planet planet) {
		planets.add(planet);
		addRenderable(planet);
		addPhysicsObject(planet);
	}
	
	public void addLander(Lander lander) {
		landers.add(lander);
		addRenderable(lander);
		addPhysicsObject(lander);
	}
	
	public void removeLander(Lander lander) {
		landers.remove(lander);
		removeRenderable(lander);
		removePhysicsObject(lander);
	}

	public void addRenderable(Renderable object) {
		scene.addObject(object);
	}

	public void addPhysicsObject(PhysicsObject object) {
		physicsSolver.addObject(object);
	}

	public void removeRenderable(Renderable object) {
		scene.removeObject(object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		physicsSolver.removeObject(object);
	}
}
