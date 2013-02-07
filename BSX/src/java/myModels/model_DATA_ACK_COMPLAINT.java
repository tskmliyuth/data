package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_ACK_COMPLAINT{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String COMP_NO;
private String COMP_MEMBERID;
private String COMP_DESC;
private String COMP_UPLOAD_USER_ID;
private String COMP_UPLOAD_DATE;
private String COMP_DIRECTOR;
private String COMP_TYPEID;
private String COMP_FILEID;
private String COMP_ORG_FILENAME;

    

  


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
String myScript ="SELECT COMP_NO,COMP_MEMBERID,COMP_DESC,COMP_UPLOAD_USER_ID,NVL(to_char(COMP_UPLOAD_DATE, 'yyyy/mm/dd hh24:mi:ss'),'') COMP_UPLOAD_DATE,COMP_DIRECTOR,COMP_TYPEID,COMP_FILEID FROM DATA_ACK_COMPLAINT";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_ACK_COMPLAINT(COMP_NO, COMP_MEMBERID, COMP_DESC, COMP_UPLOAD_USER_ID, COMP_UPLOAD_DATE, COMP_DIRECTOR, COMP_TYPEID, COMP_FILEID,COMP_ORG_FILENAME)"+ c.NewLine();
myScript +="VALUES(" + "DATA_ACK_COMPLAINT_seq.NEXTVAL" + "," + c.toOracleNumber(COMP_MEMBERID) + "," + c.toORACLEString(COMP_DESC) + "," + c.toOracleNumber(COMP_UPLOAD_USER_ID) + "," + c.toOracleDateTime(COMP_UPLOAD_DATE) + "," + c.toOracleNumber(COMP_DIRECTOR) + "," + c.toOracleNumber(COMP_TYPEID) + "," + c.toOracleNumber(COMP_FILEID) +","+c.toORACLEString(COMP_ORG_FILENAME)+ ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_ACK_COMPLAINT" + c.NewLine() ;
myScript +="SET COMP_MEMBERID=" + c.toOracleNumber(COMP_MEMBERID) + " , COMP_DESC=" + c.toORACLEString(COMP_DESC) + " , COMP_UPLOAD_USER_ID=" + c.toOracleNumber(COMP_UPLOAD_USER_ID) + " , COMP_UPLOAD_DATE=" + c.toOracleDateTime(COMP_UPLOAD_DATE) + " , COMP_DIRECTOR=" + c.toOracleNumber(COMP_DIRECTOR) + " , COMP_TYPEID=" + c.toOracleNumber(COMP_TYPEID) + " , COMP_FILEID=" + c.toOracleNumber(COMP_FILEID)+", COMP_ORG_FILENAME="+c.toORACLEString(COMP_ORG_FILENAME) + c.NewLine();
myScript +="WHERE COMP_NO=" + c.toOracleNumber(COMP_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_ACK_COMPLAINT" + c.NewLine() ;
myScript +="WHERE COMP_NO=" + c.toOracleNumber(COMP_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
COMP_NO=pResultSet.getString("COMP_NO");
COMP_MEMBERID=pResultSet.getString("COMP_MEMBERID");
COMP_DESC=pResultSet.getString("COMP_DESC");
COMP_UPLOAD_USER_ID=pResultSet.getString("COMP_UPLOAD_USER_ID");
COMP_UPLOAD_DATE=pResultSet.getString("COMP_UPLOAD_DATE");
COMP_DIRECTOR=pResultSet.getString("COMP_DIRECTOR");
COMP_TYPEID=pResultSet.getString("COMP_TYPEID");
COMP_FILEID=pResultSet.getString("COMP_FILEID");
COMP_ORG_FILENAME=pResultSet.getString("COMP_ORG_FILENAME");
}

public model_DATA_ACK_COMPLAINT() {
}

public String getCOMP_NO() {
	return COMP_NO;
}
public String getCOMP_MEMBERID() {
	return COMP_MEMBERID;
}
public String getCOMP_DESC() {
	return COMP_DESC;
}
public String getCOMP_UPLOAD_USER_ID() {
	return COMP_UPLOAD_USER_ID;
}
public String getCOMP_UPLOAD_DATE() {
	return COMP_UPLOAD_DATE;
}
public String getCOMP_DIRECTOR() {
	return COMP_DIRECTOR;
}
public String getCOMP_TYPEID() {
	return COMP_TYPEID;
}
public String getCOMP_FILEID() {
	return COMP_FILEID;
}
public String getCOMP_ORG_FILENAME() {
        return COMP_ORG_FILENAME;
    }
public void setCOMP_NO(String pCOMP_NO) {
	if (pCOMP_NO.equals("")){myERROR.put("myERR_COMP_NO", "COMPLAINT ID can not be null");myMessageError += "COMPLAINT ID can not be null\n";}
	this.COMP_NO = pCOMP_NO;
}
public void setCOMP_MEMBERID(String pCOMP_MEMBERID) {
	if (pCOMP_MEMBERID.equals("")){myERROR.put("myERR_COMP_MEMBERID", "MEMBER ID can not be null");myMessageError += "MEMBER ID can not be null\n";}
	this.COMP_MEMBERID = pCOMP_MEMBERID;
}
public void setCOMP_DESC(String pCOMP_DESC) {
	if (pCOMP_DESC.equals("")){myERROR.put("myERR_COMP_DESC", "DESCRIPTION can not be null");myMessageError += "DESCRIPTION can not be null\n";}
	this.COMP_DESC = pCOMP_DESC;
}
public void setCOMP_UPLOAD_USER_ID(String pCOMP_UPLOAD_USER_ID) {
	if (pCOMP_UPLOAD_USER_ID.equals("")){myERROR.put("myERR_COMP_UPLOAD_USER_ID", "UPLOAD USER ID can not be null");myMessageError += "UPLOAD USER ID can not be null\n";}
	this.COMP_UPLOAD_USER_ID = pCOMP_UPLOAD_USER_ID;
}
public void setCOMP_UPLOAD_DATE(String pCOMP_UPLOAD_DATE) {
	if (pCOMP_UPLOAD_DATE.equals("")){myERROR.put("myERR_COMP_UPLOAD_DATE", "DATE can not be null");myMessageError += "DATE can not be null\n";}
	this.COMP_UPLOAD_DATE = pCOMP_UPLOAD_DATE;
}
public void setCOMP_DIRECTOR(String pCOMP_DIRECTOR) {
	if (pCOMP_DIRECTOR.equals("")){myERROR.put("myERR_COMP_DIRECTOR", "DIRECTOR ID can not be null");myMessageError += "DIRECTOR ID can not be null\n";}
	this.COMP_DIRECTOR = pCOMP_DIRECTOR;
}
public void setCOMP_TYPEID(String pCOMP_TYPEID) {
	if (pCOMP_TYPEID.equals("")){myERROR.put("myERR_COMP_TYPEID", "TYPE ID can not be null");myMessageError += "TYPE ID can not be null\n";}
	this.COMP_TYPEID = pCOMP_TYPEID;
}
public void setCOMP_FILEID(String pCOMP_FILEID) {
	if (pCOMP_FILEID.equals("")){myERROR.put("myERR_COMP_FILEID", "FILE ID can not be null");myMessageError += "FILE ID can not be null\n";}
	this.COMP_FILEID = pCOMP_FILEID;
}
  public void setCOMP_ORG_FILENAME(String COMP_ORG_FILENAME) {
        this.COMP_ORG_FILENAME = COMP_ORG_FILENAME;
    }
} ;