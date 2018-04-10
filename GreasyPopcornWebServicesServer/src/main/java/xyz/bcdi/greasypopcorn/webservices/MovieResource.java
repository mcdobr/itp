package xyz.bcdi.greasypopcorn.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import xyz.bcdi.greasypopcorn.core.Movie;
import xyz.bcdi.greasypopcorn.dbaccess.MovieDAO;

@Path("movies")
public class MovieResource {
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovie() {
		System.out.println("GetMovie() called");
		Movie m = new Movie("Dr StrangeLove");
		
		return m;
	}
	*/
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMovies() {
		
		return MovieDAO.getInstance().getMovies();
	}
	
}
