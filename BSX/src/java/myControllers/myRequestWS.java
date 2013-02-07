package myControllers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * references : http://www.java2s.com/Code/Java/XML/ParseanXMLstringUsingDOMandaStringReader.htm
 *            : http://stackoverflow.com/questions/4216455/get-page-content-from-url
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import myModels.model_DATA_CURRENCIES;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "myRequestWS", urlPatterns = {"/myRequestWS"})
public class myRequestWS extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();                
        try {
            try {
                //Raw XML Data
                //out.println(readPage(new URL("http://www.nbc.org.kh/admin/webservices/exchangeRate.asp?date=12/02/2011")));

                //out.println("------");
                //out.println("------");
                // read xml string on particular tag
                //out.println("--- Read tag from xml string---");
                readXMLStringRecord(readPage(new URL("http://www.nbc.org.kh/admin/webservices/exchangeRate.asp?date=12/02/2011")),response);
                
            } catch (Exception ex) {
                Logger.getLogger(myRequestWS.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    private static void readXMLStringRecord(String source,HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(source));
            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("ex");
            
            
            String myTranNo="1234567";
            String myTranDate="2011/12/5";
            
            int myLength=nodes.getLength()-1;
            model_DATA_CURRENCIES[] myDATA_CURRENCIES;
            myDATA_CURRENCIES=new model_DATA_CURRENCIES[myLength];
                       
            for (int i = 0; i <= myLength; i++) {
                myDATA_CURRENCIES[i]=new model_DATA_CURRENCIES();
                myDATA_CURRENCIES[i].setCURR_TRAN_NO(myTranNo);
                myDATA_CURRENCIES[i].setCURR_TRAN_DATE(myTranDate);
              
                Element element = (Element) nodes.item(i);
                NodeList key = element.getElementsByTagName("key");
                Element line = (Element) key.item(0);
                myDATA_CURRENCIES[i].setCURR_NO(getCharacterDataFromElement(line));
                //out.println("key: " + getCharacterDataFromElement(line));
                
                NodeList unit = element.getElementsByTagName("unit");
                line = (Element) unit.item(0);
                myDATA_CURRENCIES[i].setCURR_UNIT(getCharacterDataFromElement(line));
                //out.println("unit: " + getCharacterDataFromElement(line));
                
                NodeList bid = element.getElementsByTagName("bid");
                line = (Element) bid.item(0);
                myDATA_CURRENCIES[i].setCURR_BID(getCharacterDataFromElement(line));
                //out.println("bid: " + getCharacterDataFromElement(line));
                
                NodeList ask = element.getElementsByTagName("ask");
                line = (Element) ask.item(0);
                myDATA_CURRENCIES[i].setCURR_ASK(getCharacterDataFromElement(line));
                //out.println("ask: " + getCharacterDataFromElement(line));
                
                NodeList average = element.getElementsByTagName("average");
                line = (Element) average.item(0);
                myDATA_CURRENCIES[i].setCURR_AVERAGE(getCharacterDataFromElement(line));
                
                myDATA_CURRENCIES[i].setCURR_SYSUID("1");
                String myInsertScript=myDATA_CURRENCIES[i].getInsertScript();
                out.println(myInsertScript);
            }            
        } catch (SAXException ex) {
            Logger.getLogger(myRequestWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(myRequestWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(myRequestWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static String readPage(URL url) throws Exception {

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
}
