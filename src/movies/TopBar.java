package movies;

/** 
 * This Class implements the Menu bar in our DSS.
 * @author Karanveer Singh
 * 
 */
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import databaseConnection.DatabaseConnection;
import initialize.Main;
import initialize.PanelBase;
import user.SignIn;
import user.UserProfile;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;

public class TopBar extends JPanel {
	private static JTextField searchTF;
	JPanel panel;
	static String title;
	// Start Elham
	public JLabel lblRegisterOrProfile;
	public JLabel lblLoginOrLogout;
	// End
	public TopBar(PanelBase panelBase) {
		this.setBounds(0, 0, 1000, 100);
		setBackground(new Color(0, 0, 0));
		// Browse and Search features are implemented
		JButton changeBtn = new JButton("Browse");
		changeBtn.setBounds(372, 40, 98, 20);
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (changeBtn.getText().equals("Browse")) {
					panelBase.getCardLayout().show(panelBase, "Browse");
					Main.frame.revalidate();
					Main.frame.repaint();
				}
				if (changeBtn.getText().equals("Search")) {
					String sql = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies WHERE Title LIKE ?";
					SearchTitle(sql);
					panelBase.getCardLayout().show(panelBase, "Adv Search");
					Main.frame.revalidate();
					Main.frame.repaint();
				}
			}
		});
		changeBtn.setBackground(Color.BLACK);
		changeBtn.setForeground(new Color(0, 153, 204));
		// Search by Title
		searchTF = new JTextField();
		searchTF.setBounds(164, 40, 202, 20);
		searchTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!searchTF.getText().isEmpty()) {
					String sql = "SELECT DISTINCT Title, Year, Local_Rating FROM Movies WHERE Title LIKE ?";
					SearchTitle(sql);
					panelBase.getCardLayout().show(panelBase, "Adv Search");
					Main.frame.revalidate();
					Main.frame.repaint();
				}
			}
		});
		// Changes Browse to Search if anything is entered in the search field.
		searchTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (searchTF.getText().isEmpty()) {
					changeBtn.setText("Browse");
				} else
					changeBtn.setText("Search");
			}
		});
		searchTF.setToolTipText("Search By Title");
		Main.frame.revalidate();
		Main.frame.repaint();
		searchTF.setColumns(10);

		lblRegisterOrProfile = new JLabel();
		lblRegisterOrProfile.setBounds(858, 12, 55, 15);
		lblRegisterOrProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblRegisterOrProfile.setForeground(new Color(0, 153, 204));
		
		
		// The label lblRegisterOrProfile when says 'register', will allow to register when not logged in.
		// It will change and say profile to get excess of ones profile once users log in. 
		if (SignIn.user.getUserName().equals("")) {
			lblRegisterOrProfile.setText("Register");
		} else {
			lblRegisterOrProfile.setText("Profile");
		}
		
		
		lblRegisterOrProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (SignIn.user.getUserName().equals("")) {
					
					// takes to Create Account Page (a Panel)
					panelBase.getCardLayout().show(panelBase, "Create Account");
					
				} else {
					
					// takes to User Profile Panel, showing logged in user's information
					UserProfile.showDetails(SignIn.user);
				}
				
			}
		});
		lblRegisterOrProfile.setFont(new Font("Tahoma", Font.BOLD, 12));

		// Sign In
		
		
		// The label 'lblLoginOrLogout' when say 'Sign in' will allow to log in.
		// It will change and say log out after user log in and can use it to log out.
		lblLoginOrLogout = new JLabel("");
		if (SignIn.user.getUserName().equals("")) {
			lblLoginOrLogout.setText("Sign In");
		} else {
			lblLoginOrLogout.setText("Log out");
		}
		
		
		lblLoginOrLogout.setBounds(932, 12, 64, 15);
		lblLoginOrLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblLoginOrLogout.setForeground(new Color(0, 153, 204));
		lblLoginOrLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (SignIn.user.getUserName().equals("")) {
					// Takes to sign in window
					getSignIn();
					panelBase.getCardLayout().show(panelBase, "Home");
				} else {
					// takes to Home page (Panel) after user logs out
					panelBase.getCardLayout().show(panelBase, "Home");
				}
				
				
			}
		});
		lblLoginOrLogout.setFont(new Font("Tahoma", Font.BOLD, 12));
		JLabel label = new JLabel("|");
		label.setBounds(915, 11, 10, 15);
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font("Tahoma", Font.BOLD, 15));

		// Advance Search
		JLabel lblAdvSearch = new JLabel("Advanced Search   \u21B7");
		lblAdvSearch.setBounds(164, 66, 137, 23);
		lblAdvSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblAdvSearch.setForeground(new Color(0, 153, 204));
		lblAdvSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelBase.getCardLayout().show(panelBase, "Adv Search");
			}
		});
		// Home
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 12, 112, 77);
		lblNewLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelBase.getCardLayout().show(panelBase, "Home");
			}
		});
		setLayout(null);
		lblNewLabel.setIcon(new ImageIcon(TopBar.class.getResource("/images/logo.png")));
		add(lblNewLabel);
		add(lblRegisterOrProfile);
		add(label);
		add(lblLoginOrLogout);
		add(lblAdvSearch);
		add(searchTF);
		add(changeBtn);
	}

	/*
	 * This method searches for title and displays the result in a table.
	 */
	public static void SearchTitle(String sql) {
		try {
			boolean hasResult = false;
			title = searchTF.getText().trim();
			PreparedStatement myPsmt = null;
			ResultSet myRs = null;
			String name = "%" + title + "%";
			DatabaseConnection dbConn = new DatabaseConnection();
			myPsmt = dbConn.getConnection().prepareStatement(sql);
			if (!searchTF.getText().isEmpty()) {
				myPsmt.setString(1, name);
				myRs = myPsmt.executeQuery();
				while (myRs.next()) {
					hasResult = true;
				}
				String sqlFinal = myRs.getStatement().toString();
				Pattern pattern = Pattern.compile(": ");
				Matcher matcher = pattern.matcher(myRs.getStatement().toString());
				if (matcher.find()) {
					sqlFinal = myRs.getStatement().toString().substring(matcher.end());
				}
				if (hasResult) {
					new MovieTable(sqlFinal, AdvSearchPanel.searchResultPanel, AdvSearchPanel.scrollPaneBrowse,
							AdvSearchPanel.tableResults);
				} else {
					JOptionPane.showMessageDialog(Main.frame, "Movie Doesnt Exist!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 *  This method will get the sign In window 
	 *  to prompt the user to log in/ sign in.
	 */
	public void getSignIn() {
		SignIn.signIn(this);
	}
	
}
