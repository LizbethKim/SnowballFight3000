package server.events;

import gameworld.world.Item;
import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class PickUpItemEvent extends ItemEvent {

	private Item item;

	public PickUpItemEvent(Item i) {
		this.item = i;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0C);
		writeItem(out,item);
	}

}
