package app.fpp.bean.useraccess;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.model.am.UserAccessAMImpl;
import app.fpp.model.views.useraccess.AppUserRemoveAreaByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByCustGroupViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByLocViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByLocViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveCustGroupByRegionViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveLocByAreaViewImpl;
import app.fpp.model.views.useraccess.AppUserRemoveLocByRegionViewImpl;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import org.apache.myfaces.trinidad.event.ReturnEvent;

public class UserAccessBean {

    private RichTable tblAssignedRole;
    private RichTable tblAssignedRegion;
    private RichTable tblAssignedMenuItems;
    private RichTable tblUserList;
    private RichTable tblRoleList;
    private RichInputText itUserPassword;
    private RichPanelSplitter psUserData;
    private RichTable tblAssignedArea;
    private RichTable tblAssignedLoc;
    private RichTable tblAssignedCust;
    private RichTable tblAssignedCustGroup;
    private RichShowDetailItem tabUserArea;
    private RichShowDetailItem tabUserRegion;
    private RichShowDetailItem tabUserLoc;
    private RichShowDetailItem tabUserCustGroup;
    private RichShowDetailItem tabUserCust;
    private UserAccessAMImpl userAccessAM =
        (UserAccessAMImpl)ADFUtils.getApplicationModuleForDataControl("UserAccessAMDataControl");

