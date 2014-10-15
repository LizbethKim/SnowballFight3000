package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent when a player is unfrozen
 * @author Bryden Frizzell
 *
 */
public class UnfreezePlayerEvent implements UpdateEvent {
	private int id;

	/**
	 * @param id the id of the player who has been unfrozen
	 */
	public UnfreezePlayerEvent(int id) {
		this.id = id;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x19);
		out.write(id);

	}

}
