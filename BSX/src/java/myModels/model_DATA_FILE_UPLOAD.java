package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;

public class model_DATA_FILE_UPLOAD{

private clsConverter c=new clsConverter();
private Long FIUP_NO;
private String FIUP_NAME;
private String FIUP_SESS_NO;
private Integer FIUP_DURA_NO;
private Integer FIUP_MEMB_NO;
private Integer FIUP_STATUS;
private String FIUP_DS;
private String FIUP_ADS;
private Long FIUP_SIZE;
private String FIUP_CONTANT;
private Integer FIUP_AUID;
private String FIUP_ASYSDATE;
private Integer FIUP_UID;
private String FIUP_SYSDATE;

public String getSelectScript(){
String myScript ="SELECT FIUP_NO,FIUP_NAME,FIUP_SESS_NO,FIUP_DURA_NO,FIUP_MEMB_NO,FIUP_STATUS,NVL(FIUP_DS,'') FIUP_DS,NVL(FIUP_ADS,'') FIUP_ADS,NVL(FIUP_SIZE,0) FIUP_SIZE,NVL(FIUP_CONTANT,'') FIUP_CONTANT,NVL(FIUP_AUID,0) FIUP_AUID,NVL(to_char(FIUP_ASYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') FIUP_ASYSDATE,FIUP_UID,NVL(to_char(FIUP_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') FIUP_SYSDATE FROM DATA_FILE_UPLOAD";
return myScript;
}

public String getInsertScript(){
String myScript ="INSERT INTO DATA_FILE_UPLOAD(FIUP_NO,FIUP_NAME, FIUP_SESS_NO, FIUP_DURA_NO, FIUP_MEMB_NO, FIUP_STATUS, FIUP_DS, FIUP_ADS, FIUP_SIZE, FIUP_CONTANT, FIUP_AUID, FIUP_ASYSDATE, FIUP_UID, FIUP_SYSDATE)"+ c.NewLine();
myScript +="VALUES(DATA_FILE_UPLOAD_SEQ.nextval," + c.toORACLEString(FIUP_NAME) + "," + c.toORACLEString(FIUP_SESS_NO) + "," + FIUP_DURA_NO + "," + FIUP_MEMB_NO + "," + FIUP_STATUS + "," + c.toORACLEString(FIUP_DS) + "," + c.toORACLEString(FIUP_ADS) + "," + FIUP_SIZE + "," + c.toORACLEString(FIUP_CONTANT) + "," + FIUP_AUID + "," + c.toOracleDateTime(FIUP_ASYSDATE) + "," + FIUP_UID + "," + FIUP_SYSDATE + ")";
return myScript;
}

public String getUpdateScript(){
String myScript ="UPDATE DATA_FILE_UPLOAD" + c.NewLine() ;
myScript +="SET FIUP_NAME=" + c.toORACLEString(FIUP_NAME) + " , FIUP_SESS_NO=" + c.toORACLEString(FIUP_SESS_NO) + " , FIUP_DURA_NO=" + FIUP_DURA_NO + " , FIUP_MEMB_NO=" + FIUP_MEMB_NO + " , FIUP_STATUS=" + FIUP_STATUS + " , FIUP_DS=" + c.toORACLEString(FIUP_DS) + " , FIUP_ADS=" + c.toORACLEString(FIUP_ADS) + " , FIUP_SIZE=" + FIUP_SIZE + " , FIUP_CONTANT=" + c.toORACLEString(FIUP_CONTANT) + " , FIUP_AUID=" + FIUP_AUID + " , FIUP_ASYSDATE=" + c.toOracleDateTime(FIUP_ASYSDATE) + " , FIUP_UID=" + FIUP_UID + " , FIUP_SYSDATE=" + c.toOracleDateTime(FIUP_SYSDATE)+ c.NewLine();
myScript +="WHERE FIUP_NO=" + FIUP_NO;
return myScript;
}

public String getDeleteScript(){
String myScript ="DELETE FROM DATA_FILE_UPLOAD" + c.NewLine() ;
myScript +="WHERE FIUP_NO=" + FIUP_NO;
return myScript;
}

public String getHTMLTable(){
String myScript ="<TR> <TD align='right'>" + getFIUP_NO() + "</TD><TD align='left'>" + getFIUP_NAME() + "</TD><TD align='left'>" + getFIUP_SESS_NO() + "</TD><TD align='right'>" + getFIUP_DURA_NO() + "</TD><TD align='right'>" + getFIUP_MEMB_NO() + "</TD><TD align='right'>" + getFIUP_STATUS() + "</TD><TD align='right'>" + getFIUP_SIZE() + "</TD><TD align='left'>" + getFIUP_CONTANT() + "</TD></TR>\n";
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
FIUP_NO=pResultSet.getLong("FIUP_NO");
FIUP_NAME=pResultSet.getString("FIUP_NAME");
FIUP_SESS_NO=pResultSet.getString("FIUP_SESS_NO");
FIUP_DURA_NO=pResultSet.getInt("FIUP_DURA_NO");
FIUP_MEMB_NO=pResultSet.getInt("FIUP_MEMB_NO");
FIUP_STATUS=pResultSet.getInt("FIUP_STATUS");
FIUP_DS=pResultSet.getString("FIUP_DS");
FIUP_ADS=pResultSet.getString("FIUP_ADS");
FIUP_SIZE=pResultSet.getLong("FIUP_SIZE");
FIUP_CONTANT=pResultSet.getString("FIUP_CONTANT");
FIUP_AUID=pResultSet.getInt("FIUP_AUID");
FIUP_ASYSDATE=pResultSet.getString("FIUP_ASYSDATE");
FIUP_UID=pResultSet.getInt("FIUP_UID");
FIUP_SYSDATE=pResultSet.getString("FIUP_SYSDATE");
}

public model_DATA_FILE_UPLOAD() {
	FIUP_SYSDATE = "sysdate";
}

public Long getFIUP_NO() {
	return FIUP_NO;
}
public void setFIUP_NO(Long pFIUP_NO) {
	this.FIUP_NO = pFIUP_NO;
}
public String getFIUP_NAME() {
	return FIUP_NAME;
}
public void setFIUP_NAME(String pFIUP_NAME) {
	this.FIUP_NAME = pFIUP_NAME;
}
public String getFIUP_SESS_NO() {
	return FIUP_SESS_NO;
}
public void setFIUP_SESS_NO(String pFIUP_SESS_NO) {
	this.FIUP_SESS_NO = pFIUP_SESS_NO;
}
public Integer getFIUP_DURA_NO() {
	return FIUP_DURA_NO;
}
public void setFIUP_DURA_NO(Integer pFIUP_DURA_NO) {
	this.FIUP_DURA_NO = pFIUP_DURA_NO;
}
public Integer getFIUP_MEMB_NO() {
	return FIUP_MEMB_NO;
}
public void setFIUP_MEMB_NO(Integer pFIUP_MEMB_NO) {
	this.FIUP_MEMB_NO = pFIUP_MEMB_NO;
}
public Integer getFIUP_STATUS() {
	return FIUP_STATUS;
}
public void setFIUP_STATUS(Integer pFIUP_STATUS) {
	this.FIUP_STATUS = pFIUP_STATUS;
}
public String getFIUP_DS() {
	return FIUP_DS;
}
public void setFIUP_DS(String pFIUP_DS) {
	this.FIUP_DS = pFIUP_DS;
}
public String getFIUP_ADS() {
	return FIUP_ADS;
}
public void setFIUP_ADS(String pFIUP_ADS) {
	this.FIUP_ADS = pFIUP_ADS;
}
public Long getFIUP_SIZE() {
	return FIUP_SIZE;
}
public void setFIUP_SIZE(Long pFIUP_SIZE) {
	this.FIUP_SIZE = pFIUP_SIZE;
}
public String getFIUP_CONTANT() {
	return FIUP_CONTANT;
}
public void setFIUP_CONTANT(String pFIUP_CONTANT) {
	this.FIUP_CONTANT = pFIUP_CONTANT;
}
public Integer getFIUP_AUID() {
	return FIUP_AUID;
}
public void setFIUP_AUID(Integer pFIUP_AUID) {
	this.FIUP_AUID = pFIUP_AUID;
}
public String getFIUP_ASYSDATE() {
	return FIUP_ASYSDATE;
}
public void setFIUP_ASYSDATE(String pFIUP_ASYSDATE) {
	this.FIUP_ASYSDATE = pFIUP_ASYSDATE;
}
public Integer getFIUP_UID() {
	return FIUP_UID;
}
public void setFIUP_UID(Integer pFIUP_UID) {
	this.FIUP_UID = pFIUP_UID;
}
public String getFIUP_SYSDATE() {
	return FIUP_SYSDATE;
}
public void setFIUP_SYSDATE(String pFIUP_SYSDATE) {
	this.FIUP_SYSDATE = pFIUP_SYSDATE;
}


} ;