package ui;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlsPopup extends JPanel{

	public ControlsPopup(){
		setLayout(new GridLayout(0,1));
		showControls();
	}
	
	private void showControls(){
		JOptionPane.showMessageDialog(null, this, "Game Controls",0);
	}
}
