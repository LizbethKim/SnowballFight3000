package network.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Direction;

/**
 * sent to the client when a player turns
 * @author Bryden Frizzell
 *
 */
public class TurnEvent implements UpdateEvent {

	private Direction direction;
	private int playerID;

	/**
	 * @param playerID the id of the player who is turning
	 * @param direction the new direction of the player
	 */
	public TurnEvent(int playerID, Direction d) {
		// AUTO
		this.playerID=playerID;
		this.direction=d;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x02);
		out.write(playerID);
		out.write(direction.ordinal());
	}

}
