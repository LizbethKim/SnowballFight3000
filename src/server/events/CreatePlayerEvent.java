package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class CreatePlayerEvent implements UpdateEvent {
	
	private int playerID;
	private String name;
	
	public CreatePlayerEvent(int id, String name) {
		this.playerID=id;
		this.name = name;
	}
	
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x05);
		out.write(playerID);
		out.write(name.length());
		out.write(name.getBytes());
	}

}
