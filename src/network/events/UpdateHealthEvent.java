package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent when the local player on a client's health changes
 * @author Bryden Frizzell
 *
 */
public class UpdateHealthEvent implements UpdateEvent {
	private int health;

	/**
	 * @param health the new health value for the client's player
	 */
	public UpdateHealthEvent(int health) {
		this.health=health;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x0A);
		out.write(health);
	}

}
