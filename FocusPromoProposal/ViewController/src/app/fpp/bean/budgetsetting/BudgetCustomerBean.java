package app.fpp.bean.budgetsetting;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.bean.useraccessmenu.UserData;
import app.fpp.model.am.BudgetSettingAMImpl;
import app.fpp.model.views.budgetsetting.BudgetCustHdrViewRowImpl;
import app.fpp.model.views.budgetsetting.BudgetCustTranGetDataImpl;
import app.fpp.model.views.budgetsetting.BudgetCustomerViewRowImpl;
import app.fpp.model.views.budgetsetting.CheckDuplicateBudgetViewImpl;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

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
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.QueryOperationEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.RemoveWithDetailsException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.component.UIXSwitcher;

public class BudgetCustomerBean {
    private RichInputText budgetCustAmount;
    private RichInputText budgetAsToDate;
    private BigDecimal bdHundred = new BigDecimal("100");
    private String action = "APPROVED";
    private String actionReject = "REJECTED";
    private RichInputListOfValues itlovBudClass;
    private RichInputListOfValues itlovBudBrand;
    private RichInputListOfValues itlovBudExtention;
    private RichInputListOfValues itlovBudPackaging;
    private RichInputListOfValues itlovBudVariant;
    private RichTable tblBudgetCustomer;
    private RichInputText itBudCustId;
    private RichPopup pbudCusTran;
    private RichSelectOneChoice socBudgetType;
    private RichOutputText otValue;
    private RichPopup ptranHistory;
    private RichInputText itPercentageTemp;
    private RichInputText budgetYearlyAmount;
    private RichInputListOfValues lovTypeBudgetHdr;
    private RichPanelGroupLayout itTradingTerm;
    private RichPopup pbalance;
    private RichInputListOfValues lovBudgetTypeTran;
    private RichPanelLabelAndMessage panelTradingTerm;
    private RichOutputText otPercentage;
    private UIXSwitcher switchMain;
    private RichCommandToolbarButton btnBudgetDelete;
    private RichCommandToolbarButton btnBudgetAdd;
    private RichCommandToolbarButton btnBudgetRefresh;
    private String dummyResetValue =
        "                                        ";
    private RichTable tblBudgetCustHdr;
    private RichPanelGroupLayout butCancelSaveGroup;
    private RichCommandButton btnCancel;
    private RichInputListOfValues itLovCustIdHdr;
    private RichInputListOfValues itLovKodePostingHdr;
    private RichInputListOfValues itLovBudgetYearHdr;
    private RichPopup ptranApproval;
    private static final DateFormat sdf =
        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private RichPopup potmessage;
    private RichOutputText otpesan;
    private RichCommandToolbarButton btnTranHistory;
    private RichCommandToolbarButton btnTranApproval;
    private String budgetTypeCust = "CUSTOMER";
    private String budgetTypePost = "POSTING";

