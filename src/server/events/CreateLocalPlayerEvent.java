package server.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class CreateLocalPlayerEvent implements UpdateEvent {
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
		out.write(location.x);
		out.write(location.x>>8);
		out.write(location.y);
		out.write(location.y>>8);
		// TODO Auto-generated method stub

	}

}
