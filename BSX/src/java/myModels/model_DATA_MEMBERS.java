package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_MEMBERS{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;
private String MEMB_NO;
private String MEMB_NAME;
private String MEMB_SNAME;
private String MEMB_BOD;
private String MEMB_BCDATE;
private String MEMB_STATUS;
private String MEMB_SYSUID;
private String MEMB_SYSDATE;


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
String myScript ="SELECT MEMB_NO,MEMB_NAME,MEMB_SNAME,NVL(to_char(MEMB_BOD, 'yyyy/mm/dd'),'') MEMB_BOD,NVL(to_char(MEMB_BCDATE, 'yyyy/mm/dd'),'') MEMB_BCDATE,MEMB_STATUS,MEMB_SYSUID,NVL(to_char(MEMB_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') MEMB_SYSDATE FROM DATA_MEMBERS";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_MEMBERS(MEMB_NO, MEMB_NAME, MEMB_SNAME, MEMB_BOD, MEMB_BCDATE, MEMB_STATUS, MEMB_SYSUID, MEMB_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + c.toOracleNumber(MEMB_NO) + "," + c.toORACLEString(MEMB_NAME) + "," + c.toORACLEString(MEMB_SNAME) + "," + c.toOracleDate(MEMB_BOD) + "," + c.toOracleDate(MEMB_BCDATE) + "," + c.toOracleNumber(MEMB_STATUS) + "," + c.toOracleNumber(MEMB_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_MEMBERS" + c.NewLine() ;
myScript +="SET MEMB_NAME=" + c.toORACLEString(MEMB_NAME) + " , MEMB_SNAME=" + c.toORACLEString(MEMB_SNAME) + " , MEMB_BOD=" + c.toOracleDate(MEMB_BOD) + " , MEMB_BCDATE=" + c.toOracleDate(MEMB_BCDATE) + " , MEMB_STATUS=" + c.toOracleNumber(MEMB_STATUS) + " , MEMB_SYSUID=" + c.toOracleNumber(MEMB_SYSUID) + " , MEMB_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE MEMB_NO=" + c.toOracleNumber(MEMB_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_MEMBERS" + c.NewLine() ;
myScript +="WHERE MEMB_NO=" + c.toOracleNumber(MEMB_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
MEMB_NO=pResultSet.getString("MEMB_NO");
MEMB_NAME=pResultSet.getString("MEMB_NAME");
MEMB_SNAME=pResultSet.getString("MEMB_SNAME");
MEMB_BOD=pResultSet.getString("MEMB_BOD");
MEMB_BCDATE=pResultSet.getString("MEMB_BCDATE");
MEMB_STATUS=pResultSet.getString("MEMB_STATUS");
MEMB_SYSUID=pResultSet.getString("MEMB_SYSUID");
MEMB_SYSDATE=pResultSet.getString("MEMB_SYSDATE");
}

public model_DATA_MEMBERS() {
	MEMB_STATUS = "1";
	MEMB_SYSDATE = "sysdate";
}

public String getMEMB_NO() {
	return MEMB_NO;
}
public String getMEMB_NAME() {
	return MEMB_NAME;
}
public String getMEMB_SNAME() {
	return MEMB_SNAME;
}
public String getMEMB_BOD() {
	return MEMB_BOD;
}
public String getMEMB_BCDATE() {
	return MEMB_BCDATE;
}
public String getMEMB_STATUS() {
	return MEMB_STATUS;
}
public String getMEMB_SYSUID() {
	return MEMB_SYSUID;
}
public String getMEMB_SYSDATE() {
	return MEMB_SYSDATE;
}

public void setMEMB_NO(String pMEMB_NO) {
	if (pMEMB_NO.equals("")){myERROR.put("myERR_MEMB_NO", "NO can not be null");myMessageError += "NO can not be null\n";}
	this.MEMB_NO = pMEMB_NO;
}
public void setMEMB_NAME(String pMEMB_NAME) {
	if (pMEMB_NAME.equals("")){myERROR.put("myERR_MEMB_NAME", "Full Name can not be null");myMessageError += "Full Name can not be null\n";}
	this.MEMB_NAME = pMEMB_NAME;
}
public void setMEMB_SNAME(String pMEMB_SNAME) {
	if (pMEMB_SNAME.equals("")){myERROR.put("myERR_MEMB_SNAME", "Sort Name can not be null");myMessageError += "Sort Name can not be null\n";}
	this.MEMB_SNAME = pMEMB_SNAME;
}
public void setMEMB_BOD(String pMEMB_BOD) {
	if (pMEMB_BOD.equals("")){myERROR.put("myERR_MEMB_BOD", "Date Created can not be null");myMessageError += "Date Created can not be null\n";}
	this.MEMB_BOD = pMEMB_BOD;
}
public void setMEMB_BCDATE(String pMEMB_BCDATE) {
	this.MEMB_BCDATE = pMEMB_BCDATE;
}
public void setMEMB_STATUS(String pMEMB_STATUS) {
	if (pMEMB_STATUS.equals("")){myERROR.put("myERR_MEMB_STATUS", "Status can not be null");myMessageError += "Status can not be null\n";}
	this.MEMB_STATUS = pMEMB_STATUS;
}
public void setMEMB_SYSUID(String pMEMB_SYSUID) {
	if (pMEMB_SYSUID.equals("")){myERROR.put("myERR_MEMB_SYSUID", "User No can not be null");myMessageError += "User No can not be null\n";}
	this.MEMB_SYSUID = pMEMB_SYSUID;
}
public void setMEMB_SYSDATE(String pMEMB_SYSDATE) {
	if (pMEMB_SYSDATE.equals("")){myERROR.put("myERR_MEMB_SYSDATE", "System Date can not be null");myMessageError += "System Date can not be null\n";}
	this.MEMB_SYSDATE = pMEMB_SYSDATE;
}

} ;