    public UserAccessBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addRolePopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    }

    public void addRoleDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("Role sudah ditambahkan");

        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void editRoleDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding roleNameAttr =
            (AttributeBinding)bindings.getControlBinding("Name");
        String roleName = (String)roleNameAttr.getInputValue();
        if (roleName != null || !roleName.equalsIgnoreCase("")) {
            if (dialogEvent.getOutcome().name().equals("ok")) {
                OperationBinding operationBindingCommit =
                    bindings.getOperationBinding("Commit");
                operationBindingCommit.execute();

                JSFUtils.addFacesInformationMessage("Role \"" + roleNameAttr +
                                                    "\" sudah diupdate");

            } else {
                OperationBinding operationBinding =
                    bindings.getOperationBinding("Rollback");
                operationBinding.execute();
            }
        } else {
            JSFUtils.addFacesInformationMessage("Pilih terlebih dahulu role yang akan di edit");
        }
    }

    public void removeDialogListener(DialogEvent dialogEvent) {

        BindingContainer bindings = getBindings();
        AttributeBinding roleNameAttr =
            (AttributeBinding)bindings.getControlBinding("Name");
        String roleName = (String)roleNameAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Delete");
            operationBinding.execute();
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("Role \"" + roleName +
                                                "\" sudah dihapus");

        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void editMenuDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding roleNameAttr =
            (AttributeBinding)bindings.getControlBinding("Label");
        String roleName = (String)roleNameAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("Menu \"" + roleNameAttr +
                                                "\" sudah diupdate");

        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void editMenuItemDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding roleNameAttr =
            (AttributeBinding)bindings.getControlBinding("Label1");
        String roleName = (String)roleNameAttr.getInputValue();

        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("Menu Item \"" + roleNameAttr +
                                                "\" sudah diupdate");
        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void addUserDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>User login aplikasi sudah ditambahkan.</p>");
            message.append("<p>Mohon dilengkapi kolom-kolom yang masih kosong.</p>");
            message.append("</body></html>");
            FacesMessage msg = new FacesMessage(message.toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            ctx.addMessage(null, msg);
        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void addUserPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    }

    public void removeUserDialogListener(DialogEvent dialogEvent) {

        BindingContainer bindings = getBindings();
        AttributeBinding fullNameAttr =
            (AttributeBinding)bindings.getControlBinding("FullName");
        String fullName = (String)fullNameAttr.getInputValue();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();

        String msgName = fullName + "[" + userName + "]";

        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Delete");
            operationBinding.execute();
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("User \"" + msgName +
                                                "\" sudah dihapus");

        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void windowRoleReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteRoles");
        operationBinding.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedRole);
    }

    public void windowRegionReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();
        
        // Remove Selected Area
        AppUserRemoveAreaByRegionViewImpl remAreaVo = userAccessAM.getAppUserRemoveAreaByRegionView1();
        remAreaVo.setNamedWhereClauseParam("userName", userName);
        remAreaVo.executeQuery();
        if (remAreaVo.getEstimatedRowCount() > 0) {
            RowSetIterator remAreaIter = remAreaVo.createRowSetIterator(null);
            Row remAreaRow = null;            
            int i = 0;
            while (remAreaIter.hasNext()) {
                i = i + 1;
                remAreaRow = remAreaIter.next();
                remAreaRow.remove();
            }
            remAreaIter.closeRowSetIterator();
        }
        
        // Remove Selected Location
        AppUserRemoveLocByRegionViewImpl remLocVo = userAccessAM.getAppUserRemoveLocByRegionView1();
        remLocVo.setNamedWhereClauseParam("userName", userName);
        remLocVo.executeQuery();
        if (remLocVo.getEstimatedRowCount() > 0) {
            RowSetIterator remLocIter = remLocVo.createRowSetIterator(null);
            Row remLocRow = null;            
            int i = 0;
            while (remLocIter.hasNext()) {
                i = i + 1;
                remLocRow = remLocIter.next();
                remLocRow.remove();
            }
            remLocIter.closeRowSetIterator();
        }
        
        // Remove Selected Customer Group
        AppUserRemoveCustGroupByRegionViewImpl remCustGroupVo = userAccessAM.getAppUserRemoveCustGroupByRegionView1();
        remCustGroupVo.setNamedWhereClauseParam("userName", userName);
        remCustGroupVo.executeQuery();
        if (remCustGroupVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustGroupIter = remCustGroupVo.createRowSetIterator(null);
            Row remCustGroupRow = null;            
            int i = 0;
            while (remCustGroupIter.hasNext()) {
                i = i + 1;
                remCustGroupRow = remCustGroupIter.next();
                remCustGroupRow.remove();
            }
            remCustGroupIter.closeRowSetIterator();
        }
        
        // Remove Selected Customer
        AppUserRemoveCustByRegionViewImpl remCustVo = userAccessAM.getAppUserRemoveCustByRegionView1();
        remCustVo.setNamedWhereClauseParam("userName", userName);
        remCustVo.executeQuery();
        if (remCustVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustIter = remCustVo.createRowSetIterator(null);
            Row remCustRow = null;            
            int i = 0;
            while (remCustIter.hasNext()) {
                i = i + 1;
                remCustRow = remCustIter.next();
                remCustRow.remove();
            }
            remCustIter.closeRowSetIterator();
        }
        
        OperationBinding execCommit =
            bindings.getOperationBinding("Commit");
         execCommit.execute();
        
        OperationBinding refreshRegion =
            bindings.getOperationBinding("ExecuteRegion");
        refreshRegion.execute();
        
        OperationBinding refreshArea =
            bindings.getOperationBinding("ExecuteArea");
        refreshArea.execute();  
        
        OperationBinding refreshLoc =
            bindings.getOperationBinding("ExecuteLoc");
        refreshLoc.execute();
        
        OperationBinding refreshCustGroup =
            bindings.getOperationBinding("ExecuteCustGroup");
        refreshCustGroup.execute();
        
        OperationBinding refreshCust =
            bindings.getOperationBinding("ExecuteCust");
        refreshCust.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedRegion);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserRegion);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserArea);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserLoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCustGroup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCust);
    }

    public void windowAreaReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();
        
        // Remove Selected Location
        AppUserRemoveLocByAreaViewImpl remLocVo = userAccessAM.getAppUserRemoveLocByAreaView1();
        remLocVo.setNamedWhereClauseParam("userName", userName);
        remLocVo.executeQuery();
        if (remLocVo.getEstimatedRowCount() > 0) {
            RowSetIterator remLocIter = remLocVo.createRowSetIterator(null);
            Row remLocRow = null;            
            int i = 0;
            while (remLocIter.hasNext()) {
                i = i + 1;
                remLocRow = remLocIter.next();
                remLocRow.remove();
            }
            remLocIter.closeRowSetIterator();
        }
        
        // Remove Selected Customer Group
        AppUserRemoveCustGroupByAreaViewImpl remCustGroupVo = userAccessAM.getAppUserRemoveCustGroupByAreaView1();
        remCustGroupVo.setNamedWhereClauseParam("userName", userName);
        remCustGroupVo.executeQuery();
        if (remCustGroupVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustGroupIter = remCustGroupVo.createRowSetIterator(null);
            Row remCustGroupRow = null;            
            int i = 0;
            while (remCustGroupIter.hasNext()) {
                i = i + 1;
                remCustGroupRow = remCustGroupIter.next();
                remCustGroupRow.remove();
            }
            remCustGroupIter.closeRowSetIterator();
        }
        
        // Remove Selected Customer
        AppUserRemoveCustByAreaViewImpl remCustVo = userAccessAM.getAppUserRemoveCustByAreaView1();
        remCustVo.setNamedWhereClauseParam("userName", userName);
        remCustVo.executeQuery();
        if (remCustVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustIter = remCustVo.createRowSetIterator(null);
            Row remCustRow = null;            
            int i = 0;
            while (remCustIter.hasNext()) {
                i = i + 1;
                remCustRow = remCustIter.next();
                remCustRow.remove();
            }
            remCustIter.closeRowSetIterator();
        }
        
        OperationBinding execCommit =
            bindings.getOperationBinding("Commit");
         execCommit.execute();
        
        OperationBinding refreshArea =
            bindings.getOperationBinding("ExecuteArea");
        refreshArea.execute();     
        
        OperationBinding refreshLoc =
            bindings.getOperationBinding("ExecuteLoc");
        refreshLoc.execute();
        
        OperationBinding refreshCustGroup =
            bindings.getOperationBinding("ExecuteCustGroup");
        refreshCustGroup.execute();
        
        OperationBinding refreshCust =
            bindings.getOperationBinding("ExecuteCust");
        refreshCust.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedArea);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserArea);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserLoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCustGroup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCust);
    }
    
    public void windowLocationReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();
        
        // Remove Selected Customer Group
        AppUserRemoveCustGroupByLocViewImpl remCustGroupVo = userAccessAM.getAppUserRemoveCustGroupByLocView1();
        remCustGroupVo.setNamedWhereClauseParam("userName", userName);
        remCustGroupVo.executeQuery();
        if (remCustGroupVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustGroupIter = remCustGroupVo.createRowSetIterator(null);
            Row remCustGroupRow = null;            
            int i = 0;
            while (remCustGroupIter.hasNext()) {
                i = i + 1;
                remCustGroupRow = remCustGroupIter.next();
                remCustGroupRow.remove();
            }
            remCustGroupIter.closeRowSetIterator();
        }
        
        // Remove Selected Customer
        AppUserRemoveCustByLocViewImpl remCustVo = userAccessAM.getAppUserRemoveCustByLocView1();
        remCustVo.setNamedWhereClauseParam("userName", userName);
        remCustVo.executeQuery();
        if (remCustVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustIter = remCustVo.createRowSetIterator(null);
            Row remCustRow = null;            
            int i = 0;
            while (remCustIter.hasNext()) {
                i = i + 1;
                remCustRow = remCustIter.next();
                remCustRow.remove();
            }
            remCustIter.closeRowSetIterator();
        }
        
        OperationBinding execCommit =
            bindings.getOperationBinding("Commit");
         execCommit.execute();
         
        OperationBinding refreshLoc =
            bindings.getOperationBinding("ExecuteLoc");
        refreshLoc.execute();
        
        OperationBinding refreshCustGroup =
            bindings.getOperationBinding("ExecuteCustGroup");
        refreshCustGroup.execute();
        
        OperationBinding refreshCust =
            bindings.getOperationBinding("ExecuteCust");
        refreshCust.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedLoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserLoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCustGroup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCust);
    }
    
    public void windowCustGroupReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();
        
        // Remove Selected Customer
        AppUserRemoveCustByCustGroupViewImpl remCustVo = userAccessAM.getAppUserRemoveCustByCustGroupView1();
        remCustVo.setNamedWhereClauseParam("userName", userName);
        remCustVo.executeQuery();
        if (remCustVo.getEstimatedRowCount() > 0) {
            RowSetIterator remCustIter = remCustVo.createRowSetIterator(null);
            Row remCustRow = null;            
            int i = 0;
            while (remCustIter.hasNext()) {
                i = i + 1;
                remCustRow = remCustIter.next();
                remCustRow.remove();
            }
            remCustIter.closeRowSetIterator();
        }
        
        OperationBinding execCommit =
            bindings.getOperationBinding("Commit");
         execCommit.execute();
         
        OperationBinding refreshCustGroup =
            bindings.getOperationBinding("ExecuteCustGroup");
        refreshCustGroup.execute();
        
        OperationBinding refreshCust =
            bindings.getOperationBinding("ExecuteCust");
        refreshCust.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedCustGroup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCustGroup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCust);
    }
    
    public void windowCustomerReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        
        OperationBinding refreshCust =
            bindings.getOperationBinding("ExecuteCust");
        refreshCust.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedCust);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabUserCust);
    }
    
    public void setTblAssignedRole(RichTable tblAssignedRole) {
        this.tblAssignedRole = tblAssignedRole;
    }

    public RichTable getTblAssignedRole() {
        return tblAssignedRole;
    }

    public void setTblAssignedRegion(RichTable tblAssignedRegion) {
        this.tblAssignedRegion = tblAssignedRegion;
    }

    public RichTable getTblAssignedRegion() {
        return tblAssignedRegion;
    }

    public void windowMenuItemReturnListener(ReturnEvent returnEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteMit");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedMenuItems);
    }

    public void setTblAssignedMenuItems(RichTable tblAssignedMenuItems) {
        this.tblAssignedMenuItems = tblAssignedMenuItems;
    }

    public RichTable getTblAssignedMenuItems() {
        return tblAssignedMenuItems;
    }

    public void addUserPopupCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding refreshUserList =
            bindings.getOperationBinding("Execute");
        refreshUserList.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblUserList);
        AdfFacesContext.getCurrentInstance().addPartialTarget(psUserData);
    }

    public void setTblUserList(RichTable tblUserList) {
        this.tblUserList = tblUserList;
    }

    public RichTable getTblUserList() {
        return tblUserList;
    }

    public void addRolePopupCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = this.getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblRoleList);
    }

    public void setTblRoleList(RichTable tblRoleList) {
        this.tblRoleList = tblRoleList;
    }

    public RichTable getTblRoleList() {
        return tblRoleList;
    }

    public void resetPasswordPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        itUserPassword.setValue("welcome1");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itUserPassword);
    }

    public void resetPasswordDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();

        AttributeBinding userNameAttr =
            (AttributeBinding)bindings.getControlBinding("UserName");
        String userName = (String)userNameAttr.getInputValue();
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBindingCommit =
                bindings.getOperationBinding("Commit");
            operationBindingCommit.execute();

            JSFUtils.addFacesInformationMessage("Password user \"" + userName +
                                                "\" sudah direset");
        } else {
            OperationBinding operationBinding =
                bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
    }

    public void setItUserPassword(RichInputText itUserPassword) {
        this.itUserPassword = itUserPassword;
    }

    public RichInputText getItUserPassword() {
        return itUserPassword;
    }

    public void setPsUserData(RichPanelSplitter psUserData) {
        this.psUserData = psUserData;
    }

    public RichPanelSplitter getPsUserData() {
        return psUserData;
    }

    public void setTblAssignedArea(RichTable tblAssignedArea) {
        this.tblAssignedArea = tblAssignedArea;
    }

    public RichTable getTblAssignedArea() {
        return tblAssignedArea;
    }

    public void setTblAssignedLoc(RichTable tblAssignedLoc) {
        this.tblAssignedLoc = tblAssignedLoc;
    }

    public RichTable getTblAssignedLoc() {
        return tblAssignedLoc;
    }

    public void setTblAssignedCust(RichTable tblAssignedCust) {
        this.tblAssignedCust = tblAssignedCust;
    }

    public RichTable getTblAssignedCust() {
        return tblAssignedCust;
    }

    public void setTblAssignedCustGroup(RichTable tblAssignedCustGroup) {
        this.tblAssignedCustGroup = tblAssignedCustGroup;
    }

    public RichTable getTblAssignedCustGroup() {
        return tblAssignedCustGroup;
    }

    public void setTabUserArea(RichShowDetailItem tabUserArea) {
        this.tabUserArea = tabUserArea;
    }

    public RichShowDetailItem getTabUserArea() {
        return tabUserArea;
    }

    public void setTabUserRegion(RichShowDetailItem tabUserRegion) {
        this.tabUserRegion = tabUserRegion;
    }

    public RichShowDetailItem getTabUserRegion() {
        return tabUserRegion;
    }

    public void setTabUserLoc(RichShowDetailItem tabUserLoc) {
        this.tabUserLoc = tabUserLoc;
    }

    public RichShowDetailItem getTabUserLoc() {
        return tabUserLoc;
    }

    public void setTabUserCustGroup(RichShowDetailItem tabUserCustGroup) {
        this.tabUserCustGroup = tabUserCustGroup;
    }

    public RichShowDetailItem getTabUserCustGroup() {
        return tabUserCustGroup;
    }

    public void setTabUserCust(RichShowDetailItem tabUserCust) {
        this.tabUserCust = tabUserCust;
    }

    public RichShowDetailItem getTabUserCust() {
        return tabUserCust;
    }
}
