import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.*;



@WebServlet(name = "frmMain", urlPatterns = {"/Index.php"})
public class frmMain extends HttpServlet {

      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        clsMain myClass = new clsMain(request,response,"myMainTemplate.html");
        
        
//        try {
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
            }else{
                 myClass.ShowPage("DATA_USERS_FORM.html");
                
            //out.println("Hello");
//                clsDataBase myCls=new clsDataBase();
//                out.println (myCls.getOptionValue("STAT_NAME", "STAT_NO", "SYS_STATUS","2"));
//                if (myCls.isERROR()==true){
//                    out.println (myCls.getERROR_Message());
//                    }
              
            }                                          
         
//        } finally {            
//            out.close();
//        }
        
    
    }
               
   
      
             
          
      
      
    public static boolean CheckPermission(){
        return true;
    }
    public String realPath( String pFile ){
        String path = getServletContext().getRealPath(pFile);
        return path;
    }
    public void ShowPage(HttpServletResponse response,String pContainFile) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
        try{
        if (CheckPermission()==true){
        clsFiles myFile=new clsFiles();
        String myTemplate=myFile.readFile(realPath("myMainTemplate.html"));
        String myContain=myFile.readFile(realPath(pContainFile));
        String myShowPage=myTemplate.replace("###Contain####", myContain);
        out.println(myShowPage);
        }
        else{
        out.println("No Permission");
        }
        } finally {            
            out.close();
        }
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   
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
    // </editor-fold>
}
