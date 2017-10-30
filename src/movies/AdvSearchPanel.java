package movies;
/**
 * @author Karanveer Singh, This class implements the Advance Search feature of our DSS.
 */
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import databaseConnection.DatabaseConnection;
import initialize.Main;
import initialize.PanelBase;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class AdvSearchPanel extends JPanel {
	public static JScrollPane scrollPaneBrowse = new JScrollPane();
	public static JPanel searchResultPanel = new JPanel();
	public static JTable tableResults = new JTable();
	private static JTextField actorTF;
	private static JTextField directorTF;
	private static String genre;
	private static String lang;
	private static int year;
	private static double rating;
	static String executedQueryWithoutJDBC = "";
	private static String actorName;
	private static String directorName;
	private static String gItem;
	private static String lItem;
	// Main SQL query string to be passed.
	private static String mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";
	private String sqlActor;
	private String sqlGenre;
	private String sqlYear;
	private String sqlRating;
	private String sqlLang;
	private String sqlDirector;
	// Will check if the search fields contain something or not .
	private static boolean containsSomething = false;

	/*
	 *  @param PanelBase
	 *  This contructs the JPanel which is added to the Main frame.
	 */
	public AdvSearchPanel(PanelBase panelBase) throws SQLException {
		searchResultPanel.setBackground(Color.WHITE);
		searchResultPanel.setBounds(0, 0, 1000, 750);
		searchResultPanel.setLayout(null);

		JLabel lblActor = new JLabel("Actor");
		lblActor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblActor.setBounds(116, 142, 46, 14);
		searchResultPanel.add(lblActor);

		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGenre.setBounds(445, 192, 46, 14);
		searchResultPanel.add(lblGenre);

		JLabel lblSearchBy = new JLabel("Search By :");
		lblSearchBy.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchBy.setBounds(116, 111, 68, 14);
		searchResultPanel.add(lblSearchBy);

		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblYear.setBounds(116, 167, 46, 14);
		searchResultPanel.add(lblYear);

		JLabel lblNewLabel = new JLabel("Rating");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(445, 167, 46, 14);
		searchResultPanel.add(lblNewLabel);

		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLanguage.setBounds(116, 192, 68, 14);
		searchResultPanel.add(lblLanguage);

		JLabel lblNewLabel_1 = new JLabel("Director");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(445, 142, 57, 14);
		searchResultPanel.add(lblNewLabel_1);
				
		actorTF = new JTextField();
		actorTF.setBounds(231, 140, 128, 20);
		searchResultPanel.add(actorTF);
		actorTF.setColumns(10);

		JComboBox<String> cbGenre = new JComboBox<String>();
		cbGenre.setBounds(566, 190, 128, 20);
		searchResultPanel.add(cbGenre);
		
		// Gets all the genres in the database and adds them to the comboBox.
		DatabaseConnection dbConn = new DatabaseConnection();
		String sql = "SELECT * FROM Genres";
		PreparedStatement myPsmt;
		ResultSet myRs;
		try {
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			myRs = myPsmt.executeQuery();
			cbGenre.addItem("Select Genre");
			while (myRs.next()) {
				cbGenre.addItem(myRs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.getConnection().close();
		}

		cbGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				String passed = (String) cb.getSelectedItem();
				genre = passed;
			}
		});
		
		JSpinner spinnerYear = new JSpinner();
	//	spinnerYear.setEditor(new JSpinner.NumberEditor(spinnerYear, "#"));
		spinnerYear.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				year = (int) spinnerYear.getValue();

			}
		});
		spinnerYear.setModel(new SpinnerNumberModel(1990, 1990, 2020, 1));
		spinnerYear.setBounds(231, 165, 128, 20);
		searchResultPanel.add(spinnerYear);

		JSpinner spinnerRating = new JSpinner();
		spinnerRating.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rating = (double) spinnerRating.getValue();
				rating = rating * 10;
				rating = (int) rating;
				rating = rating / 10;
			}
		});
		spinnerRating.setModel(new SpinnerNumberModel(0.0, 0.0, 5, 0.1));
		spinnerRating.setBounds(566, 165, 128, 20);
		searchResultPanel.add(spinnerRating);
		
		// Gets all the languages from the database and adds them to the comboBox.
		JComboBox<String> cbLang = new JComboBox<String>();
		cbLang.setBounds(231, 190, 128, 20);
		searchResultPanel.add(cbLang);

		String sql1 = "SELECT DISTINCT Language FROM Movies";
		try {
			myPsmt = dbConn.getConnection().prepareStatement(sql1);
			myRs = myPsmt.executeQuery();

			cbLang.addItem("Select Language");

			while (myRs.next()) {
				cbLang.addItem(myRs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cbLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				String passed = (String) cb.getSelectedItem();
				lang = passed;
			}
		});

		directorTF = new JTextField();
		directorTF.setBounds(566, 140, 128, 20);
		searchResultPanel.add(directorTF);
		directorTF.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Checks for the search fields and adds the related SQL query if the field is not empty.
				 * Everything is added in a sequence given below. 
				 */
				if (!actorTF.getText().isEmpty()) {
					containsSomething = true;
					actorName = actorTF.getText().trim();
					sqlActor = "Actor LIKE ? ";
					mainSQL += sqlActor;
				}
				
				gItem = (String) cbGenre.getSelectedItem();
				if (gItem != "Select Genre") {
					if (containsSomething) {
						sqlGenre = "AND Genre = ? ";
						mainSQL += sqlGenre;
						
					} else {
						sqlGenre = "Genre = ? ";
						mainSQL += sqlGenre;
						containsSomething = true;
					}
				}
				
				if (year != 0 || year == 1990) {
					if(containsSomething){
						sqlYear = "AND Year = ? ";
						mainSQL += sqlYear;
					} else {
						sqlYear = "Year = ? ";
						mainSQL += sqlYear;
						containsSomething = true;
					}
				} 

				if (rating != 0 || rating == 0.1) {
					if(containsSomething){
						sqlRating = "AND Local_Rating > ? AND Local_Rating < ?  ";
						mainSQL += sqlRating;
					} else {
						sqlRating = "Local_Rating > ? AND Local_Rating < ? ";
						mainSQL += sqlRating;
						containsSomething = true;
					}
				} 
				
				lItem = (String) cbLang.getSelectedItem();
				if (lItem != "Select Language") {
					if(containsSomething){
						sqlLang = "AND Language = ?";
						mainSQL += sqlLang;
					} else {
						sqlLang = "Language = ?";
						mainSQL += sqlLang;
						containsSomething = true;
					}
				} 
				
				if (!directorTF.getText().isEmpty()) {
					directorName = directorTF.getText().trim();
					if(containsSomething){
						sqlDirector = "AND Director LIKE ? ";
						mainSQL += sqlDirector;
					} else {
						sqlDirector = "Director LIKE ? ";
						mainSQL += sqlDirector;
						containsSomething = true;
					}
				} 
				
				/*
				 * Shows a dialog box if the search fields are empty and the search button is clicked.
				 * If it contains something in the search fields , executeSearch method is called which takes a SQL string query as a parameter.
				 */
				if (actorTF.getText().isEmpty() && gItem == "Select Genre" && year == 0 && rating == 0 && lItem == "Select Language" && directorTF.getText().isEmpty()) {
					JOptionPane.showMessageDialog(Main.frame, "Search Fields are Empty", "ERROR!",JOptionPane.ERROR_MESSAGE);
					mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";

				} else {
					executeSearch(mainSQL);
					mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";
					panelBase.getCardLayout().show(panelBase, "Search Result");
				}
			}
		});
		btnSearch.setBounds(846, 164, 89, 23);
		searchResultPanel.add(btnSearch);

		// Reset button will set all the search fields to there default value.
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actorTF.setText("");
				directorTF.setText("");
				cbGenre.setSelectedItem("Select Genre");
				cbLang.setSelectedItem("Select Language");
				spinnerYear.setModel(new SpinnerNumberModel(1990, 1990, 2018, 1));
				spinnerRating.setModel(new SpinnerNumberModel(0.1, 0.1, 10, 0.1));
				year = 0;
				rating = 0;
				mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";

				// Checks if fields are empty, show an error message if search field is empty/
				if (actorTF.getText().isEmpty() && gItem == "Select Genre" && year == 0 && rating == 0
						&& lItem == "Select Language" && directorTF.getText().isEmpty()) {

					mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";
				}

			}
		});
		btnNewButton.setBounds(747, 164, 89, 23);
		searchResultPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setIcon(new ImageIcon(AdvSearchPanel.class.getResource("/images/homeSymbol.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelBase.getCardLayout().show(panelBase, "Home");
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton_1.setBounds(10, 111, 57, 39);
		searchResultPanel.add(btnNewButton_1);	
		
		scrollPaneBrowse.setViewportView(tableResults);
		tableResults.setModel(new DefaultTableModel());
		
	}
	
	/**
	 * This method takes a SQL query and runs the search accordingly.
	 * @param String sql query
	 * 
	 */
	private void executeSearch(String sql) {
//		List<Movie> list = new ArrayList<>();
		PreparedStatement myPsmt = null;
		ResultSet myRs = null;

		try {
			DatabaseConnection myConn = new DatabaseConnection();
			String name = "";
			name = "%" + actorName + "%";
			String dname = "";
			dname = "%" + directorName + "%";
			myPsmt = myConn.getConnection().prepareStatement(mainSQL);
			// Adds the parameter of search only if that particular field is not empty.
			int i = 1;
			if (!actorTF.getText().isEmpty()) {
				myPsmt.setString(i, name);
				i++;
			}
			if (gItem != "Select Genre" ) {
				myPsmt.setString(i, genre);
				i++;
			}
			if (year != 0 || year == 1990) {
				myPsmt.setInt(i, year);
				i++;
			}
			if (rating != 0 || rating == 0.1) {
				myPsmt.setDouble(i, rating);
				i++;
				myPsmt.setDouble(i, (rating+1));
				i++;
			}
			if (lItem != "Select Language") {
				myPsmt.setString(i, lang);
				i++;
			}
			if (!directorTF.getText().isEmpty()) {
				myPsmt.setString(i, dname);
			}


			myRs = myPsmt.executeQuery();
			// Shows a dialog if no results are found.
			boolean result = false;
			if(myRs.next()){
				result = true;
			}
			if(!result){
				JOptionPane.showMessageDialog(Main.frame, "Sorry! No Results Found!");
			}
			mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";

			// Retrieves the executed query and uses that to build the movie results table.
			String executedQuery = myRs.getStatement().toString();
			Pattern pattern = Pattern.compile(": ");
			Matcher matcher = pattern.matcher(executedQuery);
			if (matcher.find()) {
				String string1 = executedQuery.substring(0, matcher.start());
				executedQueryWithoutJDBC = executedQuery.substring(matcher.end());
			}
			new MovieTable(executedQueryWithoutJDBC, AdvSearchPanel.searchResultPanel, AdvSearchPanel.scrollPaneBrowse, AdvSearchPanel.tableResults);

			mainSQL = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies NATURAL JOIN GM NATURAL JOIN Genres NATURAL JOIN AM NATURAL JOIN Actors NATURAL JOIN DM NATURAL JOIN Directors WHERE ";
			containsSomething = false;

//			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		return list;
	}
}
