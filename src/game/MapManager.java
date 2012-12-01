package game;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public class MapManager {
	private Scene scene;
	private PhysicsSolver physicsSolver;
	private List<Planet> planets;
	private List<Lander> landers;
	private List<Projectile> projectiles;
	private Timer timer;

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
		scene = new Scene();
		physicsSolver = new PhysicsSolver();

		MapGenerator gen = new MapGenerator();
		planets = gen.generatePlanets(planetCount);
		landers = gen.generateLanders(planets, players.size());

		for (Planet planet : planets) {
			scene.addObject(planet);
			physicsSolver.addObject(planet);
		}

		Iterator<Player> iter = players.iterator();
		for (Lander lander : landers) {
			iter.next().setLander(lander);
			scene.addObject(lander);
			physicsSolver.addObject(lander);
		}
	}

	public void makeExplosion(Vector position, float blastRadius, float yield) {
		Explosion explosion = new Explosion(position);
		// add to scene
		scene.addObject(explosion);
		// calculate and then apply damages
		for (Lander p : landers) {
			float distance = Vector.distanceBetween(p.getPosition(), position);
			if (distance < blastRadius) {
				int damage = (int) ((1 - (distance / blastRadius) * (distance / blastRadius)) * yield);
				p.setHealth(p.getHealth() - damage);
			}
		}
	}

	public void removeRenderable(Renderable object) {
		scene.removeObject(object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		physicsSolver.removeObject(object);
	}
}
