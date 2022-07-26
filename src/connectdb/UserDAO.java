package connectdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import module.User;

public class UserDAO extends DAO {
	private static final String SELECT_ADMIN = "select  Userr.userid, Userr.username,Userr.password from Userr,UserRole "
			+ "where username =? and UserRole.userid=Userr.userid and UserRole.roleid=1 ";

	private static final String Select_User = "select  Userr.userid, Userr.username,Userr.password from Userr,UserRole,Rolee "
			+ "where username =? and UserRole.userid=Userr.userid " + "and UserRole.roleid=Rolee.roleid "
			+ "and Rolee.authority=0";


	public UserDAO() {
		super();

	}

	/** 
	 * Check username and password of admin
	 * @param username
	 * @param password
	 * @return Return a User Object or null
	 * @throws SQLException
	 */
	public User checkLoginAdmin(String username, String password) throws SQLException {

		Connection connect = getConnect();
		User user = null;
		ResultSet res =null;
		PreparedStatement stm = null;
		int id = -1;
		String username1 = null;
		String pw = null;
		try {
			stm = connect.prepareStatement(SELECT_ADMIN);
			stm.setString(1, username);
			res = stm.executeQuery();
			while (res.next()) {
				id = res.getInt(1);
				username1 = res.getString(2);
				pw = res.getString(3);
			}

		}finally {
			if(res!=null) {res.close();}
			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		// check 'password' from database and 'pw' input from console
		if (id > 0 && password.equals(pw)) {

			user = new User(id, username1, password);

		}

		return user;
	}
	
	/** Check username and password of user
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User checkLoginUser(String username, String password) throws SQLException {
		Connection connect = getConnect();
		User user = null;
		ResultSet res =null;
		PreparedStatement stm = null;
		int id = -1;
		String username1 = null;
		String pw = null;
		try {
			stm = connect.prepareStatement(Select_User);
			stm.setString(1, username);
			res = stm.executeQuery();
			while (res.next()) {
				id = res.getInt(1);
				username1 = res.getString(2);
				pw = res.getString(3);
			}

		}finally {
			if(res!=null) {res.close();}
			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		// check 'password' from database and 'pw' input from console
		if (id > 0 && password.equals(pw)) {

			user = new User(id, username1, password);

		}

		return user;
	}

	


}
