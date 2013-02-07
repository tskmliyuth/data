/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYXML;

import generated.BiWeeklyLR;
import generated.MONTHLY;
import generated.WeeklyReport;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import myModels.clsDataBase;
import org.xml.sax.SAXException;

/**
 *
 * @author moliyuth
 */
public class WeeklyXML {
     String ErrorMessage = "";

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
    String xml;
    String xsd;
    
    public WeeklyXML(String xmlfile){
        this.xml=xmlfile;
    }
      public void readxml(String reportID) throws SAXException {
      try{
          File file = new File(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(WeeklyReport.class);
            clsDataBase data = new clsDataBase();
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            WeeklyReport week = (WeeklyReport) jaxbUnmarshaller.unmarshal(file);
            model_RPT27_WEEKLYCASHSTATEMENT we=new model_RPT27_WEEKLYCASHSTATEMENT();
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("1");
            we.setCASA_AMOUNT(week.getPreviousBalance().getPreviousBalanceKHR().toString());
            we.setCASA_REMARK("");
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("2");
            we.setCASA_AMOUNT(week.getFromDeposit().getFromDepositKHR().toString());
            we.setCASA_REMARK(week.getFromDeposit().getFromDepositRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("3");
            we.setCASA_AMOUNT(week.getFDSaving().getFDSavingKHR().toString());
            we.setCASA_REMARK(week.getFDSaving().getFDSavingRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("4");
            we.setCASA_AMOUNT(week.getFDCurrent().getFDCurrentKHR().toString());
            we.setCASA_REMARK(week.getFDCurrent().getFDCurrentRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("5");
            we.setCASA_AMOUNT(week.getFDFixed().getFDFixedKHR().toString());
            we.setCASA_REMARK(week.getFDFixed().getFDFixedRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("6");
            we.setCASA_AMOUNT(week.getLoanRepayments().getLoanRepaymentKHR().toString());
            we.setCASA_REMARK(week.getLoanRepayments().getLoanRepaymentRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("7");
            we.setCASA_AMOUNT(week.getCurrencySale().getCurrencySaleKHR().toString());
            we.setCASA_REMARK(week.getCurrencySale().getCurrencySaleRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("8");
            we.setCASA_AMOUNT(week.getFundFromNBC().getFundFromNBCKHR().toString());
            we.setCASA_REMARK(week.getFundFromNBC().getFundFromNBCRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("9");
            we.setCASA_AMOUNT(week.getFundFromOtherBank().getFundFromOtherBankKHR().toString());
            we.setCASA_REMARK(week.getFundFromOtherBank().getFundFromOtherBankRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("10");
            we.setCASA_AMOUNT(week.getOtherReceipt().getOtherReceiptKHR().toString());
            we.setCASA_REMARK(week.getOtherReceipt().getOtherReceiptRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("11");
            we.setCASA_AMOUNT(week.getTotalReceipt().getTotalReceiptKHR().toString());
            we.setCASA_REMARK(week.getTotalReceipt().getTotalReceiptRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("12");
            we.setCASA_AMOUNT(week.getTotalReceipt().getTotalReceiptKHR().toString());
            we.setCASA_REMARK(week.getTotalReceipt().getTotalReceiptRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("13");
            we.setCASA_AMOUNT(week.getSubTotalReceipt().getSubTotalReceiptKHR().toString());
            we.setCASA_REMARK(week.getSubTotalReceipt().getSubTotalReceiptRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("14");
            we.setCASA_AMOUNT(week.getWithdrawalDeposit().getWithdrawalDepositKHR().toString());
            we.setCASA_REMARK(week.getWithdrawalDeposit().getWithdrawalDepositRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("15");
            we.setCASA_AMOUNT(week.getWDSaving().getWDSavingKHR().toString());
            we.setCASA_REMARK(week.getWDSaving().getWDSavingRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID); 
            we.setCASA_TEMID("16");
            we.setCASA_AMOUNT(week.getWDCurrent().getWDCurrentKHR().toString());
            we.setCASA_REMARK(week.getWDCurrent().getWDCurrentRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("17");
            we.setCASA_AMOUNT(week.getWDFixed().getWDFixedKHR().toString());
            we.setCASA_REMARK(week.getWDFixed().getWDFixedRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("18");
            we.setCASA_AMOUNT(week.getLoanPaid().getLoanPaidKHR().toString());
            we.setCASA_REMARK(week.getLoanPaid().getLoanPaidRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("19");
            we.setCASA_AMOUNT(week.getCurrencyPurchase().getCurrencyPurchaseKHR().toString());
            we.setCASA_REMARK(week.getCurrencyPurchase().getCurrencyPurchaseRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("20");
            we.setCASA_AMOUNT(week.getFundToNBC().getFundToNBCKHR().toString());
            we.setCASA_REMARK(week.getFundToNBC().getFundToNBCRemark());
            data.ExecuteNonQuery(we.getInsertScript());
                we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("21");
            we.setCASA_AMOUNT(week.getFundToOtherBank().getFundToOtherBankKHR().toString());
            we.setCASA_REMARK(week.getFundToOtherBank().getFundToOtherBankRemark());
            data.ExecuteNonQuery(we.getInsertScript());
                 we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("22");
            we.setCASA_AMOUNT(week.getOtherPayment().getOtherPaymentKHR().toString());
            we.setCASA_REMARK(week.getOtherPayment().getOtherPaymentRemark());
            data.ExecuteNonQuery(we.getInsertScript());
               we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("23");
            we.setCASA_AMOUNT(week.getDeductPayment().getDeductPaymentKHR().toString());
            we.setCASA_REMARK(week.getDeductPayment().getDeductPaymentRemark());
            data.ExecuteNonQuery(we.getInsertScript());
             we.setCASA_REPORTID(reportID);
            we.setCASA_TEMID("24");
            we.setCASA_AMOUNT(week.getEndingBalance().getEndingBalanceKHR().toString());
            we.setCASA_REMARK(week.getEndingBalance().getEndingBalanceRemark());
            data.ExecuteNonQuery(we.getInsertScript());
            
            
      }catch (JAXBException ex) {
            Logger.getLogger(WeeklyReport.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
}
