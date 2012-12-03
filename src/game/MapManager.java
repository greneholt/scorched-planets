package game;

import java.util.LinkedList;
import java.util.List;

public class MapManager {
	private List<Lander> landers = new LinkedList<Lander>();
	private PhysicsSolver physicsSolver = new PhysicsSolver();
	private List<Planet> planets = new LinkedList<Planet>();
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	private Scene scene = new Scene();

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

	public void addLander(Lander lander) {
		landers.add(lander);
		addRenderable(lander);
		addPhysicsObject(lander);
	}

	public void addPhysicsObject(PhysicsObject object) {
		physicsSolver.addObject(object);
	}

	public void addPlanet(Planet planet) {
		planets.add(planet);
		addRenderable(planet);
		addPhysicsObject(planet);
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
		addRenderable(projectile);
		addPhysicsObject(projectile);
	}

	public void addRenderable(Renderable object) {
		scene.addObject(object);
	}

	public void explodeProjectiles() {
		List<Projectile> projectilesTemp = new LinkedList<Projectile>(projectiles);
		for (Projectile projectile : projectilesTemp) {
			projectile.explode();
		}
	}

	public List<Lander> getLanders() {
		return landers;
	}

	public PhysicsSolver getPhysicsSolver() {
		return physicsSolver;
	}

	public List<Planet> getPlanets() {
		return planets;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public Scene getScene() {
		return scene;
	}

	public boolean nextStepNeeded() {
		return projectiles.size() > 0 || scene.hasAnimations();
	}

	public void removeLander(Lander lander) {
		landers.remove(lander);
		removeRenderable(lander);
		removePhysicsObject(lander);
	}

	public void removePhysicsObject(PhysicsObject object) {
		physicsSolver.removeObject(object);
	}

	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
		removeRenderable(projectile);
		removePhysicsObject(projectile);
	}

	public void removeRenderable(Renderable object) {
		scene.removeObject(object);
	}

	// returns true if the simulation needs to continue
	public void runStep(float timeStep) {
		scene.animationTick();
		physicsSolver.simulateStep(timeStep);
	}
}
