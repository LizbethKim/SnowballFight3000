package server.events;

import gameworld.world.Location;
import gameworld.world.Team;

import java.io.IOException;
import java.io.OutputStream;

public class CreatePlayerEvent implements UpdateEvent {

	private int playerID;
	private String name;
	private Location location;
	private Team team;

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
		out.write(location.x);
		out.write(location.x>>8);
		out.write(location.y);
		out.write(location.y>>8);
		out.write(team.ordinal());
	}

}
