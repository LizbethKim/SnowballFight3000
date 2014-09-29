/**
 * 
 */
package storage.load;

import gameworld.world.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import storage.StoredGame;

/**
 * @author Kate Henderson
 *
 */
public class LoadHandler extends DefaultHandler{
	private StoredGame game;
	
	private Player curPlayer;
	private boolean playerStart = false;
	
	/**
	 * Creates a new StoredGame and loads the parsed XML into it.
	 */
	public LoadHandler() {
		game = new StoredGame();
	}
	
	
	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.println("start document   : ");
	}
	
	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("start element    : " + qName);
		
		if(qName.equalsIgnoreCase("game")){
			
		} else if(qName.equalsIgnoreCase("players")){
			game.newPlayerList();
		} else if(qName.equalsIgnoreCase("player")){
			playerStart = true;
		} else if(qName.equalsIgnoreCase("inventory")){
			
		} else if(qName.equalsIgnoreCase("map")){
			
		} else if(qName.equalsIgnoreCase("tile")){
			
		}
	}
	
	/** 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println("start characters : " + new String(ch, start, length));
		if(playerStart == true){
			int team = Integer.parseInt(new String(ch, start, 1));
			int x = Integer.parseInt(new String(ch, start+2, 3));
			int y = Integer.parseInt(new String(ch, start+6, 3));
			String name = new String (ch, start+10,length-12);//extra letter off length is \n character
			curPlayer = new Player(team, new Location(x, y), name);
			playerStart = false;
		}
	}
	
	
	/** 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("end element	 : " + qName);
		if(qName.equalsIgnoreCase("game")){
			//GAME IS COMPLETELY LOADED!!!!
		} else if(qName.equalsIgnoreCase("players")){
			//Players are loaded
		} else if(qName.equalsIgnoreCase("player")){ //curPlayer is finished
			game.addPlayer(curPlayer);
			System.out.println("ADDED PLAYER: "+curPlayer.name+" team:"+curPlayer.getTeam()+" location:"+curPlayer.getLocation().x+","+curPlayer.getLocation().y);
		} else if(qName.equalsIgnoreCase("inventory")){
			
		} else if(qName.equalsIgnoreCase("map")){
			
		} else if(qName.equalsIgnoreCase("tile")){
			
		}
	}
	
	/** 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.println("end document   : ");
	}

	/**
	 * @return
	 */
	public StoredGame buildGame() {
		game.playerCount = 5;
		return game;
	}
}
