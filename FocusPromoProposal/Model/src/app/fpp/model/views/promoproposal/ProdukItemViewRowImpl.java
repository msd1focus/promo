package app.fpp.model.views.promoproposal;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 20 14:33:03 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ProdukItemViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ProdItem {
            public Object get(ProdukItemViewRowImpl obj) {
                return obj.getProdItem();
            }

            public void put(ProdukItemViewRowImpl obj, Object value) {
                obj.setProdItem((String)value);
            }
        }
        ,
        PromoProdukId {
            public Object get(ProdukItemViewRowImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(ProdukItemViewRowImpl obj, Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(ProdukItemViewRowImpl object);

        public abstract void put(ProdukItemViewRowImpl object, Object value);

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
    public ProdukItemViewRowImpl() {
    }

    /**
     * Gets ProdukItem entity object.
     * @return the ProdukItem
     */
    public EntityImpl getProdukItem() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PROD_ITEM using the alias name ProdItem.
     * @return the PROD_ITEM
     */
    public String getProdItem() {
        return (String) getAttributeInternal(PRODITEM);
    }

    /**
     * Sets <code>value</code> as attribute value for PROD_ITEM using the alias name ProdItem.
     * @param value value to set the PROD_ITEM
     */
    public void setProdItem(String value) {
        setAttributeInternal(PRODITEM, value);
    }

    /**
     * Gets the attribute value for PROMO_PRODUK_ID using the alias name PromoProdukId.
     * @return the PROMO_PRODUK_ID
     */
    public Number getPromoProdukId() {
        return (Number) getAttributeInternal(PROMOPRODUKID);
    }

    /**
     * Sets <code>value</code> as attribute value for PROMO_PRODUK_ID using the alias name PromoProdukId.
     * @param value value to set the PROMO_PRODUK_ID
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
