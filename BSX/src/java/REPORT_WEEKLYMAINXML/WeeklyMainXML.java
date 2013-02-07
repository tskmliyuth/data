/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYMAINXML;


import generated.BiWeeklyMain;
import generated.BiWeeklyMain.FormA.ADailyAverage;
import generated.BiWeeklyMain.FormA.ATotal;

import generated.BiWeeklyMain.FormA.DateA1;
import generated.BiWeeklyMain.FormA.DateA10;
import generated.BiWeeklyMain.FormA.DateA11;
import generated.BiWeeklyMain.FormA.DateA12;
import generated.BiWeeklyMain.FormA.DateA13;
import generated.BiWeeklyMain.FormA.DateA14;
import generated.BiWeeklyMain.FormA.DateA2;
import generated.BiWeeklyMain.FormA.DateA3;
import generated.BiWeeklyMain.FormA.DateA4;
import generated.BiWeeklyMain.FormA.DateA5;
import generated.BiWeeklyMain.FormA.DateA6;
import generated.BiWeeklyMain.FormA.DateA7;
import generated.BiWeeklyMain.FormA.DateA8;
import generated.BiWeeklyMain.FormA.DateA9;
import generated.BiWeeklyMain.FormA.FormAMainDate;
import generated.BiWeeklyMain.FormA.FormAMainValue;
import generated.BiWeeklyMain.FormB.BDailyAverage;
import generated.BiWeeklyMain.FormB.BTotal;
import generated.BiWeeklyMain.FormB.DateB1;
import generated.BiWeeklyMain.FormB.DateB10;
import generated.BiWeeklyMain.FormB.DateB11;
import generated.BiWeeklyMain.FormB.DateB12;
import generated.BiWeeklyMain.FormB.DateB13;
import generated.BiWeeklyMain.FormB.DateB14;
import generated.BiWeeklyMain.FormB.DateB2;
import generated.BiWeeklyMain.FormB.DateB3;
import generated.BiWeeklyMain.FormB.DateB4;
import generated.BiWeeklyMain.FormB.DateB5;
import generated.BiWeeklyMain.FormB.DateB6;
import generated.BiWeeklyMain.FormB.DateB7;
import generated.BiWeeklyMain.FormB.DateB8;
import generated.BiWeeklyMain.FormB.DateB9;

import generated.BiWeeklyMain.FormB.FormBMainDate;
import generated.BiWeeklyMain.FormB.FormBMainValue;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import myModels.clsDataBase;
import org.xml.sax.SAXException;

public class WeeklyMainXML {

    String ErrorMessage = "";

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
    String xml;
    String xsd;

    public WeeklyMainXML(String xmlfile) {
        this.xml = xmlfile;
    }

