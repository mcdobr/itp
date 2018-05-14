package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.*;
import xyz.bcdi.greasypopcorn.core.Role.*;

public class RoleDAO extends AbstractDatabaseAccessObject {
	private static final RoleDAO instance = new RoleDAO();

	public static RoleDAO getInstance() {
		return instance;
	}

	private RoleDAO() {
		super();
	}

	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getRoles"));
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Role role = copyOf(rs).build();
				roles.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roles;
	}
	
	public Role getRoleByID(int roleID) {
		Role role = null;
		try {
			ResultSet rs = getResourceById("getRoleByID", Integer.valueOf(roleID));
			if (rs.next()) {
				role = copyOf(rs).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return role;
	}
	
	public List<Role> getRolesByMovie(int movieID) {
		List<Role> roles = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getRolesByMovie"));
			statement.setInt(1, movieID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Role role = copyOf(rs).build();
				roles.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}
	
	public List<Role> getRolesByPerson(int personID) {
		List<Role> roles = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getRolesByPerson"));
			statement.setInt(1, personID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Role role = copyOf(rs).build();
				roles.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}
	

	public SqlOperationEffect replaceOrCreateRole(Role r) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;
		
		Role roleToBeReplaced = getRoleByID(r.getRoleID());
		try {
			if (roleToBeReplaced == null ) {
				statement = conn.prepareStatement(sql.getProperty("createRole"));
				statement.setInt(1, r.getRoleID());
				statement.setInt(2, r.getMovieID());
				statement.setInt(3, r.getPersonID());
				statement.setString(4, r.getRoleName());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sql.getProperty("replaceRole"));
				statement.setString(1, r.getRoleName());
				statement.setInt(2, r.getRoleID());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opEffect;
	}

	public Role createRole(Role r) {
		Role result = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("postRole"), Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, r.getMovieID());
			statement.setInt(2, r.getPersonID());
			statement.setString(3, r.getRoleName());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			
			result = getRoleByID(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteRole(int roleID) {
		return deleteEntity("deleteRole", roleID);
	}

	private static RoleBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		RoleBuilder rb = new RoleBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "roleid":
				rb.withRoleID(rs.getInt("roleID"));
				break;
			case "movieid":
				rb.withMovieID(rs.getInt("movieID"));
				break;
			case "personid":
				rb.withPersonID(rs.getInt("personID"));
				break;
			case "rolename":
				rb.withRoleName(rs.getString("roleName"));
				break;
			}
		}
		return rb;
	}
}
