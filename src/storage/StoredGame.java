/**
 *
 */
package storage;

import gameworld.world.Board;
import gameworld.world.Player;

import java.util.List;

/**
 * Used for storing all of the load/save data for one game to pass around. Should contain game state, players, map, etc.
 *
 * @author Katherine Henderson 300279264
 *
 */
public class StoredGame {
	
	private Board board;
	private List<Player> players;

	public StoredGame(Board b, List<Player> ps){
		this.board = b;
		this.players = ps;
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
