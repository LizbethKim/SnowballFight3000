package ui;

import gameworld.game.Time;
import gameworld.game.client.ClientGame;
import gameworld.game.server.ServerGame;
import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import server.Server;

public class GameSetup extends JPanel {

	private static final Image welcomeImage = HUDPanel
			.loadImage("NewWelcomeImage.png");
	private static final double buttonXStartProportion = 1.0 / 4.0;
	private static final double buttonWidthProportion = 1.0 / 2.0;
	private static final double buttonHeightProportion = 1.0 / 10.0;

	private static final double newPlayerYStartProportion = 3.0 / 8.0;
	private static final double loadPlayerYStartProportion = 4.0 / 8.0;
	private static final double startServerYStartProportion = 5.0 / 8.0;
	private static final double controlsYStartProportion = 6.0 / 8.0;
	
	

	private final double aspectRatio;
	
	private UI ui;

	public GameSetup(UI ui, double aspectRatio) {
		this.ui = ui;
		this.aspectRatio = aspectRatio;
		setupListeners();
	}

	private void setupListeners() {
		addMouseMotionListener(new MotionListener());
		addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e){
				dealWithClick(e.getX(), e.getY());
			}
		});
	}

	private void dealWithClick(int x, int y){
		if(onNewPlayer(x,y)){
			new InputPopup(ui);
		} else if(onLoadPlayer(x,y)){
			new LoadPopup();
		} else if(onStartServer(x,y)){
			startServer();
		} else if(onControls(x,y)){
			new ControlsPopup().showControls();
		}
	}
	
	private void startServer(){
//		new UI();
	//	ui.setVisible(false);
		ServerGame g = new ServerGame(Board.defaultBoard());
		Server server = new Server(g);
		// start server connection accepting thread
		new Thread(server).start();
		new Thread(new Time(g)).start();
		server.sendLoop();

	}
	
	private void updateCursor(int x, int y) {
		if(onNewPlayer(x,y) || onLoadPlayer(x,y) || onStartServer(x, y) || onControls(x,y)){
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
			
	}

	private boolean onNewPlayer(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (newPlayerYStartProportion * getHeight());
		int width = (int) (buttonWidthProportion * getWidth());
		int height = (int) (buttonHeightProportion * getHeight());
		return new Rectangle(x, y, width, height).contains(xPos, yPos);
	}
	
	private boolean onLoadPlayer(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (loadPlayerYStartProportion * getHeight());
		int width = (int) (buttonWidthProportion * getWidth());
		int height = (int) (buttonHeightProportion * getHeight());
		return new Rectangle(x, y, width, height).contains(xPos, yPos);
	}
	
	private boolean onStartServer(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (startServerYStartProportion * getHeight());
		int width = (int) (buttonWidthProportion * getWidth());
		int height = (int) (buttonHeightProportion * getHeight());
		return new Rectangle(x, y, width, height).contains(xPos, yPos);
	}

	private boolean onControls(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (controlsYStartProportion * getHeight());
		int width = (int) (buttonWidthProportion * getWidth());
		int height = (int) (buttonHeightProportion * getHeight());
		return new Rectangle(x, y, width, height).contains(xPos, yPos);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(welcomeImage, 0, 0, getHeight(), getWidth(), null);
	}

	private class MotionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateCursor(e.getX(), e.getY());
		}

	}

}
