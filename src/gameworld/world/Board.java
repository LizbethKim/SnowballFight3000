package gameworld.world;

public class Board {
	// TODO add more 
	private Tile[][] board; 	// board[row][column] will access correctly.
	
	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	public Item removeItemAt(Location l) {
		if (l.x >= board[0].length || l.x < 0 
				|| l.y >= board.length || l.y < 0) 
			return null;	// TODO throw exception? 
		if (board[l.x][l.y] == null) 
			return null;
		return board[l.x][l.y].removeOn();
	}

	public void clockTick() {
		// TODO update projectiles, possibly do time logic. 
	}

}
