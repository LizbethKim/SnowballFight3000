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
		StoredGame testGame = parser.parse(new File("src/storage/1413289751685.xml"));
        System.out.println("Game Loaded by load main");
	}
	
	/**
	 * Loads the given filepath
	 * @param file The filepath to load
	 * @return StoredGame the storedGame that was loaded
	 */
	public StoredGame loadGame(File file){
		StaxParser parser = new StaxParser();
		System.out.println(file);
		if(file==null){
			System.out.println("That's not a path: "+file);
		}else{
			game = parser.parse(file);
			System.out.println("Game Loaded by loader");
		}
		return game;
	}

	/**
	 * Loads a file called byteBoard that is written from the byte[].
	 * @param map To be converted to a file
	 * @return The storedGame that was transferred
	 */
	public StoredGame loadGame(byte[] map) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("src/storage/byteBoard.xml"));
			fos.write(map);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loadGame(new File("byteBoard.xml"));
	}


}