    public BudgetCustomerBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addCustBudgetPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        String custIdHis = "";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("BudgetCustomerId") != null) {
            custIdHis =
                    rowSelected.getAttribute("BudgetCustomerId").toString();
        }
        String budgetType = "";
        DCIteratorBinding dciterTranHist =
            ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
        RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
        for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
            String custIdHisTra =
                (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
            if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                otValue.setValue(custIdHis);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otValue);
                budgetType = tranHistRow.getAttribute("BudgetType").toString();
                if (budgetType.equalsIgnoreCase("BALANCE")) {
                    budgetType = "ADJUSTMENT";
                    socBudgetType.setValue(0);
                    socBudgetType.setReadOnly(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(socBudgetType);
                }
            }
        }
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding =
            bindings1.getOperationBinding("CreateInsertBudCustTran");
        operationBinding.execute();
    }

    public void addCustBudgetDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            DCBindingContainer binding =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            String custIdHis = "";
            DCIteratorBinding dcItteratorBindings =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            Key budgetCustKey = dcItteratorBindings.getCurrentRow().getKey();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            /*  String budgetType = "";
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            Row tranHistRow = dciterTranHist.getCurrentRow();
            String custIdHisTra =
                (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
            String custBudgetAsToDate =
                (String)tranHistRow.getAttribute("BudgetAsToDate").toString();
            //                String   budYearlyAmount = budgetYearlyAmount.getValue() == null ? "0" : budgetYearlyAmount.getValue().toString();
            //                String   budAsToDate = budgetAsToDate.getValue()== null ? "0" : budgetAsToDate.getValue().toString();
            //                System.out.println("budYearlyAmount "+budYearlyAmount);
            //                System.out.println("budAsToDate "+budAsToDate);
            if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                //                   BigDecimal budgetAsToDate=new BigDecimal(budAsToDate.replaceAll(",", ""));
                //                  BigDecimal budgetAmountYearly=new BigDecimal(budYearlyAmount.replaceAll(",", ""));
                rowSelected.setAttribute("BudgetAsToDate", custBudgetAsToDate);
                //                  rowSelected.setAttribute("YearlyBudgetAmount", budgetAmountYearly);
                dciterTranHist.getDataControl().commitTransaction();
            } */

            DCIteratorBinding dciterCusTran =
                ADFUtils.findIterator("BudgetCustTranView1Iterator");

            dciterCusTran.getDataControl().commitTransaction();
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            OperationBinding refreshCustTran =
                bindings.getOperationBinding("CustTranRefresh");
            refreshCustTran.execute();

            OperationBinding refreshBudgetCust =
                bindings.getOperationBinding("BudgetCustRefresh");
            refreshBudgetCust.execute();
            dcItteratorBindings.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
        } else {
            String custIdHis = "";
            DCIteratorBinding dcItteratorBindings =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            Key budgetCustKey = dcItteratorBindings.getCurrentRow().getKey();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
            ViewObject voTableDataHist = dciterTranHist.getViewObject();
            Row row = dciterTranHist.getCurrentRow();
            String custIdHisTra =
                (String)row.getAttribute("BudgetCustomerId").toString();
            if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                row.remove();
                dciterTranHist.getDataControl().commitTransaction();
            }

            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding refreshCustTran =
                bindings.getOperationBinding("CustTranRefresh");
            refreshCustTran.execute();

            OperationBinding refreshBudgetCust =
                bindings.getOperationBinding("BudgetCustRefresh");
            refreshBudgetCust.execute();
            voTableDataHist.clearCache();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
            dcItteratorBindings.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
        }
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

    /*   public void percentValueChangeListener(ValueChangeEvent valueChangeEvent) {
        String custIdHis="";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("BudgetCustomerId") != null) {
            custIdHis=rowSelected.getAttribute("BudgetCustomerId").toString();
        }
        BigDecimal yearlyAmount=BigDecimal.ZERO;
        DCIteratorBinding dciterCustomerbudget=
            ADFUtils.findIterator("BudgetCustomerView1Iterator");
        if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
            RowSetIterator BudMainCust =
                dciterCustomerbudget.getRowSetIterator();
            for (Row budgetCusr : dciterCustomerbudget.getAllRowsInRange()) {
                String custId =
                    (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                if(custId.equalsIgnoreCase(custIdHis)){
                   yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                }
            }
        }

        DCIteratorBinding dciterTranHist =
            ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
        BigDecimal valPercentage =BigDecimal.ZERO;
        BigDecimal valAmount = BigDecimal.ZERO;

        RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
        for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
            String custIdHisTra =
                (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
            BigDecimal totalPercent =
                new BigDecimal(tranHistRow.getAttribute("Percentage").toString());
            valPercentage = valPercentage.add(totalPercent);
            BigDecimal totalAmount =
                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
            valAmount = valAmount.add(totalAmount);

        }
        BigDecimal budPercentTran = valPercentage;
        BigDecimal budAmountTran = valAmount.add(yearlyAmount);

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
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetAsToDate);
    }
  */

    public void amountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        BudgetSettingAMImpl BudgetSettingAM =
            (BudgetSettingAMImpl)ADFUtils.getApplicationModuleForDataControl("BudgetSettingAMDataControl");
        String typeBudgetHeader =
            lovTypeBudgetHdr.getValue() == null ? "" : lovTypeBudgetHdr.getValue().toString();
        String lovbudgettypetran =
            lovBudgetTypeTran.getValue() == null ? "" : lovBudgetTypeTran.getValue().toString();

        if (lovbudgettypetran.equalsIgnoreCase("BALANCE") &&
            typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            String custIdHis = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            BigDecimal yearlyAmount = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        yearlyAmount =
                                new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                    }
                }
            }
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranGetData1Iterator");
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            BudgetCustTranGetDataImpl voCustTran =
                BudgetSettingAM.getBudgetCustTranGetData1();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //    while(BudTranHist.hasNext()){
            //            System.out.println("count transaksi " +
            //                               voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                while (voCustTran.hasNext()) {
                    //    for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    Row tranHistRow = voCustTran.next();
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("BALANCE")) {
                            BigDecimal totalPercent =
                                new BigDecimal(tranHistRow.getAttribute("Percentage").toString());
                            valPercentage = valPercentage.add(totalPercent);
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //        pageFlowScope.put("totalAmount", valueChangeEvent.getNewValue());
                            //        System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            //    BudTranHist.closeRowSetIterator();
            //    System.out.println("valPercentage yang ada  "+valPercentage);
            //    System.out.println("valAmount yang ada "+valAmount);
            BigDecimal budPercentTran = valPercentage;
            BigDecimal budAmountTran = valAmount;
            //        .add(yearlyAmount);
            BigDecimal budPercent = BigDecimal.ZERO;
            String percentVal = (String)pageFlowScope.get("percentVal");
            //        System.out.println("PERCENTAGE: " + percentVal);
            if (percentVal != null) {
                budPercent =
                        new BigDecimal(percentVal == "" ? "0" : percentVal ==
                                                                null ? "0" :
                                                                percentVal.toString());
                budPercent = budPercent.add(budPercentTran);
            } else {
                budPercent = budPercentTran;
            }
            //        System.out.println("budPercent + yang ada "+budPercent);
            BigDecimal budAmount = BigDecimal.ZERO;
            BigDecimal oldAmount = BigDecimal.ZERO;
            if (budgetCustAmount.getValue() != null) {
                try {
                    oldAmount =
                            new BigDecimal(valueChangeEvent.getOldValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getOldValue().toString());
                } catch (Exception eOa) {
                    oldAmount = BigDecimal.ZERO;
                }
                try {
                    budAmount =
                            new BigDecimal(valueChangeEvent.getNewValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                } catch (Exception eBa) {
                    budAmount = BigDecimal.ZERO;
                }
                budAmount = budAmount.add(budAmountTran);
                //                .subtract(oldAmount);
            }
            //        System.out.println("budAmount tran + yang ada "+budAmount);
            //                    System.out.println("budPercent tran + yang ada "+budPercent);
            BigDecimal budyearly = BigDecimal.ZERO;
            if (budPercent.compareTo(BigDecimal.ZERO) == 0) {
                budyearly = budAmount;
            } else {
                budyearly = (budAmount.multiply(budPercent)).divide(bdHundred);
            }
            oracle.jbo.domain.Number number = null;
            try {
                number = new oracle.jbo.domain.Number(budyearly.toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            //        System.out.println("number "+number);
            budgetYearlyAmount.setValue(number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetYearlyAmount);
            voCustTran.clearCache();
        } else if (lovbudgettypetran.equalsIgnoreCase("BALANCE") &&
                   typeBudgetHeader.equalsIgnoreCase("POSTING")) {
            String custIdHis = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            BigDecimal budgetAsToDatevar = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        //                          yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        budgetAsToDatevar =
                                new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                    }
                }
            }
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranView1Iterator");
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                String BudgetTypeHisTra =
                    (String)tranHistRow.getAttribute("BudgetType").toString();
                if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                    if (BudgetTypeHisTra.equalsIgnoreCase("BALANCE")) {
                        BigDecimal totalAmount =
                            new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                        valAmount = valAmount.add(totalAmount);
                    }
                }
            }
            //            BudTranHist.closeRowSetIterator();
            //                    System.out.println("valAmount existing "+valAmount);
            BigDecimal budAmountTran = valAmount.add(budgetAsToDatevar);
            BigDecimal budAmount = BigDecimal.ZERO;
            BigDecimal oldAmount = BigDecimal.ZERO;
            if (budgetCustAmount.getValue() != null) {
                try {
                    oldAmount =
                            new BigDecimal(valueChangeEvent.getOldValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getOldValue().toString());
                } catch (Exception eOa) {
                    oldAmount = BigDecimal.ZERO;
                }

                try {
                    budAmount =
                            new BigDecimal(valueChangeEvent.getNewValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                } catch (Exception eBa) {
                    budAmount = BigDecimal.ZERO;
                }
                budAmount =
                        budAmount.add(budgetAsToDatevar).subtract(oldAmount);
            }

            //                        System.out.println("budAmount "+budAmount);
            oracle.jbo.domain.Number number = null;
            try {
                number = new oracle.jbo.domain.Number(budAmount.toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            budgetYearlyAmount.setValue(number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetYearlyAmount);

        } else if (lovbudgettypetran.equalsIgnoreCase("ADJUSTMENT") &&
                   typeBudgetHeader.equalsIgnoreCase("POSTING")) {
            String custIdHis = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            BigDecimal budgetAsToDatevar = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        //                          yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        budgetAsToDatevar =
                                new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            //              System.out.println("budgetAsToDatevar "+budgetAsToDatevar);
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranView1Iterator");
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                String BudgetTypeHisTra =
                    (String)tranHistRow.getAttribute("BudgetType").toString();
                if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                    if (BudgetTypeHisTra.equalsIgnoreCase("ADJUSTMENT")) {
                        BigDecimal totalAmount =
                            new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                        valAmount = valAmount.add(totalAmount);
                    }
                }
            }

            BigDecimal budAmountTran = valAmount;
            //        BudTranHist.closeRowSetIterator();
            //                System.out.println("budAmountTran "+budAmountTran);
            BigDecimal budAmount = BigDecimal.ZERO;
            BigDecimal oldAmount = BigDecimal.ZERO;
            if (budgetCustAmount.getValue() != null) {
                try {
                    oldAmount =
                            new BigDecimal(valueChangeEvent.getOldValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getOldValue().toString());
                } catch (Exception eOa) {
                    oldAmount = BigDecimal.ZERO;
                }

                try {
                    budAmount =
                            new BigDecimal(valueChangeEvent.getNewValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                } catch (Exception eBa) {
                    budAmount = BigDecimal.ZERO;
                }
                budAmount =
                        budAmount.add(budgetAsToDatevar).subtract(oldAmount);
            }
            oracle.jbo.domain.Number number = null;
            try {
                number = new oracle.jbo.domain.Number(budAmount.toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            budgetAsToDate.setValue(number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetAsToDate);
        } else if (lovbudgettypetran.equalsIgnoreCase("ADJUSTMENT") &&
                   typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            String custIdHis = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            BigDecimal budgetAsToDatevar = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        //                          yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        budgetAsToDatevar =
                                new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            //              System.out.println("budgetAsToDatevar "+budgetAsToDatevar);
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranView1Iterator");
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                String BudgetTypeHisTra =
                    (String)tranHistRow.getAttribute("BudgetType").toString();
                if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                    if (BudgetTypeHisTra.equalsIgnoreCase("ADJUSTMENT")) {
                        BigDecimal totalAmount =
                            new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                        valAmount = valAmount.add(totalAmount);
                    }
                }
            }

            BigDecimal budAmountTran = valAmount;
            //                System.out.println("budAmountTran "+budAmountTran);
            //        BudTranHist.closeRowSetIterator();
            BigDecimal budAmount = BigDecimal.ZERO;
            BigDecimal oldAmount = BigDecimal.ZERO;
            if (budgetCustAmount.getValue() != null) {
                try {
                    oldAmount =
                            new BigDecimal(valueChangeEvent.getOldValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getOldValue().toString());
                } catch (Exception eOa) {
                    oldAmount = BigDecimal.ZERO;
                }

                try {
                    budAmount =
                            new BigDecimal(valueChangeEvent.getNewValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                } catch (Exception eBa) {
                    budAmount = BigDecimal.ZERO;
                }
                budAmount =
                        budAmount.add(budgetAsToDatevar).subtract(oldAmount);
            }
            oracle.jbo.domain.Number number = null;
            try {
                number = new oracle.jbo.domain.Number(budAmount.toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            budgetAsToDate.setValue(number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetAsToDate);
        }
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
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            !getItlovBudClass().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudBrand().setValue(null);
            this.getItlovBudExtention().setValue(null);
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }

        if (getItlovBudClass().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudBrand().setValue("ALL");
            this.getItlovBudExtention().setValue("ALL");
            this.getItlovBudPackaging().setValue("ALL");
            this.getItlovBudVariant().setValue("ALL");
        }
    }

    public void productBrandChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            !getItlovBudBrand().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudExtention().setValue(null);
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }

        if (getItlovBudBrand().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudExtention().setValue("ALL");
            this.getItlovBudPackaging().setValue("ALL");
            this.getItlovBudVariant().setValue("ALL");
        }
    }

    public void productExtentionChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            !getItlovBudExtention().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudPackaging().setValue(null);
            this.getItlovBudVariant().setValue(null);
        }

        if (getItlovBudExtention().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudPackaging().setValue("ALL");
            this.getItlovBudVariant().setValue("ALL");
        }
    }

    public void productPackagingChanged(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            !getItlovBudPackaging().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudVariant().setValue(null);
        }

        if (getItlovBudPackaging().getValue().toString().equalsIgnoreCase("ALL")) {
            this.getItlovBudVariant().setValue("ALL");
        }
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

    public void setTblBudgetCustomer(RichTable tblBudgetCustomer) {
        this.tblBudgetCustomer = tblBudgetCustomer;
    }

    public RichTable getTblBudgetCustomer() {
        return tblBudgetCustomer;
    }

    public void setItBudCustId(RichInputText itBudCustId) {
        this.itBudCustId = itBudCustId;
    }

    public RichInputText getItBudCustId() {
        return itBudCustId;
    }

    public void showPopupBudTran(ActionEvent actionEvent) {
        String custIdHis = "";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        if (rowSelected.getAttribute("BudgetCustomerId") != null) {
            custIdHis =
                    rowSelected.getAttribute("BudgetCustomerId").toString();
        }
        String budgetType = "";
        DCIteratorBinding dciterTranHist =
            ADFUtils.findIterator("BudgetCustTranHistoryView1Iterator");
        RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
        for (Row tranHistRow : dciterTranHist.getAllRowsInRange()) {
            String custIdHisTra =
                (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
            if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                ADFContext adfCtx = ADFContext.getCurrent();
                Map pageFlowScope = adfCtx.getPageFlowScope();
                pageFlowScope.put("custID", custIdHis);
                otValue.setValue(custIdHis);
                AdfFacesContext.getCurrentInstance().addPartialTarget(otValue);
                budgetType = tranHistRow.getAttribute("BudgetType").toString();
            }
        }
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding =
            bindings1.getOperationBinding("CreateInsertBudCustTran");
        operationBinding.execute();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        pbudCusTran.show(hints);
    }

    public void setPbudCusTran(RichPopup pbudCusTran) {
        this.pbudCusTran = pbudCusTran;
    }

    public RichPopup getPbudCusTran() {
        return pbudCusTran;
    }


    public void setOtValue(RichOutputText otValue) {
        this.otValue = otValue;
    }

    public RichOutputText getOtValue() {
        return otValue;
    }

    public void setPtranHistory(RichPopup ptranHistory) {
        this.ptranHistory = ptranHistory;
    }

    public RichPopup getPtranHistory() {
        return ptranHistory;
    }

    public void pTranHistoryEvent(ActionEvent actionEvent) {
        String custIdHis = "";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding dcIterBudgetHeader =
            bindings.findIteratorBinding("BudgetCustHdrView1Iterator");
        Key headerKey = dcIterBudgetHeader.getCurrentRow().getKey();

        DCIteratorBinding dcIterBudgetCust =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcIterBudgetCust.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        Key budgetCustKey = dcIterBudgetCust.getCurrentRow().getKey();

        if (voTableData.getEstimatedRowCount() > 0) {
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }

            if (!custIdHis.equalsIgnoreCase(null)) {
                DCIteratorBinding dcItteratorBindingsHist =
                    bindings.findIteratorBinding("BudgetCustTranHistoryView1Iterator");
                ViewObject vo = dcItteratorBindingsHist.getViewObject();
                vo.setNamedWhereClauseParam("CustId", custIdHis);
                vo.executeQuery();

                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                ptranHistory.show(hints);

                dcIterBudgetHeader.setCurrentRowWithKey(headerKey.toStringFormat(true));
                dcIterBudgetCust.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
            } else {
                JSFUtils.addFacesWarningMessage("Row Belum Dipilih");
            }
        }
    }

    public void closeTranDialogEvent(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            /* DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindingsHist =
                bindings.findIteratorBinding("BudgetCustTranHistoryView1Iterator");
            RowSetIterator BudTranHist =
                dcItteratorBindingsHist.getRowSetIterator();
            BudTranHist.closeRowSetIterator();

            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            */
            ptranHistory.hide();
        }
    }

    public void setItPercentageTemp(RichInputText itPercentageTemp) {
        this.itPercentageTemp = itPercentageTemp;
    }

    public RichInputText getItPercentageTemp() {
        return itPercentageTemp;
    }

    public void setBudgetYearlyAmount(RichInputText budgetYearlyAmount) {
        this.budgetYearlyAmount = budgetYearlyAmount;
    }

    public RichInputText getBudgetYearlyAmount() {
        return budgetYearlyAmount;
    }

    public void setLovTypeBudgetHdr(RichInputListOfValues lovTypeBudgetHdr) {
        this.lovTypeBudgetHdr = lovTypeBudgetHdr;
    }

    public RichInputListOfValues getLovTypeBudgetHdr() {
        return lovTypeBudgetHdr;
    }

    public void setItTradingTerm(RichPanelGroupLayout itTradingTerm) {
        this.itTradingTerm = itTradingTerm;
    }


    public void setPbalance(RichPopup pbalance) {
        this.pbalance = pbalance;
    }

    public RichPopup getPbalance() {
        return pbalance;
    }

    public void BudgetTypeIfEvent(ValueChangeEvent valueChangeEvent) {
        String typeBudgetHeader =
            lovTypeBudgetHdr.getValue() == null ? "" : lovTypeBudgetHdr.getValue().toString();
        String lovbudgettypetran =
            lovBudgetTypeTran.getValue() == null ? "" : lovBudgetTypeTran.getValue().toString();
        if (lovbudgettypetran.equalsIgnoreCase("ADJUSTMENT") &&
            typeBudgetHeader.equalsIgnoreCase("POSTING")) {
            String custIdHis = otValue.getValue().toString();
            BigDecimal budgetAsToDatevar = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        //                          yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        budgetAsToDatevar =
                                new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            budgetAsToDate.setValue(budgetAsToDatevar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetAsToDate);
        } else if (lovbudgettypetran.equalsIgnoreCase("ADJUSTMENT") &&
                   typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            String custIdHis = otValue.getValue().toString();
            BigDecimal budgetAsToDatevar = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        //                          yearlyAmount= new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        budgetAsToDatevar =
                                new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            budgetAsToDate.setValue(budgetAsToDatevar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetAsToDate);
        } else if (lovbudgettypetran.equalsIgnoreCase("BALANCE") &&
                   typeBudgetHeader.equalsIgnoreCase("POSTING")) {
            String custIdHis = otValue.getValue().toString();
            BigDecimal yearlyAmount = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        yearlyAmount =
                                new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        //                       budgetAsToDatevar = new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            budgetYearlyAmount.setValue(yearlyAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetYearlyAmount);
        } else if (lovbudgettypetran.equalsIgnoreCase("BALANCE") &&
                   typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            String custIdHis = otValue.getValue().toString();
            BigDecimal yearlyAmount = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        yearlyAmount =
                                new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                        //                       budgetAsToDatevar = new BigDecimal(budgetCusr.getAttribute("BudgetAsToDate").toString());
                    }
                }
            }
            budgetYearlyAmount.setValue(yearlyAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetYearlyAmount);
        }
    }

    public void setLovBudgetTypeTran(RichInputListOfValues lovBudgetTypeTran) {
        this.lovBudgetTypeTran = lovBudgetTypeTran;
    }

    public RichInputListOfValues getLovBudgetTypeTran() {
        return lovBudgetTypeTran;
    }

    public void addNewRowBudCustomer(ActionEvent actionEvent) {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter =
            (DCIteratorBinding)bindings.get("BudgetCustomerView1Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        BudgetCustomerViewRowImpl lastRow =
            (BudgetCustomerViewRowImpl)rsi.last();
        if (dciter.getEstimatedRowCount() > 0) {
            if (lastRow.getCheckRowStatus() != 0) {
                String budCategory = lastRow.getBudgetCategory();
                String budClass = lastRow.getBudgetClass();
                String budBrand = lastRow.getBudgetBrand();
                String budExtention = lastRow.getBudgetExtention();
                String budPackaging = lastRow.getBudgetPackaging();
                String budVariant = lastRow.getBudgetVariant();

                if (budCategory == null || budClass == null ||
                    budBrand == null || budExtention == null ||
                    budPackaging == null || budVariant == null) {
                    JSFUtils.addFacesWarningMessage("Mohon dilengkapi data pada kolom kombinasi produk terlebih dahulu.");
                } else {
                    int lastRowIndex = rsi.getRangeIndexOf(lastRow);
                    Row newRow = rsi.createRow();
                    newRow.setNewRowState(Row.STATUS_INITIALIZED);
                    //add row to last index + 1 so it becomes last in the range set
                    rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
                    //make row the current row so it is displayed correctly
                    rsi.setCurrentRow(newRow);
                }
            } else {
                JSFUtils.addFacesWarningMessage("Mohon dilengkapi data pada kolom kombinasi produk terlebih dahulu.");
            }
        } else {
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            //add row to last index + 1 so it becomes last in the range set
            rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            //make row the current row so it is displayed correctly
            rsi.setCurrentRow(newRow);
        }
    }

    public void setPanelTradingTerm(RichPanelLabelAndMessage panelTradingTerm) {
        this.panelTradingTerm = panelTradingTerm;
    }

    public RichPanelLabelAndMessage getPanelTradingTerm() {
        return panelTradingTerm;
    }

    public void setOtPercentage(RichOutputText otPercentage) {
        this.otPercentage = otPercentage;
    }

    public RichOutputText getOtPercentage() {
        return otPercentage;
    }

    public void tradingTermValueChangeListener(ValueChangeEvent valueChangeEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        pageFlowScope.put("percentVal", valueChangeEvent.getNewValue());

        BudgetSettingAMImpl BudgetSettingAM =
            (BudgetSettingAMImpl)ADFUtils.getApplicationModuleForDataControl("BudgetSettingAMDataControl");

        String typeBudgetHeader =
            lovTypeBudgetHdr.getValue() == null ? "" : lovTypeBudgetHdr.getValue().toString();
        String lovbudgettypetran =
            lovBudgetTypeTran.getValue() == null ? "" : lovBudgetTypeTran.getValue().toString();

        if (lovbudgettypetran.equalsIgnoreCase("BALANCE") &&
            typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            String custIdHis = "";
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings =
                bindings.findIteratorBinding("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcItteratorBindings.getViewObject();
            // Get selected row
            Row rowSelected = voTableData.getCurrentRow();
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }
            BigDecimal yearlyAmount = BigDecimal.ZERO;
            DCIteratorBinding dciterCustomerbudget =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                RowSetIterator BudMainCust =
                    dciterCustomerbudget.getRowSetIterator();
                for (Row budgetCusr :
                     dciterCustomerbudget.getAllRowsInRange()) {
                    String custId =
                        (String)budgetCusr.getAttribute("BudgetCustomerId").toString();
                    if (custId.equalsIgnoreCase(custIdHis)) {
                        yearlyAmount =
                                new BigDecimal(budgetCusr.getAttribute("YearlyBudgetAmount").toString());
                    }
                }
            }
            DCIteratorBinding dciterTranHist =
                ADFUtils.findIterator("BudgetCustTranGetData1Iterator");
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            BudgetCustTranGetDataImpl voCustTran =
                BudgetSettingAM.getBudgetCustTranGetData1();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //    while(BudTranHist.hasNext()){
            //        System.out.println("count transaksi "+voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                while (voCustTran.hasNext()) {
                    //    for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    Row tranHistRow = voCustTran.next();
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("BALANCE")) {
                            BigDecimal totalPercent =
                                new BigDecimal(tranHistRow.getAttribute("Percentage").toString());
                            valPercentage = valPercentage.add(totalPercent);
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //        pageFlowScope.put("totalAmount", valueChangeEvent.getNewValue());
                            //            System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            //    BudTranHist.closeRowSetIterator();
            //        System.out.println("valPercentage yang ada  "+valPercentage);
            //        System.out.println("valAmount yang ada "+valAmount);
            BigDecimal budPercentTran = valPercentage;
            BigDecimal budAmountTran = valAmount;
            //        .add(yearlyAmount);
            BigDecimal budPercent = BigDecimal.ZERO;
            String percentVal = (String)pageFlowScope.get("percentVal");
            //            System.out.println("PERCENTAGE: " + percentVal);
            if (percentVal != null) {
                budPercent =
                        new BigDecimal(percentVal == "" ? "0" : percentVal ==
                                                                null ? "0" :
                                                                percentVal.toString());
                budPercent = budPercent.add(budPercentTran);
            } else {
                budPercent = budPercentTran;
            }
            //        System.out.println("budPercent + yang ada "+budPercent);
            BigDecimal budAmount = BigDecimal.ZERO;
            BigDecimal oldAmount = BigDecimal.ZERO;
            if (budgetCustAmount.getValue() != null) {
                try {
                    oldAmount =
                            new BigDecimal(valueChangeEvent.getOldValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getOldValue().toString());
                } catch (Exception eOa) {
                    oldAmount = BigDecimal.ZERO;
                }
                try {
                    budAmount =
                            new BigDecimal(valueChangeEvent.getNewValue().toString() ==
                                           "" ? "0" :
                                           valueChangeEvent.getNewValue().toString());
                } catch (Exception eBa) {
                    budAmount = BigDecimal.ZERO;
                }
                budAmount = budAmount.add(budAmountTran).subtract(oldAmount);
                //                .subtract(oldAmount);
            }
            //            System.out.println("budAmount tran + yang ada "+budAmount);
            //                        System.out.println("budPercent tran + yang ada persen event"+budPercent);
            BigDecimal budyearly = BigDecimal.ZERO;
            if (valAmount.compareTo(BigDecimal.ZERO) == 0) {
                budyearly = budPercent.divide(bdHundred);
            } else {
                budyearly = (valAmount.multiply(budPercent)).divide(bdHundred);
            }

            oracle.jbo.domain.Number number = null;
            try {
                number = new oracle.jbo.domain.Number(budyearly.toString());
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Error",
                                              e.getLocalizedMessage());
            }
            //            System.out.println("number "+number);
            budgetYearlyAmount.setValue(number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetYearlyAmount);
            voCustTran.clearCache();
        }
    }

    public void removeRowBudCustomerDialogListener(DialogEvent dialogEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();

        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        rowSelected.remove();

        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
        AdfFacesContext.getCurrentInstance().addPartialTarget(btnCancel);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itLovCustIdHdr);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itLovKodePostingHdr);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itLovBudgetYearHdr);
    }

    public void cancelAll(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding dcIterBudgetHeader =
            bindings.findIteratorBinding("BudgetCustHdrView1Iterator");

        ViewObject voBudgetHeader = dcIterBudgetHeader.getViewObject();
        // Get selected row budget header
        Row rowHeaderSelected = voBudgetHeader.getCurrentRow();
        Key headerKey = dcIterBudgetHeader.getCurrentRow().getKey();
        Integer rowStatusBudgetHeader =
            (Integer)rowHeaderSelected.getAttribute("CheckRowStatus");

        DCIteratorBinding dcIterBudgetCust =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");

        OperationBinding operationBindingRollback =
            bindings.getOperationBinding("Rollback");
        operationBindingRollback.execute();

        if (rowStatusBudgetHeader != 0) {
            dcIterBudgetHeader.setCurrentRowWithKey(headerKey.toStringFormat(true));

            if (dcIterBudgetCust.getEstimatedRowCount() > 0) {
                ViewObject voTableData = dcIterBudgetCust.getViewObject();
                // Get selected row budget cust
                Row rowSelected = voTableData.getCurrentRow();
                Key budgetCustKey = dcIterBudgetCust.getCurrentRow().getKey();
                Integer rowStatusBudgetCust =
                    (Integer)rowSelected.getAttribute("CheckRowStatus");

                if (rowStatusBudgetCust != 0) {
                    dcIterBudgetCust.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
                }
            }
        }
    }

    public void setSwitchMain(UIXSwitcher switchMain) {
        this.switchMain = switchMain;
    }

    public UIXSwitcher getSwitchMain() {
        return switchMain;
    }

    public void budgetQueryListener(QueryEvent queryEvent) {
        String budgetTypeVal = null;
        String budgetYearVal = null;
        QueryDescriptor qd = queryEvent.getDescriptor();
        ConjunctionCriterion conCrit = qd.getConjunctionCriterion();
        List<Criterion> criterionList = conCrit.getCriterionList();

        for (Criterion criterion : criterionList) {
            AttributeDescriptor attrDescriptor =
                ((AttributeCriterion)criterion).getAttribute();
            String name = attrDescriptor.getName();
            Object value = ((AttributeCriterion)criterion).getValues().get(0);
            
            if (name.equalsIgnoreCase("BudgetType") && value != null) {
                budgetTypeVal = value.toString();
            } else {
                if (budgetTypeVal == null) {
                    budgetTypeVal = "NOT VALID";
                }
            }
            
            if (name.equalsIgnoreCase("BudgetYear") && value != null) {
                budgetYearVal = value.toString();
            }
        }
        
        if (budgetTypeVal.equalsIgnoreCase("CUSTOMER") ||
            budgetTypeVal.equalsIgnoreCase("POSTING")) {
            //Execute query
            ADFUtils.invokeEL("#{bindings.BudgetCustHdrViewCriteriaQuery.processQuery}",
                              new Class[] { QueryEvent.class },
                              new Object[] { queryEvent });

            //Put Budget Type to page flow scope
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("budgetType", budgetTypeVal);
            pageFlowScope.put("budgetYear", budgetYearVal);
            /*
            //Enable button add and remove
            btnBudgetAdd.setDisabled(false);
            btnBudgetDelete.setDisabled(false);
            btnBudgetRefresh.setDisabled(false);
            */
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetAdd);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetDelete);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetRefresh);
        } else {
            //Put null Budget Type to page flow scope
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("budgetType", null);
            pageFlowScope.put("budgetYear", null);
            /*
            //Disable button add and remove
            btnBudgetAdd.setDisabled(true);
            btnBudgetDelete.setDisabled(true);
            btnBudgetRefresh.setDisabled(true);
            */
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetAdd);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetDelete);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetRefresh);
        }
    }

    public void budgetQueryOperationList(QueryOperationEvent queryOperationEvent) {
        //Retrive Budget Type from pageflowscope
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        String budgetType = (String)pageFlowScope.get("budgetType");
        String budgetYear = (String)pageFlowScope.get("budgetYear");
        
        DCIteratorBinding iter =
            (DCIteratorBinding)getBindings().get("BudgetCustHdrView1Iterator");
        ViewObjectImpl vo = (ViewObjectImpl)iter.getViewObject();
        
        if (queryOperationEvent.getOperation().name().equalsIgnoreCase("RESET")) {
            if (budgetType == null) {
                vo.ensureVariableManager().setVariableValue("budgetType",
                                                            dummyResetValue);
                vo.ensureVariableManager().setVariableValue("budgetYear",
                                                            dummyResetValue);
            } else {
                vo.ensureVariableManager().setVariableValue("budgetType",
                                                            budgetType);
                vo.ensureVariableManager().setVariableValue("budgetYear",
                                                            budgetYear);
            }
            //vo.executeQuery();
            /*
            if (budgetType == null && budgetYear == null) {
                //Disable button add and remove
                btnBudgetAdd.setDisabled(true);
                btnBudgetDelete.setDisabled(true);
                btnBudgetRefresh.setDisabled(true);
            } else {
                //Enable button add and remove
                btnBudgetAdd.setDisabled(false);
                btnBudgetDelete.setDisabled(false);
                btnBudgetRefresh.setDisabled(false);
            }
            */
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetAdd);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetDelete);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetRefresh);
        } else {
            boolean  canCreateNew = true;
            BindingContext bctx = BindingContext.getCurrent();
            DCBindingContainer binding =
                (DCBindingContainer)bctx.getCurrentBindingsEntry();
            DCIteratorBinding budgetCustIter =
                (DCIteratorBinding)binding.get("BudgetCustHdrView1Iterator");
            for (Row budgetCustHdrRow : budgetCustIter.getAllRowsInRange()) {
                Integer rowStatus =
                    (Integer)budgetCustHdrRow.getAttribute("CheckRowStatus");
                if (rowStatus == 0) {
                    canCreateNew = false;
                }
            }

            if (!canCreateNew) {
                vo.ensureVariableManager().setVariableValue("budgetType", budgetType);
                vo.ensureVariableManager().setVariableValue("budgetYear", budgetType);

                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Masih ada proses input setup budget yang masih belum selesai.</p>");
                message.append("<p>Mohon dilanjutkan terlebih dahulu atau di-cancel.</p>");
                message.append("</body></html>");
                JSFUtils.addFacesWarningMessage(message.toString());
            } else {
                //Invoke default operation listener
                ADFUtils.invokeEL("#{bindings.BudgetCustHdrViewCriteriaQuery.processQueryOperation}",
                                  new Class[] { QueryOperationEvent.class },
                                  new Object[] { queryOperationEvent });
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetAdd);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetDelete);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetRefresh);
        }
    }

    public void setBtnBudgetDelete(RichCommandToolbarButton btnBudgetDelete) {
        this.btnBudgetDelete = btnBudgetDelete;
    }

    public RichCommandToolbarButton getBtnBudgetDelete() {
        return btnBudgetDelete;
    }

    public void setBtnBudgetAdd(RichCommandToolbarButton btnBudgetAdd) {
        this.btnBudgetAdd = btnBudgetAdd;
    }

    public RichCommandToolbarButton getBtnBudgetAdd() {
        return btnBudgetAdd;
    }

    public void setBtnBudgetRefresh(RichCommandToolbarButton btnBudgetRefresh) {
        this.btnBudgetRefresh = btnBudgetRefresh;
    }

    public RichCommandToolbarButton getBtnBudgetRefresh() {
        return btnBudgetRefresh;
    }

    public void addBudgetSetup(ActionEvent actionEvent) {
        boolean canCreateNew = true;
        //Retrive Budget Type from pageflowscope
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        String budgetType = (String)pageFlowScope.get("budgetType");
        String budgetYear = (String)pageFlowScope.get("budgetYear");

        BindingContext bctx = BindingContext.getCurrent();
        DCBindingContainer binding =
            (DCBindingContainer)bctx.getCurrentBindingsEntry();
        DCIteratorBinding budgetCustIter =
            (DCIteratorBinding)binding.get("BudgetCustHdrView1Iterator");
        for (Row budgetCustHdrRow : budgetCustIter.getAllRowsInRange()) {
            Integer rowStatus =
                (Integer)budgetCustHdrRow.getAttribute("CheckRowStatus");
            if (rowStatus == 0) {
                canCreateNew = false;
            }
        }

        if (canCreateNew) {
            Row row = budgetCustIter.getNavigatableRowIterator().createRow();
            row.setNewRowState(Row.STATUS_INITIALIZED);
            row.setAttribute("BudgetType", budgetType);
            row.setAttribute("BudgetYear", budgetYear);
            //budgetCustIter.getRowSetIterator().insertRow(row);
            budgetCustIter.getNavigatableRowIterator().insertRowAtRangeIndex(0,
                                                                             row);
            budgetCustIter.setCurrentRowWithKey(row.getKey().toStringFormat(true));

            AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustHdr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(butCancelSaveGroup);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetRefresh);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnBudgetDelete);
        } else {
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Masih ada setup budget yang belum diselesaikan.</p>");
            message.append("<p>Proses penambahan setup budget baru tidak dapat dilanjutkan.</p>");
            message.append("</body></html>");
            JSFUtils.addFacesErrorMessage(message.toString());
        }
    }

    public void setTblBudgetCustHdr(RichTable tblBudgetCustHdr) {
        this.tblBudgetCustHdr = tblBudgetCustHdr;
    }

    public RichTable getTblBudgetCustHdr() {
        return tblBudgetCustHdr;
    }

    public void setButCancelSaveGroup(RichPanelGroupLayout butCancelSaveGroup) {
        this.butCancelSaveGroup = butCancelSaveGroup;
    }

    public RichPanelGroupLayout getButCancelSaveGroup() {
        return butCancelSaveGroup;
    }

    /**
     * @param actionEvent
     */
    public void removeBudgetSetup(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItteratorBindings =
            bindings.findIteratorBinding("BudgetCustHdrView1Iterator");

        ViewObject voTableData = dcItteratorBindings.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        String budgetName =
            (String)rowSelected.getAttribute("CustomerDesc") == null ? "-" :
            (String)rowSelected.getAttribute("CustomerDesc");
        String budgetYear =
            (String)rowSelected.getAttribute("BudgetYear") == null ? "-" :
            (String)rowSelected.getAttribute("BudgetYear");

        try {
            rowSelected.remove();
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();
        } catch (RemoveWithDetailsException re) {
            JSFUtils.addFacesErrorMessage("Error",
                                          "Setup budget \"" + budgetName +
                                          " [" + budgetYear +
                                          "]\" masih memiliki kombinasi produk, proses tidak dapat dilanjutkan.");
        } catch (Exception e) {
            JSFUtils.addFacesErrorMessage("Error", e.getLocalizedMessage());
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
    }

    public void saveAllBudget(ActionEvent actionEvent) {
        BudgetSettingAMImpl budgetSettingAM =
            (BudgetSettingAMImpl)ADFUtils.getApplicationModuleForDataControl("BudgetSettingAMDataControl");

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter =
            (DCIteratorBinding)getBindings().get("BudgetCustHdrView1Iterator");

        BudgetCustHdrViewRowImpl currRow =
            (BudgetCustHdrViewRowImpl)iter.getCurrentRow();
        String custId = currRow.getCustomerId();
        String custDesc = currRow.getCustomerDesc();
        String kodePosting = currRow.getKodePosting();
        String budgetType = currRow.getBudgetType();
        String budgetYear = currRow.getBudgetYear();
        Integer rowStatus = currRow.getCheckRowStatus();

        CheckDuplicateBudgetViewImpl vo =
            budgetSettingAM.getCheckDuplicateBudgetView1();

        if (budgetType.equalsIgnoreCase("CUSTOMER")) {
            vo.setWhereClause("BudgetCustHdr.CUSTOMER_ID = '" + custId +
                              "' AND BudgetCustHdr.BUDGET_TYPE = '" +
                              budgetType +
                              "' AND BudgetCustHdr.BUDGET_YEAR = '" +
                              budgetYear + "'");
            vo.executeQuery();

            if (vo.getEstimatedRowCount() > 0 && rowStatus == 0) {
                try {
                    OperationBinding operationBindingCommit =
                        bindings.getOperationBinding("Rollback");
                    operationBindingCommit.execute();
                } catch (Exception e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }

                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Budget untuk \"" + custDesc + " [" +
                               budgetYear + "]\" sudah ada sebelumnya.</p>");
                message.append("<p>Proses penambahan budget tidak dapat dilanjutkan</p>");
                message.append("</body></html>");
                JSFUtils.addFacesErrorMessage(message.toString());
            } else {
                //Check jika ada kombinasi yang belum lengkap
                DCIteratorBinding dciterCustomerbudget =
                    ADFUtils.findIterator("BudgetCustomerView1Iterator");
                if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                    for (Row budgetCust :
                         dciterCustomerbudget.getAllRowsInRange()) {
                        String budgetCategory =
                            (String)budgetCust.getAttribute("BudgetCategory") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetCategory");
                        String budgetClass =
                            (String)budgetCust.getAttribute("BudgetClass") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetClass");
                        String budgetBrand =
                            (String)budgetCust.getAttribute("BudgetBrand") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetBrand");
                        String budgetExtention =
                            (String)budgetCust.getAttribute("BudgetExtention") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetExtention");
                        String budgetPackaging =
                            (String)budgetCust.getAttribute("BudgetPackaging") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetPackaging");
                        String budgetVariant =
                            (String)budgetCust.getAttribute("BudgetVariant") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetVariant");

                        if (budgetCategory.equalsIgnoreCase("") ||
                            budgetClass.equalsIgnoreCase("") ||
                            budgetBrand.equalsIgnoreCase("") ||
                            budgetExtention.equalsIgnoreCase("") ||
                            budgetPackaging.equalsIgnoreCase("") ||
                            budgetVariant.equalsIgnoreCase("")) {
                            JSFUtils.addFacesErrorMessage("Error",
                                                          "Kombinasi produk belum lengkap terisi. Tidak dapat di save.");
                        } else {
                            try {
                                OperationBinding operationBindingCommit =
                                    bindings.getOperationBinding("Commit");
                                operationBindingCommit.execute();
                            } catch (Exception e) {
                                JSFUtils.addFacesErrorMessage("Error",
                                                              e.getLocalizedMessage());
                            }
                        }
                    }
                } else {
                    try {
                        OperationBinding operationBindingCommit =
                            bindings.getOperationBinding("Commit");
                        operationBindingCommit.execute();
                    } catch (Exception e) {
                        JSFUtils.addFacesErrorMessage("Error",
                                                      e.getLocalizedMessage());
                    }
                }
                iter.setCurrentRowWithKey(currRow.getKey().toStringFormat(true));
            }
        } else if (budgetType.equalsIgnoreCase("POSTING")) {
            vo.setWhereClause("BudgetCustHdr.KODE_POSTING = '" + kodePosting +
                              "' AND BudgetCustHdr.BUDGET_TYPE = '" +
                              budgetType +
                              "' AND BudgetCustHdr.BUDGET_YEAR = '" +
                              budgetYear + "'");
            vo.executeQuery();

            if (vo.getEstimatedRowCount() > 0 && rowStatus == 0) {
                try {
                    OperationBinding operationBindingCommit =
                        bindings.getOperationBinding("Rollback");
                    operationBindingCommit.execute();
                } catch (Exception e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }

                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Budget untuk \"" + kodePosting + " [" +
                               budgetYear + "]\" sudah ada sebelumnya.</p>");
                message.append("<p>Proses penambahan budget tidak dapat dilanjutkan.</p>");
                message.append("</body></html>");
                JSFUtils.addFacesErrorMessage(message.toString());
            } else {
                //Check jika ada kombinasi yang belum lengkap
                DCIteratorBinding dciterCustomerbudget =
                    ADFUtils.findIterator("BudgetCustomerView1Iterator");
                if (dciterCustomerbudget.getEstimatedRowCount() > 0) {
                    for (Row budgetCust :
                         dciterCustomerbudget.getAllRowsInRange()) {
                        String budgetCategory =
                            (String)budgetCust.getAttribute("BudgetCategory") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetCategory");
                        String budgetClass =
                            (String)budgetCust.getAttribute("BudgetClass") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetClass");
                        String budgetBrand =
                            (String)budgetCust.getAttribute("BudgetBrand") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetBrand");
                        String budgetExtention =
                            (String)budgetCust.getAttribute("BudgetExtention") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetExtention");
                        String budgetPackaging =
                            (String)budgetCust.getAttribute("BudgetPackaging") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetPackaging");
                        String budgetVariant =
                            (String)budgetCust.getAttribute("BudgetVariant") ==
                            null ? "" :
                            (String)budgetCust.getAttribute("BudgetVariant");

                        if (budgetCategory.equalsIgnoreCase("") ||
                            budgetClass.equalsIgnoreCase("") ||
                            budgetBrand.equalsIgnoreCase("") ||
                            budgetExtention.equalsIgnoreCase("") ||
                            budgetPackaging.equalsIgnoreCase("") ||
                            budgetVariant.equalsIgnoreCase("")) {
                            JSFUtils.addFacesErrorMessage("Error",
                                                          "Kombinasi produk belum lengkap terisi. Tidak dapat di save.");
                        } else {
                            try {
                                OperationBinding operationBindingCommit =
                                    bindings.getOperationBinding("Commit");
                                operationBindingCommit.execute();
                            } catch (Exception e) {
                                JSFUtils.addFacesErrorMessage("Error",
                                                              e.getLocalizedMessage());
                            }
                        }
                    }
                } else {
                    try {
                        OperationBinding operationBindingCommit =
                            bindings.getOperationBinding("Commit");
                        operationBindingCommit.execute();
                    } catch (Exception e) {
                        JSFUtils.addFacesErrorMessage("Error",
                                                      e.getLocalizedMessage());
                    }
                }
                iter.setCurrentRowWithKey(currRow.getKey().toStringFormat(true));
            }
        } else {
            JSFUtils.addFacesErrorMessage("\"Budget Type\" tidak dikenali, proses tidak dapat dilanjutkan.");
        }
    }

    public void setBtnCancel(RichCommandButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public RichCommandButton getBtnCancel() {
        return btnCancel;
    }

    public void setItLovCustIdHdr(RichInputListOfValues itLovCustIdHdr) {
        this.itLovCustIdHdr = itLovCustIdHdr;
    }

    public RichInputListOfValues getItLovCustIdHdr() {
        return itLovCustIdHdr;
    }

    public void setItLovKodePostingHdr(RichInputListOfValues itLovKodePostingHdr) {
        this.itLovKodePostingHdr = itLovKodePostingHdr;
    }

    public RichInputListOfValues getItLovKodePostingHdr() {
        return itLovKodePostingHdr;
    }

    public void setItLovBudgetYearHdr(RichInputListOfValues itLovBudgetYearHdr) {
        this.itLovBudgetYearHdr = itLovBudgetYearHdr;
    }

    public RichInputListOfValues getItLovBudgetYearHdr() {
        return itLovBudgetYearHdr;
    }

    public void setPtranApproval(RichPopup ptranApproval) {
        this.ptranApproval = ptranApproval;
    }

    public RichPopup getPtranApproval() {
        return ptranApproval;
    }

    public void closeTranApprovalDialogEvent(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ptranApproval.hide();
            
            DCIteratorBinding dcIterBudgetCustHdr =
                ADFUtils.findIterator("BudgetCustHdrView1Iterator");
            ViewObject voTableDataHdr = dcIterBudgetCustHdr.getViewObject();
            // Get selected row cust
            Row rowSelCustHdr = voTableDataHdr.getCurrentRow();
            Key budgetCustHdrKey = dcIterBudgetCustHdr.getCurrentRow().getKey();

            DCIteratorBinding dcIterBudgetCust =
                ADFUtils.findIterator("BudgetCustomerView1Iterator");
            ViewObject voTableData = dcIterBudgetCust.getViewObject();
            // Get selected row cust
            Row rowSelCust = voTableData.getCurrentRow();
            Key budgetCustKey = dcIterBudgetCust.getCurrentRow().getKey();
            
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Execute");
            operationBindingCommit.execute();

            dcIterBudgetCustHdr.setCurrentRowWithKey(budgetCustHdrKey.toStringFormat(true));
            dcIterBudgetCust.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnTranHistory);
            AdfFacesContext.getCurrentInstance().addPartialTarget(btnTranApproval);
        }
    }

    public void pTranApprovalEvent(ActionEvent actionEvent) {
        String custIdHis = "";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding dcIterBudgetHeader =
            bindings.findIteratorBinding("BudgetCustHdrView1Iterator");
        Key headerKey = dcIterBudgetHeader.getCurrentRow().getKey();

        DCIteratorBinding dcIterBudgetCust =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcIterBudgetCust.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        Key budgetCustKey = dcIterBudgetCust.getCurrentRow().getKey();

        if (voTableData.getEstimatedRowCount() > 0) {
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
            }

            if (!custIdHis.equalsIgnoreCase(null)) {
                DCIteratorBinding dcItteratorBindingsHist =
                    bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
                ViewObject vo = dcItteratorBindingsHist.getViewObject();
                vo.setNamedWhereClauseParam("CustId", custIdHis);
                vo.executeQuery();

                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                ptranApproval.show(hints);

                dcIterBudgetHeader.setCurrentRowWithKey(headerKey.toStringFormat(true));
                dcIterBudgetCust.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
            } else {
                JSFUtils.addFacesWarningMessage("Row Belum Dipilih");
            }
        }
    }

    public void btnSetBudgetEvent(ActionEvent actionEvent) {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userName = userData.getUserNameLogin();

        BudgetSettingAMImpl BudgetSettingAM =
            (BudgetSettingAMImpl)ADFUtils.getApplicationModuleForDataControl("BudgetSettingAMDataControl");
        String typeBudgetHeader =
            lovTypeBudgetHdr.getValue() == null ? "" : lovTypeBudgetHdr.getValue().toString();
        String custIdHis = "";
        BigDecimal yearlyBudgetAmount = BigDecimal.ZERO;
        BigDecimal yearlyBudgetAsToDate = BigDecimal.ZERO;
        BigDecimal yearlyBudgetAsToDateUsed = BigDecimal.ZERO;
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding dcIterBudgetCustHdr =
            bindings.findIteratorBinding("BudgetCustHdrView1Iterator");
        DCIteratorBinding dcIterBudgetCust =
            bindings.findIteratorBinding("BudgetCustomerView1Iterator");
        DCIteratorBinding dciterTranHist =
            bindings.findIteratorBinding("BudgetCustTranHistoryView1Iterator");
        DCIteratorBinding dciterTranHistComm =
            ADFUtils.findIterator("BudgetCustomerView1Iterator");
        
        ViewObject voTableData = dcIterBudgetCust.getViewObject();
        Row rowSelected = voTableData.getCurrentRow();
        Key budgetCustKey = dcIterBudgetCust.getCurrentRow().getKey();

        if (voTableData.getEstimatedRowCount() > 0) {
            if (rowSelected.getAttribute("BudgetCustomerId") != null) {
                custIdHis =
                        rowSelected.getAttribute("BudgetCustomerId").toString();
                yearlyBudgetAmount =
                        new BigDecimal(rowSelected.getAttribute("YearlyBudgetAmount").toString());
                yearlyBudgetAsToDate =
                        new BigDecimal(rowSelected.getAttribute("BudgetAsToDate").toString());
                yearlyBudgetAsToDateUsed =
                        new BigDecimal(rowSelected.getAttribute("BudgetAsToDateUsed").toString());

            }
        }
        //        System.out.println("custIdHis " + custIdHis);
        String custIdHisTransApprvl = "";
        String budgetTypeHisTransApprvl = "";
        BigDecimal PercentageApprvl = BigDecimal.ZERO;
        BigDecimal AmountApprvl = BigDecimal.ZERO;
        DCIteratorBinding dcIterBudgetCustHisTransApprvl =
            bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
        ViewObject voTableDataApprvl =
            dcIterBudgetCustHisTransApprvl.getViewObject();
        Row rowSelectedApprvl = voTableDataApprvl.getCurrentRow();

        if (voTableDataApprvl.getEstimatedRowCount() > 0) {
            if (rowSelectedApprvl.getAttribute("BudgetCustTranId") != null) {
                custIdHisTransApprvl =
                        rowSelectedApprvl.getAttribute("BudgetCustTranId").toString();
                budgetTypeHisTransApprvl =
                        rowSelectedApprvl.getAttribute("BudgetType").toString();
                PercentageApprvl =
                        new BigDecimal(rowSelectedApprvl.getAttribute("Percentage").toString());
                AmountApprvl =
                        new BigDecimal(rowSelectedApprvl.getAttribute("Amount").toString());
            }
        }
        /*  System.out.println("custIdHisTransApprvl " + custIdHisTransApprvl);
        System.out.println("budgetTypeHisTransApprvl " + budgetTypeHisTransApprvl);
        System.out.println("PercentageApprvl " + PercentageApprvl);
        System.out.println("AmountApprvl " + AmountApprvl); */

        if (budgetTypeHisTransApprvl.equalsIgnoreCase("BALANCE") &&
            typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            ViewObject voCustTran = dciterTranHist.getViewObject();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //                    System.out.println("count transaksi "+voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("BALANCE")) {
                            BigDecimal totalPercent =
                                new BigDecimal(tranHistRow.getAttribute("Percentage").toString());
                            valPercentage = valPercentage.add(totalPercent);
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //                                        System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            //        System.out.println("valPercentage yang ada  "+valPercentage);
            //                    System.out.println("valAmount yang ada "+valAmount);
            BigDecimal budPercentTran = valPercentage.add(PercentageApprvl);
            BigDecimal budAmountTran = valAmount.add(AmountApprvl);
            BigDecimal budyearly = BigDecimal.ZERO;
            if (budAmountTran.compareTo(BigDecimal.ZERO) == 0) {
                budyearly = budPercentTran.divide(bdHundred);
            } else if (budPercentTran.compareTo(BigDecimal.ZERO) == 0) {
                budyearly = budAmountTran;
            } else {
                budyearly =
                        (budAmountTran.multiply(budPercentTran)).divide(bdHundred);
            }
            if (budyearly.compareTo(yearlyBudgetAsToDate) >= 0) {
                oracle.jbo.domain.Number number = null;
                try {
                    number =
                            new oracle.jbo.domain.Number(budyearly.toString());
                } catch (SQLException e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }
                //                        System.out.println("number balanceCustomer"+number);

                RowSetIterator BudTranHistComm =
                    dciterTranHistComm.getRowSetIterator();
                Row tranHistRow = dciterTranHistComm.getCurrentRow();
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                    //                   BigDecimal budgetAsToDate=new BigDecimal(budAsToDate.replaceAll(",", ""));
                    //                  BigDecimal budgetAmountYearly=new BigDecimal(budYearlyAmount.replaceAll(",", ""));
                    //                rowSelected.setAttribute("BudgetAsToDate", number);
                    rowSelected.setAttribute("YearlyBudgetAmount", number);
                    dciterTranHistComm.getDataControl().commitTransaction();

                    DCIteratorBinding dcIterBudgetCustHisTransApprvlUpdate =
                        bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
                    ViewObject voTableDataApprvlUpdate =
                        dcIterBudgetCustHisTransApprvlUpdate.getViewObject();
                    Row rowSelectedApprvlUpdate =
                        voTableDataApprvlUpdate.getCurrentRow();
                    Date date = new Date();
                    if (voTableDataApprvlUpdate.getEstimatedRowCount() > 0) {
                        if (rowSelectedApprvlUpdate.getAttribute("BudgetCustTranId") !=
                            null) {
                            rowSelectedApprvlUpdate.setAttribute("Action",
                                                                 action);
                            rowSelectedApprvlUpdate.setAttribute("ActionBy",
                                                                 userName);
                            dcIterBudgetCustHisTransApprvlUpdate.getDataControl().commitTransaction();
                        }
                    }
                    dcIterBudgetCustHisTransApprvlUpdate.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
                }
            } else {
                //showPopup("Nilai \"Yearly Budget Amount\" harus lebih besar atau sama dengan nilai \"Budget As To Date\"", potmessage);
                JSFUtils.addFacesWarningMessage("Nilai \"Yearly Budget Amount\" harus lebih besar atau sama dengan nilai \"Budget As To Date\"");
            }
        } else if (budgetTypeHisTransApprvl.equalsIgnoreCase("BALANCE") &&
                   typeBudgetHeader.equalsIgnoreCase("POSTING")) {
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            ViewObject voCustTran = dciterTranHist.getViewObject();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //                    System.out.println("count transaksi "+voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("BALANCE")) {
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //                                        System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            BigDecimal budAmountTran = valAmount.add(AmountApprvl);
            BigDecimal budyearly = BigDecimal.ZERO;
            budyearly = budAmountTran;
            if (budyearly.compareTo(yearlyBudgetAsToDate) >= 0) {
                oracle.jbo.domain.Number number = null;
                try {
                    number =
                            new oracle.jbo.domain.Number(budyearly.toString());
                } catch (SQLException e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }
                //                        System.out.println("number balancePosting"+number);

                RowSetIterator BudTranHistComm =
                    dciterTranHistComm.getRowSetIterator();
                Row tranHistRow = dciterTranHistComm.getCurrentRow();
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                    //                   BigDecimal budgetAsToDate=new BigDecimal(budAsToDate.replaceAll(",", ""));
                    //                  BigDecimal budgetAmountYearly=new BigDecimal(budYearlyAmount.replaceAll(",", ""));
                    //                rowSelected.setAttribute("BudgetAsToDate", number);
                    rowSelected.setAttribute("YearlyBudgetAmount", number);
                    dciterTranHistComm.getDataControl().commitTransaction();

                    DCIteratorBinding dcIterBudgetCustHisTransApprvlUpdate =
                        bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
                    ViewObject voTableDataApprvlUpdate =
                        dcIterBudgetCustHisTransApprvlUpdate.getViewObject();
                    Row rowSelectedApprvlUpdate =
                        voTableDataApprvlUpdate.getCurrentRow();
                    Date date = new Date();
                    if (voTableDataApprvlUpdate.getEstimatedRowCount() > 0) {
                        if (rowSelectedApprvlUpdate.getAttribute("BudgetCustTranId") !=
                            null) {
                            rowSelectedApprvlUpdate.setAttribute("Action",
                                                                 action);
                            rowSelectedApprvlUpdate.setAttribute("ActionBy",
                                                                 userName);
                            dcIterBudgetCustHisTransApprvlUpdate.getDataControl().commitTransaction();
                        }
                    }
                    dcIterBudgetCustHisTransApprvlUpdate.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
                }
            } else {
                //showPopup("Nilai \"Yearly Budget Amount\" harus lebih besar atau sama dengan nilai \"Budget As To Date\"", potmessage);
                JSFUtils.addFacesWarningMessage("Nilai \"Yearly Budget Amount\" harus lebih besar atau sama dengan nilai \"Budget As To Date\"");
            }
        } else if (budgetTypeHisTransApprvl.equalsIgnoreCase("ADJUSTMENT") &&
                   typeBudgetHeader.equalsIgnoreCase("CUSTOMER")) {
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            ViewObject voCustTran = dciterTranHist.getViewObject();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //                    System.out.println("count transaksi "+voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("ADJUSTMENT")) {
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //                                        System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            BigDecimal budAmountTran = valAmount.add(AmountApprvl);
            BigDecimal budyearly = BigDecimal.ZERO;
            budyearly = budAmountTran;
            if (budyearly.compareTo(yearlyBudgetAsToDateUsed) >= 0 &&
                yearlyBudgetAmount.compareTo(budyearly) >= 0) {
                oracle.jbo.domain.Number number = null;
                try {
                    number =
                            new oracle.jbo.domain.Number(budyearly.toString());
                } catch (SQLException e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }
                //                        System.out.println("number ADJUSTMENTCustomer "+number);
                RowSetIterator BudTranHistComm =
                    dciterTranHistComm.getRowSetIterator();
                Row tranHistRow = dciterTranHistComm.getCurrentRow();
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                    //                   BigDecimal budgetAsToDate=new BigDecimal(budAsToDate.replaceAll(",", ""));
                    //                  BigDecimal budgetAmountYearly=new BigDecimal(budYearlyAmount.replaceAll(",", ""));
                    rowSelected.setAttribute("BudgetAsToDate", number);
                    //                rowSelected.setAttribute("YearlyBudgetAmount", number);
                    dciterTranHistComm.getDataControl().commitTransaction();

                    DCIteratorBinding dcIterBudgetCustHisTransApprvlUpdate =
                        bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
                    ViewObject voTableDataApprvlUpdate =
                        dcIterBudgetCustHisTransApprvlUpdate.getViewObject();
                    Row rowSelectedApprvlUpdate =
                        voTableDataApprvlUpdate.getCurrentRow();
                    Date date = new Date();
                    if (voTableDataApprvlUpdate.getEstimatedRowCount() > 0) {
                        if (rowSelectedApprvlUpdate.getAttribute("BudgetCustTranId") !=
                            null) {
                            rowSelectedApprvlUpdate.setAttribute("Action",
                                                                 action);
                            rowSelectedApprvlUpdate.setAttribute("ActionBy",
                                                                 userName);
                            dcIterBudgetCustHisTransApprvlUpdate.getDataControl().commitTransaction();
                        }
                    }
                    dcIterBudgetCustHisTransApprvlUpdate.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
                }
            } else {
                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Amount adjustment lebih besar dari \"Budget As To Date\" yang sudah terpakai</p>");
                message.append("<p>dan nilai amount adjustment kurang dari \"Yearly Budget Amount\".</p>");
                message.append("<p>Proses konfirmasi tidak dapat dilanjutkan.</p>");
                message.append("</body></html>");
                //showPopup(message.toString(),potmessage);
                JSFUtils.addFacesWarningMessage(message.toString());
            }
        } else {
            BigDecimal valPercentage = BigDecimal.ZERO;
            BigDecimal valAmount = BigDecimal.ZERO;
            ViewObject voCustTran = dciterTranHist.getViewObject();
            voCustTran.setNamedWhereClauseParam("CustId", custIdHis);
            voCustTran.executeQuery();

            RowSetIterator BudTranHist = dciterTranHist.getRowSetIterator();
            //                    System.out.println("count transaksi "+voCustTran.getAllRowsInRange().length);
            if (voCustTran.getAllRowsInRange().length > 0) {
                for (Row tranHistRow : voCustTran.getAllRowsInRange()) {
                    String custIdHisTra =
                        (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                    String BudgetTypeHisTra =
                        (String)tranHistRow.getAttribute("BudgetType").toString();
                    if (custIdHis.equalsIgnoreCase(custIdHisTra)) {
                        if (BudgetTypeHisTra.equalsIgnoreCase("ADJUSTMENT")) {
                            BigDecimal totalAmount =
                                new BigDecimal(tranHistRow.getAttribute("Amount").toString());
                            //                                        System.out.println("looping total amount "+totalAmount);
                            valAmount = valAmount.add(totalAmount);
                        }
                    }
                }
            }
            BigDecimal budAmountTran = valAmount.add(AmountApprvl);
            BigDecimal budyearly = BigDecimal.ZERO;
            budyearly = budAmountTran;
            if (budyearly.compareTo(yearlyBudgetAsToDateUsed) >= 0 &&
                yearlyBudgetAmount.compareTo(budyearly) >= 0) {
                oracle.jbo.domain.Number number = null;
                try {
                    number =
                            new oracle.jbo.domain.Number(budyearly.toString());
                } catch (SQLException e) {
                    JSFUtils.addFacesErrorMessage("Error",
                                                  e.getLocalizedMessage());
                }
                //                        System.out.println("number ADJUSTMENTPosting "+number);
                RowSetIterator BudTranHistComm =
                    dciterTranHistComm.getRowSetIterator();
                Row tranHistRow = dciterTranHistComm.getCurrentRow();
                String custIdHisTra =
                    (String)tranHistRow.getAttribute("BudgetCustomerId").toString();
                if (custIdHisTra.equalsIgnoreCase(custIdHis)) {
                    //                   BigDecimal budgetAsToDate=new BigDecimal(budAsToDate.replaceAll(",", ""));
                    //                  BigDecimal budgetAmountYearly=new BigDecimal(budYearlyAmount.replaceAll(",", ""));
                    rowSelected.setAttribute("BudgetAsToDate", number);
                    //                rowSelected.setAttribute("YearlyBudgetAmount", number);
                    dciterTranHistComm.getDataControl().commitTransaction();

                    DCIteratorBinding dcIterBudgetCustHisTransApprvlUpdate =
                        bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
                    ViewObject voTableDataApprvlUpdate =
                        dcIterBudgetCustHisTransApprvlUpdate.getViewObject();
                    Row rowSelectedApprvlUpdate =
                        voTableDataApprvlUpdate.getCurrentRow();
                    Date date = new Date();
                    if (voTableDataApprvlUpdate.getEstimatedRowCount() > 0) {
                        if (rowSelectedApprvlUpdate.getAttribute("BudgetCustTranId") !=
                            null) {
                            rowSelectedApprvlUpdate.setAttribute("Action",
                                                                 action);
                            rowSelectedApprvlUpdate.setAttribute("ActionBy",
                                                                 userName);
                            dcIterBudgetCustHisTransApprvlUpdate.getDataControl().commitTransaction();
                        }
                    }
                    dcIterBudgetCustHisTransApprvlUpdate.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
                }
            } else {
                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Amount adjustment lebih besar dari \"Budget As To Date\" yang sudah terpakai</p>");
                message.append("<p>dan nilai amount adjustment kurang dari \"Yearly Budget Amount\".</p>");
                message.append("<p>Proses konfirmasi tidak dapat dilanjutkan.</p>");
                message.append("</body></html>");
                //showPopup(message.toString(),potmessage);
                JSFUtils.addFacesWarningMessage(message.toString());
            }
        }
    }

    public void cancelEvt(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        String custIdHis = "";
        DCIteratorBinding dcItteratorBindings =
            ADFUtils.findIterator("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        //Key budgetCustKey = dcItteratorBindings.getCurrentRow().getKey();
        if (rowSelected.getAttribute("BudgetCustomerId") != null) {
            custIdHis =
                    rowSelected.getAttribute("BudgetCustomerId").toString();
        }
        OperationBinding operationBinding =
            bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding refreshCustTran =
            bindings.getOperationBinding("CustTranRefresh");
        refreshCustTran.execute();
        OperationBinding refreshBudgetCust =
            bindings.getOperationBinding("BudgetCustRefresh");
        refreshBudgetCust.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
        //dcItteratorBindings.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
        pbudCusTran.hide();
    }

    public void OKEvt(ActionEvent actionEvent) {
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        String custIdHis = "";
        DCIteratorBinding dcItteratorBindings =
            ADFUtils.findIterator("BudgetCustomerView1Iterator");
        ViewObject voTableData = dcItteratorBindings.getViewObject();
        // Get selected row
        Row rowSelected = voTableData.getCurrentRow();
        Key budgetCustKey = dcItteratorBindings.getCurrentRow().getKey();
        if (rowSelected.getAttribute("BudgetCustomerId") != null) {
            custIdHis =
                    rowSelected.getAttribute("BudgetCustomerId").toString();
        }
        DCIteratorBinding dciterCusTran =
            ADFUtils.findIterator("BudgetCustTranView1Iterator");

        dciterCusTran.getDataControl().commitTransaction();
        OperationBinding operationBindingCommit =
            bindings.getOperationBinding("Commit");
        operationBindingCommit.execute();

        OperationBinding refreshCustTran =
            bindings.getOperationBinding("CustTranRefresh");
        refreshCustTran.execute();

        OperationBinding refreshBudgetCust =
            bindings.getOperationBinding("BudgetCustRefresh");
        refreshBudgetCust.execute();
        dcItteratorBindings.setCurrentRowWithKey(budgetCustKey.toStringFormat(true));
        pbudCusTran.hide();

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

    public void showPopup(String pesan, RichPopup p) {
        otpesan.setValue(pesan);
        AdfFacesContext adc = AdfFacesContext.getCurrentInstance();
        adc.addPartialTarget(otpesan);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        p.show(hints);
    }

    public void setBtnTranHistory(RichCommandToolbarButton btnTranHistory) {
        this.btnTranHistory = btnTranHistory;
    }

    public RichCommandToolbarButton getBtnTranHistory() {
        return btnTranHistory;
    }

    public void setBtnTranApproval(RichCommandToolbarButton btnTranApproval) {
        this.btnTranApproval = btnTranApproval;
    }

    public RichCommandToolbarButton getBtnTranApproval() {
        return btnTranApproval;
    }

    public void btnRejectSetBudgetEvent(ActionEvent actionEvent) {
        UserData userData =
            (UserData)JSFUtils.resolveExpression("#{UserData}");
        String userName = userData.getUserNameLogin();
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIterBudgetCustHisTransApprvlUpdate =
            bindings.findIteratorBinding("BudgetCustTranApprovalView1Iterator");
        ViewObject voTableDataApprvlUpdate =
            dcIterBudgetCustHisTransApprvlUpdate.getViewObject();
        Row rowSelectedApprvlUpdate =
            voTableDataApprvlUpdate.getCurrentRow();
        
        if (voTableDataApprvlUpdate.getEstimatedRowCount() > 0) {
            if (rowSelectedApprvlUpdate.getAttribute("BudgetCustTranId") !=
                null) {
                rowSelectedApprvlUpdate.setAttribute("Action",
                                                     actionReject);
                rowSelectedApprvlUpdate.setAttribute("ActionBy",
                                                     userName);
                dcIterBudgetCustHisTransApprvlUpdate.getDataControl().commitTransaction();
            }
        }
        dcIterBudgetCustHisTransApprvlUpdate.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBudgetCustomer);
    }
}
