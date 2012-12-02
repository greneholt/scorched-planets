package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Lander;
import game.MapManager;
import game.Missile;
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
		a.setPlayer(players.get(0));
		b.setPlayer(players.get(1));
		players.get(0).setLander(a);
		players.get(1).setLander(b);
		a.setPosition(new Vector(0, 0));
		b.setPosition(new Vector(100, 0));
		assertEquals("lander not at full health", a.getHealth(), Lander.FULL_HEALTH);

		Missile missile1 = new Missile(b, a.getPosition().add(new Vector(5, 5)), new Vector(0,0), manager);
		Missile missile2 = new Missile(a, b.getPosition().add(new Vector(10, 10)), new Vector(0,0), manager);

		manager.makeExplosion(missile1, 40, 40);
		manager.makeExplosion(missile2, 40, 40);
		assertTrue("lander a was not damaged", a.getHealth() < Lander.FULL_HEALTH);
		assertTrue("lander b was not damaged", b.getHealth() < Lander.FULL_HEALTH);
		assertTrue("lander b was not damaged as much as lander a", a.getHealth() < b.getHealth());

		// reset the health of the landers
		a.setHealth(Lander.FULL_HEALTH);
		b.setHealth(Lander.FULL_HEALTH);

		missile2 = new Missile(a, b.getPosition().subtract(new Vector(5, 5)), new Vector(0,0), manager);
		manager.makeExplosion(missile1, 40, 40);
		manager.makeExplosion(missile2, 40, 40);
		// Test to ensure that direction of blast doesn't matter
		assertEquals("landers a and b did not suffer the same amount of damage", a.getHealth(), b.getHealth());

		a.setHealth(Lander.FULL_HEALTH);
		b.setHealth(Lander.FULL_HEALTH);
		missile2 = new Missile(a, b.getPosition().add(new Vector(5, 5)), new Vector(0,0), manager);
		manager.makeExplosion(missile1, 40, 40);
		manager.makeExplosion(missile2, 40, 40);
		// Test to make sure that same explosion location (relative) will produce same amount of damage
		assertEquals("landers suffered different amounts of damage", a.getHealth(), b.getHealth());
	}

}
