/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import myClasses.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myModels.clsDataBase;
import myModels.model_DATA_FILE_UPLOAD;
import myModels.model_DATA_SESSION;

/**
 *
 * @author NHEP-BORA
 */
@WebServlet(name = "controller_authorize", urlPatterns = {"/authorize"})
public class controller_authorize extends HttpServlet {
    String myReportType ="0";
    String myReportName="";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"");
        clsDataBase myCls=new clsDataBase();
               
	if(myClsMain.CheckPermission("11")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        
        myReportType=myClsMain.myRequest.getParameter("ReportType");
        String  myType= myClsMain.myRequest.getParameter("Type");
        if (myReportType==null || myType==null){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName=myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();
               
        if (myType.equals("Download")){
            Download(myClsMain,myCls);
        }else{
            LoadPage(myClsMain,myCls,myType);
        }              
    }
    
   private void PrintUploadPage(clsMain myClsMain,String mySession_Name,String myOUP , String myCUP,String myStatus){
       String myMainPageTemplate=myClsMain.getContentFromFile("myMainTemplateAdmin.html");
       String myMainContain=myClsMain.getContentFromFile("myauthorize_data_contain.html");
       String myContain =myClsMain.getContentFromFile("uploadinfor.html");
       myContain=myContain.replace("#REPORT_NAME#", myReportName);
       myContain=myContain.replace("#SESSION_NAME#", mySession_Name);
       myContain=myContain.replace("#OUP#", myOUP);
       myContain=myContain.replace("#CUP#", myCUP);
       myContain=myContain.replace("#STATUS#", myStatus);   
       
       String myDownloadLink = getTemplatDownloadContain();
       myMainContain=myMainContain.replace("#REPORT_NAME#", myReportName);
       myMainContain=myMainContain.replace("#SESSION_NAME#", mySession_Name);
       myMainContain=myMainContain.replace("##Download##", myDownloadLink);
       myMainContain=myMainContain.replace("##upload_form##", myContain);
       myMainPageTemplate=myMainPageTemplate.replace("###Contain####", myMainContain);
       myClsMain.PrintOut(myMainPageTemplate);
   }
   
   private void PrintUploadPage_With_Authorize(clsMain myClsMain,String mySession_Name,String myOUP , String myCUP,String myStatus,String pDS){
       String myMainPageTemplate=myClsMain.getContentFromFile("myMainTemplateAdmin.html");
       String myMainContain=myClsMain.getContentFromFile("myauthorize_data_contain.html");
       String myContain =myClsMain.getContentFromFile("uploadinfo_with_authorize.html");
       myContain=myContain.replace("#REPORT_NAME#", myReportName);
       myContain=myContain.replace("#SESSION_NAME#", mySession_Name);
       myContain=myContain.replace("#OUP#", myOUP);
       myContain=myContain.replace("#CUP#", myCUP);
       myContain=myContain.replace("#STATUS#", myStatus);  
       myContain=myContain.replace("#SESSION_TYPE#", myReportType);
       myContain=myContain.replace("#DS#", pDS);
       
       String myDownloadLink = getTemplatDownloadContain();
       myMainContain=myMainContain.replace("#REPORT_NAME#", myReportName);
       myMainContain=myMainContain.replace("#SESSION_NAME#", mySession_Name);
       myMainContain=myMainContain.replace("##Download##", myDownloadLink);
       myMainContain=myMainContain.replace("##upload_form##", myContain);
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
   
   private void LoadPage(clsMain myClsMain,clsDataBase myCls , String pType) {
        clsConverter c = new clsConverter();
        
        String mySession_Name="";
        String myOUP ="";
        String myCUP = "";
        String myStatus="";
               
        
        String myScript ="SELECT SESS_DURA_NO,SESS_NO,SESS_NAME,NVL(to_char(SESS_RF, 'yyyy/mm/dd'),'') SESS_RF,NVL(to_char(SESS_RT, 'yyyy/mm/dd'),'') SESS_RT,NVL(to_char(SESS_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_OUP,NVL(to_char(SESS_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_CUP,SESS_SYSUID,NVL(to_char(SESS_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_SYSDATE,to_char(SYSDATE, 'yyyy/mm/dd hh24:mi:ss') SYSTEMDATETIME FROM DATA_SESSION";
        myScript +=" WHERE SESS_DURA_NO=" + myReportType;
        model_DATA_SESSION myUploadSession =new model_DATA_SESSION();
        
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try{         
            if(myResultSet.next()){
                
                myUploadSession.setData(myResultSet);
                String myCurrentDate=myResultSet.getString("SYSTEMDATETIME");
                
                if (myCls.isERROR()==true){
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();                
                //If session still open
                myOUP=myUploadSession.getSESS_OUP();
                myCUP=myUploadSession.getSESS_CUP();
                mySession_Name=myUploadSession.getSESS_NAME();
                if (c.CompareDateTimeInBetween(myCurrentDate,myUploadSession.getSESS_OUP(),myUploadSession.getSESS_CUP())==0){
                    model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD=new model_DATA_FILE_UPLOAD();                    
                    
                    myScript=myDATA_FILE_UPLOAD.getSelectScript();        
                    myScript += " WHERE FIUP_SESS_NO='" + myUploadSession.getSESS_NO() + "' AND FIUP_DURA_NO=" + myUploadSession.getSESS_DURA_NO() + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                    myResultSet = myCls.ExecuteReader(myScript);
                    if(myResultSet.next()){                        
                        myDATA_FILE_UPLOAD.setData(myResultSet);
                        myResultSet.close();
                        myCls.CloseResultSet();
                        if (myDATA_FILE_UPLOAD.getFIUP_STATUS()==1){
                            if (pType.equals("Authorize")){
                                String CA= myClsMain.myRequest.getParameter("certificationChain");
                                String DS =myClsMain.myRequest.getParameter("signature");
                                if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA)!=0){
                                            myClsMain.PrintOut("Invalid eToken! please contact NBC System administrator to solve this problem"); 
                                            return ;
                                }
                                String myStr ="UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=2, FIUP_ADS="+ c.toORACLEString(DS) +" , FIUP_AUID="+ myClsMain.myDATA_USERS.getUSER_NO() +",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myDATA_FILE_UPLOAD.getFIUP_NO();
                                String myScriptReport="UPDATE RPT_REPORT SET RPOT_DIRECTOR= "+myClsMain.myDATA_USERS.getUSER_NO()+"WHERE RPOT_FILEID="+ myDATA_FILE_UPLOAD.getFIUP_NO();
                                myCls.ExecuteNonQuery(myStr);
                                 myCls.ExecuteNonQuery(myScriptReport);
                                if (myCls.isERROR()==true) {
                                    myClsMain.PrintOut(myCls.getERROR_Message());
                                }else{
                                    try {
                                        myClsMain.myResponse.sendRedirect("authorize?ReportType="+ myReportType +"&Type=New");
                                    } catch (IOException ex) {
                                        myClsMain.PrintOut(ex.getMessage());
                                    }
                                } 
                            }else if (pType.equals("Reject")){
                                String CA= myClsMain.myRequest.getParameter("certificationChain");
                                String DS =myClsMain.myRequest.getParameter("signature");
                                if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA)!=0){
                                            myClsMain.PrintOut("Invalid eToken! please contact NBC System administrator to solve this problem"); 
                                            return ;
                                }
                                String myStr ="UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=3,FIUP_AUID="+ myClsMain.myDATA_USERS.getUSER_NO() +",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myDATA_FILE_UPLOAD.getFIUP_NO();
                                myCls.ExecuteNonQuery(myStr);
                                if (myCls.isERROR()==true) {
                                    myClsMain.PrintOut(myCls.getERROR_Message());
                                }else{
                                    try {
                                        myClsMain.myResponse.sendRedirect("authorize?ReportType="+ myReportType +"&Type=New");
                                    } catch (IOException ex) {
                                        myClsMain.PrintOut(ex.getMessage());
                                    }
                                } 
                            }else{
                                myStatus = "File " + myReportName + " report for " + mySession_Name +" has been uploaded at "+ myDATA_FILE_UPLOAD.getFIUP_SYSDATE() +" and waiting for your authorization.<br/><br/>Note : Before you authorize please download file and check the content to make sure the file is correct. <br/> To download your uploaded file <a href='authorize?ReportType="+ myReportType +"&Type=Download'>Click Here</a>" ;
                                PrintUploadPage_With_Authorize(myClsMain,mySession_Name,myOUP,myCUP,myStatus,myDATA_FILE_UPLOAD.getFIUP_DS());                                                     
                            }                                               
                            
                            return;
                        }else{
                            myStatus = "Your " + myReportName + " report for " + mySession_Name +" has been uploaded at "+ myDATA_FILE_UPLOAD.getFIUP_SYSDATE() +" and authorized on " + myDATA_FILE_UPLOAD.getFIUP_ASYSDATE() + ".<br/>To download your uploaded file <a href='authorize?ReportType="+ myReportType +"&Type=Download'>Click Here</a>" ;
                            PrintUploadPage(myClsMain,mySession_Name,myOUP,myCUP,myStatus);
                            return;
                        }
                    }else{
                            myStatus="File not available for you to authorize";
                            PrintUploadPage(myClsMain,mySession_Name,myOUP,myCUP,myStatus);                    
                    }
                
                }else{
                   //Session has been closed
                   myStatus= myReportName + " report for " + myUploadSession.getSESS_NAME() + " is closed.";
                   PrintUploadPage(myClsMain,mySession_Name,myOUP,myCUP,myStatus);
                   return;
                }
            }else{
                //Error no available
                myClsMain.PrintOut("Session Error");
            }
        }catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }finally{
            myCls.CloseResultSet(); 
        }
    }

    private String getDS(String pFilePath){
       return pFilePath;
   }
   
    private String getTemplatDownloadContain(){
         
         String myDownloadLink="";
        if (myReportType.equals("1")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";
                          
        }else if(myReportType.equals("2")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/WeeklyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏</a></font>";
          
        }else if(myReportType.equals("3")){
              myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyBasedPeriod.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Base Period</br>(ធនាគារពាណិជ្ជ)</a>"
                          + "</BR><a href='/BSX/FileTemplate/2007/Reports_BiWeekly_Reserve_Requirement_BasedPeriod_Specialized_Bank.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏ Base Period</br>(ធនាគារឯកទេស)</a></font>";
      
        }else if(myReportType.equals("31")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyMaintenance.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Maintenence</a></font>";
              
        }else if(myReportType.equals("32")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyLR.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Liquidity Ratio</a></font>";
              
        }else if(myReportType.equals("4")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/MonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំខែ</a></font>";
                     
        }else if(myReportType.equals("41")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/COAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ប្រចាំខែ</a></font>";
                     
        }else if(myReportType.equals("5")){
            myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/QuarterlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំត្រីមាស</a></font>";
                      
        }else if(myReportType.equals("6")){
           myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/AnnualBPTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Business Plan</a></font>";
                           
        }else if(myReportType.equals("60")){
           myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/AnnualFSTemplate.zip'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Financial Statement</a></font>";
                           
        }else if(myReportType.equals("120")){
           myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/SWeeklyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិប្រចាំសបា្តហ៏</a></font>";
                           
        }else if(myReportType.equals("140")){
           myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/SMonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិ ប្រចាំខែ</a></font>";
                           
        }else if(myReportType.equals("141")){
           myDownloadLink="<font size='2'><a href='/BSX/FileTemplate/2007/SCOAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ស្ថិតិ ប្រចាំខែ</a></font>";
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
