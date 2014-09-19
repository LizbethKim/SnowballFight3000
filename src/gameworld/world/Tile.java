package gameworld.world;

public class Tile {
	private Location coords;
	// private Terraintype-thingy
	
	public Tile(Location l) {
		this.coords = l;
	}

	public Location getCoords () {
		return coords;
	}
}
