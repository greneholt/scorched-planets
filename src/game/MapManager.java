package game;

import java.util.Iterator;
import java.util.LinkedList;
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
		projectiles = new LinkedList<Projectile>();

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

	public void makeExplosion(Player player, Vector position, float blastRadius, float yield) {
		// add explosion to scene
		addRenderable(new Explosion(position, blastRadius));
		
		// calculate and then apply damages
		int score = 0;
		
		Iterator<Lander> iter = landers.iterator();
		while (iter.hasNext()) {
			Lander lander = iter.next();
			float distance = Vector.distanceBetween(lander.getPosition(), position);
			if (distance < blastRadius) {
				int damage = (int) ((1 - (distance / blastRadius) * (distance / blastRadius)) * yield);

				lander.damage(damage);

				// apply kill bonus
				if (lander.getHealth() <= 0) {
					addRenderable(new Explosion(lander.getPosition(), 20)); // just for fun
					
					iter.remove();
					removeRenderable(lander);
					removePhysicsObject(lander);
					
					if (!lander.getPlayer().equals(player)) {
						score += KILL_BONUS;
					}
				}

				// give negative score for hitting yourself, or just add damage to score if you hit someone else
				if (lander.getPlayer().equals(player)) {
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

	// returns true if the simulation needs to continue
	public boolean runStep(float timeStep) {
		physicsSolver.simulateStep(timeStep);
		scene.animationTick();

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
