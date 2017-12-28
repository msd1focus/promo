package app.fpp.model.views.promoproposal.duplicate;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 12 09:25:40 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DuplicateProdukItemViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ProdItem {
            public Object get(DuplicateProdukItemViewRowImpl obj) {
                return obj.getProdItem();
            }

            public void put(DuplicateProdukItemViewRowImpl obj, Object value) {
                obj.setProdItem((String)value);
            }
        }
        ,
        PromoProdukId {
            public Object get(DuplicateProdukItemViewRowImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(DuplicateProdukItemViewRowImpl obj, Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DuplicateProdukItemViewRowImpl object);

        public abstract void put(DuplicateProdukItemViewRowImpl object,
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


    public static final int PRODITEM = AttributesEnum.ProdItem.index();
    public static final int PROMOPRODUKID = AttributesEnum.PromoProdukId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DuplicateProdukItemViewRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute ProdItem.
     * @return the ProdItem
     */
    public String getProdItem() {
        return (String) getAttributeInternal(PRODITEM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProdItem.
     * @param value value to set the  ProdItem
     */
    public void setProdItem(String value) {
        setAttributeInternal(PRODITEM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PromoProdukId.
     * @return the PromoProdukId
     */
    public Number getPromoProdukId() {
        return (Number) getAttributeInternal(PROMOPRODUKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PromoProdukId.
     * @param value value to set the  PromoProdukId
     */
    public void setPromoProdukId(Number value) {
        setAttributeInternal(PROMOPRODUKID, value);
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
