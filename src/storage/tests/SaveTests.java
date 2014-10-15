/**
 * 
 */
package storage.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.junit.Test;

import storage.LoadGame;
import storage.SaveGame;
import storage.StoredGame;
/**
 * Testing for loading/saving
 * @author Katherine Henderson 300279264
 *
 */
public class SaveTests {
	private SaveGame saver = new SaveGame();
	private LoadGame loader = new LoadGame();
	private StoredGame game;
	
	@Test
	public void saveLoadMatch(){
		InputStream is = LoadGame.class.getResourceAsStream("standardMap.xml");
		File targetFile = null;
		File saveFile = new File(System.getProperty("user.home"),"testSaveGame.xml");
		System.out.println(is==null);
		byte[] buffer;
		try {
			buffer = new byte[is.available()];		
			is.read(buffer);
			targetFile = new File(System.getProperty("user.home"),"testLoadGame.xml");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		game = loader.loadGame(targetFile);
		saver.save(game, saveFile);
		
		try {
			Scanner loadScan = new Scanner(targetFile);
			Scanner saveScan = new Scanner(saveFile);
			while(loadScan.hasNext() && saveScan.hasNext()){
				String loadString = loadScan.next();
				String saveString = saveScan.next();
				assert(loadString.equals(saveString));
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
	}
	
	
}
