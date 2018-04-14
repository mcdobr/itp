package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.Movie;

/* Obiectul ăsta e un sigleton */

public class MovieDAO {
	private static final MovieDAO instance = new MovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}
	
	private List<Movie> movies;
	private Connection conn;
	
	private MovieDAO() {
		this.movies = Collections.synchronizedList(new ArrayList<Movie>());
		
		movies.add(new Movie("Deer Hunter"));
		movies.add(new Movie("Godfather"));
		/* in functia asta ar trebui sa apara conectarea la baza de date.
		 * Probabil trebuie sa ai un obiect de tip connection ca membru al clasei
		 *  */
	}
	
	public List<Movie> getMovies() {
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
