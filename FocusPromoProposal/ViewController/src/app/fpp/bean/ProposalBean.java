package app.fpp.bean;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.model.am.PromoProposalAMImpl;
import app.fpp.model.views.approval.ApprovalReceiverNewProposalViewImpl;
import app.fpp.model.views.masterdata.ebs.FcsViewCategCombinationViewImpl;
import app.fpp.model.views.promoproposal.PromoProdukViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateBiayaViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateDiscountViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionAreaViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionCustGroupViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionCustomerViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionLocViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdukItemViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdukVariantViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicatePromoAddBuyVariantViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicatePromoAddBuyViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicatePromoBonusVariantViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicatePromoBonusViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateTargetViewImpl;
import app.fpp.model.views.promoproposal.validation.ProdVariantValidationViewImpl;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.component.UISelectItems;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.ItemEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.adf.view.rich.model.CollectionModel;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.LaunchEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;

public class ProposalBean {
    private String userCompany;
    private String contactNo;
    private RichInputText userCompanyId;
    private RichInputText contactPemohon;
    private RichInputText docStatus;
    private static String docStatusInprogress = "INPROCESS";
    private static String docStatusDraft = "DRAFT";
    private RichTable tblListProduct;
    private RichInputListOfValues itlovProdCode;
    private RichInputText itProdDescr;
    private RichPanelGroupLayout panCollMain;
    private RichTable tblListRegion;
    private RichTable tblListCustomer;
    private RichCommandButton btnCancel;
    private RichCommandButton btnSave;
    private RichCommandButton btnSubmit;
    private RichInputListOfValues itlovProdClass;
    private RichInputListOfValues itlovProdBrand;
    private RichInputListOfValues itlovProdExtention;
    private RichInputListOfValues itlovProdPackaging;
    private RichInputListOfValues itlovProdClassAddBuy;
    private RichInputListOfValues itlovProdBrandAddBuy;
    private RichInputListOfValues itlovProdExtAddBuy;
    private RichInputListOfValues itlovProdPackAddBuy;
    private RichInputListOfValues itlovProdClassBonus;
    private RichInputListOfValues itlovProdBrandBonus;
    private RichInputListOfValues itlovProdExtBonus;
    private RichInputListOfValues itlovProdPackBonus;
    private RichTable tblListProductBonus;
    private RichTable tblListProductAddBuy;
    private RichInputText itTargetPercentage;
    private RichInputText itTargetValue;
    private RichInputText itTargetQty;
    private RichInputText itTargetHarga;
    private RichInputText itValueTotal;
    private UIXSwitcher switchRegCust;
    private RichTable tblListProductRegion;
    private RichTable tblListProductCustomer;
    private RichTable tblListProductArea;
    private static String propArea = "AREA";
    private static String propCustomer = "CUSTOMER";
    private static String propRegion = "REGION";
    private static String propLocation = "LOCATION";
    private static String propCustGroup = "CUSTGROUP";
    private static String prodArea = "AREA";
    private static String prodCustomer = "CUSTOMER";
    private static String prodRegion = "REGION";
    private static String prodLocation = "LOCATION";
    private static String prodCustGroup = "CUSTGROUP";
    private static String userHo = "HO";
    private static String userArea = "AREA";
    private RichSelectOneChoice copyAsFlag;
    private String promoProdIdvar;
    private String promoAddBuyIdvar;
    private String promoBonusIdvar;
    private RichSelectOneChoice socMekPenagihan;
    private RichTable tblListProposal;
    private RichPopup pdetailProduct;
    private RichPopup potmessage;
    private RichOutputText otpesan;
    private RichShowDetailItem tabTargetBtn;
    private RichShowDetailItem tabTargerCustomer;
    private RichShowDetailItem tabBiaya;
    private String BiayaNonYearly;
    private String BiayaYearly;
    private RichTable tblListProductLocation;
    private RichTable tblListLocation;
    private RichTable tblListArea;
    private String UserTypeCreator;
    private RichShowDetailItem tabPromoBarang;
    private RichShowDetailItem tabPotongan;
    private RichShowDetailItem tabTargetAndBudget;
    private RichInputText itVariant;
    private RichCommandImageLink linkVariant;
    private RichPopup psubmitProposal;
    private static String discTypePotongan = "POTONGAN";
    private static String discTypeBiaya = "BIAYA";
    private static String discTypePromoBarang = "PROMOBARANG";
    private RichCommandImageLink linkProduct;
    private RichTable tblListCustGroup;
    private RichTable tblListProductCustGroup;
    private RichInputText itVariantAddBuy;
    private RichInputText itProdukAddBuy;
    private RichInputText itBonusVariant;
    private RichSelectOneChoice itLovProposalType;
    private UISelectItems selectItSocProposalType;
    private RichSelectOneChoice customLovproposalType;
    private ArrayList listProposalType = new ArrayList();
    private RichInputListOfValues itLovProdCategory;
    private RichOutputText itCategory;
    private RichSelectOneChoice socLovDiv;
    private RichInputText avgQty;
    private DecimalFormat df = new DecimalFormat("###");
    private DecimalFormat df2dgt = new DecimalFormat("###.##");
    private static String variantAll = "ALL";
    private RichInputListOfValues itlovExclCustBy;
    private RichTable tblListExclRegion;
    private RichTable tblListExclArea;
    private RichTable tblListExclCustomer;
    private RichTable tblListExclLocation;
    private RichTable tblListExclProductRegion;
    private RichTable tblListExclProductArea;
    private RichTable tblListExclProductLoc;
    private RichTable tblListExclProductCust;
    private UIXSwitcher switchExclCust;
    private RichPanelSplitter pgMainDetail;
    private Integer idxFood = 0;
    private Integer idxNonFood = 1;
    private String prodCatCodeFood = "CT001";
    private String prodCatCodeNonFood = "CT002";
    private String userDivision;
    private RichTable tableListPotongan;

    public ProposalBean() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserCompany() {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrCompId = userData.getCompanyId();
        userCompanyId.setValue(usrCompId);
        return userCompany;
    }

    public void setUserCompanyId(RichInputText userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public RichInputText getUserCompanyId() {
        return userCompanyId;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactNo() {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String contactNum = userData.getContactNo();
        contactPemohon.setValue(contactNum);
        return contactNo;
    }

    public void setContactPemohon(RichInputText contactPemohon) {
        this.contactPemohon = contactPemohon;
    }

    public RichInputText getContactPemohon() {
        return contactPemohon;
    }

    public void submitProposal(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        //Save all changed values
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        if (dialogEvent.getOutcome().name().equals("ok")) {

            // Validasi combination sudah valid atau belum
            boolean validCombination = true;
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            for (Row promoProdRow : dciterPromoProduk.getAllRowsInRange()) {
                String kodeCombination =
                    (String)promoProdRow.getAttribute("ValidComb");
                if (kodeCombination.equalsIgnoreCase("N")) {
                    validCombination = false;
                }
            }

            if (validCombination) {
                // Validate product category combination
                PromoProposalAMImpl promoProposalAM =
                    (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");

                UserData userData =
                    (UserData)JSFUtils.resolveExpression("#{UserData}");
                String userType = userData.getUserType();
                String usrRole = userData.getUserRole();                
                Integer propTypeIdx = (Integer)itLovProposalType.getValue();
                String aprvlFlowNm = "";
                
                if (propTypeIdx.compareTo(idxFood) == 0 && userType.equalsIgnoreCase("HO")) {
                    aprvlFlowNm = "PROPOSAL FOOD HO";
                } else if (propTypeIdx.compareTo(idxFood) == 0 && userType.equalsIgnoreCase("AREA")) {
                    aprvlFlowNm = "PROPOSAL FOOD";
                } else if (propTypeIdx.compareTo(idxNonFood) == 0 && userType.equalsIgnoreCase("HO")) {
                    aprvlFlowNm = "PROPOSAL NON FOOD HO";
                } else if (propTypeIdx.compareTo(idxNonFood) == 0 && userType.equalsIgnoreCase("AREA")) {
                    aprvlFlowNm = "PROPOSAL NON FOOD";
                } else {
                    System.out.println("ERROR: Approval flow name not recognized.");
                }
                
                ApprovalReceiverNewProposalViewImpl proposalReceiver =
                    promoProposalAM.getApprovalReceiverNewProposalView1();
                proposalReceiver.setNamedWhereClauseParam("aprvlFlowNm", aprvlFlowNm);
                proposalReceiver.setNamedWhereClauseParam("usrRole", usrRole);
                proposalReceiver.executeQuery();

                if (proposalReceiver.getEstimatedRowCount() > 0) {
                    try {
                        OperationBinding operationAddApproval =
                            bindings.getOperationBinding("addDocApproval");
                        operationAddApproval.execute();
                    } catch (JboException e) {
                        JSFUtils.addFacesErrorMessage("Error", e.getBaseMessage());
                    }
    
                    /*
                    JSFUtils.addFacesInformationMessage("Promo proposal dengan no \"" +
                                                    proposalNo +
                                                    "\" telah disubmit.");
                    */
    
                    //Save all changed values
                    OperationBinding operationCommitSubmit =
                        bindings.getOperationBinding("Commit");
                    operationCommitSubmit.execute();
    
                    OperationBinding operationBinding =
                        bindings.getOperationBinding("ExecutePromoProduct");
                    operationBinding.execute();
    
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panCollMain);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnCancel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnSave);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnSubmit);
                } else {                
                    JSFUtils.addFacesErrorMessage("Error",
                                              "Role penerima pada flow approval tidak ditemukan.");                    
                }
            } else {
                JSFUtils.addFacesErrorMessage("Error",
                                              "Kombinasi category pada daftar produk ada yang tidak valid.");
            }
            psubmitProposal.hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(psubmitProposal);
        }
    }

    public void setDocStatus(RichInputText docStatus) {
        this.docStatus = docStatus;
    }

    public RichInputText getDocStatus() {
        return docStatus;
    }

    public void submitProposalPopup(PopupFetchEvent popupFetchEvent) {
        // Validasi combination sudah valid atau belum
        boolean validCombination = true;
        DCIteratorBinding dciterPromoProduk =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        for (Row promoProdRow : dciterPromoProduk.getAllRowsInRange()) {
            String kodeCombination =
                (String)promoProdRow.getAttribute("ValidComb");
            if (kodeCombination.equalsIgnoreCase("N")) {
                validCombination = false;
            }
        }

        if (validCombination) {
            docStatus.setValue(docStatusInprogress);
            linkVariant.setDisabled(true);
            linkProduct.setDisabled(true);
        } else {
            docStatus.setValue(docStatusDraft);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(linkVariant);
        AdfFacesContext.getCurrentInstance().addPartialTarget(linkProduct);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docStatus);
    }

