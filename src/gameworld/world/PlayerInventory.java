package gameworld.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents the inventory of a player. Has a limit to the number of items
 * it can carry.
 * @author Kelsey Jack 300275851
 *
 */
public class PlayerInventory implements Inventory {
	public static final int DEFAULT_LIMIT = 9;
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
	public boolean addItem(Item i) {
		if (contents.size() < itemLimit) {
			contents.add(i);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeItem(Item i) {
		return contents.remove(i);
	}

	@Override
	public Collection<Item> getContents() {
		return Collections.unmodifiableCollection(contents);
	}



}
