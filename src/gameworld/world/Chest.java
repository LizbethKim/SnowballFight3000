package gameworld.world;

import graphics.assets.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a chest. Currently has unlimited capacity.
 * @author Kelsey Jack 300275851
 */
public class Chest extends Furniture implements Inventory {
	private List<Item> contents;
	private final int itemLimit = 9;

	/**
	 * Constructs a chest containing the given collection of items.
	 * @param contents The items to be placed in the chest. Cannot be null.
	 */
	public Chest(String description, Collection<Item> contents) {
		super(description, Objects.CHEST);
		this.contents = new ArrayList<Item>(contents);
	}

	/**
	 * Constructs an empty chest.
	 */
	public Chest(String description) {
		super(description, Objects.CHEST);
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
	public List<Item> getContents() {
		return Collections.unmodifiableList(contents);
	}

	@Override
	public List<Objects> getContentsAsEnums() {
		List<Objects> ans = new ArrayList<Objects>();
		for (Item i: contents) {
			ans.add(i.asEnum());
		}
		while(ans.size() < itemLimit) {
			ans.add(null);
		}
		return Collections.unmodifiableList(ans);
	}

	@Override
	public int size() {
		return contents.size();
	}

	@Override
	public int maxSize() {
		return itemLimit;
	}


}
