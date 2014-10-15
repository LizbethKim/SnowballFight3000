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
 * @author Kate Henderson 300279254
 *
 */
public class LoadGame{
	private StoredGame game;

	/**
	 * Purely for testing
	 * @param args
	 */
	/*public static void main(String[] args) {
		
		StaxParser parser = new StaxParser();
		StoredGame testGame = parser.parse(new File("src/storage/defaultBoard.xml"));
        System.out.println("Game Loaded by load main");
	}*/
	
	/**
	 * Loads the given filepath
	 * @param file The filepath to load
	 * @return StoredGame the storedGame that was loaded
	 */
	public StoredGame loadGame(File file){
		StaxParser parser = new StaxParser();
		System.out.println(file);
		if(file==null){
			System.out.println("That's not a path: ");
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
		File file = new File("src/storage/loadedGame.xml");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(map);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StoredGame game = loadGame(file);
		if(game.getBoard()==null){
			System.out.println("Board is null");
		}
		if(game.getPlayers()==null){
			System.out.println("Players is null");
		}
		return game; 
	}


}
