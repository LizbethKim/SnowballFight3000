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
		// AUTO
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

}
