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

	/**
	 * Places the given InanimateEntity on the tile if it's clear.
	 * @param i
	 * @return Whether the InanimateEntity was placed
	 */
	public boolean place(InanimateEntity i) {
		if (this.on != null) {
			return false;
		}
		this.on = i;
		return true;
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

	/**
	 * @return Whether there is an object on this tile
	 */
	public boolean isClear() {
		return on == null;
	}

	// Getters and setters

	public boolean isSnow() {
		return type == Terrain.SNOW;
	}

	public Terrain getType() {
		return type;
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
}
