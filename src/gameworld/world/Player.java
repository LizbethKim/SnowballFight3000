package gameworld.world;

/**
 * Represents players in the world
 * @author kelsey
 */
public class Player implements Entity {
	private Location loc;
	private int team;	// KTC maybe change to enum?
	private int score;
	private int health;
	private String name;	// KTC necessary?
	private Inventory inventory;
	
	/**
	 * Moves a character to a new location. Only moves them if the new location is
	 * one step away. 
	 * @param l The new location
	 * @return	true if they moved, false otherwise
	 */
	public boolean move(Location l) {
		if ((Math.abs(l.x - loc.x) == 1) != (Math.abs(l.y - loc.y) == 1)) {	
			// if exactly one step in a cardinal direction has been taken
			loc = l;
			return true;
		}
		return false;
	}
	
}
