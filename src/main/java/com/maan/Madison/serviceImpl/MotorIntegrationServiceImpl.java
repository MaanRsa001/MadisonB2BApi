package com.maan.Madison.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.Madison.entity.HomePositionMaster;
import com.maan.Madison.entity.MotorDataDetail;
import com.maan.Madison.entity.PaymentProcessDetailId;
import com.maan.Madison.entity.PaymentProcessDetails;
import com.maan.Madison.repository.HomePositionMasterRepository;
import com.maan.Madison.repository.MotorDataDetailRepository;
import com.maan.Madison.repository.PaymentProcessDetailRepository;
import com.maan.Madison.request.MotorIntegrationRequest;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.response.PolicyDocumentRes;
import com.maan.Madison.response.PolicyIntegrationRes;
import com.maan.Madison.service.MotorIntegrationService;
import com.maan.Madison.utilityServiceImpl.ErrorList;

@Service
public  class MotorIntegrationServiceImpl implements MotorIntegrationService {
	
	Logger log =LogManager.getLogger(MotorIntegrationServiceImpl.class);
	
	@Autowired
	private CommonService cs;
	@Autowired 
	private HomePositionMasterRepository hpmRepo;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private MotorDataDetailRepository mddRepo;
	@Autowired
	private PaymentProcessDetailRepository paymentProcessRepo;
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public CommonResponse integration(MotorIntegrationRequest req) {
		CommonResponse res = new CommonResponse();
		List<ErrorList> error = new ArrayList<ErrorList>();
		log.info("Policy integration request :"+cs.reqPrint(req));
		try {
			String policyNo="";
			String receiptNo="";
			String debitNo="";
			Map<String,Object> hpm =hpmRepo.getPaymentStatusByQuoteNo(req.getQuoteNo());
			String paymentstatus =hpm.get("PAYMENT_STATUS")==null?"":hpm.get("PAYMENT_STATUS").toString();
			if("S".equalsIgnoreCase(paymentstatus)) {
				String policyStatus =hpm.get("STATUS")==null?"":hpm.get("STATUS").toString();
				if ("E".equalsIgnoreCase(policyStatus) || "P".equalsIgnoreCase(policyStatus)) {
					policyNo = hpm.get("POLICY_NO").toString();
					receiptNo = hpm.get("RECEIPT_NO").toString();
					debitNo = hpm.get("DEBIT_NOTE_NO").toString();
				}else {
					policyNo=hpmRepo.getSequence(req.getQuoteNo(),req.getBranchCode(),"Policy",req.getProductId());
					receiptNo=hpmRepo.getSequence(req.getQuoteNo(),req.getBranchCode(),"Receipt",req.getProductId());
					debitNo=hpmRepo.getSequence(req.getQuoteNo(),req.getBranchCode(),"Debit",req.getProductId());
				
					Map<String,Object> map= hpmRepo.getApplicationId(req.getQuoteNo());
					String appId =map.get("APPLICATION_ID")==null?"":map.get("APPLICATION_ID").toString();
					
					if("1".equalsIgnoreCase(appId))
						policyNo=policyNo+"/B";
					else
						policyNo=policyNo+"/E";
					
					if("65".equalsIgnoreCase(req.getProductId())) {
						List<Map<String,Object>> certificationList =hpmRepo.getCertificatNo(req.getQuoteNo());
						for(Map<String,Object> c:certificationList) {
							String certificationNo =c.get("CERTIFICATE_NO")==null?"":c.get("CERTIFICATE_NO").toString();
							if(StringUtils.isBlank(certificationNo)) {
								String vehicleId = c.get("VEHICLE_ID")==null?"":c.get("VEHICLE_ID").toString();
								String certificate_no=hpmRepo.getSequence("", req.getBranchCode(), "MotorCertificate", req.getProductId());
								hpmRepo.updateCertificateNo(certificate_no, req.getQuoteNo(), vehicleId);
							}
						}
					}
				}
					String paymentMode =hpmRepo.getPaymentMode(req.getQuoteNo());
					log.info("Policy integration paymentmode : "+paymentMode);
					HomePositionMaster homeMaster=hpmRepo.findByQuoteNo(Long.valueOf(req.getQuoteNo()));
					String inceptionDate =sdf.format(homeMaster.getInceptionDate());
					String expiryDate =sdf.format(homeMaster.getExpiryDate());
					Map<String,Object> policyTerm =hpmRepo.getPolicyTermsAndQuarter(inceptionDate,expiryDate);
					homeMaster.setPolicyNo(policyNo);
					homeMaster.setReceiptNo(receiptNo); 
					homeMaster.setDebitNoteNo(debitNo);
					homeMaster.setDebitNoteDate(new Date());
					homeMaster.setReceiptDate(new Date());
					homeMaster.setEffectiveDate(new Date());
					homeMaster.setPaymentMode(paymentMode);
					homeMaster.setPolicyTerm(policyTerm.get("POLICY_TERM")==null?"0":policyTerm.get("POLICY_TERM").toString());
					homeMaster.setQuaterId(policyTerm.get("QUARTER_ID")==null?null:Long.valueOf(policyTerm.get("QUARTER_ID").toString()));
					homeMaster.setStatus("P");
					HomePositionMaster hp=hpmRepo.save(homeMaster);
					
					try {
						log.info("inserting data into payment transaction table");
						insertpaymentprocess(hp);
												
						log.info("commonIntgProcess--> quoteNo: " + req.getQuoteNo() + " branchCode: " + req.getBranchCode());			
						StoredProcedureQuery query = em.createStoredProcedureQuery("MOTOR_INTEGRATION")
								.registerStoredProcedureParameter("pvquote", String.class, ParameterMode.IN)
								.registerStoredProcedureParameter("pvbranch", String.class, ParameterMode.IN)
								.registerStoredProcedureParameter("PVOUT", String.class, ParameterMode.OUT)
								.setParameter("pvquote", req.getQuoteNo()).setParameter("pvbranch", req.getBranchCode());
	
						query.execute();
	
					}catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						hp.setStatus("E");
						hpmRepo.save(hp);
						res.setMessage("ERROR");
						error.add(new ErrorList("501","PolicyIntegration","Something went wrong in policy integration. Please contact admin..!"));
						res.setErrors(error);
						return res;
					}
					
					res = setPolicyResponse(hp);
				
			}else {
				res.setMessage("ERROR");
				error.add(new ErrorList("502","PolicyIntegration","We have not received payment for this quote number "+req.getQuoteNo()+""));
				res.setErrors(error);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("ERROR");
			error.add(new ErrorList("503","PolicyIntegration","Something went wrong in policy integration. Please contact admin..!"));
			res.setErrors(error);
			return res;
		}
		return res;
	}

	
	private CommonResponse setPolicyResponse(HomePositionMaster hp) {
		CommonResponse res = new CommonResponse();
		try {
			List<PolicyDocumentRes> docList =new ArrayList<PolicyDocumentRes>();
			List<MotorDataDetail>mdd =mddRepo.findByQuoteNo(hp.getQuoteNo());
			for(MotorDataDetail d :mdd) {
				PolicyDocumentRes documentRes = PolicyDocumentRes.builder()
						.make(StringUtils.isBlank(d.getMakeName())?"":d.getMakeName())
						.model(StringUtils.isBlank(d.getModelName())?"":d.getModelName())
						.bodyType(StringUtils.isBlank(d.getBodyName())?"":d.getBodyName())
						.vehicleUsage(StringUtils.isBlank(d.getVehUsageName())?"":d.getVehUsageName())
						.vehicleValue(d.getSuminsuredValueLocal()==null?"":d.getSuminsuredValueLocal().toString())
						.certificateNo(StringUtils.isBlank(d.getCertificateNo())?"":d.getCertificateNo())
						.build();
				d.setPolicyNo(hp.getPolicyNo());
				mddRepo.saveAndFlush(d);
				docList.add(documentRes);
			}
			
			PolicyIntegrationRes policyRes =PolicyIntegrationRes.builder()
					.policyNo(hp.getPolicyNo())
					.Premium(hp.getOverallPremium().toString())
					.receiptNo(hp.getReceiptNo())
					.debitNo(hp.getDebitNoteNo())
					.documentInfo(docList)
					.build();
			res.setMessage("SUCCESS");
			res.setResponse(policyRes);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return res;
	}
	
	private void insertpaymentprocess(HomePositionMaster hpm) {
		PaymentProcessDetails ppd=new PaymentProcessDetails();
		ppd.setAllocatePerson(hpm.getLoginId());
		ppd.setLoginId(hpm.getLoginId());
		ppd.setPolicyNo(hpm.getPolicyNo());
		ppd.setQuoteNo(hpm.getQuoteNo().toString());
		ppd.setRemarks("");
		ppd.setResponseTime(new Date());
		ppd.setStatus("Y");
		ppd.setType("UW");
		ppd.setSno(1L);
		paymentProcessRepo.save(ppd);
	}
	

}
