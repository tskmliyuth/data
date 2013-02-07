
package myClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import myModels.*;

public class clsMain extends HttpServlet {
    public HttpServletRequest myRequest;
    public HttpServletResponse myResponse;
    public String myMainTemplate;
    public model_DATA_USERS myDATA_USERS;
    
    public clsMain(HttpServletRequest reg,HttpServletResponse res,String pmyTemplate){
        myRequest=reg;
        myResponse=res;
        myMainTemplate=pmyTemplate;
        HttpSession session = myRequest.getSession(true);
        myDATA_USERS=(model_DATA_USERS)session.getAttribute("mySession");
    }


  
    public boolean CheckPermission(){
        HttpSession session = myRequest.getSession(true);
        model_DATA_USERS myDataUser;
        myDataUser=(model_DATA_USERS)session.getAttribute("mySession");
        if (myDataUser!=null){
            return true;
        }else{
            return false;
        }
    }     
   
    public boolean CheckPermission(String... pRole_Number){
        HttpSession session = myRequest.getSession(true);
        model_DATA_USERS myDataUser;
        myDataUser=(model_DATA_USERS)session.getAttribute("mySession");
        if (myDataUser!=null){
            String myRoleNumber=myDataUser.getUSER_ROLE_NO().toString();
            for(String myRole : pRole_Number){
              if (myRoleNumber.equals(myRole)){
                return true;
                }  
            }
            return false;                   
        }else{
            return false;
        }
    }
    
    
    public model_DATA_USERS getPermission(){
        HttpSession session = myRequest.getSession(true);
        model_DATA_USERS myDataUser;
        myDataUser=(model_DATA_USERS)session.getAttribute("mySession");
        return myDataUser;
    }
    
    public String realPath( String pFile ){
        String path = myRequest.getServletContext().getRealPath(pFile);
        return path;
    }
    
    public String getContentFromFile(String pFileName){
        clsFiles myFile=new clsFiles();
        String myContain=myFile.readFile(realPath(pFileName));
        return myContain;
    }
    
    public void ShowPageContain(String pContain) throws IOException{
        myResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = myResponse.getWriter();  
        try{
        if (CheckPermission()==true){
        clsFiles myFile=new clsFiles();
        String myTemplate=myFile.readFile(realPath(myMainTemplate));
        String myContain=pContain;
        String myShowPage=myTemplate.replace("###Contain####", myContain);
        out.println(myShowPage);
        }
        else{
        myResponse.sendRedirect("/BSX/Login");
        }
        } finally {            
            out.close();
        }
    }
    
    public void ShowPage(String pContainFile) throws IOException{
        myResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = myResponse.getWriter();  
        try{
        if (CheckPermission()==true){
        clsFiles myFile=new clsFiles();
        String myTemplate=myFile.readFile(realPath(myMainTemplate));
        String myContain=myFile.readFile(realPath(pContainFile));
        String myShowPage=myTemplate.replace("###Contain####", myContain);
        out.println(myShowPage);
        }
        else{
        myResponse.sendRedirect("/BSX/Login");
        }
        } finally {            
            out.close();
        }
    }
    
    public void ShowPage(String pContainFile,String pMenu_file) throws IOException{
        myResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = myResponse.getWriter();  
        try{
        if (CheckPermission()==true){
        clsFiles myFile=new clsFiles();
        String myTemplate=myFile.readFile(realPath(myMainTemplate));
        String myContain=myFile.readFile(realPath(pContainFile));
        String myMenu=myFile.readFile(realPath(pMenu_file));
        myContain=myContain.replace("##upload_menu##", myMenu);
        String myShowPage=myTemplate.replace("###Contain####", myContain);
        out.println(myShowPage);
        }
        else{
        myResponse.sendRedirect("/BSX/Login");
        }
        } finally {            
            out.close();
        }
    }   
    
    public void ShowLoginPage(){
        try {
            myResponse.sendRedirect("/BSX/Login");
        } catch (IOException ex) {
            Logger.getLogger(clsMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public void getPageScript(String pContain,String pMenu_file) throws IOException{
        myResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = myResponse.getWriter();  
        try{        
        clsFiles myFile=new clsFiles();
        String myTemplate=myFile.readFile(realPath(myMainTemplate));
        String myContain=pContain;
        String myMenu=myFile.readFile(realPath(pMenu_file));
        myContain=myContain.replace("##upload_menu##", myMenu);
        String myShowPage=myTemplate.replace("###Contain####", myContain);
        out.println(myShowPage);
        }        
        finally {            
        out.close();
        }
    }

    public void PrintOut(String pString){
        PrintWriter out = null;
        try{
            myResponse.setContentType("text/html;charset=UTF-8");
            out = myResponse.getWriter();
            out.println(pString);           

        } catch (IOException ex) {
            Logger.getLogger(clsMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {            
            out.close();
        }
    }
    public void Alert(String messege){
      
        try{
        PrintWriter out = myResponse.getWriter();  
        myResponse.setContentType("text/popup");  
            out.println("<script>");  
            out.println("alert("+"'"+messege+"'"+");");  
            out.println("</script>");

            

        } catch (IOException ex) {
            Logger.getLogger(clsMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void ShowLogin(String pContainFile) throws IOException{
        myResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = myResponse.getWriter();  
        try{        
        clsFiles myFile=new clsFiles();        
        String myContain=myFile.readFile(realPath(pContainFile));        
        out.println(myContain);        
        } finally {            
            out.close();
        }
    }    
}
