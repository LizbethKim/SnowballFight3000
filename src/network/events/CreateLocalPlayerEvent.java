package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class CreateLocalPlayerEvent extends LocationEvent {
	int id;
	private Location location;

	public CreateLocalPlayerEvent(int id, Location l) {
		this.id = id;
		this.location = l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x06);
		out.write(id);
		writeLocation(out,location);
		// TODO Auto-generated method stub

	}

}
