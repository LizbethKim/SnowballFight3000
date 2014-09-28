package server.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Direction;

<<<<<<< HEAD
public class TurnEvent extends UpdateEvent {

	public TurnEvent(int ID, Direction d) {
=======
public class TurnEvent implements UpdateEvent {

	private Direction direction;
	private int playerID;

	public TurnEvent(int playerID, Direction d) {
>>>>>>> server
		// AUTO
		this.playerID=playerID;
		this.direction=d;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x02);
		out.write(0x02);
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

}
