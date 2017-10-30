package user;

/*
 * This class allows user to create an account and get some extra user privilegeby logging n
 * @author Karanveer Singh
 * 
 */

import javax.swing.JPanel;
import databaseConnection.DatabaseConnection;
import initialize.PanelBase;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CreateAccount extends JPanel {
	
	private static PanelBase panelBase;
	private  JTextField textFieldName;
	private CheckData check = new CheckData();
	private String name = null ;
	private String password ;
	private String confrmPasswrd ;
	private String sql = "";
	private PreparedStatement stmt = null;
	private DatabaseConnection connDB = new DatabaseConnection();
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCnfrm;
	private JButton btnCreateAccount;
	private JLabel lblYouCantLeave_2;
	private JLabel lblYouCantLeave_3;
	private JLabel lblYouCantLeave_1;
	private String role ;
	
	/**
	 * creats the panel for creating Account
	 * @param panelBase, of which this class is part of, as a panel
	 */
	public CreateAccount(PanelBase panelBase) {
		setBackground(Color.WHITE);
		CreateAccount.panelBase = panelBase;
		setBounds(0, 0, 1000, 750);
		
		/*
		 * the following JButton refreshes the Create Acount Panel and 
		 * takes back to home page (panel)
		 */
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					refresh();
					panelBase.getCardLayout().show(panelBase, "Home");
				}
			}
		);
		btnCancel.setBounds(510, 503, 126, 25);
		add(btnCancel);
		
		JLabel lblCreateAccount = new JLabel("Create Account");
		lblCreateAccount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAccount.setBounds(365, 198, 270, 37);
		
//		USER NAME
		JLabel lblYourName = new JLabel("Your Name");
		lblYourName.setBounds(364, 241, 271, 14);
		
		//input field for user's chosen name
		textFieldName = new JTextField();
		textFieldName.setBounds(364, 261, 271, 20);
		textFieldName.setColumns(10);
		
//		USER PASSWORD
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(364, 307, 271, 14);
		
		//input field for user's chosen password
		passwordField = new JPasswordField();
		passwordField.setBounds(364, 327, 271, 20);
		
//		CONFIRMING PASSWORD
		JLabel lblRetypePassword = new JLabel("Confirm Password");
		
		//input field for user's chosen name
		lblRetypePassword.setBounds(366, 374, 270, 14);
		passwordFieldCnfrm = new JPasswordField();
		passwordFieldCnfrm.setBounds(365, 396, 271, 20);
		
//		PRODUCER OR NOT PRODUCER
		JLabel lblAreYouProducer = new JLabel("Are you a producer?");
		lblAreYouProducer.setBounds(365, 443, 271, 14);
		
//		the folllowing JRadioButton rdbtnNo sets user's role as 'not producer' and is chosen by default
		JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBackground(Color.WHITE);
		rdbtnNo.setBounds(365, 464, 70, 23);
		rdbtnNo.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent arg0) {
				role = "Not Producer";
			}
			
		});
		rdbtnNo.setSelected(true);
		
//		the folllowing JRadioButton rdbtnNo sets user's role as 'producer'		
		JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBackground(Color.WHITE);
		rdbtnYes.setBounds(437, 464, 114, 23);
		rdbtnYes.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent arg0) {
				role = "Producer";
			}
			
		});
		
//		grouping the above two radio buttons so that only one can be chosen/ selected
		ButtonGroup bG = new ButtonGroup(); 
		bG.add(rdbtnNo);
		bG.add(rdbtnYes);
		
		
//		the following three labels shows alert messege for wrong user name, passoword and retype password respectively		
		lblYouCantLeave_1 = new JLabel("");
		lblYouCantLeave_1.setBounds(365, 282, 270, 25);
		lblYouCantLeave_1.setForeground(Color.RED);
		
		lblYouCantLeave_2 = new JLabel("");
		lblYouCantLeave_2.setBounds(366, 348, 270, 25);
		lblYouCantLeave_2.setForeground(Color.RED);
		
		lblYouCantLeave_3 = new JLabel("");
		lblYouCantLeave_3.setBounds(364, 417, 270, 25);
		lblYouCantLeave_3.setForeground(Color.RED);
		
