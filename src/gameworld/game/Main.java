package gameworld.game;

import client.ClientGame;
import client.SinglePlayerGame;
import client.ClientUpdater;
import server.Client;
import server.Server;
import storage.LoadGame;
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
		if (args.length > 0) {
			System.out.println(args[0]);
			if (args[0].equals("server")) {
				// KTC put an Updater in this function:
				ServerGame g = new ServerGame(new Board());
				Server server = new Server(g);
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
				Board b = new Board();	// This should be new from file - the first file to come through the network perhaps
				ClientGame g = new ClientGame(b, client);
				//this function creates a new thread for itself

				ClientUpdater u = g.makeUpdater();
				client.startReceiving(u);
			} else {
				singlePlayerGame();
			}
		}
		else { // debug single-player testing dev mode

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
		LoadGame lg = new LoadGame();
		SinglePlayerGame game = new SinglePlayerGame(lg.loadGame("defaultBoard.xml"));	//KTC add default game-file here KH uncomment to test

//		Board board = new Board(); // createBoardFromFile(filename);
//		SinglePlayerGame game = new SinglePlayerGame(board);


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
