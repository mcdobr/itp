package xyz.bcdi.greasypopcorn.core;

import java.util.*;

public class Movie {
	private static final int MISSING_ID = -1;
	
	private int movieID;
	private String name;
	private Date releaseDate;
	private String genre;

	public Movie() {
		movieID = MISSING_ID;
	}
	
	public Movie(int movieID, String name) {
		this.movieID = movieID;
		this.name = name;
		this.setReleaseDate(null);
		this.genre = new String();
	}
	
	public Movie(String name) {
		this(MISSING_ID, name);
	}

	public boolean isMissingID() {
		return (movieID == MISSING_ID);
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	public String getName() {
		return name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
}
