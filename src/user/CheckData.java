package user;
/*
 * @author Karanveer Singh
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import databaseConnection.DatabaseConnection;

public class CheckData {
	private String sql;
	private PreparedStatement stmt = null;
	private DatabaseConnection connDB = new DatabaseConnection();
	private ResultSet rs = null;
	
	/*
     * check if empty/ null/ only contain white space,
     * if not returns true or else returns false		    
     */
	public boolean valid(String name) {
		
		if ( name == null || StringUtils.isEmptyOrWhitespaceOnly(name) || StringUtils.isNullOrEmpty(name)){
			return false;
		}		
		return true;
	}
	
	/*
     * check if movie already exist in database,
     * if yes, returns true
     * else returns false	    
     */
	public boolean existMovie(String name, int year){
		
		sql = "SELECT * FROM Movies WHERE Title = ? AND Year = ?" ;
		try {			
			
			stmt = connDB.getConnection().prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setLong(2, year);
			rs = stmt.executeQuery();
			if (rs.next()){
			    return true;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return false;		
	}
	/*
     * check if actor already exist in database	
     * if yes, returns true
     * else returns false	    
     */
	public boolean existActor(String name){
		
		sql = "SELECT * FROM Actors WHERE Actor = ?" ;
		try {			
			
			stmt = connDB.getConnection().prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()){
			    return true;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return false;
		
	}

	/*
     * check if director already exist in database	
     * if yes, returns true
     * else returns false	    
     */
	public boolean existDirector(String name){
		
		sql = "SELECT * FROM Directors WHERE Director = ?" ;
		try {			
			
			stmt = connDB.getConnection().prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()){
			    return true;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;		
	}
	
	/*
     * check if user name already exist in database	
     * if yes, returns true
     * else returns false	    
     */
	public boolean existUserName(String name){
		
		sql = "SELECT * FROM UserLogin WHERE UserName LIKE ?" ;
		try {			
			
			stmt = connDB.getConnection().prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()){
			    return true;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return false;
		
	}
	
	/*
     * checks if consists valid characters for user name 
     * that is, checks if user name contains any character other then letters, digits or '.'(dot)
     * if yes, returns false
     * else returns true	    
     */
	public boolean consistsValidNameCharecters(String name) {
		
		for (int i = 0 ; i < name.length() ; i++ ){
			
			if(!Character.isLetterOrDigit(name.charAt(i)) && !(name.charAt(i) == '.')){
				return false;
			}			
		}		
		return true;
	}
	
	/*
     * check if consists valid characters for user password 
     * that is, checks if user password contains any empty space 
     * if yes, returns false.
     * If it contains atleast one digit 
     * returns true
     * but if not then returns false	    
     */
	public boolean consistsValidPasswordCharecters(String password) {
		
		for (int i = 0 ; i < password.length() ; i++ ){
			
			if(password.charAt(i) == ' '){
				return false;
			}
			if(Character.isDigit(password.charAt(i))){
				return true;
			}						
		}		
		return false;
	}
	/*
	 * Sets JLabel's text with warning message
	 */	
	public void warnEmptyField(JLabel label){
		label.setText("You can't leave this field empty");		
	}
	
	/*
	 * Sets JLabel's text warning message
	 */	
	public void warnInputLength(JLabel label){
		label.setText("Please use between 6 to 20 characters");		
	}
	
	/*
	 * Sets JLabel's text to empty String
	 */	
	public void refreshLabel(JLabel label){
		label.setText("");		
	}
	
	/*
	 * Sets JTextField's text to empty String 
	 */	
	public void refreshTextField(JTextField tF){
		tF.setText("");		
	}
	
}
