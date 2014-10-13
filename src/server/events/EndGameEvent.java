package server.events;

import gameworld.world.Team;

import java.io.IOException;
import java.io.OutputStream;

public class EndGameEvent implements UpdateEvent {

	Team winningTeam;
	
	public EndGameEvent(Team winners) {
		winningTeam = winners;
	}
	
	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x15);
		out.write(winningTeam.ordinal());
	}

}
