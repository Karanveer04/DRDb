package user;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

public class ReviewDisplay extends JFrame {	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewDisplay frame = new ReviewDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReviewDisplay() {	
		setBounds(100, 100, 384, 318);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
       				
	}
}
