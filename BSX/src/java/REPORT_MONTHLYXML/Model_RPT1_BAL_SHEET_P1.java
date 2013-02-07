/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import java.io.FileInputStream;
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

public class Model_RPT1_BAL_SHEET_P1{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String BASH_NO;
private String BASH_REPORTID;
private String BASH_TEMID;
private String BASH_GROSSAMT;
private String BASH_PROVISION;
private String BASH_RIEL;
private String BASH_OTHERCURR;
private String BASH_TOTALRIEL;


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
String myScript ="SELECT BASH_NO,BASH_REPORTID,BASH_TEMID,BASH_GROSSAMT,NVL(BASH_PROVISION,0) BASH_PROVISION,NVL(BASH_RIEL,0) BASH_RIEL,NVL(BASH_OTHERCURR,0) BASH_OTHERCURR,NVL(BASH_TOTALRIEL,0) BASH_TOTALRIEL FROM RPT1_BAL_SHEET_P1";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT1_BAL_SHEET_P1(BASH_NO, BASH_REPORTID, BASH_TEMID, BASH_GROSSAMT, BASH_PROVISION, BASH_RIEL, BASH_OTHERCURR, BASH_TOTALRIEL)"+ c.NewLine();
myScript +="VALUES(" + "RPT1_BAL_SHEET_P1_seq.NEXTVAL" + "," + c.toOracleNumber(BASH_REPORTID) + "," + c.toOracleNumber(BASH_TEMID) + "," + c.toOracleNumber(BASH_GROSSAMT) + "," + c.toOracleNumber(BASH_PROVISION) + "," + c.toOracleNumber(BASH_RIEL) + "," + c.toOracleNumber(BASH_OTHERCURR) + "," + c.toOracleNumber(BASH_TOTALRIEL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT1_BAL_SHEET_P1" + c.NewLine() ;
myScript +="SET BASH_REPORTID=" + c.toOracleNumber(BASH_REPORTID) + " , BASH_TEMID=" + c.toOracleNumber(BASH_TEMID) + " , BASH_GROSSAMT=" + c.toOracleNumber(BASH_GROSSAMT) + " , BASH_PROVISION=" + c.toOracleNumber(BASH_PROVISION) + " , BASH_RIEL=" + c.toOracleNumber(BASH_RIEL) + " , BASH_OTHERCURR=" + c.toOracleNumber(BASH_OTHERCURR) + " , BASH_TOTALRIEL=" + c.toOracleNumber(BASH_TOTALRIEL)+ c.NewLine();
myScript +="WHERE BASH_NO=" + c.toOracleNumber(BASH_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT1_BAL_SHEET_P1" + c.NewLine() ;
myScript +="WHERE BASH_NO=" + c.toOracleNumber(BASH_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
BASH_NO=pResultSet.getString("BASH_NO");
BASH_REPORTID=pResultSet.getString("BASH_REPORTID");
BASH_TEMID=pResultSet.getString("BASH_TEMID");
BASH_GROSSAMT=pResultSet.getString("BASH_GROSSAMT");
BASH_PROVISION=pResultSet.getString("BASH_PROVISION");
BASH_RIEL=pResultSet.getString("BASH_RIEL");
BASH_OTHERCURR=pResultSet.getString("BASH_OTHERCURR");
BASH_TOTALRIEL=pResultSet.getString("BASH_TOTALRIEL");
}

public Model_RPT1_BAL_SHEET_P1() {
}

public String getBASH_NO() {
	return BASH_NO;
}
public String getBASH_REPORTID() {
	return BASH_REPORTID;
}
public String getBASH_TEMID() {
	return BASH_TEMID;
}
public String getBASH_GROSSAMT() {
	return BASH_GROSSAMT;
}
public String getBASH_PROVISION() {
	return BASH_PROVISION;
}
public String getBASH_RIEL() {
	return BASH_RIEL;
}
public String getBASH_OTHERCURR() {
	return BASH_OTHERCURR;
}
public String getBASH_TOTALRIEL() {
	return BASH_TOTALRIEL;
}

public void setBASH_NO(String pBASH_NO) {
	if (pBASH_NO.equals("")){myERROR.put("myERR_BASH_NO", "ReportNo can not be null");myMessageError += "ReportNo can not be null\n";}
	this.BASH_NO = pBASH_NO;
}
public void setBASH_REPORTID(String pBASH_REPORTID) {
	if (pBASH_REPORTID.equals("")){myERROR.put("myERR_BASH_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.BASH_REPORTID = pBASH_REPORTID;
}
public void setBASH_TEMID(String pBASH_TEMID) {
	if (pBASH_TEMID.equals("")){myERROR.put("myERR_BASH_TEMID", "ASSETS can not be null");myMessageError += "ASSETS can not be null\n";}
	this.BASH_TEMID = pBASH_TEMID;
}
public void setBASH_GROSSAMT(String pBASH_GROSSAMT) {
	if (pBASH_GROSSAMT.equals("")){myERROR.put("myERR_BASH_GROSSAMT", "Gross Amount in Riel  can not be null");myMessageError += "Gross Amount in Riel  can not be null\n";}
	this.BASH_GROSSAMT = pBASH_GROSSAMT;
}
public void setBASH_PROVISION(String pBASH_PROVISION) {
	this.BASH_PROVISION = pBASH_PROVISION;
}
public void setBASH_RIEL(String pBASH_RIEL) {
	this.BASH_RIEL = pBASH_RIEL;
}
public void setBASH_OTHERCURR(String pBASH_OTHERCURR) {
	this.BASH_OTHERCURR = pBASH_OTHERCURR;
}
public void setBASH_TOTALRIEL(String pBASH_TOTALRIEL) {
	this.BASH_TOTALRIEL = pBASH_TOTALRIEL;
}

} ;
