package tests;

import static org.junit.Assert.*;
import game.MovableObject;
import game.PhysicsObject;
import game.PhysicsSolver;
import game.Planet;
import game.Projectile;
import game.Scene;
import game.Vector;

import org.junit.Test;

public class PhysicsTests {

	@Test
	public void collisionTest() {
		PhysicsSolver physics = new PhysicsSolver();
		class MockObject extends MovableObject {

			public boolean hasCollided = false;

			public MockObject(Vector position, Vector velocity) {
				super(position, velocity);
			}

			@Override
			public float getMass() {
				return 1;
			}

			@Override
			public float getBoundingRadius() {
				return 7;
			}

			@Override
			public void collidedWith(PhysicsObject o) {
				hasCollided = true;
			}

		}
		MockObject mock1 = new MockObject(new Vector(100, 100), new Vector(0, 0));
		MockObject mock2 = new MockObject(new Vector(100, 105), new Vector(0, 0));
		physics.addDynamicObject(mock1);
		physics.addDynamicObject(mock2);
		physics.simulateStep(1);

		assertTrue(mock1.hasCollided);
		assertTrue(mock2.hasCollided);
	}

}
