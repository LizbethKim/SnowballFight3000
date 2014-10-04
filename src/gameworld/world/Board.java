package gameworld.world;

import java.io.File;

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

	public Board() {
		board = new Tile[15][20];
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
		board[12][17] = new Tile(new Location(12,17), Terrain.GRASS, null);
		board[13][10].place(new Furniture("A bush", Objects.BUSH));

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

	public static Board boardFromFile(File f) {
		return null;	// KTC
	}

	public boolean containsLocation(Location newLoc) {
		return newLoc.x >= 0 && newLoc.x < board.length && newLoc.y >= 0 && newLoc.y < board[0].length;
	}

}
