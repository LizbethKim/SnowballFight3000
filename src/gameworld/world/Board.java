package gameworld.world;

import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Represents the map. Made of Tile objects.
 * @author Kelsey Jack 300275851
 *
 */
public class Board {
	// KTC add more 
	private Tile[][] board; 	// board[row][column] will access correctly. 
	
	public Board() {
		// KTC currently only a test board. need to really load in from file.
		board = new Tile[20][15];
		for (int row = 0; row < 20; row++) {
			for (int col = 0; col < 15; col++) {
				board[row][col] = new Tile(new Location(row, col), Terrain.SNOW, null);
			}
		}
		board[9][12].place(new Furniture("A tree", Objects.TREE));
		board[2][3].place(new Furniture("A tree", Objects.TREE));
		board[18][4].place(new Furniture("A tree", Objects.TREE));
		board[7][7].place(new Furniture("A bush", Objects.BUSH));
	}
	
	public Tile tileAt(Location l) {
		if (l.x >= 0 && l.x < board.length && l.y >= 0 && l.y < board[0].length) {
			return board[l.x][l.y];
		}
		throw new IllegalArgumentException();
	}
	
	public boolean canTraverse (Location l) {
		return board[l.x][l.y].isTraversable();	
	}
	
	public Item removeItemAt(Location l) {
		if (l.x >= board[0].length || l.x < 0 
				|| l.y >= board.length || l.y < 0) 
			return null;	// KTC throw exception? 
		if (board[l.x][l.y] == null) 
			return null;
		return board[l.x][l.y].removeOn();
	}

	

}
