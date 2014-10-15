package gameworld.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents one area on the board. Stores the tiles in that area.
 * @author Kelsey Jack 300275851
 *
 */
public class Area {
	List<Tile> tiles;

	/**
	 * Creates an empty area
	 */
	public Area(){
		this.tiles = new ArrayList<Tile>();
	}

	/**
	 * Adds the given tile into the area.
	 * @param t
	 * @return Whether the tile was added
	 */
	public boolean add(Tile t) {
		return tiles.add(t);
	}

	/**
	 * @param t
	 * @return Whether the given tile is in this area
	 */
	public boolean contains(Tile t) {
		return tiles.contains(t);
	}

	/**
	 * Returns the tiles in the area in an unmodifiable list
	 * @return
	 */
	public List<Tile> getTiles() {
		return Collections.unmodifiableList(tiles);
	}

	/**
	 * @param l
	 * @return Whether the tile at the given location is in this area
	 */
	public boolean containsLoc(Location l) {
		for (Tile t : tiles) {
			if (t.getCoords().equals(l)) {
				return true;
			}
		}
		return false;
	}
}
