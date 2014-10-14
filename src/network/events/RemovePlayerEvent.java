package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class RemovePlayerEvent implements UpdateEvent {

	private int id;

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
