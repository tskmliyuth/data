/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myClasses;

import java.io.*;

/**
 *
 * @author NHEP-BORA
 */
public class clsFiles {
    
    public String readFile( String file ){
        StringBuilder buffer = new StringBuilder();
        try{
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");   

            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                    buffer.append((char)ch);
            }
            in.close();
            
            String st = buffer.toString(); 
            return st;
        }catch(FileNotFoundException e){
            return "File not found";
        }catch(IOException e){
            return "IO error";
        }catch(Exception e){
            return "Read template error";
        }
    }
    
    
    
}
