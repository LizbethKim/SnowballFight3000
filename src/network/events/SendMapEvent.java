package network.events;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Sent to a player when they request the map
 * @author Bryden Frizzell
 *
 */
public class SendMapEvent implements UpdateEvent {

	private byte[] data;

	/**
	 * @param data an array of byte containing the map file data
	 */
	public SendMapEvent(byte[] data) {
		this.data = data;
		System.out.println("CREATED");
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {

		out.write(0x07);
		out.write(data.length);
		out.write(data.length>>8);
		out.write(data.length>>16);
		out.write(data.length>>24);
		for(int i=0;i<data.length;i++) {
			out.write(data[i]);
		}
	}

}
