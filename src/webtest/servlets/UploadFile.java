package webtest.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import webtest.models.File;
import webtest.models.User;
import webtest.util.DbUtils;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parent_id = "1";

		if(request.getParameter("parent_id") != null)
			parent_id = request.getParameter("parent_id");

		String owner_id = "1";

		if(request.getSession().getAttribute("user") != null)
			owner_id = ((User) request.getSession().getAttribute("user")).getId();

		InputStream inputStream = null; // input stream of the upload file
		System.out.println("Upload file: " + parent_id);
		Part filePart = request.getPart("uploadFile");
		if (filePart != null) {
			if(!extractFileName(filePart).equals(""))
			{
				File file = new File("", extractFileName(filePart), filePart.getContentType(),false, parent_id,  owner_id );
				inputStream = filePart.getInputStream();
				DbUtils dbUtils = new DbUtils();

				try {
					String message = dbUtils.uploadFile(file, inputStream);
					inputStream.close();
					if (parent_id.equals("1")) {
						response.sendRedirect("CloudDrive?message=" + message);
					}
					else {
						response.sendRedirect("CloudDrive?id=" + parent_id + "&message=" + message);
					}
					//				response.sendRedirect("CloudDrive?message=" + message);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				if (parent_id.equals("1")) {
					response.sendRedirect("CloudDrive?message=Can't upload empty files");
				}
				else {
					response.sendRedirect("CloudDrive?id=" + parent_id + "&message=Can't upload empty files");
				}
			}
		}
	}

	private String extractFileName(Part part) {
		// form-data; name="file"; filename="C:\file1.zip"
		// form-data; name="file"; filename="C:\Note\file2.zip"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				// C:\file1.zip
				// C:\Note\file2.zip
				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');
				// file1.zip
				// file2.zip
				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}

}
