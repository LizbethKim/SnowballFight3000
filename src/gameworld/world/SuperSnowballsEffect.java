package gameworld.world;

public class SuperSnowballsEffect implements PowerupEffect {
	private Player p;
	// KTC to do

	@Override
	public void apply(Player p) {
		this.p = p;
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		p.getDirection();
		// TODO Auto-generated method stub

	}

}
