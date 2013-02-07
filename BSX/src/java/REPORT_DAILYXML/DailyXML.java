package REPORT_DAILYXML;


import REPORT_DAILYXML.NXMLloader;
import generated.DailyReport;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import myModels.clsDataBase;
import org.xml.sax.SAXException;

public class DailyXML {

    String xml;
    String xsd;
    ArrayList RecordUSD = new ArrayList();
    ArrayList RecordKHR = new ArrayList();
    ArrayList comment = new ArrayList();

    public DailyXML( String xmlfile) {
        this.xml = xmlfile;
      
    }

    public void Readxml(String ReportID) throws SAXException {

    
            try {
               
                File file = new File(xml);
                JAXBContext jaxbContext = JAXBContext.newInstance(DailyReport.class);
                model_RPT26_TBT_CASH_STATEMENT daily = new model_RPT26_TBT_CASH_STATEMENT();
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                DailyReport customer = (DailyReport) jaxbUnmarshaller.unmarshal(file);
                
                RecordUSD.add(customer.getPreviousBalance().getPreviousBalanceUSD().toString());
                RecordUSD.add(customer.getFromDeposit().getFromDepositUSD().toString());
                RecordUSD.add(customer.getFDSaving().getFDSavingUSD().toString());
                RecordUSD.add(customer.getFDCurrent().getFDCurrentUSD().toString());
                RecordUSD.add(customer.getFDFixed().getFDFixedUSD().toString());
                RecordUSD.add(customer.getLoanRepayments().getLoanRepaymentUSD().toString());
                RecordUSD.add(customer.getCurrencySale().getCurrencySaleUSD().toString());
                RecordUSD.add(customer.getFundFromNBC().getFundFromNBCUSD().toString());
                RecordUSD.add(customer.getFundFromOtherBank().getFundFromOtherBankUSD().toString());
                RecordUSD.add(customer.getOtherReceipt().getOtherReceiptUSD().toString());
                RecordUSD.add(customer.getTotalReceipt().getTotalReceiptUSD().toString());
                RecordUSD.add(customer.getSubTotalReceipt().getSubTotalReceiptUSD().toString());
                RecordUSD.add(customer.getWithdrawalDeposit().getWithdrawalDepositUSD().toString());
                RecordUSD.add(customer.getWDSaving().getWDSavingUSD().toString());
                RecordUSD.add(customer.getWDCurrent().getWDCurrentUSD().toString());
                RecordUSD.add(customer.getWDFixed().getWDFixedUSD().toString());
                RecordUSD.add(customer.getLoanPaid().getLoanPaidUSD().toString());
                RecordUSD.add(customer.getCurrencyPurchase().getCurrencyPurchaseUSD().toString());
                RecordUSD.add(customer.getFundToNBC().getFundToNBCUSD().toString());
                RecordUSD.add(customer.getFundToOtherBank().getFundToOtherBankUSD().toString());
                RecordUSD.add(customer.getOtherPayment().getOtherPaymentUSD().toString());
                RecordUSD.add(customer.getDeductPayment().getDeductPaymentUSD().toString());
                RecordUSD.add(customer.getEndingBalance().getEndingBalanceUSD().toString());
                //..............................................................................
                RecordKHR.add(customer.getPreviousBalance().getPreviousBalanceKHR().toString());
                RecordKHR.add(customer.getFromDeposit().getFromDepositKHR().toString());
                RecordKHR.add(customer.getFDSaving().getFDSavingKHR().toString());
                RecordKHR.add(customer.getFDCurrent().getFDCurrentKHR().toString());
                RecordKHR.add(customer.getFDFixed().getFDFixedKHR().toString());
                RecordKHR.add(customer.getLoanRepayments().getLoanRepaymentKHR().toString());
                RecordKHR.add(customer.getCurrencySale().getCurrencySaleKHR().toString());
                RecordKHR.add(customer.getFundFromNBC().getFundFromNBCKHR().toString());
                RecordKHR.add(customer.getFundFromOtherBank().getFundFromOtherBankKHR().toString());
                RecordKHR.add(customer.getOtherReceipt().getOtherReceiptKHR().toString());
                RecordKHR.add(customer.getTotalReceipt().getTotalReceiptKHR().toString());
                RecordKHR.add(customer.getSubTotalReceipt().getSubTotalReceiptKHR().toString());
                RecordKHR.add(customer.getWithdrawalDeposit().getWithdrawalDepositKHR().toString());
                RecordKHR.add(customer.getWDSaving().getWDSavingKHR().toString());
                RecordKHR.add(customer.getWDCurrent().getWDCurrentKHR().toString());
                RecordKHR.add(customer.getWDFixed().getWDFixedKHR().toString());
                RecordKHR.add(customer.getLoanPaid().getLoanPaidKHR().toString());
                RecordKHR.add(customer.getCurrencyPurchase().getCurrencyPurchaseKHR().toString());
                RecordKHR.add(customer.getFundToNBC().getFundToNBCKHR().toString());
                RecordKHR.add(customer.getFundToOtherBank().getFundToOtherBankKHR().toString());
                RecordKHR.add(customer.getOtherPayment().getOtherPaymentKHR().toString());
                RecordKHR.add(customer.getDeductPayment().getDeductPaymentKHR().toString());
                RecordKHR.add(customer.getEndingBalance().getEndingBalanceKHR().toString());
                //........................................................
                comment.add("");
                try{
                    
                
                comment.add(customer.getFromDeposit().getFromDepositRemark().toString());
                }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFDSaving().getFDSavingRemark().toString());
                 }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFDCurrent().getFDCurrentRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFDFixed().getFDFixedRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getLoanRepayments().getLoanRepaymentRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getCurrencySale().getCurrencySaleRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFundFromNBC().getFundFromNBCRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFundFromOtherBank().getFundFromOtherBankRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getOtherReceipt().getOtherReceiptRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getTotalReceipt().getTotalReceiptRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getSubTotalReceipt().getSubTotalReceiptRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getWithdrawalDeposit().getWithdrawalDepositRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getWDSaving().getWDSavingRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getWDCurrent().getWDCurrentRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getWDFixed().getWDFixedRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getLoanPaid().getLoanPaidRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getCurrencyPurchase().getCurrencyPurchaseRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFundToNBC().getFundToNBCRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getFundToOtherBank().getFundToOtherBankRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getOtherPayment().getOtherPaymentRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getDeductPayment().getDeductPaymentRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                try{
                comment.add(customer.getEndingBalance().getEndingBalanceRemark().toString());
                  }catch(Exception e){
                  comment.add("");  
                }
                
clsDataBase data= new clsDataBase();

            for(int i=0;i<23;i++){
                daily.setCASH_ITEMID(Integer.toString(i+1));
                daily.setCASH_REPORTID(ReportID);
                
                daily.setCASH_AMOUNTKHR(RecordKHR.get(i).toString());
                daily.setCASH_AMOUNTUSD(RecordUSD.get(i).toString());
                daily.setCASH_REMARK(comment.get(i).toString());
                data.ExecuteNonQuery(daily.getInsertScript());          
            } 
            } catch (JAXBException ex) {
                Logger.getLogger(DailyXML.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
}