    public void readxml(String reportID) throws SAXException, SQLException {
        try {
            String ReportMainID = null;
            ArrayList DataV = new ArrayList();
            File file = new File(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(BiWeeklyMain.class);
            clsDataBase data = new clsDataBase();
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BiWeeklyMain weekMain = (BiWeeklyMain) jaxbUnmarshaller.unmarshal(file);

            List MainChild = weekMain.getFormA().getFormAMainDateAndABankNameAndFormAExchangeRate();
            model_RPT9_RESERVEMAINTENKHR formAMain = new model_RPT9_RESERVEMAINTENKHR();
            model_RPT9_RESERVEMAINTENDETAILKHR formA = new model_RPT9_RESERVEMAINTENDETAILKHR();
            if (weekMain.getFormA() != null) {
                ListIterator iteratorMain = MainChild.listIterator();
                while (iteratorMain.hasNext()) {
                    JAXBElement Ma = (JAXBElement) iteratorMain.next();
                    switch (Ma.getName().toString()) {
                        case "FormAMainDate":
                            FormAMainDate date = (FormAMainDate) Ma.getValue();
                            formAMain.setREMA_BASEDPERIOD(date.getABasePeriod());
                            formAMain.setREMA_MAINTENACEPERIOD(date.getAMaintenancePeriod());
                            formAMain.setREMA_REPORTINGPERIOD(date.getAReportingPeriod());
                            break;
                        
                        case "FormAMainValue":

                            FormAMainValue value = (FormAMainValue) Ma.getValue();
                            formAMain.setREMA_REPORTID(reportID);
                            formAMain.setREMA_MINRESERVER(value.getMinimumReserve().toString());
                            formAMain.setREMA_SURPLUS(value.getReserveRequirmentSurplus().toString());
                            formAMain.setREMA_DEFICIT(value.getReserveRequirmentDeficit().toString());
                            data.ExecuteNonQuery(formAMain.getInsertScript());
                            String query = "SELECT MAX(REMA_NO) FROM RPT9_RESERVEMAINTENKHR";
                            ResultSet myResultSet = data.ExecuteReader(query);
                            myResultSet.next();
                            ReportMainID = myResultSet.getString(1);

                            break;
                            


                        case "DateA1":
                            DateA1 d1 = (DateA1) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d1.getDateOfMaintenancePeriod1().toString());
                            formA.setREMD_ACCOUNTBALANCE(d1.getReserveReauirement1().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d1.getMinimumThreshold1().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d1.getDailyCompulsory1().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d1.getClearingAccount1().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d1.getDailyReserve1().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA2":
                            DateA2 d2 = (DateA2) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d2.getDateOfMaintenancePeriod2().toString());
                            formA.setREMD_ACCOUNTBALANCE(d2.getReserveReauirement2().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d2.getMinimumThreshold2().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d2.getDailyCompulsory2().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d2.getClearingAccount2().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d2.getDailyReserve2().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA3":
                            DateA3 d3 = (DateA3) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d3.getDateOfMaintenancePeriod3().toString());
                            formA.setREMD_ACCOUNTBALANCE(d3.getReserveReauirement3().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d3.getMinimumThreshold3().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d3.getDailyCompulsory3().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d3.getClearingAccount3().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d3.getDailyReserve3().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA4":
                            DateA4 d4 = (DateA4) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d4.getDateOfMaintenancePeriod4().toString());
                            formA.setREMD_ACCOUNTBALANCE(d4.getReserveReauirement4().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d4.getMinimumThreshold4().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d4.getDailyCompulsory4().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d4.getClearingAccount4().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d4.getDailyReserve4().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA5":
                            DateA5 d5 = (DateA5) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d5.getDateOfMaintenancePeriod5().toString());
                            formA.setREMD_ACCOUNTBALANCE(d5.getReserveReauirement5().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d5.getMinimumThreshold5().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d5.getDailyCompulsory5().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d5.getClearingAccount5().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d5.getDailyReserve5().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA6":
                            DateA6 d6 = (DateA6) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d6.getDateOfMaintenancePeriod6().toString());
                            formA.setREMD_ACCOUNTBALANCE(d6.getReserveReauirement6().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d6.getMinimumThreshold6().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d6.getDailyCompulsory6().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d6.getClearingAccount6().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d6.getDailyReserve6().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA7":
                            DateA7 d7 = (DateA7) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d7.getDateOfMaintenancePeriod7().toString());
                            formA.setREMD_ACCOUNTBALANCE(d7.getReserveReauirement7().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d7.getMinimumThreshold7().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d7.getDailyCompulsory7().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d7.getClearingAccount7().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d7.getDailyReserve7().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA8":
                            DateA8 d8 = (DateA8) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d8.getDateOfMaintenancePeriod8().toString());
                            formA.setREMD_ACCOUNTBALANCE(d8.getReserveReauirement8().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d8.getMinimumThreshold8().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d8.getDailyCompulsory8().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d8.getClearingAccount8().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d8.getDailyReserve8().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA9":
                            DateA9 d9 = (DateA9) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d9.getDateOfMaintenancePeriod9().toString());
                            formA.setREMD_ACCOUNTBALANCE(d9.getReserveReauirement9().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d9.getMinimumThreshold9().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d9.getDailyCompulsory9().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d9.getClearingAccount9().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d9.getDailyReserve9().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA10":
                            DateA10 d10 = (DateA10) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d10.getDateOfMaintenancePeriod10().toString());
                            formA.setREMD_ACCOUNTBALANCE(d10.getReserveReauirement10().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d10.getMinimumThreshold10().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d10.getDailyCompulsory10().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d10.getClearingAccount10().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d10.getDailyReserve10().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA11":
                            DateA11 d11 = (DateA11) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d11.getDateOfMaintenancePeriod11().toString());
                            formA.setREMD_ACCOUNTBALANCE(d11.getReserveReauirement11().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d11.getMinimumThreshold11().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d11.getDailyCompulsory11().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d11.getClearingAccount11().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d11.getDailyReserve11().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA12":
                            DateA12 d12 = (DateA12) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d12.getDateOfMaintenancePeriod12().toString());
                            formA.setREMD_ACCOUNTBALANCE(d12.getReserveReauirement12().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d12.getMinimumThreshold12().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d12.getDailyCompulsory12().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d12.getClearingAccount12().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d12.getDailyReserve12().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA13":
                            DateA13 d13 = (DateA13) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d13.getDateOfMaintenancePeriod13().toString());
                            formA.setREMD_ACCOUNTBALANCE(d13.getReserveReauirement13().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d13.getMinimumThreshold13().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d13.getDailyCompulsory13().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d13.getClearingAccount13().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d13.getDailyReserve13().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                        case "DateA14":
                            DateA14 d14 = (DateA14) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD(d14.getDateOfMaintenancePeriod14().toString());
                            formA.setREMD_ACCOUNTBALANCE(d14.getReserveReauirement14().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE(d14.getMinimumThreshold14().toString());
                            formA.setREMD_DAILYCOMPULTHRESHOLD(d14.getDailyCompulsory14().toString());
                            formA.setREMD_CLEARINGACCBALANCE(d14.getClearingAccount14().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(d14.getDailyReserve14().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                         case "ATotal":
                            ATotal ATotal = (ATotal) Ma.getValue();
                            formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD("");
                            formA.setREMD_ACCOUNTBALANCE(ATotal.getReserveReauirementTotal().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE("");
                            formA.setREMD_DAILYCOMPULTHRESHOLD("");
                            formA.setREMD_CLEARINGACCBALANCE(ATotal.getClearingAccountTotal().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(ATotal.getDailyReserveTotal().toString());
                            DataV.add(formA.getInsertScript());
                            break;
                         
                        case "ADailyAverage":
                            ADailyAverage ADailyAverage = (ADailyAverage) Ma.getValue();
                             formA.setREMD_MAINID(ReportMainID);
                            formA.setREMD_MAINTENPERIOD("");
                            formA.setREMD_ACCOUNTBALANCE(ADailyAverage.getReserveReauirementAverage().toString());
                            formA.setREMD_MINTHRESHOLDRESERVE("");
                            formA.setREMD_DAILYCOMPULTHRESHOLD("");
                            formA.setREMD_CLEARINGACCBALANCE(ADailyAverage.getClearingAccountAverage().toString());
                            formA.setREMD_DAILYRESERVEREQUIRE(ADailyAverage.getDailyReserveAverage().toString());
                            DataV.add(formA.getInsertScript());
                            data.ExecuteNonQuery(DataV.toString());
                            break;

                    }
                }
            }
             List MainChildB = weekMain.getFormB().getFormBMainDateAndBBankNameAndFormBExchangeRate();
            model_RPT9_RESERVEMAINTENUSD formBMain = new model_RPT9_RESERVEMAINTENUSD();
            model_RPT9_RESERVEMAINTENDETAILUSD formB = new model_RPT9_RESERVEMAINTENDETAILUSD();
            if (weekMain.getFormB() != null) {
                ListIterator iteratorMain = MainChildB.listIterator();
                while (iteratorMain.hasNext()) {
                    JAXBElement Ma = (JAXBElement) iteratorMain.next();
                    switch (Ma.getName().toString()) {
                        case "FormBMainDate":
                            FormBMainDate date = (FormBMainDate) Ma.getValue();
                            formBMain.setRESM_BASEDPERIOD(date.getBBasePeriod());
                            formBMain.setRESM_MAINTENACEPERIOD(date.getBMaintenancePeriod());
                            formBMain.setRESM_REPORTINGPERIOD(date.getBReportingPeriod());
                            break;
                                 case "Total":
                            BTotal BTotal = (BTotal) Ma.getValue();
                           formBMain.setRESM_TOTAL(BTotal.getBTotalReserveReauirement().toString());
                            break;
                         
                        case "BDailyAverage":
                          BDailyAverage  BDailyAverage= (BDailyAverage) Ma.getValue();
                        formBMain.setRESM_DAILYAVG(BDailyAverage.getBDailyReserveReauirement().toString());
                            break;
                        case "FormBMainValue":

                            FormBMainValue value = (FormBMainValue) Ma.getValue();
                            formBMain.setRESM_REPORTID(reportID);
                            formBMain.setRESM_MINRESERVE(value.getMinimumReserve().toString());
                            formBMain.setRESM_MINSURPLUS(value.getReserveRequirmentSurplus().toString());
                            formBMain.setRESM_DEFICIT(value.getReserveRequirmentDeficit().toString());
                            data.ExecuteNonQuery(formBMain.getInsertScript());
                            String query = "SELECT MAX(REMA_NO) FROM RPT9_RESERVEMAINTENKHR";
                            ResultSet myResultSet = data.ExecuteReader(query);
                            myResultSet.next();
                            ReportMainID = myResultSet.getString(1);

                            break;



                        case "DateB1":
                            DateB1 d1 = (DateB1) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d1.getDateOfMaintenancePeriod1().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d1.getReserveReauirement1().toString());
                            formB.setRRDE_MINTHRESHOLD(d1.getMinimumThreshold1().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d1.getDailyCompulsory1().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB2":
                            DateB2 d2 = (DateB2) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d2.getDateOfMaintenancePeriod2().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d2.getReserveReauirement2().toString());
                            formB.setRRDE_MINTHRESHOLD(d2.getMinimumThreshold2().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d2.getDailyCompulsory2().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB3":
                            DateB3 d3 = (DateB3) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d3.getDateOfMaintenancePeriod3().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d3.getReserveReauirement3().toString());
                            formB.setRRDE_MINTHRESHOLD(d3.getMinimumThreshold3().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d3.getDailyCompulsory3().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB4":
                             DateB4 d4 = (DateB4) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d4.getDateOfMaintenancePeriod4().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d4.getReserveReauirement4().toString());
                            formB.setRRDE_MINTHRESHOLD(d4.getMinimumThreshold4().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d4.getDailyCompulsory4().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB5":
                             DateB5 d5 = (DateB5) Ma.getValue();
                           formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d5.getDateOfMaintenancePeriod5().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d5.getReserveReauirement5().toString());
                            formB.setRRDE_MINTHRESHOLD(d5.getMinimumThreshold5().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d5.getDailyCompulsory5().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB6":
                             DateB6 d6 = (DateB6) Ma.getValue();
                           formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d6.getDateOfMaintenancePeriod6().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d6.getReserveReauirement6().toString());
                            formB.setRRDE_MINTHRESHOLD(d6.getMinimumThreshold6().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d6.getDailyCompulsory6().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB7":
                             DateB7 d7 = (DateB7) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d7.getDateOfMaintenancePeriod7().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d7.getReserveReauirement7().toString());
                            formB.setRRDE_MINTHRESHOLD(d7.getMinimumThreshold7().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d7.getDailyCompulsory7().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB8":
                             DateB8 d8 = (DateB8) Ma.getValue();
                          formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d8.getDateOfMaintenancePeriod8().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d8.getReserveReauirement8().toString());
                            formB.setRRDE_MINTHRESHOLD(d8.getMinimumThreshold8().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d8.getDailyCompulsory8().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB9":
                             DateB9 d9 = (DateB9) Ma.getValue();
                           formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d9.getDateOfMaintenancePeriod9().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d9.getReserveReauirement9().toString());
                            formB.setRRDE_MINTHRESHOLD(d9.getMinimumThreshold9().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d9.getDailyCompulsory9().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB10":
                             DateB10 d10 = (DateB10) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d10.getDateOfMaintenancePeriod10().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d10.getReserveReauirement10().toString());
                            formB.setRRDE_MINTHRESHOLD(d10.getMinimumThreshold10().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d10.getDailyCompulsory10().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB11":
                            DateB11 d11 = (DateB11) Ma.getValue();
                           formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d11.getDateOfMaintenancePeriod11().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d11.getReserveReauirement11().toString());
                            formB.setRRDE_MINTHRESHOLD(d11.getMinimumThreshold11().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d11.getDailyCompulsory11().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB12":
                            DateB12 d12 = (DateB12) Ma.getValue();
                        formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d12.getDateOfMaintenancePeriod12().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d12.getReserveReauirement12().toString());
                            formB.setRRDE_MINTHRESHOLD(d12.getMinimumThreshold12().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d12.getDailyCompulsory12().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB13":
                            DateB13 d13 = (DateB13) Ma.getValue();
                          formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d13.getDateOfMaintenancePeriod13().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d13.getReserveReauirement13().toString());
                            formB.setRRDE_MINTHRESHOLD(d13.getMinimumThreshold13().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d13.getDailyCompulsory13().toString());
                            DataV.add(formB.getInsertScript());
                            break;
                        case "DateB14":
                            DateB14 d14 = (DateB14) Ma.getValue();
                            formB.setRRDE_MAINID(ReportMainID);
                            formB.setRRDE_MAINTENANCEPERIOD(d14.getDateOfMaintenancePeriod14().toString());
                            formB.setRRDE_RESERVEACCBALANCE(d14.getReserveReauirement14().toString());
                            formB.setRRDE_MINTHRESHOLD(d14.getMinimumThreshold14().toString());
                            formB.setRRDE_DAILYCOMPUTHRESHOLD(d14.getDailyCompulsory14().toString());
                            DataV.add(formB.getInsertScript());
                            data.ExecuteNonQuery(DataV.toString());
                            break;
                        

                    }
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(BiWeeklyMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
