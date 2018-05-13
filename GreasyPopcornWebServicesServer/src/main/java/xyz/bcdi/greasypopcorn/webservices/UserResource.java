package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;
import java.util.stream.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.core.User.UserBuilder;
import xyz.bcdi.greasypopcorn.dbaccess.UserDAO;
import xyz.bcdi.greasypopcorn.dbaccess.AbstractDatabaseAccessObject.SqlOperationEffect;
import xyz.bcdi.greasypopcorn.webservices.security.SecurityUtils;

@Path("users")
public class UserResource extends AbstractResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<User> getUsers() {
		return UserDAO.getInstance().getUsers().stream()
					.map(u -> UserBuilder.copyOf(u).withPassword(null).build()).collect(Collectors.toList());
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public User getUser(@PathParam("username") String username) {
		User tmp = UserDAO.getInstance().getUser(username);
		return UserBuilder.copyOf(tmp).withPassword(null).build();
	}

	@PUT
	@RolesAllowed({"Moderator", "RegisteredUser"})
	public Response replaceOrCreateUsers() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	@PUT
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"Moderator", "RegisteredUser"})
	public Response replaceOrCreateUser(User u, @PathParam("username") String username, @HeaderParam("Authorization") String authHeader) {
		if (u.getUsername() == null || u.getUsername().isEmpty())
			u = UserBuilder.copyOf(u).withUsername(username).build();
		

		if (!SecurityUtils.isUserAuthorized(authHeader, u))
			return SecurityUtils.UNAUTHORIZED_RESPONSE.build();
		
		SqlOperationEffect opEffect = UserDAO.getInstance().replaceOrCreateUser(u);

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
	@PermitAll
	public Response createUser(User u, @Context UriInfo uriInfo, @HeaderParam("Authorization") String authHeader) {
		/*if (!SecurityUtils.isUserAuthorized(authHeader, u))
			return SecurityUtils.UNAUTHORIZED_RESPONSE.build();*/
		
		User result = UserDAO.getInstance().createUser(u);
		String id = (result != null) ? result.getUsername() : null;
		return buildResponseForCreateEntity(result, id, uriInfo);
	}
	
	@POST
	@Path("{username}")
	public Response login(@PathParam("username") String username, @HeaderParam("Authorization") String authHeader) {
		User user = UserDAO.getInstance().getUser(username);
		if (user == null)
			return Response.status(Status.UNAUTHORIZED).build();
		
		String[] userPassPair = SecurityUtils.getUserPassPair(authHeader);
		if (SecurityUtils.isAuthenticationValid(userPassPair) && username.equals(userPassPair[0])) {
			return Response.status(Status.OK).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@DELETE
	@RolesAllowed("Moderator")
	public Response deleteUsers() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	@DELETE
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"Moderator", "RegisteredUser"})
	public Response deleteUser(@PathParam("username") String username, @HeaderParam("Authorization") String authHeader) {
		User u = UserDAO.getInstance().getUser(username);
		if (!SecurityUtils.isUserAuthorized(authHeader, u))
			return SecurityUtils.UNAUTHORIZED_RESPONSE.build();
		
		boolean wasDeleted = UserDAO.getInstance().deleteUser(username);
		return buildResponseForDeleteEntity(wasDeleted);
	}
}
