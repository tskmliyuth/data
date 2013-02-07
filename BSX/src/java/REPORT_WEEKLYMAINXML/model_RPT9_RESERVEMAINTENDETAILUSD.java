package REPORT_WEEKLYMAINXML; 
import myClasses.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

public class model_RPT9_RESERVEMAINTENDETAILUSD{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String RRDE_NO;
private String RRDE_MAINID;
private String RRDE_MAINTENANCEPERIOD;
private String RRDE_RESERVEACCBALANCE;
private String RRDE_MINTHRESHOLD;
private String RRDE_DAILYCOMPUTHRESHOLD ;


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
String myScript ="SELECT RRDE_NO,RRDE_MAINID,NVL(to_char(RRDE_MAINTENANCEPERIOD, 'yyyy/mm/dd'),'') RRDE_MAINTENANCEPERIOD,RRDE_RESERVEACCBALANCE,RRDE_MINTHRESHOLD,RRDE_DAILYCOMPUTHRESHOLD  FROM RPT9_RESERVEMAINTENDETAILUSD";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT9_RESERVEMAINTENDETAILUSD(RRDE_NO, RRDE_MAINID, RRDE_MAINTENANCEPERIOD, RRDE_RESERVEACCBALANCE, RRDE_MINTHRESHOLD, RRDE_DAILYCOMPUTHRESHOLD )"+ c.NewLine();
myScript +="VALUES(" + "RPT9_RESERVEMDETAILUSD_SEQ.NEXTVAL" + "," + c.toOracleNumber(RRDE_MAINID) + "," + c.toOracleDate(RRDE_MAINTENANCEPERIOD) + "," + c.toOracleNumber(RRDE_RESERVEACCBALANCE) + "," + c.toOracleNumber(RRDE_MINTHRESHOLD) + "," + c.toOracleNumber(RRDE_DAILYCOMPUTHRESHOLD ) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT9_RESERVEMAINTENDETAILUSD" + c.NewLine() ;
myScript +="SET RRDE_MAINID=" + c.toOracleNumber(RRDE_MAINID) + " , RRDE_MAINTENANCEPERIOD=" + c.toOracleDate(RRDE_MAINTENANCEPERIOD) + " , RRDE_RESERVEACCBALANCE=" + c.toOracleNumber(RRDE_RESERVEACCBALANCE) + " , RRDE_MINTHRESHOLD=" + c.toOracleNumber(RRDE_MINTHRESHOLD) + " , RRDE_DAILYCOMPUTHRESHOLD =" + c.toOracleNumber(RRDE_DAILYCOMPUTHRESHOLD )+ c.NewLine();
myScript +="WHERE RRDE_NO=" + c.toOracleNumber(RRDE_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT9_RESERVEMAINTENDETAILUSD" + c.NewLine() ;
myScript +="WHERE RRDE_NO=" + c.toOracleNumber(RRDE_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
RRDE_NO=pResultSet.getString("RRDE_NO");
RRDE_MAINID=pResultSet.getString("RRDE_MAINID");
RRDE_MAINTENANCEPERIOD=pResultSet.getString("RRDE_MAINTENANCEPERIOD");
RRDE_RESERVEACCBALANCE=pResultSet.getString("RRDE_RESERVEACCBALANCE");
RRDE_MINTHRESHOLD=pResultSet.getString("RRDE_MINTHRESHOLD");
RRDE_DAILYCOMPUTHRESHOLD =pResultSet.getString("RRDE_DAILYCOMPUTHRESHOLD ");
}

public model_RPT9_RESERVEMAINTENDETAILUSD() {
}

public String getRRDE_NO() {
	return RRDE_NO;
}
public String getRRDE_MAINID() {
	return RRDE_MAINID;
}
public String getRRDE_MAINTENANCEPERIOD() {
	return RRDE_MAINTENANCEPERIOD;
}
public String getRRDE_RESERVEACCBALANCE() {
	return RRDE_RESERVEACCBALANCE;
}
public String getRRDE_MINTHRESHOLD() {
	return RRDE_MINTHRESHOLD;
}
public String getRRDE_DAILYCOMPUTHRESHOLD () {
	return RRDE_DAILYCOMPUTHRESHOLD ;
}

public void setRRDE_NO(String pRRDE_NO) {
	if (pRRDE_NO.equals("")){myERROR.put("myERR_RRDE_NO", "ReserverRequirementDetailID can not be null");myMessageError += "ReserverRequirementDetailID can not be null\n";}
	this.RRDE_NO = pRRDE_NO;
}
public void setRRDE_MAINID(String pRRDE_MAINID) {
	if (pRRDE_MAINID.equals("")){myERROR.put("myERR_RRDE_MAINID", "ReserverRequirementMainID can not be null");myMessageError += "ReserverRequirementMainID can not be null\n";}
	this.RRDE_MAINID = pRRDE_MAINID;
}
public void setRRDE_MAINTENANCEPERIOD(String pRRDE_MAINTENANCEPERIOD) {
	if (pRRDE_MAINTENANCEPERIOD.equals("")){myERROR.put("myERR_RRDE_MAINTENANCEPERIOD", "MaintenancePeriod can not be null");myMessageError += "MaintenancePeriod can not be null\n";}
	this.RRDE_MAINTENANCEPERIOD = pRRDE_MAINTENANCEPERIOD;
}
public void setRRDE_RESERVEACCBALANCE(String pRRDE_RESERVEACCBALANCE) {
	if (pRRDE_RESERVEACCBALANCE.equals("")){myERROR.put("myERR_RRDE_RESERVEACCBALANCE", "ReserveRequireAccountBalance can not be null");myMessageError += "ReserveRequireAccountBalance can not be null\n";}
	this.RRDE_RESERVEACCBALANCE = pRRDE_RESERVEACCBALANCE;
}
public void setRRDE_MINTHRESHOLD(String pRRDE_MINTHRESHOLD) {
	if (pRRDE_MINTHRESHOLD.equals("")){myERROR.put("myERR_RRDE_MINTHRESHOLD", "MinThresholdReserve can not be null");myMessageError += "MinThresholdReserve can not be null\n";}
	this.RRDE_MINTHRESHOLD = pRRDE_MINTHRESHOLD;
}
public void setRRDE_DAILYCOMPUTHRESHOLD (String pRRDE_DAILYCOMPUTHRESHOLD ) {
	if (pRRDE_DAILYCOMPUTHRESHOLD .equals("")){myERROR.put("myERR_RRDE_DAILYCOMPUTHRESHOLD ", "DailyCompulsoryThreshold  can not be null");myMessageError += "DailyCompulsoryThreshold  can not be null\n";}
	this.RRDE_DAILYCOMPUTHRESHOLD  = pRRDE_DAILYCOMPUTHRESHOLD ;
}

} ;