package gameworld.world;

import graphics.assets.Terrain;

public class FlagTile extends Tile {
	private Team t;

	public FlagTile(Location c, Terrain type, Team t) {
		super(c, type, null);
		this.t = t;
	}

	@Override
	public boolean place (InanimateEntity on) {
		if (on instanceof Flag && ((Flag)on).getTeam() == t) {
			// KTC win the game?
			return super.place(on);
		}
		return false;
	}

	public Team getTeam(){
		return t;
	}

}
