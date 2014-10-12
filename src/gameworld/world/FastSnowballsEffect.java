package gameworld.world;

public class FastSnowballsEffect implements PowerupEffect {
	private Player p;
	// KTC to do

	@Override
	public void apply(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		p.getDirection();
		// TODO Auto-generated method stub

	}

}
