package app.fpp.model.views.confirmation.targetpr;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 03 10:24:29 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FcsCheckDcvPrClosedViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FcsCheckDcvPrClosedViewImpl() {
    }

    /**
     * Returns the bind variable value for noConfirm.
     * @return bind variable value for noConfirm
     */
    public String getnoConfirm() {
        return (String)getNamedWhereClauseParam("noConfirm");
    }

    /**
     * Sets <code>value</code> for bind variable noConfirm.
     * @param value value to bind as noConfirm
     */
    public void setnoConfirm(String value) {
        setNamedWhereClauseParam("noConfirm", value);
    }
}
