package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Removes an item from a container
 * @author Bryden Frizzell
 *
 */
public class RemoveFromContainerEvent extends LocationEvent {
	private int index;
	private Location location;

	/**
	 * @param location the location of the container
	 * @param index the index in the container of the item
	 */
	public RemoveFromContainerEvent(Location l, int index){
		this.index=index;
		this.location=l;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x14);
		writeLocation(out, location);
		out.write(index);
	}

}
