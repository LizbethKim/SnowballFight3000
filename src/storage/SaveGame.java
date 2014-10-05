/**
 *
 */
package storage;

import storage.save.StaxWriter;

/**
 * @author Kate Henderson
 *
 */
public class SaveGame {
	
	public void save(StoredGame sg) {
		StaxWriter writer = new StaxWriter();
	    String file = writer.saveGame(sg);
	    System.out.println("SAVED FILENAME: "+ file);
	}
}
