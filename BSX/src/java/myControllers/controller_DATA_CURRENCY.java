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

@WebServlet(name = "controller_DATA_CURRENCY", urlPatterns = {"/currency"})
public class controller_DATA_CURRENCY extends HttpServlet {
        
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
		SearchForm(myClsMain,myCls);          
        }else if(myType.equals("Save")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Search")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Edit")){
		LoadEditPage(myClsMain,myCls);
        }else if(myType.equals("Update")){
		myBtUpdate_Click(myClsMain,myCls);
        }else{
		SearchForm(myClsMain,myCls);
	}    
    }
    
private void LoadNewPage(clsMain myClsMain,clsDataBase myCls){
	String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCY_FORM.html");
	myBodyScript=myBodyScript.replace("#myVal_CURR_NO#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_KNAME#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_ENAME#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_UNIT#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_BID#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_ASK#","");
	myBodyScript=myBodyScript.replace("#myVal_CURR_AVERAGE#","");

        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSave_Click(clsMain myClsMain,clsDataBase myCls){
	model_DATA_CURRENCY myDATA_CURRENCY=new model_DATA_CURRENCY();
	String myCURR_NO= myClsMain.myRequest.getParameter("myBox_CURR_NO");
	String myCURR_KNAME= myClsMain.myRequest.getParameter("myBox_CURR_KNAME");
	String myCURR_ENAME= myClsMain.myRequest.getParameter("myBox_CURR_ENAME");
	String myCURR_UNIT= myClsMain.myRequest.getParameter("myBox_CURR_UNIT");
	String myCURR_BID= myClsMain.myRequest.getParameter("myBox_CURR_BID");
	String myCURR_ASK= myClsMain.myRequest.getParameter("myBox_CURR_ASK");
	String myCURR_AVERAGE= myClsMain.myRequest.getParameter("myBox_CURR_AVERAGE");

	myDATA_CURRENCY.setCURR_NO(myCURR_NO);
	myDATA_CURRENCY.setCURR_KNAME(myCURR_KNAME);
	myDATA_CURRENCY.setCURR_ENAME(myCURR_ENAME);
	myDATA_CURRENCY.setCURR_UNIT(myCURR_UNIT);
	myDATA_CURRENCY.setCURR_BID(myCURR_BID);
	myDATA_CURRENCY.setCURR_ASK(myCURR_ASK);
	myDATA_CURRENCY.setCURR_AVERAGE(myCURR_AVERAGE);
 	
	myDATA_CURRENCY.setCURR_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_CURRENCY.getErrorToString();        
	if (myDATA_CURRENCY.isERROR==false){
		String myScriptInsert =myDATA_CURRENCY.getInsertScript();
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
        String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCY_FORM_SEARCH.html");
        String myScript= "SELECT CURR_NO,CURR_KNAME,CURR_ENAME,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE," + c.seOracleDateTime("CURR_SYSDATE") + " FROM DATA_CURRENCY";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>NO</TD>";
	myHTMLTABLE +="<TD align='center'>Khmer Name</TD>";
	myHTMLTABLE +="<TD align='center'>English Name</TD>";
	myHTMLTABLE +="<TD align='center'>Unit</TD>";
	myHTMLTABLE +="<TD align='center'>Bid</TD>";
	myHTMLTABLE +="<TD align='center'>Ask</TD>";
	myHTMLTABLE +="<TD align='center'>Average</TD>";
	myHTMLTABLE +="<TD align='center'>EDIT</TD>";
	//myHTMLTABLE +="<TD align='center'>DELETE</TD>";
        String myDateTimeUpdate="";                                    
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
                if (myDateTimeUpdate.equals("")){
                    myDateTimeUpdate=c.toHTML(myResultSet.getObject("CURR_SYSDATE")) ;
                }
            	myHTMLTABLE += "<TR>\n";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_NO")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("CURR_KNAME")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("CURR_ENAME")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_UNIT")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_BID")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_ASK")) + "</TD>";
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_AVERAGE")) + "</TD>";
                myHTMLTABLE +="<TD align='center'><a href='currency?Type=Edit&ID="+ c.toHTML(myResultSet.getObject("CURR_NO")) +"'>Edit</a></TD>";
                //myHTMLTABLE +="<TD align='center'><a href='currency?Type=Delete&ID="+ c.toHTML(myResultSet.getObject("CURR_NO")) +"'>Delete</a></TD>";

            	myHTMLTABLE += "</TR>\n";            
            }
		myHTMLTABLE +="</TABLE>";
                myBodyScript=myBodyScript.replace("#Update_Date#", myDateTimeUpdate);
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
	String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCY_FORM_EDIT.html");
	model_DATA_CURRENCY myDATA_CURRENCY=new model_DATA_CURRENCY();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE CURR_NO=" + myID;
	String myScript = myDATA_CURRENCY.getSelectScript() + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_CURR_NO#",c.toHTML(myResultSet.getObject("CURR_NO")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_KNAME#",c.toHTML(myResultSet.getObject("CURR_KNAME")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_ENAME#",c.toHTML(myResultSet.getObject("CURR_ENAME")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_UNIT#",c.toHTML(myResultSet.getObject("CURR_UNIT")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_BID#",c.toHTML(myResultSet.getObject("CURR_BID")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_ASK#",c.toHTML(myResultSet.getObject("CURR_ASK")));
	myBodyScript=myBodyScript.replace("#myVal_CURR_AVERAGE#",c.toHTML(myResultSet.getObject("CURR_AVERAGE")));

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
	model_DATA_CURRENCY myDATA_CURRENCY=new model_DATA_CURRENCY();
	String myCURR_NO= myClsMain.myRequest.getParameter("myBox_CURR_NO");
	String myCURR_KNAME= myClsMain.myRequest.getParameter("myBox_CURR_KNAME");
	String myCURR_ENAME= myClsMain.myRequest.getParameter("myBox_CURR_ENAME");
	String myCURR_UNIT= myClsMain.myRequest.getParameter("myBox_CURR_UNIT");
	String myCURR_BID= myClsMain.myRequest.getParameter("myBox_CURR_BID");
	String myCURR_ASK= myClsMain.myRequest.getParameter("myBox_CURR_ASK");
	String myCURR_AVERAGE= myClsMain.myRequest.getParameter("myBox_CURR_AVERAGE");

	myDATA_CURRENCY.setCURR_NO(myCURR_NO);
	myDATA_CURRENCY.setCURR_KNAME(myCURR_KNAME);
	myDATA_CURRENCY.setCURR_ENAME(myCURR_ENAME);
	myDATA_CURRENCY.setCURR_UNIT(myCURR_UNIT);
	myDATA_CURRENCY.setCURR_BID(myCURR_BID);
	myDATA_CURRENCY.setCURR_ASK(myCURR_ASK);
	myDATA_CURRENCY.setCURR_AVERAGE(myCURR_AVERAGE);
 	
	myDATA_CURRENCY.setCURR_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
	String myERR =  myDATA_CURRENCY.getErrorToString();        
	if (myDATA_CURRENCY.isERROR==false){
		String myScriptUpdate =myDATA_CURRENCY.getUpdateScript();
		myCls.ExecuteNonQuery(myScriptUpdate,"UPDATE DATA_CURRENCY SET CURR_SYSDATE=sysdate"); 
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
