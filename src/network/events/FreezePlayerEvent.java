package network.events;

import java.io.IOException;
import java.io.OutputStream;

public class FreezePlayerEvent implements UpdateEvent {
	private int id;
	public FreezePlayerEvent(int id) {
		this.id = id;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x0B);
		out.write(id);
		
	}

}
