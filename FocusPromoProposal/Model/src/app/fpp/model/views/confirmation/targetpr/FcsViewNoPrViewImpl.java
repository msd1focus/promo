package app.fpp.model.views.confirmation.targetpr;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Sep 10 07:00:35 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FcsViewNoPrViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FcsViewNoPrViewImpl() {
    }

    /**
     * Returns the bind variable value for noPr.
     * @return bind variable value for noPr
     */
    public String getnoPr() {
        return (String)getNamedWhereClauseParam("noPr");
    }

    /**
     * Sets <code>value</code> for bind variable noPr.
     * @param value value to bind as noPr
     */
    public void setnoPr(String value) {
        setNamedWhereClauseParam("noPr", value);
    }
}
