package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;

public class model_DATA_INFO_HIST{

private clsConverter c=new clsConverter();
private Integer INHI_NO;
private String INHI_MESSAGE;
private Integer INHI_SYSUID;
private String INHI_SYSDATE;

public String getSelectScript(){
String myScript ="SELECT INHI_NO,INHI_MESSAGE,INHI_SYSUID,NVL(to_char(INHI_SYSDATE, 'yyyy/mm/dd'),'') INHI_SYSDATE FROM DATA_INFO_HIST";
return myScript;
}

public String getInsertScript(){
String myScript ="INSERT INTO DATA_INFO_HIST(INHI_MESSAGE, INHI_SYSUID, INHI_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + c.toORACLEString(INHI_MESSAGE) + "," + INHI_SYSUID + "," + c.toOracleDate(INHI_SYSDATE) + ")";
return myScript;
}

public String getUpdateScript(){
String myScript ="UPDATE DATA_INFO_HIST" + c.NewLine() ;
myScript +="SET INHI_MESSAGE=" + c.toORACLEString(INHI_MESSAGE) + " , INHI_SYSUID=" + INHI_SYSUID + " , INHI_SYSDATE=" + c.toOracleDate(INHI_SYSDATE)+ c.NewLine();
myScript +="WHERE INHI_NO=" + INHI_NO;
return myScript;
}

public String getDeleteScript(){
String myScript ="DELETE FROM DATA_INFO_HIST" + c.NewLine() ;
myScript +="WHERE INHI_NO=" + INHI_NO;
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
INHI_NO=pResultSet.getInt("INHI_NO");
INHI_MESSAGE=pResultSet.getString("INHI_MESSAGE");
INHI_SYSUID=pResultSet.getInt("INHI_SYSUID");
INHI_SYSDATE=pResultSet.getString("INHI_SYSDATE");
}

public model_DATA_INFO_HIST() {
	INHI_SYSDATE = "sysdate";
}

public Integer getINHI_NO() {
	return INHI_NO;
}
public void setINHI_NO(Integer pINHI_NO) {
	this.INHI_NO = pINHI_NO;
}
public String getINHI_MESSAGE() {
	return INHI_MESSAGE;
}
public void setINHI_MESSAGE(String pINHI_MESSAGE) {
	this.INHI_MESSAGE = pINHI_MESSAGE;
}
public Integer getINHI_SYSUID() {
	return INHI_SYSUID;
}
public void setINHI_SYSUID(Integer pINHI_SYSUID) {
	this.INHI_SYSUID = pINHI_SYSUID;
}
public String getINHI_SYSDATE() {
	return INHI_SYSDATE;
}
public void setINHI_SYSDATE(String pINHI_SYSDATE) {
	this.INHI_SYSDATE = pINHI_SYSDATE;
}

} ;