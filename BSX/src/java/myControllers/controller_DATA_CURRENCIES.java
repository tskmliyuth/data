package myControllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import myClasses.*;
import myModels.*;

@WebServlet(name = "controller_DATA_CURRENCIES", urlPatterns = {"/currencies"})
public class controller_DATA_CURRENCIES extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        clsMain myClsMain = new clsMain(request,response,"myTemplateNBCAdmin.html");
        clsDataBase myCls=new clsDataBase();

	if(myClsMain.CheckPermission("1")==false){
            myClsMain.myResponse.sendRedirect("Login");
            return;
        }

        String  myType= myClsMain.myRequest.getParameter("Type");
        if (myType==null){
		SearchTodayForm(myClsMain,myCls);           
        }else if(myType.equals("Search")){
		SearchForm(myClsMain,myCls);
        }else if(myType.equals("Today")){
		SearchTodayForm(myClsMain,myCls);
        }else if(myType.equals("NBCEX")){
		ViewExchangeRate(myClsMain,myCls);        }
        else if(myType.equals("Update")){
                String  myBtSave= myClsMain.myRequest.getParameter("myBtSave");
		if (myBtSave!=null){
                    myBtUpdate_Click(myClsMain,myCls);
                }else{
                    SearchTodayForm(myClsMain,myCls);
                }
        }else{
		SearchTodayForm(myClsMain,myCls);   
	}    
    }  

    
private void SearchForm(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCIES_FORM_SEARCH.html");
        String  myTxtSearch= myClsMain.myRequest.getParameter("myTxtSearch");
        String myDate="";
        if (myTxtSearch==null || myTxtSearch.equals("")){
            myDate=c.getSystemDate("yyyy/MM/dd");
        }else{
            myDate=myTxtSearch;
        }
        
        String myScript= "SELECT CURR_TRAN_NO,NVL(to_char(CURR_TRAN_DATE, 'mm/dd/yyyy'),'') CURR_TRAN_DATE,CURR_NO,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE FROM DATA_CURRENCIES WHERE to_char(CURR_TRAN_DATE, 'yyyy/mm/dd')='"+ myDate +"'";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE +="<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>Currency</TD>";
	myHTMLTABLE +="<TD align='center'>Unit</TD>";
	myHTMLTABLE +="<TD align='center'>Bid</TD>";
	myHTMLTABLE +="<TD align='center'>Ask</TD>";
	myHTMLTABLE +="<TD align='center'>Average</TD>";
	                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            myHTMLTABLE += "<TR>\n";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_NO")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_UNIT")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_BID")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_ASK")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_AVERAGE")) + "</TD>";
            myHTMLTABLE += "</TR>\n";            
            }
		myResultSet.close();
		myHTMLTABLE +="</TABLE>";
		myBodyScript=myBodyScript.replace("#DATA#", myHTMLTABLE);  
                myBodyScript=myBodyScript.replace("#DATE#", myDate); 
		try { 
			myClsMain.ShowPageContain(myBodyScript);
		} 
		catch (IOException ex) { 
		Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
		}	
        } 
        catch (SQLException ex) { myClsMain.PrintOut(ex.getMessage()); }
}    
    
private void SearchTodayForm(clsMain myClsMain,clsDataBase myCls){
	clsConverter c=new clsConverter();
        String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCIES_FORM.html");
        String myScript= "SELECT CURR_TRAN_NO,NVL(to_char(CURR_TRAN_DATE, 'mm/dd/yyyy'),'') CURR_TRAN_DATE,CURR_NO,CURR_UNIT,CURR_BID,CURR_ASK,CURR_AVERAGE FROM DATA_CURRENCIES WHERE to_char(CURR_TRAN_DATE, 'yyyy/mm/dd')='"+ c.getSystemDate() +"'";
        ResultSet myResultSet = myCls.ExecuteReader(myScript);
        String myHTMLTABLE;
	myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
	myHTMLTABLE +="<TR style='color:#F30 ;' height='35'>";
	myHTMLTABLE +="<TD align='center'>Currency</TD>";
	myHTMLTABLE +="<TD align='center'>Unit</TD>";
	myHTMLTABLE +="<TD align='center'>Bid</TD>";
	myHTMLTABLE +="<TD align='center'>Ask</TD>";
	myHTMLTABLE +="<TD align='center'>Average</TD>";
	                                            
	myHTMLTABLE += "</TR>";
        try {
            while(myResultSet.next()){
            myHTMLTABLE += "<TR>\n";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_NO")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_UNIT")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_BID")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_ASK")) + "</TD>";
            myHTMLTABLE +="<TD align='right'>" + c.toHTML(myResultSet.getObject("CURR_AVERAGE")) + "</TD>";
            myHTMLTABLE += "</TR>\n";            
            }
		myResultSet.close();
		myHTMLTABLE +="</TABLE>";
		myBodyScript=myBodyScript.replace("#DATA#", myHTMLTABLE);  
                myBodyScript=myBodyScript.replace("#DATE#", c.getSystemDate("dd/MM/yyyy")); 
		try { 
			myClsMain.ShowPageContain(myBodyScript);
		} 
		catch (IOException ex) { 
		Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
		}	
        } 
        catch (SQLException ex) { myClsMain.PrintOut(ex.getMessage()); }
}

