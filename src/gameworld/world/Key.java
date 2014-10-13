package gameworld.world;

import graphics.assets.Objects;

/**
 * Can lock Lockable objects. Associated with one Lockable.
 * @author Kelsey Jack 300275851
 */
public class Key extends Item {
	public final int id;

	public Key (String description, int id) {
		this.description = description;
		this.id = id;
	}


	@Override
	public Objects asEnum() {
		return Objects.KEY;
	}

}
