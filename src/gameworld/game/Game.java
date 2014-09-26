package gameworld.game;

import java.util.Map;

import gameworld.world.Board;
import gameworld.world.Location;
import gameworld.world.Player;

public class Game {
	private Board board;
	private Map<Integer, Player> playerIDs;
	
	public Game() {
		// AUTO
	}
	
	// examples of methods that will be in here
	public void move (int playerID, Location l) {
		Player p = playerIDs.get(playerID);
		if (board.canTraverse(l)) {
			if (p.move(l)) {
				// KTC only here do you send out an update.
			}
		}	
	}
	
	public void pickUpItemAt (String player, Location l) {
		// KTC make this work
	}
	
	public Player playerAt(Location l) {
		// Possibly not a good idea, depends on encapsulation and mutability of Player
		return null;
	}
	
}
