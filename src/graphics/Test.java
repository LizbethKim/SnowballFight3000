package graphics;

import gameworld.game.client.BoardState;

import javax.swing.JFrame;

public class Test extends JFrame{
	
	/**
	 * Just for initial testing of graphics pane before UI was implemented
	 * @author Elizabeth Kim kimeliz1 (300302456)
	 */
	private static final long serialVersionUID = 5957453991714700567L;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;
	
	public Test(){
		super("Graphics Test");
		BoardState meow = new BoardState();
		setContentPane(new GraphicsPane(meow));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true); 
	}

	public static void main(String args[]){
		new Test();
	}
}
