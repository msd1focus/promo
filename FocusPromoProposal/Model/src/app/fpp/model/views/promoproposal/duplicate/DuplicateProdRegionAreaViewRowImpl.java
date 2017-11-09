package app.fpp.model.views.promoproposal.duplicate;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 13 14:14:04 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DuplicateProdRegionAreaViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        PromoProdukId {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ,
        AreaCode {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getAreaCode();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setAreaCode((String)value);
            }
        }
        ,
        Notes {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getNotes();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setNotes((String)value);
            }
        }
        ,
        Description {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getDescription();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setDescription((String)value);
            }
        }
        ,
        Value {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getValue();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setValue((String)value);
            }
        }
        ,
        RegionCode {
            public Object get(DuplicateProdRegionAreaViewRowImpl obj) {
                return obj.getRegionCode();
            }

            public void put(DuplicateProdRegionAreaViewRowImpl obj,
                            Object value) {
                obj.setRegionCode((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DuplicateProdRegionAreaViewRowImpl object);

        public abstract void put(DuplicateProdRegionAreaViewRowImpl object,
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


    public static final int PROMOPRODUKID = AttributesEnum.PromoProdukId.index();
    public static final int AREACODE = AttributesEnum.AreaCode.index();
    public static final int NOTES = AttributesEnum.Notes.index();
    public static final int DESCRIPTION = AttributesEnum.Description.index();
    public static final int VALUE = AttributesEnum.Value.index();
    public static final int REGIONCODE = AttributesEnum.RegionCode.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DuplicateProdRegionAreaViewRowImpl() {
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
     * Gets the attribute value for the calculated attribute AreaCode.
     * @return the AreaCode
     */
    public String getAreaCode() {
        return (String) getAttributeInternal(AREACODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AreaCode.
     * @param value value to set the  AreaCode
     */
    public void setAreaCode(String value) {
        setAttributeInternal(AREACODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Notes.
     * @return the Notes
     */
    public String getNotes() {
        return (String) getAttributeInternal(NOTES);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Notes.
     * @param value value to set the  Notes
     */
    public void setNotes(String value) {
        setAttributeInternal(NOTES, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Description.
     * @return the Description
     */
    public String getDescription() {
        return (String) getAttributeInternal(DESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Description.
     * @param value value to set the  Description
     */
    public void setDescription(String value) {
        setAttributeInternal(DESCRIPTION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Value.
     * @return the Value
     */
    public String getValue() {
        return (String) getAttributeInternal(VALUE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Value.
     * @param value value to set the  Value
     */
    public void setValue(String value) {
        setAttributeInternal(VALUE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute RegionCode.
     * @return the RegionCode
     */
    public String getRegionCode() {
        return (String) getAttributeInternal(REGIONCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RegionCode.
     * @param value value to set the  RegionCode
     */
    public void setRegionCode(String value) {
        setAttributeInternal(REGIONCODE, value);
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