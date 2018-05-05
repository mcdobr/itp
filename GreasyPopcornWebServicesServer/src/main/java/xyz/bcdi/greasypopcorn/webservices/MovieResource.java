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
	
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMoviesByName(@QueryParam("name") String name) {
		if (name == null)
			throw new IllegalArgumentException();
		return MovieDAO.getInstance().getMoviesByName(name);
	}
	
	@PUT
	public Response replaceMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	//TODO
	@PUT
	@Path("{movieID}")
	public Response replaceMovie() {
		return null;
	}
	
	@PATCH
	public Response updateMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	//TODO
	@PATCH
	@Path("{movieID}")
	public Response updateMovie() {
		return null;
	}
	
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
	public Response deleteMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	//TODO
	@DELETE
	@Path("{movieID}")
	public Response deleteMovie(@PathParam("movieID") String movieID) {
		// TODO: Do something
		return null;
	}
}
