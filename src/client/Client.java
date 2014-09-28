package client;

import gameworld.world.Direction;
import gameworld.world.Location;
import graphics.assets.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.events.MoveEvent;
import server.events.TurnEvent;
import ui.UI;

/**
 * Current reprentation of the model on the client. Deals with the
 * network and sends and receives updates to/from the server.
 * @author Kelsey Jack 300275851
 *
 */
public class Client {
	private PlayerState player;
	private BoardState board;

	private int playerID;

	private UI display;


	public Client (int playerID) {
		this.playerID = playerID;
		List<Objects> playerInventory = new ArrayList<Objects>();
		playerInventory.add(Objects.KEY);
		playerInventory.add(Objects.POWERUP);
		this.player = new PlayerState(new ArrayList<Objects>(), 80, new Location(2,2), Direction.WEST);
		Map<Integer, PlayerState> playerIDs = new HashMap<Integer, PlayerState>();
		playerIDs.put(playerID, this.player);
		board = new BoardState(playerIDs);
		display = new UI(this);
	}

	public PlayerState getPlayer() {
		return player;
	}
	public void move (Direction d) {
		if (player.getDirection() == d) {
			Location newLoc;
			if (d == Direction.NORTH) {
				newLoc = new Location(player.getL().x, player.getL().y - 1);
			} else if (d == Direction.SOUTH) {
				newLoc = new Location(player.getL().x, player.getL().y + 1);
			} else if (d == Direction.EAST) {
				newLoc = new Location(player.getL().x + 1, player.getL().y);
			} else {
				newLoc = new Location(player.getL().x - 1, player.getL().y);
			}
			new MoveEvent(playerID, newLoc);
			// network.send( new MoveEvent(newLoc);
			// Ok, BF, I need a way to send this through the network. I think I just need
			// a MoveEvent class? Which takes a playerID (possibly) and the new location.
		} else {
			new TurnEvent(playerID, d);
			// KTC send this through the network.
		}
	}

	public void rotateClockwise() {
		player.rotateClockwise();
	}

	public void rotateAnticlockwise() {
		player.rotateAnticlockwise();
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
	public List<Objects> getContents(Objects cont) {
		// KTC
		return null;
	}

	public int getPlayerID() {
		return playerID;
	}

	public BoardState getBoard() {
		return board;
	}

	public void refresh() {
		display.repaint();
	}

}
