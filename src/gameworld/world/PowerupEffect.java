package gameworld.world;

/**
 * A strategy for a powerup that applies the effect of the powerup to the given plaeyr.
 * @author Kelsey Jack 300275851
 *
 */
public interface PowerupEffect {
	/**
	 * Applies the effect to the given player
	 * @param p
	 */
	public void apply (Player p);
}
