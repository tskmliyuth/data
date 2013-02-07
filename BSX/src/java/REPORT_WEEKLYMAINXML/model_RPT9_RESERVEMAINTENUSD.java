package REPORT_WEEKLYMAINXML; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT9_RESERVEMAINTENUSD{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String RESM_NO;
private String RESM_REPORTID;
private String RESM_BASEDPERIOD;
private String RESM_MAINTENACEPERIOD;
private String RESM_REPORTINGPERIOD;
private String RESM_TOTAL;
private String RESM_DAILYAVG;
private String RESM_MINRESERVE;
private String RESM_MINSURPLUS;
private String RESM_DEFICIT;


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
String myScript ="SELECT RESM_NO,RESM_REPORTID,RESM_BASEDPERIOD,RESM_MAINTENACEPERIOD,RESM_REPORTINGPERIOD,RESM_TOTAL,RESM_DAILYAVG,RESM_MINRESERVE,RESM_MINSURPLUS,RESM_DEFICIT FROM RPT9_RESERVEMAINTENUSD";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT9_RESERVEMAINTENUSD(RESM_NO, RESM_REPORTID, RESM_BASEDPERIOD, RESM_MAINTENACEPERIOD, RESM_REPORTINGPERIOD, RESM_TOTAL, RESM_DAILYAVG, RESM_MINRESERVE, RESM_MINSURPLUS, RESM_DEFICIT)"+ c.NewLine();
myScript +="VALUES(" + "RPT9_RESERVEMAINTENUSD_seq.NEXTVAL" + "," + c.toOracleNumber(RESM_REPORTID) + "," + c.toORACLEString(RESM_BASEDPERIOD) + "," + c.toORACLEString(RESM_MAINTENACEPERIOD) + "," + c.toORACLEString(RESM_REPORTINGPERIOD) + "," + c.toOracleNumber(RESM_TOTAL) + "," + c.toOracleNumber(RESM_DAILYAVG) + "," + c.toOracleNumber(RESM_MINRESERVE) + "," + c.toOracleNumber(RESM_MINSURPLUS) + "," + c.toOracleNumber(RESM_DEFICIT) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT9_RESERVEMAINTENUSD" + c.NewLine() ;
myScript +="SET RESM_REPORTID=" + c.toOracleNumber(RESM_REPORTID) + " , RESM_BASEDPERIOD=" + c.toORACLEString(RESM_BASEDPERIOD) + " , RESM_MAINTENACEPERIOD=" + c.toORACLEString(RESM_MAINTENACEPERIOD) + " , RESM_REPORTINGPERIOD=" + c.toORACLEString(RESM_REPORTINGPERIOD) + " , RESM_TOTAL=" + c.toOracleNumber(RESM_TOTAL) + " , RESM_DAILYAVG=" + c.toOracleNumber(RESM_DAILYAVG) + " , RESM_MINRESERVE=" + c.toOracleNumber(RESM_MINRESERVE) + " , RESM_MINSURPLUS=" + c.toOracleNumber(RESM_MINSURPLUS) + " , RESM_DEFICIT=" + c.toOracleNumber(RESM_DEFICIT)+ c.NewLine();
myScript +="WHERE RESM_NO=" + c.toOracleNumber(RESM_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT9_RESERVEMAINTENUSD" + c.NewLine() ;
myScript +="WHERE RESM_NO=" + c.toOracleNumber(RESM_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
RESM_NO=pResultSet.getString("RESM_NO");
RESM_REPORTID=pResultSet.getString("RESM_REPORTID");
RESM_BASEDPERIOD=pResultSet.getString("RESM_BASEDPERIOD");
RESM_MAINTENACEPERIOD=pResultSet.getString("RESM_MAINTENACEPERIOD");
RESM_REPORTINGPERIOD=pResultSet.getString("RESM_REPORTINGPERIOD");
RESM_TOTAL=pResultSet.getString("RESM_TOTAL");
RESM_DAILYAVG=pResultSet.getString("RESM_DAILYAVG");
RESM_MINRESERVE=pResultSet.getString("RESM_MINRESERVE");
RESM_MINSURPLUS=pResultSet.getString("RESM_MINSURPLUS");
RESM_DEFICIT=pResultSet.getString("RESM_DEFICIT");
}

public model_RPT9_RESERVEMAINTENUSD() {
}

public String getRESM_NO() {
	return RESM_NO;
}
public String getRESM_REPORTID() {
	return RESM_REPORTID;
}
public String getRESM_BASEDPERIOD() {
	return RESM_BASEDPERIOD;
}
public String getRESM_MAINTENACEPERIOD() {
	return RESM_MAINTENACEPERIOD;
}
public String getRESM_REPORTINGPERIOD() {
	return RESM_REPORTINGPERIOD;
}
public String getRESM_TOTAL() {
	return RESM_TOTAL;
}
public String getRESM_DAILYAVG() {
	return RESM_DAILYAVG;
}
public String getRESM_MINRESERVE() {
	return RESM_MINRESERVE;
}
public String getRESM_MINSURPLUS() {
	return RESM_MINSURPLUS;
}
public String getRESM_DEFICIT() {
	return RESM_DEFICIT;
}

public void setRESM_NO(String pRESM_NO) {
	if (pRESM_NO.equals("")){myERROR.put("myERR_RESM_NO", "ReserverRequirementMainID can not be null");myMessageError += "ReserverRequirementMainID can not be null\n";}
	this.RESM_NO = pRESM_NO;
}
public void setRESM_REPORTID(String pRESM_REPORTID) {
	if (pRESM_REPORTID.equals("")){myERROR.put("myERR_RESM_REPORTID", "ReportID can not be null");myMessageError += "ReportID can not be null\n";}
	this.RESM_REPORTID = pRESM_REPORTID;
}
public void setRESM_BASEDPERIOD(String pRESM_BASEDPERIOD) {
	if (pRESM_BASEDPERIOD.equals("")){myERROR.put("myERR_RESM_BASEDPERIOD", "BasedPeriod can not be null");myMessageError += "BasedPeriod can not be null\n";}
	this.RESM_BASEDPERIOD = pRESM_BASEDPERIOD;
}
public void setRESM_MAINTENACEPERIOD(String pRESM_MAINTENACEPERIOD) {
	if (pRESM_MAINTENACEPERIOD.equals("")){myERROR.put("myERR_RESM_MAINTENACEPERIOD", "MaintenacePeriod can not be null");myMessageError += "MaintenacePeriod can not be null\n";}
	this.RESM_MAINTENACEPERIOD = pRESM_MAINTENACEPERIOD;
}
public void setRESM_REPORTINGPERIOD(String pRESM_REPORTINGPERIOD) {
	if (pRESM_REPORTINGPERIOD.equals("")){myERROR.put("myERR_RESM_REPORTINGPERIOD", "ReportingPeriod can not be null");myMessageError += "ReportingPeriod can not be null\n";}
	this.RESM_REPORTINGPERIOD = pRESM_REPORTINGPERIOD;
}
public void setRESM_TOTAL(String pRESM_TOTAL) {
	if (pRESM_TOTAL.equals("")){myERROR.put("myERR_RESM_TOTAL", "Total can not be null");myMessageError += "Total can not be null\n";}
	this.RESM_TOTAL = pRESM_TOTAL;
}
public void setRESM_DAILYAVG(String pRESM_DAILYAVG) {
	if (pRESM_DAILYAVG.equals("")){myERROR.put("myERR_RESM_DAILYAVG", "Daily Average can not be null");myMessageError += "Daily Average can not be null\n";}
	this.RESM_DAILYAVG = pRESM_DAILYAVG;
}
public void setRESM_MINRESERVE(String pRESM_MINRESERVE) {
	if (pRESM_MINRESERVE.equals("")){myERROR.put("myERR_RESM_MINRESERVE", "MinReserve Requirement can not be null");myMessageError += "MinReserve Requirement can not be null\n";}
	this.RESM_MINRESERVE = pRESM_MINRESERVE;
}
public void setRESM_MINSURPLUS(String pRESM_MINSURPLUS) {
	if (pRESM_MINSURPLUS.equals("")){myERROR.put("myERR_RESM_MINSURPLUS", "Reserve Requirement Surplus can not be null");myMessageError += "Reserve Requirement Surplus can not be null\n";}
	this.RESM_MINSURPLUS = pRESM_MINSURPLUS;
}
public void setRESM_DEFICIT(String pRESM_DEFICIT) {
	if (pRESM_DEFICIT.equals("")){myERROR.put("myERR_RESM_DEFICIT", "Reserver Requirement Deficit can not be null");myMessageError += "Reserver Requirement Deficit can not be null\n";}
	this.RESM_DEFICIT = pRESM_DEFICIT;
}

} ;