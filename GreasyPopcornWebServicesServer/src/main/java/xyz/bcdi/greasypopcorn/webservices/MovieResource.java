package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import xyz.bcdi.greasypopcorn.core.Movie.MovieBuilder;
import xyz.bcdi.greasypopcorn.core.Movie;
import xyz.bcdi.greasypopcorn.dbaccess.MovieDAO;
import xyz.bcdi.greasypopcorn.dbaccess.AbstractDatabaseAccessObject.SqlOperationEffect;

@Path("movies")
public class MovieResource extends AbstractResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Movie> getMovies() {
		return MovieDAO.getInstance().getMovies();
	}

	@GET
	@Path("{movieID}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Movie getMovieByID(@PathParam("movieID") int movieID) {
		return MovieDAO.getInstance().getMovieByID(movieID);
	}

	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Movie> getMoviesByName(@QueryParam("name") String name) {
		if (name == null)
			throw new IllegalArgumentException();
		return MovieDAO.getInstance().getMoviesByName(name);
	}

	@PUT
	@RolesAllowed("Moderator")
	public Response replaceOrCreateMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@PUT
	@Path("{movieID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("Moderator")
	public Response replaceOrCreateMovie(Movie m, @PathParam("movieID") int movieID) {

		if (m.getMovieID() == 0)
			m = MovieBuilder.copyOf(m).withMovieID(movieID).build();

		SqlOperationEffect opEffect = MovieDAO.getInstance().replaceOrCreateMovie(m);

		if (opEffect == SqlOperationEffect.REPLACED)
			return Response.status(Status.OK).build();
		else if (opEffect == SqlOperationEffect.CREATED)
			return Response.status(Status.CREATED).build();
		else
			return Response.status(Status.BAD_REQUEST).build();
	}

	@PATCH
	public Response patchMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@PATCH
	@Path("{movieID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response patchMovie(@PathParam("movieID") int movieID, MultivaluedMap<String, String> map) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Moderator")
	public Response createMovie(Movie m, @Context UriInfo uriInfo) {
		Movie result = MovieDAO.getInstance().createMovie(m);
		String id = (result != null) ? result.getMovieID().toString() : null; 
		return buildResponseForCreateEntity(result, id, uriInfo);
	}

	@POST
	@Path("{movieID}")
	@RolesAllowed("Moderator")
	public Response createMovie(@PathParam("movieID") int movieID) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "PUT", "DELETE").build();
	}

	@DELETE
	@RolesAllowed("Moderator")
	public Response deleteMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@DELETE
	@Path("{movieID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Moderator")
	public Response deleteMovie(@PathParam("movieID") int movieID) {
		boolean wasDeleted = MovieDAO.getInstance().deleteMovie(movieID);
		return buildResponseForDeleteEntity(wasDeleted);
	}
}
