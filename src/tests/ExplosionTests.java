package tests;

import static org.junit.Assert.assertTrue;
import game.Lander;
import game.MapManager;
import game.Player;
import game.Vector;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ExplosionTests {

	@Test
	public void varyDamageTest() {
		List<Player> players = new LinkedList<Player>();
		players.add(new Player("Bob"));
		players.add(new Player("Tom"));
		
		MapManager manager = new MapManager(2, players);
		assertTrue(manager.getLanders().size() == 2);
		assertTrue(manager.getPlanets().size() == 2);
		Lander a = manager.getLanders().get(0);
		Lander b = manager.getLanders().get(1);
		// Maybe check to make sure that health = 100?
		Vector exp1 = a.getPosition().add(new Vector(5,5));
		Vector exp2 = b.getPosition().add(new Vector(10,10));
		// TODO: will have to double-check this once we fully implement explosion logic, may have to tweak values
		manager.makeExplosion(exp1, 20, 40);
		manager.makeExplosion(exp2, 20, 40);
		assertTrue("lander a was not damaged", a.getHealth() < 100);
		assertTrue("lander b was not damaged", b.getHealth() < 100);
		assertTrue("lander b was not damaged as much as lander a", a.getHealth() < b.getHealth());
		
		// reset the health of the landers
		a.setHealth(100);
		b.setHealth(100);
		exp2 = b.getPosition().subtract(new Vector(5,5));
		manager.makeExplosion(exp1, 20, 40);
		manager.makeExplosion(exp2, 20, 40);
		// Test to ensure that direction of blast doesn't matter
		assertTrue("landers a and b did not suffer the same amount of damage", a.getHealth() == b.getHealth());
		
		a.setHealth(100);
		b.setHealth(100);
		exp2 = b.getPosition().add(new Vector(5,5));
		manager.makeExplosion(exp1, 20, 40);
		manager.makeExplosion(exp2, 20, 40);
		// Test to make sure that same explosion location (relative) will produce same amount of damage
		assertTrue("landers suffered different amounts of damage", a.getHealth() == b.getHealth());
	}

}
