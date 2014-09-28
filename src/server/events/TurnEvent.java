package server.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Direction;

public class TurnEvent extends UpdateEvent {

	public TurnEvent(int ID, Direction d) {
		// AUTO
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

}
