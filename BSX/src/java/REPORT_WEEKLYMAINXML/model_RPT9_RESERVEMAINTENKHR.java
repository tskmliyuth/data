package REPORT_WEEKLYMAINXML; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT9_RESERVEMAINTENKHR{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String REMA_NO;
private String REMA_REPORTID;
private String REMA_BASEDPERIOD;
private String REMA_MAINTENACEPERIOD;
private String REMA_REPORTINGPERIOD;
private String REMA_MINRESERVER;
private String REMA_SURPLUS;
private String REMA_DEFICIT ;


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
String myScript ="SELECT REMA_NO,REMA_REPORTID,REMA_BASEDPERIOD,REMA_MAINTENACEPERIOD,REMA_REPORTINGPERIOD,REMA_MINRESERVER,REMA_SURPLUS,REMA_DEFICIT  FROM RPT9_RESERVEMAINTENKHR";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT9_RESERVEMAINTENKHR(REMA_NO, REMA_REPORTID, REMA_BASEDPERIOD, REMA_MAINTENACEPERIOD, REMA_REPORTINGPERIOD, REMA_MINRESERVER, REMA_SURPLUS, REMA_DEFICIT )"+ c.NewLine();
myScript +="VALUES(" + "RPT9_RESERVEMAINTENKHR_seq.NEXTVAL" + "," + c.toOracleNumber(REMA_REPORTID) + "," + c.toORACLEString(REMA_BASEDPERIOD) + "," + c.toORACLEString(REMA_MAINTENACEPERIOD) + "," + c.toORACLEString(REMA_REPORTINGPERIOD) + "," + c.toOracleNumber(REMA_MINRESERVER) + "," + c.toOracleNumber(REMA_SURPLUS) + "," + c.toOracleNumber(REMA_DEFICIT ) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT9_RESERVEMAINTENKHR" + c.NewLine() ;
myScript +="SET REMA_REPORTID=" + c.toOracleNumber(REMA_REPORTID) + " , REMA_BASEDPERIOD=" + c.toORACLEString(REMA_BASEDPERIOD) + " , REMA_MAINTENACEPERIOD=" + c.toORACLEString(REMA_MAINTENACEPERIOD) + " , REMA_REPORTINGPERIOD=" + c.toORACLEString(REMA_REPORTINGPERIOD) + " , REMA_MINRESERVER=" + c.toOracleNumber(REMA_MINRESERVER) + " , REMA_SURPLUS=" + c.toOracleNumber(REMA_SURPLUS) + " , REMA_DEFICIT =" + c.toOracleNumber(REMA_DEFICIT )+ c.NewLine();
myScript +="WHERE REMA_NO=" + c.toOracleNumber(REMA_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT9_RESERVEMAINTENKHR" + c.NewLine() ;
myScript +="WHERE REMA_NO=" + c.toOracleNumber(REMA_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
REMA_NO=pResultSet.getString("REMA_NO");
REMA_REPORTID=pResultSet.getString("REMA_REPORTID");
REMA_BASEDPERIOD=pResultSet.getString("REMA_BASEDPERIOD");
REMA_MAINTENACEPERIOD=pResultSet.getString("REMA_MAINTENACEPERIOD");
REMA_REPORTINGPERIOD=pResultSet.getString("REMA_REPORTINGPERIOD");
REMA_MINRESERVER=pResultSet.getString("REMA_MINRESERVER");
REMA_SURPLUS=pResultSet.getString("REMA_SURPLUS");
REMA_DEFICIT =pResultSet.getString("REMA_DEFICIT ");
}

public model_RPT9_RESERVEMAINTENKHR() {
}

public String getREMA_NO() {
	return REMA_NO;
}
public String getREMA_REPORTID() {
	return REMA_REPORTID;
}
public String getREMA_BASEDPERIOD() {
	return REMA_BASEDPERIOD;
}
public String getREMA_MAINTENACEPERIOD() {
	return REMA_MAINTENACEPERIOD;
}
public String getREMA_REPORTINGPERIOD() {
	return REMA_REPORTINGPERIOD;
}
public String getREMA_MINRESERVER() {
	return REMA_MINRESERVER;
}
public String getREMA_SURPLUS() {
	return REMA_SURPLUS;
}
public String getREMA_DEFICIT () {
	return REMA_DEFICIT ;
}

public void setREMA_NO(String pREMA_NO) {
	if (pREMA_NO.equals("")){myERROR.put("myERR_REMA_NO", "ReserverRequirementMainID can not be null");myMessageError += "ReserverRequirementMainID can not be null\n";}
	this.REMA_NO = pREMA_NO;
}
public void setREMA_REPORTID(String pREMA_REPORTID) {
	if (pREMA_REPORTID.equals("")){myERROR.put("myERR_REMA_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.REMA_REPORTID = pREMA_REPORTID;
}
public void setREMA_BASEDPERIOD(String pREMA_BASEDPERIOD) {
	if (pREMA_BASEDPERIOD.equals("")){myERROR.put("myERR_REMA_BASEDPERIOD", "BasedPeriod can not be null");myMessageError += "BasedPeriod can not be null\n";}
	this.REMA_BASEDPERIOD = pREMA_BASEDPERIOD;
}
public void setREMA_MAINTENACEPERIOD(String pREMA_MAINTENACEPERIOD) {
	if (pREMA_MAINTENACEPERIOD.equals("")){myERROR.put("myERR_REMA_MAINTENACEPERIOD", "MaintenacePeriod can not be null");myMessageError += "MaintenacePeriod can not be null\n";}
	this.REMA_MAINTENACEPERIOD = pREMA_MAINTENACEPERIOD;
}
public void setREMA_REPORTINGPERIOD(String pREMA_REPORTINGPERIOD) {
	if (pREMA_REPORTINGPERIOD.equals("")){myERROR.put("myERR_REMA_REPORTINGPERIOD", "ReportingPeriod can not be null");myMessageError += "ReportingPeriod can not be null\n";}
	this.REMA_REPORTINGPERIOD = pREMA_REPORTINGPERIOD;
}
public void setREMA_MINRESERVER(String pREMA_MINRESERVER) {
	if (pREMA_MINRESERVER.equals("")){myERROR.put("myERR_REMA_MINRESERVER", "Min ReserveRequirement can not be null");myMessageError += "Min ReserveRequirement can not be null\n";}
	this.REMA_MINRESERVER = pREMA_MINRESERVER;
}
public void setREMA_SURPLUS(String pREMA_SURPLUS) {
	if (pREMA_SURPLUS.equals("")){myERROR.put("myERR_REMA_SURPLUS", "ReserveRequirementSurplus can not be null");myMessageError += "ReserveRequirementSurplus can not be null\n";}
	this.REMA_SURPLUS = pREMA_SURPLUS;
}
public void setREMA_DEFICIT (String pREMA_DEFICIT ) {
	if (pREMA_DEFICIT .equals("")){myERROR.put("myERR_REMA_DEFICIT ", "ReserveRequirementDeficit  can not be null");myMessageError += "ReserveRequirementDeficit  can not be null\n";}
	this.REMA_DEFICIT  = pREMA_DEFICIT ;
}

} ;
