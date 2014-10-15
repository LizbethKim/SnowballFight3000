/**
 * 
 */
package storage.tests;

import gameworld.world.Board;
import gameworld.world.Chest;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Team;
import gameworld.world.Tile;
import graphics.assets.Terrain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import storage.SaveGame;
import storage.StoredGame;
import storage.save.StaxWriter;
/**
 * @author Katherine Henderson 300279264
 *
 */
public class SaveTests {
	private SaveGame saver = new SaveGame();
	private StaxWriter writer;
	
	@Test
	public void saveTest1(){
		
	}
	
	public File buildGame(int xMax,int yMax){
		Tile[][] tiles = new Tile[xMax][yMax];
		for(int x=0; x<xMax; x++){
			for(int y=0; y<yMax; y++){
				tiles[x][y] = new Tile(new Location(x, y), Terrain.GRASS, null);
			}
		}
		tiles[1][1].place(new Chest("this is here", 1, false));
		tiles[3][2].place(new Chest("this is here", 4, true));
		tiles[2][1].place(new Key("it's a key", 1));
		Board b = new Board(tiles);
		List<Player> ps = new ArrayList<Player>();
		ps.add(new Player("defaultName", Team.BLUE, 1, new Location(1,2)));
		//StoredGame game = new StoredGame(b, ps); 
	//	File file = new File()
	//	saver.save(buildGame(5, 5),);
		//new StoredGame(b, ps);
		return new File("file");
	}
	
}
