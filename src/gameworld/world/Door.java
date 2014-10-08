package gameworld.world;

import graphics.assets.Objects;

/**
 *
 * @author Kelsey Jack 300275851
 *
 */
public class Door implements Lockable {
	private boolean locked;

	public Door() {
		// AUTO
	}

	@Override
	public boolean unlock(Key k) {
		// AUTO
		return false;
	}

	@Override
	public Key getKey() {
		// AUTO
		return null;
	}

	@Override
	public boolean canMoveThrough() {
		return !locked;
	}

	@Override
	public Objects asEnum() {
		return Objects.DOOR;
	}


}
