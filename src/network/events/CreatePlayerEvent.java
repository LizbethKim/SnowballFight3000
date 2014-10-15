package network.events;

import gameworld.world.Location;
import gameworld.world.Team;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Event for creating a remote player on a client
 * @author Bryden Frizzell
 *
 */
public class CreatePlayerEvent extends LocationEvent {

	private int playerID;
	private String name;
	private Location location;
	private Team team;

	/**
	 * @param id the id of the player to be created
	 * @param name the name of the player to be created
	 * @param location the location of the player to be created
	 * @param team the team of the player to be created
	 */
	public CreatePlayerEvent(int id, String name, Location l, Team team) {
		this.playerID=id;
		this.name = name;
		this.location=l;
		this.team=team;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x05);
		out.write(playerID);
		out.write(name.length());
		out.write(name.getBytes());
		writeLocation(out,location);
		out.write(team.ordinal());
	}

}
