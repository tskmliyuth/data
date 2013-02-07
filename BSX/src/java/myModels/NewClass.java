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
 * @author moliyuth
 */
public class NewClass {
    public static void main(String args[]){
          DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                Date date = new Date();
                                System.out.println(dateFormat.format(date));
    }
}
