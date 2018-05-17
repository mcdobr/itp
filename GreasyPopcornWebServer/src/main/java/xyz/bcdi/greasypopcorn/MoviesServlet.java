package xyz.bcdi.greasypopcorn;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import xyz.bcdi.greasypopcorn.core.Movie;

/**
 * Servlet implementation class MovieServlet
 */
public class MoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String API_URL = "http://localhost:1212/GreasyPopcornWebServicesServer/webapi";
	
	private final Client client;
    private final HttpAuthenticationFeature authFeature;
	
	public MoviesServlet() {
        super();
        client = ClientBuilder.newClient();
        authFeature = HttpAuthenticationFeature.basic("mircea", "abc");
        client.register(authFeature);
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
    	String pathInfo = request.getPathInfo();
    	System.out.println(pathInfo);
		// Create web target and specific path on web target
		WebTarget moviesTarget = client.target(API_URL).path("movies/");
						
		// Make the request
		Invocation.Builder invocationBuilder = moviesTarget.request(MediaType.APPLICATION_JSON);
		
		String jsonResponse = invocationBuilder.get().readEntity(String.class);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().append(jsonResponse);
		
		//List<Movie> wssResponse = invocationBuilder.get(new GenericType<List<Movie>>() {});
		//sysout(invocationBuilder.buildGet().toString();
		/*for (Movie m : wssResponse) {
			//System.out.println(m.getName());
			response.getWriter().println(m.getMovieID() + " " + m.getName());
		}*/
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebTarget moviesTarget = client.target(API_URL).path("movies");
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}
}
