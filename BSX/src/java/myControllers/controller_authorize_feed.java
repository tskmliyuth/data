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
import myModels.model_DATA_ENQUIRY;
import myModels.model_DATA_FILE_UPLOAD;
import myModels.model_DATA_SESSION;

/**
 *
 * @author NHEP-BORA
 */
@WebServlet(name = "controller_authorize_feed", urlPatterns = {"/authorize_feed"})
public class controller_authorize_feed extends HttpServlet {

    String myReportType = "0";
    String myReportName = "";
    String myFileName = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request, response, "");
        clsDataBase myCls = new clsDataBase();

        if (myClsMain.CheckPermission("11") == false) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        myReportType = myClsMain.myRequest.getParameter("ReportType");
        String myType = myClsMain.myRequest.getParameter("Type");
        if (myReportType == null || myType == null) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName = (String) myCls.ExecuteScalar("SELECT COMT_NAME FROM DATA_ACK_COMPLAINTTYPE WHERE COMT_NO=" + myReportType);

        if (myType.equals("Download")) {
            Download(myClsMain, myCls, myFileName);
        } else {
            LoadPage(myClsMain, myCls, myType);
        }
    }

    private void PrintUploadPage(clsMain myClsMain, String myStatus) {
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplateAdmin.html");
        String myMainContain = myClsMain.getContentFromFile("myauthorize_data_contain_feed.html");
        String myContain = myClsMain.getContentFromFile("uploadinfor_feed.html");
        myContain = myContain.replace("#REPORT_NAME#", myReportName);

        myContain = myContain.replace("#STATUS#", myStatus);
        String myDownloadLink = getTemplatDownloadContain();
        myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
        myMainContain = myMainContain.replace("##Download##", myDownloadLink);
        myMainContain = myMainContain.replace("##upload_form##", myContain);
        myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
        myClsMain.PrintOut(myMainPageTemplate);
    }

    private void PrintUploadPage_With_Authorize(clsMain myClsMain, String myStatus, String pDS) {
        String myMainPageTemplate = myClsMain.getContentFromFile("myMainTemplateAdmin.html");

        String myMainContain = myClsMain.getContentFromFile("myauthorize_data_contain_feed.html");
        if (myReportType.equals("13")) {
             clsConverter c = new clsConverter();
            String myContain = myClsMain.getContentFromFile("uploadinfo_with_authorize_feed_enquiry.html");
            String myScript;
            ResultSet myResultSet;
            clsDataBase myCls = new clsDataBase();
          
            myScript = "select MEMB_NAME, ENQY_ENQUIRYDATE,FIST_NAME,ENQY_CONTACTNAME,ENQY_CONTACTPOSITION,ENQY_EMAIL,ENQY_PHONE,ENTY_NAME,COBY_NAME,ENQY_ENQURYDETAIL FROM DATA_CONTACTBYTYPE T1, DATA_ENQUIRY T2,DATA_ENQUIRYTYPE T3,DATA_FILE_STATUS T4,DATA_MEMBERS T5 WHERE T5.MEMB_NO= T2.ENQY_MEMBERID AND T3.ENTY_NO=T2.ENQY_ENQUIRYTYPEID AND T4.FIST_NO=T2.ENQY_STATUS AND T1.COBY_NO= T2.ENQY_CONTACTBYID AND T2.ENQY_STATUS=1 AND T2.ENQY_MEMBERID="+ myClsMain.myDATA_USERS.getUSER_MEMB_NO();
             myResultSet = myCls.ExecuteReader(myScript);
            try {
            while (myResultSet.next()) {
                myContain = myContain.replace("#Bank_Name#", c.toHTML(myResultSet.getObject("MEMB_NAME")));
                myContain = myContain.replace("#Upload_date#", c.toHTML(myResultSet.getObject("ENQY_ENQUIRYDATE")));
                myContain = myContain.replace("#status#", c.toHTML(myResultSet.getObject("FIST_NAME")));
                myContain = myContain.replace("#Upload_name#", c.toHTML(myResultSet.getObject("ENQY_CONTACTNAME")));
                myContain = myContain.replace("#Position#", c.toHTML(myResultSet.getObject("ENQY_CONTACTPOSITION")));
                myContain = myContain.replace("#email_address#", c.toHTML(myResultSet.getObject("ENQY_EMAIL")));
                myContain = myContain.replace("#phone#", c.toHTML(myResultSet.getObject("ENQY_PHONE")));
                myContain = myContain.replace("#Enquiry_Subject#", c.toHTML(myResultSet.getObject("ENTY_NAME")));
                myContain = myContain.replace("#type#", c.toHTML(myResultSet.getObject("COBY_NAME")));
                myContain = myContain.replace("#details#", c.toHTML(myResultSet.getObject("ENQY_ENQURYDETAIL")));
            }
            myResultSet.close();
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }
         
 
            myContain = myContain.replace("#REPORT_NAME#", myReportName);
            myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);
             myContain = myContain.replace("#SESSION_TYPE#", myReportType);
            myContain = myContain.replace("#DS#", pDS);
            myMainContain = myMainContain.replace("##upload_form##", myContain);
            myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
            myClsMain.PrintOut(myMainPageTemplate);
        } else {
            String myContain = myClsMain.getContentFromFile("uploadinfo_with_authorize_feed.html");
            myContain = myContain.replace("#REPORT_NAME#", myReportName);


            myContain = myContain.replace("#STATUS#", myStatus);
            myContain = myContain.replace("#SESSION_TYPE#", myReportType);
            myContain = myContain.replace("#DS#", pDS);

            String myDownloadLink = getTemplatDownloadContain();
            myMainContain = myMainContain.replace("#REPORT_NAME#", myReportName);

            myMainContain = myMainContain.replace("##Download##", myDownloadLink);
            myMainContain = myMainContain.replace("##upload_form##", myContain);
            myMainPageTemplate = myMainPageTemplate.replace("###Contain####", myMainContain);
            myClsMain.PrintOut(myMainPageTemplate);
        }
    }

    private void Download(clsMain myClsMain, clsDataBase myCls, String FileName) {
        String myScript = "SELECT COMP_ORG_FILENAME FROM DATA_FILE_UPLOAD T,DATA_ACK_COMPLAINT R WHERE R.COMP_FILEID= T.FIUP_NO AND FIUP_DURA_NO=" + myReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + "AND FIUP_NAME='" + FileName + "'";
        String myFileName = myCls.ExecuteScalar(myScript).toString();
        String myPart = myCls.getParameter_Value(myReportType);
        clsFileDownload myFileDownload = new clsFileDownload();
        myFileDownload.StartDowload(myClsMain.myRequest, myClsMain.myResponse, myPart + "\\" + myFileName, myFileName);

    }

   

    private void LoadPage(clsMain myClsMain, clsDataBase myCls, String pType) {
        clsConverter c = new clsConverter();


        String myOUP = "";
        String myCUP = "";
        String myStatus = "";
        String myQuery = "";
        String Org_file = null;
        String myScript;
        ResultSet myResultSet;
        try {


            if (myReportType.equals("13")) {

                model_DATA_ENQUIRY enquiry = new model_DATA_ENQUIRY();
                myScript = enquiry.getSelectScript();
                myScript += " WHERE ENQY_STATUS=1";
                myResultSet = myCls.ExecuteReader(myScript);
                if (myResultSet.next()) {
                    enquiry.setData(myResultSet);

                    myResultSet.close();
                    myCls.CloseResultSet();

                    String status = enquiry.getENQY_STATUS();
                    if (status.equals("1")) {
                        if (pType.equals("Authorize")) {

                            String myStr = "UPDATE DATA_ENQUIRY SET ENQY_STATUS=2,ENQY_AUTHORIZERID=" + myClsMain.myDATA_USERS.getUSER_NO() + " WHERE ENQY_NO=" + enquiry.getENQY_NO();
                            // String myScriptReport="UPDATE DATA_ACK_COMPLAINT SET COMP_DIRECTOR= "+myClsMain.myDATA_USERS.getUSER_NO()+"WHERE COMP_FILEID="+ myDATA_FILE_UPLOAD.getFIUP_NO();
                            myCls.ExecuteNonQuery(myStr);
                            //    myCls.ExecuteNonQuery(myScriptReport);

                            if (myCls.isERROR() == true) {
                                myClsMain.PrintOut(myCls.getERROR_Message());
                            } else {
                                try {
                                    myClsMain.myResponse.sendRedirect("authorize_feed?ReportType=" + myReportType + "&Type=New");
                                } catch (IOException ex) {
                                    myClsMain.PrintOut(ex.getMessage());
                                }
                            }
                        } else if (pType.equals("Reject")) {

                            String myStr = "UPDATE DATA_ENQUIRY SET ENQY_STATUS=3,ENQY_AUTHORIZERID=" + myClsMain.myDATA_USERS.getUSER_NO() + " WHERE ENQY_NO=" + enquiry.getENQY_NO();
                            myCls.ExecuteNonQuery(myStr);
                            if (myCls.isERROR() == true) {
                                myClsMain.PrintOut(myCls.getERROR_Message());
                            } else {
                                try {
                                    myClsMain.myResponse.sendRedirect("authorize_feed?ReportType=" + myReportType + "&Type=New");
                                } catch (IOException ex) {
                                    myClsMain.PrintOut(ex.getMessage());
                                }
                            }
                        } else {

                            myStatus = "File  has been uploaded at  and waiting for your authorization.<br/><br/>Note : Before you authorize please check your enquiry before you authorize";
                            PrintUploadPage_With_Authorize(myClsMain, myStatus, "436345345gfhdfhdfhdf");
                        }

                        return;
                    } else {
                        myStatus = "Your " + myReportName + " report for " + Org_file + " has been uploaded at  and authorized on  .<br/>To download your uploaded file <a href='authorize_feed?ReportType=" + myReportType + "&Type=Download'>Click Here</a>";
                        PrintUploadPage(myClsMain, myStatus);
                        return;
                    }
                } else {
                    myStatus = "Enquiry not available for you to authorize";
                    PrintUploadPage(myClsMain, myStatus);
                }
            } else {
                model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD = new model_DATA_FILE_UPLOAD();

                myScript = myDATA_FILE_UPLOAD.getSelectScript();
                myScript += " WHERE FIUP_SESS_NO='Unlimited time upload' AND FIUP_DURA_NO=" + myReportType + " AND FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO() + " AND FIUP_STATUS =1";
                myResultSet = myCls.ExecuteReader(myScript);
                if (myResultSet.next()) {
                    myDATA_FILE_UPLOAD.setData(myResultSet);
                    myFileName = myDATA_FILE_UPLOAD.getFIUP_NAME();
                    myResultSet.close();
                    myCls.CloseResultSet();

                    if (myDATA_FILE_UPLOAD.getFIUP_STATUS() == 1) {
                        if (pType.equals("Authorize")) {
                            String CA = myClsMain.myRequest.getParameter("certificationChain");
                            String DS = myClsMain.myRequest.getParameter("signature");
                            if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA) != 0) {
                                myClsMain.PrintOut("Invalid eToken! please contact NBC System administrator to solve this problem");
                                return;
                            }
                            String myStr = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=2, FIUP_ADS=" + c.toORACLEString(DS) + " , FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + ",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myDATA_FILE_UPLOAD.getFIUP_NO();
                            String myScriptReport = "UPDATE DATA_ACK_COMPLAINT SET COMP_DIRECTOR= " + myClsMain.myDATA_USERS.getUSER_NO() + "WHERE COMP_FILEID=" + myDATA_FILE_UPLOAD.getFIUP_NO();
                            myCls.ExecuteNonQuery(myStr);
                            myCls.ExecuteNonQuery(myScriptReport);

                            if (myCls.isERROR() == true) {
                                myClsMain.PrintOut(myCls.getERROR_Message());
                            } else {
                                try {
                                    myClsMain.myResponse.sendRedirect("authorize_feed?ReportType=" + myReportType + "&Type=New");
                                } catch (IOException ex) {
                                    myClsMain.PrintOut(ex.getMessage());
                                }
                            }
                        } else if (pType.equals("Reject")) {
                            String CA = myClsMain.myRequest.getParameter("certificationChain");
                            String DS = myClsMain.myRequest.getParameter("signature");
                            if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA) != 0) {
                                myClsMain.PrintOut("Invalid eToken! please contact NBC System administrator to solve this problem");
                                return;
                            }

                            String myStr = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=3,FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + ",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myDATA_FILE_UPLOAD.getFIUP_NO();
                            myCls.ExecuteNonQuery(myStr);
                            if (myCls.isERROR() == true) {
                                myClsMain.PrintOut(myCls.getERROR_Message());
                            } else {
                                try {
                                    myClsMain.myResponse.sendRedirect("authorize_feed?ReportType=" + myReportType + "&Type=New");
                                } catch (IOException ex) {
                                    myClsMain.PrintOut(ex.getMessage());
                                }
                            }
                        } else {
                            myQuery = "SELECT COMP_ORG_FILENAME FROM DATA_ACK_COMPLAINT R ,DATA_FILE_UPLOAD  T WHERE R.COMP_FILEID= T.FIUP_NO AND T.FIUP_NAME='" + myDATA_FILE_UPLOAD.getFIUP_NAME() + "' AND  FIUP_MEMB_NO=" + myClsMain.myDATA_USERS.getUSER_MEMB_NO();
                            myResultSet = myCls.ExecuteReader(myQuery);
                            myResultSet.next();
                            Org_file = myResultSet.getNString(1);
                            myStatus = "File " + myReportName + " report for " + Org_file + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and waiting for your authorization.<br/><br/>Note : Before you authorize please download file and check the content to make sure the file is correct. <br/> To download your uploaded file <a href='authorize_feed?ReportType=" + myReportType + "&Type=Download'>Click Here</a>";
                            PrintUploadPage_With_Authorize(myClsMain, myStatus, myDATA_FILE_UPLOAD.getFIUP_DS());
                        }

                        return;
                    } else {
                        myStatus = "Your " + myReportName + " report for " + Org_file + " has been uploaded at " + myDATA_FILE_UPLOAD.getFIUP_SYSDATE() + " and authorized on " + myDATA_FILE_UPLOAD.getFIUP_ASYSDATE() + ".<br/>To download your uploaded file <a href='authorize_feed?ReportType=" + myReportType + "&Type=Download'>Click Here</a>";
                        PrintUploadPage(myClsMain, myStatus);
                        return;
                    }
                } else {
                    myStatus = "File not available for you to authorize";
                    PrintUploadPage(myClsMain, myStatus);
                }
            }




        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        } finally {
            myCls.CloseResultSet();
        }
    }

    private String getDS(String pFilePath) {
        return pFilePath;
    }

    private String getTemplatDownloadContain() {

        String myDownloadLink = "";
        if (myReportType.equals("1")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/DailyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំថ្ងៃ</font></a>";

        } else if (myReportType.equals("2")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/WeeklyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏</a></font>";

        } else if (myReportType.equals("3")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyBasedPeriod.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Base Period</br>(ធនាគារពាណិជ្ជ)</a>"
                    + "</BR><a href='/BSX/FileTemplate/2007/Reports_BiWeekly_Reserve_Requirement_BasedPeriod_Specialized_Bank.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំសបា្តហ៏ Base Period</br>(ធនាគារឯកទេស)</a></font>";

        } else if (myReportType.equals("31")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyMaintenance.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Maintenence</a></font>";

        } else if (myReportType.equals("32")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/Bi-WeeklyLR.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំពីរសបា្តហ៏ Liquidity Ratio</a></font>";

        } else if (myReportType.equals("4")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/MonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំខែ</a></font>";

        } else if (myReportType.equals("41")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/COAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ប្រចាំខែ</a></font>";

        } else if (myReportType.equals("5")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/QuarterlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំត្រីមាស</a></font>";

        } else if (myReportType.equals("6")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualBPTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Business Plan</a></font>";

        } else if (myReportType.equals("60")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/AnnualFSTemplate.zip'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារប្រចាំឆ្នាំ Financial Statement</a></font>";

        } else if (myReportType.equals("120")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SWeeklyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិប្រចាំសបា្តហ៏</a></font>";

        } else if (myReportType.equals("140")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SMonthlyTemplate.xlsm'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារស្ថិតិ ប្រចាំខែ</a></font>";

        } else if (myReportType.equals("141")) {
            myDownloadLink = "<font size='2'><a href='/BSX/FileTemplate/2007/SCOAMonthlyTemplate.xlsx'>Download&nbsp;&nbsp;របាយការណ៏គំរូដែលត្រូវផ្តល់ជូនធនាគារជាតិនៃកម្ពុជាដោយគ្រឹះស្ថានធនាគារ COA ស្ថិតិ ប្រចាំខែ</a></font>";
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
