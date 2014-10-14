package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateScoreEvent implements UpdateEvent {

	private int score;

	public UpdateScoreEvent(int score){
		this.score=score;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x16);
		out.write(score);
		out.write(score>>8);
	}

}
