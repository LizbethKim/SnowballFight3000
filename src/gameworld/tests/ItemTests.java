package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Bag;
import gameworld.world.Chest;
import gameworld.world.Direction;
import gameworld.world.Door;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Snowball;
import gameworld.world.Snowball.SnowballType;

import org.junit.Test;

public class ItemTests {
	// KTC finish these tests
	@Test
	public void testChest() {

	}

	@Test
	public void testChestLocking() {
		Chest c = new Chest("A chest", 2, true);
		Key goodKey = new Key("A key", 2);
		Key badKey = new Key ("A bad key", 3);
		assertFalse(c.unlock(badKey));
		assertTrue(c.isLocked());
		assertTrue(c.unlock(goodKey));
		assertFalse(c.isLocked());
	}

	@Test (expected = IllegalArgumentException.class)
	public void testBagNotContainItself() {
		Bag bag = new Bag();
		bag.addItem(bag);
	}

	@Test
	public void testSnowball() {
		Snowball s = new Snowball(new Location(3,3), Direction.SOUTH, 20, 1, SnowballType.NORMAL);
	}

	@Test
	public void testKey() {

	}

	@Test
	public void testFurniture() {

	}

	public void testDoor() {
		Door d = new Door("A door", Direction.NORTH, 2, true);
		Key goodKey = new Key("A key", 2);
		Key badKey = new Key ("A bad key", 3);
		assertFalse(d.unlock(badKey));
		assertTrue(d.isLocked());
		assertTrue(d.unlock(goodKey));
		assertFalse(d.isLocked());
	}

}
