package game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
	public static float MIN_DISTANCE_BETWEEN_PLANETS = 20;
	public static float MAX_RADIUS = 20;

	public List<Planet> generatePlanets(int n) {
		List<Planet> planets = new LinkedList<Planet>();
		Random random = new Random();
		int size = 4 * n ^ 2;

		LinkedList<Vector> possibleSpaces = new LinkedList<Vector>();
		float length = MAX_RADIUS * 2 + MIN_DISTANCE_BETWEEN_PLANETS;
		for (int i = 0; i < size; i++) {
			float x = (i % (2 * n)) * length + length / 2;
			float y = (i / (2 * n)) * length + length / 2;
			possibleSpaces.add(new Vector(x, y));
		}

		// Now, place the planets in a random spot on the grid
		for (int i = 0; i < n; i++) {
			int place = random.nextInt(size - i);
			float mass = random.nextFloat();
			float radius = random.nextInt((int) MAX_RADIUS);
			Color c = new Color('r');
			Planet planet = new Planet(mass, possibleSpaces.get(place), radius, c);
			planets.add(planet);
			possibleSpaces.remove(place);
		}
		return planets;
	}

	// generates the specified number of landers and places them on the planets
	public List<Lander> generateLanders(List<Planet> planets, int n) {
		Random random = new Random();

		LinkedList<Planet> losablePlanets = new LinkedList<Planet>(planets);

		List<Lander> landers = new LinkedList<Lander>();

		for (int i = 0; i < n; i++) {
			int planetNumber = random.nextInt(losablePlanets.size());
			int angleOnPlanet = random.nextInt(360);
			Planet planet = losablePlanets.get(planetNumber);
			float x = (float) Math.cos(angleOnPlanet * Math.PI / 180) * planet.getRadius() + planet.getPosition().x;
			float y = (float) Math.sin(angleOnPlanet * Math.PI / 180) * planet.getRadius() + planet.getPosition().y;
			Lander lander = new Lander(planet, new Vector(x, y));
			landers.add(lander);
			losablePlanets.remove(planetNumber);
		}
		return landers;
	}
}
