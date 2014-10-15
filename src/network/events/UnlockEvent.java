package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent when a door is unlocked
 * @author Bryden Frizzell
 *
 */
public class UnlockEvent extends LocationEvent {

	private Location location;

	/**
	 * @param location the location of the door which has been unlocked
	 */
	public UnlockEvent(Location l) {
		location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x18);
		writeLocation(out,location);
	}

}
