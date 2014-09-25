package client;

import java.util.List;

import client.BoardState.Objects;

public class Client {
	// RB for you
	private int playerID;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}


	public Client (int playerID) {
		this.playerID = playerID;
	}


	public void move (Direction d) {
		// KTC
	}

	public void throwSnowball () {
		// KTC
	}

	public String inspectItem(Objects i) {
		// KTC
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
}
