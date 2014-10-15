package gameworld.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Area {
	List<Tile> tiles;

	public Area(){
		this.tiles = new ArrayList<Tile>();
	}

	public boolean add(Tile t) {
		return tiles.add(t);
	}

	public boolean contains(Tile t) {
		return tiles.contains(t);
	}
	
	public List<Tile> getTiles() {
		return Collections.unmodifiableList(tiles);
	}
	
	public boolean remove(Tile t){
		return tiles.remove(t);
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
