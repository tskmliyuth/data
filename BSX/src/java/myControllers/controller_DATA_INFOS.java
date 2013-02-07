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

@WebServlet(name = "controller_DATA_INFOS", urlPatterns = {"/infos"})
public class controller_DATA_INFOS extends HttpServlet {
        
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
        String myInfo =myCls.ExecuteScalar("SELECT INFO_MESSAGE FROM DATA_INFO WHERE INFO_RE_NO=1").toString();
	String myBodyScript = myClsMain.getContentFromFile("DATA_INFOS_FORM.html");
        myBodyScript=myBodyScript.replace("#myVal_INHI_MESSAGE_CUR#",myInfo);
	myBodyScript=myBodyScript.replace("#myVal_INHI_MESSAGE#","");
        
        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSave_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_INFOS myDATA_INFOS=new model_DATA_INFOS();
	String myINHI_MESSAGE= myClsMain.myRequest.getParameter("myBox_INHI_MESSAGE");

	myDATA_INFOS.setINHI_MESSAGE(myINHI_MESSAGE);
 	
	myDATA_INFOS.setINHI_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_INFOS.getErrorToString();        
	if (myDATA_INFOS.isERROR==false){
		model_DATA_INFO myDATA_INFO=new model_DATA_INFO();
                myDATA_INFO.setINFO_MESSAGE(myINHI_MESSAGE);
                               
		myCls.ExecuteNonQuery(myDATA_INFOS.getInsertScript(),myDATA_INFO.getUpdateScript());                
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
        String myBodyScript = myClsMain.getContentFromFile("DATA_INFOS_FORM_SEARCH.html");
        String myScript= "SELECT INHI_NO,INHI_MESSAGE FROM DATA_INFOS WHERE ROWNUM<=20";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>MESSAGE</TD>";
	myHTMLTABLE +="<TD align='center'>EDIT</TD>";
	//myHTMLTABLE +="<TD align='center'>DELETE</TD>";
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
	myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("INHI_MESSAGE")) + "</TD>";
	myHTMLTABLE +="<TD align='center'><a href='infos?Type=Edit&ID="+ c.toHTML(myResultSet.getObject("INHI_NO")) +"'>Edit</a></TD>";
	//myHTMLTABLE +="<TD align='center'><a href='infos?Type=Delete&ID="+ c.toHTML(myResultSet.getObject("INHI_NO")) +"'>Delete</a></TD>";

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
        String myInfo =myCls.ExecuteScalar("SELECT INFO_MESSAGE FROM DATA_INFO WHERE INFO_RE_NO=1").toString();
	String myBodyScript = myClsMain.getContentFromFile("DATA_INFOS_FORM_EDIT.html");
        myBodyScript=myBodyScript.replace("#myVal_INHI_MESSAGE_CUR#",myInfo);
        
	model_DATA_INFOS myDATA_INFOS=new model_DATA_INFOS();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE INHI_NO=" + myID;
	String myScript = myDATA_INFOS.getSelectScript() + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_INHI_NO#",c.toHTML(myResultSet.getObject("INHI_NO")));
	myBodyScript=myBodyScript.replace("#myVal_INHI_MESSAGE#",c.toHTML(myResultSet.getObject("INHI_MESSAGE")));

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
	model_DATA_INFOS myDATA_INFOS=new model_DATA_INFOS();
	String myINHI_NO= myClsMain.myRequest.getParameter("myBox_INHI_NO");
	String myINHI_MESSAGE= myClsMain.myRequest.getParameter("myBox_INHI_MESSAGE");

	myDATA_INFOS.setINHI_NO(myINHI_NO);
	myDATA_INFOS.setINHI_MESSAGE(myINHI_MESSAGE);
 	
	myDATA_INFOS.setINHI_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_INFOS.getErrorToString();        
	if (myDATA_INFOS.isERROR==false){
                model_DATA_INFO myDATA_INFO=new model_DATA_INFO();
                myDATA_INFO.setINFO_MESSAGE(myINHI_MESSAGE);
                myDATA_INFO.setINFO_NO(myINHI_NO);
                               
		myCls.ExecuteNonQuery(myDATA_INFOS.getUpdateScript(),myDATA_INFO.getUpdateScript_For_Edit()); 
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
