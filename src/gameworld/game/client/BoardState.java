package gameworld.game.client;

import gameworld.world.Direction;
import gameworld.world.Location;
import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Represents the state of the board so that it can be
 * rendered. Consists of an orientation, the location of
 * the player it's centred on, the tiles and the objects on
 * the tiles.
 * @author Kelsey Jack 300275851
 *
 */
public class BoardState {
	private Terrain[][] board;
	private Objects[][] entities;
	private Direction orientation = Direction.NORTH;
	private Location playerCoords;

	/*
	 * EK This is for you, sets up a plain boring board for testing purposes.
	 */
	public BoardState() {
		board = new Terrain[10][10];
		for (int i = 1; i < 9; i++) {
			board[i][0] = Terrain.GRASS;
			board[i][9] = Terrain.GRASS;
			for (int j = 1; j < 9; j++) {
				board[i][j] = Terrain.SNOW;
			}
		}
		for (int i = 0; i < 10; i++) {
			board[0][i] = Terrain.GRASS;
			board[9][i] = Terrain.GRASS;
		}

		entities = new Objects[10][10];
		entities[0][0] = Objects.POWERUP;
		entities[4][2] = Objects.TREE;
		entities[5][3] = Objects.BUSH;
		entities[2][7] = Objects.BUSH;
		entities[8][5] = Objects.TREE;
		this.orientation = Direction.NORTH;

		playerCoords = new Location(3,3);

//		// Prints to the UI. Just to see if it works.
//		System.out.println("Board:");
//		for (int x = 0; x < board.length; x++) {
//			for (int y = 0; y < board[0].length; y++) {
//				if (board[x][y] == Terrain.GRASS) {
//					System.out.print("G");
//				} else if (board[x][y] == Terrain.SNOW) {
//					System.out.print("S");
//				}
//			}
//			System.out.println("");
//		}
//
//		System.out.println("");
//		System.out.println("Entities:");
//		for (int x = 0; x < board.length; x++) {
//			for (int y = 0; y < board[0].length; y++) {
//				if (entities[x][y] == Objects.TREE) {
//					System.out.print("T");
//				} else if (entities[x][y] == Objects.BUSH) {
//					System.out.print("B");
//				} else {
//					System.out.print('-');
//				}
//			}
//			System.out.println("");
//		}
	}


	public BoardState(Terrain[][] board, Objects[][] entities) {
		this.board = board;
		this.entities = entities;
	}

	// Getter methods for rendering

	/**
	 * @return A copy of the double array of Terrain enums to be rendered.
	 */
	public Terrain[][] getArea() {
		Terrain[][] boardCopy = new Terrain[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				boardCopy[x][y] = board[x][y];
			}
		}
		return boardCopy;
	}

	/**
	 * @return A double array of Objects enums to be rendered on the tiles.
	 */
	public Objects[][] getObjects() {
		Objects[][] entitiesCopy = new Objects[entities.length][entities[0].length];
		for (int x = 0; x < entities.length; x++) {
			for (int y = 0; y < entities[0].length; y++) {
				entitiesCopy[x][y] = entities[x][y];
			}
		}
		return entitiesCopy;
	}

	/**
	 * @return The coordinates of the player to centre on.
	 */
	public Location getPlayerCoords() {
		return playerCoords;
	}

	/**
	 * @return The direction that the board is oriented.
	 */
	public Direction getDirection() {
		return orientation;
	}

	// Methods to update the state

	protected void update(Terrain[][] newBoard, Objects[][] newEntities, Location playerCoords) {
		this.board = newBoard;
		this.entities = newEntities;
		this.playerCoords = playerCoords;
	}

	protected void rotateClockwise() {
		orientation = Direction.values()[(orientation.ordinal() + 1) % 4];
	}

	protected void rotateAnticlockwise() {
		// goes forward 3 clockwise - same as going one anticlockwise.
		orientation = Direction.values()[(orientation.ordinal() + 3) % 4];
	}
}
