package app.fpp.model.views.confirmation.modifier;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Nov 23 18:37:40 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FcsModifierAreaExclViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FcsModifierAreaExclViewImpl() {
    }

    /**
     * Returns the bind variable value for promoProdId.
     * @return bind variable value for promoProdId
     */
    public String getpromoProdId() {
        return (String)getNamedWhereClauseParam("promoProdId");
    }

    /**
     * Sets <code>value</code> for bind variable promoProdId.
     * @param value value to bind as promoProdId
     */
    public void setpromoProdId(String value) {
        setNamedWhereClauseParam("promoProdId", value);
    }
}
