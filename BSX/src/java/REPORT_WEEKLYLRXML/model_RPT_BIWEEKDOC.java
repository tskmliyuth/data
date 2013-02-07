/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYLRXML;

import java.sql.ResultSet;
import java.sql.SQLException;
import myClasses.clsConverter;
import org.json.simple.JSONObject;

public class model_RPT_BIWEEKDOC{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String BIDO_NO;
private String BIDO_REPORTID;
private String BIDO_BANKNAME;
private String BIDO_COUNTRY;
private String BIDO_CURRENTACC;
private String BIDO_FIXDEPOSITS1M;
private String BIDO_FIXDEPOSITS3M;
private String BIDO_FIXDEPOSITS6M;
private String BIDO_FIXDEPOSITS12M;
private String BIDO_FIXDEPOSITS12MUP;
private String BIDO_FIXEDEPOSITSOTHER;
private String BIDO_FIXDEPOSITSTOTAL;


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
String myScript ="SELECT BIDO_NO,BIDO_REPORTID,NVL(BIDO_BANKNAME,'') BIDO_BANKNAME,NVL(BIDO_COUNTRY,'') BIDO_COUNTRY,NVL(BIDO_CURRENTACC,'') BIDO_CURRENTACC,NVL(BIDO_FIXDEPOSITS1M,0) BIDO_FIXDEPOSITS1M,NVL(BIDO_FIXDEPOSITS3M,0) BIDO_FIXDEPOSITS3M,NVL(BIDO_FIXDEPOSITS6M,0) BIDO_FIXDEPOSITS6M,NVL(BIDO_FIXDEPOSITS12M,0) BIDO_FIXDEPOSITS12M,NVL(BIDO_FIXDEPOSITS12MUP,0) BIDO_FIXDEPOSITS12MUP,NVL(BIDO_FIXEDEPOSITSOTHER,0) BIDO_FIXEDEPOSITSOTHER,NVL(BIDO_FIXDEPOSITSTOTAL,0) BIDO_FIXDEPOSITSTOTAL FROM RPT40_BIWEEKDOC";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT40_BIWEEKDOC(BIDO_NO, BIDO_REPORTID, BIDO_BANKNAME, BIDO_COUNTRY, BIDO_CURRENTACC, BIDO_FIXDEPOSITS1M, BIDO_FIXDEPOSITS3M, BIDO_FIXDEPOSITS6M, BIDO_FIXDEPOSITS12M, BIDO_FIXDEPOSITS12MUP, BIDO_FIXEDEPOSITSOTHER, BIDO_FIXDEPOSITSTOTAL)"+ c.NewLine();
myScript +="VALUES(" + "RPT_BIWEEKDOC_seq.NEXTVAL" + "," + c.toOracleNumber(BIDO_REPORTID) + "," + c.toORACLEString(BIDO_BANKNAME) + "," + c.toORACLEString(BIDO_COUNTRY) + "," + c.toORACLEString(BIDO_CURRENTACC) + "," + c.toOracleNumber(BIDO_FIXDEPOSITS1M) + "," + c.toOracleNumber(BIDO_FIXDEPOSITS3M) + "," + c.toOracleNumber(BIDO_FIXDEPOSITS6M) + "," + c.toOracleNumber(BIDO_FIXDEPOSITS12M) + "," + c.toOracleNumber(BIDO_FIXDEPOSITS12MUP) + "," + c.toOracleNumber(BIDO_FIXEDEPOSITSOTHER) + "," + c.toOracleNumber(BIDO_FIXDEPOSITSTOTAL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT40_BIWEEKDOC" + c.NewLine() ;
myScript +="SET BIDO_REPORTID=" + c.toOracleNumber(BIDO_REPORTID) + " , BIDO_BANKNAME=" + c.toORACLEString(BIDO_BANKNAME) + " , BIDO_COUNTRY=" + c.toORACLEString(BIDO_COUNTRY) + " , BIDO_CURRENTACC=" + c.toORACLEString(BIDO_CURRENTACC) + " , BIDO_FIXDEPOSITS1M=" + c.toOracleNumber(BIDO_FIXDEPOSITS1M) + " , BIDO_FIXDEPOSITS3M=" + c.toOracleNumber(BIDO_FIXDEPOSITS3M) + " , BIDO_FIXDEPOSITS6M=" + c.toOracleNumber(BIDO_FIXDEPOSITS6M) + " , BIDO_FIXDEPOSITS12M=" + c.toOracleNumber(BIDO_FIXDEPOSITS12M) + " , BIDO_FIXDEPOSITS12MUP=" + c.toOracleNumber(BIDO_FIXDEPOSITS12MUP) + " , BIDO_FIXEDEPOSITSOTHER=" + c.toOracleNumber(BIDO_FIXEDEPOSITSOTHER) + " , BIDO_FIXDEPOSITSTOTAL=" + c.toOracleNumber(BIDO_FIXDEPOSITSTOTAL)+ c.NewLine();
myScript +="WHERE BIDO_NO=" + c.toOracleNumber(BIDO_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT40_BIWEEKDOC" + c.NewLine() ;
myScript +="WHERE BIDO_NO=" + c.toOracleNumber(BIDO_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
BIDO_NO=pResultSet.getString("BIDO_NO");
BIDO_REPORTID=pResultSet.getString("BIDO_REPORTID");
BIDO_BANKNAME=pResultSet.getString("BIDO_BANKNAME");
BIDO_COUNTRY=pResultSet.getString("BIDO_COUNTRY");
BIDO_CURRENTACC=pResultSet.getString("BIDO_CURRENTACC");
BIDO_FIXDEPOSITS1M=pResultSet.getString("BIDO_FIXDEPOSITS1M");
BIDO_FIXDEPOSITS3M=pResultSet.getString("BIDO_FIXDEPOSITS3M");
BIDO_FIXDEPOSITS6M=pResultSet.getString("BIDO_FIXDEPOSITS6M");
BIDO_FIXDEPOSITS12M=pResultSet.getString("BIDO_FIXDEPOSITS12M");
BIDO_FIXDEPOSITS12MUP=pResultSet.getString("BIDO_FIXDEPOSITS12MUP");
BIDO_FIXEDEPOSITSOTHER=pResultSet.getString("BIDO_FIXEDEPOSITSOTHER");
BIDO_FIXDEPOSITSTOTAL=pResultSet.getString("BIDO_FIXDEPOSITSTOTAL");
}

public model_RPT_BIWEEKDOC() {
}

public String getBIDO_NO() {
	return BIDO_NO;
}
public String getBIDO_REPORTID() {
	return BIDO_REPORTID;
}
public String getBIDO_BANKNAME() {
	return BIDO_BANKNAME;
}
public String getBIDO_COUNTRY() {
	return BIDO_COUNTRY;
}
public String getBIDO_CURRENTACC() {
	return BIDO_CURRENTACC;
}
public String getBIDO_FIXDEPOSITS1M() {
	return BIDO_FIXDEPOSITS1M;
}
public String getBIDO_FIXDEPOSITS3M() {
	return BIDO_FIXDEPOSITS3M;
}
public String getBIDO_FIXDEPOSITS6M() {
	return BIDO_FIXDEPOSITS6M;
}
public String getBIDO_FIXDEPOSITS12M() {
	return BIDO_FIXDEPOSITS12M;
}
public String getBIDO_FIXDEPOSITS12MUP() {
	return BIDO_FIXDEPOSITS12MUP;
}
public String getBIDO_FIXEDEPOSITSOTHER() {
	return BIDO_FIXEDEPOSITSOTHER;
}
public String getBIDO_FIXDEPOSITSTOTAL() {
	return BIDO_FIXDEPOSITSTOTAL;
}

public void setBIDO_NO(String pBIDO_NO) {
	if (pBIDO_NO.equals("")){myERROR.put("myERR_BIDO_NO", "BIDO_NO can not be null");myMessageError += "BIDO_NO can not be null\n";}
	this.BIDO_NO = pBIDO_NO;
}
public void setBIDO_REPORTID(String pBIDO_REPORTID) {
	if (pBIDO_REPORTID.equals("")){myERROR.put("myERR_BIDO_REPORTID", "Report ID can not be null");myMessageError += "Report ID can not be null\n";}
	this.BIDO_REPORTID = pBIDO_REPORTID;
}
public void setBIDO_BANKNAME(String pBIDO_BANKNAME) {
	this.BIDO_BANKNAME = pBIDO_BANKNAME;
}
public void setBIDO_COUNTRY(String pBIDO_COUNTRY) {
	this.BIDO_COUNTRY = pBIDO_COUNTRY;
}
public void setBIDO_CURRENTACC(String pBIDO_CURRENTACC) {
	this.BIDO_CURRENTACC = pBIDO_CURRENTACC;
}
public void setBIDO_FIXDEPOSITS1M(String pBIDO_FIXDEPOSITS1M) {
	this.BIDO_FIXDEPOSITS1M = pBIDO_FIXDEPOSITS1M;
}
public void setBIDO_FIXDEPOSITS3M(String pBIDO_FIXDEPOSITS3M) {
	this.BIDO_FIXDEPOSITS3M = pBIDO_FIXDEPOSITS3M;
}
public void setBIDO_FIXDEPOSITS6M(String pBIDO_FIXDEPOSITS6M) {
	this.BIDO_FIXDEPOSITS6M = pBIDO_FIXDEPOSITS6M;
}
public void setBIDO_FIXDEPOSITS12M(String pBIDO_FIXDEPOSITS12M) {
	this.BIDO_FIXDEPOSITS12M = pBIDO_FIXDEPOSITS12M;
}
public void setBIDO_FIXDEPOSITS12MUP(String pBIDO_FIXDEPOSITS12MUP) {
	this.BIDO_FIXDEPOSITS12MUP = pBIDO_FIXDEPOSITS12MUP;
}
public void setBIDO_FIXEDEPOSITSOTHER(String pBIDO_FIXEDEPOSITSOTHER) {
	this.BIDO_FIXEDEPOSITSOTHER = pBIDO_FIXEDEPOSITSOTHER;
}
public void setBIDO_FIXDEPOSITSTOTAL(String pBIDO_FIXDEPOSITSTOTAL) {
	this.BIDO_FIXDEPOSITSTOTAL = pBIDO_FIXDEPOSITSTOTAL;
}

} ;