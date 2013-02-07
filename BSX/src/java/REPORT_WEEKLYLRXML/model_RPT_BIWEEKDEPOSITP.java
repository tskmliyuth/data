package REPORT_WEEKLYLRXML; 
import java.sql.ResultSet;
import java.sql.SQLException;
import myClasses.clsConverter;
import org.json.simple.JSONObject;

public class model_RPT_BIWEEKDEPOSITP{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String BIDP_NO;
private String BIDP_REPORTID;
private String BIDP_TYPE;
private String BIDP_NBANKOPENBL;
private String BIDP_NBANKDEPOSIT;
private String BIDP_NBANKWITHDRAWAL;
private String BIDP_NBANKENDBL;
private String BIDP_BANKOPENBL;
private String BIDP_BANKDEPOSIT;
private String BIDP_BANKWITHDRAWAL;
private String BIDP_ENDBALANCE;
private String BIDP_TOTAL;


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
String myScript ="SELECT BIDP_NO,BIDP_REPORTID,NVL(BIDP_TYPE,0) BIDP_TYPE,NVL(BIDP_NBANKOPENBL,0) BIDP_NBANKOPENBL,NVL(BIDP_NBANKDEPOSIT,0) BIDP_NBANKDEPOSIT,NVL(BIDP_NBANKWITHDRAWAL,0) BIDP_NBANKWITHDRAWAL,NVL(BIDP_NBANKENDBL,0) BIDP_NBANKENDBL,NVL(BIDP_BANKOPENBL,0) BIDP_BANKOPENBL,NVL(BIDP_BANKDEPOSIT,0) BIDP_BANKDEPOSIT,NVL(BIDP_BANKWITHDRAWAL,0) BIDP_BANKWITHDRAWAL,NVL(BIDP_ENDBALANCE,0) BIDP_ENDBALANCE,NVL(BIDP_TOTAL,0) BIDP_TOTAL FROM RPT39_BIWEEKDEPOSITP";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT39_BIWEEKDEPOSITP(BIDP_NO, BIDP_REPORTID, BIDP_TYPE, BIDP_NBANKOPENBL, BIDP_NBANKDEPOSIT, BIDP_NBANKWITHDRAWAL, BIDP_NBANKENDBL, BIDP_BANKOPENBL, BIDP_BANKDEPOSIT, BIDP_BANKWITHDRAWAL, BIDP_ENDBALANCE, BIDP_TOTAL)"+ c.NewLine();
myScript +="VALUES(" + "RPT_BIWEEKDEPOSITP_seq.NEXTVAL" + "," + c.toOracleNumber(BIDP_REPORTID) + "," + c.toOracleNumber(BIDP_TYPE) + "," + c.toOracleNumber(BIDP_NBANKOPENBL) + "," + c.toOracleNumber(BIDP_NBANKDEPOSIT) + "," + c.toOracleNumber(BIDP_NBANKWITHDRAWAL) + "," + c.toOracleNumber(BIDP_NBANKENDBL) + "," + c.toOracleNumber(BIDP_BANKOPENBL) + "," + c.toOracleNumber(BIDP_BANKDEPOSIT) + "," + c.toOracleNumber(BIDP_BANKWITHDRAWAL) + "," + c.toOracleNumber(BIDP_ENDBALANCE) + "," + c.toOracleNumber(BIDP_TOTAL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT39_BIWEEKDEPOSITP" + c.NewLine() ;
myScript +="SET BIDP_REPORTID=" + c.toOracleNumber(BIDP_REPORTID) + " , BIDP_TYPE=" + c.toOracleNumber(BIDP_TYPE) + " , BIDP_NBANKOPENBL=" + c.toOracleNumber(BIDP_NBANKOPENBL) + " , BIDP_NBANKDEPOSIT=" + c.toOracleNumber(BIDP_NBANKDEPOSIT) + " , BIDP_NBANKWITHDRAWAL=" + c.toOracleNumber(BIDP_NBANKWITHDRAWAL) + " , BIDP_NBANKENDBL=" + c.toOracleNumber(BIDP_NBANKENDBL) + " , BIDP_BANKOPENBL=" + c.toOracleNumber(BIDP_BANKOPENBL) + " , BIDP_BANKDEPOSIT=" + c.toOracleNumber(BIDP_BANKDEPOSIT) + " , BIDP_BANKWITHDRAWAL=" + c.toOracleNumber(BIDP_BANKWITHDRAWAL) + " , BIDP_ENDBALANCE=" + c.toOracleNumber(BIDP_ENDBALANCE) + " , BIDP_TOTAL=" + c.toOracleNumber(BIDP_TOTAL)+ c.NewLine();
myScript +="WHERE BIDP_NO=" + c.toOracleNumber(BIDP_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT39_BIWEEKDEPOSITP" + c.NewLine() ;
myScript +="WHERE BIDP_NO=" + c.toOracleNumber(BIDP_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
BIDP_NO=pResultSet.getString("BIDP_NO");
BIDP_REPORTID=pResultSet.getString("BIDP_REPORTID");
BIDP_TYPE=pResultSet.getString("BIDP_TYPE");
BIDP_NBANKOPENBL=pResultSet.getString("BIDP_NBANKOPENBL");
BIDP_NBANKDEPOSIT=pResultSet.getString("BIDP_NBANKDEPOSIT");
BIDP_NBANKWITHDRAWAL=pResultSet.getString("BIDP_NBANKWITHDRAWAL");
BIDP_NBANKENDBL=pResultSet.getString("BIDP_NBANKENDBL");
BIDP_BANKOPENBL=pResultSet.getString("BIDP_BANKOPENBL");
BIDP_BANKDEPOSIT=pResultSet.getString("BIDP_BANKDEPOSIT");
BIDP_BANKWITHDRAWAL=pResultSet.getString("BIDP_BANKWITHDRAWAL");
BIDP_ENDBALANCE=pResultSet.getString("BIDP_ENDBALANCE");
BIDP_TOTAL=pResultSet.getString("BIDP_TOTAL");
}

public model_RPT_BIWEEKDEPOSITP() {
}

public String getBIDP_NO() {
	return BIDP_NO;
}
public String getBIDP_REPORTID() {
	return BIDP_REPORTID;
}
public String getBIDP_TYPE() {
	return BIDP_TYPE;
}
public String getBIDP_NBANKOPENBL() {
	return BIDP_NBANKOPENBL;
}
public String getBIDP_NBANKDEPOSIT() {
	return BIDP_NBANKDEPOSIT;
}
public String getBIDP_NBANKWITHDRAWAL() {
	return BIDP_NBANKWITHDRAWAL;
}
public String getBIDP_NBANKENDBL() {
	return BIDP_NBANKENDBL;
}
public String getBIDP_BANKOPENBL() {
	return BIDP_BANKOPENBL;
}
public String getBIDP_BANKDEPOSIT() {
	return BIDP_BANKDEPOSIT;
}
public String getBIDP_BANKWITHDRAWAL() {
	return BIDP_BANKWITHDRAWAL;
}
public String getBIDP_ENDBALANCE() {
	return BIDP_ENDBALANCE;
}
public String getBIDP_TOTAL() {
	return BIDP_TOTAL;
}

public void setBIDP_NO(String pBIDP_NO) {
	if (pBIDP_NO.equals("")){myERROR.put("myERR_BIDP_NO", "FRCL_NO can not be null");myMessageError += "FRCL_NO can not be null\n";}
	this.BIDP_NO = pBIDP_NO;
}
public void setBIDP_REPORTID(String pBIDP_REPORTID) {
	if (pBIDP_REPORTID.equals("")){myERROR.put("myERR_BIDP_REPORTID", "Report ID can not be null");myMessageError += "Report ID can not be null\n";}
	this.BIDP_REPORTID = pBIDP_REPORTID;
}
public void setBIDP_TYPE(String pBIDP_TYPE) {
	this.BIDP_TYPE = pBIDP_TYPE;
}
public void setBIDP_NBANKOPENBL(String pBIDP_NBANKOPENBL) {
	this.BIDP_NBANKOPENBL = pBIDP_NBANKOPENBL;
}
public void setBIDP_NBANKDEPOSIT(String pBIDP_NBANKDEPOSIT) {
	this.BIDP_NBANKDEPOSIT = pBIDP_NBANKDEPOSIT;
}
public void setBIDP_NBANKWITHDRAWAL(String pBIDP_NBANKWITHDRAWAL) {
	this.BIDP_NBANKWITHDRAWAL = pBIDP_NBANKWITHDRAWAL;
}
public void setBIDP_NBANKENDBL(String pBIDP_NBANKENDBL) {
	this.BIDP_NBANKENDBL = pBIDP_NBANKENDBL;
}
public void setBIDP_BANKOPENBL(String pBIDP_BANKOPENBL) {
	this.BIDP_BANKOPENBL = pBIDP_BANKOPENBL;
}
public void setBIDP_BANKDEPOSIT(String pBIDP_BANKDEPOSIT) {
	this.BIDP_BANKDEPOSIT = pBIDP_BANKDEPOSIT;
}
public void setBIDP_BANKWITHDRAWAL(String pBIDP_BANKWITHDRAWAL) {
	this.BIDP_BANKWITHDRAWAL = pBIDP_BANKWITHDRAWAL;
}
public void setBIDP_ENDBALANCE(String pBIDP_ENDBALANCE) {
	this.BIDP_ENDBALANCE = pBIDP_ENDBALANCE;
}
public void setBIDP_TOTAL(String pBIDP_TOTAL) {
	this.BIDP_TOTAL = pBIDP_TOTAL;
}

} ;