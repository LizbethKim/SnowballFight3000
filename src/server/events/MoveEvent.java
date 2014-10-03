package server.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Location;

public class MoveEvent implements UpdateEvent {
	int playerID;
	Location location;

	public MoveEvent(int playerID, Location l) {
		this.playerID=playerID;
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Writing out moveEvent to " + location.x + " " + location.y);
		out.write(0x01);
		out.write(playerID);
		System.out.println(location);
		out.write(location.x);
		out.write(location.x>>8);
		out.write(location.y);
		out.write(location.y>>8);
	}

}
