package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Freezes the player specified by the id
 * @author Bryden Frizzell
 *
 */
public class FreezePlayerEvent implements UpdateEvent {
	private int id;
	/**
	 * @param id the id of the player to be frozen
	 */
	public FreezePlayerEvent(int id) {
		this.id = id;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x0B);
		out.write(id);

	}

}
