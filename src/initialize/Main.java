package initialize;
/**
 * @author Karanveer Singh, A Main class that creates the frame that the program will use
 * JLayeredPane
 */
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JPanel;

import movies.TopBar;

/**
 * 
 * lpane the JLayeredPane will contain two panels. 
 * 1. The panel 'topbar' will always be there in lpane.
 * 2. The panel 'panelBase' will contain different panels from the class PanelBase. 
 */
public class Main {
	
	public static JFrame frame;
	
	public static JLayeredPane lpane = new JLayeredPane();	// Elham
	public static void main(String[] args) throws SQLException {
		frame = new JFrame();
		frame.setBounds(0, 0, 1000, 800);
		lpane.setBounds(0, 0, 1000, 800);//Elham
        frame.add(lpane, BorderLayout.CENTER);
		PanelBase panelBase = new PanelBase();     
		panelBase.setBounds(0, 0, 1000, 800);
		//Elham Start
        JPanel topBar = new TopBar(panelBase);
        topBar.setBounds(0, 0, 1000, 100);
        lpane.add(topBar, new Integer(1), 0);
        lpane.add(panelBase, new Integer(0), 0);
        //End
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
        frame.setLocationRelativeTo(null);		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("DarkRoomDb");		
	}
}
