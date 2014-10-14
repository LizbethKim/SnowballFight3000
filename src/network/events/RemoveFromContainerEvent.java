package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class RemoveFromContainerEvent extends LocationEvent {
	private int index;
	private Location location;
	public RemoveFromContainerEvent(Location l, int index){
		this.index=index;
		this.location=l;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x14);
		writeLocation(out, location);
		out.write(index);
	}

}
