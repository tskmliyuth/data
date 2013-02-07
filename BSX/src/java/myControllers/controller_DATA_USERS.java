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

@WebServlet(name = "controller_DATA_USERS", urlPatterns = {"/users"})
public class controller_DATA_USERS extends HttpServlet {
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"myTemplateNBCAdmin.html");
        clsDataBase myCls=new clsDataBase();
        String  myType= myClsMain.myRequest.getParameter("Type");
        if(myClsMain.CheckPermission("1")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }       
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
	String myBodyScript = myClsMain.getContentFromFile("DATA_USERS_FORM.html");
	myBodyScript=myBodyScript.replace("#myVal_USER_NO#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_NAME#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_PWD#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_FULL_NAME#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_ROLE_NO#",myCls.getSelectOptoinValue("ROLE_NO","ROLE_NAME","DATA_ROLES",""));
	myBodyScript=myBodyScript.replace("#myVal_USER_MEMB_NO#",myCls.getSelectOptoinValue("MEMB_NO","MEMB_NAME","DATA_MEMBERS",""));
	myBodyScript=myBodyScript.replace("#myVal_USER_STATUS#",myCls.getSelectOptoinValue("STAT_NO","STAT_NAME","SYS_STATUS",""));
	myBodyScript=myBodyScript.replace("#myVal_USER_ADDRESS#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_PHONE#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_FAX#","");
	myBodyScript=myBodyScript.replace("#myVal_USER_EMAIL#","");
        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSave_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_USERS myDATA_USERS=new model_DATA_USERS();
	String myUSER_NAME= myClsMain.myRequest.getParameter("myBox_USER_NAME");
	String myUSER_PWD= myClsMain.myRequest.getParameter("myBox_USER_PWD");
	String myUSER_FULL_NAME= myClsMain.myRequest.getParameter("myBox_USER_FULL_NAME");
	String myUSER_ROLE_NO= myClsMain.myRequest.getParameter("myBox_USER_ROLE_NO");
	String myUSER_MEMB_NO= myClsMain.myRequest.getParameter("myBox_USER_MEMB_NO");
	String myUSER_STATUS= myClsMain.myRequest.getParameter("myBox_USER_STATUS");
	String myUSER_ADDRESS= myClsMain.myRequest.getParameter("myBox_USER_ADDRESS");
	String myUSER_PHONE= myClsMain.myRequest.getParameter("myBox_USER_PHONE");
	String myUSER_FAX= myClsMain.myRequest.getParameter("myBox_USER_FAX");
	String myUSER_EMAIL= myClsMain.myRequest.getParameter("myBox_USER_EMAIL");

	myDATA_USERS.setUSER_NAME(myUSER_NAME);
	myDATA_USERS.setUSER_PWD(myUSER_PWD);
	myDATA_USERS.setUSER_FULL_NAME(myUSER_FULL_NAME);
	myDATA_USERS.setUSER_ROLE_NO(myUSER_ROLE_NO);
	myDATA_USERS.setUSER_MEMB_NO(myUSER_MEMB_NO);
	myDATA_USERS.setUSER_STATUS(myUSER_STATUS);
	myDATA_USERS.setUSER_ADDRESS(myUSER_ADDRESS);
	myDATA_USERS.setUSER_PHONE(myUSER_PHONE);
	myDATA_USERS.setUSER_FAX(myUSER_FAX);
	myDATA_USERS.setUSER_EMAIL(myUSER_EMAIL);
 	
	myDATA_USERS.setUSER_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_USERS.getErrorToString();        
	if (myDATA_USERS.isERROR==false){
		String myScriptInsert =myDATA_USERS.getInsertScript();
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
        String myBodyScript = myClsMain.getContentFromFile("DATA_USERS_FORM_SEARCH.html");
        String myScript= "SELECT USER_NO,USER_NAME,USER_PWD,NVL(USER_FULL_NAME,'') USER_FULL_NAME,USER_ROLE_NO,ROLE_NAME,USER_MEMB_NO,MEMB_NAME,USER_STATUS,STAT_NAME,NVL(USER_ADDRESS,'') USER_ADDRESS,NVL(USER_PHONE,'') USER_PHONE,NVL(USER_FAX,'') USER_FAX,NVL(USER_EMAIL,'') USER_EMAIL,USER_SYSUID,NVL(to_char(USER_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') USER_SYSDATE FROM DATA_USERS JOIN DATA_ROLES ON USER_ROLE_NO=ROLE_NO JOIN DATA_MEMBERS ON USER_MEMB_NO=MEMB_NO JOIN SYS_STATUS ON USER_STATUS=STAT_NO ORDER BY USER_ROLE_NO,USER_FULL_NAME ";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>Username</TD>";
	myHTMLTABLE +="<TD align='center'>Full Name</TD>";
	myHTMLTABLE +="<TD align='center'>User Role</TD>";
	myHTMLTABLE +="<TD align='center'>Member Name</TD>";
	myHTMLTABLE +="<TD align='center'>User Status</TD>";
	myHTMLTABLE +="<TD align='center'>Address</TD>";
	myHTMLTABLE +="<TD align='center'>Phone</TD>";
	myHTMLTABLE +="<TD align='center'>Fax</TD>";
	myHTMLTABLE +="<TD align='center'>Email</TD>";
        
        myHTMLTABLE +="<TD align='center'></TD>";
        //myHTMLTABLE +="<TD align='center'></TD>";
           
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_NAME")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_FULL_NAME")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("ROLE_NAME")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("MEMB_NAME")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("STAT_NAME")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_ADDRESS")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_PHONE")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_FAX")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("USER_EMAIL")) + "</TD>";
                myHTMLTABLE +="<TD align='center'><a href='users?Type=Edit&ID="+ c.toHTML(myResultSet.getObject("USER_NO")) +"'>Edit</a></TD>";
                //myHTMLTABLE +="<TD align='center'><a href='users?Type=Delete&ID="+ c.toHTML(myResultSet.getObject("USER_NO")) +"'>Delete</a></TD>";
                                
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
	String myBodyScript = myClsMain.getContentFromFile("DATA_USERS_FORM_EDIT.html");
	model_DATA_USERS myDATA_USERS=new model_DATA_USERS();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE USER_NO=" + myID;
        String myScript = "SELECT USER_NO,USER_NAME,NVL(USER_FULL_NAME,'') USER_FULL_NAME,USER_ROLE_NO,ROLE_NAME,USER_MEMB_NO,MEMB_NAME,USER_STATUS,STAT_NAME,NVL(USER_ADDRESS,'') USER_ADDRESS,NVL(USER_PHONE,'') USER_PHONE,NVL(USER_FAX,'') USER_FAX,NVL(USER_EMAIL,'') USER_EMAIL,NVL(USER_CA,'') USER_CA,NVL(USER_DS,'') USER_DS,USER_SYSUID,NVL(to_char(USER_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') USER_SYSDATE FROM DATA_USERS JOIN DATA_ROLES ON USER_ROLE_NO=ROLE_NO JOIN DATA_MEMBERS ON USER_MEMB_NO=MEMB_NO JOIN SYS_STATUS ON USER_STATUS=STAT_NO" + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
        myBodyScript=myBodyScript.replace("#myVal_USER_NO#",c.toHTML(myResultSet.getObject("USER_NO")));
	myBodyScript=myBodyScript.replace("#myVal_USER_NAME#",c.toHTML(myResultSet.getObject("USER_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_USER_FULL_NAME#",c.toHTML(myResultSet.getObject("USER_FULL_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_USER_ROLE_NO#",myCls.getSelectOptoinValue("ROLE_NO","ROLE_NAME","DATA_ROLES",c.toHTML(myResultSet.getObject("USER_ROLE_NO"))));
	myBodyScript=myBodyScript.replace("#myVal_USER_MEMB_NO#",myCls.getSelectOptoinValue("MEMB_NO","MEMB_NAME","DATA_MEMBERS",c.toHTML(myResultSet.getObject("USER_MEMB_NO"))));
	myBodyScript=myBodyScript.replace("#myVal_USER_STATUS#",myCls.getSelectOptoinValue("STAT_NO","STAT_NAME","SYS_STATUS",c.toHTML(myResultSet.getObject("USER_STATUS"))));
	myBodyScript=myBodyScript.replace("#myVal_USER_ADDRESS#",c.toHTML(myResultSet.getObject("USER_ADDRESS")));
	myBodyScript=myBodyScript.replace("#myVal_USER_PHONE#",c.toHTML(myResultSet.getObject("USER_PHONE")));
	myBodyScript=myBodyScript.replace("#myVal_USER_FAX#",c.toHTML(myResultSet.getObject("USER_FAX")));
	myBodyScript=myBodyScript.replace("#myVal_USER_EMAIL#",c.toHTML(myResultSet.getObject("USER_EMAIL")));
        myBodyScript=myBodyScript.replace("#myVal_USER_CA#",c.toHTML(myResultSet.getObject("USER_CA")));
	myBodyScript=myBodyScript.replace("#myVal_USER_DS#",c.toHTML(myResultSet.getObject("USER_DS")));
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
	model_DATA_USERS myDATA_USERS=new model_DATA_USERS();
	String myUSER_NO= myClsMain.myRequest.getParameter("myBox_USER_NO");
	String myUSER_NAME= myClsMain.myRequest.getParameter("myBox_USER_NAME");
	//String myUSER_PWD= myClsMain.myRequest.getParameter("myBox_USER_PWD");
	String myUSER_FULL_NAME= myClsMain.myRequest.getParameter("myBox_USER_FULL_NAME");
	String myUSER_ROLE_NO= myClsMain.myRequest.getParameter("myBox_USER_ROLE_NO");
	String myUSER_MEMB_NO= myClsMain.myRequest.getParameter("myBox_USER_MEMB_NO");
	String myUSER_STATUS= myClsMain.myRequest.getParameter("myBox_USER_STATUS");
	String myUSER_ADDRESS= myClsMain.myRequest.getParameter("myBox_USER_ADDRESS");
	String myUSER_PHONE= myClsMain.myRequest.getParameter("myBox_USER_PHONE");
	String myUSER_FAX= myClsMain.myRequest.getParameter("myBox_USER_FAX");
	String myUSER_EMAIL= myClsMain.myRequest.getParameter("myBox_USER_EMAIL");
        String myUSER_CA=myClsMain.myRequest.getParameter("myBox_USER_CA");
        String myUSER_DS=myClsMain.myRequest.getParameter("myBox_USER_DS");
        
	myDATA_USERS.setUSER_NO(myUSER_NO);
	myDATA_USERS.setUSER_NAME(myUSER_NAME);
	//myDATA_USERS.setUSER_PWD(myUSER_PWD);
	myDATA_USERS.setUSER_FULL_NAME(myUSER_FULL_NAME);
	myDATA_USERS.setUSER_ROLE_NO(myUSER_ROLE_NO);
	myDATA_USERS.setUSER_MEMB_NO(myUSER_MEMB_NO);
	myDATA_USERS.setUSER_STATUS(myUSER_STATUS);
	myDATA_USERS.setUSER_ADDRESS(myUSER_ADDRESS);
	myDATA_USERS.setUSER_PHONE(myUSER_PHONE);
        myDATA_USERS.setUSER_FAX(myUSER_FAX);
	myDATA_USERS.setUSER_EMAIL(myUSER_EMAIL);
 	myDATA_USERS.USER_CA=myUSER_CA;
	myDATA_USERS.USER_DS=myUSER_DS;
        
	myDATA_USERS.setUSER_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_USERS.getErrorToString();        
	if (myDATA_USERS.isERROR==false){
		String myScriptUpdate =myDATA_USERS.getUpdateScript();
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
