package game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
	public static float MIN_DISTANCE_BETWEEN_PLANETS = 20;
	public static float MAX_RADIUS = 20;

	public List<Planet> generatePlanets(int n) {
		LinkedList<Planet> planets = new LinkedList<Planet>();
		Random random = new Random();
		int size = 4*n^2;
		
		// Make a square window of possible spots for planets, twice the 
		// length of the number of planets
		boolean [] possibleSpace = new boolean [size];
		for(int i=0; i<size; i++)
			possibleSpace[i] = true;
		
		// Now, place the planets in a random spot on the grid
		for(int i=0; i<n; i++) {
			boolean foundSpot = false;
			do {
				int place = random.nextInt(size);
				if(possibleSpace[place]) {
					possibleSpace[place] = false;
					foundSpot = true;
					float mass = random.nextFloat();
					float radius = random.nextInt((int) MAX_RADIUS);
					float length = MAX_RADIUS*2 + MIN_DISTANCE_BETWEEN_PLANETS;
					float x = (place%n)*length + length/2;
					float y = (place/n) *length + length/2;
					Vector position = new Vector(x, y);
					Color c = new Color('r');
					Planet planet = new Planet(mass, position, radius, c);
					planets.add(planet);
				}
			} while(!foundSpot);
		}
		
		return planets;
	}

	// generates the specified number of landers and places them on the planets
	public List<Lander> generateLanders(List<Planet> planets, int n) {
		Random random = new Random();
		LinkedList<Lander> landers = new LinkedList<Lander>();
		
		for(int i=0; i<n; i++) {
			boolean isNewPlanet = true;
			do {
				int planetNumber = random.nextInt(planets.size());
				for(Lander l : landers) {
					if(l.getCurrentPlanet().equals(planets.get(planetNumber))) {
						isNewPlanet = false;
						continue;
					}
				}
				int angleOnPlanet = random.nextInt(360);
				float x =(float) Math.cos(angleOnPlanet*Math.PI/180)*
					planets.get(planetNumber).getRadius()+
					planets.get(planetNumber).getPosition().x;
				float y = (float) Math.sin(angleOnPlanet*Math.PI/180)*
					planets.get(planetNumber).getRadius() +
					planets.get(planetNumber).getPosition().y;
				Lander lander = new Lander(planets.get(planetNumber), 
						new Vector(x,y));
				landers.add(lander);
			} while(!isNewPlanet);
		}
		
		
		return landers;
	}
}
