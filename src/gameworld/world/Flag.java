package gameworld.world;

import graphics.assets.Objects;

public class Flag extends Item {
	private Team team;

	public Flag(Team t) {
		this.team = t;
		this.description = "The " + (this.team == Team.RED ? "red": "blue")
				+ " team's flag!";

	}

	@Override
	public boolean canMoveThrough () {
		return false;
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
