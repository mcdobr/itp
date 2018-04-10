package xyz.bcdi.greasypopcorn.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Movie {
	
	private final String movieID;
	private final String name;
	private final List<String> genres;

	public Movie(String name) {
		this.movieID = UUID.randomUUID().toString();
		this.name = name;
		this.genres = new ArrayList<String>();
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
