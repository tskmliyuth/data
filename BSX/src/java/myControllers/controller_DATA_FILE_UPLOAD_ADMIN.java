/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import myClasses.*;
import myModels.*;

/**
 *
 * @author NHEP-BORA
 */
@WebServlet(name = "controller_DATA_FILE_UPLOAD_ADMIN", urlPatterns = {"/InputReportadmin"})
public class controller_DATA_FILE_UPLOAD_ADMIN extends HttpServlet {

    String myReportType = "0";
    String myReportName = "";
    String myStatus = "";
    String mydate_NAME = "";
    String myMainTemplate = "myTemplateNBCAdmin.html";
    //String myFormTemplate="DATA_SESSIONS_FORM.html";
    String myFormSearchTemplate = "DATA_FILE_UPLOAD_FORM_SEARCH_ADMIN.html";
    String myFormSearchTemplate_feed = "DATA_FILE_UPLOAD_FORM_SEARCH_ADMIN_feed.html";
    String myFormEditTemplate = "DATA_FILE_UPLOAD_FORM_EDIT_ADMIN.html";
    String myFormDetail = "DATA_DETAIL_ENQUIRY_ADMIN.html";
    String myFormEditTemplateEnquiry = "DATA_FILE_UPLOAD_FORM_EDIT_ENQUIRY_ADMIN.html";
    String mySubManuTemplate = "DATA_FILE_UPLOAD_MANU.html";
    String mySubManuTemplate_feed = "DATA_FILE_UPLOAD_MANU_feed.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request, response, "");
        clsDataBase myCls = new clsDataBase();

