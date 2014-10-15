package network.events;

import gameworld.world.Bag;
import gameworld.world.Item;
import gameworld.world.Key;
import gameworld.world.Powerup;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class contains code for writing items to an OutputStream
 * @author Bryden Frizzell
 *
 */
public abstract class ItemEvent extends LocationEvent {

	/**
	 * writes an Item to an OutputStream
	 * @param out the OutputStream for data to be written to
	 * @param item the item to be written
	 * @throws IOException
	 */
	protected void writeItem(OutputStream out, Item item) throws IOException {
		out.write(item.asEnum().ordinal());
		if(item instanceof Key){
			out.write(((Key)item).ID);
			out.write(((Key)item).getDescription().length());
			out.write(((Key)item).getDescription().getBytes());
		}
		else if(item instanceof Powerup){
			out.write(((Powerup)item).getPower().ordinal());
		}
		else if(item instanceof Bag) {
			out.write(((Bag)item).size());
			for(Item i : ((Bag)item).getContents()) {
				writeItem(out, i);
			}
		}
	}

}
