package client;

import gameworld.game.ServerGame;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.SnowballFactory;
import graphics.assets.Objects;
import graphics.assets.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.Client;
import server.events.MoveEvent;
import server.events.TurnEvent;
import ui.UI;

/**
 * Current reprentation of the model on the client. Deals with the network and sends and receives updates to/from the server.
 *
 * @author Kelsey Jack 300275851
 *
 */
public class ClientGame {
	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;

	private Player player;
	private BoardState boardState;
	private Client client;

	private int playerID;

	private UI display;
	private long lastMovedTime;

	public ClientGame(Board b, Client c) {
		// Somewhere in here I'll need a client object. probably
		this.board = b;
		this.client = c;
		// this.playerID = KTC to do
		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		projectiles = new ArrayList<Snowball>();
		display = new UI(this);
		client.sendName("name goes here");
		player = new Player("name goes here", 0);
	}

	public ClientGame() {
		// TEMP
	}

	// public PlayerState getPlayer() {
	// return player;
	// }

	public int getPlayerHealth() {
		return player.getHealth();
	}

	public List<Objects> getPlayerInventory() {
		return null; // KTC to do
	}

	public Location getPlayerLocation() {
		return player.getLocation();
	}

	public Direction getPlayerDirection() {
		return player.getDirection();
	}

	public void move(Direction d) {
		if (player.getDirection() == d) {
			if (System.currentTimeMillis() - lastMovedTime > ServerGame.MOVE_DELAY) {
				Location newLoc;
				if (d == Direction.NORTH) {
					newLoc = new Location(player.getLocation().x,
							player.getLocation().y - 1);
				} else if (d == Direction.SOUTH) {
					newLoc = new Location(player.getLocation().x,
							player.getLocation().y + 1);
				} else if (d == Direction.EAST) {
					newLoc = new Location(player.getLocation().x + 1,
							player.getLocation().y);
				} else {
					newLoc = new Location(player.getLocation().x - 1,
							player.getLocation().y);
				}
				if (board.containsLocation(newLoc) && board.canTraverse(newLoc)) {
					client.sendMove(newLoc);
				}
			}
			lastMovedTime = System.currentTimeMillis();
		} else {
			client.sendTurn(d);
		}
	}

	public void rotateClockwise() {
		boardState.rotateClockwise();
	}

	public void rotateAnticlockwise() {
		boardState.rotateAnticlockwise();
	}

	public void throwSnowball() {
		// KTC send a fire snowball method through the network with playerID.
		// not sure how to extend for non-standard snowballs.
	}

	public String inspectItem() {
		// KTC inspect the item in front of them.
		return "";
	}

	/*
	 * RB with the current system, where items are enums, there will be no way to tell if they are a container. But you can just call this method if the user tries to access the contents of something
	 * (so you might want an 'open' button or something) and it will just return an empty list/null if it's not a container. Does that sound ok?
	 *
	 * @param cont
	 *
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

	public void setID(int ID) {
		this.playerID = ID;
		this.player.setID(ID);
		this.playerIDs.put(ID, this.player);
	}

	public void save() {
		// KTC to do add client.save() request to server or something....?
	}

	public ClientUpdater makeUpdater() {
		return new ClientUpdater(this, board, playerIDs, projectiles,
				boardState, display);
	}

}