private void ViewExchangeRate(clsMain myClsMain,clsDataBase myCls) {
           try {
                clsConverter c=new clsConverter();
                String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCIES_FORM.html");
                String myHTMLTABLE;
                myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
                myHTMLTABLE += "<TR style='color:#F30;' height='35'>";
                myHTMLTABLE +="<TD align='center'>Currency</TD>";
                myHTMLTABLE +="<TD align='center'>Unit</TD>";
                myHTMLTABLE +="<TD align='center'>Bid</TD>";
                myHTMLTABLE +="<TD align='center'>Ask</TD>";
                myHTMLTABLE +="<TD align='center'>Average</TD>";
                myHTMLTABLE += "</TR>";               
               
                String mySource=readPage(new URL("http://www.nbc.org.kh/a dmin/xml.php?ex_date=" + c.getSystemDate("MM/dd/yyyy")));
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(mySource));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("ex");
            
                String myTranNo=c.getDateTime();
                String myTranDate=c.getSystemDate("yyyy/MM/dd");
            
                int myLength=nodes.getLength()-1;
                model_DATA_CURRENCIES[] myDATA_CURRENCIES;
                myDATA_CURRENCIES=new model_DATA_CURRENCIES[myLength+1];
             
            for (int i = 0; i <= myLength; i++) {
                myHTMLTABLE += "<TR>\n";
                myDATA_CURRENCIES[i]=new model_DATA_CURRENCIES();
                myDATA_CURRENCIES[i].setCURR_TRAN_NO(myTranNo);
                myDATA_CURRENCIES[i].setCURR_TRAN_DATE(myTranDate);
              
                Element element = (Element) nodes.item(i);
                NodeList key = element.getElementsByTagName("key");
                Element line = (Element) key.item(0);
                myDATA_CURRENCIES[i].setCURR_NO(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='center'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                                
                NodeList unit = element.getElementsByTagName("unit");
                line = (Element) unit.item(0);
                myDATA_CURRENCIES[i].setCURR_UNIT(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='center'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList bid = element.getElementsByTagName("bid");
                line = (Element) bid.item(0);
                myDATA_CURRENCIES[i].setCURR_BID(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList ask = element.getElementsByTagName("ask");
                line = (Element) ask.item(0);
                myDATA_CURRENCIES[i].setCURR_ASK(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList average = element.getElementsByTagName("average");
                line = (Element) average.item(0);
                myDATA_CURRENCIES[i].setCURR_AVERAGE(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                myDATA_CURRENCIES[i].setCURR_SYSUID("1");
                myHTMLTABLE += "</TR>\n";     
                //String myInsertScript=myDATA_CURRENCIES[i].getInsertScript();                
            } 
            
                myHTMLTABLE +="</TABLE>";
                myBodyScript=myBodyScript.replace("#DATE#", c.getSystemDate("yyyy/MM/dd")); 
                myBodyScript=myBodyScript.replace("#DATA#", myHTMLTABLE);  
                myBodyScript=myBodyScript.replace("#MESSAGE#", ""); 
		try { 
			myClsMain.ShowPageContain(myBodyScript);
		} 
		catch (IOException ex) { 
		Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
		}
        } catch (Exception ex) {
            Logger.getLogger(controller_DATA_CURRENCIES.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

private void myBtUpdate_Click(clsMain myClsMain,clsDataBase myCls) {
           try {
                clsConverter c=new clsConverter();
                String myBodyScript = myClsMain.getContentFromFile("DATA_CURRENCIES_FORM.html");
                String myHTMLTABLE;
                myHTMLTABLE="<TABLE id='report' align='center' style='font-size:14px;'>";
                myHTMLTABLE += "<TR style='color:#F30;' height='35'>";
                myHTMLTABLE +="<TD align='center'>Currency</TD>";
                myHTMLTABLE +="<TD align='center'>Unit</TD>";
                myHTMLTABLE +="<TD align='center'>Bid</TD>";
                myHTMLTABLE +="<TD align='center'>Ask</TD>";
                myHTMLTABLE +="<TD align='center'>Average</TD>";
                myHTMLTABLE += "</TR>";               
               
                String mySource=readPage(new URL("http://www.nbc.org.kh/admin/xml.php?ex_date=" + c.getSystemDate("MM/dd/yyyy")));
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(mySource));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("ex");
            
                String myTranNo=c.getDateTime();
                String myTranDate=c.getSystemDate("yyyy/MM/dd");
            
                int myLength=nodes.getLength()-1;
                model_DATA_CURRENCIES[] myDATA_CURRENCIES;
                myDATA_CURRENCIES=new model_DATA_CURRENCIES[myLength+1];
                String[] myScripts;
                myScripts = new String[(myLength+1) * 2];
                boolean mySave =false;
            for (int i = 0; i <= myLength; i++) {
                myHTMLTABLE += "<TR>\n";
                myDATA_CURRENCIES[i]=new model_DATA_CURRENCIES();
                myDATA_CURRENCIES[i].setCURR_TRAN_NO(myTranNo);
                myDATA_CURRENCIES[i].setCURR_TRAN_DATE(myTranDate);
              
                Element element = (Element) nodes.item(i);
                NodeList key = element.getElementsByTagName("key");
                Element line = (Element) key.item(0);
                myDATA_CURRENCIES[i].setCURR_NO(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='center'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                                
                NodeList unit = element.getElementsByTagName("unit");
                line = (Element) unit.item(0);
                myDATA_CURRENCIES[i].setCURR_UNIT(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='center'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList bid = element.getElementsByTagName("bid");
                line = (Element) bid.item(0);
                myDATA_CURRENCIES[i].setCURR_BID(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList ask = element.getElementsByTagName("ask");
                line = (Element) ask.item(0);
                myDATA_CURRENCIES[i].setCURR_ASK(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                NodeList average = element.getElementsByTagName("average");
                line = (Element) average.item(0);
                myDATA_CURRENCIES[i].setCURR_AVERAGE(getCharacterDataFromElement(line));
                myHTMLTABLE +="<TD align='right'>" + c.toHTML(getCharacterDataFromElement(line)) + "</TD>";
                
                myDATA_CURRENCIES[i].setCURR_SYSUID(myClsMain.myDATA_USERS.getUSER_NO());
                myHTMLTABLE += "</TR>\n";     
                myScripts[i] = myDATA_CURRENCIES[i].getInsertScript() ;   
                myScripts[i+myLength+1] =myDATA_CURRENCIES[i].getUpdateCurrencyScript(); 
                mySave=true;
            }                 
                myHTMLTABLE +="</TABLE>";
                myBodyScript=myBodyScript.replace("#DATE#", c.getSystemDate("yyyy/MM/dd")); 
                myBodyScript=myBodyScript.replace("#DATA#", myHTMLTABLE);  
		try {
                    
                    String myCount= myCls.ExecuteScalar("SELECT COUNT(*) FROM DATA_CURRENCIES WHERE to_char(CURR_TRAN_DATE, 'yyyy/mm/dd')='"+ c.getSystemDate() +"'").toString();
                    if (myCount.equals("0") && mySave == true){
                        myCls.ExecuteNonQuery(myScripts);
                        if (myCls.isERROR()==true){
                            myClsMain.PrintOut(myCls.getERROR_Message());
                        }else{
                            myBodyScript=myBodyScript.replace("#MESSAGE#", "alert(\"Exchage rate have been save\")"); 
                            myClsMain.ShowPageContain(myBodyScript);
                        }
                    }else{
                        myBodyScript=myBodyScript.replace("#MESSAGE#", "alert(\"Exchage rate can not be save. please contact the IT to solve this problem\")"); 
                        myClsMain.ShowPageContain(myBodyScript);
                    }                                    
                } 
		catch (IOException ex) { 
		Logger.getLogger(controller_DATA_USERS.class.getName()).log(Level.SEVERE, null, ex);
		}
        } catch (Exception ex) {
            Logger.getLogger(controller_DATA_CURRENCIES.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

private String readPage(URL url) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);

        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuffer sb = new StringBuffer();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }
            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
              CharacterData cd = (CharacterData) child;
              return cd.getData();
        }
        return "";
    }

    //Sevelet even 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
