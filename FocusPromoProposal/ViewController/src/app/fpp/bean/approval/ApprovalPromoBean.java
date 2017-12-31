package app.fpp.bean.approval;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.bean.useraccessmenu.UserData;
import app.fpp.model.am.ApprovalAMImpl;
import app.fpp.model.am.PromoProposalAMImpl;
import app.fpp.model.views.approval.ApprovalReceiverApproveProposalViewImpl;
import app.fpp.model.views.approval.DocApprovalViewImpl;
import app.fpp.model.views.masterdata.ebs.FcsViewCategCombinationViewImpl;
import app.fpp.model.views.promoproposal.FindRegionCodeImpl;
import app.fpp.model.views.promoproposal.FindRegionCodeRowImpl;
import app.fpp.model.views.promoproposal.PromoProdukViewImpl;
import app.fpp.model.views.promoproposal.validation.ProdVariantValidationViewImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
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

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;

public class ApprovalPromoBean {
    private RichTable tblApproval;
    private RichTable tblDocHistory;
    private final Integer APPROVED = 0;
    private final Integer REJECTED = 1;
    private final Integer FORWARD = 2;
    private final String docStatusInprocess = "INPROCESS";
    private final String docStatusActive = "ACTIVE";
    private final String docStatusReject = "REJECTED";
    private RichTable tblListProduct;
    private RichInputText itTargetPercentage;
    private RichInputText itTargetValue;
    private RichInputText itTargetQty;
    private RichInputText itTargetHarga;
    private RichInputText itValueTotal;
    private RichTable tblListProductAddBuy;
    private RichInputListOfValues itlovProdClassAddBuy;
    private RichInputListOfValues itlovProdBrandAddBuy;
    private RichInputListOfValues itlovProdExtAddBuy;
    private RichInputListOfValues itlovProdPackAddBuy;
    private RichTable tblListProductBonus;
    private RichInputListOfValues itlovProdClassBonus;
    private RichInputListOfValues itlovProdBrandBonus;
    private RichInputListOfValues itlovProdExtBonus;
    private RichInputListOfValues itlovProdPackBonus;
    private RichInputListOfValues itlovProdClass;
    private RichInputListOfValues itlovProdBrand;
    private RichInputListOfValues itlovProdExtention;
    private RichInputListOfValues itlovProdPackaging;
    private UIXSwitcher switchRegCust;
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
    private static String onInvoice = "ONINVOICE";
    private static String userHo = "HO";
    private static String userArea = "AREA";
    private RichTable tblListProductRegion;
    private RichTable tblListProductCustomer;
    private RichTable tblListProductArea;
    private RichSelectOneRadio rbApprovalAction;
    private String checkCanApprove;
    private RichInputText itReasonApproval;
    private RichTable tblListProductLocation;
    private RichTable tblListRegion;
    private RichTable tblListArea;
    private RichTable tblListCustomer;
    private RichTable tblListLocation;
    private RichPopup potmessage;
    private RichOutputText otpesan;
    private RichPopup pdetailProduct;
    private RichShowDetailItem tabTargerCustomer;
    private RichShowDetailItem tabBiaya;
    private RichShowDetailItem tabPromoBarang;
    private RichShowDetailItem tabPotongan;
    private RichShowDetailItem tabTargetAndBudget;
    private RichPopup papprove;
    private RichTable tblListCustGroup;
    private RichTable tblListProductCustGroup;
    private RichInputListOfValues itlovExclCustBy;
    private DecimalFormat df = new DecimalFormat("###");
    private DecimalFormat df2dgt = new DecimalFormat("###.##");
    private RichTable tblListExclRegion;
    private RichTable tblListExclArea;
    private RichTable tblListExclCustomer;
    private RichTable tblListExclLocation;
    private RichTable tblListExclProductRegion;
    private RichTable tblListExclProductArea;
    private RichTable tblListExclProductLoc;
    private RichTable tblListExclProductCust;
    private UIXSwitcher switchExclCust;
    private RichInputText itBonusVariant;
    private RichInputText itVariant;
    private RichTable tableListPotongan;
    private RichInputListOfValues socTypePotongan;
    BigDecimal valueMf = BigDecimal.ZERO;
    private BigDecimal bdHundred = new BigDecimal("100");
    private RichInputText otOnTop;
    private RichInputText otMF;
    private RichInputText rowOntop;
    private RichInputText rowMf;
    private RichInputText otRasioOntop;
    private RichInputText otRasioMf;
    private RichInputText otRasioTotal;
    private static String userCustArea = "AREA";
    private static String userCustCustomer = "CUSTOMER";
    private static String userCustRegion = "REGION";
    private static String userCustLocation = "LOCATION";
    private static String userCustCustGroup = "CUSTGROUP";
    private static String userCustInvalid = "INVALID";
    private RichInputText itPromoBonusOntop;
    private RichInputText itPromoBonusMf;
    private RichInputText otBrgOnTop;
    private RichInputText otBrgMf;
    private RichInputText otBrgRasioOnTop;
    private RichInputText otBrgRasioMf;
    private RichInputText otBrgRasioTotal;
    private RichOutputText itCategory;
    private RichInputListOfValues itLovProdCategory;
    private String prodCatCodeFood = "CT001";
    private String prodCatCodeNonFood = "CT002";
    private String prodDescCodeFood = "FOOD";
    private String prodDescCodeNonFood = "NON FOOD";
    private RichInputText itProductItem;
    private RichCommandImageLink linkProduct;
    private RichCommandImageLink linkVariant;
    private static String backDateBlockRegion = "R1-0";
    private RichInputText otBiaOntop;
    private RichInputText otBiaMf;
    private RichInputText otBiaRasioOntop;
    private RichInputText otBiaRasioMf;
    private RichInputText otBiaRasioTotal;
    private RichInputText rowBiaOntop;
    private RichInputText rowBiaMf;
    private RichInputDate idPeriodeTo;
    private RichInputDate idPeriodProgFrom;
    private RichOutputText otCountDays;
    private RichInputDate idHistFrom;
    private RichInputDate idHistTo;
    private RichInputText itCountDays;
    private UIXSwitcher switchMain;
    private RichSelectOneChoice socForwardTo;
    private RichInputText itUserForward;
    private RichOutputText otHistFrom;
    private RichOutputText otHistTo;
    private Number zeroNumber = new Number(0);
    private Number maxNumber = new Number(999999);
    private RichSelectOneChoice propTypeVal;

