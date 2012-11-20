package tests;

import static org.junit.Assert.*;
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
		class MockProjectile extends Projectile {

			public boolean hasCollided = false;
			
			public MockProjectile(Vector position, Vector velocity) {
				super(position, velocity);
			}

			@Override
			public float getMass() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getBoundingRadius() {
				// TODO Auto-generated method stub
				return 7;
			}

			@Override
			public void hit(Planet planet, Scene scene) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void collidedWith(PhysicsObject o) {
				hasCollided = true;
			}
			
		}
		MockProjectile mock1 = new MockProjectile(new Vector(100,100), new Vector(0,0));
		MockProjectile mock2 = new MockProjectile(new Vector(100,105), new Vector(0,0));
		physics.addDynamicObject(mock1);
		physics.addDynamicObject(mock2);
		physics.simulateStep(1);
		
		assertTrue(mock1.hasCollided);
		assertTrue(mock2.hasCollided);
		
	}

}
