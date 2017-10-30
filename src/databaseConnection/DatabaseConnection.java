package databaseConnection;
/**
 * @author Karanveer Singh, This class is used to create a Database Connection using JDBC.
*/
import java.sql.*;

public class DatabaseConnection {

	Connection myConn = null;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		try {

			final String URL = "jdbc:mysql://leia.skip.chalmers.se:3306/team_06?autoReconnect=true&useSSL=false";
			final String USERNAME = "team_06";
			final String PASSWORD = "QYyw563wyGQSfcN4";
			myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (SQLException ex1) {
			ex1.printStackTrace();
		}

		return myConn;

	}

}
