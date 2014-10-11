package gameworld.world;

public class SpeedEffect implements PowerupEffect {
	private Player p;
	@Override
	public void apply(Player p) {
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
