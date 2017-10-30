package user;
/*
 * @author Karanveer Singh
 */

import javax.swing.JPanel;
import com.mysql.jdbc.StringUtils;
import databaseConnection.DatabaseConnection;
import initialize.PanelBase;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;

public class UserProfile extends JPanel {
	
	private static PanelBase panelBase;
	private static JLabel lblTheRole; 
	public static String name;
	private static JLabel lblTheName;
	private static JButton btnUpgrade;
	private static JButton btnAddMovie;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Creats the panel for User Profile
	 * @param panelBase, of which this class is part of, as a panel
	 */
	public UserProfile(PanelBase panelBase) {
		
		setBackground(Color.WHITE);
		UserProfile.panelBase = panelBase;
		setBounds(0, 0, 1000, 750);
		
		JLabel lblMyProfile = new JLabel("My Profile");
		lblMyProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyProfile.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblMyProfile.setBounds(365, 198, 270, 37);
		
//		USER NAME
		JLabel lblName = new JLabel("My user name");
		lblName.setBounds(403, 262, 194, 14);
		
		lblTheName = new JLabel("");
		lblTheName.setHorizontalAlignment(SwingConstants.LEFT);
	    lblTheName.setBounds(403, 281, 194, 35);
	    
//	    USER ROLE
		JLabel lblIAm = new JLabel("I am");
		lblIAm.setBounds(403, 322, 56, 14);
		
		lblTheRole = new JLabel("");
		lblTheRole.setHorizontalAlignment(SwingConstants.LEFT);
		lblTheRole.setBounds(403, 341, 194, 35);

		/*
		 * The following JButton Upgrades a user role to the role 'producer' if not already 
		 * and is not visible to a user who has already a role, 'producer'
		 */				
		btnUpgrade = new JButton("Upgrade to producer");
		btnUpgrade.setBounds(412, 424, 175, 25);
		btnUpgrade.setVisible(false);
		btnUpgrade.setForeground(Color.BLUE);
		
		/*
		 * The following JButton lets a user to add movie.
		 * It is not visible to a user who has role, 'not producer'
		 * so that, that user cannot add movie 
		 */	
		btnAddMovie = new JButton("Add Movie");
		btnAddMovie.setBounds(412, 388, 175, 25);
		btnAddMovie.setVisible(false);

		setLayout(null);
		add(lblMyProfile);
	    add(lblIAm);
	    add(lblName);
	    add(lblTheRole);
	    add(btnUpgrade);
	    add(btnAddMovie);
	    add(lblTheName);
	    
//	    The following two JTable contains the user information name and role respectively	    
	    table_1 = new JTable();
	    table_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, UIManager.getColor("FormattedTextField.selectionBackground"), Color.LIGHT_GRAY));
	    table_1.setBounds(398, 281, 207, 35);
	    add(table_1);
	    
	    table_2 = new JTable();
	    table_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, UIManager.getColor("FormattedTextField.selectionBackground"), Color.LIGHT_GRAY));
	    table_2.setBounds(398, 341, 207, 35);
	    add(table_2);

	}
	
	/**
	 *  Upgrades a registered user's role to a producer.
	 */
	public static void upgrade(RegisteredUser user){
		
		user.setRole("Producer");
		String sql = "";
		PreparedStatement stmt = null;
		DatabaseConnection connDB = new DatabaseConnection();
		
		// Query to update UserLogin Table
		sql = "UPDATE `UserLogin`"
				+ "SET `Role` = 'Producer' "
				+ "WHERE `UserName` = ? ";
		try {
			
			stmt = connDB.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUserName());
			stmt.executeUpdate();
			
			// to show the current information of user after updating
			showDetails(user);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 *  Shows the detail information of the logged in user.
	 */
	public static void showDetails(RegisteredUser user){
		
		panelBase.getCardLayout().show(panelBase, "User Profile");				
		String name = user.getUserName();
		String role = user.getRole();
		lblTheName.setText(name);
		lblTheRole.setText(role);
		
		/**
		 * Checks if a user has logged in and if yes then shows user detail information
		 */
		if(!StringUtils.isNullOrEmpty(name)){
			
			/**
			 * Checks if a logged in user is producer or not.
			 * btnUpgrade (Upgrade button) is set to not visible for user who has already a role, 'producer'
			 * btnAddMovie (Add Movie button) is set to not visible for user who has role, 'not producer'
			 */				
			if(role.equals("Producer")){		
				btnUpgrade.setVisible(false);
				btnAddMovie.setVisible(true);
				//Takes to Add movie page (a panel)
				btnAddMovie.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						panelBase.getCardLayout().show(panelBase, "Add Movie");
					}
				});
			} else{
				btnUpgrade.setVisible(true);
				btnAddMovie.setVisible(false);
				//Upgrades a user role to the role 'producer' 
				btnUpgrade.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						upgrade(user);					
					}
				});
			}
		}
	}
}

