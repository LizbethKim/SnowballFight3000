package client;

import java.util.Collections;
import java.util.List;

import gameworld.world.Direction;
import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Represents the state of the board on the clients so that it can be
 * rendered
 * @author Kelsey Jack 300275851
 *
 */
public class BoardState {
	private Terrain[][] board;
	private Objects[][] entities;
	private List<PlayerState> players;
	private Direction d;	// The direction that the board is oriented. (Possibly something RB should store)
	
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
		entities[5][3] = Objects.BUSH;
		entities[2][7] = Objects.BUSH;
		entities[8][5] = Objects.TREE;
		this.d = Direction.NORTH;
		
		// Prints to the UI. Just to see if it works.
		System.out.println("Board:");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == Terrain.GRASS) {
					System.out.print("G");
				} else if (board[row][col] == Terrain.SNOW) {
					System.out.print("S");
				}
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Entities:");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (entities[row][col] == Objects.TREE) {
					System.out.print("T");
				} else if (entities[row][col] == Objects.BUSH) {
					System.out.print("B");
				} else {
					System.out.print('-');
				}
			}
			System.out.println("");
		}
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

	public List<PlayerState> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public static void main (String[] args) {
		new BoardState();
	}

}

