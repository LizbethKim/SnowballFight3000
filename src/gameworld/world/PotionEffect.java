package gameworld.world;

/**
 * The effect of a Potion powerup.
 * @author Kelsey Jack 300275851
 *
 */
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
