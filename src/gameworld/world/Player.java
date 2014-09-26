package gameworld.world;

/**
 * Represents players in the world
 * @author kelsey
 */
public class Player implements Entity {
	private Location loc;
	private int team;	// KTC maybe change to enum?
	private int score = 0;
	private int health = 100;	// From 0 to 100. 0 is frozen.
	public final String name;	// KTC necessary?
	private Inventory inventory;
	
	public Player (int team, Location l, String name) {
		this.loc = l;
		this.team = team;
		this.name = name;
		this.inventory = new PlayerInventory();
	}
	
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
	
	/**
	 * @return Whether the player has been frozen or not.
	 */
	public boolean isFrozen() {
		return health == 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getTeam() {
		return team;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void damage(int amount) {
		health -= amount;
	}
	
}
