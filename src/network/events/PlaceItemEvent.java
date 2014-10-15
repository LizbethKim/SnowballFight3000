package network.events;

import gameworld.world.Item;
import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Adds an item at a location
 * @author Bryden Frizzell
 *
 */
public class PlaceItemEvent extends ItemEvent {

	private Item item;
	private Location location;

	/**
	 * @param location the location the item will be added at
	 * @param item the item to be added
	 */
	public PlaceItemEvent(Location loc, Item item) {
		this.item=item;
		this.location=loc;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x0f);
		writeItem(out,item);
		writeLocation(out,location);
	}

}
