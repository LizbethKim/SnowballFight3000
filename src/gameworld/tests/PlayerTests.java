package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Flag;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Team;

import org.junit.Test;

public class PlayerTests {
	private Player setUpPlayer() {
		return new Player("player1", Team.RED, 100, new Location (3,3));
	}

	private Player setUpPlayerWithInventory() {
		Player toReturn = new Player("Player2", Team.BLUE, 50, new Location(2,4));
		toReturn.addItemToInventory(new Key("A key", 0));
		return toReturn;
	}

	@Test
	public void testMoveWorks() {
		Player p = setUpPlayer();
		Location n = new Location (3,4);
		assertTrue(p.move(n));
		assertTrue(p.getLocation().equals(n));
	}

	@Test
	public void testMoveRestrictions() {
		Player p = setUpPlayer();
		Location n = new Location(10,10);
		assertFalse(p.move(n));
		assertFalse(p.getLocation().equals(n));
	}

	@Test
	public void testPlayerInventory() {
		Player p = setUpPlayerWithInventory();
		assertTrue(p.getInventoryItems().size() == 1);
		assertTrue(p.addItemToInventory(new Flag(Team.RED)));
		assertTrue(p.getInventoryItems().size() == 2);
		p.removeFromInventory(0);
		assertTrue(p.getInventoryItems().size() == 1);
		assertFalse(p.addItemToInventory(new Flag(Team.BLUE)));
		assertTrue(p.addItemToInventory(new Key("", 5)));
		assertTrue(p.holdsKey(5));
		assertTrue(p.getKeyIndex(5) == 1);
	}

	@Test
	public void testDamage() {
		Player p = setUpPlayer();
		assertTrue(p.getHealth() == 100);
		p.damage(20);
		assertTrue(p.getHealth() == 80);
		p.damage(85);
		assertTrue(p.getHealth() == 0);
		assertTrue(p.isFrozen());
		p.damage(-110);
		assertTrue(p.getHealth() == 100);
	}

	@Test
	public void testScore() {
		Player p = setUpPlayer();
		assertTrue(p.getScore() == 0);
		p.incrementScore(500);
		assertTrue(p.getScore() == 500);
		p.incrementScore(-600);
		assertTrue(p.getScore() == 0);
	}

}
