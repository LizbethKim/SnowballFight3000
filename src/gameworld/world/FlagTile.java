package gameworld.world;

import graphics.assets.Terrain;

/**
 * A special tile - if you place the opposite team's flag on it, you win the
 * game. Doesn't allow anything else to be placed on it.
 * @author Kelsey Jack 300275851
 *
 */
public class FlagTile extends Tile {
	private Team t;

	public FlagTile(Location c, Team t) {
		super(c, Terrain.RED, null);
		if (t == Team.BLUE) {
			this.type = Terrain.BLUE;
		}
		this.t = t;
	}

	@Override
	public boolean place (InanimateEntity on) {
		if (on instanceof Flag && ((Flag)on).getTeam() == t) {
			return super.place(on);
		}
		return false;
	}

	public Team getTeam(){
		return t;
	}

}
