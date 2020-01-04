package webtest.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.File;
import webtest.models.User;
import webtest.util.DbUtils;

/**
 * Servlet implementation class CloudDrive
 */
@WebServlet("/CloudDrive")
public class CloudDrive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CloudDrive() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null)
		{
			if (request.getParameter("owner") != null && request.getParameter("id") != null)
				response.sendRedirect("Login?id=" + request.getParameter("id") + "&owner=" + request.getParameter("owner"));
			else
				response.sendRedirect("Login");
		}
		else
		{
			String id = "";

			if (request.getParameter("id") != null)
				id = request.getParameter("id");

			User user = (User) request.getSession().getAttribute("user");
			List<File> files = new ArrayList<>();

			DbUtils dbUtils = new DbUtils();
			if (id.equals(""))
				files = dbUtils.getTopLevelFiles(user.getId());
			else
			{
				if (request.getParameter("owner") != null && !request.getParameter("owner").equals("")) {
					File file = dbUtils.getFile(id);
					files.add(file);
					
					System.out.println(files.get(0).getName());
					request.setAttribute("id", id);
					request.setAttribute("owner", request.getParameter("owner"));
				}
				else 
				{
					files = dbUtils.getSubFiles(id, user.getId());
					File file = dbUtils.getFile(id);
					boolean isTopFile = dbUtils.isTopFile(id);
					request.setAttribute("id", id);
					if (isTopFile)
						request.setAttribute("parent_id", "1");
					else
						request.setAttribute("parent_id", file.getParent_id());
				}
			}

			dbUtils.close();

			request.setAttribute("files", files);

			if(request.getParameter("message") != null)
				request.setAttribute("message",request.getParameter("message"));

			request.getRequestDispatcher("/WEB-INF/CloudDrive.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("searchKeyWord") != null && !request.getParameter("searchKeyWord").equals("")) {
			String searchKeyWord = "";
			searchKeyWord = request.getParameter("searchKeyWord");

			User user = (User) request.getSession().getAttribute("user");
			List<File> files = new ArrayList<>();

			DbUtils dbUtils = new DbUtils();
			files = dbUtils.getSearchFile(searchKeyWord, user.getId());
			dbUtils.close();

			for (int i = 0; i < files.size(); i++) {
				System.out.println(files.get(i).getName());
			}


			request.setAttribute("files", files);

			if(request.getParameter("message") != null)
				request.setAttribute("message",request.getParameter("message"));

			request.getRequestDispatcher("/WEB-INF/CloudDrive.jsp").forward(request, response);

		}
		else {
			doGet(request, response);
		}
	}

}
