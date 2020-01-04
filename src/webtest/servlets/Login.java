package webtest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.User;
import webtest.util.DbUtils;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("owner") != null && request.getParameter("id") != null)
		{
			request.setAttribute("owner", request.getParameter("owner"));
			request.setAttribute("id", request.getParameter("id"));
		}
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String user_name = "";
			String pass_word = "";
			
			if (!request.getParameter("username").isEmpty())
				user_name = request.getParameter("username");
			
			if (!request.getParameter("password").isEmpty())
				pass_word = request.getParameter("password");
			
			DbUtils dbUtils = new DbUtils();
			User user = dbUtils.authorizeUser(user_name, pass_word);
			dbUtils.close();
			if (user != null)
			{
				request.getSession().setAttribute("user", user);
				if (request.getParameter("owner") != null && request.getParameter("id") != null)
					response.sendRedirect("CloudDrive?id=" + request.getParameter("id") + "&owner=" + request.getParameter("owner"));
				else
					response.sendRedirect("CloudDrive");
			}
			else {
				request.setAttribute("message", "Invalid user name or password");
				doGet(request, response);
			}
		}
		catch(Exception e) {
			request.setAttribute("sqlError", "Exceptions Occur");
			doGet(request, response);
		}
	}

}
