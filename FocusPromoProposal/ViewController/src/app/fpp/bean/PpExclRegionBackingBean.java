package app.fpp.bean;

import app.fpp.adfextensions.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.DBSequence;

public class PpExclRegionBackingBean implements Serializable {

    // Shuttle operations
    @SuppressWarnings("compatibility:4429120787595570625")
    private static final long serialVersionUID = 1L;
    List selectedRegions;
    List allRegions;
    Boolean refreshSelectedList = false;
    private final String SELECTED_REGION_ITERATOR = "ExclPropCustRegionView1Iterator";
    private final String ALL_REGION_ITERATOR =
        "AllExclProposalRegionShuttleView1Iterator";
    List unfilteredList;

    private boolean resetShuttle = true; //false;

    public PpExclRegionBackingBean() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setSelectedRegions(List selectedRegions) {
        this.selectedRegions = selectedRegions;
    }

    public List getSelectedRegions() {
        if (selectedRegions == null || refreshSelectedList) {
            selectedRegions =
                    attributeListForIterator(SELECTED_REGION_ITERATOR,
                                             "RegionCode");
        }
        return selectedRegions;
    }

    public void setAllRegions(List allRegions) {
        this.allRegions = allRegions;
    }

    public List getAllRegions() {
        if (resetShuttle) {
            allRegions =
                    selectItemsForIterator(ALL_REGION_ITERATOR, "RegionCode",
                                           "RegionLabel");
            setResetShuttle(false);
            setUnfilteredList(allRegions);
        }
        return allRegions;
    }

    public void refreshSelectedList(ValueChangeEvent e) {
        refreshSelectedList = true;
    }

    public ArrayList<SelectItem> filterValues(String filterCriteria) {
        ArrayList<SelectItem> filteredList = new ArrayList<SelectItem>();
        for (int i = 0; i < unfilteredList.size(); i++) {
            if (((SelectItem)unfilteredList.get(i)).getLabel().toLowerCase().contains(filterCriteria.toLowerCase())) {
                SelectItem si = new SelectItem();
                si = (SelectItem)unfilteredList.get(i);
                filteredList.add(si);
            } else {
                for (int ij = 0; ij < selectedRegions.size(); ij++) {
                    if (selectedRegions.get(ij).toString().equalsIgnoreCase(((SelectItem)unfilteredList.get(i)).getValue().toString())) {
                        SelectItem si1 = new SelectItem();
                        si1 = (SelectItem)unfilteredList.get(i);
                        filteredList.add(si1);
                    }
                }
            }
        }
        return filteredList;
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
            (DCIteratorBinding)binding.get(SELECTED_REGION_ITERATOR);

        //Removing all rows
        for (Row r : iter.getAllRowsInRange()) {
            r.remove();
        }

        if (this.getSelectedRegions().size() > 0) {
            for (int i = 0; i < getSelectedRegions().size(); i++) {

                Row row = iter.getRowSetIterator().createRow();

                row.setNewRowState(Row.STATUS_INITIALIZED);
                row.setAttribute("ProposalId", getCurrentPropId().getValue());
                row.setAttribute("RegionCode", getSelectedRegions().get(i));

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
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String doRollback() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public DBSequence getCurrentPropId() {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        DBSequence roleId = (DBSequence)pageFlowScope.get("propId");
        return roleId;
    }

    public void setUnfilteredList(List unfilteredList) {
        this.unfilteredList = unfilteredList;
    }

    public List getUnfilteredList() {
        return unfilteredList;
    }

    public void setResetShuttle(boolean resetShuttle) {
        this.resetShuttle = resetShuttle;
    }

    public boolean isResetShuttle() {
        return resetShuttle;
    }
}
