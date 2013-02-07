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

@WebServlet(name = "controller_DATA_MEMBERS", urlPatterns = {"/members"})
public class controller_DATA_MEMBERS extends HttpServlet {
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"myTemplateNBCAdmin.html");
        clsDataBase myCls=new clsDataBase();

	if(myClsMain.CheckPermission("1")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        String  myType= myClsMain.myRequest.getParameter("Type");
        if (myType==null){
		LoadNewPage(myClsMain,myCls);            
        }else if(myType.equals("Save")){
		myBtSave_Click(myClsMain,myCls);
        }else if(myType.equals("Search")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Edit")){
		LoadEditPage(myClsMain,myCls);
        }else if(myType.equals("Update")){
		myBtUpdate_Click(myClsMain,myCls);
        }else{
		LoadNewPage(myClsMain,myCls);   
	}    
    }
    
private void LoadNewPage(clsMain myClsMain,clsDataBase myCls){
	String myBodyScript = myClsMain.getContentFromFile("DATA_MEMBERS_FORM.html");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_NO#","");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_NAME#","");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_SNAME#","");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_BOD#","");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_BCDATE#","");
	myBodyScript=myBodyScript.replace("#myVal_MEMB_STATUS#",myCls.getSelectOptoinValue("STAT_NO","STAT_NAME","SYS_STATUS","1"));

        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSave_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_MEMBERS myDATA_MEMBERS=new model_DATA_MEMBERS();
	String myMEMB_NO= myClsMain.myRequest.getParameter("myBox_MEMB_NO");
	String myMEMB_NAME= myClsMain.myRequest.getParameter("myBox_MEMB_NAME");
	String myMEMB_SNAME= myClsMain.myRequest.getParameter("myBox_MEMB_SNAME");
	String myMEMB_BOD= myClsMain.myRequest.getParameter("myBox_MEMB_BOD");
	String myMEMB_BCDATE= myClsMain.myRequest.getParameter("myBox_MEMB_BCDATE");
	String myMEMB_STATUS= myClsMain.myRequest.getParameter("myBox_MEMB_STATUS");

	myDATA_MEMBERS.setMEMB_NO(myMEMB_NO);
	myDATA_MEMBERS.setMEMB_NAME(myMEMB_NAME);
	myDATA_MEMBERS.setMEMB_SNAME(myMEMB_SNAME);
	myDATA_MEMBERS.setMEMB_BOD(myMEMB_BOD);
	myDATA_MEMBERS.setMEMB_BCDATE(myMEMB_BCDATE);
	myDATA_MEMBERS.setMEMB_STATUS(myMEMB_STATUS);
 	
	myDATA_MEMBERS.setMEMB_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_MEMBERS.getErrorToString();        
	if (myDATA_MEMBERS.isERROR==false){
		String myScriptInsert =myDATA_MEMBERS.getInsertScript();
		myCls.ExecuteNonQuery(myScriptInsert); 
		if(myCls.isERROR()==true) {
			myERR="{\"myERR\":\"TRUE\",\"Message\":\""+ myCls.getERROR_Message().replace("\n", "") +".\"}";
            	}else{
                    myERR="{\"myERR\":\"FALSE\",\"Message\":\""+ "Successfully saved" +".\"}";
                }
	}
	myClsMain.PrintOut(myERR); 
}

