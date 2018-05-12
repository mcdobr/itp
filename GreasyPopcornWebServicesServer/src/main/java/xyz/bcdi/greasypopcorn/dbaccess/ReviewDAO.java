package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.core.Review.ReviewBuilder;

public class ReviewDAO extends DatabaseAccessObject {
	private static final ReviewDAO instance = new ReviewDAO();

	public static ReviewDAO getInstance() {
		return instance;
	}

	private ReviewDAO() {
		super();
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
