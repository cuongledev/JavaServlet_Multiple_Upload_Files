package DAO;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HomeDAO {
	
	
	
	public static void UploadMultipleFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String Address = "E:\\";
		final int MaxMemorySize = 1024 * 1024 * 3; //3MB
		final int MaxRequestSize = 1024 * 1024 * 50; //50MB
		
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if(!isMultipart) {
			request.setAttribute("msg", "Not have enctype");
		}
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Set factory constraints
		factory.setSizeThreshold(MaxMemorySize);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Set overall request size constraint
		upload.setSizeMax(MaxRequestSize);
		
		
		try {
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (!item.isFormField()) {
			    	
			    	String fileName = item.getName();
			    	// pathFile: vị trí muốn upload vào nơi này
			    	String pathFile = Address + File.separator + fileName;
			    	
			    	File uploadedFile = new File(pathFile);
			    	
			    	boolean kt = uploadedFile.exists();
			    	
			    	try {
			    		if(kt==true) {
			    			request.setAttribute("messager","File exits");
			    		}else {
			    			// ghi file 
							item.write(uploadedFile);
							request.setAttribute("messager","Uploaded Success!!!");
			    		}
			    		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Error Log: " + e.getMessage());
					}
			    	
			    	
			    } else {
			    	System.out.println("Error Log: Not file false ");
			    }
			}
			
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Log: " + e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("Views/Result.jsp");
		rd.forward(request, response);
		
	}
}
