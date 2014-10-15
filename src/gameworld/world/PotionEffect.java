package gameworld.world;

public class PotionEffect implements PowerupEffect {
	private int boost;

	public PotionEffect(int boost) {
		this.boost = boost;
	}

	@Override
	public void apply(Player p) {
		p.damage(-boost);
	}
}
