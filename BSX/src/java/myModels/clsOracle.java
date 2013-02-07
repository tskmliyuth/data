/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myModels;

// Import JDBC Library
import java.sql.*;

/**
 *
 * @author NHEP BORA
 */
public class clsOracle {

//    //Error parameter
//    private boolean ERROR;
//    private int ERROR_CODE;
//    private String ERROR_Message;
//    //Database Parameter
//    private String DBDriverName;
//    private String DBURL ;
//    private String DBUserName;
//    private String DBPWD;
//
//    private long USER_ID;
//    private long ROLE_ID;
//    private String USER_FULL_NAME;
//    //Contructor
//    public clsOracle(){
//    // initiat error paramter
//    ERROR=false;
//    ERROR_CODE=-1;
//    ERROR_Message="";
//    // initiat database parameter
//    DBDriverName="oracle.jdbc.driver.OracleDriver";
//    DBURL="jdbc:oracle:thin:@//192.168.100.110:1521/DBNBC";
//    DBUserName="SMS";
//    DBPWD="nationalbank";
//    USER_ID=-1;
//    ROLE_ID=-1;
//    USER_FULL_NAME="";
//    }
//
//    //Properties
//    
//    /**
//     * @return the ERROR
//     */
//    public boolean isERROR() {
//        return ERROR;
//    }
//    /**
//     * @return the ERROR_CODE
//     */
//    public int getERROR_CODE() {
//        return ERROR_CODE;
//    }
//
//    /**
//     * @return the ERROR_Message
//     */
//    public String getERROR_Message() {
//        return ERROR_Message;
//    }
//
//    /**
//     * @return the USER_ID
//     */
//    public long getUSER_ID() {
//        return USER_ID;
//    }
//
//    /**
//     * @return the ROLE_ID
//     */
//    public long getROLE_ID() {
//        return ROLE_ID;
//    }
//
//      /**
//     * @return the USER_FULL_NAME
//     */
//    public String getUSER_FULL_NAME() {
//        return USER_FULL_NAME;
//    }
//    //Private Methods
//
//     /**
//     * Set the Errer code and message to defualt
//     */
//    private void clearError(){
//        ERROR = false;
//        ERROR_CODE=-1;
//        ERROR_Message="";
//    }
//
//    //Public Methods
//
//   private SIGNATURES CurrentArrayTableBean=new SIGNATURES();
//    /**
//     * @return the CurrentArrayTableBean
//     */
//    public SIGNATURES getCurrentArrayTableBean() {
//        return CurrentArrayTableBean;
//    }
//
//    /**
//     * @param CurrentArrayTableBean the CurrentArrayTableBean to set
//     */
//    public void setCurrentArrayTableBean(SIGNATURES CurrentArrayTableBean) {
//        this.CurrentArrayTableBean = CurrentArrayTableBean;
//    }
//
//   
//
//    public void Login(String pUserName, String pPassword){
//        
//        String myQuery="SELECT USR_USE_ID,USR_ROL_ID,USE_KNAME FROM SYS_USER_ROLE JOIN SYS_USERS ON USR_USE_ID=USE_ID WHERE " + clsDBFunction.LOWER("USE_NAME") + "=" + clsDBFunction.LOWER("'" + pUserName + "'") + " AND USE_PWD='" + pPassword + "' AND ROWNUM=1";
//        clearError();
//        Connection myConnection = null;
//        Statement myStatement = null;
//        ResultSet myResultSet = null;
//        //ERROR_Message= myQuery;
//        try {
//            // Load (and therefore register) the Oracle Driver
//            Class.forName(DBDriverName);
//
//            // Get a Connection to the database
//            myConnection = DriverManager.getConnection(DBURL,DBUserName,DBPWD);
//
//            // Create a Statement object
//            myStatement = myConnection.createStatement();
//
//            // Execute an SQL query, get a ResultSet
//            myResultSet = myStatement.executeQuery(myQuery);
//
//
//            // Display the result set as a list
//
//            while(myResultSet.next()) {
//                //out.println("<LI>" + rs.getString("name") + " " + rs.getString("phone"));
//                USER_ID=myResultSet.getLong("USR_USE_ID");
//                ROLE_ID=myResultSet.getLong("USR_ROL_ID");
//                USER_FULL_NAME=myResultSet.getString("USE_KNAME");
//                ERROR=false;
//            }
//
//        }
//        //Error while loading database driver
//        catch(ClassNotFoundException e) {
//            ERROR=true;
//            ERROR_Message="Couldn't load database driver: " + e.getMessage();
//        }
//        //Error connecting to database
//        catch(SQLException e) {
//            ERROR=true;
//            ERROR_Message="SQLException caught: " + e.getMessage();
//        }
//        finally {
//        // Always close the database connection.
//        try {
//            if (myConnection != null) myConnection.close();
//            
//        }
//        catch (SQLException ignored) {
//            
//        }
//        }
//    }
//
//   
//
//  
//
//    
//
//    
//


    


}
