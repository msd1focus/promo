package app.fpp.bean.settingbudget;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

public class BudgetCustomerBean {
    private RichInputText budgetCustPercentage;
    private RichInputText budgetCustAmount;
    private RichInputText budgetAsToDate;
    private BigDecimal bdHundred = new BigDecimal("100");
    private RichInputListOfValues itlovBudClass;
    private RichInputListOfValues itlovBudBrand;
    private RichInputListOfValues itlovBudExtention;
    private RichInputListOfValues itlovBudPackaging;
    private RichInputListOfValues itlovBudVariant;

    public BudgetCustomerBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addCustBudgetPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsertBudCustTran");
        operationBinding.execute();
    }

    public void addCustBudgetDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            OperationBinding refreshCustTran =
                bindings.getOperationBinding("CustTranRefresh");
            refreshCustTran.execute();

            OperationBinding refreshBudgetCust =
                bindings.getOperationBinding("BudgetCustRefresh");
            refreshBudgetCust.execute();
        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void setBudgetCustPercentage(RichInputText budgetCustPercentage) {
        this.budgetCustPercentage = budgetCustPercentage;
    }

    public RichInputText getBudgetCustPercentage() {
        return budgetCustPercentage;
    }

    public void setBudgetCustAmount(RichInputText budgetCustAmount) {
        this.budgetCustAmount = budgetCustAmount;
    }

    public RichInputText getBudgetCustAmount() {
        return budgetCustAmount;
    }

    public void setBudgetAsToDate(RichInputText budgetAsToDate) {
        this.budgetAsToDate = budgetAsToDate;
    }

    public RichInputText getBudgetAsToDate() {
        return budgetAsToDate;
    }

    public void percentValueChangeListener(ValueChangeEvent valueChangeEvent) {

        DCIteratorBinding dciterTranHist =
            ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
        
        Number valPercentage = new Number(0); 
        Number valAmount = new Number(0);
        
        for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
            valPercentage = valPercentage.add((Number)tranHistRow.getAttribute("Percentage"));
            valAmount = valAmount.add((Number)tranHistRow.getAttribute("Amount"));
        }
        
        BigDecimal budPercentTran = valPercentage.getBigDecimalValue();
        BigDecimal budAmountTran = valAmount.getBigDecimalValue();  
        
        BigDecimal budPercent = BigDecimal.ZERO;
        if (budgetCustPercentage.getValue() != null) {
            budPercent = new BigDecimal(budgetCustPercentage.getValue().toString().replaceAll(",", ""));
            budPercent = budPercent.add(budPercentTran);
        }
        
        BigDecimal budAmount = BigDecimal.ZERO;
        if (budgetCustAmount.getValue() != null) {
            budAmount = new BigDecimal(budgetCustAmount.getValue().toString().replaceAll(",", ""));
            budAmount = budAmount.add(budAmountTran);
        }  
        
        BigDecimal budAsToDate = (budAmount.multiply(budPercent)).divide(bdHundred);
        oracle.jbo.domain.Number number = null;
        try {
            number = new oracle.jbo.domain.Number(budAsToDate.toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
        }
        budgetAsToDate.setValue(number);
    }

    public void amountValueChangeListener(ValueChangeEvent valueChangeEvent) {

        DCIteratorBinding dciterTranHist =
            ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
        
        Number valPercentage = new Number(0); 
        Number valAmount = new Number(0);
        
        for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
            valPercentage = valPercentage.add((Number)tranHistRow.getAttribute("Percentage"));
            valAmount = valAmount.add((Number)tranHistRow.getAttribute("Amount"));
        }
        
        BigDecimal budPercentTran = valPercentage.getBigDecimalValue();
        BigDecimal budAmountTran = valAmount.getBigDecimalValue();   
        
        BigDecimal budPercent = BigDecimal.ZERO;
        if (budgetCustPercentage.getValue() != null) {
            budPercent = new BigDecimal(budgetCustPercentage.getValue().toString().replaceAll(",", ""));
            budPercent = budPercent.add(budPercentTran);
        }
        
        BigDecimal budAmount = BigDecimal.ZERO;
        if (budgetCustAmount.getValue() != null) {
            budAmount = new BigDecimal(budgetCustAmount.getValue().toString().replaceAll(",", ""));
            budAmount = budAmount.add(budAmountTran);
        }  
        
        BigDecimal budAsToDate = (budAmount.multiply(budPercent)).divide(bdHundred);
        oracle.jbo.domain.Number number = null;
        try {
            number = new oracle.jbo.domain.Number(budAsToDate.toString());
        } catch (SQLException e) {
            JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
        }
        budgetAsToDate.setValue(number);
    }


    public void productCategoryChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovBudClass().setValue(null);
            this.getItlovBudBrand().setValue(null);
            this.getItlovBudExtention().setValue(null);
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }
    }

    public void productClassChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovBudBrand().setValue(null);
            this.getItlovBudExtention().setValue(null);
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }
    }

    public void productBrandChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovBudExtention().setValue(null);
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }
    }

    public void productExtentionChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }
    }

    public void productPackagingChanged(ValueChangeEvent valueChangeEvent) {
        this.getItlovBudVariant().setValue(null);
    }

    public void setItlovBudClass(RichInputListOfValues itlovBudClass) {
        this.itlovBudClass = itlovBudClass;
    }

    public RichInputListOfValues getItlovBudClass() {
        return itlovBudClass;
    }

    public void setItlovBudBrand(RichInputListOfValues itlovBudBrand) {
        this.itlovBudBrand = itlovBudBrand;
    }

    public RichInputListOfValues getItlovBudBrand() {
        return itlovBudBrand;
    }

    public void setItlovBudExtention(RichInputListOfValues itlovBudExtention) {
        this.itlovBudExtention = itlovBudExtention;
    }

    public RichInputListOfValues getItlovBudExtention() {
        return itlovBudExtention;
    }

    public void setItlovBudPackaging(RichInputListOfValues itlovBudPackaging) {
        this.itlovBudPackaging = itlovBudPackaging;
    }

    public RichInputListOfValues getItlovBudPackaging() {
        return itlovBudPackaging;
    }

    public void setItlovBudVariant(RichInputListOfValues itlovBudVariant) {
        this.itlovBudVariant = itlovBudVariant;
    }

    public RichInputListOfValues getItlovBudVariant() {
        return itlovBudVariant;
    }
}
