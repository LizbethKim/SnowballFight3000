package gameworld.game;

import java.util.Map;

import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;

public class Game {
	private Board board;
	private Map<Integer, Player> playerIDs;
	
	public Game() {
		// AUTO
	}
	
	// examples of methods that will be in here
	public void movePlayer (int playerID, Location l) {
		Player p = playerIDs.get(playerID);
		if (board.canTraverse(l) && p != null) {
			if (p.move(l)) {
				// KTC only here do you send out an update.
			}
		}	
	}
	
	public void turnPlayer(int playerID, Direction d) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.setDirection(d);
			// KTC send back the update.
		}
	}
	
	public void pickUpItemAt (String player, Location l) {
		// KTC make this work
	}
	
	public void throwSnowball(int playerID) {
		
	}
	
	public Player playerAt(Location l) {
		// Possibly not a good idea, depends on encapsulation and mutability of Player
		return null;
	}
	
}
