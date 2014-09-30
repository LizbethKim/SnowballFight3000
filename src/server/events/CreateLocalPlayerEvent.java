package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class CreateLocalPlayerEvent implements UpdateEvent {
	int id;

	public CreateLocalPlayerEvent(int id) {
		this.id = id;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x06);
		out.write(id);
		// TODO Auto-generated method stub

	}

}
