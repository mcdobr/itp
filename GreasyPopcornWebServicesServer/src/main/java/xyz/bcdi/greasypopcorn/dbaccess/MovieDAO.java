package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.Movie;
import xyz.bcdi.greasypopcorn.core.Movie.MovieBuilder;

/**
 * 
 * @author mircea
 *
 */
public class MovieDAO extends AbstractDatabaseAccessObject {

	private static final MovieDAO instance = new MovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}

	private MovieDAO() {
		super();
	}

	/**
	 * @return A collection with all the movies
	 */
	public List<Movie> getMovies() {
		List<Movie> movies = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getMovies"));
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Movie movie = copyOf(rs).build();
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movies;
	}

	public Movie getMovieByID(int movieID) {

		Movie result = null;

		try {
			statement = conn.prepareStatement(sql.getProperty("getMovieByID"));
			statement.setInt(1, movieID);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result = copyOf(rs).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Movie> getMoviesByName(String name) {
		List<Movie> movies = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getMoviesByName"));
			statement.setString(1, "%" + name + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Movie movie = copyOf(rs).build();
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movies;
	}

	/**
	 * 
	 * @param m
	 *            The movie to be replaced or created.
	 * @return Status of PUT operation (CREATED, REPLACED or FAILED).
	 */
	public SqlOperationEffect replaceOrCreateMovie(Movie m) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;

		Movie movieToBeReplaced = getMovieByID(m.getMovieID());
		try {
			if (movieToBeReplaced == null) {
				statement = conn.prepareStatement(sql.getProperty("createMovie"));
				statement.setInt(1, m.getMovieID());
				statement.setString(2, m.getDescription());
				statement.setString(3, m.getName());
				statement.setString(4, m.getGenre());
				statement.executeUpdate();

				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sql.getProperty("replaceMovie"));
				statement.setString(1, m.getName());
				statement.setString(2, m.getDescription());
				statement.setString(3, m.getGenre());
				statement.setInt(4, m.getMovieID());
				statement.executeUpdate();

				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return opEffect;
	}

	/**
	 * @param name
	 *            The name of the movie.
	 * @return The created movie.
	 */
	public Movie createMovie(Movie m) {
		Movie result = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("postMovie"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, m.getName());
			statement.setString(2, m.getDescription());
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


	public boolean deleteMovie(int movieID) {
		return deleteEntity("deleteMovie", movieID);
	}

	private static MovieBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		MovieBuilder mb = new MovieBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "movieid":
				mb.withMovieID(rs.getInt("movieID"));
				break;
			case "name":
				mb.withName(rs.getString("name"));
				break;
			case "description":
				mb.withDescription(rs.getString("description"));
				break;
			case "releasedate":
				Date date = rs.getDate("releaseDate");
				if (date != null)
					mb.withReleaseDate(date.toLocalDate());
				break;
			case "genre":
				mb.withGenre(rs.getString("genre"));
				break;
			}
		}
		return mb;
	}
}
