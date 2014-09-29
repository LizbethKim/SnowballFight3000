package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class CreatePlayerEvent implements UpdateEvent {
	
	private int playerID;
	
	public CreatePlayerEvent(int id) {
		this.playerID=id;
	}
	
	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x05);
		out.write(playerID);
	}

}
