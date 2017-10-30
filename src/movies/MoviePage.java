package movies;
/**
 * @author Karanveer Singh
 * A panel that displays all the information of a specific movie
 */
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import databaseConnection.DatabaseConnection;
import initialize.PanelBase;
import movies.MovieTable;

import user.ReviewDisplay;
import user.SignIn;

import javax.swing.JTextField;

public class MoviePage extends JPanel{
	private static final long serialVersionUID = 1L;
	private static PanelBase panelBase;
	private static JLabel imageLabel;
	private static JLabel titleLabel;
	private static JLabel synopsisLabel;
	private static String imagePath;
	private static URL picURL;
	
	private static PreparedStatement myPsmt = null;
 	private static ResultSet myRs = null;
 	private static DatabaseConnection dbConn = new DatabaseConnection();
 	private static JLabel dateLabel;
 	private static JLabel runtimeLabel;
 	private static JLabel languageLabel;
 	private static JLabel actorLabel;
 	private static JLabel directorLabel;
 	private static JLabel genreLabel;
 	private static String localRating;
 	private static String titleString;
	private static JButton btnComment;
	private static JTextArea textArea;
	private static JButton button_1;
	private static JButton button_2;
	private static JButton button_3;
	private static JButton button_4;
	private static JButton button_5;
 	public  int r1=1, r2=2, r3=3, r4=4, r5=5;
 	private JLabel lblRating;
 	
