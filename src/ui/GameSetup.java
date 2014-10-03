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

	private static final Image welcomeImage = HUDPanel.loadImage("WelcomeImage.png");
	private static final double maxHealth = 100.0;

	private final double aspectRatio;
	
	public GameSetup(double aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(welcomeImage, 0, 0, getHeight(), getWidth(), null);
	}

}
