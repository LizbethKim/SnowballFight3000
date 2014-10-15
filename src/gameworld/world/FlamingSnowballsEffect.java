package gameworld.world;

import gameworld.world.Snowball.SnowballType;

public class FlamingSnowballsEffect implements PowerupEffect {

	@Override
	public void apply(Player p) {
		p.setCanThrow(SnowballType.FLAMING);
	}
}
