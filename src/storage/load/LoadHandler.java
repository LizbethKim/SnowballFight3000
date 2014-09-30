/**
 * 
 */
package storage.load;

import gameworld.world.Board;
import gameworld.world.Furniture;
import gameworld.world.InanimateEntity;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Tile;
import graphics.assets.Objects;
import graphics.assets.Terrain;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import storage.StoredGame;

/**
 * Handles the parsing of the XML file into a board and player list, ready to be loaded in game
 * @author Kate Henderson
 *
 */
public class LoadHandler extends DefaultHandler{
	
	private StoredGame game;
	private Board board;
	private Tile[][] tiles;
	private List<Player> players;
	private Player curPlayer;
	private Tile curTile;
	
	private boolean tileLoad = false;
	private boolean playerLoad = false;
	private boolean inventoryLoad = false;
	private boolean boardLoad = false;
	
	private int charDepth;
	
	
	private static final List<Terrain> terrains = new ArrayList<Terrain>(){ //would be unnecessary if terrain had int values
		{
			add(Terrain.SNOW); 
			add(Terrain.FLOOR);
			add(Terrain.GRASS);
			add(Terrain.DIRT);
			add(Terrain.TESTTILE);
		}
	};
	
	/**
	 * Creates a new StoredGame and loads the parsed XML into it.
	 */
	public LoadHandler() {
		System.out.println("New LoadHandler created");
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
			players = new ArrayList<>();
		} else if(qName.equalsIgnoreCase("player")){
			playerLoad = true;
		} else if(qName.equalsIgnoreCase("inventory")){
			inventoryLoad = true;
		} else if(qName.equalsIgnoreCase("board")){
			boardLoad = true;
		} else if(qName.equalsIgnoreCase("tile")){
			tileLoad = true;
		}
	}
	
	/** 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		charDepth = start;
		if(playerLoad == true){
			int team = Integer.parseInt(new String(ch, charDepth, 1));
			charDepth=charDepth+2;
			Location loc = parseLoc(ch,charDepth);
			String name = new String(ch, charDepth,length-9);
			curPlayer = new Player(team, loc, name);
			playerLoad = false;
			
		} else if(tileLoad == true){
			int terrain = Integer.parseInt(new String(ch, charDepth, 1));
			charDepth=charDepth+2;
			Location loc = parseLoc(ch,charDepth);
			String name = new String(ch, charDepth,length-10);
			System.out.println("NAME HERE!!!!!! "+name);
			InanimateEntity entity = null;
			if (name.equals("tree"))
				entity = new Furniture("default tree", Objects.TREE);
			curTile = new Tile(loc,terrains.get(terrain),entity);
			tileLoad = false;
			
		} else if(boardLoad == true){
			Location boardSize = parseLoc(ch,charDepth);
			tiles = new Tile[boardSize.x][boardSize.y];
			boardLoad = false;
		}
	}
	
	private Location parseLoc(char[] str, int start){
		int x = Integer.parseInt(new String(str, start, 3));
		int y = Integer.parseInt(new String(str, start+4, 3));
		charDepth= charDepth+8;
		return new Location(x, y);
	}
	
	
	/** 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("end element	 : " + qName);
		if(qName.equalsIgnoreCase("game")){
			//GAME IS COMPLETELY LOADED!!!! (Ideally)
		} else if(qName.equalsIgnoreCase("players")){
			//Players are loaded
		} else if(qName.equalsIgnoreCase("player")){ //curPlayer is finished
			players.add(curPlayer);
			System.out.println("ADDED PLAYER: "+curPlayer.name+" team:"+curPlayer.getTeam()+" location:"+curPlayer.getLocation().x+","+curPlayer.getLocation().y);
		} else if(qName.equalsIgnoreCase("inventory")){
			
		} else if(qName.equalsIgnoreCase("board")){
			board = new Board(tiles);
		} else if(qName.equalsIgnoreCase("tile")){
			tiles[curTile.getCoords().x][curTile.getCoords().y] = curTile;
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
	 * Returns a fully loaded StoredGame file containing a Board and Player list
	 * @return 
	 */
	public StoredGame buildGame() {
		return new StoredGame(board, players);
	}
}
