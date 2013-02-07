/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_MONTHLYXML;

import generated.MONTHLY;
import generated.MONTHLY.BALANCESHEET.BSASSETS;
import generated.MONTHLY.INTERBANK.BankResident.RecordBankNonResident;
import generated.MONTHLY.INTERBANK.BankResident.RecordBankResident;
import generated.MONTHLY.INTERBANK.Insurrance.RecordInsurranceNonResident;
import generated.MONTHLY.INTERBANK.Insurrance.RecordInsurranceResident;
import generated.MONTHLY.INTERBANK.OtherFis.RecordOtherfisNonResident;
import generated.MONTHLY.INTERBANK.OtherFis.RecordOtherfisResident;
import generated.MONTHLY.INTERBANK.SecurityFirm.RecordSecurityNonResident;
import generated.MONTHLY.INTERBANK.SecurityFirm.RecordSecurityResident;

import generated.MONTHLY.NETOPENPOSITION.NOPCurrency;
import generated.MONTHLY.NETWORKINFO.Data.Record;
import generated.MONTHLY.NETWORTH.NWSubTotalA;
import generated.MONTHLY.NETWORTH.NWSubTotalB;
import generated.MONTHLY.NETWORTH.NWSubTotalC;
import generated.MONTHLY.NETWORTH.NWSubTotalD;
import generated.MONTHLY.OFFBALANCESHEET.OBSDerivativeInstrumentsPositions;
import generated.MONTHLY.OFFBALANCESHEET.OBSFinancingCommitments;
import generated.MONTHLY.OFFBALANCESHEET.OBSForeignExchangeCommitments;
import generated.MONTHLY.OFFBALANCESHEET.OBSGuarantees;
import generated.MONTHLY.OFFBALANCESHEET.OBSOtherCommitments;
import generated.MONTHLY.SOLVENCYRATIO.SRBalanceSheetItems;
import generated.MONTHLY.SOLVENCYRATIO.SROffBalanceSheetItems;
import java.io.File;
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

/**
 *
 * @author moliyuth
 */
public class monthlyReport {

    String ErrorMessage = "";

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
    String xml;
    String xsd;

    public monthlyReport(String xmlfile) {
        this.xml = xmlfile;
    }

