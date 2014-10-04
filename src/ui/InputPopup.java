package ui;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InputPopup extends JPanel{
	private JTextField address;
	private JTextField name;
	private JRadioButton team1;
	private JRadioButton team2;
	private ButtonGroup teamChoice;
	

	public InputPopup(){
		setLayout(new GridLayout(0,2));
		setupPanel();
		showPopup();
	}
	
	private void setupPanel(){
		teamChoice = new ButtonGroup();
		
		team1 = new JRadioButton("Team Red");
		team2 = new JRadioButton("Team Blue");
		//JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new );
		teamChoice.add(team1);
		teamChoice.add(team2);
		
		//buttonPanel.add(team1);
		//buttonPanel.add(team2);
		
		address = new JTextField();
		name = new JTextField();
		
		add(new JLabel("Enter IP Address"));
		add(address);
		add(new JLabel("Enter Player Name"));
		add(name);
		add(team1);
		add(team2);
			
	}
	
	private void showPopup(){
		JOptionPane.showOptionDialog(null, this,
				"Enter Name, IP Adress and Team", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.DEFAULT_OPTION, null, null, null);
	}
}
