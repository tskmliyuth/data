package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_ONSIDE_FILES{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String FIUP_NO;
private String FIUP_NAME;
private String FIUP_REPORT_DATE;
private String FIUP_DES;
private String FIUP_MEMB_NO;
private String FIUP_STATUS;
private String FIUP_DS;
private String FIUP_SIZE;
private String FIUP_CONTANT;
private String FIUP_ADS;
private String FIUP_AUID;
private String FIUP_ASYSDATE;
private String FIUP_SYSUID;
private String FIUP_SYSDATE;


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
String myScript ="SELECT FIUP_NO,FIUP_NAME,NVL(to_char(FIUP_REPORT_DATE, 'yyyy/mm/dd hh24:mi:ss'),'') FIUP_REPORT_DATE,FIUP_DES,FIUP_MEMB_NO,FIUP_STATUS,NVL(FIUP_DS,'') FIUP_DS,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT,NVL(FIUP_ADS,'') FIUP_ADS,NVL(FIUP_AUID,0) FIUP_AUID,NVL(to_char(FIUP_ASYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') FIUP_ASYSDATE,FIUP_SYSUID,NVL(to_char(FIUP_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') FIUP_SYSDATE FROM DATA_ONSIDE_FILES";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_ONSIDE_FILES(FIUP_NO, FIUP_NAME, FIUP_REPORT_DATE, FIUP_DES, FIUP_MEMB_NO, FIUP_STATUS, FIUP_DS, FIUP_SIZE, FIUP_CONTANT, FIUP_ADS, FIUP_AUID, FIUP_ASYSDATE, FIUP_SYSUID, FIUP_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + "DATA_ONSIDE_FILES_seq.NEXTVAL" + "," + c.toORACLEString(FIUP_NAME) + "," + "sysdate" + "," + c.toORACLEString(FIUP_DES) + "," + c.toOracleNumber(FIUP_MEMB_NO) + "," + c.toOracleNumber(FIUP_STATUS) + "," + c.toORACLEString(FIUP_DS) + "," + c.toOracleNumber(FIUP_SIZE) + "," + c.toORACLEString(FIUP_CONTANT) + "," + c.toORACLEString(FIUP_ADS) + "," + c.toOracleNumber(FIUP_AUID) + "," + c.toOracleDateTime(FIUP_ASYSDATE) + "," + c.toOracleNumber(FIUP_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_ONSIDE_FILES" + c.NewLine() ;
myScript +="SET FIUP_NAME=" + c.toORACLEString(FIUP_NAME) + " , FIUP_REPORT_DATE=" + "sysdate" + " , FIUP_DES=" + c.toORACLEString(FIUP_DES) + " , FIUP_MEMB_NO=" + c.toOracleNumber(FIUP_MEMB_NO) + " , FIUP_STATUS=" + c.toOracleNumber(FIUP_STATUS) + " , FIUP_DS=" + c.toORACLEString(FIUP_DS) + " , FIUP_SIZE=" + c.toOracleNumber(FIUP_SIZE) + " , FIUP_CONTANT=" + c.toORACLEString(FIUP_CONTANT) + " , FIUP_ADS=" + c.toORACLEString(FIUP_ADS) + " , FIUP_AUID=" + c.toOracleNumber(FIUP_AUID) + " , FIUP_ASYSDATE=" + c.toOracleDateTime(FIUP_ASYSDATE) + " , FIUP_SYSUID=" + c.toOracleNumber(FIUP_SYSUID) + " , FIUP_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE FIUP_NO=" + c.toOracleNumber(FIUP_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_ONSIDE_FILES" + c.NewLine() ;
myScript +="WHERE FIUP_NO=" + c.toOracleNumber(FIUP_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
FIUP_NO=pResultSet.getString("FIUP_NO");
FIUP_NAME=pResultSet.getString("FIUP_NAME");
FIUP_REPORT_DATE=pResultSet.getString("FIUP_REPORT_DATE");
FIUP_DES=pResultSet.getString("FIUP_DES");
FIUP_MEMB_NO=pResultSet.getString("FIUP_MEMB_NO");
FIUP_STATUS=pResultSet.getString("FIUP_STATUS");
FIUP_DS=pResultSet.getString("FIUP_DS");
FIUP_SIZE=pResultSet.getString("FIUP_SIZE");
FIUP_CONTANT=pResultSet.getString("FIUP_CONTANT");
FIUP_ADS=pResultSet.getString("FIUP_ADS");
FIUP_AUID=pResultSet.getString("FIUP_AUID");
FIUP_ASYSDATE=pResultSet.getString("FIUP_ASYSDATE");
FIUP_SYSUID=pResultSet.getString("FIUP_SYSUID");
FIUP_SYSDATE=pResultSet.getString("FIUP_SYSDATE");
}

public model_DATA_ONSIDE_FILES() {
	FIUP_REPORT_DATE = "sysdate";
	FIUP_SYSDATE = "sysdate";
}

public String getFIUP_NO() {
	return FIUP_NO;
}
public String getFIUP_NAME() {
	return FIUP_NAME;
}
public String getFIUP_REPORT_DATE() {
	return FIUP_REPORT_DATE;
}
public String getFIUP_DES() {
	return FIUP_DES;
}
public String getFIUP_MEMB_NO() {
	return FIUP_MEMB_NO;
}
public String getFIUP_STATUS() {
	return FIUP_STATUS;
}
public String getFIUP_DS() {
	return FIUP_DS;
}
public String getFIUP_SIZE() {
	return FIUP_SIZE;
}
public String getFIUP_CONTANT() {
	return FIUP_CONTANT;
}
public String getFIUP_ADS() {
	return FIUP_ADS;
}
public String getFIUP_AUID() {
	return FIUP_AUID;
}
public String getFIUP_ASYSDATE() {
	return FIUP_ASYSDATE;
}
public String getFIUP_SYSUID() {
	return FIUP_SYSUID;
}
public String getFIUP_SYSDATE() {
	return FIUP_SYSDATE;
}

public void setFIUP_NO(String pFIUP_NO) {
	if (pFIUP_NO.equals("")){myERROR.put("myERR_FIUP_NO", "No can not be null");myMessageError += "No can not be null\n";}
	this.FIUP_NO = pFIUP_NO;
}
public void setFIUP_NAME(String pFIUP_NAME) {
	if (pFIUP_NAME.equals("")){myERROR.put("myERR_FIUP_NAME", "Name can not be null");myMessageError += "Name can not be null\n";}
	this.FIUP_NAME = pFIUP_NAME;
}
public void setFIUP_REPORT_DATE(String pFIUP_REPORT_DATE) {
	if (pFIUP_REPORT_DATE.equals("")){myERROR.put("myERR_FIUP_REPORT_DATE", "Report Date can not be null");myMessageError += "Report Date can not be null\n";}
	this.FIUP_REPORT_DATE = pFIUP_REPORT_DATE;
}
public void setFIUP_DES(String pFIUP_DES) {
	if (pFIUP_DES.equals("")){myERROR.put("myERR_FIUP_DES", "Description can not be null");myMessageError += "Description can not be null\n";}
	this.FIUP_DES = pFIUP_DES;
}
public void setFIUP_MEMB_NO(String pFIUP_MEMB_NO) {
	if (pFIUP_MEMB_NO.equals("")){myERROR.put("myERR_FIUP_MEMB_NO", "Member Name can not be null");myMessageError += "Member Name can not be null\n";}
	this.FIUP_MEMB_NO = pFIUP_MEMB_NO;
}
public void setFIUP_STATUS(String pFIUP_STATUS) {
	if (pFIUP_STATUS.equals("")){myERROR.put("myERR_FIUP_STATUS", "Status can not be null");myMessageError += "Status can not be null\n";}
	this.FIUP_STATUS = pFIUP_STATUS;
}
public void setFIUP_DS(String pFIUP_DS) {
	this.FIUP_DS = pFIUP_DS;
}
public void setFIUP_SIZE(String pFIUP_SIZE) {
	this.FIUP_SIZE = pFIUP_SIZE;
}
public void setFIUP_CONTANT(String pFIUP_CONTANT) {
	this.FIUP_CONTANT = pFIUP_CONTANT;
}
public void setFIUP_ADS(String pFIUP_ADS) {
	this.FIUP_ADS = pFIUP_ADS;
}
public void setFIUP_AUID(String pFIUP_AUID) {
	this.FIUP_AUID = pFIUP_AUID;
}
public void setFIUP_ASYSDATE(String pFIUP_ASYSDATE) {
	this.FIUP_ASYSDATE = pFIUP_ASYSDATE;
}
public void setFIUP_SYSUID(String pFIUP_SYSUID) {
	if (pFIUP_SYSUID.equals("")){myERROR.put("myERR_FIUP_SYSUID", "User ID can not be null");myMessageError += "User ID can not be null\n";}
	this.FIUP_SYSUID = pFIUP_SYSUID;
}
public void setFIUP_SYSDATE(String pFIUP_SYSDATE) {
	if (pFIUP_SYSDATE.equals("")){myERROR.put("myERR_FIUP_SYSDATE", "System Date can not be null");myMessageError += "System Date can not be null\n";}
	this.FIUP_SYSDATE = pFIUP_SYSDATE;
}

} ;