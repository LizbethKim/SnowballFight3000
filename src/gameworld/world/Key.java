package gameworld.world;

import graphics.assets.Objects;

/**
 * Can lock Lockable objects. Associated with one Lockable.
 * @author Kelsey Jack 300275851
 */
public class Key extends Item {
	// KTC more
	public Key (String description) {
		this.description = description;
	}
	
	public Key() {
		this.description = "A key, but to what?";
	}
	
	@Override
	public Objects asEnum() {
		return Objects.KEY;
	}

}
