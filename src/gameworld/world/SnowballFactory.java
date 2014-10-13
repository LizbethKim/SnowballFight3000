package gameworld.world;

import gameworld.world.Snowball.SnowballType;

public class SnowballFactory {
	public Snowball makeSnowball(Location l, Direction d, SnowballType s) {
		if (s == SnowballType.SUPER) {
			return new Snowball(l, d, 50, 1.5, s);
		} else if (s == SnowballType.ICE) {
			return new Snowball(l, d, 35, 1.5, s);
		} else {
			return new Snowball(l, d, 25, 2, s);
		}
	}

}
