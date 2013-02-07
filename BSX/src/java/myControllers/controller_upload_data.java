package myControllers;

import REPORT_DAILYXML.DailyXML;
import REPORT_DAILYXML.NXMLloader;
import REPORT_MONTHLYXML.monthlyReport;
import REPORT_WEEKLYLRXML.BIWEEKLYLR;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import myClasses.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myModels.clsDataBase;
import myModels.model_DATA_FILE_UPLOAD;
import myModels.model_DATA_SESSION;
import myModels.model_RPT_REPORT;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.xml.sax.SAXException;

@WebServlet(name = "controller_upload_data", urlPatterns = {"/upload_data"})
public class controller_upload_data extends HttpServlet {
    //String myReportType ="0";

    String myReportName = "";
    String Filename;
    String ReportID;
    ArrayList InserScript = new ArrayList();
    String done="no";

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
        myReportName = myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();


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

    private void PrintUploadPage(String pReportType, clsMain myClsMain, String mySession_Name, String myOUP, String myCUP, String myStatus) {
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplate.html");
        String myMainContain = myClsMain.getContentFromFile("myupload_data_contain.html");
        String myContain = myClsMain.getContentFromFile("uploadinfor.html");
        myContain = myContain.replace("#REPORT_NAME#", myReportName);
        myContain = myContain.replace("#SESSION_NAME#", mySession_Name);
        myContain = myContain.replace("#OUP#", myOUP);
        myContain = myContain.replace("#CUP#", myCUP);
        myContain = myContain.replace("#STATUS#", myStatus);
         if(done.equals("ok")){
             myContain = myContain.replace("#MESSAGE#", "alert(\"Exchage rate have been save\")");
        }else{
        myContain = myContain.replace("#MESSAGE#", "");
        }
        String myDownloadLink = getTemplatDownloadContain(pReportType);
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        myMainContain = myMainContain.replace("#SESSION_NAME#", mySession_Name);
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
       
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void PrintUploadPage_With_Form(String pReportType, clsMain myClsMain, String mySession_Name, String myOUP, String myCUP, String myStatus) {
        String myContain;
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplate.html");
        String myMainContain = myClsMain.getContentFromFile("myupload_data_contain.html");
//        if(pReportType.equals("32")){
//              myContain = myClsMain.getContentFromFile("uploadinfo_with_form_LR.html");
//        }else{
              myContain = myClsMain.getContentFromFile("uploadinfo_with_form.html");
       // }
        myContain = myContain.replace("#REPORT_NAME#", myReportName);
        myContain = myContain.replace("#SESSION_NAME#", mySession_Name);
        myContain = myContain.replace("#OUP#", myOUP);
        myContain = myContain.replace("#CUP#", myCUP);
        myContain = myContain.replace("#STATUS#", myStatus);
        myContain = myContain.replace("#SESSION_TYPE#", pReportType);

        String myDownloadLink = getTemplatDownloadContain(pReportType);
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        myMainContain = myMainContain.replace("#SESSION_NAME#", mySession_Name);
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

        String mySession_Name = "";
        String myOUP = "";
        String myCUP = "";
        String myStatus = "";
        String RPORT = null;


        String myScript = "SELECT SESS_DURA_NO,SESS_NO,SESS_NAME,NVL(to_char(SESS_RF, 'yyyy/mm/dd'),'') SESS_RF,NVL(to_char(SESS_RT, 'yyyy/mm/dd'),'') SESS_RT,NVL(to_char(SESS_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_OUP,NVL(to_char(SESS_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_CUP,SESS_SYSUID,NVL(to_char(SESS_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_SYSDATE,to_char(SYSDATE, 'yyyy/mm/dd hh24:mi:ss') SYSTEMDATETIME FROM DATA_SESSION";
        myScript += " WHERE SESS_DURA_NO=" + pReportType;
        model_DATA_SESSION myUploadSession = new model_DATA_SESSION();

        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            if (myResultSet.next()) {

                myUploadSession.setData(myResultSet);
                String myCurrentDate = myResultSet.getString("SYSTEMDATETIME");

                if (myCls.isERROR() == true) {
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();
                //If session still open
                     NXMLloader val= new NXMLloader();
                myOUP = myUploadSession.getSESS_OUP();
                myCUP = myUploadSession.getSESS_CUP();
                mySession_Name = myUploadSession.getSESS_NAME();
                if (c.CompareDateTimeInBetween(myCurrentDate, myUploadSession.getSESS_OUP(), myUploadSession.getSESS_CUP()) == 0) {
                    model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD = new model_DATA_FILE_UPLOAD();
                    myScript = myDATA_FILE_UPLOAD.getSelectScript();
                    myScript += " WHERE FIUP_SESS_NO='" + myUploadSession.getSESS_NO() + "' AND FIUP_DURA_NO=" + pReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                    myResultSet = myCls.ExecuteReader(myScript);
                    if (myResultSet.next()) {
                        myDATA_FILE_UPLOAD.setData(myResultSet);
                        myResultSet.close();
                        myCls.CloseResultSet();
                        if (myDATA_FILE_UPLOAD.getFIUP_STATUS() == 1) {

                            myStatus = "Your " + myReportName + " report for " + mySession_Name + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and waiting for authorization. To download your uploaded file <a href='upload_data?ReportType=" + pReportType + "&Type=Download'>Click Here</a>";
                            PrintUploadPage(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                            return;

                        } else {
                            myStatus = "Your " + myReportName + " report for " + mySession_Name + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and approved on " + myDATA_FILE_UPLOAD.getFIUP_ASYSDATE() + "To download your uploaded file <a href='upload_data?ReportType=" + pReportType + "&Type=Download'>Click Here</a>";
                            PrintUploadPage(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                            return;
                        }
                    } else {
                        //accept for upload       
                        String myFileName = "";

                        boolean isMultipart = ServletFileUpload.isMultipartContent(myClsMain.myRequest);
                        if (isMultipart) {

                            String myFileNumber = c.getDateTime();
                            String mySessionNO = myUploadSession.getSESS_NO();
                            String myBankNo = myClsMain.myDATA_USERS.getUSER_MEMB_NO().toString();
                            myFileName = mySessionNO + "_" + myBankNo + "_" + myFileNumber;
                            String myPart = myCls.getParameter_Value(pReportType);
                            String myTmp = myCls.getParameter_Value("TEMP_FOLDER");

                            clsFileUpload myFileUpload = new clsFileUpload(myClsMain, myCls, myTmp, myPart);
                            myFileUpload.FileName = myFileName;
                            myFileUpload.UploadFile();
                            String path = "";
                            path = myPart.concat("\\").concat(myFileName + "." + myFileUpload.cExtenstion);
                            ArrayList myScriptInsert = new ArrayList();

                            if (myFileUpload.isERROR == false) {

                                myDATA_FILE_UPLOAD.setFIUP_NAME(myFileName + "." + myFileUpload.cExtenstion);
                                myDATA_FILE_UPLOAD.setFIUP_SESS_NO(mySessionNO);
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
                                //

                                myScriptInsert.add(myDATA_FILE_UPLOAD.getInsertScript());

                                // Create REPORT DATABASE
                                String exe = "USD/KHR";
                                String exchange = null;
                                String queryExe = "SELECT CURR_BID FROM DATA_CURRENCY WHERE CURR_NO='USD/KHR'";
                                myResultSet = myCls.ExecuteReader(queryExe);
                                while (myResultSet.next()) {
                                    exchange = myResultSet.getNString(1);
                                };
                                model_RPT_REPORT myDATA_REPORT = new model_RPT_REPORT();
                                myDATA_REPORT.setRPOT_MEMBERID(myClsMain.myDATA_USERS.getUSER_MEMB_NO());
                                myDATA_REPORT.setRPOT_DATEFROM(myUploadSession.getSESS_RF());
                                myDATA_REPORT.setRPOT_DATETO(myUploadSession.getSESS_RT());
                                myDATA_REPORT.setRPOT_DESC("ok");
                                myDATA_REPORT.setRPOT_EXC_RATE(exchange);
                                myDATA_REPORT.setRPOT_UPLOAD_USERID(myClsMain.myDATA_USERS.getUSER_NO());
                                myDATA_REPORT.setRPOT_UPLOAD_DATE(myClsMain.myDATA_USERS.getUSER_SYSDATE());
                                myDATA_REPORT.setRPOT_DIRECTOR("");
                                myDATA_REPORT.setRPOT_TYPEID(pReportType);
                                //get File ID 
                                
                             
                                if (!myScriptInsert.equals("")) {
                                    if (pReportType.equals("1")) {
                                   String xsd="D:\\back up\\New With XML\\BSX\\web\\xsd\\DailyReport.xsd";                                        
                                        if(val.validate(xsd, path)){
                                       // if (DAILYREPORT.Error().equals("")) {
                                           DailyXML daily= new DailyXML(path);
                                            myCls.ExecuteNonQuery(myScriptInsert.get(0).toString());
                                            String myQuery = "SELECT FIUP_NO FROM DATA_FILE_UPLOAD";
                                            myQuery += " WHERE FIUP_SESS_NO='" + myUploadSession.getSESS_NO() + "' AND FIUP_DURA_NO=" + myUploadSession.getSESS_DURA_NO() + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                                            myResultSet = myCls.ExecuteReader(myQuery);
                                            myResultSet.next();
                                            ReportID = myResultSet.getNString(1);
                                            myDATA_REPORT.setRPOT_FILEID(ReportID);
                                            myScriptInsert.add(myDATA_REPORT.getInsertScript());
                                            myCls.ExecuteNonQuery(myScriptInsert.get(1).toString());
                                            String myQuery1 = "SELECT RPOT_NO FROM RPT_REPORT";
                                            myQuery1 += " WHERE RPOT_FILEID=" + ReportID;
                                            myResultSet = myCls.ExecuteReader(myQuery1);
                                            myResultSet.next();
                                            RPORT = myResultSet.getNString(1);
                                            daily.Readxml(RPORT);
                                            
                                        } else {
                                     
                                                myClsMain.PrintOut("<Center> Your Daily report has problem with XML file,<br> please recheck your data again with your report<center>");
                                  
                                        }
                                  
                                    }else if (pReportType.equals("4")) {
                                        String xsd="D:\\back up\\New With XML\\BSX\\xsd\\ReportMonthly.xsd";
                                         if(val.validate(xsd, path)){
                                             monthlyReport month= new monthlyReport(path);       
                                             myCls.ExecuteNonQuery(myScriptInsert.get(0).toString());
                                                    String myQuery = "SELECT FIUP_NO FROM DATA_FILE_UPLOAD";
                                                    myQuery += " WHERE FIUP_SESS_NO='" + myUploadSession.getSESS_NO() + "' AND FIUP_DURA_NO=" + myUploadSession.getSESS_DURA_NO() + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                                                    myResultSet = myCls.ExecuteReader(myQuery);
                                                    myResultSet.next();
                                                    ReportID = myResultSet.getNString(1);
                                                    myDATA_REPORT.setRPOT_FILEID(ReportID);
                                                    myScriptInsert.add(myDATA_REPORT.getInsertScript());
                                                    myCls.ExecuteNonQuery(myScriptInsert.get(1).toString());
                                                    String myQuery1 = "SELECT RPOT_NO FROM RPT_REPORT";
                                                    myQuery1 += " WHERE RPOT_FILEID=" + ReportID;
                                                    myResultSet = myCls.ExecuteReader(myQuery1);
                                                    myResultSet.next();
                                                    RPORT = myResultSet.getNString(1);
                                                    month.readxml(RPORT);
                                                   
                                                   
                                                   
                                                   
                                            } else {

                                                myClsMain.PrintOut("Your Monthly Report has incorrect Values or errors in your file, <br>please recheck your data again Or Contact NBC System administrator to solve this problem ");
                                            }          
                                    }   else if (pReportType.equals("32")) {
                                        String xsd="D:\\back up\\New With XML\\BSX\\xsd\\Bi_WeeklyLR.xsd";
                                         if(val.validate(xsd, path)){
                                             BIWEEKLYLR biWeek= new BIWEEKLYLR(path);       
                                             myCls.ExecuteNonQuery(myScriptInsert.get(0).toString());
                                                    String myQuery = "SELECT FIUP_NO FROM DATA_FILE_UPLOAD";
                                                    myQuery += " WHERE FIUP_SESS_NO='" + myUploadSession.getSESS_NO() + "' AND FIUP_DURA_NO=" + myUploadSession.getSESS_DURA_NO() + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS IN (1,2)";
                                                    myResultSet = myCls.ExecuteReader(myQuery);
                                                    myResultSet.next();
                                                    ReportID = myResultSet.getNString(1);
                                                    myDATA_REPORT.setRPOT_FILEID(ReportID);
                                                    myScriptInsert.add(myDATA_REPORT.getInsertScript());
                                                    myCls.ExecuteNonQuery(myScriptInsert.get(1).toString());
                                                    String myQuery1 = "SELECT RPOT_NO FROM RPT_REPORT";
                                                    myQuery1 += " WHERE RPOT_FILEID=" + ReportID;
                                                    myResultSet = myCls.ExecuteReader(myQuery1);
                                                    myResultSet.next();
                                                    RPORT = myResultSet.getNString(1);
                                                    biWeek.Readxml(RPORT);
                                                    myClsMain.Alert(biWeek.getErrorMessage());
                                                   
                                            } else {

                                                myClsMain.PrintOut("Your Bi-weekly Liquidity Raito-DDP had incorrect Values or errors in your file, <br>please recheck your data again Or Contact NBC System administrator to solve this problem ");
                                            }          
                                    } else {
                                        myCls.ExecuteNonQuery(myScriptInsert.get(0).toString());
                                    }
                                } else {
                                    myClsMain.PrintOut("Error class insert script....");
                                }
                                if (myCls.isERROR() == false) {
                                    LoadPage(pReportType, myClsMain, myCls);
                                } else {
                                    myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                                }
                                if (myDATA_FILE_UPLOAD.getFIUP_STATUS() == 1) {

                                    myStatus = "Your " + myReportName + " report for " + mySession_Name + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and waiting for authorization. To download your uploaded file <a href='upload_data?ReportType=" + pReportType + "&Type=Download'>Click Here</a>";
                                    PrintUploadPage(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                                    return;

                                } else {
                                    myStatus = "Your " + myReportName + " report for " + mySession_Name + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and approved on " + myDATA_FILE_UPLOAD.getFIUP_ASYSDATE() + "To download your uploaded file <a href='upload_data?ReportType=" + pReportType + "&Type=Download'>Click Here</a>";
                                    PrintUploadPage(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                                    return;
                                }
                            }
                            myClsMain.PrintOut(myFileUpload.ErrorMessage);
                        } else {
                            myStatus = "Open";
                            PrintUploadPage_With_Form(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                        }
                    }

                } else {
                    //Session has been closed
                    myStatus = myReportName + " report for " + myUploadSession.getSESS_NAME() + " is closed.";
                    PrintUploadPage(pReportType, myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                    return;
                }
            } else {
                //Error no available
                myClsMain.PrintOut("Session Error");
            }
        } catch (SQLException ex) {
            myClsMain.PrintOut("Please Download the Latest Templates");
        } finally {
            myCls.CloseResultSet();
        }
    }

    private String getDS(String pFilePath) {
        return pFilePath;
    }

    private String getTemplatDownloadContain(String pReportType) {

        String myDownloadLink = "";

        if (pReportType.equals("1")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";

        } else if (pReportType.equals("2")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/WeeklyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏</a></br>";
        } else if (pReportType.equals("3")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyBasedPeriod Version 2.xlsm'>Download&nbsp;&nbsp;New Version របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Base Period</br>(ធនាគារពាណិជ្ជ)</a>"
                    + "</BR><a href='/BSX/FileTemplate/2007/Reports_BiWeekly_Reserve_Requirement_BasedPeriod_Specialized_Bank.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏ Base Period</br>(ធនាគារឯកទេស)</a></font>";
        } else if (pReportType.equals("31")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyMaintenance version 2.xlsm'>Download&nbsp;&nbsp;New Version របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Maintenence</a></font>";

        } else if (pReportType.equals("32")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyLR.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Liquidity Ratio</a></font>";

        } else if (pReportType.equals("4")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/MonthlyTemplate version 2.xlsm'>Download&nbsp;&nbsp;New Version 2 របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំខែ</a></font>";

        } else if (pReportType.equals("41")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/COAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ប្រចាំខែ</a></font>";

        } else if (pReportType.equals("5")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/QuarterlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំត្រីមាស</a></font>";

        } else if (pReportType.equals("6")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualBPTemplate.zip'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Business Plan</a></font>";

        } else if (pReportType.equals("60")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualFSTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Financial Statement</a></font>";

        } else if (pReportType.equals("120")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SWeeklyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិប្រចាំសបា្តហ៏</a></font>";

        } else if (pReportType.equals("140")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SMonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិ ប្រចាំខែ</a></font>";

        } else if (pReportType.equals("141")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SCOAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ស្ថិតិ ប្រចាំខែ</a></font>";
        }
        return myDownloadLink;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SAXException ex) {
                Logger.getLogger(controller_upload_data.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SAXException ex) {
                Logger.getLogger(controller_upload_data.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
