package gameworld.world;

import graphics.assets.Terrain;

/**
 * Represents a tile that is not part of the world.
 * @author Kelsey Jack 300275851
 *
 */
public class NullTile extends Tile {

	public NullTile(Location c) {
		super(c, Terrain.NULLTILE, null);
	}

	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public boolean place(InanimateEntity i) {
		return false;
	}


}
