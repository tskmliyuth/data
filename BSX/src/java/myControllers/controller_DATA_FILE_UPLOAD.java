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

@WebServlet(name = "controller_DATA_FILE_UPLOAD", urlPatterns = {"/inputreport"})
public class controller_DATA_FILE_UPLOAD extends HttpServlet {
    String myReportType ="0";
    String myReportName="";
    String myStatus ="";
    
   
    String myMainTemplate="myTemplateNBCUser.html";
    //String myFormTemplate="DATA_SESSIONS_FORM.html";
    String myFormSearchTemplate="DATA_FILE_UPLOAD_FORM_SEARCH.html";
    String myFormEditTemplate="DATA_FILE_UPLOAD_FORM_EDIT.html";
    String mySubManuTemplate="DATA_FILE_UPLOAD_MANU_U.html";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"");
        clsDataBase myCls=new clsDataBase();

	if(myClsMain.CheckPermission("2","32","22")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("32")){
            myMainTemplate="myTemplateNBCUser_S.html";
            mySubManuTemplate="DATA_FILE_UPLOAD_MANU_S_U.html";
        }else{
            myMainTemplate="myTemplateNBCUser.html";
            mySubManuTemplate="DATA_FILE_UPLOAD_MANU_U.html";
        }
        myClsMain.myMainTemplate=myMainTemplate;
        
        myReportType=myClsMain.myRequest.getParameter("ReportType");
        
        if (myReportType==null){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }
        myReportName=myCls.ExecuteScalar("SELECT DURA_NAME FROM DATA_DURATION WHERE DURA_NO=" + myReportType).toString();
            
        
        String  myType= myClsMain.myRequest.getParameter("Type");
        if (myType==null){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Search")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Reject")){
		LoadEditPage(myClsMain,myCls);
        }else if(myType.equals("Download")){
		Download(myClsMain,myCls);
        }else if (myType.equals("Update")){
            myBtUpdate_Click(myClsMain,myCls);
        }else if (myType.equals("GSN")){
                GSN(myClsMain,myCls);
        }
    }
    
  private void GSN(clsMain myClsMain,clsDataBase myCls) {
        
        try{
        String myID= myClsMain.myRequest.getParameter("term");
        String myScriptSelect="SELECT SERE_NAME FROM DATA_SESSIONS WHERE ROWNUM<=15 AND SERE_DURA_NO=" + myReportType + " AND SERE_NAME LIKE '%"+ myID +"%'";
        
        ResultSet myResultSet=myCls.ExecuteReader(myScriptSelect);
        String s = "";
        while(myResultSet.next()){
            s += ",{\"value\":"+"\""+myResultSet.getString("SERE_NAME")+"\"}";
        }
            s += "]";
            s = "[" + s.substring(1);
            myClsMain.PrintOut(s);
        } catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }
        
}    
    
  private void Download(clsMain myClsMain,clsDataBase myCls) {
       String myID= myClsMain.myRequest.getParameter("ID");
       String myScript ="SELECT FIUP_NAME FROM DATA_FILE_UPLOAD WHERE FIUP_STATUS =2 AND FIUP_NO=" + myID;
       String myFileName=myCls.ExecuteScalar(myScript).toString();
       String myPart=myCls.getParameter_Value(myReportType);
       clsFileDownload myFileDownload =new clsFileDownload();
       myFileDownload.StartDowload(myClsMain.myRequest,myClsMain.myResponse, myPart + "\\" + myFileName ,myFileName);
       
   }

