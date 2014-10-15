package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * removes a player from the client
 * sent when a player disconnects
 * @author Bryden Frizzell
 *
 */
public class RemovePlayerEvent implements UpdateEvent {

	private int id;

	/**
	 * @param id the id of the player to be removed
	 */
	public RemovePlayerEvent(int id) {
		this.id = id;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x09);
		out.write(id);
	}

}
