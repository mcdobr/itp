package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import xyz.bcdi.greasypopcorn.core.Role;
import xyz.bcdi.greasypopcorn.core.Role.RoleBuilder;
import xyz.bcdi.greasypopcorn.dbaccess.RoleDAO;
import xyz.bcdi.greasypopcorn.dbaccess.AbstractDatabaseAccessObject.SqlOperationEffect;

@Path("roles")
public class RoleResource extends AbstractResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Role> getRoles() {
		return RoleDAO.getInstance().getRoles();
	}
	
	@GET
	@Path("{roleID}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Role getRoleByID(@PathParam("roleID") int roleID) {
		return RoleDAO.getInstance().getRoleByID(roleID);
	}
	
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Role> getRolesByField(@QueryParam("movieID") Integer movieID,
			@QueryParam("personID") Integer personID) {
		if (movieID == null && personID == null)
			throw new IllegalArgumentException("No query parameters!");
		
		if (movieID != null)
			return RoleDAO.getInstance().getRolesByMovie(movieID);
		else
			return RoleDAO.getInstance().getRolesByPerson(personID);
	}
	
	@PUT
	public Response replaceOrCreateRoles() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	@PUT
	@Path("{roleID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("Moderator")
	public Response replaceOrCreateRole(Role r, @PathParam("roleID") int roleID) {
		if (r.getRoleID() == 0)
			r = RoleBuilder.copyOf(r).withRoleID(roleID).build();
		
		SqlOperationEffect opEffect = RoleDAO.getInstance().replaceOrCreateRole(r);

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
	@RolesAllowed("Moderator")
	public Response createRole(Role r, @Context UriInfo uriInfo) {
		Role result = RoleDAO.getInstance().createRole(r);
		String id = (result != null) ? result.getRoleID().toString() : null; 
		return buildResponseForCreateEntity(result, id, uriInfo);
	}

	@POST
	@Path("{roleID}")
	@RolesAllowed("Moderator")
	public Response createRole(@PathParam("roleID") int roleID) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "PUT", "DELETE").build();
	}
	
	@DELETE
	@RolesAllowed("Moderator")
	public Response deleteRoles() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@DELETE
	@Path("{roleID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Moderator")
	public Response deleteRole(@PathParam("roleID") int roleID) {
		boolean wasDeleted = RoleDAO.getInstance().deleteRole(roleID);
		return buildResponseForDeleteEntity(wasDeleted);
	}
}
