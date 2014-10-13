package gameworld.world;

import graphics.assets.Objects;

/**
 *
 * @author Kelsey Jack 300275851
 *
 */
public class Door extends Furniture implements Lockable {
	private boolean locked;
	public final int id;

	public Door(String description, int id) {
		super(description, Objects.DOORNS);
		this.id = id;
		this.locked = false;
	}

	@Override
	public boolean unlock(Key k) {
		if (k.id == this.id) {
			this.locked = false;
			return true;
		}
		return false;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean canMoveThrough() {
		return !locked;
	}
}
