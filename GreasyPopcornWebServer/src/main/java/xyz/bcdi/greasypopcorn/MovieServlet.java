package xyz.bcdi.greasypopcorn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

import xyz.bcdi.greasypopcorn.core.Movie;

/**
 * Servlet implementation class MovieServlet
 */
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String API_URL = "http://localhost:8080/GreasyPopcornWebServicesServer/webapi";
	
	private Client client;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        client = ClientBuilder.newClient();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Create web target and specific path on web target
		WebTarget moviesTarget = client.target(API_URL).path("movies");
						
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebTarget moviesTarget = client.target(API_URL).path("movies");
		
	}

}
