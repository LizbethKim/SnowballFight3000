/**
 *
 */
package storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import storage.load.StaxParser;

/**
 * Loads a previously saved game using the StAX library and XML.
 * @author Katherine Henderson 300279264
 *
 */
public class LoadGame{
	private StoredGame game;
	
	/**
	 * Loads the given filepath
	 * @param file The filepath to load
	 * @return StoredGame the storedGame that was loaded
	 */
	public StoredGame loadGame(File file){
		StaxParser parser = new StaxParser();
		game = parser.parse(file);
		return game;
	}

	/**
	 * Loads a file called byteBoard that is written from the byte[].
	 * @param map To be converted to a file
	 * @return The storedGame that was transferred
	 */
	public StoredGame loadGame(byte[] map) {
		
		File file = new File(System.getProperty("user.home"),"loadedGame.xml");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(map);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StoredGame game = loadGame(file);
		return game; 
	}


}
