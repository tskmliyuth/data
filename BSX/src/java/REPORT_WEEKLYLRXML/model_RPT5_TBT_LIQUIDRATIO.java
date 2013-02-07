/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYLRXML;

import java.sql.ResultSet;
import java.sql.SQLException;
import myClasses.clsConverter;
import org.json.simple.JSONObject;

public class model_RPT5_TBT_LIQUIDRATIO{

private clsConverter c=new clsConverter();
JSONObject myERROR=new JSONObject();
public boolean isERROR=false;
private String myMessageSeccessfully;
private String myMessageError;

private String LIRA_NO;
private String LIRA_REPORTID;
private String LIRA_LIQRATIOITEMID;
private String LIRA_AMOUNTINUSD;
private String LIRA_AMOUNTMILLIONRIEL;


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
String myScript ="SELECT LIRA_NO,LIRA_REPORTID,LIRA_LIQRATIOITEMID,LIRA_AMOUNTINUSD,LIRA_AMOUNTMILLIONRIEL FROM RPT5_TBT_LIQUIDRATIO";
return myScript;
}

public String getInsertScript(){
myMessageSeccessfully="Successfully saved";
String myScript ="INSERT INTO RPT5_TBT_LIQUIDRATIO(LIRA_NO, LIRA_REPORTID, LIRA_LIQRATIOITEMID, LIRA_AMOUNTINUSD, LIRA_AMOUNTMILLIONRIEL)"+ c.NewLine();
myScript +="VALUES(" + "RPT5_TBT_LIQUIDRATIO_seq.NEXTVAL" + "," + c.toOracleNumber(LIRA_REPORTID) + "," + c.toOracleNumber(LIRA_LIQRATIOITEMID) + "," + c.toOracleNumber(LIRA_AMOUNTINUSD) + "," + c.toOracleNumber(LIRA_AMOUNTMILLIONRIEL) + ")";
return myScript;
}

public String getUpdateScript(){
myMessageSeccessfully="Successfully updated";
String myScript ="UPDATE RPT5_TBT_LIQUIDRATIO" + c.NewLine() ;
myScript +="SET LIRA_REPORTID=" + c.toOracleNumber(LIRA_REPORTID) + " , LIRA_LIQRATIOITEMID=" + c.toOracleNumber(LIRA_LIQRATIOITEMID) + " , LIRA_AMOUNTINUSD=" + c.toOracleNumber(LIRA_AMOUNTINUSD) + " , LIRA_AMOUNTMILLIONRIEL=" + c.toOracleNumber(LIRA_AMOUNTMILLIONRIEL)+ c.NewLine();
myScript +="WHERE LIRA_NO=" + c.toOracleNumber(LIRA_NO);
return myScript;
}

public String getDeleteScript(){
myMessageSeccessfully="Successfully deleted";
String myScript ="DELETE FROM RPT5_TBT_LIQUIDRATIO" + c.NewLine() ;
myScript +="WHERE LIRA_NO=" + c.toOracleNumber(LIRA_NO);
return myScript;
}

public void setData(ResultSet pResultSet) throws SQLException{
LIRA_NO=pResultSet.getString("LIRA_NO");
LIRA_REPORTID=pResultSet.getString("LIRA_REPORTID");
LIRA_LIQRATIOITEMID=pResultSet.getString("LIRA_LIQRATIOITEMID");
LIRA_AMOUNTINUSD=pResultSet.getString("LIRA_AMOUNTINUSD");
LIRA_AMOUNTMILLIONRIEL=pResultSet.getString("LIRA_AMOUNTMILLIONRIEL");
}

public model_RPT5_TBT_LIQUIDRATIO() {
}

public String getLIRA_NO() {
	return LIRA_NO;
}
public String getLIRA_REPORTID() {
	return LIRA_REPORTID;
}
public String getLIRA_LIQRATIOITEMID() {
	return LIRA_LIQRATIOITEMID;
}
public String getLIRA_AMOUNTINUSD() {
	return LIRA_AMOUNTINUSD;
}
public String getLIRA_AMOUNTMILLIONRIEL() {
	return LIRA_AMOUNTMILLIONRIEL;
}

public void setLIRA_NO(String pLIRA_NO) {
	if (pLIRA_NO.equals("")){myERROR.put("myERR_LIRA_NO", " Liquidity Ratio ID can not be null");myMessageError += " Liquidity Ratio ID can not be null\n";}
	this.LIRA_NO = pLIRA_NO;
}
public void setLIRA_REPORTID(String pLIRA_REPORTID) {
	if (pLIRA_REPORTID.equals("")){myERROR.put("myERR_LIRA_REPORTID", " Report ID can not be null");myMessageError += " Report ID can not be null\n";}
	this.LIRA_REPORTID = pLIRA_REPORTID;
}
public void setLIRA_LIQRATIOITEMID(String pLIRA_LIQRATIOITEMID) {
	if (pLIRA_LIQRATIOITEMID.equals("")){myERROR.put("myERR_LIRA_LIQRATIOITEMID", " Liquidity Ratio Item ID can not be null");myMessageError += " Liquidity Ratio Item ID can not be null\n";}
	this.LIRA_LIQRATIOITEMID = pLIRA_LIQRATIOITEMID;
}
public void setLIRA_AMOUNTINUSD(String pLIRA_AMOUNTINUSD) {
	if (pLIRA_AMOUNTINUSD.equals("")){myERROR.put("myERR_LIRA_AMOUNTINUSD", " Amount In USD can not be null");myMessageError += " Amount In USD can not be null\n";}
	this.LIRA_AMOUNTINUSD = pLIRA_AMOUNTINUSD;
}
public void setLIRA_AMOUNTMILLIONRIEL(String pLIRA_AMOUNTMILLIONRIEL) {
	if (pLIRA_AMOUNTMILLIONRIEL.equals("")){myERROR.put("myERR_LIRA_AMOUNTMILLIONRIEL", " Amount In Million Riel can not be null");myMessageError += " Amount In Million Riel can not be null\n";}
	this.LIRA_AMOUNTMILLIONRIEL = pLIRA_AMOUNTMILLIONRIEL;
}

} ;