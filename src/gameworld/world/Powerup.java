package gameworld.world;

import graphics.assets.Objects;

/**
 * "Magic" items. May have some effect on the player who uses it
 * and/or the player it's used on.
 * Uses the strategy pattern. KTC
 * @author Kelsey Jack 300275851
 *
 */
public class Powerup extends Item {
	
	public Powerup(String description) {
		this.description = description;
	}
	
	public Powerup() {
		this.description = "";
	}

	@Override
	public Objects asEnum() {
		return Objects.POWERUP;
	}

}
