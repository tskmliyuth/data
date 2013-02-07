package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_INFOS{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String INHI_NO;
private String INHI_MESSAGE;
private String INHI_SYSUID;
private String INHI_SYSDATE;


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
String myScript ="SELECT INHI_NO,INHI_MESSAGE,INHI_SYSUID,NVL(to_char(INHI_SYSDATE, 'yyyy/mm/dd hh24:mi:ss'),'') INHI_SYSDATE FROM DATA_INFOS";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_INFOS(INHI_NO, INHI_MESSAGE, INHI_SYSUID, INHI_SYSDATE)"+ c.NewLine();
myScript +="VALUES(" + "DATA_INFOS_seq.NEXTVAL" + "," + c.toORACLEString(INHI_MESSAGE) + "," + c.toOracleNumber(INHI_SYSUID) + "," + "sysdate" + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_INFOS" + c.NewLine() ;
myScript +="SET INHI_MESSAGE=" + c.toORACLEString(INHI_MESSAGE) + " , INHI_SYSUID=" + c.toOracleNumber(INHI_SYSUID) + " , INHI_SYSDATE=" + "sysdate"+ c.NewLine();
myScript +="WHERE INHI_NO=" + c.toOracleNumber(INHI_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_INFOS" + c.NewLine() ;
myScript +="WHERE INHI_NO=" + "DATA_INFOS_seq.NEXTVAL";
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
INHI_NO=pResultSet.getString("INHI_NO");
INHI_MESSAGE=pResultSet.getString("INHI_MESSAGE");
INHI_SYSUID=pResultSet.getString("INHI_SYSUID");
INHI_SYSDATE=pResultSet.getString("INHI_SYSDATE");
}

public model_DATA_INFOS() {
	INHI_SYSDATE = "sysdate";
}

public String getINHI_NO() {
	return INHI_NO;
}
public String getINHI_MESSAGE() {
	return INHI_MESSAGE;
}
public String getINHI_SYSUID() {
	return INHI_SYSUID;
}
public String getINHI_SYSDATE() {
	return INHI_SYSDATE;
}

public void setINHI_NO(String pINHI_NO) {
	if (pINHI_NO.equals("")){myERROR.put("myERR_INHI_NO", "NO can not be null");myMessageError += "NO can not be null\n";}
	this.INHI_NO = pINHI_NO;
}
public void setINHI_MESSAGE(String pINHI_MESSAGE) {
	if (pINHI_MESSAGE.equals("")){myERROR.put("myERR_INHI_MESSAGE", "MESSAGE can not be null");myMessageError += "MESSAGE can not be null\n";}
	this.INHI_MESSAGE = pINHI_MESSAGE;
}
public void setINHI_SYSUID(String pINHI_SYSUID) {
	if (pINHI_SYSUID.equals("")){myERROR.put("myERR_INHI_SYSUID", "USER NO can not be null");myMessageError += "USER NO can not be null\n";}
	this.INHI_SYSUID = pINHI_SYSUID;
}
public void setINHI_SYSDATE(String pINHI_SYSDATE) {
	if (pINHI_SYSDATE.equals("")){myERROR.put("myERR_INHI_SYSDATE", "SYSDATE can not be null");myMessageError += "SYSDATE can not be null\n";}
	this.INHI_SYSDATE = pINHI_SYSDATE;
}

} ;