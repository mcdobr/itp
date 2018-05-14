package xyz.bcdi.greasypopcorn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;

import xyz.bcdi.greasypopcorn.core.Movie;

/**
 * Servlet implementation class MovieServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String API_URL = "http://localhost:1212/GreasyPopcornWebServicesServer/webapi";
	
	private Client client;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        client = ClientBuilder.newClient();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Create web target and specific path on web target
		WebTarget userTarget = client.target(API_URL).path("users");
						
		// Make the request
		Invocation.Builder invocationBuilder = userTarget.request(MediaType.APPLICATION_JSON);
		
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
	
//	      String parameterName = null;
//	      Enumeration<String> enumeration = request.getParameterNames();
//	      while (enumeration.hasMoreElements()) {
//	          parameterName = (String) enumeration.nextElement();
//	      }
//	        
//
//	      	String[] parts = parameterName.split(",");
//	        String[] fullname = parts[0].split(":");
//	        String[] username = parts[1].split(":");
//	        String[] password = parts[2].split(":");
//	        String[] name = fullname[1].split("\"");
//	        String[] user = username[1].split("\"");
//	        String[] pass = password[1].split("\"");
//		String formData = 
//		"{\"name\":\""+name[1]+
//		"\",\"username\":\""+user[1]+
//		"\",\"password\":\""+pass[1]+"\"}";
		
		WebTarget userTarget= client.target(API_URL).path("users");
		Invocation.Builder invocationBuilder =  userTarget.request(MediaType.APPLICATION_JSON);
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
		  BufferedReader reader = request.getReader();
		  while ((line = reader.readLine()) != null)
			  jb.append(line);
		} catch (Exception e) { /*report an error*/ }
		System.out.println(jb);
		Response serverResponse = invocationBuilder.post(Entity.json(jb.toString()));
		String responseString = serverResponse.readEntity(String.class);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().append(responseString);
	}
}
