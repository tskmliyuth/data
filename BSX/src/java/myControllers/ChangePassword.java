
package myControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.clsConverter;
import myClasses.clsMain;
import myModels.clsDataBase;
import myModels.model_DATA_USERS;
import org.json.simple.JSONObject;


@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           clsDataBase myDb = new clsDataBase();
           clsMain myMain = new clsMain(request,response,"myMainTemplate.html"); 
               
           
           String myPWD = request.getParameter("myBox_USER_PWD");           
           String myNPWD = request.getParameter("myBox_USER_NPWD");
           String myCPWD = request.getParameter("myBox_USER_CPWD");
           model_DATA_USERS myUserInfo=null;
           myUserInfo= myMain.getPermission();
           if (myUserInfo != null){
               if (myPWD !=null){
                   ResultSet myResultSet = myDb.ExecuteReader("SELECT USER_NO,USER_NAME,USER_FULL_NAME,USER_ROLE_NO,USER_MEMB_NO,USER_STATUS,USER_ADDRESS,USER_PHONE,USER_FAX,USER_EMAIL FROM DATA_USERS WHERE USER_NO=" + myUserInfo.getUSER_NO() + " AND USER_PWD='"+ myPWD +"'");
                    try {
                        if(myResultSet.next()){
                            if (!myNPWD.equals("") && !myCPWD.equals("")){
                                if(myNPWD.equals(myCPWD)){
                                    String myScript ="UPDATE DATA_USERS SET USER_PWD='"+ myNPWD +"' WHERE USER_NO=" + myUserInfo.getUSER_NO();
                                    myDb.ExecuteNonQuery(myScript);
                                    if(myDb.isERROR()==true){
                                       myMain.PrintOut(myDb.getERROR_Message());
                                    }else{                                       
                                        clsConverter c=new clsConverter();
                                        JSONObject myJsonObject=new JSONObject();
                                        myJsonObject.put("myERR", "FALSE");
                                        myJsonObject.put("myERR_USER_PWD", "");
                                        myJsonObject.put("myERR_USER_NPWD", "");
                                        myMain.PrintOut(c.JsonToString(myJsonObject));   
                                    }
                                }else{
                                    clsConverter c=new clsConverter();
                                    JSONObject myJsonObject=new JSONObject();
                                    myJsonObject.put("myERR", "TRUE");
                                    myJsonObject.put("myERR_USER_PWD", "");
                                    myJsonObject.put("myERR_USER_NPWD", "Passwords do not match.");
                                    myMain.PrintOut(c.JsonToString(myJsonObject));                                     
                                }
                            }else{
                                clsConverter c=new clsConverter();
                                JSONObject myJsonObject=new JSONObject();
                                myJsonObject.put("myERR", "TRUE");
                                myJsonObject.put("myERR_USER_PWD", "");
                                myJsonObject.put("myERR_USER_NPWD", "Required field cannot be left blank. ");
                                myMain.PrintOut(c.JsonToString(myJsonObject));                               
                            }                   
                        }else{
                            clsConverter c=new clsConverter();
                            JSONObject myJsonObject=new JSONObject();
                            myJsonObject.put("myERR", "TRUE");
                            myJsonObject.put("myERR_USER_NPWD", "");
                            myJsonObject.put("myERR_USER_PWD", "The password you gave is incorrect.");
                            myMain.PrintOut(c.JsonToString(myJsonObject));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }else{
                   
                   if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("1")){
                            myMain.myMainTemplate="myTemplateNBCAdmin.html";
                            myMain.ShowPage("ChangePassword_NBCAdmin.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("2")){
                            myMain.myMainTemplate="myTemplateNBCUser.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("11")){
                            myMain.myMainTemplate="myMainTemplateAdmin.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("12")){
                            myMain.myMainTemplate="myMainTemplate.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("31")){
                            myMain.myMainTemplate="myTemplateNBCAdmin_S.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("32")){
                            myMain.myMainTemplate="myTemplateNBCUser_S.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("21")){
                            myMain.myMainTemplate="myMainTemplate_Onside_Admin.html";
                            myMain.ShowPage("ChangePassword.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("22")){
                            myMain.myMainTemplate="myMainTemplate_Onside.html";
                            myMain.ShowPage("ChangePassword.html");
                   }
                   
                   
                   
                   
               }
        } else{
       myMain.myResponse.sendRedirect("Login");    
    }         
    }finally {            
            out.close();
    }
}

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
    }// </editor-fold>
}
