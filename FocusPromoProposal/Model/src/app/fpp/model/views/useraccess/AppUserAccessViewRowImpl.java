package app.fpp.model.views.useraccess;

import app.fpp.model.entities.useraccess.AppUserAccessImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 28 17:58:04 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppUserAccessViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Id {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getId();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setId((DBSequence)value);
            }
        }
        ,
        UserName {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getUserName();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setUserName((String)value);
            }
        }
        ,
        UserInitial {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getUserInitial();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setUserInitial((String)value);
            }
        }
        ,
        Password {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getPassword();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setPassword((String)value);
            }
        }
        ,
        FullName {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getFullName();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setFullName((String)value);
            }
        }
        ,
        ContactNo {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getContactNo();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setContactNo((String)value);
            }
        }
        ,
        Title {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getTitle();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setTitle((String)value);
            }
        }
        ,
        Descr {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getDescr();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setDescr((String)value);
            }
        }
        ,
        CompanyId {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getCompanyId();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setCompanyId((String)value);
            }
        }
        ,
        ActivePeriodFrom {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getActivePeriodFrom();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setActivePeriodFrom((Date)value);
            }
        }
        ,
        ActivePeriodTo {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getActivePeriodTo();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setActivePeriodTo((Date)value);
            }
        }
        ,
        UserType {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getUserType();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setUserType((String)value);
            }
        }
        ,
        UserDivision {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getUserDivision();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setUserDivision((String)value);
            }
        }
        ,
        DirectSpv {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getDirectSpv();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setDirectSpv((String)value);
            }
        }
        ,
        DirectSpvDesc {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getDirectSpvDesc();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setDirectSpvDesc((String)value);
            }
        }
        ,
        AppUserAccessRolesView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserAccessRolesView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserRegionView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserRegionView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserAreaView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserAreaView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserCustView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserCustView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserLocView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserLocView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserCustGroupView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserCustGroupView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppUserCustTypeView {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getAppUserCustTypeView();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovUserTitle {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getLovUserTitle();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovCompany {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getLovCompany();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovUserType {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getLovUserType();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovUserDivision {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getLovUserDivision();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovUserCustPriv {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getLovUserCustPriv();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DirectSpvLov {
            public Object get(AppUserAccessViewRowImpl obj) {
                return obj.getDirectSpvLov();
            }

            public void put(AppUserAccessViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(AppUserAccessViewRowImpl object);

        public abstract void put(AppUserAccessViewRowImpl object,
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

    public static final int ID = AttributesEnum.Id.index();
    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int USERINITIAL = AttributesEnum.UserInitial.index();
    public static final int PASSWORD = AttributesEnum.Password.index();
    public static final int FULLNAME = AttributesEnum.FullName.index();
    public static final int CONTACTNO = AttributesEnum.ContactNo.index();
    public static final int TITLE = AttributesEnum.Title.index();
    public static final int DESCR = AttributesEnum.Descr.index();
    public static final int COMPANYID = AttributesEnum.CompanyId.index();
    public static final int ACTIVEPERIODFROM = AttributesEnum.ActivePeriodFrom.index();
    public static final int ACTIVEPERIODTO = AttributesEnum.ActivePeriodTo.index();
    public static final int USERTYPE = AttributesEnum.UserType.index();
    public static final int USERDIVISION = AttributesEnum.UserDivision.index();
    public static final int DIRECTSPV = AttributesEnum.DirectSpv.index();
    public static final int DIRECTSPVDESC = AttributesEnum.DirectSpvDesc.index();
    public static final int APPUSERACCESSROLESVIEW = AttributesEnum.AppUserAccessRolesView.index();
    public static final int APPUSERREGIONVIEW = AttributesEnum.AppUserRegionView.index();
    public static final int APPUSERAREAVIEW = AttributesEnum.AppUserAreaView.index();
    public static final int APPUSERCUSTVIEW = AttributesEnum.AppUserCustView.index();
    public static final int APPUSERLOCVIEW = AttributesEnum.AppUserLocView.index();
    public static final int APPUSERCUSTGROUPVIEW = AttributesEnum.AppUserCustGroupView.index();
    public static final int APPUSERCUSTTYPEVIEW = AttributesEnum.AppUserCustTypeView.index();
    public static final int LOVUSERTITLE = AttributesEnum.LovUserTitle.index();
    public static final int LOVCOMPANY = AttributesEnum.LovCompany.index();
    public static final int LOVUSERTYPE = AttributesEnum.LovUserType.index();
    public static final int LOVUSERDIVISION = AttributesEnum.LovUserDivision.index();
    public static final int LOVUSERCUSTPRIV = AttributesEnum.LovUserCustPriv.index();
    public static final int DIRECTSPVLOV = AttributesEnum.DirectSpvLov.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppUserAccessViewRowImpl() {
    }

    /**
     * Gets AppUserAccess entity object.
     * @return the AppUserAccess
     */
    public AppUserAccessImpl getAppUserAccess() {
        return (AppUserAccessImpl)getEntity(0);
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
     * Gets the attribute value for PASSWORD using the alias name Password.
     * @return the PASSWORD
     */
    public String getPassword() {
        return (String) getAttributeInternal(PASSWORD);
    }

    /**
     * Sets <code>value</code> as attribute value for PASSWORD using the alias name Password.
     * @param value value to set the PASSWORD
     */
    public void setPassword(String value) {
        setAttributeInternal(PASSWORD, value);
    }

    /**
     * Gets the attribute value for FULL_NAME using the alias name FullName.
     * @return the FULL_NAME
     */
    public String getFullName() {
        return (String) getAttributeInternal(FULLNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for FULL_NAME using the alias name FullName.
     * @param value value to set the FULL_NAME
     */
    public void setFullName(String value) {
        setAttributeInternal(FULLNAME, value);
    }

    /**
     * Gets the attribute value for CONTACT_NO using the alias name ContactNo.
     * @return the CONTACT_NO
     */
    public String getContactNo() {
        return (String) getAttributeInternal(CONTACTNO);
    }

    /**
     * Sets <code>value</code> as attribute value for CONTACT_NO using the alias name ContactNo.
     * @param value value to set the CONTACT_NO
     */
    public void setContactNo(String value) {
        setAttributeInternal(CONTACTNO, value);
    }

    /**
     * Gets the attribute value for TITLE using the alias name Title.
     * @return the TITLE
     */
    public String getTitle() {
        return (String) getAttributeInternal(TITLE);
    }

    /**
     * Sets <code>value</code> as attribute value for TITLE using the alias name Title.
     * @param value value to set the TITLE
     */
    public void setTitle(String value) {
        setAttributeInternal(TITLE, value);
    }

    /**
     * Gets the attribute value for DESCR using the alias name Descr.
     * @return the DESCR
     */
    public String getDescr() {
        return (String) getAttributeInternal(DESCR);
    }

    /**
     * Sets <code>value</code> as attribute value for DESCR using the alias name Descr.
     * @param value value to set the DESCR
     */
    public void setDescr(String value) {
        setAttributeInternal(DESCR, value);
    }

    /**
     * Gets the attribute value for COMPANY_ID using the alias name CompanyId.
     * @return the COMPANY_ID
     */
    public String getCompanyId() {
        return (String) getAttributeInternal(COMPANYID);
    }

    /**
     * Sets <code>value</code> as attribute value for COMPANY_ID using the alias name CompanyId.
     * @param value value to set the COMPANY_ID
     */
    public void setCompanyId(String value) {
        setAttributeInternal(COMPANYID, value);
    }

    /**
     * Gets the attribute value for ACTIVE_PERIOD_FROM using the alias name ActivePeriodFrom.
     * @return the ACTIVE_PERIOD_FROM
     */
    public Date getActivePeriodFrom() {
        return (Date) getAttributeInternal(ACTIVEPERIODFROM);
    }

    /**
     * Sets <code>value</code> as attribute value for ACTIVE_PERIOD_FROM using the alias name ActivePeriodFrom.
     * @param value value to set the ACTIVE_PERIOD_FROM
     */
    public void setActivePeriodFrom(Date value) {
        setAttributeInternal(ACTIVEPERIODFROM, value);
    }

    /**
     * Gets the attribute value for ACTIVE_PERIOD_TO using the alias name ActivePeriodTo.
     * @return the ACTIVE_PERIOD_TO
     */
    public Date getActivePeriodTo() {
        return (Date) getAttributeInternal(ACTIVEPERIODTO);
    }

    /**
     * Sets <code>value</code> as attribute value for ACTIVE_PERIOD_TO using the alias name ActivePeriodTo.
     * @param value value to set the ACTIVE_PERIOD_TO
     */
    public void setActivePeriodTo(Date value) {
        setAttributeInternal(ACTIVEPERIODTO, value);
    }

    /**
     * Gets the attribute value for USER_TYPE using the alias name UserType.
     * @return the USER_TYPE
     */
    public String getUserType() {
        return (String) getAttributeInternal(USERTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_TYPE using the alias name UserType.
     * @param value value to set the USER_TYPE
     */
    public void setUserType(String value) {
        setAttributeInternal(USERTYPE, value);
    }

    /**
     * Gets the attribute value for USER_DIVISION using the alias name UserDivision.
     * @return the USER_DIVISION
     */
    public String getUserDivision() {
        return (String) getAttributeInternal(USERDIVISION);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_DIVISION using the alias name UserDivision.
     * @param value value to set the USER_DIVISION
     */
    public void setUserDivision(String value) {
        setAttributeInternal(USERDIVISION, value);
    }

    /**
     * Gets the attribute value for DIRECT_SPV using the alias name DirectSpv.
     * @return the DIRECT_SPV
     */
    public String getDirectSpv() {
        return (String) getAttributeInternal(DIRECTSPV);
    }

    /**
     * Sets <code>value</code> as attribute value for DIRECT_SPV using the alias name DirectSpv.
     * @param value value to set the DIRECT_SPV
     */
    public void setDirectSpv(String value) {
        setAttributeInternal(DIRECTSPV, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DirectSpvDesc.
     * @return the DirectSpvDesc
     */
    public String getDirectSpvDesc() {
        String directSpv = null;
        String directSpvDesc = null;
        if (getDirectSpv() != null) {
            directSpv = getDirectSpv();
            Row[] directSpvRows;
            directSpvRows = this.getDirectSpvLov().getFilteredRows("UserName", directSpv);
            if (directSpvRows.length > 0) {
                directSpvDesc = (directSpvRows[0].getAttribute("FullName").toString()).toUpperCase();
            }
            return directSpvDesc;
        } else {
        return (String) getAttributeInternal(DIRECTSPVDESC);
        }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DirectSpvDesc.
     * @param value value to set the  DirectSpvDesc
     */
    public void setDirectSpvDesc(String value) {
        setAttributeInternal(DIRECTSPVDESC, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserAccessRolesView.
     */
    public RowIterator getAppUserAccessRolesView() {
        return (RowIterator)getAttributeInternal(APPUSERACCESSROLESVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserRegionView.
     */
    public RowIterator getAppUserRegionView() {
        return (RowIterator)getAttributeInternal(APPUSERREGIONVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserAreaView.
     */
    public RowIterator getAppUserAreaView() {
        return (RowIterator)getAttributeInternal(APPUSERAREAVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserCustView.
     */
    public RowIterator getAppUserCustView() {
        return (RowIterator)getAttributeInternal(APPUSERCUSTVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserLocView.
     */
    public RowIterator getAppUserLocView() {
        return (RowIterator)getAttributeInternal(APPUSERLOCVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserCustGroupView.
     */
    public RowIterator getAppUserCustGroupView() {
        return (RowIterator)getAttributeInternal(APPUSERCUSTGROUPVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link AppUserCustTypeView.
     */
    public RowIterator getAppUserCustTypeView() {
        return (RowIterator)getAttributeInternal(APPUSERCUSTTYPEVIEW);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUserTitle.
     */
    public RowSet getLovUserTitle() {
        return (RowSet)getAttributeInternal(LOVUSERTITLE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovCompany.
     */
    public RowSet getLovCompany() {
        return (RowSet)getAttributeInternal(LOVCOMPANY);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUserType.
     */
    public RowSet getLovUserType() {
        return (RowSet)getAttributeInternal(LOVUSERTYPE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUserDivision.
     */
    public RowSet getLovUserDivision() {
        return (RowSet)getAttributeInternal(LOVUSERDIVISION);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUserCustPriv.
     */
    public RowSet getLovUserCustPriv() {
        return (RowSet)getAttributeInternal(LOVUSERCUSTPRIV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> DirectSpvLov.
     */
    public RowSet getDirectSpvLov() {
        return (RowSet)getAttributeInternal(DIRECTSPVLOV);
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
