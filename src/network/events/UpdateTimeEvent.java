package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * sent when the time needs to be updated
 * @author Bryden Frizzell
 *
 */
public class UpdateTimeEvent implements UpdateEvent {

	private int time;

	/**
	 * @param time the new time value
	 */
	public UpdateTimeEvent(int time) {
		this.time = time;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x17);
		out.write(time);
	}

}
