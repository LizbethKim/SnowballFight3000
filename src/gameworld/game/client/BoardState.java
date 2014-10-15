package gameworld.game.client;

import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.NullLocation;
import graphics.assets.Entities;
import graphics.assets.Terrain;

/**
 * Represents the state of the board so that it can be
 * rendered. Consists of an orientation, the location of
 * the player it's centred on, the tiles, the brightness,
 * and the objects on the tiles.
 * @author Kelsey Jack 300275851
 *
 */
public class BoardState {
	private Terrain[][] board;
	private Entities[][] entities;
	private Direction orientation = Direction.NORTH;
	private Location playerCoords;
	private int time = 12;
	private boolean nightVision;

	public BoardState(Terrain[][] board, Entities[][] entities) {
		this.board = board;
		this.entities = entities;
		this.playerCoords = new NullLocation();
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
	public Entities[][] getObjects() {
		Entities[][] entitiesCopy = new Entities[entities.length][entities[0].length];
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

	/**
	 * @return The amount of light in the board
	 */
	public int getLight() {
		if (nightVision) {
			return 2;
		}
		if ((5 <= time && time <= 6) || (17 <= time && time <= 18)) {
			return 3;
		} else if (19 <= time && time <= 22 || 2 <= time && time <=4){
			return 1;
		} else if (7 <= time && time <= 16) {
			return 2;
		} else {
			return 0;
		}
	}

	// Methods to update the state

	protected void update(Terrain[][] newBoard, Entities[][] newEntities, Location playerCoords) {
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

	protected void setTime(int time) {
		this.time = time;
	}

	protected void toggleNightVision() {
		this.nightVision = !this.nightVision;
	}
}
