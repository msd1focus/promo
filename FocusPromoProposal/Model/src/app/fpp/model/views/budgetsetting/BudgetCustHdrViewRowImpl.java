package app.fpp.model.views.budgetsetting;

import app.fpp.model.entities.budgetsetting.BudgetCustHdrImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 07 15:35:27 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BudgetCustHdrViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        BudgetCustHdrId {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getBudgetCustHdrId();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setBudgetCustHdrId((DBSequence)value);
            }
        }
        ,
        CustomerId {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCustomerId();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setCustomerId((String)value);
            }
        }
        ,
        CustomerDesc {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCustomerDesc();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setCustomerDesc((String)value);
            }
        }
        ,
        KodePosting {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getKodePosting();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setKodePosting((String)value);
            }
        }
        ,
        BudgetType {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getBudgetType();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setBudgetType((String)value);
            }
        }
        ,
        BudgetYear {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getBudgetYear();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setBudgetYear((String)value);
            }
        }
        ,
        CheckRowStatus {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCheckRowStatus();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setCheckRowStatus((Integer)value);
            }
        }
        ,
        CreatedBy {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationDate {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCreationDate();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setCreationDate((Date)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedOn {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getModifiedOn();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setModifiedOn((Date)value);
            }
        }
        ,
        BudgetCustomerView {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getBudgetCustomerView();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BudgetCustomerYearLov {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getBudgetCustomerYearLov();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AllKodePostingView1 {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getAllKodePostingView1();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LookupCodeView1 {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getLookupCodeView1();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CustomerGroupLov1 {
            public Object get(BudgetCustHdrViewRowImpl obj) {
                return obj.getCustomerGroupLov1();
            }

            public void put(BudgetCustHdrViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(BudgetCustHdrViewRowImpl object);

        public abstract void put(BudgetCustHdrViewRowImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int BUDGETCUSTHDRID = AttributesEnum.BudgetCustHdrId.index();
    public static final int CUSTOMERID = AttributesEnum.CustomerId.index();
    public static final int CUSTOMERDESC = AttributesEnum.CustomerDesc.index();
    public static final int KODEPOSTING = AttributesEnum.KodePosting.index();
    public static final int BUDGETTYPE = AttributesEnum.BudgetType.index();
    public static final int BUDGETYEAR = AttributesEnum.BudgetYear.index();
    public static final int CHECKROWSTATUS = AttributesEnum.CheckRowStatus.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDON = AttributesEnum.ModifiedOn.index();
    public static final int BUDGETCUSTOMERVIEW = AttributesEnum.BudgetCustomerView.index();
    public static final int BUDGETCUSTOMERYEARLOV = AttributesEnum.BudgetCustomerYearLov.index();
    public static final int ALLKODEPOSTINGVIEW1 = AttributesEnum.AllKodePostingView1.index();
    public static final int LOOKUPCODEVIEW1 = AttributesEnum.LookupCodeView1.index();
    public static final int CUSTOMERGROUPLOV1 = AttributesEnum.CustomerGroupLov1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public BudgetCustHdrViewRowImpl() {
    }

    /**
     * Gets BudgetCustHdr entity object.
     * @return the BudgetCustHdr
     */
    public BudgetCustHdrImpl getBudgetCustHdr() {
        return (BudgetCustHdrImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for BUDGET_CUST_HDR_ID using the alias name BudgetCustHdrId.
     * @return the BUDGET_CUST_HDR_ID
     */
    public DBSequence getBudgetCustHdrId() {
        return (DBSequence)getAttributeInternal(BUDGETCUSTHDRID);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_CUST_HDR_ID using the alias name BudgetCustHdrId.
     * @param value value to set the BUDGET_CUST_HDR_ID
     */
    public void setBudgetCustHdrId(DBSequence value) {
        setAttributeInternal(BUDGETCUSTHDRID, value);
    }

    /**
     * Gets the attribute value for CUSTOMER_ID using the alias name CustomerId.
     * @return the CUSTOMER_ID
     */
    public String getCustomerId() {
        return (String) getAttributeInternal(CUSTOMERID);
    }

    /**
     * Sets <code>value</code> as attribute value for CUSTOMER_ID using the alias name CustomerId.
     * @param value value to set the CUSTOMER_ID
     */
    public void setCustomerId(String value) {
        setAttributeInternal(CUSTOMERID, value);
    }

    /**
     * Gets the attribute value for BUDGET_YEAR using the alias name BudgetYear.
     * @return the BUDGET_YEAR
     */
    public String getBudgetYear() {
        return (String) getAttributeInternal(BUDGETYEAR);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_YEAR using the alias name BudgetYear.
     * @param value value to set the BUDGET_YEAR
     */
    public void setBudgetYear(String value) {
        setAttributeInternal(BUDGETYEAR, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
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
        return (Date) getAttributeInternal(CREATIONDATE);
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
        return (String) getAttributeInternal(MODIFIEDBY);
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
        return (Date) getAttributeInternal(MODIFIEDON);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_ON using the alias name ModifiedOn.
     * @param value value to set the MODIFIED_ON
     */
    public void setModifiedOn(Date value) {
        setAttributeInternal(MODIFIEDON, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CustomerDesc.
     * @return the CustomerDesc
     */
    public String getCustomerDesc() {
        String custId = null;
        String custDesc = null;
        String postDesc = null;
        String PostId = null;
      
        if (getCustomerId() != null) {
            custId = this.getCustomerId();
            Row[] custRows;
            custRows = this.getCustomerGroupLov1().getFilteredRows("FlexValue", custId);
            if (custRows.length > 0) {
                custDesc = (custRows[0].getAttribute("CgLabel").toString()).toUpperCase();
            }
            return custDesc;
        } else if (getKodePosting()!=null){
            PostId = this.getKodePosting();
            Row[] custRows;
            custRows = this.getAllKodePostingView1().getFilteredRows("Item", PostId);
            if (custRows.length > 0) {
                postDesc = (custRows[0].getAttribute("ItemDescription").toString()).toUpperCase();
            }
            return postDesc;
        }else {
            return (String) getAttributeInternal(CUSTOMERDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CustomerDesc.
     * @param value value to set the  CustomerDesc
     */
    public void setCustomerDesc(String value) {
        setAttributeInternal(CUSTOMERDESC, value);
    }

    /**
     * Gets the attribute value for KODE_POSTING using the alias name KodePosting.
     * @return the KODE_POSTING
     */
    public String getKodePosting() {
        return (String) getAttributeInternal(KODEPOSTING);
    }

    /**
     * Sets <code>value</code> as attribute value for KODE_POSTING using the alias name KodePosting.
     * @param value value to set the KODE_POSTING
     */
    public void setKodePosting(String value) {
        setAttributeInternal(KODEPOSTING, value);
    }

    /**
     * Gets the attribute value for BUDGET_TYPE using the alias name BudgetType.
     * @return the BUDGET_TYPE
     */
    public String getBudgetType() {
        return (String) getAttributeInternal(BUDGETTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for BUDGET_TYPE using the alias name BudgetType.
     * @param value value to set the BUDGET_TYPE
     */
    public void setBudgetType(String value) {
        setAttributeInternal(BUDGETTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CheckRowStatus.
     * @return the CheckRowStatus
     */
     /*here row is reference variable of collection, this expression returns an int value if it is 
      2-Modified
      0-New
      1-Unmodified
     -1-Initialized
     */
     public Integer getCheckRowStatus() {
         byte entityState = this.getEntity(0).getEntityState();
         return new Integer(entityState);

         // return (Integer) getAttributeInternal(CHECKROWSTATUS);
     }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CheckRowStatus.
     * @param value value to set the  CheckRowStatus
     */
    public void setCheckRowStatus(Integer value) {
        setAttributeInternal(CHECKROWSTATUS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link BudgetCustomerView.
     */
    public RowIterator getBudgetCustomerView() {
        return (RowIterator)getAttributeInternal(BUDGETCUSTOMERVIEW);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BudgetCustomerYearLov.
     */
    public RowSet getBudgetCustomerYearLov() {
        return (RowSet)getAttributeInternal(BUDGETCUSTOMERYEARLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AllKodePostingView1.
     */
    public RowSet getAllKodePostingView1() {
        return (RowSet)getAttributeInternal(ALLKODEPOSTINGVIEW1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LookupCodeView1.
     */
    public RowSet getLookupCodeView1() {
        return (RowSet)getAttributeInternal(LOOKUPCODEVIEW1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CustomerGroupLov1.
     */
    public RowSet getCustomerGroupLov1() {
        return (RowSet)getAttributeInternal(CUSTOMERGROUPLOV1);
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
