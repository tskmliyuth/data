package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_SESSIONS{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String SERE_NO;
private String SERE_NAME;
private String SERE_DURA_NO;
private String SERE_SDATETIME;
private String SERE_EDATETIME;
private String SERE_OUP;
private String SERE_CUP;
private String SERE_SYSUID;
private String SERE_SYSDATE;


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
String myScript ="SELECT SERE_NO,SERE_NAME,SERE_DURA_NO,NVL(to_char(SERE_SDATETIME, 'yyyy/mm/dd'),'') SERE_SDATETIME,NVL(to_char(SERE_EDATETIME, 'yyyy/mm/dd'),'') SERE_EDATETIME,NVL(to_char(SERE_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_OUP,NVL(to_char(SERE_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_CUP,SERE_SYSUID,NVL(to_char(SERE_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SERE_SYSDATE FROM DATA_SESSIONS";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_SESSIONS(SERE_NO, SERE_NAME, SERE_DURA_NO, SERE_SDATETIME, SERE_EDATETIME, SERE_OUP, SERE_CUP, SERE_SYSUID, SERE_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + c.toORACLEString(SERE_NO) + "," + c.toORACLEString(SERE_NAME) + "," + c.toOracleNumber(SERE_DURA_NO) + "," + c.toOracleDate(SERE_SDATETIME) + "," + c.toOracleDate(SERE_EDATETIME) + "," + c.toOracleDateTime(SERE_OUP) + "," + c.toOracleDateTime(SERE_CUP) + "," + c.toOracleNumber(SERE_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_SESSIONS" + c.NewLine() ;
myScript +="SET SERE_NAME=" + c.toORACLEString(SERE_NAME) + " , SERE_DURA_NO=" + c.toOracleNumber(SERE_DURA_NO) + " , SERE_SDATETIME=" + c.toOracleDate(SERE_SDATETIME) + " , SERE_EDATETIME=" + c.toOracleDate(SERE_EDATETIME) + " , SERE_OUP=" + c.toOracleDateTime(SERE_OUP) + " , SERE_CUP=" + c.toOracleDateTime(SERE_CUP) + " , SERE_SYSUID=" + c.toOracleNumber(SERE_SYSUID) + " , SERE_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE SERE_NO=" + c.toORACLEString(SERE_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_SESSIONS" + c.NewLine() ;
myScript +="WHERE SERE_NO=" + c.toORACLEString(SERE_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
SERE_NO=pResultSet.getString("SERE_NO");
SERE_NAME=pResultSet.getString("SERE_NAME");
SERE_DURA_NO=pResultSet.getString("SERE_DURA_NO");
SERE_SDATETIME=pResultSet.getString("SERE_SDATETIME");
SERE_EDATETIME=pResultSet.getString("SERE_EDATETIME");
SERE_OUP=pResultSet.getString("SERE_OUP");
SERE_CUP=pResultSet.getString("SERE_CUP");
SERE_SYSUID=pResultSet.getString("SERE_SYSUID");
SERE_SYSDATE=pResultSet.getString("SERE_SYSDATE");
}

public model_DATA_SESSIONS() {
	SERE_SYSDATE = "sysdate";
}

public String getSERE_NO() {
	return SERE_NO;
}
public String getSERE_NAME() {
	return SERE_NAME;
}
public String getSERE_DURA_NO() {
	return SERE_DURA_NO;
}
public String getSERE_SDATETIME() {
	return SERE_SDATETIME;
}
public String getSERE_EDATETIME() {
	return SERE_EDATETIME;
}
public String getSERE_OUP() {
	return SERE_OUP;
}
public String getSERE_CUP() {
	return SERE_CUP;
}
public String getSERE_SYSUID() {
	return SERE_SYSUID;
}
public String getSERE_SYSDATE() {
	return SERE_SYSDATE;
}

public void setSERE_NO(String pSERE_NO) {
	if (pSERE_NO.equals("")){myERROR.put("myERR_SERE_NO", "No can not be null");myMessageError += "No can not be null\n";}
	this.SERE_NO = pSERE_NO;
}
public void setSERE_NAME(String pSERE_NAME) {
	if (pSERE_NAME.equals("")){myERROR.put("myERR_SERE_NAME", "Name can not be null");myMessageError += "Name can not be null\n";}
	this.SERE_NAME = pSERE_NAME;
}
public void setSERE_DURA_NO(String pSERE_DURA_NO) {
	if (pSERE_DURA_NO.equals("")){myERROR.put("myERR_SERE_DURA_NO", "Report Type can not be null");myMessageError += "Report Type can not be null\n";}
	this.SERE_DURA_NO = pSERE_DURA_NO;
}
public void setSERE_SDATETIME(String pSERE_SDATETIME) {
	if (pSERE_SDATETIME.equals("")){myERROR.put("myERR_SERE_SDATETIME", "Start Date can not be null");myMessageError += "Start Date can not be null\n";}
	this.SERE_SDATETIME = pSERE_SDATETIME;
}
public void setSERE_EDATETIME(String pSERE_EDATETIME) {
	if (pSERE_EDATETIME.equals("")){myERROR.put("myERR_SERE_EDATETIME", "End Date can not be null");myMessageError += "End Date can not be null\n";}
	this.SERE_EDATETIME = pSERE_EDATETIME;
}
public void setSERE_OUP(String pSERE_OUP) {
	if (pSERE_OUP.equals("")){myERROR.put("myERR_SERE_OUP", "Open upload can not be null");myMessageError += "Open upload can not be null\n";}
	this.SERE_OUP = pSERE_OUP;
}
public void setSERE_CUP(String pSERE_CUP) {
	if (pSERE_CUP.equals("")){myERROR.put("myERR_SERE_CUP", "Close upload can not be null");myMessageError += "Close upload can not be null\n";}
	this.SERE_CUP = pSERE_CUP;
}
public void setSERE_SYSUID(String pSERE_SYSUID) {
	if (pSERE_SYSUID.equals("")){myERROR.put("myERR_SERE_SYSUID", "User ID can not be null");myMessageError += "User ID can not be null\n";}
	this.SERE_SYSUID = pSERE_SYSUID;
}
public void setSERE_SYSDATE(String pSERE_SYSDATE) {
	if (pSERE_SYSDATE.equals("")){myERROR.put("myERR_SERE_SYSDATE", "System Date can not be null");myMessageError += "System Date can not be null\n";}
	this.SERE_SYSDATE = pSERE_SYSDATE;
}

} ;