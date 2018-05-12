package xyz.bcdi.greasypopcorn.webservices;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import xyz.bcdi.greasypopcorn.core.*;
import xyz.bcdi.greasypopcorn.core.Person.*;
import xyz.bcdi.greasypopcorn.dbaccess.*;
import xyz.bcdi.greasypopcorn.dbaccess.AbstractDatabaseAccessObject.*;

@Path("persons")
public class PersonResource extends AbstractResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getPersons() {
		return PersonDAO.getInstance().getPersons();
	}
	
	@GET
	@Path("{personID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPersonByID(@PathParam("personID") int personID) {
		return PersonDAO.getInstance().getPersonByID(personID);
	}
	
	@PUT
	public Response replaceOrCreatePersons() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}
	
	@PUT
	@Path("{personID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response replaceOrCreatePerson(Person p, @PathParam("personID") int personID) {
		if (p.getPersonID() == 0)
			p = PersonBuilder.copyOf(p).withPersonID(personID).build();
		
		SqlOperationEffect opEffect = PersonDAO.getInstance().replaceOrCreatePerson(p);

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
	public Response createPerson(Person p, @Context UriInfo uriInfo) {
		Person result = PersonDAO.getInstance().createPerson(p);
		String id = (result != null) ? result.getPersonID().toString() : null; 
		return buildResponseForCreateEntity(result, id, uriInfo);
	}

	@POST
	@Path("{personID}")
	public Response createPerson(@PathParam("personID") int personID) {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "PUT", "DELETE").build();
	}
	
	@DELETE
	public Response deletePersons() {
		return Response.status(Status.METHOD_NOT_ALLOWED).allow("GET", "POST").build();
	}

	@DELETE
	@Path("{personID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerson(@PathParam("personID") int personID) {
		boolean wasDeleted = PersonDAO.getInstance().deletePerson(personID);
		return buildResponseForDeleteEntity(wasDeleted);
	}
}
