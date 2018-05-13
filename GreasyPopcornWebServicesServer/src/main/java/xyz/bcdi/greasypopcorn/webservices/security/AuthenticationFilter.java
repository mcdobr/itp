package xyz.bcdi.greasypopcorn.webservices.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import javax.annotation.security.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import xyz.bcdi.greasypopcorn.webservices.security.SecurityUtils;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter{
	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		
		// If access is not universal
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// If access is forbidden to everyone
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(SecurityUtils.UNAUTHORIZED_RESPONSE.build());
				return;
			}
			
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get(SecurityUtils.AUTHORIZATION_PROPERTY);
			
			// the user is unauthenticated
			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(SecurityUtils.UNAUTHENTICATED_RESPONSE.build());
				return;
			}
			
			// Delete "Authentication: Basic" from header
			final String authHeader = authorization.get(0);
			final String[] userPassPair = SecurityUtils.getUserPassPair(authHeader);
			
			final String username = userPassPair[0];
			final String password = userPassPair[1];
			
			// Verify user access
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> allowedRolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				
				if (!SecurityUtils.isUserAllowedOnMethod(username, password, allowedRolesSet)) {
					requestContext.abortWith(SecurityUtils.UNAUTHORIZED_RESPONSE.build());
					return;
				}
			}
		}
	}
}
