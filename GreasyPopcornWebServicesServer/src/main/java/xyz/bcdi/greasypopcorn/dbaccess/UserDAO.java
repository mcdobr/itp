package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.core.Review.ReviewBuilder;

public class UserDAO extends AbstractDatabaseAccessObject {
	private static final UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private UserDAO() {
		super();
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getUsers"));
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				User user = copyOf(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
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
	
	private static User copyOf(ResultSet rs) throws SQLException
	{
		return new User(rs.getString("username"), rs.getString("name"));
	}
}
