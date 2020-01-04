package webtest.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.util.DbUtils;

/**
 * Servlet implementation class SetUpEnvironment
 */
@WebServlet("/SetUpEnvironment")
public class SetUpEnvironment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetUpEnvironment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/SetUpEnvironment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String host = request.getParameter("host");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String database = request.getParameter("database");
			
			DbUtils.setUpDatabase(host, username, password, database);
			
			if (request.getParameter("firstTimeSetUp") != null && !request.getParameter("firstTimeSetUp").equals("")) 
			{
				DbUtils dbUtils = new DbUtils();
				dbUtils.createTables();
				dbUtils.close();
			}
			
			response.sendRedirect("Login?message=Set up enviromnet successful");
		}
		catch(Exception e) {
			request.setAttribute("message", "Errors occur when setting up the environment");
			doGet(request, response);
		}
	}

}
