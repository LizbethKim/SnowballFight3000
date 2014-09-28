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


	public Board(Tile[][] board) {
		this.board = board;
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

	public Terrain[][] convertToEnums () {
		Terrain[][] enumTiles = new Terrain[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				enumTiles[row][col] = board[row][col].getType();
			}
		}
		return enumTiles;
	}

	public Objects[][] itemEnums () {
		Objects[][] enumObjects = new Objects[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				InanimateEntity on = board[row][col].getOn();
				if (on != null) {
					if (on instanceof Key) {
						enumObjects[row][col] = Objects.KEY;
					} else if (on instanceof Map) {
						enumObjects[row][col] = Objects.MAP;
					} else if (on instanceof Powerup) {
						enumObjects[row][col] = Objects.POWERUP;
					} else if (on instanceof Chest) {
						enumObjects[row][col] = Objects.CHEST;
					} else if (on instanceof Furniture) {
						enumObjects[row][col] = ((Furniture)on).getType();
					}
				}
			}
		}
		return enumObjects;
	}

}
