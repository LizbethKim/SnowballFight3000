package gameworld.world;

import graphics.assets.Entities;

/**
 * The flag that must be captured to win the game.
 * @author Kelsey Jack 300275851
 *
 */
public class Flag extends Item {
	private Team team;

	/**
	 * @param t The team who the flag belongs to.
	 */
	public Flag(Team t) {
		this.team = t;
		this.description = "The " + (this.team == Team.RED ? "red": "blue")
				+ " team's flag!";

	}

	public Team getTeam() {
		return team;
	}

	@Override
	public boolean canMoveThrough () {
		return false;
	}

	@Override
	public Entities asEnum() {
		if (this.team == Team.RED) {
			return Entities.REDFLAG;
		} else {
			return Entities.BLUEFLAG;
		}
	}

}
