/**
 *
 */
package storage;

import gameworld.world.Chest;
import gameworld.world.Location;
import gameworld.world.Tile;
import graphics.assets.Objects;

import java.io.File;

import storage.load.StaxParser;

/**
 * Loads a previously saved game using the StAX library and XML.
 * @author Kate Henderson 300279254
 *
 */
public class LoadGame{
	private StoredGame game;

	public static void main(String[] args) {
		
		StaxParser parser = new StaxParser();
		StoredGame testGame = parser.parse(new File("src/storage/1413195551663.xml"));
        System.out.println("Game Loaded by loader");
	}
	
	public StoredGame loadGame(String filename){
		StaxParser parser = new StaxParser();
		System.out.println(filename);
		game = parser.parse(new File("src/storage/"+filename));
		System.out.println("Game Loaded by loader");
		return game;
	}

	public StoredGame loadGame(Byte[] map) {
		// KH, I'll probably need this. Bryden is sending the map through the network in a
		// array of bytes. So it'd be cool if you could load a game from one.
		return null;
	}


}
