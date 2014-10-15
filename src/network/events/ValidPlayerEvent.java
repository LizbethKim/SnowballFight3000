package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * sent if a players selected name and team is valid
 * @author Bryden Frizzell
 *
 */
public class ValidPlayerEvent implements UpdateEvent {

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x12);
	}

}
