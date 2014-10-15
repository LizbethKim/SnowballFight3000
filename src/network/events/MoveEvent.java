package network.events;

import java.io.IOException;
import java.io.OutputStream;

import gameworld.world.Location;

/**
 * Sends a position update for a player to a client
 * @author Bryden Frizzell
 *
 */
public class MoveEvent extends LocationEvent {
	int playerID;
	Location location;

	/**
	 * @param playerID the id of the player whose position is being updated
	 * @param location the new location for the player
	 */
	public MoveEvent(int playerID, Location l) {
		this.playerID=playerID;
		this.location=l;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x01);
		out.write(playerID);
		writeLocation(out,location);
	}

}
