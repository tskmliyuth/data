
package myControllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import myClasses.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myModels.clsDataBase;
import myModels.model_DATA_USERS;


@WebServlet(name = "controller_Login", urlPatterns = {"/Login"})
public class controller_Login extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       clsMain myClass = new clsMain(request,response,"");
       myClass.ShowLogin("login.htm");
    }

        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LoginClick(request, response);
        } catch (SQLException ex) {
            
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private void LoginClick(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{ 
        response.setContentType("text/html;charset=UTF-8");
            // Connect to Oracle Database
            clsDataBase myDb = new clsDataBase();
            clsConverter c=new clsConverter();
             // Get POST data
            String myUserName = request.getParameter("myBox_UserName");
            String myPassword = request.getParameter("myBox_PWD");
            if (myUserName==null && myPassword==null || myUserName.equals("") && myPassword.equals("")){
                response.sendRedirect("Login?myErrorMessage=Invalid user name and password");   
            }      
            
            // Execute SQL statement
            String myScript ="SELECT USER_NO,USER_NAME,USER_FULL_NAME,USER_ROLE_NO,USER_MEMB_NO,USER_STATUS,USER_ADDRESS,USER_PHONE,USER_FAX,USER_EMAIL,NVL(USER_CA,'NULL') USER_CA FROM DATA_USERS WHERE LOWER(USER_NAME)=LOWER(" + c.toORACLEString(myUserName) + ") AND USER_PWD=" + c.toORACLEString(myPassword) ;
            ResultSet myResultSet = myDb.ExecuteReader(myScript);
            // Shift pointer from default index
            if(myResultSet.next()){
                int myUserStatus = myResultSet.getInt("USER_STATUS");
                // Compare PASSWORD from POST data against Database
                if(myUserStatus==1){
                    model_DATA_USERS myDataUser=new model_DATA_USERS();
                    myDataUser.setUSER_NO(myResultSet.getString("USER_NO"));
                    myDataUser.setUSER_NAME(myResultSet.getString("USER_NAME"));
                    myDataUser.setUSER_FULL_NAME(myResultSet.getString("USER_FULL_NAME"));
                    myDataUser.setUSER_ROLE_NO(myResultSet.getString("USER_ROLE_NO"));
                    myDataUser.setUSER_MEMB_NO(myResultSet.getString("USER_MEMB_NO"));
                    myDataUser.setUSER_STATUS(myResultSet.getString("USER_STATUS"));
                    myDataUser.setUSER_ADDRESS(myResultSet.getString("USER_ADDRESS"));
                    myDataUser.setUSER_PHONE(myResultSet.getString("USER_PHONE"));
                    myDataUser.setUSER_FAX(myResultSet.getString("USER_FAX"));
                    myDataUser.setUSER_EMAIL(myResultSet.getString("USER_EMAIL"));
                    myDataUser.USER_CA=myResultSet.getString("USER_CA");
                    HttpSession session = request.getSession(true);
                    session.setAttribute("mySession", myDataUser);
                    response.sendRedirect("Home");
                }else{
                  response.sendRedirect("Login?myErrorMessage=Your user account has been disable please contact your administrator to enable.");
                }
            }else{
                  response.sendRedirect("Login?myErrorMessage=Invalid user name and password");
            }

            myDb.CloseResultSet();
    }
}
    
    

