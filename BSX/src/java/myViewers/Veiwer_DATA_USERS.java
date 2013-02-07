/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myViewers;

import java.io.IOException;
import java.io.StringWriter;
import myModels.clsDataBase;
import org.json.simple.JSONObject;

public class Veiwer_DATA_USERS {
    public String PageLoad(String pRealPath){
        clsDataBase myCls=new clsDataBase();
        String myHTML_BODY="";
        
        return myHTML_BODY;
    }
    
    public String LoadData() throws IOException{
        clsDataBase myCls=new clsDataBase();
        String myStr="";
        JSONObject myJsonObject=new JSONObject();
        myJsonObject.put("USER_NAME", "Bora");
        myJsonObject.put("USER_FULL_NAME", "Nhep Bora");
        //myJsonObject.put("USER_ROLE_NO", myCls.getOptionValue("ROLE_NO", "ROLE_NAME", "DATA_ROLES","2"));
       
        StringWriter out = new StringWriter();
        myJsonObject.writeJSONString(out);
        myStr = out.toString();  
        
        return myStr;
    }
}
