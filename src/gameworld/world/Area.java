package gameworld.world;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Area {
	Set<Tile> tiles;

	public Area(){
		this.tiles = new HashSet<Tile>();
	}

	public boolean add(Tile t) {
		return tiles.add(t);
	}

	public boolean contains(Tile t) {
		return tiles.contains(t);
	}
	
	public Set<Tile> getTiles() {
		return Collections.unmodifiableSet(tiles);
	}

	public boolean containsLoc(Location l) {
		for (Tile t : tiles) {
			if (t.getCoords().equals(l)) {
				return true;
			}
		}
		return false;
	}
}
