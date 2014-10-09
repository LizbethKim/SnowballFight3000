package server.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class RemoveItemEvent implements UpdateEvent {

	private Location location;

	public RemoveItemEvent(Location l) {
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0B);
		out.write(location.x);
		out.write(location.x>>8);
		out.write(location.y);
		out.write(location.y>>8);
	}

}