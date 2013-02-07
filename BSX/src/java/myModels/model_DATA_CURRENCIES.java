package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_CURRENCIES{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String CURR_TRAN_NO;
private String CURR_TRAN_DATE;
private String CURR_NO;
private String CURR_UNIT;
private String CURR_BID;
private String CURR_ASK;
private String CURR_AVERAGE;
private String CURR_SYSUID;
private String CURR_SYSDATE;


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
String myScript ="SELECT CURR_TRAN_NO,NVL(to_char(CURR_TRAN_DATE, 'yyyy/mm/dd'),'') CURR_TRAN_DATE,CURR_NO,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE,CURR_SYSUID,NVL(to_char(CURR_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') CURR_SYSDATE FROM DATA_CURRENCIES";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_CURRENCIES(CURR_TRAN_NO, CURR_TRAN_DATE, CURR_NO, CURR_UNIT, CURR_BID, CURR_ASK, CURR_AVERAGE, CURR_SYSUID, CURR_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + c.toOracleNumber(CURR_TRAN_NO) + "," + c.toOracleDate(CURR_TRAN_DATE) + "," + c.toORACLEString(CURR_NO) + "," + c.toOracleNumber(CURR_UNIT) + "," + c.toOracleNumber(CURR_BID) + "," + c.toOracleNumber(CURR_ASK) + "," + c.toOracleNumber(CURR_AVERAGE) + "," + c.toOracleNumber(CURR_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateCurrencyScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_CURRENCY" + c.NewLine() ;
myScript +="SET CURR_UNIT=" + c.toOracleNumber(CURR_UNIT) + " , CURR_BID=" + c.toOracleNumber(CURR_BID) + " , CURR_ASK=" + c.toOracleNumber(CURR_ASK) + " , CURR_AVERAGE=" + c.toOracleNumber(CURR_AVERAGE) + " , CURR_SYSUID=" + c.toOracleNumber(CURR_SYSUID) + " , CURR_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE CURR_NO=" + c.toORACLEString(CURR_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_CURRENCIES" + c.NewLine() ;
myScript +="WHERE CURR_TRAN_NO=" + c.toOracleNumber(CURR_TRAN_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
CURR_TRAN_NO=pResultSet.getString("CURR_TRAN_NO");
CURR_TRAN_DATE=pResultSet.getString("CURR_TRAN_DATE");
CURR_NO=pResultSet.getString("CURR_NO");
CURR_UNIT=pResultSet.getString("CURR_UNIT");
CURR_BID=pResultSet.getString("CURR_BID");
CURR_ASK=pResultSet.getString("CURR_ASK");
CURR_AVERAGE=pResultSet.getString("CURR_AVERAGE");
CURR_SYSUID=pResultSet.getString("CURR_SYSUID");
CURR_SYSDATE=pResultSet.getString("CURR_SYSDATE");
}

public model_DATA_CURRENCIES() {
	CURR_SYSDATE = "sysdate";
}

public String getCURR_TRAN_NO() {
	return CURR_TRAN_NO;
}
public String getCURR_TRAN_DATE() {
	return CURR_TRAN_DATE;
}
public String getCURR_NO() {
	return CURR_NO;
}
public String getCURR_UNIT() {
	return CURR_UNIT;
}
public String getCURR_BID() {
	return CURR_BID;
}
public String getCURR_ASK() {
	return CURR_ASK;
}
public String getCURR_AVERAGE() {
	return CURR_AVERAGE;
}
public String getCURR_SYSUID() {
	return CURR_SYSUID;
}
public String getCURR_SYSDATE() {
	return CURR_SYSDATE;
}

public void setCURR_TRAN_NO(String pCURR_TRAN_NO) {
	if (pCURR_TRAN_NO.equals("")){myERROR.put("myERR_CURR_TRAN_NO", "Transaction No can not be null");myMessageError += "Transaction No can not be null\n";}
	this.CURR_TRAN_NO = pCURR_TRAN_NO;
}
public void setCURR_TRAN_DATE(String pCURR_TRAN_DATE) {
	if (pCURR_TRAN_DATE.equals("")){myERROR.put("myERR_CURR_TRAN_DATE", "Transaction Date can not be null");myMessageError += "Transaction Date can not be null\n";}
	this.CURR_TRAN_DATE = pCURR_TRAN_DATE;
}
public void setCURR_NO(String pCURR_NO) {
	if (pCURR_NO.equals("")){myERROR.put("myERR_CURR_NO", "No can not be null");myMessageError += "No can not be null\n";}
	this.CURR_NO = pCURR_NO;
}
public void setCURR_UNIT(String pCURR_UNIT) {
	if (pCURR_UNIT.equals("")){myERROR.put("myERR_CURR_UNIT", "Unit can not be null");myMessageError += "Unit can not be null\n";}
	this.CURR_UNIT = pCURR_UNIT;
}
public void setCURR_BID(String pCURR_BID) {
	if (pCURR_BID.equals("")){myERROR.put("myERR_CURR_BID", "Bid can not be null");myMessageError += "Bid can not be null\n";}
	this.CURR_BID = pCURR_BID;
}
public void setCURR_ASK(String pCURR_ASK) {
	if (pCURR_ASK.equals("")){myERROR.put("myERR_CURR_ASK", "Ask can not be null");myMessageError += "Ask can not be null\n";}
	this.CURR_ASK = pCURR_ASK;
}
public void setCURR_AVERAGE(String pCURR_AVERAGE) {
	if (pCURR_AVERAGE.equals("")){myERROR.put("myERR_CURR_AVERAGE", "Average can not be null");myMessageError += "Average can not be null\n";}
	this.CURR_AVERAGE = pCURR_AVERAGE;
}
public void setCURR_SYSUID(String pCURR_SYSUID) {
	if (pCURR_SYSUID.equals("")){myERROR.put("myERR_CURR_SYSUID", "User No can not be null");myMessageError += "User No can not be null\n";}
	this.CURR_SYSUID = pCURR_SYSUID;
}
public void setCURR_SYSDATE(String pCURR_SYSDATE) {
	if (pCURR_SYSDATE.equals("")){myERROR.put("myERR_CURR_SYSDATE", "System Date can not be null");myMessageError += "System Date can not be null\n";}
	this.CURR_SYSDATE = pCURR_SYSDATE;
}

} ;