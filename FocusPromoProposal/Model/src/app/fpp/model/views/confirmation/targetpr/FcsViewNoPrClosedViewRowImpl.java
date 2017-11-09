package app.fpp.model.views.confirmation.targetpr;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 29 15:18:44 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FcsViewNoPrClosedViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        NoPr {
            public Object get(FcsViewNoPrClosedViewRowImpl obj) {
                return obj.getNoPr();
            }

            public void put(FcsViewNoPrClosedViewRowImpl obj, Object value) {
                obj.setNoPr((String)value);
            }
        }
        ,
        Status {
            public Object get(FcsViewNoPrClosedViewRowImpl obj) {
                return obj.getStatus();
            }

            public void put(FcsViewNoPrClosedViewRowImpl obj, Object value) {
                obj.setStatus((String)value);
            }
        }
        ,
        Closed {
            public Object get(FcsViewNoPrClosedViewRowImpl obj) {
                return obj.getClosed();
            }

            public void put(FcsViewNoPrClosedViewRowImpl obj, Object value) {
                obj.setClosed((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(FcsViewNoPrClosedViewRowImpl object);

        public abstract void put(FcsViewNoPrClosedViewRowImpl object,
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


    public static final int NOPR = AttributesEnum.NoPr.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int CLOSED = AttributesEnum.Closed.index();

    /**
     * This is the default constructor (do not remove).
     */
    public FcsViewNoPrClosedViewRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute NoPr.
     * @return the NoPr
     */
    public String getNoPr() {
        return (String) getAttributeInternal(NOPR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute NoPr.
     * @param value value to set the  NoPr
     */
    public void setNoPr(String value) {
        setAttributeInternal(NOPR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Status.
     * @return the Status
     */
    public String getStatus() {
        return (String) getAttributeInternal(STATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Status.
     * @param value value to set the  Status
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Closed.
     * @return the Closed
     */
    public String getClosed() {
        return (String) getAttributeInternal(CLOSED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Closed.
     * @param value value to set the  Closed
     */
    public void setClosed(String value) {
        setAttributeInternal(CLOSED, value);
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