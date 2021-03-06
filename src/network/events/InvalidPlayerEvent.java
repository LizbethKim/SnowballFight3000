package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent if the player has given invalid player information when joining
 * @author Bryden Frizzell
 *
 */
public class InvalidPlayerEvent implements UpdateEvent {

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x11);
	}

}
