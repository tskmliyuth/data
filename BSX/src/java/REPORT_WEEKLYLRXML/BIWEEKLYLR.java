/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_WEEKLYLRXML;

import generated.BiWeeklyLR;
import generated.BiWeeklyLR.DDP.Current;
import generated.BiWeeklyLR.DDP.Fixed;
import generated.BiWeeklyLR.DDP.Others;
import generated.BiWeeklyLR.DDP.Saving;
import generated.BiWeeklyLR.DO.Data.Record;

import generated.BiWeeklyLR.Liquidity;
import generated.BiWeeklyLR.Liquidity.CreditItems;
import generated.BiWeeklyLR.Liquidity.DebitItems;
import generated.BiWeeklyLR.Liquidity.DenominatorItems;
import generated.BiWeeklyLR.Liquidity.NumeratorItems;
import java.io.File;
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

public class BIWEEKLYLR {

    String ErrorMessage = "";

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
    String xml;
    String xsd;
 

    public BIWEEKLYLR(String xmlfile) {
        this.xml = xmlfile;

    }

    public void Readxml(String ReportID) throws SAXException {


        try {

            File file = new File(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(BiWeeklyLR.class);
            clsDataBase data = new clsDataBase();
            model_RPT_BIWEEKDEPOSITP myData = new model_RPT_BIWEEKDEPOSITP();
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BiWeeklyLR bw = (BiWeeklyLR) jaxbUnmarshaller.unmarshal(file);

            List ddpChild = bw.getDDP().getDDPReportingDateAndDDPBankNameAndDDPExchangeRate();
            if (bw.getDDP() != null) {
                ListIterator iteratorDDP = ddpChild.listIterator();
                while (iteratorDDP.hasNext()) {

                    /*
                     * Shift the iterator to next object Assign the object to
                     * JAXBElement
                     */
                    JAXBElement elm = (JAXBElement) iteratorDDP.next();

                    switch (elm.getName().toString()) {

                        case "Current":
                            Current current = (Current) elm.getValue();
                            myData.setBIDP_REPORTID(ReportID);
                            myData.setBIDP_TYPE("1");
                            myData.setBIDP_NBANKOPENBL(current.getCurrentNonBankOpeningBalance().toString());
                            myData.setBIDP_NBANKDEPOSIT(current.getCurrentNonBankDeposit().toString());
                            myData.setBIDP_NBANKWITHDRAWAL(current.getCurrentNonBankWithdrawal().toString());
                            myData.setBIDP_NBANKENDBL(current.getCurrentNonBankEndingBalance().toString());
                            myData.setBIDP_BANKOPENBL(current.getCurrentBankOpeningBalance().toString());
                            myData.setBIDP_BANKDEPOSIT(current.getCurrentBankDeposit().toString());
                            myData.setBIDP_BANKWITHDRAWAL(current.getCurrentBankWithdrawal().toString());
                            myData.setBIDP_ENDBALANCE(current.getCurrentBankEndingBalance().toString());
                            myData.setBIDP_TOTAL(current.getCurrentTotal().toString());
                            //      System.out.println(myData.getInsertScript());
                            data.ExecuteNonQuery(myData.getInsertScript());


                            break;
                        case "Saving":
                            Saving saving = (Saving) elm.getValue();
                            myData.setBIDP_REPORTID(ReportID);
                            myData.setBIDP_TYPE("2");
                            myData.setBIDP_NBANKOPENBL(saving.getSavingNonBankOpeningBalance().toString());
                            myData.setBIDP_NBANKDEPOSIT(saving.getSavingNonBankDeposit().toString());
                            myData.setBIDP_NBANKWITHDRAWAL(saving.getSavingNonBankWithdrawal().toString());
                            myData.setBIDP_NBANKENDBL(saving.getSavingNonBankEndingBalance().toString());
                            myData.setBIDP_BANKOPENBL(saving.getSavingBankOpeningBalance().toString());
                            myData.setBIDP_BANKDEPOSIT(saving.getSavingBankDeposit().toString());
                            myData.setBIDP_BANKWITHDRAWAL(saving.getSavingBankWithdrawal().toString());
                            myData.setBIDP_ENDBALANCE(saving.getSavingBankEndingBalance().toString());
                            myData.setBIDP_TOTAL(saving.getSavingTotal().toString());
                            //      System.out.println(myData.getInsertScript());
                            data.ExecuteNonQuery(myData.getInsertScript());
                            break;
                        case "Fixed":
                            Fixed fixed = (Fixed) elm.getValue();
                            myData.setBIDP_REPORTID(ReportID);
                            myData.setBIDP_TYPE("3");
                            myData.setBIDP_NBANKOPENBL(fixed.getFixedNonBankOpeningBalance().toString());
                            myData.setBIDP_NBANKDEPOSIT(fixed.getFixedNonBankDeposit().toString());
                            myData.setBIDP_NBANKWITHDRAWAL(fixed.getFixedNonBankWithdrawal().toString());
                            myData.setBIDP_NBANKENDBL(fixed.getFixedNonBankEndingBalance().toString());
                            myData.setBIDP_BANKOPENBL(fixed.getFixedBankOpeningBalance().toString());
                            myData.setBIDP_BANKDEPOSIT(fixed.getFixedBankDeposit().toString());
                            myData.setBIDP_BANKWITHDRAWAL(fixed.getFixedBankWithdrawal().toString());
                            myData.setBIDP_ENDBALANCE(fixed.getFixedBankEndingBalance().toString());
                            myData.setBIDP_TOTAL(fixed.getFixedTotal().toString());
                            //     System.out.println(myData.getInsertScript());
                            data.ExecuteNonQuery(myData.getInsertScript());
                            break;
                        case "Others":
                            Others others = (Others) elm.getValue();
                            myData.setBIDP_REPORTID(ReportID);
                            myData.setBIDP_TYPE("4");
                            myData.setBIDP_NBANKOPENBL(others.getOthersNonBankOpeningBalance().toString());
                            myData.setBIDP_NBANKDEPOSIT(others.getOthersNonBankDeposit().toString());
                            myData.setBIDP_NBANKWITHDRAWAL(others.getOthersNonBankWithdrawal().toString());
                            myData.setBIDP_NBANKENDBL(others.getOthersNonBankEndingBalance().toString());
                            myData.setBIDP_BANKOPENBL(others.getOthersBankOpeningBalance().toString());
                            myData.setBIDP_BANKDEPOSIT(others.getOthersBankDeposit().toString());
                            myData.setBIDP_BANKWITHDRAWAL(others.getOthersBankWithdrawal().toString());
                            myData.setBIDP_ENDBALANCE(others.getOthersBankEndingBalance().toString());
                            myData.setBIDP_TOTAL(others.getOthersTotal().toString());
                            //   System.out.println(myData.getInsertScript());
                            data.ExecuteNonQuery(myData.getInsertScript());
                            break;

                    }

                }
            } else {
                setErrorMessage("You do not have DDP to summit to NBC");
            }
            /*
             * Declare for Read xml from DO Report
             */
            model_RPT_BIWEEKDOC myDataDO = new model_RPT_BIWEEKDOC();
            if (bw.getDO() != null) {
                List doChild = bw.getDO().getData().getRecord();

                ListIterator iteratorDo = doChild.listIterator();
                while (iteratorDo.hasNext()) {
                    Record elm = (Record) iteratorDo.next();
                    myDataDO.setBIDO_BANKNAME(elm.getBankName());
                    myDataDO.setBIDO_COUNTRY(elm.getCountry());
                    myDataDO.setBIDO_CURRENTACC(elm.getCurrentAccount().toString());
                    myDataDO.setBIDO_FIXDEPOSITS1M(elm.getOneMonths().toString());
                    myDataDO.setBIDO_FIXDEPOSITS3M(elm.getThreeMonths().toString());
                    myDataDO.setBIDO_FIXDEPOSITS6M(elm.getSixMonths().toString());
                    myDataDO.setBIDO_FIXDEPOSITS12M(elm.getTwelveMonths().toString());
                    myDataDO.setBIDO_FIXDEPOSITS12MUP(elm.getOverTwelveMonths().toString());
                    myDataDO.setBIDO_FIXEDEPOSITSOTHER(elm.getOtherDeposits().toString());
                    myDataDO.setBIDO_FIXDEPOSITSTOTAL(elm.getTotal().toString());
                    myDataDO.setBIDO_REPORTID(ReportID);
                    data.ExecuteNonQuery(myDataDO.getInsertScript());
                }



            } else {
                if (bw.getDDP() == null) {
                    setErrorMessage("No value insert to DDP , DO");
                } else {
                    setErrorMessage("You do not have DO to summit to NBC");
                }
            }
            /*
             * Declare for Liquidity Raitio
             */
            model_RPT5_TBT_LIQUIDRATIO myDataLR = new model_RPT5_TBT_LIQUIDRATIO();
            if (bw.getLiquidity() != null) {

                List LRChild = bw.getLiquidity().getLiquidityReportingDateAndLiquidityBankNameAndLiquidityExchangeRate();
                ListIterator iteratorLR = LRChild.listIterator();
                while (iteratorLR.hasNext()) {
                    JAXBElement elmlR = (JAXBElement) iteratorLR.next();




                    switch (elmlR.getName().toString()) {
                        case "DebitItems":
                            DebitItems debit = (DebitItems) elmlR.getValue();
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("1");
                            myDataLR.setLIRA_AMOUNTINUSD(debit.getDebitItemsUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(debit.getDebitItemsKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());

                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("2");
                            myDataLR.setLIRA_AMOUNTINUSD(debit.getCashGoldUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(debit.getCashGoldKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("3");
                            myDataLR.setLIRA_AMOUNTINUSD(debit.getDepositNBCUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(debit.getDepositNBCKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("4");
                            myDataLR.setLIRA_AMOUNTINUSD(debit.getDepositBankUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(debit.getDepositBankKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("5");
                            myDataLR.setLIRA_AMOUNTINUSD(debit.getPortionOfLendingToBankUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(debit.getPortionOfLendingToBankKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;
                        case "CreditItems":
                            CreditItems credit = (CreditItems) elmlR.getValue();
                            /*
                             * credit item
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("6");
                            myDataLR.setLIRA_AMOUNTINUSD(credit.getCreditItemsUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(credit.getCreditItemsKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            myDataLR.setLIRA_REPORTID(ReportID);
                            /*
                             * creditbalance
                             */
                            myDataLR.setLIRA_LIQRATIOITEMID("7");
                            myDataLR.setLIRA_AMOUNTINUSD(credit.getCreditBalancesUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(credit.getCreditBalancesKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * borrowing
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("8");
                            myDataLR.setLIRA_AMOUNTINUSD(credit.getBorrowingsUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(credit.getBorrowingsKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;

                        /*
                         * different between A and B
                         */
                        case "ABDifferenceUSD":
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("9");
                            myDataLR.setLIRA_AMOUNTINUSD(elmlR.getValue().toString());
                            break;
                        case "ABDifferenceKHR":
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(elmlR.getValue().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;
                        case "NumeratorItems":
                            /*
                             * numeratoritem
                             */
                            NumeratorItems Numr = (NumeratorItems) elmlR.getValue();
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("10");
                            myDataLR.setLIRA_AMOUNTINUSD(Numr.getNumeratorItemsUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Numr.getNumeratorItemsKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());

                            /*
                             * if c(a)-(b)>0
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("11");
                            myDataLR.setLIRA_AMOUNTINUSD(Numr.getFromCUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Numr.getFromCKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * portion
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("12");
                            myDataLR.setLIRA_AMOUNTINUSD(Numr.getPortionOfLendingToCustomerUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Numr.getPortionOfLendingToCustomerKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * if c(a)-(b)>0
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("13");
                            myDataLR.setLIRA_AMOUNTINUSD(Numr.getTreasuryBillUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Numr.getTreasuryBillKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;
                        case "DenominatorItems":
                            DenominatorItems Denumr = (DenominatorItems) elmlR.getValue();
                            /*
                             * Denominator item
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("14");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getDenominatorItemsUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getDenominatorItemsKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * absolute amount
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("15");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getAbsoluteAmountOfCUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getAbsoluteAmountOfCKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * 80% less
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("16");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getEightyPercentOfFixedDepositUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getEightyPercentOfFixedDepositKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * 50% less
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("17");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getFiftyPercentOfFixedDepositUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getFiftyPercentOfFixedDepositKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * 50% saving
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("18");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getFiftyPercentOfSavingDepositUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getFiftyPercentOfSavingDepositKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            /*
                             * 60% saving
                             */
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("19");
                            myDataLR.setLIRA_AMOUNTINUSD(Denumr.getSixtyPercentOfDemandDepositUSD().toString());
                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(Denumr.getSixtyPercentOfDemandDepositKHR().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;
                        case "LiquidityRatioUSD":
                            myDataLR.setLIRA_REPORTID(ReportID);
                            myDataLR.setLIRA_LIQRATIOITEMID("20");
                            myDataLR.setLIRA_AMOUNTINUSD(elmlR.getValue().toString());
                            break;
                        case "LiquidityRatioKHR":

                            myDataLR.setLIRA_AMOUNTMILLIONRIEL(elmlR.getValue().toString());
                            data.ExecuteNonQuery(myDataLR.getInsertScript());
                            break;


                    }
                }


            } else {
                System.out.println("NO");
                if (bw.getDDP() == null && bw.getDO() == null) {
                    setErrorMessage("No value insert to DDP , DO and Liquidity Ratio");
                } else {
                    setErrorMessage("You do not have Liquidity Ratio to summit to NBC");
                }
            }

        } catch (JAXBException ex) {
            Logger.getLogger(BiWeeklyLR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String args[]) throws SAXException{
    BIWEEKLYLR b= new BIWEEKLYLR("C:\\Users\\moliyuth\\Desktop\\biweekly.xml");
    b.Readxml("2");
    System.out.println(b.getErrorMessage());
    }
}