private void SearchForm(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
        String mySessionNo ="";
        String UserName= myClsMain.myDATA_USERS.getUSER_NAME();
        //System.out.println(UserName);
        String mySESS_NAME= myClsMain.myRequest.getParameter("myBox_FIUP_SESS_NO");
        if (mySESS_NAME!=null && !mySESS_NAME.equals("")){
            String myStr ="SELECT SERE_NO FROM DATA_SESSIONS WHERE SERE_DURA_NO=" + myReportType + " AND SERE_NAME='"+ mySESS_NAME +"'";
            Object myResult = myCls.ExecuteScalar(myStr);
            if (myResult==null){
                String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate);
                String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
                myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
                myBodyScript=myBodyScript.replace("#myVal_FIUP_SESS_NO#", ""); 
                myBodyScript=myBodyScript.replace("#REPORT_TYPE#", myReportType); 
                myBodyScript=myBodyScript.replace("#REPORT_NAME#", mySESS_NAME); 
                myBodyScript=getInformation(myClsMain,myCls,myBodyScript,"") ;
                myBodyScript=myBodyScript.replace("#DATA#", "Your session name that you were selected is invalid");  
                try {
                    myClsMain.ShowPageContain(myBodyScript);
                } catch (IOException ex) {
                    Logger.getLogger(controller_DATA_FILE_UPLOAD_ADMIN.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return ;
            }else{                
                mySessionNo=myResult.toString();
            }
        }else{
            String myR=myCls.ExecuteScalar("SELECT SESS_NO || ',' || SESS_NAME SESS_NO FROM DATA_SESSION WHERE SESS_DURA_NO=" + myReportType).toString();
            String[] myRs=myR.split(",");            
            mySessionNo =myRs[0];
            mySESS_NAME=myRs[1];            
        }     
        
        String myBodyScript = myClsMain.getContentFromFile(myFormSearchTemplate);
        String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
        myBodyScript=myBodyScript.replace("#myVal_FIUP_SESS_NO#", mySESS_NAME); 
        myBodyScript=myBodyScript.replace("#REPORT_TYPE#", myReportType); 
        myBodyScript=myBodyScript.replace("#REPORT_NAME#", myReportName); 
        myBodyScript=getInformation(myClsMain,myCls,myBodyScript,mySessionNo) ;
        //myBodyScript=myBodyScript.replace("#myVal_FIUP_DURA_NO#",myCls.getSelectOptoinValue("DURA_NO","DURA_NAME","DATA_DURATION",""));
        String myScript = null;
        String myScript2 = null;
        if(UserName.equals("csopheak")){  // CHEA SOPHEAK
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='OSK' OR MEMB_SNAME='CNB' OR MEMB_SNAME='MYB' OR MEMB_SNAME='CPB') T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("nchanthona")){  // NGOV CHANTHONA
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='BIDC' OR MEMB_SNAME='ACLEDA' OR MEMB_SNAME='VBL' OR MEMB_SNAME='ABA' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("usamnida") ){ // UNG SAMNIDA 
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND  MEMB_SNAME='CIMB' OR MEMB_SNAME='CCB' OR MEMB_SNAME='KTB' OR MEMB_SNAME='BOI' OR MEMB_SNAME='KB' OR MEMB_SNAME='HDBS' OR MEMB_SNAME='SACOM' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if( UserName.equals("msokunthea")){  //MAO SOKUNTHEA
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='CNB' OR MEMB_SNAME='CPB' OR MEMB_SNAME='MYB' OR MEMB_SNAME='OSK' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("psodalin")){ // PHEAV SODALIN
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='OSK' OR MEMB_SNAME='CNB' OR MEMB_SNAME='UCB' OR MEMB_SNAME='CPB' OR MEMB_SNAME='BIDC' OR MEMB_SNAME='ACLEDA' OR MEMB_SNAME='VBL' OR MEMB_SNAME='ANZ' OR MEMB_SNAME='FTB' OR MEMB_SNAME='ABA' OR MEMB_SNAME='MYB' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("kprachnhar")){ // KHY PRACHNHAR
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='CAB'  OR MEMB_SNAME='CMB' OR MEMB_SNAME='MJB' OR MEMB_SNAME='PCB' OR MEMB_SNAME='SBC' OR MEMB_SNAME='BKB' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("csreyroth")){ // CHAN SREYROTH 
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='BIDC' OR MEMB_SNAME='ACLEDA' OR MEMB_SNAME='VBL' OR MEMB_SNAME='ABA' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
        else if(UserName.equals("ssopheawattey")){ //  SAN SOPHEAWATTEY
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='FCB'  OR MEMB_SNAME='AGRI' OR MEMB_SNAME='SKB' OR MEMB_SNAME='MB Bank' OR MEMB_SNAME='ICBC' OR MEMB_SNAME='MEGA' OR MEMB_SNAME='BOC' OR MEMB_SNAME='SHB' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }        
        else if(UserName.equals("yvirin")){ // YOS VIRIN
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='ANZ' OR MEMB_SNAME='FTB' OR MEMB_SNAME='UCB'  ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
         else if(UserName.equals("hsadine")){ // HAY SADIN
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='RDB' OR MEMB_SNAME='TSB' OR MEMB_SNAME='FISB' OR MEMB_SNAME='ANCO' OR MEMB_SNAME='ACB' OR MEMB_SNAME='PHSME' OR MEMB_SNAME='CKB' ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }
         else if(UserName.equals("sousa")){ // SOK OUSA
        myScript= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 AND MEMB_SNAME='RDB' OR MEMB_SNAME='TSB' OR MEMB_SNAME='FISB' OR MEMB_SNAME='ANCO' OR MEMB_SNAME='ACB' OR MEMB_SNAME='PHSME' OR MEMB_SNAME='CKB' OR MEMB_SNAME='CIMB' OR MEMB_SNAME='CAB' OR MEMB_SNAME='PCB' OR MEMB_SNAME='MJB' OR MEMB_SNAME='SBC' OR MEMB_SNAME='BKB' OR MEMB_SNAME='CMB' OR MEMB_SNAME='CCB' OR MEMB_SNAME='KB' OR MEMB_SNAME='HDBS' OR MEMB_SNAME='SACOM' OR MEMB_SNAME='FCB' OR MEMB_SNAME='KTB' OR MEMB_SNAME='BOI' OR MEMB_SNAME='AGRI' OR MEMB_SNAME='SKB' OR MEMB_SNAME='MB Bank' OR MEMB_SNAME='ICBC' OR MEMB_SNAME='MEGA' OR MEMB_SNAME='BOC' OR MEMB_SNAME='SHB') T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";
        }else
        {
          myScript2= "SELECT T.*,T1.* FROM  (SELECT MEMB_NO,MEMB_SNAME FROM DATA_MEMBERS WHERE MEMB_STATUS=1 ) T LEFT JOIN (SELECT FIUP_NO,FIUP_NAME,FIUP_MEMB_NO,FIUP_SESS_NO,FIUP_DURA_NO, DURA_NAME,FIUP_STATUS, FIST_NAME,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT FROM DATA_FILE_UPLOAD JOIN DATA_DURATION ON FIUP_DURA_NO=DURA_NO JOIN DATA_FILE_STATUS ON FIUP_STATUS=FIST_NO  WHERE FIUP_SESS_NO ='"+ mySessionNo +"' AND FIUP_STATUS IN (1,2,11)) T1 ON T.MEMB_NO=T1.FIUP_MEMB_NO ";   
        }
        if(myScript == null){
        ResultSet myResultSet = myCls.ExecuteReader(myScript2);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
        myHTMLTABLE +="<TD align='center'>Member Name</TD>";
	myHTMLTABLE +="<TD align='center'>Name</TD>";
	
	myHTMLTABLE +="<TD align='center'>Duration</TD>";
	
	myHTMLTABLE +="<TD align='center'>Status</TD>";
	myHTMLTABLE +="<TD align='center'>Download</TD>";
	myHTMLTABLE +="<TD align='center'>Reject</TD>";
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_NAME")) + "</TD>";
                
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("DURA_NAME")) + "</TD>";
                if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("")){
                    if (myStatus.equals("Closed")){
                        //myHTMLTABLE +="<TD align='center'><a href='InputReportadmin?ReportType="+ myReportType +"&Type=CTO&SESS_NO="+ mySessionNo +"&MEMB_NO="+ c.toHTML(myResultSet.getObject("MEMB_NO")) +"'>"+ "Closed" +"</a></TD>";
                        myHTMLTABLE +="<TD align='center'> "+ "Closed" +"</a></TD>";
                    }else{
                        myHTMLTABLE +="<TD align='center'>" + myStatus +"</a></TD>";
                    }                    
                }else{
                    if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("11")){
                      
                        myHTMLTABLE +="<TD align='center'>" + c.toHTML(myResultSet.getObject("FIST_NAME"))  +"</a></TD>";
                    }else{
                        myHTMLTABLE +="<TD align='center'>" + c.toHTML(myResultSet.getObject("FIST_NAME")) + "</TD>";
                    }
                }
                
                if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("2")){
                myHTMLTABLE +="<TD align='center'><a href='inputreport?ReportType="+ myReportType +"&Type=Download&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) +"'>Download</a></TD>";
                myHTMLTABLE +="<TD align='center'><a href='inputreport?ReportType="+ myReportType +"&Type=Reject&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) +"'>Reject</a></TD>";
                }else{
                myHTMLTABLE +="<TD align='center'></TD>";
                myHTMLTABLE +="<TD align='center'></TD>";
                  
                }
            	myHTMLTABLE += "</TR>\n";            
            }
		myResultSet.close();
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
        
        }else{
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE += "<TR style='color:#F30 ;' height='35'>";
        myHTMLTABLE +="<TD align='center'>Member Name</TD>";
	myHTMLTABLE +="<TD align='center'>Name</TD>";
	
	myHTMLTABLE +="<TD align='center'>Duration</TD>";
	
	
	myHTMLTABLE +="<TD align='center'>Download</TD>";
	
                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            	myHTMLTABLE += "<TR>\n";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("MEMB_SNAME")) + "</TD>";
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("FIUP_NAME")) + "</TD>";
                
                myHTMLTABLE +="<TD align='left'>" + c.toHTML(myResultSet.getObject("DURA_NAME")) + "</TD>";
                
                if (c.toHTML(myResultSet.getObject("FIUP_STATUS")).equals("2")){
                myHTMLTABLE +="<TD align='center'><a href='inputreport?ReportType="+ myReportType +"&Type=Download&ID="+ c.toHTML(myResultSet.getObject("FIUP_NO")) +"'>Download</a></TD>";
               
                }else{
                myHTMLTABLE +="<TD align='center'></TD>";
              
                  
                }
            	myHTMLTABLE += "</TR>\n";            
            }
		myResultSet.close();
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
}

