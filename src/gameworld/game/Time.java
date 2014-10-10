package gameworld.game;

import gameworld.game.server.ServerGame;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.clockTick();
		}
	}

}
