
package myControllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import myClasses.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myModels.*;

import org.apache.commons.fileupload.servlet.ServletFileUpload;



@WebServlet(name = "controller_upload_data_onside", urlPatterns = {"/upload_data_onside"})
public class controller_upload_data_onside extends HttpServlet {
    String myReportType ="200";
    
    clsConverter c = new clsConverter();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"myMainTemplate_Onside_Admin.html");
        clsDataBase myCls=new clsDataBase();
               
	if(myClsMain.CheckPermission("21","22")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
               
        String  myType= myClsMain.myRequest.getParameter("Type");
                
        if (myType.equals("New")){
            LoadPage(myClsMain,myCls);
        }else if (myType.equals("Upload")){
            LoadPage(myClsMain,myCls);
        }else if (myType.equals("Download")){
            Download(myClsMain,myCls);
        }
    }
    
   private void PrintUploadPage(clsMain myClsMain,clsDataBase myCls,String pMessage){
       String myMainPageTemplate=myClsMain.getContentFromFile("myMainTemplate_Onside_Admin.html");
       String myMainContain=myClsMain.getContentFromFile("myupload_data_contain_for_onside.html");         
             
       
       myMainContain=myMainContain.replace("#MESSAGE#", pMessage);
       myMainContain=myMainContain.replace("#myVal_FIUP_REPORT_DATE#", c.getSystemDate());
       myMainContain=myMainContain.replace("#myVal_FIUP_MEMB_NO#", myCls.getSelectOptoinValue("MEMB_NO","MEMB_NAME","DATA_MEMBERS WHERE MEMB_STATUS=1","100"));
       
       String myDownloadLink = getTemplatDownloadContain();
       myMainContain=myMainContain.replace("##Download##", myDownloadLink);       
       myMainPageTemplate=myMainPageTemplate.replace("###Contain####", myMainContain);
       myClsMain.PrintOut(myMainPageTemplate);
   }
   
   
   private void Download(clsMain myClsMain,clsDataBase myCls) {
       String myScript ="SELECT FIUP_NAME FROM DATA_FILE_UPLOAD JOIN DATA_SESSION ON SESS_NO=FIUP_SESS_NO AND FIUP_STATUS IN (1,2) WHERE FIUP_DURA_NO=" + myReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO();
       String myFileName=myCls.ExecuteScalar(myScript).toString();
       String myPart=myCls.getParameter_Value(myReportType);
       clsFileDownload myFileDownload =new clsFileDownload();
       myFileDownload.StartDowload(myClsMain.myRequest,myClsMain.myResponse, myPart + "\\" + myFileName ,myFileName);
       
   }
   
   private void LoadPage(clsMain myClsMain,clsDataBase myCls) {
        
             
        String myFileName="";
        boolean isMultipart = ServletFileUpload.isMultipartContent(myClsMain.myRequest);
        if (isMultipart) {   

            String myFileNumber=c.getDateTime();
            String myBankNo=myClsMain.myDATA_USERS.getUSER_MEMB_NO().toString();
            myFileName=myBankNo + "_" + myFileNumber;
            String myPart=myCls.getParameter_Value(myReportType);
            String myTmp=myCls.getParameter_Value("TEMP_FOLDER");

            clsFileUpload_onside myFileUpload =new clsFileUpload_onside(myClsMain,myCls,myTmp,myPart);
            myFileUpload.FileName=myFileName;
            myFileUpload.UploadFile();
            
            if (myFileUpload.isERROR==false){
                model_DATA_ONSIDE_FILES myDATA_ONSIDE_FILES= new model_DATA_ONSIDE_FILES();
                
                myDATA_ONSIDE_FILES.setFIUP_NAME(myFileName +"."+ myFileUpload.cExtenstion);
                myDATA_ONSIDE_FILES.setFIUP_REPORT_DATE(myFileUpload.FIUP_REPORT_DATE);
                myDATA_ONSIDE_FILES.setFIUP_DES(myFileUpload.FIUP_DES);
                myDATA_ONSIDE_FILES.setFIUP_MEMB_NO(myFileUpload.FIUP_MEMB_NO);
                myDATA_ONSIDE_FILES.setFIUP_STATUS("200");
                myDATA_ONSIDE_FILES.setFIUP_DS(myFileUpload.DS);
                myDATA_ONSIDE_FILES.setFIUP_ADS("");
                myDATA_ONSIDE_FILES.setFIUP_SIZE(myFileUpload.cFileSide.toString());
                myDATA_ONSIDE_FILES.setFIUP_CONTANT(myFileUpload.cFileContent);
                myDATA_ONSIDE_FILES.setFIUP_AUID("-1");
                myDATA_ONSIDE_FILES.setFIUP_ASYSDATE("");
                myDATA_ONSIDE_FILES.setFIUP_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
                myDATA_ONSIDE_FILES.setFIUP_SYSDATE("sysdate");
                String myScriptInsert =myDATA_ONSIDE_FILES.getInsertScript();                         
                if (!myScriptInsert.equals("")){
                    myCls.ExecuteNonQuery(myScriptInsert);
                    if (myCls.isERROR()==false){
                        PrintUploadPage(myClsMain,myCls,"<font size='-1' color='#0000FF'>File name:"+ myFileUpload.cFileName +" size: "+ myFileUpload.cFileSide +" byte has been uploaded</font><br/><br/><br/>");
                    }else{
                        myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                    }                        
                }else{
                    myClsMain.PrintOut("Error class insert script....");
                }                              
            }                            
            myClsMain.PrintOut (myFileUpload.ErrorMessage);
        }else{            
            PrintUploadPage(myClsMain,myCls,"");
        }
    }

    private String getDS(String pFilePath){
       return pFilePath;
   }
   
    private String getTemplatDownloadContain(){
         
         String myDownloadLink="";
         
         if (myReportType.equals("21")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/OnsideTemplate.xlsx'>Downlaod&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";
                          
        }
    
       return myDownloadLink;   
    }    
    
    
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
     
    
}
