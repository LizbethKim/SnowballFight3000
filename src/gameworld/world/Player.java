package gameworld.world;

import java.util.List;

import gameworld.world.Snowball.SnowballType;
import graphics.assets.Entities;

/**
 * Represents players in the world
 * @author Kelsey Jack 300275851
 */
public class Player implements Entity {
	private Location loc;
	private Direction d = Direction.NORTH;
	private Team team;
	private Inventory inventory;
	private int score = 0;
	private int health = 100;	// From 0 to 100. 0 is frozen.
	public final String name;
	private int ID;

	// Determine the speed the player can do stuff
	private long stepDelay = DEFAULT_STEP_DELAY;
	private long snowballDelay = DEFAULT_SNOWBALL_DELAY;
	private SnowballType canThrow = SnowballType.NORMAL;

	public static final int MAX_SCORE = 64998;
	public static final long DEFAULT_STEP_DELAY = 200;
	public static final long DEFAULT_SNOWBALL_DELAY = 500;


	public Player (String name, Team t, int ID, Location l) {
		this.loc = l;
		this.ID = ID;
		this.name = name;
		this.team = t;
		this.inventory = new PlayerInventory();
	}

	/**
	 * Moves a character to a new location. Only moves them if the new location is
	 * one step away, and they are not frozen.
	 * @param l The new location
	 * @return	true if they moved, false otherwise
	 */
	public boolean move(Location l) {
		if (this.isFrozen()) {
			return false;
		}
		if ( this.loc == null || (Math.abs(l.x - loc.x) == 1) != (Math.abs(l.y - loc.y) == 1)) {
			// if exactly one step in a cardinal direction has been taken
			loc = l;
			return true;
		}
		return false;
	}

	/**
	 * Damages the player the given amount. Doesn't allow the health of the player
	 * exceed 100 or drop below 0.
	 * @param amount The amount of health to take off the player
	 */
	public void damage(int amount) {
		health -= amount;
		if (health < 0) {
			health = 0;
		}
		if (health > 100) {
			health = 100;
		}
	}

	/**
	 * @param amount The amount to increment the score by
	 */
	public void incrementScore(int amount) {
		score += amount;
		if (score < 0) {
			score = 0;
		} else if (score > MAX_SCORE) {
			score = MAX_SCORE;
		}
	}

	/**
	 * @return The location the player is facing
	 */
	public Location getLocationInFrontOf() {
		return Location.locationInFrontOf(this.loc, this.d);
	}

	/**
	 * @return A list of the locations surrounding the player.
	 */
	public List<Location> getSurroundingLocations() {
		return Location.getSurroundingLocations(this.loc);
	}

	// Inventory methods

	public List<Item> getInventoryItems() {
		return this.inventory.getContents();
	}

	public List<Entities> getInventoryEnums() {
		return this.inventory.getContentsAsEnums();
	}

	public boolean removeFromInventory (Item i) {
		return this.inventory.removeItem(i);
	}

	public Item removeFromInventory (int index) {
		if (index < this.inventory.getContents().size()) {
			Item toRemove = this.inventory.getContents().get(index);
			if (inventory.removeItem(toRemove)) {
				return toRemove;
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Adds the given item to the Inventory only if it isn't their team's flag.
	 * @param i
	 * @return
	 */
	public boolean addItemToInventory (Item i) {
		if (i instanceof Flag && ((Flag)i).getTeam() == this.team) {
			return false;
		}
		return this.inventory.addItem(i);
	}

	/**
	 * @param ID
	 * @return Whether the player has a key with the given ID in their inventory.
	 */
	public boolean holdsKey(int ID) {
		for (Item i: inventory.getContents()) {
			if (i instanceof Key && ((Key)i).ID == ID) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param ID
	 * @return The index of the key with the given ID within the player's inventory
	 */
	public int getKeyIndex(int ID) {
		for (int i = 0; i < inventory.getContents().size(); i++) {
			if (inventory.getContents().get(i) instanceof Key &&
					((Key)inventory.getContents().get(i)).ID == ID) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}

	public void emptyInventory() {
		this.inventory.empty();
	}

	// GETTERS AND SETTERS
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Allows the location to be set when the player first starts the game
	 * @param l new location
	 */
	public void setLocation(Location l) {
		this.loc = l;
	}

	public Location getLocation() {
		return loc;
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

	public void setScore(int score) {
		if (score > 0 && score < MAX_SCORE) {
			this.score = score;
		}
	}

	public int getHealth() {
		return health;
	}

	public Team getTeam() {
		return team;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Direction getDirection() {
		return d;
	}

	public void setDirection(Direction d) {
		this.d = d;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setTeam(Team team) {
			this.team = team;
	}

	public long getSnowballDelay() {
		return snowballDelay;
	}

	public void setSnowballDelay(long snowballDelay) {
		this.snowballDelay = snowballDelay;
	}

	public long getStepDelay() {
		return stepDelay;
	}

	public void setStepDelay(long stepDelay) {
		this.stepDelay = stepDelay;
	}

	public SnowballType getCanThrow() {
		if (canThrow == SnowballType.FLAMING) {
			this.canThrow = SnowballType.NORMAL;
			return SnowballType.FLAMING;
		}
		return canThrow;
	}

	public void setCanThrow(SnowballType canThrow) {
		this.canThrow = canThrow;
	}

	@Override
	public Entities asEnum() {
		if (this.isFrozen()) {
			return Entities.SNOWMAN;
		}
		if (this.team == Team.RED) {
			if (this.d == Direction.NORTH) {
				return Entities.REDPLAYER_N;
			} else if (this.d== Direction.EAST) {
				return Entities.REDPLAYER_E;
			} else if (this.d == Direction.SOUTH) {
				return Entities.REDPLAYER_S;
			} else {
				return Entities.REDPLAYER_W;
			}
		} else {
			if (this.d == Direction.NORTH) {
				return Entities.BLUEPLAYER_N;
			} else if (this.d == Direction.EAST) {
				return Entities.BLUEPLAYER_E;
			} else if (this.d == Direction.SOUTH) {
				return Entities.BLUEPLAYER_S;
			} else {
				return Entities.BLUEPLAYER_W;
			}
		}
	}


}
