package client;

import client.Client.Direction;
import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Represents the state of the board on the clients so that it can be
 * rendered
 * @author jackkels
 *
 */
public class BoardState {
	private Terrain[][] board;
	private Objects[][] entities;
	private Direction d;
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
		entities[4][2] = Objects.TREE;
		entities[2][7] = Objects.CHEST;
		entities[8][5] = Objects.TREE;
		this.d = Direction.NORTH;
	}

	// Getter methods for EK
	public Terrain[][] getArea() {
		return board;
	}

	public Objects[][] getObjects() {
		return entities;
	}

	public Direction getDirection() {
		return d;
	}


	public void update(Terrain[][] newBoard, Objects[][] newEntities, Direction d) {
		this.board = newBoard;
		this.entities = newEntities;
		this.d = d;
	}


}

