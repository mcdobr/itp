package xyz.bcdi.greasypopcorn.dbaccess;

import java.io.*;
import java.sql.*;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.Movie;

/**
 * 
 * @author mircea
 *
 */
public class MovieDAO {
	private static final MovieDAO instance = new MovieDAO();
	private static final String URL = "jdbc:mariadb://localhost:3306/GreasyPopcorn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String statementsSqlFile = "sqlStatements.properties";
	
	public static MovieDAO getInstance() {
		return instance;
	}
	
	public enum SqlOperationEffect
	{
		CREATED,
		REPLACED,
		UPDATED,
		DELETED,
		FAILED
	};
	
	private Connection conn;
	private Statement statement;
	private Properties sqlStatements;
	
	private MovieDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String propsSqlPath = rootPath + statementsSqlFile;
			
			sqlStatements = new Properties();
			sqlStatements.load(new FileInputStream(propsSqlPath));
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return A collection with all the movies
	 */
	public List<Movie> getMovies() {
		List<Movie> movies = new ArrayList<>();
		
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sqlStatements.getProperty("getMovies"));
			
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("movieID"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
	
	public Movie getMovieByID(int movieID) {
		
		Movie result = null;
		
		try {
			statement = conn.createStatement();
			String sql = String.format(sqlStatements.getProperty("getMovieByID"), movieID);
			
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				result = new Movie(rs.getInt("movieID"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<Movie> getMoviesByName(String name) {
		List<Movie> movies = new ArrayList<>();
		
		try {
			statement = conn.createStatement();
			String sql = String.format(sqlStatements.getProperty("getMoviesByName"), name);
			
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("movieID"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
	
	/**
	 * 
	 * @param m The movie to be replaced or created.
	 * @return Status of PUT operation (CREATED, REPLACED or FAILED).
	 */
	public SqlOperationEffect replaceOrCreateMovie(Movie m) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;
		
		Movie movieToBeReplaced = getMovieByID(m.getMovieID());
		try {
			statement = conn.createStatement();
			if (movieToBeReplaced == null) {
				String sql = String.format(sqlStatements.getProperty("createMovie"),
						m.getMovieID(), m.getName(), "default", "default");
				
				statement.executeUpdate(sql);
				opEffect = SqlOperationEffect.CREATED;
			} else {
				String sql = String.format("UPDATE movies SET name = '%s', releaseDate = %s, genre = '%s' WHERE movieID = %d;", 
						m.getName(),
						(m.getReleaseDate() == null) ? "default" : m.getReleaseDate(),
						(m.getGenre() == null) ? "default" : m.getGenre(),
						m.getMovieID());
				
				statement.executeUpdate(sql);
				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return opEffect;
	}
	
	/**
	 * @param name The name of the movie
	 * @return The created movie
	 */
	public Movie createMovie(Movie m) {
		Movie result = null;
		try {
			statement = conn.createStatement();
			String sql = String.format(sqlStatements.getProperty("postMovie"),
					m.getName(), "default", "vxzc");
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = statement.getGeneratedKeys();
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			
			result = getMovieByID(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @return 200 OK if was deleted or 204 NO CONTENT if there was no such entry
	 */
	public boolean deleteMovie(int movieID) {
		
		int rowsAffected = 0;
		try {
			statement = conn.createStatement();
			String sql = String.format(sqlStatements.getProperty("deleteMovie"), movieID);
			rowsAffected = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rowsAffected == 1);
	}
}
