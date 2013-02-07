/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.File;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.clsMain;
import myModels.clsDataBase;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
  

public class clsFileUpload_onside {
    
        public boolean isERROR=true;
        public String ErrorMessage="";
    
        public String TMP_DIR_PATH = "c:\\tmp";
	public File tmpDir;
	public String DESTINATION_DIR_PATH ="c:\\tmp\\files";
	public File destinationDir;
        public String FileName="";
 
        //File information....
        public String cFileName="";
        public String cExtenstion="";
        public String cFileContent="";
        public Long cFileSide;
        
        public String DS="";
        public String CA="";
        public String FIUP_MEMB_NO="";
        public String FIUP_DES="";
        public String FIUP_REPORT_DATE="";
        
        public HttpServletRequest request;
        public HttpServletResponse response;
        
        
        clsMain myClsMain; 
        clsDataBase myCls;
        
        public clsFileUpload_onside(clsMain pClsMain,clsDataBase pCls,String pTmp,String pDes){
                myCls=pCls;
                myClsMain=pClsMain;
            
                this.request=myClsMain.myRequest;
                this.response=myClsMain.myResponse;
                this.TMP_DIR_PATH=pTmp;
                this.DESTINATION_DIR_PATH=pDes;
                
                tmpDir = new File(TMP_DIR_PATH);
		if(!tmpDir.isDirectory()) {
                        isERROR=true;
			ErrorMessage=TMP_DIR_PATH + " is not a directory";
		}
		//String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(DESTINATION_DIR_PATH);
		if(!destinationDir.isDirectory()) {
			isERROR=true;
			ErrorMessage=DESTINATION_DIR_PATH+" is not a directory";
		}
            
        }
	       
	protected void UploadFile()  {
	                    
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		/*
		 *Set the size threshold, above which content will be stored on disk.
		 */
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above threshold.
		 */
		fileItemFactory.setRepository(tmpDir);
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			/*
			 * Parse the request
			 */
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				
				if(item.isFormField()) {
                                    if (item.getFieldName().equals("certificationChain")){
                                        CA=item.getString();
                                        //String myScript ="SELECT USER_NO FROM DATA_USER WHERE USER_NO="+ myClsMain.myDATA_USERS.getUSER_NO() +" AND USER_CA='"+ CA +"'";
                                        if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA)!=0){
                                            ErrorMessage="Invalid eToken! please contact NBC System administrator to solve this problem"; 
                                            isERROR=true;
                                            return ;
                                        }                            
                                                                           
                                    }else if (item.getFieldName().equals("signature")){
                                        DS=item.getString();
                                    }else if (item.getFieldName().equals("myBox_FIUP_MEMB_NO")){
                                        FIUP_MEMB_NO=item.getString();
                                    }else if (item.getFieldName().equals("myBox_FIUP_REPORT_DATE")){
                                        FIUP_REPORT_DATE=item.getString();
                                    }else if (item.getFieldName().equals("myBox_FIUP_DES")){
                                        FIUP_DES=item.getString();
                                    }                                    
				} else {
                                        cFileName=item.getName();
                                        cFileContent=item.getContentType();
                                        cFileSide=item.getSize();
                                        cExtenstion=getFileExtension(cFileName);
                                        String myFileName=FileName + "." + cExtenstion;
					File file = new File(destinationDir,myFileName);
					item.write(file);
                                        isERROR=false;                                        
				}				
			}
		}catch(FileUploadException ex) {
                    isERROR=true;
                    ErrorMessage="Error encountered while parsing the request" + ex.getMessage();
		} catch(Exception ex) {
                    isERROR=true;
                    ErrorMessage="Error encountered while uploading file" + ex.getMessage();
		}
 
	}
        
        
       
        public String getFileExtension(String pFileName){
            int mid= pFileName.lastIndexOf(".");
            String myExtension=pFileName.substring(mid+1,pFileName.length());  
            return myExtension;
        }
}