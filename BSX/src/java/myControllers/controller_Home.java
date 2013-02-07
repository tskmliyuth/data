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
import myModels.clsDataBase;
import myModels.model_DATA_CURRENCY;

@WebServlet(name = "controller_Home", urlPatterns = {"/Home"})
public class controller_Home extends HttpServlet {

     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            clsMain myClsMain = new clsMain(request,response,"myMainTemplate.html");
            clsDataBase myCls=new clsDataBase();
            String myInfo =myCls.ExecuteScalar("SELECT INFO_MESSAGE FROM DATA_INFO WHERE INFO_RE_NO=1").toString();
            String myDGV ="";
            String myDateTimeUpdate="";
            try{
            ResultSet myResultSet = myCls.ExecuteReader("SELECT CURR_NO,CURR_KNAME,CURR_ENAME,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE,CURR_SYSUID,NVL(to_char(CURR_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') CURR_SYSDATE FROM DATA_CURRENCY WHERE CURR_NO IN ('USD/KHR','EUR/KHR','AUD/KHR','JPY/KHR','CNY/KHR','THB/KHR','VND/KHR','KRW/KHR')");
            while(myResultSet.next()){                
                model_DATA_CURRENCY pDATA_CURRENCY=new model_DATA_CURRENCY();
                pDATA_CURRENCY.setData(myResultSet);
                myDGV += pDATA_CURRENCY.getHTMLTable();
                if (myDateTimeUpdate.equals("")){
                    myDateTimeUpdate=pDATA_CURRENCY.getCURR_SYSDATE();
                }
            }          
            String myContainTemplate=myClsMain.getContentFromFile("Home.html");
            
            myContainTemplate=myContainTemplate.replace("##Update_Date##", myDateTimeUpdate);
            myContainTemplate=myContainTemplate.replace("##myInfo##", myInfo);
            myContainTemplate=myContainTemplate.replace("##HTML_TABLE##", myDGV);
            
            if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("1")){
                            myClsMain.myMainTemplate="myTemplateNBCAdmin.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("2")){
                            myClsMain.myMainTemplate="myTemplateNBCUser.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("11")){
                            myClsMain.myMainTemplate="myMainTemplateAdmin.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("12")){
                            myClsMain.myMainTemplate="myMainTemplate.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("31")){
                            myClsMain.myMainTemplate="myTemplateNBCAdmin_S.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("32")){
                            myClsMain.myMainTemplate="myTemplateNBCUser_S.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("21")){
                            myClsMain.myMainTemplate="myMainTemplate_Onside_Admin.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }else if (myClsMain.myDATA_USERS.getUSER_ROLE_NO().equals("22")){
                            myClsMain.myMainTemplate="myMainTemplate_Onside.html";
                            myClsMain.ShowPageContain(myContainTemplate);
                   }
            
            
            }catch (SQLException ex){
            
            }  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
