package gameworld.world;

import java.util.Set;

public class Area {
	Set<Tile> tiles;

	public boolean contains(Tile t) {
		return tiles.contains(t);
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
