/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYXML;

import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT27_WEEKLYCASHSTATEMENT{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String CASA_NO;
private String CASA_REPORTID;
private String CASA_TEMID;
private String CASA_AMOUNT;
private String CASA_REMARK;


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
String myScript ="SELECT CASA_NO,CASA_REPORTID,CASA_TEMID,CASA_AMOUNT,CASA_REMARK FROM RPT27_WEEKLYCASHSTATEMENT";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT27_WEEKLYCASHSTATEMENT(CASA_NO, CASA_REPORTID, CASA_TEMID, CASA_AMOUNT, CASA_REMARK)"+ c.NewLine();
myScript +="VALUES(" + "RPT27_WEEKLYCASHSTATEMENT_seq.NEXTVAL" + "," + c.toOracleNumber(CASA_REPORTID) + "," + c.toOracleNumber(CASA_TEMID) + "," + c.toOracleNumber(CASA_AMOUNT) + "," + c.toORACLEString(CASA_REMARK) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT27_WEEKLYCASHSTATEMENT" + c.NewLine() ;
myScript +="SET CASA_REPORTID=" + c.toOracleNumber(CASA_REPORTID) + " , CASA_TEMID=" + c.toOracleNumber(CASA_TEMID) + " , CASA_AMOUNT=" + c.toOracleNumber(CASA_AMOUNT) + " , CASA_REMARK=" + c.toORACLEString(CASA_REMARK)+ c.NewLine();
myScript +="WHERE CASA_NO=" + c.toOracleNumber(CASA_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT27_WEEKLYCASHSTATEMENT" + c.NewLine() ;
myScript +="WHERE CASA_NO=" + c.toOracleNumber(CASA_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
CASA_NO=pResultSet.getString("CASA_NO");
CASA_REPORTID=pResultSet.getString("CASA_REPORTID");
CASA_TEMID=pResultSet.getString("CASA_TEMID");
CASA_AMOUNT=pResultSet.getString("CASA_AMOUNT");
CASA_REMARK=pResultSet.getString("CASA_REMARK");
}

public model_RPT27_WEEKLYCASHSTATEMENT() {
}

public String getCASA_NO() {
	return CASA_NO;
}
public String getCASA_REPORTID() {
	return CASA_REPORTID;
}
public String getCASA_TEMID() {
	return CASA_TEMID;
}
public String getCASA_AMOUNT() {
	return CASA_AMOUNT;
}
public String getCASA_REMARK() {
	return CASA_REMARK;
}

public void setCASA_NO(String pCASA_NO) {
	if (pCASA_NO.equals("")){myERROR.put("myERR_CASA_NO", "CashStatementID can not be null");myMessageError += "CashStatementID can not be null\n";}
	this.CASA_NO = pCASA_NO;
}
public void setCASA_REPORTID(String pCASA_REPORTID) {
	if (pCASA_REPORTID.equals("")){myERROR.put("myERR_CASA_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.CASA_REPORTID = pCASA_REPORTID;
}
public void setCASA_TEMID(String pCASA_TEMID) {
	if (pCASA_TEMID.equals("")){myERROR.put("myERR_CASA_TEMID", "CashStatementItemID can not be null");myMessageError += "CashStatementItemID can not be null\n";}
	this.CASA_TEMID = pCASA_TEMID;
}
public void setCASA_AMOUNT(String pCASA_AMOUNT) {
	if (pCASA_AMOUNT.equals("")){myERROR.put("myERR_CASA_AMOUNT", "Amount can not be null");myMessageError += "Amount can not be null\n";}
	this.CASA_AMOUNT = pCASA_AMOUNT;
}
public void setCASA_REMARK(String pCASA_REMARK) {
	if (pCASA_REMARK.equals("")){myERROR.put("myERR_CASA_REMARK", "Remark can not be null");myMessageError += "Remark can not be null\n";}
	this.CASA_REMARK = pCASA_REMARK;
}

} ;