package app.fpp.model.views.approval;

import app.fpp.model.entities.approval.DocApprovalImpl;
import app.fpp.model.entities.approvalsetting.ApprovalFlowImpl;
import app.fpp.model.entities.approvalsetting.ApprovalStepsImpl;

import app.fpp.model.entities.useraccess.AppUserAccessImpl;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jul 12 10:57:34 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprovalReceiverRejectProposalViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        IdAprvlFlow {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getIdAprvlFlow();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setIdAprvlFlow((DBSequence)value);
            }
        }
        ,
        IdAprvlStep {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getIdAprvlStep();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setIdAprvlStep((DBSequence)value);
            }
        }
        ,
        AprvlFlowNm {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getAprvlFlowNm();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setAprvlFlowNm((String)value);
            }
        }
        ,
        AprvlCode {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getAprvlCode();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setAprvlCode((String)value);
            }
        }
        ,
        RoleName {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getRoleName();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setRoleName((String)value);
            }
        }
        ,
        StepSequence {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getStepSequence();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setStepSequence((Number)value);
            }
        }
        ,
        UserName {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getUserName();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setUserName((String)value);
            }
        }
        ,
        Role {
            public Object get(ApprovalReceiverRejectProposalViewRowImpl obj) {
                return obj.getRole();
            }

            public void put(ApprovalReceiverRejectProposalViewRowImpl obj,
                            Object value) {
                obj.setRole((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(ApprovalReceiverRejectProposalViewRowImpl object);

        public abstract void put(ApprovalReceiverRejectProposalViewRowImpl object,
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


    public static final int IDAPRVLFLOW = AttributesEnum.IdAprvlFlow.index();
    public static final int IDAPRVLSTEP = AttributesEnum.IdAprvlStep.index();
    public static final int APRVLFLOWNM = AttributesEnum.AprvlFlowNm.index();
    public static final int APRVLCODE = AttributesEnum.AprvlCode.index();
    public static final int ROLENAME = AttributesEnum.RoleName.index();
    public static final int STEPSEQUENCE = AttributesEnum.StepSequence.index();
    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int ROLE = AttributesEnum.Role.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ApprovalReceiverRejectProposalViewRowImpl() {
    }

    /**
     * Gets ApprovalFlow entity object.
     * @return the ApprovalFlow
     */
    public ApprovalFlowImpl getApprovalFlow() {
        return (ApprovalFlowImpl)getEntity(0);
    }

    /**
     * Gets ApprovalSteps entity object.
     * @return the ApprovalSteps
     */
    public ApprovalStepsImpl getApprovalSteps() {
        return (ApprovalStepsImpl)getEntity(1);
    }


    /**
     * Gets AppUserAccess entity object.
     * @return the AppUserAccess
     */
    public AppUserAccessImpl getAppUserAccess() {
        return (AppUserAccessImpl)getEntity(2);
    }

    /**
     * Gets AppUserAccessRoles entity object.
     * @return the AppUserAccessRoles
     */
    public EntityImpl getAppUserAccessRoles() {
        return (EntityImpl)getEntity(3);
    }

    /**
     * Gets DocApproval entity object.
     * @return the DocApproval
     */
    public DocApprovalImpl getDocApproval() {
        return (DocApprovalImpl)getEntity(4);
    }

    /**
     * Gets the attribute value for ID_APRVL_FLOW using the alias name IdAprvlFlow.
     * @return the ID_APRVL_FLOW
     */
    public DBSequence getIdAprvlFlow() {
        return (DBSequence)getAttributeInternal(IDAPRVLFLOW);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_APRVL_FLOW using the alias name IdAprvlFlow.
     * @param value value to set the ID_APRVL_FLOW
     */
    public void setIdAprvlFlow(DBSequence value) {
        setAttributeInternal(IDAPRVLFLOW, value);
    }

    /**
     * Gets the attribute value for ID_APRVL_STEP using the alias name IdAprvlStep.
     * @return the ID_APRVL_STEP
     */
    public DBSequence getIdAprvlStep() {
        return (DBSequence)getAttributeInternal(IDAPRVLSTEP);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_APRVL_STEP using the alias name IdAprvlStep.
     * @param value value to set the ID_APRVL_STEP
     */
    public void setIdAprvlStep(DBSequence value) {
        setAttributeInternal(IDAPRVLSTEP, value);
    }

    /**
     * Gets the attribute value for APRVL_FLOW_NM using the alias name AprvlFlowNm.
     * @return the APRVL_FLOW_NM
     */
    public String getAprvlFlowNm() {
        return (String) getAttributeInternal(APRVLFLOWNM);
    }

    /**
     * Sets <code>value</code> as attribute value for APRVL_FLOW_NM using the alias name AprvlFlowNm.
     * @param value value to set the APRVL_FLOW_NM
     */
    public void setAprvlFlowNm(String value) {
        setAttributeInternal(APRVLFLOWNM, value);
    }

    /**
     * Gets the attribute value for APRVL_CODE using the alias name AprvlCode.
     * @return the APRVL_CODE
     */
    public String getAprvlCode() {
        return (String) getAttributeInternal(APRVLCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for APRVL_CODE using the alias name AprvlCode.
     * @param value value to set the APRVL_CODE
     */
    public void setAprvlCode(String value) {
        setAttributeInternal(APRVLCODE, value);
    }

    /**
     * Gets the attribute value for ROLE_NAME using the alias name RoleName.
     * @return the ROLE_NAME
     */
    public String getRoleName() {
        return (String) getAttributeInternal(ROLENAME);
    }

    /**
     * Sets <code>value</code> as attribute value for ROLE_NAME using the alias name RoleName.
     * @param value value to set the ROLE_NAME
     */
    public void setRoleName(String value) {
        setAttributeInternal(ROLENAME, value);
    }

    /**
     * Gets the attribute value for STEP_SEQUENCE using the alias name StepSequence.
     * @return the STEP_SEQUENCE
     */
    public Number getStepSequence() {
        return (Number) getAttributeInternal(STEPSEQUENCE);
    }

    /**
     * Sets <code>value</code> as attribute value for STEP_SEQUENCE using the alias name StepSequence.
     * @param value value to set the STEP_SEQUENCE
     */
    public void setStepSequence(Number value) {
        setAttributeInternal(STEPSEQUENCE, value);
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
     * Gets the attribute value for ROLE using the alias name Role.
     * @return the ROLE
     */
    public String getRole() {
        return (String) getAttributeInternal(ROLE);
    }

    /**
     * Sets <code>value</code> as attribute value for ROLE using the alias name Role.
     * @param value value to set the ROLE
     */
    public void setRole(String value) {
        setAttributeInternal(ROLE, value);
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
