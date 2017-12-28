package app.fpp.bean.useraccessmenu;

import java.util.HashMap;

public class UserData {
    
    private HashMap userAccess;
    private Boolean loggedIn = Boolean.FALSE;    
    private String fullName;
    private String fullNameSubstr;
    private String userPassword;
    private String userNameLogin;
    private String title;  
    private String userRole;
    private String contactNo;
    private String companyId;
    private String userType;
    private String userInitial;
    private String userDivision;
    private String userCustomer;
    private String reportDomain;
    
    public UserData() {
    }

    public void setUserAccess(HashMap userAccess) {
        this.userAccess = userAccess;
    }

    public HashMap getUserAccess() {
        return userAccess;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserNameLogin(String userNameLogin) {
        this.userNameLogin = userNameLogin;
    }

    public String getUserNameLogin() {
        return userNameLogin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setFullNameSubstr(String fullNameSubstr) {
        this.fullNameSubstr = fullNameSubstr;
    }

    public String getFullNameSubstr() {
        return fullNameSubstr;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserInitial(String userInitial) {
        this.userInitial = userInitial;
    }

    public String getUserInitial() {
        return userInitial;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserCustomer(String userCustomer) {
        this.userCustomer = userCustomer;
    }

    public String getUserCustomer() {
        return userCustomer;
    }

    public void setReportDomain(String reportDomain) {
        this.reportDomain = reportDomain;
    }

    public String getReportDomain() {
        return reportDomain;
    }
}
