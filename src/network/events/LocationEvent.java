package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public abstract class LocationEvent implements UpdateEvent {
	protected void writeLocation(OutputStream out, Location location) throws IOException {
		out.write(location.x);
		out.write(location.x>>8);
		out.write(location.y);
		out.write(location.y>>8);
	}
}
