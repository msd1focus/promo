package app.fpp.bean.confirmation;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.bean.useraccessmenu.UserData;
import app.fpp.model.am.ConfirmationAMImpl;
import app.fpp.model.views.confirmation.ProposalUpdateConfirmAdendumViewImpl;
import app.fpp.model.views.confirmation.ProposalUpdatePrCreatedViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierAreaExclViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierAreaViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierHoExclViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsModifierHoViewImpl;
import app.fpp.model.views.confirmation.modifier.FcsQpModifierTempExclViewImpl;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
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
    private RichInputText itConfirmNo;
    private RichSelectOneChoice budgetBySoc;
    private RichShowDetailItem tabBudgetBind;
    private BigDecimal pajakDiv = new BigDecimal("1.1");
    private RichOutputText pathBind;
    private RichPopup pattacment;
    private UIXSwitcher switchMain;


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

        if (dialogEvent.getOutcome().name().equals("ok")) {
            ConfirmationAMImpl confirmationAM =
                (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

            DCIteratorBinding dciter =
                ADFUtils.findIterator("ProposalReadyConfirmAdendumView1Iterator");
            DBSequence propId =
                (DBSequence)dciter.getCurrentRow().getAttribute("ProposalId");

            ProposalUpdateConfirmAdendumViewImpl voProposal =
                (ProposalUpdateConfirmAdendumViewImpl)confirmationAM.getProposalUpdateConfirmAdendumView1();
            voProposal.setWhereClause("Proposal.PROPOSAL_ID = " +
                                      propId.getValue());
            voProposal.executeQuery();

            if (voProposal.getEstimatedRowCount() > 0) {
                Row propRow = voProposal.first();
                //propRow.setAttribute("ConfirmNo", confNumber);
                propRow.setAttribute("ConfirmNo", "Auto Generated");
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

            AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropConfirm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblSrcPropAdendum);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblPropConfirmation);
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
        
        AttributeBinding categoryPcAttr =
            (AttributeBinding)bindings.getControlBinding("CategoryPc");
        String categoryPc = (String)categoryPcAttr.getInputValue();

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
                propRow.setAttribute("CategoryPc", categoryPc);
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

            HashMap<Integer, String> valKodePostItemExp =
                new HashMap<Integer, String>();

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
                    if (kodePostingOnTop == null &&
                        barangBonusOnTop.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpBonus.trim().length() == 0) {
                            msgValPostItemExpBonus = msgValKodePostOnTop1st;
                        } else {
                            msgValPostItemExpBonus =
                                    msgValPostItemExpBonus + msgValKodePostOnTop2nd;
                        }
                    }

                    if (kodePostingMf == null &&
                        barangBonusMf.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpBonus.trim().length() == 0) {
                            msgValPostItemExpBonus = msgValKodePostMf1st;
                        } else {
                            msgValPostItemExpBonus =
                                    msgValPostItemExpBonus + msgValKodePostMf2nd;
                        }
                    }

                    if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                        if (msgValPostItemExpBonus.trim().length() == 0) {
                            msgValPostItemExpBonus = msgValItemExp1st;
                        } else {
                            msgValPostItemExpBonus =
                                    msgValPostItemExpBonus + msgValItemExp2nd;
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
                    if (kodePostingOnTop == null &&
                        biayaOnTop.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpBiaya.trim().length() == 0) {
                            msgValPostItemExpBiaya = msgValKodePostOnTop1st;
                        } else {
                            msgValPostItemExpBiaya =
                                    msgValPostItemExpBiaya + msgValKodePostOnTop2nd;
                        }
                    }

                    if (kodePostingMf == null &&
                        biayaMf.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpBiaya.trim().length() == 0) {
                            msgValPostItemExpBiaya = msgValKodePostMf1st;
                        } else {
                            msgValPostItemExpBiaya =
                                    msgValPostItemExpBiaya + msgValKodePostMf2nd;
                        }
                    }

                    if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                        if (msgValPostItemExpBiaya.trim().length() == 0) {
                            msgValPostItemExpBiaya = msgValItemExp1st;
                        } else {
                            msgValPostItemExpBiaya =
                                    msgValPostItemExpBiaya + msgValItemExp2nd;
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
                    if (kodePostingOnTop == null &&
                        potonganOnTop.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpPotongan.trim().length() == 0) {
                            msgValPostItemExpPotongan = msgValKodePostOnTop1st;
                        } else {
                            msgValPostItemExpPotongan =
                                    msgValPostItemExpPotongan +
                                    msgValKodePostOnTop2nd;
                        }
                    }

                    if (kodePostingMf == null &&
                        potonganMf.compareTo(zeroNum) > 0) {
                        if (msgValPostItemExpPotongan.trim().length() == 0) {
                            msgValPostItemExpPotongan = msgValKodePostMf1st;
                        } else {
                            msgValPostItemExpPotongan =
                                    msgValPostItemExpPotongan +
                                    msgValKodePostMf2nd;
                        }
                    }

                    if (itemExpense == null && !isBJP.equals(isBJPTrue)) {
                        if (msgValPostItemExpPotongan.trim().length() == 0) {
                            msgValPostItemExpPotongan = msgValItemExp1st;
                        } else {
                            msgValPostItemExpPotongan =
                                    msgValPostItemExpPotongan +
                                    msgValItemExp2nd;
                        }
                    }

                    valKodePostItemExp.put(prodLine,
                                           msgValPostItemExpPotongan);
                    prodLine = prodLine + 1;
                }
            }

            // Validate error message
            for (int k = 0; k < valKodePostItemExp.size(); k++) {
                String errMsg =
                    valKodePostItemExp.get(k) == null ? "" : valKodePostItemExp.get(k);
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
                    FcsViewNoPrViewRowImpl noPrRow =
                        (FcsViewNoPrViewRowImpl)noPrView.first();
                    prStatus = noPrRow.getStatus();
                    prRevNum = noPrRow.getRevision();
                }

                if (prExists == 0 ||
                    (prStatus.equalsIgnoreCase(prStatusCancelled))) {
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
                                                            confirmNo + "." +
                                                            newRevNum);
                                prTableTempRow.setAttribute("Revision",
                                                            newRevNum);
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
                                                        allBjpBudget.getBigDecimalValue().divide(pajakDiv, 2, RoundingMode.HALF_UP));
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
                                                            confirmNo + "." +
                                                            newRevNum);
                                prTableTempRow.setAttribute("Revision",
                                                            newRevNum);
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
                            prTableTempRow.setAttribute("Quantity", allBudget.getBigDecimalValue().divide(pajakDiv, 2, RoundingMode.HALF_UP));
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
                    String errMsg =
                        valKodePostItemExp.get(j) == null ? "" : valKodePostItemExp.get(j);
                    int prodLineNum = j + 1;
                    if (!errMsg.equalsIgnoreCase("")) {
                        message.append("<p>Produk line (" + prodLineNum +
                                       "): " + errMsg + " belum terisi.</p>");
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

                HashMap<Integer, String> valKodePosting =
                    new HashMap<Integer, String>();
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
                        if (kodePostingOnTop == null &&
                            barangBonusOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostBonus.trim().length() == 0) {
                                msgValPostBonus = msgValKodePostOnTop1st;
                            } else {
                                msgValPostBonus =
                                        msgValPostBonus + msgValKodePostOnTop2nd;
                            }
                        }

                        if (kodePostingMf == null &&
                            barangBonusMf.compareTo(zeroNum) > 0) {
                            if (msgValPostBonus.trim().length() == 0) {
                                msgValPostBonus = msgValKodePostMf1st;
                            } else {
                                msgValPostBonus =
                                        msgValPostBonus + msgValKodePostMf2nd;
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
                        if (kodePostingOnTop == null &&
                            biayaOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostBiaya.trim().length() == 0) {
                                msgValPostBiaya = msgValKodePostOnTop1st;
                            } else {
                                msgValPostBiaya =
                                        msgValPostBiaya + msgValKodePostOnTop2nd;
                            }
                        }

                        if (kodePostingMf == null &&
                            biayaMf.compareTo(zeroNum) > 0) {
                            if (msgValPostBiaya.trim().length() == 0) {
                                msgValPostBiaya = msgValKodePostMf1st;
                            } else {
                                msgValPostBiaya =
                                        msgValPostBiaya + msgValKodePostMf2nd;
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
                        if (kodePostingOnTop == null &&
                            potonganOnTop.compareTo(zeroNum) > 0) {
                            if (msgValPostPotongan.trim().length() == 0) {
                                msgValPostPotongan = msgValKodePostOnTop1st;
                            } else {
                                msgValPostPotongan =
                                        msgValPostPotongan + msgValKodePostOnTop2nd;
                            }
                        }

                        if (kodePostingMf == null &&
                            potonganMf.compareTo(zeroNum) > 0) {
                            if (msgValPostPotongan.trim().length() == 0) {
                                msgValPostPotongan = msgValKodePostMf1st;
                            } else {
                                msgValPostPotongan =
                                        msgValPostPotongan + msgValKodePostMf2nd;
                            }
                        }

                        valKodePosting.put(prodLine, msgValPostPotongan);
                        prodLine = prodLine + 1;

                    }
                }

                // Validate error message
                boolean postingValid = true;
                for (int k = 0; k < valKodePosting.size(); k++) {
                    String errMsg =
                        valKodePosting.get(k) == null ? "" : valKodePosting.get(k);
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
                            ArrayList<String> runMarkList = new ArrayList<String>();
                            
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
                                    (String)modifierHoRow.getAttribute("Bucket");
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
                                Number lineNum =
                                    (Number)modifierHoRow.getAttribute("LineNum");
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
                                String valueConfirmNoDef =
                                    (String)modifierHoRow.getAttribute("ConfirmNoDef");
                                Number promoProdukId =
                                    (Number)modifierHoRow.getAttribute("PromoProdukId");     
                                String ketOntopMf =
                                    (String)modifierHoRow.getAttribute("Ket"); 
                                String runMark = String.valueOf(promoProdukId.intValue()).concat(ketOntopMf);

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
                                modifierTempRow.setAttribute("Attribute2",
                                                             valueConfirmNoDef);
                                modifierTempRow.setAttribute("Operator1",
                                                             operatorSign);
                                modifierTempRow.setAttribute("QValueFrom",
                                                             valueQualifier);
                                modifierTempRow.setAttribute("Bucket", bucket);
                                modifierTempRow.setAttribute("Flag", "N");
                                modifierTempRow.setAttribute("ListHeaderId",
                                                             headerIdNum);
                                modifierTempRow.setAttribute("LineNo", lineNum);
                                modifierTempRow.setAttribute("CreatedBy",
                                                             userName);
                                
                                if (!runMarkList.contains(runMark)) {
                                    createModifierHoExcl(confirmationAM, promoProdukId, headerIdNum, lineNum, userName, ketOntopMf); 
                                    runMarkList.add(runMark);
                                }
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
                            ArrayList<String> runMarkList = new ArrayList<String>();
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
                                    (String)modifierAreaRow.getAttribute("Bucket");
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
                                Number lineNum =
                                    (Number)modifierAreaRow.getAttribute("LineNum");
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
                                String valueConfirmNoDef =
                                    (String)modifierAreaRow.getAttribute("ConfirmNoDef");
                                Number promoProdukId =
                                    (Number)modifierAreaRow.getAttribute("PromoProdukId");
                                String ketOntopMf =
                                    (String)modifierAreaRow.getAttribute("Ket");
                                String runMark = String.valueOf(promoProdukId.intValue()).concat(ketOntopMf);

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
                                modifierTempRow.setAttribute("Attribute2",
                                                             valueConfirmNoDef);
                                modifierTempRow.setAttribute("Operator1",
                                                             operatorSign);
                                modifierTempRow.setAttribute("QValueFrom",
                                                             valueQualifier);
                                modifierTempRow.setAttribute("Bucket", bucket);
                                modifierTempRow.setAttribute("Flag", "N");
                                modifierTempRow.setAttribute("ListHeaderId",
                                                             headerIdNum);
                                modifierTempRow.setAttribute("LineNo", lineNum);
                                modifierTempRow.setAttribute("CreatedBy",
                                                             userName);
                                
                                if (!runMarkList.contains(runMark)) {
                                    createModifierAreaExcl(confirmationAM, promoProdukId, headerIdNum, lineNum, userName, ketOntopMf);  
                                    runMarkList.add(runMark);
                                }
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
                        String errMsg =
                            valKodePosting.get(j) == null ? "" : valKodePosting.get(j);
                        int prodLineNum = j + 1;
                        if (!errMsg.equalsIgnoreCase("")) {
                            message.append("<p>Produk line (" + prodLineNum +
                                           "): " + errMsg +
                                           " belum terisi.</p>");
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
            String budcustId="";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("ProdBudgetByPostView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetById") != null) {
                budcustId=rowSelected.getAttribute("BudgetById").toString();
            } 
            
            DCIteratorBinding dcItteratorProposal =
                bindings.findIteratorBinding("ProposalConfirmationView1Iterator");
            Row rDiskon=dcItteratorProposal.getCurrentRow();
            String diskonType=rDiskon.getAttribute("DiscountType").toString();
            DCIteratorBinding dcItteratorDiscPromoProd =
                bindings.findIteratorBinding("PromoProdukView1Iterator");
            Row rPromoProd=dcItteratorDiscPromoProd.getCurrentRow();
            int IMF=0;
            if(diskonType.equalsIgnoreCase(discTypePotongan)){
               IMF=Integer.parseInt(rPromoProd.getAttribute("DiscYearly").toString() == null ? "" : rPromoProd.getAttribute("DiscYearly").toString());
    //               System.out.println("IMFD = "+IMF);
            }else if(diskonType.equalsIgnoreCase(discTypeBiaya)){
               IMF=Integer.parseInt(rPromoProd.getAttribute("BiayaYearly").toString() == null ? "" : rPromoProd.getAttribute("BiayaYearly").toString());
    //               System.out.println("IMFB = "+IMF);
            }else if(diskonType.equalsIgnoreCase(discTypePromoBarang)){
               IMF=Integer.parseInt(rPromoProd.getAttribute("BarangBonusYearly").toString() == null ? "" : rPromoProd.getAttribute("BarangBonusYearly").toString());
    //               System.out.println("IMFPB = "+IMF);
            }
                 DCIteratorBinding dciterBudget = ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
            for (Row r : dciterBudget.getAllRowsInRange()) {
                int amountFirst=0;
                float persen=0;
                String budId=r.getAttribute("BudgetById").toString() == null ? "" : r.getAttribute("BudgetById").toString();
                if(budcustId.equalsIgnoreCase(budId)){
                    amountFirst=Integer.parseInt(valueChangeEvent.getNewValue().toString().replaceAll(",",""));                   
                    persen=(amountFirst * 100.0f) / IMF;
                    r.setAttribute("Percentage", toPercentage(persen, 2));
                }
            }                                                                                                 
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setItBudgetPostPercent(RichInputText itBudgetPostPercent) {
        this.itBudgetPostPercent = itBudgetPostPercent;
    }

    public RichInputText getItBudgetPostPercent() {
        return itBudgetPostPercent;
    }

    public void budgetAmountCustValueChangeListener(ValueChangeEvent valueChangeEvent) {
            try {
                String budcustId="";
                DCBindingContainer bindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dcItteratorBindings =
                    bindings.findIteratorBinding("ProdBudgetByCustView1Iterator");
                ViewObject voTableData = dcItteratorBindings.getViewObject();
                Row rowSelected = voTableData.getCurrentRow();
                if (rowSelected.getAttribute("BudgetById") != null) {
                    budcustId=rowSelected.getAttribute("BudgetById").toString();
                } 
                
                DCIteratorBinding dcItteratorProposal =
                    bindings.findIteratorBinding("ProposalConfirmationView1Iterator");
                Row rDiskon=dcItteratorProposal.getCurrentRow();
                String diskonType=rDiskon.getAttribute("DiscountType").toString();
                DCIteratorBinding dcItteratorDiscPromoProd =
                    bindings.findIteratorBinding("PromoProdukView1Iterator");
                Row rPromoProd=dcItteratorDiscPromoProd.getCurrentRow();
                int IMF=0;
                if(diskonType.equalsIgnoreCase(discTypePotongan)){
                   IMF=Integer.parseInt(rPromoProd.getAttribute("DiscMf").toString() == null ? "" : rPromoProd.getAttribute("DiscMf").toString());
    //               System.out.println("IMFD = "+IMF);
                }else if(diskonType.equalsIgnoreCase(discTypeBiaya)){
                   IMF=Integer.parseInt(rPromoProd.getAttribute("BiayaYearly").toString() == null ? "" : rPromoProd.getAttribute("BiayaYearly").toString());
    //               System.out.println("IMFB = "+IMF);
                }else if(diskonType.equalsIgnoreCase(discTypePromoBarang)){
                   IMF=Integer.parseInt(rPromoProd.getAttribute("BarangBonusYearly").toString() == null ? "" : rPromoProd.getAttribute("BarangBonusYearly").toString());
    //               System.out.println("IMFPB = "+IMF);
                }
                     DCIteratorBinding dciterBudget = ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
                for (Row r : dciterBudget.getAllRowsInRange()) {
                    int amountFirst=0;
                    float persen=0;
                    String budId=r.getAttribute("BudgetById").toString() == null ? "" : r.getAttribute("BudgetById").toString();
                    if(budcustId.equalsIgnoreCase(budId)){
                        amountFirst=Integer.parseInt(valueChangeEvent.getNewValue().toString().replaceAll(",",""));                   
                        persen=(amountFirst * 100.0f) / IMF;
                        r.setAttribute("Percentage", toPercentage(persen, 2));
                    }
                }                                                                                                 
                AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                if (dciterCust.getEstimatedRowCount() > 0) {
                    for (Row budCustRow : dciterCust.getAllRowsInRange()) {
                        totalPercen +=
                                Float.valueOf(budCustRow.getAttribute("Percentage").toString());
                    }
                }
                DCIteratorBinding dciterPost =
                    ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
                float totalPercenPost = 0;
                if (dciterPost.getEstimatedRowCount() > 0) {
                    for (Row budPostRow : dciterPost.getAllRowsInRange()) {
                        totalPercenPost +=
                                Float.valueOf(budPostRow.getAttribute("Percentage").toString());
                    }
                }

                if (totalPercenPost > 100 || totalPercen > 100) {
                    showPopup("Persentase Lebih Dari 100 %", potmessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void addRowRealItemPaket(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsertItemPaket");
        operationBinding.execute();
    }

    public void deleteRowRealItemPaket(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("DeleteItemPaket");
        operationBinding.execute();
    }

    public void setAmount(RichInputText amount) {
        this.amount = amount;
    }

    public RichInputText getAmount() {
        return amount;
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
            String budgetByChoice=budgetBySoc.getValue().toString();
            if(budgetByChoice.equalsIgnoreCase("0")){
                DCIteratorBinding dciterCust =
                    ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
                DCIteratorBinding dciterCustomerbudget=
                    ADFUtils.findIterator("BudgetCustomerHeaderView1Iterator");
            float totalPercen = 0;
            if(dciterCust.getEstimatedRowCount() > 0){
                RowSetIterator BudMainCust = dciterCust.getRowSetIterator();
                HashMap<String, BigDecimal> mapOfSum = new HashMap<String, BigDecimal>();
                BigDecimal budgetAsToDateUsed=BigDecimal.ZERO;
            for(Row budgetCusr : dciterCust.getAllRowsInRange()){
                  String custId =
                      budgetCusr.getAttribute("BudgetCustId").toString();
                  totalPercen +=
                          Float.valueOf(budgetCusr.getAttribute("Percentage").toString()); 
               
                if(mapOfSum.containsKey(custId)){
                  BigDecimal total=new BigDecimal(budgetCusr.getAttribute("Amount").toString());
                                         budgetAsToDateUsed = budgetAsToDateUsed.add(total);   
                }else{
                   BigDecimal total=new BigDecimal(budgetCusr.getAttribute("Amount").toString());
                    budgetAsToDateUsed=total;
                }
                mapOfSum.put(custId, budgetAsToDateUsed);
              }
            BudMainCust.closeRowSetIterator();
//                System.out.println("mapOfSum = "+mapOfSum);
                if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                    RowSetIterator rsiBudCust = dciterCustomerbudget.getRowSetIterator();
                    for (Row budCustRow : dciterCustomerbudget.getAllRowsInRange()) {
                        String  BudgetCustId=budCustRow.getAttribute("BudgetCustomerId").toString(); 
                    for (Map.Entry<String, BigDecimal> entry :
                                 mapOfSum.entrySet()) {
                                String key = entry.getKey();
                                BigDecimal value = entry.getValue();
                        if(key.equalsIgnoreCase(BudgetCustId)){
                            budCustRow.setAttribute("BudgetAsToDateUsed", value);
                        }
                    }
                    dciterCustomerbudget.getDataControl().commitTransaction();
                }
            rsiBudCust.closeRowSetIterator();
        }
}
                if (totalPercen > 100) {
                    showPopup("Persentase Lebih Dari 100 %", potmessage);
                }else{
                    popupDetailProd.hide();
                }
            }else {
                DCIteratorBinding dciterPost =
                    ADFUtils.findIterator("ProdBudgetByPostView1Iterator");
//                System.out.println("dciterPost count "+dciterPost.getEstimatedRowCount());
                DCIteratorBinding dciterCustomerbudget=
                    ADFUtils.findIterator("BudgetCustomerHeaderView1Iterator");
                float totalPercenPost = 0;
                if(dciterPost.getEstimatedRowCount() > 0){
                RowSetIterator BudMainCust = dciterPost.getRowSetIterator();
                HashMap<String, BigDecimal> mapOfSum = new HashMap<String, BigDecimal>();
                BigDecimal budgetAsToDateUsed=BigDecimal.ZERO;
                for(Row budgetCusr : dciterPost.getAllRowsInRange()){
                  String custId =
                      budgetCusr.getAttribute("BudgetCustId").toString();
                  totalPercenPost +=
                          Float.valueOf(budgetCusr.getAttribute("Percentage").toString()); 
                
                if(mapOfSum.containsKey(custId)){
                  BigDecimal total=new BigDecimal(budgetCusr.getAttribute("Amount").toString());
                                         budgetAsToDateUsed = budgetAsToDateUsed.add(total);   
                }else{
                   BigDecimal total=new BigDecimal(budgetCusr.getAttribute("Amount").toString());
                    budgetAsToDateUsed=total;
                }
                mapOfSum.put(custId, budgetAsToDateUsed);
                }
                BudMainCust.closeRowSetIterator();
                System.out.println("mapOfSum = "+mapOfSum);
                if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                    RowSetIterator rsiBudCust = dciterCustomerbudget.getRowSetIterator();
                    for (Row budCustRow : dciterCustomerbudget.getAllRowsInRange()) {
                        String  BudgetCustId=budCustRow.getAttribute("BudgetCustomerId").toString(); 
                    for (Map.Entry<String, BigDecimal> entry :
                                 mapOfSum.entrySet()) {
                                String key = entry.getKey();
                                BigDecimal value = entry.getValue();
                        if(key.equalsIgnoreCase(BudgetCustId)){
                            budCustRow.setAttribute("BudgetAsToDateUsed", value);
                        }
                    }
                    dciterCustomerbudget.getDataControl().commitTransaction();
                }
                rsiBudCust.closeRowSetIterator();
            }
    }
                if (totalPercenPost > 100) {
                    showPopup("Persentase Lebih Dari 100 %", potmessage);
                }else{
                    popupDetailProd.hide();
                }
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
        propAdendumView.setWhereClause("Proposal.COPY_SOURCE = '" + propNo +
                                       "'");
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
            budPostRow.setAttribute("BudgetCustId", rw.getAttribute("BudgetCustomerId").toString());
            dciterPost.getDataControl().commitTransaction();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetPosting);
    }

    
    public void kombinasiBudgetCustLovEvent(ReturnPopupEvent returnPopupEvent) {
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
            ADFUtils.findIterator("ProdBudgetByCustView1Iterator");
        if (dciterPost.getEstimatedRowCount() > 0) {
            Row budPostRow = dciterPost.getCurrentRow();
            budPostRow.setAttribute("BudgetCustId", rw.getAttribute("BudgetCustomerId").toString());
            dciterPost.getDataControl().commitTransaction();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
    }

    public void setItLovbudgetPost(RichInputListOfValues itLovbudgetPost) {
        this.itLovbudgetPost = itLovbudgetPost;
    }

    public RichInputListOfValues getItLovbudgetPost() {
        return itLovbudgetPost;
    }

    public void setBudgetBySoc(RichSelectOneChoice budgetBySoc) {
        this.budgetBySoc = budgetBySoc;
    }

    public RichSelectOneChoice getBudgetBySoc() {
        return budgetBySoc;
    }

    public void setItConfirmNo(RichInputText itConfirmNo) {
        this.itConfirmNo = itConfirmNo;
    }

    public RichInputText getItConfirmNo() {
        return itConfirmNo;
    }

    public void setTabBudgetBind(RichShowDetailItem tabBudgetBind) {
        this.tabBudgetBind = tabBudgetBind;
    }

    public RichShowDetailItem getTabBudgetBind() {
        return tabBudgetBind;
    }

    public void setPathBind(RichOutputText pathBind) {
        this.pathBind = pathBind;
    }

    public RichOutputText getPathBind() {
        return pathBind;
    }

    public void downloadAction(FacesContext facesContext,
                               OutputStream outputStream) throws IOException {
        File filed = new File(pathBind.getValue().toString());
        FileInputStream fis;
        byte[] b;
        try {
            fis = new FileInputStream(filed);

            int n;
            while ((n = fis.available()) > 0) {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.flush();
    }

    public void setPattacment(RichPopup pattacment) {
        this.pattacment = pattacment;
    }

    public RichPopup getPattacment() {
        return pattacment;
    }

    public void showPAttacment(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            ADFUtils.findIterator("ProposalConfirmationView1Iterator");
        DBSequence propId =
            (DBSequence)dciter.getCurrentRow().getAttribute("ProposalId");
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("UploadDownloadView1Iterator");
        ViewObject vo = dcItteratorBindings.getViewObject();
        vo.setWhereClause("UploadDownload.PROPOSAL_ID = "+"'"+propId.getValue()+"'");
        vo.executeQuery();
        
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        pattacment.show(hints);
    }
    
    public void createModifierHoExcl(ConfirmationAMImpl confirmationAM, Number promoProdId, 
                                     Number headerIdNum, Number parentLine, String userName, String mfOntop) {

        // Fetch Modifier Exclude HO
        FcsModifierHoExclViewImpl voModifierHoExcl = confirmationAM.getFcsModifierHoExclView1();
        voModifierHoExcl.setNamedWhereClauseParam("promoProdId",
                                              promoProdId);
        voModifierHoExcl.setNamedWhereClauseParam("ketMfOntop",
                                              mfOntop);
        voModifierHoExcl.executeQuery();

        if (voModifierHoExcl.getEstimatedRowCount() > 0) {
            FcsQpModifierTempExclViewImpl voModifierTempExcl =
                (FcsQpModifierTempExclViewImpl)confirmationAM.getFcsQpModifierTempExclView1();
            while (voModifierHoExcl.hasNext()) {
                Row modifierHoRow = voModifierHoExcl.next();
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
                String valueConfirmNoDef =
                    (String)modifierHoRow.getAttribute("ConfirmNoDef");

                Row modifierTempExclRow =
                    voModifierTempExcl.createRow();
                modifierTempExclRow.setAttribute("ModifierType",
                                             "Discount List");
                modifierTempExclRow.setAttribute("Name",
                                             noConfirm);
                modifierTempExclRow.setAttribute("Description",
                                             noConfirm);
                modifierTempExclRow.setAttribute("Currency",
                                             currency);
                modifierTempExclRow.setAttribute("StartDate",
                                             startDate);
                modifierTempExclRow.setAttribute("EndDate",
                                             endDate);
                modifierTempExclRow.setAttribute("Active", active);
                modifierTempExclRow.setAttribute("Automatic",
                                             automatic);
                modifierTempExclRow.setAttribute("Version", null);
                modifierTempExclRow.setAttribute("LineLevel",
                                             lineLevel);
                modifierTempExclRow.setAttribute("LineType",
                                             lineType);
                modifierTempExclRow.setAttribute("StartDateLine",
                                             startDateLine);
                modifierTempExclRow.setAttribute("EndDateLine",
                                             endDateLine);
                modifierTempExclRow.setAttribute("AutomaticLine",
                                             automaticLine);
                modifierTempExclRow.setAttribute("PricingPhase",
                                             pricingPhase);
                modifierTempExclRow.setAttribute("ProductAttribute",
                                             productAttribute);
                modifierTempExclRow.setAttribute("ProductAttributeValue",
                                             productValue);
                modifierTempExclRow.setAttribute("VolumeType",
                                             volumeType);
                modifierTempExclRow.setAttribute("BreakType",
                                             breakType);
                modifierTempExclRow.setAttribute("Uom", uom);
                modifierTempExclRow.setAttribute("ValueFrom",
                                             valueFrom);
                modifierTempExclRow.setAttribute("ValueTo",
                                             valueTo);
                modifierTempExclRow.setAttribute("ApplicationMethod",
                                             applicationMethod);
                modifierTempExclRow.setAttribute("Value", value);
                modifierTempExclRow.setAttribute("GroupingNo",
                                             groupingNo);
                modifierTempExclRow.setAttribute("QualifierContext",
                                             qualifierContext);
                modifierTempExclRow.setAttribute("QualifierAttr",
                                             qualifierAttr);
                modifierTempExclRow.setAttribute("Attribute2",
                                             valueConfirmNoDef);
                modifierTempExclRow.setAttribute("Operator1",
                                             operatorSign);
                modifierTempExclRow.setAttribute("QValueFrom",
                                             valueQualifier);
                modifierTempExclRow.setAttribute("Flag", "N");
                modifierTempExclRow.setAttribute("ListHeaderId",
                                             headerIdNum);
                modifierTempExclRow.setAttribute("LineNo", parentLine);
                modifierTempExclRow.setAttribute("CreatedBy",
                                             userName);
            }
        }
    }
    
    public void createModifierAreaExcl(ConfirmationAMImpl confirmationAM, Number promoProdId, 
                                     Number headerIdNum, Number parentLine, String userName, String mfOntop) {
        // Fetch Modifier Exclude Area
        FcsModifierAreaExclViewImpl voModifierAreaExcl =
            confirmationAM.getFcsModifierAreaExclView1();
        voModifierAreaExcl.setNamedWhereClauseParam("promoProdId",
                                                promoProdId);
        voModifierAreaExcl.setNamedWhereClauseParam("ketMfOntop",
                                              mfOntop);
        voModifierAreaExcl.executeQuery();

        if (voModifierAreaExcl.getEstimatedRowCount() > 0) {
            FcsQpModifierTempExclViewImpl voModifierTempExcl =
                (FcsQpModifierTempExclViewImpl)confirmationAM.getFcsQpModifierTempExclView1();
            
            while (voModifierAreaExcl.hasNext()) {
                Row modifierAreaExclRow = voModifierAreaExcl.next();
                String noConfirm =
                    (String)modifierAreaExclRow.getAttribute("NoConfirm");
                String currency =
                    (String)modifierAreaExclRow.getAttribute("Currency");
                Date startDate =
                    (Date)modifierAreaExclRow.getAttribute("StartDate");
                Date endDate =
                    (Date)modifierAreaExclRow.getAttribute("EndDate");
                String active =
                    (String)modifierAreaExclRow.getAttribute("Active");
                String automatic =
                    (String)modifierAreaExclRow.getAttribute("Automatic");
                String lineLevel =
                    (String)modifierAreaExclRow.getAttribute("LineLevel");
                String lineType =
                    (String)modifierAreaExclRow.getAttribute("LineType");
                Date startDateLine =
                    (Date)modifierAreaExclRow.getAttribute("StartDateLine");
                Date endDateLine =
                    (Date)modifierAreaExclRow.getAttribute("EndDateLine");
                String automaticLine =
                    (String)modifierAreaExclRow.getAttribute("AutomaticLine");
                String pricingPhase =
                    (String)modifierAreaExclRow.getAttribute("PricingPhase");
                String productAttribute =
                    (String)modifierAreaExclRow.getAttribute("ProductAttribute");
                String productValue =
                    (String)modifierAreaExclRow.getAttribute("ProductValue");
                String volumeType =
                    (String)modifierAreaExclRow.getAttribute("VolumeType");
                String breakType =
                    (String)modifierAreaExclRow.getAttribute("BreakType");
                String uom =
                    (String)modifierAreaExclRow.getAttribute("Uom");
                Number valueFrom =
                    (Number)modifierAreaExclRow.getAttribute("ValueFrom");
                Number valueTo =
                    (Number)modifierAreaExclRow.getAttribute("ValueTo");
                String applicationMethod =
                    (String)modifierAreaExclRow.getAttribute("ApplicationMethod");
                Number value =
                    (Number)modifierAreaExclRow.getAttribute("Value");
                Number groupingNo =
                    (Number)modifierAreaExclRow.getAttribute("GroupingNo");
                String qualifierContext =
                    (String)modifierAreaExclRow.getAttribute("QualifierContext");
                String qualifierAttr =
                    (String)modifierAreaExclRow.getAttribute("QualifierAttr");
                String operatorSign =
                    (String)modifierAreaExclRow.getAttribute("OperatorSign");
                String valueQualifier =
                    (String)modifierAreaExclRow.getAttribute("ValueQualifier");
                String valueConfirmNoDef =
                    (String)modifierAreaExclRow.getAttribute("ConfirmNoDef");

                Row modifierTempExclRow =
                    voModifierTempExcl.createRow();
                modifierTempExclRow.setAttribute("ModifierType",
                                             "Discount List");
                modifierTempExclRow.setAttribute("Name",
                                             noConfirm);
                modifierTempExclRow.setAttribute("Description",
                                             noConfirm);
                modifierTempExclRow.setAttribute("Currency",
                                             currency);
                modifierTempExclRow.setAttribute("StartDate",
                                             startDate);
                modifierTempExclRow.setAttribute("EndDate",
                                             endDate);
                modifierTempExclRow.setAttribute("Active", active);
                modifierTempExclRow.setAttribute("Automatic",
                                             automatic);
                modifierTempExclRow.setAttribute("Version", null);
                modifierTempExclRow.setAttribute("LineLevel",
                                             lineLevel);
                modifierTempExclRow.setAttribute("LineType",
                                             lineType);
                modifierTempExclRow.setAttribute("StartDateLine",
                                             startDateLine);
                modifierTempExclRow.setAttribute("EndDateLine",
                                             endDateLine);
                modifierTempExclRow.setAttribute("AutomaticLine",
                                             automaticLine);
                modifierTempExclRow.setAttribute("PricingPhase",
                                             pricingPhase);
                modifierTempExclRow.setAttribute("ProductAttribute",
                                             productAttribute);
                modifierTempExclRow.setAttribute("ProductAttributeValue",
                                             productValue);
                modifierTempExclRow.setAttribute("VolumeType",
                                             volumeType);
                modifierTempExclRow.setAttribute("BreakType",
                                             breakType);
                modifierTempExclRow.setAttribute("Uom", uom);
                modifierTempExclRow.setAttribute("ValueFrom",
                                             valueFrom);
                modifierTempExclRow.setAttribute("ValueTo",
                                             valueTo);
                modifierTempExclRow.setAttribute("ApplicationMethod",
                                             applicationMethod);
                modifierTempExclRow.setAttribute("Value", value);
                modifierTempExclRow.setAttribute("GroupingNo",
                                             groupingNo);
                modifierTempExclRow.setAttribute("QualifierContext",
                                             qualifierContext);
                modifierTempExclRow.setAttribute("QualifierAttr",
                                             qualifierAttr);
                modifierTempExclRow.setAttribute("Attribute2",
                                             valueConfirmNoDef);
                modifierTempExclRow.setAttribute("Operator1",
                                             operatorSign);
                modifierTempExclRow.setAttribute("QValueFrom",
                                             valueQualifier);
                modifierTempExclRow.setAttribute("Flag", "N");
                modifierTempExclRow.setAttribute("ListHeaderId",
                                             headerIdNum);
                modifierTempExclRow.setAttribute("LineNo", parentLine);
                modifierTempExclRow.setAttribute("CreatedBy",
                                             userName);
            }
        }
    }

    public void saveConfirm(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        

            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();        
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblPropConfirmation);
        /*    
        } else {
            JSFUtils.addFacesWarningMessage("Category PC harus diisi terlebih dahulu.");
        }
        */
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblPropConfirmation);
    }

    public void setSwitchMain(UIXSwitcher switchMain) {
        this.switchMain = switchMain;
    }

    public UIXSwitcher getSwitchMain() {
        return switchMain;
    }

    public void categoryPcReturnPopupListener(ReturnPopupEvent returnPopupEvent) {
        BindingContainer bindings = getBindings();
        
        ConfirmationAMImpl confirmationAM =
            (ConfirmationAMImpl)ADFUtils.getApplicationModuleForDataControl("ConfirmationAMDataControl");

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
        
        AttributeBinding confirmNoAttr =
            (AttributeBinding)bindings.getControlBinding("ConfirmNo");
        
        String confirmNo = (String)confirmNoAttr.getInputValue() == null ? "" : (String)confirmNoAttr.getInputValue();   
        
        String existingCatPc = confirmNo.substring(0, Math.min(confirmNo.length(), 3));        
        String categoryPc = (String)rw.getAttribute("Value") == null ? "---" : (String)rw.getAttribute("Value");

        if (!categoryPc.equalsIgnoreCase("---")) {
            if (!existingCatPc.equalsIgnoreCase(categoryPc)) {
                // Get tahun 2 digit (dari tahun Start Program Promo Date)
                AttributeBinding periodeProgFromAttr =
                    (AttributeBinding)bindings.getControlBinding("PeriodeProgFrom");
                String periodeProgFrom = (String)periodeProgFromAttr.getInputValue();
        
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                java.util.Date date = null;
                try {
                    date = formatter.parse(periodeProgFrom);
                } catch (ParseException e) {
                    JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
                }      
                
                DateFormat dfYY = new SimpleDateFormat("yy");
                String currYear = dfYY.format(date);
                Number numYear = new Number(Integer.parseInt(currYear));
                    
                // Get Running Number
                Number runNumber;
                RunNumConfViewImpl runNumVo = confirmationAM.getRunNumConfView1();
                runNumVo.setNamedWhereClauseParam("userName", categoryPc);
                runNumVo.setNamedWhereClauseParam("runYear", numYear);
                runNumVo.setNamedWhereClauseParam("runMonth", 0);
                runNumVo.executeQuery();
        
                if (runNumVo.getAllRowsInRange().length > 0) {
                    runNumConf = runNumVo.first();
                    runNumber = (Number)runNumConf.getAttribute("Value");
                    runNumConf.setAttribute("Value", runNumber.add(1));
                } else {
                    Row newRow = runNumVo.createRow();
                    newRow.setAttribute("RunNumType", "CONF");
                    newRow.setAttribute("UserName", categoryPc);
                    newRow.setAttribute("RunYear", numYear);
                    newRow.setAttribute("RunMonth", 0);
                    newRow.setAttribute("Value", 2);
                    runNumVo.insertRow(newRow);
                    runNumber = new Number(1);
                }
        
                // Generate Confirmation Number
                String runNumberFormatted =
                    String.format("%06d", runNumber.getBigDecimalValue().intValue());
                String confNumber = categoryPc + numYear + runNumberFormatted;
                itConfirmNo.setValue(confNumber);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itConfirmNo);
            }
        }
    }

    public void tableFilterProcessQuery(QueryEvent queryEvent) {
        FilterableQueryDescriptor fqd = (FilterableQueryDescriptor) queryEvent.getDescriptor();
        ConjunctionCriterion conjunctionCriterion = fqd.getConjunctionCriterion();
        List<Criterion> criterionList = conjunctionCriterion.getCriterionList();
        for (Criterion criterion : criterionList) {
            AttributeDescriptor attrDescriptor = ((AttributeCriterion) criterion).getAttribute();
            Object value = ((AttributeCriterion) criterion).getValues();
            /*
            if (attrDescriptor.getType().equals(String.class)) {
                if (value != null) {
                    ((AttributeCriterion) criterion).setValue("%" + value + "%");
                }
            }
            */
        }

        //Execute query
        ADFUtils.invokeEL("#{bindings.ProposalConfirmationView11Query.processQuery}",
                          new Class[] { QueryEvent.class },
                          new Object[] { queryEvent });
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
    }
}
