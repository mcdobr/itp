package xyz.bcdi.greasypopcorn.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Movie {
	
	private final String movieID;
	private final String name;
	private final List<String> genres;

	public Movie(String movieID, String name) {
		this.movieID = movieID;
		this.name = name;
		this.genres = new ArrayList<String>();
	}
	
	public Movie(String name) {
		this(UUID.randomUUID().toString(), name);
		this.genres.add("comedy");
	}
	
	public String getMovieID() {
		return movieID;
	}
	
	public String getName() {
		return name;
	}

	public List<String> getGenres() {
		return genres;
	}

}
