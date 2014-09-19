package gameworld.world;

/**
 * Can be locked. Has a key associated with it.
 * @author kelsey
 */
public interface Lockable extends StaticEntity {
	public boolean unlock(Key k);
	public Key getKey();
	
}
