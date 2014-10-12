package gameworld.world;

import graphics.assets.Objects;

/**
 * Represents players in the world
 * @author Kelsey Jack 300275851
 */
public class Player implements Entity {
	private Location loc;
	private Team team;
	private int score = 0;
	private int health = 100;	// From 0 to 100. 0 is frozen.
	public final String name;
	private Inventory inventory;
	private Direction d = Direction.NORTH;
	private int ID;

	// Determine the speed the player can do stuff
	private long stepDelay = 200;
	private long snowballDelay = 500;


	public Player (String name, Team t, int ID, Location l) {
		this.loc = l;
		this.ID = ID;
		this.name = name;
		this.team = t;
		this.inventory = new PlayerInventory();
	}

	/**
	 * Moves a character to a new location. Only moves them if the new location is
	 * one step away.
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

	@Override
	public Objects asEnum() {
		if (this.isFrozen()) {
			return Objects.SNOWMAN;
		}
		if (this.team == Team.RED) {
			if (this.d == Direction.NORTH) {
				return Objects.REDPLAYER_N;
			} else if (this.d== Direction.EAST) {
				return Objects.REDPLAYER_E;
			} else if (this.d == Direction.SOUTH) {
				return Objects.REDPLAYER_S;
			} else {
				return Objects.REDPLAYER_W;
			}
		} else {
			if (this.d == Direction.NORTH) {
				return Objects.BLUEPLAYER_N;
			} else if (this.d == Direction.EAST) {
				return Objects.BLUEPLAYER_E;
			} else if (this.d == Direction.SOUTH) {
				return Objects.BLUEPLAYER_S;
			} else {
				return Objects.BLUEPLAYER_W;
			}
		}
	}

	public void damage(int amount) {
		health -= amount;
		if (health < 0) {
			health = 0;
		}
		if (health > 100) {
			health = 100;
		}
	}

	public void incrementScore(int amount) {
		score += amount;
		if (score < 0) {
			score = 0;
		}
	}

	public Location getLocationInFrontOf() {
		return Location.locationInFrontOf(this.loc, this.d);
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

}
