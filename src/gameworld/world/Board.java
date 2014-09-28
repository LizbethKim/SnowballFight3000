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
	private Tile[][] board; 	// board[x][y] will access correctly. (same x and y as in location)


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
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				enumTiles[x][y] = board[x][y].getType();
			}
		}
		return enumTiles;
	}

	public Objects[][] itemEnums () {
		Objects[][] enumObjects = new Objects[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				InanimateEntity on = board[x][y].getOn();
				if (on != null) {
					if (on instanceof Key) {
						enumObjects[x][y] = Objects.KEY;
					} else if (on instanceof Map) {
						enumObjects[x][y] = Objects.MAP;
					} else if (on instanceof Powerup) {
						enumObjects[x][y] = Objects.POWERUP;
					} else if (on instanceof Chest) {
						enumObjects[x][y] = Objects.CHEST;
					} else if (on instanceof Furniture) {
						enumObjects[x][y] = ((Furniture)on).getType();
					}
				}
			}
		}
		return enumObjects;
	}

}