    public ApprovalPromoBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void proposalApproveRejectDialog(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();

        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String usrName =
            userData.getUserNameLogin() == null ? "" : userData.getUserNameLogin();

        AttributeBinding aprvlActionAttr =
            (AttributeBinding)bindings.getControlBinding("Action");
        Integer aprvlAction = (Integer)aprvlActionAttr.getInputValue();

        AttributeBinding aprvlCodeAttr =
            (AttributeBinding)bindings.getControlBinding("AprvlCode");
        String aprvlCode = (String)aprvlCodeAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            try {
                if (aprvlAction.equals(APPROVED)) {
                    DCIteratorBinding dciter =
                        ADFUtils.findIterator("ApprovalReceiverApproveProposalView1Iterator");
                    ApprovalReceiverApproveProposalViewImpl apvrlReceiverVO =
                        (ApprovalReceiverApproveProposalViewImpl)dciter.getViewObject();
                    apvrlReceiverVO.setNamedWhereClauseParam("aprvlCode",
                                                             aprvlCode);
                    apvrlReceiverVO.setNamedWhereClauseParam("usrName",
                                                             usrName);
                    apvrlReceiverVO.executeQuery();

                    if (apvrlReceiverVO.getEstimatedRowCount() > 0) {
                        AttributeBinding propStatus =
                            (AttributeBinding)bindings.getControlBinding("Status1");
                        propStatus.setInputValue(docStatusInprocess);
                    } else {
                        AttributeBinding propStatus =
                            (AttributeBinding)bindings.getControlBinding("Status1");
                        propStatus.setInputValue(docStatusActive);
                    }

                    OperationBinding operationApproveApproval =
                        bindings.getOperationBinding("approveDocApproval");
                    operationApproveApproval.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
                } else if (aprvlAction.equals(REJECTED)) {

                    AttributeBinding rejectReason =
                        (AttributeBinding)bindings.getControlBinding("Reason");
                    String reasonStr =
                        rejectReason.getInputValue() == null ? "" :
                        (String)rejectReason.getInputValue();

                    if (!reasonStr.trim().equalsIgnoreCase("")) {
                        AttributeBinding forwardTo = 
                            (AttributeBinding)bindings.getControlBinding("ForwardTo1");
                        String isForward = forwardTo.getInputValue() == null ? "N" : "Y"; 
                        if (isForward.equalsIgnoreCase("Y")) {
                            AttributeBinding propStatus =
                                (AttributeBinding)bindings.getControlBinding("Status1");
                            propStatus.setInputValue(docStatusReject);
    
                            OperationBinding operationApproveApproval =
                                bindings.getOperationBinding("rejectFwdDocApproval");
                            operationApproveApproval.execute();
                        } else {
                            AttributeBinding propStatus =
                                (AttributeBinding)bindings.getControlBinding("Status1");
                            propStatus.setInputValue(docStatusReject);
    
                            OperationBinding operationApproveApproval =
                                bindings.getOperationBinding("rejectDocApproval");
                            operationApproveApproval.execute();
                        }
                    } else {
                        JSFUtils.addFacesWarningMessage("Reason harus diisi apabila melakukan reject approval.");
                    }
                } else if (aprvlAction.equals(FORWARD)) {
                    AttributeBinding propStatus =
                        (AttributeBinding)bindings.getControlBinding("Status1");
                    propStatus.setInputValue(docStatusInprocess);

                    OperationBinding operationApproveApproval =
                        bindings.getOperationBinding("forwardDocApproval");
                    operationApproveApproval.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
                } else {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  "Action approval tidak dikenal.");
                }

                OperationBinding operationRefresh =
                    bindings.getOperationBinding("Execute");
                operationRefresh.execute();

                OperationBinding operationRefreshDoc =
                    bindings.getOperationBinding("Execute1");
                operationRefreshDoc.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
            } catch (JboException e) {
                JSFUtils.addFacesErrorMessage("Error", e.getBaseMessage());
            }
        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblApproval);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblDocHistory);
        }

    }

    public void setTblApproval(RichTable tblApproval) {
        this.tblApproval = tblApproval;
    }

    public RichTable getTblApproval() {
        return tblApproval;
    }

    public void setTblDocHistory(RichTable tblDocHistory) {
        this.tblDocHistory = tblDocHistory;
    }

    public RichTable getTblDocHistory() {
        return tblDocHistory;
    }

    public void removeProducts(ActionEvent actionEvent) {
        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        ViewObject vo1 = dciter.getViewObject();
        RowSetIterator rsProductIter = vo1.createRowSetIterator(null);
        Row currRow = null;
        while (rsProductIter.hasNext()) {
            currRow = rsProductIter.next();
            if (true == currRow.getAttribute("SelectedRow")) {
                currRow.remove();
            }
        }
        rsProductIter.closeRowSetIterator();
        BindingContainer bindings = getBindings();
        OperationBinding operationCommit =
            bindings.getOperationBinding("Commit");
        operationCommit.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

    public void setTblListProduct(RichTable tblListProduct) {
        this.tblListProduct = tblListProduct;
    }

    public RichTable getTblListProduct() {
        return tblListProduct;
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

    public void detailProdDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();
        } else {
            // Just do nothing !!!, kalau di rollback suka banyak yang ke reset jadi null soc nya.
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
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
    
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding iter =
                (DCIteratorBinding)binding.get("ProposalApprovalView1Iterator");
            Row r = iter.getCurrentRow();
            String FoodCode = r.getAttribute("ProposalType").toString();
            
            if (prodDescCodeFood.equalsIgnoreCase(FoodCode)) {
                itLovProdCategory.setValue(prodCatCodeFood);
                itCategory.setValue(prodDescCodeFood);
                AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
                adc.addPartialTarget(itLovProdCategory);
                adc.addPartialTarget(itCategory);
                adc.addPartialTarget(tblListProduct);
            } else {
                itLovProdCategory.setValue(prodCatCodeNonFood);
                itCategory.setValue(prodDescCodeNonFood);
                AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
                adc.addPartialTarget(itLovProdCategory);
                adc.addPartialTarget(itCategory);
                adc.addPartialTarget(tblListProduct);
            }
        } else {
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Data produk ada yang belum selesai dilengkapi dan kombinasi belum valid.</p>");
            message.append("<p>Proses penambahan produk baru tidak dapat dilanjutkan.</p>");
            message.append("</body></html>");
            JSFUtils.addFacesWarningMessage(message.toString());
        }
    }

    public void growthByValueChangeListener(ValueChangeEvent valueChangeEvent) {
        itTargetPercentage.setValue(0);
        itTargetValue.setValue(0);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itTargetPercentage);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itTargetValue);
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

    public void setItTargetQty(RichInputText itTargetQty) {
        this.itTargetQty = itTargetQty;
    }

    public RichInputText getItTargetQty() {
        return itTargetQty;
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

    public void setItTargetHarga(RichInputText itTargetHarga) {
        this.itTargetHarga = itTargetHarga;
    }

    public RichInputText getItTargetHarga() {
        return itTargetHarga;
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

    public void setItValueTotal(RichInputText itValueTotal) {
        this.itValueTotal = itValueTotal;
    }

    public RichInputText getItValueTotal() {
        return itValueTotal;
    }

    public void setTblListProductAddBuy(RichTable tblListProductAddBuy) {
        this.tblListProductAddBuy = tblListProductAddBuy;
    }

    public RichTable getTblListProductAddBuy() {
        return tblListProductAddBuy;
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
    }

    public void productBrandAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdExtAddBuy().setValue(null);
            this.getItlovProdPackAddBuy().setValue(null);
        }
    }

    public void productExtentionAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovProdPackAddBuy().setValue(null);
        }
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

    public void setTblListProductBonus(RichTable tblListProductBonus) {
        this.tblListProductBonus = tblListProductBonus;
    }

    public RichTable getTblListProductBonus() {
        return tblListProductBonus;
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

    public void setItlovProdClassBonus(RichInputListOfValues itlovProdClassBonus) {
        this.itlovProdClassBonus = itlovProdClassBonus;
    }

    public RichInputListOfValues getItlovProdClassBonus() {
        return itlovProdClassBonus;
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

    public void setItlovProdBrandBonus(RichInputListOfValues itlovProdBrandBonus) {
        this.itlovProdBrandBonus = itlovProdBrandBonus;
    }

    public RichInputListOfValues getItlovProdBrandBonus() {
        return itlovProdBrandBonus;
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

    public void setItlovProdExtBonus(RichInputListOfValues itlovProdExtBonus) {
        this.itlovProdExtBonus = itlovProdExtBonus;
    }

    public RichInputListOfValues getItlovProdExtBonus() {
        return itlovProdExtBonus;
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

    public void setItlovProdPackBonus(RichInputListOfValues itlovProdPackBonus) {
        this.itlovProdPackBonus = itlovProdPackBonus;
    }

    public RichInputListOfValues getItlovProdPackBonus() {
        return itlovProdPackBonus;
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

    public void setItlovProdExtention(RichInputListOfValues itlovProdExtention) {
        this.itlovProdExtention = itlovProdExtention;
    }

    public RichInputListOfValues getItlovProdExtention() {
        return itlovProdExtention;
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
            Row row = iter.getRowSetIterator().createRow();
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
    }

    public void setItlovProdPackaging(RichInputListOfValues itlovProdPackaging) {
        this.itlovProdPackaging = itlovProdPackaging;
    }

    public RichInputListOfValues getItlovProdPackaging() {
        return itlovProdPackaging;
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

    public void itlovCustomerPilih(ValueChangeEvent valueChangeEvent) {
        String chgNewVal = (String)valueChangeEvent.getNewValue();
        if (chgNewVal.equalsIgnoreCase(prodArea)) {
            DCIteratorBinding dciterArea =
                ADFUtils.findIterator("ProdRegionAreaView1Iterator");
            RowSetIterator rsiArea = dciterArea.getRowSetIterator();
            for (Row areaRow : dciterArea.getAllRowsInRange()) {
                areaRow.remove();
            }
            rsiArea.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);
        } else if (chgNewVal.equalsIgnoreCase(prodCustomer)) {
            DCIteratorBinding dciterCustomer =
                ADFUtils.findIterator("ProdRegionCustomerView1Iterator");
            RowSetIterator rsiCustomer = dciterCustomer.getRowSetIterator();
            for (Row customerRow : dciterCustomer.getAllRowsInRange()) {
                customerRow.remove();
            }
            rsiCustomer.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustomer);
        } else if (chgNewVal.equalsIgnoreCase(prodRegion)) {
            DCIteratorBinding dciterRegion =
                ADFUtils.findIterator("ProdRegionView1Iterator");
            RowSetIterator rsiRegion = dciterRegion.getRowSetIterator();
            for (Row regionRow : dciterRegion.getAllRowsInRange()) {
                regionRow.remove();
            }
            rsiRegion.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductRegion);
        } else if (chgNewVal.equalsIgnoreCase(prodLocation)) {
            DCIteratorBinding dciterLocation =
                ADFUtils.findIterator("ProdRegionLocView1Iterator");
            RowSetIterator rsiLocation = dciterLocation.getRowSetIterator();
            for (Row locationRow : dciterLocation.getAllRowsInRange()) {
                locationRow.remove();
            }
            rsiLocation.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductLocation);
        } else if (chgNewVal.equalsIgnoreCase(prodCustGroup)) {
            DCIteratorBinding dciterCustGroup =
                ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
            RowSetIterator rsiCustGroup = dciterCustGroup.getRowSetIterator();
            for (Row custGroupRow : dciterCustGroup.getAllRowsInRange()) {
                custGroupRow.remove();
            }
            rsiCustGroup.closeRowSetIterator();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductCustGroup);
        } else {
            // DO NOTHING
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchRegCust);
    }

    public void setSwitchRegCust(UIXSwitcher switchRegCust) {
        this.switchRegCust = switchRegCust;
    }

    public UIXSwitcher getSwitchRegCust() {
        return switchRegCust;
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

    public void windowProdukAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteProdukArea");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductArea);
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

    public void saveAll(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding userTypeAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String userTypeCreator = (String)userTypeAttr.getInputValue();
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
        if (userTypeCreator.equalsIgnoreCase(userArea)) {
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
        ApprovalAMImpl approvalAM =
            (ApprovalAMImpl)ADFUtils.getApplicationModuleForDataControl("ApprovalAMDataControl");
        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        Key prodKey = null;
        ViewObject vo1 = dciter.getViewObject();
        RowSetIterator rsProductIter = vo1.createRowSetIterator(null);
        Row currRow = null;
        while (rsProductIter.hasNext()) {
            currRow = rsProductIter.next();
            prodKey = currRow.getKey();
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
            ProdVariantValidationViewImpl prodVariant =
                approvalAM.getProdVariantValidationView1();
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
                            approvalAM.getFcsViewCategCombinationView1();
                        catCombView.setNamedWhereClauseParam("combVal",
                                                             fullComb);
                        catCombView.executeQuery();

                        if (catCombView.getEstimatedRowCount() == 1 &&
                            rowIsValid.equalsIgnoreCase("T")) {
                            currRow.setAttribute("ValidComb", "Y");
                        } else {
                            currRow.setAttribute("ValidComb", "N");
                            rowIsValid = "F";
                        }
                    }
                }
            }
        }
        rsProductIter.closeRowSetIterator();

        OperationBinding operationCommitLast =
            bindings.getOperationBinding("Commit");
        operationCommitLast.execute();

        if (prodKey != null) {
            dciter.setCurrentRowWithKey(prodKey.toStringFormat(true));
        }
    }

    public void setRbApprovalAction(RichSelectOneRadio rbApprovalAction) {
        this.rbApprovalAction = rbApprovalAction;
    }

    public RichSelectOneRadio getRbApprovalAction() {
        return rbApprovalAction;
    }

    public void setCheckCanApprove(String checkCanApprove) {
        this.checkCanApprove = checkCanApprove;
    }

    public String getCheckCanApprove() {
        checkCanApprove = null;

        BindingContainer bindings = getBindings();

        AttributeBinding mekPenagihanAttr =
            (AttributeBinding)bindings.getControlBinding("MekanismePenagihan1");
        String mekPenagihan = (String)mekPenagihanAttr.getInputValue();

        AttributeBinding periodeFromAttr =
            (AttributeBinding)bindings.getControlBinding("PeriodeProgFrom");
        String startDateString = (String)periodeFromAttr.getInputValue();

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date startDate = null;
        try {
            startDate = df.parse(startDateString + " 00:00:01");
        } catch (ParseException e) {
            JSFUtils.addFacesErrorMessage(e.getMessage());
        }

        Date todayDate = new Date();
        if ((todayDate.after(startDate) &&
             mekPenagihan.equalsIgnoreCase(onInvoice)) ||
            (todayDate.equals(startDate) &&
             mekPenagihan.equalsIgnoreCase(onInvoice))) {
            checkCanApprove = "N";
            itReasonApproval.setValue("Approval tidak dapat diteruskan, sudah memasuki periode promo.");
            rbApprovalAction.setValue(1);
        } else {
            checkCanApprove = "Y";
            itReasonApproval.setValue("");
            rbApprovalAction.setValue(0);
        }

        return checkCanApprove;
    }

    public void setItReasonApproval(RichInputText itReasonApproval) {
        this.itReasonApproval = itReasonApproval;
    }

    public RichInputText getItReasonApproval() {
        return itReasonApproval;
    }

    public void setTblListProductLocation(RichTable tblListProductLocation) {
        this.tblListProductLocation = tblListProductLocation;
    }

    public RichTable getTblListProductLocation() {
        return tblListProductLocation;
    }

    public void setTblListRegion(RichTable tblListRegion) {
        this.tblListRegion = tblListRegion;
    }

    public RichTable getTblListRegion() {
        return tblListRegion;
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
            JSFUtils.addFacesErrorMessage("Error",
                                          "\"Tipe Customer\" tidak dikenali.");
        }
        clearAllCustExclude();
    }

    public void setTblListArea(RichTable tblListArea) {
        this.tblListArea = tblListArea;
    }

    public RichTable getTblListArea() {
        return tblListArea;
    }

    public void windowRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding executeRegion =
            bindings.getOperationBinding("ExecuteRegion");
        executeRegion.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListRegion);
    }

    public void windowAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteArea");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListArea);
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
    
    public void cancelDetailProduct(ActionEvent actionEvent) {
        pdetailProduct.hide();
        tabTargerCustomer.setDisclosed(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabTargerCustomer);
    }

    public void showPopup(String pesan, RichPopup p) {
        otpesan.setValue(pesan);
        AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
        adc.addPartialTarget(otpesan);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        p.show(hints);
    }

    public void setPdetailProduct(RichPopup pdetailProduct) {
        this.pdetailProduct = pdetailProduct;
    }

    public void setTblListCustomer(RichTable tblListCustomer) {
        this.tblListCustomer = tblListCustomer;
    }

    public RichTable getTblListCustomer() {
        return tblListCustomer;
    }

    public void setTblListLocation(RichTable tblListLocation) {
        this.tblListLocation = tblListLocation;
    }

    public RichTable getTblListLocation() {
        return tblListLocation;
    }

    public RichPopup getPdetailProduct() {
        return pdetailProduct;
    }

    public void setTabTargerCustomer(RichShowDetailItem tabTargerCustomer) {
        this.tabTargerCustomer = tabTargerCustomer;
    }

    public RichShowDetailItem getTabTargerCustomer() {
        return tabTargerCustomer;
    }

    public void tabTargetEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            AttributeBinding userTypeAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            String userTypeCreator = (String)userTypeAttr.getInputValue();
            if (userTypeCreator.equalsIgnoreCase(userHo)) {
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

    public void tabBiayaEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            AttributeBinding userTypeAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            String userTypeCreator = (String)userTypeAttr.getInputValue();
            if (userTypeCreator.equalsIgnoreCase(userHo)) {
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
            AttributeBinding userTypeAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            String userTypeCreator = (String)userTypeAttr.getInputValue();
            if (userTypeCreator.equalsIgnoreCase(userHo)) {
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
            AttributeBinding userTypeAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            String userTypeCreator = (String)userTypeAttr.getInputValue();
            if (userTypeCreator.equalsIgnoreCase(userHo)) {
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

    public void setTabBiaya(RichShowDetailItem tabBiaya) {
        this.tabBiaya = tabBiaya;
    }

    public RichShowDetailItem getTabBiaya() {
        return tabBiaya;
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


    public void btnOkDetailProduct(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discType = (String)discTypeAttr.getInputValue();
        Boolean isIterValid = true;
        String sIterMsg = "";
        AttributeBinding userTypeAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String userTypeCreator = (String)userTypeAttr.getInputValue();
        if (discType.equalsIgnoreCase("BIAYA")) {
            DCIteratorBinding dciterBiaya =
                ADFUtils.findIterator("BiayaView1Iterator");
            if (dciterBiaya.getEstimatedRowCount() < 1) {
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
        if (userTypeCreator.equalsIgnoreCase(userHo)) {
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
            if (discType.equalsIgnoreCase("BIAYA")) {
                Boolean isInputValid = true;
                String sErrMsg = "";
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
                    eventOk(userTypeCreator);
                } else {
                    showPopup(sErrMsg + " Harus Diisi", potmessage);
                    tabBiaya.setDisclosed(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tabBiaya);
                }

            } else if (discType.equalsIgnoreCase("PROMOBARANG")) {
                Boolean isInputValid = true;
                String sErrMsg = "";
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
                    eventOk(userTypeCreator);
                } else {
                    showPopup(sErrMsg + " Harus Diisi", potmessage);
                    tabPromoBarang.setDisclosed(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tabPromoBarang);
                }
            } else if (discType.equalsIgnoreCase("POTONGAN")) {
                Boolean isInputValid = true;
                String sErrMsg = "";
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
                    eventOk(userTypeCreator);
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

    private void eventOk(String userType) {
        BindingContainer bindings = getBindings();
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

            AttributeBinding userTypeCreatorAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            userTypeCreatorAttr.setInputValue(userHo);
        }

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
        pdetailProduct.hide();
        DCBindingContainer bindings1 =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter =
            bindings1.findIteratorBinding("PromoProdukView1Iterator");
        iter.executeQuery();

        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecutePromoProduct");
        operationBinding.execute();
    }

    public void setPapprove(RichPopup papprove) {
        this.papprove = papprove;
    }

    public RichPopup getPapprove() {
        return papprove;
    }

    public void approveEventBtn(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding discTypeAttr =
            (AttributeBinding)bindings.getControlBinding("DiscountType1");
        String discType = (String)discTypeAttr.getInputValue();
        AttributeBinding userTypeAttr =
            (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
        String userTypeCreator = (String)userTypeAttr.getInputValue();
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
        if (userTypeCreator.equalsIgnoreCase(userHo)) {
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
                papprove.show(hints);
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
            if (userTypeCreator.equalsIgnoreCase(userArea)) {
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
                papprove.show(hints);
            } else {
                showPopup(sIterMsg + " Harus Diisi", potmessage);
            }
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

    public void setItVariant(RichInputText itVariant) {
        this.itVariant = itVariant;
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

    public RichInputText getItVariant() {
        return itVariant;
    }

    public void setItBonusVariant(RichInputText itBonusVariant) {
        this.itBonusVariant = itBonusVariant;
    }

    public RichInputText getItBonusVariant() {
        return itBonusVariant;
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

    public void setTblListExclLocation(RichTable tblListExclLocation) {
        this.tblListExclLocation = tblListExclLocation;
    }

    public RichTable getTblListExclLocation() {
        return tblListExclLocation;
    }

    public void setTblListExclProductRegion(RichTable tblListExclProductRegion) {
        this.tblListExclProductRegion = tblListExclProductRegion;
    }

    public RichTable getTblListExclProductRegion() {
        return tblListExclProductRegion;
    }

    public void setTblListExclProductArea(RichTable tblListExclProductArea) {
        this.tblListExclProductArea = tblListExclProductArea;
    }

    public RichTable getTblListExclProductArea() {
        return tblListExclProductArea;
    }

    public void setTblListExclProductLoc(RichTable tblListExclProductLoc) {
        this.tblListExclProductLoc = tblListExclProductLoc;
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

    public void windowExclProdukRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukRegion");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductRegion);
    }

    public void windowExclProdukAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukArea");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductArea);
    }

    public void windowExclProdukLocReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukLoc");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductLoc);
    }

    public void windowExclProdukCustReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteExclProdukCust");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListExclProductCust);
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

        clearAllCustExclude();
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchExclCust);
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
        if (socType.equalsIgnoreCase("PERCENT")) {
            rOntop =
                    new BigDecimal(rowOntop.getValue().toString().replaceAll(",",
                                                                             ""));
            String rasio = otRasioMf.getValue().toString();
            rasioT =
                    new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                   ""));
            ontop =
                    value.multiply(rOntop).getBigDecimalValue().divide(bdHundred);
            otOnTop.setSubmittedValue(ontop);
            RasioOntop =
                    ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasOntop = RasioOntop.toString();
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
                String discIdComp = r.getAttribute("DiscountId").toString();
                if (!discId.equalsIgnoreCase(discIdComp)) {
                    String valueTop =
                        r.getAttribute("DiscNonYearly").toString().replaceAll(",",
                                                                              "");
                    BigDecimal ontopValue =
                        new BigDecimal(valueTop) == null ? new BigDecimal(0) :
                        new BigDecimal(valueTop);
                    BigDecimal newChangedOnTopValue =
                        new BigDecimal(valueChangeEvent.getNewValue().toString());

                    if (maxOntop.compareTo(ontopValue) < 0) {
                        maxOntop = ontopValue;
                    }

                    if (maxOntop.compareTo(newChangedOnTopValue) < 0) {
                        maxOntop = newChangedOnTopValue;
                    }
                } else {
                    BigDecimal newChangedOnTopValue =
                        new BigDecimal(valueChangeEvent.getNewValue().toString());
                    if (maxOntop.compareTo(newChangedOnTopValue) < 0) {
                        maxOntop = newChangedOnTopValue;
                    }
                }
            }
            ontop = qty.getBigDecimalValue().multiply(maxOntop);
            otOnTop.setSubmittedValue(ontop);
            RasioOntop =
                    ontop.divide(value.getBigDecimalValue(), 2, RoundingMode.HALF_UP).multiply(bdHundred);
            String rasio = otRasioMf.getValue().toString();
            rasioT =
                    new BigDecimal(rasio.toString().replaceAll(" ", "").replaceAll("%",
                                                                                   ""));
            String rasOntop = RasioOntop.toString();
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
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
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
            mf = value.multiply(rMf).getBigDecimalValue().divide(bdHundred);
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
                        new BigDecimal(valueChangeEvent.getNewValue().toString());
                    if (maxMf.compareTo(mfValue) < 0) {
                        maxMf = mfValue;
                    }
                    if (maxMf.compareTo(newChangedMfValue) < 0) {
                        maxMf = newChangedMfValue;
                    }

                } else {
                    BigDecimal newChangedMfValue =
                        new BigDecimal(valueChangeEvent.getNewValue().toString());
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProductBonus);
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
 value.multiply(rMf).getBigDecimalValue().divide(bdHundred);
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
                            value.multiply(rOntop).getBigDecimalValue().divide(bdHundred);
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
        }
    }

    public void tabExcludeEvent(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
            AttributeBinding userTypeAttr =
                (AttributeBinding)bindings.getControlBinding("UserTypeCreator");
            String userTypeCreator = (String)userTypeAttr.getInputValue();
            if (userTypeCreator.equalsIgnoreCase(userHo)) {
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
                r.setAttribute("ExclCustBy", null);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
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
                    otBrgRasioMf.getValue() == "" ? "0" :
                    otBrgRasioMf.getValue() == null ? "0" :
                    otBrgRasioMf.getValue().toString();
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
                    otBrgRasioMf.getValue() == "" ? "0" : 
                    otBrgRasioMf.getValue() == null ? "0" : 
                    otBrgRasioMf.getValue().toString();
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
            }
            } else {
                JSFUtils.addFacesWarningMessage("Kolom \"Price\" tidak boleh kosong atau diisi 0 (Nol)");
            }
        }
    }

    public void setItCategory(RichOutputText itCategory) {
        this.itCategory = itCategory;
    }

    public RichOutputText getItCategory() {
        return itCategory;
    }

    public void setItLovProdCategory(RichInputListOfValues itLovProdCategory) {
        this.itLovProdCategory = itLovProdCategory;
    }

    public RichInputListOfValues getItLovProdCategory() {
        return itLovProdCategory;
    }

    public void setItProductItem(RichInputText itProductItem) {
        this.itProductItem = itProductItem;
    }

    public RichInputText getItProductItem() {
        return itProductItem;
    }

    public void setLinkProduct(RichCommandImageLink linkProduct) {
        this.linkProduct = linkProduct;
    }

    public RichCommandImageLink getLinkProduct() {
        return linkProduct;
    }

    public void setLinkVariant(RichCommandImageLink linkVariant) {
        this.linkVariant = linkVariant;
    }

    public RichCommandImageLink getLinkVariant() {
        return linkVariant;
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
            
            DCIteratorBinding iterVar =
                ADFUtils.findIterator("ProdukVariantView1Iterator");
            for (Row r : iterVar.getAllRowsInRange()) {
                String idVar = r.getAttribute("PromoProdukId").toString();
                if (idVar.equalsIgnoreCase(PromoProdukIdSel)) {
                    r.remove();
                }
            }
            //iterVar.getDataControl().commitTransaction();
            
            DCIteratorBinding iterItem =
                ADFUtils.findIterator("ProdukItemView1Iterator");
            for (Row rItem : iterItem.getAllRowsInRange()) {
                String idItem = rItem.getAttribute("PromoProdukId").toString();
                if (idItem.equalsIgnoreCase(PromoProdukIdSel)) {
                    rItem.remove();
                }
            }
            //iterItem.getDataControl().commitTransaction();
            
            DCIteratorBinding iterPP =
                ADFUtils.findIterator("PromoProdukView1Iterator");
            for (Row rPP : iterPP.getAllRowsInRange()) {
                String idPP = rPP.getAttribute("PromoProdukId").toString();
                if (idPP.equalsIgnoreCase(PromoProdukIdSel)) {
                    rPP.remove();
                }
            }
            //iterPP.getDataControl().commitTransaction();
        }
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
                otBiaRasioMf.getValue() == "" ? "0" : 
                otBiaRasioMf.getValue() == null ? "0" : 
                otBiaRasioMf.getValue().toString();
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
                otBiaRasioMf.getValue() == "" ? "0" : 
                otBiaRasioMf.getValue() == null ? "0" : 
                otBiaRasioMf.getValue().toString();
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
                    (Number)r.getAttribute("BiayaYearly") == null ? new Number(0) :
                    (Number)r.getAttribute("BiayaYearly");
                totalOT = totalOT.add(MF.getBigDecimalValue());
            }
            BiaIter.closeRowSetIterator();
            String rasio =
                otBiaRasioOntop.getValue() == "" ? "0" : 
                otBiaRasioOntop.getValue() == null ? "0" : 
                otBiaRasioOntop.getValue().toString();
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
        DCIteratorBinding parentIter =
            (DCIteratorBinding)bindings.get("ProposalApprovalView1Iterator");
        //Get current row key
        Key parentKey = parentIter.getCurrentRow().getKey();

        OperationBinding operationBindingRollback =
            bindings.getOperationBinding("Rollback");
        operationBindingRollback.execute();

        //Set again row key as current row
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
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
    }

    public void vcePeriodePromo(ValueChangeEvent valueChangeEvent) throws ParseException {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
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
        SimpleDateFormat formatterui = new SimpleDateFormat("dd-MMM-yyyy");
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
            if(idPeriodeTo.getValue()!= null){
                java.util.Date date1 = new Date(idPeriodeTo.getValue().toString());
                java.util.Date date21 =
                    new Date(valueChangeEvent.getNewValue().toString());
                long days = ADFUtils.daysBetween(date1, date21);
                Number days1 = new Number(days + 1);
                itCountDays.setValue(days1);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itCountDays);
            }
        } else {
            idPeriodProgFrom.setSubmittedValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(idPeriodProgFrom);
            JSFUtils.addFacesWarningMessage("Pengajuan proposal ini tidak diperkenankan untuk \"Back Date\"");
        }
    }

    public void idPeriodProgToCount(ValueChangeEvent valueChangeEvent) {
        if(idPeriodProgFrom.getValue() != null){
        java.util.Date date1 =
            new Date(idPeriodProgFrom.getValue().toString());
        java.util.Date date2 =
            new Date(valueChangeEvent.getNewValue().toString());
        long days = ADFUtils.daysBetween(date1, date2);
        Number days1 = new Number(days + 1);
        itCountDays.setValue(days1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itCountDays);
        }
    }

    public void setIdPeriodeTo(RichInputDate idPeriodeTo) {
        this.idPeriodeTo = idPeriodeTo;
    }

    public RichInputDate getIdPeriodeTo() {
        return idPeriodeTo;
    }

    public void setIdPeriodProgFrom(RichInputDate idPeriodProgFrom) {
        this.idPeriodProgFrom = idPeriodProgFrom;
    }

    public RichInputDate getIdPeriodProgFrom() {
        return idPeriodProgFrom;
    }

    public void setOtCountDays(RichOutputText otCountDays) {
        this.otCountDays = otCountDays;
    }

    public RichOutputText getOtCountDays() {
        return otCountDays;
    }

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


    public void setItCountDays(RichInputText itCountDays) {
        this.itCountDays = itCountDays;
    }

    public RichInputText getItCountDays() {
        return itCountDays;
    }

    public void setSwitchMain(UIXSwitcher switchMain) {
        this.switchMain = switchMain;
    }

    public UIXSwitcher getSwitchMain() {
        return switchMain;
    }

    public void setSocForwardTo(RichSelectOneChoice socForwardTo) {
        this.socForwardTo = socForwardTo;
    }

    public RichSelectOneChoice getSocForwardTo() {
        return socForwardTo;
    }

    public void actionValueChangeListener(ValueChangeEvent valueChangeEvent) {
        socForwardTo.setSubmittedValue(null);
        itUserForward.setSubmittedValue(null);
    }

    public void setItUserForward(RichInputText itUserForward) {
        this.itUserForward = itUserForward;
    }

    public RichInputText getItUserForward() {
        return itUserForward;
    }

    public void forwardValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itUserForward);
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
        ADFUtils.invokeEL("#{bindings.ProposalApprovalView1Query.processQuery}",
                          new Class[] { QueryEvent.class },
                          new Object[] { queryEvent });
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchMain);
    }

    public void setPropTypeVal(RichSelectOneChoice propTypeVal) {
        this.propTypeVal = propTypeVal;
    }

    public RichSelectOneChoice getPropTypeVal() {
        return propTypeVal;
    }
}
