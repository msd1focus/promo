package app.fpp.model.views.promoproposal.duplicate;

import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 13 14:14:04 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DuplicateProdRegionAreaViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public DuplicateProdRegionAreaViewImpl() {
    }

    /**
     * Returns the bind variable value for promoProdukId.
     * @return bind variable value for promoProdukId
     */
    public Number getpromoProdukId() {
        return (Number)getNamedWhereClauseParam("promoProdukId");
    }

    /**
     * Sets <code>value</code> for bind variable promoProdukId.
     * @param value value to bind as promoProdukId
     */
    public void setpromoProdukId(Number value) {
        setNamedWhereClauseParam("promoProdukId", value);
    }
}
