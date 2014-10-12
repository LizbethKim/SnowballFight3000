package gameworld.world;

import java.util.ArrayList;
import java.util.List;

import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Represents the map. Made of Tile objects.
 * @author Kelsey Jack 300275851
 *
 */
public class Board {
	private Tile[][] board; 	// board[x][y] will access correctly. (same x and y as in location)
	private List<Area> rooms;

	public Board(Tile[][] board) {
		this.board = board;
	}

	// KH if you need this.
	public Board(Tile[][] board, List<Area> rooms) {
		this.board = board;
		this.rooms = rooms;
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

	public void removeItemAt(Location l) {
		if (this.containsLocation(l)) {
				board[l.x][l.y].clear();
		}
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

	public Terrain[][] convertToEnumsInArea (Area a) {
		Terrain[][] enumTiles = new Terrain[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (a.contains(board[x][y])){
					enumTiles[x][y] = board[x][y].getType();
				} else {
					enumTiles[x][y] = Terrain.NULLTILE;
				}
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
					enumObjects[x][y] = on.asEnum();
				}
			}
		}
		return enumObjects;
	}

	/**
	 * Gets the double array of objects within the area that contains the location
	 * of the player.
	 * @param playerLoc
	 * @return
	 */
	public Objects[][] itemEnumsInArea (Area a) {
		Objects[][] enumObjects = new Objects[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				InanimateEntity on = board[x][y].getOn();
				if (on != null && a.contains(board[x][y])) {
					enumObjects[x][y] = on.asEnum();
				}
			}
		}
		return enumObjects;
	}

	public Area getAreaContaining(Location l) {
		for (Area a: rooms) {
			if (a.containsLoc(l)) {
				return a;
			}
		}
		throw new IllegalArgumentException();
	}

	public boolean containsLocation(Location l) {
		return l.x >= 0 && l.x < board.length && l.y >= 0 && l.y < board[0].length;
	}

	// For board storage help
	/**
	 * Gets max X coordinate on board
	 * @return
	 */
	public int getXMax() {
		return board.length;
	}

	/**
	 * Gets max Y coordinate on board
	 * @return
	 */
	public int getYMax() {
		return board[0].length;
	}

	public static Board defaultBoard() {
		Tile[][] board = new Tile[15][20];
		List<Area> rooms = new ArrayList<Area>();
		Area main = new Area();
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
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 20; y++) {
				main.add(board[x][y]);
			}
		}
		rooms.add(main);

		board[0][0].place(new Furniture("A tree", Objects.TREE));
		board[4][2].place(new Furniture("A tree", Objects.TREE));
		board[5][3].place(new Furniture("A tree", Objects.TREE));
		board[3][14].place(new Furniture("A tree", Objects.TREE));
		board[4][17].place(new Furniture("A tree", Objects.TREE));
		board[12][6].place(new Furniture("A tree", Objects.TREE));
		board[14][18].place(new Furniture("A tree", Objects.TREE));
		board[13][7].place(new Furniture("A tree", Objects.TREE));
		board[10][14].place(new Furniture("A tree", Objects.TREE));
		board[2][18].place(new Furniture("A bush", Objects.BUSH));
		board[3][4].place(new Furniture("A corner", Objects.CORNER_N_E));
		board[0][4].place(new Furniture("A corner", Objects.CORNER_N_W));
		board[3][5].place(new Furniture("A wall", Objects.WALL_N_S));
		board[3][6].place(new Furniture("A wall", Objects.WALL_N_S));
		board[2][7].place(new Furniture("A wall", Objects.WALL_E_W));
		board[1][7].place(new Furniture("A wall", Objects.WALL_E_W));
		board[0][7].place(new Furniture("A corner", Objects.CORNER_S_W));
		board[3][7].place(new Furniture("A corner", Objects.CORNER_S_E));
		board[6][11].place(new Key());
		board[14][4].place(new Flag(Team.RED));
		board[3][10].place(new Flag(Team.BLUE));
		board[12][17].place(new Powerup(Powerup.Power.HEALTH_POTION));
		board[13][10].place(new Furniture("A bush", Objects.BUSH));

		Bag b = new Bag();
		b.addItem(new Key());
		b.addItem(new Powerup(Powerup.Power.STRONG_HEALTH_POTION));
		board[3][18].place(b);

		Chest c = new Chest("A treasure chest");
		c.addItem(new Key());
		c.addItem(new Bag());

		board[13][2].place(c);

		return new Board(board, rooms);

//		board = new Tile[10][10];
//		for (int x = 1; x < 9; x++) {
//			board[x][0] = new Tile(new Location(x, 0), Terrain.GRASS, null);
//			board[x][9] = new Tile(new Location(x, 9), Terrain.GRASS, null);
//			for (int y = 1; y < 9; y++) {
// 				board[x][y] = new Tile(new Location(x, y), Terrain.SNOW, null);
// 			}
// 		}
//		for (int y = 0; y < 10; y++) {
// 			board[0][y] = new Tile(new Location(0, y), Terrain.GRASS, null);
//			board[9][y] = new Tile(new Location(9, y), Terrain.GRASS, null);
//		}
//
// 		board[0][0].place(new Furniture("A tree", Objects.TREE));
// 		board[4][2].place(new Furniture("A tree", Objects.TREE));
// 		board[5][3].place(new Furniture("A tree", Objects.TREE));
// 		board[2][7].place(new Furniture("A bush", Objects.BUSH));
	}

	public static Board defaultBoard2() {
		Tile[][] board = new Tile[80][80];
		List<Area> areas = new ArrayList<Area>();
		for (int x = 0; x < 80; x++) {
			for (int y = 0; y < 80; y++) {
				board[x][y] = new NullTile(new Location(x,y));
			}
		}

		Area main = new Area();
		for (int x = 10; x < 70; x++) {
			for (int y = 10; y < 70; y++) {
				board[x][y] = new Tile(new Location(x, y), Terrain.SNOW, null);
				main.add(board[x][y]);
			}
			board[x][10].place(new Furniture("A wall", Objects.WALL_E_W));
			board[x][69].place(new Furniture("A wall", Objects.WALL_E_W));
		}
		for (int y = 11; y < 69; y++) {
			board[10][y].place(new Furniture("A wall", Objects.WALL_N_S));
			board[69][y].place(new Furniture("A wall", Objects.WALL_N_S));
		}
		board[69][10].clear();
		board[69][69].clear();
		board[10][10].clear();
		board[10][69].clear();
		board[69][10].place(new Furniture("A wall", Objects.CORNER_S_W));
		board[69][69].place(new Furniture("A wall", Objects.CORNER_S_E));
		board[10][10].place(new Furniture("A wall", Objects.CORNER_N_W));
		board[10][69].place(new Furniture("A wall", Objects.CORNER_N_E));


		areas.add(main);
		Area redSpawn = new Area();

		for (int x = 0; x < 10; x++) {
			for (int y = 13; y < 40; y++) {
				board[x][y] = new Tile(new Location(x,y), Terrain.FLOOR, null);
				redSpawn.add(board[x][y]);
			}
			board[x][13].place(new Furniture("A wall", Objects.WALL_E_W));
			board[x][39].place(new Furniture("A wall", Objects.WALL_E_W));
		}

		for (int y = 13; y < 40; y++) {
			redSpawn.add(board[10][y]);
			board[0][y].place(new Furniture("A wall", Objects.WALL_N_S));
		}
		areas.add(redSpawn);
		return new Board(board, areas);

	}

}
