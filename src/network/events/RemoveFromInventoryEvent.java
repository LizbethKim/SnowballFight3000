package network.events;


import java.io.IOException;
import java.io.OutputStream;

/**
 * removes an item from the players inventory
 * @author Bryden Frizzell
 *
 */
public class RemoveFromInventoryEvent implements UpdateEvent {
	private int index;

	/**
	 * @param index the index of the item in the players inventory to remove
	 */
	public RemoveFromInventoryEvent(int index){
		this.index=index;
	}
	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(0x13);
		out.write(index);
	}

}
