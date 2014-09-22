package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameworld.world.Board;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JFrame {
	//Fields
	
	private Board board;
	private JPanel buttonPanel;
	private JButton rotateViewLeft;
	private JButton rotateViewRight;
	
	
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
		setupButtonPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);

		// Display window
		setVisible(true);
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
		add(buttonPanel, BorderLayout.EAST);
	}

	public void repaint() {
		
	}
	
	public static void main(String[] args){
		UI tester = new UI();
	}
}
