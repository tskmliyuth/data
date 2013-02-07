/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;
import myClasses.clsConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT34_TBT_NETWORKINFO{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String NETW_NO;
private String NETW_REPORTID;
private String NETW_CITIESBRANCHES;
private String NETW_NUMBRANCHESDISTRICT;
private String NETW_NUMBRANCHESCOMMUNE;
private String NETW_LOANOUTSTANDAMT;
private String NETW_OUTSTANDBORROWER;
private String NETW_OUTSTANDBORROWMALE;
private String NETW_OUTSTANDBORROWFEMALE;
private String NETW_DBAMT;
private String NETW_DBDEPOSITORTOTAL;
private String NETW_DBDEPOSITORMALE;
private String NETW_DBDEPOSITORFEMALE;
private String NETW_NUMBEROFSTAFFTOTAL;
private String NETW_NUMBEROFSTAFFMALE;
private String NETW_NUMBEROFSTAFFFEMALE;
private String NETW_NUMBEROFATM;
private String NETW_NUMBEROFPOS;
private String NETW_DEBITCARDAMOUNT;
private String NETW_DEBITCARDNUMOFCARD;
private String NETW_CREDITCARDAMOUNT;
private String NETW_CREDITCARDNUMOFCARD;


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
String myScript ="SELECT NETW_NO,NETW_REPORTID,NETW_CITIESBRANCHES,NETW_NUMBRANCHESDISTRICT,NETW_NUMBRANCHESCOMMUNE,NETW_LOANOUTSTANDAMT,NETW_OUTSTANDBORROWER,NETW_OUTSTANDBORROWMALE,NETW_OUTSTANDBORROWFEMALE,NETW_DBAMT,NETW_DBDEPOSITORTOTAL,NETW_DBDEPOSITORMALE,NETW_DBDEPOSITORFEMALE,NETW_NUMBEROFSTAFFTOTAL,NETW_NUMBEROFSTAFFMALE,NETW_NUMBEROFSTAFFFEMALE,NETW_NUMBEROFATM,NETW_NUMBEROFPOS,NETW_DEBITCARDAMOUNT,NETW_DEBITCARDNUMOFCARD,NETW_CREDITCARDAMOUNT,NETW_CREDITCARDNUMOFCARD FROM RPT34_TBT_NETWORKINFO";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT34_TBT_NETWORKINFO(NETW_NO, NETW_REPORTID, NETW_CITIESBRANCHES, NETW_NUMBRANCHESDISTRICT, NETW_NUMBRANCHESCOMMUNE, NETW_LOANOUTSTANDAMT, NETW_OUTSTANDBORROWER, NETW_OUTSTANDBORROWMALE, NETW_OUTSTANDBORROWFEMALE, NETW_DBAMT, NETW_DBDEPOSITORTOTAL, NETW_DBDEPOSITORMALE, NETW_DBDEPOSITORFEMALE, NETW_NUMBEROFSTAFFTOTAL, NETW_NUMBEROFSTAFFMALE, NETW_NUMBEROFSTAFFFEMALE, NETW_NUMBEROFATM, NETW_NUMBEROFPOS, NETW_DEBITCARDAMOUNT, NETW_DEBITCARDNUMOFCARD, NETW_CREDITCARDAMOUNT, NETW_CREDITCARDNUMOFCARD)"+ c.NewLine();
myScript +="VALUES(" + "RPT34_TBT_NETWORKINFO_seq.NEXTVAL" + "," + c.toOracleNumber(NETW_REPORTID) + "," + c.toORACLEString(NETW_CITIESBRANCHES) + "," + c.toOracleNumber(NETW_NUMBRANCHESDISTRICT) + "," + c.toOracleNumber(NETW_NUMBRANCHESCOMMUNE) + "," + c.toOracleNumber(NETW_LOANOUTSTANDAMT) + "," + c.toOracleNumber(NETW_OUTSTANDBORROWER) + "," + c.toOracleNumber(NETW_OUTSTANDBORROWMALE) + "," + c.toOracleNumber(NETW_OUTSTANDBORROWFEMALE) + "," + c.toOracleNumber(NETW_DBAMT) + "," + c.toOracleNumber(NETW_DBDEPOSITORTOTAL) + "," + c.toOracleNumber(NETW_DBDEPOSITORMALE) + "," + c.toOracleNumber(NETW_DBDEPOSITORFEMALE) + "," + c.toOracleNumber(NETW_NUMBEROFSTAFFTOTAL) + "," + c.toOracleNumber(NETW_NUMBEROFSTAFFMALE) + "," + c.toOracleNumber(NETW_NUMBEROFSTAFFFEMALE) + "," + c.toOracleNumber(NETW_NUMBEROFATM) + "," + c.toOracleNumber(NETW_NUMBEROFPOS) + "," + c.toOracleNumber(NETW_DEBITCARDAMOUNT) + "," + c.toOracleNumber(NETW_DEBITCARDNUMOFCARD) + "," + c.toOracleNumber(NETW_CREDITCARDAMOUNT) + "," + c.toOracleNumber(NETW_CREDITCARDNUMOFCARD) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT34_TBT_NETWORKINFO" + c.NewLine() ;
myScript +="SET NETW_REPORTID=" + c.toOracleNumber(NETW_REPORTID) + " , NETW_CITIESBRANCHES=" + c.toORACLEString(NETW_CITIESBRANCHES) + " , NETW_NUMBRANCHESDISTRICT=" + c.toOracleNumber(NETW_NUMBRANCHESDISTRICT) + " , NETW_NUMBRANCHESCOMMUNE=" + c.toOracleNumber(NETW_NUMBRANCHESCOMMUNE) + " , NETW_LOANOUTSTANDAMT=" + c.toOracleNumber(NETW_LOANOUTSTANDAMT) + " , NETW_OUTSTANDBORROWER=" + c.toOracleNumber(NETW_OUTSTANDBORROWER) + " , NETW_OUTSTANDBORROWMALE=" + c.toOracleNumber(NETW_OUTSTANDBORROWMALE) + " , NETW_OUTSTANDBORROWFEMALE=" + c.toOracleNumber(NETW_OUTSTANDBORROWFEMALE) + " , NETW_DBAMT=" + c.toOracleNumber(NETW_DBAMT) + " , NETW_DBDEPOSITORTOTAL=" + c.toOracleNumber(NETW_DBDEPOSITORTOTAL) + " , NETW_DBDEPOSITORMALE=" + c.toOracleNumber(NETW_DBDEPOSITORMALE) + " , NETW_DBDEPOSITORFEMALE=" + c.toOracleNumber(NETW_DBDEPOSITORFEMALE) + " , NETW_NUMBEROFSTAFFTOTAL=" + c.toOracleNumber(NETW_NUMBEROFSTAFFTOTAL) + " , NETW_NUMBEROFSTAFFMALE=" + c.toOracleNumber(NETW_NUMBEROFSTAFFMALE) + " , NETW_NUMBEROFSTAFFFEMALE=" + c.toOracleNumber(NETW_NUMBEROFSTAFFFEMALE) + " , NETW_NUMBEROFATM=" + c.toOracleNumber(NETW_NUMBEROFATM) + " , NETW_NUMBEROFPOS=" + c.toOracleNumber(NETW_NUMBEROFPOS) + " , NETW_DEBITCARDAMOUNT=" + c.toOracleNumber(NETW_DEBITCARDAMOUNT) + " , NETW_DEBITCARDNUMOFCARD=" + c.toOracleNumber(NETW_DEBITCARDNUMOFCARD) + " , NETW_CREDITCARDAMOUNT=" + c.toOracleNumber(NETW_CREDITCARDAMOUNT) + " , NETW_CREDITCARDNUMOFCARD=" + c.toOracleNumber(NETW_CREDITCARDNUMOFCARD)+ c.NewLine();
myScript +="WHERE NETW_NO=" + c.toOracleNumber(NETW_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT34_TBT_NETWORKINFO" + c.NewLine() ;
myScript +="WHERE NETW_NO=" + c.toOracleNumber(NETW_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
NETW_NO=pResultSet.getString("NETW_NO");
NETW_REPORTID=pResultSet.getString("NETW_REPORTID");
NETW_CITIESBRANCHES=pResultSet.getString("NETW_CITIESBRANCHES");
NETW_NUMBRANCHESDISTRICT=pResultSet.getString("NETW_NUMBRANCHESDISTRICT");
NETW_NUMBRANCHESCOMMUNE=pResultSet.getString("NETW_NUMBRANCHESCOMMUNE");
NETW_LOANOUTSTANDAMT=pResultSet.getString("NETW_LOANOUTSTANDAMT");
NETW_OUTSTANDBORROWER=pResultSet.getString("NETW_OUTSTANDBORROWER");
NETW_OUTSTANDBORROWMALE=pResultSet.getString("NETW_OUTSTANDBORROWMALE");
NETW_OUTSTANDBORROWFEMALE=pResultSet.getString("NETW_OUTSTANDBORROWFEMALE");
NETW_DBAMT=pResultSet.getString("NETW_DBAMT");
NETW_DBDEPOSITORTOTAL=pResultSet.getString("NETW_DBDEPOSITORTOTAL");
NETW_DBDEPOSITORMALE=pResultSet.getString("NETW_DBDEPOSITORMALE");
NETW_DBDEPOSITORFEMALE=pResultSet.getString("NETW_DBDEPOSITORFEMALE");
NETW_NUMBEROFSTAFFTOTAL=pResultSet.getString("NETW_NUMBEROFSTAFFTOTAL");
NETW_NUMBEROFSTAFFMALE=pResultSet.getString("NETW_NUMBEROFSTAFFMALE");
NETW_NUMBEROFSTAFFFEMALE=pResultSet.getString("NETW_NUMBEROFSTAFFFEMALE");
NETW_NUMBEROFATM=pResultSet.getString("NETW_NUMBEROFATM");
NETW_NUMBEROFPOS=pResultSet.getString("NETW_NUMBEROFPOS");
NETW_DEBITCARDAMOUNT=pResultSet.getString("NETW_DEBITCARDAMOUNT");
NETW_DEBITCARDNUMOFCARD=pResultSet.getString("NETW_DEBITCARDNUMOFCARD");
NETW_CREDITCARDAMOUNT=pResultSet.getString("NETW_CREDITCARDAMOUNT");
NETW_CREDITCARDNUMOFCARD=pResultSet.getString("NETW_CREDITCARDNUMOFCARD");
}

public model_RPT34_TBT_NETWORKINFO() {
}

public String getNETW_NO() {
	return NETW_NO;
}
public String getNETW_REPORTID() {
	return NETW_REPORTID;
}
public String getNETW_CITIESBRANCHES() {
	return NETW_CITIESBRANCHES;
}
public String getNETW_NUMBRANCHESDISTRICT() {
	return NETW_NUMBRANCHESDISTRICT;
}
public String getNETW_NUMBRANCHESCOMMUNE() {
	return NETW_NUMBRANCHESCOMMUNE;
}
public String getNETW_LOANOUTSTANDAMT() {
	return NETW_LOANOUTSTANDAMT;
}
public String getNETW_OUTSTANDBORROWER() {
	return NETW_OUTSTANDBORROWER;
}
public String getNETW_OUTSTANDBORROWMALE() {
	return NETW_OUTSTANDBORROWMALE;
}
public String getNETW_OUTSTANDBORROWFEMALE() {
	return NETW_OUTSTANDBORROWFEMALE;
}
public String getNETW_DBAMT() {
	return NETW_DBAMT;
}
public String getNETW_DBDEPOSITORTOTAL() {
	return NETW_DBDEPOSITORTOTAL;
}
public String getNETW_DBDEPOSITORMALE() {
	return NETW_DBDEPOSITORMALE;
}
public String getNETW_DBDEPOSITORFEMALE() {
	return NETW_DBDEPOSITORFEMALE;
}
public String getNETW_NUMBEROFSTAFFTOTAL() {
	return NETW_NUMBEROFSTAFFTOTAL;
}
public String getNETW_NUMBEROFSTAFFMALE() {
	return NETW_NUMBEROFSTAFFMALE;
}
public String getNETW_NUMBEROFSTAFFFEMALE() {
	return NETW_NUMBEROFSTAFFFEMALE;
}
public String getNETW_NUMBEROFATM() {
	return NETW_NUMBEROFATM;
}
public String getNETW_NUMBEROFPOS() {
	return NETW_NUMBEROFPOS;
}
public String getNETW_DEBITCARDAMOUNT() {
	return NETW_DEBITCARDAMOUNT;
}
public String getNETW_DEBITCARDNUMOFCARD() {
	return NETW_DEBITCARDNUMOFCARD;
}
public String getNETW_CREDITCARDAMOUNT() {
	return NETW_CREDITCARDAMOUNT;
}
public String getNETW_CREDITCARDNUMOFCARD() {
	return NETW_CREDITCARDNUMOFCARD;
}

public void setNETW_NO(String pNETW_NO) {
	if (pNETW_NO.equals("")){myERROR.put("myERR_NETW_NO", "NetworkInfoID can not be null");myMessageError += "NetworkInfoID can not be null\n";}
	this.NETW_NO = pNETW_NO;
}
public void setNETW_REPORTID(String pNETW_REPORTID) {
	if (pNETW_REPORTID.equals("")){myERROR.put("myERR_NETW_REPORTID", "Report ID can not be null");myMessageError += "Report ID can not be null\n";}
	this.NETW_REPORTID = pNETW_REPORTID;
}
public void setNETW_CITIESBRANCHES(String pNETW_CITIESBRANCHES) {
	if (pNETW_CITIESBRANCHES.equals("")){myERROR.put("myERR_NETW_CITIESBRANCHES", "Provincial_CitiesBranches can not be null");myMessageError += "Provincial_CitiesBranches can not be null\n";}
	this.NETW_CITIESBRANCHES = pNETW_CITIESBRANCHES;
}
public void setNETW_NUMBRANCHESDISTRICT(String pNETW_NUMBRANCHESDISTRICT) {
	if (pNETW_NUMBRANCHESDISTRICT.equals("")){myERROR.put("myERR_NETW_NUMBRANCHESDISTRICT", "NumberBranchesDistrict can not be null");myMessageError += "NumberBranchesDistrict can not be null\n";}
	this.NETW_NUMBRANCHESDISTRICT = pNETW_NUMBRANCHESDISTRICT;
}
public void setNETW_NUMBRANCHESCOMMUNE(String pNETW_NUMBRANCHESCOMMUNE) {
	if (pNETW_NUMBRANCHESCOMMUNE.equals("")){myERROR.put("myERR_NETW_NUMBRANCHESCOMMUNE", "NumberBranchesCommune can not be null");myMessageError += "NumberBranchesCommune can not be null\n";}
	this.NETW_NUMBRANCHESCOMMUNE = pNETW_NUMBRANCHESCOMMUNE;
}
public void setNETW_LOANOUTSTANDAMT(String pNETW_LOANOUTSTANDAMT) {
	if (pNETW_LOANOUTSTANDAMT.equals("")){myERROR.put("myERR_NETW_LOANOUTSTANDAMT", "LoanOutstandingAmount can not be null");myMessageError += "LoanOutstandingAmount can not be null\n";}
	this.NETW_LOANOUTSTANDAMT = pNETW_LOANOUTSTANDAMT;
}
public void setNETW_OUTSTANDBORROWER(String pNETW_OUTSTANDBORROWER) {
	if (pNETW_OUTSTANDBORROWER.equals("")){myERROR.put("myERR_NETW_OUTSTANDBORROWER", "LoanOutstandingBorrowerTotal can not be null");myMessageError += "LoanOutstandingBorrowerTotal can not be null\n";}
	this.NETW_OUTSTANDBORROWER = pNETW_OUTSTANDBORROWER;
}
public void setNETW_OUTSTANDBORROWMALE(String pNETW_OUTSTANDBORROWMALE) {
	if (pNETW_OUTSTANDBORROWMALE.equals("")){myERROR.put("myERR_NETW_OUTSTANDBORROWMALE", "LoanOutstandingBorrowerMale can not be null");myMessageError += "LoanOutstandingBorrowerMale can not be null\n";}
	this.NETW_OUTSTANDBORROWMALE = pNETW_OUTSTANDBORROWMALE;
}
public void setNETW_OUTSTANDBORROWFEMALE(String pNETW_OUTSTANDBORROWFEMALE) {
	if (pNETW_OUTSTANDBORROWFEMALE.equals("")){myERROR.put("myERR_NETW_OUTSTANDBORROWFEMALE", "LoanOutstandingBorrowerFemale can not be null");myMessageError += "LoanOutstandingBorrowerFemale can not be null\n";}
	this.NETW_OUTSTANDBORROWFEMALE = pNETW_OUTSTANDBORROWFEMALE;
}
public void setNETW_DBAMT(String pNETW_DBAMT) {
	if (pNETW_DBAMT.equals("")){myERROR.put("myERR_NETW_DBAMT", "DepositBalanceAmount can not be null");myMessageError += "DepositBalanceAmount can not be null\n";}
	this.NETW_DBAMT = pNETW_DBAMT;
}
public void setNETW_DBDEPOSITORTOTAL(String pNETW_DBDEPOSITORTOTAL) {
	if (pNETW_DBDEPOSITORTOTAL.equals("")){myERROR.put("myERR_NETW_DBDEPOSITORTOTAL", "DepositBalanceDepositorTotal can not be null");myMessageError += "DepositBalanceDepositorTotal can not be null\n";}
	this.NETW_DBDEPOSITORTOTAL = pNETW_DBDEPOSITORTOTAL;
}
public void setNETW_DBDEPOSITORMALE(String pNETW_DBDEPOSITORMALE) {
	if (pNETW_DBDEPOSITORMALE.equals("")){myERROR.put("myERR_NETW_DBDEPOSITORMALE", "DepositBalanceDepositorMale can not be null");myMessageError += "DepositBalanceDepositorMale can not be null\n";}
	this.NETW_DBDEPOSITORMALE = pNETW_DBDEPOSITORMALE;
}
public void setNETW_DBDEPOSITORFEMALE(String pNETW_DBDEPOSITORFEMALE) {
	if (pNETW_DBDEPOSITORFEMALE.equals("")){myERROR.put("myERR_NETW_DBDEPOSITORFEMALE", "DepositBalanceDepositorFemale can not be null");myMessageError += "DepositBalanceDepositorFemale can not be null\n";}
	this.NETW_DBDEPOSITORFEMALE = pNETW_DBDEPOSITORFEMALE;
}
public void setNETW_NUMBEROFSTAFFTOTAL(String pNETW_NUMBEROFSTAFFTOTAL) {
	if (pNETW_NUMBEROFSTAFFTOTAL.equals("")){myERROR.put("myERR_NETW_NUMBEROFSTAFFTOTAL", "NumberOfStaffTotal can not be null");myMessageError += "NumberOfStaffTotal can not be null\n";}
	this.NETW_NUMBEROFSTAFFTOTAL = pNETW_NUMBEROFSTAFFTOTAL;
}
public void setNETW_NUMBEROFSTAFFMALE(String pNETW_NUMBEROFSTAFFMALE) {
	if (pNETW_NUMBEROFSTAFFMALE.equals("")){myERROR.put("myERR_NETW_NUMBEROFSTAFFMALE", "NumberOfStaffMale can not be null");myMessageError += "NumberOfStaffMale can not be null\n";}
	this.NETW_NUMBEROFSTAFFMALE = pNETW_NUMBEROFSTAFFMALE;
}
public void setNETW_NUMBEROFSTAFFFEMALE(String pNETW_NUMBEROFSTAFFFEMALE) {
	if (pNETW_NUMBEROFSTAFFFEMALE.equals("")){myERROR.put("myERR_NETW_NUMBEROFSTAFFFEMALE", "NumberOfStaffFemale can not be null");myMessageError += "NumberOfStaffFemale can not be null\n";}
	this.NETW_NUMBEROFSTAFFFEMALE = pNETW_NUMBEROFSTAFFFEMALE;
}
public void setNETW_NUMBEROFATM(String pNETW_NUMBEROFATM) {
	if (pNETW_NUMBEROFATM.equals("")){myERROR.put("myERR_NETW_NUMBEROFATM", "NumberOfATM can not be null");myMessageError += "NumberOfATM can not be null\n";}
	this.NETW_NUMBEROFATM = pNETW_NUMBEROFATM;
}
public void setNETW_NUMBEROFPOS(String pNETW_NUMBEROFPOS) {
	if (pNETW_NUMBEROFPOS.equals("")){myERROR.put("myERR_NETW_NUMBEROFPOS", "NumberOfPOS can not be null");myMessageError += "NumberOfPOS can not be null\n";}
	this.NETW_NUMBEROFPOS = pNETW_NUMBEROFPOS;
}
public void setNETW_DEBITCARDAMOUNT(String pNETW_DEBITCARDAMOUNT) {
	if (pNETW_DEBITCARDAMOUNT.equals("")){myERROR.put("myERR_NETW_DEBITCARDAMOUNT", "DebitCardAmount can not be null");myMessageError += "DebitCardAmount can not be null\n";}
	this.NETW_DEBITCARDAMOUNT = pNETW_DEBITCARDAMOUNT;
}
public void setNETW_DEBITCARDNUMOFCARD(String pNETW_DEBITCARDNUMOFCARD) {
	if (pNETW_DEBITCARDNUMOFCARD.equals("")){myERROR.put("myERR_NETW_DEBITCARDNUMOFCARD", "DebitCardNumOfCard can not be null");myMessageError += "DebitCardNumOfCard can not be null\n";}
	this.NETW_DEBITCARDNUMOFCARD = pNETW_DEBITCARDNUMOFCARD;
}
public void setNETW_CREDITCARDAMOUNT(String pNETW_CREDITCARDAMOUNT) {
	if (pNETW_CREDITCARDAMOUNT.equals("")){myERROR.put("myERR_NETW_CREDITCARDAMOUNT", "CreditCardAmount can not be null");myMessageError += "CreditCardAmount can not be null\n";}
	this.NETW_CREDITCARDAMOUNT = pNETW_CREDITCARDAMOUNT;
}
public void setNETW_CREDITCARDNUMOFCARD(String pNETW_CREDITCARDNUMOFCARD) {
	if (pNETW_CREDITCARDNUMOFCARD.equals("")){myERROR.put("myERR_NETW_CREDITCARDNUMOFCARD", "CreditCardNumOfCard can not be null");myMessageError += "CreditCardNumOfCard can not be null\n";}
	this.NETW_CREDITCARDNUMOFCARD = pNETW_CREDITCARDNUMOFCARD;
}

} ;
