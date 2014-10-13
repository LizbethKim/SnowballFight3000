package ui.popups;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlsPopup extends JPanel{
	private JPanel actions;
	private JPanel controls;

	public ControlsPopup(){
		setLayout(new FlowLayout());
		actions = new JPanel();
		controls = new JPanel();
		actions.setLayout(new GridLayout(0,1));
		controls.setLayout(new GridLayout(0,1));
		
		setupLabels();
		add(actions);
		add(controls);
	}
	
	public void showControls(){
		JOptionPane.showMessageDialog(null, this, "Game Controls",JOptionPane.PLAIN_MESSAGE);
	}
	
	private void addControl(String action, String control){
		JLabel a = new JLabel(action);
		JLabel c = new JLabel(" "+control);
		a.setFont(new Font("Verdana", Font.BOLD, 18));
		c.setFont(new Font("Verdana", Font.PLAIN, 18));
		actions.add(a);
		controls.add(c);
	}
	
	private void setupLabels(){
		addControl("Move Up:", "press W or Up on the Arrow Keys");
		addControl("Move Down:", "press S or Down on the Arrow Keys");
		addControl("Move Left:", "press A or Left on the Arrow Keys");
		addControl("Move Right:", "press D or Right on the Arrow Keys");
		addControl("Select Inventory Item:", "press the corresponding number key");
		addControl("Throw Snowball:", "press Space");
		addControl("Inspect Item:", "press I when adjacent to and facing an item");
		addControl("Interact with Item:", "press X");
		addControl("Pick up Item:", "press Z");
		addControl("Drop Selected Item:", "press F");
		addControl("Use Inventory Item:", "press R");
	}
}
