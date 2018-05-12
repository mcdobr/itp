package xyz.bcdi.greasypopcorn.webservices;

import java.net.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.dbaccess.UserDAO;

@Path("users")
public class UserResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return UserDAO.getInstance().getUsers();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		return UserDAO.getInstance().getUser(username);
	}
}
