package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Furniture;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Tile;
import graphics.assets.Objects;
import graphics.assets.Terrain;

import org.junit.Test;

public class BoardTests {
	
	@Test
	public void testTileAt() {
		Board b = setUpBoard();
		assertTrue(b.tileAt(new Location (0,0)).getType() == Terrain.GRASS);
	}
	
	@Test 
	public void testTraversable() {
		
	}
	
	@Test 
	public void testContainsLocation() {
		Board b = setUpBoard();
		assertTrue(b.containsLocation(new Location(0,0)));
		assertTrue(b.containsLocation(new Location(14,19)));
		assertTrue(b.containsLocation(new Location(12, 7)));
		assertFalse(b.containsLocation(new Location(-2,-2)));
		assertFalse(b.containsLocation(new Location(7, 25)));
		assertFalse(b.containsLocation(new Location(18, 10)));
		assertFalse(b.containsLocation(new Location(15, 20)));
	}
	
	@Test 
	public void testLocation() {
		Location l = new Location(2,3);
		assertTrue(Location.locationInFrontOf(l, Direction.EAST).equals(new Location(3,3)));
		assertTrue(Location.locationInFrontOf(l, Direction.NORTH).equals(new Location(2,2)));
		assertTrue(Location.locationInFrontOf(l, Direction.SOUTH).equals(new Location(2,4)));
		assertTrue(Location.locationInFrontOf(l, Direction.WEST).equals(new Location(1,3)));
	}
	
	
	// same as default board setup, but local in case that gets changed.
	private Board setUpBoard() {
		Tile[][] board = new Tile[15][20];
		for (int x = 1; x < 14; x++) {
			board[x][0] = new Tile(new Location(x, 0), Terrain.GRASS, null);
			board[x][19] = new Tile(new Location(x, 19), Terrain.GRASS, null);
			for (int y = 1; y < 19; y++) {
				board[x][y] = new Tile(new Location(x, y), Terrain.SNOW, null);
			}
		}
		for (int y = 0; y < 20; y++) {
			board[0][y] = new Tile(new Location(0, y), Terrain.GRASS, null);
			board[14][y] = new Tile(new Location(14, y), Terrain.GRASS, null);
		}

		board[0][0].place(new Furniture("A tree", Objects.TREE));
		board[4][2].place(new Furniture("A tree", Objects.TREE));
		board[5][3].place(new Furniture("A tree", Objects.TREE));
		board[2][7].place(new Furniture("A bush", Objects.BUSH));
		board[6][11].place(new Key("A key", 0));
		board[12][17] = new Tile(new Location(12,17), Terrain.GRASS, null);
		board[13][10].place(new Furniture("A bush", Objects.BUSH));
		return new Board(board);
	}
	

}
