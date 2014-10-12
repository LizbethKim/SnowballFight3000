package gameworld.game.client;

import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.InanimateEntity;
import gameworld.world.Inventory;
import gameworld.world.Item;
import gameworld.world.Location;
import gameworld.world.NullLocation;
import gameworld.world.Player;
import gameworld.world.Powerup;
import gameworld.world.Team;
import graphics.assets.Objects;

import java.util.ArrayList;
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

	private Player player;
	private Client client;
	private ClientUpdater updater;

	private int playerID;

	private UI display;
	private long lastMovedTime;
	private long lastFiredTime;

	private BoardState boardState;
	private int selectedIndex = -1;	// index selected in inventory

	public ClientGame(String name, String IP, Team team, UI display) {
		this.client = new Client(IP);
		//StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		// this.playerID = KTC to do
		//this.board = sb.getBoard();

		this.board = Board.defaultBoard();

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
		return player.getInventory().getContentsAsEnums();
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

		if (System.currentTimeMillis() - lastMovedTime > player.getStepDelay()) {
			if (player.getDirection() == d) {
				Location newLoc = player.getLocationInFrontOf();
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
		if (System.currentTimeMillis() - lastFiredTime > player.getSnowballDelay()) {
			client.throwSnowball();
			lastFiredTime = System.currentTimeMillis();
		}
	}

	public String inspectItem() throws NoItemException {
		Location facing = player.getLocationInFrontOf();
		if (board.containsLocation(facing)) {
			if (board.tileAt(facing).getOn() != null) {
				return board.tileAt(facing).getOn().getDescription();
			}
		}
		throw new NoItemException();
	}

	public void pickUpItem() {
		Location facing = player.getLocationInFrontOf();
		if (board.containsLocation(facing)) {
			if (board.tileAt(facing).getOn() != null) {
				client.pickUpItem();
			}
		}
	}

	/**
	 * Removes the item at the specified index from the container
	 * in front of the player in the world. Places it in the
	 * player's inventory
	 * @param index
	 */
	public void takeItemFromContainer(int index) {
		// KTC make picking from containers work
	}

	/**
	 * Drops the item at the index position in the player's inventory into
	 * the container in front of them in the world
	 */
	public void dropIntoContainer(int index) {
		// KTC make adding into containers work
	}

	public void useItem() {
		if (selectedIndex != -1 && player.getInventory().getContents().get(selectedIndex) != null) {
			Item toUse = player.getInventory().getContents().get(selectedIndex);
			if (toUse instanceof Powerup) {
				Powerup powerup = (Powerup)toUse;
				powerup.use(player);
				// client.removeItem(selectedIndex);
			} else {
				// KTC later
			}
		}
	}

	public boolean selectedIsContainer(){
		if (selectedIndex != -1 && player.getInventory().getContents().get(selectedIndex) instanceof Inventory) {
			return true;
		}
		return false;
	}

	public List<Objects> getContentsOfSelected() throws NotAContainerException {
		if (selectedIsContainer()) {
			return ((Inventory)player.getInventory().getContents().get(selectedIndex)).getContentsAsEnums();
		}
		throw new NotAContainerException();
	}

	public void dropSelectedItem() {
		if (player.getInventory().size() > selectedIndex && player.getInventory().getContents().get(selectedIndex) != null) {
			client.dropItem(selectedIndex);
		}
	}

	/**
	 * Gets the contents of the inventory (if there is one) in front of where the
	 * player is standing. If there is none, it returns an empty list.
	 * @return An unmodifiable list of enums representing the objects in the inventory.
	 * @throws NotAContainerException
	 */
	public List<Objects> getContents() throws NotAContainerException {
		Location containerLoc = Location.locationInFrontOf(player.getLocation(), player.getDirection());
		if (board.containsLocation(containerLoc)) {
			InanimateEntity on = board.tileAt(containerLoc).getOn();
			if (on != null && on instanceof Inventory) {
				return ((Inventory)on).getContentsAsEnums();
			}
		}
		throw new NotAContainerException();
	}

	public int getPlayerID() {
		return playerID;
	}


	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
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
