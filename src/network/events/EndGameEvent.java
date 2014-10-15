package network.events;

import gameworld.world.Team;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent to a player when the game has ended
 * @author Bryden Frizzell
 *
 */
public class EndGameEvent implements UpdateEvent {

	private Team winningTeam;

	/**
	 * @param winners the team that won
	 */
	public EndGameEvent(Team winners) {
		winningTeam = winners;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x17);
		out.write(winningTeam.ordinal());
	}

}
