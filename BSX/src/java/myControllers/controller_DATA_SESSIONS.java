package myControllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet(name = "controller_DATA_SESSIONS", urlPatterns = {"/sessions"})
public class controller_DATA_SESSIONS extends HttpServlet {
    String myReportType ="0";
    String myReportName="";
    String myMainTemplate="myTemplateNBCAdmin.html";
    String myFormTemplate="DATA_SESSIONS_FORM.html";
    String myFormSearchTemplate="DATA_SESSIONS_FORM_SEARCH.html";
    String myFormEditTemplate="DATA_SESSIONS_FORM_EDIT.html";
    String mySubManuTemplate="DATA_SESSION_MANU.html";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"");
        clsDataBase myCls=new clsDataBase();
        
       
	if(myClsMain.CheckPermission("1","31")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        
        if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("31")){
            myMainTemplate="myTemplateNBCAdmin_S.html";
            mySubManuTemplate="DATA_SESSION_MANU_S.html";
        }else{
            myMainTemplate="myTemplateNBCAdmin.html";
            mySubManuTemplate="DATA_SESSION_MANU.html";        
        }
        myClsMain.myMainTemplate=myMainTemplate;
        
        myReportType=myClsMain.myRequest.getParameter("ReportType");
        String  myType= myClsMain.myRequest.getParameter("Type");
        
        if (myReportType==null){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        
        myReportName=myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();
        
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
        }else if(myType.equals("GetName")){
		getSessionName(myClsMain,myCls);
        }else{
		LoadNewPage(myClsMain,myCls);   
	}    
    }

private void getSessionName (clsMain myClsMain,clsDataBase myCls){
        clsConverter c=new clsConverter();
        String mySERE_SDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
	String mySERE_EDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_EDATETIME");
//	String mySERE_OUP= myClsMain.myRequest.getParameter("myBox_SERE_OUP");
//	String mySERE_CUP= myClsMain.myRequest.getParameter("myBox_SERE_CUP");

        Date myDS =c.StringToDate(mySERE_SDATETIME);
        Date myDE =c.StringToDate(mySERE_EDATETIME);
        
        String myS = c.DateTimeFormat(myDS, "yyyy/MM/dd");
        String myE = c.DateTimeFormat(myDE, "yyyy/MM/dd");
        
        String myNOS = c.DateTimeFormat(myDS, "yyyy_MM_dd");
        String myNOE = c.DateTimeFormat(myDE, "yyyy_MM_dd");
        
        String myNR=myS + " To " + myE;
        String myNOR=myNOS + "_"+ myReportType +"_" + myNOE;
        
        String myResult="";
        myResult="{\"myERR\":\"FALSE\",\"NO\":\""+ myNOR +"\",\"NAME\":\""+ myNR +"\"}";
        myClsMain.PrintOut(myResult); 
}
    
