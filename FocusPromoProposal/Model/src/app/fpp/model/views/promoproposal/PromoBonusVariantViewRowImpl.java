package app.fpp.model.views.promoproposal;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 19 18:12:36 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PromoBonusVariantViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ProdVariant {
            public Object get(PromoBonusVariantViewRowImpl obj) {
                return obj.getProdVariant();
            }

            public void put(PromoBonusVariantViewRowImpl obj, Object value) {
                obj.setProdVariant((String)value);
            }
        }
        ,
        PromoBonusId {
            public Object get(PromoBonusVariantViewRowImpl obj) {
                return obj.getPromoBonusId();
            }

            public void put(PromoBonusVariantViewRowImpl obj, Object value) {
                obj.setPromoBonusId((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(PromoBonusVariantViewRowImpl object);

        public abstract void put(PromoBonusVariantViewRowImpl object,
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
    public static final int PRODVARIANT = AttributesEnum.ProdVariant.index();
    public static final int PROMOBONUSID = AttributesEnum.PromoBonusId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PromoBonusVariantViewRowImpl() {
    }

    /**
     * Gets PromoBonusVariant entity object.
     * @return the PromoBonusVariant
     */
    public EntityImpl getPromoBonusVariant() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PROD_VARIANT using the alias name ProdVariant.
     * @return the PROD_VARIANT
     */
    public String getProdVariant() {
        return (String) getAttributeInternal(PRODVARIANT);
    }

    /**
     * Sets <code>value</code> as attribute value for PROD_VARIANT using the alias name ProdVariant.
     * @param value value to set the PROD_VARIANT
     */
    public void setProdVariant(String value) {
        setAttributeInternal(PRODVARIANT, value);
    }

    /**
     * Gets the attribute value for PROMO_BONUS_ID using the alias name PromoBonusId.
     * @return the PROMO_BONUS_ID
     */
    public Number getPromoBonusId() {
        return (Number) getAttributeInternal(PROMOBONUSID);
    }

    /**
     * Sets <code>value</code> as attribute value for PROMO_BONUS_ID using the alias name PromoBonusId.
     * @param value value to set the PROMO_BONUS_ID
     */
    public void setPromoBonusId(Number value) {
        setAttributeInternal(PROMOBONUSID, value);
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
