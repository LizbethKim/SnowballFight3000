package server.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class UnlockEvent extends LocationEvent {

	private Location location;

	public UnlockEvent(Location l) {
		location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x18);
		writeLocation(out,location);
	}

}
