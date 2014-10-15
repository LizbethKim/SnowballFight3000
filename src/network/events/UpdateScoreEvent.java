package network.events;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sent when a client needs it's score updated
 * @author Bryden Frizzell
 *
 */
public class UpdateScoreEvent implements UpdateEvent {

	private int score;

	/**
	 * @param score the new score value for the client
	 */
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
