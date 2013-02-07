package myControllers;


import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myClasses.clsConverter;
import myClasses.clsMain;
import myModels.clsDataBase;
import myModels.model_DATA_ACK_COMPLAINT;
import myModels.model_DATA_FILE_UPLOAD;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.xml.sax.SAXException;

@WebServlet(name = "controller_upload_data_feedback", urlPatterns = {"/feedback"})
public class controller_upload_data_feedback extends HttpServlet {
    //String myReportType ="0";

    String myReportName = "";
    String Filename;
    String ReportID;
  

    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SAXException {
        HttpSession session = request.getSession(true);

        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request, response, "myMainTemplate.html");
        clsDataBase myCls = new clsDataBase();

        if (myClsMain.CheckPermission("12") == false) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        session.setAttribute("myReportType", myClsMain.myRequest.getParameter("ReportType"));

        String myType = myClsMain.myRequest.getParameter("Type");
        String myReportType = session.getAttribute("myReportType").toString();
        if (myReportType == null) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName = (String) myCls.ExecuteScalar("SELECT COMT_NAME FROM DATA_ACK_COMPLAINTTYPE WHERE COMT_NO="+myReportType);


        if (myType.equals("New")) {
            myReportType = session.getAttribute("myReportType").toString();
            LoadPage(myReportType, myClsMain, myCls);
        } else if (myType.equals("Upload")) {
            myReportType = session.getAttribute("myReportType").toString();
            LoadPage(myReportType, myClsMain, myCls);
        } else if (myType.equals("Download")) {
            myReportType = session.getAttribute("myReportType").toString();
            Download(myReportType, myClsMain, myCls);
        }
    }

    private void PrintUploadPage(String pReportType, clsMain myClsMain, String myStatus) {
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplate.html");
        String myMainContain = myClsMain.getContentFromFile("enquiry.html");
        String myContain = myClsMain.getContentFromFile("uploadinfor_feed.html");
        myContain = myContain.replace("#REPORT_NAME#", myReportName);
        myContain = myContain.replace("#STATUS#", myStatus);

        String myDownloadLink = getTemplatDownloadContain(pReportType);
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void PrintUploadPage_With_Form(String pReportType, clsMain myClsMain, String myStatus) {
        String myContain;
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplate.html");
        String myMainContain = myClsMain.getContentFromFile("enquiry.html");
              myContain = myClsMain.getContentFromFile("uploadinfo_with_form_for_feedback.html");
       // }
        myContain = myContain.replace("#REPORT_NAME#", myReportName);
      
        myContain = myContain.replace("#SESSION_TYPE#", pReportType);

        String myDownloadLink = getTemplatDownloadContain(pReportType);
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
      
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void Download(String pReportType, clsMain myClsMain, clsDataBase myCls) {
        String myScript = "SELECT FIUP_NAME FROM DATA_FILE_UPLOAD JOIN DATA_SESSION ON SESS_NO=FIUP_SESS_NO AND FIUP_STATUS IN (1,2) WHERE FIUP_DURA_NO=" + pReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO();
        String myFileName = myCls.ExecuteScalar(myScript).toString();
        String myPart = myCls.getParameter_Value(pReportType);
        clsFileDownload myFileDownload = new clsFileDownload();
        myFileDownload.StartDowload(myClsMain.myRequest, myClsMain.myResponse, myPart + "\\" + myFileName, myFileName);

    }

    private void LoadPage(String pReportType, clsMain myClsMain, clsDataBase myCls) throws IOException, ClassNotFoundException, SAXException {

        clsConverter c = new clsConverter();
        String myStatus = "";      
        try {
            

                    model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD = new model_DATA_FILE_UPLOAD();
                        //accept for upload       
                        String myFileName = "";
                        String Org_FileName="";
                        boolean isMultipart = ServletFileUpload.isMultipartContent(myClsMain.myRequest);
                        if (isMultipart) {

                            String myFileNumber = c.getDateTime();
                           
                            String myBankNo = myClsMain.myDATA_USERS.getUSER_MEMB_NO().toString();
                            myFileName =  myBankNo + "_" + myFileNumber;
                            String myPart = myCls.getParameter_Value(pReportType);
                            String myTmp = myCls.getParameter_Value("TEMP_FOLDER");

                            clsFileUpload myFileUpload = new clsFileUpload(myClsMain, myCls, myTmp, myPart);
                          
            // myFileUpload.FileName=myFileName;
                   //      myFileName= myFileUpload.cFileName;
                  //        String path = "";
                            myFileUpload.UploadFile();
                            String test= myFileUpload.cFileName;
                     String path = myPart.concat("\\").concat(myFileName + "." + myFileUpload.cExtenstion);
                          

                            if (myFileUpload.isERROR == false) {

                                myDATA_FILE_UPLOAD.setFIUP_NAME(myFileName + "." + myFileUpload.cExtenstion);
                                myDATA_FILE_UPLOAD.setFIUP_SESS_NO("Unlimited time upload");
                                myDATA_FILE_UPLOAD.setFIUP_DURA_NO(Integer.parseInt(pReportType));
                                myDATA_FILE_UPLOAD.setFIUP_MEMB_NO(Integer.parseInt(myClsMain.myDATA_USERS.getUSER_MEMB_NO()));
                                myDATA_FILE_UPLOAD.setFIUP_STATUS(1);
                                myDATA_FILE_UPLOAD.setFIUP_DS(myFileUpload.DS);
                                myDATA_FILE_UPLOAD.setFIUP_ADS("");
                                myDATA_FILE_UPLOAD.setFIUP_SIZE(myFileUpload.cFileSide);
                                myDATA_FILE_UPLOAD.setFIUP_CONTANT(myFileUpload.cFileContent);
                                myDATA_FILE_UPLOAD.setFIUP_AUID(-1);
                                myDATA_FILE_UPLOAD.setFIUP_ASYSDATE("");
                                myDATA_FILE_UPLOAD.setFIUP_UID(Integer.parseInt(myClsMain.myDATA_USERS.getUSER_NO()));
                                myDATA_FILE_UPLOAD.setFIUP_SYSDATE("sysdate");
                                myCls.ExecuteNonQuery( myDATA_FILE_UPLOAD.getInsertScript());
                                
                                // insert data complaint 
                                
                                
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                myClsMain.Alert("Your "+myReportName+" has been upload at "+dateFormat.format(date));
                                model_DATA_ACK_COMPLAINT Data_Ack= new model_DATA_ACK_COMPLAINT();
                                 String myQuery = "SELECT FIUP_NO FROM DATA_FILE_UPLOAD";
                                            myQuery += " WHERE FIUP_NAME='" +myDATA_FILE_UPLOAD.getFIUP_NAME()  + "' AND FIUP_DURA_NO=" + pReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                                      ResultSet    myResultSet = myCls.ExecuteReader(myQuery);
                                            myResultSet.next();
                                            ReportID = myResultSet.getNString(1);
                                Data_Ack.setCOMP_MEMBERID(myClsMain.myDATA_USERS.getUSER_MEMB_NO());
                                Data_Ack.setCOMP_DESC("No description");
                                Data_Ack.setCOMP_UPLOAD_USER_ID(myClsMain.myDATA_USERS.getUSER_NO());
                                Data_Ack.setCOMP_UPLOAD_DATE(myDATA_FILE_UPLOAD.getFIUP_SYSDATE());
                                Data_Ack.setCOMP_DIRECTOR("-1");
                                Data_Ack.setCOMP_TYPEID(pReportType);
                                Data_Ack.setCOMP_FILEID(ReportID);
                                Data_Ack.setCOMP_ORG_FILENAME(test);
                                String test2= Data_Ack.getInsertScript();
                                
                                  myCls.ExecuteNonQuery(Data_Ack.getInsertScript());
                                PrintUploadPage_With_Form(pReportType, myClsMain, myStatus);
                            
                                if (myCls.isERROR() == false) {
                                    LoadPage(pReportType, myClsMain, myCls);
                                } else {
                                    myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                                }
                          
                                 
                                
                            }
                            myClsMain.PrintOut(myFileUpload.ErrorMessage);
                        } else {
                            myStatus = "Open";
                            PrintUploadPage_With_Form(pReportType, myClsMain, myStatus);
                        }
                    

              
          
        } catch (Exception e) {
        }
    }

    private String getDS(String pFilePath) {
        return pFilePath;
    }

    private String getTemplatDownloadContain(String pReportType) {

        String myDownloadLink = "";

        if (pReportType.equals("11")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>testing file &nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";

        } 
         if (pReportType.equals("12")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>testing file &nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";

        } 
        return myDownloadLink;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SAXException ex) {
                Logger.getLogger(controller_upload_data_feedback.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data_feedback.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SAXException ex) {
                Logger.getLogger(controller_upload_data_feedback.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data_feedback.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
