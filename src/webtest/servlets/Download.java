package webtest.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.File;
import webtest.models.User;
import webtest.util.DbUtils;


/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Download() {
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
			String id = "0";
			
			if (request.getParameter("id") != null)
				id = request.getParameter("id");

			DbUtils dbUtils = new DbUtils();
			File file = dbUtils.getFile(id);
			boolean isTopFile = dbUtils.isTopFile(file.getId());
			String user_id = ((User)request.getSession().getAttribute("user")).getId();
			System.out.println("user: " + user_id);
			if (request.getParameter("owner") != null)
				user_id = request.getParameter("owner");
			
			System.out.println("owner: " + user_id);
			
			InputStream inputStream = dbUtils.downloadFile(id, user_id);
			dbUtils.close();

			if (inputStream != null) {
				ServletContext context = getServletContext();
				int fileLength = inputStream.available();

				// sets MIME type for the file download
				String mimeType = context.getMimeType(file.getName());
				if (mimeType == null) {        
					mimeType = "application/octet-stream";
				}              

				// set content properties and header attributes for the response
				response.setContentType(mimeType);
				response.setContentLength(fileLength);
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
				response.setHeader(headerKey, headerValue);

				// writes the file to the client
				OutputStream outStream = response.getOutputStream();

				int BUFFER_SIZE = 4096;   
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();
			
				
			}
			else {
				if (isTopFile) {
					response.sendRedirect("CloudDrive?message=" + "File Not Found");
				}
				else {
					response.sendRedirect("CloudDrive?id=" + file.getParent_id() + "&message=" + "File Not Found");
				}
			}
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
