package server.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class RemoveItemEvent extends LocationEvent {

	private Location location;

	public RemoveItemEvent(Location l) {
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0B);
		writeLocation(out,location);
	}

}
