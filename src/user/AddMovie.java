package user;
/*
 * @author Karanveer Singh
 */

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import databaseConnection.DatabaseConnection;
import initialize.PanelBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

public class AddMovie extends JPanel {

	private static PanelBase panelBase; 
	private JTextField tFTitle;
	private JTextField[] tFActor = new JTextField[3];
	private JTextField[] actorPhotoLink = new JTextField[3];
	private JTextField[] tFDirector = new JTextField[2];
	private JTextField tFActor_0;
	private JTextField tFActor_1;
	private JTextField tFActor_2;
	private JLabel lblReleaseDate;
	private JList <String> genreList;
	private JLabel lblGenre;
	private JLabel lblDirectors;
	private JTextField tFDirector_0;
	private JTextField tFDirector_1;
	private JLabel lblRuntimeminutes;
	private JTextField tFRuntime;
	private JLabel lblLanguage;
	private JTextField tFLanguage;
	private JLabel lblSynopsis;	
	private String sql;
	private PreparedStatement stmt = null;
	private DatabaseConnection connDB = new DatabaseConnection();
	private CheckData check = new CheckData();
	private JLabel lblCoverPhotoLink;
	private JTextField tFCoverPhotoLink;
	private JTextField tFAPhotoLink_1;
	private JTextField tFAPhotoLink_2;	
	private JTextField tFAPhotoLink_3;
	private JTextArea tASynopsis;
	private JComboBox<String> comboBoxlblIMDbRating_integer;
	private JComboBox<String> comboBoxlblIMDbRating_fraction;
	private JSpinner spinnerDate;
	
	// The following two labels will show warning messege 
	private JLabel lblYouCantTitle;
	private JLabel lblYouCantThumbnail;
	
	private String title ; 
	private String language ;
	private String thumbnail ; 
	private String synopsis;
	private Double IMDbRating;
	private int runTime;
	
	/**
	 * creats the panel for Adding Movie
	 * @param panelBase, of which this class is part of, as a panel
	 */
	
	@SuppressWarnings("serial")
	public AddMovie(PanelBase panelBase) {
		/*
		 * the GUI for add movie panel
		 */
		AddMovie.panelBase = panelBase;
		setBackground(Color.WHITE);
		setBounds(0, 0, 1000, 750);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblAddAMovie = new JLabel("Add a Movie");
		lblAddAMovie.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddAMovie.setBounds(191, 143, 530, 44);
		lblAddAMovie.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblAddAMovie);  
		
//		TITLE of the movie 
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(196, 209, 91, 14);
		
		tFTitle = new JTextField();
		tFTitle.setBounds(297, 206, 424, 20);
		tFTitle.setColumns(10);
		
		// The following JLabel shows warning message 
		lblYouCantTitle = new JLabel();
		lblYouCantTitle.setBounds(297, 227, 323, 14);
		lblYouCantTitle.setForeground(Color.RED);
		add(lblYouCantTitle);
		add(lblTitle);
		add(tFTitle);
		
//		IMDbRating for the movie
		JLabel lblIMDbRating = new JLabel("IMDb Rating");
		lblIMDbRating.setBounds(191, 497, 81, 14);
		add(lblIMDbRating);
		
		// The following label '.' shows(represents) decimal point for IMDB rating
		JLabel label_3 = new JLabel(".");
		label_3.setBounds(361, 496, 12, 17);
		add(label_3);
		