        if (myClsMain.CheckPermission("1", "31") == false) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("31")) {
            myMainTemplate = "myTemplateNBCAdmin_S.html";
            mySubManuTemplate = "DATA_FILE_UPLOAD_MANU_S.html";
        } else {

            myMainTemplate = "myTemplateNBCAdmin.html";
            if (myReportType.equals("11") || myReportType.equals("11")) {
                mySubManuTemplate = "DATA_FILE_UPLOAD_MANU_feed.html";
            } else {
                mySubManuTemplate = "DATA_FILE_UPLOAD_MANU.html";
            }
        }
        myClsMain.myMainTemplate = myMainTemplate;

        myReportType = myClsMain.myRequest.getParameter("ReportType");

        if (myReportType == null) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName = myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();


        String myType = myClsMain.myRequest.getParameter("Type");
        if (myType == null) {
            SearchForm(myClsMain, myCls);
        } else if (myType.equals("Search")) {
            SearchForm(myClsMain, myCls);
        } else if (myType.equals("Reject")) {
            LoadEditPage(myClsMain, myCls);
        } else if (myType.equals("Reject_enquiry")) {
            LoadEditPage_enquiry(myClsMain, myCls);
        } else if (myType.equals("Details")) {
            Load_Details(myClsMain, myCls);
        } else if (myType.equals("Downloads")) {
            Download(myClsMain, myCls);
        } else if (myType.equals("Update")) {
            myBtUpdate_Click(myClsMain, myCls);
        } else if (myType.equals("Update_enquiry")) {
            myBtUpdate_Click_enquiry(myClsMain, myCls);
        } else if (myType.equals("CTO")) {
            CTO(myClsMain, myCls);
        } else if (myType.equals("CTC")) {
            CTC(myClsMain, myCls);
        } else if (myType.equals("GSN")) {
            GSN(myClsMain, myCls);
        }
    }

    private void GSN(clsMain myClsMain, clsDataBase myCls) {

        try {
            String myID = myClsMain.myRequest.getParameter("term");
            String myScriptSelect = "SELECT SERE_NAME FROM DATA_SESSIONS WHERE ROWNUM<=15 AND SERE_DURA_NO=" + myReportType + " AND SERE_NAME LIKE '%" + myID + "%'";

            ResultSet myResultSet = myCls.ExecuteReader(myScriptSelect);
            String s = "";
            while (myResultSet.next()) {
                s += ",{\"value\":" + "\"" + myResultSet.getString("SERE_NAME") + "\"}";
            }
            s += "]";
            s = "[" + s.substring(1);
            myClsMain.PrintOut(s);
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }

    }

    private void CTO(clsMain myClsMain, clsDataBase myCls) {
        String mySESS_NO = myClsMain.myRequest.getParameter("SESS_NO");
        String myMEMB_NO = myClsMain.myRequest.getParameter("MEMB_NO");

        String myScript = "SELECT * FROM DATA_FILE_UPLOAD WHERE FIUP_SESS_NO='" + mySESS_NO + "' AND FIUP_MEMB_NO=" + myMEMB_NO + " AND FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS=11";
        if (myCls.Existed(myScript) == true) {
            SearchForm(myClsMain, myCls);
            return;
        }

        model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD = new model_DATA_FILE_UPLOAD();

        myDATA_FILE_UPLOAD.setFIUP_NAME(" ");
        myDATA_FILE_UPLOAD.setFIUP_SESS_NO(mySESS_NO);
        myDATA_FILE_UPLOAD.setFIUP_DURA_NO(Integer.parseInt(myReportType));
        myDATA_FILE_UPLOAD.setFIUP_MEMB_NO(Integer.parseInt(myMEMB_NO));
        myDATA_FILE_UPLOAD.setFIUP_STATUS(11);
        myDATA_FILE_UPLOAD.setFIUP_DS("");
        myDATA_FILE_UPLOAD.setFIUP_ADS("");
        myDATA_FILE_UPLOAD.setFIUP_SIZE(0L);
        myDATA_FILE_UPLOAD.setFIUP_CONTANT("");
        myDATA_FILE_UPLOAD.setFIUP_AUID(Integer.parseInt(myClsMain.myDATA_USERS.getUSER_NO()));
        myDATA_FILE_UPLOAD.setFIUP_ASYSDATE("sysdate");
        myDATA_FILE_UPLOAD.setFIUP_UID(Integer.parseInt(myClsMain.myDATA_USERS.getUSER_NO()));

        myDATA_FILE_UPLOAD.setFIUP_SYSDATE("sysdate");

        String myScriptInsert = myDATA_FILE_UPLOAD.getInsertScript();

        if (!myScriptInsert.equals("")) {
            myCls.ExecuteNonQuery(myScriptInsert);
            if (myCls.isERROR() == false) {
                SearchForm(myClsMain, myCls);
            } else {
                myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
            }
        } else {
            myClsMain.PrintOut("Error class insert script....");
        }
    }

    private void CTC(clsMain myClsMain, clsDataBase myCls) {
        String myID = myClsMain.myRequest.getParameter("ID");

        String myScriptUpldate = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=12, FIUP_ADS='" + myClsMain.myDATA_USERS.getUSER_NAME() + "' , FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + ",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myID;

        if (!myScriptUpldate.equals("")) {
            myCls.ExecuteNonQuery(myScriptUpldate);
            if (myCls.isERROR() == false) {
                SearchForm(myClsMain, myCls);
            } else {
                myClsMain.PrintOut("Error ! " + myCls.getERROR_Message());
            }
        } else {
            myClsMain.PrintOut("Error class update script....");
        }
    }

    private void Download(clsMain myClsMain, clsDataBase myCls) {
        String myID = myClsMain.myRequest.getParameter("ID");
        String myScript = "SELECT COMP_ORG_FILENAME FROM DATA_FILE_UPLOAD T, DATA_ACK_COMPLAINT T2 WHERE T.FIUP_NO = T2.COMP_FILEID AND T.FIUP_NO=" + myID;
        String myFileName = myCls.ExecuteScalar(myScript).toString();
        String myPart = myCls.getParameter_Value(myReportType);
        clsFileDownload myFileDownload = new clsFileDownload();
        myFileDownload.StartDowload(myClsMain.myRequest, myClsMain.myResponse, myPart + "\\" + myFileName, myFileName);

    }

    private void SearchForm(clsMain myClsMain, clsDataBase myCls) {
        clsConverter c = new clsConverter();
        String mySessionNo = "";

        String mySESS_NAME = myClsMain.myRequest.getParameter("myBox_FIUP_SESS_NO");
        if (myReportType.equals("11") || myReportType.equals("12")) {



            String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate_feed);
            String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate_feed);
            myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

            // myBodyScript=myBodyScript.replace("#myVal_FIUP_SESS_NO#", "");
            myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
            myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
            myBodyScript = getInformation_feed(myClsMain, myCls, myBodyScript);
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = new Date();

            String mydate_NAME = "";
            String myScript = null;
            ResultSet myResultSet = null;
            mydate_NAME = myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
            if (!"".equals(mydate_NAME) && mydate_NAME != null) {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT COMP_ORG_FILENAME,FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO JOIN DATA_ACK_COMPLAINT ON COMP_FILEID= FIUP_NO WHERE trunc(FIUP_SYSDATE) =to_date('" + mydate_NAME + "') AND FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
                myResultSet = myCls.ExecuteReader(myScript);
                mydate_NAME = "";
            } else {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT COMP_ORG_FILENAME,FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO JOIN DATA_ACK_COMPLAINT ON COMP_FILEID= FIUP_NO WHERE trunc(FIUP_SYSDATE) =to_date('" + dateFormat.format(date) + "') AND FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO";
                myResultSet = myCls.ExecuteReader(myScript);
                mydate_NAME = "";
            }
            String myHTMLTABLE;
            myHTMLTABLE = "<TABLE id='report' align='center' style='font-size:14px;'>";
            myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
            myHTMLTABLE += "<TD align='center'>Member Name</TD>";
            myHTMLTABLE += "<TD align='center'>Name</TD>";

            myHTMLTABLE += "<TD align='center'>Duration</TD>";

            myHTMLTABLE += "<TD align='center'>Status</TD>";
            myHTMLTABLE += "<TD align='center'>Download</TD>";
            myHTMLTABLE += "<TD align='center'>Reject</TD>";

            myHTMLTABLE += "</TR>";
            try {
                while (myResultSet.next()) {
                    myHTMLTABLE += "<TR>\n";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("COMP_ORG_FILENAME")) + "</TD>";

                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("DURA_NAME")) + "</TD>";
                    if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("")) {
                        if (myStatus.equals("Closed")) {
                            //myHTMLTABLE +="<TD align='center'><a href='InputReportadmin?ReportType="+ myReportType +"&Type=CTO&SESS_NO="+ mySessionNo +"&MEMB_NO="+ c.toHTML(myResultSet.getObject("MEMB_NO")) +"'>"+ "Closed" +"</a></TD>";
                            myHTMLTABLE += "<TD align='center'><a href='javascript:Open_Session(" + myReportType + ",\"" + mySessionNo + "\"," + c.toHTML(myResultSet.getObject("MEMB_NO")) + ")'>" + "Closed" + "</a></TD>";
                        } else {
                            myHTMLTABLE += "<TD align='center'>" + myStatus + "</a></TD>";
                        }
                    } else {
                        if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("11")) {
                            //myHTMLTABLE +="<TD align='center'><a href='InputReportadmin?ReportType="+ myReportType +"&Type=CTC&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>" + c.toHTML(myResultSet.getObject("FIST_NAME"))  +"</a></TD>";
                            myHTMLTABLE += "<TD align='center'><a href='javascript:Close_Session(" + myReportType + "," + c.toHTML(myResultSet.getObject("FIUP_NO")) + ")'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</a></TD>";
                        } else {
                            myHTMLTABLE += "<TD align='center'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</TD>";
                        }
                    }

                    if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("2")) {
                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Downloads&ID=" + c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>Download</a></TD>";
                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Reject&ID=" + c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>Reject</a></TD>";
                    } else {
                        myHTMLTABLE += "<TD align='center'></TD>";
                        myHTMLTABLE += "<TD align='center'></TD>";

                    }
                    myHTMLTABLE += "</TR>\n";
                }
                myResultSet.close();
                myHTMLTABLE += "</TABLE>";
                myBodyScript = myBodyScript.replace("#DATA#", myHTMLTABLE);
                try {
                    myClsMain.ShowPageContain(myBodyScript);
                } catch (IOException ex) {
                    Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }

        } else if (myReportType.equals("13")) {
            String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate_feed);
            String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate_feed);
            myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

            // myBodyScript=myBodyScript.replace("#myVal_FIUP_SESS_NO#", "");
            myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
            myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
            myBodyScript = getInformation_feed(myClsMain, myCls, myBodyScript);
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = new Date();

            String mydate_NAME = "";
            String myScript = null;
            ResultSet myResultSet = null;
            mydate_NAME = myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
            if (!"".equals(mydate_NAME) && mydate_NAME != null) {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT ENQY_NO,ENQY_MEMBERID,ENQY_STATUS,ENQY_USERID,ENQY_ENQUIRYTYPEID,ENQY_ENQURYDETAIL,ENTY_NAME,ENQY_ENQUIRYDATE,FIST_NAME FROM DATA_ENQUIRY JOIN DATA_FILE_STATUS ON ENQY_STATUS=FIST_NO JOIN DATA_ENQUIRYTYPE ON ENTY_NO=ENQY_ENQUIRYTYPEID  WHERE trunc(ENQY_ENQUIRYDATE) =to_date('" + mydate_NAME + "')  AND ENQY_STATUS IN (1,2,11,4)) T1 ON T.MEMB_NO=T1.ENQY_MEMBERID ";
                myResultSet = myCls.ExecuteReader(myScript);
                mydate_NAME = "";
            } else {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT ENQY_NO,ENQY_MEMBERID,ENQY_STATUS,ENQY_USERID,ENQY_ENQUIRYTYPEID,ENQY_ENQURYDETAIL,ENTY_NAME,ENQY_ENQUIRYDATE,FIST_NAME FROM DATA_ENQUIRY JOIN DATA_FILE_STATUS ON ENQY_STATUS=FIST_NO JOIN DATA_ENQUIRYTYPE ON ENTY_NO=ENQY_ENQUIRYTYPEID  WHERE trunc(ENQY_ENQUIRYDATE) =to_date('" + dateFormat.format(date) + "')  AND ENQY_STATUS IN (1,2,11,4)) T1 ON T.MEMB_NO=T1.ENQY_MEMBERID ";
                myResultSet = myCls.ExecuteReader(myScript);
                mydate_NAME = "";
            }
            String myHTMLTABLE;
            myHTMLTABLE = "<TABLE id='report' align='center' style='font-size:14px;'>";
            myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
            myHTMLTABLE += "<TD align='center'>Member Name</TD>";
            myHTMLTABLE += "<TD align='center'>Enquiry Subject</TD>";

            myHTMLTABLE += "<TD align='center'>Short Description</TD>";

            myHTMLTABLE += "<TD align='center'>Status</TD>";
            myHTMLTABLE += "<TD align='center'>View Details</TD>";
            myHTMLTABLE += "<TD align='center'>Reject</TD>";

            myHTMLTABLE += "</TR>";
            try {
                while (myResultSet.next()) {
                    myHTMLTABLE += "<TR>\n";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("ENTY_NAME")) + "</TD>";

                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("ENQY_ENQURYDETAIL")) + "</TD>";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</TD>";
                    if (c.toHTML(myResultSet.getObject("ENQY_STATUS")).equals("2") || c.toHTML(myResultSet.getObject("ENQY_STATUS")).equals("4")) {

                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Details&ID=" + c.toHTML(myResultSet.getObject("ENQY_NO")) + "'>View Details</a></TD>";
                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Reject_enquiry&ID=" + c.toHTML(myResultSet.getObject("ENQY_NO")) + "'>Reject</a></TD>";
                    } else {
                        myHTMLTABLE += "<TD align='center'></TD>";
                        myHTMLTABLE += "<TD align='center'></TD>";

                    }

                    myHTMLTABLE += "</TR>\n";
                }
                myResultSet.close();
                myHTMLTABLE += "</TABLE>";
                myBodyScript = myBodyScript.replace("#DATA#", myHTMLTABLE);
                try {
                    myClsMain.ShowPageContain(myBodyScript);
                } catch (IOException ex) {
                    Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }
        } else {
            if (mySESS_NAME != null && !mySESS_NAME.equals("")) {
                String myStr = "SELECT SERE_NO FROM DATA_SESSIONS WHERE SERE_DURA_NO=" + myReportType + " AND SERE_NAME='" + mySESS_NAME + "'";
                Object myResult = myCls.ExecuteScalar(myStr);
                if (myResult == null) {

                    String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate);
                    String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate);
                    myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

                    myBodyScript = myBodyScript.replace("#myVal_FIUP_SESS_NO#", "");
                    myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
                    myBodyScript = myBodyScript.replace("#REPORT_NAME#", mySESS_NAME);
                    myBodyScript = getInformation(myClsMain, myCls, myBodyScript, "");
                    myBodyScript = myBodyScript.replace("#DATA#", "Your session name that you were selected is invalid");
                    try {
                        myClsMain.ShowPageContain(myBodyScript);
                    } catch (IOException ex) {
                        Logger.getLogger(controller_DATA_FILE_UPLOAD_ADMIN.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return;
                } else {
                    mySessionNo = myResult.toString();
                }
            } else {
                String myR = myCls.ExecuteScalar("SELECT SESS_NO || ',' || SESS_NAME SESS_NO FROM DATA_SESSION WHERE SESS_DURA_NO=" + myReportType).toString();
                String[] myRs = myR.split(",");
                mySessionNo = myRs[0];
                mySESS_NAME = myRs[1];
            }

            String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate);
            String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate);
            myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

            myBodyScript = myBodyScript.replace("#myVal_FIUP_SESS_NO#", mySESS_NAME);
            myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
            myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
            myBodyScript = getInformation(myClsMain, myCls, myBodyScript, mySessionNo);
            //myBodyScript=myBodyScript.replace("#myVal_FIUP_DURA_NO#",myCls.getSelectOptoinValue("DURA_NO","DURA_NAME","DATA_DURATION",""));


            String myScript = null;
            ResultSet myResultSet;
            mydate_NAME = myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
            if (!"".equals(mydate_NAME) && mydate_NAME != null) {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO WHERE trunc(FIUP_SYSDATE) =to_date('" + mydate_NAME + "') AND FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO";
                myResultSet = myCls.ExecuteReader(myScript);
                mydate_NAME = "";
            } else {
                myScript = "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO WHERE FIUP_SESS_NO ='" + mySessionNo + "' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO";
                System.out.println("");
                myResultSet = myCls.ExecuteReader(myScript);
            }
            String myHTMLTABLE;
            myHTMLTABLE = "<TABLE id='report' align='center' style='font-size:14px;'>";
            myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
            myHTMLTABLE += "<TD align='center'>Member Name</TD>";
            myHTMLTABLE += "<TD align='center'>Name</TD>";

            myHTMLTABLE += "<TD align='center'>Duration</TD>";

            myHTMLTABLE += "<TD align='center'>Status</TD>";
            myHTMLTABLE += "<TD align='center'>Download</TD>";
            myHTMLTABLE += "<TD align='center'>Reject</TD>";

            myHTMLTABLE += "</TR>";
            try {
                while (myResultSet.next()) {
                    myHTMLTABLE += "<TR>\n";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_NAME")) + "</TD>";

                    myHTMLTABLE += "<TD align='left'>" + c.toHTML(myResultSet.getObject("DURA_NAME")) + "</TD>";
                    if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("")) {
                        if (myStatus.equals("Closed")) {
                            //myHTMLTABLE +="<TD align='center'><a href='InputReportadmin?ReportType="+ myReportType +"&Type=CTO&SESS_NO="+ mySessionNo +"&MEMB_NO="+ c.toHTML(myResultSet.getObject("MEMB_NO")) +"'>"+ "Closed" +"</a></TD>";
                            myHTMLTABLE += "<TD align='center'><a href='javascript:Open_Session(" + myReportType + ",\"" + mySessionNo + "\"," + c.toHTML(myResultSet.getObject("MEMB_NO")) + ")'>" + "Closed" + "</a></TD>";
                        } else {
                            myHTMLTABLE += "<TD align='center'>" + myStatus + "</a></TD>";
                        }
                    } else {
                        if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("11")) {
                            //myHTMLTABLE +="<TD align='center'><a href='InputReportadmin?ReportType="+ myReportType +"&Type=CTC&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>" + c.toHTML(myResultSet.getObject("FIST_NAME"))  +"</a></TD>";
                            myHTMLTABLE += "<TD align='center'><a href='javascript:Close_Session(" + myReportType + "," + c.toHTML(myResultSet.getObject("FIUP_NO")) + ")'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</a></TD>";
                        } else {
                            myHTMLTABLE += "<TD align='center'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</TD>";
                        }
                    }

                    if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("2")) {
                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Download&ID=" + c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>Download</a></TD>";
                        myHTMLTABLE += "<TD align='center'><a href='InputReportadmin?ReportType=" + myReportType + "&Type=Reject&ID=" + c.toHTML(myResultSet.getObject("FIUP_NO")) + "'>Reject</a></TD>";
                    } else {
                        myHTMLTABLE += "<TD align='center'></TD>";
                        myHTMLTABLE += "<TD align='center'></TD>";

                    }
                    myHTMLTABLE += "</TR>\n";
                }
                myResultSet.close();
                myHTMLTABLE += "</TABLE>";
                myBodyScript = myBodyScript.replace("#DATA#", myHTMLTABLE);
                try {
                    myClsMain.ShowPageContain(myBodyScript);
                } catch (IOException ex) {
                    Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }



        }
    }

    private String getInformation(clsMain myClsMain, clsDataBase myCls, String pBodyScript, String pSESS_NO) {
        clsConverter c = new clsConverter();

        if (pSESS_NO.equals("")) {
            pBodyScript = pBodyScript.replace("#SESSION_NAME#", "");
            pBodyScript = pBodyScript.replace("#OUP#", "");
            pBodyScript = pBodyScript.replace("#CUP#", "");
            pBodyScript = pBodyScript.replace("#STATUS#", myStatus);
            return pBodyScript;
        }

        String mySession_Name = "";
        String myOUP = "";
        String myCUP = "";

        String myScript = "SELECT SERE_NO,SERE_NAME,SERE_DURA_NO,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP,SERE_SYSUID,NVL(to_char(SERE_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_SYSDATE,to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss') SYSTEMDATETIME FROM DATA_SESSIONS ";
        myScript += " WHERE SERE_DURA_NO=" + myReportType + " AND  SERE_NO='" + pSESS_NO + "'";
        model_DATA_SESSIONS myDATA_SESSIONS = new model_DATA_SESSIONS();

        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            if (myResultSet.next()) {
                myDATA_SESSIONS.setData(myResultSet);
                String myCurrentDate = myResultSet.getString("SYSTEMDATETIME");

                if (myCls.isERROR() == true) {
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();
                //If session still open
                myOUP = myDATA_SESSIONS.getSERE_OUP();
                myCUP = myDATA_SESSIONS.getSERE_CUP();
                mySession_Name = myDATA_SESSIONS.getSERE_NAME();
                if (c.CompareDateTimeInBetween(myCurrentDate, myDATA_SESSIONS.getSERE_OUP(), myDATA_SESSIONS.getSERE_CUP()) == 0) {
                    myStatus = "Open";
                } else {
                    myStatus = "Closed";
                    String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS= 4";
                    myScriptUpdate += " WHERE FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS= 1 AND FIUP_SESS_NO='" + pSESS_NO + "'";
                    if (!myScriptUpdate.equals("")) {
                        myCls.ExecuteNonQuery(myScriptUpdate);
                    }
                }
            }
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        } finally {
            myCls.CloseResultSet();
        }
        pBodyScript = pBodyScript.replace("#SESSION_NAME#", mySession_Name);
        pBodyScript = pBodyScript.replace("#OUP#", myOUP);
        pBodyScript = pBodyScript.replace("#CUP#", myCUP);
        pBodyScript = pBodyScript.replace("#STATUS#", myStatus);

        return pBodyScript;

    }

    private String getInformation_feed(clsMain myClsMain, clsDataBase myCls, String pBodyScript) {
        clsConverter c = new clsConverter();
        String mydate_Name = "";

        mydate_Name = myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
        String mySession_Name = "";
        String myOUP = "";
        String myCUP = "";

        String myScript = "SELECT SERE_NO,SERE_NAME,SERE_DURA_NO,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP,SERE_SYSUID,NVL(to_char(SERE_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_SYSDATE,to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss') SYSTEMDATETIME FROM DATA_SESSIONS ";
        myScript += " WHERE SERE_DURA_NO=" + myReportType;
        model_DATA_SESSIONS myDATA_SESSIONS = new model_DATA_SESSIONS();

        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            if (myResultSet.next()) {
                myDATA_SESSIONS.setData(myResultSet);
                String myCurrentDate = myResultSet.getString("SYSTEMDATETIME");

                if (myCls.isERROR() == true) {
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();
                //If session still open
                myOUP = myDATA_SESSIONS.getSERE_OUP();
                myCUP = myDATA_SESSIONS.getSERE_CUP();
                mySession_Name = myDATA_SESSIONS.getSERE_NAME();
                if (c.CompareDateTimeInBetween(myCurrentDate, myDATA_SESSIONS.getSERE_OUP(), myDATA_SESSIONS.getSERE_CUP()) == 0) {
                    myStatus = "Open";
                } else {
                    myStatus = "Closed";
                    String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS= 4";
                    myScriptUpdate += " WHERE FIUP_DURA_NO=" + myReportType + " AND FIUP_STATUS= 1  ";
                    if (!myScriptUpdate.equals("")) {
                        myCls.ExecuteNonQuery(myScriptUpdate);
                    }
                }
            }
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        } finally {
            myCls.CloseResultSet();
        }
        if (mydate_Name == null) {
            pBodyScript = pBodyScript.replace("#SESSION_NAME#", "today");
        } else {
            pBodyScript = pBodyScript.replace("#SESSION_NAME#", mydate_Name);
        }
        pBodyScript = pBodyScript.replace("#OUP#", myOUP);
        pBodyScript = pBodyScript.replace("#CUP#", myCUP);
        pBodyScript = pBodyScript.replace("#STATUS#", myStatus);

        return pBodyScript;

    }

    private void LoadEditPage(clsMain myClsMain, clsDataBase myCls) {
        clsConverter c = new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile(myFormEditTemplate);
        String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

        myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
        myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
        model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD = new model_DATA_FILE_UPLOAD();
        String myID = myClsMain.myRequest.getParameter("ID");
        if (myID == null) {
            myClsMain.PrintOut("Wrong request no data to for view");
            return;
        }
        String myCondition = " WHERE FIUP_NO=" + myID;
        String myScript = myDATA_FILE_UPLOAD.getSelectScript() + myCondition;
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            while (myResultSet.next()) {
                myBodyScript = myBodyScript.replace("#myVal_FIUP_NO#", c.toHTML(myResultSet.getObject("FIUP_NO")));
                myBodyScript = myBodyScript.replace("#myVal_FIUP_NAME#", c.toHTML(myResultSet.getObject("FIUP_NAME")));
                myBodyScript = myBodyScript.replace("#myVal_FIUP_SESS_NO#", c.toHTML(myResultSet.getObject("FIUP_SESS_NO")));
                myBodyScript = myBodyScript.replace("#myVal_FIUP_DURA_NO#", myCls.getSelectOptoinValue("DURA_NO", "DURA_NAME", "DATA_DURATION", c.toHTML(myResultSet.getObject("FIUP_DURA_NO"))));
                myBodyScript = myBodyScript.replace("#myVal_FIUP_MEMB_NO#", myCls.getSelectOptoinValue("MEMB_NO", "MEMB_SNAME", "DATA_MEMBERS", c.toHTML(myResultSet.getObject("FIUP_MEMB_NO"))));
                myBodyScript = myBodyScript.replace("#myVal_FIUP_STATUS#", myCls.getSelectOptoinValue("FIST_NO", "FIST_NAME", "DATA_FILE_STATUS", c.toHTML(myResultSet.getObject("FIUP_STATUS"))));

            }
            myResultSet.close();
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }

        try {
            myClsMain.ShowPageContain(myBodyScript);
        } catch (IOException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }
    }

    private void Load_Details(clsMain myClsMain, clsDataBase myCls) {
        clsConverter c = new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile(myFormDetail);
        String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate_feed);
        myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

        myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
        myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
        model_DATA_ENQUIRY myDATA_ENQUIRY = new model_DATA_ENQUIRY();
        String myID = myClsMain.myRequest.getParameter("ID");
        if (myID == null) {
            myClsMain.PrintOut("Wrong request no data to for view");
            return;
        }
        String myScript = "select MEMB_NAME, ENQY_ENQUIRYDATE,FIST_NAME,ENQY_CONTACTNAME,ENQY_CONTACTPOSITION,ENQY_EMAIL,ENQY_PHONE,ENTY_NAME,COBY_NAME,ENQY_ENQURYDETAIL FROM DATA_CONTACTBYTYPE T1, DATA_ENQUIRY T2,DATA_ENQUIRYTYPE T3,DATA_FILE_STATUS T4,DATA_MEMBERS T5 WHERE T5.MEMB_NO= T2.ENQY_MEMBERID AND T3.ENTY_NO=T2.ENQY_ENQUIRYTYPEID AND T4.FIST_NO=T2.ENQY_STATUS AND T1.COBY_NO= T2.ENQY_CONTACTBYID AND T2.ENQY_NO=" + myID;
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            while (myResultSet.next()) {
                myBodyScript = myBodyScript.replace("#Bank_Name#", c.toHTML(myResultSet.getObject("MEMB_NAME")));
                myBodyScript = myBodyScript.replace("#Upload_date#", c.toHTML(myResultSet.getObject("ENQY_ENQUIRYDATE")));
                myBodyScript = myBodyScript.replace("#status#", c.toHTML(myResultSet.getObject("FIST_NAME")));
                myBodyScript = myBodyScript.replace("#Upload_name#", c.toHTML(myResultSet.getObject("ENQY_CONTACTNAME")));
                myBodyScript = myBodyScript.replace("#Position#", c.toHTML(myResultSet.getObject("ENQY_CONTACTPOSITION")));
                myBodyScript = myBodyScript.replace("#email_address#", c.toHTML(myResultSet.getObject("ENQY_EMAIL")));
                myBodyScript = myBodyScript.replace("#phone#", c.toHTML(myResultSet.getObject("ENQY_PHONE")));
                myBodyScript = myBodyScript.replace("#Enquiry_Subject#", c.toHTML(myResultSet.getObject("ENTY_NAME")));
                myBodyScript = myBodyScript.replace("#type#", c.toHTML(myResultSet.getObject("COBY_NAME")));
                myBodyScript = myBodyScript.replace("#details#", c.toHTML(myResultSet.getObject("ENQY_ENQURYDETAIL")));



            }
            myResultSet.close();
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }

        try {
            myClsMain.ShowPageContain(myBodyScript);
        } catch (IOException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }
    }

    private void LoadEditPage_enquiry(clsMain myClsMain, clsDataBase myCls) {
        clsConverter c = new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile(myFormEditTemplateEnquiry);
        String mySubManuScript = myClsMain.getContentFromFile(mySubManuTemplate_feed);
        myBodyScript = myBodyScript.replace("#SUB_MANU#", mySubManuScript);

        myBodyScript = myBodyScript.replace("#REPORT_TYPE#", myReportType);
        myBodyScript = myBodyScript.replace("#REPORT_NAME#", myReportName);
        model_DATA_ENQUIRY myDATA_ENQUIRY = new model_DATA_ENQUIRY();
        String myID = myClsMain.myRequest.getParameter("ID");
        if (myID == null) {
            myClsMain.PrintOut("Wrong request no data to for view");
            return;
        }
        String myScript = "select ENQY_NO, MEMB_NAME, ENQY_ENQUIRYDATE,FIST_NAME,ENQY_CONTACTNAME,ENQY_CONTACTPOSITION,ENQY_EMAIL,ENQY_PHONE,ENTY_NAME,COBY_NAME,ENQY_ENQURYDETAIL FROM DATA_CONTACTBYTYPE T1, DATA_ENQUIRY T2,DATA_ENQUIRYTYPE T3,DATA_FILE_STATUS T4,DATA_MEMBERS T5 WHERE T5.MEMB_NO= T2.ENQY_MEMBERID AND T3.ENTY_NO=T2.ENQY_ENQUIRYTYPEID AND T4.FIST_NO=T2.ENQY_STATUS AND T1.COBY_NO= T2.ENQY_CONTACTBYID AND T2.ENQY_NO=" + myID;
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try {
            while (myResultSet.next()) {
                myBodyScript = myBodyScript.replace("#ID#", c.toHTML(myResultSet.getObject("ENQY_NO")));
                myBodyScript = myBodyScript.replace("#Bank_Name#", c.toHTML(myResultSet.getObject("MEMB_NAME")));
                myBodyScript = myBodyScript.replace("#Upload_date#", c.toHTML(myResultSet.getObject("ENQY_ENQUIRYDATE")));
                myBodyScript = myBodyScript.replace("#status#", c.toHTML(myResultSet.getObject("FIST_NAME")));
                myBodyScript = myBodyScript.replace("#Upload_name#", c.toHTML(myResultSet.getObject("ENQY_CONTACTNAME")));
                myBodyScript = myBodyScript.replace("#Position#", c.toHTML(myResultSet.getObject("ENQY_CONTACTPOSITION")));
                myBodyScript = myBodyScript.replace("#email_address#", c.toHTML(myResultSet.getObject("ENQY_EMAIL")));
                myBodyScript = myBodyScript.replace("#phone#", c.toHTML(myResultSet.getObject("ENQY_PHONE")));
                myBodyScript = myBodyScript.replace("#Enquiry_Subject#", c.toHTML(myResultSet.getObject("ENTY_NAME")));
                myBodyScript = myBodyScript.replace("#type#", c.toHTML(myResultSet.getObject("COBY_NAME")));
                myBodyScript = myBodyScript.replace("#details#", c.toHTML(myResultSet.getObject("ENQY_ENQURYDETAIL")));



            }
            myResultSet.close();
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }

        try {
            myClsMain.ShowPageContain(myBodyScript);
        } catch (IOException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }
    }

    private void myBtUpdate_Click_enquiry(clsMain myClsMain, clsDataBase myCls) {
        //model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD=new model_DATA_FILE_UPLOAD();
        String myFIUP_NO = myClsMain.myRequest.getParameter("ID");
        if (myFIUP_NO == null) {
            return;
        }

        String myStr = "UPDATE DATA_ENQUIRY SET ENQY_STATUS=4  WHERE ENQY_NO=" + myFIUP_NO;
        myCls.ExecuteNonQuery(myStr);
        if (myCls.isERROR() == true) {
            myClsMain.PrintOut(myCls.getERROR_Message());
        } else {
            try {
                myClsMain.myResponse.sendRedirect("InputReportadmin?ReportType=" + myReportType + "&Type=Search");
            } catch (IOException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }
        }
    }

    private void myBtUpdate_Click(clsMain myClsMain, clsDataBase myCls) {
        //model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD=new model_DATA_FILE_UPLOAD();
        String myFIUP_NO = myClsMain.myRequest.getParameter("myVal_FIUP_NO");
        if (myFIUP_NO == null) {
            return;
        }

        String myStr = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=4,FIUP_AUID=" + myClsMain.myDATA_USERS.getUSER_NO() + ",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myFIUP_NO;
        myCls.ExecuteNonQuery(myStr);
        if (myCls.isERROR() == true) {
            myClsMain.PrintOut(myCls.getERROR_Message());
        } else {
            try {
                myClsMain.myResponse.sendRedirect("InputReportadmin?ReportType=" + myReportType + "&Type=Search");
            } catch (IOException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
