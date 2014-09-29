package gameworld.game;

import client.SinglePlayerGame;
import server.Client;
import server.Server;
import ui.UI;
import gameworld.world.Board;
import gameworld.world.Furniture;
import gameworld.world.Location;
import gameworld.world.Tile;
import graphics.assets.Objects;
import graphics.assets.Terrain;

/**
 * Main game loop.
 * @author Kelsey Jack 300275851
 *
 */
public class Main {
	public static final boolean SERVER = false;
	public static final boolean CLIENT = false;
	public static final int TICK_TIME = 15;

	public Main() {
		// AUTO
	}

	public static void main (String[] args) {
		System.out.println(args[0]);
		if (args[0].equals("server")) {
			Server server = new Server();
			// start server connection accepting thread
			new Thread(server).start();
			// currently, the server hogs the current thread (this function runs a while(true) statement)
			server.sendLoop();
			// I think in theory, the server's main loop is basically the server recieve loop anyway, so this should be fine
			// (since the tick stuff should happen in another thread anyway)
			// the only caveat is a client probably can't be a server as well
			// if you don't like this, I can change it and make it more multithreaded
		} else if (args[0].equals("client")) {
			Client client = new Client("127.0.0.1"); //connect to localhost for testing, you can put a different address here
			//this function creates a new thread for itself
			client.startReceiving();
		} else { // debug single-player testing dev mode

			// KH, this is where I'll need dataloading methods

			singlePlayerGame();


		}
	}


	/*
	 * Main game loop frame. The idea is that this will just keep going,
	 * calling repaint on the UI and graphics, and ticking the model
	 * so it knows to increment projectiles and other time-based things.
	 * Everything else will be event-driven (at least, in my mind).
	 */
	private static void singlePlayerGame() {

		Tile[][] boardArray = new Tile[10][10];
		for (int x = 1; x < 9; x++) {
			boardArray[x][0] = new Tile(new Location(x, 0), Terrain.GRASS, null);
			boardArray[x][9] = new Tile(new Location(x, 9), Terrain.GRASS, null);
			for (int y = 1; y < 9; y++) {
				boardArray[x][y] = new Tile(new Location(x, y), Terrain.SNOW, null);
			}
		}
		for (int y = 0; y < 10; y++) {
			boardArray[0][y] = new Tile(new Location(0, y), Terrain.GRASS, null);
			boardArray[9][y] = new Tile(new Location(9, y), Terrain.GRASS, null);
		}

		boardArray[0][0].place(new Furniture("A tree", Objects.TREE));
		boardArray[4][2].place(new Furniture("A tree", Objects.TREE));
		boardArray[5][3].place(new Furniture("A tree", Objects.TREE));
		boardArray[2][7].place(new Furniture("A bush", Objects.BUSH));

		Board board = new Board(boardArray); // createBoardFromFile(filename);
		SinglePlayerGame game = new SinglePlayerGame(board);


		//	PROBABLY REDUNDANT
		 	// This is where I'll hook in Ryan's code.
		// This should create the window etc.
		// Also of course appropriate parameters.

//		while (true) {	// KTC perhaps change to "playing", allow game to finish.
//			// Loop forever
//			try {
//				Thread.sleep(TICK_TIME);
//				game.clockTick();
//				if(display != null) {
//					display.repaint();
//				}
//			} catch(InterruptedException e) {
//				// should never happen KTC perhaps do something here
//			}
//		}
	}

	/*
	 * In terms of client/server, I think the clients should have a tick that
	 * calls repaint on the UI/graphics, and the server should have one that
	 * ticks the model (projectiles, etc). Everything else can happen event-based. (Probably?)
	 */


}
