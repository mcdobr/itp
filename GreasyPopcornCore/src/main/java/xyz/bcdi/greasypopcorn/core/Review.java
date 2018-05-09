package xyz.bcdi.greasypopcorn.core;

import java.time.*;

import com.fasterxml.jackson.annotation.*;

public class Review {
	private final Integer reviewID;
	private final String username;
	private final Integer movieID, rating;
	private final String label, content;
	private final LocalDateTime reviewTime;
	
	@JsonCreator
	public Review(@JsonProperty("reviewID") Integer reviewID,
			@JsonProperty("username") String username,
			@JsonProperty("movieID") Integer movieID,
			@JsonProperty("rating") Integer rating,
			@JsonProperty("label") String label,
			@JsonProperty("content") String content,
			@JsonProperty("reviewTime") LocalDateTime reviewTime) {
		super();
		this.reviewID = reviewID;
		this.username = username;
		this.movieID = movieID;
		this.rating = rating;
		this.label = label;
		this.content = content;
		this.reviewTime = reviewTime;
	}
	
	public Integer getReviewID() {
		return reviewID;
	}


	public String getUsername() {
		return username;
	}


	public Integer getMovieID() {
		return movieID;
	}


	public Integer getRating() {
		return rating;
	}


	public String getLabel() {
		return label;
	}


	public String getContent() {
		return content;
	}


	public LocalDateTime getReviewTime() {
		return reviewTime;
	}


	public static class ReviewBuilder {
		private Integer reviewID;
		private String username;
		private Integer movieID, rating;
		private String label, content;
		private LocalDateTime reviewTime;
		
		public ReviewBuilder() {}
		
		private ReviewBuilder(Review review) {
			this.reviewID = review.reviewID;
			this.username = review.username;
			this.movieID = review.movieID;
			this.rating = review.rating;
			this.label = review.label;
			this.content = review.content;
			this.reviewTime = review.reviewTime;
		}
		
		public Review build() {
			return new Review(reviewID, username, movieID, rating, label, content, reviewTime);
		}
		
		public ReviewBuilder withReviewID(Integer reviewID) {
			this.reviewID = reviewID;
			return this;
		}
		public ReviewBuilder withUsername(String username) {
			this.username = username;
			return this;
		}
		public ReviewBuilder withMovieID(Integer movieID) {
			this.movieID = movieID;
			return this;
		}
		public ReviewBuilder withRating(Integer rating) {
			this.rating = rating;
			return this;
		}
		public ReviewBuilder withLabel(String label) {
			this.label = label;
			return this;
		}
		public ReviewBuilder withContent(String content) {
			this.content = content;
			return this;
		}
		public ReviewBuilder withReviewTime(LocalDateTime reviewTime) {
			this.reviewTime = reviewTime;
			return this;
		}

		public static ReviewBuilder copyOf(Review review) {
			return new ReviewBuilder(review);
		}
	}
}
