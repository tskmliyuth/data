package myControllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.*;
import myModels.*;

@WebServlet(name = "controller_DATA_ONSIDE_FILES", urlPatterns = {"/onside_files"})
public class controller_DATA_ONSIDE_FILES extends HttpServlet {
        
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
        if (myType==null){
		SearchForm(myClsMain,myCls);          
        }else if(myType.equals("Search")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Edit")){
		LoadEditPage(myClsMain,myCls);
        }else if(myType.equals("Update")){
		myBtUpdate_Click(myClsMain,myCls);
        }else if(myType.equals("Download")){
		Download(myClsMain,myCls);
        }else if(myType.equals("Reject")){
		LoadEditPage(myClsMain,myCls);
        }
    }

private void Download(clsMain myClsMain,clsDataBase myCls) {
       String myID= myClsMain.myRequest.getParameter("ID");
       String myScript ="SELECT FIUP_NAME FROM DATA_ONSIDE_FILES WHERE FIUP_STATUS =200 AND FIUP_NO=" + myID;
       String myFileName=myCls.ExecuteScalar(myScript).toString();
       String myPart=myCls.getParameter_Value("200");
       clsFileDownload myFileDownload =new clsFileDownload();
       myFileDownload.StartDowload(myClsMain.myRequest,myClsMain.myResponse, myPart + "\\" + myFileName ,myFileName);
       
   } 


private void SearchForm(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile("DATA_ONSIDE_FILES_FORM_SEARCH.html");
        String  myTxtSearch= myClsMain.myRequest.getParameter("myTxtSearch");
        String myDate="";
        if (myTxtSearch==null || myTxtSearch.equals("")){
            myDate=c.getSystemDate("yyyy/MM/dd");
        }else{
            myDate=myTxtSearch;
        }
        String myScript= "SELECT FIUP_NO,FIUP_NAME,NVL(to_char(FIUP_REPORT_DATE, 'yyyy/mm/dd'),'') FIUP_REPORT_DATE,FIUP_DES,FIUP_MEMB_NO, MEMB_SNAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_ONSIDE_FILES JOIN DATA_MEMBERS ON FIUP_MEMB_NO=MEMB_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO ";
        myScript +=" WHERE ROWNUM<=30 AND to_char(FIUP_REPORT_DATE, 'yyyy/mm/dd')='"+ myDate +"'";
        
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>Name</TD>";
	myHTMLTABLE +="<TD align='center'>Report Date</TD>";
	myHTMLTABLE +="<TD align='center'>Description</TD>";
	myHTMLTABLE +="<TD align='center'>Member Name</TD>";
	
	myHTMLTABLE +="<TD align='center'>Download</TD>";
	//myHTMLTABLE +="<TD align='center'>Reject</TD>";
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_NAME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_REPORT_DATE")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_DES")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
	
	myHTMLTABLE +="<TD align='center'><a href='onside_files?Type=Download&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) +"'>Download</a></TD>";
	//myHTMLTABLE +="<TD align='center'><a href='onside_files?Type=Reject&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) +"'>Reject</a></TD>";

            	myHTMLTABLE += "</TR>\n";            
            }
		myResultSet.close();
		myHTMLTABLE +="</TABLE>";
                myBodyScript=myBodyScript.replace("#DATE#", myDate); 
		myBodyScript=myBodyScript.replace("#DATA#", myHTMLTABLE);  
		try {
			myClsMain.ShowPageContain(myBodyScript);
		} 
		catch (IOException ex) { 
		Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
		}	
        } 
        catch (SQLException ex) { myClsMain.PrintOut(ex.getMessage()); }
}

private void LoadEditPage(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
	String myBodyScript = myClsMain.getContentFromFile("DATA_ONSIDE_FILES_FORM_EDIT.html");
	model_DATA_ONSIDE_FILES myDATA_ONSIDE_FILES=new model_DATA_ONSIDE_FILES();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE FIUP_NO=" + myID;
	String myScript = myDATA_ONSIDE_FILES.getSelectScript() + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_FIUP_NO#",c.toHTML(myResultSet.getObject("FIUP_NO")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_NAME#",c.toHTML(myResultSet.getObject("FIUP_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_REPORT_DATE#",c.toHTML(myResultSet.getObject("FIUP_REPORT_DATE")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_DES#",c.toHTML(myResultSet.getObject("FIUP_DES")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_MEMB_NO#",myCls.getSelectOptoinValue("MEMB_NO","MEMB_SNAME","DATA_MEMBERS",c.toHTML(myResultSet.getObject("FIUP_MEMB_NO"))));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_STATUS#",myCls.getSelectOptoinValue("FIST_NO","FIST_NAME","DATA_FILE_STATUS",c.toHTML(myResultSet.getObject("FIUP_STATUS"))));

	}
	myResultSet.close();
	} 
	catch (SQLException ex) { 
	myClsMain.PrintOut(ex.getMessage()); 
	}
        
	try { 
		myClsMain.ShowPageContain(myBodyScript);
	} 
	catch (IOException ex) { 
		myClsMain.PrintOut(ex.getMessage());
	}
}

private void myBtUpdate_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_ONSIDE_FILES myDATA_ONSIDE_FILES=new model_DATA_ONSIDE_FILES();
	String myFIUP_NO= myClsMain.myRequest.getParameter("myBox_FIUP_NO");
	String myFIUP_NAME= myClsMain.myRequest.getParameter("myBox_FIUP_NAME");
	String myFIUP_REPORT_DATE= myClsMain.myRequest.getParameter("myBox_FIUP_REPORT_DATE");
	String myFIUP_DES= myClsMain.myRequest.getParameter("myBox_FIUP_DES");
	String myFIUP_MEMB_NO= myClsMain.myRequest.getParameter("myBox_FIUP_MEMB_NO");
	String myFIUP_STATUS= myClsMain.myRequest.getParameter("myBox_FIUP_STATUS");

	myDATA_ONSIDE_FILES.setFIUP_NO(myFIUP_NO);
	myDATA_ONSIDE_FILES.setFIUP_NAME(myFIUP_NAME);
	myDATA_ONSIDE_FILES.setFIUP_REPORT_DATE(myFIUP_REPORT_DATE);
	myDATA_ONSIDE_FILES.setFIUP_DES(myFIUP_DES);
	myDATA_ONSIDE_FILES.setFIUP_MEMB_NO(myFIUP_MEMB_NO);
	myDATA_ONSIDE_FILES.setFIUP_STATUS(myFIUP_STATUS);
 	
	myDATA_ONSIDE_FILES.setFIUP_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_ONSIDE_FILES.getErrorToString();        
	if (myDATA_ONSIDE_FILES.isERROR==false){
		String myScriptUpdate =myDATA_ONSIDE_FILES.getUpdateScript();
		myCls.ExecuteNonQuery(myScriptUpdate ); 
		if(myCls.isERROR()==true) {
			myERR="{\"myERR\":\"TRUE\",\"Message\":\""+ myCls.getERROR_Message().replace("\n", "") +".\"}";
            	}else{
                    myERR="{\"myERR\":\"FALSE\",\"Message\":\""+ "Successfully updated" +".\"}";
                }
	}
	myClsMain.PrintOut(myERR); 
}


    //Sevelet even 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
