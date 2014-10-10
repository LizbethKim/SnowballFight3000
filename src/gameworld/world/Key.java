package gameworld.world;

import graphics.assets.Objects;

/**
 * Can lock Lockable objects. Associated with one Lockable.
 * @author Kelsey Jack 300275851
 */
public class Key extends Item {
	// KTC more
	@Override
	public Objects asEnum() {
		return Objects.KEY;
	}

}
