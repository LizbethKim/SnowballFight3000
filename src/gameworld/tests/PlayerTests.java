package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Flag;
import gameworld.world.Inventory;
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
		toReturn.getInventory().addItem(new Key());
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
		Inventory i = p.getInventory();
		assertTrue(i.getContents().size() == 1);
		i.addItem(new Flag(Team.BLUE));
		assertTrue(i.getContents().size() == 2);
		i.removeItem(i.getContents().get(0));
		assertTrue(i.getContents().size() == 1);
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
