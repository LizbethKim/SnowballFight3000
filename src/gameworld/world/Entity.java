package gameworld.world;

import graphics.assets.Entities;

/**
 * Anything that can be within the world.
 * @author Kelsey Jack 300275851
 *
 */
public interface Entity {
	/**
	 * @return The enum representation of the item (immutable with no extra information)
	 */
	public Entities asEnum();
}