private void LoadNewPage(clsMain myClsMain,clsDataBase myCls){
        
        clsConverter c=new clsConverter();
	String myBodyScript = myClsMain.getContentFromFile(myFormTemplate);
        String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
        
        myBodyScript=myBodyScript.replace("#SESSION_NAME#",myReportName);
        
        String mySelectScript ="SELECT SESS_DURA_NO,SESS_NO,SESS_NAME,NVL(to_char(SESS_RF, 'yyyy/mm/dd'),'') SESS_RF,NVL(to_char(SESS_RT, 'yyyy/mm/dd'),'') SESS_RT,NVL(to_char(SESS_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_OUP,NVL(to_char(SESS_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_CUP,SESS_SYSUID,NVL(to_char(SESS_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_SYSDATE FROM DATA_SESSION WHERE SESS_DURA_NO=" + myReportType;
        ResultSet myResultSet = myCls.ExecuteReader(mySelectScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_SERE_NO_CUR#",c.toHTML(myResultSet.getObject("SESS_NO")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_NAME_CUR#",c.toHTML(myResultSet.getObject("SESS_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_DURA_NO_CUR#",myReportName);
	myBodyScript=myBodyScript.replace("#myVal_SERE_SDATETIME_CUR#",c.toHTML(myResultSet.getObject("SESS_RF")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_EDATETIME_CUR#",c.toHTML(myResultSet.getObject("SESS_RT")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_OUP_CUR#",c.toHTML(myResultSet.getObject("SESS_OUP")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_CUP_CUR#",c.toHTML(myResultSet.getObject("SESS_CUP")));
	}
	} 
	catch (SQLException ex) { 
	myClsMain.PrintOut(ex.getMessage()); 
	}
        
	myBodyScript=myBodyScript.replace("#myVal_SERE_NO#","");
	myBodyScript=myBodyScript.replace("#myVal_SERE_NAME#","");
	myBodyScript=myBodyScript.replace("#myVal_SERE_DURA_NO#",myCls.getSelectOptoinValue("DURA_NO","DURA_NAME","DATA_DURATION",myReportType));
	myBodyScript=myBodyScript.replace("#myVal_SERE_SDATETIME#","");
	myBodyScript=myBodyScript.replace("#myVal_SERE_EDATETIME#","");
	myBodyScript=myBodyScript.replace("#myVal_SERE_OUP#","");
	myBodyScript=myBodyScript.replace("#myVal_SERE_CUP#","");
        
                
        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSave_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_SESSIONS myDATA_SESSIONS=new model_DATA_SESSIONS();
	String mySERE_NO= myClsMain.myRequest.getParameter("myBox_SERE_NO");
	String mySERE_NAME= myClsMain.myRequest.getParameter("myBox_SERE_NAME");
	String mySERE_DURA_NO= myClsMain.myRequest.getParameter("myBox_SERE_DURA_NO");
	String mySERE_SDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
	String mySERE_EDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_EDATETIME");
	String mySERE_OUP= myClsMain.myRequest.getParameter("myBox_SERE_OUP");
	String mySERE_CUP= myClsMain.myRequest.getParameter("myBox_SERE_CUP");

	myDATA_SESSIONS.setSERE_NO(mySERE_NO);
	myDATA_SESSIONS.setSERE_NAME(mySERE_NAME);
	myDATA_SESSIONS.setSERE_DURA_NO(mySERE_DURA_NO);
	myDATA_SESSIONS.setSERE_SDATETIME(mySERE_SDATETIME);
	myDATA_SESSIONS.setSERE_EDATETIME(mySERE_EDATETIME);
	myDATA_SESSIONS.setSERE_OUP(mySERE_OUP);
	myDATA_SESSIONS.setSERE_CUP(mySERE_CUP);
 	
	myDATA_SESSIONS.setSERE_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_SESSIONS.getErrorToString();        
	if (myDATA_SESSIONS.isERROR==false){
            model_DATA_SESSION myDATA_SESSION=new model_DATA_SESSION();
            myDATA_SESSION.setSESS_NO(mySERE_NO);
            myDATA_SESSION.setSESS_NAME(mySERE_NAME);
            myDATA_SESSION.setSESS_DURA_NO(mySERE_DURA_NO);
            myDATA_SESSION.setSESS_RF(mySERE_SDATETIME);
            myDATA_SESSION.setSESS_RT(mySERE_EDATETIME);
            myDATA_SESSION.setSESS_OUP(mySERE_OUP);
            myDATA_SESSION.setSESS_CUP(mySERE_CUP);
            myDATA_SESSION.setSESS_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
            
            String Script1=myDATA_SESSIONS.getInsertScript();
            String Script2=myDATA_SESSION.getUpdateScript();
            
            myCls.ExecuteNonQuery(Script1,Script2); 
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
        String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate);
        String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
        
        String myScript= "SELECT SERE_NO,SERE_NAME,SERE_DURA_NO, DURA_NAME,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP FROM DATA_SESSIONS JOIN DATA_DURATION ON SERE_DURA_NO=DURA_NO";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>No</TD>";
	myHTMLTABLE +="<TD align='center'>Name</TD>";
	myHTMLTABLE +="<TD align='center'>Report Type</TD>";
	myHTMLTABLE +="<TD align='center'>Start Date</TD>";
	myHTMLTABLE +="<TD align='center'>End Date</TD>";
	myHTMLTABLE +="<TD align='center'>Open upload</TD>";
	myHTMLTABLE +="<TD align='center'>Close upload</TD>";
	myHTMLTABLE +="<TD align='center'>EDIT</TD>";
	myHTMLTABLE +="<TD align='center'>DELETE</TD>";
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_NO")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_NAME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("DURA_NAME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_SDATETIME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_EDATETIME")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_OUP")) + "</TD>";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("SERE_CUP")) + "</TD>";
	myHTMLTABLE +="<TD align='center'><a href='sessions?Type=Edit&ID="+ c.toHTML(myResultSet.getObject("SERE_NO")) +"'>Edit</a></TD>";
	myHTMLTABLE +="<TD align='center'><a href='sessions?Type=Delete&ID="+ c.toHTML(myResultSet.getObject("SERE_NO")) +"'>Delete</a></TD>";

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
	String myBodyScript = myClsMain.getContentFromFile(myFormEditTemplate);
        String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
        
	myBodyScript=myBodyScript.replace("#SESSION_NAME#",myReportName);
        
        String mySelectScript ="SELECT SESS_DURA_NO,SESS_NO,SESS_NAME,NVL(to_char(SESS_RF, 'yyyy/mm/dd'),'') SESS_RF,NVL(to_char(SESS_RT, 'yyyy/mm/dd'),'') SESS_RT,NVL(to_char(SESS_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_OUP,NVL(to_char(SESS_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_CUP,SESS_SYSUID,NVL(to_char(SESS_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_SYSDATE FROM DATA_SESSION WHERE SESS_DURA_NO=" + myReportType;
        ResultSet myResultSet = myCls.ExecuteReader(mySelectScript);
        String mySessionNO="";
	try {
	while(myResultSet.next()){
        mySessionNO=c.toHTML(myResultSet.getObject("SESS_NO"));
	myBodyScript=myBodyScript.replace("#myVal_SERE_NO_CUR#",mySessionNO);
	myBodyScript=myBodyScript.replace("#myVal_SERE_NAME_CUR#",c.toHTML(myResultSet.getObject("SESS_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_DURA_NO_CUR#",myReportName);
	myBodyScript=myBodyScript.replace("#myVal_SERE_SDATETIME_CUR#",c.toHTML(myResultSet.getObject("SESS_RF")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_EDATETIME_CUR#",c.toHTML(myResultSet.getObject("SESS_RT")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_OUP_CUR#",c.toHTML(myResultSet.getObject("SESS_OUP")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_CUP_CUR#",c.toHTML(myResultSet.getObject("SESS_CUP")));
	}
        myResultSet.close();
	} 
	catch (SQLException ex) { 
	myClsMain.PrintOut(ex.getMessage()); 
	}
        
        model_DATA_SESSIONS myDATA_SESSIONS=new model_DATA_SESSIONS();
	String myCondition=" WHERE SERE_NO='" + mySessionNO + "'";
	String myScript = myDATA_SESSIONS.getSelectScript() + myCondition;
        
	myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_SERE_NO#",c.toHTML(myResultSet.getObject("SERE_NO")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_NAME#",c.toHTML(myResultSet.getObject("SERE_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_DURA_NO#",myCls.getSelectOptoinValue("DURA_NO","DURA_NAME","DATA_DURATION",c.toHTML(myResultSet.getObject("SERE_DURA_NO"))));
	myBodyScript=myBodyScript.replace("#myVal_SERE_SDATETIME#",c.toHTML(myResultSet.getObject("SERE_SDATETIME")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_EDATETIME#",c.toHTML(myResultSet.getObject("SERE_EDATETIME")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_OUP#",c.toHTML(myResultSet.getObject("SERE_OUP")));
	myBodyScript=myBodyScript.replace("#myVal_SERE_CUP#",c.toHTML(myResultSet.getObject("SERE_CUP")));
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
	model_DATA_SESSIONS myDATA_SESSIONS=new model_DATA_SESSIONS();
	String mySERE_NO= myClsMain.myRequest.getParameter("myBox_SERE_NO");
	String mySERE_NAME= myClsMain.myRequest.getParameter("myBox_SERE_NAME");
	String mySERE_DURA_NO= myClsMain.myRequest.getParameter("myBox_SERE_DURA_NO");
	String mySERE_SDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_SDATETIME");
	String mySERE_EDATETIME= myClsMain.myRequest.getParameter("myBox_SERE_EDATETIME");
	String mySERE_OUP= myClsMain.myRequest.getParameter("myBox_SERE_OUP");
	String mySERE_CUP= myClsMain.myRequest.getParameter("myBox_SERE_CUP");

	myDATA_SESSIONS.setSERE_NO(mySERE_NO);
	myDATA_SESSIONS.setSERE_NAME(mySERE_NAME);
	myDATA_SESSIONS.setSERE_DURA_NO(mySERE_DURA_NO);
	myDATA_SESSIONS.setSERE_SDATETIME(mySERE_SDATETIME);
	myDATA_SESSIONS.setSERE_EDATETIME(mySERE_EDATETIME);
	myDATA_SESSIONS.setSERE_OUP(mySERE_OUP);
	myDATA_SESSIONS.setSERE_CUP(mySERE_CUP);
 	
	myDATA_SESSIONS.setSERE_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_SESSIONS.getErrorToString();        
	if (myDATA_SESSIONS.isERROR==false){
                model_DATA_SESSION myDATA_SESSION=new model_DATA_SESSION();
                myDATA_SESSION.setSESS_NO(mySERE_NO);
                myDATA_SESSION.setSESS_NAME(mySERE_NAME);
                myDATA_SESSION.setSESS_DURA_NO(mySERE_DURA_NO);
                myDATA_SESSION.setSESS_RF(mySERE_SDATETIME);
                myDATA_SESSION.setSESS_RT(mySERE_EDATETIME);
                myDATA_SESSION.setSESS_OUP(mySERE_OUP);
                myDATA_SESSION.setSESS_CUP(mySERE_CUP);
                myDATA_SESSION.setSESS_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());

                String Script1=myDATA_SESSIONS.getUpdateScript();
                String Script2=myDATA_SESSION.getUpdateScript();
                            
                myCls.ExecuteNonQuery(Script1,Script2);
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
