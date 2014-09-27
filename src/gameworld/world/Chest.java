package gameworld.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a chest. Currently has unlimited capacity.
 * @author Kelsey Jack 300275851
 */
public class Chest implements Inventory, StaticEntity {
	List<Item> contents;
	
	/**
	 * Constructs a chest containing the given collection of items.
	 * @param contents The items to be placed in the chest. Cannot be null.
	 */
	public Chest(Collection<Item> contents) {
		this.contents = new ArrayList<Item>(contents);
	}
	
	/**
	 * Constructs an empty chest.
	 */
	public Chest() {
		this.contents = new ArrayList<Item>();
	}
	
	@Override
	public boolean addItem(Item i) {
		this.contents.add(i);
		return true;
	}

	@Override
	public boolean removeItem(Item i) {
		return this.contents.remove(i);
	}

	@Override
	public Collection<Item> getContents() {
		return Collections.unmodifiableCollection(contents);
	}

	@Override
	public boolean canMoveThrough() {
		return false;
	}


}
