package network.events;

import java.io.IOException;
import java.io.OutputStream;

public interface UpdateEvent {
	public abstract void writeTo(OutputStream out) throws IOException;
}
