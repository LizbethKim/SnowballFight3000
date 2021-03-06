package gameworld.world;

import gameworld.world.Snowball.SnowballType;

/**
 * Factory to create snowballs with the given values
 * @author Kelsey Jack 300275851
 *
 */
public class SnowballFactory {
	public Snowball makeSnowball(Location l, Direction d, SnowballType s) {
		if (s == SnowballType.SUPER) {
			return new Snowball(l, d, 50, 2, s);
		} else if (s == SnowballType.FAST) {
			return new Snowball(l, d, 35, 1, s);
		} else if (s == SnowballType.ONE_HIT) {
			return new Snowball(l, d, 100, 2, s);
		} else if (s == SnowballType.FLAMING) {
			return new Snowball(l, d, 75, 2, s);
		} else {
			return new Snowball(l, d, 25, 2, s);
		}
	}
}
