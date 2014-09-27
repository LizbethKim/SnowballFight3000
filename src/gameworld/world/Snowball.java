package gameworld.world;

/**
 * Represents a normal snowball.
 * @author Kelsey Jack 300275851
 *
 */
public class Snowball implements Projectile{
	public final Direction d;
	private Location l;
	public static final int DAMAGE = 10;
	// KTC moves per tick modulo arithmetic
	
	/**
	 * Creates a new snowball and starts it one square in 
	 * direction d from where it was thrown.
	 * @param thrownAt
	 * @param d
	 */
	public Snowball(Location thrownAt, Direction d) {
		this.d = d;
		this.l = thrownAt;
		this.moveForward();

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

	@Override
	public void hit(Player p) {
		p.damage(Snowball.DAMAGE);
	}

	@Override
	public void clockTick() {
		moveForward();
	}

}
