package app.fpp.model.am;

import app.fpp.adfextensions.CustomApplicationModuleImpl;
import app.fpp.model.am.common.ApprovalAM;

import app.fpp.model.views.approval.ApprovalReceiverApproveProposalViewImpl;
import app.fpp.model.views.approval.ApprovalReceiverRejectProposalViewImpl;
import app.fpp.model.views.approval.CheckRoleProposalCreatorViewImpl;
import app.fpp.model.views.approval.DocApprovalViewImpl;

import app.fpp.model.views.masterdata.ebs.FcsViewCategCombinationViewImpl;
import app.fpp.model.views.promoproposal.DiscountViewImpl;
import app.fpp.model.views.promoproposal.ProdukItemViewImpl;
import app.fpp.model.views.promoproposal.ProdukVariantViewImpl;
import app.fpp.model.views.promoproposal.PromoBonusVariantViewImpl;
import app.fpp.model.views.promoproposal.PromoBonusViewImpl;
import app.fpp.model.views.promoproposal.PromoProdukViewImpl;

import app.fpp.model.views.promoproposal.validation.ProdVariantValidationViewImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 12 10:27:18 ICT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprovalAMImpl extends CustomApplicationModuleImpl implements ApprovalAM {

    /**
     * This is the default constructor (do not remove).
     */
    private final Integer FIRSTSTEPINFLOW = 1;
    private static final String finalReason = "Finished approval";

    public ApprovalAMImpl() {
    }

    public void setLoginToSession_ApprovalAM(String key, String val) {
        this.setLoginToSession(key, val);
    }

    public void approveDocApproval(String propId, String docNo,
                                   String docStatus, String promoDtFrom,
                                   String promoDtTo, String docRegion,
                                   String usrRole, String userNm,
                                   String aprvlCodeRun, String idDocAprvl,
                                   String reasonDocAprvl, String usrAction) {

        //Retrieve proposal receiver role
        ViewObjectImpl proposalReceiver =
            this.getApprovalReceiverApproveProposalView1();
        proposalReceiver.setNamedWhereClauseParam("aprvlCode", aprvlCodeRun);
        proposalReceiver.setNamedWhereClauseParam("usrRole", usrRole);
        proposalReceiver.executeQuery();

        if (proposalReceiver.getEstimatedRowCount() > 0) {
            //IF USER NOT THE LATEST ROLE IN APPROVAL FLOW
            Row propReceiverRow = proposalReceiver.first();
            String roleName = (String)propReceiverRow.getAttribute("RoleName");
            String aprvlCode =
                (String)propReceiverRow.getAttribute("AprvlCode");

            PreparedStatement addDocApprovalStmt = null;

            try {
                String SQL =
                    "INSERT INTO DOC_APPROVAL (DOC_APPROVAL_ID, PROPOSAL_ID, DOCUMENT_NO, STATUS, PROMO_DATE_FROM, PROMO_DATE_TO, ROLE_NAME, REGION, APRVL_CODE, CREATED_BY, CREATION_DATE) " +
                    "VALUES (DOC_APPROVAL_SEQ.NEXTVAL, TO_NUMBER(" + propId +
                    "), '" + docNo + "', '" + docStatus +
                    "', TO_DATE(UPPER('" + promoDtFrom +
                    "'), 'DD-MON-YYYY'), " + "TO_DATE(UPPER('" + promoDtTo +
                    "'), 'DD-MON-YYYY'), '" + roleName + "', '" + docRegion +
                    "', '" + aprvlCode + "', '" + userNm + "', SYSDATE)";
                addDocApprovalStmt =
                        getDBTransaction().createPreparedStatement(SQL, 1);
                addDocApprovalStmt.executeUpdate();
            } catch (SQLException e) {
                throw new JboException(e.getMessage());
            } finally {
                getDBTransaction().commit();
                if (addDocApprovalStmt != null) {
                    try {
                        addDocApprovalStmt.close();
                    } catch (Exception e) {
                        throw new JboException(e.getMessage());
                    }
                }
            }
        } else {
            //IF USER IS THE LATEST ROLE IN APPROVAL FLOW
            PreparedStatement addDocFinishApprovalStmt = null;

            try {
                String SQLFinish =
                    "INSERT INTO DOC_APPROVAL (DOC_APPROVAL_ID, PROPOSAL_ID, DOCUMENT_NO, STATUS, PROMO_DATE_FROM, PROMO_DATE_TO, ACTION, ACTION_DATE, ACTION_BY, REASON, ROLE_NAME, REGION, APRVL_CODE, CREATED_BY, CREATION_DATE) " +
                    "VALUES (DOC_APPROVAL_SEQ.NEXTVAL, TO_NUMBER(" + propId +
                    "), '" + docNo + "', 'ACTIVE', TO_DATE(UPPER('" + promoDtFrom +
                    "'), 'DD-MON-YYYY'), " + "TO_DATE(UPPER('" + promoDtTo +
                    "'), 'DD-MON-YYYY'), 'FINISHED', SYSDATE, '" + userNm +
                    "', '" + finalReason + "', '" + usrRole + "', '" +
                    docRegion + "', '" + aprvlCodeRun + "', '" + userNm +
                    "', SYSDATE)";

                addDocFinishApprovalStmt =
                        getDBTransaction().createPreparedStatement(SQLFinish,
                                                                   1);
                addDocFinishApprovalStmt.executeUpdate();
            } catch (SQLException e) {
                throw new JboException(e.getMessage());
            } finally {
                getDBTransaction().commit();
                if (addDocFinishApprovalStmt != null) {
                    try {
                        addDocFinishApprovalStmt.close();
                    } catch (Exception e) {
                        throw new JboException(e.getMessage());
                    }
                }
            }
        }
    }

    public void rejectDocApproval(String propId, String docNo,
                                  String docStatus, String promoDtFrom,
                                  String promoDtTo, String docRegion,
                                  String usrRole, String userNm,
                                  String aprvlCodeRun, String idDocAprvl,
                                  String reasonDocAprvl, String usrAction) {

        //Retrieve proposal receiver role
        ViewObjectImpl proposalReceiver =
            this.getApprovalReceiverRejectProposalView1();
        proposalReceiver.setNamedWhereClauseParam("aprvlCode", aprvlCodeRun);
        proposalReceiver.setNamedWhereClauseParam("usrRole", usrRole);
        proposalReceiver.executeQuery();

        if (proposalReceiver.getEstimatedRowCount() > 0) {
            Row propReceiverRow = proposalReceiver.first();
            String roleName = (String)propReceiverRow.getAttribute("RoleName");
            String aprvlCode =
                (String)propReceiverRow.getAttribute("AprvlCode");
            Number stepSeqNum =
                (Number)propReceiverRow.getAttribute("StepSequence");
            Integer stepSeq = stepSeqNum.intValue();

            //Role proposal creator
            ViewObjectImpl isNextStepCreator =
                this.getCheckRoleProposalCreatorView1();
            isNextStepCreator.setNamedWhereClauseParam("aprvlCd",
                                                       aprvlCodeRun);
            isNextStepCreator.setNamedWhereClauseParam("usrRole", roleName);
            isNextStepCreator.setNamedWhereClauseParam("propId",
                                                       new Number(Integer.parseInt(propId)));
            isNextStepCreator.executeQuery();

            if (!stepSeq.equals(FIRSTSTEPINFLOW)) {
                if (isNextStepCreator.getEstimatedRowCount() == 0) {
                    PreparedStatement addDocApprovalStmt = null;

                    try {
                        String SQL =
                            "INSERT INTO DOC_APPROVAL (DOC_APPROVAL_ID, PROPOSAL_ID, DOCUMENT_NO, STATUS, PROMO_DATE_FROM, PROMO_DATE_TO, ROLE_NAME, REGION, APRVL_CODE, CREATED_BY, CREATION_DATE) " +
                            "VALUES (DOC_APPROVAL_SEQ.NEXTVAL, TO_NUMBER(" +
                            propId + "), '" + docNo + "', '" + docStatus +
                            "', TO_DATE(UPPER('" + promoDtFrom +
                            "'), 'DD-MON-YYYY'), " + "TO_DATE(UPPER('" +
                            promoDtTo + "'), 'DD-MON-YYYY'), '" + roleName +
                            "', '" + docRegion + "', '" + aprvlCode + "', '" +
                            userNm + "', SYSDATE)";
                        addDocApprovalStmt =
                                getDBTransaction().createPreparedStatement(SQL,
                                                                           1);
                        addDocApprovalStmt.executeUpdate();
                    } catch (SQLException e) {
                        throw new JboException(e.getMessage());
                    } finally {
                        getDBTransaction().commit();
                        if (addDocApprovalStmt != null) {
                            try {
                                addDocApprovalStmt.close();
                            } catch (Exception e) {
                                throw new JboException(e.getMessage());
                            }
                        }
                    }
                } else {
                    try {
                        getDBTransaction().commit();
                    } catch (Exception e) {
                        throw new JboException(e.getMessage());
                    }
                }
            } else {
                try {
                    getDBTransaction().commit();
                } catch (Exception e) {
                    throw new JboException(e.getMessage());
                }
            }
        } else {
            getDBTransaction().rollback();
            throw new JboException("Role penerima pada flow approval tidak ditemukan.");
        }
    }

    /**
     * Container's getter for ProposalApprovalView1.
     * @return ProposalApprovalView1
     */
    public ViewObjectImpl getProposalApprovalView1() {
        return (ViewObjectImpl)findViewObject("ProposalApprovalView1");
    }

    /**
     * Container's getter for DocApprovalView1.
     * @return DocApprovalView1
     */
    public DocApprovalViewImpl getDocApprovalView1() {
        return (DocApprovalViewImpl)findViewObject("DocApprovalView1");
    }

    /**
     * Container's getter for ProposalApprovalDocApprovalFk1Link1.
     * @return ProposalApprovalDocApprovalFk1Link1
     */
    public ViewLinkImpl getProposalApprovalDocApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalDocApprovalFk1Link1");
    }


    /**
     * Container's getter for ApprovalReceiverApproveProposalView1.
     * @return ApprovalReceiverApproveProposalView1
     */
    public ApprovalReceiverApproveProposalViewImpl getApprovalReceiverApproveProposalView1() {
        return (ApprovalReceiverApproveProposalViewImpl)findViewObject("ApprovalReceiverApproveProposalView1");
    }

    /**
     * Container's getter for ApprovalReceiverRejectProposalView1.
     * @return ApprovalReceiverRejectProposalView1
     */
    public ApprovalReceiverRejectProposalViewImpl getApprovalReceiverRejectProposalView1() {
        return (ApprovalReceiverRejectProposalViewImpl)findViewObject("ApprovalReceiverRejectProposalView1");
    }

    /**
     * Container's getter for CheckRoleProposalCreatorView1.
     * @return CheckRoleProposalCreatorView1
     */
    public ViewObjectImpl getCheckRoleProposalCreatorView1() {
        return (ViewObjectImpl)findViewObject("CheckRoleProposalCreatorView1");
    }

    /**
     * Container's getter for PromoProdukView1.
     * @return PromoProdukView1
     */
    public PromoProdukViewImpl getPromoProdukView1() {
        return (PromoProdukViewImpl)findViewObject("PromoProdukView1");
    }

    /**
     * Container's getter for PromoProdukProposalApprovalFk1Link1.
     * @return PromoProdukProposalApprovalFk1Link1
     */
    public ViewLinkImpl getPromoProdukProposalApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("PromoProdukProposalApprovalFk1Link1");
    }

    /**
     * Container's getter for BiayaView1.
     * @return BiayaView1
     */
    public ViewObjectImpl getBiayaView1() {
        return (ViewObjectImpl)findViewObject("BiayaView1");
    }

    /**
     * Container's getter for BiayaPromoProdukFk1Link1.
     * @return BiayaPromoProdukFk1Link1
     */
    public ViewLinkImpl getBiayaPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("BiayaPromoProdukFk1Link1");
    }


    /**
     * Container's getter for DiscountView1.
     * @return DiscountView1
     */
    public ViewObjectImpl getDiscountView1() {
        return (ViewObjectImpl)findViewObject("DiscountView1");
    }

    /**
     * Container's getter for DiscountPromoProdukFk1Link1.
     * @return DiscountPromoProdukFk1Link1
     */
    public ViewLinkImpl getDiscountPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("DiscountPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ProdukItemView1.
     * @return ProdukItemView1
     */
    public ViewObjectImpl getProdukItemView1() {
        return (ViewObjectImpl)findViewObject("ProdukItemView1");
    }

    /**
     * Container's getter for ProdukItemPromoProdukFk1Link1.
     * @return ProdukItemPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdukItemPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdukItemPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ProdukVariantView1.
     * @return ProdukVariantView1
     */
    public ViewObjectImpl getProdukVariantView1() {
        return (ViewObjectImpl)findViewObject("ProdukVariantView1");
    }

    /**
     * Container's getter for ProdukVariantPromoProdukFk1Link1.
     * @return ProdukVariantPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdukVariantPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdukVariantPromoProdukFk1Link1");
    }


    /**
     * Container's getter for PromoBonusView1.
     * @return PromoBonusView1
     */
    public PromoBonusViewImpl getPromoBonusView1() {
        return (PromoBonusViewImpl)findViewObject("PromoBonusView1");
    }

    /**
     * Container's getter for PromoBonusPromoProdukFk1Link1.
     * @return PromoBonusPromoProdukFk1Link1
     */
    public ViewLinkImpl getPromoBonusPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("PromoBonusPromoProdukFk1Link1");
    }

    /**
     * Container's getter for TargetView1.
     * @return TargetView1
     */
    public ViewObjectImpl getTargetView1() {
        return (ViewObjectImpl)findViewObject("TargetView1");
    }

    /**
     * Container's getter for TargetPromoProdukFk1Link1.
     * @return TargetPromoProdukFk1Link1
     */
    public ViewLinkImpl getTargetPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("TargetPromoProdukFk1Link1");
    }


    /**
     * Container's getter for PromoBonusProdItemView1.
     * @return PromoBonusProdItemView1
     */
    public ViewObjectImpl getPromoBonusProdItemView1() {
        return (ViewObjectImpl)findViewObject("PromoBonusProdItemView1");
    }

    /**
     * Container's getter for PromoBonusProdItemPromoBonusFk1Link1.
     * @return PromoBonusProdItemPromoBonusFk1Link1
     */
    public ViewLinkImpl getPromoBonusProdItemPromoBonusFk1Link1() {
        return (ViewLinkImpl)findViewLink("PromoBonusProdItemPromoBonusFk1Link1");
    }

    /**
     * Container's getter for PromoBonusVariantView1.
     * @return PromoBonusVariantView1
     */
    public ViewObjectImpl getPromoBonusVariantView1() {
        return (ViewObjectImpl)findViewObject("PromoBonusVariantView1");
    }

    /**
     * Container's getter for PromoBonusVariantPromoBonusFk1Link1.
     * @return PromoBonusVariantPromoBonusFk1Link1
     */
    public ViewLinkImpl getPromoBonusVariantPromoBonusFk1Link1() {
        return (ViewLinkImpl)findViewLink("PromoBonusVariantPromoBonusFk1Link1");
    }

    /**
     * Container's getter for AllProdukItemShuttleView1.
     * @return AllProdukItemShuttleView1
     */
    public ViewObjectImpl getAllProdukItemShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukItemShuttleView1");
    }

    /**
     * Container's getter for AllProdukVariantShuttleView1.
     * @return AllProdukVariantShuttleView1
     */
    public ViewObjectImpl getAllProdukVariantShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukVariantShuttleView1");
    }

    /**
     * Container's getter for AllProdukAreaShuttleView1.
     * @return AllProdukAreaShuttleView1
     */
    public ViewObjectImpl getAllProdukAreaShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukAreaShuttleView1");
    }

    /**
     * Container's getter for AllProdukCustomerShuttleView1.
     * @return AllProdukCustomerShuttleView1
     */
    public ViewObjectImpl getAllProdukCustomerShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukCustomerShuttleView1");
    }

    /**
     * Container's getter for AllProdukRegionShuttleView1.
     * @return AllProdukRegionShuttleView1
     */
    public ViewObjectImpl getAllProdukRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukRegionShuttleView1");
    }

    /**
     * Container's getter for ProdRegionAreaView1.
     * @return ProdRegionAreaView1
     */
    public ViewObjectImpl getProdRegionAreaView1() {
        return (ViewObjectImpl)findViewObject("ProdRegionAreaView1");
    }

    /**
     * Container's getter for ProdRegionAreaPromoProdukFk1Link1.
     * @return ProdRegionAreaPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdRegionAreaPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdRegionAreaPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ProdRegionCustomerView1.
     * @return ProdRegionCustomerView1
     */
    public ViewObjectImpl getProdRegionCustomerView1() {
        return (ViewObjectImpl)findViewObject("ProdRegionCustomerView1");
    }

    /**
     * Container's getter for ProdRegionCustomerPromoProdukFk1Link1.
     * @return ProdRegionCustomerPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdRegionCustomerPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdRegionCustomerPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ProdRegionView1.
     * @return ProdRegionView1
     */
    public ViewObjectImpl getProdRegionView1() {
        return (ViewObjectImpl)findViewObject("ProdRegionView1");
    }

    /**
     * Container's getter for ProdRegionPromoProdukFk1Link1.
     * @return ProdRegionPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdRegionPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdRegionPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ProdVariantValidationView1.
     * @return ProdVariantValidationView1
     */
    public ProdVariantValidationViewImpl getProdVariantValidationView1() {
        return (ProdVariantValidationViewImpl)findViewObject("ProdVariantValidationView1");
    }

    /**
     * Container's getter for FcsViewCategCombinationView1.
     * @return FcsViewCategCombinationView1
     */
    public FcsViewCategCombinationViewImpl getFcsViewCategCombinationView1() {
        return (FcsViewCategCombinationViewImpl)findViewObject("FcsViewCategCombinationView1");
    }

    /**
     * Container's getter for AllProdukLocationShuttleView1.
     * @return AllProdukLocationShuttleView1
     */
    public ViewObjectImpl getAllProdukLocationShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukLocationShuttleView1");
    }

    /**
     * Container's getter for ProdRegionLocView1.
     * @return ProdRegionLocView1
     */
    public ViewObjectImpl getProdRegionLocView1() {
        return (ViewObjectImpl)findViewObject("ProdRegionLocView1");
    }

    /**
     * Container's getter for ProdRegionLocPromoProdukFk1Link1.
     * @return ProdRegionLocPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdRegionLocPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdRegionLocPromoProdukFk1Link1");
    }

    /**
     * Container's getter for PropRegionAreaView1.
     * @return PropRegionAreaView1
     */
    public ViewObjectImpl getPropRegionAreaView1() {
        return (ViewObjectImpl)findViewObject("PropRegionAreaView1");
    }

    /**
     * Container's getter for ProposalApprovalPropRegionAreaFk1Link1.
     * @return ProposalApprovalPropRegionAreaFk1Link1
     */
    public ViewLinkImpl getProposalApprovalPropRegionAreaFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalPropRegionAreaFk1Link1");
    }

    /**
     * Container's getter for PropRegionCustomerView1.
     * @return PropRegionCustomerView1
     */
    public ViewObjectImpl getPropRegionCustomerView1() {
        return (ViewObjectImpl)findViewObject("PropRegionCustomerView1");
    }

    /**
     * Container's getter for ProposalApprovalPropRegionCustomerFk1Link1.
     * @return ProposalApprovalPropRegionCustomerFk1Link1
     */
    public ViewLinkImpl getProposalApprovalPropRegionCustomerFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalPropRegionCustomerFk1Link1");
    }

    /**
     * Container's getter for PropRegionLocView1.
     * @return PropRegionLocView1
     */
    public ViewObjectImpl getPropRegionLocView1() {
        return (ViewObjectImpl)findViewObject("PropRegionLocView1");
    }

    /**
     * Container's getter for ProposalApprovalPropRegionLocFk1Link1.
     * @return ProposalApprovalPropRegionLocFk1Link1
     */
    public ViewLinkImpl getProposalApprovalPropRegionLocFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalPropRegionLocFk1Link1");
    }

    /**
     * Container's getter for PropRegionView1.
     * @return PropRegionView1
     */
    public ViewObjectImpl getPropRegionView1() {
        return (ViewObjectImpl)findViewObject("PropRegionView1");
    }

    /**
     * Container's getter for ProposalApprovalPropRegionFk1Link1.
     * @return ProposalApprovalPropRegionFk1Link1
     */
    public ViewLinkImpl getProposalApprovalPropRegionFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalPropRegionFk1Link1");
    }

    /**
     * Container's getter for AllProposalAreaShuttleView1.
     * @return AllProposalAreaShuttleView1
     */
    public ViewObjectImpl getAllProposalAreaShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProposalAreaShuttleView1");
    }

    /**
     * Container's getter for AllProposalCustomerShuttleView1.
     * @return AllProposalCustomerShuttleView1
     */
    public ViewObjectImpl getAllProposalCustomerShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProposalCustomerShuttleView1");
    }

    /**
     * Container's getter for AllProposalLocationShuttleView1.
     * @return AllProposalLocationShuttleView1
     */
    public ViewObjectImpl getAllProposalLocationShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProposalLocationShuttleView1");
    }

    /**
     * Container's getter for AllProposalRegionShuttleView1.
     * @return AllProposalRegionShuttleView1
     */
    public ViewObjectImpl getAllProposalRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProposalRegionShuttleView1");
    }

    /**
     * Container's getter for ApprovalRegPropView1.
     * @return ApprovalRegPropView1
     */
    public ViewObjectImpl getApprovalRegPropView1() {
        return (ViewObjectImpl)findViewObject("ApprovalRegPropView1");
    }

    /**
     * Container's getter for ApprovalRegPropProposalApprovalFk1Link1.
     * @return ApprovalRegPropProposalApprovalFk1Link1
     */
    public ViewLinkImpl getApprovalRegPropProposalApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ApprovalRegPropProposalApprovalFk1Link1");
    }

    /**
     * Container's getter for ProdRegionCustGroupView1.
     * @return ProdRegionCustGroupView1
     */
    public ViewObjectImpl getProdRegionCustGroupView1() {
        return (ViewObjectImpl)findViewObject("ProdRegionCustGroupView1");
    }

    /**
     * Container's getter for ProdRegionCustGroupPromoProdukFk1Link1.
     * @return ProdRegionCustGroupPromoProdukFk1Link1
     */
    public ViewLinkImpl getProdRegionCustGroupPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProdRegionCustGroupPromoProdukFk1Link1");
    }

    /**
     * Container's getter for PropRegionCustGroupView1.
     * @return PropRegionCustGroupView1
     */
    public ViewObjectImpl getPropRegionCustGroupView1() {
        return (ViewObjectImpl)findViewObject("PropRegionCustGroupView1");
    }

    /**
     * Container's getter for ProposalApprovalPropRegionCustGroupFk1Link1.
     * @return ProposalApprovalPropRegionCustGroupFk1Link1
     */
    public ViewLinkImpl getProposalApprovalPropRegionCustGroupFk1Link1() {
        return (ViewLinkImpl)findViewLink("ProposalApprovalPropRegionCustGroupFk1Link1");
    }

    /**
     * Container's getter for AllProdukCustGroupShuttleView1.
     * @return AllProdukCustGroupShuttleView1
     */
    public ViewObjectImpl getAllProdukCustGroupShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProdukCustGroupShuttleView1");
    }

    /**
     * Container's getter for AllProposalCustGroupShuttleView1.
     * @return AllProposalCustGroupShuttleView1
     */
    public ViewObjectImpl getAllProposalCustGroupShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllProposalCustGroupShuttleView1");
    }

    /**
     * Container's getter for ExclCustAreaView1.
     * @return ExclCustAreaView1
     */
    public ViewObjectImpl getExclCustAreaView1() {
        return (ViewObjectImpl)findViewObject("ExclCustAreaView1");
    }

    /**
     * Container's getter for ExclCustAreaPromoProdukFk1Link1.
     * @return ExclCustAreaPromoProdukFk1Link1
     */
    public ViewLinkImpl getExclCustAreaPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclCustAreaPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ExclCustLocView1.
     * @return ExclCustLocView1
     */
    public ViewObjectImpl getExclCustLocView1() {
        return (ViewObjectImpl)findViewObject("ExclCustLocView1");
    }

    /**
     * Container's getter for ExclCustLocPromoProdukFk1Link1.
     * @return ExclCustLocPromoProdukFk1Link1
     */
    public ViewLinkImpl getExclCustLocPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclCustLocPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ExclCustRegionView1.
     * @return ExclCustRegionView1
     */
    public ViewObjectImpl getExclCustRegionView1() {
        return (ViewObjectImpl)findViewObject("ExclCustRegionView1");
    }

    /**
     * Container's getter for ExclCustRegionPromoProdukFk1Link1.
     * @return ExclCustRegionPromoProdukFk1Link1
     */
    public ViewLinkImpl getExclCustRegionPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclCustRegionPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ExclCustCustView1.
     * @return ExclCustCustView1
     */
    public ViewObjectImpl getExclCustCustView1() {
        return (ViewObjectImpl)findViewObject("ExclCustCustView1");
    }

    /**
     * Container's getter for ExclCustCustPromoProdukFk1Link1.
     * @return ExclCustCustPromoProdukFk1Link1
     */
    public ViewLinkImpl getExclCustCustPromoProdukFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclCustCustPromoProdukFk1Link1");
    }

    /**
     * Container's getter for ExclPropCustAreaView1.
     * @return ExclPropCustAreaView1
     */
    public ViewObjectImpl getExclPropCustAreaView1() {
        return (ViewObjectImpl)findViewObject("ExclPropCustAreaView1");
    }

    /**
     * Container's getter for ExclPropCustAreaProposalApprovalFk1Link1.
     * @return ExclPropCustAreaProposalApprovalFk1Link1
     */
    public ViewLinkImpl getExclPropCustAreaProposalApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclPropCustAreaProposalApprovalFk1Link1");
    }

    /**
     * Container's getter for ExclPropCustCustView1.
     * @return ExclPropCustCustView1
     */
    public ViewObjectImpl getExclPropCustCustView1() {
        return (ViewObjectImpl)findViewObject("ExclPropCustCustView1");
    }

    /**
     * Container's getter for ExclPropCustCustProposalApprovalFk1Link1.
     * @return ExclPropCustCustProposalApprovalFk1Link1
     */
    public ViewLinkImpl getExclPropCustCustProposalApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclPropCustCustProposalApprovalFk1Link1");
    }

    /**
     * Container's getter for ExclPropCustLocView1.
     * @return ExclPropCustLocView1
     */
    public ViewObjectImpl getExclPropCustLocView1() {
        return (ViewObjectImpl)findViewObject("ExclPropCustLocView1");
    }

    /**
     * Container's getter for ExclPropCustLocProposalApprovalFk1Link1.
     * @return ExclPropCustLocProposalApprovalFk1Link1
     */
    public ViewLinkImpl getExclPropCustLocProposalApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclPropCustLocProposalApprovalFk1Link1");
    }

    /**
     * Container's getter for ExclPropCustRegionView1.
     * @return ExclPropCustRegionView1
     */
    public ViewObjectImpl getExclPropCustRegionView1() {
        return (ViewObjectImpl)findViewObject("ExclPropCustRegionView1");
    }

    /**
     * Container's getter for ExclPropCustRegionProposaApprovalFk1Link1.
     * @return ExclPropCustRegionProposaApprovalFk1Link1
     */
    public ViewLinkImpl getExclPropCustRegionProposaApprovalFk1Link1() {
        return (ViewLinkImpl)findViewLink("ExclPropCustRegionProposaApprovalFk1Link1");
    }

    /**
     * Container's getter for AllExclProposalAreaShuttleView1.
     * @return AllExclProposalAreaShuttleView1
     */
    public ViewObjectImpl getAllExclProposalAreaShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExclProposalAreaShuttleView1");
    }

    /**
     * Container's getter for AllExclProposalCustomerShuttleView1.
     * @return AllExclProposalCustomerShuttleView1
     */
    public ViewObjectImpl getAllExclProposalCustomerShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExclProposalCustomerShuttleView1");
    }

    /**
     * Container's getter for AllExclProposalLocationShuttleView1.
     * @return AllExclProposalLocationShuttleView1
     */
    public ViewObjectImpl getAllExclProposalLocationShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExclProposalLocationShuttleView1");
    }

    /**
     * Container's getter for AllExclProposalRegionShuttleView1.
     * @return AllExclProposalRegionShuttleView1
     */
    public ViewObjectImpl getAllExclProposalRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExclProposalRegionShuttleView1");
    }

    /**
     * Container's getter for AllExcludeProdAreaShuttleView1.
     * @return AllExcludeProdAreaShuttleView1
     */
    public ViewObjectImpl getAllExcludeProdAreaShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExcludeProdAreaShuttleView1");
    }

    /**
     * Container's getter for AllExcludeProdCustomerShuttleView1.
     * @return AllExcludeProdCustomerShuttleView1
     */
    public ViewObjectImpl getAllExcludeProdCustomerShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExcludeProdCustomerShuttleView1");
    }

    /**
     * Container's getter for AllExcludeProdLocationShuttleView1.
     * @return AllExcludeProdLocationShuttleView1
     */
    public ViewObjectImpl getAllExcludeProdLocationShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExcludeProdLocationShuttleView1");
    }

    /**
     * Container's getter for AllExcludeProdRegionShuttleView1.
     * @return AllExcludeProdRegionShuttleView1
     */
    public ViewObjectImpl getAllExcludeProdRegionShuttleView1() {
        return (ViewObjectImpl)findViewObject("AllExcludeProdRegionShuttleView1");
    }
}
