package app.fpp.model.views.approval;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jul 12 10:57:23 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprovalReceiverApproveProposalViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ApprovalReceiverApproveProposalViewImpl() {
    }

    /**
     * Returns the bind variable value for aprvlCode.
     * @return bind variable value for aprvlCode
     */
    public String getaprvlCode() {
        return (String)getNamedWhereClauseParam("aprvlCode");
    }

    /**
     * Sets <code>value</code> for bind variable aprvlCode.
     * @param value value to bind as aprvlCode
     */
    public void setaprvlCode(String value) {
        setNamedWhereClauseParam("aprvlCode", value);
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
