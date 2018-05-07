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
	private PreparedStatement statement;
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
			statement = conn.prepareStatement(sqlStatements.getProperty("getMovies"));
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("movieID"), rs.getString("name"), rs.getString("genre")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
	
	public Movie getMovieByID(int movieID) {
		
		Movie result = null;
		
		try {
			statement = conn.prepareStatement(sqlStatements.getProperty("getMovieByID"));
			statement.setInt(1, movieID);
			
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result = new Movie(rs.getInt("movieID"), rs.getString("name"), rs.getString("genre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<Movie> getMoviesByName(String name) {
		List<Movie> movies = new ArrayList<>();
		
		try {
			statement = conn.prepareStatement(sqlStatements.getProperty("getMoviesByName"));
			statement.setString(1, "%" + name + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("movieID"), rs.getString("name"), rs.getString("genre")));
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
			if (movieToBeReplaced == null) {
				statement = conn.prepareStatement(sqlStatements.getProperty("createMovie"));
				statement.setInt(1, m.getMovieID());
				statement.setString(2, m.getName());
				statement.setString(3, m.getGenre());
				statement.executeUpdate();
				
				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sqlStatements.getProperty("replaceMovie"));
				statement.setString(1, m.getName());
				statement.setString(2, m.getGenre());
				statement.setInt(3, m.getMovieID());
				statement.executeUpdate();
				
				opEffect = SqlOperationEffect.REPLACED;
				/*
				String sql = String.format(", 
						m.getName(),
						(m.getReleaseDate() == null) ? "default" : m.getReleaseDate(),
						(m.getGenre() == null) ? "default" : m.getGenre(),
						m.getMovieID());*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return opEffect;
	}
	
	/**
	 * @param name The name of the movie.
	 * @return The created movie.
	 */
	public Movie createMovie(Movie m) {
		Movie result = null;
		try {
			statement = conn.prepareStatement(sqlStatements.getProperty("postMovie"),
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, m.getName());
			statement.executeUpdate();
			
			// Get last inserted id
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
			statement = conn.prepareStatement(sqlStatements.getProperty("deleteMovie"));
			statement.setInt(1, movieID);
			rowsAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rowsAffected == 1);
	}
}