 	public void displayRev(){
 		// This method will display the user's review 
		ReviewDisplay reviewD = new ReviewDisplay();
		reviewD.setVisible(true);
		
		PreparedStatement myPsmt = null;
		//int MovieID = 1;
		ResultSet myRs = null;
		
		String sql = "SELECT `Review` FROM `Reviews` WHERE MovieID = '" + MovieTable.getClickId() + "'";
		try {
			
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			//myPsmt.setInt(1, MovieID);
			myRs = myPsmt.executeQuery();
			
			while(myRs.next()){
				String review1 = myRs.getString(1);
				JLabel reviewDis = new JLabel(review1);
				reviewD.getContentPane().add(reviewDis);
										
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
	
		
	}
 	public void add(int rating) {
 		//This method will add the user's rating into the database system.
		 PreparedStatement myPsmt = null;
		 String MovieID = MovieTable.getClickId();
		 float sum = 0;
		 int counter = 0;

		 String sql = "INSERT INTO `Ratings` "
		 		+ "(`MovieID` , `Rating`)"
		 		+ "VALUES(?,?)";
		 try {			
				myPsmt = dbConn.getConnection().prepareStatement(sql);
				
				
				myPsmt.setString(1, MovieID);
				myPsmt.setInt(2, rating);
				myPsmt.executeUpdate();

				System.out.println("Insert Complete!");
				
				myPsmt = dbConn.getConnection().prepareStatement("Select * from Ratings where MovieID = '" + MovieID + "'");
				myRs = myPsmt.executeQuery();
				
				while(myRs.next()) {				
					sum += myRs.getInt("Rating");
					counter++;
				}
				if (counter > 1) 
					sum = sum / counter;


				DecimalFormat dec = new DecimalFormat("#.#");

			 	myPsmt = dbConn.getConnection().prepareStatement("UPDATE Movies "
			 			+ "SET Local_Rating = ?"
			 					+ "WHERE MovieID = ?");
			 	
			 	myPsmt.setString(1, dec.format(sum));
			 	myPsmt.setString(2, MovieID);
			 	myPsmt.executeUpdate();			

				titleLabel.setText("<html> <b>" + titleString + "</b>  <font size = 6> " + dec.format(sum) + "/5  </font> </html>");
				System.out.println(sum);
				
		 } catch (SQLException e) {
				System.out.println(e);
			}
		}
 	public static void comment(String review){
 		//This method will stored the user's reviews into the database system.
		 PreparedStatement myPsmt = null;
		 String MovieID = MovieTable.getClickId();

		String sql = "INSERT INTO `Reviews` "
				+ "(`MovieID` , `Review`)"
				+ "VALUES(?,?)";
		 try {
			    
				myPsmt = dbConn.getConnection().prepareStatement(sql);				
				myPsmt.setString(1, MovieID);
				myPsmt.setString(2, review);
				myPsmt.executeUpdate();

				System.out.println("Insert Complete!");

			} catch (SQLException e) {
				System.out.println(e);
			}

		}

 	
	public MoviePage(PanelBase panelBase) {
		this.panelBase = panelBase;
		setBounds(0, 0, 1000, 750);
		setLayout(null);
		
		// Lots of labels to display movie information
		imageLabel = new JLabel();
		imageLabel.setBounds(12, 134, 200, 243);
		add(imageLabel);	
		
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(327, 114, 426, 69);
		add(titleLabel);
		
		synopsisLabel = new JLabel();
		synopsisLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		synopsisLabel.setBounds(219, 170, 715, 100);
		add(synopsisLabel);
		
		dateLabel = new JLabel();
		dateLabel.setBounds(213, 281, 200, 16);
		add(dateLabel);
		
		runtimeLabel = new JLabel();
		runtimeLabel.setBounds(213, 321, 200, 16);
		add(runtimeLabel);
		
		languageLabel = new JLabel();
		languageLabel.setBounds(213, 361, 200, 16);
		add(languageLabel);
		
		actorLabel = new JLabel();
		actorLabel.setBounds(509, 281, 358, 33);
		add(actorLabel);
		
		directorLabel = new JLabel();
		directorLabel.setBounds(509, 314, 358, 33);
		add(directorLabel);
		
		genreLabel = new JLabel();
		genreLabel.setBounds(509, 361, 358, 16);
		add(genreLabel);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(MoviePage.class.getResource("/images/Star.png")));

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(1);				
			}
		});

		button_1.setBounds(509, 425, 33, 35);
		add(button_1);
		button_1.setVisible(false);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(MoviePage.class.getResource("/images/Star.png")));

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(2);
				
			}
		});

		button_2.setBounds(555, 425, 33, 35);
		add(button_2);
		button_2.setVisible(false);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(MoviePage.class.getResource("/images/Star.png")));

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(3);
				
			}
		});

		button_3.setBounds(599, 425, 33, 35);
		add(button_3);
		button_3.setVisible(false);
		
		button_4 = new JButton("");
		button_4.setIcon(new ImageIcon(MoviePage.class.getResource("/images/Star.png")));

		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(4);
			
			}
		});

		button_4.setBounds(642, 425, 33, 35);
		add(button_4);
		button_4.setVisible(false);
		
		button_5 = new JButton("");
		button_5.setIcon(new ImageIcon(MoviePage.class.getResource("/images/Star.png")));

		button_5.setFont(new Font("Times New Roman", Font.BOLD, 13));
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(5);
			}
		});

		button_5.setBounds(686, 425, 33, 35);
		add(button_5);
		button_5.setVisible(false);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(213, 420, 249, 105);
		add(textArea);		
		
		
		btnComment = new JButton("Write Review");		
		btnComment.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		btnComment.setBounds(224, 537, 91, 29);
		add(btnComment);
		btnComment.setVisible(false);
		
		JButton btnViewReview = new JButton("View Review");
		btnViewReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayRev();
				
			}
		});
		btnViewReview.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		btnViewReview.setBounds(347, 537, 91, 29);
		add(btnViewReview);
	
	}
				
	// A method that decides what information to show on the movie page based on the SQL query
	public static void modifyMoviePage(String query) throws IOException {
		String actor1 = "";
		String actor2 = "";
		String actor3 = "";
		String director1 = "";
		String director2 = "";
		String title = "";
		String genre1 = "";
		String genre2 = "";
		String genre3 = "";
		/**
		 * 
		 * Checks if a user is logged in or not.
		 * 'textArea' (JTextArea) for typing reviews are not editable if user is not logged in
		 * 'btnComment' (Jbutton) is not visible if user is not logged in
		 */		
		if(!SignIn.user.getUserName().equals("")){
			btnComment.setVisible(true);
			textArea.setEditable(true);
			button_1.setVisible(true);
			button_2.setVisible(true);
			button_3.setVisible(true);
			button_4.setVisible(true);
			button_5.setVisible(true);
			
			btnComment.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comment(textArea.getText());
					textArea.setText(null);
				}
			});
		}else{
			btnComment.setVisible(false);
			button_1.setVisible(false);
			button_2.setVisible(false);
			button_3.setVisible(false);
			button_4.setVisible(false);
			button_5.setVisible(false);
		}
		
		panelBase.getCardLayout().show(panelBase, "Movie Page");
		
		try {
			myPsmt = dbConn.getConnection().prepareStatement(query);
			myRs = myPsmt.executeQuery();
			// Execute then get the information from the database to set the text of the labels created above
			while(myRs.next()) {
				titleString = myRs.getString("Title");
				localRating = myRs.getString("Local_Rating");
				
				title = myRs.getString("Title");
				titleLabel.setText("<html> <b>" + titleString + "</b>    <font size = 6> " + localRating + "/5  </font> </html>");
				synopsisLabel.setText("<html>" + myRs.getString("Synopsis") + "</html>");
				dateLabel.setText("Release Date: " + myRs.getString("Date"));
				runtimeLabel.setText("Run-Time: " + myRs.getString("Run-Time") + " min");
				languageLabel.setText("Language: " + myRs.getString("Language"));				
				imagePath = myRs.getString("Thumbnails");

				MovieTable.clickId = myRs.getString("MovieId");
				//change the prepared statement to go to the Actor and AM tables to retrieve the information from there
				myPsmt = dbConn.getConnection().prepareStatement("SELECT Actor FROM Movies NATURAL JOIN AM NATURAL JOIN Actors WHERE Title = '" + title +"'");
			}

			myRs = myPsmt.executeQuery();
			int counter = 0;
			// Retrieves the amount of actors based on how many are stored for this movies
			// also changed the prepared statement to go to the Director and DM tables
			while (myRs.next()) {
				if (counter == 0) {
					actor1 = myRs.getString(1);
					myPsmt = dbConn.getConnection().prepareStatement("SELECT Director FROM Movies NATURAL JOIN DM NATURAL JOIN Directors WHERE Title = '" + title +"'");
				}
				else if (counter == 1)
					actor2 = myRs.getString(1);
				else if (counter == 2)
					actor3 = myRs.getString(1);				
				counter++;			
			}
			if (myRs.isAfterLast()) {
				if (actor1 != "")
					actorLabel.setText("<html>" + "Actors: " + actor1 + "<br><br> </html>");
				if (actor2 != "")
					actorLabel.setText("<html>" + "Actors: " + actor1 + ", " + actor2 + " <br><br> </html>");
				if (actor3 != "")
					actorLabel.setText("<html>" + "Actors: " + actor1 + ", " + actor2 + ", <br>" + actor3 + "</html>");
			}
			
			myRs = myPsmt.executeQuery();
			counter = 0;
			// Same process with Director table as the above, changes prepared statement to get the genres
			while (myRs.next()) {
				if (counter == 0)  {
					director1 = myRs.getString(1);
					myPsmt = dbConn.getConnection().prepareStatement("SELECT Genre FROM Movies NATURAL JOIN GM NATURAL JOIN Genres WHERE Title = '" + title +"'");
				}
				else if (counter == 1)
					director2 = myRs.getString(1);
				
				counter++;			
			}			
			if (myRs.isAfterLast()) {
				if (director1 != "")
					directorLabel.setText("<html>" + "Directors: " + director1 + "<br> </html>");
				if (director2 != "")
					directorLabel.setText("<html>" + "Directors: " + director1 + ", " + director2 + "<br> </html>");
			}		
			
			myRs = myPsmt.executeQuery();
			counter = 0;
			// Gets the genres and displays them
			while (myRs.next()) {
				if (counter == 0) {
					genre1 = myRs.getString(1);
				}
				else if (counter == 1)
					genre2 = myRs.getString(1);
				else if (counter == 2)
					genre3 = myRs.getString(1);				
				counter++;			
			}
			if (myRs.isAfterLast()) {
				if (genre1 != "")
					genreLabel.setText("<html>" + "Genres: " + genre1 + "<br> </html>");
				if (genre2 != "")
					genreLabel.setText("<html>" + "Genres: " + genre1 + ", " + genre2 + "</html>");
				if (genre3 != "")
					genreLabel.setText("<html>" + "Genres: " + genre1 +  ", " + genre2 + ", " + genre3 + "</html>");
			}
			
			// Gets the image from the URL and scales it, then sets it for the label
			picURL = new URL(imagePath);
			Image image = ImageIO.read(picURL);
			Image scimage = image.getScaledInstance(189, 243, Image.SCALE_FAST);
			imageLabel.setIcon(new ImageIcon(scimage));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}


