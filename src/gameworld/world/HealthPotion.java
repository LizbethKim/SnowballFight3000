package gameworld.world;

import graphics.assets.Objects;

public class HealthPotion extends Powerup {
	
	public HealthPotion() {
		super("A health potion!");
		this.effect = new PotionEffect(50);
	}

	
	@Override
	public Objects asEnum() {
		return Objects.HEALTH;
	}
	
}
