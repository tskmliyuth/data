package myModels; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_DATA_INFO{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String INFO_RE_NO;
private String INFO_NO;
private String INFO_MESSAGE;


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
String myScript ="SELECT INFO_RE_NO,INFO_NO,INFO_MESSAGE FROM DATA_INFO";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO DATA_INFO(INFO_RE_NO, INFO_NO, INFO_MESSAGE)"+ c.NewLine();
myScript +="VALUES(" + c.toOracleNumber(INFO_RE_NO) + "," + c.toOracleNumber(INFO_NO) + "," + c.toORACLEString(INFO_MESSAGE) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_INFO" + c.NewLine() ;
myScript +="SET INFO_MESSAGE=" + c.toORACLEString(INFO_MESSAGE) + ",INFO_NO= DATA_INFOS_seq.CURRVAL " + c.NewLine();
myScript +="WHERE INFO_RE_NO=1"; 
return myScript;
}

public String getUpdateScript_For_Edit(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE DATA_INFO" + c.NewLine() ;
myScript +="SET INFO_NO=" + c.toOracleNumber(INFO_NO) + " , INFO_MESSAGE=" + c.toORACLEString(INFO_MESSAGE)+ c.NewLine();
myScript +="WHERE INFO_RE_NO=1";
return myScript;
}


public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM DATA_INFO" + c.NewLine() ;
myScript +="WHERE INFO_RE_NO=1";
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
INFO_RE_NO=pResultSet.getString("INFO_RE_NO");
INFO_NO=pResultSet.getString("INFO_NO");
INFO_MESSAGE=pResultSet.getString("INFO_MESSAGE");
}

public model_DATA_INFO() {
	INFO_MESSAGE = "sysdate";
}

public String getINFO_RE_NO() {
	return INFO_RE_NO;
}
public String getINFO_NO() {
	return INFO_NO;
}
public String getINFO_MESSAGE() {
	return INFO_MESSAGE;
}

public void setINFO_RE_NO(String pINFO_RE_NO) {
	if (pINFO_RE_NO.equals("")){myERROR.put("myERR_INFO_RE_NO", "Record No can not be null");myMessageError += "Record No can not be null\n";}
	this.INFO_RE_NO = pINFO_RE_NO;
}
public void setINFO_NO(String pINFO_NO) {
	if (pINFO_NO.equals("")){myERROR.put("myERR_INFO_NO", "NO can not be null");myMessageError += "NO can not be null\n";}
	this.INFO_NO = pINFO_NO;
}
public void setINFO_MESSAGE(String pINFO_MESSAGE) {
	if (pINFO_MESSAGE.equals("")){myERROR.put("myERR_INFO_MESSAGE", "MESSAGE can not be null");myMessageError += "MESSAGE can not be null\n";}
	this.INFO_MESSAGE = pINFO_MESSAGE;
}

} ;