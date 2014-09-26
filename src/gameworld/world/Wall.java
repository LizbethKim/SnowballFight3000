package gameworld.world;

public class Wall implements StaticEntity {

	public Wall() {
		// AUTO
	}

	@Override
	public boolean canMoveThrough() {
		return false;
	}

}
