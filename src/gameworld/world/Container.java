package gameworld.world;

import java.util.Collection;

/**
 * Can contain items. May be an Item itself (eg a wallet, backpack) or 
 * a StaticEntity (eg a chest).
 * 
 * @author kelsey
 */
public interface Container extends InanimateEntity {
	
	public void addItem(Item i);
	public void removeItem(Item i);
	public Collection<Item> getContents();

}
