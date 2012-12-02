package game;

import java.util.Iterator;
import java.util.List;

public class MapManager {
	private Scene scene;
	private PhysicsSolver physicsSolver;
	private List<Planet> planets;
	private List<Lander> landers;
	private List<Projectile> projectiles;
	public static int KILL_BONUS = 1000;

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
			addRenderable(planet);
			addPhysicsObject(planet);
		}

		Iterator<Player> iter = players.iterator();
		for (Lander lander : landers) {
			Player player = iter.next();
			player.setLander(lander);
			lander.setPlayer(player);
			addRenderable(lander);
			addPhysicsObject(lander);
		}
	}

	public void makeExplosion(Player player, Vector position, float blastRadius, float yield ) {
		Explosion explosion = new Explosion(position);
		// add to scene
		addRenderable(explosion);
		// calculate and then apply damages
		int score = 0;
		for (Lander p : landers) {
			float distance = Vector.distanceBetween(p.getPosition(), position);
			if (distance < blastRadius) {
				int damage = (int) ((1 - (distance / blastRadius) * (distance / blastRadius)) * yield);
				// apply kill bonus
				if (p.getHealth() - damage <= 0 && !p.getPlayer().equals(player)) {
					score += KILL_BONUS;
				}
				p.setHealth(p.getHealth() - damage);
				// give negative score for hitting yourself, or just add damage to score if you hit someone else
				if (p.getPlayer().equals(player)) {
					score -= damage;
				} else {
					score += damage;
				}
			}
		}
		// apply score
		score += player.getScore();
		player.setScore(score);
	}
	
	public void runStep(float timeStep) {
		physicsSolver.simulateStep(timeStep);
		scene.animationTick();
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
