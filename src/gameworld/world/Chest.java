package gameworld.world;

import graphics.assets.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a chest, which is an Inventory and Furniture. Can be locked.
 * @author Kelsey Jack 300275851
 */
public class Chest extends Furniture implements Inventory, Lockable {
	private List<Item> contents;
	private final int itemLimit = 9;
	private boolean locked = false;
	public final int id;

	/**
	 * Constructs a chest containing the given collection of items,
	 * and associated with keys with the given ID.
	 * @param contents The items to be placed in the chest. Cannot be null.
	 * @param description A description of the chest
	 * @param ID the id associated with unlocking the chest
	 * @param locked Whether the chest is initially locked
	 */
	public Chest(String description, Collection<Item> contents, int ID, boolean locked) {
		super(description, Entities.CHEST);
		this.contents = new ArrayList<Item>(contents);
		this.id = ID;
		this.locked = locked;
	}

	/**
	 * Constructs an empty chest associated with a key.
	 * @param description A desciption of the chest
	 * @param ID The id of the keys that can unlock it
	 * @param locked Whether the chest is initally locked
	 */
	public Chest(String description, int ID, boolean locked) {
		super(description, Entities.CHEST);
		this.contents = new ArrayList<Item>();
		this.id = ID;
		this.locked = locked;
	}

	@Override
	public boolean addItem(Item i) {
		if (contents.size() < itemLimit) {
			this.contents.add(i);
			return true;
		}
		return false;
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
	public List<Entities> getContentsAsEnums() {
		List<Entities> ans = new ArrayList<Entities>();
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

	@Override
	public boolean unlock(Key k) {
		if (k.ID == this.id) {
			this.locked = false;
			return true;
		} else if (this.id == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void empty() {
		this.contents.clear();
	}

	public boolean isLocked() {
		return locked;
	}
}
