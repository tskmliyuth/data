/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.clsConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT8_TBT_NETPOSITION{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String OPEN_NO;
private String OPEN_REPORTID;
private String OPEN_CURRENCYID;
private String OPEN_AFFECTPROASSESTS;
private String OPEN_AFFECTPROLIA_CAPI;
private String OPEN_PROCUR_OFF_BLS;
private String OPEN_PROVICU_PAY_BLS;
private String OPEN_NETOPENPOSITION;
private String OPEN_NETOPENPOS_NWT;
private String OPEN_LIMIT;
private String OPEN_EXCESS;


public String getErrorToString(){
    if (myERROR.isEmpty()==true){
        isERROR=false;
        myERROR.put("myERR", "FALSE");
        myERROR.put("Message", "Successfully save");
    }else{
        isERROR=true;
        myERROR.put("myERR", "TRUE");
        myERROR.put("Message", "There are some fields are invalid.");
    }        
    return c.JsonToString(myERROR);     
}

public String getSelectScript(){
String myScript ="SELECT OPEN_NO,OPEN_REPORTID,OPEN_CURRENCYID,OPEN_AFFECTPROASSESTS,OPEN_AFFECTPROLIA_CAPI,OPEN_PROCUR_OFF_BLS,OPEN_PROVICU_PAY_BLS,OPEN_NETOPENPOSITION,OPEN_NETOPENPOS_NWT,OPEN_LIMIT,OPEN_EXCESS FROM RPT8_TBT_NETPOSITION";
return myScript;
}

public String getInsertScript(){
String myScript ="INSERT INTO RPT8_TBT_NETPOSITION(OPEN_NO, OPEN_REPORTID, OPEN_CURRENCYID, OPEN_AFFECTPROASSESTS, OPEN_AFFECTPROLIA_CAPI, OPEN_PROCUR_OFF_BLS, OPEN_PROVICU_PAY_BLS, OPEN_NETOPENPOSITION, OPEN_NETOPENPOS_NWT, OPEN_LIMIT, OPEN_EXCESS)"+ c.NewLine();
myScript +="VALUES(" + "RPT8_TBT_NETPOSITION_seq.NEXTVAL" + "," + c.toOracleNumber(OPEN_REPORTID) + "," + c.toOracleNumber(OPEN_CURRENCYID) + "," + c.toOracleNumber(OPEN_AFFECTPROASSESTS) + "," + c.toOracleNumber(OPEN_AFFECTPROLIA_CAPI) + "," + c.toOracleNumber(OPEN_PROCUR_OFF_BLS) + "," + c.toOracleNumber(OPEN_PROVICU_PAY_BLS) + "," + c.toOracleNumber(OPEN_NETOPENPOSITION) + "," + c.toOracleNumber(OPEN_NETOPENPOS_NWT) + "," + c.toOracleNumber(OPEN_LIMIT) + "," + c.toOracleNumber(OPEN_EXCESS) + ")";
return myScript;
}

public String getUpdateScript(){
String myScript ="UPDATE RPT8_TBT_NETPOSITION" + c.NewLine() ;
myScript +="SET OPEN_REPORTID=" + c.toOracleNumber(OPEN_REPORTID) + " , OPEN_CURRENCYID=" + c.toOracleNumber(OPEN_CURRENCYID) + " , OPEN_AFFECTPROASSESTS=" + c.toOracleNumber(OPEN_AFFECTPROASSESTS) + " , OPEN_AFFECTPROLIA_CAPI=" + c.toOracleNumber(OPEN_AFFECTPROLIA_CAPI) + " , OPEN_PROCUR_OFF_BLS=" + c.toOracleNumber(OPEN_PROCUR_OFF_BLS) + " , OPEN_PROVICU_PAY_BLS=" + c.toOracleNumber(OPEN_PROVICU_PAY_BLS) + " , OPEN_NETOPENPOSITION=" + c.toOracleNumber(OPEN_NETOPENPOSITION) + " , OPEN_NETOPENPOS_NWT=" + c.toOracleNumber(OPEN_NETOPENPOS_NWT) + " , OPEN_LIMIT=" + c.toOracleNumber(OPEN_LIMIT) + " , OPEN_EXCESS=" + c.toOracleNumber(OPEN_EXCESS)+ c.NewLine();
myScript +="WHERE OPEN_NO=" + "RPT8_TBT_NETPOSITION_seq.NEXTVAL";
return myScript;
}

public String getDeleteScript(){
String myScript ="DELETE FROM RPT8_TBT_NETPOSITION" + c.NewLine() ;
myScript +="WHERE OPEN_NO=" + "RPT8_TBT_NETPOSITION_seq.NEXTVAL";
return myScript;
}

public String getHTMLTable(){
String myScript ="<TR> </TR>\n";
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
OPEN_NO=pResultSet.getString("OPEN_NO");
OPEN_REPORTID=pResultSet.getString("OPEN_REPORTID");
OPEN_CURRENCYID=pResultSet.getString("OPEN_CURRENCYID");
OPEN_AFFECTPROASSESTS=pResultSet.getString("OPEN_AFFECTPROASSESTS");
OPEN_AFFECTPROLIA_CAPI=pResultSet.getString("OPEN_AFFECTPROLIA_CAPI");
OPEN_PROCUR_OFF_BLS=pResultSet.getString("OPEN_PROCUR_OFF_BLS");
OPEN_PROVICU_PAY_BLS=pResultSet.getString("OPEN_PROVICU_PAY_BLS");
OPEN_NETOPENPOSITION=pResultSet.getString("OPEN_NETOPENPOSITION");
OPEN_NETOPENPOS_NWT=pResultSet.getString("OPEN_NETOPENPOS_NWT");
OPEN_LIMIT=pResultSet.getString("OPEN_LIMIT");
OPEN_EXCESS=pResultSet.getString("OPEN_EXCESS");
}

public model_RPT8_TBT_NETPOSITION() {
}

public String getOPEN_NO() {
	return OPEN_NO;
}
public void setOPEN_NO(String pOPEN_NO) {
	if (pOPEN_NO.equals("")){myERROR.put("myERR_OPEN_NO", "Net Open Position ID can not be null");}
	this.OPEN_NO = pOPEN_NO;
}
public String getOPEN_REPORTID() {
	return OPEN_REPORTID;
}
public void setOPEN_REPORTID(String pOPEN_REPORTID) {
	if (pOPEN_REPORTID.equals("")){myERROR.put("myERR_OPEN_REPORTID", "Report ID can not be null");}
	this.OPEN_REPORTID = pOPEN_REPORTID;
}
public String getOPEN_CURRENCYID() {
	return OPEN_CURRENCYID;
}
public void setOPEN_CURRENCYID(String pOPEN_CURRENCYID) {
	if (pOPEN_CURRENCYID.equals("")){myERROR.put("myERR_OPEN_CURRENCYID", "Currency  can not be null");}
	this.OPEN_CURRENCYID = pOPEN_CURRENCYID;
}
public String getOPEN_AFFECTPROASSESTS() {
	return OPEN_AFFECTPROASSESTS;
}
public void setOPEN_AFFECTPROASSESTS(String pOPEN_AFFECTPROASSESTS) {
	if (pOPEN_AFFECTPROASSESTS.equals("")){myERROR.put("myERR_OPEN_AFFECTPROASSESTS", "Affected Provision Assests can not be null");}
	this.OPEN_AFFECTPROASSESTS = pOPEN_AFFECTPROASSESTS;
}
public String getOPEN_AFFECTPROLIA_CAPI() {
	return OPEN_AFFECTPROLIA_CAPI;
}
public void setOPEN_AFFECTPROLIA_CAPI(String pOPEN_AFFECTPROLIA_CAPI) {
	if (pOPEN_AFFECTPROLIA_CAPI.equals("")){myERROR.put("myERR_OPEN_AFFECTPROLIA_CAPI", "Affected Provision Liabilities Capital can not be null");}
	this.OPEN_AFFECTPROLIA_CAPI = pOPEN_AFFECTPROLIA_CAPI;
}
public String getOPEN_PROCUR_OFF_BLS() {
	return OPEN_PROCUR_OFF_BLS;
}
public void setOPEN_PROCUR_OFF_BLS(String pOPEN_PROCUR_OFF_BLS) {
	if (pOPEN_PROCUR_OFF_BLS.equals("")){myERROR.put("myERR_OPEN_PROCUR_OFF_BLS", "Provision Current Received Off-Balance Sheet can not be null");}
	this.OPEN_PROCUR_OFF_BLS = pOPEN_PROCUR_OFF_BLS;
}
public String getOPEN_PROVICU_PAY_BLS() {
	return OPEN_PROVICU_PAY_BLS;
}
public void setOPEN_PROVICU_PAY_BLS(String pOPEN_PROVICU_PAY_BLS) {
	if (pOPEN_PROVICU_PAY_BLS.equals("")){myERROR.put("myERR_OPEN_PROVICU_PAY_BLS", "Provision Current Payable Off-Balance Sheet can not be null");}
	this.OPEN_PROVICU_PAY_BLS = pOPEN_PROVICU_PAY_BLS;
}
public String getOPEN_NETOPENPOSITION() {
	return OPEN_NETOPENPOSITION;
}
public void setOPEN_NETOPENPOSITION(String pOPEN_NETOPENPOSITION) {
	if (pOPEN_NETOPENPOSITION.equals("")){myERROR.put("myERR_OPEN_NETOPENPOSITION", "Net Open Position can not be null");}
	this.OPEN_NETOPENPOSITION = pOPEN_NETOPENPOSITION;
}
public String getOPEN_NETOPENPOS_NWT() {
	return OPEN_NETOPENPOS_NWT;
}
public void setOPEN_NETOPENPOS_NWT(String pOPEN_NETOPENPOS_NWT) {
	if (pOPEN_NETOPENPOS_NWT.equals("")){myERROR.put("myERR_OPEN_NETOPENPOS_NWT", "Net Open Position Networth can not be null");}
	this.OPEN_NETOPENPOS_NWT = pOPEN_NETOPENPOS_NWT;
}
public String getOPEN_LIMIT() {
	return OPEN_LIMIT;
}
public void setOPEN_LIMIT(String pOPEN_LIMIT) {
	if (pOPEN_LIMIT.equals("")){myERROR.put("myERR_OPEN_LIMIT", "Limit can not be null");}
	this.OPEN_LIMIT = pOPEN_LIMIT;
}
public String getOPEN_EXCESS() {
	return OPEN_EXCESS;
}
public void setOPEN_EXCESS(String pOPEN_EXCESS) {
	if (pOPEN_EXCESS.equals("")){myERROR.put("myERR_OPEN_EXCESS", "Excess can not be null");}
	this.OPEN_EXCESS = pOPEN_EXCESS;
}


} ;
