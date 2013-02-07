
package myClasses;

import java.io.IOException;
import java.io.StringWriter;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class clsConverter {
    public String JavaDateTimeFormat="yyyy/MM/dd HH:mm:ss";
    public String JavaDateFormat="yyyy/MM/dd";
    public String OracleDateTimeFormat="yyyy/mm/dd hh24:mi:ss";
    public String OracleDateFormat="yyyy/mm/dd";
        
    public String NewLine(){
        return "\n";
    }
    public String toHTMLString(String pStr){
        return pStr;
    }
    public  String toMYSQLString(String pString){
        String myStr =pString;
        myStr=myStr.replace("\\", "\\\\");
        myStr=myStr.replace("'", "\\'");
        myStr=myStr.replace("\"", "\\\"");
    return "'" + myStr + "'";    
    }
    public  String toORACLEString(String pString){
        String myStr =pString;
        myStr=myStr.replace("'", "''");
    return "'" + myStr + "'";   
    }
    public String toHTML(Object pObj){
        if (pObj==null){
            return "";
        }
        return pObj.toString();
    }    
    public String toOracleNumber(String pNumber){
        if (pNumber.equals("") || pNumber.equals(null)) {
        return "null";
        }
        return pNumber;
    }    
    public String toOracleDate(String pDate){
        if (pDate.equals("")) {
            return "null";
        }else if (pDate.equals("sysdate")){
            return "sysdate";
        }
        String myStr ="to_date('"+ pDate +"', '" + OracleDateFormat + "')";
        return myStr;
    }
    public String toOracleDate(Date pDate){
        DateFormat df = new SimpleDateFormat(JavaDateFormat); 
        String myStr ="to_date('"+ df.format(pDate) +"', '" + OracleDateFormat + "')";
        return myStr;
    }   
    
    public String toOracleDateTime(String pDate){
        if (pDate.equals("")) {
            return "null";
        }else if (pDate.equals("sysdate")){
            return "sysdate";
        }
        String myStr ="to_date('"+ pDate +"', '" + OracleDateTimeFormat + "')";
        return myStr;
    }
    public String toOracleDateTime(Date pDate){
        DateFormat df = new SimpleDateFormat(JavaDateTimeFormat); 
        String myStr ="to_date('"+ df.format(pDate) +"', '" + OracleDateTimeFormat + "')";
        return myStr;
    }
    //Select oracle date time
    public String seOracleDateTime(String pColumnName){
        String myStr="to_char("+ pColumnName +", '" + OracleDateTimeFormat + "') " + pColumnName;
        return myStr;
    }
    public String seOracleDate(String pColumnName){
        String myStr="to_char("+ pColumnName +", '" + OracleDateFormat + "') " + pColumnName;
        return myStr;
    }
    
    public String DateTimeToString(Date pDate){
        DateFormat df = new SimpleDateFormat(JavaDateTimeFormat);
        return (df.format(pDate));
    }
    
    public String DateToString(Date pDate){
        DateFormat df = new SimpleDateFormat(JavaDateFormat);
        return (df.format(pDate));
    }
    public String DateTimeFormat(Date pDate,String pFormate){
        DateFormat df = new SimpleDateFormat(pFormate);
        return (df.format(pDate));
    }
       
    public Date StringToDate(String pDate){
    try {
            DateFormat formatter=new SimpleDateFormat(JavaDateFormat);
            Date myDate= (Date)formatter.parse(pDate);
            return myDate;
        } catch (ParseException ex) {
            Logger.getLogger(clsConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
     
    public Date StringToDateTime(String pDate){
    try {
            DateFormat formatter=new SimpleDateFormat(JavaDateTimeFormat);
            Date myDate= (Date)formatter.parse(pDate);
            return myDate;
        } catch (ParseException ex) {
            Logger.getLogger(clsConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    public Integer CompareDate(Date pDate1,Date pDate2){         
        if (pDate1.getYear() != pDate2.getYear()) {
            return pDate1.getYear() - pDate2.getYear();
        }
        if (pDate1.getMonth() != pDate2.getMonth()) {
            return pDate1.getMonth() - pDate2.getMonth();
        }
        if (pDate1.getDate()!= pDate2.getDate()){
            return pDate1.getDate() - pDate2.getDate();
        }
        return 0;
    }
    
    public Integer CompareDateTime(Date pDate1,Date pDate2){         
        if (pDate1.getYear() != pDate2.getYear()) {
            return pDate1.getYear() - pDate2.getYear();
        }
        if (pDate1.getMonth() != pDate2.getMonth()) {
            return pDate1.getMonth() - pDate2.getMonth();
        }
        if (pDate1.getDate()!= pDate2.getDate()){
            return pDate1.getDate() - pDate2.getDate();
        }
        if (pDate1.getHours()!= pDate2.getHours()){
            return pDate1.getHours() - pDate2.getHours();
        }
        if (pDate1.getMinutes()!= pDate2.getMinutes()){
            return pDate1.getMinutes() - pDate2.getMinutes();
        }
        if (pDate1.getSeconds()!= pDate2.getSeconds()){
            return pDate1.getSeconds() - pDate2.getSeconds();
        }
        return 0;
    }
    public Integer CompareDateInBetween(Date pDate,Date pBeginDate,Date pEndDate){ 
        if (this.CompareDate(pDate,pBeginDate)>=0){
            if(this.CompareDate(pDate,pEndDate)<=0){
                return 0;
            }else{
                return 1;
            }           
        }else{
            return -1;
        }      
    }
    public Integer CompareDateTimeInBetween(Date pDate,Date pBeginDate,Date pEndDate){ 
        if (this.CompareDateTime(pDate,pBeginDate)>=0){
            if(this.CompareDateTime(pDate,pEndDate)<=0){
                return 0;
            }else{
                return 1;
            }           
        }else{
            return -1;
        }      
    }
    
    public Integer CompareDate(String pDate1,String pDate2){ 
         Date myDate1= this.StringToDate(pDate1);
         Date myDate2=this.StringToDate(pDate2);
         return this.CompareDate(myDate1,myDate2);        
    }
    public Integer CompareDateTime(String pDate1,String pDate2){ 
         Date myDate1= this.StringToDateTime(pDate1);
         Date myDate2=this.StringToDateTime(pDate2);
         return this.CompareDateTime(myDate1,myDate2);        
    }
    public Integer CompareDateInBetween(String pDate,String pBeginDate,String pEndDate){ 
         Date myDate= this.StringToDate(pDate);
         Date myBeginDate=this.StringToDate(pBeginDate);
         Date myEndDate=this.StringToDate(pEndDate);
         return this.CompareDateInBetween(myDate,myBeginDate,myEndDate);        
    }
    public Integer CompareDateTimeInBetween(String pDate,String pBeginDate,String pEndDate){ 
         Date myDate= this.StringToDateTime(pDate);
         Date myBeginDate=this.StringToDateTime(pBeginDate);
         Date myEndDate=this.StringToDateTime(pEndDate);
         return this.CompareDateTimeInBetween(myDate,myBeginDate,myEndDate);        
    }
    
    public Date Now(){
        Calendar currentDate = Calendar.getInstance();
        return currentDate.getTime();   
    }
    public String getDateTime(){
        DateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
        String myStr=formatter.format(this.Now());
        return myStr;
    }
    public String getSystemDate(String pDateFormate){
        DateFormat formatter=new SimpleDateFormat(pDateFormate);
        String myStr=formatter.format(this.Now());
        return myStr;
    }
    public String getSystemDate(){
        DateFormat formatter=new SimpleDateFormat(JavaDateFormat);
        String myStr=formatter.format(this.Now());
        return myStr;
    }
     public String getSystemDateTime(){
        DateFormat formatter=new SimpleDateFormat(JavaDateTimeFormat);
        String myStr=formatter.format(this.Now());
        return myStr;
    }
    public String FormatNumber(Integer pValue){    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    String myStr = formatter.format(pValue);  
    return myStr;
    } 
    public String FormatNumber(Double pValue,Integer pScale){
    String myScale="";
    for(int i=1;i<=pScale;i++){
        if (myScale.equals("")){
            myScale=".0";
        }else{
            myScale += "0";
        }            
    }
    NumberFormat formatter = new DecimalFormat("#,###,###" + myScale);
    String myStr = formatter.format(pValue);  
    return myStr;
    }
    
    public String JsonToString(JSONObject pJSon){
    StringWriter out = new StringWriter();
        try {
            pJSon.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(clsConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        String myStr=out.toString();  
    return myStr;
    }
}
