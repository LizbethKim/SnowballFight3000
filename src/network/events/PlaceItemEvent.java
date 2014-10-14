package network.events;

import gameworld.world.Item;
import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class PlaceItemEvent extends ItemEvent {

	private Item item;
	private Location location;
	
	public PlaceItemEvent(Location loc, Item item) {
		this.item=item;
		this.location=loc;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0f);
		writeItem(out,item);
		writeLocation(out,location);
	}

}
