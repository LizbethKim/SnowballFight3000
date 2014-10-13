package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateTimeEvent implements UpdateEvent {

	private int time;

	public UpdateTimeEvent(int time) {
		this.time = time;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x17);
		out.write(time);
	}

}
