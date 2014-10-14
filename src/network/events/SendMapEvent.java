package server.events;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class SendMapEvent implements UpdateEvent {

	private byte[] data;

	public SendMapEvent(byte[] data) {
		this.data = data;
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
