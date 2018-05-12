package xyz.bcdi.greasypopcorn.webservices;

import java.net.URI;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

public abstract class AbstractResource {
	protected Response buildResponseForCreateEntity(Object obj, String id, UriInfo uriInfo) {
		if (obj != null) {
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			URI location = uriBuilder.path(id).build();
			return Response.status(Status.CREATED).location(location).entity(obj).build();
		} else
			return Response.status(Status.SEE_OTHER).build();
	}
	
	/**
	 * @param movieID
	 * @return 200 OK if something was deleted. 204 NO CONTENT otherwise.
	 */
	protected Response buildResponseForDeleteEntity(boolean wasDeleted)
	{
		if (wasDeleted)
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.NO_CONTENT).build();
	}
}
