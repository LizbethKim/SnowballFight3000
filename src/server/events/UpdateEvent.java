package server.events;

import java.io.IOException;
import java.io.OutputStream;

public abstract class UpdateEvent {
	public abstract void writeTo(OutputStream out) throws IOException;
}
