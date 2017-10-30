package movies;

/** 
 * @author Karanveer Singh, This Class implements the Home Page of our DSS.
 */
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import databaseConnection.DatabaseConnection;
import initialize.PanelBase;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;

public class HomePanel extends JPanel {
	public static JPanel homePanel;
	public static String moviePath;
	public static String titleName;
	public static String labelName;

	public HomePanel(PanelBase panelBase) {
		// These methods will save all the movies in there respective list.The list will be of HomeObj data type.
		ArrayList<HomeObj> list1 = getPopularMovieDetails();
		ArrayList<HomeObj> list2 = getLatestMovieDetails();
		ArrayList<HomeObj> list3 = getComingSoonMovieDetails();

		homePanel = new JPanel();
		homePanel.setBackground(new Color(255, 255, 255));
		homePanel.setLayout(null);
		homePanel.setVisible(true);
		homePanel.repaint();
		homePanel.revalidate();
		
		// -------------------------------------------------------------------------------------------------

		JLabel lblMostPopular = new JLabel("MOST POPULAR");
		lblMostPopular.setForeground(new Color(0, 0, 0));
		lblMostPopular.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostPopular.setFont(new Font("Chiller", Font.BOLD, 18));
		lblMostPopular.setBounds(420, 110, 119, 26);
		homePanel.add(lblMostPopular);

		int a = 10;
		int b = 30;
		// This for loop will go through list1 containing Most Popular movies and initialize the movie thumbnails and their respective titles.
		for (HomeObj x : list1) {
			// initialize thumbnail
			JLabel img = new JLabel(new ImageIcon(x.getImageIcon()));
			img.setCursor(new Cursor(Cursor.HAND_CURSOR));
			img.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e1) {
					titleName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title='" + titleName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			img.setHorizontalAlignment(SwingConstants.CENTER);
			img.setBounds(a, 152, 150, 112);
			a = a + 200;
			homePanel.add(img);

			// initialize title
			JLabel title = new JLabel(x.getTitle());
			title.setCursor(new Cursor(Cursor.HAND_CURSOR));
			title.setForeground(new Color(0, 0, 0));
			title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e2) {
					labelName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title = '" + labelName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBounds(b, 277, 100, 14);
			b = b + 200;
			homePanel.add(title);
			homePanel.revalidate();
			homePanel.repaint();
		}

		// ---------------------------------------------------------------------------------------------------

		JLabel lblLatest = new JLabel("LATEST");
		lblLatest.setForeground(new Color(0, 0, 0));
		lblLatest.setFont(new Font("Chiller", Font.BOLD, 18));
		lblLatest.setHorizontalAlignment(SwingConstants.CENTER);
		lblLatest.setBounds(420, 315, 119, 22);
		homePanel.add(lblLatest);

		int c = 10;
		int d = 10;
		// This for loop will go through list2 containing Latest movies and initialize the movie thumbnails and there respective titles.
		for (HomeObj x : list2) {
			// initialize thumbnail
			JLabel img = new JLabel(new ImageIcon(x.getImageIcon()));
			img.setCursor(new Cursor(Cursor.HAND_CURSOR));
			img.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e1) {
					titleName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title='" + titleName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			img.setHorizontalAlignment(SwingConstants.CENTER);
			img.setBounds(c, 355, 150, 112);
			c = c + 200;
			homePanel.add(img);
			
			// initialize title
			JLabel title = new JLabel(x.getTitle());
			title.setCursor(new Cursor(Cursor.HAND_CURSOR));
			title.setForeground(new Color(0, 0, 0));
			title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e2) {
					labelName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title = '" + labelName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBounds(d, 487, 150, 14);
			d = d + 200;
			homePanel.add(title);
			homePanel.revalidate();
			homePanel.repaint();
		}

		// ------------------------------------------------------------------------------------------------------

		JLabel lblComingSoon = new JLabel("COMING SOON");
		lblComingSoon.setForeground(new Color(0, 0, 0));
		lblComingSoon.setFont(new Font("Chiller", Font.BOLD, 18));
		lblComingSoon.setHorizontalAlignment(SwingConstants.CENTER);
		lblComingSoon.setBounds(434, 525, 105, 22);
		homePanel.add(lblComingSoon);

		int e = 10;
		int f = 10;
		// This for loop will go through list3 containing Coming Soon movies and initialize the movie thumbnails and there respective titles.
		for (HomeObj x : list3) {
			// initialize thumbnail
			JLabel img = new JLabel(new ImageIcon(x.getImageIcon()));
			img.setCursor(new Cursor(Cursor.HAND_CURSOR));
			img.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e1) {
					titleName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title='" + titleName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			img.setHorizontalAlignment(SwingConstants.CENTER);
			img.setBounds(e, 570, 150, 112);
			e = e + 200;
			homePanel.add(img);

			// initialize title
			JLabel title = new JLabel(x.getTitle());
			title.setCursor(new Cursor(Cursor.HAND_CURSOR));
			title.setForeground(new Color(0, 0, 0));
			title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e2) {
					labelName = x.getTitle();
					moviePath = "SELECT * FROM Movies WHERE Title='" + labelName + "'";
					try {
						MoviePage.modifyMoviePage(moviePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBounds(f, 690, 150, 14);
			f = f + 200;
			homePanel.add(title);
			homePanel.revalidate();
			homePanel.repaint();
		}

		JLabel wallpaper = new JLabel("");
		wallpaper.setHorizontalAlignment(SwingConstants.CENTER);
		wallpaper.setIcon(null);
		wallpaper.setBounds(0, 100, 1000, 643);
		homePanel.add(wallpaper);

		// -------------------------------------------------------------------------------------------------------

	}
	
	/*
	 * This method will get all the Popular movies in the database and initialize the movie into a HomeObject
	 * and then add it to the Array List. 
	 */
	private ArrayList<HomeObj> getPopularMovieDetails() {
		ArrayList<HomeObj> list = new ArrayList<>();
		PreparedStatement myPsmt = null;
		ResultSet myRs = null;
		String sql = "SELECT Title,Thumbnails FROM Movies WHERE (SELECT MAX(IMDB_Rating) FROM Movies) ORDER BY IMDB_Rating DESC LIMIT 5";
		try {
			DatabaseConnection dbConn = new DatabaseConnection();
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			myRs = myPsmt.executeQuery();

			while (myRs.next()) {
				HomeObj temp = getResults(myRs);
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(list.toString());
		return list;
	}

	/*
	 * This method will get all the Latest movies in the database and initialize the movie into a HomeObject
	 * and then add it to the Array List. 
	 */
	private ArrayList<HomeObj> getLatestMovieDetails() {
		ArrayList<HomeObj> list = new ArrayList<>();
		PreparedStatement myPsmt = null;
		ResultSet myRs = null;
//		String sql = "SELECT Title,Thumbnails FROM Movies WHERE (SELECT MAX(Date) FROM Movies) ORDER BY Date DESC LIMIT 5";
		String sql = "SELECT Title,Thumbnails From Movies WHERE Year<=2016 ORDER BY year DESC LIMIT 5;";
		try {
			DatabaseConnection dbConn = new DatabaseConnection();
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			myRs = myPsmt.executeQuery();

			while (myRs.next()) {
				HomeObj temp = getResults(myRs);
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(list.toString());
		return list;
	}
	
	/*
	 * This method will get all the Coming Soon movies in the database and initialize the movie into a HomeObject
	 * and then add it to the Array List. 
	 */
	private ArrayList<HomeObj> getComingSoonMovieDetails() {
		ArrayList<HomeObj> list = new ArrayList<>();
		PreparedStatement myPsmt = null;
		ResultSet myRs = null;
	//	String sql = "SELECT Title,Thumbnails FROM Movies WHERE (SELECT MAX(Year) FROM Movies) ORDER BY YEAR DESC LIMIT 5";
		String sql = "SELECT Title,Thumbnails FROM Movies WHERE Year>2016 ORDER BY Year DESC LIMIT 5;";
		try {
			DatabaseConnection dbConn = new DatabaseConnection();
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			myRs = myPsmt.executeQuery();

			while (myRs.next()) {
				HomeObj temp = getResults(myRs);
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(list.toString());
		return list;
	}
	/*
	 * This method gets the result from the database of the above written methods.
	 */
	private HomeObj getResults(ResultSet myRs) throws SQLException {
		String name = myRs.getString("Title");
		String thumbnail = myRs.getString("Thumbnails");
		HomeObj tempObj = new HomeObj(name, thumbnail);
		return tempObj;
	}
}

class ButtonListenerBrowse implements ActionListener {
	private PanelBase panelBase;

	public ButtonListenerBrowse(PanelBase panelBase) {
		this.panelBase = panelBase;
	}

	public void actionPerformed(ActionEvent event) {
		panelBase.getCardLayout().show(panelBase, "Browse");
	}
}

class ButtonListenerSearch implements ActionListener {
	private PanelBase panelBase;

	public ButtonListenerSearch(PanelBase panelBase) {
		this.panelBase = panelBase;
	}

	public void actionPerformed(ActionEvent event) {
		panelBase.getCardLayout().show(panelBase, "Adv Search");
	}
}
