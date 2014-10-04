package ui;

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

import client.ClientGame;

public class GameSetup extends JPanel {

	private static final Image welcomeImage = HUDPanel
			.loadImage("WelcomeImage.png");
	private static final double buttonXStartProportion = 1.0 / 4.0;
	private static final double buttonWidthProportion = 1.0 / 2.0;
	private static final double buttonHeightProportion = 1.0 / 7.0;

	private static final double startGameYStartProportion = 3.0 / 7.0;
	private static final double controlsYStartProportion = 4.0 / 7.0;
	private static final double helpYStartProportion = 5.0 / 7.0;

	private final double aspectRatio;

	public GameSetup(double aspectRatio) {
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
		if(onStartGame(x,y)){
			new InputPopup();
		} else if(onControls(x,y)){
			
		} else if(onHelp(x,y)){
			
		}
	}
	
	private void updateCursor(int x, int y) {
		if(onStartGame(x,y) || onControls(x,y) || onHelp(x, y)){
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
			
	}

	private boolean onStartGame(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (startGameYStartProportion * getHeight());
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

	private boolean onHelp(int xPos, int yPos) {
		int x = (int) (buttonXStartProportion * getWidth());
		int y = (int) (helpYStartProportion * getHeight());
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
