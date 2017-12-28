package app.fpp.model.entities.confirmation;

import app.fpp.model.entities.promoproposal.PromoProdukImpl;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 31 13:31:35 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ProdBudgetByImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        BudgetById {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getBudgetById();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setBudgetById((DBSequence)value);
            }
        }
        ,
        PromoProdukId {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ,
        KombinasiBudget {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getKombinasiBudget();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setKombinasiBudget((String)value);
            }
        }
        ,
        Amount {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getAmount();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setAmount((Number)value);
            }
        }
        ,
        Percentage {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getPercentage();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setPercentage((Number)value);
            }
        }
        ,
        BudgetPostingId {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getBudgetPostingId();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setBudgetPostingId((Number)value);
            }
        }
        ,
        BudgetCustId {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getBudgetCustId();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setBudgetCustId((Number)value);
            }
        }
        ,
        PromoProduk {
            public Object get(ProdBudgetByImpl obj) {
                return obj.getPromoProduk();
            }

            public void put(ProdBudgetByImpl obj, Object value) {
                obj.setPromoProduk((PromoProdukImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(ProdBudgetByImpl object);

        public abstract void put(ProdBudgetByImpl object, Object value);

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


    public static final int BUDGETBYID = AttributesEnum.BudgetById.index();
    public static final int PROMOPRODUKID = AttributesEnum.PromoProdukId.index();
    public static final int KOMBINASIBUDGET = AttributesEnum.KombinasiBudget.index();
    public static final int AMOUNT = AttributesEnum.Amount.index();
    public static final int PERCENTAGE = AttributesEnum.Percentage.index();
    public static final int BUDGETPOSTINGID = AttributesEnum.BudgetPostingId.index();
    public static final int BUDGETCUSTID = AttributesEnum.BudgetCustId.index();
    public static final int PROMOPRODUK = AttributesEnum.PromoProduk.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ProdBudgetByImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("app.fpp.model.entities.confirmation.ProdBudgetBy");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for BudgetById, using the alias name BudgetById.
     * @return the BudgetById
     */
    public DBSequence getBudgetById() {
        return (DBSequence)getAttributeInternal(BUDGETBYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BudgetById.
     * @param value value to set the BudgetById
     */
    public void setBudgetById(DBSequence value) {
        setAttributeInternal(BUDGETBYID, value);
    }

    /**
     * Gets the attribute value for PromoProdukId, using the alias name PromoProdukId.
     * @return the PromoProdukId
     */
    public Number getPromoProdukId() {
        return (Number)getAttributeInternal(PROMOPRODUKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PromoProdukId.
     * @param value value to set the PromoProdukId
     */
    public void setPromoProdukId(Number value) {
        setAttributeInternal(PROMOPRODUKID, value);
    }

    /**
     * Gets the attribute value for KombinasiBudget, using the alias name KombinasiBudget.
     * @return the KombinasiBudget
     */
    public String getKombinasiBudget() {
        return (String)getAttributeInternal(KOMBINASIBUDGET);
    }

    /**
     * Sets <code>value</code> as the attribute value for KombinasiBudget.
     * @param value value to set the KombinasiBudget
     */
    public void setKombinasiBudget(String value) {
        setAttributeInternal(KOMBINASIBUDGET, value);
    }

    /**
     * Gets the attribute value for Amount, using the alias name Amount.
     * @return the Amount
     */
    public Number getAmount() {
        return (Number)getAttributeInternal(AMOUNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for Amount.
     * @param value value to set the Amount
     */
    public void setAmount(Number value) {
        setAttributeInternal(AMOUNT, value);
    }

    /**
     * Gets the attribute value for Percentage, using the alias name Percentage.
     * @return the Percentage
     */
    public Number getPercentage() {
        return (Number)getAttributeInternal(PERCENTAGE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Percentage.
     * @param value value to set the Percentage
     */
    public void setPercentage(Number value) {
        setAttributeInternal(PERCENTAGE, value);
    }

    /**
     * Gets the attribute value for BudgetPostingId, using the alias name BudgetPostingId.
     * @return the BudgetPostingId
     */
    public Number getBudgetPostingId() {
        return (Number)getAttributeInternal(BUDGETPOSTINGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BudgetPostingId.
     * @param value value to set the BudgetPostingId
     */
    public void setBudgetPostingId(Number value) {
        setAttributeInternal(BUDGETPOSTINGID, value);
    }

    /**
     * Gets the attribute value for BudgetCustId, using the alias name BudgetCustId.
     * @return the BudgetCustId
     */
    public Number getBudgetCustId() {
        return (Number)getAttributeInternal(BUDGETCUSTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BudgetCustId.
     * @param value value to set the BudgetCustId
     */
    public void setBudgetCustId(Number value) {
        setAttributeInternal(BUDGETCUSTID, value);
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

    /**
     * @return the associated entity app.fpp.model.entities.promoproposal.PromoProdukImpl.
     */
    public PromoProdukImpl getPromoProduk() {
        return (PromoProdukImpl)getAttributeInternal(PROMOPRODUK);
    }

    /**
     * Sets <code>value</code> as the associated entity app.fpp.model.entities.promoproposal.PromoProdukImpl.
     */
    public void setPromoProduk(PromoProdukImpl value) {
        setAttributeInternal(PROMOPRODUK, value);
    }


    /**
     * @param budgetById key constituent
     * @param promoProdukId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence budgetById,
                                       Number promoProdukId) {
        return new Key(new Object[]{budgetById, promoProdukId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        SequenceImpl seq =
            new SequenceImpl("PROD_BUDGET_BY_SEQ", getDBTransaction());
        this.setBudgetById(new DBSequence(seq.getSequenceNumber()));
    }
}
