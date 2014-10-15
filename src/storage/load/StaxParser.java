package storage.load;

import gameworld.world.*;
import graphics.assets.Entities;
import graphics.assets.Terrain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import storage.StoredGame;
import storage.XMLValues;



/**
 * Handles the parsing of the XML file using StAX into a board and player list, ready to be loaded in game
 * @author Katherine Henderson 300279264
 *
 */
public class StaxParser {

	private Board board;
	private Tile[][] tiles;
	private List<Player> players;
	private List<Area> areaList;

	private Player curPlayer;
	private Tile curTile;
	private Inventory curInven;
	private Area curArea;

	private boolean playerLoad=false;
	private boolean tileLoad=false;
	private boolean chestLoad=false;
	private int areaCount=0;




	/**
	 * Parses the given file into a StoredGame ready to be played
	 * @param file
	 */
	public StoredGame parse(File file) {
		try {
			// XMLInputFactory, used to process input
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Starts streaming the file
			InputStream in = new FileInputStream(file);
			//Make the event reader to detect the XML
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					//The event is a start element, so figure out what tag it is
					StartElement startElement = event.asStartElement();
					String elemName = startElement.getName().getLocalPart();

					if (elemName.equals(XMLValues.GAME)) {
						//Could add Name of the game or something here
						continue;
					}
					//Start of list of Players
					if (elemName.equals(XMLValues.PLAYERS)) {
						players = new ArrayList<>();
						continue;
					}

					//Start of a new Player, contains its team, location, and name in its Characters
					if (elemName.equals(XMLValues.PLAYER)) {
						//get the characters within the tag
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(XMLValues.DELIMITER);
						//Parse the player values from the Characters
						curPlayer = parsePlayer(values);
						continue;
					}

					//Start of the Board, just contains size values as characters,
					//and all the tiles are within the tag
					if (elemName.equals(XMLValues.BOARD)) {
						//get the characters within the tag
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(XMLValues.DELIMITER);
						// parse the size of the board, to know how big the array should be
						Location boardSize = parseLoc(values[0],values[1]);
						// create the board
						tiles = new Tile[boardSize.x][boardSize.y];
						// initialise areas
						areaList = new ArrayList<Area>();
						continue;
					}

					//Start of a tile tag, contains a terrain value and location
					if (elemName.equals(XMLValues.TILE)) {
						//get the characters within the tag
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(XMLValues.DELIMITER);
						//parse tile from characters
						curTile = parseTile(values);
						continue;
					}

					//Start of a new area
					if (elemName.equals(XMLValues.AREA)) {
						//get the characters within the tag
						event = eventReader.nextEvent();
						String data = event.asCharacters().getData();
						System.out.println("DATA: "+data.length());
						String[] values = data.split(XMLValues.DELIMITER);
						curArea = parseArea(values);
						continue;
					}

					//Start of an inventory tag, this could be a Chest on a tile or a PlayerInventory.
					//Just anything of the Inventory class works
					if (elemName.equals(XMLValues.INVENTORY)) {
						//if playerLoad is true then it's a PlayerInventory, so get curPlayer inventory
						if(playerLoad){
							curInven = (PlayerInventory) curPlayer.getInventory();
						}
						//If tileLoad is true then it's a chest/bag on a tile.
						else if(tileLoad){
							event = eventReader.nextEvent();
							String[] values = event.asCharacters().getData().split(XMLValues.DELIMITER);
							//uses loadEntity because both chest and Bag are InanimateEntitys
							curInven = (Inventory)parseEntitiy(values);
						}
						continue;
					}
					//Start of an item tag. always uses loadEntity
					if (elemName.equals(XMLValues.ITEM)) {
						//get the characters within the tag
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(XMLValues.DELIMITER);
						//calls loadEntity to parse the item
						InanimateEntity item = parseEntitiy(values);
						if(chestLoad){
							//still loading the contents of a chest, so add it to that
							curInven.addItem((Item)item);
						}else if(playerLoad){
							//loading a player, so puts it in their inventory.
							curInven.addItem((Item)item);
						}else if(tileLoad){
							//Loading items on a tile, but not within a chest, so add to the tile
							curTile.place(item);
						}
						continue;
					}

				}

				//An end element of a tag
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					String elemName = endElement.getName().getLocalPart();
					//End of a player, so add it to the players list
					if (elemName.equals(XMLValues.PLAYER)) {
						players.add(curPlayer);
						//disable playerLoad, to stop it loading this players inventory
						playerLoad=false;
						continue;
					}
					//End of the inventory/chest/bag etc
					if (elemName.equals(XMLValues.INVENTORY)){
						//check if its a chest on a tile
						if(chestLoad==true && tileLoad==true){
							//place it on the tile and disable chestLoad
							if(curInven instanceof Chest){
								curTile.place((Chest)curInven);
							}else{
								curTile.place((Bag)curInven);
							}
							chestLoad=false;
						}
						continue;
					}

					//End of a tile, adds it to the board and disables tileLoad
					if (elemName.equals(XMLValues.TILE)) {
						tileLoad = false;
						tiles[curTile.getCoords().x][curTile.getCoords().y] = curTile;
						continue;
					}

					//end of an area, add it to the list
					if (elemName.equals(XMLValues.AREA)) {
						if(areaCount>=3){
							areaList.add(curArea);
						}
						continue;
					}

					// end of a board element, add the tiles and areas
					if (elemName.equals(XMLValues.BOARD)) {
						board = new Board(tiles, areaList);
						continue;
					}

					//The game should be finished loading, it will ignore excess info past this
					if (elemName.equals(XMLValues.GAME)) {
						System.out.println("GAME LOADED");
						break;
					}

				}

			}
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
		//returns the built game
		return new StoredGame(board, players);

	}

	/**
	 * Parse an area, just has it's type and then the cooridnates of the tiles
	 * @param values Area data
	 * @return area Area loaded
	 */
	private Area parseArea(String[] values) {
		Area a;
		if(areaCount==2 ||areaCount==1){
			a = curArea;
		}else if(values[0].equals("NOTEAM")){
			//just a plain area
			a = new Area();
		}else{
			//spawn area, give it its team name
			a = new SpawnArea(Team.valueOf(values[0]));
		}

		int i;
		System.out.println(values.length);
		for(i = 1;i<values.length;i=i+2){
			Location loc = parseLoc(values[i], values[i+1]);
			a.add(tiles[loc.x][loc.y]);
		}
		System.out.println("FINAL i VALUE:"+i);
		areaCount++;
		return a;
	}

	/**
	 * Parse a tile, returns the Tile and activates tileLoad. Does not load it's on Object yet
	 * @param values Tile data
	 * @return tile loaded Tile object
	 */
	private Tile parseTile(String[] values){
		// turn tileLoad on, to differentiate between inventories
		tileLoad=true;
		Location loc = parseLoc(values[1],values[2]);
		// create the tile
		return new Tile(loc,Terrain.valueOf(values[0]), null);
	}

	/**
	 * Parse a player, returns the Player and activates playerLoad. Does not load it's inventory yet
	 * @param values player data
	 * @return player loaded Player object
	 */
	private Player parsePlayer(String[] values) {
		//turn player load on, to keep track of what the inventory belongs to etc
		playerLoad=true;
		int teamNum = Integer.parseInt(values[0]);
		Team team = Team.values()[teamNum];
		Location loc = parseLoc(values[1], values[2]);
		//create the player
		return new Player(values[3], team, 10, loc);
	}

	/**
	 * Loading an item from its tag into an actual InanimateEntity. Also loads all its properties.
	 * @param values A string array, usually from the characters within a particular tag
	 * @return entity The item represented as an
	 */
	private InanimateEntity parseEntitiy(String[] values) {
		String name = values[0];
		InanimateEntity item = null;

		if(name.equals(Entities.KEY.name())){
			//Its a Key! parse its description and id
			item = new Key(parseDescription(2, values), Integer.parseInt(values[1]));
		}else if(name.equals(Entities.BAG.name())){
			//bags don't have anything special
			chestLoad = true;
			item = new Bag();
		}else if(name.equals(Entities.CHEST.name())){
			//turn chestLoad on and parse it's description
			chestLoad = true;
			item = new Chest(parseDescription(1, values), Integer.parseInt(values[1]), Boolean.parseBoolean(values[2]));
		}else if(name.equals(Entities.REDFLAG.name())){
			//new Flag for the redteam
			item = new Flag(Team.RED);
		}else if(name.equals(Entities.BLUEFLAG.name())){
			//new flag for the blue team
			item = new Flag(Team.BLUE);
		}else if(name.equals(Entities.DOOREW.name())){
			//parse its description, id. It's direction will be east/west
			item = new Door(parseDescription(3, values), Direction.EAST, Integer.parseInt(values[1]),Boolean.parseBoolean(values[2]));
		}else if(name.equals(Entities.DOORNS.name())){
			//parse its description, id. It's direction will be north/south
			item = new Door(parseDescription(3, values), Direction.NORTH, Integer.parseInt(values[1]),Boolean.parseBoolean(values[2]));
		}else if(name.equals(Entities.POWERUP.name()) || name.equals(Entities.HEALTH.name())){
			//It's some kind of powerup/health, just give it it's power enum
			item = new Powerup(Powerup.Power.valueOf(values[1]));
		}else{
			//It's just another piece of furniture, parse it's description and type enum.
			Entities type = Entities.valueOf(name);
			item = new Furniture(parseDescription(1, values), type);
		}

		return item;
	}


	/**
	 * Parses the leftover part of the values array into a single string. Used to load item descriptions.
	 * @param start The starting index of the description
	 * @param values The array of strings to load
	 * @return description A single string description
	 */
	private String parseDescription(int start, String[] values) {
		StringBuilder builder = new StringBuilder();
		for(int i=start; i<values.length; i++) {
			builder.append(values[i]);
		    builder.append(" ");
		}
		return builder.toString();
	}

	/**
	 * Parses two integer strings into a location.
	 * @param xStr String of x value
	 * @param yStr String of y value
	 * @return location new Location of the points
	 */
	private Location parseLoc(String xStr, String yStr){
		int x = Integer.parseInt(xStr);
		int y = Integer.parseInt(yStr);
		return new Location(x, y);
	}
}