		// The following combobox contains the integer part of the IBDb Rating
		comboBoxlblIMDbRating_integer = new JComboBox<String>();
		comboBoxlblIMDbRating_integer.setModel(new DefaultComboBoxModel<String>(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		comboBoxlblIMDbRating_integer.setBounds(297, 494, 54, 20);
		add(comboBoxlblIMDbRating_integer);
		
		// The following combobox contains the fraction part of the IBDb Rating
		comboBoxlblIMDbRating_fraction = new JComboBox<String>();
		comboBoxlblIMDbRating_fraction.setModel(new DefaultComboBoxModel<String>(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		comboBoxlblIMDbRating_fraction.setBounds(371, 494, 54, 20);		
		add(comboBoxlblIMDbRating_fraction);

//		ACTORS
		JLabel lblActors = new JLabel("Actors");
		lblActors.setBounds(191, 291, 46, 14);
		add(lblActors);
		
		//Input field for first actor
		tFActor_0 = new JTextField();
		tFActor_0.setBounds(297, 288, 135, 20);
		tFActor_0.setColumns(10);
		add(tFActor_0);
		tFActor[0] = tFActor_0;
		
		//Input field for second Actor
		tFActor_1 = new JTextField();
		tFActor_1.setColumns(10);
		tFActor_1.setBounds(297, 312, 135, 20);
		add(tFActor_1);
		tFActor[1] = tFActor_1;
		
		//Input field for third Actor
		tFActor_2 = new JTextField();
		tFActor_2.setColumns(10);
		tFActor_2.setBounds(297, 336, 135, 20);
		add(tFActor_2);
		tFActor[2] = tFActor_2;
			
		// the following three labels shows the serialnumber for actors
		JLabel label_5 = new JLabel("1.");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(252, 291, 38, 14);
		add(label_5);
		
		JLabel label_6 = new JLabel("2.");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(252, 315, 38, 14);
		add(label_6);
		
		JLabel label_7 = new JLabel("3.");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(252, 339, 38, 14);
		add(label_7);
		
//		Actor photo link		
		JLabel lblThumbnailLinkActor = new JLabel("Actor photo link");
		lblThumbnailLinkActor.setBounds(442, 291, 112, 14);
		add(lblThumbnailLinkActor);
		
		// The following three input field is for Actor's photo links
		tFAPhotoLink_1 = new JTextField();
		tFAPhotoLink_1.setColumns(10);
		tFAPhotoLink_1.setBounds(571, 288, 150, 20);
		add(tFAPhotoLink_1);
		actorPhotoLink[0] = tFAPhotoLink_1;
		
		tFAPhotoLink_2 = new JTextField();
		tFAPhotoLink_2.setColumns(10);
		tFAPhotoLink_2.setBounds(571, 312, 150, 20);
		add(tFAPhotoLink_2);
		actorPhotoLink[1] = tFAPhotoLink_2;
		
		tFAPhotoLink_3 = new JTextField();
		tFAPhotoLink_3.setColumns(10);
		tFAPhotoLink_3.setBounds(570, 336, 150, 20);
		add(tFAPhotoLink_3);
		actorPhotoLink[2] = tFAPhotoLink_3;
		
		// the following three labels shows the serialnumber for actors photo link
		JLabel label_11 = new JLabel("1.");
		label_11.setHorizontalAlignment(SwingConstants.RIGHT);
		label_11.setBounds(526, 291, 38, 14);
		add(label_11);
		
		JLabel label = new JLabel("2.");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(526, 315, 38, 14);
		add(label);
		
		JLabel label_1 = new JLabel("3.");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(526, 339, 38, 14);
		add(label_1);		
		
//      RELEASE DATE of movie		
		lblReleaseDate = new JLabel("Release Date");
		lblReleaseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblReleaseDate.setBounds(191, 458, 81, 14);
		add(lblReleaseDate);
		
		// The following jSpinner shows todays date by default in the format 'dd/mm/yyyy' and let user change 
		Date today = new Date();
		spinnerDate = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.MONTH));
	    JSpinner.DateEditor de = new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy");
	    spinnerDate.setBounds(297, 455, 135, 20);
		spinnerDate.setEditor(de);
		add(spinnerDate);		
		
//		GENRE
		lblGenre = new JLabel("Genre");
		lblGenre.setHorizontalAlignment(SwingConstants.LEFT);
		lblGenre.setBounds(442, 370, 112, 14);
		add(lblGenre);
		
		//Jlist for genre 
		genreList = new JList<String>();
		genreList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		genreList.setModel(new AbstractListModel<String>() {
			
			//array genreList contains names of genre to select from
			String[] genreValues = new String[] {"Adventure", "Action", "Comedy", "Sci-Fi", "Drama", "Horror"};
			public int getSize() {
				return genreValues.length;
			}
			
			public String getElementAt (int index) {
				return genreValues[index];
			}
		});
		genreList.setBounds(571, 370, 150, 113);
		add(genreList);
		
//		DIRECTORS
		lblDirectors = new JLabel("Directors");
		lblDirectors.setBounds(191, 370, 81, 14);
		add(lblDirectors);
		
		//Input field for first Director
		tFDirector_0 = new JTextField();
		tFDirector_0.setColumns(10);
		tFDirector_0.setBounds(297, 367, 135, 20);
		add(tFDirector_0);
		tFDirector[0] = tFDirector_0;
		
		//Input field for second Director
		tFDirector_1 = new JTextField();
		tFDirector_1.setColumns(10);
		tFDirector_1.setBounds(297, 391, 135, 20);
		add(tFDirector_1);
		tFDirector[1] = tFDirector_1;
		
		// the following three labels show the serialnumbers for directors
		JLabel label_8 = new JLabel("1.");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(252, 370, 38, 14);
		add(label_8);
		
		JLabel label_9 = new JLabel("2.");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(252, 396, 38, 14);
		add(label_9);
		
//		RUNTIME (Duration of movie in minutes)
		lblRuntimeminutes = new JLabel("Runtime (minutes)");
		lblRuntimeminutes.setHorizontalAlignment(SwingConstants.LEFT);
		lblRuntimeminutes.setBounds(444, 497, 124, 14);
		add(lblRuntimeminutes);
		
		// input field for runtime. By default it is 0(zero).
		tFRuntime = new JTextField();
		tFRuntime.setText("0");
		tFRuntime.setColumns(10);
		tFRuntime.setBounds(571, 494, 150, 20);
		add(tFRuntime);
		
//		LANGUAGE		
		lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(191, 422, 87, 14);
		add(lblLanguage);
		
		// input field for language of the movie
		tFLanguage = new JTextField();
		tFLanguage.setColumns(10);
		tFLanguage.setBounds(297, 422, 135, 20);
		add(tFLanguage);
		
//		THUMBNAIL(COVER PICTURE) for movies
		lblCoverPhotoLink = new JLabel("Cover photo link");
		lblCoverPhotoLink.setBounds(191, 252, 99, 14);
		add(lblCoverPhotoLink);
		
		// input field for THUMBNAIL for movie
		tFCoverPhotoLink = new JTextField();
		tFCoverPhotoLink.setColumns(10);
		tFCoverPhotoLink.setBounds(297, 247, 424, 20);
		add(tFCoverPhotoLink);
		
		// The following JLabel shows warning messege if Movie coverphoto link is left empty  
		lblYouCantThumbnail = new JLabel();
		lblYouCantThumbnail.setForeground(Color.RED);
		lblYouCantThumbnail.setBounds(297, 267, 323, 14);
		add(lblYouCantThumbnail);
		
//		SYNOPSIS
		lblSynopsis = new JLabel("Synopsis");
		lblSynopsis.setBounds(191, 538, 59, 14);
		add(lblSynopsis);
		
		// input field for synopsis of movie
		tASynopsis = new JTextArea();
		tASynopsis.setLineWrap(true);
		tASynopsis.setForeground(Color.BLACK);
		tASynopsis.setBounds(297, 538, 424, 76);
		tASynopsis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(tASynopsis);		
		
		// The following JButton will add movie
		JButton btnAddMovie = new JButton("Add movie");
		btnAddMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMovie(SignIn.user);
			}	
		});
		btnAddMovie.setBounds(610, 625, 111, 23);
		add(btnAddMovie);
		
		JButton btnCancel = new JButton("Cancel");
		
		/*
		 * gets back to user profile page (panel)
		 */
		btnCancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					
					panelBase.getCardLayout().show(panelBase, "User Profile");					
				}
			}
		);
		btnCancel.setBounds(493, 625, 111, 23);
		add(btnCancel);
		
	}
	
	/*
	 * the following method adds movie in database and all its releted information in respective tables
	 * @param object RegisteredUser, The user who is adding the movie. 
	 */
	public void addMovie(RegisteredUser user){
		
		title = tFTitle.getText();				
		language = tFLanguage.getText();
		thumbnail = tFCoverPhotoLink.getText();
		synopsis = tASynopsis.getText();
		Double IMDBratingInteger = Double.parseDouble(comboBoxlblIMDbRating_integer.getSelectedItem().toString());
		Double IMDBratingFraction = Double.parseDouble(comboBoxlblIMDbRating_fraction.getSelectedItem().toString());
		
		// after calcutaing the IMDb Rating stores it in variables IMDbRating
		IMDbRating = IMDBratingInteger + IMDBratingFraction * 0.1;
		
		// convert string input to Integer and then store it in  variable runtime 
		runTime = Integer.parseInt(tFRuntime.getText());
		Object[] selectedGenreList = genreList.getSelectedValuesList().toArray();
		
		//date format is changed to match with that of the database
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String releaseDate = dateFormat.format(spinnerDate.getValue());
		
		// The year is taken from the JSpinner 'spinnerDate' 
		Date time = (Date)spinnerDate.getValue();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(time);
	    int year = cal.get(Calendar.YEAR);
	    
	    // The JLabels that shows warning messeges are refreshed according to inputs in JTextField for title and
	    // movie cover photo
	    check.refreshLabel(lblYouCantTitle);
	    check.refreshLabel(lblYouCantThumbnail);
	    
	    System.out.println(title + language + synopsis + IMDbRating  + runTime + releaseDate + year + "user is" + user.getUserName());
	    
	    /*
	     * check if the movie title empty/ null/ contains whitspace only			    
	     */
		if (check.valid(title) && check.valid(thumbnail)){
			/*
			 *  check if movie with same name and year exists,
			 *  if not then adds all the movie information in all necessery table
			 */
			if(!check.existMovie(title, year)){     
				/*
			     * check if the movie language, thumbnail, synopsis are empty/ null/ contains whitspace only and if yes, 
			     * set them to 'not available' by default 		    
			     */
				if (!check.valid(language)){
					language = "not available";
				}
				
				if (!check.valid(thumbnail)){
					thumbnail = "not available";
				} 
				
				if (!check.valid(synopsis)){
					synopsis = "not available";
				}
				
				try {
					/*
					 * the following SQL query inserts in Movie table 
					 */
					sql = "INSERT INTO Movies"
							+ "(Title, Synopsis, IMDB_Rating, Date, Year,`Run-time`, Language, Thumbnails, UserName, Local_Rating)"
							+ "VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?, ? )";
					
					stmt = connDB.getConnection().prepareStatement(sql);
					stmt.setString(1, title);
					stmt.setString(2, synopsis);
					stmt.setDouble(3, IMDbRating);
					stmt.setString(4, releaseDate);
					stmt.setLong(5, year);
					stmt.setLong(6, runTime);
					stmt.setString(7, language);
					stmt.setString(8, thumbnail);
					stmt.setString(9, user.getUserName());
					stmt.setFloat(10, 0);
					stmt.executeUpdate();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				/*
				 * 	Insert in Actor Table		
				 */
				
				for(int i = 0 ; i < tFActor.length ; i++){
					String actor = (tFActor[i].getText()).trim();	
					String aPhotoLink = (actorPhotoLink[i].getText()).trim();
					/*
				     * check if the input for actors name is empty/ null/ contains whitspace only			    
				     */
					if(check.valid(actor)){
						/*
						 *  check if actors with same name exists,
						 *  if not then adds all the releted information
						 */
						if(!check.existActor(actor)){							
							/*
							 * the following SQL query that inserts in Actor table 
							 */
							String sqlActor = "INSERT INTO Actors (Actor, ActorImage) "
									+ "VALUES (?, ?)";
							try {
								PreparedStatement stmtActor = connDB.getConnection().prepareStatement(sqlActor);
								stmtActor.setString(1, actor);
								stmtActor.setString(2, aPhotoLink);
								stmtActor.executeUpdate();
							} catch (SQLException e) {
								e.printStackTrace();
							}							
						
							/*
							 * 	 Insert in Actor-Movie relational Table AM
							 */
							String sqlAM = "INSERT INTO AM (MovieID, ActorsID) "
									+ "VALUES ((SELECT MovieID FROM Movies "
									+ "WHERE Title = ?),"
									+ "(SELECT ActorsID FROM Actors "
									+ "WHERE Actor = ?))"; 
							try {									
								PreparedStatement stmtAM = connDB.getConnection().prepareStatement(sqlAM);
								stmtAM.setString(1, title);
								stmtAM.setString(2, actor);
								stmtAM.executeUpdate();
						          
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}								
					}
				}
				/*
				 * 	Insert in Director Table		
				 */
				for(int i = 0 ; i < tFDirector.length ; i++){
					String director = (tFDirector[i].getText()).trim();					 
					/*
				     * check if the input for directors name is empty/ null/ contains whitspace only			    
				     */
					if(check.valid(director)){
						/*
						 *  check if directors with same name exists,
						 *  if not then adds all the releted information
						 */
						if(!check.existDirector(director)){
							/*
							 * the following SQL query that inserts in Director table 
							 */
							String sqlActor = "INSERT INTO Directors (Director) "
									+ "VALUES (?)";
							try {
								PreparedStatement stmtDirector = connDB.getConnection().prepareStatement(sqlActor);
								stmtDirector.setString(1, director);
								stmtDirector.executeUpdate();
								System.out.println(director);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
							/*
							 * 	Insert in Director-Movie relational Table DM
							 */
							String sqlDM = "INSERT INTO DM (MovieID, DirectorsID) "
									+ "VALUES ((SELECT MovieID FROM Movies "
									+ "WHERE Title = ?),"
									+ "(SELECT DirectorsID FROM Directors "
									+ "WHERE Director = ?))";  
							try {									
								PreparedStatement stmtDM = connDB.getConnection().prepareStatement(sqlDM);
								stmtDM.setString(1, title);
								stmtDM.setString(2, director);
								stmtDM.executeUpdate();
						          
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}								
					}
				}
				
				/*
				 * 	Insert in Genre-Movie reletional Table GM
				 */
				for(int i=0; i<selectedGenreList.length;i++){
					String selectedGenre = selectedGenreList[i].toString();
					/*
					 * the following SQL query that inserts in Genre-Movie reletional Table GM
					 */
					String sqlGM = "INSERT INTO GM (MovieID, GenresID) "
							+ "VALUES ((SELECT MovieID FROM Movies "
							+ "WHERE Title = ?),"
							+ "(SELECT GenresID FROM Genres "
							+ "WHERE Genre = ?))"; 
					try {									
						PreparedStatement stmtGM = connDB.getConnection().prepareStatement(sqlGM);
						stmtGM.setString(1, title);
						stmtGM.setString(2, selectedGenre);
						stmtGM.executeUpdate();
				          
					} catch (SQLException e1) {
						e1.printStackTrace();
					}						
				}
				
				/*
				 * 	DECLARE SUCCESSFUL CREATION		
				 */
				JOptionPane.showMessageDialog(panelBase, "Successfully added movie");
				// Takes back to user profile panel
				panelBase.getCardLayout().show(panelBase, "User Profile");
			}else {
				/*
				 * 	DECLARE that movie exists	
				 */
				JOptionPane.showMessageDialog(panelBase, "Movie already exists");
			}
		}else{
			if(!check.valid(title)){
				check.warnEmptyField(lblYouCantTitle);
			}
			if(!check.valid(thumbnail)){
				check.warnEmptyField(lblYouCantThumbnail);
			}
			
		}
		
		System.out.println(title + language + synopsis + IMDbRating  + runTime + releaseDate + year);
	}
	
}
