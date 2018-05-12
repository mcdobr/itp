package xyz.bcdi.greasypopcorn.webservices;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.*;

import org.glassfish.jersey.internal.util.Base64;

import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.dbaccess.UserDAO;


@Provider
public class AuthenticationFilter implements ContainerRequestFilter{
	
	// HTTP has a misnomer on 401 (it should be unauthenticated not unauthorized). 403 is for unauthorized.
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final ResponseBuilder UNAUTHENTICATED_RESPONSE = Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity("You are not logged in!");
	private static final ResponseBuilder UNAUTHORIZED_RESPONSE = Response.status(Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).entity("You are not authorized to do this!");
	
	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		
		// If access is not universal
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// If access is forbidden to everyone
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(UNAUTHORIZED_RESPONSE.build());
				return;
			}
			
			
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
			
			// the user is unauthenticated
			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(UNAUTHENTICATED_RESPONSE.build());
				return;
			}
			
			// Delete "Authentication: Basic" from header
			final String encodedUserPass = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
			final String usernameAndPassword = new String(Base64.decode(encodedUserPass.getBytes()));;
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();
			
			// Verify user access
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> allowedRolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				
				if (!isUserAllowed(username, password, allowedRolesSet)) {
					requestContext.abortWith(UNAUTHORIZED_RESPONSE.build());
					return;
				}
			}
		}
	}

	public static String[] getUserPassPair(String authHeader) {
		final String encodedUserPass = authHeader.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		final String usernameAndPassword = new String(Base64.decode(encodedUserPass.getBytes()));;
		
		// Split the string by ':'
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		
		return new String[] {username, password};
	}
	
	public static boolean isUserAllowed(String username, String password, Set<String> allowedRolesSet) {
		User user = UserDAO.getInstance().getUser(username);
		if (user == null || !password.equals(user.getPassword()))
			return false;
		
		if (user.getIsModerator() && allowedRolesSet.contains("Moderator"))
			return true;
		if (!user.getIsModerator() && allowedRolesSet.contains("RegisteredUser"))
			return true;
		return false;
	}
}
