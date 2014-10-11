package gameworld.world;

import graphics.assets.Objects;

/**
 * "Magic" items. May have some effect on the player who uses it
 * and/or the player it's used on.
 * KTC Use the strategy pattern. 
 * @author Kelsey Jack 300275851
 *
 */
public class Powerup extends Item {
	protected PowerupEffect effect;
	
	public Powerup(String description) {
		this.description = description;
	}
	
	public Powerup() {
		this.description = "";
	}
	
	public void use (Player p) {
		effect.apply(p);
	}

	@Override
	public Objects asEnum() {
		return Objects.POWERUP;
	}

}
