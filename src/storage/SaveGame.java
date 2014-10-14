/**
 *
 */
package storage;

import java.io.File;

import storage.save.StaxWriter;

/**
 * @author Kate Henderson
 *
 */
public class SaveGame {
	
	/**
	 * Default saves to an XML file in /src/storage called the System.currentTimeMillis() value of the save time.
	 * @param sg StoredGame to be saved
	 */
	public void save(StoredGame sg) {
		save(sg, "src/storage/"+ Long.toString(System.currentTimeMillis()));
	}
	
	/**
	 * Saves to the given filepath. which should include a potential filename.
	 * @param sg StoredGame to be saved
	 * @param file2 Path to be saved at, should include a filename
	 */
	public void save(StoredGame sg, File file2) {
		StaxWriter writer = new StaxWriter();
	    String file = writer.saveGame(sg, file2);
	    System.out.println("SAVED FILENAME: "+ file);
	}
	
}
