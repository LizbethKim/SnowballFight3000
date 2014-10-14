package gameworld.world;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a coordinate in the map
 * @author Kelsey Jack 300275851
 */
public class Location {
	public final int x;	// The bigger the x, the more east we are
	public final int y;	// The bigger the y, the further south (EK, does that make sense?)

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public static Location locationInFrontOf (Location l, Direction d) {
		if (d == Direction.NORTH) {
			return new Location (l.x, l.y - 1);
		} else if (d == Direction.SOUTH) {
			return new Location (l.x, l.y + 1);
		} else if (d == Direction.WEST) {
			return new Location (l.x - 1, l.y);
		} else {
			return new Location (l.x + 1, l.y);
		}
	}
	
	public static List<Location> getSurroundingLocations(Location l) {
		List<Location> ans = new ArrayList<Location>();
		ans.add(new Location (l.x + 1, l.y));
		ans.add(new Location (l.x - 1, l.y));
		ans.add(new Location (l.x, l.y + 1));
		ans.add(new Location (l.x, l.y - 1));
		ans.add(new Location (l.x + 1, l.y + 1));
		ans.add(new Location (l.x + 1, l.y - 1));
		ans.add(new Location (l.x - 1, l.y + 1));
		ans.add(new Location (l.x - 1, l.y - 1));
		ans.add(new Location (l.x + 2, l.y));
		ans.add(new Location (l.x + 2, l.y + 1));
		ans.add(new Location (l.x + 2, l.y - 1));
		ans.add(new Location (l.x - 2, l.y));
		ans.add(new Location (l.x - 2, l.y + 1));
		ans.add(new Location (l.x - 2, l.y - 1));
		ans.add(new Location (l.x, l.y - 2));
		ans.add(new Location (l.x + 1, l.y - 2));
		ans.add(new Location (l.x - 1, l.y - 2));
		ans.add(new Location (l.x, l.y - 2));
		ans.add(new Location (l.x + 1, l.y - 2));
		ans.add(new Location (l.x - 1, l.y - 2));
		return ans;
	}
}

