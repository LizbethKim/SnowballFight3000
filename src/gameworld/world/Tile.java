package gameworld.world;

public class Tile {
	private Location coords;
	// private Terraintype-thingy
	private InanimateEntity on;
	
	public Tile(Location l, InanimateEntity on) {
		this.coords = l;
		this.on = on;
	}

	public Location getCoords () {
		return coords;
	}
	
	public InanimateEntity getOn() {
		return on;
	}
	
	public Item removeOn() {	// TODO perhaps exceptions, not nulls
		Item toReturn = null;
		if (this.on instanceof Item) {
			toReturn = (Item) on;
			this.on = null;
		}
		return toReturn;
	}
	
	public boolean place(Item i) {
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
		
	/**
	 * @return Whether moving entities could move through the tile.
	 */
	public boolean isTraversable() {
		// TODO
		return false; 	// something to do with what sort of object is on it
	}
}
