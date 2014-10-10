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
	public final String name;	// KTC necessary?
	private Inventory inventory;
	private Direction d = Direction.NORTH;
	private int ID;

	public Player (Team team, Location l, String name) {
		this.loc = l;
		this.team = team;
		this.name = name;
		this.inventory = new PlayerInventory();
	}

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
		if ( this.loc == null || (Math.abs(l.x - loc.x) == 1) != (Math.abs(l.y - loc.y) == 1)) {
			// if exactly one step in a cardinal direction has been taken
			loc = l;
			return true;
		}
		return false;
	}

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

	public void damage(int amount) {
		health -= amount;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void incrementScore(int amount) {
		score += amount;
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

	@Override
	public Objects asEnum() {
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

}
