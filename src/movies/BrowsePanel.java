package movies;
/**
 * @author Karanveer Singh, Creates the Browse Panel to show list of all movies.
 */
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import initialize.PanelBase;

import javax.swing.JLabel;


import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BrowsePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JScrollPane scrollPaneBrowse = new JScrollPane();
 	public static JTable tableBrowse = new JTable();

	public BrowsePanel(PanelBase panelBase) {
		setBackground(Color.WHITE);
		this.setBounds(0, 0, 1000, 800);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Movies");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 41));
		lblNewLabel.setBounds(243, 174, 168, 63);
		add(lblNewLabel);
		
		scrollPaneBrowse.setViewportView(tableBrowse);
		tableBrowse.setModel(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		});
		
		new MovieTable("Select * FROM Movies" , this, scrollPaneBrowse, tableBrowse); //Creates a table that contains all movies
	}
}

//Listener that takes back to the HomePanel
class ButtonListenerBack implements ActionListener {
	private PanelBase panelBase;
	
	public ButtonListenerBack(PanelBase panelBase) {
        this.panelBase = panelBase;
    }
	public void actionPerformed(ActionEvent event) {
        panelBase.getCardLayout().show(panelBase, "Home");
	}
}

