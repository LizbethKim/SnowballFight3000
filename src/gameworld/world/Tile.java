package gameworld.world;

import graphics.assets.Terrain;

/**
 * Represents one tile in the board.
 * @author Kelsey Jack 300275851
 *
 */
public class Tile {
	private Location coords;
	protected Terrain type;	// the image won't be used internally
	private InanimateEntity on;


	public Tile(Location c, Terrain type, InanimateEntity on) {
		this.coords = c;
		this.type = type;
		this.on = on;
	}

	public Location getCoords () {
		return coords;
	}

	public InanimateEntity getOn() {
		return on;
	}

	public void clear() {
		this.on = null;
	}

	public boolean place(InanimateEntity i) {
		if (this.on != null) {
			return false;
		}
		this.on = i;
		return true;
	}

	/**
	 * @return Whether there is an object on this tile
	 */
	public boolean isClear() {
		return on == null;
	}

	public boolean isSnow() {
		return type == Terrain.SNOW;
	}

	/**
	 * Checks whether the item on the tile will block the way through it.
	 * @return Whether moving entities could move through the tile.
	 */
	public boolean isTraversable() {
		if (on != null && !on.canMoveThrough()) {
			return false;
		}
		return true;
	}

	public Terrain getType() {
		return type;
	}
}
