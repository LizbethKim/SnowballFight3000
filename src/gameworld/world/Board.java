package gameworld.world;

public class Board {
	// KTC add more 
	private Tile[][] board; 	// board[row][column] will access correctly.
	
	public Board() {
		// AUTO
	}
	
	public boolean canTraverse (Location l) {
		return board[l.x][l.y].isTraversable();		// TEMP
	}
	
	public Item removeItemAt(Location l) {
		if (l.x >= board[0].length || l.x < 0 
				|| l.y >= board.length || l.y < 0) 
			return null;	// KTC throw exception? 
		if (board[l.x][l.y] == null) 
			return null;
		return board[l.x][l.y].removeOn();
	}

	public void clockTick() {
		// KTC update projectiles, possibly do time logic. 
	}

}
