package gameworld.world;

import gameworld.world.Snowball.SnowballType;

/**
 * The effect of a FastSnowballPowerup.
 * @author Kelsey Jack 300275851
 *
 */
public class FastSnowballsEffect implements PowerupEffect, Runnable {
	private Player p;

	@Override
	public void apply(Player p) {
		this.p = p;
		new Thread(this).start();
	}

	@Override
	public void run() {
		p.setCanThrow(SnowballType.FAST);
		try{
			Thread.sleep(30000);
		} catch(InterruptedException e) {

		}
		p.setCanThrow(SnowballType.NORMAL);
	}

}
