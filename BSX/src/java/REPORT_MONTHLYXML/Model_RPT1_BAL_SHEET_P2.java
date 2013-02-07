/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class Model_RPT1_BAL_SHEET_P2{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String BASP_NO;
private String BASP_REPORTID;
private String BASP_TEMID;
private String BASP_RIEL;
private String BASP_OTHERCURR;
private String BASP_TOTALRIEL;


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
String myScript ="SELECT BASP_NO,BASP_REPORTID,BASP_TEMID,BASP_RIEL,NVL(BASP_OTHERCURR,0) BASP_OTHERCURR,NVL(BASP_TOTALRIEL,'') BASP_TOTALRIEL FROM RPT1_BAL_SHEET_P2";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT1_BAL_SHEET_P2(BASP_NO, BASP_REPORTID, BASP_TEMID, BASP_RIEL, BASP_OTHERCURR, BASP_TOTALRIEL)"+ c.NewLine();
myScript +="VALUES(" + "RPT1_BAL_SHEET_P2_seq.NEXTVAL" + "," + c.toOracleNumber(BASP_REPORTID) + "," + c.toOracleNumber(BASP_TEMID) + "," + c.toOracleNumber(BASP_RIEL) + "," + c.toOracleNumber(BASP_OTHERCURR) + "," + c.toORACLEString(BASP_TOTALRIEL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT1_BAL_SHEET_P2" + c.NewLine() ;
myScript +="SET BASP_REPORTID=" + c.toOracleNumber(BASP_REPORTID) + " , BASP_TEMID=" + c.toOracleNumber(BASP_TEMID) + " , BASP_RIEL=" + c.toOracleNumber(BASP_RIEL) + " , BASP_OTHERCURR=" + c.toOracleNumber(BASP_OTHERCURR) + " , BASP_TOTALRIEL=" + c.toORACLEString(BASP_TOTALRIEL)+ c.NewLine();
myScript +="WHERE BASP_NO=" + c.toOracleNumber(BASP_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT1_BAL_SHEET_P2" + c.NewLine() ;
myScript +="WHERE BASP_NO=" + c.toOracleNumber(BASP_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
BASP_NO=pResultSet.getString("BASP_NO");
BASP_REPORTID=pResultSet.getString("BASP_REPORTID");
BASP_TEMID=pResultSet.getString("BASP_TEMID");
BASP_RIEL=pResultSet.getString("BASP_RIEL");
BASP_OTHERCURR=pResultSet.getString("BASP_OTHERCURR");
BASP_TOTALRIEL=pResultSet.getString("BASP_TOTALRIEL");
}

public Model_RPT1_BAL_SHEET_P2() {
}

public String getBASP_NO() {
	return BASP_NO;
}
public String getBASP_REPORTID() {
	return BASP_REPORTID;
}
public String getBASP_TEMID() {
	return BASP_TEMID;
}
public String getBASP_RIEL() {
	return BASP_RIEL;
}
public String getBASP_OTHERCURR() {
	return BASP_OTHERCURR;
}
public String getBASP_TOTALRIEL() {
	return BASP_TOTALRIEL;
}

public void setBASP_NO(String pBASP_NO) {
	if (pBASP_NO.equals("")){myERROR.put("myERR_BASP_NO", "ReportNo can not be null");myMessageError += "ReportNo can not be null\n";}
	this.BASP_NO = pBASP_NO;
}
public void setBASP_REPORTID(String pBASP_REPORTID) {
	if (pBASP_REPORTID.equals("")){myERROR.put("myERR_BASP_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.BASP_REPORTID = pBASP_REPORTID;
}
public void setBASP_TEMID(String pBASP_TEMID) {
	if (pBASP_TEMID.equals("")){myERROR.put("myERR_BASP_TEMID", "LIABILLITIES AND SHAREHOLDER'S EQUITY can not be null");myMessageError += "LIABILLITIES AND SHAREHOLDER'S EQUITY can not be null\n";}
	this.BASP_TEMID = pBASP_TEMID;
}
public void setBASP_RIEL(String pBASP_RIEL) {
	if (pBASP_RIEL.equals("")){myERROR.put("myERR_BASP_RIEL", "Riels can not be null");myMessageError += "Riels can not be null\n";}
	this.BASP_RIEL = pBASP_RIEL;
}
public void setBASP_OTHERCURR(String pBASP_OTHERCURR) {
	this.BASP_OTHERCURR = pBASP_OTHERCURR;
}
public void setBASP_TOTALRIEL(String pBASP_TOTALRIEL) {
	this.BASP_TOTALRIEL = pBASP_TOTALRIEL;
}

} ;
