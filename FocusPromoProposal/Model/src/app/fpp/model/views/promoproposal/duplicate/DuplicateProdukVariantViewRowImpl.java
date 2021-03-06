package app.fpp.model.views.promoproposal.duplicate;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 12 08:51:38 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DuplicateProdukVariantViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ProdVariant {
            public Object get(DuplicateProdukVariantViewRowImpl obj) {
                return obj.getProdVariant();
            }

            public void put(DuplicateProdukVariantViewRowImpl obj,
                            Object value) {
                obj.setProdVariant((String)value);
            }
        }
        ,
        PromoProdukId {
            public Object get(DuplicateProdukVariantViewRowImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(DuplicateProdukVariantViewRowImpl obj,
                            Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DuplicateProdukVariantViewRowImpl object);

        public abstract void put(DuplicateProdukVariantViewRowImpl object,
                                 Object value);

        public int index() {
            return DuplicateProdukVariantViewRowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return DuplicateProdukVariantViewRowImpl.AttributesEnum.firstIndex() + DuplicateProdukVariantViewRowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = DuplicateProdukVariantViewRowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int PRODVARIANT = AttributesEnum.ProdVariant.index();
    public static final int PROMOPRODUKID = AttributesEnum.PromoProdukId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DuplicateProdukVariantViewRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute ProdVariant.
     * @return the ProdVariant
     */
    public String getProdVariant() {
        return (String) getAttributeInternal(PRODVARIANT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProdVariant.
     * @param value value to set the  ProdVariant
     */
    public void setProdVariant(String value) {
        setAttributeInternal(PRODVARIANT, value);
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
