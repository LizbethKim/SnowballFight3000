package gameworld.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerInventory implements Inventory {
	public static final int DEFAULT_LIMIT = 15; // TODO change perhaps 
	private int itemLimit;
	private List<Item> contents;
	
	/**
	 * Creates an empty inventory
	 */
	public PlayerInventory() {
		this.contents = new ArrayList<Item>();
	}

	/**
	 * Creates an inventory containing the items passed in.
	 * @param contents The items for the inventory to initially contain.
	 */
	public PlayerInventory(Collection<Item> contents) {
		this.contents = new ArrayList<Item>(contents);
	}
	
	/**
	 * Allows the limit on the number of items to be changed.
	 * @param limit The new item limit
	 */
	public void setLimit(int limit) {
		this.itemLimit = limit;
	}
	
	public void resetLimit() {
		this.itemLimit = PlayerInventory.DEFAULT_LIMIT;
	}
	
	@Override
	public void addItem(Item i) {
		contents.add(i);
	}

	@Override
	public boolean removeItem(Item i) {
		return contents.remove(i);
	}

	@Override
	public Collection<Item> getContents() {
		return contents;
	}

	
	
}