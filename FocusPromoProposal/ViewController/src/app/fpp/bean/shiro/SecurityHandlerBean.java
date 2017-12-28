package app.fpp.bean.shiro;

import app.fpp.adfextensions.ADFUtils;
import app.fpp.adfextensions.JSFUtils;
import app.fpp.bean.useraccessmenu.UserData;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletResponse;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.binding.OperationBinding;

public class SecurityHandlerBean {
    private String userName;
    private String password;
    private String encpassword;
    private RichInputText inputLogin, inputPassword;
    private boolean remember;
    public static final String HOME_URL = "/faces/dashboard.jspx";
    public static final String LOGIN_URL = "slogin.jspx";
    private static String userAccessDisabled = "D";
    
    public SecurityHandlerBean() {
    }
    
    public String login() {
        try {
            //encpassword=getKeyDigestString(password,null);
            encpassword=password;
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userName, encpassword, remember));
            
            // PPPC Application Data Session
            OperationBinding login =
            ADFUtils.findOperation("authenticateUser");
            Map m = (Map)login.execute();
            
            String fullName = (String)m.get("FullName");
            HashMap userAccess = (HashMap)m.get("UserAccess");
            String userPassword = (String)m.get("Password");
            String userNameLogin = (String)m.get("UserName");
            String title = (String)m.get("Title");
            String userRole = (String)m.get("UserRole");
            String contactNo = (String)m.get("ContactNo");
            String companyId = (String)m.get("CompanyId");
            String accessStatus = (String)m.get("AccessStatus");
            String userType = (String)m.get("UserType");
            String userInitial = (String)m.get("UserInitial");
            String userDivision = (String)m.get("UserDivision");
            String userCustomer = (String)m.get("UserCustomer");
            String reportDomain = (String)m.get("ReportDomain");
            
            HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            /*
            if (savedRequest != null) {
                System.out.println("SavedRequest URL:" + savedRequest.getRequestUrl());
            }
            */
            //externalContext.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
            
            UserData userData =
              (UserData)JSFUtils.resolveExpression("#{UserData}");
            userData.setLoggedIn(Boolean.TRUE);
            userData.setFullName(fullName);
            String fullNameSub = null;
            if (fullName.length() > 16) {
              fullNameSub = (fullName.substring(0, 12)).concat("...");
            } else {
              fullNameSub = fullName;
            }
            userData.setFullNameSubstr(fullNameSub);
            userData.setUserAccess(userAccess);
            userData.setUserNameLogin(userNameLogin);
            userData.setUserPassword(userPassword);
            userData.setTitle(title);
            userData.setUserRole(userRole);
            userData.setContactNo(contactNo);
            userData.setCompanyId(companyId);
            userData.setUserType(userType);
            userData.setUserInitial(userInitial);
            userData.setUserDivision(userDivision);
            userData.setUserCustomer(userCustomer);
            userData.setReportDomain(reportDomain);
            
            OperationBinding _dashboardAMSession =
              ADFUtils.findOperation("setLoginToSession_DashboardAM");
            _dashboardAMSession.execute();
            
            OperationBinding _promoProposalAMSession =
              ADFUtils.findOperation("setLoginToSession_PromoProposalAM");
            _promoProposalAMSession.execute();
            
            OperationBinding _confirmationAMSession =
              ADFUtils.findOperation("setLoginToSession_ConfirmationAM");
            _confirmationAMSession.execute();
            
            OperationBinding _approvalAMSession =
              ADFUtils.findOperation("setLoginToSession_ApprovalAM");
            _approvalAMSession.execute();
            
            OperationBinding _approvalSettingAMSession =
              ADFUtils.findOperation("setLoginToSession_ApprovalSettingAM");
            _approvalSettingAMSession.execute();
            
            OperationBinding _budgetSettingAMSession =
              ADFUtils.findOperation("setLoginToSession_BudgetSettingAM");
            _budgetSettingAMSession.execute();
            
            if (accessStatus.equalsIgnoreCase(userAccessDisabled)) {
                StringBuilder message = new StringBuilder("<html><body>");
                message.append("<p>Username yang anda masukan telah expired.</p>");
                message.append("</body></html>");
                FacesMessage msg = new FacesMessage(message.toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                externalContext.redirect( request.getContextPath()+HOME_URL);
            }
            } /*catch (FailedLoginException fle) {
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Isian salah pada \"Username\" atau \"Password\".</p>");
            message.append("</body></html>");
            FacesMessage msg = new FacesMessage(message.toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (LoginException le) {
            FacesMessage msg = new FacesMessage(le.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            }*/ catch (Exception e) {
            /*
            FacesMessage msg = new FacesMessage(e.getLocalizedMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            */
            
            StringBuilder message = new StringBuilder("<html><body>");
            message.append("<p>Isian salah pada \"Username\" atau \"Password\".</p>");
            message.append("</body></html>");
            FacesMessage msg = new FacesMessage(message.toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return "";
    }

    String getAbsoluteApplicationUrl() throws Exception {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        URL url = new URL(request.getRequestURL().toString());
        URL newUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), request.getContextPath());
        return newUrl.toString();
    }
    /*
    public String logout() throws IOException {
        SecurityUtils.getSubject().logout();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //externalContext.invalidateSession();
        externalContext.redirect(LOGIN_URL);
        return "logout";
    }
    */
    public String logout_action()throws IOException {
        SecurityUtils.getSubject().logout();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
        HttpServletRequest req = (HttpServletRequest)externalContext.getRequest();
        //externalContext.invalidateSession();
        externalContext.redirect(LOGIN_URL);
        try {
            response.sendRedirect((new StringBuilder()).append(req.getContextPath()).append("/faces/dashboard.jspx").toString());
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(e.getLocalizedMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }       
        return null;               
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setRemember(boolean remember) {
        this.remember = remember;
    }
    public boolean isRemember() {
        return remember;
    }
    public String getKeyDigestString(String message, String key) throws NoSuchProviderException {
        try {
            String pwCompareStr = "";
            byte[] messageByte = message.getBytes();
            // if no key is provided, the message string gets encrypted with itself
            byte[] keyByte = (key != null && key.length() > 0) ? key.getBytes() : message.getBytes();
            // get SHA1 instance      
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1", "SUN");
            sha1.update(messageByte);
           
            //byte[] digestByte = sha1.digest(keyByte);
            byte[] digestByte = sha1.digest();

            // base 64 encoding
            BASE64Encoder b64Encoder = new BASE64Encoder();
            pwCompareStr = (b64Encoder.encode(digestByte));
            pwCompareStr = new StringBuilder("{SHA-1}").append(pwCompareStr).toString();
            return pwCompareStr;
        } catch (NoSuchAlgorithmException e) {
            FacesMessage msg = new FacesMessage(e.getLocalizedMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    } 

    public void setInputLogin(RichInputText inputLogin) {
        this.inputLogin = inputLogin;
    }

    public RichInputText getInputLogin() {
        return inputLogin;
    }

    public void setInputPassword(RichInputText inputPassword) {
        this.inputPassword = inputPassword;
    }

    public RichInputText getInputPassword() {
        return inputPassword;
    }
}
