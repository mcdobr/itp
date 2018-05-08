package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import xyz.bcdi.greasypopcorn.core.DatabaseAccessObject;
import xyz.bcdi.greasypopcorn.core.Review;

public class ReviewDAO extends DatabaseAccessObject{
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
				review = new Review();
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
			if (rs.next())
				reviews.add(new Review());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reviews;
	}

	public List<Review> getReviewsByMovie(int movieID) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
