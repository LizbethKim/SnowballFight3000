package network.events;

import java.io.IOException;
import java.io.OutputStream;

public class ValidPlayerEvent implements UpdateEvent {

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x12);
	}

}
