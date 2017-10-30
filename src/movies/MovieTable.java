package movies;
/**
 * @author Karanveer Singh, This class creates a movie table based on the SQL query given to it, used in BrowsePanel and AdvSearchPanel
 */
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import databaseConnection.DatabaseConnection;

import javax.imageio.*;

public class MovieTable {
	private JLabel titleLabel = new JLabel();
	private JLabel lblNewLabel_2 = new JLabel();
	JLabel picLabel = new JLabel();
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private MouseListener searchSort;
	private MouseListener browseSort;
	private MouseListener rowClick;
	private static int counter = 0;
	private static String sqlClick;
	public static String clickId;
	
	private  boolean switch1 = false;
 	private  boolean switch2 = false;
 	private  boolean switch3 = false;
 	private String imagePath; 	

 	private static PreparedStatement myPsmt = null;
 	private static ResultSet myRs = null;
 	private static DatabaseConnection dbConn = new DatabaseConnection();
 	
 	public MovieTable(String sql, JPanel panel, JScrollPane scrollPane, JTable table) {
 		this.panel = panel;
 		this.scrollPane = scrollPane;
 		this.table = table;	
	
 		this.generateTable(sql);
		
 		// Adding the MouseAdapter suitable for each table since their sorting uses different SQL queries.
 		if (counter == 0) {
 			BrowsePanel.tableBrowse.getTableHeader().addMouseListener(browseSort);
 			table.addMouseListener(rowClick);
 			counter++;
 		}
 		else if (counter == 1) {
 			AdvSearchPanel.tableResults.getTableHeader().addMouseListener(searchSort);
 			table.addMouseListener(rowClick);
 			counter++;
 		}
 	}
 	
 	private void generateTable(String sql) {
 		AdvSearchPanel.executedQueryWithoutJDBC = sql;
		panel.remove(scrollPane);
		scrollPane.setBounds(12, 250, 600, 510);	
	
		DefaultTableModel browseModel = new DefaultTableModel();
		browseModel.setColumnIdentifiers(new Object[] {"Title", "Rating", "Year"});
		Vector<String> titleList = new Vector<String>();
		Vector<Float> ratingList = new Vector<Float>();
		Vector<Integer> yearList = new Vector<Integer>();
		
		
		try {
			myPsmt = dbConn.getConnection().prepareStatement(AdvSearchPanel.executedQueryWithoutJDBC);			
			myRs = myPsmt.executeQuery();			
			// Gets the information from the corresponding column names
			while (myRs.next()) {
				titleList.add(myRs.getString("Title"));
			}
			
			myRs.beforeFirst();
			while (myRs.next()) {
				ratingList.add(myRs.getFloat("Local_Rating"));
			}
			
			myRs.beforeFirst();
			while (myRs.next()) {
				yearList.add(myRs.getInt("Year"));
			}
	
			// Prints them into the table
			for (int i = 0; i < titleList.size(); i++) {
				browseModel.addRow(new Object[] {titleList.get(i), ratingList.get(i), yearList.get(i)});
				
			}				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		// This is to remove a string that does not belong to the query (added from the JAR we're using to connect to the database)
		Pattern pattern = Pattern.compile(" O");
		Matcher matcher = pattern.matcher(AdvSearchPanel.executedQueryWithoutJDBC);
		if (matcher.find())
			AdvSearchPanel.executedQueryWithoutJDBC = AdvSearchPanel.executedQueryWithoutJDBC.substring(0, matcher.start());		
		
		// The mouse adapter used in the search table to sort
		searchSort = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int col = table.columnAtPoint(e.getPoint());
		        switch (col) {
		        	// Switch1, 2 and 3 are used to determine if you clicked the same header before
		        	// If you did, it sorts in the opposite manner than the first time
		        	// Example: If you already sorted alphabetically by ascending order, you can click again to sort in descending order
		        	// This is reset if you sort using another header
		        
		        	// Three cases, one for each header
		        	case 0:	        		
		        		panel.remove(scrollPane);
		        		if (!switch1)
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER  BY Title ASC");
		        		else
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER BY Title DESC");
		        		switch1 = !switch1;
		        		switch2 = false;
		        		switch3 = false;
		        		break;
		        	case 1:
		        		panel.remove(scrollPane);
		        		if (!switch2)
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER BY Local_Rating DESC");
		        		else
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER BY Local_Rating ASC");
		        		switch1 = false;
		        		switch2 = !switch2;
		        		switch3 = false;		        		
		        		break;
		        	case 2: 
		        		panel.remove(scrollPane);
		        		if (!switch3) 
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER BY Year DESC");
		        		else
		        			generateTable(AdvSearchPanel.executedQueryWithoutJDBC + " ORDER BY Year ASC");
		        		switch1 = false;
		        		switch2 = false;
		        		switch3 = !switch3;
		        		break;
		        } 
			}
		};
		
