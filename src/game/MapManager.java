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
		Explosion explosion = new Explosion(position);
		// add to scene
		scene.addObject(explosion);
		// calculate and then apply damages
		for (Lander p : landers) {
			float distance = p.getPosition().subtract(position).magnitude();
			if(distance <= blastRadius) {
				int damage = (int) ((1 - (distance/blastRadius)*(distance/blastRadius)) * yield);
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
