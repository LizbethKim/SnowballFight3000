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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import server.Server;
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
	private ButtonSelected selected;
	private UI ui;

	public GameSetup(UI ui, double aspectRatio) {
		this.ui = ui;
		this.aspectRatio = aspectRatio;
		setupListeners();
	}

	private void setupListeners() {
		addMouseMotionListener(new MotionListener());
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e.getX(), e.getY());
			}
		});
	}

	private void dealWithClick(int x, int y) {
		if (onNewPlayer(x, y)) {
			new InputPopup(ui, false);
		} else if (onLoadPlayer(x, y)) {
			new InputPopup(ui, true);
		} else if (onStartServer(x, y)) {
			startServer();
		} else if (onControls(x, y)) {
			new ControlsPopup().showControls();
		}
	}

	private void startServer() {
		ServerGame g = new ServerGame(Board.defaultBoard());
		Server server = new Server(g);
		// start server connection accepting thread
		new Thread(server).start();
		new Thread(new Time(g)).start();
		JOptionPane.showMessageDialog(null, "Server Started");
	}

	private Rectangle getButtonBounds(double xProportion, double yProportion) {
		int xPos = (int) (xProportion * getWidth());
		int yPos = (int) (yProportion * getHeight());
		int width = (int) (buttonWidthProportion * getWidth());
		int height = (int) (buttonHeightProportion * getHeight());
		return new Rectangle(xPos, yPos, width, height);
	}

	private void highlightButton(Graphics g) {
		Rectangle bounds = null;
		g.setColor(Color.red);
		if (selected == ButtonSelected.NEW) {
			bounds = getButtonBounds(buttonXStartProportion,
					newPlayerYStartProportion);
		} else if (selected == ButtonSelected.LOAD) {
			bounds = getButtonBounds(buttonXStartProportion,
					loadPlayerYStartProportion);
		} else if (selected == ButtonSelected.SERVER) {
			bounds = getButtonBounds(buttonXStartProportion,
					startServerYStartProportion);
		} else if (selected == ButtonSelected.CONTROLS) {
			bounds = getButtonBounds(buttonXStartProportion,
					controlsYStartProportion);
		}

		if (bounds != null) {
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
			g.drawRect(bounds.x - 1, bounds.y - 1, bounds.width + 2,
					bounds.height + 2);
			g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 2,
					bounds.height - 2);
			repaint();
		}
	}

	private void updateCursor(int x, int y) {
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

	private boolean onNewPlayer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(buttonXStartProportion,
				newPlayerYStartProportion);
		return bounds.contains(xPos, yPos);
	}

	private boolean onLoadPlayer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(buttonXStartProportion,
				loadPlayerYStartProportion);
		return bounds.contains(xPos, yPos);
	}

	private boolean onStartServer(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(buttonXStartProportion,
				startServerYStartProportion);
		return bounds.contains(xPos, yPos);
	}

	private boolean onControls(int xPos, int yPos) {
		Rectangle bounds = getButtonBounds(buttonXStartProportion,
				controlsYStartProportion);
		return bounds.contains(xPos, yPos);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(welcomeImage, 0, 0, getHeight(), getWidth(), null);
		highlightButton(g);
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
