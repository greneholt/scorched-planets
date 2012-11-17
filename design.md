# Classes

	class Planet implements StaticObject, Renderable {
		color
		radius
	}

	interface DynamicObject {
		mass
		velocity vector
		position vector
	}

	interface StaticObject {
		mass
		position vector
	}

	interface Renderable {
		render(graphics)
	}

	class Scene {
		list of renderables
		render(graphics)
		physics solver
		removeObject(object) - removes from renderables and solver
		timer for periodic events
	}

	class Lander implements StaticObject, Renderable {
		health
		planet reference
		angle (for rendering)
		shot power
		shot angle
	}

	interface Projectile implements DynamicObject {
		hit(planet, scene)
		fired by lander
	}

	class Missile implements Projectile, Renderable {
		implements hit to cause damage to other landers
	}

	class Teleporter implements Projectile, Renderable {
		implements hit to teleport firing lander to hit location
	}

	class PhysicsSolver {
		list of dynamic objects
		list of static objects
		removeObject(object)
		simulateStep()
	}

	class Player {
		lander
		score
		kills list
	}

	class GameController {
		list of players
		nextTurn()
		runTurn()
	}