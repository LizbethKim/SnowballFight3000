package gameworld.world;

import gameworld.world.SnowballFactory.SnowballType;

/**
 * Represents a projectile, eg a snowball.
 * @author Kelsey Jack 300275851
 *
 */
public class Snowball implements Entity {
	
	public final Direction d;
	private Location l;
	public final int damage;
	public final double ticksPerSquare;
	private double ticksSinceLast = 0;
	public final SnowballType type;
	
	/**
	 * Creates a new snowball and starts it one square in 
	 * direction d from where it was thrown.
	 * @param thrownAt
	 * @param d
	 */
	public Snowball(Location thrownAt, Direction d, int damage, double ticksPerSquare, SnowballType t) {
		this.d = d;
		this.l = thrownAt;
		this.moveForward();
		this.damage = damage;
		this.ticksPerSquare = ticksPerSquare;
		this.type = t;
	}

	/* Move forward one square. May be different to the clockTick,
	 * as it may tick multiple times before moving.
	 */	 
	private void moveForward() {
		if (d == Direction.NORTH) {
			l = new Location(l.x - 1, l.y);
		} else if (d == Direction.SOUTH) {
			l = new Location(l.x + 1, l.y);
		} else if (d == Direction.EAST) {
			l = new Location(l.x, l.y + 1);
		} else {
			l = new Location(l.x, l.y - 1);
		}
	}

	
	/**
	 * Damages the player it hits. KTC right now friendly fire can hurt
	 * @param p The player hit
	 */
	public void hit(Player p) {
		p.damage(this.damage);
	}

	/**
	 * Will move the projectile one tick's worth.
	 */
	public void clockTick() {
		ticksSinceLast += 1;
		while (ticksSinceLast >= ticksPerSquare) {
			moveForward();
			ticksSinceLast -= ticksPerSquare;
		}
	}

}
