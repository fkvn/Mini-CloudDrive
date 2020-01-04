package webtest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.File;
import webtest.util.DbUtils;

/**
 * Servlet implementation class Rename
 */
@WebServlet("/Rename")
public class Rename extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rename() {
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
			String id = "1";
			
			if (request.getParameter("id") != null)
				id = request.getParameter("id");
			
			DbUtils dbUtils = new DbUtils();
			File file = dbUtils.getFile(id);
			if (dbUtils.isTopFile(id))
				file.setParent_id("1");
			dbUtils.close();
			
			request.setAttribute("file", file);
			request.getRequestDispatcher("WEB-INF/Rename.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parent_id = "1";
		String id = "1";
		String fileName = "";
		
		if (request.getParameter("parent_id") != null)
			parent_id = request.getParameter("parent_id");
		
		if (request.getParameter("id") != null)
			id = request.getParameter("id");
		
		if (request.getParameter("fileName") != null)
			fileName = request.getParameter("fileName");
		
		DbUtils dbUtils = new DbUtils();
		String message = dbUtils.upDateFileName(id, fileName);
		boolean isTopFile = dbUtils.isTopFile(id);
		dbUtils.close();
		
		if (isTopFile) {
			response.sendRedirect("CloudDrive?message=" + message);
		}
		else {
			response.sendRedirect("CloudDrive?id=" + parent_id + "&message=" + message);
		}
	}

}
