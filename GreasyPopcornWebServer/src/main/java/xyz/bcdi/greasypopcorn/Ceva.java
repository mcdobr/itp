package xyz.bcdi.greasypopcorn;

import java.io.IOException;
<<<<<<< HEAD
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
=======
import java.util.List;

>>>>>>> a6955d747fb1218c25098fc3562488322f1a359b
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
 * Servlet implementation class Ceva
 */
public class Ceva extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private Client client;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ceva() {
        super();
        client = ClientBuilder.newClient();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Create web target and specific path on web target
		WebTarget webTarget = client.target("http://localhost:8080/GreasyPopcornWebServicesServer/webapi");
		WebTarget moviesTarget = webTarget.path("movies");
						
		// Request
		Invocation.Builder invocationBuilder = moviesTarget.request(MediaType.APPLICATION_JSON);
				
		List<Movie> wssResponse = invocationBuilder.get(new GenericType<List<Movie>>() {});
		//sysout(invocationBuilder.buildGet().toString();
		for (Movie m : wssResponse) {
			//System.out.println(m.getName());
			response.getWriter().println(m.getMovieID() + " " + m.getName());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		
	}

}
