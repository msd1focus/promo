package app.fpp.model.views.useraccess;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 24 17:46:10 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppUserRemoveLocByRegionViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppUserRemoveLocByRegionViewImpl() {
    }

    /**
     * Returns the bind variable value for userName.
     * @return bind variable value for userName
     */
    public String getuserName() {
        return (String)getNamedWhereClauseParam("userName");
    }

    /**
     * Sets <code>value</code> for bind variable userName.
     * @param value value to bind as userName
     */
    public void setuserName(String value) {
        setNamedWhereClauseParam("userName", value);
    }
}
