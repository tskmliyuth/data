/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myModels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author NHEP BORA
 */
public class clsDBFunction {
    //Method convert to Lower cast
    public static String LOWER(String pValue){
        return "LOWER(" + pValue + ")";
    }
    public static String Format(Object pObject){

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String myString = (String) formatter.format(pObject);
        return myString;
    }
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static int Boolean_to_Integer(boolean pValue){
        if (pValue==true){
            return 1;
        }else{
            return 0;
        }
   }
    public static boolean Integer_to_Boolean(int pValue){
        if (pValue==1){
            return true;
        }else{
            return false;
        }
   }
}
