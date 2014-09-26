package gameworld.world;

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

	
}
