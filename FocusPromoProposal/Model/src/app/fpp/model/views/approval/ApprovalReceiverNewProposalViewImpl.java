package app.fpp.model.views.approval;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jul 07 02:59:22 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprovalReceiverNewProposalViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ApprovalReceiverNewProposalViewImpl() {
    }

    /**
     * Returns the bind variable value for aprvlFlowNm.
     * @return bind variable value for aprvlFlowNm
     */
    public String getaprvlFlowNm() {
        return (String)getNamedWhereClauseParam("aprvlFlowNm");
    }

    /**
     * Sets <code>value</code> for bind variable aprvlFlowNm.
     * @param value value to bind as aprvlFlowNm
     */
    public void setaprvlFlowNm(String value) {
        setNamedWhereClauseParam("aprvlFlowNm", value);
    }

    /**
     * Returns the bind variable value for usrRole.
     * @return bind variable value for usrRole
     */
    public String getusrRole() {
        return (String)getNamedWhereClauseParam("usrRole");
    }

    /**
     * Sets <code>value</code> for bind variable usrRole.
     * @param value value to bind as usrRole
     */
    public void setusrRole(String value) {
        setNamedWhereClauseParam("usrRole", value);
    }
}