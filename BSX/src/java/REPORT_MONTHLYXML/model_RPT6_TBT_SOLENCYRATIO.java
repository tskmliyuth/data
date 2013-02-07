/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.clsConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT6_TBT_SOLENCYRATIO{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String SOLV_NO;
private String SOLV_REPORTID;
private String SOLV_RATIOITEMID;
private String SOLV_AMOUNTINUSD;
private String SOLV_WEIGHTING;
private String SOLV_TOTALAMT_USD;
private String SOLV_TOTALAMT_MILLIONRIEL;


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
String myScript ="SELECT SOLV_NO,SOLV_REPORTID,SOLV_RATIOITEMID,SOLV_AMOUNTINUSD,SOLV_WEIGHTING,SOLV_TOTALAMT_USD,SOLV_TOTALAMT_MILLIONRIEL FROM RPT6_TBT_SOLENCYRATIO";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT6_TBT_SOLENCYRATIO(SOLV_NO, SOLV_REPORTID, SOLV_RATIOITEMID, SOLV_AMOUNTINUSD, SOLV_WEIGHTING, SOLV_TOTALAMT_USD, SOLV_TOTALAMT_MILLIONRIEL)"+ c.NewLine();
myScript +="VALUES(" + "RPT6_TBT_SOLENCYRATIO_seq.NEXTVAL" + "," + c.toOracleNumber(SOLV_REPORTID) + "," + c.toOracleNumber(SOLV_RATIOITEMID) + "," + c.toOracleNumber(SOLV_AMOUNTINUSD) + "," + c.toOracleNumber(SOLV_WEIGHTING) + "," + c.toOracleNumber(SOLV_TOTALAMT_USD) + "," + c.toOracleNumber(SOLV_TOTALAMT_MILLIONRIEL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT6_TBT_SOLENCYRATIO" + c.NewLine() ;
myScript +="SET SOLV_REPORTID=" + c.toOracleNumber(SOLV_REPORTID) + " , SOLV_RATIOITEMID=" + c.toOracleNumber(SOLV_RATIOITEMID) + " , SOLV_AMOUNTINUSD=" + c.toOracleNumber(SOLV_AMOUNTINUSD) + " , SOLV_WEIGHTING=" + c.toOracleNumber(SOLV_WEIGHTING) + " , SOLV_TOTALAMT_USD=" + c.toOracleNumber(SOLV_TOTALAMT_USD) + " , SOLV_TOTALAMT_MILLIONRIEL=" + c.toOracleNumber(SOLV_TOTALAMT_MILLIONRIEL)+ c.NewLine();
myScript +="WHERE SOLV_NO=" + c.toOracleNumber(SOLV_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT6_TBT_SOLENCYRATIO" + c.NewLine() ;
myScript +="WHERE SOLV_NO=" + c.toOracleNumber(SOLV_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
SOLV_NO=pResultSet.getString("SOLV_NO");
SOLV_REPORTID=pResultSet.getString("SOLV_REPORTID");
SOLV_RATIOITEMID=pResultSet.getString("SOLV_RATIOITEMID");
SOLV_AMOUNTINUSD=pResultSet.getString("SOLV_AMOUNTINUSD");
SOLV_WEIGHTING=pResultSet.getString("SOLV_WEIGHTING");
SOLV_TOTALAMT_USD=pResultSet.getString("SOLV_TOTALAMT_USD");
SOLV_TOTALAMT_MILLIONRIEL=pResultSet.getString("SOLV_TOTALAMT_MILLIONRIEL");
}

public model_RPT6_TBT_SOLENCYRATIO() {
}

public String getSOLV_NO() {
	return SOLV_NO;
}
public String getSOLV_REPORTID() {
	return SOLV_REPORTID;
}
public String getSOLV_RATIOITEMID() {
	return SOLV_RATIOITEMID;
}
public String getSOLV_AMOUNTINUSD() {
	return SOLV_AMOUNTINUSD;
}
public String getSOLV_WEIGHTING() {
	return SOLV_WEIGHTING;
}
public String getSOLV_TOTALAMT_USD() {
	return SOLV_TOTALAMT_USD;
}
public String getSOLV_TOTALAMT_MILLIONRIEL() {
	return SOLV_TOTALAMT_MILLIONRIEL;
}

public void setSOLV_NO(String pSOLV_NO) {
	
	this.SOLV_NO = pSOLV_NO;
}
public void setSOLV_REPORTID(String pSOLV_REPORTID) {
	
	this.SOLV_REPORTID = pSOLV_REPORTID;
}
public void setSOLV_RATIOITEMID(String pSOLV_RATIOITEMID) {
	
	this.SOLV_RATIOITEMID = pSOLV_RATIOITEMID;
}
public void setSOLV_AMOUNTINUSD(String pSOLV_AMOUNTINUSD) {
	
	this.SOLV_AMOUNTINUSD = pSOLV_AMOUNTINUSD;
}
public void setSOLV_WEIGHTING(String pSOLV_WEIGHTING) {
	
	this.SOLV_WEIGHTING = pSOLV_WEIGHTING;
}
public void setSOLV_TOTALAMT_USD(String pSOLV_TOTALAMT_USD) {
	
	this.SOLV_TOTALAMT_USD = pSOLV_TOTALAMT_USD;
}
public void setSOLV_TOTALAMT_MILLIONRIEL(String pSOLV_TOTALAMT_MILLIONRIEL) {
	
	this.SOLV_TOTALAMT_MILLIONRIEL = pSOLV_TOTALAMT_MILLIONRIEL;
}

} ;
