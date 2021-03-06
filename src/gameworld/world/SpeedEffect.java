package gameworld.world;

/**
 * The effect of a SpeedBoost powerup.
 * @author Kelsey Jack 300275851
 *
 */
public class SpeedEffect implements PowerupEffect, Runnable {
	private Player p;
	@Override
	public void apply(Player p) {
		this.p = p;
		new Thread(this).start();
	}

	@Override
	public void run() {
		long originalDelay = p.getStepDelay();
		p.setStepDelay(0);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.setStepDelay(originalDelay);
	}

}
