package network.events;

import gameworld.world.Item;
import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

/**
 * adds an item to a players inventory
 * @author Bryden Frizzell
 *
 */
public class PickUpItemEvent extends ItemEvent {

	private Item item;

	/**
	 * @param item the item to be added to the players inventory
	 */
	public PickUpItemEvent(Item i) {
		this.item = i;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x0C);
		writeItem(out,item);
	}

}
