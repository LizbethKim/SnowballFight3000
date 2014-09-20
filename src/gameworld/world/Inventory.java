package gameworld.world;

import java.util.Collection;

/**
 * Can contain items. May be an Item itself (eg a wallet, backpack) or 
 * a StaticEntity (eg a chest). Also could be an Inventory for a player.
 * 
 * @author kelsey
 */
public interface Inventory {
	
	public void addItem(Item i);
	public boolean removeItem(Item i);
	public Collection<Item> getContents();

}
