package gameworld.game;

import server.Server;
import ui.UI;
import gameworld.game.server.ServerGame;
import gameworld.world.Board;

/**
 * Main game loop.
 * @author Kelsey Jack 300275851
 *
 */
public class Main {
	public static void main (String[] args) {
		if (args.length > 0) {
			if (args[0].equals("server")) {
				ServerGame g = new ServerGame(Board.defaultBoard());
				Server server = new Server(g);
				// start server connection accepting thread
				new Thread(server).start();
				new Thread(new Time(g)).start();
				server.sendLoop();
			}
			new UI();

		}
		new UI();
	}

}
