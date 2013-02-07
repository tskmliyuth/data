/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myControllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myClasses.clsMain;
import myModels.clsDataBase;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class clsFileUpload {
    public boolean isWrong= true;
    public boolean isERROR = true;
    public String ErrorMessage = "";
    public String TMP_DIR_PATH = "c:\\tmp";
    public File tmpDir;
    public String DESTINATION_DIR_PATH = "c:\\tmp\\files";
    public File destinationDir;
    public File file;
    public String FileName = "";
    String name;
    //File information....
    public String cFileName = "";
    public String cExtenstion = "";
    public String cFileContent = "";
    public Long cFileSide;
    public String DS = "";
    public String CA = "";
    public HttpServletRequest request;
    public HttpServletResponse response;
    
    clsMain myClsMain;
    clsDataBase myCls;

    public clsFileUpload(clsMain pClsMain, clsDataBase pCls, String pTmp, String pDes) {
        myCls = pCls;
        myClsMain = pClsMain;

        this.request = myClsMain.myRequest;
        this.response = myClsMain.myResponse;
        this.TMP_DIR_PATH = pTmp;
        this.DESTINATION_DIR_PATH = pDes;

        tmpDir = new File(TMP_DIR_PATH);
        if (!tmpDir.isDirectory()) {
            isERROR = true;
            ErrorMessage = TMP_DIR_PATH + " is not a directory";
        }
        //String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
        destinationDir = new File(DESTINATION_DIR_PATH);
        if (!destinationDir.isDirectory()) {
            isERROR = true;
            ErrorMessage = DESTINATION_DIR_PATH + " is not a directory";
        }

    }

    protected void UploadFile() {

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        /*
         *Set the size threshold, above which content will be stored on disk.
         */
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB
		/*
         * Set the temporary directory to store the uploaded files of size above threshold.
         */
        fileItemFactory.setRepository(tmpDir);

        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        try {
            /*
             * Parse the request
             */
            List items = uploadHandler.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();

                if (item.isFormField()) {
                    if (item.getFieldName().equals("certificationChain")) {
                        CA = item.getString();
                        //String myScript ="SELECT USER_NO FROM DATA_USER WHERE USER_NO="+ myClsMain.myDATA_USERS.getUSER_NO() +" AND USER_CA='"+ CA +"'";
                        if (myClsMain.myDATA_USERS.USER_CA.compareTo(CA) != 0) {
                            ErrorMessage = "Invalid eToken! please contact NBC System administrator to solve this problem";
                            isERROR = true;
                            return;
                        }

                    } else if (item.getFieldName().equals("signature")) {
                        DS = item.getString();
                    }
                } else {

                    cFileName = item.getName();
                    cFileContent = item.getContentType();
                    cFileSide = item.getSize();
                    cExtenstion = getFileExtension(cFileName);
                    String myFileName = FileName + "." + cExtenstion;
                     file = new File(destinationDir, myFileName);
                    item.write(file);
                    if(DESTINATION_DIR_PATH.equals("D:\\DATA_ACK\\BUREAU COMPLAINT")){
                        if (cExtenstion.equals("pdf")||cExtenstion.equals("docx")||cExtenstion.equals("doc")) {
                             String mynewFileName = cFileName;
                             File newfile = new File("D:\\DATA_ACK\\BUREAU COMPLAINT", mynewFileName);
                             file.renameTo(newfile);
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Credit Bureau Complaint Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                           
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);
                            if(isWrong==false){
                                 mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                                 newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                                 file.renameTo(newfile);
                            }
                    }
                    }else if(DESTINATION_DIR_PATH.equals("D:\\DATA_ACK\\ACKNOWLEGEMENT")){
                        if (cExtenstion.equals("pdf")||cExtenstion.equals("docx")||cExtenstion.equals("doc")) {
                             String mynewFileName = cFileName;
                             File newfile = new File("D:\\DATA_ACK\\ACKNOWLEGEMENT", mynewFileName);
                             file.renameTo(newfile);
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Acknowlegdement Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);
                            if(isWrong==false){
                                 mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                                 newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                                 file.renameTo(newfile);
                            }
                    }
                    }else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\DAILY_FOLDER")) {
                        if (cExtenstion.equals("xml")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Daily Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: dailytemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);
                            if(isWrong==false){
                                 mynewFileName = FileName+" Daily" + "(_Delete)" + "." + cExtenstion;
                                 newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                                 file.renameTo(newfile);
                            }


                        }
                        //weekly report
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\WEEKLY_FOLDER")) {
                        if (cExtenstion.equals("xlsm")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC weekly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: weeklytemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+"Weekly" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        //
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\MONTHLY_FOLDER")) {
                        if (cExtenstion.equals("xml")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Monthly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: monthlytemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName +"monthly"+ "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        // quarterly Report
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\QUARTERLY_FOLDER")) {
                        if (cExtenstion.equals("xlsm")) {
                            isERROR = false;
                        } else {

                          String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC quarterly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: quarterlytemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+" quaterly  " + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        // aunual report
                        
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\ANNUALLY_FOLDER")) {
                        if ( cExtenstion.equals("zip") || cExtenstion.equals("rar") || cExtenstion.equals("7z")) {
                            isERROR = false;
                        } else {

                           String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Anually Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                           
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName +"annual"+ "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\TWICE_WEEK_FOLDER")) {
                        if (cExtenstion.equals("xml")) {
                            isERROR = false;
                        } else {

                           String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC bi-weekly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: biweeklytemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName +"BI-weekly 1"+ "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        // COA monthly report
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\COAMONTHLY_FOLDER")) {
                        if (cExtenstion.equals("xlsx") || cExtenstion.equals("xlsm")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC COA monthly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: COAMonthlyTemplate.xlsx</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+"COAMONTHLY" +"(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                       // sCOA MONTHLY REPORT
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\SCOAMONTHLY_FOLDER")) {
                        if (cExtenstion.equals("xlsx" ) || cExtenstion.equals("xlsm")) {
                            isERROR = false;
                        } else {

                           String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC static COA monthly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: SCOAMonthlyTemplate.xlsx</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName="StaticCOA" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        //Satatic Monthly
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\SMONTHLY_FOLDER")) {
                        if (cExtenstion.equals("xlsm")) {
                            isERROR = false;
                        } else {

                         String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Static Monthly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: SMonthlyTemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+ "Static Monthly" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        //Satatic Weekly Report
                        
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\SWEEKLY_FOLDER")) {
                        if (cExtenstion.equals("xlsx") || cExtenstion.equals("xlsm") ) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Static Weekly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: SWeeklyTemplate.xlsx</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+ "staticweekly" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\ONSIDE_FOLDER")) {
                        if (cExtenstion.equals("xlsx") || cExtenstion.equals("xls")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Onside Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: OnsideTemplate.xlsx</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName+"onside" + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        //BI weekly
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\TWICE_WEEK_FOLDER_31")) {
                        if (cExtenstion.equals("xml")) {
                            isERROR = false;
                        } else {

                           String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += "<font color=" + "blue" + "><br>This is not NBC Bi-weekly Report Template</br>";
                            Error += "<br>Please download Template from Bank supervision website</br>";
                            Error += "<br> Example: biweeklyTemplate.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName +"biweekly"+ "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        //Bi weekly
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\TWICE_WEEK_FOLDER_32")) {
                        if (cExtenstion.equals("xml")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += " <br>you might uploaded wrong excel file</br>";
                            Error += "<br> Your excel File is not NBC Excel file</br>";
                            Error += "<br>In your Report, you might gave wrong information according to the sheet </br>";
                            Error += "<br>Incorrect Excel file<br>";
                            Error += "<font color=" + "Red" + "><br>Problems might be solved by:</br></font>";
                            Error += "<font color=" + "blue" + "><br>Please check your Data again</br>";
                            Error += "<br>Please check your excel file extension. It must be .xlsm extension</br>";
                            Error += "<br> Example: NBCdailyreport.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                        // annually Report
                    } else if (DESTINATION_DIR_PATH.equals("D:\\BSX_FILES\\ANNUALLY_FOLDER_60")) {
                        if (cExtenstion.equals("zip") || cExtenstion.equals("rar") || cExtenstion.equals("7z")) {
                            isERROR = false;
                        } else {

                            String Error = "<font color=" + "red" + ">Problems occured during upload NBC report</br><font>";
                            Error += " <br>you might uploaded wrong excel file</br>";
                            Error += "<br> Your excel File is not NBC Excel file</br>";
                            Error += "<br>In your Report, you might gave wrong information according to the sheet </br>";
                            Error += "<br>Incorrect Excel file<br>";
                            Error += "<font color=" + "Red" + "><br>Problems might be solved by:</br></font>";
                            Error += "<font color=" + "blue" + "><br>Please check your Data again</br>";
                            Error += "<br>Please check your excel file extension. It must be .xlsm extension</br>";
                            Error += "<br> Example: NBCdailyreport.xlsm</br>";
                            Error += "<br>Or Contact NBC System administrator to solve this problem </br>";

                            myClsMain.PrintOut(Error);

                            String mynewFileName = FileName + "(_Delete)" + "." + cExtenstion;
                            File newfile = new File("D:/BSX_FILES/INCORRECT_FILES/", mynewFileName);
                            file.renameTo(newfile);


                        }
                    }

                }

            }
        } catch (FileUploadException ex) {
            isERROR = true;
            ErrorMessage = "Error encountered while parsing the request" + ex.getMessage();
        } catch (Exception ex) {
            isERROR = true;
            ErrorMessage = "Error encountered while uploading file" + ex.getMessage();
        }

    }

    public String getFileExtension(String pFileName) {
        int mid = pFileName.lastIndexOf(".");
        String myExtension = pFileName.substring(mid + 1, pFileName.length());
        return myExtension;
    }
}