package app.fpp.model.views.confirmation.modifier;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 14 16:54:45 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FcsModifierAreaViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        NoConfirm {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getNoConfirm();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setNoConfirm((String)value);
            }
        }
        ,
        Currency {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getCurrency();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setCurrency((String)value);
            }
        }
        ,
        StartDate {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getStartDate();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setStartDate((Date)value);
            }
        }
        ,
        EndDate {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getEndDate();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setEndDate((Date)value);
            }
        }
        ,
        Active {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getActive();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setActive((String)value);
            }
        }
        ,
        Automatic {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getAutomatic();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setAutomatic((String)value);
            }
        }
        ,
        LineLevel {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getLineLevel();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setLineLevel((String)value);
            }
        }
        ,
        LineType {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getLineType();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setLineType((String)value);
            }
        }
        ,
        StartDateLine {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getStartDateLine();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setStartDateLine((Date)value);
            }
        }
        ,
        EndDateLine {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getEndDateLine();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setEndDateLine((Date)value);
            }
        }
        ,
        AutomaticLine {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getAutomaticLine();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setAutomaticLine((String)value);
            }
        }
        ,
        PricingPhase {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getPricingPhase();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setPricingPhase((String)value);
            }
        }
        ,
        Bucket {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getBucket();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setBucket((String)value);
            }
        }
        ,
        ProductAttribute {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getProductAttribute();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setProductAttribute((String)value);
            }
        }
        ,
        ProductValue {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getProductValue();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setProductValue((String)value);
            }
        }
        ,
        VolumeType {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getVolumeType();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setVolumeType((String)value);
            }
        }
        ,
        BreakType {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getBreakType();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setBreakType((String)value);
            }
        }
        ,
        Uom {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getUom();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setUom((String)value);
            }
        }
        ,
        ValueFrom {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getValueFrom();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setValueFrom((Number)value);
            }
        }
        ,
        ValueTo {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getValueTo();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setValueTo((Number)value);
            }
        }
        ,
        ApplicationMethod {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getApplicationMethod();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setApplicationMethod((String)value);
            }
        }
        ,
        Value {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getValue();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setValue((Number)value);
            }
        }
        ,
        GroupingNo {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getGroupingNo();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setGroupingNo((Number)value);
            }
        }
        ,
        QualifierContext {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getQualifierContext();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setQualifierContext((String)value);
            }
        }
        ,
        QualifierAttr {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getQualifierAttr();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setQualifierAttr((String)value);
            }
        }
        ,
        OperatorSign {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getOperatorSign();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setOperatorSign((String)value);
            }
        }
        ,
        ValueQualifier {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getValueQualifier();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setValueQualifier((String)value);
            }
        }
        ,
        Bucket1 {
            public Object get(FcsModifierAreaViewRowImpl obj) {
                return obj.getBucket1();
            }

            public void put(FcsModifierAreaViewRowImpl obj, Object value) {
                obj.setBucket1((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(FcsModifierAreaViewRowImpl object);

        public abstract void put(FcsModifierAreaViewRowImpl object,
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


    public static final int NOCONFIRM = AttributesEnum.NoConfirm.index();
    public static final int CURRENCY = AttributesEnum.Currency.index();
    public static final int STARTDATE = AttributesEnum.StartDate.index();
    public static final int ENDDATE = AttributesEnum.EndDate.index();
    public static final int ACTIVE = AttributesEnum.Active.index();
    public static final int AUTOMATIC = AttributesEnum.Automatic.index();
    public static final int LINELEVEL = AttributesEnum.LineLevel.index();
    public static final int LINETYPE = AttributesEnum.LineType.index();
    public static final int STARTDATELINE = AttributesEnum.StartDateLine.index();
    public static final int ENDDATELINE = AttributesEnum.EndDateLine.index();
    public static final int AUTOMATICLINE = AttributesEnum.AutomaticLine.index();
    public static final int PRICINGPHASE = AttributesEnum.PricingPhase.index();
    public static final int BUCKET = AttributesEnum.Bucket.index();
    public static final int PRODUCTATTRIBUTE = AttributesEnum.ProductAttribute.index();
    public static final int PRODUCTVALUE = AttributesEnum.ProductValue.index();
    public static final int VOLUMETYPE = AttributesEnum.VolumeType.index();
    public static final int BREAKTYPE = AttributesEnum.BreakType.index();
    public static final int UOM = AttributesEnum.Uom.index();
    public static final int VALUEFROM = AttributesEnum.ValueFrom.index();
    public static final int VALUETO = AttributesEnum.ValueTo.index();
    public static final int APPLICATIONMETHOD = AttributesEnum.ApplicationMethod.index();
    public static final int VALUE = AttributesEnum.Value.index();
    public static final int GROUPINGNO = AttributesEnum.GroupingNo.index();
    public static final int QUALIFIERCONTEXT = AttributesEnum.QualifierContext.index();
    public static final int QUALIFIERATTR = AttributesEnum.QualifierAttr.index();
    public static final int OPERATORSIGN = AttributesEnum.OperatorSign.index();
    public static final int VALUEQUALIFIER = AttributesEnum.ValueQualifier.index();
    public static final int BUCKET1 = AttributesEnum.Bucket1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public FcsModifierAreaViewRowImpl() {
    }

    /**
     * Gets FcsViewItemMasterCategory entity object.
     * @return the FcsViewItemMasterCategory
     */
    public EntityImpl getFcsViewItemMasterCategory() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for the calculated attribute NoConfirm.
     * @return the NoConfirm
     */
    public String getNoConfirm() {
        return (String) getAttributeInternal(NOCONFIRM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute NoConfirm.
     * @param value value to set the  NoConfirm
     */
    public void setNoConfirm(String value) {
        setAttributeInternal(NOCONFIRM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Currency.
     * @return the Currency
     */
    public String getCurrency() {
        return (String) getAttributeInternal(CURRENCY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Currency.
     * @param value value to set the  Currency
     */
    public void setCurrency(String value) {
        setAttributeInternal(CURRENCY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StartDate.
     * @return the StartDate
     */
    public Date getStartDate() {
        return (Date) getAttributeInternal(STARTDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StartDate.
     * @param value value to set the  StartDate
     */
    public void setStartDate(Date value) {
        setAttributeInternal(STARTDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EndDate.
     * @return the EndDate
     */
    public Date getEndDate() {
        return (Date) getAttributeInternal(ENDDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EndDate.
     * @param value value to set the  EndDate
     */
    public void setEndDate(Date value) {
        setAttributeInternal(ENDDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Active.
     * @return the Active
     */
    public String getActive() {
        return (String) getAttributeInternal(ACTIVE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Active.
     * @param value value to set the  Active
     */
    public void setActive(String value) {
        setAttributeInternal(ACTIVE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Automatic.
     * @return the Automatic
     */
    public String getAutomatic() {
        return (String) getAttributeInternal(AUTOMATIC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Automatic.
     * @param value value to set the  Automatic
     */
    public void setAutomatic(String value) {
        setAttributeInternal(AUTOMATIC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LineLevel.
     * @return the LineLevel
     */
    public String getLineLevel() {
        return (String) getAttributeInternal(LINELEVEL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LineLevel.
     * @param value value to set the  LineLevel
     */
    public void setLineLevel(String value) {
        setAttributeInternal(LINELEVEL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LineType.
     * @return the LineType
     */
    public String getLineType() {
        return (String) getAttributeInternal(LINETYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LineType.
     * @param value value to set the  LineType
     */
    public void setLineType(String value) {
        setAttributeInternal(LINETYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StartDateLine.
     * @return the StartDateLine
     */
    public Date getStartDateLine() {
        return (Date) getAttributeInternal(STARTDATELINE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StartDateLine.
     * @param value value to set the  StartDateLine
     */
    public void setStartDateLine(Date value) {
        setAttributeInternal(STARTDATELINE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EndDateLine.
     * @return the EndDateLine
     */
    public Date getEndDateLine() {
        return (Date) getAttributeInternal(ENDDATELINE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EndDateLine.
     * @param value value to set the  EndDateLine
     */
    public void setEndDateLine(Date value) {
        setAttributeInternal(ENDDATELINE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AutomaticLine.
     * @return the AutomaticLine
     */
    public String getAutomaticLine() {
        return (String) getAttributeInternal(AUTOMATICLINE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AutomaticLine.
     * @param value value to set the  AutomaticLine
     */
    public void setAutomaticLine(String value) {
        setAttributeInternal(AUTOMATICLINE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PricingPhase.
     * @return the PricingPhase
     */
    public String getPricingPhase() {
        return (String) getAttributeInternal(PRICINGPHASE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PricingPhase.
     * @param value value to set the  PricingPhase
     */
    public void setPricingPhase(String value) {
        setAttributeInternal(PRICINGPHASE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Bucket.
     * @return the Bucket
     */
    public String getBucket() {
        return (String) getAttributeInternal(BUCKET);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Bucket.
     * @param value value to set the  Bucket
     */
    public void setBucket(String value) {
        setAttributeInternal(BUCKET, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ProductAttribute.
     * @return the ProductAttribute
     */
    public String getProductAttribute() {
        return (String) getAttributeInternal(PRODUCTATTRIBUTE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProductAttribute.
     * @param value value to set the  ProductAttribute
     */
    public void setProductAttribute(String value) {
        setAttributeInternal(PRODUCTATTRIBUTE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ProductValue.
     * @return the ProductValue
     */
    public String getProductValue() {
        return (String) getAttributeInternal(PRODUCTVALUE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProductValue.
     * @param value value to set the  ProductValue
     */
    public void setProductValue(String value) {
        setAttributeInternal(PRODUCTVALUE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute VolumeType.
     * @return the VolumeType
     */
    public String getVolumeType() {
        return (String) getAttributeInternal(VOLUMETYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute VolumeType.
     * @param value value to set the  VolumeType
     */
    public void setVolumeType(String value) {
        setAttributeInternal(VOLUMETYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BreakType.
     * @return the BreakType
     */
    public String getBreakType() {
        return (String) getAttributeInternal(BREAKTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BreakType.
     * @param value value to set the  BreakType
     */
    public void setBreakType(String value) {
        setAttributeInternal(BREAKTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Uom.
     * @return the Uom
     */
    public String getUom() {
        return (String) getAttributeInternal(UOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Uom.
     * @param value value to set the  Uom
     */
    public void setUom(String value) {
        setAttributeInternal(UOM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValueFrom.
     * @return the ValueFrom
     */
    public Number getValueFrom() {
        return (Number) getAttributeInternal(VALUEFROM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValueFrom.
     * @param value value to set the  ValueFrom
     */
    public void setValueFrom(Number value) {
        setAttributeInternal(VALUEFROM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValueTo.
     * @return the ValueTo
     */
    public Number getValueTo() {
        return (Number) getAttributeInternal(VALUETO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValueTo.
     * @param value value to set the  ValueTo
     */
    public void setValueTo(Number value) {
        setAttributeInternal(VALUETO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ApplicationMethod.
     * @return the ApplicationMethod
     */
    public String getApplicationMethod() {
        return (String) getAttributeInternal(APPLICATIONMETHOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ApplicationMethod.
     * @param value value to set the  ApplicationMethod
     */
    public void setApplicationMethod(String value) {
        setAttributeInternal(APPLICATIONMETHOD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Value.
     * @return the Value
     */
    public Number getValue() {
        return (Number) getAttributeInternal(VALUE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Value.
     * @param value value to set the  Value
     */
    public void setValue(Number value) {
        setAttributeInternal(VALUE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GroupingNo.
     * @return the GroupingNo
     */
    public Number getGroupingNo() {
        return (Number) getAttributeInternal(GROUPINGNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GroupingNo.
     * @param value value to set the  GroupingNo
     */
    public void setGroupingNo(Number value) {
        setAttributeInternal(GROUPINGNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute QualifierContext.
     * @return the QualifierContext
     */
    public String getQualifierContext() {
        return (String) getAttributeInternal(QUALIFIERCONTEXT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute QualifierContext.
     * @param value value to set the  QualifierContext
     */
    public void setQualifierContext(String value) {
        setAttributeInternal(QUALIFIERCONTEXT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute QualifierAttr.
     * @return the QualifierAttr
     */
    public String getQualifierAttr() {
        return (String) getAttributeInternal(QUALIFIERATTR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute QualifierAttr.
     * @param value value to set the  QualifierAttr
     */
    public void setQualifierAttr(String value) {
        setAttributeInternal(QUALIFIERATTR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OperatorSign.
     * @return the OperatorSign
     */
    public String getOperatorSign() {
        return (String) getAttributeInternal(OPERATORSIGN);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OperatorSign.
     * @param value value to set the  OperatorSign
     */
    public void setOperatorSign(String value) {
        setAttributeInternal(OPERATORSIGN, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValueQualifier.
     * @return the ValueQualifier
     */
    public String getValueQualifier() {
        return (String) getAttributeInternal(VALUEQUALIFIER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValueQualifier.
     * @param value value to set the  ValueQualifier
     */
    public void setValueQualifier(String value) {
        setAttributeInternal(VALUEQUALIFIER, value);
    }


    /**
     * Gets the attribute value for the calculated attribute Bucket1.
     * @return the Bucket1
     */
    public String getBucket1() {
        return (String) getAttributeInternal(BUCKET1);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Bucket1.
     * @param value value to set the  Bucket1
     */
    public void setBucket1(String value) {
        setAttributeInternal(BUCKET1, value);
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
