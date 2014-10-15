package gameworld.world;

/**
 * Special area where the players in the stored team can spawn.
 * @author Kelsey Jack 300275851
 *
 */
public class SpawnArea extends Area {
	public final Team team;

	public SpawnArea(Team t) {
		super();
		this.team = t;
	}
}
