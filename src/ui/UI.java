package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameworld.world.Board;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class UI extends JFrame {
	
	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 500;
	
	//Fields
	private Board board;
	private JPanel buttonPanel;
	private JPanel inventoryPanel;
	private JButton rotateViewLeft;
	private JButton rotateViewRight;
	private Canvas gameCanvas;
	private JLayeredPane gamePanel;
	
	
	/*	=========================================================
	 * 	PLEASE MODIFY
	 * 	This is here so code compiles, I just don't uncompilable 
	 * 	code at the moment. But feel free to rename EVERYTHING,
	 * 	including the package.
	 * 	-Kelsey
	 * 	=========================================================
	 */
	
	public UI() {
		
		//initialise UI
		super();
		setLayout(new BorderLayout());
		
		setupFileBar();
		setupButtonPanel();
		setupGamePanel();
		
		
		
		//set initial size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		setBounds((scrnsize.width - getWidth()) / 2,
				(scrnsize.height - getHeight()) / 2, getWidth(), getHeight());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);

		// Display window
		setVisible(true);
	}
	
	private void setupGamePanel(){
		setupCanvas();
		setupInventoryBar();
		gamePanel = new JLayeredPane();
	//	gamePanel.setLayout(new BorderLayout());
		gamePanel.setPreferredSize(new Dimension(500,500));
		gamePanel.setBorder(BorderFactory.createTitledBorder(
                "Snowball Fight 3000"));
		gamePanel.add(new JLabel("working"), new Integer(1));
		gamePanel.add(gameCanvas, /*BorderLayout.CENTER,*/ new Integer(2));
		gamePanel.add(inventoryPanel, /*BorderLayout.CENTER,*/ new Integer(3));
		gamePanel.moveToFront(inventoryPanel);
		add(gamePanel, BorderLayout.CENTER);
	}
	
	private void setupInventoryBar(){
		
		//setup buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(new JButton("Item"));
		buttonPanel.add(new JButton("Item2"));
		buttonPanel.add(new JButton("Item3"));
		buttonPanel.add(new JButton("Item4"));
		
		inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BorderLayout());
		inventoryPanel.setOpaque(false);
		inventoryPanel.setSize(GAME_WIDTH,GAME_HEIGHT);
		
		inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);
		//add(inventoryPanel, BorderLayout.SOUTH);
	}
	
	private void setupFileBar(){
		
		//setup menu and items
		JMenuBar menu = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem newGame = new JMenuItem("New Game");
		final JMenuItem quit = new JMenuItem("Quit");

		//add menu and items
		menu.add(fileMenu);
		fileMenu.add(newGame);
		fileMenu.add(quit);
		
		setJMenuBar(menu);
		//setup listeners for the menu
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
	}
	
	private void setupCanvas(){
		final int TEMP_WIDTH = 500;
		final int TEMP_HEIGHT = 500;
		gameCanvas = new Canvas(){
			public void paint(Graphics g){
				g.fillRect(0, 0, this.getHeight(), this.getWidth());
			}
		};
		gameCanvas.setPreferredSize(new Dimension(TEMP_WIDTH, TEMP_HEIGHT));
		//add(gameCanvas, BorderLayout.CENTER);
	}
	
	private void setupButtonPanel(){
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		
		//setup buttons
		rotateViewLeft = new JButton("Rotate Left");
		rotateViewLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			//:TODO rotate view left
				System.out.println("Testing... Rotate Left");
			}
		});
		
		rotateViewRight = new JButton("Rotate Right");
		rotateViewRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			//:TODO rotate view left
				System.out.println("Testing... Rotate Right");
			}
		});
		
		buttonPanel.add(rotateViewLeft);
		buttonPanel.add(rotateViewRight);
		add(buttonPanel, BorderLayout.WEST);
	}

	public void repaint() {
		gamePanel.repaint();
	}
	
	public static void main(String[] args){
		try {
		    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }

		    }
		} catch (Exception e) {
			//do nothing
		}
		UI tester = new UI();
	}
}
