package myControllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myClasses.*;
import myModels.*;

@WebServlet(name = "controller_data_enquiry_auth", urlPatterns = {"/enquiry_auth"})
public class controller_data_enquiry_auth extends HttpServlet {
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"myMainTemplateAdmin.html");
        clsDataBase myCls=new clsDataBase();

	if(myClsMain.CheckPermission("11")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        String  myType= myClsMain.myRequest.getParameter("Type");
        String   myReportType = myClsMain.myRequest.getParameter("ReportType");
        if (myReportType == null) {
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        if (myType==null){
		LoadNewPage(myClsMain,myCls);            
        }else if(myType.equals("Submit")){
		myBtSubmit_Click(myClsMain,myCls);
        }else{
		LoadNewPage(myClsMain,myCls);   
	}    
    }
    
private void LoadNewPage(clsMain myClsMain,clsDataBase myCls){
	String myBodyScript = myClsMain.getContentFromFile("DATA_ENQUIRY_FORM_AUTH.html");
	myBodyScript=myBodyScript.replace("#myVal_NAME#","");
	myBodyScript=myBodyScript.replace("#myVal_POSITION#","");
	myBodyScript=myBodyScript.replace("#myVal_EMAIL_ADDRESS#","");
	myBodyScript=myBodyScript.replace("#myVal_MOBILE_PHONE#","");
	myBodyScript=myBodyScript.replace("#myVal_ENQUIRY_SUBJECT#",myCls.getSelectOptoinValue("ENTY_NO","ENTY_NAME","DATA_ENQUIRYTYPE","1"));
        myBodyScript=myBodyScript.replace("#myVal_CONTACT#",myCls.getSelectOptoinValue("COBY_NO","COBY_NAME","DATA_CONTACTBYTYPE","1"));
        myBodyScript=myBodyScript.replace("#myVal_ENQUIRY#","");
        try { 
            myClsMain.ShowPageContain(myBodyScript);
        } 
        catch (IOException ex) { 
            Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
private void myBtSubmit_Click(clsMain myClsMain,clsDataBase myCls){
        model_DATA_ENQUIRY myDATA_ENQUIRY=new model_DATA_ENQUIRY();
	String myENQ_NAME= myClsMain.myRequest.getParameter("myBox_NAME");
	String myENQ_POSITION= myClsMain.myRequest.getParameter("myBox_POSITION");
	String myENQ_EMAIL_ADDRESS= myClsMain.myRequest.getParameter("myBox_EMAIL_ADDRESS");
	String myENQ_MOBILE_PHONE= myClsMain.myRequest.getParameter("myBox_MOBILE_PHONE");
	String myENQ_ENQUIRY_SUBJECT= myClsMain.myRequest.getParameter("myBox_ENQUIRY_SUBJECT");
	String myENQ_CONTACT= myClsMain.myRequest.getParameter("myBox_CONTACT");
        String myENQ_ENQUIRY= myClsMain.myRequest.getParameter("myBox_ENQUIRY");     
	myDATA_ENQUIRY.setENQY_CONTACTNAME(myENQ_NAME);
        myDATA_ENQUIRY.setENQY_CONTACTPOSITION(myENQ_POSITION);
        myDATA_ENQUIRY.setENQY_EMAIL(myENQ_EMAIL_ADDRESS);
        myDATA_ENQUIRY.setENQY_PHONE(myENQ_MOBILE_PHONE);
        myDATA_ENQUIRY.setENQY_ENQUIRYTYPEID(myENQ_ENQUIRY_SUBJECT);
        myDATA_ENQUIRY.setENQY_CONTACTBYID(myENQ_CONTACT);
        myDATA_ENQUIRY.setENQY_ENQURYDETAIL(myENQ_ENQUIRY);
        myDATA_ENQUIRY.setENQY_USERID(myClsMain.myDATA_USERS.getUSER_NO());
        //////////
        myDATA_ENQUIRY.setENQY_MEMBERID(myClsMain.myDATA_USERS.getUSER_MEMB_NO());
        //////////
        myDATA_ENQUIRY.setENQY_ENQUIRYDATE("sysdate");
	String myERR =  myDATA_ENQUIRY.getErrorToString();        
	if (myDATA_ENQUIRY.isERROR==false){
		String myScriptInsert =myDATA_ENQUIRY.getInsertScript();
		myCls.ExecuteNonQuery(myScriptInsert); 
		if(myCls.isERROR()==true) {
			myERR="{\"myERR\":\"TRUE\",\"Message\":\""+ myCls.getERROR_Message().replace("\n", "") +".\"}";
            	}else{
                    myERR="{\"myERR\":\"FALSE\",\"Message\":\""+ "Successfully saved" +".\"}";
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
