package REPORT_WEEKLYMAINXML; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT9_RESERVEMAINTENDETAILKHR{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String REMD_NO;
private String REMD_MAINID;
private String REMD_MAINTENPERIOD;
private String REMD_ACCOUNTBALANCE;
private String REMD_MINTHRESHOLDRESERVE;
private String REMD_DAILYCOMPULTHRESHOLD ;
private String REMD_CLEARINGACCBALANCE;
private String REMD_DAILYRESERVEREQUIRE;


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
String myScript ="SELECT REMD_NO,REMD_MAINID,NVL(to_char(REMD_MAINTENPERIOD, 'yyyy/mm/dd'),'') REMD_MAINTENPERIOD,REMD_ACCOUNTBALANCE,REMD_MINTHRESHOLDRESERVE,REMD_DAILYCOMPULTHRESHOLD ,REMD_CLEARINGACCBALANCE,REMD_DAILYRESERVEREQUIRE FROM RPT9_RESERVEMAINTENDETAILKHR";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT9_RESERVEMAINTENDETAILKHR(REMD_NO, REMD_MAINID, REMD_MAINTENPERIOD, REMD_ACCOUNTBALANCE, REMD_MINTHRESHOLDRESERVE, REMD_DAILYCOMPULTHRESHOLD , REMD_CLEARINGACCBALANCE, REMD_DAILYRESERVEREQUIRE)"+ c.NewLine();
myScript +="VALUES(" + "RPT9_RESERVEMETAILKHR_SEQ.NEXTVAL" + "," + c.toOracleNumber(REMD_MAINID) + "," + c.toOracleDate(REMD_MAINTENPERIOD) + "," + c.toOracleNumber(REMD_ACCOUNTBALANCE) + "," + c.toOracleNumber(REMD_MINTHRESHOLDRESERVE) + "," + c.toOracleNumber(REMD_DAILYCOMPULTHRESHOLD ) + "," + c.toOracleNumber(REMD_CLEARINGACCBALANCE) + "," + c.toOracleNumber(REMD_DAILYRESERVEREQUIRE) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT9_RESERVEMAINTENDETAILKHR" + c.NewLine() ;
myScript +="SET REMD_MAINID=" + c.toOracleNumber(REMD_MAINID) + " , REMD_MAINTENPERIOD=" + c.toOracleDate(REMD_MAINTENPERIOD) + " , REMD_ACCOUNTBALANCE=" + c.toOracleNumber(REMD_ACCOUNTBALANCE) + " , REMD_MINTHRESHOLDRESERVE=" + c.toOracleNumber(REMD_MINTHRESHOLDRESERVE) + " , REMD_DAILYCOMPULTHRESHOLD =" + c.toOracleNumber(REMD_DAILYCOMPULTHRESHOLD ) + " , REMD_CLEARINGACCBALANCE=" + c.toOracleNumber(REMD_CLEARINGACCBALANCE) + " , REMD_DAILYRESERVEREQUIRE=" + c.toOracleNumber(REMD_DAILYRESERVEREQUIRE)+ c.NewLine();
myScript +="WHERE REMD_NO=" + c.toOracleNumber(REMD_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT9_RESERVEMAINTENDETAILKHR" + c.NewLine() ;
myScript +="WHERE REMD_NO=" + c.toOracleNumber(REMD_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
REMD_NO=pResultSet.getString("REMD_NO");
REMD_MAINID=pResultSet.getString("REMD_MAINID");
REMD_MAINTENPERIOD=pResultSet.getString("REMD_MAINTENPERIOD");
REMD_ACCOUNTBALANCE=pResultSet.getString("REMD_ACCOUNTBALANCE");
REMD_MINTHRESHOLDRESERVE=pResultSet.getString("REMD_MINTHRESHOLDRESERVE");
REMD_DAILYCOMPULTHRESHOLD =pResultSet.getString("REMD_DAILYCOMPULTHRESHOLD ");
REMD_CLEARINGACCBALANCE=pResultSet.getString("REMD_CLEARINGACCBALANCE");
REMD_DAILYRESERVEREQUIRE=pResultSet.getString("REMD_DAILYRESERVEREQUIRE");
}

public model_RPT9_RESERVEMAINTENDETAILKHR() {
}

public String getREMD_NO() {
	return REMD_NO;
}
public String getREMD_MAINID() {
	return REMD_MAINID;
}
public String getREMD_MAINTENPERIOD() {
	return REMD_MAINTENPERIOD;
}
public String getREMD_ACCOUNTBALANCE() {
	return REMD_ACCOUNTBALANCE;
}
public String getREMD_MINTHRESHOLDRESERVE() {
	return REMD_MINTHRESHOLDRESERVE;
}
public String getREMD_DAILYCOMPULTHRESHOLD () {
	return REMD_DAILYCOMPULTHRESHOLD ;
}
public String getREMD_CLEARINGACCBALANCE() {
	return REMD_CLEARINGACCBALANCE;
}
public String getREMD_DAILYRESERVEREQUIRE() {
	return REMD_DAILYRESERVEREQUIRE;
}

public void setREMD_NO(String pREMD_NO) {
	if (pREMD_NO.equals("")){myERROR.put("myERR_REMD_NO", "ReserverRequirementDetailID can not be null");myMessageError += "ReserverRequirementDetailID can not be null\n";}
	this.REMD_NO = pREMD_NO;
}
public void setREMD_MAINID(String pREMD_MAINID) {
	if (pREMD_MAINID.equals("")){myERROR.put("myERR_REMD_MAINID", "ReserverRequirementMainID can not be null");myMessageError += "ReserverRequirementMainID can not be null\n";}
	this.REMD_MAINID = pREMD_MAINID;
}
public void setREMD_MAINTENPERIOD(String pREMD_MAINTENPERIOD) {
	if (pREMD_MAINTENPERIOD.equals("")){myERROR.put("myERR_REMD_MAINTENPERIOD", "MaintenancePeriod can not be null");myMessageError += "MaintenancePeriod can not be null\n";}
	this.REMD_MAINTENPERIOD = pREMD_MAINTENPERIOD;
}
public void setREMD_ACCOUNTBALANCE(String pREMD_ACCOUNTBALANCE) {
	if (pREMD_ACCOUNTBALANCE.equals("")){myERROR.put("myERR_REMD_ACCOUNTBALANCE", "ReserveRequireAccountBalance can not be null");myMessageError += "ReserveRequireAccountBalance can not be null\n";}
	this.REMD_ACCOUNTBALANCE = pREMD_ACCOUNTBALANCE;
}
public void setREMD_MINTHRESHOLDRESERVE(String pREMD_MINTHRESHOLDRESERVE) {
	if (pREMD_MINTHRESHOLDRESERVE.equals("")){myERROR.put("myERR_REMD_MINTHRESHOLDRESERVE", "MinThresholdReserve can not be null");myMessageError += "MinThresholdReserve can not be null\n";}
	this.REMD_MINTHRESHOLDRESERVE = pREMD_MINTHRESHOLDRESERVE;
}
public void setREMD_DAILYCOMPULTHRESHOLD (String pREMD_DAILYCOMPULTHRESHOLD ) {
	if (pREMD_DAILYCOMPULTHRESHOLD .equals("")){myERROR.put("myERR_REMD_DAILYCOMPULTHRESHOLD ", "DailyCompulsoryThreshold  can not be null");myMessageError += "DailyCompulsoryThreshold  can not be null\n";}
	this.REMD_DAILYCOMPULTHRESHOLD  = pREMD_DAILYCOMPULTHRESHOLD ;
}
public void setREMD_CLEARINGACCBALANCE(String pREMD_CLEARINGACCBALANCE) {
	if (pREMD_CLEARINGACCBALANCE.equals("")){myERROR.put("myERR_REMD_CLEARINGACCBALANCE", "ClearingAccountBalance can not be null");myMessageError += "ClearingAccountBalance can not be null\n";}
	this.REMD_CLEARINGACCBALANCE = pREMD_CLEARINGACCBALANCE;
}
public void setREMD_DAILYRESERVEREQUIRE(String pREMD_DAILYRESERVEREQUIRE) {
	if (pREMD_DAILYRESERVEREQUIRE.equals("")){myERROR.put("myERR_REMD_DAILYRESERVEREQUIRE", "DailyReserveRequirement can not be null");myMessageError += "DailyReserveRequirement can not be null\n";}
	this.REMD_DAILYRESERVEREQUIRE = pREMD_DAILYRESERVEREQUIRE;
}

} ;