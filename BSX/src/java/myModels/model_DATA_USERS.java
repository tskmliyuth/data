package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_USERS{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String USER_NO;
private String USER_NAME;
private String USER_PWD;
private String USER_FULL_NAME;
private String USER_ROLE_NO;
private String USER_MEMB_NO;
private String USER_STATUS;
private String USER_ADDRESS;
private String USER_PHONE;
private String USER_FAX;
private String USER_EMAIL;
public String USER_CA;
public String USER_DS;
private String USER_SYSUID;
private String USER_SYSDATE;


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
String myScript ="SELECT USER_NO,USER_NAME,USER_PWD,NVL(USER_FULL_NAME,'') USER_FULL_NAME,USER_ROLE_NO,USER_MEMB_NO,USER_STATUS,NVL(USER_ADDRESS,'') USER_ADDRESS,NVL(USER_PHONE,'') USER_PHONE,NVL(USER_FAX,'') USER_FAX,NVL(USER_EMAIL,'') USER_EMAIL,USER_SYSUID,NVL(to_char(USER_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') USER_SYSDATE FROM DATA_USERS";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_USERS(USER_NO, USER_NAME, USER_PWD, USER_FULL_NAME, USER_ROLE_NO, USER_MEMB_NO, USER_STATUS, USER_ADDRESS, USER_PHONE, USER_FAX, USER_EMAIL, USER_SYSUID, USER_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + "DATA_USERS_seq.NEXTVAL" + "," + c.toORACLEString(USER_NAME) + "," + c.toORACLEString(USER_PWD) + "," + c.toORACLEString(USER_FULL_NAME) + "," + c.toOracleNumber(USER_ROLE_NO) + "," + c.toOracleNumber(USER_MEMB_NO) + "," + c.toOracleNumber(USER_STATUS) + "," + c.toORACLEString(USER_ADDRESS) + "," + c.toORACLEString(USER_PHONE) + "," + c.toORACLEString(USER_FAX) + "," + c.toORACLEString(USER_EMAIL) + "," + c.toOracleNumber(USER_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_USERS" + c.NewLine() ;
myScript +="SET USER_NAME=" + c.toORACLEString(USER_NAME)  + " , USER_FULL_NAME=" + c.toORACLEString(USER_FULL_NAME) + " , USER_ROLE_NO=" + c.toOracleNumber(USER_ROLE_NO) + " , USER_MEMB_NO=" + c.toOracleNumber(USER_MEMB_NO) + " , USER_STATUS=" + c.toOracleNumber(USER_STATUS) + " , USER_ADDRESS=" + c.toORACLEString(USER_ADDRESS) + " , USER_PHONE=" + c.toORACLEString(USER_PHONE) + " , USER_FAX=" + c.toORACLEString(USER_FAX) + " , USER_EMAIL=" + c.toORACLEString(USER_EMAIL)+ " , USER_CA=" + c.toORACLEString(USER_CA) + " , USER_DS=" + c.toORACLEString(USER_DS) + " , USER_SYSUID=" + c.toOracleNumber(USER_SYSUID) + " , USER_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE USER_NO=" + c.toOracleNumber(USER_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_USERS" + c.NewLine() ;
myScript +="WHERE USER_NO=" + "DATA_USERS_seq.NEXTVAL";
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
USER_NO=pResultSet.getString("USER_NO");
USER_NAME=pResultSet.getString("USER_NAME");
USER_PWD=pResultSet.getString("USER_PWD");
USER_FULL_NAME=pResultSet.getString("USER_FULL_NAME");
USER_ROLE_NO=pResultSet.getString("USER_ROLE_NO");
USER_MEMB_NO=pResultSet.getString("USER_MEMB_NO");
USER_STATUS=pResultSet.getString("USER_STATUS");
USER_ADDRESS=pResultSet.getString("USER_ADDRESS");
USER_PHONE=pResultSet.getString("USER_PHONE");
USER_FAX=pResultSet.getString("USER_FAX");
USER_EMAIL=pResultSet.getString("USER_EMAIL");
USER_SYSUID=pResultSet.getString("USER_SYSUID");
USER_SYSDATE=pResultSet.getString("USER_SYSDATE");
}

public model_DATA_USERS() {
	USER_SYSDATE = "sysdate";
}

public String getUSER_NO() {
	return USER_NO;
}
public String getUSER_NAME() {
	return USER_NAME;
}
public String getUSER_PWD() {
	return USER_PWD;
}
public String getUSER_FULL_NAME() {
	return USER_FULL_NAME;
}
public String getUSER_ROLE_NO() {
	return USER_ROLE_NO;
}
public String getUSER_MEMB_NO() {
	return USER_MEMB_NO;
}
public String getUSER_STATUS() {
	return USER_STATUS;
}
public String getUSER_ADDRESS() {
	return USER_ADDRESS;
}
public String getUSER_PHONE() {
	return USER_PHONE;
}
public String getUSER_FAX() {
	return USER_FAX;
}
public String getUSER_EMAIL() {
	return USER_EMAIL;
}
public String getUSER_CA() {
	return USER_CA;
}
public String getUSER_DS() {
	return USER_DS;
}
public String getUSER_SYSUID() {
	return USER_SYSUID;
}
public String getUSER_SYSDATE() {
	return USER_SYSDATE;
}

public void setUSER_NO(String pUSER_NO) {
	if (pUSER_NO.equals("")){myERROR.put("myERR_USER_NO", "User number can not be null");myMessageError += "User number can not be null\n";}
	this.USER_NO = pUSER_NO;
}
public void setUSER_NAME(String pUSER_NAME) {
	if (pUSER_NAME.equals("")){myERROR.put("myERR_USER_NAME", "User name can not be null");myMessageError += "User name can not be null\n";}
	this.USER_NAME = pUSER_NAME;
}
public void setUSER_PWD(String pUSER_PWD) {
	if (pUSER_PWD.equals("")){myERROR.put("myERR_USER_PWD", "Password can not be null");myMessageError += "Password can not be null\n";}
	this.USER_PWD = pUSER_PWD;
}
public void setUSER_FULL_NAME(String pUSER_FULL_NAME) {
	this.USER_FULL_NAME = pUSER_FULL_NAME;
}
public void setUSER_ROLE_NO(String pUSER_ROLE_NO) {
	if (pUSER_ROLE_NO.equals("")){myERROR.put("myERR_USER_ROLE_NO", "Role Name can not be null");myMessageError += "Role Name can not be null\n";}
	this.USER_ROLE_NO = pUSER_ROLE_NO;
}
public void setUSER_MEMB_NO(String pUSER_MEMB_NO) {
	if (pUSER_MEMB_NO.equals("")){myERROR.put("myERR_USER_MEMB_NO", "Member Name can not be null");myMessageError += "Member Name can not be null\n";}
	this.USER_MEMB_NO = pUSER_MEMB_NO;
}
public void setUSER_STATUS(String pUSER_STATUS) {
	if (pUSER_STATUS.equals("")){myERROR.put("myERR_USER_STATUS", "User Status can not be null");myMessageError += "User Status can not be null\n";}
	this.USER_STATUS = pUSER_STATUS;
}
public void setUSER_ADDRESS(String pUSER_ADDRESS) {
	this.USER_ADDRESS = pUSER_ADDRESS;
}
public void setUSER_PHONE(String pUSER_PHONE) {
	this.USER_PHONE = pUSER_PHONE;
}
public void setUSER_FAX(String pUSER_FAX) {
	this.USER_FAX = pUSER_FAX;
}
public void setUSER_EMAIL(String pUSER_EMAIL) {
	this.USER_EMAIL = pUSER_EMAIL;
}

public void setUSER_SYSUID(String pUSER_SYSUID) {
	if (pUSER_SYSUID.equals("")){myERROR.put("myERR_USER_SYSUID", "SYS_UID can not be null");myMessageError += "SYS_UID can not be null\n";}
	this.USER_SYSUID = pUSER_SYSUID;
}
public void setUSER_SYSDATE(String pUSER_SYSDATE) {
	if (pUSER_SYSDATE.equals("")){myERROR.put("myERR_USER_SYSDATE", "SYS_DATE can not be null");myMessageError += "SYS_DATE can not be null\n";}
	this.USER_SYSDATE = pUSER_SYSDATE;
}

} ;