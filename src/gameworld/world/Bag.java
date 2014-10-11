package gameworld.world;

import graphics.assets.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag extends Item implements Inventory {
	private final int itemLimit = 4;
	private List<Item> contents;
	
	/**
	 * Constructs an empty bag
	 */
	public Bag() {
		this.contents = new ArrayList<Item>();
		this.description = "A bag can carry a few items. You can carry the bag.";
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
	public Objects asEnum() {
		return Objects.BAG;
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
