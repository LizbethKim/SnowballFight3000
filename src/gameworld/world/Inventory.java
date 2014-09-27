package gameworld.world;

import java.util.Collection;

/**
 * Can contain items. May be an Item itself (eg a wallet, backpack) or 
 * a StaticEntity (eg a chest). Also could be an Inventory for a player.
 * 
 * @author Kelsey Jack 300275851
 */
public interface Inventory {
	
	/**
	 * Attempts to add i to the inventory. The inventory may be at capacity 
	 * and not allow the addition though.
	 * @param i Item to be added
	 * @return True if there was space and the item was added, false otherwise.
	 */
	public boolean addItem(Item i);
	
	
	/**
	 * Removes i from the inventory if it contains i. 
	 * @param i
	 * @return True for successful removal, false if the item wasn't present.
	 */
	public boolean removeItem(Item i);
	public Collection<Item> getContents();

}