    public void addProductPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsertPromoProduk");
        operationBinding.execute();
    }

    public void setTblListProduct(RichTable tblListProduct) {
        this.tblListProduct = tblListProduct;
    }

    public RichTable getTblListProduct() {
        return tblListProduct;
    }

    public void addProductPopupCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBindingRollback =
            bindings.getOperationBinding("removeCanceledRow");
        operationBindingRollback.execute();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void socProdTypeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        itlovProdCode.setValue(null);
        itProdDescr.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itlovProdCode);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itProdDescr);
    }

    public void setItlovProdCode(RichInputListOfValues itlovProdCode) {
        this.itlovProdCode = itlovProdCode;
    }

    public RichInputListOfValues getItlovProdCode() {
        return itlovProdCode;
    }

    public void setItProdDescr(RichInputText itProdDescr) {
        this.itProdDescr = itProdDescr;
    }

    public RichInputText getItProdDescr() {
        return itProdDescr;
    }

    public void productItlovReturnPopupListener(ReturnPopupEvent returnPopupEvent) {
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        JUCtrlListBinding list =
            (JUCtrlListBinding)bindings.get("ProductCode");
        String selectedValue = (String)list.getAttributeValue();
        list.getListIterBinding().setCurrentRowWithKeyValue(selectedValue);
        Row currRow = list.getListIterBinding().getCurrentRow();
        String prodName = (String)currRow.getAttribute("ProductName");
        itProdDescr.setValue(prodName);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itProdDescr);
    }

    public void setPanCollMain(RichPanelGroupLayout panCollMain) {
        this.panCollMain = panCollMain;
    }

    public RichPanelGroupLayout getPanCollMain() {
        return panCollMain;
    }

    public void detailProdDialogListener(DialogEvent dialogEvent) {
        // MOVED TO EVENT OK
    }

    public void showPopup(String pesan, RichPopup p) {
        otpesan.setValue(pesan);
        AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
        adc.addPartialTarget(otpesan);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        p.show(hints);
    }

    public void detailProdPopupFetchListener(PopupFetchEvent popupFetchEvent) {

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");

        if (dciterTarget.getEstimatedRowCount() == 0) {
            OperationBinding operationCreateInsertDiscount =
                bindings.getOperationBinding("CreateInsertTarget");
            operationCreateInsertDiscount.execute();
        }

    }

    public void windowVariantReturnListener(ReturnEvent returnEvent) {

        DCIteratorBinding dciterProdVariant =
            ADFUtils.findIterator("ProdukVariantView1Iterator");
        if (dciterProdVariant.getEstimatedRowCount() == 1) {
            RowSetIterator rsiProdVariant =
                dciterProdVariant.getRowSetIterator();
            Row prodVariantRow = rsiProdVariant.first();
            if (!prodVariantRow.getAttribute("ProdVariant").toString().equalsIgnoreCase("ALL")) {
                DCIteratorBinding dciterProdItem =
                    ADFUtils.findIterator("ProdukItemView1Iterator");
                RowSetIterator rsiProdItem =
                    dciterProdItem.getRowSetIterator();
                for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                    prodItemRow.remove();
                }
                rsiProdItem.closeRowSetIterator();
            }
        } else {
            DCIteratorBinding dciterProdItem =
                ADFUtils.findIterator("ProdukItemView1Iterator");
            RowSetIterator rsiProdItem = dciterProdItem.getRowSetIterator();
            for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                prodItemRow.remove();
            }
            rsiProdItem.closeRowSetIterator();
        }

        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void windowItemReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void windowAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteArea");
        operationBinding.execute();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        DCIteratorBinding dciterArea =
            ADFUtils.findIterator("PropRegionAreaView1Iterator");
        for (Row r : dciterArea.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            if (!regAprvlNewList.contains(regCode)) {
                regAprvlNewList.add(regCode);
            }
        }

        ArrayList<String> regAprvlUnionList =
            new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }

        for (String regCode : regAprvlUnionList) {
            if (regAprvlOldList.contains(regCode) &&
                !regAprvlNewList.contains(regCode)) {
                Key keyRegCode =
                    new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg =
                    dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion = rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) &&
                       regAprvlNewList.contains(regCode)) {
                Row rowApprovalRegion =
                    dciterApprovalRegion.getRowSetIterator().createRow();
                rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                rowApprovalRegion.setAttribute("ProposalId",
                                               propId.getValue());
                rowApprovalRegion.setAttribute("RegionCode", regCode);

                dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
            }
        }

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);
    }

    public void windowRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteRegion");
        operationBinding.execute();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        DCIteratorBinding dciterRegion =
            ADFUtils.findIterator("PropRegionView1Iterator");
        for (Row r : dciterRegion.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            if (!regAprvlNewList.contains(regCode)) {
                regAprvlNewList.add(regCode);
            }
        }

        ArrayList<String> regAprvlUnionList =
            new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }

        for (String regCode : regAprvlUnionList) {
            if (regAprvlOldList.contains(regCode) &&
                !regAprvlNewList.contains(regCode)) {
                Key keyRegCode =
                    new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg =
                    dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion = rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) &&
                       regAprvlNewList.contains(regCode)) {
                Row rowApprovalRegion =
                    dciterApprovalRegion.getRowSetIterator().createRow();
                rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                rowApprovalRegion.setAttribute("ProposalId",
                                               propId.getValue());
                rowApprovalRegion.setAttribute("RegionCode", regCode);

                dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
            }
        }

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);
    }

    public void windowCustomerReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteCustomer");
        operationBinding.execute();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        DCIteratorBinding dciterCustomer =
            ADFUtils.findIterator("PropRegionCustomerView1Iterator");
        for (Row r : dciterCustomer.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            if (!regAprvlNewList.contains(regCode)) {
                regAprvlNewList.add(regCode);
            }
        }

        ArrayList<String> regAprvlUnionList =
            new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }

        for (String regCode : regAprvlUnionList) {
            if (regAprvlOldList.contains(regCode) &&
                !regAprvlNewList.contains(regCode)) {
                Key keyRegCode =
                    new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg =
                    dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion = rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) &&
                       regAprvlNewList.contains(regCode)) {
                Row rowApprovalRegion =
                    dciterApprovalRegion.getRowSetIterator().createRow();
                rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                rowApprovalRegion.setAttribute("ProposalId",
                                               propId.getValue());
                rowApprovalRegion.setAttribute("RegionCode", regCode);

                dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
            }
        }
        
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);
    }

    public void windowLocationReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteLocation");
        operationBinding.execute();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        DCIteratorBinding dciterLocation =
            ADFUtils.findIterator("PropRegionLocView1Iterator");
        for (Row r : dciterLocation.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            if (!regAprvlNewList.contains(regCode)) {
                regAprvlNewList.add(regCode);
            }
        }

        ArrayList<String> regAprvlUnionList =
            new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }

        for (String regCode : regAprvlUnionList) {
            if (regAprvlOldList.contains(regCode) &&
                !regAprvlNewList.contains(regCode)) {
                Key keyRegCode =
                    new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg =
                    dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion = rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) &&
                       regAprvlNewList.contains(regCode)) {
                Row rowApprovalRegion =
                    dciterApprovalRegion.getRowSetIterator().createRow();
                rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                rowApprovalRegion.setAttribute("ProposalId",
                                               propId.getValue());
                rowApprovalRegion.setAttribute("RegionCode", regCode);

                dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
            }
        }
        
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);
    }

    public void windowCustGroupReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteCustGroup");
        operationBinding.execute();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        DCIteratorBinding dciterLocation =
            ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
        for (Row r : dciterLocation.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            String[] arrayRegCode = regCode.split("\\|", -1);
            for (int i = 0; i < arrayRegCode.length; i++) {
                if (!regAprvlNewList.contains(arrayRegCode[i])) {
                    regAprvlNewList.add(arrayRegCode[i]);
                }
            }
        }

        ArrayList<String> regAprvlUnionList =
            new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }

        for (String regCode : regAprvlUnionList) {
            if (regAprvlOldList.contains(regCode) &&
                !regAprvlNewList.contains(regCode)) {
                Key keyRegCode =
                    new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg =
                    dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion = rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) &&
                       regAprvlNewList.contains(regCode)) {
                Row rowApprovalRegion =
                    dciterApprovalRegion.getRowSetIterator().createRow();
                rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                rowApprovalRegion.setAttribute("ProposalId",
                                               propId.getValue());
                rowApprovalRegion.setAttribute("RegionCode", regCode);

                dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
            }
        }
        
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);
    }

    public void setTblListRegion(RichTable tblListRegion) {
        this.tblListRegion = tblListRegion;
    }

    public RichTable getTblListRegion() {
        return tblListRegion;
    }

    public void setTblListCustomer(RichTable tblListCustomer) {
        this.tblListCustomer = tblListCustomer;
    }

    public RichTable getTblListCustomer() {
        return tblListCustomer;
    }

    public void changeRegionCustomer(ValueChangeEvent valueChangeEvent) {
        String valueChange = valueChangeEvent.getNewValue().toString();
        if (valueChange.equalsIgnoreCase(propRegion)) {
            DCIteratorBinding dciterCust =
                ADFUtils.findIterator("PropRegionCustomerView1Iterator");
            for (Row r : dciterCust.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);

            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("PropRegionLocView1Iterator");
            for (Row r : dciterLoc.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("PropRegionAreaView1Iterator");
            for (Row r : dciterArea.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
            for (Row r : dciterCustGroup.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);

        } else if (valueChange.equalsIgnoreCase(propCustomer)) {
            DCIteratorBinding dciterReg =
                ADFUtils.findIterator("PropRegionView1Iterator");
            for (Row r : dciterReg.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);

            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("PropRegionLocView1Iterator");
            for (Row r : dciterLoc.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("PropRegionAreaView1Iterator");
            for (Row r : dciterArea.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
            for (Row r : dciterCustGroup.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);

        } else if (valueChange.equalsIgnoreCase(propLocation)) {
            DCIteratorBinding dciterReg =
                ADFUtils.findIterator("PropRegionView1Iterator");
            for (Row r : dciterReg.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);

            DCIteratorBinding dciterCust =
                ADFUtils.findIterator("PropRegionCustomerView1Iterator");
            for (Row r : dciterCust.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("PropRegionAreaView1Iterator");
            for (Row r : dciterArea.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
            for (Row r : dciterCustGroup.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);

        } else if (valueChange.equalsIgnoreCase(propArea)) {
            DCIteratorBinding dciterReg =
                ADFUtils.findIterator("PropRegionView1Iterator");
            for (Row r : dciterReg.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);

            DCIteratorBinding dciterCust =
                ADFUtils.findIterator("PropRegionCustomerView1Iterator");
            for (Row r : dciterCust.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);

            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("PropRegionLocView1Iterator");
            for (Row r : dciterLoc.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
            for (Row r : dciterCustGroup.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);

        } else if (valueChange.equalsIgnoreCase(propCustGroup)) {
            DCIteratorBinding dciterReg =
                ADFUtils.findIterator("PropRegionView1Iterator");
            for (Row r : dciterReg.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);

            DCIteratorBinding dciterCust =
                ADFUtils.findIterator("PropRegionCustomerView1Iterator");
            for (Row r : dciterCust.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);

            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("PropRegionLocView1Iterator");
            for (Row r : dciterLoc.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("PropRegionAreaView1Iterator");
            for (Row r : dciterArea.getAllRowsInRange()) {
                r.remove();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);
        } else {
            System.out.println("NO CHANGE ON PROP CUSTOMER");
            JSFUtils.addFacesErrorMessage("Error",
                                          "Customer option area not recognized");
        }
    }

    public void setBtnCancel(RichCommandButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public RichCommandButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnSave(RichCommandButton btnSave) {
        this.btnSave = btnSave;
    }

    public RichCommandButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSubmit(RichCommandButton btnSubmit) {
        this.btnSubmit = btnSubmit;
    }

    public RichCommandButton getBtnSubmit() {
        return btnSubmit;
    }

    public void productCategoryChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdClass().setValue(null);
            this.getItlovProdBrand().setValue(null);
            this.getItlovProdExtention().setValue(null);
            this.getItlovProdPackaging().setValue(null);
        }
        clearVariantItem();
    }

    public void productClassChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdBrand().setValue(null);
            this.getItlovProdExtention().setValue(null);
            this.getItlovProdPackaging().setValue(null);

        }
        if (itlovProdClass.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoProdRow = voTableData.getCurrentRow();
            String promoProdukId =
                ((DBSequence)promoProdRow.getAttribute("PromoProdukId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", promoProdukId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            itlovProdBrand.setValue("ALL");
            itlovProdExtention.setValue("ALL");
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdBrand);
            ctx.addPartialTarget(itlovProdExtention);
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(tblListProduct);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
        //        clearVariantItem();
    }


    public Row getTableRow(String iterator) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding(iterator);
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        return rowSelected;
    }

    public void productBrandChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdExtention().setValue(null);
            this.getItlovProdPackaging().setValue(null);
        }
        if (itlovProdBrand.getValue().toString().equalsIgnoreCase("ALL")) {

            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoProdRow = voTableData.getCurrentRow();
            String promoProdukId =
                ((DBSequence)promoProdRow.getAttribute("PromoProdukId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", promoProdukId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            itlovProdExtention.setValue("ALL");
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdExtention);
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(tblListProduct);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
        //        clearVariantItem();

    }

    public void productExtentionChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdPackaging().setValue(null);
        }
        if (itlovProdExtention.getValue().toString().equalsIgnoreCase("ALL")) {

            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoProdRow = voTableData.getCurrentRow();
            String promoProdukId =
                ((DBSequence)promoProdRow.getAttribute("PromoProdukId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", promoProdukId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(tblListProduct);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
        //        clearVariantItem();

    }

    public void productPackagingChanged(ValueChangeEvent valueChangeEvent) {
        if (itlovProdPackaging.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoProdRow = voTableData.getCurrentRow();
            String promoProdukId =
                ((DBSequence)promoProdRow.getAttribute("PromoProdukId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", promoProdukId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(tblListProduct);
        } else {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(tblListProduct);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();

        //        clearVariantItem();
        linkVariant.setDisabled(false);
        linkProduct.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(linkVariant);
        AdfFacesContext.getCurrentInstance().addPartialTarget(linkProduct);
    }

    public void clearVariantItem() {
        DCIteratorBinding dciterVariant =
            ADFUtils.findIterator("ProdukVariantView1Iterator");
        RowSetIterator rsiVariant = dciterVariant.getRowSetIterator();
        for (Row variantRow : dciterVariant.getAllRowsInRange()) {
            variantRow.remove();
        }
        rsiVariant.closeRowSetIterator();

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
    }

    public void setItlovProdClass(RichInputListOfValues itlovProdClass) {
        this.itlovProdClass = itlovProdClass;
    }

    public RichInputListOfValues getItlovProdClass() {
        return itlovProdClass;
    }

    public void setItlovProdBrand(RichInputListOfValues itlovProdBrand) {
        this.itlovProdBrand = itlovProdBrand;
    }

    public RichInputListOfValues getItlovProdBrand() {
        return itlovProdBrand;
    }

    public void setItlovProdExtention(RichInputListOfValues itlovProdExtention) {
        this.itlovProdExtention = itlovProdExtention;
    }

    public RichInputListOfValues getItlovProdExtention() {
        return itlovProdExtention;
    }

    public void setItlovProdPackaging(RichInputListOfValues itlovProdPackaging) {
        this.itlovProdPackaging = itlovProdPackaging;
    }

    public RichInputListOfValues getItlovProdPackaging() {
        return itlovProdPackaging;
    }

    public void setItlovProdClassAddBuy(RichInputListOfValues itlovProdClassAddBuy) {
        this.itlovProdClassAddBuy = itlovProdClassAddBuy;
    }

    public RichInputListOfValues getItlovProdClassAddBuy() {
        return itlovProdClassAddBuy;
    }

    public void setItlovProdBrandAddBuy(RichInputListOfValues itlovProdBrandAddBuy) {
        this.itlovProdBrandAddBuy = itlovProdBrandAddBuy;
    }

    public RichInputListOfValues getItlovProdBrandAddBuy() {
        return itlovProdBrandAddBuy;
    }

    public void setItlovProdExtAddBuy(RichInputListOfValues itlovProdExtAddBuy) {
        this.itlovProdExtAddBuy = itlovProdExtAddBuy;
    }

    public RichInputListOfValues getItlovProdExtAddBuy() {
        return itlovProdExtAddBuy;
    }

    public void setItlovProdPackAddBuy(RichInputListOfValues itlovProdPackAddBuy) {
        this.itlovProdPackAddBuy = itlovProdPackAddBuy;
    }

    public RichInputListOfValues getItlovProdPackAddBuy() {
        return itlovProdPackAddBuy;
    }

    public void setItlovProdClassBonus(RichInputListOfValues itlovProdClassBonus) {
        this.itlovProdClassBonus = itlovProdClassBonus;
    }

    public RichInputListOfValues getItlovProdClassBonus() {
        return itlovProdClassBonus;
    }

    public void setItlovProdBrandBonus(RichInputListOfValues itlovProdBrandBonus) {
        this.itlovProdBrandBonus = itlovProdBrandBonus;
    }

    public RichInputListOfValues getItlovProdBrandBonus() {
        return itlovProdBrandBonus;
    }

    public void setItlovProdExtBonus(RichInputListOfValues itlovProdExtBonus) {
        this.itlovProdExtBonus = itlovProdExtBonus;
    }

    public RichInputListOfValues getItlovProdExtBonus() {
        return itlovProdExtBonus;
    }

    public void setItlovProdPackBonus(RichInputListOfValues itlovProdPackBonus) {
        this.itlovProdPackBonus = itlovProdPackBonus;
    }

    public RichInputListOfValues getItlovProdPackBonus() {
        return itlovProdPackBonus;
    }

    public void productCategoryAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdClassAddBuy().setValue(null);
            this.getItlovProdBrandAddBuy().setValue(null);
            this.getItlovProdExtAddBuy().setValue(null);
            this.getItlovProdPackAddBuy().setValue(null);
        }
    }

    public void productClassAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdBrandAddBuy().setValue(null);
            this.getItlovProdExtAddBuy().setValue(null);
            this.getItlovProdPackAddBuy().setValue(null);

        }
        if (itlovProdClassAddBuy.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("AddBuyVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 =
                bindings1.getOperationBinding("Commit");
            Object result1 = operationBinding1.execute();
            Row row = iter.getNavigatableRowIterator().createRow();

            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoAddBuyView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoAddbuyRow = voTableData.getCurrentRow();
            String PromoAddBuyId =
                ((DBSequence)promoAddbuyRow.getAttribute("PromoAddBuyId")).toString();

            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoAddBuyId", PromoAddBuyId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            iter.executeQuery();

            itlovProdBrandAddBuy.setValue("ALL");
            itlovProdExtAddBuy.setValue("ALL");
            itlovProdPackAddBuy.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdBrandAddBuy);
            ctx.addPartialTarget(itlovProdExtAddBuy);
            ctx.addPartialTarget(itlovProdPackAddBuy);
            ctx.addPartialTarget(itVariantAddBuy);
            ctx.addPartialTarget(tblListProductAddBuy);
        }
    }

    public void productBrandAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdExtAddBuy().setValue(null);
            this.getItlovProdPackAddBuy().setValue(null);

        }

        if (itlovProdBrandAddBuy.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("AddBuyVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoAddBuyView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoAddbuyRow = voTableData.getCurrentRow();
            String PromoAddBuyId =
                ((DBSequence)promoAddbuyRow.getAttribute("PromoAddBuyId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoAddBuyId", PromoAddBuyId);
            row.setAttribute("ProdVariant", "ALL");

            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();
            itlovProdExtAddBuy.setValue("ALL");
            itlovProdPackAddBuy.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdExtAddBuy);
            ctx.addPartialTarget(itlovProdPackAddBuy);
            ctx.addPartialTarget(itVariantAddBuy);
            ctx.addPartialTarget(tblListProductAddBuy);
        }
    }

    public void productExtentionAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdPackAddBuy().setValue(null);
        }
        if (itlovProdExtAddBuy.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("AddBuyVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoAddBuyView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoAddbuyRow = voTableData.getCurrentRow();
            String PromoAddBuyId =
                ((DBSequence)promoAddbuyRow.getAttribute("PromoAddBuyId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoAddBuyId", PromoAddBuyId);
            row.setAttribute("ProdVariant", "ALL");

            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();
            itlovProdPackAddBuy.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdPackAddBuy);
            ctx.addPartialTarget(itVariantAddBuy);
            ctx.addPartialTarget(tblListProductAddBuy);
        }
    }

    public void productPackAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            DCIteratorBinding dciterItem =
                ADFUtils.findIterator("AddBuyVariantView1Iterator");
            RowSetIterator rsiItem = dciterItem.getRowSetIterator();
            for (Row itemRow : dciterItem.getAllRowsInRange()) {
                itemRow.remove();
            }

            rsiItem.closeRowSetIterator();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoAddBuyView1Iterator");
            dciter.executeQuery();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itVariantAddBuy);
            ctx.addPartialTarget(tblListProductAddBuy);
        }
        if (itlovProdPackAddBuy.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("AddBuyVariantView1Iterator");
            if (iter.getEstimatedRowCount() > 0) {
                for (Row r : iter.getAllRowsInRange()) {
                    r.remove();
                }
            }
            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoAddBuyView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoAddbuyRow = voTableData.getCurrentRow();
            String PromoAddBuyId =
                ((DBSequence)promoAddbuyRow.getAttribute("PromoAddBuyId")).toString();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoAddBuyId", PromoAddBuyId);
            row.setAttribute("ProdVariant", "ALL");

            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itVariantAddBuy);
            ctx.addPartialTarget(tblListProductAddBuy);
        }
        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("AddBuyProdItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
    }

    public void clearBonusVariantItem() {
        DCIteratorBinding dciterVariant =
            ADFUtils.findIterator("PromoBonusVariantView1Iterator");
        RowSetIterator rsiVariant = dciterVariant.getRowSetIterator();
        for (Row variantRow : dciterVariant.getAllRowsInRange()) {
            variantRow.remove();
        }
        rsiVariant.closeRowSetIterator();

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            itemRow.remove();
        }
        rsiItem.closeRowSetIterator();
    }


    public void productCategoryBonusChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdClassBonus().setValue(null);
            this.getItlovProdBrandBonus().setValue(null);
            this.getItlovProdExtBonus().setValue(null);
            this.getItlovProdPackBonus().setValue(null);
        }
        clearBonusVariantItem();
    }

    public void productClassBonusChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdBrandBonus().setValue(null);
            this.getItlovProdExtBonus().setValue(null);
            this.getItlovProdPackBonus().setValue(null);
        }
        if (itlovProdClassBonus.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();

            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoBonusRow = voTableData.getCurrentRow();
            String PromoBonusId =
                ((DBSequence)promoBonusRow.getAttribute("PromoBonusId")).toString();

            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoBonusId", PromoBonusId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            itlovProdBrandBonus.setValue("ALL");
            itlovProdExtBonus.setValue("ALL");
            itlovProdPackBonus.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdBrandBonus);
            ctx.addPartialTarget(itlovProdExtBonus);
            ctx.addPartialTarget(itlovProdPackBonus);
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        } else {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            DCIteratorBinding dciterItem =
                ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
            RowSetIterator rsiItem = dciterItem.getRowSetIterator();
            for (Row itemRow : dciterItem.getAllRowsInRange()) {
                itemRow.remove();
            }
            rsiItem.closeRowSetIterator();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        }
    }

    public void productBrandBonusChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdExtBonus().setValue(null);
            this.getItlovProdPackBonus().setValue(null);
        }

        if (itlovProdBrandBonus.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();

            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoBonusRow = voTableData.getCurrentRow();
            String PromoBonusId =
                ((DBSequence)promoBonusRow.getAttribute("PromoBonusId")).toString();

            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoBonusId", PromoBonusId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            itlovProdExtBonus.setValue("ALL");
            itlovProdPackBonus.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();

            ctx.addPartialTarget(itlovProdExtBonus);
            ctx.addPartialTarget(itlovProdPackBonus);
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        } else {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            DCIteratorBinding dciterItem =
                ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
            RowSetIterator rsiItem = dciterItem.getRowSetIterator();
            for (Row itemRow : dciterItem.getAllRowsInRange()) {
                itemRow.remove();
            }
            rsiItem.closeRowSetIterator();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        }
    }

    public void productExtentionBonusChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdPackBonus().setValue(null);
        }
        if (itlovProdExtBonus.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }

            Row row = iter.getNavigatableRowIterator().createRow();

            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoBonusRow = voTableData.getCurrentRow();
            String PromoBonusId =
                ((DBSequence)promoBonusRow.getAttribute("PromoBonusId")).toString();

            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoBonusId", PromoBonusId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            itlovProdPackBonus.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdPackBonus);
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        } else {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            DCIteratorBinding dciterItem =
                ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
            RowSetIterator rsiItem = dciterItem.getRowSetIterator();
            for (Row itemRow : dciterItem.getAllRowsInRange()) {
                itemRow.remove();
            }
            rsiItem.closeRowSetIterator();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        }
    }

    public void productPackBonusChanged(ValueChangeEvent valueChangeEvent) {
        if (itlovProdPackBonus.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            Row row = iter.getNavigatableRowIterator().createRow();

            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            Row promoBonusRow = voTableData.getCurrentRow();
            String PromoBonusId =
                ((DBSequence)promoBonusRow.getAttribute("PromoBonusId")).toString();

            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoBonusId", PromoBonusId);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        } else {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("PromoBonusVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                r.remove();
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            dciter.executeQuery();

            DCIteratorBinding dciterItem =
                ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
            RowSetIterator rsiItem = dciterItem.getRowSetIterator();
            for (Row itemRow : dciterItem.getAllRowsInRange()) {
                itemRow.remove();
            }
            rsiItem.closeRowSetIterator();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itBonusVariant);
            ctx.addPartialTarget(tblListProductBonus);
        }
    }

    public void windowVariantAddBuyReturnListener(ReturnEvent returnEvent) {

        DCIteratorBinding dciterProdVariant =
            ADFUtils.findIterator("AddBuyVariantView1Iterator");
        if (dciterProdVariant.getEstimatedRowCount() == 1) {
            RowSetIterator rsiProdVariant =
                dciterProdVariant.getRowSetIterator();
            Row prodVariantRow = rsiProdVariant.first();
            if (!prodVariantRow.getAttribute("ProdVariant").toString().equalsIgnoreCase("ALL")) {
                DCIteratorBinding dciterProdItem =
                    ADFUtils.findIterator("AddBuyProdItemView1Iterator");
                RowSetIterator rsiProdItem =
                    dciterProdItem.getRowSetIterator();
                for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                    prodItemRow.remove();
                }
                rsiProdItem.closeRowSetIterator();
            }
        } else {
            DCIteratorBinding dciterProdItem =
                ADFUtils.findIterator("AddBuyProdItemView1Iterator");
            RowSetIterator rsiProdItem = dciterProdItem.getRowSetIterator();
            for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                prodItemRow.remove();
            }
            rsiProdItem.closeRowSetIterator();
        }

        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoAddBuy");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductAddBuy);
    }

    public void windowItemAddBuyReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoAddBuy");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductAddBuy);
    }

    public void windowVariantBonusReturnListener(ReturnEvent returnEvent) {

        DCIteratorBinding dciterProdVariant =
            ADFUtils.findIterator("PromoBonusVariantView1Iterator");
        if (dciterProdVariant.getEstimatedRowCount() == 1) {
            RowSetIterator rsiProdVariant =
                dciterProdVariant.getRowSetIterator();
            Row prodVariantRow = rsiProdVariant.first();
            if (!prodVariantRow.getAttribute("ProdVariant").toString().equalsIgnoreCase("ALL")) {
                DCIteratorBinding dciterProdItem =
                    ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
                RowSetIterator rsiProdItem =
                    dciterProdItem.getRowSetIterator();
                for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                    prodItemRow.remove();
                }
                rsiProdItem.closeRowSetIterator();
            }
        } else {
            DCIteratorBinding dciterProdItem =
                ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
            RowSetIterator rsiProdItem = dciterProdItem.getRowSetIterator();
            for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                prodItemRow.remove();
            }
            rsiProdItem.closeRowSetIterator();
        }

        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoBonus");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
    }

    public void windowItemBonusReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoBonus");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
    }

    public void windowProdukRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukRegion");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);
    }

    public void windowProdukCustomerReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukCustomer");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);
    }

    public void windowProdukLocationReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukLocation");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);
    }

    public void windowProdukCustGroupReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukCustGroup");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);
    }

    public void windowProdukAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukArea");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);
    }

    public void setTblListProductBonus(RichTable tblListProductBonus) {
        this.tblListProductBonus = tblListProductBonus;
    }

    public RichTable getTblListProductBonus() {
        return tblListProductBonus;
    }

    public void setTblListProductAddBuy(RichTable tblListProductAddBuy) {
        this.tblListProductAddBuy = tblListProductAddBuy;
    }

    public RichTable getTblListProductAddBuy() {
        return tblListProductAddBuy;
    }

    public void saveAll(ActionEvent actionEvent) {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userType = userData.getUserType();
        DCIteratorBinding dciterArea =
            ADFUtils.findIterator("PropRegionAreaView1Iterator");
        DCIteratorBinding dciterCustomer =
            ADFUtils.findIterator("PropRegionCustomerView1Iterator");
        DCIteratorBinding dciterRegion =
            ADFUtils.findIterator("PropRegionView1Iterator");
        DCIteratorBinding dciterLoc =
            ADFUtils.findIterator("PropRegionLocView1Iterator");
        DCIteratorBinding dciterCustGroup =
            ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
        Integer cekArea = (int)dciterArea.getEstimatedRowCount();
        Integer cekCustomer = (int)dciterCustomer.getEstimatedRowCount();
        Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
        Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
        Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
        if (userType.equalsIgnoreCase(userArea)) {
            Boolean isSavedValid = true;
            String sSavedMsg = "";
            if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                  cekLoc > 0 || cekCustGroup > 0)) {
                if (!isSavedValid) {
                    sSavedMsg += ", ";
                }
                sSavedMsg += "Customer";
                isSavedValid = false;
            }
            if (isSavedValid) {
                saveAll();

            } else {
                JSFUtils.addFacesWarningMessage(sSavedMsg + " Harus Diisi");
            }
        } else {
            saveAll();
        }
    }

    private void saveAll() {
        BindingContainer bindings = getBindings();
        // Validate product category combination
        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");

        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        Key prodKey = null;
        HashMap<Integer, String> prodIdComb = new HashMap<Integer, String>();
        int i = 0;
        for (Row currRow : dciter.getAllRowsInRange()) {
            String prodCategory =
                (String)currRow.getAttribute("ProductCategory");
            String prodClass = (String)currRow.getAttribute("ProductClass");
            String prodBrand = (String)currRow.getAttribute("ProductBrand");
            String prodExt = (String)currRow.getAttribute("ProductExt");
            String prodPack = (String)currRow.getAttribute("ProductPack");
            String prodCombination =
                prodCategory + "." + prodClass + "." + prodBrand + "." +
                prodExt + "." + prodPack;

            String promoProdId =
                String.valueOf(((DBSequence)currRow.getAttribute("PromoProdukId")).getValue());
            String prodIdCombVal = promoProdId + ";" + prodCombination;
            prodIdComb.put(i, prodIdCombVal);
            i = i + 1;
            prodKey = currRow.getKey();
        }

        for (int j = 0; j < prodIdComb.size(); j++) {
            String prodIdCombMap = prodIdComb.get(j);
            String[] output = prodIdCombMap.split(";");
            String promoProdId = output[0].trim();
            String prodCombination = output[1].trim();

            ProdVariantValidationViewImpl prodVariant =
                promoProposalAM.getProdVariantValidationView1();
            prodVariant.setNamedWhereClauseParam("promoProdukId",
                                                 promoProdId.trim());
            prodVariant.executeQuery();

            if (prodVariant.getEstimatedRowCount() > 0) {
                Row currVariantRow = null;
                String rowIsValid = "T";
                while (prodVariant.hasNext()) {
                    currVariantRow = prodVariant.next();
                    String currProdVariant =
                        (String)currVariantRow.getAttribute("ProdVariant");
                    String fullComb = prodCombination + "." + currProdVariant;
                    if (fullComb != null) {
                        FcsViewCategCombinationViewImpl catCombView =
                            promoProposalAM.getFcsViewCategCombinationView1();
                        catCombView.setNamedWhereClauseParam("combVal",
                                                             fullComb);
                        catCombView.executeQuery();

                        if (catCombView.getEstimatedRowCount() == 1 &&
                            rowIsValid.equalsIgnoreCase("T")) {
                            updateValidComb(promoProdId.trim(), "Y");
                        } else {
                            updateValidComb(promoProdId.trim(), "N");
                            rowIsValid = "F";
                        }
                    }
                }
            }
        }

        OperationBinding operationCommitLast =
            bindings.getOperationBinding("Commit");
        operationCommitLast.execute();

        if (prodKey != null) {
            dciter.setCurrentRowWithKey(prodKey.toStringFormat(true));
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProposal);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pgMainDetail);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void updateValidComb(String passProdKey, String validValue) {
        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        for (Row promoProdRow : dciter.getAllRowsInRange()) {
            String promoProdukId =
                ((DBSequence)promoProdRow.getAttribute("PromoProdukId")).toString();
            if (passProdKey.equalsIgnoreCase(promoProdukId.trim())) {
                promoProdRow.setAttribute("ValidComb", validValue);
            }
        }
    }

    public void setItTargetPercentage(RichInputText itTargetPercentage) {
        this.itTargetPercentage = itTargetPercentage;
    }

    public RichInputText getItTargetPercentage() {
        return itTargetPercentage;
    }

    public void setItTargetValue(RichInputText itTargetValue) {
        this.itTargetValue = itTargetValue;
    }

    public RichInputText getItTargetValue() {
        return itTargetValue;
    }

    public void growthByValueChangeListener(ValueChangeEvent valueChangeEvent) {
        itTargetPercentage.setValue(0);
        itTargetValue.setValue(0);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itTargetPercentage);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itTargetValue);
    }

    public void setItTargetQty(RichInputText itTargetQty) {
        this.itTargetQty = itTargetQty;
    }

    public RichInputText getItTargetQty() {
        return itTargetQty;
    }

    public void setItTargetHarga(RichInputText itTargetHarga) {
        this.itTargetHarga = itTargetHarga;
    }

    public RichInputText getItTargetHarga() {
        return itTargetHarga;
    }

    public void setItValueTotal(RichInputText itValueTotal) {
        this.itValueTotal = itValueTotal;
    }

    public RichInputText getItValueTotal() {
        return itValueTotal;
    }

    public void targetQtyValueChangeListener(ValueChangeEvent valueChangeEvent) {
        BigDecimal tgtQty =
            new BigDecimal(itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue().toString().replaceAll(",",
                                                                          ""));
        BigDecimal totalValue = tgtQty.multiply(tgtHarga);
        oracle.jbo.domain.Number number = null;
        try {
            number =
                    new oracle.jbo.domain.Number(df2dgt.format(totalValue).toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
        }
        itValueTotal.setValue(number);
    }

    public void targetHargaValueChangeListener(ValueChangeEvent valueChangeEvent) {

        BigDecimal tgtQty =
            new BigDecimal(itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue().toString().replaceAll(",",
                                                                          ""));
        BigDecimal totalValue = tgtQty.multiply(tgtHarga);
        oracle.jbo.domain.Number number = null;
        try {
            number =
                    new oracle.jbo.domain.Number(df2dgt.format(totalValue).toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
        }
        itValueTotal.setValue(number);
    }

    public void setSwitchRegCust(UIXSwitcher switchRegCust) {
        this.switchRegCust = switchRegCust;
    }

    public UIXSwitcher getSwitchRegCust() {
        return switchRegCust;
    }

    public void itlovCustomerPilih(ValueChangeEvent valueChangeEvent) {
        String chgNewVal = (String)valueChangeEvent.getNewValue();
        if (chgNewVal.equalsIgnoreCase(prodArea)) {
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            RowSetIterator rsiCustGroup = dciterCustGroup.getRowSetIterator();
            for (Row custGroupRow : dciterCustGroup.getAllRowsInRange()) {
                custGroupRow.remove();
            }
            rsiCustGroup.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);

        } else if (chgNewVal.equalsIgnoreCase(prodCustomer)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            RowSetIterator rsiCustGroup = dciterCustGroup.getRowSetIterator();
            for (Row custGroupRow : dciterCustGroup.getAllRowsInRange()) {
                custGroupRow.remove();
            }
            rsiCustGroup.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);

        } else if (chgNewVal.equalsIgnoreCase(prodRegion)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            RowSetIterator rsiCustGroup = dciterCustGroup.getRowSetIterator();
            for (Row custGroupRow : dciterCustGroup.getAllRowsInRange()) {
                custGroupRow.remove();
            }
            rsiCustGroup.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);

        } else if (chgNewVal.equalsIgnoreCase(prodLocation)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);

            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            RowSetIterator rsiCustGroup = dciterCustGroup.getRowSetIterator();
            for (Row custGroupRow : dciterCustGroup.getAllRowsInRange()) {
                custGroupRow.remove();
            }
            rsiCustGroup.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);

        } else if (chgNewVal.equalsIgnoreCase(prodCustGroup)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);

        } else {
            System.out.println("NO CHANGE ON PROD CUSTOMER");
            JSFUtils.addFacesErrorMessage("Error",
                                          "Customer option produk not recognized");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchRegCust);
    }

    public void setTblListProductRegion(RichTable tblListProductRegion) {
        this.tblListProductRegion = tblListProductRegion;
    }

    public RichTable getTblListProductRegion() {
        return tblListProductRegion;
    }

    public void setTblListProductCustomer(RichTable tblListProductCustomer) {
        this.tblListProductCustomer = tblListProductCustomer;
    }

    public RichTable getTblListProductCustomer() {
        return tblListProductCustomer;
    }

    public void setTblListProductArea(RichTable tblListProductArea) {
        this.tblListProductArea = tblListProductArea;
    }

    public RichTable getTblListProductArea() {
        return tblListProductArea;
    }

    /* OLD CODE
    public void addPromoProduk(ActionEvent actionEvent) {
        BindingContainer bindings = this.getBindings();
        /*
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();
        */
    /*
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsertPromoProduk");
        operationBinding.execute();
    }
    */

    public void addPromoProduk(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("PromoProdukView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
        
        Integer propTypeIdx = (Integer)itLovProposalType.getValue();
        if (propTypeIdx.compareTo(idxFood) == 0) {
            itLovProdCategory.setValue(prodCatCodeFood);
            AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
            adc.addPartialTarget(itLovProdCategory);
            adc.addPartialTarget(itCategory);
            adc.addPartialTarget(tblListProduct);
        } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
            itLovProdCategory.setValue(prodCatCodeNonFood);
            AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
            adc.addPartialTarget(itLovProdCategory);
            adc.addPartialTarget(itCategory);
            adc.addPartialTarget(tblListProduct);
        } else {
            JSFUtils.addFacesErrorMessage("Tipe proposal selain FOOD dan NON FOOD tidak dikenali.");
        }
    }

    public void copyProposal(DialogEvent dialogEvent) {

        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");

        List aprvlRegPropList = new ArrayList();
        List propRegionList = new ArrayList();
        List propRegionCustList = new ArrayList();
        List propRegionAreaList = new ArrayList();
        List propRegionLocList = new ArrayList();
        List propRegionCustGroupList = new ArrayList();
        String copyAs = (String)copyAsFlag.getValue();
        HashMap<String, DBSequence> promoAddBuyMap =
            new HashMap<String, DBSequence>();
        HashMap<String, DBSequence> promoBonusMap =
            new HashMap<String, DBSequence>();
        BindingContainer bindings = this.getBindings();

        DCIteratorBinding dciterAprvlRegProp =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        DCIteratorBinding dciterPropRegion =
            ADFUtils.findIterator("PropRegionView1Iterator");
        DCIteratorBinding dciterPropRegionCust =
            ADFUtils.findIterator("PropRegionCustomerView1Iterator");
        DCIteratorBinding dciterPropRegionArea =
            ADFUtils.findIterator("PropRegionAreaView1Iterator");
        DCIteratorBinding dciterPropRegionLoc =
            ADFUtils.findIterator("PropRegionLocView1Iterator");
        DCIteratorBinding dciterPropRegionCustGroup =
            ADFUtils.findIterator("PropRegionCustGroupView1Iterator");

        AttributeBinding propNoAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalNo");
        String srcProposalNo = (String)propNoAttr.getInputValue();

        AttributeBinding usrTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String srcUsrTypeCreator = (String)usrTypeCreatorAttr.getInputValue();

        AttributeBinding custRegFlag1Attr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");
        String custRegFlag1 = (String)custRegFlag1Attr.getInputValue();

        AttributeBinding discountType1Attr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discountType1 = (String)discountType1Attr.getInputValue();

        // ===== COLLECT DATA ==================================================
        // PARENT: Collect Data Proposal
        DCIteratorBinding dciterProposal =
            ADFUtils.findIterator("ProposalView1Iterator");
        Row propCurrentRow = dciterProposal.getCurrentRow();
        String[] propAttrs = propCurrentRow.getAttributeNames();
        String[] skipPropAttrs =
            new String[] { "ProposalId", "ProposalNo", "Status", "AddendumKe",
                           "ConfirmNo", "CopySource" };
        List skipPropAttrList = Arrays.asList(skipPropAttrs);

        // CHILD: Collect Data Region Approval
        if (dciterAprvlRegProp.getEstimatedRowCount() > 0) {
            for (Row r : dciterAprvlRegProp.getAllRowsInRange()) {
                aprvlRegPropList.add(r.getAttribute("RegionCode"));
            }
        }

        // CHILD: Collect Data Customer
        if (srcUsrTypeCreator.equalsIgnoreCase(userArea)) {
            // USER TYPE CREATOR == AREA
            if (custRegFlag1.equalsIgnoreCase(propRegion)) {
                if (dciterPropRegion.getEstimatedRowCount() > 0) {
                    for (Row r : dciterPropRegion.getAllRowsInRange()) {
                        propRegionList.add(r.getAttribute("RegionCode"));
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propCustomer)) {
                if (dciterPropRegionCust.getEstimatedRowCount() > 0) {
                    for (Row r : dciterPropRegionCust.getAllRowsInRange()) {
                        propRegionCustList.add(r.getAttribute("CustomerId"));
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propArea)) {
                if (dciterPropRegionArea.getEstimatedRowCount() > 0) {
                    for (Row r : dciterPropRegionArea.getAllRowsInRange()) {
                        propRegionAreaList.add(r.getAttribute("AreaCode"));
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propLocation)) {
                if (dciterPropRegionLoc.getEstimatedRowCount() > 0) {
                    for (Row r : dciterPropRegionLoc.getAllRowsInRange()) {
                        propRegionLocList.add(r.getAttribute("LocationCode"));
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propCustGroup)) {
                if (dciterPropRegionCustGroup.getEstimatedRowCount() > 0) {
                    for (Row r :
                         dciterPropRegionCustGroup.getAllRowsInRange()) {
                        propRegionCustGroupList.add(r.getAttribute("CustGroup"));
                    }
                }
            }
        } else {
            // USER TYPE CREATOR == HO
        }

        // CHILD: Data Promo Produk
        DCIteratorBinding dciterPromoProduk =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        List promoProdList = new ArrayList<Row>();
        if (dciterPromoProduk.getEstimatedRowCount() > 0) {
            int i = 0;
            for (Row r : dciterPromoProduk.getAllRowsInRange()) {
                promoProdList.add(r);
            }
        }

        // ===== INSERTING DATA ================================================
        // PARENT: Insert Data Proposal
        Row dupProposalRow = dciterProposal.getRowSetIterator().createRow();
        for (int i = 0; i < propAttrs.length; i++) {
            String propAttrName = propAttrs[i];
            if ("Status".equals(propAttrName))
                dupProposalRow.setAttribute(propAttrName, "DRAFT");
            if ("CopySource".equals(propAttrName) &&
                copyAs.equalsIgnoreCase("R"))
                dupProposalRow.setAttribute(propAttrName, srcProposalNo);
            int attrIndex = dupProposalRow.getAttributeIndexOf(propAttrName);
            //Checking if the attribute is in the skip attribute list and the attribute is updatable
            if (!skipPropAttrList.contains(propAttrName) &&
                dupProposalRow.isAttributeUpdateable(attrIndex))
                //Setting the value for the attributes
                dupProposalRow.setAttribute(propAttrName,
                                            propCurrentRow.getAttribute(propAttrName));
        }
        //Inserting the duplicate proposal row
        dciterProposal.getRowSetIterator().insertRow(dupProposalRow);

        // CHILD: Inserting Data Region Approval
        if (aprvlRegPropList.size() > 0) {
            for (int i = 0; i < aprvlRegPropList.size(); i++) {
                Row dupAprvlRegPropRow =
                    dciterAprvlRegProp.getRowSetIterator().createRow();
                dupAprvlRegPropRow.setAttribute("RegionCode",
                                                aprvlRegPropList.get(i));

                //Inserting the duplicate approval region proposal row
                dciterAprvlRegProp.getRowSetIterator().insertRow(dupAprvlRegPropRow);
            }
        }

        // CHILD: Inserting Data Customer
        if (srcUsrTypeCreator.equalsIgnoreCase(userArea)) {
            // USER TYPE CREATOR == AREA
            if (custRegFlag1.equalsIgnoreCase(propRegion)) {
                if (propRegionList.size() > 0) {
                    for (int i = 0; i < propRegionList.size(); i++) {
                        Row dupPropRegionRow =
                            dciterPropRegion.getRowSetIterator().createRow();
                        dupPropRegionRow.setAttribute("RegionCode",
                                                      propRegionList.get(i));

                        //Inserting the duplicate proposal region row
                        dciterPropRegion.getRowSetIterator().insertRow(dupPropRegionRow);
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propCustomer)) {
                if (propRegionCustList.size() > 0) {
                    for (int i = 0; i < propRegionCustList.size(); i++) {
                        Row dupPropRegionCustRow =
                            dciterPropRegionCust.getRowSetIterator().createRow();
                        dupPropRegionCustRow.setAttribute("CustomerId",
                                                          propRegionCustList.get(i));

                        //Inserting the duplicate proposal customer row
                        dciterPropRegionCust.getRowSetIterator().insertRow(dupPropRegionCustRow);
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propArea)) {
                if (propRegionAreaList.size() > 0) {
                    for (int i = 0; i < propRegionAreaList.size(); i++) {
                        Row dupPropRegionAreaRow =
                            dciterPropRegionArea.getRowSetIterator().createRow();
                        dupPropRegionAreaRow.setAttribute("AreaCode",
                                                          propRegionAreaList.get(i));

                        //Inserting the duplicate proposal area row
                        dciterPropRegionArea.getRowSetIterator().insertRow(dupPropRegionAreaRow);
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propLocation)) {
                if (propRegionLocList.size() > 0) {
                    for (int i = 0; i < propRegionLocList.size(); i++) {
                        Row dupPropRegionLocRow =
                            dciterPropRegionLoc.getRowSetIterator().createRow();
                        dupPropRegionLocRow.setAttribute("LocationCode",
                                                         propRegionLocList.get(i));

                        //Inserting the duplicate proposal location row
                        dciterPropRegionLoc.getRowSetIterator().insertRow(dupPropRegionLocRow);
                    }
                }
            } else if (custRegFlag1.equalsIgnoreCase(propCustGroup)) {
                if (propRegionCustGroupList.size() > 0) {
                    for (int i = 0; i < propRegionCustGroupList.size(); i++) {
                        Row dupPropRegionCustGroupRow =
                            dciterPropRegionCustGroup.getRowSetIterator().createRow();
                        dupPropRegionCustGroupRow.setAttribute("CustGroup",
                                                               propRegionCustGroupList.get(i));

                        //Inserting the duplicate proposal customer group row
                        dciterPropRegionCustGroup.getRowSetIterator().insertRow(dupPropRegionCustGroupRow);
                    }
                }
            }
        } else {
            // USER TYPE CREATOR == HO
        }

        // CHILD: Inserting Data Promo Produk
        if (promoProdList.size() > 0) {
            for (int i = 0; i < promoProdList.size(); i++) {
                Row dupPromoProdukRow =
                    dciterPromoProduk.getRowSetIterator().createRow();
                Row promoProdSource = (Row)promoProdList.get(i);
                String produkCustomer =
                    (String)promoProdSource.getAttribute("RegCustFlag");

                dupPromoProdukRow.setAttribute("ProductCategory",
                                               promoProdSource.getAttribute("ProductCategory"));
                dupPromoProdukRow.setAttribute("ProductClass",
                                               promoProdSource.getAttribute("ProductClass"));
                dupPromoProdukRow.setAttribute("ProductBrand",
                                               promoProdSource.getAttribute("ProductBrand"));
                dupPromoProdukRow.setAttribute("ProductExt",
                                               promoProdSource.getAttribute("ProductExt"));
                dupPromoProdukRow.setAttribute("ProductPack",
                                               promoProdSource.getAttribute("ProductPack"));
                dupPromoProdukRow.setAttribute("Descr",
                                               promoProdSource.getAttribute("Descr"));
                dupPromoProdukRow.setAttribute("Mekanisme",
                                               promoProdSource.getAttribute("Mekanisme"));
                dupPromoProdukRow.setAttribute("AmountPromo",
                                               promoProdSource.getAttribute("AmountPromo"));
                dupPromoProdukRow.setAttribute("EstBudgetProp",
                                               promoProdSource.getAttribute("EstBudgetProp"));
                dupPromoProdukRow.setAttribute("RegCustFlag", produkCustomer);
                dupPromoProdukRow.setAttribute("BudgetBy",
                                               promoProdSource.getAttribute("BudgetBy"));

                //Inserting the duplicate proposal region row
                dciterPromoProduk.getRowSetIterator().insertRow(dupPromoProdukRow);

                String promoProdId =
                    String.valueOf(((DBSequence)promoProdSource.getAttribute("PromoProdukId")).getValue());
                this.promoProdIdvar = promoProdId.toString();

                // SUB CHILD: Collect and Inserting Data Produk Variant
                DuplicateProdukVariantViewImpl voDupProdukVariant =
                    promoProposalAM.getDuplicateProdukVariantView1();
                voDupProdukVariant.setNamedWhereClauseParam("promoProdukId",
                                                            promoProdId.trim());
                voDupProdukVariant.executeQuery();

                if (voDupProdukVariant.getEstimatedRowCount() > 0) {
                    while (voDupProdukVariant.hasNext()) {
                        Row variantRow = voDupProdukVariant.next();
                        String prodVariant =
                            (String)variantRow.getAttribute("ProdVariant");
                        DCIteratorBinding dciterProdukVariant =
                            ADFUtils.findIterator("ProdukVariantView1Iterator");
                        Row dupProdVariantRow =
                            dciterProdukVariant.getRowSetIterator().createRow();
                        dupProdVariantRow.setAttribute("ProdVariant",
                                                       prodVariant);
                    }
                }

                // SUB CHILD: Collect and Inserting Data Produk Item
                DuplicateProdukItemViewImpl voDupProdukItem =
                    promoProposalAM.getDuplicateProdukItemView1();
                voDupProdukItem.setNamedWhereClauseParam("promoProdukId",
                                                         promoProdId.trim());
                voDupProdukItem.executeQuery();

                if (voDupProdukItem.getEstimatedRowCount() > 0) {
                    while (voDupProdukItem.hasNext()) {
                        Row itemRow = voDupProdukItem.next();
                        String prodItem =
                            (String)itemRow.getAttribute("ProdItem");
                        DCIteratorBinding dciterProdukItem =
                            ADFUtils.findIterator("ProdukItemView1Iterator");
                        Row dupProdVariantRow =
                            dciterProdukItem.getRowSetIterator().createRow();
                        dupProdVariantRow.setAttribute("ProdItem", prodItem);
                    }
                }

                if (produkCustomer.equalsIgnoreCase(prodRegion)) {
                    // SUB CHILD: Collect and Inserting Data Produk Customer by Region
                    DuplicateProdRegionViewImpl voDupProdRegion =
                        promoProposalAM.getDuplicateProdRegionView1();
                    voDupProdRegion.setNamedWhereClauseParam("promoProdukId",
                                                             promoProdId.trim());
                    voDupProdRegion.executeQuery();

                    if (voDupProdRegion.getEstimatedRowCount() > 0) {
                        while (voDupProdRegion.hasNext()) {
                            Row prodRegRow = voDupProdRegion.next();
                            String regionCode =
                                (String)prodRegRow.getAttribute("RegionCode");
                            String notes =
                                (String)prodRegRow.getAttribute("Notes");

                            DCIteratorBinding dciterProdRegion =
                                ADFUtils.findIterator("ProdRegionView1Iterator");
                            Row dupProdRegionRow =
                                dciterProdRegion.getRowSetIterator().createRow();
                            dupProdRegionRow.setAttribute("RegionCode",
                                                          regionCode);
                            dupProdRegionRow.setAttribute("Notes", notes);
                        }
                    }
                } else if (produkCustomer.equalsIgnoreCase(prodArea)) {
                    // SUB CHILD: Collect and Inserting Data Produk Customer by Area
                    DuplicateProdRegionAreaViewImpl voDupProdArea =
                        promoProposalAM.getDuplicateProdRegionAreaView1();
                    voDupProdArea.setNamedWhereClauseParam("promoProdukId",
                                                           promoProdId.trim());
                    voDupProdArea.executeQuery();

                    if (voDupProdArea.getEstimatedRowCount() > 0) {
                        while (voDupProdArea.hasNext()) {
                            Row prodAreaRow = voDupProdArea.next();
                            String areaCode =
                                (String)prodAreaRow.getAttribute("AreaCode");
                            String notes =
                                (String)prodAreaRow.getAttribute("Notes");

                            DCIteratorBinding dciterProdArea =
                                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
                            Row dupProdAreaRow =
                                dciterProdArea.getRowSetIterator().createRow();
                            dupProdAreaRow.setAttribute("AreaCode", areaCode);
                            dupProdAreaRow.setAttribute("Notes", notes);
                        }
                    }
                } else if (produkCustomer.equalsIgnoreCase(prodCustomer)) {
                    // SUB CHILD: Collect and Inserting Data Produk Customer by Customer
                    DuplicateProdRegionCustomerViewImpl voDupProdCustomer =
                        promoProposalAM.getDuplicateProdRegionCustomerView1();
                    voDupProdCustomer.setNamedWhereClauseParam("promoProdukId",
                                                               promoProdId.trim());
                    voDupProdCustomer.executeQuery();

                    if (voDupProdCustomer.getEstimatedRowCount() > 0) {
                        while (voDupProdCustomer.hasNext()) {
                            Row prodCustomerRow = voDupProdCustomer.next();
                            Number custId =
                                (Number)prodCustomerRow.getAttribute("CustomerId");
                            String notes =
                                (String)prodCustomerRow.getAttribute("Notes");

                            DCIteratorBinding dciterProdCust =
                                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
                            Row dupProdCustRow =
                                dciterProdCust.getRowSetIterator().createRow();
                            dupProdCustRow.setAttribute("CustomerId", custId);
                            dupProdCustRow.setAttribute("Notes", notes);
                        }
                    }
                } else if (produkCustomer.equalsIgnoreCase(prodLocation)) {
                    // SUB CHILD: Collect and Inserting Data Produk Customer by Location
                    DuplicateProdRegionLocViewImpl voDupProdLocation =
                        promoProposalAM.getDuplicateProdRegionLocView1();
                    voDupProdLocation.setNamedWhereClauseParam("promoProdukId",
                                                               promoProdId.trim());
                    voDupProdLocation.executeQuery();

                    if (voDupProdLocation.getEstimatedRowCount() > 0) {
                        while (voDupProdLocation.hasNext()) {
                            Row prodLocationRow = voDupProdLocation.next();
                            String locCode =
                                (String)prodLocationRow.getAttribute("LocationCode");
                            String notes =
                                (String)prodLocationRow.getAttribute("Notes");

                            DCIteratorBinding dciterProdLoc =
                                ADFUtils.findIterator("ProdRegionLocView1Iterator");
                            Row dupProdLocRow =
                                dciterProdLoc.getRowSetIterator().createRow();
                            dupProdLocRow.setAttribute("LocationCode",
                                                       locCode);
                            dupProdLocRow.setAttribute("Notes", notes);
                        }
                    }
                } else if (produkCustomer.equalsIgnoreCase(prodCustGroup)) {
                    // SUB CHILD: Collect and Inserting Data Produk Customer by Customer Group
                    DuplicateProdRegionCustGroupViewImpl voDupProdCustGroup =
                        promoProposalAM.getDuplicateProdRegionCustGroupView1();
                    voDupProdCustGroup.setNamedWhereClauseParam("promoProdukId",
                                                                promoProdId.trim());
                    voDupProdCustGroup.executeQuery();

                    if (voDupProdCustGroup.getEstimatedRowCount() > 0) {
                        while (voDupProdCustGroup.hasNext()) {
                            Row prodCustGroupRow = voDupProdCustGroup.next();
                            String custGroup =
                                (String)prodCustGroupRow.getAttribute("CustGroup");
                            String notes =
                                (String)prodCustGroupRow.getAttribute("Notes");

                            DCIteratorBinding dciterProdCustGroup =
                                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
                            Row dupProdCustGroupRow =
                                dciterProdCustGroup.getRowSetIterator().createRow();
                            dupProdCustGroupRow.setAttribute("CustGroup",
                                                             custGroup);
                            dupProdCustGroupRow.setAttribute("Notes", notes);
                        }
                    }
                } else {
                    // DONT COPY PRODUK CUSTOMER
                }

                // SUB CHILD: Collect and Inserting Data Produk Target
                DuplicateTargetViewImpl voDupTarget =
                    (DuplicateTargetViewImpl)promoProposalAM.getDuplicateTargetView1();
                voDupTarget.setNamedWhereClauseParam("promoProdukId",
                                                     promoProdId.trim());
                voDupTarget.executeQuery();

                if (voDupTarget.getEstimatedRowCount() > 0) {
                    while (voDupTarget.hasNext()) {
                        Row targetRow = voDupTarget.next();
                        /*
                        String growthBy =
                            (String)targetRow.getAttribute("GrowthBy");
                        Number growthPercent =
                            (Number)targetRow.getAttribute("GrowthPercent");
                        */
                        Number avgQty =
                            (Number)targetRow.getAttribute("AvgQty");
                        Number price = (Number)targetRow.getAttribute("Price");
                        String priceBased =
                            (String)targetRow.getAttribute("PriceBased");
                        String priceList =
                            (String)targetRow.getAttribute("PriceList");
                        Number qty = (Number)targetRow.getAttribute("Qty");
                        String uom = (String)targetRow.getAttribute("Uom");
                        /*
                        Number targetValue =
                            (Number)targetRow.getAttribute("TargetValue");
                        */
                        Number value = (Number)targetRow.getAttribute("Value");

                        DCIteratorBinding dciterTarget =
                            ADFUtils.findIterator("TargetView1Iterator");
                        Row dupTargetRow =
                            dciterTarget.getRowSetIterator().createRow();
                        /*
                        dupTargetRow.setAttribute("GrowthBy", growthBy);
                        dupTargetRow.setAttribute("GrowthPercent",
                                                  growthPercent);
                        */
                        dupTargetRow.setAttribute("AvgQty", avgQty);
                        dupTargetRow.setAttribute("Price", price);
                        dupTargetRow.setAttribute("PriceBased", priceBased);
                        dupTargetRow.setAttribute("PriceList", priceList);
                        dupTargetRow.setAttribute("Qty", qty);
                        dupTargetRow.setAttribute("Uom", uom);
                        /*
                        dupTargetRow.setAttribute("TargetValue", targetValue);
                        */
                        dupTargetRow.setAttribute("Value", value);
                    }
                }

                if (discountType1.equalsIgnoreCase(discTypePotongan)) {
                    // SUB CHILD: Collect and Inserting Data Produk Potongan / Discount
                    DuplicateDiscountViewImpl voDupDiscount =
                        (DuplicateDiscountViewImpl)promoProposalAM.getDuplicateDiscountView1();
                    voDupDiscount.setNamedWhereClauseParam("promoProdukId",
                                                           promoProdId.trim());
                    voDupDiscount.executeQuery();

                    if (voDupTarget.getEstimatedRowCount() > 0) {
                        while (voDupDiscount.hasNext()) {
                            Row discountRow = voDupDiscount.next();
                            String tipePerhitungan =
                                (String)discountRow.getAttribute("TipePerhitungan");
                            String uom =
                                (String)discountRow.getAttribute("Uom");
                            Number qtyFrom =
                                (Number)discountRow.getAttribute("QtyFrom");
                            Number qtyTo =
                                (Number)discountRow.getAttribute("QtyTo");
                            String tipePotongan =
                                (String)discountRow.getAttribute("TipePotongan");
                            Number discNonYearly =
                                (Number)discountRow.getAttribute("DiscNonYearly");
                            Number discYearly =
                                (Number)discountRow.getAttribute("DiscYearly");

                            DCIteratorBinding dciterDiscount =
                                ADFUtils.findIterator("DiscountView1Iterator");
                            Row dupDiscountRow =
                                dciterDiscount.getRowSetIterator().createRow();
                            dupDiscountRow.setAttribute("TipePerhitungan",
                                                        tipePerhitungan);
                            dupDiscountRow.setAttribute("Uom", uom);
                            dupDiscountRow.setAttribute("QtyFrom", qtyFrom);
                            dupDiscountRow.setAttribute("QtyTo", qtyTo);
                            dupDiscountRow.setAttribute("TipePotongan",
                                                        tipePotongan);
                            dupDiscountRow.setAttribute("DiscNonYearly",
                                                        discNonYearly);
                            dupDiscountRow.setAttribute("DiscYearly",
                                                        discYearly);
                        }
                    }
                } else if (discountType1.equalsIgnoreCase(discTypeBiaya)) {
                    // SUB CHILD: Collect and Inserting Data Produk Biaya
                    DuplicateBiayaViewImpl voDupBiaya =
                        promoProposalAM.getDuplicateBiayaView1();
                    voDupBiaya.setNamedWhereClauseParam("promoProdukId",
                                                        promoProdId.trim());
                    voDupBiaya.executeQuery();

                    if (voDupBiaya.getEstimatedRowCount() > 0) {
                        while (voDupBiaya.hasNext()) {
                            Row biayaRow = voDupBiaya.next();
                            String descr =
                                (String)biayaRow.getAttribute("Descr");
                            Number biayaNonYearly =
                                (Number)biayaRow.getAttribute("BiayaNonYearly");
                            Number biayaYearly =
                                (Number)biayaRow.getAttribute("BiayaYearly");

                            DCIteratorBinding dciterBiaya =
                                ADFUtils.findIterator("BiayaView1Iterator");
                            Row dupBiayaRow =
                                dciterBiaya.getRowSetIterator().createRow();
                            dupBiayaRow.setAttribute("Descr", descr);
                            dupBiayaRow.setAttribute("BiayaNonYearly",
                                                     biayaNonYearly);
                            dupBiayaRow.setAttribute("BiayaYearly",
                                                     biayaYearly);
                        }
                    }
                } else if (discountType1.equalsIgnoreCase(discTypePromoBarang)) {
                    // SUB CHILD: Collect and Inserting Data Produk Promo Bonus
                    DuplicatePromoBonusViewImpl voDupBonus =
                        promoProposalAM.getDuplicatePromoBonusView1();
                    voDupBonus.setNamedWhereClauseParam("promoProdukId",
                                                        promoProdId.trim());
                    voDupBonus.executeQuery();

                    if (voDupBonus.getEstimatedRowCount() > 0) {
                        int p = 0;
                        while (voDupBonus.hasNext()) {
                            Row addBonus = voDupBonus.next();
                            String productCategory =
                                (String)addBonus.getAttribute("ProductCategory");
                            String productClass =
                                (String)addBonus.getAttribute("ProductClass");
                            String productBrand =
                                (String)addBonus.getAttribute("ProductBrand");
                            String productExt =
                                (String)addBonus.getAttribute("ProductExt");
                            String productPack =
                                (String)addBonus.getAttribute("ProductPack");
                            String uom = (String)addBonus.getAttribute("Uom");
                            Number qtyFrom =
                                (Number)addBonus.getAttribute("QtyFrom");
                            String tipePotongan =
                                (String)addBonus.getAttribute("TipePotongan");
                            Number valuePotongan =
                                (Number)addBonus.getAttribute("ValuePotongan");
                            Number discYearly =
                                (Number)addBonus.getAttribute("DiscYearly");
                            Number discNonYearly =
                                (Number)addBonus.getAttribute("DiscNonYearly");

                            DCIteratorBinding dciterBonus =
                                ADFUtils.findIterator("PromoBonusView1Iterator");
                            Row dupBonusRow =
                                dciterBonus.getRowSetIterator().createRow();
                            dupBonusRow.setAttribute("ProductCategory",
                                                     productCategory);
                            dupBonusRow.setAttribute("ProductClass",
                                                     productClass);
                            dupBonusRow.setAttribute("ProductBrand",
                                                     productBrand);
                            dupBonusRow.setAttribute("ProductExt", productExt);
                            dupBonusRow.setAttribute("ProductPack",
                                                     productPack);
                            dupBonusRow.setAttribute("Uom", uom);
                            dupBonusRow.setAttribute("QtyFrom", qtyFrom);
                            dupBonusRow.setAttribute("TipePotongan",
                                                     tipePotongan);
                            dupBonusRow.setAttribute("ValuePotongan",
                                                     valuePotongan);
                            dupBonusRow.setAttribute("DiscYearly", discYearly);
                            dupBonusRow.setAttribute("DiscNonYearly",
                                                     discNonYearly);

                            DBSequence promoBonusId =
                                (DBSequence)dupBonusRow.getAttribute("PromoBonusId");

                            System.out.println("NEW BONUS ROW ID: |" +
                                               promoBonusId.getValue() + "|");
                            this.promoBonusIdvar =
                                    Long.toString(promoBonusId.getValue());
                            // Collect Promo Add Buy -- Variant
                            DuplicatePromoBonusVariantViewImpl voDupBonusVariant =
                                promoProposalAM.getDuplicatePromoBonusVariantView1();
                            voDupBonusVariant.setNamedWhereClauseParam("promoProdukId",
                                                                       promoProdId.trim());
                            voDupBonusVariant.executeQuery();

                            if (voDupBonusVariant.getEstimatedRowCount() > 0) {
                                while (voDupBonusVariant.hasNext()) {
                                    Row bonusVariantRow =
                                        voDupBonusVariant.next();
                                    String bonusProdVariant =
                                        (String)bonusVariantRow.getAttribute("ProdVariant");
                                    String key =
                                        promoBonusId + bonusProdVariant;
                                    promoBonusMap.put(key, promoBonusId);
                                }
                            }

                            p = p + 1;
                        }
                    }

                    //==============================================================================
                    // SUB-SUB CHILD: Collect and Inserting Data Produk Promo Add Buy Variant
                    /*
                    DuplicatePromoAddBuyVariantViewImpl voDupAddBuyVariant =
                        promoProposalAM.getDuplicatePromoAddBuyVariantView1();
                    voDupAddBuyVariant.setNamedWhereClauseParam("promoProdukId",
                                                                promoProdId.trim());
                    voDupAddBuyVariant.executeQuery();

                    if (voDupAddBuyVariant.getEstimatedRowCount() > 0) {
                        while (voDupAddBuyVariant.hasNext()) {
                            Row addBuyVariantRow = voDupAddBuyVariant.next();
                            String addBuyProdVariant =
                                (String)addBuyVariantRow.getAttribute("ProdVariant");

                            //                        DCIteratorBinding dciterAddBuyVariant =
                            //                            ADFUtils.findIterator("AddBuyVariantView1Iterator");
                            //                        Row dupAddBuyVariantRow = dciterAddBuyVariant.getRowSetIterator().createRow();
                            //                        dupAddBuyVariantRow.setAttribute("ProdVariant", addBuyProdVariant);
                        }
                    }
                    */

                    // SUB-SUB CHILD: Collect and Inserting Data Produk Promo Bonus Variant
                    /*
                    DuplicatePromoBonusVariantViewImpl voDupBonusVariant = promoProposalAM.getDuplicatePromoBonusVariantView1();
                    voDupBonusVariant.setNamedWhereClauseParam("promoProdukId", promoProdId.trim());
                    voDupBonusVariant.executeQuery();

                    if (voDupBonusVariant.getEstimatedRowCount() > 0) {
                        while (voDupBonusVariant.hasNext()) {
                            Row bonusVariantRow = voDupBonusVariant.next();
                            String bonusProdVariant = (String)bonusVariantRow.getAttribute("ProdVariant");

                            DCIteratorBinding dciterBonusVariant =
                                ADFUtils.findIterator("PromoBonusVariantView1Iterator");
                            Row dupAddBuyVariantRow = dciterBonusVariant.getRowSetIterator().createRow();
                            dupAddBuyVariantRow.setAttribute("ProdVariant", bonusProdVariant);
                        }
                    }
                    */
                    //==============================================================================
                } else {
                    // OTHER SUB CHILD TYPE
                }
            }
        }

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        DuplicatePromoBonusVariantViewImpl voDupBonusVariant =
            promoProposalAM.getDuplicatePromoBonusVariantView1();
        voDupBonusVariant.setNamedWhereClauseParam("promoProdukId",
                                                   promoProdIdvar);
        voDupBonusVariant.executeQuery();

        if (voDupBonusVariant.getEstimatedRowCount() > 0) {
            while (voDupBonusVariant.hasNext()) {
                Row dupBonusVariantRow = voDupBonusVariant.next();
                String bonusProdVariant =
                    (String)dupBonusVariantRow.getAttribute("ProdVariant");

                try {
                    DCIteratorBinding dciterBonusVariant =
                        ADFUtils.findIterator("PromoBonusVariantView1Iterator");
                    Row bonusVariantRow =
                        dciterBonusVariant.getRowSetIterator().createRow();
                    bonusVariantRow.setAttribute("ProdVariant",
                                                 bonusProdVariant);
                    bonusVariantRow.setAttribute("PromoBonusId",
                                                 promoBonusIdvar);
                    promoProposalAM.getPromoBonusVariantView1().getDBTransaction().commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Iterator iter = promoAddBuyMap.keySet().iterator();
        //Iterate over hashmap to get key
        int i = 0;
        while (iter.hasNext()) {
            i = i + 1;
            String key = (String)iter.next();
            //Use this key to find value
            DBSequence value = promoAddBuyMap.get(key);
            System.out.println("**KEY**  " + key + " AND VALUE**  " +
                               value.getValue());
        }

        dciterPropRegion.executeQuery();
        dciterPropRegionCust.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustGroup);

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProposal);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);

    }

    public void setCopyAsFlag(RichSelectOneChoice copyAsFlag) {
        this.copyAsFlag = copyAsFlag;
    }

    public RichSelectOneChoice getCopyAsFlag() {
        return copyAsFlag;
    }

    public void setPromoProdIdvar(String promoProdIdvar) {
        this.promoProdIdvar = promoProdIdvar;
    }

    public String getPromoProdIdvar() {
        return promoProdIdvar;
    }

    public void setPromoAddBuyIdvar(String promoAddBuyIdvar) {
        this.promoAddBuyIdvar = promoAddBuyIdvar;
    }

    public String getPromoAddBuyIdvar() {
        return promoAddBuyIdvar;
    }

    public void setPromoBonusIdvar(String promoBonusIdvar) {
        this.promoBonusIdvar = promoBonusIdvar;
    }

    public String getPromoBonusIdvar() {
        return promoBonusIdvar;
    }
    /*
    public void tipeProposalValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            int tipeProp = (Integer)valueChangeEvent.getNewValue();
            if (tipeProp == 0) {
                socMekPenagihan.setValue(0);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(socMekPenagihan);
        }
    }
*/

    public void setSocMekPenagihan(RichSelectOneChoice socMekPenagihan) {
        this.socMekPenagihan = socMekPenagihan;
    }

    public RichSelectOneChoice getSocMekPenagihan() {
        return socMekPenagihan;
    }

    public void setTblListProposal(RichTable tblListProposal) {
        this.tblListProposal = tblListProposal;
    }

    public RichTable getTblListProposal() {
        return tblListProposal;
    }

    public void btnOkDetailProduct(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discType = (String)discTypeAttr.getInputValue();
        Boolean isIterValid = true;
        String sIterMsg = "";
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userType = userData.getUserType();
        if (discType.equalsIgnoreCase("BIAYA")) {
            DCIteratorBinding dciterBiaya =
                ADFUtils.findIterator("BiayaView1Iterator");
            if (dciterBiaya.getEstimatedRowCount() < 1) {
                //                String value=itValueTotal.getValue()==null ? "" : itValueTotal.getValue().toString();
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Biaya";
                isIterValid = false;
            }
        } else if (discType.equalsIgnoreCase("PROMOBARANG")) {
            DCIteratorBinding dciterPromoBonus =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            if (dciterPromoBonus.getEstimatedRowCount() < 1) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Promo";
                isIterValid = false;
            }
        } else if (discType.equalsIgnoreCase("POTONGAN")) {
            DCIteratorBinding dciterDiscount =
                ADFUtils.findIterator("DiscountView1Iterator");
            if (dciterDiscount.getEstimatedRowCount() < 1) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Potongan";
                isIterValid = false;
            }
        }
        if (userType.equalsIgnoreCase(userHo)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            Integer cekArea = (int)dciterArea.getEstimatedRowCount();
            Integer cekCustomer = (int)dciterCustomer.getEstimatedRowCount();
            Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
            Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
            Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
            if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                  cekLoc > 0 || cekCustGroup > 0)) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Customer";
                isIterValid = false;
            }
        }
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        for (Row r : dciterTarget.getAllRowsInRange()) {
            int price =
                Integer.parseInt(r.getAttribute("Price") == null ? "0" :
                                 (df.format(((Number)r.getAttribute("Price")).getValue())).toString());
            int Qty =
                Integer.parseInt(r.getAttribute("Qty") == null ? "0" : r.getAttribute("Qty").toString());
            //            System.out.println("price = " + price);
            if (price <= 0 || Qty <= 0) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Target & Budget: Price,Qty Harus >= 1";
                isIterValid = false;
            }
        }
        if (isIterValid) {
            Boolean isInputValid = true;
            String sErrMsg = "";
            if (discType.equalsIgnoreCase("BIAYA")) {
                DCIteratorBinding dciterBiaya =
                    ADFUtils.findIterator("BiayaView1Iterator");
                for (Row r : dciterBiaya.getAllRowsInRange()) {
                    String BNY =
                        r.getAttribute("BiayaNonYearly") == null ? "" :
                        r.getAttribute("BiayaNonYearly").toString();
                    String BN =
                        r.getAttribute("BiayaYearly") == null ? "" : r.getAttribute("BiayaYearly").toString();
                    if (BNY.length() == 0 && BN.length() == 0) {
                        if (!isInputValid) {
                            sErrMsg += ", ";
                        }
                        sErrMsg +=
                                "Tab Biaya : Biaya Non Yearly atau Biaya Yearly";
                        isInputValid = false;
                    }
                }
                if (isInputValid) {
                    eventOk();
                } else {
                    showPopup(sErrMsg + " Harus Diisi", potmessage);
                    tabBiaya.setDisclosed(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tabBiaya);
                }
            } else if (discType.equalsIgnoreCase("PROMOBARANG")) {
                DCIteratorBinding dciterPromoBonus =
                    ADFUtils.findIterator("PromoBonusView1Iterator");
                for (Row r : dciterPromoBonus.getAllRowsInRange()) {
                    String BNY =
                        r.getAttribute("DiscNonYearly") == null ? "" : r.getAttribute("DiscNonYearly").toString();
                    String BN =
                        r.getAttribute("DiscYearly") == null ? "" : r.getAttribute("DiscYearly").toString();
                    if (BNY.length() == 0 && BN.length() == 0) {
                        if (!isInputValid) {
                            sErrMsg += ", ";
                        }
                        sErrMsg +=
                                "Tab Promo Barang : Discount Non Yearly atau Discount Yearly";
                        isInputValid = false;
                    }
                }
                if (isInputValid) {
                    eventOk();
                } else {
                    showPopup(sErrMsg + " Harus Diisi", potmessage);
                    tabPromoBarang.setDisclosed(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tabPromoBarang);
                }
            } else if (discType.equalsIgnoreCase("POTONGAN")) {
                DCIteratorBinding dciterDiscount1 =
                    ADFUtils.findIterator("DiscountView1Iterator");
                for (Row r : dciterDiscount1.getAllRowsInRange()) {
                    String BNY =
                        r.getAttribute("DiscNonYearly") == null ? "" : r.getAttribute("DiscNonYearly").toString();
                    String BN =
                        r.getAttribute("DiscYearly") == null ? "" : r.getAttribute("DiscYearly").toString();
                    if (BNY.length() == 0 && BN.length() == 0) {
                        if (!isInputValid) {
                            sErrMsg += ", ";
                        }
                        sErrMsg +=
                                "Tab Potongan : Discount Non Yearly atau Discount Yearly";
                        isInputValid = false;
                    }
                }
                if (isInputValid) {
                    eventOk();
                } else {
                    showPopup(sErrMsg + " Harus Diisi", potmessage);
                    tabPotongan.setDisclosed(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tabPotongan);
                }
            }

            tabTargerCustomer.setDisclosed(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabTargerCustomer);
        } else {
            showPopup(sIterMsg + " Harus Diisi", potmessage);
        }
        /*
            OperationBinding operationBindingRollback =
                bindings.getOperationBinding("Rollback");
            operationBindingRollback.execute();
            */
        // Just do nothing !!!, kalau di rollback suka banyak yang ke reset jadi null soc nya.
    }


    private void eventOk() {
        BindingContainer bindings = getBindings();

        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userType = userData.getUserType();

        AttributeBinding regCustFlagAttr =
            (AttributeBinding)bindings.getControlBinding("RegCustFlag2");
        String regCustFlag = (String)regCustFlagAttr.getInputValue();

        AttributeBinding propIdAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalId");
        DBSequence propId = (DBSequence)propIdAttr.getInputValue();

        ArrayList<String> regAprvlOldList = new ArrayList<String>();
        ArrayList<String> regAprvlNewList = new ArrayList<String>();

        // Get old approval region pada proposal
        DCIteratorBinding dciterApprovalRegion =
            ADFUtils.findIterator("ApprovalRegPropView1Iterator");
        if (dciterApprovalRegion.getEstimatedRowCount() > 0) {
            for (Row r : dciterApprovalRegion.getAllRowsInRange()) {
                String regCode = (String)r.getAttribute("RegionCode");
                if (!regAprvlOldList.contains(regCode)) {
                    regAprvlOldList.add(regCode);
                }
            }
        }

        if (userType.equalsIgnoreCase(userHo)) {
            // If user HO, set approval region here.
            if (regCustFlag.equalsIgnoreCase(prodArea)) {
                DCIteratorBinding dciterArea =
                    ADFUtils.findIterator("ProdRegionAreaView1Iterator");

                for (Row r : dciterArea.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!regAprvlNewList.contains(regCode)) {
                        regAprvlNewList.add(regCode);
                    }
                }
            } else if (regCustFlag.equalsIgnoreCase(prodCustomer)) {
                DCIteratorBinding dciterCustomer =
                    ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
                for (Row r : dciterCustomer.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!regAprvlNewList.contains(regCode)) {
                        regAprvlNewList.add(regCode);
                    }
                }
            } else if (regCustFlag.equalsIgnoreCase(prodRegion)) {
                DCIteratorBinding dciterRegion =
                    ADFUtils.findIterator("ProdRegionView1Iterator");
                for (Row r : dciterRegion.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!regAprvlNewList.contains(regCode)) {
                        regAprvlNewList.add(regCode);
                    }
                }
            } else if (regCustFlag.equalsIgnoreCase(prodLocation)) {
                DCIteratorBinding dciterLocation =
                    ADFUtils.findIterator("ProdRegionLocView1Iterator");
                for (Row r : dciterLocation.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!regAprvlNewList.contains(regCode)) {
                        regAprvlNewList.add(regCode);
                    }
                }
            } else if (regCustFlag.equalsIgnoreCase(prodCustGroup)) {
                DCIteratorBinding dciterLocation =
                    ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
                for (Row r : dciterLocation.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    String[] arrayRegCode = regCode.split("\\|", -1);
                    for (int i = 0; i < arrayRegCode.length; i++) {
                        if (!regAprvlNewList.contains(arrayRegCode[i])) {
                            regAprvlNewList.add(arrayRegCode[i]);
                        }
                    }
                }
            } else {
                System.out.println("PRODUK CUSTOMER TYPE NOT RECOGNIZED ");
            }

            AttributeBinding userTypeCreatorAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            userTypeCreatorAttr.setInputValue(userHo);
            ArrayList<String> regAprvlUnionList =
                new ArrayList<String>(regAprvlOldList);
            for (String regCodeNew : regAprvlNewList) {
                if (!regAprvlUnionList.contains(regCodeNew)) {
                    regAprvlUnionList.add(regCodeNew);
                }
            }

            for (String regCode : regAprvlUnionList) {
                if (regAprvlOldList.contains(regCode) &&
                    !regAprvlNewList.contains(regCode)) {
                    Key keyRegCode =
                        new Key(new Object[] { propId.getValue(), regCode });
                    RowSetIterator rsiAprvlReg =
                        dciterApprovalRegion.getRowSetIterator();
                    Row[] rowApprovalRegion =
                        rsiAprvlReg.findByKey(keyRegCode, 1);
                    rowApprovalRegion[0].remove();
                } else if (!regAprvlOldList.contains(regCode) &&
                           regAprvlNewList.contains(regCode)) {
                    Row rowApprovalRegion =
                        dciterApprovalRegion.getRowSetIterator().createRow();
                    rowApprovalRegion.setNewRowState(Row.STATUS_INITIALIZED);
                    rowApprovalRegion.setAttribute("ProposalId",
                                                   propId.getValue());
                    rowApprovalRegion.setAttribute("RegionCode", regCode);

                    dciterApprovalRegion.getRowSetIterator().insertRow(rowApprovalRegion);
                    dciterApprovalRegion.setCurrentRowWithKey(rowApprovalRegion.getKey().toStringFormat(true));
                }
            }
        }

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();
        pdetailProduct.hide();
        DCBindingContainer bindings1 =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter =
            bindings1.findIteratorBinding("PromoProdukView1Iterator");
        iter.executeQuery();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }


    public void tabTargetEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            UserData userData =
                (UserData)JSFUtils.resolveExpression("#{UserData}");
            String userType = userData.getUserType();
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

            Integer cekArea = (int)dciterArea.getEstimatedRowCount();
            Integer cekCustomer = (int)dciterCustomer.getEstimatedRowCount();
            Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
            Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
            Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
            if (userType.equalsIgnoreCase(userHo)) {
                if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                      cekLoc > 0 || cekCustGroup > 0)) {
                    showPopup("Tab customer harus diisi", potmessage);
                    tabTargerCustomer.setDisclosed(true);
                }
            }
        }
        /*
            OperationBinding operationBindingRollback =
                bindings.getOperationBinding("Rollback");
            operationBindingRollback.execute();
            */
        // Just do nothing !!!, kalau di rollback suka banyak yang ke reset jadi null soc nya.

        //        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }


    public void tabBiayaEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            UserData userData =
                (UserData)JSFUtils.resolveExpression("#{UserData}");
            String userType = userData.getUserType();
            if (userType.equalsIgnoreCase(userHo)) {
                DCIteratorBinding dciterArea =
                    ADFUtils.findIterator("ProdRegionAreaView1Iterator");
                DCIteratorBinding dciterCustomer =
                    ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
                DCIteratorBinding dciterRegion =
                    ADFUtils.findIterator("ProdRegionView1Iterator");
                DCIteratorBinding dciterLoc =
                    ADFUtils.findIterator("ProdRegionLocView1Iterator");
                DCIteratorBinding dciterCustGroup =
                    ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

                Integer cekArea = (int)dciterArea.getEstimatedRowCount();
                Integer cekCustomer =
                    (int)dciterCustomer.getEstimatedRowCount();
                Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
                Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
                Integer cekCustGroup =
                    (int)dciterCustGroup.getEstimatedRowCount();
                if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                      cekLoc > 0 || cekCustGroup > 0)) {
                    showPopup("Tab customer harus diisi", potmessage);
                    tabTargerCustomer.setDisclosed(true);
                }
            }
        }
    }


    public void tabPromoBarang(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            UserData userData =
                (UserData)JSFUtils.resolveExpression("#{UserData}");
            String userType = userData.getUserType();
            if (userType.equalsIgnoreCase(userHo)) {
                DCIteratorBinding dciterArea =
                    ADFUtils.findIterator("ProdRegionAreaView1Iterator");
                DCIteratorBinding dciterCustomer =
                    ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
                DCIteratorBinding dciterRegion =
                    ADFUtils.findIterator("ProdRegionView1Iterator");
                DCIteratorBinding dciterLoc =
                    ADFUtils.findIterator("ProdRegionLocView1Iterator");
                DCIteratorBinding dciterCustGroup =
                    ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

                Integer cekArea = (int)dciterArea.getEstimatedRowCount();
                Integer cekCustomer =
                    (int)dciterCustomer.getEstimatedRowCount();
                Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
                Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
                Integer cekCustGroup =
                    (int)dciterCustGroup.getEstimatedRowCount();
                if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                      cekLoc > 0 || cekCustGroup > 0)) {
                    showPopup("Tab customer harus diisi", potmessage);
                    tabTargerCustomer.setDisclosed(true);
                }
            }
            
            DCIteratorBinding dciterUOM =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            DCIteratorBinding dciterUOMTarget =
                ADFUtils.findIterator("TargetView1Iterator");
            Row rTarget=dciterUOMTarget.getCurrentRow();
            String uomTar=(String)rTarget.getAttribute("Uom");
            ViewObject vouom=dciterUOM.getViewObject();
            if(dciterUOM.getEstimatedRowCount() > 0){
                for(Row r :dciterUOM.getAllRowsInRange()){
                r.setAttribute("Uom", uomTar);
                }
                OperationBinding operationBinding =
                    bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                dciterUOM.executeQuery();
            }
           
        }
    }

    public void tabPotonganEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            UserData userData =
                (UserData)JSFUtils.resolveExpression("#{UserData}");
            String userType = userData.getUserType();
            if (userType.equalsIgnoreCase(userHo)) {
                DCIteratorBinding dciterArea =
                    ADFUtils.findIterator("ProdRegionAreaView1Iterator");
                DCIteratorBinding dciterCustomer =
                    ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
                DCIteratorBinding dciterRegion =
                    ADFUtils.findIterator("ProdRegionView1Iterator");
                DCIteratorBinding dciterLoc =
                    ADFUtils.findIterator("ProdRegionLocView1Iterator");
                DCIteratorBinding dciterCustGroup =
                    ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

                Integer cekArea = (int)dciterArea.getEstimatedRowCount();
                Integer cekCustomer =
                    (int)dciterCustomer.getEstimatedRowCount();
                Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
                Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
                Integer cekCustGroup =
                    (int)dciterCustGroup.getEstimatedRowCount();
                if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                      cekLoc > 0 || cekCustGroup > 0)) {
                    showPopup("Tab customer harus diisi", potmessage);
                    tabTargerCustomer.setDisclosed(true);
                }
            }

            DCIteratorBinding dciterUOM =
                ADFUtils.findIterator("DiscountView1Iterator");
            DCIteratorBinding dciterUOMTarget =
                ADFUtils.findIterator("TargetView1Iterator");
            Row rTarget=dciterUOMTarget.getCurrentRow();
            String uomTar=(String)rTarget.getAttribute("Uom");
            ViewObject vouom=dciterUOM.getViewObject();
            if(dciterUOM.getEstimatedRowCount() > 0){
                for(Row r :dciterUOM.getAllRowsInRange()){
                r.setAttribute("Uom", uomTar);
                }
                OperationBinding operationBinding =
                    bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                dciterUOM.executeQuery();
            }
        }
    }

    public void setPdetailProduct(RichPopup pdetailProduct) {
        this.pdetailProduct = pdetailProduct;
    }

    public RichPopup getPdetailProduct() {
        return pdetailProduct;
    }

    public void cancelDetailProduct(ActionEvent actionEvent) {
        pdetailProduct.hide();
        tabTargerCustomer.setDisclosed(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabTargerCustomer);
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

    public void setTabTargetBtn(RichShowDetailItem tabTargetBtn) {
        this.tabTargetBtn = tabTargetBtn;
    }

    public RichShowDetailItem getTabTargetBtn() {
        return tabTargetBtn;
    }

    public void setTabTargerCustomer(RichShowDetailItem tabTargerCustomer) {
        this.tabTargerCustomer = tabTargerCustomer;
    }

    public RichShowDetailItem getTabTargerCustomer() {
        return tabTargerCustomer;
    }

    public void setTabBiaya(RichShowDetailItem tabBiaya) {
        this.tabBiaya = tabBiaya;
    }

    public RichShowDetailItem getTabBiaya() {
        return tabBiaya;
    }

    public void setBiayaNonYearly(String BiayaNonYearly) {
        this.BiayaNonYearly = BiayaNonYearly;
    }

    public String getBiayaNonYearly() {
        return BiayaNonYearly;
    }

    public void setBiayaYearly(String BiayaYearly) {
        this.BiayaYearly = BiayaYearly;
    }

    public String getBiayaYearly() {
        return BiayaYearly;
    }


    public void setTblListProductLocation(RichTable tblListProductLocation) {
        this.tblListProductLocation = tblListProductLocation;
    }

    public RichTable getTblListProductLocation() {
        return tblListProductLocation;
    }

    public void setTblListLocation(RichTable tblListLocation) {
        this.tblListLocation = tblListLocation;
    }

    public RichTable getTblListLocation() {
        return tblListLocation;
    }

    public void setTblListArea(RichTable tblListArea) {
        this.tblListArea = tblListArea;
    }

    public RichTable getTblListArea() {
        return tblListArea;
    }

    public void setUserTypeCreator(String UserTypeCreator) {
        this.UserTypeCreator = UserTypeCreator;
    }

    public String getUserTypeCreator() {
        return UserTypeCreator;
    }

    public void setTabPromoBarang(RichShowDetailItem tabPromoBarang) {
        this.tabPromoBarang = tabPromoBarang;
    }

    public RichShowDetailItem getTabPromoBarang() {
        return tabPromoBarang;
    }

    public void setTabPotongan(RichShowDetailItem tabPotongan) {
        this.tabPotongan = tabPotongan;
    }

    public RichShowDetailItem getTabPotongan() {
        return tabPotongan;
    }

    public void setTabTargetAndBudget(RichShowDetailItem tabTargetAndBudget) {
        this.tabTargetAndBudget = tabTargetAndBudget;
    }

    public RichShowDetailItem getTabTargetAndBudget() {
        return tabTargetAndBudget;
    }

    public void setItVariant(RichInputText itVariant) {
        this.itVariant = itVariant;
    }

    public RichInputText getItVariant() {
        return itVariant;
    }

    public void setLinkVariant(RichCommandImageLink linkVariant) {
        this.linkVariant = linkVariant;
    }

    public RichCommandImageLink getLinkVariant() {
        return linkVariant;
    }

    public void setPsubmitProposal(RichPopup psubmitProposal) {
        this.psubmitProposal = psubmitProposal;
    }

    public RichPopup getPsubmitProposal() {
        return psubmitProposal;
    }

    public void submitEvent(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discType = (String)discTypeAttr.getInputValue();
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userType = userData.getUserType();
        DCIteratorBinding dciterArea =
            ADFUtils.findIterator("ProdRegionAreaView1Iterator");
        DCIteratorBinding dciterCustomer =
            ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
        DCIteratorBinding dciterRegion =
            ADFUtils.findIterator("ProdRegionView1Iterator");
        DCIteratorBinding dciterLoc =
            ADFUtils.findIterator("ProdRegionLocView1Iterator");
        DCIteratorBinding dciterCustGroup =
            ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

        Integer cekArea = (int)dciterArea.getEstimatedRowCount();
        Integer cekCustomer = (int)dciterCustomer.getEstimatedRowCount();
        Integer cekRegion = (int)dciterRegion.getEstimatedRowCount();
        Integer cekLoc = (int)dciterLoc.getEstimatedRowCount();
        Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
        if (userType.equalsIgnoreCase(userHo)) {
            Boolean isSavedValid = true;
            String sSavedMsg = "";
            if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                  cekLoc > 0 || cekCustGroup > 0)) {
                if (!isSavedValid) {
                    sSavedMsg += ", ";
                }
                sSavedMsg += "Customer Pada Product Detail Harus Diisi";
                isSavedValid = false;
            }
            if (isSavedValid) {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                psubmitProposal.show(hints);
            } else {
                showPopup(sSavedMsg, potmessage);
            }
        } else {
            Boolean isIterValid = true;
            String sIterMsg = "";
            if (discType.equalsIgnoreCase("BIAYA")) {
                DCIteratorBinding dciterBiaya =
                    ADFUtils.findIterator("BiayaView1Iterator");
                if (dciterBiaya.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Biaya Pada Produk Detail";
                    isIterValid = false;
                }
            } else if (discType.equalsIgnoreCase("PROMOBARANG")) {
                DCIteratorBinding dciterPromoBonus =
                    ADFUtils.findIterator("PromoBonusView1Iterator");
                if (dciterPromoBonus.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Promo Pada Produk Detail";
                    isIterValid = false;
                }
            } else if (discType.equalsIgnoreCase("POTONGAN")) {
                DCIteratorBinding dciterDiscount =
                    ADFUtils.findIterator("DiscountView1Iterator");
                if (dciterDiscount.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Potongan Pada Produk Detail";
                    isIterValid = false;
                }
            }
            if (userType.equalsIgnoreCase(userArea)) {
                DCIteratorBinding dciterArea1 =
                    ADFUtils.findIterator("PropRegionAreaView1Iterator");
                DCIteratorBinding dciterCustomer1 =
                    ADFUtils.findIterator("PropRegionCustomerView1Iterator");
                DCIteratorBinding dciterRegion1 =
                    ADFUtils.findIterator("PropRegionView1Iterator");
                DCIteratorBinding dciterLoc1 =
                    ADFUtils.findIterator("PropRegionLocView1Iterator");
                DCIteratorBinding dciterCustGroup1 =
                    ADFUtils.findIterator("PropRegionCustGroupView1Iterator");

                Integer cekArea1 = (int)dciterArea1.getEstimatedRowCount();
                Integer cekCustomer1 =
                    (int)dciterCustomer1.getEstimatedRowCount();
                Integer cekRegion1 = (int)dciterRegion1.getEstimatedRowCount();
                Integer cekLoc1 = (int)dciterLoc1.getEstimatedRowCount();
                Integer cekCustGroup1 =
                    (int)dciterCustGroup1.getEstimatedRowCount();
                if (!(cekArea1 > 0 || cekCustomer1 > 0 || cekRegion1 > 0 ||
                      cekLoc1 > 0 || cekCustGroup1 > 0)) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Customer";
                    isIterValid = false;
                }
            }
            if (isIterValid) {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                psubmitProposal.show(hints);
            } else {
                showPopup(sIterMsg + " Harus Diisi", potmessage);
            }
        }
    }

    public void setLinkProduct(RichCommandImageLink linkProduct) {
        this.linkProduct = linkProduct;
    }

    public RichCommandImageLink getLinkProduct() {
        return linkProduct;
    }

    public void addPromoAddBuy(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("PromoAddBuyView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
    }

    public void addPromoBonus(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("PromoBonusView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        
        DCIteratorBinding dciterUOMTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row rTarget=dciterUOMTarget.getCurrentRow();
        String uomTar=(String)rTarget.getAttribute("Uom");
        
        
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        newRow.setAttribute("Uom", uomTar);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
    }

    public void addDiscount(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("DiscountView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        
        DCIteratorBinding dciterUOMTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row rTarget=dciterUOMTarget.getCurrentRow();
        String uomTar=(String)rTarget.getAttribute("Uom");
        
        
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        newRow.setAttribute("Uom", uomTar);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
    }
    public void setTblListCustGroup(RichTable tblListCustGroup) {
        this.tblListCustGroup = tblListCustGroup;
    }

    public RichTable getTblListCustGroup() {
        return tblListCustGroup;
    }

    public void setTblListProductCustGroup(RichTable tblListProductCustGroup) {
        this.tblListProductCustGroup = tblListProductCustGroup;
    }

    public RichTable getTblListProductCustGroup() {
        return tblListProductCustGroup;
    }

    public void setAvgQty(RichInputText avgQty) {
        this.avgQty = avgQty;
    }

    public void setItVariantAddBuy(RichInputText itVariantAddBuy) {
        this.itVariantAddBuy = itVariantAddBuy;
    }

    public RichInputText getItVariantAddBuy() {
        return itVariantAddBuy;
    }

    public RichInputText getAvgQty() {
        return avgQty;
    }

    public void setItProdukAddBuy(RichInputText itProdukAddBuy) {
        this.itProdukAddBuy = itProdukAddBuy;
    }

    public void calcAvgQtyEbs(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");
        CallableStatement cst = null;
        BigDecimal responQty = new BigDecimal(0);
        BigDecimal responPrice = new BigDecimal(0);
        String pItemNumber = "";

        AttributeBinding uomAttr =
            (AttributeBinding)bindings.getControlBinding("Uom1");
        String pUomPppc = (String)uomAttr.getInputValue();
        
        AttributeBinding prodNameAttr =
            (AttributeBinding)bindings.getControlBinding("ProductCombination");
        String prodName = (String)prodNameAttr.getInputValue() == null ? "" : (String)prodNameAttr.getInputValue();
        
        AttributeBinding prodItemCodeAttr =
            (AttributeBinding)bindings.getControlBinding("ProductItemCode");
        String prodItemCode = (String)prodItemCodeAttr.getInputValue() == null ? "" : (String)prodItemCodeAttr.getInputValue();
        
        if (prodItemCode.trim().equalsIgnoreCase("")) {       
            AttributeBinding prodVarCodeAttr =
                (AttributeBinding)bindings.getControlBinding("ProductVarCode");
            String prodVarCode = (String)prodVarCodeAttr.getInputValue();
            
            String[] arrayProdVar = prodVarCode.split("\\,", -1);
            for (int i = 0; i < arrayProdVar.length; i++) {
                pItemNumber = pItemNumber + prodName + "." + arrayProdVar[i] + ";";
            }    
        } else {           
            String[] arrayProdItem = prodItemCode.split("\\,", -1);
            for (int i = 0; i < arrayProdItem.length; i++) {
                pItemNumber = pItemNumber + arrayProdItem[i] + ";";
            }          
        }
        
        try {
            cst =
        promoProposalAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_PPPC_OVERAGE_QTY ('" +
                                                          pItemNumber + "', '" +
                                                          pUomPppc + "', ?, ?); END;", 0);
            cst.registerOutParameter(1, Types.NUMERIC);
            cst.registerOutParameter(2, Types.NUMERIC);
            cst.executeUpdate();
            responQty = cst.getBigDecimal(1);
            responPrice = cst.getBigDecimal(2);
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
        
        avgQty.setValue(responQty);

        AdfFacesContext.getCurrentInstance().addPartialTarget(avgQty);
    }

    public RichInputText getItProdukAddBuy() {
        return itProdukAddBuy;
    }

    public void priceListPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        String prodCombItem = "";
        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        AttributeBinding usrTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String srcUsrTypeCreator = (String)usrTypeCreatorAttr.getInputValue();
        AttributeBinding custTypeHoAttr =
            (AttributeBinding)bindings.getControlBinding("RegCustFlag2");
        AttributeBinding custTypeAreaAttr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");
        AttributeBinding prodCombAttr =
            (AttributeBinding)bindings.getControlBinding("ProductCombination");
        String prodComb = (String)prodCombAttr.getInputValue();

        // Get variant list
        ArrayList<String> prodVariantList = new ArrayList<String>();
        DCIteratorBinding dciterProdVariant =
            ADFUtils.findIterator("ProdukVariantView1Iterator");

        long rowCountVariant = dciterProdVariant.getEstimatedRowCount();
        if (rowCountVariant > 0) {
            for (Row r : dciterProdVariant.getAllRowsInRange()) {
                String variantCode = (String)r.getAttribute("ProdVariant");
                prodVariantList.add(variantCode);
            }
        }

        // Get item code list
        String prodItemCodeList = "";
        DCIteratorBinding dciterProdItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");

        long rowCountItem = dciterProdItem.getEstimatedRowCount();
        if (rowCountItem > 0) {
            int i = 1;
            for (Row r : dciterProdItem.getAllRowsInRange()) {
                String itemCode = (String)r.getAttribute("ProdItem");
                if (i < rowCountItem) {
                    prodItemCodeList = prodItemCodeList + itemCode + ";";
                } else {
                    prodItemCodeList = prodItemCodeList + itemCode;
                }
                i = i + 1;
            }
        }

        int variantSize = prodVariantList.size();
        if (variantSize == 1) {
            String currVariant = prodVariantList.get(0);
            if (currVariant.equalsIgnoreCase(variantAll) && rowCountItem > 0) {
                prodCombItem = prodItemCodeList;
            } else {
                prodCombItem = prodComb + "." + currVariant;
            }
        } else if (variantSize > 1) {
            int i = 1;
            for (String varCode : prodVariantList) {
                if (i < variantSize) {
                    prodCombItem =
                            prodCombItem + prodComb + "." + varCode + ";";
                } else {
                    prodCombItem = prodCombItem + prodComb + "." + varCode;
                }
                i = i + 1;
            }
        } else {
            JSFUtils.addFacesErrorMessage("Error",
                                          "Invalid produk combination.");
        }

        CallableStatement cst = null;
        String listCustomer = "";
        String custType = "";

        if (srcUsrTypeCreator.equalsIgnoreCase(userArea)) {
            // USER TYPE CREATOR == AREA
            custType = (String)custTypeAreaAttr.getInputValue();
            DCIteratorBinding dciterPropRegion =
                ADFUtils.findIterator("PropRegionView1Iterator");
            DCIteratorBinding dciterPropRegionCust =
                ADFUtils.findIterator("PropRegionCustomerView1Iterator");
            DCIteratorBinding dciterPropRegionArea =
                ADFUtils.findIterator("PropRegionAreaView1Iterator");
            DCIteratorBinding dciterPropRegionLoc =
                ADFUtils.findIterator("PropRegionLocView1Iterator");
            DCIteratorBinding dciterPropRegionCustGroup =
                ADFUtils.findIterator("PropRegionCustGroupView1Iterator");

            if (custType.equalsIgnoreCase(propRegion)) {
                long rowCount = dciterPropRegion.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterPropRegion.getAllRowsInRange()) {
                        String regCode = (String)r.getAttribute("RegionCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + regCode + ";";
                        } else {
                            listCustomer = listCustomer + regCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustomer)) {
                long rowCount = dciterPropRegionCust.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterPropRegionCust.getAllRowsInRange()) {
                        String custId =
                            String.valueOf(df.format(((Number)r.getAttribute("CustomerId")).getValue()));
                        if (i < rowCount) {
                            listCustomer = listCustomer + custId + ";";
                        } else {
                            listCustomer = listCustomer + custId;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(propArea)) {
                long rowCount = dciterPropRegionArea.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterPropRegionArea.getAllRowsInRange()) {
                        String areaCode = (String)r.getAttribute("AreaCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + areaCode + ";";
                        } else {
                            listCustomer = listCustomer + areaCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(propLocation)) {
                long rowCount = dciterPropRegionLoc.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterPropRegionLoc.getAllRowsInRange()) {
                        String locCode =
                            (String)r.getAttribute("LocationCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + locCode + ";";
                        } else {
                            listCustomer = listCustomer + locCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustGroup)) {
                long rowCount =
                    dciterPropRegionCustGroup.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r :
                         dciterPropRegionCustGroup.getAllRowsInRange()) {
                        String custGroup = (String)r.getAttribute("CustGroup");
                        if (i < rowCount) {
                            listCustomer = listCustomer + custGroup + ";";
                        } else {
                            listCustomer = listCustomer + custGroup;
                        }
                        i = i + 1;
                    }
                }
            } else {
                JSFUtils.addFacesWarningMessage("\"Customer Type\" tidak dikenali.");
            }
        } else if (srcUsrTypeCreator.equalsIgnoreCase(userHo)) {
            // USER TYPE CREATOR == HO
            custType = (String)custTypeHoAttr.getInputValue();
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            DCIteratorBinding dciterLoc =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");

            if (custType.equalsIgnoreCase(prodRegion)) {
                long rowCount = dciterRegion.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterRegion.getAllRowsInRange()) {
                        String regCode = (String)r.getAttribute("RegionCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + regCode + ";";
                        } else {
                            listCustomer = listCustomer + regCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(prodCustomer)) {
                long rowCount = dciterCustomer.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterCustomer.getAllRowsInRange()) {
                        String custId =
                            String.valueOf(df.format(((Number)r.getAttribute("CustomerId")).getValue()));
                        if (i < rowCount) {
                            listCustomer = listCustomer + custId + ";";
                        } else {
                            listCustomer = listCustomer + custId;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(prodArea)) {
                long rowCount = dciterArea.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterArea.getAllRowsInRange()) {
                        String areaCode = (String)r.getAttribute("AreaCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + areaCode + ";";
                        } else {
                            listCustomer = listCustomer + areaCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(prodLocation)) {
                long rowCount = dciterLoc.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterLoc.getAllRowsInRange()) {
                        String locCode =
                            (String)r.getAttribute("LocationCode");
                        if (i < rowCount) {
                            listCustomer = listCustomer + locCode + ";";
                        } else {
                            listCustomer = listCustomer + locCode;
                        }
                        i = i + 1;
                    }
                }
            } else if (custType.equalsIgnoreCase(prodCustGroup)) {
                long rowCount = dciterCustGroup.getEstimatedRowCount();
                if (rowCount > 0) {
                    int i = 1;
                    for (Row r : dciterCustGroup.getAllRowsInRange()) {
                        String custGroup = (String)r.getAttribute("CustGroup");
                        if (i < rowCount) {
                            listCustomer = listCustomer + custGroup + ";";
                        } else {
                            listCustomer = listCustomer + custGroup;
                        }
                        i = i + 1;
                    }
                }
            } else {
                JSFUtils.addFacesWarningMessage("\"Customer Type\" tidak dikenali.");
            }
        }

        String loggedUserName = userData.getUserNameLogin();
        /*
        System.out.println("=================================================");
        System.out.println("CUST TYPE: " + custType);        
        System.out.println("PROD COMB: " + prodCombItem);        
        System.out.println("LIST CUST: " + listCustomer);        
        System.out.println("USER NAME: " + loggedUserName);        
        System.out.println("=================================================");
        */
        try {
            cst =
promoProposalAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_PPPC_GET_PRICE_LIST.INSERT_TABLE_PRICE_LIST('" +
                                                           custType + "', '" +
                                                           prodCombItem +
                                                           "', '" +
                                                           listCustomer +
                                                           "', '" +
                                                           loggedUserName +
                                                           "'); END;", 0);
            cst.executeUpdate();
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

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePriceList");
        operationBinding.execute();
    }

    public void setItBonusVariant(RichInputText itBonusVariant) {
        this.itBonusVariant = itBonusVariant;
    }

    public void priceListDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BigDecimal tgtQty =
                new BigDecimal(itTargetQty.getValue().toString().replaceAll(",",
                                                                            ""));
            DCIteratorBinding dciterPriceList =
                ADFUtils.findIterator("FcsPppcPriceListView1Iterator");
            Number priceVal = new Number(0);
            if (dciterPriceList.getEstimatedRowCount() > 0) {
                priceVal = (Number)dciterPriceList.getCurrentRow().getAttribute("Price") == null ? new Number(0) : (Number)dciterPriceList.getCurrentRow().getAttribute("Price");
            } 
            
            itTargetHarga.setValue(priceVal);

            BigDecimal tgtHarga = new BigDecimal(priceVal.getValue());

            BigDecimal totalValue = tgtQty.multiply(tgtHarga);
            oracle.jbo.domain.Number number = null;
            try {
                number =
                        new oracle.jbo.domain.Number(df2dgt.format(totalValue).toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            itValueTotal.setValue(number);

            AdfFacesContext.getCurrentInstance().addPartialTarget(itTargetHarga);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itValueTotal);
        }
    }

    public void custTypeReturnPopupListener(ReturnPopupEvent returnPopupEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();

        AttributeBinding exclCustByAttr =
            (AttributeBinding)bindings.getControlBinding("ExclCustBy");
        exclCustByAttr.setInputValue(null);
        /*
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();
        */
        AttributeBinding promoProdukIdAttr =
            (AttributeBinding)bindings.getControlBinding("PromoProdukId");
        DBSequence promoProdukId =
            (DBSequence)promoProdukIdAttr.getInputValue();

        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        PromoProdukViewImpl promoProdView =
            (PromoProdukViewImpl)dciter.getViewObject();
        promoProdView.setWhereClause("PromoProduk.PROMO_PRODUK_ID = " +
                                     df.format(promoProdukId.getValue()));
        promoProdView.executeQuery();

        itlovExclCustBy.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itlovExclCustBy);
    }

    public RichInputText getItBonusVariant() {
        return itBonusVariant;
    }

    public RichSelectOneChoice getItLovProposalType() {
        return itLovProposalType;
    }

    public void setItLovProposalType(RichSelectOneChoice itLovProposalType) {
        this.itLovProposalType = itLovProposalType;
    }

    public void setItlovExclCustBy(RichInputListOfValues itlovExclCustBy) {
        this.itlovExclCustBy = itlovExclCustBy;
    }

    public RichInputListOfValues getItlovExclCustBy() {
        return itlovExclCustBy;
    }


    public void windowExcludeAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExcludeArea");
        operationBinding.execute();

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclArea);
    }

    public void windowExcludeRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExcludeRegion");
        operationBinding.execute();

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclRegion);
    }

    public void windowExcludeCustReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExcludeCustomer");
        operationBinding.execute();

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclCustomer);
    }

    public void windowExcludeLocReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExcludeLoc");
        operationBinding.execute();

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclLocation);
    }

    public void setTblListExclRegion(RichTable tblListExclRegion) {
        this.tblListExclRegion = tblListExclRegion;
    }

    public RichTable getTblListExclRegion() {
        return tblListExclRegion;
    }

    public void setTblListExclArea(RichTable tblListExclArea) {
        this.tblListExclArea = tblListExclArea;
    }

    public RichTable getTblListExclArea() {
        return tblListExclArea;
    }

    public void setTblListExclCustomer(RichTable tblListExclCustomer) {
        this.tblListExclCustomer = tblListExclCustomer;
    }

    public RichTable getTblListExclCustomer() {
        return tblListExclCustomer;
    }

    public void newProposalEvent(ActionEvent actionEvent) {
        /*
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        */
        
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrDivId = userData.getUserDivision();
        String usrType = userData.getUserType();

        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciterProposal =
            (DCIteratorBinding)bindings.get("ProposalView1Iterator");
        RowSetIterator rsi = dciterProposal.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        if (usrDivId.equalsIgnoreCase("NONFOOD")) {
            newRow.setAttribute("ProposalType", "NONFOOD");
        } else {
            newRow.setAttribute("ProposalType", "FOOD");
        }
        newRow.setAttribute("UserTypeCreator", usrType);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);       
        
    }

    public void setTblListExclLocation(RichTable tblListExclLocation) {
        this.tblListExclLocation = tblListExclLocation;
    }

    public RichTable getTblListExclLocation() {
        return tblListExclLocation;
    }

    public void windowExclProdukRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukRegion");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductRegion);
    }

    public void setSelectItSocProposalType(UISelectItems selectItSocProposalType) {
        this.selectItSocProposalType = selectItSocProposalType;
    }

    public void windowExclProdukAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukArea");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);
    }

    public UISelectItems getSelectItSocProposalType() {
        return selectItSocProposalType;
    }

    public void windowExclProdukLocReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukLoc");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);
    }

    public void setCustomLovproposalType(RichSelectOneChoice customLovproposalType) {
        this.customLovproposalType = customLovproposalType;
    }

    public void windowExclProdukCustReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukCust");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductCust);
    }

    public RichSelectOneChoice getCustomLovproposalType() {
        return customLovproposalType;
    }

    public void setTblListExclProductRegion(RichTable tblListExclProductRegion) {
        this.tblListExclProductRegion = tblListExclProductRegion;
    }

    public void setListProposalType(ArrayList listProposalType) {
        this.listProposalType = listProposalType;
    }

    public RichTable getTblListExclProductRegion() {
        return tblListExclProductRegion;
    }

    public void setTblListExclProductArea(RichTable tblListExclProductArea) {
        this.tblListExclProductArea = tblListExclProductArea;
    }

    public ArrayList getListProposalType() {
        return listProposalType;
    }

    public RichTable getTblListExclProductArea() {
        return tblListExclProductArea;
    }

    public void setTblListExclProductLoc(RichTable tblListExclProductLoc) {
        this.tblListExclProductLoc = tblListExclProductLoc;
    }

    public void setItLovProdCategory(RichInputListOfValues itLovProdCategory) {
        this.itLovProdCategory = itLovProdCategory;
    }

    public RichInputListOfValues getItLovProdCategory() {
        return itLovProdCategory;
    }

    public void setItCategory(RichOutputText itCategory) {
        this.itCategory = itCategory;
    }

    public RichOutputText getItCategory() {
        return itCategory;
    }

    public void setSocLovDiv(RichSelectOneChoice socLovDiv) {
        this.socLovDiv = socLovDiv;
    }

    public RichSelectOneChoice getSocLovDiv() {
        return socLovDiv;
    }

    public RichTable getTblListExclProductLoc() {
        return tblListExclProductLoc;
    }

    public void setTblListExclProductCust(RichTable tblListExclProductCust) {
        this.tblListExclProductCust = tblListExclProductCust;
    }
    
    public RichTable getTblListExclProductCust() {
        return tblListExclProductCust;
    }

    public void setSwitchExclCust(UIXSwitcher switchExclCust) {
        this.switchExclCust = switchExclCust;
    }

    public UIXSwitcher getSwitchExclCust() {
        return switchExclCust;
    }

    public void itlovExcludePilih(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = this.getBindings();
        AttributeBinding usrTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String usrTypeCreator = (String)usrTypeCreatorAttr.getInputValue();

        String chgNewVal = (String)valueChangeEvent.getNewValue();
        if (chgNewVal.equalsIgnoreCase(prodArea) &&
            usrTypeCreator.equalsIgnoreCase(userHo)) {
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductCust);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);

        } else if (chgNewVal.equalsIgnoreCase(prodCustomer) &&
                   usrTypeCreator.equalsIgnoreCase(userHo)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);

        } else if (chgNewVal.equalsIgnoreCase(prodRegion) &&
                   usrTypeCreator.equalsIgnoreCase(userHo)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductCust);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);

        } else if (chgNewVal.equalsIgnoreCase(prodLocation) &&
                   usrTypeCreator.equalsIgnoreCase(userHo)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductCust);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductRegion);

        } else if (chgNewVal.equalsIgnoreCase(propArea) &&
                   usrTypeCreator.equalsIgnoreCase(userArea)) {
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclPropCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclCustomer);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclPropCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclPropCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclLocation);

        } else if (chgNewVal.equalsIgnoreCase(propCustomer) &&
                   usrTypeCreator.equalsIgnoreCase(userArea)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclPropCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclArea);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclPropCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclRegion);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclPropCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclLocation);

        } else if (chgNewVal.equalsIgnoreCase(propRegion) &&
                   usrTypeCreator.equalsIgnoreCase(userArea)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclPropCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclPropCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclCustomer);

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclPropCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclLocation);

        } else if (chgNewVal.equalsIgnoreCase(propLocation) &&
                   usrTypeCreator.equalsIgnoreCase(userArea)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclPropCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclArea);

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclPropCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclCustomer);

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclPropCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclRegion);

        } else {
            System.out.println("NO CHANGE ON PROD EXCLUDE CUSTOMER");
            JSFUtils.addFacesErrorMessage("Error",
                                          "Customer option produk not recognized");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchExclCust);
    }

    public void setPgMainDetail(RichPanelSplitter pgMainDetail) {
        this.pgMainDetail = pgMainDetail;
    }

    public RichPanelSplitter getPgMainDetail() {
        return pgMainDetail;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserDivision() {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrDivId = userData.getUserDivision();
        Integer propTypeIdx = (Integer)itLovProposalType.getValue();
        System.out.println("PROP IDX: " + propTypeIdx);
        DCIteratorBinding dciterProposal =
            ADFUtils.findIterator("ProposalView1Iterator");
        if (dciterProposal.getEstimatedRowCount() > 0) {
            if ((usrDivId.equalsIgnoreCase("NONFOOD") && propTypeIdx.compareTo(idxNonFood) == 0) || 
                (usrDivId.equalsIgnoreCase("NONFOOD") && propTypeIdx.compareTo(idxNonFood) != 0)) {
                itLovProposalType.setSubmittedValue("1");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "NONFOOD";
            } else if ((usrDivId.equalsIgnoreCase("FOOD") && propTypeIdx.compareTo(idxFood) == 0) || 
                       (usrDivId.equalsIgnoreCase("FOOD") && propTypeIdx.compareTo(idxFood) != 0)) {
                itLovProposalType.setSubmittedValue("0");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "FOOD";
            } else if (usrDivId.equalsIgnoreCase("ALL") && propTypeIdx.compareTo(idxNonFood) == 0) {
                itLovProposalType.setSubmittedValue("1");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "NONFOOD";
            } else if (usrDivId.equalsIgnoreCase("ALL") && propTypeIdx.compareTo(idxFood) == 0) {
                itLovProposalType.setSubmittedValue("0");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "FOOD";
            } else {
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                RowSetIterator rsiPromoProduk = dciterPromoProduk.getRowSetIterator();
                if (dciterPromoProduk.getEstimatedRowCount() > 0) {
                    Row promoProdukRow = rsiPromoProduk.first();
                    String kodeCategory = (String)promoProdukRow.getAttribute("ProductCategory");
                    if (kodeCategory.equalsIgnoreCase(prodCatCodeFood)) {                
                        itLovProposalType.setSubmittedValue("0");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                        userDivision = "FOOD";
                    } else {                
                        itLovProposalType.setSubmittedValue("1");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                        userDivision = "NONFOOD";
                    }
                } else {
                    itLovProposalType.setSubmittedValue("0");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                    userDivision = "FOOD";
                }                
            }
        }
        return userDivision;
    }

    public void refreshOntop(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableListPotongan);
    }

    public void refreshMf(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableListPotongan);
    }

    public void setTableListPotongan(RichTable tableListPotongan) {
        this.tableListPotongan = tableListPotongan;
    }

    public RichTable getTableListPotongan() {
        return tableListPotongan;
    }
}