private String getInformation(clsMain myClsMain,clsDataBase myCls,String pBodyScript,String pSESS_NO) {
        clsConverter c = new clsConverter();
        
        if (pSESS_NO.equals("")){
            pBodyScript=pBodyScript.replace("#SESSION_NAME#", "");
            pBodyScript=pBodyScript.replace("#OUP#", "");
            pBodyScript=pBodyScript.replace("#CUP#", "");
            pBodyScript=pBodyScript.replace("#STATUS#", myStatus);
            return pBodyScript;
        }
        
        String mySession_Name="";
        String myOUP ="";
        String myCUP = "";
               
        String myScript ="SELECT SERE_NO,SERE_NAME,SERE_DURA_NO,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP,SERE_SYSUID,NVL(to_char(SERE_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_SYSDATE,to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss') SYSTEMDATETIME FROM DATA_SESSIONS ";
        myScript +=" WHERE SERE_DURA_NO=" + myReportType + " AND  SERE_NO='" + pSESS_NO + "'";
        model_DATA_SESSIONS myDATA_SESSIONS=new model_DATA_SESSIONS();
        
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        try{
            if(myResultSet.next()){
                myDATA_SESSIONS.setData(myResultSet);
                String myCurrentDate=myResultSet.getString("SYSTEMDATETIME");
                
                if (myCls.isERROR()==true){
                    myClsMain.PrintOut(myCls.getERROR_Message());
                }
                myResultSet.close();
                myCls.CloseResultSet();                
                //If session still open
                myOUP=myDATA_SESSIONS.getSERE_OUP();
                myCUP=myDATA_SESSIONS.getSERE_CUP();
                mySession_Name=myDATA_SESSIONS.getSERE_NAME();
                if (c.CompareDateTimeInBetween(myCurrentDate,myDATA_SESSIONS.getSERE_OUP(),myDATA_SESSIONS.getSERE_CUP())==0){
                         myStatus="Open"    ;       
                }else{
                         myStatus="Closed";
                         String myScriptUpdate = "UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS= 4";
                           myScriptUpdate += " WHERE FIUP_DURA_NO=" +myReportType +" AND FIUP_STATUS= 1 AND FIUP_SESS_NO='"+ pSESS_NO+ "'";
                         if(!myScriptUpdate.equals("")){  
                         myCls.ExecuteNonQuery(myScriptUpdate);
                         }
                }
            }
        }catch (SQLException ex) {
            myClsMain.PrintOut(ex.getMessage());
        }finally{
            myCls.CloseResultSet(); 
        }
        pBodyScript=pBodyScript.replace("#SESSION_NAME#", mySession_Name);
        pBodyScript=pBodyScript.replace("#OUP#", myOUP);
        pBodyScript=pBodyScript.replace("#CUP#", myCUP);
        pBodyScript=pBodyScript.replace("#STATUS#", myStatus);
        
        return pBodyScript;
        
    }


