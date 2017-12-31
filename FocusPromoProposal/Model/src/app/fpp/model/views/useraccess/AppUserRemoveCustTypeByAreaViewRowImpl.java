package app.fpp.model.views.useraccess;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Dec 29 15:04:01 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppUserRemoveCustTypeByAreaViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CustType {
            public Object get(AppUserRemoveCustTypeByAreaViewRowImpl obj) {
                return obj.getCustType();
            }

            public void put(AppUserRemoveCustTypeByAreaViewRowImpl obj,
                            Object value) {
                obj.setCustType((String)value);
            }
        }
        ,
        UserName {
            public Object get(AppUserRemoveCustTypeByAreaViewRowImpl obj) {
                return obj.getUserName();
            }

            public void put(AppUserRemoveCustTypeByAreaViewRowImpl obj,
                            Object value) {
                obj.setUserName((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(AppUserRemoveCustTypeByAreaViewRowImpl object);

        public abstract void put(AppUserRemoveCustTypeByAreaViewRowImpl object,
                                 Object value);

        public int index() {
            return AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex() + AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int CUSTTYPE = AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.CustType.index();
    public static final int USERNAME = AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.UserName.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppUserRemoveCustTypeByAreaViewRowImpl() {
    }

    /**
     * Gets AppUserCustType entity object.
     * @return the AppUserCustType
     */
    public EntityImpl getAppUserCustType() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets ArCustomers entity object.
     * @return the ArCustomers
     */
    public EntityImpl getArCustomers() {
        return (EntityImpl)getEntity(1);
    }

    /**
     * Gets the attribute value for CUST_TYPE using the alias name CustType.
     * @return the CUST_TYPE
     */
    public String getCustType() {
        return (String) getAttributeInternal(CUSTTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CUST_TYPE using the alias name CustType.
     * @param value value to set the CUST_TYPE
     */
    public void setCustType(String value) {
        setAttributeInternal(CUSTTYPE, value);
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
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex()) && (index < AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.count())) {
            return AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.staticValues()[index - AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex()].get(this);
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
        if ((index >= AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex()) && (index < AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.count())) {
            AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.staticValues()[index - AppUserRemoveCustTypeByAreaViewRowImpl.AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}