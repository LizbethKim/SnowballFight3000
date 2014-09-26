package gameworld.world;

public abstract class Item implements InanimateEntity{
	private Tile on;
	private Inventory in;
	private boolean inInventory; 	// indicates whether the item is being carried or is in the map
	
	public boolean canMoveThrough () {
		return true;
	}
}
