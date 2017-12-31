package app.fpp.bean.useraccess;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class AssignCustTypeBackingBean implements Serializable {

    // Shuttle operations
    @SuppressWarnings("compatibility:4429120787595570625")
    private static final long serialVersionUID = 1L;
    List selectedCustType;
    List allCustType;
    Boolean refreshSelectedList = false;
    private final String SELECTED_CUST_TYPE_ITERATOR =
        "AppUserCustTypeView1Iterator";
    private final String ALL_CUST_TYPE_ITERATOR = "AllUserMgmtCustTypeShuttleView1Iterator";
    
    public AssignCustTypeBackingBean() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setSelectedCustType(List selectedCustType) {
        this.selectedCustType = selectedCustType;
    }

    public List getSelectedCustType() {

        if (selectedCustType == null || refreshSelectedList) {
            selectedCustType =
                    attributeListForIterator(SELECTED_CUST_TYPE_ITERATOR, "CustType");
        }
        return selectedCustType;
    }

    public void setAllCustType(List allCustType) {
        this.allCustType = allCustType;
    }

    public List getAllCustType() {
        if (allCustType == null) {
            allCustType = selectItemsForIterator(ALL_CUST_TYPE_ITERATOR, "CustTypeCode", "CustTypeLabel");
        }
        return allCustType;
    }

    public void refreshSelectedList(ValueChangeEvent e) {
        refreshSelectedList = true;
    }

    // Shuttle operations
    public static List attributeListForIterator(String iteratorName,
                                                String valueAttrName) {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer binding =
            (DCBindingContainer)bc.getCurrentBindingsEntry();
        DCIteratorBinding iter = binding.findIteratorBinding(iteratorName);
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    public static List<SelectItem> selectItemsForIterator(String iteratorName,
                                                          String valueAttrName,
                                                          String displayAttrName) {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer binding =
            (DCBindingContainer)bc.getCurrentBindingsEntry();
        DCIteratorBinding iter = binding.findIteratorBinding(iteratorName);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    public String processShuttle() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String closeAfter = "close";
        BindingContext bctx = BindingContext.getCurrent();
        DCBindingContainer binding =
            (DCBindingContainer)bctx.getCurrentBindingsEntry();
        DCIteratorBinding iter =
            (DCIteratorBinding)binding.get(SELECTED_CUST_TYPE_ITERATOR);

        //Removing all rows
        for (Row r : iter.getAllRowsInRange()) {
            r.remove();
        }

        if (this.getSelectedCustType().size() > 0) {
            for (int i = 0; i < getSelectedCustType().size(); i++) {

                Row row = iter.getRowSetIterator().createRow();

                row.setNewRowState(Row.STATUS_INITIALIZED);
                row.setAttribute("UserName", getUserName());
                row.setAttribute("CustType", getSelectedCustType().get(i));

                iter.getRowSetIterator().insertRow(row);
                iter.setCurrentRowWithKey(row.getKey().toStringFormat(true));

            }
        }
        
        String ok = doCommit();
        return closeAfter;
    }

    public String processCancel() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String closeAfter = "close";
        //String cancel = doRollback();
        return closeAfter;
    }

    public String doCommit() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String doRollback() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String getUserName() {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        String userName = (String)pageFlowScope.get("userName");
        return userName;
    }
}
