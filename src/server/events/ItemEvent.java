package server.events;

import gameworld.world.Item;
import gameworld.world.Key;
import gameworld.world.Powerup;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ItemEvent implements UpdateEvent {

	protected void writeItem(OutputStream out, Item item) throws IOException {
		out.write(item.asEnum().ordinal());
		if(item instanceof Key){
			out.write(((Key)item).id);
		}
		else if(item instanceof Powerup){
			out.write(((Powerup)item).getPower().ordinal());
		}
	}

}
