package gameworld.world;

public class FireSpeedEffect implements PowerupEffect, Runnable {
	private Player p;

	@Override
	public void apply(Player p) {
		this.p = p;
		new Thread(this).start();

	}

	@Override
	public void run() {
		long originalDelay = p.getSnowballDelay();
		p.setSnowballDelay(100);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		p.setSnowballDelay(originalDelay);
	}

}
