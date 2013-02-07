/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import myClasses.*;
import myModels.*;

/**
 *
 * @author NHEP-BORA
 */
@WebServlet(name = "ExchageRate", urlPatterns = {"/ExchageRate"})
public class ExchageRate extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        clsMain myClsMain = new clsMain(request,response,"");
        clsDataBase myCls=new clsDataBase();
        String myDGV ="";
        String myDateTimeUpdate="";
        ResultSet myResultSet = myCls.ExecuteReader("SELECT CURR_NO,CURR_KNAME,CURR_ENAME,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE,CURR_SYSUID,NVL(to_char(CURR_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') CURR_SYSDATE FROM DATA_CURRENCY");
        try {
            while(myResultSet.next()){                
                model_DATA_CURRENCY pDATA_CURRENCY=new model_DATA_CURRENCY();
                pDATA_CURRENCY.setData(myResultSet);
                myDGV += pDATA_CURRENCY.getHTMLTable();
                if (myDateTimeUpdate.equals("")){
                    myDateTimeUpdate=pDATA_CURRENCY.getCURR_SYSDATE();
                }                
            }
            String myContainTemplate=myClsMain.getContentFromFile("Exchagerate.html");            
            myContainTemplate=myContainTemplate.replace("##Update_Date##", myDateTimeUpdate);
            myContainTemplate=myContainTemplate.replace("##HTML_TABLE##", myDGV);
            myClsMain.PrintOut(myContainTemplate);
        } catch (SQLException ex) {
            Logger.getLogger(ExchageRate.class.getName()).log(Level.SEVERE, null, ex);
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
