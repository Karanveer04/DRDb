package user;

/**
 * This class implements the login feature of a user account.
 * @author Karanveer Singh
 * User privileges authored by Syeda Elham Shahed
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import databaseConnection.DatabaseConnection;
import initialize.Main;
import initialize.PanelBase;
import movies.TopBar;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SignIn {

	private static JFrame LoginFrame;
	private JTextField userTF;
	private JPasswordField passTF;
	private static String Username;

	// Start Elham
	private String role = "";
	public static RegisteredUser user = new RegisteredUser();
	// End Elham
	String Password;
	DatabaseConnection dbConn = new DatabaseConnection();	
	private PanelBase panelBase;

	
	public static void signIn(TopBar topBar) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn window = new SignIn(topBar);
					window.LoginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignIn(TopBar topBar) {
		initialize(topBar);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(TopBar topBar) {
		LoginFrame = new JFrame();
		LoginFrame.setTitle("Login");
		LoginFrame.setBounds(100, 100, 600, 400);
		LoginFrame.setLocationRelativeTo(null);
		LoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 584, 361);
		panel.setBackground(SystemColor.inactiveCaptionText);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(329, 50, 245, 224);
		panel_1.setBackground(new Color(0, 0, 0));

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(351, 28, 34, 16);
		lblLogin.setForeground(new Color(0, 153, 204));
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(8, 11, 311, 294);
		lblNewLabel.setIcon(new ImageIcon(SignIn.class.getResource("/images/userIcon.png")));

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 46, 59, 15);
		lblUserName.setForeground(new Color(0, 153, 255));
		lblUserName.setBackground(new Color(255, 255, 204));
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 84, 51, 15);
		lblPassword.setForeground(new Color(0, 153, 255));
		lblPassword.setBackground(new Color(51, 0, 102));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));

		userTF = new JTextField();
		userTF.setBounds(87, 44, 149, 20);
		userTF.setColumns(10);

		passTF = new JPasswordField();
		passTF.setBounds(87, 82, 146, 20);
		passTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn(topBar);
			}
		});

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(87, 175, 71, 23);
		btnLogin.setForeground(new Color(0, 153, 204));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn(topBar);
			}
		});
		btnLogin.setBackground(new Color(0, 0, 0));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(162, 175, 74, 23);
		btnCancel.setForeground(new Color(0, 153, 204));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame.dispose();
			}
		});
		LoginFrame.getContentPane().setLayout(null);
		btnCancel.setBackground(new Color(0, 0, 0));
		panel_1.setLayout(null);
		panel_1.add(lblUserName);
		panel_1.add(lblPassword);
		panel_1.add(btnLogin);
		panel_1.add(btnCancel);
		panel_1.add(passTF);
		panel_1.add(userTF);
		LoginFrame.getContentPane().add(panel);
		panel.setLayout(null);
		panel.add(lblNewLabel);
		panel.add(panel_1);
		panel.add(lblLogin);

	}

	/*
	 *  This method implements the logging in feature of our DSS. 
	 *  It gets the username and password from their respective fields and matches that to the data stored in the database.
	 *  If the information is matched, the user is logged in.
	 */
	@SuppressWarnings("deprecation")
	private void logIn(TopBar topBar) {
		try {
			Username = userTF.getText();
			Password = passTF.getText();
			String sql = "SELECT UserName,Password,Role FROM UserLogin WHERE UserName = ? AND Password = ?";

			PreparedStatement myPsmt = null;
			ResultSet myRs = null;
			try {

				myPsmt = dbConn.getConnection().prepareStatement(sql);
				myPsmt.setString(1, Username);
				myPsmt.setString(2, HashFunctions.getHash(Password.toCharArray(), "SHA-512"));
				myRs = myPsmt.executeQuery();
				int counter = 0;
				while (myRs.next()) {
					counter++;
					role = myRs.getString("Role"); // Elham
				}
				if (counter == 1) {
					JOptionPane.showMessageDialog(LoginFrame, "User Found , Access Granted!");
					/*
					 *  Elham Start
					 *  Once a user logs in, topbar (by Topbar JPanel) changes to give excess to extra user privilege.
					 */
					topBar.lblRegisterOrProfile.setText("Profile");
					topBar.lblLoginOrLogout.setText("Log out");
					user = new RegisteredUser(Username, Password, role);
					// Elham End
					LoginFrame.dispose();
					
					
					topBar.lblLoginOrLogout.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent e){
							logOut(topBar);
						}
					});
					
				} else if (counter > 1) {
					JOptionPane.showMessageDialog(LoginFrame, "Duplicate User , Access Denied!");
				} else {
					JOptionPane.showMessageDialog(LoginFrame, "User Not Found Or Password is Incorrect!");
				}
			} catch (Exception a) {
				a.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void logOut(TopBar topbar){
		topbar.lblRegisterOrProfile.setText("Register");
		topbar.lblLoginOrLogout.setText("Sign In");
		user.setUserName(""); // Elham	
		
	}

}