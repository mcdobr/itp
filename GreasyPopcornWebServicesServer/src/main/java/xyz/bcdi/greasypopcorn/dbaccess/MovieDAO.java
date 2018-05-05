package xyz.bcdi.greasypopcorn.dbaccess;

import java.io.*;
import java.sql.*;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.Movie;

/* Obiectul ăsta e un sigleton */

public class MovieDAO {
	private static final MovieDAO instance = new MovieDAO();
	private static final String URL = "jdbc:mariadb://localhost:3306/GreasyPopcorn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String sqlStatementsFile = "sqlStatements.properties";
	
	
	public static MovieDAO getInstance() {
		return instance;
	}
	
	private Connection conn;
	private Statement statement;
	private Properties sqlStatements;
	
	private MovieDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String sqlStatementsPath = rootPath + sqlStatementsFile;
			
			
			sqlStatements = new Properties();
			sqlStatements.load(new FileInputStream(sqlStatementsPath));
			
			// To see if db connection is valid
			// System.out.println(conn.isValid(0));
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
			String sql = String.format(sqlStatements.getProperty("getMovieByID"), movieID);
			
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
	public List<Movie> getMoviesByName(String name) {
		List<Movie> movies = new ArrayList<>();
		
		try {
			statement = conn.createStatement();
			String sql = String.format(sqlStatements.getProperty("getMoviesByName"), name);
			
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				movies.add(new Movie(rs.getString("movieID"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movies;
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
