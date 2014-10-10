package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Direction;
import gameworld.world.Location;

import org.junit.Test;

public class BoardTests {

	@Test
	public void testTileAt() {
		
	}
	
	@Test 
	public void testTraversable() {
		
	}
	
	@Test 
	public void testLocation() {
		Location l = new Location(2,3);
		assertTrue(Location.locationInFrontOf(l, Direction.EAST).equals(new Location(3,3)));
		assertTrue(Location.locationInFrontOf(l, Direction.NORTH).equals(new Location(2,2)));
		assertTrue(Location.locationInFrontOf(l, Direction.SOUTH).equals(new Location(2,4)));
		assertTrue(Location.locationInFrontOf(l, Direction.WEST).equals(new Location(1,3)));
	}
	
	

}
