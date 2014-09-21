package gameworld.game;

import gameworld.world.Board;
import gameworld.world.Location;
import gameworld.world.Player;

public class Game {
	private Board board;
	
	public Game() {
		// AUTO
	}
	
	// examples of methods that will be in here
	public void move (String player, Location l) {
		// KTC move specified player to specified location IF possible.
		// KTC representation of players outside of objects perhaps 
	}
	
	public void pickUpItemAt (String player, Location l) {
		// KTC make this work
	}
	
	public Player playerAt(Location l) {
		// Possibly not a good idea, depends on encapsulation and mutability of Player
		return null;
	}
	
}
