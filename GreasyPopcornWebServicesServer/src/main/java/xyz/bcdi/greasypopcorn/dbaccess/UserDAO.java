package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import xyz.bcdi.greasypopcorn.core.User;

public class UserDAO extends DatabaseAccessObject {
	private static final UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private UserDAO() {
		super();
	}

	public User getUser(String username) {
		User user = null;

		try {
			statement = conn.prepareStatement(sql.getProperty("getUser"));
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			if (rs.next())
				user = new User(rs.getString("username"), null, rs.getString("name"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}