private void LoadEditPage(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
	String myBodyScript = myClsMain.getContentFromFile(myFormEditTemplate);
        String mySubManuScript=myClsMain.getContentFromFile(mySubManuTemplate);
        myBodyScript=myBodyScript.replace("#SUB_MANU#",mySubManuScript);
        myBodyScript=myBodyScript.replace("#REPORT_TYPE#", myReportType); 
        myBodyScript=myBodyScript.replace("#REPORT_NAME#", myReportName);
	model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD=new model_DATA_FILE_UPLOAD();
	String myID= myClsMain.myRequest.getParameter("ID");
	if (myID == null){
		myClsMain.PrintOut("Wrong request no data to for view");
		return;
	}
	String myCondition=" WHERE FIUP_NO=" + myID;
	String myScript = myDATA_FILE_UPLOAD.getSelectScript() + myCondition;
	ResultSet myResultSet = myCls.ExecuteReader(myScript);
	try {
	while(myResultSet.next()){
	myBodyScript=myBodyScript.replace("#myVal_FIUP_NO#",c.toHTML(myResultSet.getObject("FIUP_NO")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_NAME#",c.toHTML(myResultSet.getObject("FIUP_NAME")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_SESS_NO#",c.toHTML(myResultSet.getObject("FIUP_SESS_NO")));
	myBodyScript=myBodyScript.replace("#myVal_FIUP_DURA_NO#",myCls.getSelectOptoinValue("DURA_NO","DURA_NAME","DATA_DURATION",c.toHTML(myResultSet.getObject("FIUP_DURA_NO"))));
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
	//model_DATA_FILE_UPLOAD myDATA_FILE_UPLOAD=new model_DATA_FILE_UPLOAD();
	String myFIUP_NO= myClsMain.myRequest.getParameter("myVal_FIUP_NO");
	if (myFIUP_NO==null){
            return ;
        }
        
        String myStr ="UPDATE DATA_FILE_UPLOAD SET FIUP_STATUS=4,FIUP_AUID="+ myClsMain.myDATA_USERS.getUSER_NO() +",FIUP_ASYSDATE=sysdate WHERE FIUP_NO=" + myFIUP_NO;
        myCls.ExecuteNonQuery(myStr);
        if (myCls.isERROR()==true) {
            myClsMain.PrintOut(myCls.getERROR_Message());
        }else{
            try {
                myClsMain.myResponse.sendRedirect("inputreport?ReportType="+ myReportType +"&Type=Search");
            } catch (IOException ex) {
                myClsMain.PrintOut(ex.getMessage());
            }
        } 
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
