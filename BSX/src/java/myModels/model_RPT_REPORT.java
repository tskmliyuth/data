package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT_REPORT{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String RPOT_NO;
private String RPOT_MEMBERID;
private String RPOT_DATEFROM;
private String RPOT_DATETO;
private String RPOT_DESC;
private String RPOT_EXC_RATE;
private String RPOT_UPLOAD_USERID;
private String RPOT_UPLOAD_DATE;
private String RPOT_DIRECTOR;
private String RPOT_TYPEID;
private String RPOT_FILEID;


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
String myScript ="SELECT RPOT_NO,RPOT_MEMBERID,NVL(to_char(RPOT_DATEFROM, 'yyyy/mm/dd hh24:mi:ss'),'') RPOT_DATEFROM,NVL(to_char(RPOT_DATETO, 'yyyy/mm/dd hh24:mi:ss'),'') RPOT_DATETO,RPOT_DESC,RPOT_EXC_RATE,RPOT_UPLOAD_USERID,NVL(to_char(RPOT_UPLOAD_DATE, 'yyyy/mm/dd hh24:mi:ss'),'') RPOT_UPLOAD_DATE,RPOT_DIRECTOR,RPOT_TYPEID,RPOT_FILEID FROM RPT_REPORT";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT_REPORT(RPOT_NO, RPOT_MEMBERID, RPOT_DATEFROM, RPOT_DATETO, RPOT_DESC, RPOT_EXC_RATE, RPOT_UPLOAD_USERID, RPOT_UPLOAD_DATE, RPOT_DIRECTOR, RPOT_TYPEID, RPOT_FILEID)"+ c.NewLine();
myScript +="VALUES(" + "RPT_REPORT_seq.NEXTVAL" + "," + c.toOracleNumber(RPOT_MEMBERID) + "," + c.toOracleDateTime(RPOT_DATEFROM) + "," + c.toOracleDateTime(RPOT_DATETO) + "," + c.toORACLEString(RPOT_DESC) + "," + c.toOracleNumber(RPOT_EXC_RATE) + "," + c.toOracleNumber(RPOT_UPLOAD_USERID) + "," + c.toOracleDateTime(RPOT_UPLOAD_DATE) + "," + c.toOracleNumber(RPOT_DIRECTOR) + "," + c.toOracleNumber(RPOT_TYPEID) + "," + c.toOracleNumber(RPOT_FILEID) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT_REPORT" + c.NewLine() ;
myScript +="SET RPOT_MEMBERID=" + c.toOracleNumber(RPOT_MEMBERID) + " , RPOT_DATEFROM=" + c.toOracleDateTime(RPOT_DATEFROM) + " , RPOT_DATETO=" + c.toOracleDateTime(RPOT_DATETO) + " , RPOT_DESC=" + c.toORACLEString(RPOT_DESC) + " , RPOT_EXC_RATE=" + c.toOracleNumber(RPOT_EXC_RATE) + " , RPOT_UPLOAD_USERID=" + c.toOracleNumber(RPOT_UPLOAD_USERID) + " , RPOT_UPLOAD_DATE=" + c.toOracleDateTime(RPOT_UPLOAD_DATE) + " , RPOT_DIRECTOR=" + c.toOracleNumber(RPOT_DIRECTOR) + " , RPOT_TYPEID=" + c.toOracleNumber(RPOT_TYPEID) + " , RPOT_FILEID=" + c.toOracleNumber(RPOT_FILEID)+ c.NewLine();
myScript +="WHERE RPOT_NO=" + c.toOracleNumber(RPOT_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT_REPORT" + c.NewLine() ;
myScript +="WHERE RPOT_NO=" + c.toOracleNumber(RPOT_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
RPOT_NO=pResultSet.getString("RPOT_NO");
RPOT_MEMBERID=pResultSet.getString("RPOT_MEMBERID");
RPOT_DATEFROM=pResultSet.getString("RPOT_DATEFROM");
RPOT_DATETO=pResultSet.getString("RPOT_DATETO");
RPOT_DESC=pResultSet.getString("RPOT_DESC");
RPOT_EXC_RATE=pResultSet.getString("RPOT_EXC_RATE");
RPOT_UPLOAD_USERID=pResultSet.getString("RPOT_UPLOAD_USERID");
RPOT_UPLOAD_DATE=pResultSet.getString("RPOT_UPLOAD_DATE");
RPOT_DIRECTOR=pResultSet.getString("RPOT_DIRECTOR");
RPOT_TYPEID=pResultSet.getString("RPOT_TYPEID");
RPOT_FILEID=pResultSet.getString("RPOT_FILEID");
}

public model_RPT_REPORT() {
}

public String getRPOT_NO() {
	return RPOT_NO;
}
public String getRPOT_MEMBERID() {
	return RPOT_MEMBERID;
}
public String getRPOT_DATEFROM() {
	return RPOT_DATEFROM;
}
public String getRPOT_DATETO() {
	return RPOT_DATETO;
}
public String getRPOT_DESC() {
	return RPOT_DESC;
}
public String getRPOT_EXC_RATE() {
	return RPOT_EXC_RATE;
}
public String getRPOT_UPLOAD_USERID() {
	return RPOT_UPLOAD_USERID;
}
public String getRPOT_UPLOAD_DATE() {
	return RPOT_UPLOAD_DATE;
}
public String getRPOT_DIRECTOR() {
	return RPOT_DIRECTOR;
}
public String getRPOT_TYPEID() {
	return RPOT_TYPEID;
}
public String getRPOT_FILEID() {
	return RPOT_FILEID;
}

public void setRPOT_NO(String pRPOT_NO) {
	if (pRPOT_NO.equals("")){myERROR.put("myERR_RPOT_NO", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.RPOT_NO = pRPOT_NO;
}
public void setRPOT_MEMBERID(String pRPOT_MEMBERID) {
	if (pRPOT_MEMBERID.equals("")){myERROR.put("myERR_RPOT_MEMBERID", "Member can not be null");myMessageError += "Member can not be null\n";}
	this.RPOT_MEMBERID = pRPOT_MEMBERID;
}
public void setRPOT_DATEFROM(String pRPOT_DATEFROM) {
	if (pRPOT_DATEFROM.equals("")){myERROR.put("myERR_RPOT_DATEFROM", "Report Date From can not be null");myMessageError += "Report Date From can not be null\n";}
	this.RPOT_DATEFROM = pRPOT_DATEFROM;
}
public void setRPOT_DATETO(String pRPOT_DATETO) {
	if (pRPOT_DATETO.equals("")){myERROR.put("myERR_RPOT_DATETO", "Report Date To can not be null");myMessageError += "Report Date To can not be null\n";}
	this.RPOT_DATETO = pRPOT_DATETO;
}
public void setRPOT_DESC(String pRPOT_DESC) {
	if (pRPOT_DESC.equals("")){myERROR.put("myERR_RPOT_DESC", "Description can not be null");myMessageError += "Description can not be null\n";}
	this.RPOT_DESC = pRPOT_DESC;
}
public void setRPOT_EXC_RATE(String pRPOT_EXC_RATE) {
	if (pRPOT_EXC_RATE.equals("")){myERROR.put("myERR_RPOT_EXC_RATE", "Exchange Rate can not be null");myMessageError += "Exchange Rate can not be null\n";}
	this.RPOT_EXC_RATE = pRPOT_EXC_RATE;
}
public void setRPOT_UPLOAD_USERID(String pRPOT_UPLOAD_USERID) {
	if (pRPOT_UPLOAD_USERID.equals("")){myERROR.put("myERR_RPOT_UPLOAD_USERID", "Uploaded User can not be null");myMessageError += "Uploaded User can not be null\n";}
	this.RPOT_UPLOAD_USERID = pRPOT_UPLOAD_USERID;
}
public void setRPOT_UPLOAD_DATE(String pRPOT_UPLOAD_DATE) {
	if (pRPOT_UPLOAD_DATE.equals("")){myERROR.put("myERR_RPOT_UPLOAD_DATE", "Uploaded Date can not be null");myMessageError += "Uploaded Date can not be null\n";}
	this.RPOT_UPLOAD_DATE = pRPOT_UPLOAD_DATE;
}
public void setRPOT_DIRECTOR(String pRPOT_DIRECTOR) {
	if (pRPOT_DIRECTOR.equals("")){myERROR.put("myERR_RPOT_DIRECTOR", "Director can not be null");myMessageError += "Director can not be null\n";}
	this.RPOT_DIRECTOR = pRPOT_DIRECTOR;
}
public void setRPOT_TYPEID(String pRPOT_TYPEID) {
	if (pRPOT_TYPEID.equals("")){myERROR.put("myERR_RPOT_TYPEID", "Report Type can not be null");myMessageError += "Report Type can not be null\n";}
	this.RPOT_TYPEID = pRPOT_TYPEID;
}
public void setRPOT_FILEID(String pRPOT_FILEID) {
	if (pRPOT_FILEID.equals("")){myERROR.put("myERR_RPOT_FILEID", "FILE Id can not be null");myMessageError += "FILE Id can not be null\n";}
	this.RPOT_FILEID = pRPOT_FILEID;
}

} ;