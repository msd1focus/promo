package app.fpp.bean;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.model.am.ConfirmationAMImpl;
import app.fpp.model.am.common.BudgetSettingAM;
import app.fpp.model.views.confirmation.ProposalReadyConfirmAdendumViewImpl;
import app.fpp.model.views.confirmation.ProposalUpdateConfirmAdendumViewImpl;
import app.fpp.model.views.confirmation.ProposalUpdatePrCreatedViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierAreaViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierHoViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsQpModifierTempViewImpl;
import app.fpp.model.views.confirmation.modifier.HeaderIdSeqViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsApprovalPathViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsCheckDcvPrClosedViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsPoRequisitionTempViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsViewNoPrClosedViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsViewNoPrViewImpl;
import app.fpp.model.views.confirmation.targetpr.FcsViewNoPrViewRowImpl;
import app.fpp.model.views.confirmation.targetpr.ItemBjpFlagViewImpl;
import app.fpp.model.views.masterdata.RunNumConfViewImpl;
import app.fpp.model.views.masterdata.ebs.CompanyOUViewImpl;
import app.fpp.model.views.masterdata.ebs.FcsViewGudangInventoryViewImpl;
import app.fpp.model.views.masterdata.ebs.KodePostingBJPViewImpl;
import app.fpp.model.views.masterdata.ebs.KodePostingViewImpl;

import java.math.BigDecimal;

import java.sql.CallableStatement;

import java.sql.SQLException;

