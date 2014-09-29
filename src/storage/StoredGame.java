/**
 *
 */
package storage;

import gameworld.world.Board;
import gameworld.world.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for storing all of the load/save data for one game to pass around. Should contain game state, players, map, etc.
 *
 * @author Kate Henderson
 *
 */
public class StoredGame {
	private Board board;
	public int playerCount; //just used for testing access
	private List<Player> players;

	public StoredGame(Board b, List<Player> ps){
		this.board = b;
		this.players = ps;
	}

	public StoredGame() {

	}

	public void addPlayer(Player p){
		players.add(p);
	}

	public void newPlayerList(){
		players = new ArrayList<Player>();
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
