package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DESKTOP-S98DJJK\\SQLEXPRESS
public class DAO {
	private static final String dURL = "jdbc:sqlserver://localhost:1433;databaseName=bookmanager";

	/** Connect to database 'bookmanager' in sql server
	 * @return Connection
	 */
	public Connection getConnect()  {
		Connection connect = null;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(dURL, "sa", "0123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		return connect;
	}

}
