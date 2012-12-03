package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Lander;
import game.MapGenerator;
import game.MapManager;
import game.Planet;
import game.Vector;

import java.util.List;

import org.junit.Test;

public class GameSetupTests {

	@Test
	public void planetPlacementTest() {
		MapGenerator gen = new MapGenerator();
		List<Planet> planets = gen.generatePlanets(5);
		assertEquals(5, planets.size());

		for (Planet p1 : planets) {
			for (Planet p2 : planets) {
				if (p1 == p2) {
					continue;
				}

				float distance = Vector.distanceBetween(p1.getPosition(), p2.getPosition());
				float minDistance = p1.getRadius() + p2.getRadius() + MapGenerator.MIN_DISTANCE_BETWEEN_PLANETS;
				assertTrue("planets too close together", distance >= minDistance);
			}
		}
	}

	@Test
	public void landerPlacementTest() {
		MapManager map = new MapManager(4, 4);
		List<Planet> planets = map.getPlanets();
		List<Lander> landers = map.getLanders();

		assertEquals(4, planets.size());
		assertEquals(4, landers.size());

		for (Lander lander : landers) {
			assertTrue("more than one lander per planet", planets.contains(lander.getCurrentPlanet()));
			planets.remove(lander.getCurrentPlanet());
		}

		assertEquals("all planets did not have a lander on them", 0, planets.size());
	}

}
