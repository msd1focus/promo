package app.fpp.model.views.budgetsetting;

import app.fpp.model.entities.budgetsetting.BudgetCustomerImpl;

import java.math.BigDecimal;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 07 12:45:00 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BudgetCustomerViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        BudgetCustomerId {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCustomerId();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetCustomerId((DBSequence)value);
            }
        }
        ,
        BudgetCategory {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCategory();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetCategory((String)value);
            }
        }
        ,
        BudgetCategoryDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCategoryDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetCategoryDesc((String)value);
            }
        }
        ,
        BudgetClass {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetClass();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetClass((String)value);
            }
        }
        ,
        BudgetClassDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetClassDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetClassDesc((String)value);
            }
        }
        ,
        BudgetBrand {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetBrand();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetBrand((String)value);
            }
        }
        ,
        BudgetBrandDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetBrandDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetBrandDesc((String)value);
            }
        }
        ,
        BudgetExtention {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetExtention();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetExtention((String)value);
            }
        }
        ,
        BudgetExtentionDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetExtentionDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetExtentionDesc((String)value);
            }
        }
        ,
        BudgetPackaging {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetPackaging();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetPackaging((String)value);
            }
        }
        ,
        BudgetPackagingDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetPackagingDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetPackagingDesc((String)value);
            }
        }
        ,
        BudgetVariant {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetVariant();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetVariant((String)value);
            }
        }
        ,
        BudgetVariantDesc {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetVariantDesc();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setBudgetVariantDesc((String)value);
            }
        }
        ,
        YearlyBudgetAmount {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getYearlyBudgetAmount();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setYearlyBudgetAmount((Number)value);
            }
        }
        ,
        YearlyBudgetUsed {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getYearlyBudgetUsed();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setYearlyBudgetUsed((Number)value);
            }
        }
        ,
        YearlyBudgetRemaining {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getYearlyBudgetRemaining();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setYearlyBudgetRemaining((Number)value);
            }
        }
        ,
        Status {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getStatus();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setStatus((String)value);
            }
        }
        ,
        CreatedBy {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationDate {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getCreationDate();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setCreationDate((Date)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedOn {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getModifiedOn();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setModifiedOn((Date)value);
            }
        }
        ,
        TranRow {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getTranRow();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setTranRow((String)value);
            }
        }
        ,
        BudgetCustTranView {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCustTranView();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetCustTranHistoryView {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCustTranHistoryView();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetCustTypeLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCustTypeLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByCategoryLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByCategoryLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByClassLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByClassLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByBrandLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByBrandLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByExtLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByExtLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByPackagingLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByPackagingLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetByVariantLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetByVariantLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetCustStatusLov {
            public Object get(BudgetCustomerViewRowImpl obj) {
                return obj.getBudgetCustStatusLov();
            }

            public void put(BudgetCustomerViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(BudgetCustomerViewRowImpl object);

        public abstract void put(BudgetCustomerViewRowImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() +
                AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int BUDGETCUSTOMERID = AttributesEnum.BudgetCustomerId.index();
    public static final int BUDGETCATEGORY = AttributesEnum.BudgetCategory.index();
    public static final int BUDGETCATEGORYDESC = AttributesEnum.BudgetCategoryDesc.index();
    public static final int BUDGETCLASS = AttributesEnum.BudgetClass.index();
    public static final int BUDGETCLASSDESC = AttributesEnum.BudgetClassDesc.index();
    public static final int BUDGETBRAND = AttributesEnum.BudgetBrand.index();
    public static final int BUDGETBRANDDESC = AttributesEnum.BudgetBrandDesc.index();
    public static final int BUDGETEXTENTION = AttributesEnum.BudgetExtention.index();
    public static final int BUDGETEXTENTIONDESC = AttributesEnum.BudgetExtentionDesc.index();
    public static final int BUDGETPACKAGING = AttributesEnum.BudgetPackaging.index();
    public static final int BUDGETPACKAGINGDESC = AttributesEnum.BudgetPackagingDesc.index();
    public static final int BUDGETVARIANT = AttributesEnum.BudgetVariant.index();
    public static final int BUDGETVARIANTDESC = AttributesEnum.BudgetVariantDesc.index();
    public static final int YEARLYBUDGETAMOUNT = AttributesEnum.YearlyBudgetAmount.index();
    public static final int YEARLYBUDGETUSED = AttributesEnum.YearlyBudgetUsed.index();
    public static final int YEARLYBUDGETREMAINING = AttributesEnum.YearlyBudgetRemaining.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDON = AttributesEnum.ModifiedOn.index();
    public static final int TRANROW = AttributesEnum.TranRow.index();
    public static final int BUDGETCUSTTRANVIEW = AttributesEnum.BudgetCustTranView.index();
    public static final int BUDGETCUSTTRANHISTORYVIEW = AttributesEnum.BudgetCustTranHistoryView.index();
    public static final int BUDGETCUSTTYPELOV = AttributesEnum.BudgetCustTypeLov.index();
    public static final int BUDGETBYCATEGORYLOV = AttributesEnum.BudgetByCategoryLov.index();
    public static final int BUDGETBYCLASSLOV = AttributesEnum.BudgetByClassLov.index();
    public static final int BUDGETBYBRANDLOV = AttributesEnum.BudgetByBrandLov.index();
    public static final int BUDGETBYEXTLOV = AttributesEnum.BudgetByExtLov.index();
    public static final int BUDGETBYPACKAGINGLOV = AttributesEnum.BudgetByPackagingLov.index();
    public static final int BUDGETBYVARIANTLOV = AttributesEnum.BudgetByVariantLov.index();
    public static final int BUDGETCUSTSTATUSLOV = AttributesEnum.BudgetCustStatusLov.index();

    /**
     * This is the default constructor (do not remove).
     */
    public BudgetCustomerViewRowImpl() {
    }

    /**
     * Gets BudgetCustomer entity object.
     * @return the BudgetCustomer
     */
    public BudgetCustomerImpl getBudgetCustomer() {
        return (BudgetCustomerImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for BUDGET_CUSTOMER_ID using the alias name BudgetCustomerId.
     * @return the BUDGET_CUSTOMER_ID
     */
    public DBSequence getBudgetCustomerId() {
        return (DBSequence)getAttributeInternal(BUDGETCUSTOMERID);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_CUSTOMER_ID using the alias name BudgetCustomerId.
     * @param value value to set the BUDGET_CUSTOMER_ID
     */
    public void setBudgetCustomerId(DBSequence value) {
        setAttributeInternal(BUDGETCUSTOMERID, value);
    }

    /**
     * Gets the attribute value for BUDGET_CATEGORY using the alias name BudgetCategory.
     * @return the BUDGET_CATEGORY
     */
    public String getBudgetCategory() {
        return (String)getAttributeInternal(BUDGETCATEGORY);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_CATEGORY using the alias name BudgetCategory.
     * @param value value to set the BUDGET_CATEGORY
     */
    public void setBudgetCategory(String value) {
        setAttributeInternal(BUDGETCATEGORY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetCategoryDesc.
     * @return the BudgetCategoryDesc
     */
    public String getBudgetCategoryDesc() {
        String budgetCategory = null;
        String budgetCategoryDesc = null;
        if (getBudgetCategory() != null) {
            budgetCategory = getBudgetCategory();
            Row[] budgetCatRows;
            budgetCatRows = this.getBudgetByCategoryLov().getFilteredRows("SetCategory", budgetCategory);
            if (budgetCatRows.length > 0) {
                budgetCategoryDesc = (budgetCatRows[0].getAttribute("SetCategoryDesc").toString()).toUpperCase();
            }
            return budgetCategoryDesc;
        } else {
            return (String)getAttributeInternal(BUDGETCATEGORYDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetCategoryDesc.
     * @param value value to set the  BudgetCategoryDesc
     */
    public void setBudgetCategoryDesc(String value) {
        setAttributeInternal(BUDGETCATEGORYDESC, value);
    }

    /**
     * Gets the attribute value for BUDGET_CLASS using the alias name BudgetClass.
     * @return the BUDGET_CLASS
     */
    public String getBudgetClass() {
        return (String)getAttributeInternal(BUDGETCLASS);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_CLASS using the alias name BudgetClass.
     * @param value value to set the BUDGET_CLASS
     */
    public void setBudgetClass(String value) {
        setAttributeInternal(BUDGETCLASS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetClassDesc.
     * @return the BudgetClassDesc
     */
    public String getBudgetClassDesc() {
        String budgetClass = null;
        String budgetClassDesc = null;
        if (getBudgetClass() != null) {
            budgetClass = getBudgetClass();
            Row[] budgetClassRows;
            budgetClassRows = this.getBudgetByClassLov().getFilteredRows("SetClass", budgetClass);
            if (budgetClassRows.length > 0) {
                budgetClassDesc = (budgetClassRows[0].getAttribute("SetClassDesc").toString()).toUpperCase();
            }
            return budgetClassDesc;
        } else {
            return (String)getAttributeInternal(BUDGETCLASSDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetClassDesc.
     * @param value value to set the  BudgetClassDesc
     */
    public void setBudgetClassDesc(String value) {
        setAttributeInternal(BUDGETCLASSDESC, value);
    }

    /**
     * Gets the attribute value for BUDGET_BRAND using the alias name BudgetBrand.
     * @return the BUDGET_BRAND
     */
    public String getBudgetBrand() {
        return (String)getAttributeInternal(BUDGETBRAND);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_BRAND using the alias name BudgetBrand.
     * @param value value to set the BUDGET_BRAND
     */
    public void setBudgetBrand(String value) {
        setAttributeInternal(BUDGETBRAND, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetBrandDesc.
     * @return the BudgetBrandDesc
     */
    public String getBudgetBrandDesc() {
        String budgetBrand = null;
        String budgetBrandDesc = null;
        if (getBudgetBrand() != null) {
            budgetBrand = getBudgetBrand();
            Row[] budgetBrandRows;
            budgetBrandRows = this.getBudgetByBrandLov().getFilteredRows("SetBrand", budgetBrand);
            if (budgetBrandRows.length > 0) {
                budgetBrandDesc = (budgetBrandRows[0].getAttribute("SetBrandDesc").toString()).toUpperCase();
            }
            return budgetBrandDesc;
        } else {
            return (String)getAttributeInternal(BUDGETBRANDDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetBrandDesc.
     * @param value value to set the  BudgetBrandDesc
     */
    public void setBudgetBrandDesc(String value) {
        setAttributeInternal(BUDGETBRANDDESC, value);
    }

    /**
     * Gets the attribute value for BUDGET_EXTENTION using the alias name BudgetExtention.
     * @return the BUDGET_EXTENTION
     */
    public String getBudgetExtention() {
        return (String)getAttributeInternal(BUDGETEXTENTION);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_EXTENTION using the alias name BudgetExtention.
     * @param value value to set the BUDGET_EXTENTION
     */
    public void setBudgetExtention(String value) {
        setAttributeInternal(BUDGETEXTENTION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetExtentionDesc.
     * @return the BudgetExtentionDesc
     */
    public String getBudgetExtentionDesc() {
        String budgetExt = null;
        String budgetExtDesc = null;
        if (getBudgetExtention() != null) {
            budgetExt = getBudgetExtention();
            Row[] budgetExtRows;
            budgetExtRows = this.getBudgetByExtLov().getFilteredRows("SetExt", budgetExt);
            if (budgetExtRows.length > 0) {
                budgetExtDesc = (budgetExtRows[0].getAttribute("SetExtDesc").toString()).toUpperCase();
            }
            return budgetExtDesc;
        } else {
            return (String)getAttributeInternal(BUDGETEXTENTIONDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetExtentionDesc.
     * @param value value to set the  BudgetExtentionDesc
     */
    public void setBudgetExtentionDesc(String value) {
        setAttributeInternal(BUDGETEXTENTIONDESC, value);
    }

    /**
     * Gets the attribute value for BUDGET_PACKAGING using the alias name BudgetPackaging.
     * @return the BUDGET_PACKAGING
     */
    public String getBudgetPackaging() {
        return (String)getAttributeInternal(BUDGETPACKAGING);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_PACKAGING using the alias name BudgetPackaging.
     * @param value value to set the BUDGET_PACKAGING
     */
    public void setBudgetPackaging(String value) {
        setAttributeInternal(BUDGETPACKAGING, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetPackagingDesc.
     * @return the BudgetPackagingDesc
     */
    public String getBudgetPackagingDesc() {
        String budgetPack = null;
        String budgetPackDesc = null;
        if (getBudgetPackaging() != null) {
            budgetPack = getBudgetPackaging();
            Row[] budgetPackRows;
            budgetPackRows = this.getBudgetByPackagingLov().getFilteredRows("SetPackaging", budgetPack);
            if (budgetPackRows.length > 0) {
                budgetPackDesc = (budgetPackRows[0].getAttribute("SetPackagingDesc").toString()).toUpperCase();
            }
            return budgetPackDesc;
        } else {
            return (String)getAttributeInternal(BUDGETPACKAGINGDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetPackagingDesc.
     * @param value value to set the  BudgetPackagingDesc
     */
    public void setBudgetPackagingDesc(String value) {
        setAttributeInternal(BUDGETPACKAGINGDESC, value);
    }

    /**
     * Gets the attribute value for BUDGET_VARIANT using the alias name BudgetVariant.
     * @return the BUDGET_VARIANT
     */
    public String getBudgetVariant() {
        return (String)getAttributeInternal(BUDGETVARIANT);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_VARIANT using the alias name BudgetVariant.
     * @param value value to set the BUDGET_VARIANT
     */
    public void setBudgetVariant(String value) {
        setAttributeInternal(BUDGETVARIANT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BudgetVariantDesc.
     * @return the BudgetVariantDesc
     */
    public String getBudgetVariantDesc() {
        String budgetVariant = null;
        String budgetVariantDesc = null;
        if (getBudgetVariant() != null) {
            budgetVariant = getBudgetVariant();
            Row[] budgetVarRows;
            budgetVarRows = this.getBudgetByVariantLov().getFilteredRows("SetVariant", budgetVariant);
            if (budgetVarRows.length > 0) {
                budgetVariantDesc = (budgetVarRows[0].getAttribute("SetVariantDesc").toString()).toUpperCase();
            }
            return budgetVariantDesc;
        } else {
            return (String)getAttributeInternal(BUDGETVARIANTDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BudgetVariantDesc.
     * @param value value to set the  BudgetVariantDesc
     */
    public void setBudgetVariantDesc(String value) {
        setAttributeInternal(BUDGETVARIANTDESC, value);
    }

    /**
     * Gets the attribute value for YEARLY_BUDGET_AMOUNT using the alias name YearlyBudgetAmount.
     * @return the YEARLY_BUDGET_AMOUNT
     */
    public Number getYearlyBudgetAmount() {
        return (Number)getAttributeInternal(YEARLYBUDGETAMOUNT);
    }

    /**
     * Sets <code>value</code> as attribute value for YEARLY_BUDGET_AMOUNT using the alias name YearlyBudgetAmount.
     * @param value value to set the YEARLY_BUDGET_AMOUNT
     */
    public void setYearlyBudgetAmount(Number value) {
        setAttributeInternal(YEARLYBUDGETAMOUNT, value);
    }

    /**
     * Gets the attribute value for STATUS using the alias name Status.
     * @return the STATUS
     */
    public String getStatus() {
        return (String)getAttributeInternal(STATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for STATUS using the alias name Status.
     * @param value value to set the STATUS
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }


    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String)getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Date getCreationDate() {
        return (Date)getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @return the MODIFIED_BY
     */
    public String getModifiedBy() {
        return (String)getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @param value value to set the MODIFIED_BY
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for MODIFIED_ON using the alias name ModifiedOn.
     * @return the MODIFIED_ON
     */
    public Date getModifiedOn() {
        return (Date)getAttributeInternal(MODIFIEDON);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_ON using the alias name ModifiedOn.
     * @param value value to set the MODIFIED_ON
     */
    public void setModifiedOn(Date value) {
        setAttributeInternal(MODIFIEDON, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranRow.
     * @return the TranRow
     */
    public String getTranRow() {
        return (String) getAttributeInternal(TRANROW);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranRow.
     * @param value value to set the  TranRow
     */
    public void setTranRow(String value) {
        setAttributeInternal(TRANROW, value);
    }

    /**
     * Gets the attribute value for the calculated attribute YearlyBudgetRemaining.
     * @return the YearlyBudgetRemaining
     */
    public Number getYearlyBudgetRemaining() {
        return (Number) getAttributeInternal(YEARLYBUDGETREMAINING);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute YearlyBudgetRemaining.
     * @param value value to set the  YearlyBudgetRemaining
     */
    public void setYearlyBudgetRemaining(Number value) {
        setAttributeInternal(YEARLYBUDGETREMAINING, value);
    }

    /**
     * Gets the attribute value for the calculated attribute YearlyBudgetUsed.
     * @return the YearlyBudgetUsed
     */
    public Number getYearlyBudgetUsed() {
        return (Number) getAttributeInternal(YEARLYBUDGETUSED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute YearlyBudgetUsed.
     * @param value value to set the  YearlyBudgetUsed
     */
    public void setYearlyBudgetUsed(Number value) {
        setAttributeInternal(YEARLYBUDGETUSED, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link BudgetCustTranView.
     */
    public RowIterator getBudgetCustTranView() {
        return (RowIterator)getAttributeInternal(BUDGETCUSTTRANVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link BudgetCustTranHistoryView.
     */
    public RowIterator getBudgetCustTranHistoryView() {
        return (RowIterator)getAttributeInternal(BUDGETCUSTTRANHISTORYVIEW);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetCustTypeLov.
     */
    public RowSet getBudgetCustTypeLov() {
        return (RowSet)getAttributeInternal(BUDGETCUSTTYPELOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByCategoryLov.
     */
    public RowSet getBudgetByCategoryLov() {
        return (RowSet)getAttributeInternal(BUDGETBYCATEGORYLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByClassLov.
     */
    public RowSet getBudgetByClassLov() {
        return (RowSet)getAttributeInternal(BUDGETBYCLASSLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByBrandLov.
     */
    public RowSet getBudgetByBrandLov() {
        return (RowSet)getAttributeInternal(BUDGETBYBRANDLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByExtLov.
     */
    public RowSet getBudgetByExtLov() {
        return (RowSet)getAttributeInternal(BUDGETBYEXTLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByPackagingLov.
     */
    public RowSet getBudgetByPackagingLov() {
        return (RowSet)getAttributeInternal(BUDGETBYPACKAGINGLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetByVariantLov.
     */
    public RowSet getBudgetByVariantLov() {
        return (RowSet)getAttributeInternal(BUDGETBYVARIANTLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetCustStatusLov.
     */
    public RowSet getBudgetCustStatusLov() {
        return (RowSet)getAttributeInternal(BUDGETCUSTSTATUSLOV);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
