package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameworld.game.ServerGame;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import graphics.assets.Objects;
import storage.SaveGame;
import storage.StoredGame;
import ui.UI;

public class SinglePlayerGame extends ClientGame {
	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;


	private Player player;
	private BoardState boardState;

	private int playerID;

	private UI display;
	private ClientUpdater update;
	private long lastMovedTime;

	public SinglePlayerGame (Board b) {
		// Somewhere in here I'll need a client object. probably
		this.board = b;
		//this.playerID = KTC to do
		this.playerID = 100;
		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		display = new UI(this);
		player = new Player(1, new Location(2, 2), "");
		playerIDs.put(this.playerID, player);
		projectiles = new ArrayList<Snowball>();
		this.update = new ClientUpdater(board, playerIDs, projectiles, boardState, display);
	}

	public SinglePlayerGame(StoredGame g) {
		this.board = g.getBoard();
		this.player = g.getPlayers().get(0);
		playerID = 100;
		playerIDs = new HashMap<Integer, Player>();
		playerIDs.put(100, player);
		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		display = new UI(this);
		this.update = new ClientUpdater(board, playerIDs, projectiles, boardState, display);
	}


	public int getPlayerHealth() {
		return player.getHealth();
	}

	public List<Objects> getPlayerInventory() {
		return null;	// KTC to do
	}

	public Location getPlayerLocation() {
		return player.getLocation();
	}

	public Direction getPlayerDirection() {
		return player.getDirection();
	}

	public void move (Direction d) {
		Direction up = boardState.getDirection();
		d = Direction.values()[(d.ordinal() - up.ordinal() + 4)%4];



		if (System.currentTimeMillis() - lastMovedTime > ServerGame.MOVE_DELAY) {
			// KTC check if it's off the edge of the board
			if (player.getDirection() == d) {
				Location newLoc;
				if (d == Direction.NORTH) {
					newLoc = new Location(player.getLocation().x, player.getLocation().y - 1);
				} else if (d == Direction.SOUTH) {
					newLoc = new Location(player.getLocation().x, player.getLocation().y + 1);
				} else if (d == Direction.EAST) {
					newLoc = new Location(player.getLocation().x + 1, player.getLocation().y);
				} else {
					newLoc = new Location(player.getLocation().x - 1, player.getLocation().y);
				}
				if (board.containsLocation(newLoc) && board.canTraverse(newLoc)) {
					update.movePlayer(playerID, newLoc);
				}
			} else {
				update.turnPlayer(playerID, d);
			}
			lastMovedTime = System.currentTimeMillis();
		}
	}

	public void rotateClockwise() {
		boardState.rotateClockwise();
		refresh();
	}

	public void rotateAnticlockwise() {
		boardState.rotateAnticlockwise();
		refresh();
	}

	public void throwSnowball () {
		// KTC send a fire snowball method through the network with playerID.
		// not sure how to extend for non-standard snowballs.
	}

	public String inspectItem() {
		// KTC inspect the item in front of them.
		return "";
	}

	/*
	 * RB with the current system, where items are enums, there will be no
	 * way to tell if they are a container. But you can just call this method
	 * if the user tries to access the contents of something (so you might
	 * want an 'open' button or something) and it will just return an empty
	 * list/null if it's not a container. Does that sound ok?
	 *
	 * @param cont
	 * @return
	 */
	public List<Objects> getContents() {
		// KTC do it for the object in front of them.
		return null;
	}

	public int getPlayerID() {
		return playerID;
	}

	public BoardState getBoard() {
		return boardState;
	}

	public void refresh() {
		display.repaint();
	}

	// KH this is where I'll do saving, dunno if it makes sense but.
	public void save() {
		SaveGame saver = new SaveGame();
		List<Player> ps = new ArrayList<Player>();
		ps.addAll(playerIDs.values());
		StoredGame sg = new StoredGame(this.board, ps);
		saver.save(sg);
	}

}
