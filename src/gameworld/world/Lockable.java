package gameworld.world;

/**
 * Can be locked. Has a key associated with it.
 * @author Kelsey Jack 300275851
 */
public interface Lockable extends StaticEntity {
	public boolean unlock(Key k);
	public int getID();

}
