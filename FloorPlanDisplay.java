import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Util
import java.util.ArrayList;



public class Floorplan extends JFrame {

	static JFrame window;
    JPanel floorDisplayPanel;
    int[][] tableLocation; // The first bracket controls which table it is, the second bracket controls whether or not it's x or y    
	  
	Floorplan () {
		
	    super("My Game");  
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	    // this.setUndecorated(true);  //Set to true to remove title bar
	    this.setResizable(false);

	    //Set up the game panel (where we put our graphics)
	    floorDisplayPanel = new FloorDisplayPanel();
	    this.add(new FloorDisplayPanel());
	    this.requestFocusInWindow(); //make sure the frame has focus   
	    
	    // this.setVisible(true);
	}
	
	public generateFloorPlan void (ArrayList<Table> tables) {
		
	}
	
	public displayFloorPlan void () {
		
	}
	
	private class FloorDisplayPanel extends JPanel {
		
		public void paintComponent(Graphics g) {   
			
			
			super.paintComponent(g); //required
			setDoubleBuffered(true);
			
			
		}
		
	}
	
}
