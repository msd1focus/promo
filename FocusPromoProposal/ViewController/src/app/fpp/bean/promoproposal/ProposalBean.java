package app.fpp.bean.promoproposal;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.bean.useraccessmenu.UserData;
import app.fpp.model.am.PromoProposalAMImpl;
import app.fpp.model.views.approval.ApprovalReceiverNewProposalViewImpl;
import app.fpp.model.views.approval.DocApprovalViewImpl;
import app.fpp.model.views.masterdata.ebs.FcsViewCategCombinationViewImpl;
import app.fpp.model.views.promoproposal.FindRegionCodeImpl;
import app.fpp.model.views.promoproposal.FindRegionCodeRowImpl;
import app.fpp.model.views.promoproposal.PromoProdukViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateBiayaViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateDiscountViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdExclAreaViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdExclCustomerViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdExclLocViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdExclRegionViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionAreaViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionCustGroupViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionCustomerViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionLocViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdRegionViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdukItemViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateProdukVariantViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicatePromoBonusViewImpl;
import app.fpp.model.views.promoproposal.duplicate.DuplicateTargetViewImpl;
import app.fpp.model.views.promoproposal.validation.ProdVariantValidationViewImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichMessages;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.format.DefaultDateFormatter;
import oracle.jbo.format.FormatErrorException;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
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
    private Integer idxFood = 1;
    private Integer idxNonFood = 2;
    private String prodCatCodeFood = "CT001";
    private String prodCatCodeNonFood = "CT002";
    private String prodDescCodeFood = "FOOD";
    private String prodDescCodeNonFood = "NON FOOD";
    private String userDivision;
    private RichTable tableListPotongan;
    private RichInputListOfValues socTypePotongan;
    private RichInputText otOnTop;
    private RichInputText otMF;
    private RichInputText rowOntop;
    private RichInputText rowMf;
    BigDecimal valueMf = BigDecimal.ZERO;
    private BigDecimal bdHundred = new BigDecimal("100");
    private static String prodExclArea = "AREA";
    private static String prodExclCustomer = "CUSTOMER";
    private static String prodExclRegion = "REGION";
    private static String prodExclLocation = "LOCATION";
    private RichInputText otRasioOntop;
    private RichInputText otRasioMf;
    private RichInputText otRasioTotal;
    private RichInputText itPromoBonusOntop;
    private RichInputText itPromoBonusMf;
    private static String userCustArea = "AREA";
    private static String userCustCustomer = "CUSTOMER";
    private static String userCustRegion = "REGION";
    private static String userCustLocation = "LOCATION";
    private static String userCustCustGroup = "CUSTGROUP";
    private static String userCustInvalid = "INVALID";
    private RichInputText itPricePromoBonus;
    private RichInputText itQtyPromoboBonus;
    private RichInputText otBrgOnTop;
    private RichInputText otBrgMf;
    private RichInputText otBrgRasioOnTop;
    private RichInputText otBrgRasioMf;
    private RichInputText otBrgRasioTotal;
    private RichCommandButton btnOkpromoDetail;
    private RichInputText itProductItem;
    private RichInputDate idHistFrom;
    private RichInputDate idHistTo;
    private RichInputDate idPeriodProgFrom;
    private RichInputDate idPeriodeTo;
    private RichInputText otBiaOntop;
    private RichInputText otBiaMf;
    private RichInputText otBiaRasioOntop;
    private RichInputText otBiaRasioMf;
    private RichInputText otBiaRasioTotal;
    private RichInputText rowBiaOntop;
    private RichInputText rowBiaMf;
    private static String backDateBlockRegion = "R1-0";
    private RichInputDate newPromoPeriodFrom;
    private RichInputDate newPromoPeriodTo;
    private String textCopyProposal;
    private RichMessages copyPropMessage;
    private RichInputText itDaysCount;
    private RichOutputText otdaysview;
    private RichInputText itProgDays;
    private static String wflowFoodHo = "PROPOSAL FOOD HO";
    private static String wflowFoodArea = "PROPOSAL FOOD";
    private static String wflowNonFoodHo = "PROPOSAL NON FOOD HO";
    private static String wflowNonFoodArea = "PROPOSAL NON FOOD";
    private RichSelectOneChoice socMixQtyPromo;
    private UIXSwitcher switchMain;
    private RichInputListOfValues itlovUomTarget;
    private RichCommandButton btnPriceListpromoBrng;
    private RichPopup ppricelistTarget;
    private RichOutputText otHistFrom;
    private RichOutputText otHistTo;
    private Number zeroNumber = new Number(0);
    private Number maxNumber = new Number(999999);

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
        if (itProgDays.getValue() == null) {
            itProgDays.setValue(new Number(0));
        }
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
                String usrName = userData.getUserNameLogin();
                Integer propTypeIdx = (Integer)itLovProposalType.getValue();
                String aprvlFlowNm = "";

                if (propTypeIdx.compareTo(idxFood) == 0 &&
                    userType.equalsIgnoreCase(userHo)) {
                    aprvlFlowNm = wflowFoodHo;
                } else if (propTypeIdx.compareTo(idxFood) == 0 &&
                           userType.equalsIgnoreCase(userArea)) {
                    aprvlFlowNm = wflowFoodArea;
                } else if (propTypeIdx.compareTo(idxNonFood) == 0 &&
                           userType.equalsIgnoreCase(userHo)) {
                    aprvlFlowNm = wflowNonFoodHo;
                } else if (propTypeIdx.compareTo(idxNonFood) == 0 &&
                           userType.equalsIgnoreCase(userArea)) {
                    aprvlFlowNm = wflowNonFoodArea;
                } else {
                    System.out.println("ERROR: Approval flow name not recognized.");
                }

                ApprovalReceiverNewProposalViewImpl proposalReceiver =
                    promoProposalAM.getApprovalReceiverNewProposalView1();
                proposalReceiver.setNamedWhereClauseParam("aprvlFlowNm",
                                                          aprvlFlowNm);
                proposalReceiver.setNamedWhereClauseParam("usrName", usrName);
                proposalReceiver.executeQuery();

                if (proposalReceiver.getEstimatedRowCount() > 0) {
                    try {
                        OperationBinding operationAddApproval =
                            bindings.getOperationBinding("addDocApproval");
                        operationAddApproval.execute();
                    } catch (JboException e) {
                        JSFUtils.addFacesErrorMessage("Error",
                                                      e.getBaseMessage());
                    }

                    //Save all changed values
                    OperationBinding operationCommitSubmit =
                        bindings.getOperationBinding("Commit");
                    operationCommitSubmit.execute();

                    OperationBinding operationBinding =
                        bindings.getOperationBinding("ExecutePromoProduct");
                    operationBinding.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panCollMain);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnCancel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnSave);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnSubmit);
                } else {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  "User penerima pada flow approval tidak ditemukan.");
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
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }

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
                    String ppId =
                        prodItemRow.getAttribute("PromoProdukId").toString();
                    if (ppId.equalsIgnoreCase(PromoProdukIdSel)) {
                        prodItemRow.remove();
                        linkProduct.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(linkProduct);
                    }
                }
                rsiProdItem.closeRowSetIterator();
            } else {
                DCIteratorBinding dciterProdItem =
                    ADFUtils.findIterator("ProdukItemView1Iterator");
                RowSetIterator rsiProdItem =
                    dciterProdItem.getRowSetIterator();
                for (Row prodItemRow : dciterProdItem.getAllRowsInRange()) {
                    String ppId =
                        prodItemRow.getAttribute("PromoProdukId").toString();
                    if (ppId.equalsIgnoreCase(PromoProdukIdSel)) {
                        rsiProdItem.closeRowSetIterator();
                        linkProduct.setVisible(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(linkProduct);
                    }
                }
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(itVariant);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itProductItem);
        AdfFacesContext.getCurrentInstance().addPartialTarget(linkProduct);
    }

    public void windowItemReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itProductItem);
    }

    public void windowAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteArea");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);
    }

    public void windowRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteRegion");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);
    }

    public void windowCustomerReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteCustomer");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListCustomer);
    }

    public void windowLocationReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteLocation");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListLocation);
    }

    public void windowCustGroupReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteCustGroup");
        operationBinding.execute();

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
        clearAllCustExclude();
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
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }

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
                String idRem = r.getAttribute("PromoProdukId").toString();
                if (idRem.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", PromoProdukIdSel);
            row.setAttribute("ProdVariant", "ALL");
            Row row1 = dciter.getCurrentRow();
            row1.setAttribute("ProductBrand", "ALL");
            row1.setAttribute("ProductExt", "ALL");
            row1.setAttribute("ProductPack", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            dciter.executeQuery();

            itlovProdBrand.setValue("ALL");
            itlovProdExtention.setValue("ALL");
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdBrand);
            ctx.addPartialTarget(itlovProdExtention);
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(itProductItem);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            String idRemItem =
                itemRow.getAttribute("PromoProdukId").toString();
            if (idRemItem.equalsIgnoreCase(PromoProdukIdSel)) {
                itemRow.remove();
            }
        }
        rsiItem.closeRowSetIterator();
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
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }
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
                String idRem = r.getAttribute("PromoProdukId").toString();
                if (idRem.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            ViewObject voTableData = dciter.getViewObject();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", PromoProdukIdSel);
            row.setAttribute("ProdVariant", "ALL");
            Row row1 = dciter.getCurrentRow();
            row1.setAttribute("ProductExt", "ALL");
            row1.setAttribute("ProductPack", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            dciter.executeQuery();

            itlovProdExtention.setValue("ALL");
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdExtention);
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(itProductItem);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            String idRemItem =
                itemRow.getAttribute("PromoProdukId").toString();
            if (idRemItem.equalsIgnoreCase(PromoProdukIdSel)) {
                itemRow.remove();
            }
        }
        rsiItem.closeRowSetIterator();

    }

    public void productExtentionChanged(ValueChangeEvent valueChangeEvent) {
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }
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
                String idRem = r.getAttribute("PromoProdukId").toString();
                if (idRem.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", PromoProdukIdSel);
            row.setAttribute("ProdVariant", "ALL");
            Row row1 = dciter.getCurrentRow();
            row1.setAttribute("ProductPack", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            dciter.executeQuery();
            itlovProdPackaging.setValue("ALL");

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itlovProdPackaging);
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(itProductItem);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            String idRemItem =
                itemRow.getAttribute("PromoProdukId").toString();
            if (idRemItem.equalsIgnoreCase(PromoProdukIdSel)) {
                itemRow.remove();
            }
        }
        rsiItem.closeRowSetIterator();
    }

    public void productPackagingChanged(ValueChangeEvent valueChangeEvent) {
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }
        if (itlovProdPackaging.getValue().toString().equalsIgnoreCase("ALL")) {
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");

            for (Row r : iter.getAllRowsInRange()) {
                String idRem = r.getAttribute("PromoProdukId").toString();
                if (idRem.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }

            Row row = iter.getNavigatableRowIterator().createRow();
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("PromoProdukId", PromoProdukIdSel);
            row.setAttribute("ProdVariant", "ALL");
            iter.getRowSetIterator().insertRow(row);
            iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            dciter.executeQuery();

            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(itProductItem);
        } else {

            if (rowSelected.getAttribute("PromoProdukId") != null) {
                PromoProdukIdSel =
                        rowSelected.getAttribute("PromoProdukId").toString();
            }
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProdukVariantView1Iterator");
            DCIteratorBinding dciter =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            for (Row r : iter.getAllRowsInRange()) {
                String idRem = r.getAttribute("PromoProdukId").toString();
                if (idRem.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            dciter.executeQuery();
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();

            ctx.addPartialTarget(itVariant);
            ctx.addPartialTarget(itProductItem);
        }

        DCIteratorBinding dciterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        RowSetIterator rsiItem = dciterItem.getRowSetIterator();
        for (Row itemRow : dciterItem.getAllRowsInRange()) {
            String idRemItem =
                itemRow.getAttribute("PromoProdukId").toString();
            if (idRemItem.equalsIgnoreCase(PromoProdukIdSel)) {
                itemRow.remove();
            }
        }
        rsiItem.closeRowSetIterator();

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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
            operationBinding.execute();
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
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        boolean backDateOk = false;

        java.util.Date date2 =
            new java.util.Date(idPeriodProgFrom.getValue().toString());
        java.util.Date today = new java.util.Date();

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, -1);
        Date yesterday = c.getTime();

        AttributeBinding proposalTypeAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalType");
        Integer propTypeIdx = (Integer)proposalTypeAttr.getInputValue();

        AttributeBinding custTypeAttr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");
        String custType = (String)custTypeAttr.getInputValue();

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

        ArrayList<String> custRegCodeList = new ArrayList<String>();

        if (date2.before(yesterday)) {
            if (custType.equalsIgnoreCase(propRegion)) {
                for (Row r : dciterRegion.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propArea)) {
                for (Row r : dciterArea.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propArea, (String)r.getAttribute("AreaCode"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propLocation)) {
                for (Row r : dciterLoc.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propLocation, (String)r.getAttribute("LocationCode"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustGroup)) {
                for (Row r : dciterCustGroup.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propCustGroup, (String)r.getAttribute("CustGroup"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustomer)) {
                for (Row r : dciterCustomer.getAllRowsInRange()) {
                    oracle.jbo.domain.Number custId =
                        (oracle.jbo.domain.Number)r.getAttribute("CustomerId");
                    String regCode =
                        findCustRegCode(propCustomer, String.valueOf(custId.bigIntegerValue().intValue()));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            }

            if (custRegCodeList.contains(backDateBlockRegion) &&
                custRegCodeList.size() == 1) {
                backDateOk = true;
            } else {
                backDateOk = false;
            }

        } else {
            backDateOk = true;
        }

        if (backDateOk) {
            c.setTime(today);
            Integer valFoodDate = 0;
            if (propTypeIdx.compareTo(idxFood) == 0) {
                c.add(Calendar.DATE, 6);
                Date todayAdd7 = c.getTime();

                if (date2.after(yesterday) && date2.compareTo(todayAdd7) < 0) {
                    valFoodDate = 1;
                } else {
                    valFoodDate = 0;
                }
            } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
                c.add(Calendar.DATE, 9);
                Date todayAdd10 = c.getTime();

                if (date2.after(yesterday) &&
                    date2.compareTo(todayAdd10) < 0) {
                    valFoodDate = 2;
                } else {
                    valFoodDate = 0;
                }
            }

            if (valFoodDate.compareTo(0) == 0) {

                // Validate product category combination
                PromoProposalAMImpl promoProposalAM =
                    (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");
                DCIteratorBinding dciter =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Key prodKey = null;
                HashMap<Integer, String> prodIdComb =
                    new HashMap<Integer, String>();
                int i = 0;
                for (Row currRow : dciter.getAllRowsInRange()) {
                    String prodCategory =
                        (String)currRow.getAttribute("ProductCategory");
                    String prodClass =
                        (String)currRow.getAttribute("ProductClass");
                    String prodBrand =
                        (String)currRow.getAttribute("ProductBrand");
                    String prodExt =
                        (String)currRow.getAttribute("ProductExt");
                    String prodPack =
                        (String)currRow.getAttribute("ProductPack");
                    String prodCombination =
                        prodCategory + "." + prodClass + "." + prodBrand +
                        "." + prodExt + "." + prodPack;

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
                            String fullComb =
                                prodCombination + "." + currProdVariant;
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
            } else if (valFoodDate.compareTo(1) == 0) {
                idPeriodProgFrom.setSubmittedValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                this.showPopup("Pengajuan proposal periode promo FOOD minimal harus untuk H+7",
                               potmessage);
            } else if (valFoodDate.compareTo(2) == 0) {
                idPeriodProgFrom.setSubmittedValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                this.showPopup("Pengajuan proposal periode promo NON FOOD minimal harus untuk H+10",
                               potmessage);
            }
        } else {
            JSFUtils.addFacesWarningMessage("Pengajuan proposal ini tidak diperkenankan untuk \"Back Date\"");
        }
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
            new BigDecimal(itTargetQty.getValue() == "" ? "0" : itTargetQty.getValue() == null ? "0" : itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue() == "" ? "0" : itTargetHarga.getValue() == null ? "0" : itTargetHarga.getValue().toString().replaceAll(",",
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
            new BigDecimal(itTargetQty.getValue() == "" ? "0" : itTargetQty.getValue() == null ? "0" : itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue() == "" ? "0" : itTargetHarga.getValue() == null ? "0" : itTargetHarga.getValue().toString().replaceAll(",",
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

        clearAllCustExclude();
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

    public void addPromoProduk(ActionEvent actionEvent) {
        boolean canCreateNew = true;
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();

        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrCustomer =
            userData.getUserCustomer() == null ? "" : userData.getUserCustomer();

        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("PromoProdukView1Iterator");
        long produkRowCount = dciter.getEstimatedRowCount();
        
        for (Row produkRow : dciter.getAllRowsInRange()) {
            Integer rowStatus =
                (Integer)produkRow.getAttribute("CheckRowStatus");
            String validComb =
                (String)produkRow.getAttribute("ValidComb");
            if ((rowStatus == 0 && produkRowCount > 0) || 
                (!("Y").equalsIgnoreCase(validComb) && produkRowCount > 0)) {
                canCreateNew = false;
            }
        }

        if (canCreateNew) {
            RowSetIterator rsi = dciter.getRowSetIterator();
            Row lastRow = rsi.last();
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
    
            if (usrCustomer.equalsIgnoreCase(userCustRegion)) {
                newRow.setAttribute("RegCustFlag", userCustRegion);
            } else if (usrCustomer.equalsIgnoreCase(userCustArea)) {
                newRow.setAttribute("RegCustFlag", userCustArea);
            } else if (usrCustomer.equalsIgnoreCase(userCustLocation)) {
                newRow.setAttribute("RegCustFlag", userCustLocation);
            } else if (usrCustomer.equalsIgnoreCase(userCustCustGroup)) {
                newRow.setAttribute("RegCustFlag", userCustCustGroup);
            } else if (usrCustomer.equalsIgnoreCase(userCustCustomer)) {
                newRow.setAttribute("RegCustFlag", userCustCustomer);
            } else {
                newRow.setAttribute("RegCustFlag", userCustInvalid);
                JSFUtils.addFacesWarningMessage("Anda tidak memiliki hak akses memilih customer.");
            }
    
            //add row to last index + 1 so it becomes last in the range set
            rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            //make row the current row so it is displayed correctly
            rsi.setCurrentRow(newRow);
    
            Integer propTypeIdx = (Integer)itLovProposalType.getValue();
            if (propTypeIdx.compareTo(idxFood) == 0) {
                itLovProdCategory.setValue(prodCatCodeFood);
                itCategory.setValue(prodDescCodeFood);
                AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
                adc.addPartialTarget(itLovProdCategory);
                adc.addPartialTarget(itCategory);
                adc.addPartialTarget(tblListProduct);
            } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
                itLovProdCategory.setValue(prodCatCodeNonFood);
                itCategory.setValue(prodDescCodeNonFood);
                AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
                adc.addPartialTarget(itLovProdCategory);
                adc.addPartialTarget(itCategory);
                adc.addPartialTarget(tblListProduct);
            } else {
                JSFUtils.addFacesErrorMessage("Tipe proposal selain FOOD dan NON FOOD tidak dikenali.");
            }
        } else {
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Data produk ada yang belum selesai dilengkapi dan kombinasi belum valid.</p>");
            message.append("<p>Proses penambahan produk baru tidak dapat dilanjutkan.</p>");
            message.append("</body></html>");
            JSFUtils.addFacesWarningMessage(message.toString());
        }
    }

    public void copyProposal(DialogEvent dialogEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");

        List aprvlRegPropList = new ArrayList();
        List propRegionList = new ArrayList();
        List propRegionCustList = new ArrayList();
        List propRegionAreaList = new ArrayList();
        List propRegionLocList = new ArrayList();
        List propRegionCustGroupList = new ArrayList();
        String copyAs = (String)copyAsFlag.getValue();
        DefaultDateFormatter ddf = new DefaultDateFormatter();
        String dateValueStart = "", dateValueEnd = "", dateTimeValueStart =
            "", dateTimeValueEnd = "";

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

        // ===== GET NEW PROPOSAL PERIODE ======================================
        try {
            dateValueStart =
                    ddf.format("yyyy-MM-dd", newPromoPeriodFrom.getValue());
            dateTimeValueStart = dateValueStart + " 00:00:00";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueStart = "";
        }

        try {
            dateValueEnd =
                    ddf.format("yyyy-MM-dd", newPromoPeriodTo.getValue());
            dateTimeValueEnd = dateValueEnd + " 23:59:59";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueEnd = "";
        }

        boolean dateValid = false;
        try {
            dateValid =
                    ADFUtils.compareDates(dateTimeValueStart, dateTimeValueEnd);
        } catch (ParseException e) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to parse date value.",
                                 "Failed to parse date value.");
            fctx.addMessage(null, msg);
        }

        if (dateValid) {
            // INSERT NEW PROMO DATE VALUE
            oracle.jbo.domain.Date newProgPromoStart =
                ADFUtils.convertToJboDate("yyyy-MM-dd hh:mm:ss",
                                          dateTimeValueStart);
            oracle.jbo.domain.Date newProgPromoEnd =
                ADFUtils.convertToJboDate("yyyy-MM-dd hh:mm:ss",
                                          dateTimeValueEnd);

            long numOfDaysPromo = 0;
            try {
                numOfDaysPromo =
                        ADFUtils.diffInDays(dateTimeValueStart, dateTimeValueEnd);
            } catch (ParseException e) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Failed to parse date value.",
                                     "Failed to parse date value.");
                fctx.addMessage(null, msg);
            }

            // ===== COLLECT DATA ==================================================
            // PARENT: Collect Data Proposal
            DCIteratorBinding dciterProposal =
                ADFUtils.findIterator("ProposalView1Iterator");
            Row propCurrentRow = dciterProposal.getCurrentRow();
            String[] propAttrs = propCurrentRow.getAttributeNames();
            String[] skipPropAttrs =
                new String[] { "ProposalId", "ProposalNo", "Status",
                               "AddendumKe", "ConfirmNo", "CopySource",
                               "PeriodeProgFrom", "PeriodeProgTo",
                               "ProgDays" };
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
                            propRegionList.add(r.getAttribute("RegionCode") +
                                               ";" +
                                               r.getAttribute("Description1"));
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propCustomer)) {
                    if (dciterPropRegionCust.getEstimatedRowCount() > 0) {
                        for (Row r :
                             dciterPropRegionCust.getAllRowsInRange()) {
                            propRegionCustList.add(r.getAttribute("CustomerId") +
                                                   ";" +
                                                   r.getAttribute("Custdescription"));
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propArea)) {
                    if (dciterPropRegionArea.getEstimatedRowCount() > 0) {
                        for (Row r :
                             dciterPropRegionArea.getAllRowsInRange()) {
                            propRegionAreaList.add(r.getAttribute("AreaCode") +
                                                   ";" +
                                                   r.getAttribute("Areadiscription"));
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propLocation)) {
                    if (dciterPropRegionLoc.getEstimatedRowCount() > 0) {
                        for (Row r : dciterPropRegionLoc.getAllRowsInRange()) {
                            propRegionLocList.add(r.getAttribute("LocationCode") +
                                                  ";" +
                                                  r.getAttribute("Locdescription"));
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propCustGroup)) {
                    if (dciterPropRegionCustGroup.getEstimatedRowCount() > 0) {
                        for (Row r :
                             dciterPropRegionCustGroup.getAllRowsInRange()) {
                            propRegionCustGroupList.add(r.getAttribute("CustGroup") +
                                                        ";" +
                                                        r.getAttribute("Custgroupdesc"));
                        }
                    }
                }
            } else {
                // USER TYPE CREATOR == HO
                // DO NOTHING
            }

            // CHILD: Data Promo Produk
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            List promoProdList = new ArrayList<Row>();
            if (dciterPromoProduk.getEstimatedRowCount() > 0) {
                for (Row r : dciterPromoProduk.getAllRowsInRange()) {
                    promoProdList.add(r);
                }
            }

            // SUB CHILD: Data Promo Bonus
            DCIteratorBinding dciterPromoBonus =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            List promoBonusList = new ArrayList<Row>();
            if (dciterPromoBonus.getEstimatedRowCount() > 0) {
                for (Row r : dciterPromoBonus.getAllRowsInRange()) {
                    promoBonusList.add(r);
                }
            }

            // ===== INSERTING DATA ============================================
            // PARENT: Insert Data Proposal
            Row dupProposalRow =
                dciterProposal.getRowSetIterator().createRow();
            for (int i = 0; i < propAttrs.length; i++) {
                String propAttrName = propAttrs[i];
                if ("Status".equals(propAttrName))
                    dupProposalRow.setAttribute(propAttrName, "DRAFT");
                if ("CopySource".equals(propAttrName) &&
                    copyAs.equalsIgnoreCase("R"))
                    dupProposalRow.setAttribute(propAttrName, srcProposalNo);
                if ("PeriodeProgFrom".equals(propAttrName)) {
                    dupProposalRow.setAttribute(propAttrName,
                                                newProgPromoStart);
                }
                if ("PeriodeProgTo".equals(propAttrName)) {
                    dupProposalRow.setAttribute(propAttrName, newProgPromoEnd);
                }
                if ("ProgDays".equals(propAttrName)) {
                    dupProposalRow.setAttribute(propAttrName, numOfDaysPromo);
                }
                int attrIndex =
                    dupProposalRow.getAttributeIndexOf(propAttrName);
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
                            String[] output =
                                propRegionList.get(i).toString().split(";");
                            String code = output[0].trim();
                            String descr = output[1].trim();

                            Row dupPropRegionRow =
                                dciterPropRegion.getRowSetIterator().createRow();
                            dupPropRegionRow.setAttribute("RegionCode", code);
                            dupPropRegionRow.setAttribute("Description1",
                                                          descr);

                            //Inserting the duplicate proposal region row
                            dciterPropRegion.getRowSetIterator().insertRow(dupPropRegionRow);
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propCustomer)) {
                    if (propRegionCustList.size() > 0) {
                        for (int i = 0; i < propRegionCustList.size(); i++) {
                            String[] output =
                                propRegionCustList.get(i).toString().split(";");
                            String code = output[0].trim();
                            String descr = output[1].trim();

                            Row dupPropRegionCustRow =
                                dciterPropRegionCust.getRowSetIterator().createRow();
                            dupPropRegionCustRow.setAttribute("CustomerId",
                                                              code);
                            dupPropRegionCustRow.setAttribute("Custdescription",
                                                              descr);

                            //Inserting the duplicate proposal customer row
                            dciterPropRegionCust.getRowSetIterator().insertRow(dupPropRegionCustRow);
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propArea)) {
                    if (propRegionAreaList.size() > 0) {
                        for (int i = 0; i < propRegionAreaList.size(); i++) {
                            String[] output =
                                propRegionAreaList.get(i).toString().split(";");
                            String code = output[0].trim();
                            String descr = output[1].trim();

                            Row dupPropRegionAreaRow =
                                dciterPropRegionArea.getRowSetIterator().createRow();
                            dupPropRegionAreaRow.setAttribute("AreaCode",
                                                              code);
                            dupPropRegionAreaRow.setAttribute("Areadiscription",
                                                              descr);

                            //Inserting the duplicate proposal area row
                            dciterPropRegionArea.getRowSetIterator().insertRow(dupPropRegionAreaRow);
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propLocation)) {
                    if (propRegionLocList.size() > 0) {
                        for (int i = 0; i < propRegionLocList.size(); i++) {
                            String[] output =
                                propRegionLocList.get(i).toString().split(";");
                            String code = output[0].trim();
                            String descr = output[1].trim();

                            Row dupPropRegionLocRow =
                                dciterPropRegionLoc.getRowSetIterator().createRow();
                            dupPropRegionLocRow.setAttribute("LocationCode",
                                                             code);
                            dupPropRegionLocRow.setAttribute("Locdescription",
                                                             descr);

                            //Inserting the duplicate proposal location row
                            dciterPropRegionLoc.getRowSetIterator().insertRow(dupPropRegionLocRow);
                        }
                    }
                } else if (custRegFlag1.equalsIgnoreCase(propCustGroup)) {
                    if (propRegionCustGroupList.size() > 0) {
                        for (int i = 0; i < propRegionCustGroupList.size();
                             i++) {
                            String[] output =
                                propRegionCustGroupList.get(i).toString().split(";");
                            String code = output[0].trim();
                            String descr = output[1].trim();

                            Row dupPropRegionCustGroupRow =
                                dciterPropRegionCustGroup.getRowSetIterator().createRow();
                            dupPropRegionCustGroupRow.setAttribute("CustGroup",
                                                                   code);
                            dupPropRegionCustGroupRow.setAttribute("Custgroupdesc",
                                                                   descr);

                            //Inserting the duplicate proposal customer group row
                            dciterPropRegionCustGroup.getRowSetIterator().insertRow(dupPropRegionCustGroupRow);
                        }
                    }
                }
            } else {
                // USER TYPE CREATOR == HO
                // DO NOTHING
            }

            // CHILD: Inserting Data Promo Produk
            ArrayList<String> promoBonusVariantList = new ArrayList<String>();
            ArrayList<String> promoBonusItemList = new ArrayList<String>();

            if (promoProdList.size() > 0) {
                for (int i = 0; i < promoProdList.size(); i++) {
                    Row dupPromoProdukRow =
                        dciterPromoProduk.getRowSetIterator().createRow();
                    Row promoProdSource = (Row)promoProdList.get(i);
                    String produkCustomer =
                        (String)promoProdSource.getAttribute("RegCustFlag");
                    String produkExclCust =
                        (String)promoProdSource.getAttribute("ExclCustBy") ==
                        null ? "" :
                        (String)promoProdSource.getAttribute("ExclCustBy");

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
                    dupPromoProdukRow.setAttribute("RegCustFlag",
                                                   produkCustomer);
                    dupPromoProdukRow.setAttribute("BudgetBy",
                                                   promoProdSource.getAttribute("BudgetBy"));
                    dupPromoProdukRow.setAttribute("ExclCustBy",
                                                   promoProdSource.getAttribute("ExclCustBy"));
                    dupPromoProdukRow.setAttribute("DiscMf",
                                                   promoProdSource.getAttribute("DiscMf"));
                    dupPromoProdukRow.setAttribute("DiscOnTop",
                                                   promoProdSource.getAttribute("DiscOnTop"));
                    dupPromoProdukRow.setAttribute("DiscRasioMf",
                                                   promoProdSource.getAttribute("DiscRasioMf"));
                    dupPromoProdukRow.setAttribute("DiscRasioOnTop",
                                                   promoProdSource.getAttribute("DiscRasioOnTop"));
                    dupPromoProdukRow.setAttribute("DiscRasioTotal1",
                                                   promoProdSource.getAttribute("DiscRasioTotal1"));
                    dupPromoProdukRow.setAttribute("BrgBonusMf",
                                                   promoProdSource.getAttribute("BrgBonusMf"));
                    dupPromoProdukRow.setAttribute("BrgBonusOnTop",
                                                   promoProdSource.getAttribute("BrgBonusOnTop"));
                    dupPromoProdukRow.setAttribute("BrgBonusRasioMf",
                                                   promoProdSource.getAttribute("BrgBonusRasioMf"));
                    dupPromoProdukRow.setAttribute("BrgBonusRasioOnTop",
                                                   promoProdSource.getAttribute("BrgBonusRasioOnTop"));
                    dupPromoProdukRow.setAttribute("BrgBonusRasioTotal",
                                                   promoProdSource.getAttribute("BrgBonusRasioTotal"));
                    dupPromoProdukRow.setAttribute("BiaMf",
                                                   promoProdSource.getAttribute("BiaMf"));
                    dupPromoProdukRow.setAttribute("BiaOntop",
                                                   promoProdSource.getAttribute("BiaOntop"));
                    dupPromoProdukRow.setAttribute("BiaRasioMf",
                                                   promoProdSource.getAttribute("BiaRasioMf"));
                    dupPromoProdukRow.setAttribute("BiaRasionOntop",
                                                   promoProdSource.getAttribute("BiaRasionOntop"));
                    dupPromoProdukRow.setAttribute("BiaRasioTotal",
                                                   promoProdSource.getAttribute("BiaRasioTotal"));

                    //Inserting the duplicate proposal region row
                    dciterPromoProduk.getRowSetIterator().insertRow(dupPromoProdukRow);

                    String promoProdId =
                        String.valueOf(((DBSequence)promoProdSource.getAttribute("PromoProdukId")).getValue());

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
                            dupProdVariantRow.setAttribute("ProdItem",
                                                           prodItem);
                        }
                    }

                    // SUB CHILD: Duplicate customer
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
                                dupProdAreaRow.setAttribute("AreaCode",
                                                            areaCode);
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
                                dupProdCustRow.setAttribute("CustomerId",
                                                            custId);
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
                                Row prodCustGroupRow =
                                    voDupProdCustGroup.next();
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
                                dupProdCustGroupRow.setAttribute("Notes",
                                                                 notes);
                            }
                        }
                    } else {
                        // DONT COPY PRODUK CUSTOMER
                    }

                    // SUB CHILD: Duplicate exclude customer
                    if (!produkExclCust.equalsIgnoreCase("")) {
                        if (produkExclCust.equalsIgnoreCase(prodExclRegion)) {
                            // SUB CHILD: Collect and Inserting Data Exclude Customer by Region
                            DuplicateProdExclRegionViewImpl voDupProdExclRegion =
                                promoProposalAM.getDuplicateProdExclRegionView1();
                            voDupProdExclRegion.setNamedWhereClauseParam("promoProdukId",
                                                                         promoProdId.trim());
                            voDupProdExclRegion.executeQuery();

                            if (voDupProdExclRegion.getEstimatedRowCount() >
                                0) {
                                while (voDupProdExclRegion.hasNext()) {
                                    Row prodExclRegRow =
                                        voDupProdExclRegion.next();
                                    String regionCode =
                                        (String)prodExclRegRow.getAttribute("RegionCode");

                                    DCIteratorBinding dciterProdRegion =
                                        ADFUtils.findIterator("ExclCustRegionView1Iterator");
                                    Row dupProdRegionRow =
                                        dciterProdRegion.getRowSetIterator().createRow();
                                    dupProdRegionRow.setAttribute("RegionCode",
                                                                  regionCode);
                                }
                            }
                        } else if (produkExclCust.equalsIgnoreCase(prodExclArea)) {
                            // SUB CHILD: Collect and Inserting Data Exclude Customer by Area
                            DuplicateProdExclAreaViewImpl voDupProdExclArea =
                                promoProposalAM.getDuplicateProdExclAreaView1();
                            voDupProdExclArea.setNamedWhereClauseParam("promoProdukId",
                                                                       promoProdId.trim());
                            voDupProdExclArea.executeQuery();

                            if (voDupProdExclArea.getEstimatedRowCount() > 0) {
                                while (voDupProdExclArea.hasNext()) {
                                    Row prodExclAreaRow =
                                        voDupProdExclArea.next();
                                    String areaCode =
                                        (String)prodExclAreaRow.getAttribute("AreaCode");

                                    DCIteratorBinding dciterProdArea =
                                        ADFUtils.findIterator("ExclCustAreaView1Iterator");
                                    Row dupProdAreaRow =
                                        dciterProdArea.getRowSetIterator().createRow();
                                    dupProdAreaRow.setAttribute("AreaCode",
                                                                areaCode);
                                }
                            }
                        } else if (produkExclCust.equalsIgnoreCase(prodExclCustomer)) {
                            // SUB CHILD: Collect and Inserting Data Exclude Customer by Customer
                            DuplicateProdExclCustomerViewImpl voDupProdExclCustomer =
                                promoProposalAM.getDuplicateProdExclCustomerView1();
                            voDupProdExclCustomer.setNamedWhereClauseParam("promoProdukId",
                                                                           promoProdId.trim());
                            voDupProdExclCustomer.executeQuery();

                            if (voDupProdExclCustomer.getEstimatedRowCount() >
                                0) {
                                while (voDupProdExclCustomer.hasNext()) {
                                    Row prodExclCustomerRow =
                                        voDupProdExclCustomer.next();
                                    Number custId =
                                        (Number)prodExclCustomerRow.getAttribute("CustomerId");

                                    DCIteratorBinding dciterProdCust =
                                        ADFUtils.findIterator("ExclCustCustView1Iterator");
                                    Row dupProdCustRow =
                                        dciterProdCust.getRowSetIterator().createRow();
                                    dupProdCustRow.setAttribute("CustomerId",
                                                                custId);
                                }
                            }
                        } else if (produkExclCust.equalsIgnoreCase(prodExclLocation)) {
                            // SUB CHILD: Collect and Inserting Data Exclude Customer by Location
                            DuplicateProdExclLocViewImpl voDupProdExclLocation =
                                promoProposalAM.getDuplicateProdExclLocView1();
                            voDupProdExclLocation.setNamedWhereClauseParam("promoProdukId",
                                                                           promoProdId.trim());
                            voDupProdExclLocation.executeQuery();

                            if (voDupProdExclLocation.getEstimatedRowCount() >
                                0) {
                                while (voDupProdExclLocation.hasNext()) {
                                    Row prodExclLocationRow =
                                        voDupProdExclLocation.next();
                                    String locCode =
                                        (String)prodExclLocationRow.getAttribute("LocationCode");

                                    DCIteratorBinding dciterProdLoc =
                                        ADFUtils.findIterator("ExclCustLocView1Iterator");
                                    Row dupProdLocRow =
                                        dciterProdLoc.getRowSetIterator().createRow();
                                    dupProdLocRow.setAttribute("LocationCode",
                                                               locCode);
                                }
                            }
                        } else {
                            // DONT COPY EXCLUDE CUSTOMER
                        }
                    } else {
                        // DONT COPY EXCLUDE CUSTOMER
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
                            Number avgQty =
                                (Number)targetRow.getAttribute("AvgQty");
                            Number price =
                                (Number)targetRow.getAttribute("Price");
                            String priceBased =
                                (String)targetRow.getAttribute("PriceBased");
                            String priceList =
                                (String)targetRow.getAttribute("PriceList");
                            Number qty = (Number)targetRow.getAttribute("Qty");
                            String uom = (String)targetRow.getAttribute("Uom");
                            Number value =
                                (Number)targetRow.getAttribute("Value");

                            DCIteratorBinding dciterTarget =
                                ADFUtils.findIterator("TargetView1Iterator");
                            Row dupTargetRow =
                                dciterTarget.getRowSetIterator().createRow();
                            dupTargetRow.setAttribute("AvgQty", avgQty);
                            dupTargetRow.setAttribute("Price", price);
                            dupTargetRow.setAttribute("PriceBased",
                                                      priceBased);
                            dupTargetRow.setAttribute("PriceList", priceList);
                            dupTargetRow.setAttribute("Qty", qty);
                            dupTargetRow.setAttribute("Uom", uom);
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
                                dupDiscountRow.setAttribute("QtyFrom",
                                                            qtyFrom);
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
                                String uom =
                                    (String)addBonus.getAttribute("Uom");
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
                                String prodVarBonus =
                                    (String)addBonus.getAttribute("ProductVariantBonus") ==
                                    null ? "" :
                                    (String)addBonus.getAttribute("ProductVariantBonus");
                                String prodItemBonus =
                                    (String)addBonus.getAttribute("ProductItemBonus") ==
                                    null ? "" :
                                    (String)addBonus.getAttribute("ProductItemBonus");

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
                                dupBonusRow.setAttribute("ProductExt",
                                                         productExt);
                                dupBonusRow.setAttribute("ProductPack",
                                                         productPack);
                                dupBonusRow.setAttribute("Uom", uom);
                                dupBonusRow.setAttribute("QtyFrom", qtyFrom);
                                dupBonusRow.setAttribute("TipePotongan",
                                                         tipePotongan);
                                dupBonusRow.setAttribute("ValuePotongan",
                                                         valuePotongan);
                                dupBonusRow.setAttribute("DiscYearly",
                                                         discYearly);
                                dupBonusRow.setAttribute("DiscNonYearly",
                                                         discNonYearly);

                                DBSequence promoBonusId =
                                    (DBSequence)dupBonusRow.getAttribute("PromoBonusId");

                                promoBonusVariantList.add(promoBonusId.getValue() +
                                                          ";" + prodVarBonus);
                                promoBonusItemList.add(promoBonusId.getValue() +
                                                       ";" + prodItemBonus);
                            }
                        }
                    } else {
                        // OTHER SUB CHILD TYPE
                    }
                }
            }

            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            if (discountType1.equalsIgnoreCase(discTypePromoBarang)) {
                // SUB-SUB CHILD: Inserting Data Produk Promo Bonus Variant
                if (promoBonusVariantList.size() > 0) {
                    for (String promoBonusVariant : promoBonusVariantList) {
                        String[] bonusVarArr = promoBonusVariant.split(";");

                        String promoBonusId = "";
                        try {
                            promoBonusId = bonusVarArr[0].trim();
                        } catch (Exception e) {
                            promoBonusId = "";
                        }

                        String bonusVariantList = "";
                        try {
                            bonusVariantList = bonusVarArr[1].trim();
                        } catch (Exception e) {
                            bonusVariantList = "";
                        }

                        if (!promoBonusId.equalsIgnoreCase("") &&
                            !bonusVariantList.equalsIgnoreCase("")) {
                            String[] bonVarArr =
                                bonusVariantList.split("\\|", -1);
                            for (int v = 0; v < bonVarArr.length; v++) {
                                String bonusVariant = bonVarArr[v].trim();
                                if (promoBonusId != null &&
                                    bonusVariant != null) {
                                    PreparedStatement addProdBonusVariantStmt =
                                        null;
                                    try {
                                        String SQL =
                                            "INSERT INTO PROMO_BONUS_VARIANT (PROMO_BONUS_ID, PROD_VARIANT) " +
                                            "VALUES (" + promoBonusId + ", '" +
                                            bonusVariant + "')";
                                        addProdBonusVariantStmt =
                                                promoProposalAM.getDBTransaction().createPreparedStatement(SQL,
                                                                                                           1);
                                        addProdBonusVariantStmt.executeUpdate();
                                    } catch (SQLException e) {
                                        throw new JboException(e.getMessage());
                                    } finally {
                                        promoProposalAM.getDBTransaction().commit();
                                        if (addProdBonusVariantStmt != null) {
                                            try {
                                                addProdBonusVariantStmt.close();
                                            } catch (Exception e) {
                                                throw new JboException(e.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // SUB-SUB CHILD: Inserting Data Produk Promo Bonus Item
                if (promoBonusItemList.size() > 0) {
                    for (String promoBonusItem : promoBonusItemList) {
                        String[] bonusItemArr = promoBonusItem.split(";");

                        String promoBonusId = "";
                        try {
                            promoBonusId = bonusItemArr[0].trim();
                        } catch (Exception e) {
                            promoBonusId = "";
                        }

                        String bonusItemList = "";
                        try {
                            bonusItemList = bonusItemArr[1].trim();
                        } catch (Exception e) {
                            bonusItemList = "";
                        }

                        if (!promoBonusId.equalsIgnoreCase("") &&
                            !bonusItemList.equalsIgnoreCase("")) {
                            String[] bonItemArr =
                                bonusItemList.split("\\|", -1);
                            for (int b = 0; b < bonItemArr.length; b++) {
                                String prodItemCode = bonItemArr[b].trim();
                                if (promoBonusId != null &&
                                    prodItemCode != null) {
                                    PreparedStatement addProdBonusItemStmt =
                                        null;
                                    try {
                                        String SQL =
                                            "INSERT INTO PROMO_BONUS_PROD_ITEM (PROMO_BONUS_ID, PROD_ITEM) " +
                                            "VALUES (" + promoBonusId + ", '" +
                                            prodItemCode + "')";
                                        addProdBonusItemStmt =
                                                promoProposalAM.getDBTransaction().createPreparedStatement(SQL,
                                                                                                           1);
                                        addProdBonusItemStmt.executeUpdate();
                                    } catch (SQLException e) {
                                        throw new JboException(e.getMessage());
                                    } finally {
                                        promoProposalAM.getDBTransaction().commit();
                                        if (addProdBonusItemStmt != null) {
                                            try {
                                                addProdBonusItemStmt.close();
                                            } catch (Exception e) {
                                                throw new JboException(e.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

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

        } else {
            // DO NOTHING
        }
    }

    public void setCopyAsFlag(RichSelectOneChoice copyAsFlag) {
        this.copyAsFlag = copyAsFlag;
    }

    public RichSelectOneChoice getCopyAsFlag() {
        return copyAsFlag;
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
        if (discType.equalsIgnoreCase(discTypeBiaya)) {
            DCIteratorBinding dciterBiaya =
                ADFUtils.findIterator("BiayaView1Iterator");
            if (dciterBiaya.getEstimatedRowCount() < 1) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Biaya";
                isIterValid = false;
            }
        } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
            DCIteratorBinding dciterPromoBonus =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            if (dciterPromoBonus.getEstimatedRowCount() < 1) {
                if (!isIterValid) {
                    sIterMsg += ", ";
                }
                sIterMsg += "Tab Promo";
                isIterValid = false;
            }
        } else if (discType.equalsIgnoreCase(discTypePotongan)) {
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
            BigDecimal price =
                new BigDecimal(r.getAttribute("Price") == null ? "0" :
                               (df.format(((Number)r.getAttribute("Price")).getValue())).toString());
            BigDecimal Qty =
                new BigDecimal(r.getAttribute("Qty") == null ? "0" :
                               r.getAttribute("Qty").toString());
            //            System.out.println("price = " + price);
            if (price.compareTo(BigDecimal.ZERO) <= 0 ||
                Qty.compareTo(BigDecimal.ZERO) <= 0) {
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
            if (discType.equalsIgnoreCase(discTypeBiaya)) {
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
            } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
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
            } else if (discType.equalsIgnoreCase(discTypePotongan)) {
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
                System.out.println("\"Tipe Customer\" pada produk tidak dikenal.");
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
    }


    public void tabBiayaEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
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
            Row rTarget = dciterUOMTarget.getCurrentRow();
            String uomTar = (String)rTarget.getAttribute("Uom");
            ViewObject vouom = dciterUOM.getViewObject();
            if (dciterUOM.getEstimatedRowCount() > 0) {
                for (Row r : dciterUOM.getAllRowsInRange()) {
                    r.setAttribute("Uom", uomTar);
                }
                OperationBinding operationBinding =
                    bindings.getOperationBinding("Commit");
                operationBinding.execute();
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
            Row rTarget = dciterUOMTarget.getCurrentRow();
            String uomTar = (String)rTarget.getAttribute("Uom");
            if (dciterUOM.getEstimatedRowCount() > 0) {
                for (Row r : dciterUOM.getAllRowsInRange()) {
                    r.setAttribute("Uom", uomTar);
                }
                OperationBinding operationBinding =
                    bindings.getOperationBinding("Commit");
                operationBinding.execute();
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

        boolean backDateOk = false;
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        AttributeBinding custTypeAttr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");
        String custType = (String)custTypeAttr.getInputValue();
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

            if (discType.equalsIgnoreCase(discTypeBiaya)) {
                DCIteratorBinding dciterBiaya =
                    ADFUtils.findIterator("BiayaView1Iterator");
                if (dciterBiaya.getEstimatedRowCount() < 1) {
                    if (!isSavedValid) {
                        sSavedMsg += ", ";
                    }
                    sSavedMsg += "Biaya Pada Produk Detail";
                    isSavedValid = false;
                } else {
                    for (Row r : dciterBiaya.getAllRowsInRange()) {
                        Number biayaNonYearly = (Number)r.getAttribute("BiayaNonYearly") == null ? zeroNumber : (Number)r.getAttribute("BiayaNonYearly");
                        Number biayaYearly = (Number)r.getAttribute("BiayaYearly") == null ? zeroNumber : (Number)r.getAttribute("BiayaYearly");
                        if ((biayaNonYearly == null && biayaYearly == null) ||
                            (biayaNonYearly.compareTo(zeroNumber) == 0 && biayaYearly == null) ||
                            (biayaNonYearly.compareTo(zeroNumber) == 0 && biayaYearly.compareTo(zeroNumber) == 0) ||
                            (biayaNonYearly == null && biayaYearly.compareTo(zeroNumber) == 0)) {
                            sSavedMsg += "Biaya \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isSavedValid = false;
                            break;
                        }
                    }
                }
            } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
                DCIteratorBinding dciterPromoBonus =
                    ADFUtils.findIterator("PromoBonusView1Iterator");
                if (dciterPromoBonus.getEstimatedRowCount() < 1) {
                    if (!isSavedValid) {
                        sSavedMsg += ", ";
                    }
                    sSavedMsg += "Promo Pada Produk Detail";
                    isSavedValid = false;
                } else {
                    for (Row r : dciterPromoBonus.getAllRowsInRange()) {
                        Number promoNonYearly = (Number)r.getAttribute("DiscNonYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscNonYearly");
                        Number promoYearly = (Number)r.getAttribute("DiscYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscYearly");
                        if ((promoNonYearly == null && promoYearly == null) ||
                            (promoNonYearly.compareTo(zeroNumber) == 0 && promoYearly == null) ||
                            (promoNonYearly.compareTo(zeroNumber) == 0 && promoYearly.compareTo(zeroNumber) == 0) ||
                            (promoNonYearly == null && promoYearly.compareTo(zeroNumber) == 0)) {
                            sSavedMsg += "Promo Bonus \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isSavedValid = false;
                            break;
                        }
                    }
                }
            } else if (discType.equalsIgnoreCase(discTypePotongan)) {
                DCIteratorBinding dciterDiscount =
                    ADFUtils.findIterator("DiscountView1Iterator");
                if (dciterDiscount.getEstimatedRowCount() < 1) {
                    if (!isSavedValid) {
                        sSavedMsg += ", ";
                    }
                    sSavedMsg += "Potongan Pada Produk Detail";
                    isSavedValid = false;
                } else {
                    for (Row r : dciterDiscount.getAllRowsInRange()) {
                        Number discNonYearly = (Number)r.getAttribute("DiscNonYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscNonYearly");
                        Number discYearly = (Number)r.getAttribute("DiscYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscYearly");
                        if ((discNonYearly == null && discYearly == null) ||
                            (discNonYearly.compareTo(zeroNumber) == 0 && discYearly == null) ||
                            (discNonYearly.compareTo(zeroNumber) == 0 && discYearly.compareTo(zeroNumber) == 0) ||
                            (discNonYearly == null && discYearly.compareTo(zeroNumber) == 0)) {
                            sSavedMsg += "Potongan \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isSavedValid = false;
                            break;
                        }
                    }
                }
            }
            
            if (isSavedValid) {
                java.util.Date date2 =
                    new java.util.Date(idPeriodProgFrom.getValue().toString());
                java.util.Date today = new java.util.Date();

                AttributeBinding proposalTypeAttr =
                    (AttributeBinding)bindings.getControlBinding("ProposalType");
                Integer propTypeIdx =
                    (Integer)proposalTypeAttr.getInputValue();

                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DATE, -1);
                Date yesterday = c.getTime();
                Integer valFoodDate = 0;

                ArrayList<String> custRegCodeList = new ArrayList<String>();
                if (date2.before(yesterday)) {
                    if (custType.equalsIgnoreCase(prodRegion)) {
                        for (Row r : dciterRegion.getAllRowsInRange()) {
                            String regCode =
                                (String)r.getAttribute("RegionCode");
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(prodArea)) {
                        for (Row r : dciterArea.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(prodArea, (String)r.getAttribute("AreaCode"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(prodLocation)) {
                        for (Row r : dciterLoc.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(prodLocation, (String)r.getAttribute("LocationCode"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(prodCustGroup)) {
                        for (Row r : dciterCustGroup.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(prodCustGroup, (String)r.getAttribute("CustGroup"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(prodCustomer)) {
                        for (Row r : dciterCustomer.getAllRowsInRange()) {
                            oracle.jbo.domain.Number custId =
                                (oracle.jbo.domain.Number)r.getAttribute("CustomerId");
                            String regCode =
                                findCustRegCode(prodCustomer, String.valueOf(custId.bigIntegerValue().intValue()));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    }

                    if (custRegCodeList.contains(backDateBlockRegion) &&
                        custRegCodeList.size() == 1) {
                        backDateOk = true;
                    } else {
                        backDateOk = false;
                    }

                } else {
                    backDateOk = true;
                }

                if (backDateOk) {
                    c.setTime(today);
                    if (propTypeIdx.compareTo(idxFood) == 0) {
                        c.add(Calendar.DATE, 6);
                        Date todayAdd7 = c.getTime();

                        if (date2.after(yesterday) &&
                            date2.compareTo(todayAdd7) < 0) {
                            valFoodDate = 1;
                        } else {
                            valFoodDate = 0;
                        }
                    } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
                        c.add(Calendar.DATE, 9);
                        Date todayAdd10 = c.getTime();

                        if (date2.after(yesterday) &&
                            date2.compareTo(todayAdd10) < 0) {
                            valFoodDate = 2;
                        } else {
                            valFoodDate = 0;
                        }
                    }

                    if (valFoodDate.compareTo(0) == 0) {
                        RichPopup.PopupHints hints =
                            new RichPopup.PopupHints();
                        psubmitProposal.show(hints);
                    } else if (valFoodDate.compareTo(1) == 0) {
                        idPeriodProgFrom.setSubmittedValue(null);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                        this.showPopup("Pengajuan proposal periode promo FOOD minimal harus untuk H+7",
                                       potmessage);
                    } else if (valFoodDate.compareTo(2) == 0) {
                        idPeriodProgFrom.setSubmittedValue(null);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                        this.showPopup("Pengajuan proposal periode promo NON FOOD minimal harus untuk H+10",
                                       potmessage);
                    }
                } else {
                    idPeriodProgFrom.setSubmittedValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                    this.showPopup("Pengajuan proposal ini tidak diperkenankan untuk \"Back Date\"",
                                   potmessage);
                }
            } else {
                showPopup(sSavedMsg, potmessage);
            }
        } else {
            Boolean isIterValid = true;
            String sIterMsg = "";
            if (discType.equalsIgnoreCase(discTypeBiaya)) {
                DCIteratorBinding dciterBiaya =
                    ADFUtils.findIterator("BiayaView1Iterator");
                if (dciterBiaya.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Biaya Pada Produk Detail";
                    isIterValid = false;
                } else {
                    for (Row r : dciterBiaya.getAllRowsInRange()) {
                        Number biayaNonYearly = (Number)r.getAttribute("BiayaNonYearly") == null ? zeroNumber : (Number)r.getAttribute("BiayaNonYearly");
                        Number biayaYearly = (Number)r.getAttribute("BiayaYearly") == null ? zeroNumber : (Number)r.getAttribute("BiayaYearly");
                        if ((biayaNonYearly == null && biayaYearly == null) ||
                            (biayaNonYearly.compareTo(zeroNumber) == 0 && biayaYearly == null) ||
                            (biayaNonYearly.compareTo(zeroNumber) == 0 && biayaYearly.compareTo(zeroNumber) == 0) ||
                            (biayaNonYearly == null && biayaYearly.compareTo(zeroNumber) == 0)) {
                            sIterMsg += "Biaya \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isIterValid = false;
                            break;
                        }
                    }
                }
            } else if (discType.equalsIgnoreCase(discTypePromoBarang)) {
                DCIteratorBinding dciterPromoBonus =
                    ADFUtils.findIterator("PromoBonusView1Iterator");
                if (dciterPromoBonus.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Promo Pada Produk Detail";
                    isIterValid = false;
                } else {
                    for (Row r : dciterPromoBonus.getAllRowsInRange()) {
                        Number promoNonYearly = (Number)r.getAttribute("DiscNonYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscNonYearly");
                        Number promoYearly = (Number)r.getAttribute("DiscYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscYearly");
                        if ((promoNonYearly == null && promoYearly == null) ||
                            (promoNonYearly.compareTo(zeroNumber) == 0 && promoYearly == null) ||
                            (promoNonYearly.compareTo(zeroNumber) == 0 && promoYearly.compareTo(zeroNumber) == 0) ||
                            (promoNonYearly == null && promoYearly.compareTo(zeroNumber) == 0)) {
                            sIterMsg += "Promo Bonus \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isIterValid = false;
                            break;
                        }
                    }
                }
            } else if (discType.equalsIgnoreCase(discTypePotongan)) {
                DCIteratorBinding dciterDiscount =
                    ADFUtils.findIterator("DiscountView1Iterator");
                if (dciterDiscount.getEstimatedRowCount() < 1) {
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Potongan Pada Produk Detail";
                    isIterValid = false;
                } else {
                    for (Row r : dciterDiscount.getAllRowsInRange()) {
                        Number discNonYearly = (Number)r.getAttribute("DiscNonYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscNonYearly");
                        Number discYearly = (Number)r.getAttribute("DiscYearly") == null ? zeroNumber : (Number)r.getAttribute("DiscYearly");
                        if ((discNonYearly == null && discYearly == null) ||
                            (discNonYearly.compareTo(zeroNumber) == 0 && discYearly == null) ||
                            (discNonYearly.compareTo(zeroNumber) == 0 && discYearly.compareTo(zeroNumber) == 0) ||
                            (discNonYearly == null && discYearly.compareTo(zeroNumber) == 0)) {
                            sIterMsg += "Potongan \"Tdk Potong Budget / On Top\" atau \"Potong Budget / MF\"";
                            isIterValid = false;
                            break;
                        }
                    }
                }
            }

            if (userType.equalsIgnoreCase(userArea)) {
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
                java.util.Date date2 =
                    new java.util.Date(idPeriodProgFrom.getValue().toString());
                java.util.Date today = new java.util.Date();

                AttributeBinding proposalTypeAttr =
                    (AttributeBinding)bindings.getControlBinding("ProposalType");
                Integer propTypeIdx =
                    (Integer)proposalTypeAttr.getInputValue();

                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DATE, -1);
                Date yesterday = c.getTime();
                Integer valFoodDate = 0;

                ArrayList<String> custRegCodeList = new ArrayList<String>();
                if (date2.before(yesterday)) {
                    if (custType.equalsIgnoreCase(propRegion)) {
                        for (Row r : dciterRegion1.getAllRowsInRange()) {
                            String regCode =
                                (String)r.getAttribute("RegionCode");
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(propArea)) {
                        for (Row r : dciterArea1.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(propArea, (String)r.getAttribute("AreaCode"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(propLocation)) {
                        for (Row r : dciterLoc1.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(propLocation, (String)r.getAttribute("LocationCode"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(propCustGroup)) {
                        for (Row r : dciterCustGroup1.getAllRowsInRange()) {
                            String regCode =
                                findCustRegCode(propCustGroup, (String)r.getAttribute("CustGroup"));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    } else if (custType.equalsIgnoreCase(propCustomer)) {
                        for (Row r : dciterCustomer1.getAllRowsInRange()) {
                            oracle.jbo.domain.Number custId =
                                (oracle.jbo.domain.Number)r.getAttribute("CustomerId");
                            String regCode =
                                findCustRegCode(propCustomer, String.valueOf(custId.bigIntegerValue().intValue()));
                            if (!custRegCodeList.contains(regCode)) {
                                custRegCodeList.add(regCode);
                            }
                        }
                    }
                    
                    if (custRegCodeList.contains(backDateBlockRegion) &&
                        custRegCodeList.size() == 1) {
                        backDateOk = true;
                    } else {
                        backDateOk = false;
                    }

                } else {
                    backDateOk = true;
                }

                if (backDateOk) {
                    c.setTime(today);
                    if (propTypeIdx.compareTo(idxFood) == 0) {
                        c.add(Calendar.DATE, 6);
                        Date todayAdd7 = c.getTime();

                        if (date2.after(yesterday) &&
                            date2.compareTo(todayAdd7) < 0) {
                            valFoodDate = 1;
                        } else {
                            valFoodDate = 0;
                        }
                    } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
                        c.add(Calendar.DATE, 9);
                        Date todayAdd10 = c.getTime();

                        if (date2.after(yesterday) &&
                            date2.compareTo(todayAdd10) < 0) {
                            valFoodDate = 2;
                        } else {
                            valFoodDate = 0;
                        }
                    }

                    if (valFoodDate.compareTo(0) == 0) {
                        RichPopup.PopupHints hints =
                            new RichPopup.PopupHints();
                        psubmitProposal.show(hints);
                    } else if (valFoodDate.compareTo(1) == 0) {
                        idPeriodProgFrom.setSubmittedValue(null);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                        this.showPopup("Pengajuan proposal periode promo FOOD minimal harus untuk H+7",
                                       potmessage);
                    } else if (valFoodDate.compareTo(2) == 0) {
                        idPeriodProgFrom.setSubmittedValue(null);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                        this.showPopup("Pengajuan proposal periode promo NON FOOD minimal harus untuk H+10",
                                       potmessage);
                    }
                } else {
                    idPeriodProgFrom.setSubmittedValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                    this.showPopup("Pengajuan proposal ini tidak diperkenankan untuk \"Back Date\"",
                                   potmessage);
                }
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
        Row rTarget = dciterUOMTarget.getCurrentRow();
        String uomTar = (String)rTarget.getAttribute("Uom");

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
        Row rTarget = dciterUOMTarget.getCurrentRow();
        String uomTar = (String)rTarget.getAttribute("Uom");
        
        if (dciter.getEstimatedRowCount() > 0) {
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            
            //Get last inserted row qty to
            Number lastRowQtyTo = lastRow.getAttribute("QtyTo") == null ? maxNumber : (Number)lastRow.getAttribute("QtyTo");
            
            if (lastRowQtyTo.compareTo(maxNumber) == 0) {
                JSFUtils.addFacesWarningMessage("Nilai \"Qty To\" harus diisi dan kurang dari " + maxNumber);
            } else {
                Row newRow = rsi.createRow();
                newRow.setNewRowState(Row.STATUS_INITIALIZED);
                newRow.setAttribute("Uom", uomTar);
                newRow.setAttribute("QtyFrom", lastRowQtyTo.add(1));
                newRow.setAttribute("QtyTo", maxNumber);
                //add row to last index + 1 so it becomes last in the range set
                rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
                //make row the current row so it is displayed correctly
                rsi.setCurrentRow(newRow);
                newRow.validate();
            }
        } else {
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            newRow.setAttribute("Uom", uomTar);
            newRow.setAttribute("QtyFrom", new Number(1));
            newRow.setAttribute("QtyTo", maxNumber);
            newRow.validate();
        }
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
        String listCustomer = "";
        String custType = "";

        AttributeBinding usrTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String srcUsrTypeCreator = (String)usrTypeCreatorAttr.getInputValue();

        AttributeBinding uomAttr =
            (AttributeBinding)bindings.getControlBinding("Uom1");
        String pUomPppc = (String)uomAttr.getInputValue();

        AttributeBinding progDaysPppcAttr =
            (AttributeBinding)bindings.getControlBinding("ProgDays");
        oracle.jbo.domain.Number progDaysPppc =
            (oracle.jbo.domain.Number)progDaysPppcAttr.getInputValue();
        Integer pProgDaysPppc = progDaysPppc.getBigDecimalValue().intValue();

        AttributeBinding prodNameAttr =
            (AttributeBinding)bindings.getControlBinding("ProductCombination");
        String prodName =
            (String)prodNameAttr.getInputValue() == null ? "" : (String)prodNameAttr.getInputValue();

        AttributeBinding prodItemCodeAttr =
            (AttributeBinding)bindings.getControlBinding("ProductItemCode");
        String prodItemCode =
            (String)prodItemCodeAttr.getInputValue() == null ? "" :
            (String)prodItemCodeAttr.getInputValue();

        AttributeBinding custTypeHoAttr =
            (AttributeBinding)bindings.getControlBinding("RegCustFlag2");

        AttributeBinding custTypeAreaAttr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");

        if (prodItemCode.trim().equalsIgnoreCase("")) {
            AttributeBinding prodVarCodeAttr =
                (AttributeBinding)bindings.getControlBinding("ProductVarCode");
            String prodVarCode = (String)prodVarCodeAttr.getInputValue();

            String[] arrayProdVar = prodVarCode.split("\\,", -1);
            for (int i = 0; i < arrayProdVar.length; i++) {
                pItemNumber =
                        pItemNumber + prodName + "." + arrayProdVar[i] + ";";
            }
        } else {
            String[] arrayProdItem = prodItemCode.split("\\,", -1);
            for (int i = 0; i < arrayProdItem.length; i++) {
                pItemNumber = pItemNumber + arrayProdItem[i] + ";";
            }
        }

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
        /*
        System.out.println("=================================================");
        System.out.println("CUST TYPE  : " + custType);
        System.out.println("LIST CUST  : " + listCustomer);
        System.out.println("ITEM NUMBER: " + pItemNumber);
        System.out.println("UOM PPPC   : " + pUomPppc);
        System.out.println("=================================================");
        */
        try {
            cst =
promoProposalAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_PPPC_OVERAGE_QTY ('" +
                                                           custType + "', '" +
                                                           listCustomer +
                                                           "', '" +
                                                           pItemNumber +
                                                           "', '" + pUomPppc +
                                                           "', '" +
                                                           pProgDaysPppc +
                                                           "', ?, ?); END;",
                                                           0);
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
//                prodCombItem = prodComb + "." + currVariant;
                 showPopup("Item Harus Diisi", potmessage);
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
        String Uom = itlovUomTarget.getValue().toString();
        String loggedUserName = userData.getUserNameLogin();
        /*
        System.out.println("=================================================");
        System.out.println("CUST TYPE: " + custType);
        System.out.println("PROD COMB: " + prodCombItem);
        System.out.println("LIST CUST: " + listCustomer);
        System.out.println("USER NAME: " + loggedUserName);
        System.out.println("Uom: " + Uom);
        System.out.println("=================================================");
        */
        if(!prodCombItem.equalsIgnoreCase(null)){
        try {
            cst =
            promoProposalAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_PPPC_GET_PRICE_LIST.INSERT_TABLE_PRICE_LIST('" +
                                                           custType + "', '" +
                                                           prodCombItem +
                                                           "', '" +
                                                           listCustomer +
                                                           "', '" +
                                                           loggedUserName +
                                                             "', '" + Uom +
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
                priceVal =
                        (Number)dciterPriceList.getCurrentRow().getAttribute("Price") ==
                        null ? new Number(0) :
                        (Number)dciterPriceList.getCurrentRow().getAttribute("Price");
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
        boolean canCreateNew = true;
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrDivId = userData.getUserDivision();
        String usrType = userData.getUserType();
        String usrCustomer =
            userData.getUserCustomer() == null ? "" : userData.getUserCustomer();

        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciterProposal =
            (DCIteratorBinding)bindings.get("ProposalView1Iterator");
        for (Row proposalRow : dciterProposal.getAllRowsInRange()) {
            Integer rowStatus =
                (Integer)proposalRow.getAttribute("CheckRowStatus");
            if (rowStatus == 0) {
                canCreateNew = false;
            }
        }

        if (canCreateNew) {
            RowSetIterator rsi = dciterProposal.getRowSetIterator();
            Row lastRow = rsi.last();
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
    
            if (usrDivId.equalsIgnoreCase("NONFOOD")) {
                newRow.setAttribute("ProposalType", "NONFOOD");
            } else if (usrDivId.equalsIgnoreCase("FOOD")) {
                newRow.setAttribute("ProposalType", "FOOD");
            } else {
                newRow.setAttribute("ProposalType", null);
            }
    
            if (usrCustomer.equalsIgnoreCase(userCustRegion)) {
                newRow.setAttribute("CustRegFlag", userCustRegion);
                newRow.setAttribute("CustRegFlagLov", userCustRegion);
            } else if (usrCustomer.equalsIgnoreCase(userCustArea)) {
                newRow.setAttribute("CustRegFlag", userCustArea);
                newRow.setAttribute("CustRegFlagLov", userCustArea);
            } else if (usrCustomer.equalsIgnoreCase(userCustLocation)) {
                newRow.setAttribute("CustRegFlag", userCustLocation);
                newRow.setAttribute("CustRegFlagLov", userCustLocation);
            } else if (usrCustomer.equalsIgnoreCase(userCustCustGroup)) {
                newRow.setAttribute("CustRegFlag", userCustCustGroup);
                newRow.setAttribute("CustRegFlagLov", userCustCustGroup);
            } else if (usrCustomer.equalsIgnoreCase(userCustCustomer)) {
                newRow.setAttribute("CustRegFlag", userCustCustomer);
                newRow.setAttribute("CustRegFlagLov", userCustCustomer);
            } else {
                newRow.setAttribute("CustRegFlag", userCustInvalid);
                newRow.setAttribute("CustRegFlagLov", userCustInvalid);
                JSFUtils.addFacesWarningMessage("Anda tidak memiliki hak akses memilih customer.");
            }
    
            newRow.setAttribute("UserTypeCreator", usrType);
    
            //add row to last index + 1 so it becomes last in the range set
            rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            //make row the current row so it is displayed correctly
            rsi.setCurrentRow(newRow);
    
            AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
        } else {
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Masih ada proposal baru yang belum dilengkapi dan belum tersimpan.</p>");
            message.append("<p>Proses penambahan proposal baru tidak dapat dilanjutkan.</p>");
            message.append("</body></html>");
            JSFUtils.addFacesWarningMessage(message.toString());
        }
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

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclCustLocView1Iterator");
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
        DCIteratorBinding dciterProposal =
            ADFUtils.findIterator("ProposalView1Iterator");
        if (dciterProposal.getEstimatedRowCount() > 0) {
            if ((usrDivId.equalsIgnoreCase("NONFOOD") &&
                 propTypeIdx.compareTo(idxNonFood) == 0) ||
                (usrDivId.equalsIgnoreCase("NONFOOD") &&
                 propTypeIdx.compareTo(idxNonFood) != 0)) {
                itLovProposalType.setSubmittedValue("1");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "NONFOOD";
            } else if ((usrDivId.equalsIgnoreCase("FOOD") &&
                        propTypeIdx.compareTo(idxFood) == 0) ||
                       (usrDivId.equalsIgnoreCase("FOOD") &&
                        propTypeIdx.compareTo(idxFood) != 0)) {
                itLovProposalType.setSubmittedValue("0");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "FOOD";
            } else if (usrDivId.equalsIgnoreCase("ALL") &&
                       propTypeIdx.compareTo(idxNonFood) == 0) {
                itLovProposalType.setSubmittedValue("1");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "NONFOOD";
            } else if (usrDivId.equalsIgnoreCase("ALL") &&
                       propTypeIdx.compareTo(idxFood) == 0) {
                itLovProposalType.setSubmittedValue("0");
                AdfFacesContext.getCurrentInstance().addPartialTarget(itLovProposalType);
                userDivision = "FOOD";
            } else {
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                RowSetIterator rsiPromoProduk =
                    dciterPromoProduk.getRowSetIterator();
                if (dciterPromoProduk.getEstimatedRowCount() > 0) {
                    Row promoProdukRow = rsiPromoProduk.first();
                    String kodeCategory =
                        (String)promoProdukRow.getAttribute("ProductCategory");
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
        String socType = socTypePotongan.getValue().toString();
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row row = dciterTarget.getCurrentRow();
        BigDecimal ontop = BigDecimal.ZERO;
        BigDecimal rOntop = BigDecimal.ZERO;
        BigDecimal maxOntop = BigDecimal.ZERO;
        BigDecimal RasioOntop = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;
        Number qty =
            (Number)row.getAttribute("Qty") == null ? new Number(0) : (Number)row.getAttribute("Qty");
        Number value =
            (Number)row.getAttribute("Value") == null ? new Number(0) :
            (Number)row.getAttribute("Value");

        if (value.compareTo(zeroNumber) < 0 || value.compareTo(zeroNumber) > 0) {

            if (socType.equalsIgnoreCase("PERCENT")) {
                rOntop =
                        new BigDecimal(rowOntop.getValue().toString().replaceAll(",",
                                                                                 ""));
                String rasio =
                    otRasioMf.getValue() == "" ? "0" : otRasioMf.getValue() == null ? "0" : otRasioMf.getValue().toString();
                rasioT =
                        new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                       ""));
                ontop =
                        (value.multiply(rOntop)).getBigDecimalValue().divide(bdHundred);
                otOnTop.setSubmittedValue(ontop);
                RasioOntop =
                        ontop.divide(value.getBigDecimalValue()).multiply(bdHundred);
                String rasOntop = RasioOntop.toString();
                //            System.out.println("rasOntop = "+rasOntop);
                otRasioOntop.setSubmittedValue(rasOntop);
                rasioTotal = RasioOntop.add(rasioT);
                String total = rasioTotal.toString();
                otRasioTotal.setSubmittedValue(total);
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Row r = dciterPromoProduk.getCurrentRow();
                r.setAttribute("DiscOnTop", ontop);
                r.setAttribute("DiscRasioOnTop", rasOntop);
                r.setAttribute("DiscRasioTotal1", total);
                dciterPromoProduk.getDataControl().commitTransaction();

                AdfFacesContext.getCurrentInstance().addPartialTarget(otOnTop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioOntop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
            } else {
                String discId = "";
                DCBindingContainer bindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dcItteratorBindings =
                    bindings.findIteratorBinding("DiscountView1Iterator");
                ViewObject voTableData = dcItteratorBindings.getViewObject();
                Row rowSelected = voTableData.getCurrentRow();
                if (rowSelected.getAttribute("DiscountId") != null) {
                    discId = rowSelected.getAttribute("DiscountId").toString();
                }
                DCIteratorBinding dciterDiscount1 =
                    ADFUtils.findIterator("DiscountView1Iterator");
                for (Row r : dciterDiscount1.getAllRowsInRange()) {
                    String discIdComp =
                        r.getAttribute("DiscountId").toString();
                    if (!discId.equalsIgnoreCase(discIdComp)) {
                        String valueTop =
                            r.getAttribute("DiscNonYearly").toString().replaceAll(",",
                                                                                  "");
                        BigDecimal newChangedOnTopValue =
                            new BigDecimal(valueChangeEvent.getNewValue() ==
                                           "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                        BigDecimal ontopValue =
                            new BigDecimal(valueTop) == null ?
                            new BigDecimal(0) : new BigDecimal(valueTop);

                        if (maxOntop.compareTo(ontopValue) < 0) {
                            maxOntop = ontopValue;
                        }

                        if (maxOntop.compareTo(newChangedOnTopValue) < 0) {
                            maxOntop = newChangedOnTopValue;
                        }
                    } else {
                        BigDecimal newChangedOnTopValue =
                            new BigDecimal(valueChangeEvent.getNewValue() ==
                                           "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                        if (maxOntop.compareTo(newChangedOnTopValue) < 0) {
                            maxOntop = newChangedOnTopValue;
                        }
                    }
                }
                ontop = qty.getBigDecimalValue().multiply(maxOntop);
                otOnTop.setSubmittedValue(ontop);
                RasioOntop =
                        ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                String rasOntop = RasioOntop.toString();
                otRasioOntop.setSubmittedValue(rasOntop);
                String rasio =
                    otRasioMf.getValue() == "" ? "0" : otRasioMf.getValue() == null ? "0" : otRasioMf.getValue().toString();
                rasioT =
                        new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                       ""));
                rasioTotal = RasioOntop.add(rasioT);
                String total = rasioTotal.toString();
                otRasioTotal.setSubmittedValue(total);
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Row r = dciterPromoProduk.getCurrentRow();
                r.setAttribute("DiscOnTop", ontop);
                r.setAttribute("DiscRasioOnTop", rasOntop);
                r.setAttribute("DiscRasioTotal1", total);
                dciterPromoProduk.getDataControl().commitTransaction();

                AdfFacesContext.getCurrentInstance().addPartialTarget(otOnTop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioOntop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
            }
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
        } else {
            JSFUtils.addFacesWarningMessage("Quantity target dan harga tidak boleh 0 (nol) atau kosong.");
        }

    }

    public void refreshMf(ValueChangeEvent valueChangeEvent) {
        String socType = socTypePotongan.getValue().toString();
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row row = dciterTarget.getCurrentRow();
        BigDecimal mf = BigDecimal.ZERO;
        BigDecimal rMf = BigDecimal.ZERO;
        BigDecimal maxMf = BigDecimal.ZERO;
        BigDecimal RasioMf = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;
        Number qty =
            (Number)row.getAttribute("Qty") == null ? new Number(0) : (Number)row.getAttribute("Qty");
        Number value =
            (Number)row.getAttribute("Value") == null ? new Number(0) :
            (Number)row.getAttribute("Value");

        if (socType.equalsIgnoreCase("PERCENT")) {
            rMf =
new BigDecimal(rowMf.getValue().toString().replaceAll(",", ""));
            mf =
 ((value.multiply(rMf)).getBigDecimalValue()).divide(bdHundred);
            otMF.setSubmittedValue(mf);
            RasioMf =
                    mf.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasMf = RasioMf.toString();
            otRasioMf.setSubmittedValue(rasMf);
            rasioT =
                    new BigDecimal(otRasioOntop.getValue().toString().replaceAll(" ",
                                                                                 "").replaceAll("%",
                                                                                                ""));
            rasioTotal = RasioMf.add(rasioT);
            String Total = rasioTotal.toString();
            otRasioTotal.setSubmittedValue(Total);

            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            Row r = dciterPromoProduk.getCurrentRow();
            r.setAttribute("DiscMf", mf);
            r.setAttribute("DiscRasioMf", rasMf);
            r.setAttribute("DiscRasioTotal1", Total);
            dciterPromoProduk.getDataControl().commitTransaction();

            AdfFacesContext.getCurrentInstance().addPartialTarget(otMF);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
        } else {
            String discId = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("DiscountView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();

            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("DiscountId") != null) {
                discId = rowSelected.getAttribute("DiscountId").toString();
            }
            DCIteratorBinding dciterDiscount1 =
                ADFUtils.findIterator("DiscountView1Iterator");
            for (Row r : dciterDiscount1.getAllRowsInRange()) {
                String discIdComp = r.getAttribute("DiscountId").toString();
                if (!discId.equalsIgnoreCase(discIdComp)) {
                    String valueMF =
                        r.getAttribute("DiscYearly").toString().replaceAll(",",
                                                                           "");
                    BigDecimal mfValue =
                        new BigDecimal(valueMF) == null ? new BigDecimal(0) :
                        new BigDecimal(valueMF);
                    BigDecimal newChangedMfValue =
                        new BigDecimal(valueChangeEvent.getNewValue() == "" ?
                                       "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                       valueChangeEvent.getNewValue().toString());
                    if (maxMf.compareTo(mfValue) < 0) {
                        maxMf = mfValue;
                    }
                    if (maxMf.compareTo(newChangedMfValue) < 0) {
                        maxMf = newChangedMfValue;
                    }
                } else {
                    BigDecimal newChangedMfValue =
                        new BigDecimal(valueChangeEvent.getNewValue() == "" ?
                                       "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                       valueChangeEvent.getNewValue().toString());
                    if (maxMf.compareTo(newChangedMfValue) < 0) {
                        maxMf = newChangedMfValue;
                    }
                }
            }
            mf = qty.getBigDecimalValue().multiply(maxMf);
            otMF.setSubmittedValue(mf);
            RasioMf =
                    mf.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasMf = RasioMf.toString();
            otRasioMf.setSubmittedValue(rasMf);
            rasioT =
                    new BigDecimal(otRasioOntop.getValue().toString().replaceAll(" ",
                                                                                 "").replaceAll("%",
                                                                                                ""));
            rasioTotal = RasioMf.add(rasioT);
            String Total = rasioTotal.toString();
            otRasioTotal.setSubmittedValue(Total);

            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            Row r = dciterPromoProduk.getCurrentRow();
            r.setAttribute("DiscMf", mf);
            r.setAttribute("DiscRasioMf", rasMf);
            r.setAttribute("DiscRasioTotal1", Total);
            dciterPromoProduk.getDataControl().commitTransaction();

            AdfFacesContext.getCurrentInstance().addPartialTarget(otMF);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
        }
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
    }

    public void setTableListPotongan(RichTable tableListPotongan) {
        this.tableListPotongan = tableListPotongan;
    }

    public RichTable getTableListPotongan() {
        return tableListPotongan;
    }

    public void approvalHistPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        DCIteratorBinding dciter =
            ADFUtils.findIterator("DocApprovalView1Iterator");
        DocApprovalViewImpl aprvlHistView =
            (DocApprovalViewImpl)dciter.getViewObject();
        aprvlHistView.executeQuery();
    }

    public void setSocTypePotongan(RichInputListOfValues socTypePotongan) {
        this.socTypePotongan = socTypePotongan;
    }

    public RichInputListOfValues getSocTypePotongan() {
        return socTypePotongan;
    }

    public void setOtOnTop(RichInputText otOnTop) {
        this.otOnTop = otOnTop;
    }

    public RichInputText getOtOnTop() {
        return otOnTop;
    }

    public void setOtMF(RichInputText otMF) {
        this.otMF = otMF;
    }

    public RichInputText getOtMF() {
        return otMF;
    }

    public void setRowOntop(RichInputText rowOntop) {
        this.rowOntop = rowOntop;
    }

    public RichInputText getRowOntop() {
        return rowOntop;
    }

    public void setRowMf(RichInputText rowMf) {
        this.rowMf = rowMf;
    }

    public RichInputText getRowMf() {
        return rowMf;
    }

    public void setValueMf(BigDecimal valueMf) {
        this.valueMf = valueMf;
    }

    public BigDecimal getValueMf() {
        return valueMf;
    }

    public void setOtRasioOntop(RichInputText otRasioOntop) {
        this.otRasioOntop = otRasioOntop;
    }

    public RichInputText getOtRasioOntop() {
        return otRasioOntop;
    }

    public void setOtRasioMf(RichInputText otRasioMf) {
        this.otRasioMf = otRasioMf;
    }

    public RichInputText getOtRasioMf() {
        return otRasioMf;
    }

    public void setOtRasioTotal(RichInputText otRasioTotal) {
        this.otRasioTotal = otRasioTotal;
    }

    public RichInputText getOtRasioTotal() {
        return otRasioTotal;
    }

    public void removeRowPotongan(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("DiscountView1Iterator");
        RowSetIterator rsi = dcItteratorBindings.getRowSetIterator();
        Integer currRowIndex = rsi.getCurrentRowIndex() + 1;
        Row lastRow = rsi.last();
        Integer fetchedRowCount = rsi.getFetchedRowCount();
        
        if (dcItteratorBindings.getEstimatedRowCount() == 1) {
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("DiscountId") != null) {
                voTableData.removeCurrentRow();
                OperationBinding operation =
                    (OperationBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("Commit");
                operation.execute();
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Row r = dciterPromoProduk.getCurrentRow();
                r.setAttribute("DiscOnTop", 0);
                r.setAttribute("DiscRasioOnTop", 0);
                r.setAttribute("DiscMf", 0);
                r.setAttribute("DiscRasioMf", 0);
                r.setAttribute("DiscRasioTotal1", 0);
                dciterPromoProduk.getDataControl().commitTransaction();
                otOnTop.setSubmittedValue(0);
                otRasioOntop.setSubmittedValue(0);
                otRasioTotal.setSubmittedValue(0);
                otMF.setSubmittedValue(0);
                otRasioMf.setSubmittedValue(0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otOnTop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioOntop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otMF);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
            }
        } else {
            if (fetchedRowCount == currRowIndex) {
                ViewObject voTableData = dcItteratorBindings.getViewObject();
                Row rowSelected = voTableData.getCurrentRow();
                if (rowSelected.getAttribute("DiscountId") != null) {
                    voTableData.removeCurrentRow();
                    OperationBinding operation =
                        (OperationBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("Commit");
                    operation.execute();
                }
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Row r = dciterPromoProduk.getCurrentRow();
                DCIteratorBinding dciterDiscount =
                    ADFUtils.findIterator("DiscountView1Iterator");
                BigDecimal totalMf = BigDecimal.ZERO;
                BigDecimal totalOntop = BigDecimal.ZERO;
                for (Row er : dciterDiscount.getAllRowsInRange()) {
                    String valueMF = null;
                    try {
                        valueMF = er.getAttribute("DiscYearly").toString().replaceAll(",", "");
                    } catch (Exception e) {
                        valueMF = null;
                    }
                    
                    String valueTop = null;
                    try {
                        valueTop = er.getAttribute("DiscNonYearly").toString().replaceAll(",", "");
                    } catch (Exception e) {
                        valueTop = null;
                    }
                    
                    BigDecimal ontopValue =
                        valueTop == null ? new BigDecimal(0) :
                        new BigDecimal(valueTop);
                    BigDecimal mfValue =
                        valueMF == null ? new BigDecimal(0) :
                        new BigDecimal(valueMF);
                    totalMf = totalMf.add(mfValue);
                    totalOntop = totalOntop.add(ontopValue);
                }
                DCIteratorBinding dciterTarget =
                    ADFUtils.findIterator("TargetView1Iterator");
                Row row = dciterTarget.getCurrentRow();
                BigDecimal mf = BigDecimal.ZERO;
                BigDecimal rMf = BigDecimal.ZERO;
                BigDecimal RasioMf = BigDecimal.ZERO;
                BigDecimal rasioTotal = BigDecimal.ZERO;
                BigDecimal rasioT = BigDecimal.ZERO;
                BigDecimal ontop = BigDecimal.ZERO;
                BigDecimal rOntop = BigDecimal.ZERO;
                BigDecimal RasioOntop = BigDecimal.ZERO;
                Number qty =
                    (Number)row.getAttribute("Qty") == null ? new Number(0) :
                    (Number)row.getAttribute("Qty");
                Number value =
                    (Number)row.getAttribute("Value") == null ? new Number(0) :
                    (Number)row.getAttribute("Value");
    
                DCIteratorBinding dciterDiscountTgetTPot =
                    ADFUtils.findIterator("DiscountView1Iterator");
                for (Row ere : dciterDiscountTgetTPot.getAllRowsInRange()) {
                    String typePot =
                        ere.getAttribute("TipePotongan").toString().replaceAll(",",
                                                                               "");
                    if (typePot.equalsIgnoreCase("PERCENT")) {
                        rMf = totalMf;
                        mf =
     (value.multiply(rMf)).getBigDecimalValue().divide(bdHundred);
                        otMF.setSubmittedValue(mf);
                        RasioMf =
                                mf.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                        String rasMf = RasioMf.toString();
                        otRasioMf.setSubmittedValue(rasMf);
                        rasioT =
                                new BigDecimal(otRasioOntop.getValue().toString().replaceAll(" ",
                                                                                             "").replaceAll("%",
                                                                                                            ""));
                        rasioTotal = RasioMf.add(rasioT);
                        String Total = rasioTotal.toString();
                        otRasioTotal.setSubmittedValue(Total);
    
                        rOntop = totalOntop;
                        String rasio =
                            otRasioMf.getValue() == "" ? "0" :
                            otRasioMf.getValue() == null ? "0" :
                            otRasioMf.getValue().toString();
                        rasioT =
                                new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                               ""));
                        ontop =
                                (value.multiply(rOntop)).getBigDecimalValue().divide(bdHundred);
                        otOnTop.setSubmittedValue(ontop);
                        RasioOntop =
                                ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                        String rasOntop = RasioOntop.toString();
                        otRasioOntop.setSubmittedValue(rasOntop);
    
                        r.setAttribute("DiscOnTop", ontop);
                        r.setAttribute("DiscRasioOnTop", rasOntop);
                        r.setAttribute("DiscRasioTotal1", Total);
                        r.setAttribute("DiscMf", mf);
                        r.setAttribute("DiscRasioMf", rasMf);
    
                        dciterPromoProduk.getDataControl().commitTransaction();
    
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otOnTop);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioOntop);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otMF);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
    
                    } else {
                        ontop = qty.getBigDecimalValue().multiply(totalOntop);
                        otOnTop.setSubmittedValue(ontop);
                        RasioOntop =
                                ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                        String rasOntop = RasioOntop.toString();
    
                        otRasioOntop.setSubmittedValue(rasOntop);
                        String rasio =
                            otRasioMf.getValue() == "" ? "0" :
                            otRasioMf.getValue() == null ? "0" :
                            otRasioMf.getValue().toString();
                        rasioT =
                                new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                               ""));
                        rasioTotal = RasioOntop.add(rasioT);
                        String total = rasioTotal.toString();
                        otRasioTotal.setSubmittedValue(total);
    
                        mf = qty.getBigDecimalValue().multiply(totalMf);
                        otMF.setSubmittedValue(mf);
                        RasioMf =
                                mf.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                        String rasMf = RasioMf.toString();
                        otRasioMf.setSubmittedValue(rasMf);
    
                        r.setAttribute("DiscOnTop", ontop);
                        r.setAttribute("DiscRasioOnTop", rasOntop);
                        r.setAttribute("DiscRasioTotal1", total);
                        r.setAttribute("DiscMf", mf);
                        r.setAttribute("DiscRasioMf", rasMf);
                        dciterPromoProduk.getDataControl().commitTransaction();
    
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otMF);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otOnTop);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioOntop);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioTotal);
                    }
                }
            } else {
                JSFUtils.addFacesWarningMessage("Potongan hanya dapat dihapus mulai dari baris terakhir.");
            }
        }
    }

    public void tabExcludeEvent(DisclosureEvent disclosureEvent) {
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
    }

    public void clearAllCustExclude() {

        BindingContainer bindings = this.getBindings();
        AttributeBinding usrTypeCreatorAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String usrTypeCreator = (String)usrTypeCreatorAttr.getInputValue();

        if (usrTypeCreator.equalsIgnoreCase(userHo)) {
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

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);

        } else if (usrTypeCreator.equalsIgnoreCase(userArea)) {

            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ExclPropCustCustView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();

            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ExclPropCustRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();

            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ExclPropCustLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();

            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ExclPropCustAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();

        } else {
            JSFUtils.addFacesWarningMessage("\"User Type Creator\" pada proposal tidak dikenali. Event: clearing exclude.");
        }

        // Clear column "ExclCustBy"
        DCIteratorBinding dciterPromoProduk =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        if (dciterPromoProduk.getEstimatedRowCount() > 0) {
            for (Row r : dciterPromoProduk.getAllRowsInRange()) {
                DBSequence promoProdId =
                    (DBSequence)r.getAttribute("PromoProdukId");
                r.setAttribute("ExclCustBy", null);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void priceListPromoBonusPopupFetchListener(PopupFetchEvent popupFetchEvent) {
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

        String ProdPromCombFirst = "";
        String ProdPromCombEnd = "";
        String PromoBonusIdSel = "";
        String Uom = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoBonusView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("PromoBonusId") != null) {
            PromoBonusIdSel =
                    rowSelected.getAttribute("PromoBonusId").toString();
            ProdPromCombFirst =
                    rowSelected.getAttribute("ProductCategory").toString() +
                    "." + rowSelected.getAttribute("ProductClass").toString() +
                    "." + rowSelected.getAttribute("ProductBrand").toString() +
                    "." + rowSelected.getAttribute("ProductExt").toString() +
                    "." + rowSelected.getAttribute("ProductPack").toString();
            Uom = rowSelected.getAttribute("Uom").toString();
        }
        DCIteratorBinding dciterPromoVariant =
            ADFUtils.findIterator("PromoBonusVariantView1Iterator");
        long rowCountVar = dciterPromoVariant.getEstimatedRowCount();
        if (rowCountVar > 0) {
            String variantCode = "";
            String varUom = "";
            StringBuilder sb = new StringBuilder();
            for (Row r : dciterPromoVariant.getAllRowsInRange()) {
                String promoBonusIdVar =
                    r.getAttribute("PromoBonusId").toString();
                if (PromoBonusIdSel.equalsIgnoreCase(promoBonusIdVar)) {
                    String varCode = r.getAttribute("ProdVariant").toString();
                    sb.append(varCode + ";");

                }
            }

            variantCode = sb.substring(0, sb.length() - 1);
            String variantEnd = variantCode.substring(0, 3);

            if (variantEnd.equalsIgnoreCase("ALL")) {
                DCIteratorBinding dciterPromBonusItem =
                    ADFUtils.findIterator("PromoBonusProdItemView1Iterator");
                long rowCountItem = dciterPromBonusItem.getEstimatedRowCount();
                if (rowCountItem > 0) {
                    StringBuilder sbItem = new StringBuilder();
                    for (Row rIt : dciterPromBonusItem.getAllRowsInRange()) {
                        String promoBonusIdIt =
                            rIt.getAttribute("PromoBonusId").toString();
                        if (PromoBonusIdSel.equalsIgnoreCase(promoBonusIdIt)) {
                            String itemCode =
                                rIt.getAttribute("ProdItem").toString();
                            sbItem.append(itemCode + ";");
                        }
                    }
                    ProdPromCombEnd = sbItem.substring(0, sbItem.length() - 1);
                } else {
                    //                    ProdPromCombEnd=ProdPromCombFirst;
                    showPopup("Item Harus Diisi", potmessage);
                }
            } else {
                ProdPromCombEnd = ProdPromCombFirst + "." + variantCode;
            }
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
        System.out.println("PROD COMB: " + ProdPromCombEnd);
        System.out.println("LIST CUST: " + listCustomer);
        System.out.println("USER NAME: " + loggedUserName);
        System.out.println("Uom: " + Uom);
        System.out.println("=================================================");
        */
        try {
            cst =
promoProposalAM.getDBTransaction().createCallableStatement("BEGIN APPS.FCS_PPPC_GET_PRICE_LIST.INSERT_TABLE_PRICE_LIST('" +
                                                           custType + "', '" +
                                                           ProdPromCombEnd +
                                                           "', '" +
                                                           listCustomer +
                                                           "', '" +
                                                           loggedUserName +
                                                           "', '" + Uom +
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

    public void priceListpromoBonusDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            String PromoBonusIdSel = "";
            Number Qty = new Number(0);
            DCBindingContainer bindingsSelRow =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindingsSelRow.findIteratorBinding("PromoBonusView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("PromoBonusId") != null) {
                PromoBonusIdSel =
                        rowSelected.getAttribute("PromoBonusId").toString();
                Qty =
(Number)rowSelected.getAttribute("QtyFrom") == null ? new Number(0) :
(Number)rowSelected.getAttribute("QtyFrom");
            }
            DCIteratorBinding dciterPriceList =
                ADFUtils.findIterator("FcsPppcPriceListView1Iterator");
            Number priceVal = new Number(0);
            if (dciterPriceList.getEstimatedRowCount() > 0) {
                priceVal =
                        (Number)dciterPriceList.getCurrentRow().getAttribute("Price") ==
                        null ? new Number(0) :
                        (Number)dciterPriceList.getCurrentRow().getAttribute("Price");
            }
            BigDecimal endQty = new BigDecimal(Qty.getValue());
            BigDecimal tgtHarga = new BigDecimal(priceVal.getValue());

            BigDecimal totalValue = endQty.multiply(tgtHarga);
            oracle.jbo.domain.Number number = null;
            try {
                number =
                        new oracle.jbo.domain.Number(df2dgt.format(totalValue).toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }

            String PromoBonusIdSeT = "";
            DCIteratorBinding dciterPromoBonusSet =
                ADFUtils.findIterator("PromoBonusView1Iterator");
            Row r = dciterPromoBonusSet.getCurrentRow();
            PromoBonusIdSeT = r.getAttribute("PromoBonusId").toString();
            if (PromoBonusIdSeT.equalsIgnoreCase(PromoBonusIdSel)) {
                r.setAttribute("PriceVal", number);
                itPricePromoBonus.setSubmittedValue(number);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itPricePromoBonus);
                dciterPromoBonusSet.getDataControl().commitTransaction();
                dciterPromoBonusSet.executeQuery();
            }
        }
    }

    public void refreshOntopProBar(ValueChangeEvent valueChangeEvent) {
        String PromoBonusIdSel = "";
        Number Mf = new Number(0);
        Number PriceVal = new Number(0);
        Number QtyFrom = new Number(0);
        BigDecimal RasioOntop = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;

        String InputPriceBy = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoBonusView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("PromoBonusId") != null) {
            PromoBonusIdSel =
                    rowSelected.getAttribute("PromoBonusId").toString();
            PriceVal =
                    (Number)rowSelected.getAttribute("PriceVal") == null ? new Number(0) :
                    (Number)rowSelected.getAttribute("PriceVal");
            Mf =
 (Number)rowSelected.getAttribute("DiscYearly") == null ? new Number(0) :
 (Number)rowSelected.getAttribute("DiscYearly");
            InputPriceBy =
                    rowSelected.getAttribute("InputPriceBy") == null ? "" :
                    rowSelected.getAttribute("InputPriceBy").toString();
            QtyFrom =
                    (Number)rowSelected.getAttribute("QtyFrom") == null ? new Number(0) :
                    (Number)rowSelected.getAttribute("QtyFrom");
        }

        if (InputPriceBy.equalsIgnoreCase("PRICELIST")) {
            if (PriceVal.compareTo(zeroNumber) < 0 || PriceVal.compareTo(zeroNumber) > 0) {
            BigDecimal newChangedOnTopValueCek =
                new BigDecimal(valueChangeEvent.getNewValue() == "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                               valueChangeEvent.getNewValue().toString());
                BigDecimal totalOntopcek =
                    newChangedOnTopValueCek.add(Mf.getBigDecimalValue());
                BigDecimal totalPriceQty =
                    PriceVal.getBigDecimalValue().multiply(QtyFrom.getBigDecimalValue());
                BigDecimal val =
                    totalOntopcek.divide(totalPriceQty, 2, RoundingMode.HALF_UP);
                if (val.compareTo(BigDecimal.ONE) == 1) {
                    showPopup("Persentase Lebih Dari 100 % dari Quantity dikali Price",
                              potmessage);
                    btnOkpromoDetail.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                } else {
                    DCIteratorBinding dciterTarget =
                        ADFUtils.findIterator("TargetView1Iterator");
                    Row row = dciterTarget.getCurrentRow();
                    Number value =
                        (Number)row.getAttribute("Value") == null ? new Number(0) :
                        (Number)row.getAttribute("Value");
    
                    DCIteratorBinding dcItteratorBrgBonusOT =
                        ADFUtils.findIterator("PromoBonusView1Iterator");
                    ViewObject voTableData1 =
                        dcItteratorBrgBonusOT.getViewObject();
                    RowSetIterator iter = voTableData1.createRowSetIterator(null);
                    BigDecimal totalOntop = new BigDecimal(0);
                    BigDecimal valueOTsel = BigDecimal.ZERO;
                    while (iter.hasNext()) {
                        Row r = iter.next();
                        String PromoBonusIdBrg =
                            r.getAttribute("PromoBonusId").toString();
                        if (PromoBonusIdBrg.equalsIgnoreCase(PromoBonusIdSel)) {
                            BigDecimal newChangedOnTopValue =
                                new BigDecimal(valueChangeEvent.getNewValue() ==
                                               "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                               valueChangeEvent.getNewValue().toString());
                            valueOTsel = valueOTsel.add(newChangedOnTopValue);
                            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        }
                        Number ontop =
                            (Number)r.getAttribute("DiscNonYearly") == null ?
                            new Number(0) :
                            (Number)r.getAttribute("DiscNonYearly");
                        totalOntop = totalOntop.add(ontop.getBigDecimalValue());
                    }
                    iter.closeRowSetIterator();
                    String rasio =
                        otBrgRasioMf.getValue() == "" ? "0" : otBrgRasioMf.getValue() == null ? "0" : otBrgRasioMf.getValue().toString();
                    rasioT =
                            new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                           ""));
                    otBrgOnTop.setSubmittedValue(totalOntop);
                    RasioOntop =
                            totalOntop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                    BigDecimal rasOntop = RasioOntop;
    
                    otBrgRasioOnTop.setSubmittedValue(rasOntop);
                    rasioTotal = RasioOntop.add(rasioT);
                    String total = rasioTotal.toString();
                    otBrgRasioTotal.setSubmittedValue(total);
    
                    DCIteratorBinding dciterPromoProduk =
                        ADFUtils.findIterator("PromoProdukView1Iterator");
                    Row r = dciterPromoProduk.getCurrentRow();
                    r.setAttribute("BrgBonusOnTop", totalOntop);
                    r.setAttribute("BrgBonusRasioOnTop", rasOntop);
                    r.setAttribute("BrgBonusRasioTotal", total);
    
                    dciterPromoProduk.getDataControl().commitTransaction();
                    dciterPromoProduk.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgOnTop);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioOnTop);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioTotal);
                    btnOkpromoDetail.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                }
            } else {
                JSFUtils.addFacesWarningMessage("Kolom \"Price\" tidak boleh kosong atau diisi 0 (Nol)");
            }
        } else {
            if (PriceVal.compareTo(zeroNumber) < 0 || PriceVal.compareTo(zeroNumber) > 0) {
            BigDecimal newChangedOnTopValueCek =
                new BigDecimal(valueChangeEvent.getNewValue() == "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                               valueChangeEvent.getNewValue().toString());
                BigDecimal totalOntopcek =
                    newChangedOnTopValueCek.add(Mf.getBigDecimalValue());
                BigDecimal totalPriceQty =
                    PriceVal.getBigDecimalValue().multiply(QtyFrom.getBigDecimalValue());
                BigDecimal val =
                    totalOntopcek.divide(totalPriceQty, 2, RoundingMode.HALF_UP);
                if (val.compareTo(BigDecimal.ONE) == 1) {
                    showPopup("Persentase Lebih Dari 100 % dari Quantity dikali Price",
                              potmessage);
                    btnOkpromoDetail.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                } else {
                    DCIteratorBinding dciterTarget =
                        ADFUtils.findIterator("TargetView1Iterator");
                    Row row = dciterTarget.getCurrentRow();
                    Number value =
                        (Number)row.getAttribute("Value") == null ? new Number(0) :
                        (Number)row.getAttribute("Value");
    
                    DCIteratorBinding dcItteratorBrgBonusOT =
                        ADFUtils.findIterator("PromoBonusView1Iterator");
                    ViewObject voTableData1 =
                        dcItteratorBrgBonusOT.getViewObject();
                    RowSetIterator iter = voTableData1.createRowSetIterator(null);
                    BigDecimal totalOntop = new BigDecimal(0);
                    BigDecimal valueOTsel = BigDecimal.ZERO;
                    while (iter.hasNext()) {
                        Row r = iter.next();
                        String PromoBonusIdBrg =
                            r.getAttribute("PromoBonusId").toString();
                        if (PromoBonusIdBrg.equalsIgnoreCase(PromoBonusIdSel)) {
                            BigDecimal newChangedOnTopValue =
                                new BigDecimal(valueChangeEvent.getNewValue() ==
                                               "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                               valueChangeEvent.getNewValue().toString());
                            valueOTsel = valueOTsel.add(newChangedOnTopValue);
                            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        }
                        Number ontop =
                            (Number)r.getAttribute("DiscNonYearly") == null ?
                            new Number(0) :
                            (Number)r.getAttribute("DiscNonYearly");
                        totalOntop = totalOntop.add(ontop.getBigDecimalValue());
                    }
                    iter.closeRowSetIterator();
                    String rasio =
                        otBrgRasioMf.getValue() == "" ? "0" : otBrgRasioMf.getValue() == null ? "0" : otBrgRasioMf.getValue().toString();
                    rasioT =
                            new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                           ""));
                    otBrgOnTop.setSubmittedValue(totalOntop);
                    RasioOntop =
                            totalOntop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                    BigDecimal rasOntop = RasioOntop;
    
                    otBrgRasioOnTop.setSubmittedValue(rasOntop);
                    rasioTotal = RasioOntop.add(rasioT);
                    String total = rasioTotal.toString();
                    otBrgRasioTotal.setSubmittedValue(total);
    
                    DCIteratorBinding dciterPromoProduk =
                        ADFUtils.findIterator("PromoProdukView1Iterator");
                    Row r = dciterPromoProduk.getCurrentRow();
                    r.setAttribute("BrgBonusOnTop", totalOntop);
                    r.setAttribute("BrgBonusRasioOnTop", rasOntop);
                    r.setAttribute("BrgBonusRasioTotal", total);
    
                    dciterPromoProduk.getDataControl().commitTransaction();
                    dciterPromoProduk.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgOnTop);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioOnTop);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioTotal);
                    btnOkpromoDetail.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                }
            } else {
                JSFUtils.addFacesWarningMessage("Kolom \"Price\" tidak boleh kosong atau diisi 0 (Nol)");
            }
        }
    }

    public void refreshMfProBar(ValueChangeEvent valueChangeEvent) {
        String PromoBonusIdSel = "";
        String InputPriceBy = "";
        Number QtyFrom = new Number(0);
        Number ontop = new Number(0);
        Number PriceVal = new Number(0);

        BigDecimal RasioMf = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;

        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoBonusView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("PromoBonusId") != null) {
            PromoBonusIdSel =
                    rowSelected.getAttribute("PromoBonusId").toString();
            PriceVal =
                    (Number)rowSelected.getAttribute("PriceVal") == null ? new Number(0) :
                    (Number)rowSelected.getAttribute("PriceVal");
            ontop =
                    (Number)rowSelected.getAttribute("DiscNonYearly") == null ? new Number(0) :
                    (Number)rowSelected.getAttribute("DiscNonYearly");
            InputPriceBy =
                    rowSelected.getAttribute("InputPriceBy") == null ? "" :
                    rowSelected.getAttribute("InputPriceBy").toString();
            QtyFrom =
                    (Number)rowSelected.getAttribute("QtyFrom") == null ? new Number(0) :
                    (Number)rowSelected.getAttribute("QtyFrom");
        }

        if (InputPriceBy.equalsIgnoreCase("PRICELIST")) {
            if (PriceVal.compareTo(zeroNumber) < 0 || PriceVal.compareTo(zeroNumber) > 0) {
            BigDecimal newChangedMfValueCek =
                new BigDecimal(valueChangeEvent.getNewValue() == "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                               valueChangeEvent.getNewValue().toString());
                BigDecimal totalOntop =
                    newChangedMfValueCek.add(ontop.getBigDecimalValue());
                BigDecimal totalPriceQty =
                    PriceVal.getBigDecimalValue().multiply(QtyFrom.getBigDecimalValue());
                BigDecimal val =
                    totalOntop.divide(totalPriceQty, 2, RoundingMode.HALF_UP);
                if (val.compareTo(BigDecimal.ONE) == 1) {
                    showPopup("Persentase Lebih Dari 100 % dari Quantity dikali Price",
                              potmessage);
                    btnOkpromoDetail.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                } else {
                    DCIteratorBinding dciterTarget =
                        ADFUtils.findIterator("TargetView1Iterator");
                    Row row = dciterTarget.getCurrentRow();
                    Number value =
                        (Number)row.getAttribute("Value") == null ? new Number(0) :
                        (Number)row.getAttribute("Value");
    
                    DCIteratorBinding dcItteratorBrgBonusMF =
                        ADFUtils.findIterator("PromoBonusView1Iterator");
                    ViewObject voTableDataMF =
                        dcItteratorBrgBonusMF.getViewObject();
                    RowSetIterator iterMF =
                        voTableDataMF.createRowSetIterator(null);
                    BigDecimal valueOTsel = BigDecimal.ZERO;
                    BigDecimal totalMF = new BigDecimal(0);
                    while (iterMF.hasNext()) {
                        Row r = iterMF.next();
                        String PromoBonusIdBrg =
                            r.getAttribute("PromoBonusId").toString();
                        if (PromoBonusIdBrg.equalsIgnoreCase(PromoBonusIdSel)) {
                            BigDecimal newChangedMfValue =
                                new BigDecimal(valueChangeEvent.getNewValue() ==
                                               "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                               valueChangeEvent.getNewValue().toString());
                            valueOTsel = valueOTsel.add(newChangedMfValue);
                            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        }
                        Number MF =
                            (Number)r.getAttribute("DiscYearly") == null ? new Number(0) :
                            (Number)r.getAttribute("DiscYearly");
                        totalMF = totalMF.add(MF.getBigDecimalValue());
                    }
                    iterMF.closeRowSetIterator();
                    String rasio =
                        otBrgRasioOnTop.getValue() == "" ? "0" :
                        otBrgRasioOnTop.getValue() == null ? "0" :
                        otBrgRasioOnTop.getValue().toString();
                    rasioT =
                            new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                           ""));
                    otBrgMf.setSubmittedValue(totalMF);
    
                    RasioMf =
                            totalMF.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                    BigDecimal rasMf = RasioMf;
                    otBrgRasioMf.setSubmittedValue(rasMf);
                    rasioTotal = RasioMf.add(rasioT);
                    String total = rasioTotal.toString();
                    otBrgRasioTotal.setSubmittedValue(total);
    
                    DCIteratorBinding dciterPromoProduk =
                        ADFUtils.findIterator("PromoProdukView1Iterator");
                    Row r = dciterPromoProduk.getCurrentRow();
    
                    r.setAttribute("BrgBonusMf", totalMF);
                    r.setAttribute("BrgBonusRasioMf", rasMf);
                    r.setAttribute("BrgBonusRasioTotal", total);
    
                    dciterPromoProduk.getDataControl().commitTransaction();
                    dciterPromoProduk.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgMf);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioMf);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioTotal);
                    btnOkpromoDetail.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                }
            } else {
                JSFUtils.addFacesWarningMessage("Kolom \"Price\" tidak boleh kosong atau diisi 0 (Nol)");
            }
        } else {
            if (PriceVal.compareTo(zeroNumber) < 0 || PriceVal.compareTo(zeroNumber) > 0) {
            BigDecimal newChangedMfValueCek =
                new BigDecimal(valueChangeEvent.getNewValue() == "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                               valueChangeEvent.getNewValue().toString());
                BigDecimal totalOntop =
                    newChangedMfValueCek.add(ontop.getBigDecimalValue());
                BigDecimal totalPriceQty =
                    PriceVal.getBigDecimalValue().multiply(QtyFrom.getBigDecimalValue());
                BigDecimal val =
                    totalOntop.divide(totalPriceQty, 2, RoundingMode.HALF_UP);
                if (val.compareTo(BigDecimal.ONE) == 1) {
                    showPopup("Persentase Lebih Dari 100 % dari Quantity dikali Price",
                              potmessage);
                    btnOkpromoDetail.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                } else {
                    DCIteratorBinding dciterTarget =
                        ADFUtils.findIterator("TargetView1Iterator");
                    Row row = dciterTarget.getCurrentRow();
                    Number value =
                        (Number)row.getAttribute("Value") == null ? new Number(0) :
                        (Number)row.getAttribute("Value");
    
                    DCIteratorBinding dcItteratorBrgBonusMF =
                        ADFUtils.findIterator("PromoBonusView1Iterator");
                    ViewObject voTableDataMF =
                        dcItteratorBrgBonusMF.getViewObject();
                    RowSetIterator iterMF =
                        voTableDataMF.createRowSetIterator(null);
                    BigDecimal valueOTsel = BigDecimal.ZERO;
                    BigDecimal totalMF = new BigDecimal(0);
                    while (iterMF.hasNext()) {
                        Row r = iterMF.next();
                        String PromoBonusIdBrg =
                            r.getAttribute("PromoBonusId").toString();
                        if (PromoBonusIdBrg.equalsIgnoreCase(PromoBonusIdSel)) {
                            BigDecimal newChangedMfValue =
                                new BigDecimal(valueChangeEvent.getNewValue() ==
                                               "" ? "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                               valueChangeEvent.getNewValue().toString());
                            valueOTsel = valueOTsel.add(newChangedMfValue);
                            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        }
                        Number MF =
                            (Number)r.getAttribute("DiscYearly") == null ? new Number(0) :
                            (Number)r.getAttribute("DiscYearly");
                        totalMF = totalMF.add(MF.getBigDecimalValue());
                    }
                    iterMF.closeRowSetIterator();
                    String rasio =
                        otBrgRasioOnTop.getValue() == "" ? "0" :
                        otBrgRasioOnTop.getValue() == null ? "0" :
                        otBrgRasioOnTop.getValue().toString();
                    rasioT =
                            new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                           ""));
                    otBrgMf.setSubmittedValue(totalMF);
    
                    RasioMf =
                            totalMF.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
                    BigDecimal rasMf = RasioMf;
                    otBrgRasioMf.setSubmittedValue(rasMf);
                    rasioTotal = RasioMf.add(rasioT);
                    String total = rasioTotal.toString();
                    otBrgRasioTotal.setSubmittedValue(total);
    
                    DCIteratorBinding dciterPromoProduk =
                        ADFUtils.findIterator("PromoProdukView1Iterator");
                    Row r = dciterPromoProduk.getCurrentRow();
    
                    r.setAttribute("BrgBonusMf", totalMF);
                    r.setAttribute("BrgBonusRasioMf", rasMf);
                    r.setAttribute("BrgBonusRasioTotal", total);
    
                    dciterPromoProduk.getDataControl().commitTransaction();
                    dciterPromoProduk.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgMf);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioMf);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otBrgRasioTotal);
                    btnOkpromoDetail.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(btnOkpromoDetail);
                }
            } else {
                JSFUtils.addFacesWarningMessage("Kolom \"Price\" tidak boleh kosong atau diisi 0 (Nol)");
            }
        }
    }

    public void setItPromoBonusOntop(RichInputText itPromoBonusOntop) {
        this.itPromoBonusOntop = itPromoBonusOntop;
    }

    public RichInputText getItPromoBonusOntop() {
        return itPromoBonusOntop;
    }

    public void setItPromoBonusMf(RichInputText itPromoBonusMf) {
        this.itPromoBonusMf = itPromoBonusMf;
    }

    public RichInputText getItPromoBonusMf() {
        return itPromoBonusMf;
    }

    public void setItPricePromoBonus(RichInputText itPricePromoBonus) {
        this.itPricePromoBonus = itPricePromoBonus;
    }

    public RichInputText getItPricePromoBonus() {
        return itPricePromoBonus;
    }

    public void setItQtyPromoboBonus(RichInputText itQtyPromoboBonus) {
        this.itQtyPromoboBonus = itQtyPromoboBonus;
    }

    public RichInputText getItQtyPromoboBonus() {
        return itQtyPromoboBonus;
    }

    public void setOtBrgOnTop(RichInputText otBrgOnTop) {
        this.otBrgOnTop = otBrgOnTop;
    }

    public RichInputText getOtBrgOnTop() {
        return otBrgOnTop;
    }

    public void setOtBrgMf(RichInputText otBrgMf) {
        this.otBrgMf = otBrgMf;
    }

    public RichInputText getOtBrgMf() {
        return otBrgMf;
    }

    public void setOtBrgRasioOnTop(RichInputText otBrgRasioOnTop) {
        this.otBrgRasioOnTop = otBrgRasioOnTop;
    }

    public RichInputText getOtBrgRasioOnTop() {
        return otBrgRasioOnTop;
    }

    public void setOtBrgRasioMf(RichInputText otBrgRasioMf) {
        this.otBrgRasioMf = otBrgRasioMf;
    }

    public RichInputText getOtBrgRasioMf() {
        return otBrgRasioMf;
    }

    public void setOtBrgRasioTotal(RichInputText otBrgRasioTotal) {
        this.otBrgRasioTotal = otBrgRasioTotal;
    }

    public RichInputText getOtBrgRasioTotal() {
        return otBrgRasioTotal;
    }

    public void setBtnOkpromoDetail(RichCommandButton btnOkpromoDetail) {
        this.btnOkpromoDetail = btnOkpromoDetail;
    }

    public RichCommandButton getBtnOkpromoDetail() {
        return btnOkpromoDetail;
    }

    public void setItProductItem(RichInputText itProductItem) {
        this.itProductItem = itProductItem;
    }

    public RichInputText getItProductItem() {
        return itProductItem;
    }

    public void removeProduk(ActionEvent actionEvent) {
        String PromoProdukIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("PromoProdukView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("PromoProdukId") != null) {
            PromoProdukIdSel =
                    rowSelected.getAttribute("PromoProdukId").toString();
        }
        DCIteratorBinding iterVar =
            ADFUtils.findIterator("ProdukVariantView1Iterator");
        for (Row r : iterVar.getAllRowsInRange()) {
            String idVar = r.getAttribute("PromoProdukId").toString();
            if (idVar.equalsIgnoreCase(PromoProdukIdSel)) {
                r.remove();
            }
        }
        iterVar.getDataControl().commitTransaction();

        DCIteratorBinding iterItem =
            ADFUtils.findIterator("ProdukItemView1Iterator");
        for (Row rItem : iterItem.getAllRowsInRange()) {
            String idItem = rItem.getAttribute("PromoProdukId").toString();
            if (idItem.equalsIgnoreCase(PromoProdukIdSel)) {
                rItem.remove();
            }
        }
        iterItem.getDataControl().commitTransaction();
        DCIteratorBinding iterPP =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        for (Row rPP : iterPP.getAllRowsInRange()) {
            String idPP = rPP.getAttribute("PromoProdukId").toString();
            if (idPP.equalsIgnoreCase(PromoProdukIdSel)) {
                rPP.remove();
            }
        }
        iterPP.getDataControl().commitTransaction();
    }

    public void vcePeriodePromo(ValueChangeEvent valueChangeEvent) throws ParseException {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();

        AttributeBinding proposalTypeAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalType");
        Integer propTypeIdx = (Integer)proposalTypeAttr.getInputValue();

        boolean backDateOk = false;
        java.util.Date date2 =
            new java.util.Date(valueChangeEvent.getNewValue().toString());
        java.util.Date today = new java.util.Date();

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, -1);
        Date yesterday = c.getTime();

        AttributeBinding custTypeAttr =
            (AttributeBinding)bindings.getControlBinding("CustRegFlag1");
        String custType = (String)custTypeAttr.getInputValue();

        ArrayList<String> custRegCodeList = new ArrayList<String>();
        
        // Calculate history date
        DateFormat dfMM = new SimpleDateFormat("MM");
        String currMon = dfMM.format(date2);
        Integer last3Mon = Integer.valueOf(currMon) - 3;
        Integer last1Mon = Integer.valueOf(currMon) - 1;
        DateFormat dfYY = new SimpleDateFormat("yyyy");
        String currYear = dfYY.format(date2);
        Integer thisYear = Integer.valueOf(currYear);

        DateConversion.Measure last3MonCal =
            new DateConversion.Measure().month(last3Mon).year(thisYear);
        String DateTo =
            thisYear.toString() + String.format("%02d", last1Mon) +
            getLastDay(thisYear.toString(), last1Mon.toString());
        String d1HisF = last3MonCal.min().toString();
        SimpleDateFormat formatterx = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatterui =
            new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date fr = formatterx.parse(d1HisF);
        java.util.Date to = formatterx.parse(DateTo);
        idHistFrom.setSubmittedValue(formatterui.format(fr).toString());
        idHistTo.setSubmittedValue(formatterui.format(to).toString());
        otHistFrom.setValue(formatterui.format(fr).toString());
        otHistTo.setValue(formatterui.format(to).toString());
        AdfFacesContext.getCurrentInstance().addPartialTarget(idHistFrom);
        AdfFacesContext.getCurrentInstance().addPartialTarget(idHistTo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(otHistFrom);
        AdfFacesContext.getCurrentInstance().addPartialTarget(otHistTo);

        if (date2.before(yesterday)) {
            if (custType.equalsIgnoreCase(propRegion)) {
                DCIteratorBinding dciterRegion =
                    ADFUtils.findIterator("PropRegionView1Iterator");
                for (Row r : dciterRegion.getAllRowsInRange()) {
                    String regCode = (String)r.getAttribute("RegionCode");
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propArea)) {
                DCIteratorBinding dciterArea =
                    ADFUtils.findIterator("PropRegionAreaView1Iterator");
                for (Row r : dciterArea.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propArea, (String)r.getAttribute("AreaCode"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propLocation)) {
                DCIteratorBinding dciterLoc =
                    ADFUtils.findIterator("PropRegionLocView1Iterator");
                for (Row r : dciterLoc.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propLocation, (String)r.getAttribute("LocationCode"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustGroup)) {
                DCIteratorBinding dciterCustGroup =
                    ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
                for (Row r : dciterCustGroup.getAllRowsInRange()) {
                    String regCode =
                        findCustRegCode(propCustGroup, (String)r.getAttribute("CustGroup"));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            } else if (custType.equalsIgnoreCase(propCustomer)) {
                DCIteratorBinding dciterCustomer =
                    ADFUtils.findIterator("PropRegionCustomerView1Iterator");
                for (Row r : dciterCustomer.getAllRowsInRange()) {
                    oracle.jbo.domain.Number custId =
                        (oracle.jbo.domain.Number)r.getAttribute("CustomerId");
                    String regCode =
                        findCustRegCode(propCustomer, String.valueOf(custId.bigIntegerValue().intValue()));
                    if (!custRegCodeList.contains(regCode)) {
                        custRegCodeList.add(regCode);
                    }
                }
            }

            if (custRegCodeList.contains(backDateBlockRegion) &&
                custRegCodeList.size() == 1) {
                backDateOk = true;
            } else {
                backDateOk = false;
            }

        } else {
            backDateOk = true;
        }

        if (backDateOk) {
            c.setTime(today);
            Integer valFoodDate = 0;
            if (propTypeIdx.compareTo(idxFood) == 0) {
                c.add(Calendar.DATE, 6);
                Date todayAdd7 = c.getTime();

                if (date2.after(yesterday) && date2.compareTo(todayAdd7) < 0) {
                    valFoodDate = 1;
                } else {
                    valFoodDate = 0;
                    if(idPeriodeTo.getValue()!=null){
                    java.util.Date date1 =
                        new Date(idPeriodeTo.getValue().toString());
                    java.util.Date date21 =
                        new Date(valueChangeEvent.getNewValue().toString());
                    long days = ADFUtils.daysBetween(date1, date21);
                    Number days1 = new Number(days + 1);
                    itDaysCount.setValue(days1);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itDaysCount);
                    }
                }
            } else if (propTypeIdx.compareTo(idxNonFood) == 0) {
                c.add(Calendar.DATE, 9);
                Date todayAdd10 = c.getTime();

                if (date2.after(yesterday) &&
                    date2.compareTo(todayAdd10) < 0) {
                    valFoodDate = 2;
                } else {
                    valFoodDate = 0;
                    if(idPeriodeTo.getValue()!=null){
                        java.util.Date date1 =
                            new Date(idPeriodeTo.getValue().toString());
                        java.util.Date date22 =
                            new Date(valueChangeEvent.getNewValue().toString());
                        long days = ADFUtils.daysBetween(date1, date22);
                        Number days1 = new Number(days + 1);
                        itDaysCount.setValue(days1);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itDaysCount);
                    }
                }
            }

            if (valFoodDate.compareTo(0) == 0) {
                // CONTINUE
            } else if (valFoodDate.compareTo(1) == 0) {
                idPeriodProgFrom.setSubmittedValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                JSFUtils.addFacesWarningMessage("Pengajuan proposal periode promo FOOD minimal harus untuk H+7");
            } else if (valFoodDate.compareTo(2) == 0) {
                idPeriodProgFrom.setSubmittedValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
                JSFUtils.addFacesWarningMessage("Pengajuan proposal periode promo NON FOOD minimal harus untuk H+10");
            }
        } else {
            idPeriodProgFrom.setSubmittedValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
            JSFUtils.addFacesWarningMessage("Pengajuan proposal ini tidak diperkenankan untuk \"Back Date\"");
        }
    }

    public static String getLastDay(String year, String month) {
        // get a calendar object
        GregorianCalendar calendar = new GregorianCalendar();
        // convert the year and month to integers
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        // adjust the month for a zero based index
        monthInt = monthInt - 1;
        // set the date of the calendar to the date provided
        calendar.set(yearInt, monthInt, 1);
        int dayInt = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return Integer.toString(dayInt);
    } // end getLastDay method

    public void setIdHistFrom(RichInputDate idHistFrom) {
        this.idHistFrom = idHistFrom;
    }

    public RichInputDate getIdHistFrom() {
        return idHistFrom;
    }

    public void setIdHistTo(RichInputDate idHistTo) {
        this.idHistTo = idHistTo;
    }

    public RichInputDate getIdHistTo() {
        return idHistTo;
    }

    public void idPeriodProgToCount(ValueChangeEvent valueChangeEvent) {
        if(idPeriodProgFrom.getValue()!= null){
        java.util.Date date1 =
            new Date(idPeriodProgFrom.getValue().toString());
        java.util.Date date2 =
            new Date(valueChangeEvent.getNewValue().toString());
        long days = ADFUtils.daysBetween(date1, date2);
        Number days1 = new Number(days + 1);
        itDaysCount.setValue(days1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itDaysCount);
        }
    }

    public void setIdPeriodProgFrom(RichInputDate idPeriodProgFrom) {
        this.idPeriodProgFrom = idPeriodProgFrom;
    }

    public RichInputDate getIdPeriodProgFrom() {
        return idPeriodProgFrom;
    }

    public void setIdPeriodeTo(RichInputDate idPeriodeTo) {
        this.idPeriodeTo = idPeriodeTo;
    }

    public RichInputDate getIdPeriodeTo() {
        return idPeriodeTo;
    }

    public void setOtBiaOntop(RichInputText otBiaOntop) {
        this.otBiaOntop = otBiaOntop;
    }

    public RichInputText getOtBiaOntop() {
        return otBiaOntop;
    }

    public void setOtBiaMf(RichInputText otBiaMf) {
        this.otBiaMf = otBiaMf;
    }

    public RichInputText getOtBiaMf() {
        return otBiaMf;
    }

    public void setOtBiaRasioOntop(RichInputText otBiaRasioOntop) {
        this.otBiaRasioOntop = otBiaRasioOntop;
    }

    public RichInputText getOtBiaRasioOntop() {
        return otBiaRasioOntop;
    }

    public void setOtBiaRasioMf(RichInputText otBiaRasioMf) {
        this.otBiaRasioMf = otBiaRasioMf;
    }

    public RichInputText getOtBiaRasioMf() {
        return otBiaRasioMf;
    }

    public void setOtBiaRasioTotal(RichInputText otBiaRasioTotal) {
        this.otBiaRasioTotal = otBiaRasioTotal;
    }

    public RichInputText getOtBiaRasioTotal() {
        return otBiaRasioTotal;
    }

    public void setRowBiaOntop(RichInputText rowBiaOntop) {
        this.rowBiaOntop = rowBiaOntop;
    }

    public RichInputText getRowBiaOntop() {
        return rowBiaOntop;
    }

    public void setRowBiaMf(RichInputText rowBiaMf) {
        this.rowBiaMf = rowBiaMf;
    }

    public RichInputText getRowBiaMf() {
        return rowBiaMf;
    }

    public void addNewRowBiaya(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("BiayaView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
        newRow.validate();
    }

    public void removeBiayaRow(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BiayaView1Iterator");
        if (dcItteratorBindings.getEstimatedRowCount() == 1) {
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BiayaId") != null) {
                voTableData.removeCurrentRow();
                OperationBinding operation =
                    (OperationBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("Commit");
                operation.execute();
                DCIteratorBinding dciterPromoProduk =
                    ADFUtils.findIterator("PromoProdukView1Iterator");
                Row r = dciterPromoProduk.getCurrentRow();
                r.setAttribute("BiaOntop", 0);
                r.setAttribute("BiaRasionOntop", 0);
                r.setAttribute("BiaMf", 0);
                r.setAttribute("BiaRasioMf", 0);
                r.setAttribute("BiaRasioTotal", 0);
                dciterPromoProduk.getDataControl().commitTransaction();
                otBiaOntop.setSubmittedValue(0);
                otBiaRasioOntop.setSubmittedValue(0);
                otBiaRasioTotal.setSubmittedValue(0);
                otBiaMf.setSubmittedValue(0);
                otRasioMf.setSubmittedValue(0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaOntop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioOntop);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioTotal);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaMf);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otRasioMf);
            }
        } else {
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BiayaId") != null) {
                voTableData.removeCurrentRow();
                OperationBinding operation =
                    (OperationBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("Commit");
                operation.execute();
            }
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            Row r = dciterPromoProduk.getCurrentRow();
            DCIteratorBinding dciterDiscount =
                ADFUtils.findIterator("BiayaView1Iterator");
            BigDecimal totalMf = BigDecimal.ZERO;
            BigDecimal totalOntop = BigDecimal.ZERO;
            for (Row er : dciterDiscount.getAllRowsInRange()) {
                String valueMF =
                    er.getAttribute("BiayaYearly").toString().replaceAll(",",
                                                                         "");
                String valueTop =
                    er.getAttribute("BiayaNonYearly").toString().replaceAll(",",
                                                                            "");
                BigDecimal ontopValue =
                    new BigDecimal(valueTop) == null ? new BigDecimal(0) :
                    new BigDecimal(valueTop);
                BigDecimal mfValue =
                    new BigDecimal(valueMF) == null ? new BigDecimal(0) :
                    new BigDecimal(valueMF);
                totalMf = totalMf.add(mfValue);
                totalOntop = totalOntop.add(ontopValue);
            }
            DCIteratorBinding dciterTarget =
                ADFUtils.findIterator("TargetView1Iterator");
            Row row = dciterTarget.getCurrentRow();
            BigDecimal mf = BigDecimal.ZERO;
            BigDecimal rMf = BigDecimal.ZERO;
            BigDecimal RasioMf = BigDecimal.ZERO;
            BigDecimal rasioTotal = BigDecimal.ZERO;
            BigDecimal rasioT = BigDecimal.ZERO;
            BigDecimal ontop = BigDecimal.ZERO;
            BigDecimal rOntop = BigDecimal.ZERO;
            BigDecimal RasioOntop = BigDecimal.ZERO;
            Number qty =
                (Number)row.getAttribute("Qty") == null ? new Number(0) :
                (Number)row.getAttribute("Qty");
            Number value =
                (Number)row.getAttribute("Value") == null ? new Number(0) :
                (Number)row.getAttribute("Value");
            rMf = totalMf;
            mf = (value.multiply(rMf)).getBigDecimalValue().divide(bdHundred);
            otBiaMf.setSubmittedValue(rMf);
            RasioMf =
                    mf.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasMf = RasioMf.toString();
            otBiaRasioMf.setSubmittedValue(rasMf);
            rasioT =
                    new BigDecimal(otBiaRasioOntop.getValue().toString().replaceAll(" ",
                                                                                    "").replaceAll("%",
                                                                                                   ""));
            rasioTotal = RasioMf.add(rasioT);
            String Total = rasioTotal.toString();
            otBiaRasioTotal.setSubmittedValue(Total);

            rOntop = totalOntop;
            String rasio =
                otBiaRasioMf.getValue() == "" ? "0" : otBiaRasioMf.getValue() == null ? "0" : otBiaRasioMf.getValue().toString();
            rasioT =
                    new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                   ""));
            ontop =
                    rOntop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);

            otBiaOntop.setSubmittedValue(totalOntop);
            RasioOntop =
                    ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasOntop = RasioOntop.toString();
            otBiaRasioOntop.setSubmittedValue(rasOntop);

            r.setAttribute("BiaOntop", totalOntop);
            r.setAttribute("BiaRasionOntop", ontop);
            r.setAttribute("BiaRasioTotal", Total);
            r.setAttribute("BiaMf", rMf);
            r.setAttribute("BiaRasioMf", rasMf);

            dciterPromoProduk.getDataControl().commitTransaction();

            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaOntop);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioOntop);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioTotal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaMf);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioMf);
        }
    }

    public void refreshBiayaOntop(ValueChangeEvent valueChangeEvent) {
        String BiayaIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("BiayaView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("BiayaId") != null) {
            BiayaIdSel = rowSelected.getAttribute("BiayaId").toString();
        }

        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row row = dciterTarget.getCurrentRow();
        BigDecimal RasioOntop = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;
        Number value =
            (Number)row.getAttribute("Value") == null ? new Number(0) :
            (Number)row.getAttribute("Value");
        if (value.compareTo(zeroNumber) > 0 || value.compareTo(zeroNumber) < 0) {
            DCIteratorBinding BiaIterator =
                ADFUtils.findIterator("BiayaView1Iterator");
            ViewObject voTableDataOT = BiaIterator.getViewObject();
            RowSetIterator BiaIter = voTableDataOT.createRowSetIterator(null);
            BigDecimal valueOTsel = BigDecimal.ZERO;
            BigDecimal totalOT = BigDecimal.ZERO;
            while (BiaIter.hasNext()) {
                Row r = BiaIter.next();
                String id = r.getAttribute("BiayaId").toString();
                if (id.equalsIgnoreCase(BiayaIdSel)) {
                    BigDecimal newChangedMfValue =
                        new BigDecimal(valueChangeEvent.getNewValue() == "" ?
                                       "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                       valueChangeEvent.getNewValue().toString());
                    valueOTsel = valueOTsel.add(newChangedMfValue);
                    valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                }
                Number MF =
                    (Number)r.getAttribute("BiayaNonYearly") == null ? new Number(0) :
                    (Number)r.getAttribute("BiayaNonYearly");
                totalOT = totalOT.add(MF.getBigDecimalValue());
            }
            BiaIter.closeRowSetIterator();
            String rasio =
                otBiaRasioMf.getValue() == "" ? "0" : otBiaRasioMf.getValue() == null ? "0" : otBiaRasioMf.getValue().toString();
            rasioT =
                    new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                   ""));
            otBiaOntop.setSubmittedValue(totalOT);
            RasioOntop =
                    totalOT.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            BigDecimal rasOntop = RasioOntop;

            otBiaRasioOntop.setSubmittedValue(rasOntop);
            rasioTotal = RasioOntop.add(rasioT);
            String total = rasioTotal.toString();
            otBiaRasioTotal.setSubmittedValue(total);
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            Row r = dciterPromoProduk.getCurrentRow();
            r.setAttribute("BiaOntop", totalOT);
            r.setAttribute("BiaRasionOntop", rasOntop);
            r.setAttribute("BiaRasioTotal", total);
            dciterPromoProduk.getDataControl().commitTransaction();

            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaOntop);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioOntop);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioTotal);
        }
    }

    public void refreshBiayaMf(ValueChangeEvent valueChangeEvent) {
        String BiayaIdSel = "";
        DCBindingContainer bindingsSelRow =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindingsSelRow.findIteratorBinding("BiayaView1Iterator");
        ViewObject voTableDatasel = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableDatasel.getCurrentRow();
        if (rowSelected.getAttribute("BiayaId") != null) {
            BiayaIdSel = rowSelected.getAttribute("BiayaId").toString();
        }
        DCIteratorBinding dciterTarget =
            ADFUtils.findIterator("TargetView1Iterator");
        Row row = dciterTarget.getCurrentRow();
        BigDecimal RasioOntop = BigDecimal.ZERO;
        BigDecimal rasioTotal = BigDecimal.ZERO;
        BigDecimal rasioT = BigDecimal.ZERO;
        Number value =
            (Number)row.getAttribute("Value") == null ? new Number(0) :
            (Number)row.getAttribute("Value");
        if (value.getBigDecimalValue() != BigDecimal.ZERO) {
            DCIteratorBinding BiaIterator =
                ADFUtils.findIterator("BiayaView1Iterator");
            ViewObject voTableDataOT = BiaIterator.getViewObject();
            RowSetIterator BiaIter = voTableDataOT.createRowSetIterator(null);
            BigDecimal valueOTsel = BigDecimal.ZERO;
            BigDecimal totalOT = BigDecimal.ZERO;
            while (BiaIter.hasNext()) {
                Row r = BiaIter.next();
                String id = r.getAttribute("BiayaId").toString();
                if (id.equalsIgnoreCase(BiayaIdSel)) {
                    BigDecimal newChangedMfValue =
                        new BigDecimal(valueChangeEvent.getNewValue() == "" ?
                                       "0" : valueChangeEvent.getNewValue() == null ? "0" :
                                       valueChangeEvent.getNewValue().toString());
                    valueOTsel = valueOTsel.add(newChangedMfValue);
                    valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                }
                Number MF =
                    (Number)r.getAttribute("BiayaYearly") == null ? new Number(0) :
                    (Number)r.getAttribute("BiayaYearly");
                totalOT = totalOT.add(MF.getBigDecimalValue());
            }
            BiaIter.closeRowSetIterator();
            String rasio =
                otBiaRasioOntop.getValue() == "" ? "0" : otBiaRasioOntop.getValue() == null ? "0" : otBiaRasioOntop.getValue().toString();
            rasioT =
                    new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                   ""));
            otBiaMf.setSubmittedValue(totalOT);
            RasioOntop =
                    totalOT.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            BigDecimal rasOntop = RasioOntop;

            otBiaRasioMf.setSubmittedValue(rasOntop);
            rasioTotal = RasioOntop.add(rasioT);
            String total = rasioTotal.toString();
            otBiaRasioTotal.setSubmittedValue(total);
            DCIteratorBinding dciterPromoProduk =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            Row r = dciterPromoProduk.getCurrentRow();
            r.setAttribute("BiaMf", totalOT);
            r.setAttribute("BiaRasioMf", rasOntop);
            r.setAttribute("BiaRasioTotal", total);
            dciterPromoProduk.getDataControl().commitTransaction();
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaMf);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioMf);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otBiaRasioTotal);
        }
    }

    public void resetProposal(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();

        AttributeBinding propNoAttr =
            (AttributeBinding)bindings.getControlBinding("ProposalNo");
        String proposalNo =
            (String)propNoAttr.getInputValue() == null ? "Auto Generated" :
            (String)propNoAttr.getInputValue();

        DCIteratorBinding parentIter =
            (DCIteratorBinding)bindings.get("ProposalView1Iterator");
        //Get current row key
        Key parentKey = parentIter.getCurrentRow().getKey();

        OperationBinding operationBindingRollback =
            bindings.getOperationBinding("Rollback");
        operationBindingRollback.execute();

        if (!proposalNo.equalsIgnoreCase("Auto Generated")) {
            //Set again row key as current row
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
    }

    public String findCustRegCode(String custType, String custValue) {
        String regCodeResult = "";
        PromoProposalAMImpl promoProposalAM =
            (PromoProposalAMImpl)ADFUtils.getApplicationModuleForDataControl("PromoProposalAMDataControl");
        FindRegionCodeImpl regionCodeView =
            promoProposalAM.getFindRegionCode1();
        if (custType.equalsIgnoreCase(propArea)) {
            regionCodeView.setWhereClause("ArCustomers.ATTRIBUTE4 = '" +
                                          custValue + "'");
        } else if (custType.equalsIgnoreCase(propLocation)) {
            regionCodeView.setWhereClause("ArCustomers.ATTRIBUTE5 = '" +
                                          custValue + "'");
        } else if (custType.equalsIgnoreCase(propCustGroup)) {
            regionCodeView.setWhereClause("ArCustomers.ATTRIBUTE1 = '" +
                                          custValue + "'");
        } else if (custType.equalsIgnoreCase(propCustomer)) {
            regionCodeView.setWhereClause("ArCustomers.CUSTOMER_ID = " +
                                          custValue + "");
        }

        regionCodeView.executeQuery();

        if (regionCodeView.getEstimatedRowCount() > 0) {
            FindRegionCodeRowImpl regCodeRow =
                (FindRegionCodeRowImpl)regionCodeView.first();
            regCodeResult = regCodeRow.getRegionCode();
        }

        return regCodeResult;
    }

    public void setNewPromoPeriodFrom(RichInputDate newPromoPeriodFrom) {
        this.newPromoPeriodFrom = newPromoPeriodFrom;
    }

    public RichInputDate getNewPromoPeriodFrom() {
        return newPromoPeriodFrom;
    }

    public void setNewPromoPeriodTo(RichInputDate newPromoPeriodTo) {
        this.newPromoPeriodTo = newPromoPeriodTo;
    }

    public RichInputDate getNewPromoPeriodTo() {
        return newPromoPeriodTo;
    }

    public void setTextCopyProposal(String textCopyProposal) {
        this.textCopyProposal = textCopyProposal;
    }

    public String getTextCopyProposal() {
        newPromoPeriodFrom.setValue(null);
        newPromoPeriodTo.setValue(null);
        textCopyProposal = "Anda akan melakukan copy proposal no \"";
        return textCopyProposal;
    }

    public void setCopyPropMessage(RichMessages copyPropMessage) {
        this.copyPropMessage = copyPropMessage;
    }

    public RichMessages getCopyPropMessage() {
        return copyPropMessage;
    }

    public void newPromoFromChange(ValueChangeEvent valueChangeEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        DefaultDateFormatter ddf = new DefaultDateFormatter();
        String dateValueStart = "", dateValueEnd = "", dateTimeValueStart =
            "", dateTimeValueEnd = "";

        try {
            dateValueStart =
                    ddf.format("yyyy-MM-dd", newPromoPeriodFrom.getValue());
            dateTimeValueStart = dateValueStart + " 00:00:00";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueStart = "";
        }

        try {
            dateValueEnd =
                    ddf.format("yyyy-MM-dd", newPromoPeriodTo.getValue());
            dateTimeValueEnd = dateValueEnd + " 23:59:59";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueEnd = "";
        }

        if (dateValueStart.length() > 0 && dateValueEnd.length() > 0) {
            boolean dateValid = false;
            try {
                dateValid =
                        ADFUtils.compareDates(dateTimeValueStart, dateTimeValueEnd);
            } catch (ParseException e) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Failed to parse date value.",
                                     "Failed to parse date value.");
                fctx.addMessage(null, msg);
            }

            if (dateValid) {
            } else {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Periode akhir promo tidak boleh lebih awal dari periode mulai.",
                                     "Periode akhir promo tidak boleh lebih awal dari periode mulai.");
                fctx.addMessage(null, msg);
            }
        }
    }

    public void newPromoToChange(ValueChangeEvent valueChangeEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        DefaultDateFormatter ddf = new DefaultDateFormatter();
        String dateValueStart = "", dateValueEnd = "", dateTimeValueStart =
            "", dateTimeValueEnd = "";

        try {
            dateValueStart =
                    ddf.format("yyyy-MM-dd", newPromoPeriodFrom.getValue());
            dateTimeValueStart = dateValueStart + " 00:00:00";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueStart = "";
        }

        try {
            dateValueEnd =
                    ddf.format("yyyy-MM-dd", newPromoPeriodTo.getValue());
            dateTimeValueEnd = dateValueEnd + " 23:59:59";
        } catch (FormatErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dateValueEnd = "";
        }

        if (dateValueStart.length() > 0 && dateValueEnd.length() > 0) {
            boolean dateValid = false;
            try {
                dateValid =
                        ADFUtils.compareDates(dateTimeValueStart, dateTimeValueEnd);
            } catch (ParseException e) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Failed to parse date value.",
                                     "Failed to parse date value.");
                fctx.addMessage(null, msg);
            }

            if (dateValid) {
            } else {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Periode akhir promo tidak boleh lebih awal dari periode mulai.",
                                     "Periode akhir promo tidak boleh lebih awal dari periode mulai.");
                fctx.addMessage(null, msg);
            }
        }
    }

    public void setItDaysCount(RichInputText itDaysCount) {
        this.itDaysCount = itDaysCount;
    }

    public RichInputText getItDaysCount() {
        return itDaysCount;
    }

    public RichOutputText getOtdaysview() {
        return otdaysview;
    }

    public void setOtdaysview(RichOutputText otdaysview) {
        this.otdaysview = otdaysview;
    }

    public void setItProgDays(RichInputText itProgDays) {
        this.itProgDays = itProgDays;
    }

    public RichInputText getItProgDays() {
        return itProgDays;
    }

    public void discTypeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (Integer.valueOf(valueChangeEvent.getNewValue().toString()) == 2) {
            socMixQtyPromo.setRequired(true);
            socMixQtyPromo.setValue(0);
        } else {
            socMixQtyPromo.setRequired(false);
            socMixQtyPromo.setValue(0);
        }
    }

    public void setSocMixQtyPromo(RichSelectOneChoice socMixQtyPromo) {
        this.socMixQtyPromo = socMixQtyPromo;
    }

    public RichSelectOneChoice getSocMixQtyPromo() {
        return socMixQtyPromo;
    }

    public void setSwitchMain(UIXSwitcher switchMain) {
        this.switchMain = switchMain;
    }

    public UIXSwitcher getSwitchMain() {
        return switchMain;
    }

    public void setItlovUomTarget(RichInputListOfValues itlovUomTarget) {
        this.itlovUomTarget = itlovUomTarget;
    }

    public RichInputListOfValues getItlovUomTarget() {
        return itlovUomTarget;
    }

    public void setBtnPriceListpromoBrng(RichCommandButton btnPriceListpromoBrng) {
        this.btnPriceListpromoBrng = btnPriceListpromoBrng;
    }

    public RichCommandButton getBtnPriceListpromoBrng() {
        return btnPriceListpromoBrng;
    }

    public void setPpricelistTarget(RichPopup ppricelistTarget) {
        this.ppricelistTarget = ppricelistTarget;
    }

    public RichPopup getPpricelistTarget() {
        return ppricelistTarget;
    }

    public void showPriceListTarget(ActionEvent actionEvent) {
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
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                ppricelistTarget.show(hints);
            } else {
                 showPopup("Item Harus Diisi", potmessage);
            }
        }
    }

    public void setOtHistFrom(RichOutputText otHistFrom) {
        this.otHistFrom = otHistFrom;
    }

    public RichOutputText getOtHistFrom() {
        return otHistFrom;
    }

    public void setOtHistTo(RichOutputText otHistTo) {
        this.otHistTo = otHistTo;
    }

    public RichOutputText getOtHistTo() {
        return otHistTo;
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
        ADFUtils.invokeEL("#{bindings.ProposalView1Query.processQuery}",
                          new Class[] { QueryEvent.class },
                          new Object[] { queryEvent });
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
    }
}
