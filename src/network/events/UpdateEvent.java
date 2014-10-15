package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An UpdateEvent is an event that can be queued to be
 * sent to a player. UpdateEvents contain code for writing
 * them to an OutputStream.
 * @author Bryden Frizzell
 *
 */
public interface UpdateEvent {
	/**
	 * Writes this event to an OutputStream
	 * @param out the OutputStream to be written to
	 * @throws IOException
	 */
	public abstract void writeTo(OutputStream out) throws IOException;
}
