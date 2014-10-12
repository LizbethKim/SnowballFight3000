package gameworld.tests;

import gameworld.world.Bag;

import org.junit.Test;

public class ItemTests {
	// KTC finish these tests
	@Test
	public void testChest() {
		
	}
	
	@Test
	public void testChestLocking() {
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBagNotContainItself() {
		Bag bag = new Bag();
		bag.addItem(bag);	
	}
	
	@Test
	public void testSnowball() {
		
	}
	
	@Test
	public void testKey() {
		
	}
	
	@Test 
	public void testFurniture() {
		
	}

	public void testDoor() {
		
	}
	
}
