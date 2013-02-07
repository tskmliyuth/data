/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.clsMain;
import myModels.clsDataBase;

/**
 *
 * @author NHEP-BORA
 */
@WebServlet(name = "Contactus", urlPatterns = {"/Contactus"})
public class Contactus extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           //clsDataBase myDb = new clsDataBase();
           clsMain myMain = new clsMain(request,response,"myMainTemplate.html"); 
           if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("1")){
                            myMain.myMainTemplate="myTemplateNBCAdmin.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("2")){
                            myMain.myMainTemplate="myTemplateNBCUser.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("11")){
                            myMain.myMainTemplate="myMainTemplateAdmin.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("12")){
                            myMain.myMainTemplate="myMainTemplate.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("31")){
                            myMain.myMainTemplate="myTemplateNBCAdmin_S.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("32")){
                            myMain.myMainTemplate="myTemplateNBCUser_S.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("21")){
                            myMain.myMainTemplate="myMainTemplate_Onside_Admin.html";
                            myMain.ShowPage("Contactus.html");
                   }else if (myMain.myDATA_USERS.getUSER_ROLE_NO().equals("22")){
                            myMain.myMainTemplate="myMainTemplate_Onside.html";
                            myMain.ShowPage("Contactus.html");
                   }
           
        } finally {            
            out.close();
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
