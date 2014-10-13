/**
 *
 */
package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import storage.load.StaxParser;

/**
 * Loads a previously saved game using the StAX library and XML.
 * @author Kate Henderson 300279254
 *
 */
public class LoadGame{
	private StoredGame game;

	/**
	 * Purely for testing
	 * @param args
	 */
	public static void main(String[] args) {
		
		StaxParser parser = new StaxParser();
		StoredGame testGame = parser.parse(new File("src/storage/1413195551663.xml"));
        System.out.println("Game Loaded by loader");
	}
	
	/**
	 * Loads the given filename, currently from src/storage/
	 * KH shouldn't just be from src/storage/
	 * @param filename The filename to load
	 * @return
	 */
	public StoredGame loadGame(String filename){
		StaxParser parser = new StaxParser();
		System.out.println(filename);
		game = parser.parse(new File("src/storage/"+filename));
		System.out.println("Game Loaded by loader");
		return game;
	}

	/**
	 * Loads a file called byteBoard that is written from the byte[].
	 * @param map To be converted to a file
	 * @return The storedGame that was transferred
	 */
	public StoredGame loadGame(byte[] map) {
		// KH, I'll probably need this. Bryden is sending the map through the network in a
		// array of bytes. So it'd be cool if you could load a game from one.
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("src/storage/byteBoard.xml"));
			fos.write(map);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loadGame("byteBoard.xml");
	}


}
