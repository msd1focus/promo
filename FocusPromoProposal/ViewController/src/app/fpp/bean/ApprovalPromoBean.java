package app.fpp.bean;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;

import app.fpp.model.am.ApprovalAMImpl;
import app.fpp.model.views.masterdata.ebs.FcsViewCategCombinationViewImpl;
import app.fpp.model.views.promoproposal.PromoProdukViewImpl;
import app.fpp.model.views.promoproposal.validation.ProdVariantValidationViewImpl;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

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
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.DBSequence;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;

public class ApprovalPromoBean {
    private RichTable tblApproval;
    private RichTable tblDocHistory;
    private final Integer APPROVED = 0;
    private final Integer REJECTED = 1;
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
    
    public ApprovalPromoBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void proposalApproveRejectDialog(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();

        AttributeBinding aprvlActionAttr =
            (AttributeBinding)bindings.getControlBinding("Action");
        Integer aprvlAction = (Integer)aprvlActionAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            try {
                if (aprvlAction.equals(APPROVED)) {

                    DCBindingContainer dcBindings =
                        (DCBindingContainer)getBindings();
                    DCIteratorBinding dciter =
                        ADFUtils.findIterator("ApprovalReceiverApproveProposalView1Iterator");

                    if (dciter.getEstimatedRowCount() > 0) {
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

                } else if (aprvlAction.equals(REJECTED)) {

                    AttributeBinding rejectReason =
                        (AttributeBinding)bindings.getControlBinding("Reason");
                    String reasonStr = rejectReason.getInputValue() == null ? "" : (String)rejectReason.getInputValue();
                    
                    if (!reasonStr.trim().equalsIgnoreCase("")) {
                        AttributeBinding propStatus =
                            (AttributeBinding)bindings.getControlBinding("Status1");
                        propStatus.setInputValue(docStatusReject);
    
                        OperationBinding operationApproveApproval =
                            bindings.getOperationBinding("rejectDocApproval");
                        operationApproveApproval.execute();
                    } else {
                        JSFUtils.addFacesWarningMessage("Reason harus diisi apabila melakukan reject approval.");
                    }
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

                //AdfFacesContext.getCurrentInstance().addPartialTarget(tblApproval);
                //AdfFacesContext.getCurrentInstance().addPartialTarget(tblDocHistory);

            } catch (JboException e) {
                JSFUtils.addFacesErrorMessage("Error", e.getBaseMessage());
            }
        } else {
            /*
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            */
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
            AttributeBinding discTypeAttr =
                (AttributeBinding)bindings.getControlBinding("DiscountType");
            String discType = (String)discTypeAttr.getInputValue();
            //            System.out.println("discType = "+discType);
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();
        } else {
            /*
            OperationBinding operationBindingRollback =
                bindings.getOperationBinding("Rollback");
            operationBindingRollback.execute();
            */
            // Just do nothing !!!, kalau di rollback suka banyak yang ke reset jadi null soc nya.
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
    }

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
            new BigDecimal(itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue().toString().replaceAll(",",
                                                                          ""));
        BigDecimal totalValue = tgtQty.multiply(tgtHarga);
        oracle.jbo.domain.Number number = null;
        try {
            number = new oracle.jbo.domain.Number(totalValue.toString());
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
            new BigDecimal(itTargetQty.getValue().toString().replaceAll(",",
                                                                        ""));
        BigDecimal tgtHarga =
            new BigDecimal(itTargetHarga.getValue().toString().replaceAll(",",
                                                                          ""));
        BigDecimal totalValue = tgtQty.multiply(tgtHarga);
        oracle.jbo.domain.Number number = null;
        try {
            number = new oracle.jbo.domain.Number(totalValue.toString());
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

    public void productPackAddBuyChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
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
        }else{
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
            Object result = operationBinding.execute();
            dciter.executeQuery();  
            
            itlovProdExtBonus.setValue("ALL");
            itlovProdPackBonus.setValue("ALL");
                
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
           
           ctx.addPartialTarget(itlovProdExtBonus);
           ctx.addPartialTarget(itlovProdPackBonus);
           ctx.addPartialTarget(itBonusVariant);
           ctx.addPartialTarget(tblListProductBonus);
        }else{
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
            Object result = operationBinding.execute();
            dciter.executeQuery();  
            
            itlovProdPackBonus.setValue("ALL");
                
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
           ctx.addPartialTarget(itlovProdPackBonus);
           ctx.addPartialTarget(itBonusVariant);
           ctx.addPartialTarget(tblListProductBonus);
        }else{
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
        }else{
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
                //            lastRow = iter.getNavigatableRowIterator().last();
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

    public void setItlovProdExtention(RichInputListOfValues itlovProdExtention) {
        this.itlovProdExtention = itlovProdExtention;
    }

    public RichInputListOfValues getItlovProdExtention() {
        return itlovProdExtention;
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

            Row row = iter.getRowSetIterator().createRow();
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
        }else{
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
            System.out.println("NO CHANGE");
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
//        UserData userData =
//            (UserData)JSFUtils.resolveExpression("#{UserData}");
//        String userType = userData.getUserType();
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
        }else{
            saveAll();
        }
    }
    private void saveAll(){
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

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblListProduct);
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
            System.out.println("NO CHANGE ON PROP CUSTOMER");
            JSFUtils.addFacesErrorMessage("Error",
                                          "Customer option area not recognized");
        }
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
//            UserData userData =
//                (UserData)JSFUtils.resolveExpression("#{UserData}");
//            String userType = userData.getUserType();
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
                Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
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
//            UserData userData =
//                (UserData)JSFUtils.resolveExpression("#{UserData}");
//            String userType = userData.getUserType();
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
                Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
                if (!(cekArea > 0 || cekCustomer > 0 || cekRegion > 0 ||
                      cekLoc > 0 || cekCustGroup > 0)) {
                    showPopup("Tab customer harus diisi", potmessage);
                    tabTargerCustomer.setDisclosed(true);
                }
            }
        }
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

