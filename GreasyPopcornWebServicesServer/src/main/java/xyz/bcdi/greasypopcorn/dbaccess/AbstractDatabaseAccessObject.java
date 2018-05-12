package xyz.bcdi.greasypopcorn.dbaccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDatabaseAccessObject {
	private static final String URL = "jdbc:mariadb://localhost:3306/GreasyPopcorn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String statementsSqlFile = "sql.properties";

	public enum SqlOperationEffect {
		CREATED, REPLACED, UPDATED, DELETED, FAILED
	};

	protected Connection conn;
	protected PreparedStatement statement;
	protected Properties sql;

	protected AbstractDatabaseAccessObject() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String propsSqlPath = rootPath + statementsSqlFile;

			sql = new Properties();
			sql.load(new FileInputStream(propsSqlPath));
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	protected ResultSet getResourceById(String propertyName, Object id) throws SQLException {
		ResultSet rs = null;

		statement = conn.prepareStatement(sql.getProperty(propertyName));
		if (id instanceof String)
			statement.setString(1, (String) id);
		else if (id instanceof Integer)
			statement.setInt(1, ((Integer) id).intValue());

		rs = statement.executeQuery();
		return rs;
	}
	
	protected boolean deleteEntity(String property, Object id) {
		int rowsAffected = 0;
		try {
			statement = conn.prepareStatement(sql.getProperty(property));
			if (id instanceof Integer)
				statement.setString(1, ((Integer)id).toString());
			else if (id instanceof String)
				statement.setString(1, (String)id);
			rowsAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (rowsAffected == 1);
	}
}
