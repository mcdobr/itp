package xyz.bcdi.greasypopcorn.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public abstract class DatabaseAccessObject {
	private static final String URL = "jdbc:mariadb://localhost:3306/GreasyPopcorn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String statementsSqlFile = "sql.properties";
		
	public enum SqlOperationEffect
	{
		CREATED,
		REPLACED,
		UPDATED,
		DELETED,
		FAILED
	};
	
	protected Connection conn;
	protected PreparedStatement statement;
	protected Properties sql;
	
	protected DatabaseAccessObject() {
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
			statement.setString(1, (String)id);
		else if (id instanceof Integer)
			statement.setInt(1, ((Integer)id).intValue());
			
		rs = statement.executeQuery();
		return rs;
	}
}
