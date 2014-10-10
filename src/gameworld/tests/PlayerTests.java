package gameworld.tests;

import static org.junit.Assert.*;
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

}
