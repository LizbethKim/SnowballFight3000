package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateHeathEvent implements UpdateEvent {
	private int health;

	public UpdateHeathEvent(int health) {
		this.health=health;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0A);
		out.write(health);
	}

}
