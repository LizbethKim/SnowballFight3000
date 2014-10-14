package network.events;

import java.io.IOException;
import java.io.OutputStream;

public class UnfreezePlayerEvent implements UpdateEvent {
	private int id;
	public UnfreezePlayerEvent(int id) {
		this.id = id;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x19);
		out.write(id);
		
	}

}
