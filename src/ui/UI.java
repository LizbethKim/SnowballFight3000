package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameworld.world.Board;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class UI extends JFrame {
	//Fields
	
	private Board board;
	private JPanel buttonPanel;
	private JPanel inventoryPanel;
	private JButton rotateViewLeft;
	private JButton rotateViewRight;
	private Canvas canvas;
	
	
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
		setupCanvas();
		
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
				System.exit(EXIT_ON_CLOSE);;
			}
		});
	}
	
	private void setupCanvas(){
		canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
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
		canvas.repaint();
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
