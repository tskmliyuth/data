package myControllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.*;

@WebServlet(name = "controller_download_template", urlPatterns = {"/download_template"})
public class controller_download_template extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clsMain myClass = new clsMain(request,response,"myMainTemplate.html");
        String formName=request.getParameter("form");
        if ("DownloadTemplateDailyForm".equals(formName)){
            myClass.ShowPage("contain_download_daily.html");
        }else if("DownloadTemplateWeeklyForm".equals(formName)){
            myClass.ShowPage("contain_download_Weekly.html");
        }else if("DownloadTemplateMonthlyForm".equals(formName)){
            myClass.ShowPage("contain_download_monthly.html");
        }else if("DownloadTemplateQuarterlyForm".equals(formName)){
            myClass.ShowPage("contain_download_quarterly.html");
        }else if("DownloadTemplateAnnualForm".equals(formName)){
            myClass.ShowPage("contain_download_annual.html");
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
