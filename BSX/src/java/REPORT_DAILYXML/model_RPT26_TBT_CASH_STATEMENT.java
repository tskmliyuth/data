/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_DAILYXML;

import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT26_TBT_CASH_STATEMENT{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String CASH_NO;
private String CASH_REPORTID;
private String CASH_ITEMID;
private String CASH_AMOUNTUSD;
private String CASH_AMOUNTKHR;
private String CASH_REMARK;


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
String myScript ="SELECT CASH_NO,CASH_REPORTID,CASH_ITEMID,CASH_AMOUNTUSD,CASH_AMOUNTKHR,CASH_REMARK FROM RPT26_TBT_CASH_STATEMENT";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT26_TBT_CASH_STATEMENT(CASH_NO, CASH_REPORTID, CASH_ITEMID, CASH_AMOUNTUSD, CASH_AMOUNTKHR, CASH_REMARK)"+ c.NewLine();
myScript +="VALUES(" + "RPT26_TBT_CASH_STATEMENT_seq.NEXTVAL" + "," + c.toOracleNumber(CASH_REPORTID) + "," + c.toOracleNumber(CASH_ITEMID) + "," + c.toOracleNumber(CASH_AMOUNTUSD) + "," + c.toOracleNumber(CASH_AMOUNTKHR) + "," + c.toORACLEString(CASH_REMARK) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT26_TBT_CASH_STATEMENT" + c.NewLine() ;
myScript +="SET CASH_REPORTID=" + c.toOracleNumber(CASH_REPORTID) + " , CASH_ITEMID=" + c.toOracleNumber(CASH_ITEMID) + " , CASH_AMOUNTUSD=" + c.toOracleNumber(CASH_AMOUNTUSD) + " , CASH_AMOUNTKHR=" + c.toOracleNumber(CASH_AMOUNTKHR) + " , CASH_REMARK=" + c.toORACLEString(CASH_REMARK)+ c.NewLine();
myScript +="WHERE CASH_NO=" + c.toOracleNumber(CASH_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT26_TBT_CASH_STATEMENT" + c.NewLine() ;
myScript +="WHERE CASH_NO=" + c.toOracleNumber(CASH_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
CASH_NO=pResultSet.getString("CASH_NO");
CASH_REPORTID=pResultSet.getString("CASH_REPORTID");
CASH_ITEMID=pResultSet.getString("CASH_ITEMID");
CASH_AMOUNTUSD=pResultSet.getString("CASH_AMOUNTUSD");
CASH_AMOUNTKHR=pResultSet.getString("CASH_AMOUNTKHR");
CASH_REMARK=pResultSet.getString("CASH_REMARK");
}

public model_RPT26_TBT_CASH_STATEMENT() {
}

public String getCASH_NO() {
	return CASH_NO;
}
public String getCASH_REPORTID() {
	return CASH_REPORTID;
}
public String getCASH_ITEMID() {
	return CASH_ITEMID;
}
public String getCASH_AMOUNTUSD() {
	return CASH_AMOUNTUSD;
}
public String getCASH_AMOUNTKHR() {
	return CASH_AMOUNTKHR;
}
public String getCASH_REMARK() {
	return CASH_REMARK;
}

public void setCASH_NO(String pCASH_NO) {
	if (pCASH_NO.equals("")){myERROR.put("myERR_CASH_NO", "Cash Statement Item ID can not be null");myMessageError += "Cash Statement Item ID can not be null\n";}
	this.CASH_NO = pCASH_NO;
}
public void setCASH_REPORTID(String pCASH_REPORTID) {
	if (pCASH_REPORTID.equals("")){myERROR.put("myERR_CASH_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.CASH_REPORTID = pCASH_REPORTID;
}
public void setCASH_ITEMID(String pCASH_ITEMID) {
	if (pCASH_ITEMID.equals("")){myERROR.put("myERR_CASH_ITEMID", "Cash Statement ItemID can not be null");myMessageError += "Cash Statement ItemID can not be null\n";}
	this.CASH_ITEMID = pCASH_ITEMID;
}
public void setCASH_AMOUNTUSD(String pCASH_AMOUNTUSD) {
	if (pCASH_AMOUNTUSD.equals("")){myERROR.put("myERR_CASH_AMOUNTUSD", "Amount usd can not be null");myMessageError += "Amount usd can not be null\n";}
	this.CASH_AMOUNTUSD = pCASH_AMOUNTUSD;
}
public void setCASH_AMOUNTKHR(String pCASH_AMOUNTKHR) {
	if (pCASH_AMOUNTKHR.equals("")){myERROR.put("myERR_CASH_AMOUNTKHR", "Amount khr can not be null");myMessageError += "Amount khr can not be null\n";}
	this.CASH_AMOUNTKHR = pCASH_AMOUNTKHR;
}
public void setCASH_REMARK(String pCASH_REMARK) {
	if (pCASH_REMARK.equals("")){myERROR.put("myERR_CASH_REMARK", "Remark can not be null");myMessageError += "Remark can not be null\n";}
	this.CASH_REMARK = pCASH_REMARK;
}

} ;