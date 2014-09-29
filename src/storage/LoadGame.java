/**
 * 
 */
package storage;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import storage.load.LoadHandler;

/**
 * Loads a previously saved game using the SAX library and XML. 
 * @author Kate Henderson 300279254
 *
 */
public class LoadGame{
	
	public LoadGame(){
		
	}
	
	/**
	 * Purely for testing, will be phased out when some kind of file chooser is done. Possibly just a default to the
	 *  last saved game, and then a pre-established one to demonstrate loading
	 * @param args
	 */
	public static void main(String[] args) {
	    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        LoadHandler handler = new LoadHandler();
	        saxParser.parse(new File("src/storage/testgame.xml"), handler);
	         
	        StoredGame game = handler.buildGame();
	        System.out.println("Game Player count: "+ game.playerCount);
	        
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public StoredGame loadGame(String filename){
		
		return new StoredGame();

	}


}
