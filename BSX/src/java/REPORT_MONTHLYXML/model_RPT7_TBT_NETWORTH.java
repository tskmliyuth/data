/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT7_TBT_NETWORTH{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String NETW_NO;
private String NETW_REPORTID;
private String NETW_NETWORTHITEMID;
private String NETW_AMOUNTINUSD;
private String NETW_AMOUNTINKHR;


public String getErrorToString(){
    if (myERROR.isEmpty()==true){
        isERROR=false;
        myERROR.put("myERR", "FALSE");
        myERROR.put("Message", myMessageSeccessfully);
    }else{
        isERROR=true;
        myERROR.put("myERR", "TRUE");
        myERROR.put("Message", "There are some fields are invalid.\n" + myMessageError);
    }        
    return c.JsonToString(myERROR);     
}
public String getSelectScript(){
String myScript ="SELECT NETW_NO,NETW_REPORTID,NETW_NETWORTHITEMID,NETW_AMOUNTINUSD,NETW_AMOUNTINKHR FROM RPT7_TBT_NETWORTH";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT7_TBT_NETWORTH(NETW_NO, NETW_REPORTID, NETW_NETWORTHITEMID, NETW_AMOUNTINUSD, NETW_AMOUNTINKHR)"+ c.NewLine();
myScript +="VALUES(" + "RPT7_TBT_NETWORTH_seq.NEXTVAL" + "," + c.toOracleNumber(NETW_REPORTID) + "," + c.toOracleNumber(NETW_NETWORTHITEMID) + "," + c.toOracleNumber(NETW_AMOUNTINUSD) + "," + c.toOracleNumber(NETW_AMOUNTINKHR) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT7_TBT_NETWORTH" + c.NewLine() ;
myScript +="SET NETW_REPORTID=" + c.toOracleNumber(NETW_REPORTID) + " , NETW_NETWORTHITEMID=" + c.toOracleNumber(NETW_NETWORTHITEMID) + " , NETW_AMOUNTINUSD=" + c.toOracleNumber(NETW_AMOUNTINUSD) + " , NETW_AMOUNTINKHR=" + c.toOracleNumber(NETW_AMOUNTINKHR)+ c.NewLine();
myScript +="WHERE NETW_NO=" + c.toOracleNumber(NETW_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT7_TBT_NETWORTH" + c.NewLine() ;
myScript +="WHERE NETW_NO=" + c.toOracleNumber(NETW_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
NETW_NO=pResultSet.getString("NETW_NO");
NETW_REPORTID=pResultSet.getString("NETW_REPORTID");
NETW_NETWORTHITEMID=pResultSet.getString("NETW_NETWORTHITEMID");
NETW_AMOUNTINUSD=pResultSet.getString("NETW_AMOUNTINUSD");
NETW_AMOUNTINKHR=pResultSet.getString("NETW_AMOUNTINKHR");
}

public model_RPT7_TBT_NETWORTH() {
}

public String getNETW_NO() {
	return NETW_NO;
}
public String getNETW_REPORTID() {
	return NETW_REPORTID;
}
public String getNETW_NETWORTHITEMID() {
	return NETW_NETWORTHITEMID;
}
public String getNETW_AMOUNTINUSD() {
	return NETW_AMOUNTINUSD;
}
public String getNETW_AMOUNTINKHR() {
	return NETW_AMOUNTINKHR;
}

public void setNETW_NO(String pNETW_NO) {
	
	this.NETW_NO = pNETW_NO;
}
public void setNETW_REPORTID(String pNETW_REPORTID) {
	
	this.NETW_REPORTID = pNETW_REPORTID;
}
public void setNETW_NETWORTHITEMID(String pNETW_NETWORTHITEMID) {
	 
	this.NETW_NETWORTHITEMID = pNETW_NETWORTHITEMID;
}
public void setNETW_AMOUNTINUSD(String pNETW_AMOUNTINUSD) {
	
	this.NETW_AMOUNTINUSD = pNETW_AMOUNTINUSD;
}
public void setNETW_AMOUNTINKHR(String pNETW_AMOUNTINKHR) {
	
	this.NETW_AMOUNTINKHR = pNETW_AMOUNTINKHR;
}

} ;