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
 * Servlet implementation class RecoverPassword
 */
@WebServlet("/RecoverPassword")
public class RecoverPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoverPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/RecoverPassword.jsp").forward(request, response);
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
			
			System.out.println(pass_word);

			DbUtils dbUtils = new DbUtils();
			if (!dbUtils.isDuplicateUser(user_name)) {
				request.setAttribute("message", "This username hasn't registered yet. Please make sure you enter the correct username");
				dbUtils.close();
				doGet(request, response);
			}
			else  {
				User user = dbUtils.getUserByName(user_name);
				user = dbUtils.recoverPassword(user.getId(), pass_word);
				dbUtils.close();
				if (user != null)
				{
					request.getSession().setAttribute("user", user);
					response.sendRedirect("CloudDrive");
				}
				else {
					request.setAttribute("message", "Please provide correct information");
					doGet(request, response);
				}
			}
		}
		catch(Exception e) {
			request.setAttribute("sqlError", "Exceptions Occur");
			doGet(request, response);
		}
	}

}
