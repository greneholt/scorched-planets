package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import game.GameController;
import game.Lander;
import game.MapGenerator;
import game.PhysicsSolver;
import game.Planet;

import org.junit.Test;

public class GameSetupTests {

	@Test
	public void planetPlacingTest() {
		MapGenerator game = new MapGenerator();
		List<Planet> planets = game.generatePlanets(5);
		
		for(Planet p1 : planets) {
			for(Planet p2 : planets) {
				if(!p1.equals(p2)) {
					assertTrue(p1.getPosition().subtract(p2.getPosition()).magnitude()>= p1.getRadius() 
							+ p2.getRadius() + game.MIN_DISTANCE_BETWEEN_PLANETS);
				}
			}
		}
	}
	
	@Test
	public void landerPlacementTest() {
		
	}
 
}
