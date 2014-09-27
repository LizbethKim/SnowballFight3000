package gameworld.world;

public class SnowballFactory {

	public enum SnowballType{
		NORMAL, FLAMING, ICE
	}
	
	public SnowballFactory() {
		// AUTO
	}
	
	public Snowball makeSnowball(Location l, Direction d, SnowballType s) {
		if (s == SnowballType.FLAMING) {
			return new Snowball(l, d, 20, 1.5, s);
		} else if (s == SnowballType.ICE) {
			return new Snowball(l, d, 15, 1.5, s);
		} else {
			return new Snowball(l, d, 10, 2, s);
		}
	}

}
