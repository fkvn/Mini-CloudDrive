package webtest.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.util.DbUtils;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parent_id = "1";
		String id = "1";
		
		if (request.getParameter("parent_id") != null)
			parent_id = request.getParameter("parent_id");
		
		if (request.getParameter("id") != null)
			id = request.getParameter("id");
		
		DbUtils dbUtils = new DbUtils();
		boolean isTopFile = dbUtils.isTopFile(id);
		String message = dbUtils.deleteFile(id);
		dbUtils.close();
		
		System.out.println(parent_id);
		System.out.println(id);
		System.out.println(isTopFile);
		
		if (isTopFile) {
			response.sendRedirect("CloudDrive?message=" + message);
		}
		else {
			response.sendRedirect("CloudDrive?id=" + parent_id + "&message=" + message);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
