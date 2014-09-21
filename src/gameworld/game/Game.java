package gameworld.game;

import ui.UI;
import gameworld.world.Board;

public class Game {
	public static final boolean SERVER = false;
	public static final boolean CLIENT = false;
	public static final int TICK_TIME = 15;
	
	public Game() {
		// TODO Auto-generated constructor stub
	}

	public static void main (String[] args) {
		if (SERVER) {
			// Bryden, this is where I'll need your code
			// runServer(board); (??)
		} else if (CLIENT) {
			// Here too Bryden
			// runClient(); (??)
		} else { // debug single-player testing dev mode
			
			// KATE, this is where I'll need dataloading methods
			Board board = null; // createBoardFromFile(filename);	
			
			singlePlayerGame(board);							
		}
	}
	
	
	/*
	 * Main game loop frame. The idea is that this will just keep going, 
	 * calling repaint on the UI and graphics, and ticking the model 
	 * so it knows to increment projectiles and other time-based things.
	 * Everything else will be event-driven (at least, in my mind). 
	 */
	private static void singlePlayerGame(Board game) {
		
		UI display = new UI(); 	// This is where I'll hook in Ryan's code. 
		// This should create the window etc.
		// Also of course appropriate parameters.
		
		while (true) {	// TODO perhaps change to "playing", allow game to finish.
			// Loop forever			
			try {
				Thread.sleep(TICK_TIME);
				game.clockTick();
				if(display != null) {
					display.repaint();
				}
			} catch(InterruptedException e) {
				// should never happen TODO perhaps do something here
			}			
		}
	}
	
	/*
	 * In terms of client/server, I think the clients should have a tick that
	 * calls repaint on the UI/graphics, and the server should have one that
	 * ticks the model (projectiles, etc). Everything else can happen event-based. (Probably?)
	 */
	

}
