package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Lander;
import game.MapGenerator;
import game.Planet;

import java.util.List;

import javax.media.jai.operator.MinDescriptor;

import org.junit.Test;

public class GameSetupTests {

	@Test
	public void planetPlacingTest() {
		MapGenerator gen = new MapGenerator();
		List<Planet> planets = gen.generatePlanets(5);
		assertEquals(5, planets.size());
		
		for(Planet p1 : planets) {
			for(Planet p2 : planets) {
				if(p1 == p2) {
					continue;
				}
				
				
				float distance = p1.getPosition().subtract(p2.getPosition()).magnitude();
				float minDistance = p1.getRadius() + p2.getRadius() + MapGenerator.MIN_DISTANCE_BETWEEN_PLANETS;
				assertTrue("planets too close together", distance >= minDistance);
			}
		}
	}

	@Test
	public void landerPlacementTest() {
		MapGenerator gen = new MapGenerator();
		List<Planet> planets = gen.generatePlanets(4);
		List<Lander> landers = gen.generateLanders(planets, 4);

		assertEquals(4, planets.size());
		assertEquals(4, landers.size());

		for (Lander lander : landers) {
			assertTrue("more than one planet per lander", planets.contains(lander.getCurrentPlanet()));
			planets.remove(lander.getCurrentPlanet());
		}

		assertEquals("all planets did not have a lander on them", 0, planets.size());
	}

}
