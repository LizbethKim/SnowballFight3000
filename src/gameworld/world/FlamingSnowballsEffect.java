package gameworld.world;

import gameworld.world.Snowball.SnowballType;

/**
 * The effect of a FlamingSnowballs powerup.
 * @author Kelsey Jack 300275851
 *
 */
public class FlamingSnowballsEffect implements PowerupEffect {

	@Override
	public void apply(Player p) {
		p.setCanThrow(SnowballType.FLAMING);
	}
}
