package gameworld.world;

/**
 * Items can be picked up and moved around by players.
 * Inventories can contain items, but not other entities.
 * @author Kelsey Jack 300275851
 *
 */
public abstract class Item implements InanimateEntity{
//	private Tile on;			KTC necessary?
//	private Inventory in;
//	private boolean inInventory; 	// indicates whether the item is being carried or is in the map

	public boolean canMoveThrough () {
		return true;
	}
}
