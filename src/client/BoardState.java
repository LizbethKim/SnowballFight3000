package client;

/**
 * Represents the state of the board on the clients so that it can be
 * rendered
 * @author jackkels
 *
 */
public class BoardState {
	private Tile[][] board;
	private Objects[][] objects;

	/*
	 * EK This is for you, sets up a plain boring board for testing purposes.
	 */
	public BoardState() {
		board = new Tile[10][10];
		for (int i = 1; i < 9; i++) {
			board[i][0] = Tile.GRASS;
			board[i][9] = Tile.GRASS;
			for (int j = 1; j < 9; j++) {
				board[i][j] = Tile.SNOW;
			}
		}
		for (int i = 0; i < 10; i++) {
			board[0][i] = Tile.GRASS;
			board[9][i] = Tile.GRASS;
		}

		objects = new Objects[10][10];
		objects[4][2] = Objects.TREE;
		objects[2][7] = Objects.CHEST;
		objects[8][5] = Objects.TREE;
	}

	/**
	 * Represents the types of objects that can be rendered on a tile
	 *  - this can be furniture, items, etc. WALL_E_W is a wall going east to west,
	 *  WALL_N_S is a wall going north to south.
	 *  KTC EK we may need to add extra wall types, such as corners/doors.
	 * @author jackkels
	 *
	 */
	public enum Objects {
		KEY, POWERUP, CHEST, TREE, TABLE, WALL_E_W, WALL_N_S, BUSH
	}

	/**
	 * Represents the different types of terrain a tile may have.
	 * @author jackkels
	 *
	 */
	public enum Tile {
		SNOW, FLOOR, GRASS
	}

	public Tile[][] getArea() {
		return board;
	}

	public Objects[][] getObjects() {
		return objects;
	}


	public void update(Tile[][] newBoard, Objects[][] newObjects) {
		this.board = newBoard;
		this.objects = newObjects;
	}


}

