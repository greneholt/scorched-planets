package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import game.GameController;
import game.Lander;
import game.PhysicsSolver;
import game.Planet;

import org.junit.Test;

public class GameSetupTests {

	@Test
	public void planetPlacingTest() {
		PhysicsSolver testSolver;
		GameController game = new GameController();
		game.generatePlanets(5);
		List<Planet> planets = new LinkedList<Planet>();
		// TODO: initialize the following (probably in for loop) --> get access to planets generated
		Planet p1 = null;
		Planet p2 = null;
		
		assertTrue(p1.getPosition().subtract(p2.getPosition()).magnitude()>= p1.getRadius() 
				+ p2.getRadius() + game.MIN_DISTANCE_BETWEEN_PLANETS);
	}
	
	@Test
	public void landerPlacementTest() {
		GameController game = new GameController();
		game.generatePlanets(4);
		game.generateLanders();
		// TODO: initialize the following --> get access to landers generated
		Lander l1 = null;
		Lander l2 = null;
		Planet p1 = l1.getCurrentPlanet();
		Planet p2 = l2.getCurrentPlanet();
		
		// check to make sure there are no more than two landers on each planet
		// check planets with 2 landers to make sure that two landers aren't in same hemisphere
	}
 
}