		// The mouse adapter used in the browse table to sort, nearly the same as previous MouseAdapter but uses a different query
		browseSort = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int col = table.columnAtPoint(e.getPoint());
		        System.out.println(col);
		        System.out.println(this);
		        switch (col) {
		        	case 0:	        		
		        		panel.remove(scrollPane);
		        		if (!switch1)
		        			generateTable("Select * FROM Movies" + " ORDER  BY Title ASC");
		        		else 
		        			generateTable("Select * FROM Movies" + " ORDER BY Title DESC");
		        		switch1 = !switch1;
		        		switch2 = false;
		        		switch3 = false;
		        		break;
		        	case 1:
		        		panel.remove(scrollPane);
		        		if (!switch2)
		        			generateTable("Select * FROM Movies" + " ORDER BY Local_Rating DESC");
		        		else
		        			generateTable("Select * FROM Movies" + " ORDER BY Local_Rating ASC");
		        		switch1 = false;
		        		switch2 = !switch2;
		        		switch3 = false;		        		
		        		break;
		        	case 2: 
		        		panel.remove(scrollPane);
		        		if (!switch3) 
		        			generateTable("Select * FROM Movies" + " ORDER BY Year DESC");		        		
		        		else
		        			generateTable("Select * FROM Movies" + " ORDER BY Year ASC");
		        		switch1 = false;
		        		switch2 = false;
		        		switch3 = !switch3;		        		
		        		break;
		        } 
			}
		};
		
		// This mouse adapter is put on the rows of the tables
		// A click previews a picture of the movie you clicked on and its title
		// A double click takes you to the corresponding movie page
		rowClick = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
				Object titleClick =  table.getModel().getValueAt(table.getSelectedRow(), 0);
				sqlClick = "SELECT * FROM Movies WHERE Title = '" + titleClick + "'";
				try {
					myPsmt = dbConn.getConnection().prepareStatement(sqlClick);
					myRs = myPsmt.executeQuery();
		
					while(myRs.next()) {
						imagePath = myRs.getString("Thumbnails");
						clickId = myRs.getString("MovieID");
					}
					titleLabel.setText((String) titleClick);
					titleLabel.setBounds(710, 250, 181, 33);
					titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
					titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
					
					picLabel.setBounds(650, 300, 300, 400);
					URL url = new URL(imagePath);
					Image image = ImageIO.read(url);
					Image scimage = image.getScaledInstance(300, 400, Image.SCALE_FAST);
					picLabel.setIcon(new ImageIcon(scimage));					
					panel.add(picLabel);
					
					lblNewLabel_2.setBounds(650, 150, 310, 80);
					panel.add(lblNewLabel_2);
					panel.add(titleLabel);
					panel.revalidate();
					panel.repaint();
					
					if (e.getClickCount() == 2)
						MoviePage.modifyMoviePage(sqlClick);					
				}
				catch (SQLException | MalformedURLException error) {
					error.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	
			
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setModel(browseModel); 
		panel.add(scrollPane, "Browse Table");
 	}
 	public static String getClickQuery() {
 		return sqlClick;
 	}
	public static String getClickId() {
		return clickId;
	}
}
