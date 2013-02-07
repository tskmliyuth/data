package myControllers;

import REPORT_DAILYXML.DailyXML;
import REPORT_DAILYXML.NXMLloader;

import REPORT_WEEKLYLRXML.BIWEEKLYLR;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.clsConverter;
import myClasses.clsMain;
import myModels.clsDataBase;
import myModels.model_DATA_SESSIONS;
import myModels.model_RPT_REPORT;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.xml.sax.SAXException;

@WebServlet(name = "controller_upload_data_authorize", urlPatterns = {"/upload_data_authorize"})
public class controller_upload_data_for_authorize extends HttpServlet {

    String myReportType = "0";
    String myReportName = "";
    ArrayList InserScript = new ArrayList();

    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request, response, "myMainTemplateAdmin.html");
        clsDataBase myCls = new clsDataBase();

        if (myClsMain.CheckPermission("11") == false) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        myReportType = myClsMain.myRequest.getParameter("ReportType");
        String myType = myClsMain.myRequest.getParameter("Type");
        if (myReportType == null) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName = myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();


        if (myType.equals("New")) {
            LoadPage(myClsMain, myCls);
        } else if (myType.equals("Upload")) {
            LoadPage(myClsMain, myCls);
        } else if (myType.equals("Download")) {
            Download(myClsMain, myCls);
        }
    }

    private void PrintUploadPage(clsMain myClsMain) {

        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplateAdmin.html");
        String myMainContain = myClsMain.getContentFromFile("myupload_data_contain_for_authorize.html");
        String myContain = "<font size='+1' color='#0000FF'> " + myReportName + " Late Session not available </font>";


        String myDownloadLink = getTemplatDownloadContain();
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        myMainContain = myMainContain.replace("#SESSION_NAME#", "");
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void PrintUploadPage_With_Form(clsMain myClsMain, String mySession_Name, String myOUP, String myCUP, String myStatus) {
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplateAdmin.html");
        String myMainContain = myClsMain.getContentFromFile("myupload_data_contain_for_authorize.html");
        String myContain = myClsMain.getContentFromFile("uploadinfo_with_form_for_authorize.html");
        myContain = myContain.replace("#REPORT_NAME#", myReportName);
        myContain = myContain.replace("#SESSION_NAME#", mySession_Name);
        myContain = myContain.replace("#OUP#", myOUP);
        myContain = myContain.replace("#CUP#", myCUP);
        myContain = myContain.replace("#STATUS#", myStatus);
        myContain = myContain.replace("#SESSION_TYPE#", myReportType);

        String myDownloadLink = getTemplatDownloadContain();
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        myMainContain = myMainContain.replace("#SESSION_NAME#", mySession_Name);
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void Download(clsMain myClsMain, clsDataBase myCls) {
        String myScript = "SELECT FIUP_NAME FROM DATA_FILE_UPLOAD JOIN DATA_SESSION ON SESS_NO=FIUP_SESS_NO AND FIUP_STATUS IN (1,2) WHERE FIUP_DURA_NO=" + myReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO();
        String myFileName = myCls.ExecuteScalar(myScript).toString();
        String myPart = myCls.getParameter_Value(myReportType);
        clsFileDownload myFileDownload = new clsFileDownload();
        myFileDownload.StartDowload(myClsMain.myRequest, myClsMain.myResponse, myPart + "\\" + myFileName, myFileName);

    }

    private void LoadPage(clsMain myClsMain, clsDataBase myCls) throws IOException, ClassNotFoundException {
        clsConverter c = new clsConverter();

        String mySession_Name = "";
        String myOUP = "";
        String myCUP = "";
        String myStatus = "";


        String myScript = "SELECT FIUP_NO,SERE_NO,SERE_NAME,SERE_DURA_NO,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP,SERE_SYSUID,NVL(to_char(SERE_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_SYSDATE FROM DATA_SESSIONS JOIN DATA_FILE_UPLOAD ON FIUP_SESS_NO=SERE_NO";
        myScript += " WHERE ROWNUM<=1 AND FIUP_STATUS=11 AND FIUP_DURA_NO=" + myReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO();
        model_DATA_SESSIONS myDATA_SESSIONS = new model_DATA_SESSIONS();

        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            if (myResultSet.next()) {
                myDATA_SESSIONS.setData(myResultSet);
                Long myFIUP_NO = myResultSet.getLong("FIUP_NO");

                if (myCls.isERROR() == true) {
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();
                //If session still open

                myOUP = myDATA_SESSIONS.getSERE_OUP();
                myCUP = myDATA_SESSIONS.getSERE_CUP();
                mySession_Name = myDATA_SESSIONS.getSERE_NAME();

                //accept for upload       
                String myFileName = "";

                boolean isMultipart = ServletFileUpload.isMultipartContent(myClsMain.myRequest);
                if (isMultipart) {

                    String myFileNumber = c.getDateTime();
                    String mySessionNO = myDATA_SESSIONS.getSERE_NO();
                    String myBankNo = myClsMain.myDATA_USERS.getUSER_MEMB_NO().toString();
                    myFileName = mySessionNO + "_" + myBankNo + "_" + myFileNumber;
                    String myPart = myCls.getParameter_Value(myReportType);
                    String myTmp = myCls.getParameter_Value("TEMP_FOLDER");

                    clsFileUpload myFileUpload = new clsFileUpload(myClsMain, myCls, myTmp, myPart);
                    myFileUpload.FileName = myFileName;
                    myFileUpload.UploadFile();
                    NXMLloader val= new NXMLloader();

                    if (myFileUpload.isERROR == false) {
                        String RF = null;
                        String RT = null;
                        String RPORT = null;
                        String myFileNameEXT = myFileName + "." + myFileUpload.cExtenstion;
                        String path = "";
                        path = myPart.concat("\\").concat(myFileNameEXT);
                        if (myReportType.equals("1")) {
                          String xsd="D:\\back up\\New With XML\\BSX\\web\\xsd\\DailyReport.xsd";       
                             DailyXML daily= new DailyXML(path);
                            if (val.validate(xsd,path)) {
                                
                                String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_NAME='" + myFileNameEXT + "' ,FIUP_STATUS=2 , FIUP_ADS='" + myFileUpload.DS + "' , FIUP_SIZE=" + myFileUpload.cFileSide + " , FIUP_CONTANT='" + myFileUpload.cFileContent + "' , FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + " , FIUP_ASYSDATE=sysdate";
                                myScriptUpdate += " WHERE FIUP_NO=" + myFIUP_NO;
                                if (!myScriptUpdate.equals("")) {
                                    myCls.ExecuteNonQuery(myScriptUpdate);
                                    String exe = "USD/KHR";
                                    String exchange = null;
                                    String queryExe = "SELECT CURR_BID FROM DATA_CURRENCY WHERE CURR_NO='USD/KHR'";
                                    myResultSet = myCls.ExecuteReader(queryExe);
                                    while (myResultSet.next()) {
                                        exchange = myResultSet.getNString(1);
                                    };
                                    String querySession = "SELECT SERE_SDATETIME,SERE_EDATETIME FROM DATA_SESSIONS WHERE SERE_DURA_NO=" + myReportType + " AND SERE_NAME='" + mySession_Name + "'";
                                    myResultSet = myCls.ExecuteReader(querySession);
                                    while (myResultSet.next()) {
                                        RF = myResultSet.getString(1);
                                        RT = myResultSet.getString(2);

                                    };
                                    model_RPT_REPORT myDATA_REPORT = new model_RPT_REPORT();
                                    myDATA_REPORT.setRPOT_MEMBERID(myClsMain.myDATA_USERS.getUSER_MEMB_NO());
                                    myDATA_REPORT.setRPOT_DATEFROM(RF);
                                    myDATA_REPORT.setRPOT_DATETO(RT);
                                    myDATA_REPORT.setRPOT_DESC("ok");
                                    myDATA_REPORT.setRPOT_EXC_RATE(exchange);
                                    myDATA_REPORT.setRPOT_UPLOAD_USERID(myClsMain.myDATA_USERS.getUSER_NO());
                                    myDATA_REPORT.setRPOT_UPLOAD_DATE(myClsMain.myDATA_USERS.getUSER_SYSDATE());
                                    myDATA_REPORT.setRPOT_DIRECTOR(myClsMain.myDATA_USERS.getUSER_NO());
                                    myDATA_REPORT.setRPOT_TYPEID(myReportType);
                                    myDATA_REPORT.setRPOT_FILEID(Long.toString(myFIUP_NO));

                                    String myScriptInsertReport = myDATA_REPORT.getInsertScript();
                                    if (!myScriptInsertReport.equals("")) {
                                        myCls.ExecuteNonQuery(myScriptInsertReport);
                                        String myQuery1 = "SELECT RPOT_NO FROM RPT_REPORT";
                                        myQuery1 += " WHERE RPOT_FILEID=" + myFIUP_NO;
                                        myResultSet = myCls.ExecuteReader(myQuery1);
                                        myResultSet.next();
                                            RPORT = myResultSet.getNString(1);
                                        

                                       daily.Readxml(RPORT);
                                         myClsMain.Alert("You have Uploaded your report");
                                     
                                    /*    <script language="javascript" type="text/javascript">
                                        alert('This is what an alert message looks like.');
                                        </script>*/
                                    } else {
                                        myClsMain.PrintOut("Error class insert script....");
                                    }
                                    if (myCls.isERROR() == false) {
                                        LoadPage(myClsMain, myCls);
                                    } else {
                                        myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                                    }
                                } else {
                                    myClsMain.PrintOut("Error class insert script....");
                                }
                            } else {

                                    myClsMain.PrintOut("Your Daily Report had incorrect Values or error with xml file<br>please recheck your data again Or Contact NBC System administrator to solve this problem ");
                                }
                            
                        
                        }else  if (myReportType.equals("32")) {
                          String xsd="D:\\back up\\New With XML\\BSX\\xsd\\Bi_WeeklyLR.xsd";       
                              BIWEEKLYLR biWeek= new BIWEEKLYLR(path);  
                            if (val.validate(xsd,path)) {
                                
                                String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_NAME='" + myFileNameEXT + "' ,FIUP_STATUS=2 , FIUP_ADS='" + myFileUpload.DS + "' , FIUP_SIZE=" + myFileUpload.cFileSide + " , FIUP_CONTANT='" + myFileUpload.cFileContent + "' , FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + " , FIUP_ASYSDATE=sysdate";
                                myScriptUpdate += " WHERE FIUP_NO=" + myFIUP_NO;
                                if (!myScriptUpdate.equals("")) {
                                    myCls.ExecuteNonQuery(myScriptUpdate);
                                    String exe = "USD/KHR";
                                    String exchange = null;
                                    String queryExe = "SELECT CURR_BID FROM DATA_CURRENCY WHERE CURR_NO='USD/KHR'";
                                    myResultSet = myCls.ExecuteReader(queryExe);
                                    while (myResultSet.next()) {
                                        exchange = myResultSet.getNString(1);
                                    };
                                    String querySession = "SELECT SERE_SDATETIME,SERE_EDATETIME FROM DATA_SESSIONS WHERE SERE_DURA_NO=" + myReportType + " AND SERE_NAME='" + mySession_Name + "'";
                                    myResultSet = myCls.ExecuteReader(querySession);
                                    while (myResultSet.next()) {
                                        RF = myResultSet.getString(1);
                                        RT = myResultSet.getString(2);

                                    };
                                    model_RPT_REPORT myDATA_REPORT = new model_RPT_REPORT();
                                    myDATA_REPORT.setRPOT_MEMBERID(myClsMain.myDATA_USERS.getUSER_MEMB_NO());
                                    myDATA_REPORT.setRPOT_DATEFROM(RF);
                                    myDATA_REPORT.setRPOT_DATETO(RT);
                                    myDATA_REPORT.setRPOT_DESC("ok");
                                    myDATA_REPORT.setRPOT_EXC_RATE(exchange);
                                    myDATA_REPORT.setRPOT_UPLOAD_USERID(myClsMain.myDATA_USERS.getUSER_NO());
                                    myDATA_REPORT.setRPOT_UPLOAD_DATE(myClsMain.myDATA_USERS.getUSER_SYSDATE());
                                    myDATA_REPORT.setRPOT_DIRECTOR(myClsMain.myDATA_USERS.getUSER_NO());
                                    myDATA_REPORT.setRPOT_TYPEID(myReportType);
                                    myDATA_REPORT.setRPOT_FILEID(Long.toString(myFIUP_NO));

                                    String myScriptInsertReport = myDATA_REPORT.getInsertScript();
                                    if (!myScriptInsertReport.equals("")) {
                                        myCls.ExecuteNonQuery(myScriptInsertReport);
                                        String myQuery1 = "SELECT RPOT_NO FROM RPT_REPORT";
                                        myQuery1 += " WHERE RPOT_FILEID=" + myFIUP_NO;
                                        myResultSet = myCls.ExecuteReader(myQuery1);
                                        myResultSet.next();
                                            RPORT = myResultSet.getNString(1);
                                        

                                       biWeek.Readxml(RPORT);
                                         myClsMain.Alert("You have Uploaded your report");
                                     
                                    /*    <script language="javascript" type="text/javascript">
                                        alert('This is what an alert message looks like.');
                                        </script>*/
                                    } else {
                                        myClsMain.PrintOut("Error class insert script....");
                                    }
                                    if (myCls.isERROR() == false) {
                                        LoadPage(myClsMain, myCls);
                                    } else {
                                        myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                                    }
                                } else {
                                    myClsMain.PrintOut("Error class insert script....");
                                }
                            } else {

                                    myClsMain.PrintOut("Your Bi_weekly liquidity Ratio Report had incorrect Values or error with xml file<br>please recheck your data again Or Contact NBC System administrator to solve this problem ");
                                }
                            
                        } else {
                            String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_NAME='" + myFileNameEXT + "' ,FIUP_STATUS=2 , FIUP_ADS='" + myFileUpload.DS + "' , FIUP_SIZE=" + myFileUpload.cFileSide + " , FIUP_CONTANT='" + myFileUpload.cFileContent + "' , FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + " , FIUP_ASYSDATE=sysdate";
                            myScriptUpdate += " WHERE FIUP_NO=" + myFIUP_NO;

                            if (!myScriptUpdate.equals("")) {
                                myCls.ExecuteNonQuery(myScriptUpdate);
                                  myClsMain.Alert("You have Uploaded your report");
                                if (myCls.isERROR() == false) {
                                    LoadPage(myClsMain, myCls);
                                } else {
                                    myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
                                }
                            } else {
                                myClsMain.PrintOut("Error class insert script....");
                            }
                        }
                    }
                    //..............................................
                    //..............................................
                    myClsMain.PrintOut(myFileUpload.ErrorMessage);
                } else {
                    myStatus = "Open";
                    PrintUploadPage_With_Form(myClsMain, mySession_Name, myOUP, myCUP, myStatus);
                }

            } else {
                PrintUploadPage(myClsMain);
            }
        } catch (SAXException ex) {
            Logger.getLogger(controller_upload_data_for_authorize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        } finally {
            myCls.CloseResultSet();
        }
    }

    private String getTemplatDownloadContain() {

        String myDownloadLink = "";

        if (myReportType.equals("1")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";

        } else if (myReportType.equals("2")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/WeeklyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏</a></font>";

        } else if (myReportType.equals("3")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyBasedPeriod Version 2.xlsm'>Download&nbsp;&nbsp; New Version របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Base Period</br>(ធនាគារពាណិជ្ជ)</a>"
                    + "</BR><a href='/BSX/FileTemplate/2007/Reports_BiWeekly_Reserve_Requirement_BasedPeriod_Specialized_Bank.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏ Base Period</br>(ធនាគារឯកទេស)</a></font>";

        } else if (myReportType.equals("31")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyMaintenance version 2.xlsm'>Download&nbsp;&nbsp;New Version របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Maintenence</a></font>";

        } else if (myReportType.equals("32")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyLR.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Liquidity Ratio</a></font>";

        } else if (myReportType.equals("4")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/MonthlyTemplate version 2.xlsm'>Download&nbsp;&nbsp;New Version 2 របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំខែ</a></font>";

        } else if (myReportType.equals("41")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/COAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ប្រចាំខែ</a></font>";

        } else if (myReportType.equals("5")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/QuarterlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំត្រីមាស</a></font>";

        } else if (myReportType.equals("6")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualBPTemplate.zip'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Business Plan</a></font>";

        } else if (myReportType.equals("60")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualFSTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Financial Statement</a></font>";

        } else if (myReportType.equals("120")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SWeeklyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិប្រចាំសបា្តហ៏</a></font>";

        } else if (myReportType.equals("140")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SMonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិ ប្រចាំខែ</a></font>";

        } else if (myReportType.equals("141")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SCOAMonthlyTemplate.xlsx'>Downlaod&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ស្ថិតិ ប្រចាំខែ</a></font>";
        }

        return myDownloadLink;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data_for_authorize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller_upload_data_for_authorize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
