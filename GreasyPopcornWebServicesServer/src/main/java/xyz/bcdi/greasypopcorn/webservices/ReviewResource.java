package xyz.bcdi.greasypopcorn.webservices;

import java.net.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.dbaccess.ReviewDAO;

@Path("reviews")
public class ReviewResource {
	@GET
	@Path("{reviewID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Review getReviewByID(@PathParam("reviewID") int reviewID) {
		return ReviewDAO.getInstance().getReviewByID(reviewID);
	}
	
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviewByField(@QueryParam("movie") Integer movieID, @QueryParam("username") String username) {
		if (movieID == null && username == null)
			throw new IllegalArgumentException();
		
		if (username != null)
			return ReviewDAO.getInstance().getReviewsByUser(username);
		else
			return ReviewDAO.getInstance().getReviewsByMovie(movieID);
	}
}
