package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.core.Review.ReviewBuilder;
import xyz.bcdi.greasypopcorn.dbaccess.AbstractDatabaseAccessObject.SqlOperationEffect;
import xyz.bcdi.greasypopcorn.dbaccess.ReviewDAO;

@Path("reviews")
public class ReviewResource extends AbstractResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getMovies() {
		return ReviewDAO.getInstance().getReviews();
	}	
	
	@GET
	@Path("{reviewID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Review getReviewByID(@PathParam("reviewID") int reviewID) {
		return ReviewDAO.getInstance().getReviewByID(reviewID);
	}

	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviewByField(@QueryParam("movieID") Integer movieID,
			@QueryParam("username") String username) {
		if (movieID == null && username == null)
			throw new IllegalArgumentException();

		if (username != null)
			return ReviewDAO.getInstance().getReviewsByUser(username);
		else
			return ReviewDAO.getInstance().getReviewsByMovie(movieID);
	}
	
	@PUT
	public Response replaceOrCreateReviews() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("POST").build();
	}
	
	@PUT
	@Path("{reviewID}")
	public Response replaceOrCreateReview(Review r, @PathParam("reviewID") int reviewID) {
		if (r.getReviewID() == 0)
			r = ReviewBuilder.copyOf(r).withReviewID(reviewID).build();
		
		SqlOperationEffect opEffect = ReviewDAO.getInstance().replaceOrCreateReview(r); //ReviewDAO.getInstance();
		
		if (opEffect == SqlOperationEffect.REPLACED)
			return Response.status(Status.OK).build();
		else if (opEffect == SqlOperationEffect.CREATED)
			return Response.status(Status.CREATED).build();
		else 
			return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReview(Review r, @Context UriInfo uriInfo) {
		Review result = ReviewDAO.getInstance().createReview(r);
		String id = (result != null) ? result.getReviewID().toString() : null; 
		return buildResponseForCreateEntity(result, id, uriInfo);
	}
	
	@POST
	@Path("{movieID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMovie(@PathParam("movieID") int movieID) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "PUT", "DELETE").build();
	}
	
	@DELETE
	public Response deleteMovies() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	@DELETE
	@Path("{reviewID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReview(@PathParam("reviewID") int reviewID) {
		boolean wasDeleted = ReviewDAO.getInstance().deleteReview(reviewID);
		return buildResponseForDeleteEntity(wasDeleted);
	}
}
