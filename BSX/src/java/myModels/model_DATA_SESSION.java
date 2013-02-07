package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_SESSION{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String SESS_DURA_NO;
private String SESS_NO;
private String SESS_NAME;
private String SESS_RF;
private String SESS_RT;
private String SESS_OUP;
private String SESS_CUP;
private String SESS_SYSUID;
private String SESS_SYSDATE;


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
String myScript ="SELECT SESS_DURA_NO,SESS_NO,SESS_NAME,NVL(to_char(SESS_RF, 'yyyy/mm/dd'),'') SESS_RF,NVL(to_char(SESS_RT, 'yyyy/mm/dd'),'') SESS_RT,NVL(to_char(SESS_OUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_OUP,NVL(to_char(SESS_CUP, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_CUP,SESS_SYSUID,NVL(to_char(SESS_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') SESS_SYSDATE FROM DATA_SESSION";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_SESSION(SESS_DURA_NO, SESS_NO, SESS_NAME, SESS_RF, SESS_RT, SESS_OUP, SESS_CUP, SESS_SYSUID, SESS_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + c.toOracleNumber(SESS_DURA_NO) + "," + c.toORACLEString(SESS_NO) + "," + c.toORACLEString(SESS_NAME) + "," + c.toOracleDate(SESS_RF) + "," + c.toOracleDate(SESS_RT) + "," + c.toOracleDateTime(SESS_OUP) + "," + c.toOracleDateTime(SESS_CUP) + "," + c.toOracleNumber(SESS_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_SESSION" + c.NewLine() ;
myScript +="SET SESS_NO=" + c.toORACLEString(SESS_NO) + " , SESS_NAME=" + c.toORACLEString(SESS_NAME) + " , SESS_RF=" + c.toOracleDate(SESS_RF) + " , SESS_RT=" + c.toOracleDate(SESS_RT) + " , SESS_OUP=" + c.toOracleDateTime(SESS_OUP) + " , SESS_CUP=" + c.toOracleDateTime(SESS_CUP) + " , SESS_SYSUID=" + c.toOracleNumber(SESS_SYSUID) + " , SESS_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE SESS_DURA_NO=" + c.toOracleNumber(SESS_DURA_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_SESSION" + c.NewLine() ;
myScript +="WHERE SESS_DURA_NO=" + c.toOracleNumber(SESS_DURA_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
SESS_DURA_NO=pResultSet.getString("SESS_DURA_NO");
SESS_NO=pResultSet.getString("SESS_NO");
SESS_NAME=pResultSet.getString("SESS_NAME");
SESS_RF=pResultSet.getString("SESS_RF");
SESS_RT=pResultSet.getString("SESS_RT");
SESS_OUP=pResultSet.getString("SESS_OUP");
SESS_CUP=pResultSet.getString("SESS_CUP");
SESS_SYSUID=pResultSet.getString("SESS_SYSUID");
SESS_SYSDATE=pResultSet.getString("SESS_SYSDATE");
}

public model_DATA_SESSION() {
	SESS_SYSDATE = "sysdate";
}

public String getSESS_DURA_NO() {
	return SESS_DURA_NO;
}
public String getSESS_NO() {
	return SESS_NO;
}
public String getSESS_NAME() {
	return SESS_NAME;
}
public String getSESS_RF() {
	return SESS_RF;
}
public String getSESS_RT() {
	return SESS_RT;
}
public String getSESS_OUP() {
	return SESS_OUP;
}
public String getSESS_CUP() {
	return SESS_CUP;
}
public String getSESS_SYSUID() {
	return SESS_SYSUID;
}
public String getSESS_SYSDATE() {
	return SESS_SYSDATE;
}

public void setSESS_DURA_NO(String pSESS_DURA_NO) {
	if (pSESS_DURA_NO.equals("")){myERROR.put("myERR_SESS_DURA_NO", "Report Type can not be null");myMessageError += "Report Type can not be null\n";}
	this.SESS_DURA_NO = pSESS_DURA_NO;
}
public void setSESS_NO(String pSESS_NO) {
	if (pSESS_NO.equals("")){myERROR.put("myERR_SESS_NO", "No can not be null");myMessageError += "No can not be null\n";}
	this.SESS_NO = pSESS_NO;
}
public void setSESS_NAME(String pSESS_NAME) {
	if (pSESS_NAME.equals("")){myERROR.put("myERR_SESS_NAME", "Name can not be null");myMessageError += "Name can not be null\n";}
	this.SESS_NAME = pSESS_NAME;
}
public void setSESS_RF(String pSESS_RF) {
	if (pSESS_RF.equals("")){myERROR.put("myERR_SESS_RF", "Report from can not be null");myMessageError += "Report from can not be null\n";}
	this.SESS_RF = pSESS_RF;
}
public void setSESS_RT(String pSESS_RT) {
	if (pSESS_RT.equals("")){myERROR.put("myERR_SESS_RT", "To can not be null");myMessageError += "To can not be null\n";}
	this.SESS_RT = pSESS_RT;
}
public void setSESS_OUP(String pSESS_OUP) {
	if (pSESS_OUP.equals("")){myERROR.put("myERR_SESS_OUP", "Open from can not be null");myMessageError += "Open from can not be null\n";}
	this.SESS_OUP = pSESS_OUP;
}
public void setSESS_CUP(String pSESS_CUP) {
	if (pSESS_CUP.equals("")){myERROR.put("myERR_SESS_CUP", "Close can not be null");myMessageError += "Close can not be null\n";}
	this.SESS_CUP = pSESS_CUP;
}
public void setSESS_SYSUID(String pSESS_SYSUID) {
	if (pSESS_SYSUID.equals("")){myERROR.put("myERR_SESS_SYSUID", "User ID can not be null");myMessageError += "User ID can not be null\n";}
	this.SESS_SYSUID = pSESS_SYSUID;
}
public void setSESS_SYSDATE(String pSESS_SYSDATE) {
	if (pSESS_SYSDATE.equals("")){myERROR.put("myERR_SESS_SYSDATE", "System Date can not be null");myMessageError += "System Date can not be null\n";}
	this.SESS_SYSDATE = pSESS_SYSDATE;
}

} ;