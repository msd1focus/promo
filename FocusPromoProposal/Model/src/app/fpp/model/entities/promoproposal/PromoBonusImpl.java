package app.fpp.model.entities.promoproposal;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 10 13:22:26 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PromoBonusImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        PromoBonusId {
            public Object get(PromoBonusImpl obj) {
                return obj.getPromoBonusId();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setPromoBonusId((DBSequence)value);
            }
        }
        ,
        PromoProdukId {
            public Object get(PromoBonusImpl obj) {
                return obj.getPromoProdukId();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setPromoProdukId((Number)value);
            }
        }
        ,
        ProductCategory {
            public Object get(PromoBonusImpl obj) {
                return obj.getProductCategory();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setProductCategory((String)value);
            }
        }
        ,
        ProductClass {
            public Object get(PromoBonusImpl obj) {
                return obj.getProductClass();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setProductClass((String)value);
            }
        }
        ,
        ProductBrand {
            public Object get(PromoBonusImpl obj) {
                return obj.getProductBrand();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setProductBrand((String)value);
            }
        }
        ,
        ProductExt {
            public Object get(PromoBonusImpl obj) {
                return obj.getProductExt();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setProductExt((String)value);
            }
        }
        ,
        ProductPack {
            public Object get(PromoBonusImpl obj) {
                return obj.getProductPack();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setProductPack((String)value);
            }
        }
        ,
        Uom {
            public Object get(PromoBonusImpl obj) {
                return obj.getUom();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setUom((String)value);
            }
        }
        ,
        QtyFrom {
            public Object get(PromoBonusImpl obj) {
                return obj.getQtyFrom();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setQtyFrom((Number)value);
            }
        }
        ,
        TipePotongan {
            public Object get(PromoBonusImpl obj) {
                return obj.getTipePotongan();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setTipePotongan((String)value);
            }
        }
        ,
        ValuePotongan {
            public Object get(PromoBonusImpl obj) {
                return obj.getValuePotongan();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setValuePotongan((Number)value);
            }
        }
        ,
        DiscNonYearly {
            public Object get(PromoBonusImpl obj) {
                return obj.getDiscNonYearly();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setDiscNonYearly((Number)value);
            }
        }
        ,
        DiscYearly {
            public Object get(PromoBonusImpl obj) {
                return obj.getDiscYearly();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setDiscYearly((Number)value);
            }
        }
        ,
        PromoProduk {
            public Object get(PromoBonusImpl obj) {
                return obj.getPromoProduk();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setPromoProduk((PromoProdukImpl)value);
            }
        }
        ,
        PromoBonusProdItem {
            public Object get(PromoBonusImpl obj) {
                return obj.getPromoBonusProdItem();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PromoBonusVariant {
            public Object get(PromoBonusImpl obj) {
                return obj.getPromoBonusVariant();
            }

            public void put(PromoBonusImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(PromoBonusImpl object);

        public abstract void put(PromoBonusImpl object, Object value);

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

    public static final int PROMOBONUSID = AttributesEnum.PromoBonusId.index();
    public static final int PROMOPRODUKID = AttributesEnum.PromoProdukId.index();
    public static final int PRODUCTCATEGORY = AttributesEnum.ProductCategory.index();
    public static final int PRODUCTCLASS = AttributesEnum.ProductClass.index();
    public static final int PRODUCTBRAND = AttributesEnum.ProductBrand.index();
    public static final int PRODUCTEXT = AttributesEnum.ProductExt.index();
    public static final int PRODUCTPACK = AttributesEnum.ProductPack.index();
    public static final int UOM = AttributesEnum.Uom.index();
    public static final int QTYFROM = AttributesEnum.QtyFrom.index();
    public static final int TIPEPOTONGAN = AttributesEnum.TipePotongan.index();
    public static final int VALUEPOTONGAN = AttributesEnum.ValuePotongan.index();
    public static final int DISCNONYEARLY = AttributesEnum.DiscNonYearly.index();
    public static final int DISCYEARLY = AttributesEnum.DiscYearly.index();
    public static final int PROMOPRODUK = AttributesEnum.PromoProduk.index();
    public static final int PROMOBONUSPRODITEM = AttributesEnum.PromoBonusProdItem.index();
    public static final int PROMOBONUSVARIANT = AttributesEnum.PromoBonusVariant.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PromoBonusImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("app.fpp.model.entities.promoproposal.PromoBonus");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for PromoBonusId, using the alias name PromoBonusId.
     * @return the PromoBonusId
     */
    public DBSequence getPromoBonusId() {
        return (DBSequence)getAttributeInternal(PROMOBONUSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PromoBonusId.
     * @param value value to set the PromoBonusId
     */
    public void setPromoBonusId(DBSequence value) {
        setAttributeInternal(PROMOBONUSID, value);
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
     * Gets the attribute value for ProductCategory, using the alias name ProductCategory.
     * @return the ProductCategory
     */
    public String getProductCategory() {
        return (String)getAttributeInternal(PRODUCTCATEGORY);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProductCategory.
     * @param value value to set the ProductCategory
     */
    public void setProductCategory(String value) {
        setAttributeInternal(PRODUCTCATEGORY, value);
    }

    /**
     * Gets the attribute value for ProductClass, using the alias name ProductClass.
     * @return the ProductClass
     */
    public String getProductClass() {
        return (String)getAttributeInternal(PRODUCTCLASS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProductClass.
     * @param value value to set the ProductClass
     */
    public void setProductClass(String value) {
        setAttributeInternal(PRODUCTCLASS, value);
    }

    /**
     * Gets the attribute value for ProductBrand, using the alias name ProductBrand.
     * @return the ProductBrand
     */
    public String getProductBrand() {
        return (String)getAttributeInternal(PRODUCTBRAND);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProductBrand.
     * @param value value to set the ProductBrand
     */
    public void setProductBrand(String value) {
        setAttributeInternal(PRODUCTBRAND, value);
    }

    /**
     * Gets the attribute value for ProductExt, using the alias name ProductExt.
     * @return the ProductExt
     */
    public String getProductExt() {
        return (String)getAttributeInternal(PRODUCTEXT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProductExt.
     * @param value value to set the ProductExt
     */
    public void setProductExt(String value) {
        setAttributeInternal(PRODUCTEXT, value);
    }

    /**
     * Gets the attribute value for ProductPack, using the alias name ProductPack.
     * @return the ProductPack
     */
    public String getProductPack() {
        return (String)getAttributeInternal(PRODUCTPACK);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProductPack.
     * @param value value to set the ProductPack
     */
    public void setProductPack(String value) {
        setAttributeInternal(PRODUCTPACK, value);
    }

    /**
     * Gets the attribute value for Uom, using the alias name Uom.
     * @return the Uom
     */
    public String getUom() {
        return (String)getAttributeInternal(UOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for Uom.
     * @param value value to set the Uom
     */
    public void setUom(String value) {
        setAttributeInternal(UOM, value);
    }

    /**
     * Gets the attribute value for QtyFrom, using the alias name QtyFrom.
     * @return the QtyFrom
     */
    public Number getQtyFrom() {
        return (Number)getAttributeInternal(QTYFROM);
    }

    /**
     * Sets <code>value</code> as the attribute value for QtyFrom.
     * @param value value to set the QtyFrom
     */
    public void setQtyFrom(Number value) {
        setAttributeInternal(QTYFROM, value);
    }

    /**
     * Gets the attribute value for TipePotongan, using the alias name TipePotongan.
     * @return the TipePotongan
     */
    public String getTipePotongan() {
        return (String)getAttributeInternal(TIPEPOTONGAN);
    }

    /**
     * Sets <code>value</code> as the attribute value for TipePotongan.
     * @param value value to set the TipePotongan
     */
    public void setTipePotongan(String value) {
        setAttributeInternal(TIPEPOTONGAN, value);
    }

    /**
     * Gets the attribute value for ValuePotongan, using the alias name ValuePotongan.
     * @return the ValuePotongan
     */
    public Number getValuePotongan() {
        return (Number)getAttributeInternal(VALUEPOTONGAN);
    }

    /**
     * Sets <code>value</code> as the attribute value for ValuePotongan.
     * @param value value to set the ValuePotongan
     */
    public void setValuePotongan(Number value) {
        setAttributeInternal(VALUEPOTONGAN, value);
    }

    /**
     * Gets the attribute value for DiscNonYearly, using the alias name DiscNonYearly.
     * @return the DiscNonYearly
     */
    public Number getDiscNonYearly() {
        return (Number)getAttributeInternal(DISCNONYEARLY);
    }

    /**
     * Sets <code>value</code> as the attribute value for DiscNonYearly.
     * @param value value to set the DiscNonYearly
     */
    public void setDiscNonYearly(Number value) {
        setAttributeInternal(DISCNONYEARLY, value);
    }

    /**
     * Gets the attribute value for DiscYearly, using the alias name DiscYearly.
     * @return the DiscYearly
     */
    public Number getDiscYearly() {
        return (Number)getAttributeInternal(DISCYEARLY);
    }

    /**
     * Sets <code>value</code> as the attribute value for DiscYearly.
     * @param value value to set the DiscYearly
     */
    public void setDiscYearly(Number value) {
        setAttributeInternal(DISCYEARLY, value);
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
     * @return the associated entity PromoProdukImpl.
     */
    public PromoProdukImpl getPromoProduk() {
        return (PromoProdukImpl)getAttributeInternal(PROMOPRODUK);
    }

    /**
     * Sets <code>value</code> as the associated entity PromoProdukImpl.
     */
    public void setPromoProduk(PromoProdukImpl value) {
        setAttributeInternal(PROMOPRODUK, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getPromoBonusProdItem() {
        return (RowIterator)getAttributeInternal(PROMOBONUSPRODITEM);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getPromoBonusVariant() {
        return (RowIterator)getAttributeInternal(PROMOBONUSVARIANT);
    }


    /**
     * @param promoBonusId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence promoBonusId) {
        return new Key(new Object[]{promoBonusId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        SequenceImpl seq =
            new SequenceImpl("PROMO_BONUS_SEQ", getDBTransaction());
        this.setPromoBonusId(new DBSequence(seq.getSequenceNumber()));
    }
}