package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.core.User.UserBuilder;

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
				User user = copyOf(rs).build();
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
				user = copyOf(rs).build();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public SqlOperationEffect replaceOrCreateUser(User u) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;
		
		User userToBeReplaced = getUser(u.getUsername());
		try {
			if (userToBeReplaced == null ) {
				statement = conn.prepareStatement(sql.getProperty("postUser"));
				statement.setString(1, u.getUsername());
				statement.setString(2, u.getName());
				statement.setString(3, u.getPassword());
				statement.setBoolean(4, u.getIsModerator());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sql.getProperty("replaceUser"));
				statement.setString(1, u.getName());
				statement.setString(2, u.getPassword());
				statement.setBoolean(3, u.getIsModerator());
				statement.setString(4, u.getUsername());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opEffect;
	}

	public User createUser(User u) {
		User result = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("postUser"));
			statement.setString(1, u.getUsername());
			statement.setString(2, u.getName());
			statement.setString(3, u.getPassword());
			statement.setBoolean(4, u.getIsModerator());
			statement.executeUpdate();
			
			result = getUser(u.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteUser(String username) {
		return deleteEntity("deleteUser", username);
	}
	
	private static UserBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		UserBuilder ub = new UserBuilder();
		
		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "username":
				ub.withUsername(rs.getString("username"));
				break;
			case "name":
				ub.withName(rs.getString("name"));
				break;
			case "password":
				ub.withPassword(rs.getString("password"));
				break;
			case "ismoderator":
				ub.withIsModerator(rs.getBoolean("isModerator"));
				break;
			}
		}
		return ub;
	}
}