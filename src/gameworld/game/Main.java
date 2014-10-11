package gameworld.game;

import server.Server;
import storage.LoadGame;
import storage.StoredGame;
import ui.UI;
import gameworld.game.client.SinglePlayerGame;
import gameworld.game.server.ServerGame;
import gameworld.world.Board;

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
				ServerGame g = new ServerGame(Board.defaultBoard());
				Server server = new Server(g);
				// start server connection accepting thread
				new Thread(server).start();
				new Thread(new Time(g)).start();
				server.sendLoop();
			} else if (args[0].equals("client")) {
				new UI();
			} else {
				singlePlayerGame();
			}
		}
		else { // debug single-player testing dev mode
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
		StoredGame sg = lg.loadGame("defaultBoard.xml");
		new SinglePlayerGame(sg);	// uncomment to test

//		Board board = new Board(); // createBoardFromFile(filename);
//		SinglePlayerGame game = new SinglePlayerGame(board);
	}


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
