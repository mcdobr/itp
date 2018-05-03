package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.Movie;

/* Obiectul ăsta e un sigleton */

public class MovieDAO {
	private static final MovieDAO instance = new MovieDAO();
	private static final String URL = "jdbc:mariadb://localhost:3306/GreasyPopcorn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	public static MovieDAO getInstance() {
		return instance;
	}
	
	private Connection conn;
	private Statement statement;
	
	private MovieDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			// To see if db connection is valid
			// System.out.println(conn.isValid(0));
		} catch (SQLException | ClassNotFoundException e) {
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
			String getAllTitles = "SELECT name, movieID " +
								"FROM movies;";
			
			ResultSet rs = statement.executeQuery(getAllTitles);
			
			while (rs.next()) {
				movies.add(new Movie(rs.getString("movieID"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
	
	public Movie getMovieByID(String movieID) {
		
		Movie result = null;
		
		try {
			statement = conn.createStatement();
			String getMovieFormatStr = 	"SELECT movieID, name " +
										"FROM movies " + 
										"WHERE movieID = '%s';";
			
			String sql = getMovieFormatStr.format(getMovieFormatStr, movieID);
			
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				result = new Movie(rs.getString("movieID"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * Pot fi mai multe filme cu acelasi nume. de aceea fol o lista
	 */
	public List<Movie> getMoviesByName() {
		return null;
	}
	
	/**
	 * @param name The name of the movie
	 * @return The created movie
	 */
	public Movie createMovie(String name) {
		Movie movie = new Movie(name);
		
		try {
			statement = conn.createStatement();

			// Db needs to be modified... for now it adds movie with the name given
			String insertFormat = "INSERT INTO movies VALUES ('%s, %s, STR_TO_DATE('1929-02-01' , '%Y-%m-%d'), 'USA', 1200, 2, 3)"; 
			statement.execute(String.format(insertFormat, movie.getName(), movie.getMovieID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// TODO: Daca nu a fost efectuata cu succes, returneaza null
		return movie;
	}
	
}
