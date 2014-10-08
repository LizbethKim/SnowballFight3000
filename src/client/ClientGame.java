package client;

import gameworld.game.ServerGame;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.NullLocation;
import gameworld.world.Player;
import gameworld.world.Team;
import graphics.assets.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.Client;
import storage.SaveGame;
import storage.StoredGame;
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
	//private List<Snowball> projectiles;

	private Player player;
	private BoardState boardState;
	private Client client;
	private ClientUpdater updater;

	private int playerID;

	private UI display;
	private long lastMovedTime;

	// Only used for single player
	public ClientGame(Board b, Client c){
		this.board = b;
		this.client = c;
		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		display = new UI(this);
		client.sendNameAndTeam("name goes here", Team.BLUE);
		player = new Player("name goes here", Team.BLUE, 0, new Location(3,3));

		updater = this.getUpdater();
		client.startReceiving(updater);
		System.out.println("new game created");
	}

	public ClientGame(String name, String IP, Team team, UI display) {
		this.client = new Client(IP);
		//StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		// this.playerID = KTC to do
		//this.board = sb.getBoard();

		this.board = new Board();

		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		this.display = display;
		client.sendNameAndTeam(name, team);
		player = new Player(name, team, 0, new NullLocation());

		ClientUpdater u = this.getUpdater();
		client.startReceiving(u);
	}

	public ClientGame() {
		// TEMP
	}

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
		Direction up = boardState.getDirection();
		d = Direction.values()[(d.ordinal() - up.ordinal() + 4)%4];

		if (System.currentTimeMillis() - lastMovedTime > ServerGame.MOVE_DELAY) {
			if (player.getDirection() == d) {
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
				if (board.containsLocation(newLoc) && board.canTraverse(newLoc) && this.isFree(newLoc)) {
					client.sendMove(newLoc);
				}

			} else {
				client.sendTurn(d);
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

	public void setLocalLocation(Location l) {
		this.player.setLocation(l);
	}

	public void save() {
		SaveGame saver = new SaveGame();
		List<Player> ps = new ArrayList<Player>();
		ps.addAll(playerIDs.values());
		StoredGame sg = new StoredGame(this.board, ps);
		saver.save(sg);
	}

	public ClientUpdater getUpdater() {
		if (this.updater != null) {
			return updater;
		}
		return new ClientUpdater(this, board, playerIDs,
				boardState, display, playerID);
	}

	private boolean isFree(Location l) {
		for (Player p: playerIDs.values()) {
			if (p.getLocation().equals(l)) {
				return false;
			}
		}
		return true;
	}

}
