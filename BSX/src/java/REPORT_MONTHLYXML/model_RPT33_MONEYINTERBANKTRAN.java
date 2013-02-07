/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import myClasses.clsConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT33_MONEYINTERBANKTRAN{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String INBK_NO;
private String INBK_REPORTID;
private String INBK_FINANCIALINNAME;
private String INBK_FINANCIALINTYPE;
private String INBK_ASSESTOUTSTANDING;
private String INBK_ASSETINTERESTRATE;
private String INBK_ASSETMATURITY;
private String INBK_ASSETTYPEID;
private String INBK_LIOUTSTANDING;
private String INBK_LIASSETINTERESTRATE;
private String INBK_LIASSETMATURITY;
private String INBK_LIASSETTYPEID;
private String INBK_RESIDENT;

  

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
String myScript ="SELECT INBK_NO,INBK_REPORTID,NVL(INBK_FINANCIALINNAME,'') INBK_FINANCIALINNAME,INBK_FINANCIALINTYPE,INBK_ASSESTOUTSTANDING,INBK_ASSETINTERESTRATE,NVL(to_char(INBK_ASSETMATURITY, 'yyyy/mm/dd'),'') INBK_ASSETMATURITY,INBK_ASSETTYPEID,INBK_LIOUTSTANDING,INBK_LIASSETINTERESTRATE,NVL(to_char(INBK_LIASSETMATURITY, 'yyyy/mm/dd'),'') INBK_LIASSETMATURITY,INBK_LIASSETTYPEID FROM RPT33_MONEYINTERBANKTRAN";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT33_MONEYINTERBANKTRAN(INBK_NO, INBK_REPORTID, INBK_FINANCIALINNAME, INBK_FINANCIALINTYPE, INBK_ASSESTOUTSTANDING, INBK_ASSETINTERESTRATE, INBK_ASSETMATURITY, INBK_ASSETTYPEID, INBK_LIOUTSTANDING, INBK_LIASSETINTERESTRATE, INBK_LIASSETMATURITY, INBK_LIASSETTYPEID, INBK_RESIDENT)"+ c.NewLine();
myScript +="VALUES(" + "RPT33_MONEYINTERBANKTRAN_seq.NEXTVAL" + "," + c.toOracleNumber(INBK_REPORTID) + "," + c.toORACLEString(INBK_FINANCIALINNAME) + "," + c.toOracleNumber(INBK_FINANCIALINTYPE) + "," + c.toOracleNumber(INBK_ASSESTOUTSTANDING) + "," + c.toOracleNumber(INBK_ASSETINTERESTRATE) + "," + c.toOracleDateTime(INBK_ASSETMATURITY) + "," + c.toOracleNumber(INBK_ASSETTYPEID) + "," + c.toOracleNumber(INBK_LIOUTSTANDING) + "," + c.toOracleNumber(INBK_LIASSETINTERESTRATE) + "," + c.toOracleDateTime(INBK_LIASSETMATURITY) + "," + c.toOracleNumber(INBK_LIASSETTYPEID) +"," + c.toOracleNumber(INBK_RESIDENT) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT33_MONEYINTERBANKTRAN" + c.NewLine() ;
myScript +="SET INBK_REPORTID=" + c.toOracleNumber(INBK_REPORTID) + " , INBK_FINANCIALINNAME=" + c.toORACLEString(INBK_FINANCIALINNAME) + " , INBK_FINANCIALINTYPE=" + c.toOracleNumber(INBK_FINANCIALINTYPE) + " , INBK_ASSESTOUTSTANDING=" + c.toOracleNumber(INBK_ASSESTOUTSTANDING) + " , INBK_ASSETINTERESTRATE=" + c.toOracleNumber(INBK_ASSETINTERESTRATE) + " , INBK_ASSETMATURITY=" + c.toOracleDate(INBK_ASSETMATURITY) + " , INBK_ASSETTYPEID=" + c.toOracleNumber(INBK_ASSETTYPEID) + " , INBK_LIOUTSTANDING=" + c.toOracleNumber(INBK_LIOUTSTANDING) + " , INBK_LIASSETINTERESTRATE=" + c.toOracleNumber(INBK_LIASSETINTERESTRATE) + " , INBK_LIASSETMATURITY=" + c.toOracleDate(INBK_LIASSETMATURITY) + " , INBK_LIASSETTYPEID=" + c.toOracleNumber(INBK_LIASSETTYPEID)+ c.NewLine();
myScript +="WHERE INBK_NO=" + c.toOracleNumber(INBK_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT33_MONEYINTERBANKTRAN" + c.NewLine() ;
myScript +="WHERE INBK_NO=" + c.toOracleNumber(INBK_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
INBK_NO=pResultSet.getString("INBK_NO");
INBK_REPORTID=pResultSet.getString("INBK_REPORTID");
INBK_FINANCIALINNAME=pResultSet.getString("INBK_FINANCIALINNAME");
INBK_FINANCIALINTYPE=pResultSet.getString("INBK_FINANCIALINTYPE");
INBK_ASSESTOUTSTANDING=pResultSet.getString("INBK_ASSESTOUTSTANDING");
INBK_ASSETINTERESTRATE=pResultSet.getString("INBK_ASSETINTERESTRATE");
INBK_ASSETMATURITY=pResultSet.getString("INBK_ASSETMATURITY");
INBK_ASSETTYPEID=pResultSet.getString("INBK_ASSETTYPEID");
INBK_LIOUTSTANDING=pResultSet.getString("INBK_LIOUTSTANDING");
INBK_LIASSETINTERESTRATE=pResultSet.getString("INBK_LIASSETINTERESTRATE");
INBK_LIASSETMATURITY=pResultSet.getString("INBK_LIASSETMATURITY");
INBK_LIASSETTYPEID=pResultSet.getString("INBK_LIASSETTYPEID");
INBK_RESIDENT=pResultSet.getString("INBK_RESIDENT");
}

public model_RPT33_MONEYINTERBANKTRAN() {
}

public String getINBK_NO() {
	return INBK_NO;
}
public String getINBK_REPORTID() {
	return INBK_REPORTID;
}
public String getINBK_FINANCIALINNAME() {
	return INBK_FINANCIALINNAME;
}
public String getINBK_FINANCIALINTYPE() {
	return INBK_FINANCIALINTYPE;
}
public String getINBK_ASSESTOUTSTANDING() {
	return INBK_ASSESTOUTSTANDING;
}
public String getINBK_ASSETINTERESTRATE() {
	return INBK_ASSETINTERESTRATE;
}
public String getINBK_ASSETMATURITY() {
	return INBK_ASSETMATURITY;
}
public String getINBK_ASSETTYPEID() {
	return INBK_ASSETTYPEID;
}
public String getINBK_LIOUTSTANDING() {
	return INBK_LIOUTSTANDING;
}
public String getINBK_LIASSETINTERESTRATE() {
	return INBK_LIASSETINTERESTRATE;
}
public String getINBK_LIASSETMATURITY() {
	return INBK_LIASSETMATURITY;
}
public String getINBK_LIASSETTYPEID() {
	return INBK_LIASSETTYPEID;
}
  public String getINBK_RESIDENT() {
        return INBK_RESIDENT;
    }

    public void setINBK_RESIDENT(String INBK_RESIDENT) {
        this.INBK_RESIDENT = INBK_RESIDENT;
    }

public void setINBK_NO(String pINBK_NO) {
	if (pINBK_NO.equals("")){myERROR.put("myERR_INBK_NO", "MoneyInterbankID can not be null");myMessageError += "MoneyInterbankID can not be null\n";}
	this.INBK_NO = pINBK_NO;
}
public void setINBK_REPORTID(String pINBK_REPORTID) {
	if (pINBK_REPORTID.equals("")){myERROR.put("myERR_INBK_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.INBK_REPORTID = pINBK_REPORTID;
}
public void setINBK_FINANCIALINNAME(String pINBK_FINANCIALINNAME) {
	this.INBK_FINANCIALINNAME = pINBK_FINANCIALINNAME;
}
public void setINBK_FINANCIALINTYPE(String pINBK_FINANCIALINTYPE) {
	if (pINBK_FINANCIALINTYPE.equals("")){myERROR.put("myERR_INBK_FINANCIALINTYPE", "FinancialInstitutionType can not be null");myMessageError += "FinancialInstitutionType can not be null\n";}
	this.INBK_FINANCIALINTYPE = pINBK_FINANCIALINTYPE;
}
public void setINBK_ASSESTOUTSTANDING(String pINBK_ASSESTOUTSTANDING) {
	if (pINBK_ASSESTOUTSTANDING.equals("")){myERROR.put("myERR_INBK_ASSESTOUTSTANDING", "AssestOutStanding can not be null");myMessageError += "AssestOutStanding can not be null\n";}
	this.INBK_ASSESTOUTSTANDING = pINBK_ASSESTOUTSTANDING;
}
public void setINBK_ASSETINTERESTRATE(String pINBK_ASSETINTERESTRATE) {
	if (pINBK_ASSETINTERESTRATE.equals("")){myERROR.put("myERR_INBK_ASSETINTERESTRATE", "AssetInterestRate can not be null");myMessageError += "AssetInterestRate can not be null\n";}
	this.INBK_ASSETINTERESTRATE = pINBK_ASSETINTERESTRATE;
}
public void setINBK_ASSETMATURITY(String pINBK_ASSETMATURITY) {
	if (pINBK_ASSETMATURITY.equals("")){myERROR.put("myERR_INBK_ASSETMATURITY", "AssetMaturity can not be null");myMessageError += "AssetMaturity can not be null\n";}
	this.INBK_ASSETMATURITY = pINBK_ASSETMATURITY;
}
public void setINBK_ASSETTYPEID(String pINBK_ASSETTYPEID) {
	if (pINBK_ASSETTYPEID.equals("")){myERROR.put("myERR_INBK_ASSETTYPEID", "AssetTypeID can not be null");myMessageError += "AssetTypeID can not be null\n";}
	this.INBK_ASSETTYPEID = pINBK_ASSETTYPEID;
}
public void setINBK_LIOUTSTANDING(String pINBK_LIOUTSTANDING) {
	if (pINBK_LIOUTSTANDING.equals("")){myERROR.put("myERR_INBK_LIOUTSTANDING", "LiabilitiesOutStanding can not be null");myMessageError += "LiabilitiesOutStanding can not be null\n";}
	this.INBK_LIOUTSTANDING = pINBK_LIOUTSTANDING;
}
public void setINBK_LIASSETINTERESTRATE(String pINBK_LIASSETINTERESTRATE) {
	if (pINBK_LIASSETINTERESTRATE.equals("")){myERROR.put("myERR_INBK_LIASSETINTERESTRATE", "LiabilitiesAssetInterestRate can not be null");myMessageError += "LiabilitiesAssetInterestRate can not be null\n";}
	this.INBK_LIASSETINTERESTRATE = pINBK_LIASSETINTERESTRATE;
}
public void setINBK_LIASSETMATURITY(String pINBK_LIASSETMATURITY) {
	if (pINBK_LIASSETMATURITY.equals("")){myERROR.put("myERR_INBK_LIASSETMATURITY", "LiabilitiesAssetMaturity can not be null");myMessageError += "LiabilitiesAssetMaturity can not be null\n";}
	this.INBK_LIASSETMATURITY = pINBK_LIASSETMATURITY;
}
public void setINBK_LIASSETTYPEID(String pINBK_LIASSETTYPEID) {
	if (pINBK_LIASSETTYPEID.equals("")){myERROR.put("myERR_INBK_LIASSETTYPEID", "LiabilitiesAssetTypeID can not be null");myMessageError += "LiabilitiesAssetTypeID can not be null\n";}
	this.INBK_LIASSETTYPEID = pINBK_LIASSETTYPEID;
}

} ;