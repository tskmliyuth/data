
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class clsFileDownload{
    public boolean isERROR=true;
    public String ERROR_Message="";
    
    protected void StartDowload(HttpServletRequest request, HttpServletResponse response,String pServerFileName,String pClientFileName) {
        FileInputStream fileIn = null;
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + pClientFileName);
            File file = new File(pServerFileName);
            fileIn = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while(fileIn.read(outputByte, 0, 4096) != -1)
            {
                    out.write(outputByte, 0, 4096);
            }
            fileIn.close();
            out.flush();
            out.close();
            isERROR=false;
        } catch (IOException ex) {
            isERROR=true;
            ERROR_Message=ex.getMessage();        
        }finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                isERROR=true;
                ERROR_Message=ex.getMessage();
            }
        }
   
        }
 

}