    public void readxml(String reportID) throws SAXException, SQLException, ClassNotFoundException {
        Model_RPT3_OFFBAL_SHEET dOffBa = new Model_RPT3_OFFBAL_SHEET();
        try {
            File file = new File(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(MONTHLY.class);
            clsDataBase data = new clsDataBase();
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            MONTHLY mLR = (MONTHLY) jaxbUnmarshaller.unmarshal(file);

            /*
             * *************off balance sheet report**********
             */
            Model_RPT1_BAL_SHEET_P1 ba1= new Model_RPT1_BAL_SHEET_P1();
            Model_RPT1_BAL_SHEET_P2 ba2=new Model_RPT1_BAL_SHEET_P2();
            ArrayList off = new ArrayList();
            if(mLR.getBALANCESHEET()!=null){
                List Balance= mLR.getBALANCESHEET().getBSReportingDateAndBSBankNameAndBSExchangeRate();
                ListIterator iteratorBal = Balance.listIterator();
                while(iteratorBal.hasNext()){
                    JAXBElement elmBal= (JAXBElement) iteratorBal.next();
                    switch(elmBal.getName().toString()){
                        case "BSASSETS":
                            BSASSETS Ass = (BSASSETS) elmBal.getValue();
                            
                    }
                }
            }
            if (mLR.getOFFBALANCESHEET() != null) {
                List offBa = mLR.getOFFBALANCESHEET().getOBSReportingDateAndOBSBankNameAndOBSExchangeRate();
                ListIterator iteratorOffBa = offBa.listIterator();
                while (iteratorOffBa.hasNext()) {
                    JAXBElement elmOffBa = (JAXBElement) iteratorOffBa.next();
                    switch (elmOffBa.getName().toString()) {
                        case "OBSFinancingCommitments":
                            OBSFinancingCommitments fic = (OBSFinancingCommitments) elmOffBa.getValue();
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("1");
                            dOffBa.setOBSH_RIELS(fic.getOBSFinancingCommitmentsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fic.getOBSFinancingCommitmentsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fic.getOBSFinancingCommitmentsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("2");
                            dOffBa.setOBSH_RIELS(fic.getOBSReceivedfromBanksandOtherFIRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fic.getOBSReceivedfromBanksandOtherFIOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fic.getOBSReceivedfromBanksandOtherFITotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("3");
                            dOffBa.setOBSH_RIELS(fic.getOBSInFavourofBanksandOtherFIRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fic.getOBSInFavourofBanksandOtherFIOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fic.getOBSInFavourofBanksandOtherFITotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("4");
                            dOffBa.setOBSH_RIELS(fic.getOBSInFavourofCusRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fic.getOBSInFavourofCusOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fic.getOBSInFavourofCusTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("5");
                            dOffBa.setOBSH_RIELS(fic.getOBSOfwhichUnusedPortionofOverdraftLoansandfacilitiesextendedRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fic.getOBSOfwhichUnusedPortionofOverdraftLoansandfacilitiesextendedOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fic.getOBSOfwhichUnusedPortionofOverdraftLoansandfacilitiesextendedTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            //   System.out.println(dOffBa.getInsertScript());

                            break;
                        case "OBSGuarantees":
                            OBSGuarantees guar = (OBSGuarantees) elmOffBa.getValue();
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_TEMID("6");
                            dOffBa.setOBSH_RIELS(guar.getOBSGuaranteesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSGuaranteesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSGuaranteesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("7");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSGuaranteesReceivedRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSGuaranteesReceivedOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSGuaranteesReceivedTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("8");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSGuaranteesIssuedRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSGuaranteesIssuedOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSGuaranteesIssuedTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("9");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSIssuedOnAccOfCusRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSIssuedOnAccOfCusOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSIssuedOnAccOfCusTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("10");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSAcceptancesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSAcceptancesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSAcceptancesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("11");

                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSStandbyLettersofCreditRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSStandbyLettersofCreditOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSStandbyLettersofCreditTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("12");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSBidBondsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSBidBondsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSBidBondsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("13");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSPerformanceBondsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSPerformanceBondsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSPerformanceBondsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("14");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSWarrantyBondsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSWarrantyBondsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSWarrantyBondsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("15");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSPaymentGuaranteesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSPaymentGuaranteesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSPaymentGuaranteesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("16");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSEndorsementsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSEndorsementsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSEndorsementsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("17");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(guar.getOBSIssuedOnAccOfCus().getOBSOtherGuaranteesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(guar.getOBSIssuedOnAccOfCus().getOBSOtherGuaranteesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(guar.getOBSIssuedOnAccOfCus().getOBSOtherGuaranteesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            break;

                        case "OBSForeignExchangeCommitments":
                            OBSForeignExchangeCommitments fec = (OBSForeignExchangeCommitments) elmOffBa.getValue();
                            dOffBa.setOBSH_TEMID("18");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSForeignExchangeCommitmentsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSForeignExchangeCommitmentsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSForeignExchangeCommitmentsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("19");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSCommitmentstoBuyForeignCcyRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoBuyForeignCcy().getOBSCommitmentstoBuyForeignCcyOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSCommitmentstoBuyForeignCcyTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("20");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSSpotForeignExchangeReceivableRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoBuyForeignCcy().getOBSSpotForeignExchangeReceivableOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSSpotForeignExchangeReceivableTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("21");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSForwardForeignExchangeReceivableRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoBuyForeignCcy().getOBSForwardForeignExchangeReceivableOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoBuyForeignCcy().getOBSForwardForeignExchangeReceivableTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("22");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSCommitmentstoSellForeignCcyRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoSellForeignCcy().getOBSCommitmentstoSellForeignCcyOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSCommitmentstoSellForeignCcyTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("23");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSSpotForeignExchangeDeliverRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoSellForeignCcy().getOBSSpotForeignExchangeDeliverOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSSpotForeignExchangeDeliverTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("24");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSForwardForeignExchangeDeliverRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(fec.getOBSCommitmentstoSellForeignCcy().getOBSForwardForeignExchangeDeliverOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(fec.getOBSCommitmentstoSellForeignCcy().getOBSForwardForeignExchangeDeliverTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            break;
                        case "OBSDerivativeInstrumentsPositions":
                            OBSDerivativeInstrumentsPositions dip = (OBSDerivativeInstrumentsPositions) elmOffBa.getValue();
                            dOffBa.setOBSH_TEMID("25");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(dip.getOBSDerivativeInstrumentsPositionsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(dip.getOBSDerivativeInstrumentsPositionsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(dip.getOBSDerivativeInstrumentsPositionsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("26");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(dip.getOBSPlsAttachADetailedAnnexRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(dip.getOBSPlsAttachADetailedAnnexOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(dip.getOBSPlsAttachADetailedAnnexTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("27");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(dip.getOBSInterestRateContractsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(dip.getOBSInterestRateContractsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(dip.getOBSInterestRateContractsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("28");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(dip.getOBSInterestRateSwapsandForwardRateAgreementRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(dip.getOBSInterestRateSwapsandForwardRateAgreementOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(dip.getOBSInterestRateSwapsandForwardRateAgreementTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("29");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(dip.getOBSForeignExchangeFuturesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(dip.getOBSForeignExchangeFuturesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(dip.getOBSForeignExchangeFuturesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            break;
                        case "OBSOtherCommitments":
                            OBSOtherCommitments oc = (OBSOtherCommitments) elmOffBa.getValue();
                            dOffBa.setOBSH_TEMID("30");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSOtherCommitmentsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSOtherCommitmentsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSOtherCommitmentsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("31");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSCommitmentstoSellPreciousMetalsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSCommitmentstoSellPreciousMetalsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSCommitmentstoSellPreciousMetalsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("32");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSCommitmentstoPurchasePreciousmetalsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSCommitmentstoPurchasePreciousmetalsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSCommitmentstoPurchasePreciousmetalsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("33");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSNonperformingCommitmentsRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSNonperformingCommitmentsOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSNonperformingCommitmentsTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("34");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSAssetsHeldasCustodian().getOBSAssetsHeldasCustodianRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSAssetsHeldasCustodian().getOBSAssetsHeldasCustodianOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSAssetsHeldasCustodian().getOBSAssetsHeldasCustodianTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("35");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSAssetsHeldasCustodian().getOBSBillsofExchangeRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSAssetsHeldasCustodian().getOBSBillsofExchangeOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSAssetsHeldasCustodian().getOBSBillsofExchangeTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("36");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSAssetsHeldasCustodian().getOBSSecuritiesRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSAssetsHeldasCustodian().getOBSSecuritiesOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSAssetsHeldasCustodian().getOBSSecuritiesTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());
                            dOffBa.setOBSH_TEMID("37");
                            dOffBa.setOBSH_REPORTID(reportID);
                            dOffBa.setOBSH_RIELS(oc.getOBSAssetsHeldasCustodian().getOBSOthersRiel().toString());
                            dOffBa.setOBSH_OTHERCURR(oc.getOBSAssetsHeldasCustodian().getOBSOthersOtherToRiel().toString());
                            dOffBa.setOBSH_TOTALRIELS(oc.getOBSAssetsHeldasCustodian().getOBSOthersTotalRiel().toString());
                            off.add(dOffBa.getInsertScript());

                            break;
                    }
                }
            } else {

                if (mLR.getOFFBALANCESHEET() == null) {
                    //  setErrorMessage("No value insert to DDP , DO and Liquidity Ratio");
                }
            }
            /*
             * ***********************
             */
            /*
             * *************Networth**********
             */
            model_RPT7_TBT_NETWORTH net = new model_RPT7_TBT_NETWORTH();
            if (mLR.getNETWORTH() != null) {
                List netWorth = mLR.getNETWORTH().getNWReportingDateAndNWBankNameAndNWExchangeRate();
                ListIterator iteratorNetWorth = netWorth.listIterator();
                while (iteratorNetWorth.hasNext()) {
                    JAXBElement nW = (JAXBElement) iteratorNetWorth.next();
                    switch (nW.getName().toString()) {
                        case "NWSubTotalA":
                            NWSubTotalA tA = (NWSubTotalA) nW.getValue();
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("1");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("2");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("3");
                            net.setNETW_AMOUNTINUSD(tA.getNWPaidInCapitalInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWPaidInCapitalInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("4");
                            net.setNETW_AMOUNTINUSD(tA.getNWReservesInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWReservesInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("5");
                            net.setNETW_AMOUNTINUSD(tA.getNWAuditedNetProfitInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWAuditedNetProfitInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("6");
                            net.setNETW_AMOUNTINUSD(tA.getNWRetainedEarningsInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWRetainedEarningsInKHR().toString());
                            off.add(net.getInsertScript());

                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("7");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());

                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("8");
                            net.setNETW_AMOUNTINUSD(tA.getOtherItemsA().getNWProvideReferenceOfNBCAuthority1InUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getOtherItemsA().getNWProvideReferenceOfNBCAuthority1InKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("9");
                            net.setNETW_AMOUNTINUSD(tA.getOtherItemsA().getNWProvideReferenceOfNBCAuthority2InUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getOtherItemsA().getNWProvideReferenceOfNBCAuthority2InKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("10");
                            net.setNETW_AMOUNTINUSD(tA.getNWSubTotalAInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWSubTotalAInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("11");
                            net.setNETW_AMOUNTINUSD(tA.getNWLimitedCheckOnRetainedEarningsInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tA.getNWLimitedCheckOnRetainedEarningsInKHR().toString());
                            off.add(net.getInsertScript());
                            break;
                        case "NWSubTotalB":
                            NWSubTotalB tB = (NWSubTotalB) nW.getValue();
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("12");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);

                            net.setNETW_NETWORTHITEMID("13");
                            net.setNETW_AMOUNTINUSD(tB.getNWOwnSharesHeldInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWOwnSharesHeldInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("14");
                            net.setNETW_AMOUNTINUSD(tB.getNWAccumulatedLossesInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWAccumulatedLossesInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("15");
                            net.setNETW_AMOUNTINUSD(tB.getNWIntangibleAssetsToBeDeductedInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWIntangibleAssetsToBeDeductedInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("16");
                            net.setNETW_AMOUNTINUSD(tB.getNWShareholdersDirectorsRelatedParties().getNWShareholdersDirectorsRelatedPartiesInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWShareholdersDirectorsRelatedParties().getNWShareholdersDirectorsRelatedPartiesInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("17");
                            net.setNETW_AMOUNTINUSD(tB.getNWShareholdersDirectorsRelatedParties().getNWUnpaidPortionOfCapitalInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWShareholdersDirectorsRelatedParties().getNWUnpaidPortionOfCapitalInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("18");
                            net.setNETW_AMOUNTINUSD(tB.getNWShareholdersDirectorsRelatedParties().getNWLoansOverdraftsAndOtherAdvancesInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWShareholdersDirectorsRelatedParties().getNWLoansOverdraftsAndOtherAdvancesInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("19");
                            net.setNETW_AMOUNTINUSD(tB.getNWShareholdersDirectorsRelatedParties().getNWDebtInstrumentsInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWShareholdersDirectorsRelatedParties().getNWDebtInstrumentsInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("20");
                            net.setNETW_AMOUNTINUSD(tB.getNWOtherLossInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWOtherLossInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("21");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());

                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("22");
                            net.setNETW_AMOUNTINUSD(tB.getNWSubTotalBInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWSubTotalBInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("23");
                            net.setNETW_AMOUNTINUSD(tB.getNWTotalAAndBInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tB.getNWTotalAAndBInKHR().toString());
                            off.add(net.getInsertScript());
                            break;
                        case "NWSubTotalC":
                            NWSubTotalC tC = (NWSubTotalC) nW.getValue();
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("24");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("25");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("26");
                            net.setNETW_AMOUNTINUSD(tC.getNWReevaluationReservesInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWReevaluationReservesInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("27");
                            net.setNETW_AMOUNTINUSD(tC.getNWProvisionsForGeneralBankingRisksInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWProvisionsForGeneralBankingRisksInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("28");
                            net.setNETW_AMOUNTINUSD(tC.getNWGeneralProvisionInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWGeneralProvisionInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("29");
                            net.setNETW_AMOUNTINUSD(tC.getNWSubordinatedDebtInstrumentsInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWSubordinatedDebtInstrumentsInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("30");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("31");
                            net.setNETW_AMOUNTINUSD(tC.getOtherItemsC().getNWProvideReferenceOfNBCAuth1InUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getOtherItemsC().getNWProvideReferenceOfNBCAuth1InKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_NETWORTHITEMID("32");
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_AMOUNTINUSD(tC.getOtherItemsC().getNWProvideReferenceOfNBCAuth2InUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getOtherItemsC().getNWProvideReferenceOfNBCAuth2InKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("33");
                            net.setNETW_AMOUNTINUSD(tC.getNWSubTotalCInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWSubTotalCInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("34");
                            net.setNETW_AMOUNTINUSD(tC.getNWLimitCheckOnSubordinatedDebtInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tC.getNWLimitCheckOnSubordinatedDebtInKHR().toString());
                            off.add(net.getInsertScript());
                            break;
                        case "NWSubTotalD":
                            NWSubTotalD tD = (NWSubTotalD) nW.getValue();
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("35");
                            net.setNETW_AMOUNTINUSD("");
                            net.setNETW_AMOUNTINKHR("");
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("36");
                            net.setNETW_AMOUNTINUSD(tD.getNWEquityPartiBankAndFIInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWEquityPartiBankAndFIInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("37");
                            net.setNETW_AMOUNTINUSD(tD.getNWSROtherItemsDeductedInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWSROtherItemsDeductedInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_REPORTID(reportID);
                            net.setNETW_NETWORTHITEMID("38");
                            net.setNETW_AMOUNTINUSD(tD.getNWSubTotalDInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWSubTotalDInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_NETWORTHITEMID("39");
                            net.setNETW_AMOUNTINUSD(tD.getNWTotalCAndDInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWTotalCAndDInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_NETWORTHITEMID("40");
                            net.setNETW_AMOUNTINUSD(tD.getNWLimitCheckOnTier2CapitalInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWLimitCheckOnTier2CapitalInKHR().toString());
                            off.add(net.getInsertScript());
                            net.setNETW_NETWORTHITEMID("41");
                            net.setNETW_AMOUNTINUSD(tD.getNWRegulationNetworthInUSD().toString());
                            net.setNETW_AMOUNTINKHR(tD.getNWRegulationNetworthInKHR().toString());
                            off.add(net.getInsertScript());
                            break;
                    }
                }
            } else {
            }

            /*
             * ***********************
             */
            model_RPT6_TBT_SOLENCYRATIO sol = new model_RPT6_TBT_SOLENCYRATIO();
            if (mLR.getSOLVENCYRATIO() != null) {
                List ListsR = mLR.getSOLVENCYRATIO().getSRReportingDateAndSRBankNameAndSRNetWorth();
                ListIterator iteratorSR = ListsR.listIterator();
                while (iteratorSR.hasNext()) {
                    JAXBElement sR = (JAXBElement) iteratorSR.next();
                    switch (sR.getName().toString()) {
                        case "SRBalanceSheetItems":
                            SRBalanceSheetItems bSheet = (SRBalanceSheetItems) sR.getValue();
                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("1");
                            sol.setSOLV_AMOUNTINUSD("");
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD("");
                            sol.setSOLV_TOTALAMT_MILLIONRIEL("");
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("2");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRAssetsWithLowRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRAssetsWithLowRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRAssetsWithLowRiskInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("3");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRCashBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithLowRisk().getSRCashWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRCashInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRCashInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("4");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRGoldBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithLowRisk().getSRGoldWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRGoldInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRGoldInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("5");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRClaimOnNBCBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithLowRisk().getSRClaimOnNBCWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRClaimOnNBCInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRClaimOnNBCInKHR().toString());
                            off.add(sol.getInsertScript());



                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("6");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRAssetsCollaterallizedByDepBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithLowRisk().getSRAssetsCollaterallizedByDepWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRAssetsCollaterallizedByDepInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRAssetsCollaterallizedByDepInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("7");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithLowRisk().getSRClaimsonorGuaranteedbySovereigns1BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithLowRisk().getSRClaimsonorGuaranteedbySovereigns1Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithLowRisk().getSRClaimsonorGuaranteedbySovereigns1InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithLowRisk().getSRClaimsonorGuaranteedbySovereigns1InKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("8");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithModerateRisk().getSRAssetsWithModerateRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithModerateRisk().getSRAssetsWithModerateRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithModerateRisk().getSRAssetsWithModerateRiskInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("9");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbySovereigns2BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbySovereigns2Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbySovereigns2InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbySovereigns2InKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("10");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbybanks2BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbybanks2Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbybanks2InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithModerateRisk().getSRClaimsonorGuaranteedbybanks2InKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("11");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithMediumRisk().getSRAssetsWithMediumRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithMediumRisk().getSRAssetsWithMediumRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithMediumRisk().getSRAssetsWithMediumRiskInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("12");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbySovereigns3BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbySovereigns3Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbySovereigns3InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbySovereigns3InKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("13");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbyBanks3BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbyBanks3Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbyBanks3InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithMediumRisk().getSRClaimsonorGuaranteedbyBanks3InKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("14");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithFullRisk().getSRAssetsWithFullRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithFullRisk().getSRAssetsWithFullRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithFullRisk().getSRAssetsWithFullRiskInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("15");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithFullRisk().getSROtherAssetsBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(bSheet.getSRAssetsWithFullRisk().getSROtherAssetsWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithFullRisk().getSROtherAssetsInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithFullRisk().getSROtherAssetsInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("16");
                            sol.setSOLV_AMOUNTINUSD(bSheet.getSRAssetsWithFullRisk().getSRTotalIBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(bSheet.getSRAssetsWithFullRisk().getSRTotalIInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(bSheet.getSRAssetsWithFullRisk().getSRTotalIInKHR().toString());
                            off.add(sol.getInsertScript());

                            break;

                        case "SROffBalanceSheetItems":
                            SROffBalanceSheetItems offBa = (SROffBalanceSheetItems) sR.getValue();
                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("17");
                            sol.setSOLV_AMOUNTINUSD("");
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD("");
                            sol.setSOLV_TOTALAMT_MILLIONRIEL("");
                            off.add(sol.getInsertScript());
                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("18");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithlowRisk().getSROperationswithlowRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithlowRisk().getSROperationswithlowRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithlowRisk().getSROperationswithlowRiskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("19");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithlowRisk().getSRUndrawnFacilities1BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithlowRisk().getSRUndrawnFacilities1Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithlowRisk().getSRUndrawnFacilities1InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithlowRisk().getSRUndrawnFacilities1InKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_REPORTID(reportID);
                            sol.setSOLV_RATIOITEMID("20");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithlowRisk().getSROtherItemscarryingalowriskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithlowRisk().getSROtherItemscarryingalowriskWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithlowRisk().getSROtherItemscarryingalowriskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithlowRisk().getSROtherItemscarryingalowriskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("21");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithModerateRisk().getSROperationswithModerateRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithModerateRisk().getSROperationswithModerateRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithModerateRisk().getSROperationswithModerateRiskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("22");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithModerateRisk().getSRDocumentaryCreditsBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithModerateRisk().getSRDocumentaryCreditsWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithModerateRisk().getSRDocumentaryCreditsInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithModerateRisk().getSRDocumentaryCreditsInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("23");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithModerateRisk().getSROtherItemscarryingmoderateriskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithModerateRisk().getSROtherItemscarryingmoderateriskWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithModerateRisk().getSROtherItemscarryingmoderateriskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithModerateRisk().getSROtherItemscarryingmoderateriskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("24");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithMediumRisk().getSROperationswithMediumRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithMediumRisk().getSROperationswithMediumRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithMediumRisk().getSROperationswithMediumRiskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("25");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithMediumRisk().getSRCommitmentstopayBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithMediumRisk().getSRCommitmentstopayWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithMediumRisk().getSRCommitmentstopayInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithMediumRisk().getSRCommitmentstopayInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("26");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithMediumRisk().getSRWarrantiesandguaranteesBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithMediumRisk().getSRWarrantiesandguaranteesWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithMediumRisk().getSRWarrantiesandguaranteesInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithMediumRisk().getSRWarrantiesandguaranteesInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("27");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithMediumRisk().getSRUndrawnFacilities3BalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithMediumRisk().getSRUndrawnFacilities3Weighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithMediumRisk().getSRUndrawnFacilities3InUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithMediumRisk().getSRUndrawnFacilities3InKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("28");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithMediumRisk().getSROtheritemscarryingmediumriskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithMediumRisk().getSROtheritemscarryingmediumriskWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithMediumRisk().getSROtheritemscarryingmediumriskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithMediumRisk().getSROtheritemscarryingmediumriskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("29");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSROperationswithFullRiskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSROperationswithFullRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSROperationswithFullRiskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("30");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSRLoanguaranteesBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSRLoanguaranteesWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRLoanguaranteesInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRLoanguaranteesInKHR().toString());
                            off.add(sol.getInsertScript());

                            sol.setSOLV_RATIOITEMID("31");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSRAcceptancesBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSRAcceptancesWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRAcceptancesInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRAcceptancesInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("32");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSREndorsementonbillsBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSREndorsementonbillsWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSREndorsementonbillsInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSREndorsementonbillsInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("33");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSRTransactionswithrecourseBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSRTransactionswithrecourseWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRTransactionswithrecourseInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRTransactionswithrecourseInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("34");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSRIrrevocablecreditlinesBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSRIrrevocablecreditlinesWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRIrrevocablecreditlinesInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRIrrevocablecreditlinesInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("35");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSROtheritemscarryingahighriskBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING(offBa.getSROperationswithFullRisk().getSROtheritemscarryingahighriskWeighting().toString());
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSROtheritemscarryingahighriskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSROtheritemscarryingahighriskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("36");
                            sol.setSOLV_AMOUNTINUSD(offBa.getSROperationswithFullRisk().getSRTotalIIBalanceInUSD().toString());
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRTotalIIInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRTotalIIInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("37");
                            sol.setSOLV_AMOUNTINUSD("");
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRTotalAssetsandOperationsafterweightingRiskInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRTotalAssetsandOperationsafterweightingRiskInKHR().toString());
                            off.add(sol.getInsertScript());
                            sol.setSOLV_RATIOITEMID("38");
                            sol.setSOLV_AMOUNTINUSD("");
                            sol.setSOLV_WEIGHTING("");
                            sol.setSOLV_TOTALAMT_USD(offBa.getSROperationswithFullRisk().getSRSolvencyRatioInUSD().toString());
                            sol.setSOLV_TOTALAMT_MILLIONRIEL(offBa.getSROperationswithFullRisk().getSRSolvencyRatioInKHR().toString());
                            off.add(sol.getInsertScript());
                            break;
                    }
                }
            }
            /*
             * Net position
             */
            ArrayList netOp = new ArrayList();
            model_RPT8_TBT_NETPOSITION netP = new model_RPT8_TBT_NETPOSITION();
            if (mLR.getNETOPENPOSITION() != null) {
                List ListNet = mLR.getNETOPENPOSITION().getNOPReportingDateAndNOPBankNameAndNOPNetWorth();
                ListIterator iteratorNet = ListNet.listIterator();
                while (iteratorNet.hasNext()) {
                    JAXBElement Ne = (JAXBElement) iteratorNet.next();

                    //System.out.println(Ne.getName());
                    switch (Ne.getName().toString()) {
                        case "NOPCurrency":
                            NOPCurrency cur = (NOPCurrency) Ne.getValue();
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("1");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPUSD().getNOPUSDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPUSD().getNOPUSDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPUSD().getNOPUSDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPUSD().getNOPUSDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPUSD().getNOPUSDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPUSD().getNOPUSDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPUSD().getNOPUSDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPUSD().getNOPUSDExcess().toString());
                            off.add(netP.getInsertScript());

                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("2");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPKHR().getNOPKHRAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPKHR().getNOPKHRLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPKHR().getNOPKHRCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPKHR().getNOPKHRCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPKHR().getNOPKHRNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPKHR().getNOPKHRNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPKHR().getNOPKHRLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPKHR().getNOPKHRExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("3");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPEUR().getNOPEURAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPEUR().getNOPEURLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPEUR().getNOPEURCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPEUR().getNOPEURCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPEUR().getNOPEURNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPEUR().getNOPEURNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPEUR().getNOPEURLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPEUR().getNOPEURExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("4");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPSGD().getNOPSGDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPSGD().getNOPSGDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPSGD().getNOPSGDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPSGD().getNOPSGDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPSGD().getNOPSGDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPSGD().getNOPSGDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPSGD().getNOPSGDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPSGD().getNOPSGDExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("5");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPHKD().getNOPHKDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPHKD().getNOPHKDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPHKD().getNOPHKDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPHKD().getNOPHKDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPHKD().getNOPHKDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPHKD().getNOPHKDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPHKD().getNOPHKDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPHKD().getNOPHKDExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("6");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPTHB().getNOPTHBAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPTHB().getNOPTHBLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPTHB().getNOPTHBCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPTHB().getNOPTHBCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPTHB().getNOPTHBNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPTHB().getNOPTHBNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPTHB().getNOPTHBLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPTHB().getNOPTHBExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("7");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPJPY().getNOPJPYAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPJPY().getNOPJPYLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPJPY().getNOPJPYCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPJPY().getNOPJPYCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPJPY().getNOPJPYNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPJPY().getNOPJPYNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPJPY().getNOPJPYLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPJPY().getNOPJPYExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("8");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPPHP().getNOPPHPAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPPHP().getNOPPHPLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPPHP().getNOPPHPCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPPHP().getNOPPHPCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPPHP().getNOPPHPNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPPHP().getNOPPHPNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPPHP().getNOPPHPLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPPHP().getNOPPHPExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("9");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPAUD().getNOPAUDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPAUD().getNOPAUDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPAUD().getNOPAUDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPAUD().getNOPAUDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPAUD().getNOPAUDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPAUD().getNOPAUDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPAUD().getNOPAUDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPAUD().getNOPAUDExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("10");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPCAD().getNOPCADAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPCAD().getNOPCADLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPCAD().getNOPCADCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPCAD().getNOPCADCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPCAD().getNOPCADNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPCAD().getNOPCADNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPCAD().getNOPCADLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPCAD().getNOPCADExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("11");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPGBP().getNOPGBPAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPGBP().getNOPGBPLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPGBP().getNOPGBPCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPGBP().getNOPGBPCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPGBP().getNOPGBPNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPGBP().getNOPGBPNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPGBP().getNOPGBPLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPGBP().getNOPGBPExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("12");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPCNY().getNOPCNYAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPCNY().getNOPCNYLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPCNY().getNOPCNYCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPCNY().getNOPCNYCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPCNY().getNOPCNYNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPCNY().getNOPCNYNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPCNY().getNOPCNYLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPCNY().getNOPCNYExcess().toString());
                            off.add(netP.getInsertScript());

                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("13");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPVND().getNOPVNDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPVND().getNOPVNDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPVND().getNOPVNDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPVND().getNOPVNDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPVND().getNOPVNDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPVND().getNOPVNDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPVND().getNOPVNDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPVND().getNOPVNDExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("14");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPVND().getNOPVNDAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPVND().getNOPVNDLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPVND().getNOPVNDCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPVND().getNOPVNDCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPVND().getNOPVNDNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPVND().getNOPVNDNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPVND().getNOPVNDLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPVND().getNOPVNDExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("15");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPOTHERS().getNOPOTHERSAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPOTHERS().getNOPOTHERSLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPOTHERS().getNOPOTHERSCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPOTHERS().getNOPOTHERSCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION(cur.getNOPOTHERS().getNOPOTHERSNopLongShort().toString());
                            netP.setOPEN_NETOPENPOS_NWT(cur.getNOPOTHERS().getNOPOTHERSNopNetWorth().toString());
                            netP.setOPEN_LIMIT(cur.getNOPOTHERS().getNOPOTHERSLimit().toString());
                            netP.setOPEN_EXCESS(cur.getNOPOTHERS().getNOPOTHERSExcess().toString());
                            off.add(netP.getInsertScript());
                            netP.setOPEN_REPORTID(reportID);
                            netP.setOPEN_CURRENCYID("16");
                            netP.setOPEN_AFFECTPROASSESTS(cur.getNOPTOTAL().getNOPTOTALAssets().toString());
                            netP.setOPEN_AFFECTPROLIA_CAPI(cur.getNOPTOTAL().getNOPTOTALLiabilitiesCapital().toString());
                            netP.setOPEN_PROCUR_OFF_BLS(cur.getNOPTOTAL().getNOPTOTALCcyReceivables().toString());
                            netP.setOPEN_PROVICU_PAY_BLS(cur.getNOPTOTAL().getNOPTOTALCcyPayables().toString());
                            netP.setOPEN_NETOPENPOSITION("");
                            netP.setOPEN_NETOPENPOS_NWT("");
                            netP.setOPEN_LIMIT("");
                            netP.setOPEN_EXCESS("");
                            off.add(netP.getInsertScript());
                            // System.out.println(netOp.get(0));
                            data.ExecuteNonQuery(off);

                            if (data.getERROR_Message().equals("")) {

                                this.setErrorMessage("You have completed Uploading Monthly Report");
                            } else {
                                this.setErrorMessage("You have not completed Uploading Monthly Report, please recheck again");
                            }

                            break;

                    }
                }
            }
            model_RPT34_TBT_NETWORKINFO nw = new model_RPT34_TBT_NETWORKINFO();
            if (mLR.getNETWORKINFO() != null) {
                List network = mLR.getNETWORKINFO().getData().getRecord();
                ListIterator iteratorNetwork = network.listIterator();
                while (iteratorNetwork.hasNext()) {
                    Record netw = (Record) iteratorNetwork.next();
                    nw.setNETW_CITIESBRANCHES(netw.getProvincialCitiesBranches());
                    nw.setNETW_NUMBRANCHESDISTRICT(netw.getNumberOfDistrict().toString());
                    nw.setNETW_NUMBRANCHESCOMMUNE(netw.getNumberOfCommune().toString());
                    nw.setNETW_LOANOUTSTANDAMT(netw.getLoanAmount().toString());
                    nw.setNETW_OUTSTANDBORROWER(netw.getLoanLegalEntities().toString());
                    nw.setNETW_OUTSTANDBORROWMALE(netw.getLoanNMale().toString());
                    nw.setNETW_OUTSTANDBORROWFEMALE(netw.getLoanNFemale().toString());
                    nw.setNETW_DBAMT(netw.getLoanTotal().toString());
                    nw.setNETW_DBDEPOSITORTOTAL(netw.getDepositTotal().toString());
                    nw.setNETW_DBDEPOSITORMALE(netw.getDepositNMale().toString());
                    nw.setNETW_DBDEPOSITORFEMALE(netw.getDepositNFemale().toString());
                    nw.setNETW_NUMBEROFSTAFFTOTAL(netw.getNumberOfSTotal().toString());
                    nw.setNETW_NUMBEROFSTAFFMALE(netw.getNumberOfSMale().toString());
                    nw.setNETW_NUMBEROFSTAFFFEMALE(netw.getNumberOfSFemale().toString());
                    nw.setNETW_NUMBEROFATM(netw.getNumberOfATM().toString());
                    nw.setNETW_NUMBEROFPOS(netw.getNumberOfPOS().toString());
                    nw.setNETW_DEBITCARDAMOUNT(netw.getDebitAmount().toString());
                    nw.setNETW_DEBITCARDNUMOFCARD(netw.getDebitCard().toString());
                    nw.setNETW_CREDITCARDAMOUNT(netw.getCreditAmount().toString());
                    nw.setNETW_CREDITCARDNUMOFCARD(netw.getCebitCard().toString());
                    off.add(nw.getInsertScript());

                }
            }
            model_RPT33_MONEYINTERBANKTRAN inter = new model_RPT33_MONEYINTERBANKTRAN();
            if (mLR.getINTERBANK()!= null) {
                List interBank = mLR.getINTERBANK().getBankResident().getRecordBankResidentAndRecordBankNonResident();
                ListIterator iteratorBank = interBank.listIterator();
                while (iteratorBank.hasNext()) {
                    JAXBElement bank = (JAXBElement) iteratorBank.next();
                    switch (bank.getName().toString()) {
                        case "RecordBankResident":
                            RecordBankResident bR = (RecordBankResident) bank.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(bR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(bR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(bR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(bR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(bR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(bR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(bR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(bR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(bR.getLibType().toString());
                            inter.setINBK_RESIDENT("1");
                            off.add(inter.getInsertScript());
                            break;

                        case "RecordBankNonResident":
                            RecordBankNonResident bNR = (RecordBankNonResident) bank.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(bNR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(bNR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(bNR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(bNR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(bNR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(bNR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(bNR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(bNR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(bNR.getLibType().toString());
                            inter.setINBK_RESIDENT("2");
                            off.add(inter.getInsertScript());
                            break;
                    }
                }
                // SECURITY FIRMS
                List interSercurity = mLR.getINTERBANK().getSecurityFirm().getRecordSecurityResidentAndRecordSecurityNonResident();
                ListIterator iteratorSecurity = interSercurity.listIterator();
                while (iteratorSecurity.hasNext()) {
                    JAXBElement Security = (JAXBElement) iteratorSecurity.next();
                    switch (Security.getName().toString()) {
                        case "RecordSecurityResident":
                            RecordSecurityResident sR = (RecordSecurityResident) Security.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(sR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(sR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(sR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(sR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(sR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(sR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(sR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(sR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(sR.getLibType().toString());
                            inter.setINBK_RESIDENT("3");
                            off.add(inter.getInsertScript());
                            break;

                        case "RecordSecurityNonResident":
                            RecordSecurityNonResident sNR = (RecordSecurityNonResident) Security.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(sNR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(sNR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(sNR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(sNR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(sNR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(sNR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(sNR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(sNR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(sNR.getLibType().toString());
                            inter.setINBK_RESIDENT("4");
                            off.add(inter.getInsertScript());
                            break;
                    }
                }
                // Insurrance
                List interInsurrance = mLR.getINTERBANK().getInsurrance().getRecordInsurranceResidentAndRecordInsurranceNonResident();
                ListIterator iteratorInsurrance = interInsurrance.listIterator();
                while (iteratorInsurrance.hasNext()) {
                    JAXBElement Insurrance = (JAXBElement) iteratorInsurrance.next();
                    switch (Insurrance.getName().toString()) {
                        case "RecordInsurranceResident":
                            RecordInsurranceResident rR = (RecordInsurranceResident) Insurrance.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(rR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(rR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(rR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(rR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(rR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(rR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(rR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(rR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(rR.getLibType().toString());
                            inter.setINBK_RESIDENT("5");
                            off.add(inter.getInsertScript());
                            break;

                        case "RecordInsurranceNonResident":
                            RecordInsurranceNonResident rNR = (RecordInsurranceNonResident) Insurrance.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(rNR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(rNR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(rNR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(rNR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(rNR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(rNR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(rNR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(rNR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(rNR.getLibType().toString());
                            inter.setINBK_RESIDENT("6");
                            off.add(inter.getInsertScript());
                            break;
                    }
                }
                //Other fis
                List interOtherfis = mLR.getINTERBANK().getOtherFis().getRecordOtherfisResidentAndRecordOtherfisNonResident();
                ListIterator iteratorOtherfis = interOtherfis.listIterator();
                while (iteratorOtherfis.hasNext()) {
                    JAXBElement Otherfis = (JAXBElement) iteratorOtherfis.next();
                    switch (Otherfis.getName().toString()) {
                        case "RecordOtherfisResident":
                            RecordOtherfisResident oR = (RecordOtherfisResident) Otherfis.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(oR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(oR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(oR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(oR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(oR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(oR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(oR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(oR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(oR.getLibType().toString());
                            inter.setINBK_RESIDENT("7");
                            off.add(inter.getInsertScript());
                            break;

                        case "RecordOtherfisNonResident":
                            RecordOtherfisNonResident oNR = (RecordOtherfisNonResident) Otherfis.getValue();
                            inter.setINBK_REPORTID(reportID);
                            inter.setINBK_FINANCIALINNAME(oNR.getResidentName());
                            inter.setINBK_ASSESTOUTSTANDING(oNR.getAssOutstanding().toString());
                            inter.setINBK_ASSETINTERESTRATE(oNR.getAssInterestRate().toString());
                            inter.setINBK_ASSETMATURITY(oNR.getAssMaturity().toString());
                            inter.setINBK_ASSETTYPEID(oNR.getAssType().toString());
                            inter.setINBK_LIOUTSTANDING(oNR.getLibOutstanding().toString());
                            inter.setINBK_LIASSETINTERESTRATE(oNR.getLibInterestRate().toString());
                            inter.setINBK_LIASSETMATURITY(oNR.getLibMaturity().toString());
                            inter.setINBK_LIASSETTYPEID(oNR.getLibType().toString());
                            inter.setINBK_RESIDENT("8");
                            off.add(inter.getInsertScript());
                            break;
                    }
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(MONTHLY.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void main(String args[]) throws SAXException, SQLException, ClassNotFoundException {
//        monthlyReport m = new monthlyReport("C:\\Users\\moliyuth\\Desktop\\Form1.xml");
//        m.readxml("134467");
//
//    }
}
