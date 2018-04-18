package xyz.bcdi.greasypopcorn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ceva
 */
public class Ceva extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ceva() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		 try (PrintWriter out = response.getWriter()) {

		        out.println("[{\"name\": \"Nadal, Rafael (ESP)\", \"email\": \"nadalrafael@gmail.com\", \"rank\": \"1\"},"
		                        + "{\"name\": \"Djokovic, Novak (SRB)\", \"email\": \"djokovicnovak@gmail.com\", \"rank\": \"2\"},"
		                        + "{\"name\": \"Federer, Roger (SUI)\", \"email\": \"federerroger@gmail.com\", \"rank\": \"3\"},"
		                        + "{\"name\": \"Wawrinka, Stan (SUI)\", \"email\": \"wawrinkastan@gmail.com\", \"rank\": \"4\"},"
		                        + "{\"name\": \"Ferrer, David (ESP)\", \"email\": \"ferrerdavid@gmail.com\", \"rank\": \"5\"}]");
		        }
		 catch (Exception e) {
	         e.printStackTrace();
	      }
        
       /* RequestDispatcher rd= request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