//		the following button if pressed will create an account 
		btnCreateAccount = new JButton("Create");
		btnCreateAccount.setBounds(365, 503, 135, 25);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAcc();
			}			
		});
		
		setLayout(null);
		add(lblAreYouProducer);
		add(lblPassword);
		add(lblYourName);
		add(lblRetypePassword);	
		add(lblCreateAccount);
		add(textFieldName);
		add(lblYouCantLeave_1);
		add(lblYouCantLeave_2);
		add(lblYouCantLeave_3);
		add(passwordField);
		add(passwordFieldCnfrm);
		add(rdbtnNo);
		add(rdbtnYes);
		add(btnCreateAccount);
			
	}
	/*
	 * The following method checks for validity of user name and password and will create an account accordingly
	 */
	
	@SuppressWarnings("deprecation")
	public void createAcc(){
		
		name = textFieldName.getText();
		password = passwordField.getText();
		confrmPasswrd = passwordFieldCnfrm.getText();
		
		/*
		 * 	Check validity of user's chosen user name.
		 *  If it is not valid, JLabel 'lblYouCantLeave_1' text is set with appropriate warning message
		 *  and the boolean variable validName is set to false.
		 *  If it is valid, JLabel 'lblYouCantLeave_1' is set to empty String
		 */
		boolean validName = true;
		if (!check.valid(name)){ 
			
			check.warnEmptyField(lblYouCantLeave_1);
			validName = false;			
		}else if (!check.consistsValidNameCharecters(name)){

			lblYouCantLeave_1.setText("Please use only letters (a-z), numbers, and periods.");
			validName = false;
		}else if (name.length() < 6 || name.length() > 20){
			
			check.warnInputLength(lblYouCantLeave_1);
			validName = false;
		}else if (check.existUserName(name) ) {
			
			lblYouCantLeave_1.setText("User name already exists. Please choose another");
			validName = false;
		}else {
			
			// The JLabel is set to empty String
			check.refreshLabel(lblYouCantLeave_1);
		}
		
		/*
		 * 	Check validity of user's chosen password
		 *  If it is not valid, JLabel 'lblYouCantLeave_2' text is set with appropriate warning message
		 *  and the boolean variable validPassword is set to false.
		 *  If it is valid, JLabel 'lblYouCantLeave_2' is set to empty String
		 */
		
		boolean validPassword = true;
		if(!check.valid(password)){

			check.warnEmptyField(lblYouCantLeave_2);
			validPassword = false;
		}else if (!check.consistsValidPasswordCharecters (password) ){

			lblYouCantLeave_2.setText("Please use atleast one number (0-9)");
			validPassword = false;
		}else if(password.length() < 6 || password.length() > 20){
			
			check.warnInputLength(lblYouCantLeave_2);;
			validPassword = false;
		}else {
			
			// The JLabel is set to empty String
			check.refreshLabel(lblYouCantLeave_2);
		}
		
		/*
		 * 	Check and match input for confirm password with input password
		 *  If it is not valid, JLabel 'lblYouCantLeave_3' text is set with appropriate warning message
		 *  and the boolean variable validConfrmPassword is set to false.
		 *  If it is valid, JLabel 'lblYouCantLeave_3' is set to empty String
		 */
		 		
		boolean validConfrmPassword = true;
		if(!check.valid(confrmPasswrd)){

			check.warnEmptyField(lblYouCantLeave_3);
			validConfrmPassword = false;
		}else if (!password.equals(confrmPasswrd)){

			lblYouCantLeave_3.setText("These passwords don't match. Try again.");
			validConfrmPassword = false;
		}else {
			
			// The JLabel is set to empty String
			check.refreshLabel(lblYouCantLeave_3);
		}
		
		/*
		 * 	Insert in the UserLogin Table if all user input is correct or valid and so 
		 *  the boolean variables validName, validPassword and validConfrmPassword are true
		 */		
		if (validName && validPassword && validConfrmPassword){	
			
			//SQL query to insert in UserLogin Table
			sql = "INSERT INTO `UserLogin`"
					+ "(`UserName` , `Password` , `Role`)"
					+ "VALUES(?,?,?)";
			try {
				
				stmt = connDB.getConnection().prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, HashFunctions.getHash(password.toCharArray(), "SHA-512"));
				//
				stmt.setString(3, role);
				stmt.executeUpdate();
				
				/*
				 * 	DECLARE SUCCESSFUL CREATION	and 
				 *  refreshes the Create Acount Panel and 
				 *  takes back to home page (panel) 	
				 */
				JOptionPane.showMessageDialog(panelBase, "Successfully created account");
				refresh();
				panelBase.getCardLayout().show(panelBase, "Home");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}										
		}			
	}
	
	/*
	 * The following method refreshes the Creat Account Panel
	 */
	private void refresh() {
		
		check.refreshLabel(lblYouCantLeave_1);
		check.refreshLabel(lblYouCantLeave_2);
		check.refreshLabel(lblYouCantLeave_3);
		check.refreshTextField(textFieldName);
		check.refreshTextField(passwordField);
		check.refreshTextField(passwordFieldCnfrm);
	}

}
