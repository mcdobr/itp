package xyz.bcdi.greasypopcorn.core;

import java.time.*;

import com.fasterxml.jackson.annotation.*;

public class Movie {
	private final Integer movieID;
	private final String name;
	private final LocalDate releaseDate;
	private final String genre;
	
	@JsonCreator
	public Movie(@JsonProperty("movieID") int movieID,
			@JsonProperty("name") String name,
			@JsonProperty("releaseDate") LocalDate releaseDate,
			@JsonProperty("genre") String genre) {
		this.movieID = movieID;
		this.name = name;
		this.releaseDate = releaseDate;
		this.genre = genre;
	}
	
	public Integer getMovieID() {
		return movieID;
	}

	public String getName() {
		return name;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public String getGenre() {
		return genre;
	}
	
	public static class MovieBuilder {
		private Integer movieID;
		private String name;
		private LocalDate releaseDate;
		private String genre;
		
		public MovieBuilder() {}
		
		private MovieBuilder(Movie movie) {
			this.movieID = movie.movieID;
			this.name = movie.name;
			this.releaseDate = movie.releaseDate;
			this.genre = movie.genre;
		}
		
		public Movie build() {
			return new Movie(movieID, name, releaseDate, genre);
		}
		
		public MovieBuilder withMovieID(int movieID) {
			this.movieID = movieID;
			return this;
		}

		public MovieBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public MovieBuilder withReleaseDate(LocalDate releaseDate) {
			this.releaseDate = releaseDate;
			return this;
		}

		public MovieBuilder withGenre(String genre) {
			this.genre = genre;
			return this;
		}		

		public static MovieBuilder copyOf(Movie movie) {
			return new MovieBuilder(movie);
		}
	}
}
