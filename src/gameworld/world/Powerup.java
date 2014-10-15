package gameworld.world;

import graphics.assets.Entities;

/**
 * "Magic" items. May have some effect on the player who uses it
 * and/or the player it's used on.
 * Uses a strategy pattern and a factory pattern.
 * @author Kelsey Jack 300275851
 *
 */
public class Powerup extends Item {
	protected PowerupEffect effect;
	private Power power;

	public enum Power {
		SPEED_BOOST("Move twice as fast for 30 seconds"),
		FIRE_SPEED_BOOST("You can throw snowballs more frequently for 30 seconds"),
		STRONG_HEALTH_POTION("Restores you to full health"),
		HEALTH_POTION("Restores 50 health points"),
		FAST_SNOWBALLS("Throw fast snowballs for 30 seconds"),
		SUPER_SNOWBALLS("Throw snowballs that do double damage for 30 seconds"),
		FLAMING_SNOWBALL("Allows you to throw one one-hit-kill snowball.");


		public final String description;
		Power(String description) {
			this.description = description;
		}
	}

	/**
	 * Creates a powerup with the given power
	 * @param p
	 */
	public Powerup(Power p) {
		this.power = p;
		this.description = p.description;
		if (power == Power.SPEED_BOOST) {
			this.effect = new SpeedEffect();
		} else if (power == Power.FIRE_SPEED_BOOST) {
			this.effect = new FireSpeedEffect();
		} else if (power == Power.STRONG_HEALTH_POTION) {
			this.effect = new PotionEffect(100);
		} else if (power == Power.HEALTH_POTION) {
			this.effect = new PotionEffect(50);
		} else if (power == Power.FAST_SNOWBALLS) {
			this.effect = new FastSnowballsEffect();
		} else if (power == Power.FLAMING_SNOWBALL) {
			this.effect = new FlamingSnowballsEffect();
		} else {
			this.effect = new SuperSnowballsEffect();
		}
	}

	/**
	 * Creates a random powerup.
	 */
	public Powerup() {
		int random = (int)(Math.random()*Power.values().length);
		this.power = Power.values()[random];
		this.description = power.description;
		if (power == Power.SPEED_BOOST) {
			this.effect = new SpeedEffect();
		} else if (power == Power.FIRE_SPEED_BOOST) {
			this.effect = new FireSpeedEffect();
		} else if (power == Power.STRONG_HEALTH_POTION) {
			this.effect = new PotionEffect(100);
		} else if (power == Power.HEALTH_POTION) {
			this.effect = new PotionEffect(50);
		} else if (power == Power.FAST_SNOWBALLS) {
			this.effect = new FastSnowballsEffect();
		} else if (power == Power.FLAMING_SNOWBALL) {
			this.effect = new FlamingSnowballsEffect();
		} else {
			this.effect = new SuperSnowballsEffect();
		}
	}

	/**
	 * Applies the effect of the powerup to the given player
	 * @param p
	 */
	public void use (Player p) {
		effect.apply(p);
	}

	public Power getPower(){
		return power;
	}

	@Override
	public Entities asEnum() {
		if (power == Power.STRONG_HEALTH_POTION || power == Power.HEALTH_POTION) {
			return Entities.HEALTH;
		}
		return Entities.POWERUP;
	}

}
