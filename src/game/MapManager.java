package game;

public class MapManager {
	private Scene scene;
	private PhysicsSolver physicsSolver;

	public void setupMap(int planet_count) {
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
