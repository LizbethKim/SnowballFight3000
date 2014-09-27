package gameworld.world;

/**
 * Represents a projectile, eg. a snowball
 * @author Kelsey Jack 300275851
 */
public interface Projectile extends Entity {
	/**
	 * Damages the player it hits. KTC right now friendly fire can hurt
	 * @param p The player hit
	 */
	public void hit (Player p);
	
	/**
	 * Will move the projectile one tick's worth.
	 */
	public void clockTick();
}
