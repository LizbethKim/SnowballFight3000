package server.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Location;

public class MoveEvent extends LocationEvent {
	int playerID;
	Location location;

	public MoveEvent(int playerID, Location l) {
		this.playerID=playerID;
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x01);
		out.write(playerID);
		writeLocation(out,location);
	}

}
