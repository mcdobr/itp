package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.core.Review.ReviewBuilder;

public class ReviewDAO extends AbstractDatabaseAccessObject {
	private static final ReviewDAO instance = new ReviewDAO();

	public static ReviewDAO getInstance() {
		return instance;
	}

	private ReviewDAO() {
		super();
	}

	public List<Review> getReviews() {
		List<Review> reviews = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getReviews"));
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Review review = copyOf(rs).build();
				reviews.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviews;
	}
	
	public Review getReviewByID(int reviewID) {
		Review review = null;
		try {
			ResultSet rs = getResourceById("getReviewByID", Integer.valueOf(reviewID));
			if (rs.next()) {
				review = copyOf(rs).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return review;
	}

	public List<Review> getReviewsByUser(String username) {
		List<Review> reviews = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getReviewsByUser"));
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Review review = copyOf(rs).build();
				reviews.add(review);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviews;
	}

	public List<Review> getReviewsByMovie(int movieID) {
		List<Review> reviews = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getReviewsByMovieID"));
			statement.setInt(1, movieID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Review review = copyOf(rs).build();
				reviews.add(review);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviews;
	}
	

	public SqlOperationEffect replaceOrCreateReview(Review r) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;
		
		Review reviewToBeReplaced = getReviewByID(r.getReviewID());
		try {
			if (reviewToBeReplaced == null ) {
				statement = conn.prepareStatement(sql.getProperty("createReview"));
				statement.setInt(1, r.getReviewID());
				statement.setString(2, r.getUsername());
				statement.setInt(3, r.getMovieID());
				statement.setInt(4, r.getRating());
				statement.setString(5, r.getLabel());
				statement.setString(6, r.getContent());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sql.getProperty("replaceReview"));
				statement.setInt(1, r.getRating());
				statement.setString(2, r.getLabel());
				statement.setString(3, r.getContent());
				statement.setInt(4, r.getReviewID());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opEffect;
	}

	public Review createReview(Review r) {
		Review result = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("postReview"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, r.getUsername());
			statement.setInt(2, r.getMovieID());
			statement.setInt(3, r.getRating());
			statement.setString(4, r.getLabel());
			statement.setString(5, r.getContent());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			
			result = getReviewByID(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteReview(int reviewID) {
		return deleteEntity("deleteReview", reviewID);
	}

	private static ReviewBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		ReviewBuilder rb = new ReviewBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "reviewid":
				rb.withReviewID(rs.getInt("reviewID"));
				break;
			case "username":
				rb.withUsername(rs.getString("username"));
				break;
			case "movieid":
				rb.withMovieID(rs.getInt("movieID"));
				break;
			case "rating":
				rb.withRating(rs.getInt("rating"));
				break;
			case "label":
				rb.withLabel(rs.getString("label"));
				break;
			case "content":
				rb.withContent(rs.getString("content"));
				break;
			case "reviewtime":
				rb.withReviewTime(rs.getTimestamp("reviewTime").toLocalDateTime());
				break;
			}
		}
		return rb;
	}
}
