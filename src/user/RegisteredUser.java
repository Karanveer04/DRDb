package user;
/*
 * @author Karanveer Singh
 * this class is for implementing Registered users for our DSS
 */
public class RegisteredUser {
	
	private String userName;
	private String password;
	private String role;
	private boolean loginStatus;
	public RegisteredUser(){
		this.userName = "";
		this.password = null;
		this.role = null;
	}
	public RegisteredUser (String userName , String password , String role){
		
		this.userName = userName;
		this.password = password;
		this.role = role;
		
	}
	
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getRole(){
		return this.role;
	}
	public void setRole(String role){
		this.role = role;
	}
	
}

