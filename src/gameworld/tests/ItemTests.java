package gameworld.tests;

import static org.junit.Assert.*;
import gameworld.world.Bag;
import gameworld.world.Chest;
import gameworld.world.Direction;
import gameworld.world.Door;
import gameworld.world.Flag;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.Snowball.SnowballType;
import gameworld.world.Team;
import graphics.assets.Entities;

import org.junit.Test;

public class ItemTests {
	@Test
	public void testChest() {
		Chest c = new Chest("A chest", 0, false);
		assertTrue(c.getContents().isEmpty());
		Key k = new Key("", 0);
		for (int i = 0; i < c.maxSize(); i++) {
			k = new Key("", 0);
			assertTrue(c.addItem(k));
		}
		assertFalse(c.addItem(new Key("", 0)));
		assertTrue(c.removeItem(k));
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testAddingToChestItemList(){
		Chest c = new Chest("A chest", 0, false);
		c.getContents().add(new Key("", 0));
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
		assertFalse(c.canMoveThrough());
	}

	@Test (expected = IllegalArgumentException.class)
	public void testBagNotContainItself() {
		Bag bag = new Bag();
		bag.addItem(bag);
	}

	@Test
	public void testBag() {
		Bag b = new Bag();
		Flag f = new Flag(Team.RED);
		assertFalse(b.addItem(f));
		assertTrue(b.asEnum() == Entities.BAG);
		assertTrue(b.canMoveThrough());

	}

	@Test
	public void testSnowball() {
		Snowball s = new Snowball(new Location(3,3), Direction.SOUTH, 20, 1, SnowballType.NORMAL);
		assertTrue(s.getLocation().equals(new Location(3,4)));
		s.clockTick();
		assertTrue(s.getLocation().equals(new Location(3,5)));
		Player p = new Player("A player", Team.RED, 0, new Location(3,5));
		assertTrue(s.hit(p));
		assertTrue(p.getHealth() == 80);
		p.setLocation(new Location(5,5));
		assertFalse(s.hit(p));
		assertTrue(p.getHealth() == 80);

	}

	public void testDoor() {
		Door d = new Door("A door", Direction.NORTH, 2, true);
		Key goodKey = new Key("A key", 2);
		Key badKey = new Key ("A bad key", 3);
		assertFalse(d.unlock(badKey));
		assertTrue(d.isLocked());
		assertFalse(d.canMoveThrough());
		assertTrue(d.unlock(goodKey));
		assertFalse(d.isLocked());
		assertTrue(d.canMoveThrough());
	}
}