    public void tabPromoBarang(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            BindingContainer bindings = getBindings();
//            UserData userData =
//                (UserData)JSFUtils.resolveExpression("#{UserData}");
//            String userType = userData.getUserType();
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
                Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
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
//            UserData userData =
//                (UserData)JSFUtils.resolveExpression("#{UserData}");
//            String userType = userData.getUserType();
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
                Integer cekCustGroup = (int)dciterCustGroup.getEstimatedRowCount();
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
        
        DCIteratorBinding dciterCustGroup =
            ADFUtils.findIterator("PropRegionCustGroupView1Iterator");
        for (Row r : dciterCustGroup.getAllRowsInRange()) {
            String regCode = (String)r.getAttribute("RegionCode");
            String[] arrayRegCode = regCode.split("\\|", -1);
            for (int i=0; i < arrayRegCode.length; i++) {
                if (!regAprvlNewList.contains(arrayRegCode[i])) {
                    regAprvlNewList.add(arrayRegCode[i]);
                }
            }
        }
        
        ArrayList<String> regAprvlUnionList = new ArrayList<String>(regAprvlOldList);
        for (String regCodeNew : regAprvlNewList) {
            if (!regAprvlUnionList.contains(regCodeNew)) {
                regAprvlUnionList.add(regCodeNew);
            }
        }
        
        for (String regCode : regAprvlUnionList) { 
            if (regAprvlOldList.contains(regCode) && !regAprvlNewList.contains(regCode)) {
                Key keyRegCode = new Key(new Object[] { propId.getValue(), regCode });
                RowSetIterator rsiAprvlReg = dciterApprovalRegion.getRowSetIterator();
                Row[] rowApprovalRegion =rsiAprvlReg.findByKey(keyRegCode, 1);
                rowApprovalRegion[0].remove();
            } else if (!regAprvlOldList.contains(regCode) && regAprvlNewList.contains(regCode)) {
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
            int price =
                Integer.parseInt(r.getAttribute("Price") == null ? "0" :
                                 r.getAttribute("Price").toString());
            int Qty =
                Integer.parseInt(r.getAttribute("Qty") == null ? "0" : r.getAttribute("Qty").toString());
            if (price <= 0 || Qty <= 0) {
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
                    for (int i=0; i < arrayRegCode.length; i++) {
                        if (!regAprvlNewList.contains(arrayRegCode[i])) {
                            regAprvlNewList.add(arrayRegCode[i]);
                        }
                    }
                }
            } else {
                System.out.println("PRODUK CUSTOMER TYPE NOT RECOGNIZED ");
            }
            
            ArrayList<String> regAprvlUnionList = new ArrayList<String>(regAprvlOldList);
            for (String regCodeNew : regAprvlNewList) {
                if (!regAprvlUnionList.contains(regCodeNew)) {
                    regAprvlUnionList.add(regCodeNew);
                }
            }
            
            for (String regCode : regAprvlUnionList) { 
                if (regAprvlOldList.contains(regCode) && !regAprvlNewList.contains(regCode)) {
                    Key keyRegCode = new Key(new Object[] { propId.getValue(), regCode });
                    RowSetIterator rsiAprvlReg = dciterApprovalRegion.getRowSetIterator();
                    Row[] rowApprovalRegion =rsiAprvlReg.findByKey(keyRegCode, 1);
                    rowApprovalRegion[0].remove();
                } else if (!regAprvlOldList.contains(regCode) && regAprvlNewList.contains(regCode)) {
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
        }else{
            Boolean isIterValid = true;
            String sIterMsg = "";
            if(discType.equalsIgnoreCase("BIAYA")){
                DCIteratorBinding dciterBiaya =
                    ADFUtils.findIterator("BiayaView1Iterator");
                if(dciterBiaya.getEstimatedRowCount() < 1){
                    if (!isIterValid) {
                        sIterMsg += ", ";
                    }
                    sIterMsg += "Biaya Pada Produk Detail";
                    isIterValid = false;
                }
            }else if(discType.equalsIgnoreCase("PROMOBARANG")){
                    DCIteratorBinding dciterPromoBonus =
                        ADFUtils.findIterator("PromoBonusView1Iterator");
                    if(dciterPromoBonus.getEstimatedRowCount() < 1){
                        if (!isIterValid) {
                            sIterMsg += ", ";
                        }
                        sIterMsg += "Promo Pada Produk Detail";
                        isIterValid = false;
                    }        
            }else if(discType.equalsIgnoreCase("POTONGAN")){
                    DCIteratorBinding dciterDiscount =
                        ADFUtils.findIterator("DiscountView1Iterator");
                    if(dciterDiscount.getEstimatedRowCount() < 1){
                        if (!isIterValid) {
                            sIterMsg += ", ";
                        }
                        sIterMsg += "Potongan Pada Produk Detail";
                        isIterValid = false;
                    }
            }
            if(userTypeCreator.equalsIgnoreCase(userArea)) {
                DCIteratorBinding dciterArea1 =
                    ADFUtils.findIterator("PropRegionAreaView1Iterator");
                DCIteratorBinding dciterCustomer1 =
                    ADFUtils.findIterator("PropRegionCustomerView1Iterator");
                DCIteratorBinding dciterRegion1 =
                    ADFUtils.findIterator("PropRegionView1Iterator");
                DCIteratorBinding dciterLoc1 =
                    ADFUtils.findIterator("PropRegionLocView1Iterator");
                DCIteratorBinding dciterCustGroup1 =
                    ADFUtils.findIterator("ProdRegionCustGroupView1Iterator");
                
                Integer cekArea1 = (int)dciterArea1.getEstimatedRowCount();
                Integer cekCustomer1 = (int)dciterCustomer1.getEstimatedRowCount();
                Integer cekRegion1 = (int)dciterRegion1.getEstimatedRowCount();
                Integer cekLoc1 = (int)dciterLoc1.getEstimatedRowCount();
                Integer cekCustGroup1 = (int)dciterCustGroup1.getEstimatedRowCount();
            if (!(cekArea1 > 0 || cekCustomer1 > 0 || cekRegion1 > 0 || cekLoc1 > 0 || cekCustGroup1 > 0)) {
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
                   showPopup(sIterMsg+ " Harus Diisi", potmessage);
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
        System.out.println("======= RETURN CUST TYPE NIH !!! =======");
        
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
        DBSequence promoProdukId = (DBSequence)promoProdukIdAttr.getInputValue();
        
        DCIteratorBinding dciter =
            ADFUtils.findIterator("PromoProdukView1Iterator");
        PromoProdukViewImpl promoProdView = (PromoProdukViewImpl)dciter.getViewObject();
        promoProdView.setWhereClause("PromoProduk.PROMO_PRODUK_ID = " + df.format(promoProdukId.getValue()));
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
        if (chgNewVal.equalsIgnoreCase(prodArea) && usrTypeCreator.equalsIgnoreCase(userHo)) {
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

        } else if (chgNewVal.equalsIgnoreCase(prodCustomer) && usrTypeCreator.equalsIgnoreCase(userHo)) {
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

        } else if (chgNewVal.equalsIgnoreCase(prodRegion) && usrTypeCreator.equalsIgnoreCase(userHo)) {
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

        } else if (chgNewVal.equalsIgnoreCase(prodLocation) && usrTypeCreator.equalsIgnoreCase(userHo)) {
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

        } else if (chgNewVal.equalsIgnoreCase(propArea) && usrTypeCreator.equalsIgnoreCase(userArea)) {
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

        } else if (chgNewVal.equalsIgnoreCase(propCustomer) && usrTypeCreator.equalsIgnoreCase(userArea)) {
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

        } else if (chgNewVal.equalsIgnoreCase(propRegion) && usrTypeCreator.equalsIgnoreCase(userArea)) {
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

        } else if (chgNewVal.equalsIgnoreCase(propLocation) && usrTypeCreator.equalsIgnoreCase(userArea)) {
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

    public void addPromoBonus(ActionEvent actionEvent) {
//        #{bindings.CreateInsertPromoBonus.execute}
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
