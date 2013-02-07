package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_ENQUIRY{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String ENQY_NO;
private String ENQY_MEMBERID;
private String ENQY_USERID;
private String ENQY_STATUS;
private String ENQY_AUTHORIZERID;
private String ENQY_ENQUIRYDATE;
private String ENQY_ENQUIRYTYPEID;
private String ENQY_CONTACTNAME;
private String ENQY_CONTACTPOSITION;
private String ENQY_EMAIL;
private String ENQY_PHONE;
private String ENQY_CONTACTBYID;
private String ENQY_ENQURYDETAIL;


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
String myScript ="SELECT ENQY_NO,ENQY_MEMBERID,ENQY_USERID,ENQY_STATUS,ENQY_AUTHORIZERID,ENQY_ENQUIRYDATE,ENQY_ENQUIRYTYPEID,ENQY_CONTACTNAME,ENQY_CONTACTPOSITION,ENQY_EMAIL,ENQY_PHONE,ENQY_CONTACTBYID,ENQY_ENQURYDETAIL FROM DATA_ENQUIRY";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_ENQUIRY(ENQY_NO, ENQY_MEMBERID, ENQY_USERID, ENQY_STATUS, ENQY_AUTHORIZERID, ENQY_ENQUIRYDATE, ENQY_ENQUIRYTYPEID, ENQY_CONTACTNAME, ENQY_CONTACTPOSITION, ENQY_EMAIL, ENQY_PHONE, ENQY_CONTACTBYID, ENQY_ENQURYDETAIL)"+ c.NewLine();
myScript +="VALUES(" + "DATA_ENQUIRY_seq.NEXTVAL" + "," + c.toOracleNumber(ENQY_MEMBERID) + "," + c.toOracleNumber(ENQY_USERID) + "," + c.toOracleNumber(ENQY_STATUS) + "," + c.toOracleNumber(ENQY_AUTHORIZERID) + "," + c.toOracleNumber(ENQY_ENQUIRYDATE) + "," + c.toOracleNumber(ENQY_ENQUIRYTYPEID) + "," + c.toORACLEString(ENQY_CONTACTNAME) + "," + c.toORACLEString(ENQY_CONTACTPOSITION) + "," + c.toORACLEString(ENQY_EMAIL) + "," + c.toORACLEString(ENQY_PHONE) + "," + c.toOracleNumber(ENQY_CONTACTBYID) + "," + c.toORACLEString(ENQY_ENQURYDETAIL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_ENQUIRY" + c.NewLine() ;
myScript +="SET ENQY_MEMBERID=" + c.toOracleNumber(ENQY_MEMBERID) + " , ENQY_USERID=" + c.toOracleNumber(ENQY_USERID) + " , ENQY_STATUS=" + c.toOracleNumber(ENQY_STATUS) + " , ENQY_AUTHORIZERID=" + c.toOracleNumber(ENQY_AUTHORIZERID) + " , ENQY_ENQUIRYDATE=" + c.toOracleNumber(ENQY_ENQUIRYDATE) + " , ENQY_ENQUIRYTYPEID=" + c.toOracleNumber(ENQY_ENQUIRYTYPEID) + " , ENQY_CONTACTNAME=" + c.toORACLEString(ENQY_CONTACTNAME) + " , ENQY_CONTACTPOSITION=" + c.toORACLEString(ENQY_CONTACTPOSITION) + " , ENQY_EMAIL=" + c.toORACLEString(ENQY_EMAIL) + " , ENQY_PHONE=" + c.toORACLEString(ENQY_PHONE) + " , ENQY_CONTACTBYID=" + c.toOracleNumber(ENQY_CONTACTBYID) + " , ENQY_ENQURYDETAIL=" + c.toORACLEString(ENQY_ENQURYDETAIL)+ c.NewLine();
myScript +="WHERE ENQY_NO=" + c.toOracleNumber(ENQY_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_ENQUIRY" + c.NewLine() ;
myScript +="WHERE ENQY_NO=" + c.toOracleNumber(ENQY_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
ENQY_NO=pResultSet.getString("ENQY_NO");
ENQY_MEMBERID=pResultSet.getString("ENQY_MEMBERID");
ENQY_USERID=pResultSet.getString("ENQY_USERID");
ENQY_STATUS=pResultSet.getString("ENQY_STATUS");
ENQY_AUTHORIZERID=pResultSet.getString("ENQY_AUTHORIZERID");
ENQY_ENQUIRYDATE=pResultSet.getString("ENQY_ENQUIRYDATE");
ENQY_ENQUIRYTYPEID=pResultSet.getString("ENQY_ENQUIRYTYPEID");
ENQY_CONTACTNAME=pResultSet.getString("ENQY_CONTACTNAME");
ENQY_CONTACTPOSITION=pResultSet.getString("ENQY_CONTACTPOSITION");
ENQY_EMAIL=pResultSet.getString("ENQY_EMAIL");
ENQY_PHONE=pResultSet.getString("ENQY_PHONE");
ENQY_CONTACTBYID=pResultSet.getString("ENQY_CONTACTBYID");
ENQY_ENQURYDETAIL=pResultSet.getString("ENQY_ENQURYDETAIL");
}

public model_DATA_ENQUIRY() {
}

public String getENQY_NO() {
 return ENQY_NO;
}
public String getENQY_MEMBERID() {
 return ENQY_MEMBERID;
}
public String getENQY_USERID() {
 return ENQY_USERID;
}
public String getENQY_STATUS() {
 return ENQY_STATUS;
}
public String getENQY_AUTHORIZERID() {
 return ENQY_AUTHORIZERID;
}
public String getENQY_ENQUIRYDATE() {
 return ENQY_ENQUIRYDATE;
}
public String getENQY_ENQUIRYTYPEID() {
 return ENQY_ENQUIRYTYPEID;
}
public String getENQY_CONTACTNAME() {
 return ENQY_CONTACTNAME;
}
public String getENQY_CONTACTPOSITION() {
 return ENQY_CONTACTPOSITION;
}
public String getENQY_EMAIL() {
 return ENQY_EMAIL;
}
public String getENQY_PHONE() {
 return ENQY_PHONE;
}
public String getENQY_CONTACTBYID() {
 return ENQY_CONTACTBYID;
}
public String getENQY_ENQURYDETAIL() {
 return ENQY_ENQURYDETAIL;
}

public void setENQY_NO(String pENQY_NO) {
 if (pENQY_NO.equals("")){myERROR.put("myERR_ENQY_NO", "ENQUIRY DATA ID can not be null");myMessageError += "ENQUIRY DATA ID can not be null\n";}
 this.ENQY_NO = pENQY_NO;
}
public void setENQY_MEMBERID(String pENQY_MEMBERID) {
 if (pENQY_MEMBERID.equals("")){myERROR.put("myERR_ENQY_MEMBERID", "ENQUIRY DATA MEMBER ID can not be null");myMessageError += "ENQUIRY DATA MEMBER ID can not be null\n";}
 this.ENQY_MEMBERID = pENQY_MEMBERID;
}
public void setENQY_USERID(String pENQY_USERID) {
 if (pENQY_USERID.equals("")){myERROR.put("myERR_ENQY_USERID", "ENQUIRY DATA USER ID can not be null");myMessageError += "ENQUIRY DATA USER ID can not be null\n";}
 this.ENQY_USERID = pENQY_USERID;
}
public void setENQY_STATUS(String pENQY_STATUS) {
 if (pENQY_STATUS.equals("")){myERROR.put("myERR_ENQY_STATUS", "ENQUIRY DATA STATUS can not be null");myMessageError += "ENQUIRY DATA STATUS can not be null\n";}
 this.ENQY_STATUS = pENQY_STATUS;
}
public void setENQY_AUTHORIZERID(String pENQY_AUTHORIZERID) {
 if (pENQY_AUTHORIZERID.equals("")){myERROR.put("myERR_ENQY_AUTHORIZERID", "ENQUIRY DATA AUTHORIZER ID can not be null");myMessageError += "ENQUIRY DATA AUTHORIZER ID can not be null\n";}
 this.ENQY_AUTHORIZERID = pENQY_AUTHORIZERID;
}
public void setENQY_ENQUIRYDATE(String pENQY_ENQUIRYDATE) {
 if (pENQY_ENQUIRYDATE.equals("")){myERROR.put("myERR_ENQY_ENQUIRYDATE", "ENQUIRY DATA DATE can not be null");myMessageError += "ENQUIRY DATA DATE can not be null\n";}
 this.ENQY_ENQUIRYDATE = pENQY_ENQUIRYDATE;
}
public void setENQY_ENQUIRYTYPEID(String pENQY_ENQUIRYTYPEID) {
 if (pENQY_ENQUIRYTYPEID.equals("")){myERROR.put("myERR_ENQY_ENQUIRYTYPEID", "ENQUIRY DATA TYPE ID can not be null");myMessageError += "ENQUIRY DATA TYPE ID can not be null\n";}
 this.ENQY_ENQUIRYTYPEID = pENQY_ENQUIRYTYPEID;
}
public void setENQY_CONTACTNAME(String pENQY_CONTACTNAME) {
 if (pENQY_CONTACTNAME.equals("")){myERROR.put("myERR_ENQY_CONTACTNAME", "ENQUIRY DATA CONTACT NAME can not be null");myMessageError += "ENQUIRY DATA CONTACT NAME can not be null\n";}
 this.ENQY_CONTACTNAME = pENQY_CONTACTNAME;
}
public void setENQY_CONTACTPOSITION(String pENQY_CONTACTPOSITION) {
 if (pENQY_CONTACTPOSITION.equals("")){myERROR.put("myERR_ENQY_CONTACTPOSITION", "ENQUIRY DATA CONTACT POSITION can not be null");myMessageError += "ENQUIRY DATA CONTACT POSITION can not be null\n";}
 this.ENQY_CONTACTPOSITION = pENQY_CONTACTPOSITION;
}
public void setENQY_EMAIL(String pENQY_EMAIL) {
 if (pENQY_EMAIL.equals("")){myERROR.put("myERR_ENQY_EMAIL", "ENQUIRY DATA EMAIL can not be null");myMessageError += "ENQUIRY DATA EMAIL can not be null\n";}
 this.ENQY_EMAIL = pENQY_EMAIL;
}
public void setENQY_PHONE(String pENQY_PHONE) {
 if (pENQY_PHONE.equals("")){myERROR.put("myERR_ENQY_PHONE", "ENQUIRY DATA PHONE can not be null");myMessageError += "ENQUIRY DATA PHONE can not be null\n";}
 this.ENQY_PHONE = pENQY_PHONE;
}
public void setENQY_CONTACTBYID(String pENQY_CONTACTBYID) {
 if (pENQY_CONTACTBYID.equals("")){myERROR.put("myERR_ENQY_CONTACTBYID", "ENQUIRY DATA CONTACT BY ID can not be null");myMessageError += "ENQUIRY DATA CONTACT BY ID can not be null\n";}
 this.ENQY_CONTACTBYID = pENQY_CONTACTBYID;
}
public void setENQY_ENQURYDETAIL(String pENQY_ENQURYDETAIL) {
 if (pENQY_ENQURYDETAIL.equals("")){myERROR.put("myERR_ENQY_ENQURYDETAIL", "ENQUIRY DATA DETAIL can not be null");myMessageError += "ENQUIRY DATA DETAIL can not be null\n";}
 this.ENQY_ENQURYDETAIL = pENQY_ENQURYDETAIL;
}

} ;