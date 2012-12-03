package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.MovableObject;
import game.PhysicsObject;
import game.PhysicsSolver;
import game.Planet;
import game.Vector;

import java.awt.Color;

import org.junit.Test;

public class PhysicsTests {

	private class MockObject extends MovableObject {

		public int collissionCount = 0;

		public MockObject(Vector position, Vector velocity) {
			super(position, velocity);
		}

		@Override
		public void collidedWith(PhysicsObject o) {
			collissionCount++;
		}

		@Override
		public float getBoundingRadius() {
			return 7;
		}

		@Override
		public float getMass() {
			return 1;
		}
	}

	@Test
	public void collisionTest() {
		PhysicsSolver physics = new PhysicsSolver();

		MockObject mock1 = new MockObject(new Vector(100, 100), new Vector(0, 0));
		MockObject mock2 = new MockObject(new Vector(100, 105), new Vector(0, 0));
		physics.addObject(mock1);
		physics.addObject(mock2);
		physics.simulateStep(0.0005f);

		assertTrue(mock1.collissionCount > 0);
		assertTrue(mock2.collissionCount > 0);

		assertEquals("collission processed wrong number of times", 1, mock1.collissionCount);
		assertEquals("collission processed wrong number of times", 1, mock2.collissionCount);
	}

	@Test
	public void gravityTest() {
		PhysicsSolver physics = new PhysicsSolver();

		Planet planet = new Planet(50, new Vector(0, 0), 10, Color.red);
		MockObject mock = new MockObject(new Vector(100, 0), new Vector(0, 0));

		physics.addObject(planet);
		physics.addObject(mock);

		for (int i = 0; i < 1000; i++) {
			physics.simulateStep(0.0005f);
		}

		assertTrue("object was not pulled into planet", mock.collissionCount > 0);
	}
}