import java.sql.Types;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class ConfirmationPromoBean {

    private Row runNumConf;
    private RichTable tblPropConfirmation;
    private RichTable tblSrcPropConfirm;
    private RichTable tblSrcPropAdendum;
    private String ebsOuIdFDI = "FDI OU";
    private String ebsOuIdFDN = "FDN OU";
    private UIXSwitcher switcherCustPost;
    private RichTable tblBudgetCustomer;
    private RichTable tblBudgetPosting;
    private String userCreatorHo = "HO";
    private String userCreatorArea = "AREA";
    private RichInputText itBudgetPostPercent;
    private RichInputText itBudgetCustPercent;
    private Integer isBJPTrue = 1;
    private String discTypePotongan = "POTONGAN";
    private String discTypeBiaya = "BIAYA";
    private String discTypePromoBarang = "PROMOBARANG";
    private RichInputText itBudgetOnTop;
    private RichInputText itBudgetMF;
    private RichInputText amount;
    private RichCommandButton btnHitungPercent;
    private RichPopup potmessage;
    private RichOutputText otpesan;
    private RichInputText itBudgetPostAmount;
    private RichPopup popupDetailProd;
    private static String offInvoice = "OFFINVOICE";
    private Number zeroNum = new Number(0);
    private String msgValKodePostOnTop1st = "Kode Posting On Top";
    private String msgValKodePostOnTop2nd = ", Kode Posting On Top";
    private String msgValKodePostMf1st = "Kode Posting MF";
    private String msgValKodePostMf2nd = ", Kode Posting MF";
    private String msgValItemExp1st = "Item Expense";
    private String msgValItemExp2nd = ", Item Expense";
    private RichTable tblListProduct;
    private String prStatusCancelled = "CANCELLED";
    private RichInputListOfValues itLovbudgetPost;


    public ConfirmationPromoBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public static java.util.Date convertOracleDateToJavaUtilDate(oracle.jbo.domain.Date oracleDate) {
        if (oracleDate == null)
            return null;

        java.sql.Date javaSqlDate = oracleDate.dateValue();
        long javaMilliSeconds = javaSqlDate.getTime();
        return new java.util.Date(javaMilliSeconds);
    }

    public void confirmationPopupDialogListener(DialogEvent dialogEvent) {

        BindingContainer bindings = getBindings();

        // Get user initial
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userInitial = userData.getUserInitial();
        String userName = userData.getUserNameLogin();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            ConfirmationAMImpl confirmationAM =
                (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

            Number runNumber;
            DCIteratorBinding dciter =
                ADFUtils.findIterator("ProposalReadyConfirmAdendumView1Iterator");
            DBSequence propId =
                (DBSequence)dciter.getCurrentRow().getAttribute("ProposalId");

            // Get Date on MM and yy Format
            java.util.Date date =
                new java.util.Date(System.currentTimeMillis());
            DateFormat dfMM = new SimpleDateFormat("MM");
            String currMon = dfMM.format(date);
            DateFormat dfYY = new SimpleDateFormat("yy");
            String currYear = dfYY.format(date);

            Number numMonth = new Number(Integer.parseInt(currMon));
            Number numYear = new Number(Integer.parseInt(currYear));

            // Get Running Number
            RunNumConfViewImpl runNumVo = confirmationAM.getRunNumConfView1();
            runNumVo.setNamedWhereClauseParam("userName", userName);
            runNumVo.setNamedWhereClauseParam("runYear", numYear);
            runNumVo.setNamedWhereClauseParam("runMonth", numMonth);
            runNumVo.executeQuery();

            if (runNumVo.getAllRowsInRange().length > 0) {
                runNumConf = runNumVo.first();
                runNumber = (Number)runNumConf.getAttribute("Value");
                runNumConf.setAttribute("Value", runNumber.add(1));
            } else {
                Row newRow = runNumVo.createRow();
                newRow.setAttribute("RunNumType", "CONF");
                newRow.setAttribute("UserName", userName);
                newRow.setAttribute("RunYear", currYear);
                newRow.setAttribute("RunMonth", currMon);
                newRow.setAttribute("Value", 2);
                runNumVo.insertRow(newRow);
                runNumber = new Number(1);
            }

            // Generate Confirmation Number
            String runNumberFormatted =
                String.format("%04d", runNumber.getBigDecimalValue().intValue());
            String numMonthFormatted =
                String.format("%02d", numMonth.getBigDecimalValue().intValue());
            String numYearFormatted =
                String.format("%02d", numYear.getBigDecimalValue().intValue());

            String confNumber =
                userInitial + numYearFormatted + numMonthFormatted +
                runNumberFormatted;

            ProposalUpdateConfirmAdendumViewImpl voProposal =
                (ProposalUpdateConfirmAdendumViewImpl)confirmationAM.getProposalUpdateConfirmAdendumView1();
            voProposal.setWhereClause("Proposal.PROPOSAL_ID = " +
                                      propId.getValue());
            voProposal.executeQuery();

            if (voProposal.getEstimatedRowCount() > 0) {
                Row propRow = voProposal.first();
                propRow.setAttribute("ConfirmNo", confNumber);
                propRow.setAttribute("ConfirmDate", this.getCurrentSysDate());
            }
            voProposal.closeRowSet();

            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            OperationBinding operationExecutePropConfirm =
                bindings.getOperationBinding("ExecutePropConfirm");
            operationExecutePropConfirm.execute();

            OperationBinding operRefreshProposalList =
                bindings.getOperationBinding("refreshReadyProposal");
            operRefreshProposalList.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropConfirm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropAdendum);
        }
    }

    public oracle.jbo.domain.Date getCurrentSysDate() {
        java.sql.Timestamp datetime =
            new java.sql.Timestamp(System.currentTimeMillis());
        oracle.jbo.domain.Date daTime = new oracle.jbo.domain.Date(datetime);
        return daTime;
    }

    public void setTblPropConfirmation(RichTable tblPropConfirmation) {
        this.tblPropConfirmation = tblPropConfirmation;
    }

    public RichTable getTblPropConfirmation() {
        return tblPropConfirmation;
    }

    public void adendumPopupDialogListener(DialogEvent dialogEvent) {

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter =
            ADFUtils.findIterator("ProposalReadyConfirmAdendumView1Iterator");
        DBSequence propId =
            (DBSequence)dciter.getCurrentRow().getAttribute("ProposalId");

        AttributeBinding confirmNoAttr =
            (AttributeBinding)bindings.getControlBinding("ConfirmNo");
        String confirmNo = (String)confirmNoAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {

            ConfirmationAMImpl confirmationAM =
                (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

            ProposalUpdateConfirmAdendumViewImpl voProposalConfirm =
                (ProposalUpdateConfirmAdendumViewImpl)confirmationAM.getProposalUpdateConfirmAdendumView1();
            voProposalConfirm.setWhereClause("Proposal.CONFIRM_NO = '" +
                                             confirmNo + "'");
            voProposalConfirm.executeQuery();

            Row propConfirmRow = voProposalConfirm.first();
            Number nextAddNumber =
                (Number)propConfirmRow.getAttribute("NextAddendum");
            String addendumNumber = String.valueOf(nextAddNumber);

            //ProposalReadyConfirmAdendumView1Iterator
            ProposalUpdateConfirmAdendumViewImpl voProposal =
                (ProposalUpdateConfirmAdendumViewImpl)confirmationAM.getProposalUpdateConfirmAdendumView1();
            voProposal.setWhereClause("Proposal.PROPOSAL_ID = " +
                                      propId.getValue());
            voProposal.executeQuery();

            if (voProposal.getEstimatedRowCount() > 0) {
                Row propRow = voProposal.first();
                propRow.setAttribute("ConfirmNo", confirmNo);
                propRow.setAttribute("ConfirmDate", this.getCurrentSysDate());
                propRow.setAttribute("AddendumKe", addendumNumber);
            }
            voProposal.closeRowSet();

            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            OperationBinding operationExecutePropConfirm =
                bindings.getOperationBinding("ExecutePropConfirm");
            operationExecutePropConfirm.execute();

            OperationBinding operRefreshProposalList =
                bindings.getOperationBinding("refreshReadyProposal");
            operRefreshProposalList.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropConfirm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropAdendum);
        }
    }

    public void setTblSrcPropConfirm(RichTable tblSrcPropConfirm) {
        this.tblSrcPropConfirm = tblSrcPropConfirm;
    }

    public RichTable getTblSrcPropConfirm() {
        return tblSrcPropConfirm;
    }

    public void setTblSrcPropAdendum(RichTable tblSrcPropAdendum) {
        this.tblSrcPropAdendum = tblSrcPropAdendum;
    }

    public RichTable getTblSrcPropAdendum() {
        return tblSrcPropAdendum;
    }

    public void createPrDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();

        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Format datePrAttr2Fmt = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date prDate =
            convertOracleDateToJavaUtilDate(getCurrentSysDate());
        String datePrAttr2Str =
            datePrAttr2Fmt.format(prDate).toString().toUpperCase();

        ConfirmationAMImpl confirmationAM =
            (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

        // Remove PR failed rows
        clearFailPrCreation(confirmationAM);

        DCIteratorBinding dciter =
            ADFUtils.findIterator("ProposalConfirmationView1Iterator");
        DBSequence propId =
            (DBSequence)dciter.getCurrentRow().getAttribute("ProposalId");

        AttributeBinding confirmNoAttr =
            (AttributeBinding)bindings.getControlBinding("ConfirmNo");
        String confNo = (String)confirmNoAttr.getInputValue();
        AttributeBinding adendumKeAttr =
            (AttributeBinding)bindings.getControlBinding("AddendumKe");
        String addendumKe = (String)adendumKeAttr.getInputValue();
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discType = (String)discTypeAttr.getInputValue();

        AttributeBinding bjpFlag =
            (AttributeBinding)bindings.getControlBinding("BjpFlag");
        Integer isBJP = (Integer)bjpFlag.getInputValue();

        String confirmNo = null;
        if (addendumKe != null) {
            confirmNo = confNo + "-" + addendumKe;
        } else {
            confirmNo = confNo;
        }

        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userName = userData.getFullName();
        String userTitle = userData.getTitle();

        String userFullName = null;
        if (userTitle == null || userTitle.isEmpty() || userTitle == "") {
            userFullName = userName + ",";
        } else {
            userFullName = userName + ", " + userTitle;
        }
        String compId = userData.getCompanyId();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            // Flag run ebs procedure
            String flagRunEbs = "N";
            // Get batch id
            String currBatchId = batchNumber();
            // Validasi kode posting sudah terisi semua
            boolean kodePostingItemExpenseFilled = true;

            HashMap<Integer, String> valKodePostItemExp = new HashMap<Integer, String>();
            
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            int prodLine = 0;
            for (Row promoProdRow : dciterPromoProduk.getAllRowsInRange()) {
                    String itemExpense =
                        (String)promoProdRow.getAttribute("ItemExpense");
                    String kodePostingOnTop =
                        (String)promoProdRow.getAttribute("KodePosting");
                    String kodePostingMf =
                        (String)promoProdRow.getAttribute("KodePostingMf");
                    
                    if (discType.equalsIgnoreCase(discTypePromoBarang)) {                    
                        Number barangBonusOnTop =
                        (Number)promoProdRow.getAttribute("BarangBonusNonYearly");            
                        Number barangBonusMf =
                        (Number)promoProdRow.getAttribute("BarangBonusYearly");
                        
                        String msgValPostItemExpBonus = "";
                        if (kodePostingOnTop == null && barangBonusOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpBonus.trim().length() == 0) {
                                msgValPostItemExpBonus = msgValKodePostOnTop1st;
                            } else {
                                msgValPostItemExpBonus = msgValPostItemExpBonus + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && barangBonusMf.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpBonus.trim().length() == 0) {
                                msgValPostItemExpBonus = msgValKodePostMf1st;
                            } else {
                                msgValPostItemExpBonus = msgValPostItemExpBonus + msgValKodePostMf2nd;
                            }
                        }
                        
                        if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                            if (msgValPostItemExpBonus.trim().length() == 0) {
                                msgValPostItemExpBonus = msgValItemExp1st;
                            } else {
                                msgValPostItemExpBonus = msgValPostItemExpBonus + msgValItemExp2nd;
                            }
                        }
                        
                        valKodePostItemExp.put(prodLine, msgValPostItemExpBonus);
                        prodLine = prodLine + 1;
                    
                    } else if (discType.equalsIgnoreCase(discTypeBiaya)) {                 
                        Number biayaOnTop =
                        (Number)promoProdRow.getAttribute("BiayaNonYearly");            
                        Number biayaMf =
                        (Number)promoProdRow.getAttribute("BiayaYearly");
                        
                        String msgValPostItemExpBiaya = "";
                        if (kodePostingOnTop == null && biayaOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpBiaya.trim().length() == 0) {
                                msgValPostItemExpBiaya = msgValKodePostOnTop1st;
                            } else {
                                msgValPostItemExpBiaya = msgValPostItemExpBiaya + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && biayaMf.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpBiaya.trim().length() == 0) {
                                msgValPostItemExpBiaya = msgValKodePostMf1st;
                            } else {
                                msgValPostItemExpBiaya = msgValPostItemExpBiaya + msgValKodePostMf2nd;
                            }
                        }
                        
                        if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                            if (msgValPostItemExpBiaya.trim().length() == 0) {
                                msgValPostItemExpBiaya = msgValItemExp1st;
                            } else {
                                msgValPostItemExpBiaya = msgValPostItemExpBiaya + msgValItemExp2nd;
                            }
                        }
                        
                        valKodePostItemExp.put(prodLine, msgValPostItemExpBiaya);
                        prodLine = prodLine + 1;
                    
                    } else if (discType.equalsIgnoreCase(discTypePotongan)) {                
                        Number potonganOnTop =
                        (Number)promoProdRow.getAttribute("DiscNonYearly");            
                        Number potonganMf =
                        (Number)promoProdRow.getAttribute("DiscYearly");
                        
                        String msgValPostItemExpPotongan = "";
                        if (kodePostingOnTop == null && potonganOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpPotongan.trim().length() == 0) {
                                msgValPostItemExpPotongan = msgValKodePostOnTop1st;
                            } else {
                                msgValPostItemExpPotongan = msgValPostItemExpPotongan + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && potonganMf.compareTo(zeroNum) > 0) {
                            if (msgValPostItemExpPotongan.trim().length() == 0) {
                                msgValPostItemExpPotongan = msgValKodePostMf1st;
                            } else {
                                msgValPostItemExpPotongan = msgValPostItemExpPotongan + msgValKodePostMf2nd;
                            }
                        }
                        
                        if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                            if (msgValPostItemExpPotongan.trim().length() == 0) {
                                msgValPostItemExpPotongan = msgValItemExp1st;
                            } else {
                                msgValPostItemExpPotongan = msgValPostItemExpPotongan + msgValItemExp2nd;
                            }
                        }
                        
                        valKodePostItemExp.put(prodLine, msgValPostItemExpPotongan);
                        prodLine = prodLine + 1;
                }
            }
            
            // Validate error message
            for (int k = 0; k < valKodePostItemExp.size(); k++) {
                String errMsg = valKodePostItemExp.get(k) == null ? "" : valKodePostItemExp.get(k);
                if (!errMsg.equalsIgnoreCase("")) {
                    kodePostingItemExpenseFilled = false;
                }
            }

            if (kodePostingItemExpenseFilled) {

                // Validasi jika sudah pernah Create PR
                FcsViewNoPrViewImpl noPrView =
                    (FcsViewNoPrViewImpl)confirmationAM.getFcsViewNoPrView1();
                noPrView.setNamedWhereClauseParam("noPr", confirmNo);
                noPrView.executeQuery();

                long prExists = noPrView.getEstimatedRowCount();
                String prStatus = "";
                Number prRevNum = new Number(0);
                if (prExists > 0) {
                    FcsViewNoPrViewRowImpl noPrRow = (FcsViewNoPrViewRowImpl)noPrView.first();
                    prStatus = noPrRow.getStatus();
                    prRevNum = noPrRow.getRevision();
                }

                if (prExists == 0 || (prStatus.equalsIgnoreCase(prStatusCancelled))) {
                    if (isBJP.equals(isBJPTrue)) {
                        String orgName = ebsOuIdFDI;
                        if (compId.equalsIgnoreCase("N")) {
                            orgName = ebsOuIdFDN;
                        }

                        ItemBjpFlagViewImpl itemBjpView =
                            confirmationAM.getItemBjpFlagView1();
                        itemBjpView.setNamedWhereClauseParam("orgName",
                                                             orgName);
                        itemBjpView.executeQuery();

                        if (itemBjpView.getEstimatedRowCount() == 0) {
                            JSFUtils.addFacesErrorMessage("Error",
                                                          "Kode item untuk BJP tidak ditemukan, proses tidak dapat dilanjutkan.");
                        } else if (itemBjpView.getEstimatedRowCount() > 1) {
                            JSFUtils.addFacesErrorMessage("Error",
                                                          "Ditemukan duplikasi kode item untuk BJP, proses tidak dapat dilanjutkan.");
                        } else {

                            Row itemCodeBjpRow = itemBjpView.first();
                            String bjpItemCode =
                                (String)itemCodeBjpRow.getAttribute("Item");
                            Number allBjpBudget = new Number(0);
                            Number nonYearlyBudget = new Number(0);
                            Number yearlyBudget = new Number(0);
                            int i = 0;
                            for (Row r :
                                 dciterPromoProduk.getAllRowsInRange()) {
                                i = i + 1;

                                if (discType.equalsIgnoreCase(discTypePotongan)) {
                                    nonYearlyBudget =
                                            (Number)r.getAttribute("DiscNonYearly");
                                    yearlyBudget =
                                            (Number)r.getAttribute("DiscYearly");
                                } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
                                    nonYearlyBudget =
                                            (Number)r.getAttribute("BarangBonusNonYearly");
                                    yearlyBudget =
                                            (Number)r.getAttribute("BarangBonusYearly");
                                } else {
                                    nonYearlyBudget =
                                            (Number)r.getAttribute("BiayaNonYearly");
                                    yearlyBudget =
                                            (Number)r.getAttribute("BiayaYearly");
                                }
                                Number allBudget =
                                    nonYearlyBudget.add(yearlyBudget);

                                allBjpBudget = allBjpBudget.add(allBudget);
                            }

                            FcsPoRequisitionTempViewImpl prTableTemp =
                                (FcsPoRequisitionTempViewImpl)confirmationAM.getFcsPoRequisitionTempView1();
                            Row prTableTempRow = prTableTemp.createRow();
                            prTableTempRow.setAttribute("CreationDate",
                                                        getCurrentSysDate());
                            prTableTempRow.setAttribute("InterfaceSourceCode",
                                                        "PC");
                            prTableTempRow.setAttribute("SourceTypeCode",
                                                        "VENDOR");
                            prTableTempRow.setAttribute("RequisitionType",
                                                        "PURCHASE");
                            prTableTempRow.setAttribute("DestinationTypeCode",
                                                        "INVENTORY");
                            prTableTempRow.setAttribute("UnitPrice", 1);
                            prTableTempRow.setAttribute("AuthorizationStatus",
                                                        "INCOMPLETE");
                            prTableTempRow.setAttribute("BatchId",
                                                        currBatchId);
                            prTableTempRow.setAttribute("GroupCode",
                                                        confirmNo);
                            prTableTempRow.setAttribute("ApprovalPathId",
                                                        getApprovalPathId());
                            //prTableTempRow.setAttribute("PreparerName", userFullName);
                            prTableTempRow.setAttribute("PreparerName",
                                                        "GENERATE,");
                            
                            if (prStatus.equalsIgnoreCase(prStatusCancelled)) {
                                Number newRevNum = prRevNum.add(1);
                                prTableTempRow.setAttribute("ReqNumberSegment1",
                                                            confirmNo + "." + newRevNum);
                                prTableTempRow.setAttribute("Revision", newRevNum);
                            } else {
                                prTableTempRow.setAttribute("ReqNumberSegment1",
                                                            confirmNo);
                                prTableTempRow.setAttribute("Revision", 0);
                            }
                            
                            prTableTempRow.setAttribute("HeaderAttribute1",
                                                        "PEMBELIAN BARANG PROMOSI");
                            prTableTempRow.setAttribute("HeaderAttribute2",
                                                        datePrAttr2Str);
                            prTableTempRow.setAttribute("HeaderAttribute3",
                                                        "Barang Promosi");
                            prTableTempRow.setAttribute("HeaderAttribute4",
                                                        confirmNo);
                            prTableTempRow.setAttribute("ItemSegment1",
                                                        bjpItemCode);
                            prTableTempRow.setAttribute("UnitOfMeasure",
                                                        getUomDesc(confirmationAM,
                                                                   bjpItemCode,
                                                                   "Y"));
                            prTableTempRow.setAttribute("UomCode",
                                                        getUomCode(confirmationAM,
                                                                   bjpItemCode,
                                                                   "Y"));
                            prTableTempRow.setAttribute("Quantity",
                                                        allBjpBudget);
                            prTableTempRow.setAttribute("LineType", "Goods");
                            prTableTempRow.setAttribute("NoteToReceiver",
                                                        confirmNo);
                            prTableTempRow.setAttribute("DestinationOrganizationCode",
                                                        "IOT");
                            prTableTempRow.setAttribute("DestinationSubinventory",
                                                        getDestinationSubinventory());
                            prTableTempRow.setAttribute("DeliverToLocationCode",
                                                        "FDI Location");
                            prTableTempRow.setAttribute("DeliverToRequestorName",
                                                        "PEMBELIAN BARANG PROMOSI,");
                            prTableTempRow.setAttribute("DistributionAttribute1",
                                                        confirmNo);
                            prTableTempRow.setAttribute("OrgId",
                                                        getOrgId(confirmationAM,
                                                                 compId));
                            prTableTempRow.setAttribute("LineNum", 1);
                            prTableTempRow.setAttribute("Status", "NEW");
                            prTableTempRow.setAttribute("LineAttribute2",
                                                        datePrAttr2Str);
                            prTableTemp.insertRow(prTableTempRow);

                            OperationBinding operationBinding =
                                bindings.getOperationBinding("Commit");
                            operationBinding.execute();

                            if (!operationBinding.getErrors().isEmpty()) {
                                throw new RuntimeException(operationBinding.getErrors().toString());
                            } else {
                                ProposalUpdatePrCreatedViewImpl voProposal =
                                    (ProposalUpdatePrCreatedViewImpl)confirmationAM.getProposalUpdatePrCreatedView1();
                                voProposal.setWhereClause("Proposal.PROPOSAL_ID = " +
                                                          propId.getValue());
                                voProposal.executeQuery();

                                if (voProposal.getEstimatedRowCount() > 0) {
                                    Row propRow = voProposal.first();
                                    propRow.setAttribute("PrCreated", "Y");

                                    OperationBinding operationBindingCommit =
                                        bindings.getOperationBinding("Commit");
                                    operationBindingCommit.execute();

                                    OperationBinding operationExecutePropConfirm =
                                        bindings.getOperationBinding("ExecutePropConfirm");
                                    operationExecutePropConfirm.execute();

                                    flagRunEbs = "Y";

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropConfirm);
                                }
                            }
                        }
                    } else {
                        int i = 0;
                        for (Row r : dciterPromoProduk.getAllRowsInRange()) {
                            i = i + 1;
                            /*
                            String postingCode =
                                (String)r.getAttribute("KodePosting");
                            */
                            String itemExpense =
                                (String)r.getAttribute("ItemExpense");
                            Number nonYearlyBudget = new Number(0);
                            Number yearlyBudget = new Number(0);

                            if (discType.equalsIgnoreCase(discTypePotongan)) {
                                nonYearlyBudget =
                                        (Number)r.getAttribute("DiscNonYearly");
                                yearlyBudget =
                                        (Number)r.getAttribute("DiscYearly");
                            } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
                                nonYearlyBudget =
                                        (Number)r.getAttribute("BarangBonusNonYearly");
                                yearlyBudget =
                                        (Number)r.getAttribute("BarangBonusYearly");
                            } else {
                                nonYearlyBudget =
                                        (Number)r.getAttribute("BiayaNonYearly");
                                yearlyBudget =
                                        (Number)r.getAttribute("BiayaYearly");
                            }
                            Number allBudget =
                                nonYearlyBudget.add(yearlyBudget);

                            FcsPoRequisitionTempViewImpl prTableTemp =
                                (FcsPoRequisitionTempViewImpl)confirmationAM.getFcsPoRequisitionTempView1();
                            Row prTableTempRow = prTableTemp.createRow();
                            prTableTempRow.setAttribute("CreationDate",
                                                        getCurrentSysDate());
                            prTableTempRow.setAttribute("InterfaceSourceCode",
                                                        "PC");
                            prTableTempRow.setAttribute("SourceTypeCode",
                                                        "VENDOR");
                            prTableTempRow.setAttribute("RequisitionType",
                                                        "PURCHASE");
                            prTableTempRow.setAttribute("DestinationTypeCode",
                                                        "EXPENSE");
                            prTableTempRow.setAttribute("UnitPrice", 1);
                            prTableTempRow.setAttribute("AuthorizationStatus",
                                                        "INCOMPLETE");
                            prTableTempRow.setAttribute("BatchId",
                                                        currBatchId);
                            prTableTempRow.setAttribute("GroupCode",
                                                        confirmNo);
                            prTableTempRow.setAttribute("ApprovalPathId",
                                                        getApprovalPathId());
                            //prTableTempRow.setAttribute("PreparerName", userFullName);
                            prTableTempRow.setAttribute("PreparerName",
                                                        "GENERATE,");
                            
                            if (prStatus.equalsIgnoreCase(prStatusCancelled)) {
                                Number newRevNum = prRevNum.add(1);
                                prTableTempRow.setAttribute("ReqNumberSegment1",
                                                            confirmNo + "." + newRevNum);
                                prTableTempRow.setAttribute("Revision", newRevNum);
                            } else {
                                prTableTempRow.setAttribute("ReqNumberSegment1",
                                                            confirmNo);
                                prTableTempRow.setAttribute("Revision", 0);
                            }
                            
                            prTableTempRow.setAttribute("HeaderAttribute1",
                                                        "PROMO");
                            prTableTempRow.setAttribute("HeaderAttribute2",
                                                        datePrAttr2Str);
                            prTableTempRow.setAttribute("HeaderAttribute3",
                                                        "Promo");
                            prTableTempRow.setAttribute("HeaderAttribute4",
                                                        confirmNo);
                            prTableTempRow.setAttribute("ItemSegment1",
                                                        itemExpense);
                            prTableTempRow.setAttribute("UnitOfMeasure",
                                                        getUomDesc(confirmationAM,
                                                                   itemExpense,
                                                                   "N"));
                            prTableTempRow.setAttribute("UomCode",
                                                        getUomCode(confirmationAM,
                                                                   itemExpense,
                                                                   "N"));
                            prTableTempRow.setAttribute("Quantity", allBudget);
                            prTableTempRow.setAttribute("LineType", "EXPENSE");
                            prTableTempRow.setAttribute("NoteToReceiver",
                                                        confirmNo);
                            prTableTempRow.setAttribute("DestinationOrganizationCode",
                                                        "IOT");
                            prTableTempRow.setAttribute("DestinationSubinventory",
                                                        null);
                            prTableTempRow.setAttribute("DeliverToLocationCode",
                                                        "FDI Location");
                            prTableTempRow.setAttribute("DeliverToRequestorName",
                                                        "PROMO,");
                            prTableTempRow.setAttribute("DistributionAttribute1",
                                                        confirmNo);
                            prTableTempRow.setAttribute("OrgId",
                                                        getOrgId(confirmationAM,
                                                                 compId));
                            prTableTempRow.setAttribute("LineNum", i);
                            prTableTempRow.setAttribute("Status", "NEW");
                            prTableTempRow.setAttribute("LineAttribute2",
                                                        datePrAttr2Str);
                            prTableTemp.insertRow(prTableTempRow);

                        }

                        OperationBinding operationBinding =
                            bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        if (!operationBinding.getErrors().isEmpty()) {
                            throw new RuntimeException(operationBinding.getErrors().toString());
                        } else {
                            ProposalUpdatePrCreatedViewImpl voProposal =
                                (ProposalUpdatePrCreatedViewImpl)confirmationAM.getProposalUpdatePrCreatedView1();
                            voProposal.setWhereClause("Proposal.PROPOSAL_ID = " +
                                                      propId.getValue());
                            voProposal.executeQuery();

                            if (voProposal.getEstimatedRowCount() > 0) {
                                Row propRow = voProposal.first();
                                propRow.setAttribute("PrCreated", "Y");

                                OperationBinding operationBindingCommit =
                                    bindings.getOperationBinding("Commit");
                                operationBindingCommit.execute();

                                OperationBinding operationExecutePropConfirm =
                                    bindings.getOperationBinding("ExecutePropConfirm");
                                operationExecutePropConfirm.execute();

                                flagRunEbs = "Y";

                                AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropConfirm);

                            }
                        }
                    }

                    // Execute procedure EBS push PR
                    if (flagRunEbs.equalsIgnoreCase("Y")) {
                        Integer prRequestId =
                            runConcurentPrPppc(confirmationAM, currBatchId);
                        if (prRequestId != null) {
                            AttributeBinding prRequestIdAttr =
                                (AttributeBinding)bindings.getControlBinding("PrRequestId");
                            prRequestIdAttr.setInputValue(prRequestId);
                        }
                    }

                    OperationBinding operationBindingCommit =
                        bindings.getOperationBinding("Commit");
                    operationBindingCommit.execute();
                } else {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  "Dokumen ini \"" + confirmNo +
                                                  "\", sudah pernah diajukan PR sebelumnya.");
                }
            } else {
                /*
                JSFUtils.addFacesErrorMessage("Error",
                                              "Kode posting dan item expense pada daftar produk belum lengkap terisi.");
                */

                StringBuilder message = new StringBuilder("<html><body>");
                for (int j = 0; j < valKodePostItemExp.size(); j++) {
                    String errMsg = valKodePostItemExp.get(j) == null ? "" : valKodePostItemExp.get(j);
                    int prodLineNum = j+1;
                    if (!errMsg.equalsIgnoreCase("")) {
                        message.append("<p>Produk line (" + prodLineNum + "): "+ errMsg +" belum terisi.</p>");
                    }
                }
                message.append("</body></html>");
                FacesMessage msg = new FacesMessage(message.toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                ctx.addMessage(null, msg);
            }

        }

    }

    private String getUomCode(ConfirmationAMImpl confirmationAM,
                              String kodePosting, String isBJP) {
        String uomCode = null;
        if (isBJP.equalsIgnoreCase("Y")) {
            KodePostingBJPViewImpl kodePostingVo =
                confirmationAM.getKodePostingBJPView1();
            kodePostingVo.setNamedWhereClauseParam("kodePosting", kodePosting);
            kodePostingVo.executeQuery();
            if (kodePostingVo.getEstimatedRowCount() > 0) {
                Row allKodePostingRow = kodePostingVo.first();
                uomCode = (String)allKodePostingRow.getAttribute("PrimaryUom");
            }
        } else {
            KodePostingViewImpl kodePostingVo =
                confirmationAM.getKodePostingView1();
            kodePostingVo.setNamedWhereClauseParam("kodePosting", kodePosting);
            kodePostingVo.executeQuery();
            if (kodePostingVo.getEstimatedRowCount() > 0) {
                Row allKodePostingRow = kodePostingVo.first();
                uomCode = (String)allKodePostingRow.getAttribute("PrimaryUom");
            }
        }
        return uomCode;
    }

    private String getUomDesc(ConfirmationAMImpl confirmationAM,
                              String kodePosting, String isBJP) {
        String uomDesc = null;
        if (isBJP.equalsIgnoreCase("Y")) {
            KodePostingBJPViewImpl kodePostingVo =
                confirmationAM.getKodePostingBJPView1();
            kodePostingVo.setNamedWhereClauseParam("kodePosting", kodePosting);
            kodePostingVo.executeQuery();
            if (kodePostingVo.getEstimatedRowCount() > 0) {
                Row allKodePostingRow = kodePostingVo.first();
                uomDesc =
                        (String)allKodePostingRow.getAttribute("PrimaryUnitOfMeasure");
            }
        } else {
            KodePostingViewImpl kodePostingVo =
                confirmationAM.getKodePostingView1();
            kodePostingVo.setNamedWhereClauseParam("kodePosting", kodePosting);
            kodePostingVo.executeQuery();
            if (kodePostingVo.getEstimatedRowCount() > 0) {
                Row allKodePostingRow = kodePostingVo.first();
                uomDesc =
                        (String)allKodePostingRow.getAttribute("PrimaryUnitOfMeasure");
            }
        }
        return uomDesc;
    }

    private oracle.jbo.domain.Number getOrgId(ConfirmationAMImpl confirmationAM,
                                              String compId) {
        oracle.jbo.domain.Number orgId = null;
        String compOu = null;
        if (compId.equalsIgnoreCase("N")) {
            compOu = ebsOuIdFDN;
        } else {
            compOu = ebsOuIdFDI;
        }

        CompanyOUViewImpl companyOUVo =
            (CompanyOUViewImpl)confirmationAM.getCompanyOUView1();
        companyOUVo.setNamedWhereClauseParam("ouName", compOu);
        companyOUVo.executeQuery();
        if (companyOUVo.getEstimatedRowCount() > 0) {
            Row companyOURow = companyOUVo.first();
            orgId = (oracle.jbo.domain.Number)companyOURow.getAttribute("Id");
        }
        return orgId;
    }

    private String batchNumber() {
        String batchNum = null;

        // Get Date on ddMMyy Format
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        DateFormat dfddMMyy = new SimpleDateFormat("ddMMyyHHmmss");
        String currDay = dfddMMyy.format(date);

        batchNum = "99" + currDay;

        return batchNum;
    }

    public void setSwitcherCustPost(UIXSwitcher switcherCustPost) {
        this.switcherCustPost = switcherCustPost;
    }

    public UIXSwitcher getSwitcherCustPost() {
        return switcherCustPost;
    }

    public void budgetByValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //Remove all budget customer row
        DCIteratorBinding dciterCust =
            ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
        RowSetIterator rsiBudCust = dciterCust.getRowSetIterator();
        for (Row budCustRow : dciterCust.getAllRowsInRange()) {
            budCustRow.remove();
        }
        rsiBudCust.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);

        //Remove all budget posting row
        DCIteratorBinding dciterPost =
            ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
        RowSetIterator rsiBudPost = dciterPost.getRowSetIterator();
        for (Row budPostRow : dciterPost.getAllRowsInRange()) {
            budPostRow.remove();
        }
        rsiBudPost.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);

        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherCustPost);
    }

    public void setTblBudgetCustomer(RichTable tblBudgetCustomer) {
        this.tblBudgetCustomer = tblBudgetCustomer;
    }

    public RichTable getTblBudgetCustomer() {
        return tblBudgetCustomer;
    }

    public void setTblBudgetPosting(RichTable tblBudgetPosting) {
        this.tblBudgetPosting = tblBudgetPosting;
    }

    public RichTable getTblBudgetPosting() {
        return tblBudgetPosting;
    }

    public void createModifierDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();

        FacesContext ctx = FacesContext.getCurrentInstance();
        
        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();
        AttributeBinding confirmNoAttr =
            (AttributeBinding)bindings.getControlBinding("ConfirmNo");
        String confNo = (String)confirmNoAttr.getInputValue();
        AttributeBinding adendumKeAttr =
            (AttributeBinding)bindings.getControlBinding("AddendumKe");
        String addendumKe = (String)adendumKeAttr.getInputValue();
        AttributeBinding userTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String userTypeCreator = (String)userTypeCreatorAttr.getInputValue();
        AttributeBinding discountTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discountType = (String)discountTypeAttr.getInputValue();
        
        String confirmNo = null;
        if (addendumKe != null) {
            confirmNo = confNo + "-" + addendumKe;
        } else {
            confirmNo = confNo;
        }

        ConfirmationAMImpl confirmationAM =
            (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

        FcsQpModifierTempViewImpl voModifierFind =
            confirmationAM.getFcsQpModifierTempView1();
        voModifierFind.setWhereClause("FcsQpModifierTemp.NAME = '" +
                                      confirmNo + "'");
        voModifierFind.executeQuery();

        if (voModifierFind.getEstimatedRowCount() == 0) {
            // Get user initial
            UserData userData =
                (UserData)JSFUtils.resolveExpression("#{UserData}");
            String userName = userData.getUserNameLogin();

            if (dialogEvent.getOutcome().name().equals("ok")) {
                // Flag run ebs procedure
                String flagRunEbs = "N";
                // Get Header Id Sequence
                HeaderIdSeqViewImpl voHeaderId =
                    (HeaderIdSeqViewImpl)confirmationAM.getHeaderIdSeqView1();
                voHeaderId.executeQuery();

                Row headerIdRow = voHeaderId.first();
                Number headerIdNum =
                    (Number)headerIdRow.getAttribute("HeaderIdSeq");

                HashMap<Integer, String> valKodePosting = new HashMap<Integer, String>();
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                int prodLine = 0;
                for (Row promoProdRow :
                     dciterPromoProduk.getAllRowsInRange()) {
                    String kodePostingOnTop =
                        (String)promoProdRow.getAttribute("KodePosting");
                    String kodePostingMf =
                        (String)promoProdRow.getAttribute("KodePostingMf");
                    
                    if (discountType.equalsIgnoreCase(discTypePromoBarang)) {                    
                        Number barangBonusOnTop =
                        (Number)promoProdRow.getAttribute("BarangBonusNonYearly");            
                        Number barangBonusMf =
                        (Number)promoProdRow.getAttribute("BarangBonusYearly");
                        
                        String msgValPostBonus = "";
                        if (kodePostingOnTop == null && barangBonusOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostBonus.trim().length() == 0) {
                                msgValPostBonus = msgValKodePostOnTop1st;
                            } else {
                                msgValPostBonus = msgValPostBonus + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && barangBonusMf.compareTo(zeroNum) > 0) {
                            if (msgValPostBonus.trim().length() == 0) {
                                msgValPostBonus = msgValKodePostMf1st;
                            } else {
                                msgValPostBonus = msgValPostBonus + msgValKodePostMf2nd;
                            }
                        }
                        
                        valKodePosting.put(prodLine, msgValPostBonus);
                        prodLine = prodLine + 1;
                    
                    } else if (discountType.equalsIgnoreCase(discTypeBiaya)) {                 
                        Number biayaOnTop =
                        (Number)promoProdRow.getAttribute("BiayaNonYearly");            
                        Number biayaMf =
                        (Number)promoProdRow.getAttribute("BiayaYearly");
                        
                        String msgValPostBiaya = "";
                        if (kodePostingOnTop == null && biayaOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostBiaya.trim().length() == 0) {
                                msgValPostBiaya = msgValKodePostOnTop1st;
                            } else {
                                msgValPostBiaya = msgValPostBiaya + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && biayaMf.compareTo(zeroNum) > 0) {
                            if (msgValPostBiaya.trim().length() == 0) {
                                msgValPostBiaya = msgValKodePostMf1st;
                            } else {
                                msgValPostBiaya = msgValPostBiaya + msgValKodePostMf2nd;
                            }
                        }
                        
                        valKodePosting.put(prodLine, msgValPostBiaya);
                        prodLine = prodLine + 1;
                    
                    } else if (discountType.equalsIgnoreCase(discTypePotongan)) {                
                        Number potonganOnTop =
                        (Number)promoProdRow.getAttribute("DiscNonYearly");            
                        Number potonganMf =
                        (Number)promoProdRow.getAttribute("DiscYearly");
                        
                        String msgValPostPotongan = "";
                        if (kodePostingOnTop == null && potonganOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostPotongan.trim().length() == 0) {
                                msgValPostPotongan = msgValKodePostOnTop1st;
                            } else {
                                msgValPostPotongan = msgValPostPotongan + msgValKodePostOnTop2nd;
                            }
                        }
                        
                        if (kodePostingMf == null && potonganMf.compareTo(zeroNum) > 0) {
                            if (msgValPostPotongan.trim().length() == 0) {
                                msgValPostPotongan = msgValKodePostMf1st;
                            } else {
                                msgValPostPotongan = msgValPostPotongan + msgValKodePostMf2nd;
                            }
                        }
                        
                        valKodePosting.put(prodLine, msgValPostPotongan);
                        prodLine = prodLine + 1;
                    
                    }
                }
                
                // Validate error message
                boolean postingValid = true;
                for (int k = 0; k < valKodePosting.size(); k++) {
                    String errMsg = valKodePosting.get(k) == null ? "" : valKodePosting.get(k);
                    if (!errMsg.equalsIgnoreCase("")) {
                        postingValid = false;
                    }
                }

                if (postingValid) {
                    // Update modifier type
                    DCIteratorBinding dciterPropConfirm =
                        ADFUtils.findIterator("ProposalConfirmationView1Iterator");

                    RowSetIterator rsiPropConfirm =
                        dciterPropConfirm.getRowSetIterator();
                    Row[] propConfirmRow =
                        rsiPropConfirm.findByKey(new Key(new Object[] { propId.getValue() }),
                                                 1);

                    if (userTypeCreator.equalsIgnoreCase(userCreatorHo)) {
                        // Fetch Modifier HO
                        FcsModifierHoViewImpl voModifierHo =
                            (FcsModifierHoViewImpl)confirmationAM.getFcsModifierHoView1();
                        voModifierHo.setNamedWhereClauseParam("noConfirm",
                                                              confirmNo);
                        voModifierHo.executeQuery();

                        if (voModifierHo.getEstimatedRowCount() > 0) {
                            FcsQpModifierTempViewImpl voModifierTemp =
                                confirmationAM.getFcsQpModifierTempView1();
                            int i = 0;
                            while (voModifierHo.hasNext()) {
                            /*
                            for (Row modifierHoRow :
                                 voModifierHo.getAllRowsInRange()) {
                            */
                                i = i + 1;
                                Row modifierHoRow = voModifierHo.next();
                                String noConfirm =
                                    (String)modifierHoRow.getAttribute("NoConfirm");
                                String currency =
                                    (String)modifierHoRow.getAttribute("Currency");
                                Date startDate =
                                    (Date)modifierHoRow.getAttribute("StartDate");
                                Date endDate =
                                    (Date)modifierHoRow.getAttribute("EndDate");
                                String active =
                                    (String)modifierHoRow.getAttribute("Active");
                                String automatic =
                                    (String)modifierHoRow.getAttribute("Automatic");
                                String lineLevel =
                                    (String)modifierHoRow.getAttribute("LineLevel");
                                String lineType =
                                    (String)modifierHoRow.getAttribute("LineType");
                                Date startDateLine =
                                    (Date)modifierHoRow.getAttribute("StartDateLine");
                                Date endDateLine =
                                    (Date)modifierHoRow.getAttribute("EndDateLine");
                                String automaticLine =
                                    (String)modifierHoRow.getAttribute("AutomaticLine");
                                String pricingPhase =
                                    (String)modifierHoRow.getAttribute("PricingPhase");
                                String bucket =
                                    (String)modifierHoRow.getAttribute("Bucket1");
                                String productAttribute =
                                    (String)modifierHoRow.getAttribute("ProductAttribute");
                                String productValue =
                                    (String)modifierHoRow.getAttribute("ProductValue");
                                String volumeType =
                                    (String)modifierHoRow.getAttribute("VolumeType");
                                String breakType =
                                    (String)modifierHoRow.getAttribute("BreakType");
                                String uom =
                                    (String)modifierHoRow.getAttribute("Uom");
                                Number valueFrom =
                                    (Number)modifierHoRow.getAttribute("ValueFrom");
                                Number valueTo =
                                    (Number)modifierHoRow.getAttribute("ValueTo");
                                String applicationMethod =
                                    (String)modifierHoRow.getAttribute("ApplicationMethod");
                                Number value =
                                    (Number)modifierHoRow.getAttribute("Value");
                                Number groupingNo =
                                    (Number)modifierHoRow.getAttribute("GroupingNo");
                                String qualifierContext =
                                    (String)modifierHoRow.getAttribute("QualifierContext");
                                String qualifierAttr =
                                    (String)modifierHoRow.getAttribute("QualifierAttr");
                                String operatorSign =
                                    (String)modifierHoRow.getAttribute("OperatorSign");
                                String valueQualifier =
                                    (String)modifierHoRow.getAttribute("ValueQualifier");

                                Row modifierTempRow =
                                    voModifierTemp.createRow();
                                modifierTempRow.setAttribute("ModifierType",
                                                             "Discount List");
                                modifierTempRow.setAttribute("Name",
                                                             noConfirm);
                                modifierTempRow.setAttribute("Description",
                                                             noConfirm);
                                modifierTempRow.setAttribute("Currency",
                                                             currency);
                                modifierTempRow.setAttribute("StartDate",
                                                             startDate);
                                modifierTempRow.setAttribute("EndDate",
                                                             endDate);
                                modifierTempRow.setAttribute("Active", active);
                                modifierTempRow.setAttribute("Automatic",
                                                             automatic);
                                modifierTempRow.setAttribute("Version", null);
                                modifierTempRow.setAttribute("LineLevel",
                                                             lineLevel);
                                modifierTempRow.setAttribute("LineType",
                                                             lineType);
                                modifierTempRow.setAttribute("StartDateLine",
                                                             startDateLine);
                                modifierTempRow.setAttribute("EndDateLine",
                                                             endDateLine);
                                modifierTempRow.setAttribute("AutomaticLine",
                                                             automaticLine);
                                modifierTempRow.setAttribute("PricingPhase",
                                                             pricingPhase);
                                modifierTempRow.setAttribute("ProductAttribute",
                                                             productAttribute);
                                modifierTempRow.setAttribute("ProductAttributeValue",
                                                             productValue);
                                modifierTempRow.setAttribute("VolumeType",
                                                             volumeType);
                                modifierTempRow.setAttribute("BreakType",
                                                             breakType);
                                modifierTempRow.setAttribute("Uom", uom);
                                modifierTempRow.setAttribute("ValueFrom",
                                                             valueFrom);
                                modifierTempRow.setAttribute("ValueTo",
                                                             valueTo);
                                modifierTempRow.setAttribute("ApplicationMethod",
                                                             applicationMethod);
                                modifierTempRow.setAttribute("Value", value);
                                modifierTempRow.setAttribute("GroupingNo",
                                                             groupingNo);
                                modifierTempRow.setAttribute("QualifierContext",
                                                             qualifierContext);
                                modifierTempRow.setAttribute("QualifierAttr",
                                                             qualifierAttr);
                                modifierTempRow.setAttribute("Operator1",
                                                             operatorSign);
                                modifierTempRow.setAttribute("QValueFrom",
                                                             valueQualifier);
                                modifierTempRow.setAttribute("Bucket", bucket);
                                modifierTempRow.setAttribute("Flag", "N");
                                modifierTempRow.setAttribute("ListHeaderId",
                                                             headerIdNum);
                                modifierTempRow.setAttribute("LineNo", i);
                                modifierTempRow.setAttribute("CreatedBy",
                                                             userName);
                            }
                        }

                        propConfirmRow[0].setAttribute("ModifierType", "D");

                        flagRunEbs = "Y";

                    } else if (userTypeCreator.equalsIgnoreCase(userCreatorArea)) {
                        // Fetch Modifier Area
                        FcsModifierAreaViewImpl voModifierArea =
                            confirmationAM.getFcsModifierAreaView1();
                        voModifierArea.setNamedWhereClauseParam("noConfirm",
                                                                confirmNo);
                        voModifierArea.executeQuery();

                        if (voModifierArea.getEstimatedRowCount() > 0) {
                            FcsQpModifierTempViewImpl voModifierTemp =
                                confirmationAM.getFcsQpModifierTempView1();

                            int i = 0;
                            while (voModifierArea.hasNext()) {
                            /*
                            for (Row modifierAreaRow :
                                 voModifierArea.getAllRowsInRange()) {
                            */
                                i = i + 1;
                                Row modifierAreaRow = voModifierArea.next();
                                String noConfirm =
                                    (String)modifierAreaRow.getAttribute("NoConfirm");
                                String currency =
                                    (String)modifierAreaRow.getAttribute("Currency");
                                Date startDate =
                                    (Date)modifierAreaRow.getAttribute("StartDate");
                                Date endDate =
                                    (Date)modifierAreaRow.getAttribute("EndDate");
                                String active =
                                    (String)modifierAreaRow.getAttribute("Active");
                                String automatic =
                                    (String)modifierAreaRow.getAttribute("Automatic");
                                String lineLevel =
                                    (String)modifierAreaRow.getAttribute("LineLevel");
                                String lineType =
                                    (String)modifierAreaRow.getAttribute("LineType");
                                Date startDateLine =
                                    (Date)modifierAreaRow.getAttribute("StartDateLine");
                                Date endDateLine =
                                    (Date)modifierAreaRow.getAttribute("EndDateLine");
                                String automaticLine =
                                    (String)modifierAreaRow.getAttribute("AutomaticLine");
                                String pricingPhase =
                                    (String)modifierAreaRow.getAttribute("PricingPhase");
                                String bucket =
                                    (String)modifierAreaRow.getAttribute("Bucket1");
                                String productAttribute =
                                    (String)modifierAreaRow.getAttribute("ProductAttribute");
                                String productValue =
                                    (String)modifierAreaRow.getAttribute("ProductValue");
                                String volumeType =
                                    (String)modifierAreaRow.getAttribute("VolumeType");
                                String breakType =
                                    (String)modifierAreaRow.getAttribute("BreakType");
                                String uom =
                                    (String)modifierAreaRow.getAttribute("Uom");
                                Number valueFrom =
                                    (Number)modifierAreaRow.getAttribute("ValueFrom");
                                Number valueTo =
                                    (Number)modifierAreaRow.getAttribute("ValueTo");
                                String applicationMethod =
                                    (String)modifierAreaRow.getAttribute("ApplicationMethod");
                                Number value =
                                    (Number)modifierAreaRow.getAttribute("Value");
                                Number groupingNo =
                                    (Number)modifierAreaRow.getAttribute("GroupingNo");
                                String qualifierContext =
                                    (String)modifierAreaRow.getAttribute("QualifierContext");
                                String qualifierAttr =
                                    (String)modifierAreaRow.getAttribute("QualifierAttr");
                                String operatorSign =
                                    (String)modifierAreaRow.getAttribute("OperatorSign");
                                String valueQualifier =
                                    (String)modifierAreaRow.getAttribute("ValueQualifier");

                                Row modifierTempRow =
                                    voModifierTemp.createRow();
                                modifierTempRow.setAttribute("ModifierType",
                                                             "Discount List");
                                modifierTempRow.setAttribute("Name",
                                                             noConfirm);
                                modifierTempRow.setAttribute("Description",
                                                             noConfirm);
                                modifierTempRow.setAttribute("Currency",
                                                             currency);
                                modifierTempRow.setAttribute("StartDate",
                                                             startDate);
                                modifierTempRow.setAttribute("EndDate",
                                                             endDate);
                                modifierTempRow.setAttribute("Active", active);
                                modifierTempRow.setAttribute("Automatic",
                                                             automatic);
                                modifierTempRow.setAttribute("Version", null);
                                modifierTempRow.setAttribute("LineLevel",
                                                             lineLevel);
                                modifierTempRow.setAttribute("LineType",
                                                             lineType);
                                modifierTempRow.setAttribute("StartDateLine",
                                                             startDateLine);
                                modifierTempRow.setAttribute("EndDateLine",
                                                             endDateLine);
                                modifierTempRow.setAttribute("AutomaticLine",
                                                             automaticLine);
                                modifierTempRow.setAttribute("PricingPhase",
                                                             pricingPhase);
                                modifierTempRow.setAttribute("ProductAttribute",
                                                             productAttribute);
                                modifierTempRow.setAttribute("ProductAttributeValue",
                                                             productValue);
                                modifierTempRow.setAttribute("VolumeType",
                                                             volumeType);
                                modifierTempRow.setAttribute("BreakType",
                                                             breakType);
                                modifierTempRow.setAttribute("Uom", uom);
                                modifierTempRow.setAttribute("ValueFrom",
                                                             valueFrom);
                                modifierTempRow.setAttribute("ValueTo",
                                                             valueTo);
                                modifierTempRow.setAttribute("ApplicationMethod",
                                                             applicationMethod);
                                modifierTempRow.setAttribute("Value", value);
                                modifierTempRow.setAttribute("GroupingNo",
                                                             groupingNo);
                                modifierTempRow.setAttribute("QualifierContext",
                                                             qualifierContext);
                                modifierTempRow.setAttribute("QualifierAttr",
                                                             qualifierAttr);
                                modifierTempRow.setAttribute("Operator1",
                                                             operatorSign);
                                modifierTempRow.setAttribute("QValueFrom",
                                                             valueQualifier);
                                modifierTempRow.setAttribute("Bucket", bucket);
                                modifierTempRow.setAttribute("Flag", "N");
                                modifierTempRow.setAttribute("ListHeaderId",
                                                             headerIdNum);
                                modifierTempRow.setAttribute("LineNo", i);
                                modifierTempRow.setAttribute("CreatedBy",
                                                             userName);
                            }
                        }

                        propConfirmRow[0].setAttribute("ModifierType", "D");

                        flagRunEbs = "Y";

                    } else {
                        JSFUtils.addFacesErrorMessage("Error",
                                                      "Tipe proposal creator tidak dikenali, valid input \"HO\" dan \"AREA\"");
                    }

                    // Execute procedure EBS push Modifier
                    if (flagRunEbs.equalsIgnoreCase("Y")) {
                        Integer modifRequestId =
                            runConcurentModifPppc(confirmationAM, headerIdNum);
                        if (modifRequestId != null) {
                            AttributeBinding prRequestIdAttr =
                                (AttributeBinding)bindings.getControlBinding("ModRequestId");
                            prRequestIdAttr.setInputValue(modifRequestId);
                        }
                    }

                    OperationBinding operationBinding =
                        bindings.getOperationBinding("Commit");
                    operationBinding.execute();
                    
                } else {
                    /*
                    JSFUtils.addFacesErrorMessage("Error",
                                                  "Kode posting pada daftar produk belum lengkap terisi.");
                    */

                    StringBuilder message = new StringBuilder("<html><body>");
                    for (int j = 0; j < valKodePosting.size(); j++) {
                        String errMsg = valKodePosting.get(j) == null ? "" : valKodePosting.get(j);
                        int prodLineNum = j+1;
                        if (!errMsg.equalsIgnoreCase("")) {
                            message.append("<p>Produk line (" + prodLineNum + "): "+ errMsg +" belum terisi.</p>");
                        }
                    }
                    message.append("</body></html>");
                    FacesMessage msg = new FacesMessage(message.toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    ctx.addMessage(null, msg);
                }
            }
        } else {
            JSFUtils.addFacesErrorMessage("Error",
                                          "Create Modifier sudah pernah dilakukan untuk dokumen no  \"" +
                                          confirmNo + "\" ini.");
        }
    }


    public void createModifierPopupFetchListener(AttributeChangeEvent attributeChangeEvent) {
        // Nothing todo
    }

    public void budgetAmountPostValueChangeListener(ValueChangeEvent valueChangeEvent) {

        try {
            eksekusiPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Number valueTarget =
            (Number)dciterTarget.getCurrentRow().getAttribute("Value");
        System.out.println("VALUE POST TARGET: " + valueTarget.getValue());

        Number newValue = (Number)valueChangeEvent.getNewValue();

        System.out.println("NEW POST VALUE   : " + newValue.getValue());
        */
        //itBudgetPercent.setValue(arg0);
    }

    public void setItBudgetPostPercent(RichInputText itBudgetPostPercent) {
        this.itBudgetPostPercent = itBudgetPostPercent;
    }

    public RichInputText getItBudgetPostPercent() {
        return itBudgetPostPercent;
    }

    public void budgetAmountCustValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //                System.out.println("testinggg");
        try {
            eksekusi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //            int onTop=Integer.parseInt(itBudgetOnTop.getValue()==null?"0":itBudgetOnTop.getValue().toString().replaceAll(",", ""));
        //            int IMF=Integer.parseInt(itBudgetMF.getValue()==null?"0":itBudgetMF.getValue().toString().replaceAll(",", ""));
        //            int total=onTop+IMF;
        //            //        System.out.println(amount.getValue());
        ////            System.out.println("total"+total);
        //            int amounts=Integer.parseInt(amount.getValue()==null?"0":amount.getValue().toString().replaceAll(",", ""));
        //            float persen=(amounts*100.0f)/total;
        //            System.out.println("persen= "+toPercentage(persen));
        //            itBudgetCustPercent.setValue(toPercentage(persen,2));
        //
        //
        ////        AdfFacesContext.getCurrentInstance().addPartialTarget(itBudgetCustPercent);
        ////        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
        /*
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Number valueTarget =
            (Number)dciterTarget.getCurrentRow().getAttribute("Value");
        System.out.println("VALUE CUST TARGET: " + valueTarget.getValue());

        Number newValue = (Number)valueChangeEvent.getNewValue();

        System.out.println("NEW CUST VALUE   : " + newValue.getValue());
*/
    }

    public static String toPercentage(float n) {
        return String.format("%.0f", n) + "%";
    }

    public static String toPercentage(float n, int digits) {
        return String.format("%." + digits + "f", n);
    }

    public void setItBudgetCustPercent(RichInputText itBudgetCustPercent) {
        this.itBudgetCustPercent = itBudgetCustPercent;
    }

    public RichInputText getItBudgetCustPercent() {
        return itBudgetCustPercent;
    }

    public void detailProdukDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            try {
                DCIteratorBinding dciterCust =
                    ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
                float totalPercen = 0;
                //                System.out.println("count = "+dciterCust.getEstimatedRowCount());
                if (dciterCust.getEstimatedRowCount() > 0) {
                    RowSetIterator rsiBudCust = dciterCust.getRowSetIterator();

                    for (Row budCustRow : dciterCust.getAllRowsInRange()) {
                        totalPercen +=
                                Float.valueOf(budCustRow.getAttribute("Percentage").toString());
                    }
                    System.out.println("totalPercen = " + totalPercen);
                    //                    rsiBudCust.closeRowSetIterator();
                }
                //                if(totalPercen > 100){
                //                //                showPopup("Persentase Lebih Dari 100 %", potmessage);
                //                //                this.messageBox("Persentase Lebih Dari 100 %",
                //                //                                                      FacesMessage.SEVERITY_WARN);
                //                    JSFUtils.addFacesInformationMessage("Persentase Lebih Dari 100 %");
                //
                //                }
                DCIteratorBinding dciterPost =
                    ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
                float totalPercenPost = 0;
                if (dciterPost.getEstimatedRowCount() > 0) {
                    RowSetIterator rsiBudPost = dciterPost.getRowSetIterator();
                    for (Row budPostRow : dciterPost.getAllRowsInRange()) {
                        totalPercenPost +=
                                Float.valueOf(budPostRow.getAttribute("Percentage").toString());
                    }
                    //                    rsiBudPost.closeRowSetIterator();
                }

                if (totalPercenPost > 100 || totalPercen > 100) {
                    //                    JSFUtils.addFacesInformationMessage("Persentase Lebih Dari 100 %");
                    showPopup("Persentase Lebih Dari 100 %", potmessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();

            OperationBinding executeBudgetPost =
                bindings.getOperationBinding("ExecuteBudgetPost");
            executeBudgetPost.execute();

            OperationBinding executeBudgetCust =
                bindings.getOperationBinding("ExecuteBudgetCust");
            executeBudgetCust.execute();
*/
            //
            //                AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
            //                AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    private void messageBox(String msg, FacesMessage.Severity severity) {
        String messageText = msg;
        FacesMessage fm = new FacesMessage(messageText);
        /**
                * set the type of the message.
                * Valid types: error, fatal,info,warning
                */
        fm.setSeverity(severity);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
    }

    public void showPopup(String pesan, RichPopup p) {
        otpesan.setValue(pesan);
        AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
        adc.addPartialTarget(otpesan);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        p.show(hints);
    }

    public void detilProdukPopupFetchListener(PopupFetchEvent popupFetchEvent) {

        BindingContainer bindings = getBindings();

        OperationBinding executeBudgetPost =
            bindings.getOperationBinding("ExecuteBudgetPost");
        executeBudgetPost.execute();

        OperationBinding executeBudgetCust =
            bindings.getOperationBinding("ExecuteBudgetCust");
        executeBudgetCust.execute();
    }

    public void clearFailPrCreation(ConfirmationAMImpl confirmationAM) {
        FcsPoRequisitionTempViewImpl prTableTemp =
            (FcsPoRequisitionTempViewImpl)confirmationAM.getFcsPoRequisitionTempView1();
        //prTableTemp.setWhereClause("FcsPoRequisitionTemp.STATUS = 'FAIL'");
        prTableTemp.setWhereClause("EXISTS (SELECT 1 FROM APPS.FCS_PO_REQUISITION_TEMP FPR WHERE FPR.REQ_NUMBER_SEGMENT1 = FcsPoRequisitionTemp.REQ_NUMBER_SEGMENT1 AND (FPR.STATUS LIKE 'ERROR%' OR STATUS = 'FAIL'))");
        prTableTemp.executeQuery();

        if (prTableTemp.getEstimatedRowCount() > 0) {
            while (prTableTemp.hasNext()) {
                Row prTableTempRow = prTableTemp.next();
                prTableTempRow.remove();
            }
            confirmationAM.getDBTransaction().commit();
        }
    }

    public Integer runConcurentPrPppc(ConfirmationAMImpl confirmationAM,
                                      String batchIdGroup) {
        CallableStatement cst = null;
        Integer responMsg = null;
        try {
            cst =
confirmationAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_RUN_CONCURRENT_PR_PPPC('" +
                                                          batchIdGroup +
                                                          "', ?); END;", 0);
            cst.registerOutParameter(1, Types.NUMERIC);
            cst.executeUpdate();
            responMsg = cst.getInt(1);
            //System.out.println("RESPON MSG: " + responMsg.toString());
            JSFUtils.addFacesInformationMessage("PR sudah di submit dengan no request: " +
                                                responMsg.toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage(e.getMessage());
        } finally {
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return responMsg;
    }

    public Integer runConcurentModifPppc(ConfirmationAMImpl confirmationAM,
                                      Number listHeaderId) {
        CallableStatement cst = null;
        Integer responMsg = null;
        try {
            cst =
    confirmationAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_RUN_CONCURRENT_MODIF_PPPC('" +
                                                          listHeaderId +
                                                          "', ?); END;", 0);
            cst.registerOutParameter(1, Types.NUMERIC);
            cst.executeUpdate();
            responMsg = cst.getInt(1);
            //System.out.println("RESPON MSG: " + responMsg.toString());
            JSFUtils.addFacesInformationMessage("Modifier sudah di submit dengan no request: " +
                                                responMsg.toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage(e.getMessage());
        } finally {
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return responMsg;
    }

    public void setItBudgetOnTop(RichInputText itBudgetOnTop) {
        this.itBudgetOnTop = itBudgetOnTop;
    }

    public RichInputText getItBudgetOnTop() {
        return itBudgetOnTop;
    }

    public void setItBudgetMF(RichInputText itBudgetMF) {
        this.itBudgetMF = itBudgetMF;
    }

    public RichInputText getItBudgetMF() {
        return itBudgetMF;
    }

    public void addRowEvent(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    }

    public void addRowPostEvent(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
    }

    public void setAmount(RichInputText amount) {
        this.amount = amount;
    }

    public RichInputText getAmount() {
        return amount;
    }

    void eksekusi() {

        int onTop =
            Integer.parseInt(itBudgetOnTop.getValue() == null ? "0" : itBudgetOnTop.getValue().toString().replaceAll(",",
                                                                                                                     ""));
        int IMF =
            Integer.parseInt(itBudgetMF.getValue() == null ? "0" : itBudgetMF.getValue().toString().replaceAll(",",
                                                                                                               ""));
        int total = onTop + IMF;
        int amounts =
            Integer.parseInt(amount.getValue() == null ? "0" : amount.getValue().toString().replaceAll(",",
                                                                                                       ""));
        float persen = (amounts * 100.0f) / total;
        itBudgetCustPercent.setValue(toPercentage(persen, 2));
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
    }

    void eksekusiPost() {

        int onTop =
            Integer.parseInt(itBudgetOnTop.getValue() == null ? "0" : itBudgetOnTop.getValue().toString().replaceAll(",",
                                                                                                                     ""));
        int IMF =
            Integer.parseInt(itBudgetMF.getValue() == null ? "0" : itBudgetMF.getValue().toString().replaceAll(",",
                                                                                                               ""));
        int total = onTop + IMF;
        int amountspost =
            Integer.parseInt(itBudgetPostAmount.getValue() == null ? "0" :
                             itBudgetPostAmount.getValue().toString().replaceAll(",",
                                                                                 ""));
        float persen = (amountspost * 100.0f) / total;
        itBudgetPostPercent.setValue(toPercentage(persen, 2));

        //        AdfFacesContext.getCurrentInstance().addPartialTarget(itBudgetCustPercent);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
    }

    public void hitung(ActionEvent actionEvent) {
        int onTop =
            Integer.parseInt(itBudgetOnTop.getValue() == null ? "0" : itBudgetOnTop.getValue().toString().replaceAll(",",
                                                                                                                     ""));
        int IMF =
            Integer.parseInt(itBudgetMF.getValue() == null ? "0" : itBudgetMF.getValue().toString().replaceAll(",",
                                                                                                               ""));
        int total = onTop + IMF;
        int amounts =
            Integer.parseInt(amount.getValue() == null ? "0" : amount.getValue().toString().replaceAll(",",
                                                                                                       ""));
        float persen = (amounts * 100.0f) / total;
        System.out.println("persen= " + toPercentage(persen));
        itBudgetCustPercent.setValue(toPercentage(persen, 2));
        AdfFacesContext.getCurrentInstance().addPartialTarget(itBudgetCustPercent);
    }

    public void hitungPost(ActionEvent actionEvent) {
        int onTop =
            Integer.parseInt(itBudgetOnTop.getValue() == null ? "0" : itBudgetOnTop.getValue().toString().replaceAll(",",
                                                                                                                     ""));
        int IMF =
            Integer.parseInt(itBudgetMF.getValue() == null ? "0" : itBudgetMF.getValue().toString().replaceAll(",",
                                                                                                               ""));
        int total = onTop + IMF;
        int amountspost =
            Integer.parseInt(itBudgetPostAmount.getValue() == null ? "0" :
                             itBudgetPostAmount.getValue().toString().replaceAll(",",
                                                                                 ""));
        float persen = (amountspost * 100.0f) / total;
        itBudgetPostPercent.setValue(toPercentage(persen, 2));
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
    }

    public void setBtnHitungPercent(RichCommandButton btnHitungPercent) {
        this.btnHitungPercent = btnHitungPercent;
    }

    public RichCommandButton getBtnHitungPercent() {
        return btnHitungPercent;
    }

    public void setPotmessage(RichPopup potmessage) {
        this.potmessage = potmessage;
    }

    public RichPopup getPotmessage() {
        return potmessage;
    }

    public void setOtpesan(RichOutputText otpesan) {
        this.otpesan = otpesan;
    }

    public RichOutputText getOtpesan() {
        return otpesan;
    }

    public void setItBudgetPostAmount(RichInputText itBudgetPostAmount) {
        this.itBudgetPostAmount = itBudgetPostAmount;
    }

    public RichInputText getItBudgetPostAmount() {
        return itBudgetPostAmount;
    }

    public void setPopupDetailProd(RichPopup popupDetailProd) {
        this.popupDetailProd = popupDetailProd;
    }

    public RichPopup getPopupDetailProd() {
        return popupDetailProd;
    }

    public void dialogEventCustomDetailProduct(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        try {
            DCIteratorBinding dciterCust =
                ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
            float totalPercen = 0;
            if (dciterCust.getEstimatedRowCount() > 0) {
                RowSetIterator rsiBudCust = dciterCust.getRowSetIterator();

                for (Row budCustRow : dciterCust.getAllRowsInRange()) {
                    totalPercen +=
                            Float.valueOf(budCustRow.getAttribute("Percentage").toString());
                }
                rsiBudCust.closeRowSetIterator();
            }
            DCIteratorBinding dciterPost =
                ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
            float totalPercenPost = 0;
            if (dciterPost.getEstimatedRowCount() > 0) {
                RowSetIterator rsiBudPost = dciterPost.getRowSetIterator();
                DCIteratorBinding dciterPosting=
                    ADFUtils.findIterator("BudgetPostingView1Iterator");
                for (Row budPostRow : dciterPost.getAllRowsInRange()) {
                    totalPercenPost +=
                            Float.valueOf(budPostRow.getAttribute("Percentage").toString());
                    String KombinasiBudget=budPostRow.getAttribute("KombinasiBudget").toString();
                    String Amount=(String)budPostRow.getAttribute("Amount").toString();
                    String BudgetPostingId=(String)budPostRow.getAttribute("BudgetPostingId").toString();

                    BigDecimal am=new BigDecimal(Amount.toString());
                    
                    if (dciterPosting.getEstimatedRowCount() > 0) {
                        RowSetIterator BudMainpost = dciterPosting.getRowSetIterator();
                        for (Row budgetPosting : dciterPosting.getAllRowsInRange()) {
                            String yearBudgetAmount =
                                (String)budgetPosting.getAttribute("YearlyBudgetRemaining").toString();
                            String KodePosting =
                                (String)budgetPosting.getAttribute("BudgetPostingId").toString();
//                            String YearlyBudgetCalc =
//                                (String)budgetPosting.getAttribute("YearlyBudgetCalc").toString()==null?"0": (String)budgetPosting.getAttribute("YearlyBudgetCalc").toString();
                            if(BudgetPostingId.equalsIgnoreCase(KodePosting)){
                                BigDecimal calc=new BigDecimal(0);
                                BigDecimal sum=new BigDecimal(yearBudgetAmount.toString());
                                calc=sum.subtract(am);

                                budgetPosting.setAttribute("YearlyBudgetCalc",
                                                           calc);
                                dciterPosting.getDataControl().commitTransaction();
                            }
                        }
                        BudMainpost.closeRowSetIterator();
                    }
                }
                rsiBudPost.closeRowSetIterator();
            }

            if (totalPercenPost > 100 || totalPercen > 100) {
                showPopup("Persentase Lebih Dari 100 %", potmessage);
            }else{
                popupDetailProd.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        OperationBinding operationBinding =
            bindings.getOperationBinding("Commit");
        operationBinding.execute();

        OperationBinding executeBudgetPost =
            bindings.getOperationBinding("ExecuteBudgetPost");
        executeBudgetPost.execute();

        OperationBinding executeBudgetCust =
            bindings.getOperationBinding("ExecuteBudgetCust");
        executeBudgetCust.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
    }

    public void dialogCancelEventCustomDetailProduct(ActionEvent actionEvent) {
        try {

            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popupDetailProd.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void closePcDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (dialogEvent.getOutcome().name().equals("ok")) {

            ConfirmationAMImpl confirmationAM =
                (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");
            AttributeBinding mekPenagihanAttr =
                (AttributeBinding)bindings.getControlBinding("MekanismePenagihan1");
            String mekPenagihan = (String)mekPenagihanAttr.getInputValue();

            AttributeBinding confirmNoAttr =
                (AttributeBinding)bindings.getControlBinding("ConfirmNo");
            String confNo = (String)confirmNoAttr.getInputValue();
            AttributeBinding adendumKeAttr =
                (AttributeBinding)bindings.getControlBinding("AddendumKe");
            String addendumKe = (String)adendumKeAttr.getInputValue();

            String confirmNo = null;
            if (addendumKe != null) {
                confirmNo = confNo + "-" + addendumKe;
            } else {
                confirmNo = confNo;
            }

            if (mekPenagihan.equalsIgnoreCase(offInvoice)) {
                // Cek status PR, if CANCELLED or FINALLY CLOSED
                FcsViewNoPrClosedViewImpl noPrClosedView =
                    confirmationAM.getFcsViewNoPrClosedView1();
                noPrClosedView.setNamedWhereClauseParam("noPr", confirmNo);
                noPrClosedView.executeQuery();

                long prExists = noPrClosedView.getEstimatedRowCount();

                if (prExists > 0) {
                    // Cek status DCV: AMOUNT DUE REMAINING = 0
                    FcsCheckDcvPrClosedViewImpl checkDcvView =
                        confirmationAM.getFcsCheckDcvPrClosedView1();
                    checkDcvView.setNamedWhereClauseParam("noConfirm",
                                                          confirmNo);
                    checkDcvView.executeQuery();

                    long dcvExists = checkDcvView.getEstimatedRowCount();

                    if (dcvExists > 0) {
                        Row checkDcvRow = checkDcvView.first();
                        Number amtDueRem =
                            (Number)checkDcvRow.getAttribute("AmountDueRemaining");
                        Number zeroVal = new Number(0);
                        if (amtDueRem.compareTo(zeroVal) == 0) {
                            // Set CLOSE PC
                            AttributeBinding statusPropAttr =
                                (AttributeBinding)bindings.getControlBinding("Status1");
                            statusPropAttr.setInputValue("CLOSED");
                        } else {
                            double amount = amtDueRem.getValue();
                            DecimalFormat formatter =
                                new DecimalFormat("#,###.##");

                            StringBuilder message =
                                new StringBuilder("<html><body>");
                            message.append("<p>\"Amount Due Remaining\" masih tersisa " +
                                           formatter.format(amount) + "</p>");
                            message.append("<p>Dokumen PR ini belum dapat di-close.</p>");
                            message.append("</body></html>");
                            FacesMessage msg =
                                new FacesMessage(message.toString());
                            msg.setSeverity(FacesMessage.SEVERITY_WARN);
                            ctx.addMessage(null, msg);
                        }
                    } else {
                        StringBuilder message =
                            new StringBuilder("<html><body>");
                        message.append("<p>\"Amount Due Remaining\" tidak ditemukan untuk no confirm \"" +
                                       confirmNo + "\".</p>");
                        message.append("<p>Dokumen PR ini belum dapat di-close.</p>");
                        message.append("</body></html>");
                        FacesMessage msg =
                            new FacesMessage(message.toString());
                        msg.setSeverity(FacesMessage.SEVERITY_WARN);
                        ctx.addMessage(null, msg);
                    }
                } else {
                    // Cek tanggal akhir periode promo
                    AttributeBinding periodeToAttr =
                        (AttributeBinding)bindings.getControlBinding("PeriodeProgTo");
                    String endDateString =
                        (String)periodeToAttr.getInputValue();

                    DateFormat df =
                        new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    java.util.Date endDate = null;
                    try {
                        endDate = df.parse(endDateString + " 23:59:59");
                    } catch (ParseException e) {
                        JSFUtils.addFacesErrorMessage(e.getMessage());
                    }

                    java.util.Date todayDate = new java.util.Date();
                    if (todayDate.after(endDate) ||
                        todayDate.equals(endDate)) {
                        // Set CLOSE PC
                        AttributeBinding statusPropAttr =
                            (AttributeBinding)bindings.getControlBinding("Status1");
                        statusPropAttr.setInputValue("CLOSED");
                    } else {
                        JSFUtils.addFacesErrorMessage("Dokumen PC masih dalam periode promosi tidak dapat di-close.");
                    }
                }

            } else {
                // Set CLOSE PC
                AttributeBinding statusPropAttr =
                    (AttributeBinding)bindings.getControlBinding("Status1");
                statusPropAttr.setInputValue("CLOSED");
            }

            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();

            OperationBinding executeListConf =
                bindings.getOperationBinding("ExecuteProposalConf");
            executeListConf.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(tblPropConfirmation);

        }
    }

    private Number getApprovalPathId() {
        Number apvPathId = null;
        ConfirmationAMImpl confirmationAM =
            (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

        FcsApprovalPathViewImpl approvalPathView =
            confirmationAM.getFcsApprovalPathView1();
        approvalPathView.executeQuery();

        long pathExists = approvalPathView.getEstimatedRowCount();

        if (pathExists > 0) {
            Row approvalPathRow = approvalPathView.first();
            apvPathId =
                    (Number)approvalPathRow.getAttribute("PositionStructureId");
        } else {
            JSFUtils.addFacesWarningMessage("\"Position Structure Id\" tidak ditemukan, \"Approval Path Id\" set null.");
        }

        return apvPathId;
    }

    private String getDestinationSubinventory() {
        String destSubInv = null;
        ConfirmationAMImpl confirmationAM =
            (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

        FcsViewGudangInventoryViewImpl gudangInvView =
            confirmationAM.getFcsViewGudangInventoryView1();
        gudangInvView.executeQuery();

        long gudangInvExists = gudangInvView.getEstimatedRowCount();

        if (gudangInvExists > 0) {
            Row gudangInvRow = gudangInvView.first();
            destSubInv =
                    (String)gudangInvRow.getAttribute("SecondaryInventoryName");
        } else {
            JSFUtils.addFacesWarningMessage("\"Secondary Inventory Name\" tidak ditemukan, \"Destination Sub Inventory\" set null.");
        }

        return destSubInv;
    }

    public void showPDetailProduct(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popupDetailProd.show(hints);
    }

    public void bjpValueChangeListener(ValueChangeEvent valueChangeEvent) {
        DCIteratorBinding dciterPromoProduk =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        for (Row promoProdRow : dciterPromoProduk.getAllRowsInRange()) {
            promoProdRow.setAttribute("ItemExpense", null);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void setTblListProduct(RichTable tblListProduct) {
        this.tblListProduct = tblListProduct;
    }

    public RichTable getTblListProduct() {
        return tblListProduct;
    }

    public void addendumPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding propNoAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalNo");
        String propNo = (String)propNoAttr.getInputValue();
        
        DCIteratorBinding dciterPropAdendum =
            ADFUtils.findIterator("ProposalReadyConfirmAdendumView1Iterator");
        ViewObject propAdendumView = dciterPropAdendum.getViewObject();
        propAdendumView.setWhereClause("Proposal.COPY_SOURCE = '" + propNo + "'");
        propAdendumView.executeQuery();
    }

    public void propConfirmPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        DCIteratorBinding dciterPropConfirm =
            ADFUtils.findIterator("ProposalReadyConfirmAdendumView1Iterator");
        ViewObject propAdendumView = dciterPropConfirm.getViewObject();
        propAdendumView.setWhereClause("Proposal.COPY_SOURCE IS NULL");
        propAdendumView.executeQuery();
    }

    public void kombinasiBudgetPostLovEvent(ReturnPopupEvent returnPopupEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        RichInputListOfValues lovField =
            (RichInputListOfValues)returnPopupEvent.getSource();
        ListOfValuesModel lovModel = lovField.getModel();
        CollectionModel collectionModel =
            lovModel.getTableModel().getCollectionModel();
        JUCtrlHierBinding treeBinding =
            (JUCtrlHierBinding)collectionModel.getWrappedData();
        RowKeySet rks = (RowKeySet)returnPopupEvent.getReturnValue();
        List tableRowKey = (List)rks.iterator().next();
        DCIteratorBinding dciter = treeBinding.getDCIteratorBinding();
        Key key = (Key)tableRowKey.get(0);
        Row rw = dciter.findRowByKeyString(key.toStringFormat(true));
        
        DCIteratorBinding dciterPost =
            ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
        if (dciterPost.getEstimatedRowCount() > 0) {
          
            Row budPostRow = dciterPost.getCurrentRow();

                System.out.println(" id budget =" +rw.getAttribute("BudgetPostingId").toString());
               budPostRow.setAttribute("BudgetPostingId", rw.getAttribute("BudgetPostingId").toString());
            dciterPost.getDataControl().commitTransaction();
          
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
    }

    public void setItLovbudgetPost(RichInputListOfValues itLovbudgetPost) {
        this.itLovbudgetPost = itLovbudgetPost;
    }

    public RichInputListOfValues getItLovbudgetPost() {
        return itLovbudgetPost;
    }

   



}
