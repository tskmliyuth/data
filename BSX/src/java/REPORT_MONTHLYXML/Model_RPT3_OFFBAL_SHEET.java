/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class Model_RPT3_OFFBAL_SHEET{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String OBSH_NO;
private String OBSH_REPORTID;
private String OBSH_TEMID;
private String OBSH_RIELS;
private String OBSH_OTHERCURR;
private String OBSH_TOTALRIELS;


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
String myScript ="SELECT OBSH_NO,OBSH_REPORTID,OBSH_TEMID,OBSH_RIELS,NVL(OBSH_OTHERCURR,0) OBSH_OTHERCURR,NVL(OBSH_TOTALRIELS,0) OBSH_TOTALRIELS FROM RPT3_OFFBAL_SHEET";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT3_OFFBAL_SHEET(OBSH_NO, OBSH_REPORTID, OBSH_TEMID, OBSH_RIELS, OBSH_OTHERCURR, OBSH_TOTALRIELS)"+ c.NewLine();
myScript +="VALUES(" + "RPT3_OFFBAL_SHEET_seq.NEXTVAL" + "," + c.toOracleNumber(OBSH_REPORTID) + "," + c.toOracleNumber(OBSH_TEMID) + "," + c.toOracleNumber(OBSH_RIELS) + "," + c.toOracleNumber(OBSH_OTHERCURR) + "," + c.toOracleNumber(OBSH_TOTALRIELS) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT3_OFFBAL_SHEET" + c.NewLine() ;
myScript +="SET OBSH_REPORTID=" + c.toOracleNumber(OBSH_REPORTID) + " , OBSH_TEMID=" + c.toOracleNumber(OBSH_TEMID) + " , OBSH_RIELS=" + c.toOracleNumber(OBSH_RIELS) + " , OBSH_OTHERCURR=" + c.toOracleNumber(OBSH_OTHERCURR) + " , OBSH_TOTALRIELS=" + c.toOracleNumber(OBSH_TOTALRIELS)+ c.NewLine();
myScript +="WHERE OBSH_NO=" + c.toOracleNumber(OBSH_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT3_OFFBAL_SHEET" + c.NewLine() ;
myScript +="WHERE OBSH_NO=" + c.toOracleNumber(OBSH_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
OBSH_NO=pResultSet.getString("OBSH_NO");
OBSH_REPORTID=pResultSet.getString("OBSH_REPORTID");
OBSH_TEMID=pResultSet.getString("OBSH_TEMID");
OBSH_RIELS=pResultSet.getString("OBSH_RIELS");
OBSH_OTHERCURR=pResultSet.getString("OBSH_OTHERCURR");
OBSH_TOTALRIELS=pResultSet.getString("OBSH_TOTALRIELS");
}

public Model_RPT3_OFFBAL_SHEET() {
}

public String getOBSH_NO() {
	return OBSH_NO;
}
public String getOBSH_REPORTID() {
	return OBSH_REPORTID;
}
public String getOBSH_TEMID() {
	return OBSH_TEMID;
}
public String getOBSH_RIELS() {
	return OBSH_RIELS;
}
public String getOBSH_OTHERCURR() {
	return OBSH_OTHERCURR;
}
public String getOBSH_TOTALRIELS() {
	return OBSH_TOTALRIELS;
}

public void setOBSH_NO(String pOBSH_NO) {
	if (pOBSH_NO.equals("")){myERROR.put("myERR_OBSH_NO", "ReportNo can not be null");myMessageError += "ReportNo can not be null\n";}
	this.OBSH_NO = pOBSH_NO;
}
public void setOBSH_REPORTID(String pOBSH_REPORTID) {
	if (pOBSH_REPORTID.equals("")){myERROR.put("myERR_OBSH_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.OBSH_REPORTID = pOBSH_REPORTID;
}
public void setOBSH_TEMID(String pOBSH_TEMID) {
	if (pOBSH_TEMID.equals("")){myERROR.put("myERR_OBSH_TEMID", "Description can not be null");myMessageError += "Description can not be null\n";}
	this.OBSH_TEMID = pOBSH_TEMID;
}
public void setOBSH_RIELS(String pOBSH_RIELS) {
	if (pOBSH_RIELS.equals("")){myERROR.put("myERR_OBSH_RIELS", "Riels can not be null");myMessageError += "Riels can not be null\n";}
	this.OBSH_RIELS = pOBSH_RIELS;
}
public void setOBSH_OTHERCURR(String pOBSH_OTHERCURR) {
	this.OBSH_OTHERCURR = pOBSH_OTHERCURR;
}
public void setOBSH_TOTALRIELS(String pOBSH_TOTALRIELS) {
	this.OBSH_TOTALRIELS = pOBSH_TOTALRIELS;
}

} ;