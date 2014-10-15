package gameworld.world;

import graphics.assets.Entities;

/**
 * Represents a map that can be read to show a more detailed view
 * of the world.
 * @author Kelsey Jack 300275851
 *
 */
public class Map extends Item {

	public Map() {
		// AUTO
	}

	@Override
	public Entities asEnum() {
		return Entities.MAP;
	}

}
