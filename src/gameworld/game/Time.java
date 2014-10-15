package gameworld.game;

import gameworld.game.server.ServerGame;

/**
 * Used to tick time. Calls tick on a ServerGame.
 * @author Kelsey Jack 300275851
 *
 */
public class Time implements Runnable {
	private ServerGame game;

	public Time(ServerGame game) {
		this.game = game;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.clockTick();
		}
	}

}
