package app.fpp.model.am;

import app.fpp.model.am.common.UserAccessAM;
import app.fpp.model.views.useraccess.AppUserAccessChangePasswordViewImpl;
import app.fpp.model.views.useraccess.AppUserAccessViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveAreaByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByCustGroupViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByCustTypeViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByLocViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByCustTypeViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByLocViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustTypeByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustTypeByLocViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustTypeByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveLocByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveLocByRegionViewImpl;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jul 22 07:05:35 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class UserAccessAMImpl extends ApplicationModuleImpl implements UserAccessAM {
    /**
     * This is the default constructor (do not remove).
     */
    public UserAccessAMImpl() {
    }

    public void changePassword(String userId, String newPassword) {
        ViewObjectImpl user = this.getAppUserAccessView1();
        ViewCriteria uc = user.createViewCriteria();
        ViewCriteriaRow userVcr = uc.createViewCriteriaRow();
        userVcr.setAttribute("UserName", "=" + userId);
        uc.addElement(userVcr);
        user.applyViewCriteria(uc);
        user.executeQuery();

        Row userRow = user.first();
        userRow.setAttribute("Password", newPassword);
        getDBTransaction().commit();
    }
    
    /**
     * Container's getter for AppUserAccessView1.
     * @return AppUserAccessView1
     */
    public ViewObjectImpl getAppUserAccessView1() {
        return (ViewObjectImpl)findViewObject("AppUserAccessView1");
    }

    /**
     * Container's getter for AppRolesView1.
     * @return AppRolesView1
     */
    public ViewObjectImpl getAppRolesView1() {
        return (ViewObjectImpl)findViewObject("AppRolesView1");
    }

    /**
     * Container's getter for AppMenuView1.
     * @return AppMenuView1
     */
    public ViewObjectImpl getAppMenuView1() {
        return (ViewObjectImpl)findViewObject("AppMenuView1");
    }

    /**
     * Container's getter for AppMenuItemsView1.
     * @return AppMenuItemsView1
     */
    public ViewObjectImpl getAppMenuItemsView1() {
        return (ViewObjectImpl)findViewObject("AppMenuItemsView1");
    }

    /**
     * Container's getter for AllRolesShuttleView1.
     * @return AllRolesShuttleView1
     */
    public ViewObjectImpl getAllRolesShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllRolesShuttleView1");
    }

    /**
     * Container's getter for AppUserAccessRolesView1.
     * @return AppUserAccessRolesView1
     */
    public ViewObjectImpl getAppUserAccessRolesView1() {
        return (ViewObjectImpl)findViewObject("AppUserAccessRolesView1");
    }

    /**
     * Container's getter for AppUserRegionView1.
     * @return AppUserRegionView1
     */
    public ViewObjectImpl getAppUserRegionView1() {
        return (ViewObjectImpl)findViewObject("AppUserRegionView1");
    }

    /**
     * Container's getter for AllRegionShuttleView1.
     * @return AllRegionShuttleView1
     */
    public ViewObjectImpl getAllRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllRegionShuttleView1");
    }

    /**
     * Container's getter for AppRoleMenuItemsView1.
     * @return AppRoleMenuItemsView1
     */
    public ViewObjectImpl getAppRoleMenuItemsView1() {
        return (ViewObjectImpl)findViewObject("AppRoleMenuItemsView1");
    }

    /**
     * Container's getter for AllMenuItemShuttleView1.
     * @return AllMenuItemShuttleView1
     */
    public ViewObjectImpl getAllMenuItemShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllMenuItemShuttleView1");
    }

    /**
     * Container's getter for AppMenuItemsAppMenuFk1Link1.
     * @return AppMenuItemsAppMenuFk1Link1
     */
    public ViewLinkImpl getAppMenuItemsAppMenuFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppMenuItemsAppMenuFk1Link1");
    }

    /**
     * Container's getter for AppUserAccessRolesFk1Link1.
     * @return AppUserAccessRolesFk1Link1
     */
    public ViewLinkImpl getAppUserAccessRolesFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserAccessRolesFk1Link1");
    }

    /**
     * Container's getter for AppUserAccessRegionFk1Link1.
     * @return AppUserAccessRegionFk1Link1
     */
    public ViewLinkImpl getAppUserAccessRegionFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserAccessRegionFk1Link1");
    }

    /**
     * Container's getter for AppRoleMenuItemsAppRFk2Link1.
     * @return AppRoleMenuItemsAppRFk2Link1
     */
    public ViewLinkImpl getAppRoleMenuItemsAppRFk2Link1() {
        return (ViewLinkImpl)findViewLink("AppRoleMenuItemsAppRFk2Link1");
    }

    /**
     * Container's getter for AppUserAccessChangePasswordView1.
     * @return AppUserAccessChangePasswordView1
     */
    public AppUserAccessChangePasswordViewImpl getAppUserAccessChangePasswordView1() {
        return (AppUserAccessChangePasswordViewImpl)findViewObject("AppUserAccessChangePasswordView1");
    }

    /**
     * Container's getter for AppUserAreaView1.
     * @return AppUserAreaView1
     */
    public ViewObjectImpl getAppUserAreaView1() {
        return (ViewObjectImpl)findViewObject("AppUserAreaView1");
    }

    /**
     * Container's getter for AppUserAreaUserAccessFk1Link1.
     * @return AppUserAreaUserAccessFk1Link1
     */
    public ViewLinkImpl getAppUserAreaUserAccessFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserAreaUserAccessFk1Link1");
    }

    /**
     * Container's getter for AppUserCustView1.
     * @return AppUserCustView1
     */
    public ViewObjectImpl getAppUserCustView1() {
        return (ViewObjectImpl)findViewObject("AppUserCustView1");
    }

    /**
     * Container's getter for AppUserCustUserAccessFk1Link1.
     * @return AppUserCustUserAccessFk1Link1
     */
    public ViewLinkImpl getAppUserCustUserAccessFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserCustUserAccessFk1Link1");
    }

    /**
     * Container's getter for AppUserLocView1.
     * @return AppUserLocView1
     */
    public ViewObjectImpl getAppUserLocView1() {
        return (ViewObjectImpl)findViewObject("AppUserLocView1");
    }

    /**
     * Container's getter for AppUserLocUserAccessFk1Link1.
     * @return AppUserLocUserAccessFk1Link1
     */
    public ViewLinkImpl getAppUserLocUserAccessFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserLocUserAccessFk1Link1");
    }

    /**
     * Container's getter for AllUserMgmtAreaShuttleView1.
     * @return AllUserMgmtAreaShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtAreaShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtAreaShuttleView1");
    }

    /**
     * Container's getter for AllUserMgmtCustomerShuttleView1.
     * @return AllUserMgmtCustomerShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtCustomerShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtCustomerShuttleView1");
    }

    /**
     * Container's getter for AllUserMgmtLocationShuttleView1.
     * @return AllUserMgmtLocationShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtLocationShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtLocationShuttleView1");
    }

    /**
     * Container's getter for AllUserMgmtRegionShuttleView1.
     * @return AllUserMgmtRegionShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtRegionShuttleView1");
    }

    /**
     * Container's getter for AppUserCustGroupView1.
     * @return AppUserCustGroupView1
     */
    public ViewObjectImpl getAppUserCustGroupView1() {
        return (ViewObjectImpl)findViewObject("AppUserCustGroupView1");
    }

    /**
     * Container's getter for AppUserCustGroupUserAccessFk1Link1.
     * @return AppUserCustGroupUserAccessFk1Link1
     */
    public ViewLinkImpl getAppUserCustGroupUserAccessFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserCustGroupUserAccessFk1Link1");
    }

    /**
     * Container's getter for AllUserMgmtCustGroupShuttleView1.
     * @return AllUserMgmtCustGroupShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtCustGroupShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtCustGroupShuttleView1");
    }


    /**
     * Container's getter for AppUserRemoveAreaByRegionView1.
     * @return AppUserRemoveAreaByRegionView1
     */
    public AppUserRemoveAreaByRegionViewImpl getAppUserRemoveAreaByRegionView1() {
        return (AppUserRemoveAreaByRegionViewImpl)findViewObject("AppUserRemoveAreaByRegionView1");
    }

    /**
     * Container's getter for AppUserRemoveCustByRegionView1.
     * @return AppUserRemoveCustByRegionView1
     */
    public AppUserRemoveCustByRegionViewImpl getAppUserRemoveCustByRegionView1() {
        return (AppUserRemoveCustByRegionViewImpl)findViewObject("AppUserRemoveCustByRegionView1");
    }

    /**
     * Container's getter for AppUserRemoveCustGroupByRegionView1.
     * @return AppUserRemoveCustGroupByRegionView1
     */
    public AppUserRemoveCustGroupByRegionViewImpl getAppUserRemoveCustGroupByRegionView1() {
        return (AppUserRemoveCustGroupByRegionViewImpl)findViewObject("AppUserRemoveCustGroupByRegionView1");
    }

    /**
     * Container's getter for AppUserRemoveLocByRegionView1.
     * @return AppUserRemoveLocByRegionView1
     */
    public AppUserRemoveLocByRegionViewImpl getAppUserRemoveLocByRegionView1() {
        return (AppUserRemoveLocByRegionViewImpl)findViewObject("AppUserRemoveLocByRegionView1");
    }

    /**
     * Container's getter for AppUserRemoveCustByAreaView1.
     * @return AppUserRemoveCustByAreaView1
     */
    public AppUserRemoveCustByAreaViewImpl getAppUserRemoveCustByAreaView1() {
        return (AppUserRemoveCustByAreaViewImpl)findViewObject("AppUserRemoveCustByAreaView1");
    }

    /**
     * Container's getter for AppUserRemoveCustGroupByAreaView1.
     * @return AppUserRemoveCustGroupByAreaView1
     */
    public AppUserRemoveCustGroupByAreaViewImpl getAppUserRemoveCustGroupByAreaView1() {
        return (AppUserRemoveCustGroupByAreaViewImpl)findViewObject("AppUserRemoveCustGroupByAreaView1");
    }

    /**
     * Container's getter for AppUserRemoveLocByAreaView1.
     * @return AppUserRemoveLocByAreaView1
     */
    public AppUserRemoveLocByAreaViewImpl getAppUserRemoveLocByAreaView1() {
        return (AppUserRemoveLocByAreaViewImpl)findViewObject("AppUserRemoveLocByAreaView1");
    }

    /**
     * Container's getter for AppUserRemoveCustGroupByLocView1.
     * @return AppUserRemoveCustGroupByLocView1
     */
    public AppUserRemoveCustGroupByLocViewImpl getAppUserRemoveCustGroupByLocView1() {
        return (AppUserRemoveCustGroupByLocViewImpl)findViewObject("AppUserRemoveCustGroupByLocView1");
    }

    /**
     * Container's getter for AppUserRemoveCustByLocView1.
     * @return AppUserRemoveCustByLocView1
     */
    public AppUserRemoveCustByLocViewImpl getAppUserRemoveCustByLocView1() {
        return (AppUserRemoveCustByLocViewImpl)findViewObject("AppUserRemoveCustByLocView1");
    }

    /**
     * Container's getter for AppUserRemoveCustByCustGroupView1.
     * @return AppUserRemoveCustByCustGroupView1
     */
    public AppUserRemoveCustByCustGroupViewImpl getAppUserRemoveCustByCustGroupView1() {
        return (AppUserRemoveCustByCustGroupViewImpl)findViewObject("AppUserRemoveCustByCustGroupView1");
    }

    /**
     * Container's getter for AppUserCustTypeView1.
     * @return AppUserCustTypeView1
     */
    public ViewObjectImpl getAppUserCustTypeView1() {
        return (ViewObjectImpl)findViewObject("AppUserCustTypeView1");
    }

    /**
     * Container's getter for AppUserCustTypeUserAccessFk1Link1.
     * @return AppUserCustTypeUserAccessFk1Link1
     */
    public ViewLinkImpl getAppUserCustTypeUserAccessFk1Link1() {
        return (ViewLinkImpl)findViewLink("AppUserCustTypeUserAccessFk1Link1");
    }

    /**
     * Container's getter for AllUserMgmtCustTypeShuttleView1.
     * @return AllUserMgmtCustTypeShuttleView1
     */
    public ViewObjectImpl getAllUserMgmtCustTypeShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllUserMgmtCustTypeShuttleView1");
    }

    /**
     * Container's getter for AppUserRemoveCustGroupByCustTypeView1.
     * @return AppUserRemoveCustGroupByCustTypeView1
     */
    public AppUserRemoveCustGroupByCustTypeViewImpl getAppUserRemoveCustGroupByCustTypeView1() {
        return (AppUserRemoveCustGroupByCustTypeViewImpl)findViewObject("AppUserRemoveCustGroupByCustTypeView1");
    }

    /**
     * Container's getter for AppUserRemoveCustTypeByAreaView1.
     * @return AppUserRemoveCustTypeByAreaView1
     */
    public AppUserRemoveCustTypeByAreaViewImpl getAppUserRemoveCustTypeByAreaView1() {
        return (AppUserRemoveCustTypeByAreaViewImpl)findViewObject("AppUserRemoveCustTypeByAreaView1");
    }

    /**
     * Container's getter for AppUserRemoveCustTypeByLocView1.
     * @return AppUserRemoveCustTypeByLocView1
     */
    public AppUserRemoveCustTypeByLocViewImpl getAppUserRemoveCustTypeByLocView1() {
        return (AppUserRemoveCustTypeByLocViewImpl)findViewObject("AppUserRemoveCustTypeByLocView1");
    }

    /**
     * Container's getter for AppUserRemoveCustTypeByRegionView1.
     * @return AppUserRemoveCustTypeByRegionView1
     */
    public AppUserRemoveCustTypeByRegionViewImpl getAppUserRemoveCustTypeByRegionView1() {
        return (AppUserRemoveCustTypeByRegionViewImpl)findViewObject("AppUserRemoveCustTypeByRegionView1");
    }

    /**
     * Container's getter for AppUserRemoveCustByCustTypeView1.
     * @return AppUserRemoveCustByCustTypeView1
     */
    public AppUserRemoveCustByCustTypeViewImpl getAppUserRemoveCustByCustTypeView1() {
        return (AppUserRemoveCustByCustTypeViewImpl)findViewObject("AppUserRemoveCustByCustTypeView1");
    }
}
