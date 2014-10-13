package gameworld.world;

import gameworld.world.Snowball.SnowballType;

public class FastSnowballsEffect implements PowerupEffect {
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
