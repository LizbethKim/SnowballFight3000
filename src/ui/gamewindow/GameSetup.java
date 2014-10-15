package ui.gamewindow;

import gameworld.game.Time;
import gameworld.game.server.ServerGame;
import gameworld.world.Board;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.Server;
import storage.LoadGame;
import ui.popups.ControlsPopup;
import ui.popups.InputPopup;
import ui.popups.LoadPopup;

/**
 * The GameSetup is a panel displayed at the start of the game and is
 * responsible for starting up the game, getting the necessary inputs from the
 * user about the server and player name
 *
 * @author Ryan Burnell, 300279172
 *
 */
public class GameSetup extends JPanel {

	private enum ButtonSelected {
		NEW, LOAD, SERVER, CONTROLS
	}

	// constants
	private static final Image WELCOME_IMAGE = HUDPanel
			.loadImage("NewWelcomeImage.png");
	private static final double BUTTON_X_START_PROPORTION = 1.0 / 4.0;
	private static final double BUTTON_WIDTH_PROPORTION = 1.0 / 2.0;
	private static final double BUTTON_HEIGHT_PROPORTION = 1.0 / 10.0;

	private static final double NEW_PLAYER_Y_PROPORTION = 3.0 / 8.0;
	private static final double LOAD_PLAYER_Y_PROPORTION = 4.0 / 8.0;
	private static final double START_SERVER_Y_PROPORTION = 5.0 / 8.0;
	private static final double CONTROLS_Y_PROPORTION = 6.0 / 8.0;

	private final double aspectRatio;
	private ButtonSelected selected;
	private UI ui;

	public GameSetup(UI ui, double aspectRatio) {
		this.ui = ui;
		this.aspectRatio = aspectRatio;
		setupListeners();
	}

	/**
	 * sets up the mouse and mouse motion listeners for the panel
	 */
	private void setupListeners() {
		addMouseMotionListener(new MotionListener());
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e.getX(), e.getY());
			}
		});
	}

	/**
	 * Responds to the button at the given point (x,y)
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	private void dealWithClick(int x, int y) {
		// check which button was clicked and respond
		if (onNewPlayer(x, y)) {
			new InputPopup(ui, false);
		} else if (onLoadPlayer(x, y)) {
			new InputPopup(ui, true);
		} else if (onStartServer(x, y)) {
			startServer();
		} else if (onControls(x, y)) {
			// show a controls popup
			new ControlsPopup().showControls();
		}
	}

	/**
	 * starts a new server
	 */
	private void startServer() {
//		JFileChooser fileChooser = new JFileChooser();
//		// display the dialog and save the file if valid one selected
//		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
//			File file = fileChooser.getSelectedFile();
//			LoadGame loader = new LoadGame();
//		// make new server
//		ServerGame g = new ServerGame(loader.loadGame(file));
		ServerGame g = new ServerGame(Board.defaultBoard());

		Server server = new Server(g);

		// start server connection accepting thread
		new Thread(server).start();
		new Thread(new Time(g)).start();
		// let the user know the server was started
		JOptionPane.showMessageDialog(null, "Server Started");
		//}
	}

	/**
	 * Get the bounds of a button with the given x and y proportions
	 *
	 * @param xPosition
	 * @param yPosition
	 * @return a rectangle representing the bounds of the button
	 */
	private Rectangle getButtonBounds(double xPosition, double yPosition) {
		// get the bounds of the button relative to the window size
		int relativeXPos = (int) (xPosition * getWidth());
		int relativeYPos = (int) (yPosition * getHeight());
		int width = (int) (BUTTON_WIDTH_PROPORTION * getWidth());
		int height = (int) (BUTTON_HEIGHT_PROPORTION * getHeight());

		// now return
		return new Rectangle(relativeXPos, relativeYPos, width, height);
	}

	/**
	 * highlights the selected button by drawing a square around it
	 *
	 * @param g
	 */
	private void highlightButton(Graphics g) {
		Rectangle bounds = null;
		g.setColor(Color.red);

		// check which button is selected
		if (selected == ButtonSelected.NEW) {
			bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
					NEW_PLAYER_Y_PROPORTION);
		} else if (selected == ButtonSelected.LOAD) {
			bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
					LOAD_PLAYER_Y_PROPORTION);
		} else if (selected == ButtonSelected.SERVER) {
			bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
					START_SERVER_Y_PROPORTION);
		} else if (selected == ButtonSelected.CONTROLS) {
			bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
					CONTROLS_Y_PROPORTION);
		}

		// if there is a selected button
		if (bounds != null) {
			// draw three adjacent rectangles around it
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
			g.drawRect(bounds.x - 1, bounds.y - 1, bounds.width + 2,
					bounds.height + 2);
			g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 2,
					bounds.height - 2);
			// now repaint
			repaint();
		}
	}

	/**
	 * changes the cursor to a hand and selects a button when hovered over
	 * @param x x position of the mouse
	 * @param y y position of the mouse
	 */
	private void updateSelection(int x, int y) {
		//check which button is at the point x,y and select it and update cursor
		if (onNewPlayer(x, y)) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			selected = ButtonSelected.NEW;
		} else if (onLoadPlayer(x, y)) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			selected = ButtonSelected.LOAD;
		} else if (onStartServer(x, y)) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			selected = ButtonSelected.SERVER;
		} else if (onControls(x, y)) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			selected = ButtonSelected.CONTROLS;
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			selected = null;
		}
		repaint();
	}

	/**
	 * returns whether the given point is on the new player button
	 * @param xPos the x coordinate
	 * @param yPos the y coordinate
	 * @return true if the point is on the button, otherwise false
	 */
	private boolean onNewPlayer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
				NEW_PLAYER_Y_PROPORTION);
		return bounds.contains(xPos, yPos);
	}

	/**
	 * returns whether the given point is on the load player button
	 * @param xPos the x coordinate
	 * @param yPos the y coordinate
	 * @return true if the point is on the button, otherwise false
	 */
	private boolean onLoadPlayer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
				LOAD_PLAYER_Y_PROPORTION);
		return bounds.contains(xPos, yPos);
	}

	/**
	 * returns whether the given point is on the start server button
	 * @param xPos the x coordinate
	 * @param yPos the y coordinate
	 * @return true if the point is on the button, otherwise false
	 */
	private boolean onStartServer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
				START_SERVER_Y_PROPORTION);
		return bounds.contains(xPos, yPos);
	}

	/**
	 * returns whether the given point is on the controls button
	 * @param xPos the x coordinate
	 * @param yPos the y coordinate
	 * @return true if the point is on the button, otherwise false
	 */
	private boolean onControls(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(BUTTON_X_START_PROPORTION,
				CONTROLS_Y_PROPORTION);
		return bounds.contains(xPos, yPos);
	}

	/**
	 * paints the welcome image and highlights a button if selected
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(WELCOME_IMAGE, 0, 0, getHeight(), getWidth(), null);
		highlightButton(g);
	}

	/**
	 * mouse motion listener that updates button selection and cursor
	 * @author Ryan Burnell, 300279172
	 *
	 */
	private class MotionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateSelection(e.getX(), e.getY());
		}

	}

}
