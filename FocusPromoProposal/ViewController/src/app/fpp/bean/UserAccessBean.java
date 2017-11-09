package app.fpp.bean;

import app.fpp.adfextensions.JSFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class UserAccessBean {

    private RichTable tblAssignedRole;
    private RichTable tblAssignedRegion;
    private RichTable tblAssignedMenuItems;
    private RichTable tblUserList;
    private RichTable tblRoleList;
    private RichInputText itUserPassword;
    private RichPanelSplitter psUserData;

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
        OperationBinding operationBinding =
            bindings.getOperationBinding("ExecuteRegion");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblAssignedRegion);
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
}
