
package myClasses;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myModels.clsDataBase;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
@WebServlet(name = "clsCommonsFileUploadServlet", urlPatterns = {"/FileUpload1234"})
public class clsCommonsFileUploadServlet extends HttpServlet {
        clsDataBase mCls=new clsDataBase();
    	private File tmpDir;
	private File destinationDir;
 
        @Override
	public void init(ServletConfig config) throws ServletException {
                super.init(config);
                String myTMP_DIR_PATH =mCls.getParameter_Value("TEMP_FOLDER");
                String myDESTINATION_DIR_PATH =mCls.getParameter_Value("DAILY_FOLDER");
		tmpDir = new File(myTMP_DIR_PATH);
		if(!tmpDir.isDirectory()) {
			throw new ServletException(myTMP_DIR_PATH + " is not a directory");
		}
		destinationDir = new File(myDESTINATION_DIR_PATH);
		if(!destinationDir.isDirectory()) {
			throw new ServletException(myDESTINATION_DIR_PATH+" is not a directory");
		}
 	}
 
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/plain");
 
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		
		fileItemFactory.setRepository(tmpDir);
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				
				if(item.isFormField()) {
					out.println("File Name = "+item.getFieldName()+", Value = "+item.getString());
				} else {
					//Handle Uploaded files.
                                       out.println("Field Name = "+item.getFieldName()+
						", File Name = " + item.getName()+
						", Content type = "+item.getContentType()+
						", File Size = "+item.getSize());
					
					File file = new File(destinationDir,item.getName());
					item.write(file);
                                        
				}
                                
				out.close();
			}
		}catch(FileUploadException ex) {
			log("Error encountered while parsing the request",ex);
		} catch(Exception ex) {
			log("Error encountered while uploading file",ex);
		}
 
	}
        
 
}