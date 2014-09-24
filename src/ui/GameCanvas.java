package ui;


import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;

import client.Client;

public class GameCanvas extends Canvas{
	private final Client client;

	public GameCanvas(Client cl) {
		this.client = cl;
		super.setCursor(new Cursor(Cursor.CUSTOM_CURSOR));
		
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		HashSet<String> availableNames = new HashSet();

		for (String name : env.getAvailableFontFamilyNames()) {
			availableNames.add(name);
		}
	}
}
