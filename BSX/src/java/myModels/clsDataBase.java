/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myModels;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clsDataBase {
public boolean falseUpload = true;
    //Error parameter
    private Connection mConnection = null;
    private Statement mStatement = null;
    private boolean ERROR;
    private int ERROR_CODE;
    private String ERROR_Message;
    //Database Parameter
    private String DBDriverName;
    private String DBURL;
    private String DBUserName;
    private String DBPWD;

    //Contructor
    public clsDataBase() {
        // initiat error paramter
        ERROR = false;
        ERROR_CODE = -1;
        ERROR_Message = "";
        // initiat database parameter
        DBDriverName = "oracle.jdbc.driver.OracleDriver";
 DBURL="jdbc:oracle:thin:@//172.16.5.89:1521/BSRS"; 
// DBURL="jdbc:oracle:thin:@//192.168.0.11:1521/BSRS";
        DBUserName = "BSRSPROD";
        DBPWD = "Nbc4BsRs$20110511#";



    }

    public void clearError() {
        ERROR = false;
        ERROR_CODE = -1;
        ERROR_Message = "";
    }

    public boolean isERROR() {
        return ERROR;
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public String getERROR_Message() {
        return ERROR_Message;
    }

    public String getDBDriverName() {
        return DBDriverName;
    }

    public String getDBURL() {
        return DBURL;
    }

    public String getDBUserName() {
        return DBUserName;
    }

    public String getDBPWD() {
        return DBPWD;
    }

    public void setERROR(boolean ERROR) {
        this.ERROR = ERROR;
    }

    public void setERROR_CODE(int ERROR_CODE) {
        this.ERROR_CODE = ERROR_CODE;
    }

    public void setERROR_Message(String ERROR_Message) {
        this.ERROR_Message = ERROR_Message;
    }

    public void setDBDriverName(String DBDriverName) {
        this.DBDriverName = DBDriverName;
    }

    public void setDBURL(String DBURL) {
        this.DBURL = DBURL;
    }

    public void setDBUserName(String DBUserName) {
        this.DBUserName = DBUserName;
    }

    public void setDBPWD(String DBPWD) {
        this.DBPWD = DBPWD;
    }

    public void ExecuteNonQuery(String pScript) {
        Connection myConnection = null;
        Statement myStatement = null;
        clearError();
        try {
            Class.forName(getDBDriverName());
            myConnection = DriverManager.getConnection(getDBURL(), getDBUserName(), getDBPWD());
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate(pScript);
            myStatement.close();
        } //Error while loading database driver
        catch (ClassNotFoundException e) {
            setERROR(true);
            setERROR_Message("Couldn't load database driver: " + e.getMessage());
        } //Error connecting to database
        catch (SQLException e) {
            setERROR(true);
            setERROR_Message("SQLException caught: " + e.getMessage());
        } finally {
            // Always close the database connection.
            try {
                if (myConnection != null) {
                    myStatement.close();
                    myConnection.close();
                }
            } catch (SQLException ignored) {
                ERROR_Message += ignored.getMessage();
            }
        }

    }

    public void ExecuteNonQuery(String... pScript) {
        Connection myConnection = null;
        Statement myStatement = null;
        clearError();
        try {
            Class.forName(getDBDriverName());
            myConnection = DriverManager.getConnection(getDBURL(), getDBUserName(), getDBPWD());
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();

            for (String sql : pScript) {
                myStatement.addBatch(sql);
            }
            myStatement.executeBatch();
            myConnection.commit();
            myStatement.close();
        } //Error while loading database driver
        catch (ClassNotFoundException e) {
            setERROR(true);
            setERROR_Message("Couldn't load database driver: " + e.getMessage());
        } catch (BatchUpdateException e) {
            setERROR(true);
            setERROR_Message("Batch Error: " + e.getMessage());
        } //Error connecting to database
        catch (SQLException e) {
            setERROR(true);
            setERROR_Message("SQLException caught: " + e.getMessage());
        } finally {
            // Always close the database connection.
            try {
                if (myConnection != null) {
                    myStatement.close();
                    myConnection.close();
                }
            } catch (SQLException ignored) {
                ERROR_Message += ignored.getMessage();
            }
        }
    }

    public void ExecuteNonQuery(ArrayList pScript) throws SQLException, ClassNotFoundException{
        Connection myConnection = null;
        Statement myStatement = null;
        clearError();
        try {
            Class.forName(getDBDriverName());
            myConnection = DriverManager.getConnection(getDBURL(), getDBUserName(),getDBPWD());
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();
                       
            for(Object sql : pScript) {
                myStatement.addBatch(sql.toString());
            }
            myStatement.executeBatch();
            myConnection.commit();
            myStatement.close();
        }
        //Error while loading database driver
        catch(ClassNotFoundException e) {
            setERROR(true);
            setERROR_Message("Couldn't load database driver: " + e.getMessage());
        }
        //handle batch update exception  
        catch (BatchUpdateException be) {
//            int[] counts = be.getUpdateCounts();
//            for (int i=0; i> counts.length; i++) {
//		System.out.println("Statement["+i+"] :"+counts[i]);
//            }
            myConnection.rollback();
            setERROR(true);
            setERROR_Message("SQLException caught: " + be.getMessage());
              falseUpload=false;
           
        }     
        //Error connecting to database
        catch(SQLException e) {
            myConnection.rollback();
            setERROR(true);
            setERROR_Message("SQLException caught: " + e.getMessage());
              falseUpload=false;
        }
        finally {
            // Always close the database connection.
            try {if (myConnection != null) {
                myStatement.close();
                myConnection.close();}
                }
            catch (SQLException ignored) {
             ERROR_Message += ignored.getMessage();
            }
        }       
    }

    public ResultSet ExecuteReader(String pSQL) {
        clearError();
        try {
            Class.forName(getDBDriverName());
            mConnection = DriverManager.getConnection(getDBURL(), getDBUserName(), getDBPWD());
            mStatement = mConnection.createStatement();
            ResultSet myResultSet = mStatement.executeQuery(pSQL);
            return myResultSet;
        } //Error while loading database driver
        catch (ClassNotFoundException ex) {
            setERROR(true);
            setERROR_Message("Couldn't load database driver: " + ex.getMessage());
            return null;
        } //Error connecting to database
        catch (SQLException ex) {
            setERROR(true);
            setERROR_Message("SQLException caught: " + ex.getMessage());
            return null;
        }

    }

    public String getSelectOptoinValue(String pValue, String pDisplay, String pTableName, String pSelectedValue) {
        String myScript = "SELECT " + pValue + " KEY_VALUE, " + pDisplay + " KEY_DISPLAY FROM " + pTableName;
        String myOptionValue = "";
        ResultSet myResultSet = ExecuteReader(myScript);
        if (isERROR() == true) {
            return "";
        } else {
            clearError();
        }

        try {
            while (myResultSet.next()) {
                String myKEY_VALUE = myResultSet.getString("KEY_VALUE");
                String myKEY_DISPLAY = myResultSet.getString("KEY_DISPLAY");
                if (!pSelectedValue.equals("") && pSelectedValue.equals(myKEY_VALUE)) {
                    myOptionValue += "<option value=\'" + myKEY_VALUE + "\' SELECTED>" + myKEY_DISPLAY + "</option>";
                } else {
                    myOptionValue += "<option value=\'" + myKEY_VALUE + "\'>" + myKEY_DISPLAY + "</option>";
                }

            }
        } catch (SQLException ex) {
            setERROR(true);
            setERROR_Message("SQLException caught: " + ex.getMessage());
            return "";
        }
        CloseResultSet();
        return myOptionValue;
    }

    public void CloseResultSet() {
        try {
            if (isERROR() == true) {
                return;
            } else {
                clearError();
            }
            mStatement.close();
            mConnection.close();
        } catch (SQLException e) {
            setERROR(true);
            setERROR_Message("SQLException caught: " + e.getMessage());
        }
    }

    public Object ExecuteScalar(String pScript) {
        try {
            ResultSet myResultSet = ExecuteReader(pScript);
            Object myObject_Value = null;
            while (myResultSet.next()) {
                myObject_Value = myResultSet.getObject(1);
                ERROR = false;
                break;
            }
            CloseResultSet();
            return myObject_Value;
        } catch (SQLException ex) {
            Logger.getLogger(clsDataBase.class.getName()).log(Level.SEVERE, null, ex);
            CloseResultSet();
            return null;
        }
    }

    public Boolean Existed(String pScript) {
        try {
            ResultSet myResultSet = ExecuteReader(pScript);
            Integer myCount = 0;
            while (myResultSet.next()) {
                myCount = 1;
                break;
            }
            CloseResultSet();
            if (myCount == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(clsDataBase.class.getName()).log(Level.SEVERE, null, ex);
            CloseResultSet();
            return false;
        }
    }

    public String getParameter_Value(int pPara_ID) {
        String myQuery = "SELECT PARA_VALUE FROM SYS_PARAMETERS WHERE PAR_NO=" + pPara_ID + " AND ROWNUM=1";
//        Object myResult = ExecuteScalar(myQuery, "PARA_VALUE");
        Object myResult = ExecuteScalar(myQuery);
        if (myResult == null) {
            return "";
        } else {
            return ((String) myResult);
        }
    }

    public String getParameter_Value(String pPara_Name) {

        String myQuery = "SELECT PARA_VALUE FROM SYS_PARAMETERS WHERE PARA_NAME='" + pPara_Name + "' AND ROWNUM=1";
//        Object myResult = ExecuteScalar(myQueryObject, "PARA_VALUE");
        Object myResult = ExecuteScalar(myQuery);
        if (myResult == null) {
            return "";
        } else {
            return ((String) myResult);
        }
    }
}
