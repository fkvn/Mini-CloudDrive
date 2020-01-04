package webtest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.File;
import webtest.models.User;
import webtest.util.DbUtils;

/**
 * Servlet implementation class NewFolder
 */
@WebServlet("/NewFolder")
public class NewFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewFolder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("Login");
		else {
			
			if (request.getParameter("id") != null)
				request.setAttribute("id", request.getParameter("id"));
			else 
				request.setAttribute("id", "1");
			
			request.getRequestDispatcher("WEB-INF/NewFolder.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parent_id = "";
		
		if (request.getParameter("parent_id") != null)
			parent_id = request.getParameter("parent_id");
		
		String folderName = "";
		String message  = "";
		if (request.getParameter("folderName") != null)
			folderName = request.getParameter("folderName");
		
		if (!folderName.equals(""))
		{
			File file = new File("", folderName, "Folder", true, parent_id, ((User)request.getSession().getAttribute("user")).getId());
			
			DbUtils dbUtils = new DbUtils();
			message = dbUtils.addNewFolder(file);
			dbUtils.close();
		}
		else {
			message = "Can't create a folder with empty name";
		}
		
		if (parent_id.equals("1")) {
			response.sendRedirect("CloudDrive?message=" + message);
		}
		else {
			response.sendRedirect("CloudDrive?id=" + parent_id + "&message=" + message);
		}
		
	}

}
