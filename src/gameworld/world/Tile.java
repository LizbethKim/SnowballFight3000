package gameworld.world;

public class Tile {
	private Location coords;
	// private Terraintype-thingy
	private InanimateEntity on;
	
	public Tile(Location l) {
		this.coords = l;
	}

	public Location getCoords () {
		return coords;
	}
	
	public InanimateEntity getOn() {
		return on;
	}
	
	/**
	 * @return Whether there is an object on this tile
	 */
	public boolean isClear() {
		return on == null;
	}
		
	/**
	 * @return Whether moving entities could move through the tile.
	 */
	public boolean isTraversable() {
		// TODO
		return false; 	// something to do with what sort of object is on it
	}
}
