package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import xyz.bcdi.greasypopcorn.core.Movie;
import xyz.bcdi.greasypopcorn.dbaccess.MovieDAO;

@Path("movies")
public class MovieResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMovies() {
		return MovieDAO.getInstance().getMovies();
	}
	
	@GET
	@Path("{movieID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovieByID(@PathParam("movieID") String movieID) {
		return MovieDAO.getInstance().getMovieByID(movieID);
	}
	
	//TODO
	@GET
	public List<Movie> getMoviesByName(@QueryParam("name") String name) {
		return null;
	}
	
	/*
	//TODO
	@PUT
	public Response replaceMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	//TODO
	@PUT
	public void replaceMovie() {
		
	}*/
	
	/*
	//TODO
	@PATCH
	public void updateMovies() {
		
	}
	
	//TODO
	@PATCH
	public void updateMovie() {
		
	}
	*/
	//TODO
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie createMovieInCollection(@FormParam("name") String name) {
		//TODO: validare
		return MovieDAO.getInstance().createMovie(name);
	}
	
	
	@POST
	@Path("{movieID}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response createMovieEntity(@PathParam("movieID") String movieID) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "PUT", "DELETE", "PATCH").build();
	}
	
	@DELETE
	public void deleteMovies() {
		Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	//TODO
	@DELETE
	@Path("{movieID}")
	public Response deleteMovie(@PathParam("movieID") String movieID) {
		// TODO: Do something
		return null;
	}
}
