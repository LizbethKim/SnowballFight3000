package gameworld.world;

import graphics.assets.Entities;

/**
 * Can lock Lockable objects. Associated with any lockable with the same ID.
 * @author Kelsey Jack 300275851
 */
public class Key extends Item {
	public final int ID;

	/**
	 * @param description
	 * @param ID The ID of the Lockable(s) this key opens.
	 */
	public Key (String description, int ID) {
		this.description = description;
		this.ID = ID;
	}


	@Override
	public Entities asEnum() {
		return Entities.KEY;
	}

}
