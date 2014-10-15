package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Removes an item from a location
 * @author Bryden Frizzell
 *
 */
public class RemoveItemEvent extends LocationEvent {

	private Location location;

	/**
	 * @param location the location of the item to remove
	 */
	public RemoveItemEvent(Location l) {
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x0D);
		writeLocation(out,location);
	}

}