private void SearchForm(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile("DATA_MEMBERS_FORM_SEARCH.html");
        String myScript= "SELECT MEMB_NO,MEMB_NAME,MEMB_SNAME,NVL(to_char(MEMB_BOD, 'yyyy/mm/dd'),'') MEMB_BOD,NVL(to_char(MEMB_BCDATE, 'yyyy/mm/dd'),'') MEMB_BCDATE,MEMB_STATUS, STAT_NAME FROM DATA_MEMBERS JOIN SYS_STATUS ON MEMB_STATUS=STAT_NO ORDER BY MEMB_NO";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>NO</TD>";
	myHTMLTABLE +="<TD align='center'>Full Name</TD>";
	myHTMLTABLE +="<TD align='center'>Sort Name</TD>";
	myHTMLTABLE +="<TD align='center'>Date Created</TD>";
	myHTMLTABLE +="<TD align='center'>Date Closed</TD>";
	myHTMLTABLE +="<TD align='center'>Status</TD>";
	myHTMLTABLE +="<TD align='center'>EDIT</TD>";
	//myHTMLTABLE +="<TD align='center'>DELETE</TD>";
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
	myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("MEMB_NO")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_NAME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_BOD")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_BCDATE")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("STAT_NAME")) + "</TD>";
	myHTMLTABLE +="<TD align='center'><a href='members?Type=Edit&ID="+ c.toHTML(myResultSet.getObject("MEMB_NO")) +"'>Edit</a></TD>";
	//myHTMLTABLE +="<TD align='center'><a href='members?Type=Delete&ID="+ c.toHTML(myResultSet.getObject("MEMB_NO")) +"'>Delete</a></TD>";

            	myHTMLTABLE += "</TR>\n";            
            }
		myHTMLTABLE +="</TABLE>";
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
	String myBodyScript = myClsMain.getContentFromFile("DATA_MEMBERS_FORM_EDIT.html");
	model_DATA_MEMBERS myDATA_MEMBERS=new model_DATA_MEMBERS();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE MEMB_NO=" + myID;
	String myScript = myDATA_MEMBERS.getSelectScript() + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_MEMB_NO#",c.toHTML(myResultSet.getObject("MEMB_NO")));
	myBodyScript=myBodyScript.replace("#myVal_MEMB_NAME#",c.toHTML(myResultSet.getObject("MEMB_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_MEMB_SNAME#",c.toHTML(myResultSet.getObject("MEMB_SNAME")));
	myBodyScript=myBodyScript.replace("#myVal_MEMB_BOD#",c.toHTML(myResultSet.getObject("MEMB_BOD")));
	myBodyScript=myBodyScript.replace("#myVal_MEMB_BCDATE#",c.toHTML(myResultSet.getObject("MEMB_BCDATE")));
	myBodyScript=myBodyScript.replace("#myVal_MEMB_STATUS#",myCls.getSelectOptoinValue("STAT_NO","STAT_NAME","SYS_STATUS",c.toHTML(myResultSet.getObject("MEMB_STATUS"))));

	}
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
	model_DATA_MEMBERS myDATA_MEMBERS=new model_DATA_MEMBERS();
	String myMEMB_NO= myClsMain.myRequest.getParameter("myBox_MEMB_NO");
	String myMEMB_NAME= myClsMain.myRequest.getParameter("myBox_MEMB_NAME");
	String myMEMB_SNAME= myClsMain.myRequest.getParameter("myBox_MEMB_SNAME");
	String myMEMB_BOD= myClsMain.myRequest.getParameter("myBox_MEMB_BOD");
	String myMEMB_BCDATE= myClsMain.myRequest.getParameter("myBox_MEMB_BCDATE");
	String myMEMB_STATUS= myClsMain.myRequest.getParameter("myBox_MEMB_STATUS");

	myDATA_MEMBERS.setMEMB_NO(myMEMB_NO);
	myDATA_MEMBERS.setMEMB_NAME(myMEMB_NAME);
	myDATA_MEMBERS.setMEMB_SNAME(myMEMB_SNAME);
	myDATA_MEMBERS.setMEMB_BOD(myMEMB_BOD);
	myDATA_MEMBERS.setMEMB_BCDATE(myMEMB_BCDATE);
	myDATA_MEMBERS.setMEMB_STATUS(myMEMB_STATUS);
 	
	myDATA_MEMBERS.setMEMB_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_MEMBERS.getErrorToString();        
	if (myDATA_MEMBERS.isERROR==false){
		String myScriptUpdate =myDATA_MEMBERS.getUpdateScript();
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
