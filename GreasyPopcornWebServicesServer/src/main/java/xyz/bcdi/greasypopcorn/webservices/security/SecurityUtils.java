package xyz.bcdi.greasypopcorn.webservices.security;

import java.util.Set;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;

import xyz.bcdi.greasypopcorn.core.Review;
import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.dbaccess.UserDAO;

/**
 * @author mircea
 */
public final class SecurityUtils {

	// HTTP has a misnomer on 401 (it should be unauthenticated not unauthorized). 403 is for unauthorized.
	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	public static final String AUTHENTICATION_SCHEME = "Basic";
	public static final ResponseBuilder UNAUTHENTICATED_RESPONSE = Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity("You are not logged in!");
	public static final ResponseBuilder UNAUTHORIZED_RESPONSE = Response.status(Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).entity("You are not authorized to do this!");
	
	/**
	 * @param authHeader The HTTP basic auth header string.
	 * @return An array with two Strings representing the username and the password.
	 */
	public static String[] getUserPassPair(String authHeader) {
		final String encodedUserPass = authHeader.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		final String usernameAndPassword = new String(Base64.decode(encodedUserPass.getBytes()));;
		
		if (usernameAndPassword.contains(":")) {
			final String[] userPassPair = usernameAndPassword.split(":");
			return userPassPair;
		} else
			throw new IllegalArgumentException("Invalid authHeader!");
	}
	
	/**
	 * 
	 * @param username Username supplied by basic auth header.
	 * @param password Password supplied by basic auth header.
	 * @return If the user is authenticated (proves his identity).
	 */
	public static boolean isAuthenticationValid(String username, String password) {
		User user = UserDAO.getInstance().getUser(username);
		if (user == null || !password.equals(user.getPassword()))
			return false;
		return true;
	}
	
	public static boolean isAuthenticationValid(String[] userPassPair) {
		return isAuthenticationValid(userPassPair[0], userPassPair[1]);
	}
	
	/**
	 * @param username Username supplied by basic auth header.
	 * @param password Password supplied by basic auth header.
	 * @param allowedRolesSet Set of annotation strings present on the method.
	 * @return If the user can access the method.
	 */
	public static boolean isUserAllowedOnMethod(String username, String password, Set<String> allowedRolesSet) {
		if (!isAuthenticationValid(username, password))
			return false;
		
		User user = UserDAO.getInstance().getUser(username);
		if (user.getIsModerator() && allowedRolesSet.contains("Moderator"))
			return true;
		if (!user.getIsModerator() && allowedRolesSet.contains("RegisteredUser"))
			return true;
		return false;
	}
	
	/**
	 * @param username Username supplied by basic auth header.
	 * @param password Password supplied by basic auth header.
	 * @param obj Called resource.
	 * @return If the user is authorized to modify the entity.
	 */
	public static boolean isUserAuthorized(String authHeader, Object obj) {
		final String[] userPassPair = getUserPassPair(authHeader);
		final String username = userPassPair[0];
		final String password = userPassPair[1];
		
		if (!isAuthenticationValid(username, password))
			return false;
		
		// A user with an account is making the request
		User user = UserDAO.getInstance().getUser(username);
		if (user.getIsModerator()) {
			return true;
		} else {
			// A registered user is making the request
			if (obj instanceof User) {
				User res = (User)obj;
				return (username.equals(res.getUsername()));
			} else if (obj instanceof Review) {
				Review res = (Review)obj;
				return (username.equals(res.getUsername()));
			}
			return false;
		}
	}
}
