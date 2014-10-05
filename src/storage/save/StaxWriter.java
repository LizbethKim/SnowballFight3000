/**
 * 
 */
package storage.save;

import gameworld.world.Board;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Team;
import gameworld.world.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import storage.StoredGame;

/**
 * @author Kate Henderson
 *
 */

public class StaxWriter {

	private static final String GAME = "game";
	private static final String PLAYERS = "players";
	private static final String PLAYER = "player";
	private static final String BOARD = "board";
	private static final String TILE = "tile";
	private static final String INVENTORY= "inventory";

	//private static final String DELIMITER = "\\s+";
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String NEWLINE = "\n";
	private static final String TAB = "\t";

	private String filename = "src/storage/";
	private Board board;
	private List<Player> players;

	public String saveGame(StoredGame g){
		
		filename = filename + Long.toString(System.currentTimeMillis())+".xml"; //filename is current time in milliseconds
		board = g.getBoard();
		players = g.getPlayers();
		try {
			// create an XMLOutputFactory
			OutputStream out = new FileOutputStream(new File(filename));
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLEventWriter eventWriter;

			eventWriter = outputFactory.createXMLEventWriter(out);
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent newline = eventFactory.createDTD(NEWLINE); //use for newlines
			
			// open game tag
			eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, GAME));
			eventWriter.add(newline);
			
			if(players!=null){
				// open players tag
				eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, PLAYERS));
				eventWriter.add(newline);
				
				// create player start and end elements
				StartElement playerStartElement = eventFactory.createStartElement(EMPTY,EMPTY, PLAYER);
				EndElement playerEndElement = eventFactory.createEndElement(EMPTY,EMPTY, PLAYER);
				
				for(Player p :players){
					eventWriter.add(playerStartElement); //open new player tag
					Characters playerContents = eventFactory.createCharacters(buildPlayerString(p));
					eventWriter.add(playerContents);
					createTag(eventWriter,INVENTORY,p.getInventory().toString()); //KH for real inventory implementation
					eventWriter.add(playerEndElement);
					eventWriter.add(newline);
				}
				
				// close players tag
				eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, PLAYERS));
				eventWriter.add(newline);
			}
			
			if(board!=null){
				// open board tag
				eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, BOARD));
				//add board size
				int xMax = board.getXMax();
				int yMax = board.getYMax();
				Characters boardSize = eventFactory.createCharacters(String.format("%03d %03d", xMax, yMax)+NEWLINE);
				eventWriter.add(boardSize);
				
				//for each tile, make its tag and fill it
				for(int x=0;x<xMax;x++){
					for(int y=0;y<xMax;y++){
						Tile t = board.tileAt(new Location(x,y));
						String tileContents = buildTileString(t);
						createTag(eventWriter,TILE,tileContents);
					}
				}
				
				// close board tag
				eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, BOARD));
				eventWriter.add(newline);
			}
			
			//end the game tag
			eventWriter.add(eventFactory.createEndElement(EMPTY, EMPTY, GAME));
			eventWriter.add(newline);
			
			//end the document
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.close();
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filename;
	}


	/**
	 * @param t
	 * @return
	 */
	private String buildTileString(Tile t) {
		StringBuilder str = new StringBuilder();
		switch (t.getType()){
			case SNOW:
				str.append(0);break;
			case FLOOR:
				str.append(1);break;
			case GRASS:
				str.append(2);break;
			case DIRT:
				str.append(3);break;
			default:
				str.append(0);break;
		}
		str.append(String.format(" %03d %03d ", t.getCoords().x, t.getCoords().y));
		//KH add things for on tile items
		return str.toString();
	}


	/**
	 * @param p
	 */
	private String buildPlayerString(Player p) {
		StringBuilder str = new StringBuilder();
		if(p.getTeam().equals(Team.values()[0])){
			str.append(0 + SPACE);
		}else if(p.getTeam().equals(Team.values()[1])){
			str.append(1 + SPACE);
		}
		str.append(String.format("%03d %03d ", p.getLocation().x, p.getLocation().y));
		str.append(p.name+NEWLINE);
		return str.toString();	
	}
	
	
	/**
	 * Used for creating innermost tags, like tile and inventory
	 * @param eventWriter
	 * @param name
	 * @param value
	 * @throws XMLStreamException
	 */
	private void createTag(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent newline = eventFactory.createDTD(NEWLINE);
		XMLEvent tab = eventFactory.createDTD(TAB);
		
		// open tag
		StartElement sElement = eventFactory.createStartElement(EMPTY, EMPTY, name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		
		// save the characters of content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		
		// end tag
		EndElement eElement = eventFactory.createEndElement(EMPTY, EMPTY, name);
		eventWriter.add(eElement);
		eventWriter.add(newline);

	}


} 

