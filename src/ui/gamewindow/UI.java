package ui.gamewindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import gameworld.game.client.ClientGame;
import graphics.GraphicsPane;
import ui.actions.*;
import ui.popups.CheatsPopup;
import ui.popups.ControlsPopup;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * The UI class encompasses the application window to display the game. It
 * listens to all user inputs and sends them to the board where they can be
 * dealt with. It holds the canvas for the game to be displayed on and controls
 * the pop-up windows related to the game.
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class UI extends JFrame {

	public static final int DEFAULT_GAME_WIDTH = 600;
	public static final int DEFAULT_GAME_HEIGHT = 600;
	public static final double ASPECT_RATIO = 1.0 * DEFAULT_GAME_WIDTH
			/ DEFAULT_GAME_HEIGHT;

	// Fields
	private ClientGame client;
	private HUDPanel hudPanel;
	private JPanel graphicsPanel;
	private JLayeredPane gamePanel;
	private CheatsPopup cheatsPopup;
	private ControlsPopup controlsPopup;
	private GameSetup gameSetup;
	private JFrame frame;

	private final boolean debugMode = false;

	public UI() {

		// initialise UI
		super();
		setupWelcome();
	}

	public void startGame(String name, String IP, gameworld.world.Team t) {
		client = new ClientGame(name, IP, t, this);
		// setupGamePanel();
		setupFileBar();
		setupKeyBindings();
		setupGamePanel();
		setupHUD();
		setupGraphics();
		// pack and Display window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);

		frame.setVisible(false);
		frame.dispose();
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	private void setupGamePanel() {
		gamePanel = new JLayeredPane();
		gamePanel.setPreferredSize(new Dimension(DEFAULT_GAME_WIDTH,
				DEFAULT_GAME_HEIGHT));
		setupResizing();
		add(gamePanel);
	}

	private void setupWelcome() {
		frame = new JFrame();
		frame.setSize(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT);
		gameSetup = new GameSetup(this, ASPECT_RATIO);
		frame.add(gameSetup);
		gameSetup.setPreferredSize(new Dimension(DEFAULT_GAME_WIDTH,
				DEFAULT_GAME_WIDTH));
		frame.pack();
		// setResizable(true);
		frame.setVisible(true);
	}

	private void setupGraphics() {
		graphicsPanel = new GraphicsPane(client.getBoard());
		gamePanel.add(graphicsPanel);
		graphicsPanel.setBounds(0, 0, DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT);
	}

	/**
	 * method to set up the HUD
	 */
	private void setupHUD() {
		hudPanel = new HUDPanel(client, ASPECT_RATIO);
		gamePanel.add(hudPanel);
		hudPanel.setOpaque(false);
		// hudPanel.setSize(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT);
		hudPanel.setupInventory(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT);
		// hudPanel.setPreferredSize(new Dimension(DEFAULT_GAME_WIDTH,
		// DEFAULT_GAME_HEIGHT));

	}

	private void setupResizing() {
		gamePanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeToRatio(e);
			}
		});
	}

	private void resizeToRatio(ComponentEvent e) {

		int width = e.getComponent().getWidth();
		int height = e.getComponent().getHeight();

		Dimension newSize = getMaximumAspectSize(width, height);
		int xBorder = (width - newSize.width) / 2;
		int yBorder = (height - newSize.height) / 2;
		setGameBounds(xBorder, yBorder, newSize.width, newSize.height);
		// hudPanel.setBounds(xBorder, yBorder, newSize.width,newSize.height);
	}

	private void setGameBounds(int x, int y, int width, int height) {
		for (Component c : gamePanel.getComponents()) {
			c.setBounds(x, y, width, height);
		}
	}

	public void endGame(){
		JOptionPane.showMessageDialog(this, "Game Over! Final score: "+client.getPlayerScore());
		System.exit(EXIT_ON_CLOSE);
	}
	
	private Dimension getMaximumAspectSize(int width, int height) {
		if (width / ASPECT_RATIO < height) {
			return new Dimension(width, (int) (width / ASPECT_RATIO));
		} else {
			return new Dimension((int) (height * ASPECT_RATIO), height);
		}
	}

	/**
	 * sets up the window to respond to key presses using a key binding system
	 */
	private void setupKeyBindings() {
		// sets the window to respond to inputs from any internal components

		// add arrow keys and WASD for movement
		addKeyBinding("MoveRight", new MoveRight(client, this),
				KeyEvent.VK_RIGHT, KeyEvent.VK_D);
		addKeyBinding("MoveLeft", new MoveLeft(client, this), KeyEvent.VK_LEFT,
				KeyEvent.VK_A);
		addKeyBinding("MoveUp", new MoveUp(client, this), KeyEvent.VK_UP,
				KeyEvent.VK_W);
		addKeyBinding("MoveDown", new MoveDown(client, this), KeyEvent.VK_DOWN,
				KeyEvent.VK_S);

		// player action keys
		addKeyBinding("ThrowSnowball", new ThrowSnowball(client, this),
				KeyEvent.VK_SPACE);
		addKeyBinding("Inspect", new InspectItem(client, this), KeyEvent.VK_I);
		addKeyBinding("OpenChest", new InteractWithItem(client, this),
				KeyEvent.VK_X);
		addKeyBinding("PickupItem", new PickupItem(client, this), KeyEvent.VK_Z);
		addKeyBinding("UseInventoryItem", new UseItem(client, this),
				KeyEvent.VK_R);
		addKeyBinding("DropItem", new DropItem(client, this),
				KeyEvent.VK_F);

		// view rotation keys
		addKeyBinding("RotateClockwise", new RotateClockwise(client, this),
				KeyEvent.VK_E);
		addKeyBinding("RotateAntiClockwise", new RotateAntiClockwise(client,
				this), KeyEvent.VK_Q);

		// setup item hotkeys
		addKeyBinding("SelectItem1", new SelectItem(client, this, 1),
				KeyEvent.VK_1);
		addKeyBinding("SelectItem2", new SelectItem(client, this, 2),
				KeyEvent.VK_2);
		addKeyBinding("SelectItem3", new SelectItem(client, this, 3),
				KeyEvent.VK_3);
		addKeyBinding("SelectItem4", new SelectItem(client, this, 4),
				KeyEvent.VK_4);
		addKeyBinding("SelectItem5", new SelectItem(client, this, 5),
				KeyEvent.VK_5);
		addKeyBinding("SelectItem6", new SelectItem(client, this, 6),
				KeyEvent.VK_6);
		addKeyBinding("SelectItem7", new SelectItem(client, this, 7),
				KeyEvent.VK_7);
		addKeyBinding("SelectItem8", new SelectItem(client, this, 8),
				KeyEvent.VK_8);
		addKeyBinding("SelectItem9", new SelectItem(client, this, 9),
				KeyEvent.VK_9);

	}

	/**
	 * Sets up key bindings for given keyConstants
	 * 
	 * @param actionString
	 *            the action string to be used in the action map
	 * @param action
	 *            the action to be tied to the keys
	 * @param keyConstants
	 *            the key constants to tie to the action string
	 */
	private void addKeyBinding(String actionString, KeyAction action,
			int... keyConstants) {

		// get the input and action maps
		InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getRootPane().getActionMap();

		// tie each key constant to the action string
		for (int key : keyConstants) {
			im.put(KeyStroke.getKeyStroke(key, 0), actionString);
		}
		// now tie the action to the action string
		am.put(actionString, action);
	}

	private void saveGame() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			client.save();
			// KTC save to file client.save(file) or equivalent, change to
			// whatever when implemented
		}
	}

	/**
	 * Sets up the file menu and listeners
	 */
	private void setupFileBar() {

		// setup menu and items
		JMenuBar menu = new JMenuBar();
		menu.setFocusable(false);
		final JMenu fileMenu = new JMenu("File");
		final JMenu helpMenu = new JMenu("Help");

		final JMenuItem newGame = new JMenuItem("New Game");
		final JMenuItem save = new JMenuItem("Save Game");
		// final JMenuItem load = new JMenuItem("Load Game");
		final JMenuItem quit = new JMenuItem("Quit");
		final JMenuItem controls = new JMenuItem("Show Controls");
		final JMenuItem cheats = new JMenuItem("Enable Cheats");
		cheatsPopup = new CheatsPopup(client);
		controlsPopup = new ControlsPopup();

		// add menu and items
		menu.add(fileMenu);
		fileMenu.add(newGame);
		fileMenu.add(save);
		fileMenu.add(quit);

		menu.add(helpMenu);
		helpMenu.add(controls);
		helpMenu.add(cheats);

		setJMenuBar(menu);
		// setup listeners for the menu
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});

		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// RB: client.newGame()?
			}
		});

		controls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlsPopup.showControls();
			}
		});

		cheats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cheatsPopup.showCheats();
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
	}

	// public static void main(String[] args) {
	// try {
	// for (UIManager.LookAndFeelInfo info : UIManager
	// .getInstalledLookAndFeels()) {
	// if ("Nimbus".equals(info.getName())) {
	// UIManager.setLookAndFeel(info.getClassName());
	// break;
	// }
	//
	// }
	// } catch (Exception e) {
	// // do nothing
	// }
	// UI tester = new UI(new Client(1));
	// }

}
