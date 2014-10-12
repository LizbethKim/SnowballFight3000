package server.events;

import java.io.IOException;
import java.io.OutputStream;

public class RemoveFromInventoryEvent implements UpdateEvent {
	private int index;
	public RemoveFromInventoryEvent(int index){
		this.index=index;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x10);
		out.write(index);
	}

}
