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
		//this.movies = Collections.synchronizedList(new ArrayList<Movie>());
		
		/*movies.add(new Movie("Deer Hunter"));
		movies.add(new Movie("Godfather"));*/
		
		// Establish connection to db
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println(conn.isValid(0));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Movie> getMovies() {
		

		List<Movie> movies = new ArrayList<>();
		
		try {
			statement = conn.createStatement();
			String getAllTitles = "SELECT title, imdbId " +
								"FROM movies";
			
			ResultSet rs = statement.executeQuery(getAllTitles);
			
			while (rs.next()) {
				movies.add(new Movie(rs.getString("title")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
	
	public Movie getMovieByID() {
		return null;
	}
	
	/*
	 * Pot fi mai multe filme cu acelasi nume. de aceea fol o lista
	 */
	public List<Movie> getMoviesByName() {
		return null;
	}
	
}
