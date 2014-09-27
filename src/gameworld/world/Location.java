package gameworld.world;

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

}
