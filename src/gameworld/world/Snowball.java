package gameworld.world;

import graphics.assets.Entities;

/**
 * Represents a snowball, which can damage players.
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


	public enum SnowballType{
		NORMAL, SUPER, FAST, ONE_HIT, FLAMING;
	}

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

	/**
	 * Damages the player it hits.
	 * @param p The player hit
	 */
	public boolean hit(Player p) {
		if (p.getLocation().equals(this.l)) {
			p.damage(this.damage);
			return true;
		}
		return false;
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

	@Override
	public Entities asEnum() {
		if (this.type == SnowballType.FLAMING) {
			return Entities.FIRESNOWBALL;
		}
		return Entities.SNOWBALL;
	}

	public Location getLocation() {
		return l;
	}

	/* Move forward one square. May be different to the clockTick,
	 * as it may tick multiple times before moving.
	 */
	private void moveForward() {
		l = Location.locationInFrontOf(l, d);
	}


}
