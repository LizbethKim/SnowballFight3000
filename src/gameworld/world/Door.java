package gameworld.world;

import graphics.assets.Objects;

/**
 *
 * @author Kelsey Jack 300275851
 *
 */
public class Door extends Furniture implements Lockable {
	private boolean locked;
	private Key key;

	public Door(String description, Key k) {
		super(description, Objects.DOOR);
	}

	@Override
	public boolean unlock(Key k) {
		if (k.equals(key)) {	// KTC ??
			this.locked = false;
			return true;
		}
		return false;
	}

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public boolean canMoveThrough() {
		return !locked;
	}
}
