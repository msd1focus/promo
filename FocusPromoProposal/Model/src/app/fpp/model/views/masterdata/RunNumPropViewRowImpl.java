package app.fpp.model.views.masterdata;

import app.fpp.model.entities.useraccess.AppUserAccessImpl;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jun 29 22:03:37 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class RunNumPropViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        RunNumType {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getRunNumType();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setRunNumType((String)value);
            }
        }
        ,
        UserName {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getUserName();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setUserName((String)value);
            }
        }
        ,
        RunYear {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getRunYear();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setRunYear((Number)value);
            }
        }
        ,
        RunMonth {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getRunMonth();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setRunMonth((Number)value);
            }
        }
        ,
        Value {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getValue();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setValue((Number)value);
            }
        }
        ,
        Id {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getId();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setId((DBSequence)value);
            }
        }
        ,
        UserInitial {
            public Object get(RunNumPropViewRowImpl obj) {
                return obj.getUserInitial();
            }

            public void put(RunNumPropViewRowImpl obj, Object value) {
                obj.setUserInitial((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(RunNumPropViewRowImpl object);

        public abstract void put(RunNumPropViewRowImpl object, Object value);

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


    public static final int RUNNUMTYPE = AttributesEnum.RunNumType.index();
    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int RUNYEAR = AttributesEnum.RunYear.index();
    public static final int RUNMONTH = AttributesEnum.RunMonth.index();
    public static final int VALUE = AttributesEnum.Value.index();
    public static final int ID = AttributesEnum.Id.index();
    public static final int USERINITIAL = AttributesEnum.UserInitial.index();

    /**
     * This is the default constructor (do not remove).
     */
    public RunNumPropViewRowImpl() {
    }

    /**
     * Gets RunNumber entity object.
     * @return the RunNumber
     */
    public EntityImpl getRunNumber() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets AppUserAccess entity object.
     * @return the AppUserAccess
     */
    public AppUserAccessImpl getAppUserAccess() {
        return (AppUserAccessImpl)getEntity(1);
    }

    /**
     * Gets the attribute value for RUN_MONTH using the alias name RunMonth.
     * @return the RUN_MONTH
     */
    public Number getRunMonth() {
        return (Number) getAttributeInternal(RUNMONTH);
    }

    /**
     * Sets <code>value</code> as attribute value for RUN_MONTH using the alias name RunMonth.
     * @param value value to set the RUN_MONTH
     */
    public void setRunMonth(Number value) {
        setAttributeInternal(RUNMONTH, value);
    }

    /**
     * Gets the attribute value for RUN_NUM_TYPE using the alias name RunNumType.
     * @return the RUN_NUM_TYPE
     */
    public String getRunNumType() {
        return (String) getAttributeInternal(RUNNUMTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for RUN_NUM_TYPE using the alias name RunNumType.
     * @param value value to set the RUN_NUM_TYPE
     */
    public void setRunNumType(String value) {
        setAttributeInternal(RUNNUMTYPE, value);
    }

    /**
     * Gets the attribute value for USER_NAME using the alias name UserName.
     * @return the USER_NAME
     */
    public String getUserName() {
        return (String) getAttributeInternal(USERNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_NAME using the alias name UserName.
     * @param value value to set the USER_NAME
     */
    public void setUserName(String value) {
        setAttributeInternal(USERNAME, value);
    }

    /**
     * Gets the attribute value for RUN_YEAR using the alias name RunYear.
     * @return the RUN_YEAR
     */
    public Number getRunYear() {
        return (Number) getAttributeInternal(RUNYEAR);
    }

    /**
     * Sets <code>value</code> as attribute value for RUN_YEAR using the alias name RunYear.
     * @param value value to set the RUN_YEAR
     */
    public void setRunYear(Number value) {
        setAttributeInternal(RUNYEAR, value);
    }


    /**
     * Gets the attribute value for VALUE using the alias name Value.
     * @return the VALUE
     */
    public Number getValue() {
        return (Number) getAttributeInternal(VALUE);
    }

    /**
     * Sets <code>value</code> as attribute value for VALUE using the alias name Value.
     * @param value value to set the VALUE
     */
    public void setValue(Number value) {
        setAttributeInternal(VALUE, value);
    }

    /**
     * Gets the attribute value for ID using the alias name Id.
     * @return the ID
     */
    public DBSequence getId() {
        return (DBSequence)getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as attribute value for ID using the alias name Id.
     * @param value value to set the ID
     */
    public void setId(DBSequence value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for USER_INITIAL using the alias name UserInitial.
     * @return the USER_INITIAL
     */
    public String getUserInitial() {
        return (String) getAttributeInternal(USERINITIAL);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_INITIAL using the alias name UserInitial.
     * @param value value to set the USER_INITIAL
     */
    public void setUserInitial(String value) {
        setAttributeInternal(USERINITIAL, value);
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
