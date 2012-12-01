package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
	public static float MIN_DISTANCE_BETWEEN_PLANETS = 100;
	public static float MAX_RADIUS = 80;
	public static float MIN_RADIUS = 30;
	public static float MIN_DENSITY = 10;
	public static float MAX_DENSITY = 40;

	public List<Planet> generatePlanets(int planetCount) {
		float gridSpacing = MIN_RADIUS * 2 + MIN_DISTANCE_BETWEEN_PLANETS;
		
		int gridSize = planetCount;
		
		Random rand = new Random();
		
		// populate list of possible positions
		List<Vector> positions = new ArrayList<Vector>(gridSize * gridSize);
		for (int y =  0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				positions.add(new Vector(x * gridSpacing, y * gridSpacing));
			}
		}
		
		// place the planets in random positions
		LinkedList<Vector> planetPositions = new LinkedList<Vector>();
		for (int i = 0; i < planetCount; i++) {
			int p = rand.nextInt(positions.size());
			planetPositions.add(positions.remove(p));
		}
		
		List<Planet> planets = new ArrayList<Planet>(planetCount);
		
		// determine the size and mass of each planet
		while (planetPositions.size() > 0) {
			Vector position = planetPositions.removeFirst();
			
			// find how much space it has around it
			float maxRadius = MAX_RADIUS;
			
			for (Planet planet : planets) {
				// calculate the maximum radius the new planet could be without coming too close to this planet 
				float maxLegalRadius = Vector.distanceBetween(planet.getPosition(), position) - planet.getRadius() - MIN_DISTANCE_BETWEEN_PLANETS;
				if (maxLegalRadius < maxRadius) {
					maxRadius = maxLegalRadius;
				}
			}
			
			for (Vector v : planetPositions) {
				float maxLegalRadius = Vector.distanceBetween(v, position) - MIN_RADIUS - MIN_DISTANCE_BETWEEN_PLANETS;
				if (maxLegalRadius < maxRadius) {
					maxRadius = maxLegalRadius;
				}
			}
			
			float radius = rand.nextFloat() * (maxRadius - MIN_RADIUS) + MIN_RADIUS;
			float density = rand.nextFloat() * (MAX_DENSITY - MIN_DENSITY) + MIN_DENSITY;
			float mass = (float)Math.PI * radius * radius * radius * 4 / 3 * density;
			
			// the multipliers cause the colors to generally be darker
			float red = rand.nextFloat() * 0.5f + 0.2f;
			float green = rand.nextFloat() * 0.5f + 0.2f;
			float blue = rand.nextFloat() * 0.5f + 0.2f;
			Color color = new Color(red, green, blue);
			
			planets.add(new Planet(mass, position, radius, color));
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
