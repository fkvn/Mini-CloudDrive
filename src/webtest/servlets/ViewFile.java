package webtest.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.models.File;
import webtest.util.DbUtils;

/**
 * Servlet implementation class ViewFile
 */
@WebServlet("/ViewFile")
public class ViewFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		DbUtils dbUtils = new DbUtils();
		File file = dbUtils.getFile(id);
		
		ServletContext context = getServletContext();
		int fileLength = file.getFileData().length;

		// sets MIME type for the file download
		String mimeType = context.getMimeType(file.getName());
		if (mimeType == null) {        
			mimeType = "application/octet-stream";
		}              

		// set content properties and header attributes for the response
		response.setContentType(mimeType);
		System.out.println(mimeType);
		response.setContentLength(fileLength);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("inline; filename=\"%s\"", file.getName());
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(file.getFileData());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
