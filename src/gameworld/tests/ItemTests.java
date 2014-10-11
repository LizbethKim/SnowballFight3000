package gameworld.tests;

import gameworld.world.Bag;

import org.junit.Test;

public class ItemTests {

	@Test
	public void testChest() {
		
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

}
