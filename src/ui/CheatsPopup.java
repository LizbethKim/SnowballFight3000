package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;
import ui.cheats.CheatSwitch;
import ui.cheats.Invincibility;
import ui.cheats.OneHitKill;
import ui.cheats.UnlimitedSnowballs;

public class CheatsPopup extends JPanel{

	
	
	private JPanel actions;
	private JPanel switches;
	private Client client;

	public CheatsPopup(Client cl){
		this.client = cl;
		setLayout(new FlowLayout());
		actions = new JPanel();
		switches = new JPanel();
		actions.setLayout(new GridLayout(0,1));
		switches.setLayout(new GridLayout(0,1));
		
		setupLabels();
		add(actions);
		add(switches);
		showCheats();
	}
	
	private void showCheats(){
		JOptionPane.showMessageDialog(null, this, "Enable/Disable Cheats",JOptionPane.PLAIN_MESSAGE);
	}
	
	private void addCheat(String cheat, CheatSwitch c){
		JLabel a = new JLabel(cheat);
		a.setPreferredSize(new Dimension(250,30));
		a.setFont(new Font("Verdana", Font.BOLD, 18));
		actions.add(a);
		switches.add(c);
	}
	
	private void setupLabels(){
		addCheat("Invincibility", new Invincibility(client));
		addCheat("Unlimited Snowballs", new UnlimitedSnowballs(client));
		addCheat("One Hit Kill", new OneHitKill(client));
	}
}
