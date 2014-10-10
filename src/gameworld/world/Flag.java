package gameworld.world;

import graphics.assets.Objects;

public class Flag extends Item {
	private Team team;
	
	public Flag(Team t) {
		this.team = t;
	}

	@Override
	public Objects asEnum() {
		if (this.team == Team.RED) {
			return Objects.REDFLAG;
		} else {
			return Objects.BLUEFLAG;
		}
	}

}
