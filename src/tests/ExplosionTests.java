package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Explosion;
import game.Lander;
import game.MapManager;
import game.Player;
import game.Vector;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ExplosionTests {

	@Test
	public void scoreTest() {
		List<Player> players = new LinkedList<Player>();
		players.add(new Player("Bob"));
		players.add(new Player("Tom"));

		MapManager map = new MapManager(2, players);
		assertTrue(map.getLanders().size() == 2);
		assertTrue(map.getPlanets().size() == 2);
		Lander a = map.getLanders().get(0);
		Lander b = map.getLanders().get(1);
		a.setPosition(new Vector(0, 0));
		b.setPosition(new Vector(100, 0));

		int initialScoreB = b.getPlayer().getScore();
		assertEquals("lander not at full health", a.getHealth(), Lander.FULL_HEALTH);
		Vector exp1 = a.getPosition().add(new Vector(5, 5));

		map.addRenderable(new Explosion(b.getPlayer(), exp1, 40, 40, map));

		for (int i = 0; i < 100; i++) {
			map.runStep(0.01f);
		}

		assertTrue("player's score did not increase", b.getPlayer().getScore() > initialScoreB);

		while (a.getHealth() > 0) {
			initialScoreB = b.getPlayer().getScore();
			map.addRenderable(new Explosion(b.getPlayer(), exp1, 40, 40, map));

			for (int i = 0; i < 100; i++) {
				map.runStep(0.01f);
			}
		}

		assertTrue("player did not get kill bonus", Player.KILL_BONUS <= b.getPlayer().getScore() - initialScoreB);

		map.addLander(a);

		int initialScoreA = a.getPlayer().getScore();
		a.setHealth(Lander.FULL_HEALTH);
		assertEquals("lander not at full health", a.getHealth(), Lander.FULL_HEALTH);

		map.addRenderable(new Explosion(a.getPlayer(), exp1, 40, 40, map));

		for (int i = 0; i < 100; i++) {
			map.runStep(0.01f);
		}

		assertTrue("player did not lose points for self-damage", a.getPlayer().getScore() < initialScoreA);

		while (a.getHealth() > 0) {
			initialScoreA = a.getPlayer().getScore();

			map.addRenderable(new Explosion(a.getPlayer(), exp1, 40, 40, map));

			for (int i = 0; i < 100; i++) {
				map.runStep(0.01f);
			}
		}
		assertTrue("player got kill bonus for suicide", Player.KILL_BONUS > a.getPlayer().getScore() - initialScoreA);
	}

	@Test
	public void varyDamageTest() {
		List<Player> players = new LinkedList<Player>();
		players.add(new Player("Bob"));
		players.add(new Player("Tom"));

		MapManager map = new MapManager(2, players);
		assertTrue(map.getLanders().size() == 2);
		assertTrue(map.getPlanets().size() == 2);
		Lander a = map.getLanders().get(0);
		Lander b = map.getLanders().get(1);
		a.setPosition(new Vector(0, 0));
		b.setPosition(new Vector(100, 0));
		assertEquals("lander not at full health", a.getHealth(), Lander.FULL_HEALTH);

		Vector exp1 = a.getPosition().add(new Vector(5, 5));
		Vector exp2 = b.getPosition().add(new Vector(10, 10));

		map.addRenderable(new Explosion(b.getPlayer(), exp1, 40, 40, map));
		map.addRenderable(new Explosion(a.getPlayer(), exp2, 40, 40, map));

		for (int i = 0; i < 100; i++) {
			map.runStep(0.01f);
		}

		assertTrue("lander a was not damaged", a.getHealth() < Lander.FULL_HEALTH);
		assertTrue("lander b was not damaged", b.getHealth() < Lander.FULL_HEALTH);
		assertTrue("lander b was not damaged as much as lander a", a.getHealth() < b.getHealth());

		// reset the health of the landers
		a.setHealth(Lander.FULL_HEALTH);
		b.setHealth(Lander.FULL_HEALTH);

		exp2 = b.getPosition().subtract(new Vector(5, 5));
		map.addRenderable(new Explosion(b.getPlayer(), exp1, 40, 40, map));
		map.addRenderable(new Explosion(a.getPlayer(), exp2, 40, 40, map));

		for (int i = 0; i < 100; i++) {
			map.runStep(0.01f);
		}
		// Test to ensure that direction of blast doesn't matter
		assertEquals("landers a and b did not suffer the same amount of damage", a.getHealth(), b.getHealth());

		a.setHealth(Lander.FULL_HEALTH);
		b.setHealth(Lander.FULL_HEALTH);
		exp2 = b.getPosition().add(new Vector(5, 5));
		map.addRenderable(new Explosion(b.getPlayer(), exp1, 40, 40, map));
		map.addRenderable(new Explosion(a.getPlayer(), exp2, 40, 40, map));

		for (int i = 0; i < 100; i++) {
			map.runStep(0.01f);
		}
		// Test to make sure that same explosion location (relative) will produce same amount of damage
		assertEquals("landers suffered different amounts of damage", a.getHealth(), b.getHealth());
	}

}
