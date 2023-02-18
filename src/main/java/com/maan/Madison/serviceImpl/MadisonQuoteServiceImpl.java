package com.maan.Madison.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maan.Madison.entity.HomePositionMaster;
import com.maan.Madison.entity.MotorDataDetail;
import com.maan.Madison.entity.MotorPolicyDetails;
import com.maan.Madison.entity.PersonalInfo;
import com.maan.Madison.repository.HomePositionMasterRepository;
import com.maan.Madison.repository.MotorDataDetailRepository;
import com.maan.Madison.repository.MotorMakeMasterRepository;
import com.maan.Madison.repository.MotorPolicyCoverDataRepository;
import com.maan.Madison.repository.MotorPolicyRepository;
import com.maan.Madison.repository.PersonalInfoRepository;
import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.CustomerReq;
import com.maan.Madison.request.CustomerSaveReq;
import com.maan.Madison.request.MadisonQuoteRequest;
import com.maan.Madison.request.QuoteRequest;
import com.maan.Madison.request.SaveVehicleReq;
import com.maan.Madison.request.VehDriverReq;
import com.maan.Madison.response.BuyPolicyCustomerReq;
import com.maan.Madison.response.BuyPolicyResponse;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.response.CreateQuoteRes;
import com.maan.Madison.response.CustomerEditRes;
import com.maan.Madison.response.CustomerInfoRes;
import com.maan.Madison.response.CustomerRes;
import com.maan.Madison.response.CustomerSaveRes;
import com.maan.Madison.response.DriverEditRes;
import com.maan.Madison.response.GetVehicleReq;
import com.maan.Madison.response.PremiumInfoRes;
import com.maan.Madison.response.QuoteInfoRes;
import com.maan.Madison.response.SaveVehicleRes;
import com.maan.Madison.response.VehicleEditRes;
import com.maan.Madison.response.VehicleInfoRes;
import com.maan.Madison.service.DocumentUploadService;
import com.maan.Madison.service.MadisonQuoteService;
import com.maan.Madison.utilityServiceImpl.ErrorList;
import com.maan.Madison.utilityServiceImpl.MadisonInputValidation;

@Service
public class MadisonQuoteServiceImpl implements MadisonQuoteService{
	
	
	Logger log =LogManager.getLogger(MadisonQuoteServiceImpl.class);
	
	
	
	@Autowired
	private CommonService cs;
	@Autowired
	private PersonalInfoRepository personalInfoRepository;
	@Autowired
	private MotorDataDetailRepository motorDataDetailRepository;
	@Autowired
	private MotorPolicyCoverDataRepository motorPolicyCoverDataRepository;
	@Autowired
	private ParallelThreadExecutionImpl executionImpl;
	@Autowired
	private MotorMakeMasterRepository makeMasterRepository;
	@Autowired
	private HomePositionMasterRepository hpmRepo;
	@Autowired
	private MotorPolicyRepository motorPolicyRepository;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private MadisonInputValidation validation;
	@Autowired
	private DocumentUploadService docUploadService;
	
	
	SimpleDateFormat sdf =new SimpleDateFormat("dd/mm/yyyy");
	
	
	@Override
	public CommonResponse getCustomerDetails(CustomerReq req) {
		CommonResponse res =new CommonResponse();
		ArrayList<CustomerRes> resList =new ArrayList<CustomerRes>();
		try {
			List<PersonalInfo> list=personalInfoRepository.findByAgencyCodeAndCustomerTypeIgnoreCaseOrderByEntryDateDesc(req.getBrokerCode(),req.getCustomerType());
			if(list.size()>0) {
				list.forEach( p->{
					String firstName =StringUtils.isBlank(p.getFirstName())?"":p.getFirstName();
					String lastName =StringUtils.isBlank(p.getLastName())?"":p.getLastName();
					CustomerRes r = CustomerRes.builder()
							.ContactNo(StringUtils.isBlank(p.getMobile())?"":p.getMobile())
							.customerName(firstName+lastName)
							.emailId(StringUtils.isBlank(p.getEmail())?"":p.getEmail())
							.nrc(StringUtils.isBlank(p.getNrc())?"":p.getNrc())
							.passportNo(StringUtils.isBlank(p.getPassportNumber())?"":p.getPassportNumber())
							.customerId(p.getCustomerId().toString())
							.brokerCode(StringUtils.isBlank(p.getAgencyCode())?"":p.getAgencyCode())
							.build();
					resList.add(r);
				});
				res.setMessage("SUCCESS");
				res.setResponse(resList);
			}else {
				res.setMessage("FAILED");
				res.setResponse(resList);
			}
		
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse getCustomerById(String customerId) {
		CommonResponse res =new CommonResponse();
		try {
			PersonalInfo info=personalInfoRepository.findById(Long.valueOf(customerId)).get();
			if(info!=null) {
				CustomerEditRes r =CustomerEditRes.builder()
						.address(StringUtils.isBlank(info.getAddress1())?"":info.getAddress1())
						.alternatMobileNo(StringUtils.isBlank(info.getAlternateMobile())?"":info.getAlternateMobile())
						.city(StringUtils.isBlank(info.getCity())?"":info.getCity())
						.companyRegNo(StringUtils.isBlank(info.getCompanyRegNo())?"":info.getCompanyRegNo())
						.customerTyp(StringUtils.isBlank(info.getCustomerType())?"":info.getCustomerType())
						.dateOfBirth(info.getDob()==null?"":sdf.format(info.getDob()))
						.email(StringUtils.isBlank(info.getEmail())?"":info.getEmail())
						.firstName(StringUtils.isBlank(info.getFirstName())?"":info.getFirstName())
						.gender(StringUtils.isBlank(info.getGender())?"":info.getGender())
						.lastname(StringUtils.isBlank(info.getLastName())?"":info.getLastName())
						.mobile(StringUtils.isBlank(info.getMobile())?"":info.getMobile())
						.nrc(StringUtils.isBlank(info.getNrc())?"":info.getNrc())
						.occupation(StringUtils.isBlank(info.getOccupation())?"":info.getOccupation())
						.passportNo(StringUtils.isBlank(info.getPassportNumber())?"":info.getPassportNumber())
						.poBox(StringUtils.isBlank(info.getPobox())?"":info.getPobox())
						.title(StringUtils.isBlank(info.getTitle())?"":info.getTitle())
						.brokerCode(StringUtils.isBlank(info.getAgencyCode())?"":info.getAgencyCode())
						.loginId(StringUtils.isBlank(info.getLoginId())?"":info.getLoginId())
						.build();
				res.setMessage("SUCCESS");	
				res.setResponse(r);
			}else {
				res.setMessage("FAILED");	
				res.setResponse(null);
			}
			
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse saveCustomer(CustomerSaveReq req) {
		log.info("customer request : "+cs.reqPrint(req));
		CommonResponse res = new CommonResponse();
		CustomerSaveRes cusRes = new CustomerSaveRes();
		try {
			List<ErrorList> error =validation.validateCustomer(req);
			if(CollectionUtils.isEmpty(error)) {
				//String agencyCode =personalInfoRepository.getBrokerCode(req.getBrokerCode());
				PersonalInfo personalInfo =PersonalInfo.builder()
						.title(StringUtils.isBlank(req.getTitle())?"":req.getTitle())
						.amendId(new BigDecimal(0))
						.firstName(StringUtils.isBlank(req.getFirstName())?"":req.getFirstName())
						.lastName(StringUtils.isBlank(req.getLastname())?"":req.getLastname())
						.email(StringUtils.isBlank(req.getEmail())?"":req.getEmail())
						.mobile(StringUtils.isBlank(req.getMobileno())?"":req.getMobileno())
						.customerType(req.getCustomerTyp())
						.loginId(StringUtils.isBlank(req.getLoginId())?"":req.getLoginId())
						.customerId(StringUtils.isBlank(req.getCustomerId())?personalInfoRepository.getCustomerId():Long.valueOf(req.getCustomerId()))
						.agencyCode(StringUtils.isBlank(req.getBrokerCode())?"":req.getBrokerCode())
						.build();
				Long customerId =personalInfoRepository.save(personalInfo).getCustomerId();
				
				res.setMessage("SUCCESS");
				cusRes.setCustomerId(String.valueOf(customerId));
				res.setResponse(cusRes);
			}else {
				res.setMessage("ERROR");
				res.setErrors(error);
			}
		}catch (Exception e) {
			log.error(e);
			res.setMessage("FAILED");
			e.printStackTrace();
		}
		return res;
	}

	
	@Override
	public CommonResponse createQuote(MadisonQuoteRequest req) {
		CommonResponse comres =new CommonResponse();
		CreateQuoteRes quoteRes = new CreateQuoteRes();
		log.info("createQuote request : "+cs.reqPrint(req));
		try {
			QuoteRequest quoteReq =req.getQuoteRequest();
			Long applicationNo =StringUtils.isBlank(quoteReq.getApplicationNo())?0L:Long.valueOf(quoteReq.getApplicationNo());
			Long quoteNo =StringUtils.isBlank(quoteReq.getQuoteNo())?motorDataDetailRepository.getQuoteNo():Long.valueOf(quoteReq.getQuoteNo());			
			String branchCode =req.getQuoteRequest().getBranchCode();
			quoteReq.setQuoteNo(quoteNo.toString());
			quoteReq.setApplicationNo(applicationNo.toString());
			String policyType=StringUtils.isBlank(quoteReq.getPolicyInfo().getPolicyType())?"":quoteReq.getPolicyInfo().getPolicyType();
			String policyStartDate =quoteReq.getPolicyInfo().getPolicyStartState();
			String policyEndtDate =quoteReq.getPolicyInfo().getPolicyEndDate();
			String brokerCode =quoteReq.getBrokerCode();
			String loginId =quoteReq.getLoginId();
			String productId =quoteReq.getProductId();
			String type="";
			MotorPolicyDetails mpd =executionImpl.savePolicyDet(req);
			
			motorDataDetailRepository.updatePolicyDetailsByApplicationNo(policyStartDate,policyEndtDate,
					mpd.getPolicyType(),mpd.getCurrencyType(),mpd.getQuoteNo(),mpd.getApplicationNo(),65L);
			
			HomePositionMaster hpm =executionImpl.saveHpm(req);
			
			if("admin".equalsIgnoreCase(quoteReq.getUserType())) {
				type="referal";
			}else if("broker".equalsIgnoreCase(quoteReq.getUserType())) {
				type="Normal";
			}

			StoredProcedureQuery sp =em.createStoredProcedureQuery("MOTOR_PREMIUM_CALC_B2BV2")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN) 
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN)
					.setParameter(1, "C")
					.setParameter(2, applicationNo)
					.setParameter(3, branchCode)
					.setParameter(4,65L)
					.setParameter(5, type)
					.setParameter(6, null);					
			sp.execute();
			
			motorDataDetailRepository.updateMddReferal(loginId, productId, applicationNo);
			
			StoredProcedureQuery sp1 =em.createStoredProcedureQuery("MOTOR_PREMIUM_CALC_B2BV2")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN) 
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN)
					.setParameter(1, "B")
					.setParameter(2, applicationNo)
					.setParameter(3, branchCode)
					.setParameter(4,65L)
					.setParameter(5, type)
					.setParameter(6, null);					
			sp1.execute();
						
			List<MotorDataDetail> mdd =motorDataDetailRepository.findByApplicationNoAndReferralRemarksIsNotNull(applicationNo);
			
			if(CollectionUtils.isEmpty(mdd)) {						
		
				List<Map<String,Object>> opCover= motorPolicyCoverDataRepository.getOpttionalCover(applicationNo,branchCode,policyType);
				String opCovers=opCover.stream().map(i ->i.get("Y_ID").toString()).collect(Collectors.joining("~"));
				motorPolicyCoverDataRepository.updateOptionalCover(policyType, opCovers, applicationNo);
				log.info("OptionalCovers || "+opCovers+" || ApplicationNo || "+applicationNo+" || quoteNo ||"+quoteNo);
				mpd.setOptionalCover(opCovers);
				mpd.setIsSelectedCover(Long.valueOf(policyType));
				motorPolicyRepository.saveAndFlush(mpd);			
				
				updateHpmPremium(hpm);
												
				CompletableFuture<QuoteInfoRes> quoteInfo =executionImpl.getQuoteInfoRes(quoteReq.getCustomerId(),applicationNo);
				CompletableFuture<List<VehicleInfoRes>> vehicleInfoRes =executionImpl.getVehicleInfoRes(applicationNo);
				CompletableFuture<CustomerInfoRes> customerInfoRes =executionImpl.getCustomerInfoRes(quoteReq.getCustomerId());
				CompletableFuture<PremiumInfoRes> premiumInfoRes =executionImpl.getPremiumInfoRes(applicationNo);
				CompletableFuture.allOf(quoteInfo,vehicleInfoRes,customerInfoRes,premiumInfoRes).join();
				
				quoteRes.setCustomerInfo(customerInfoRes.get());
				quoteRes.setQuoteInfo(quoteInfo.get());
				quoteRes.setVehicleInfo(vehicleInfoRes.get());	
				quoteRes.setPremium(premiumInfoRes.get());
				quoteRes.setReferalStatus("N");
				quoteRes.setQuoteNo(quoteNo.toString());
				quoteRes.setApplicationNo(applicationNo.toString());
				comres.setResponse(quoteRes);
				comres.setMessage("SUCCESS");
			}else {
				CompletableFuture<QuoteInfoRes> quoteInfo =executionImpl.getQuoteInfoRes(quoteReq.getCustomerId(),applicationNo);
				CompletableFuture.allOf(quoteInfo).join();

				String referalRemarks =StringUtils.isBlank(mdd.get(0).getReferralRemarks())?"":mdd.get(0).getReferralRemarks();
				quoteRes.setReferalStatus("Y");
				quoteRes.setReferalRemarks(referalRemarks);
				quoteRes.setQuoteInfo(quoteInfo.get());
				comres.setResponse(quoteRes);
				comres.setMessage("REFERAL");
				hpm.setReferralDescription(referalRemarks);
				hpm.setStatus("R");
			}
			log.info("getQuote Premium Response"+cs.reqPrint(comres));
		}catch (Exception e) {
			e.printStackTrace();
			comres.setMessage("FAILED");
			comres.setResponse(null);

		}
		return comres;
		
	}

	private void updateHpmPremium(HomePositionMaster hpm) {
		log.info("Enter || updateHpmPremium || applicationNo : "+hpm.getApplicationNo());
		Double prewithloading =0D;
		Double policyFees =0D;
		String policyFeesPercent ="";
		String excess_sign ="";
		String excess_premium="";
		Double overallPremium =0D;
		try {
			String premium =StringUtils.isBlank(hpmRepo.getPremium(hpm.getApplicationNo().toString()))?"0":hpmRepo.getPremium(hpm.getApplicationNo().toString());
			List<Map<String,Object>> excessList =hpmRepo.getExcessPremium(hpm.getApplicationNo().toString());
			if(!CollectionUtils.isEmpty(excessList)) {
				 Map<String,Object> excess =excessList.get(0);	
				 excess_sign =excess.get("EXCESS_SIGN")==null?"":excess.get("EXCESS_SIGN").toString();
				 excess_premium =excess.get("EXCESS_PREMIUM")==null?"0":excess.get("EXCESS_PREMIUM").toString();
				if("-".equalsIgnoreCase(excess_sign))
					prewithloading=Double.valueOf(premium) - Double.parseDouble(StringUtils.isBlank(excess_premium)?"0":excess_premium);
				else
					prewithloading=Double.valueOf(premium) + Double.parseDouble(StringUtils.isBlank(excess_premium)?"0":excess_premium);
				
				policyFeesPercent =hpmRepo.getPolicyfeesPercent(hpm.getBranchCode());
				
				policyFees =prewithloading*Double.valueOf(policyFeesPercent)/100;				
				
			}else {			
				String pre =hpmRepo.getPremium(hpm.getApplicationNo());
				policyFees =Double.valueOf(pre)*Double.valueOf(policyFeesPercent)/100;
				
			}
			
			hpm.setPolicyFee(policyFees);
			hpm.setPremium(Double.valueOf(premium));
			if("-".equalsIgnoreCase(excess_sign))
				overallPremium=Double.valueOf(premium) + policyFees;
			else
				overallPremium=Double.valueOf(premium) + policyFees;
			hpm.setOverallPremium(overallPremium);
			hpm.setExcessSign(excess_sign);
			hpm.setExcessPremium(Double.valueOf(excess_premium));
			hpmRepo.saveAndFlush(hpm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
	}

	@Override
	public CommonResponse saveVehicle(SaveVehicleReq req) {
		CommonResponse res = new CommonResponse();
		SaveVehicleRes vehRes =new SaveVehicleRes();
		log.info("Save vehicle request : "+cs.reqPrint(req));
		try {
			List<ErrorList> errorLists =validation.validateVehicle(req);
			if(CollectionUtils.isEmpty(errorLists)) {
				Long applicationNo =StringUtils.isBlank(req.getApplicationNo())?motorDataDetailRepository.getApplicatonNo():Long.valueOf(req.getApplicationNo());
				PersonalInfo pi=personalInfoRepository.findById(Long.valueOf(req.getCustomerId())).get();
				Long vehicleId=StringUtils.isBlank(req.getVehicleId())?motorDataDetailRepository.getVehicleId(applicationNo):Long.valueOf(req.getVehicleId());
				List<Map<String,Object>> list=motorDataDetailRepository.getDeductibleList(req.getBranchCode(), req.getSeatingCapacity(), req.getVehicleUsage(), req.getBodyTypeId(),req.getExcessLimit());
				String excessAmount ="";
				
				if(!CollectionUtils.isEmpty(list))
					 excessAmount =list.get(0).get("DEDUCT_END")==null?"":list.get(0).get("DEDUCT_END").toString();
			
				List<String> makeArr=makeMasterRepository.getMakeNameById(req.getMakeId());
				List<String> modelArr=makeMasterRepository.getModelNameById(req.getMakeId(),req.getModelId());
				List<String> bodyArr=makeMasterRepository.getBodyNameById(req.getBodyTypeId());
				List<String> vehUsageArr=makeMasterRepository.getVehicleUsage(req.getVehicleUsage());
	
				MotorDataDetail motorDataDetail = MotorDataDetail.builder()
						.applicationNo(Long.valueOf(applicationNo))
						.vehicleId(vehicleId)
						.productId(65L)
						.amendId(0L)
						.customerId(Long.valueOf(pi.getCustomerId()))
						.makeId(Long.valueOf(req.getMakeId()))
						.modelId(Long.valueOf(req.getModelId()))
						.body(Long.valueOf(req.getBodyTypeId()))
						.manufactureYear(req.getManufacureYear())
						.seatingCapacity(Long.valueOf(req.getSeatingCapacity()))
						.suminsuredValueLocal(Double.valueOf(req.getSumInsured()))
						.vehicleType(Long.valueOf(req.getVehicleUsage()))
						.agencyRepair("N")
						.vehicleColor(StringUtils.isBlank(req.getVehicleColor())?null:Long.valueOf(req.getVehicleColor()))
						.leased(StringUtils.isBlank(req.getLeasedYn())?"N":req.getLeasedYn())
						.bankId(StringUtils.isBlank(req.getBankOfFinance())?null:Long.valueOf(req.getBankOfFinance()))
						.registrationNo(req.getRegistrationNo())
						.chassisNo(req.getChassisNo())
						.engineNumber(StringUtils.isBlank(req.getEngineNo())?"":req.getEngineNo())
						.noOfClaims(StringUtils.isBlank(req.getNoOfClaims())?null:Long.valueOf(req.getNoOfClaims()))
						.cubicCapacity(StringUtils.isBlank(req.getEngineCapacity())?null:Long.valueOf(req.getEngineCapacity()))
						.entryDate(new Date())
						.status("Y")
						.electricalSi(StringUtils.isBlank(req.getElectricalAccesAmt())?null:Long.valueOf(req.getElectricalAccesAmt()))
						.nonelectricalSi(StringUtils.isBlank(req.getNonElectricalAccesAmt())?null:Long.valueOf(req.getNonElectricalAccesAmt()))
						.customerType(pi.getCustomerType())
						.deductibleId(Long.valueOf(req.getExcessLimit()))
						.deductibleAmount(Long.valueOf(excessAmount))
						.makeName(CollectionUtils.isEmpty(makeArr)?"":makeArr.get(0))
						.modelName(CollectionUtils.isEmpty(modelArr)?"":modelArr.get(0))
						.bodyName(CollectionUtils.isEmpty(bodyArr)?"":bodyArr.get(0))
						.vehUsageName(CollectionUtils.isEmpty(vehUsageArr)?"":vehUsageArr.get(0))
						.build();				
				motorDataDetailRepository.save(motorDataDetail);
					
				vehRes.setApplicationNo(applicationNo.toString());
				vehRes.setVehicleId(vehicleId.toString());
				res.setMessage("SUCCESS");
				res.setResponse(vehRes);
			}else {
				res.setMessage("ERROR");
				res.setResponse(errorLists);
			}
		}catch (Exception e) {
			e.printStackTrace();
			res.setMessage("FAILED");
			res.setResponse(null);
			log.error(e);
		}
		return res;
	}

	@Override
	public CommonResponse getVehicleByApplicationId(String applicationNo) {
		CommonResponse res = new CommonResponse();
		List<GetVehicleReq> list = new ArrayList<GetVehicleReq>();
		try {
			List<MotorDataDetail> mdd =motorDataDetailRepository.findByApplicationNo(Long.valueOf(applicationNo));
			if(!CollectionUtils.isEmpty(mdd)) {
				for(MotorDataDetail m :mdd) {
					GetVehicleReq vehicleInfo =GetVehicleReq.builder()
							.vehicleValue(m.getSuminsuredValueLocal()==null?"":m.getSuminsuredValueLocal().toString())
							.bodyId(m.getBody().toString())
							.applicationNo(m.getApplicationNo().toString())
							.vehicleId(m.getVehicleId().toString())
							.makeId(StringUtils.isBlank(m.getMakeName())?"":m.getMakeName())
							.modelId(StringUtils.isBlank(m.getModelName())?"":m.getModelName())
							.bodyId(StringUtils.isBlank(m.getBodyName())?"":m.getBodyName())
							.vehicleUseageId(StringUtils.isBlank(m.getVehUsageName())?"":m.getVehUsageName())
							.quoteNo(m.getQuoteNo()==null?"":m.getQuoteNo().toString())
							.productId(m.getProductId().toString())
							.customerId(m.getCustomerId().toString())							
							.build();
					list.add(vehicleInfo);
				}
				res.setMessage("SUCCESS");
				res.setResponse(list);
			}else {
				res.setMessage("FAILED");
				res.setResponse(list);
			}
		}catch (Exception e) {
			res.setMessage("FAILED");
			res.setResponse(null);
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse editVehicleById(String applicationNo, String vehicleId) {
		CommonResponse res = new CommonResponse();		
		try {
			MotorDataDetail mdd =motorDataDetailRepository.findByApplicationNoAndVehicleId(Long.valueOf(applicationNo),Long.valueOf(vehicleId));
			VehicleEditRes response =VehicleEditRes.builder()
					.applicationNo(mdd.getApplicationNo().toString())
					.bankOfFinance(mdd.getBankId()==null?"":mdd.getBankId().toString())
					.bodyTypeId(mdd.getBody().toString())
					.chassisNo(mdd.getChassisNo())
					//.driverDateOfBirth(mdd.getDriverDob()==null?"":sdf.format(mdd.getDriverDob()))
					.electricalAccesAmt(mdd.getElectricalSi()==null?"":mdd.getElectricalSi().toString())
					.engineCapacity(mdd.getCubicCapacity()==null?"":mdd.getCubicCapacity().toString())
					.engineNo(mdd.getEngineNumber())
					.excessLimit(mdd.getDeductibleId()==null?"":mdd.getDeductibleId().toString())
					.leasedYn(StringUtils.isBlank(mdd.getLeased())?"":mdd.getLeased())
					.licenseNo(StringUtils.isBlank(mdd.getDriverId())?"":mdd.getDriverId())
					.makeId(mdd.getMakeId().toString())
					.modelId(mdd.getModelId().toString())
					.NonElectricalAccesAmt(mdd.getNonelectricalSi()==null?"":mdd.getNonelectricalSi().toString())
					.noOfClaims(mdd.getNoOfClaims()==null?"":mdd.getNoOfClaims().toString())
					//.previousClaimYN(mdd.getClaimyn())
					.ProductId(mdd.getProductId().toString())
					.registrationNo(mdd.getRegistrationNo())
					.seatingCapacity(mdd.getSeatingCapacity().toString())
					.sumInsured(mdd.getSuminsuredValueLocal().toString())
					//.quoteNo(mdd.getQuoteNo().toString())
					//.policyEndDate(sdf.format(mdd.getExpiryDate()))
					//.policyStartDate(sdf.format(mdd.getInceptionDate()))
					//.policyType(mdd.getPolicytype().toString())
					//.currencyType(mdd.getCurrencyType())
					.vehicleUsage(mdd.getVehicleType().toString())
					.manufacureYear(mdd.getManufactureYear()==null?"":mdd.getManufactureYear())
					.vehicleColor(mdd.getVehicleColor()==null?"":mdd.getVehicleColor().toString())
					.vehicleId(mdd.getVehicleId().toString())
					.modelName(mdd.getModelName())	
					.makeName(mdd.getMakeName())
					.bodyName(mdd.getBodyName())
					.vehicleUsageName(mdd.getVehUsageName())
					.build();
			res.setMessage("SUCCESS");
			res.setResponse(response);
		}catch (Exception e) {
			res.setMessage("FAILED");
			res.setResponse(null);
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse deleteVehicleById(String applicationNo, String vehicleId) {
		CommonResponse res = new CommonResponse();		
		try {
			motorDataDetailRepository.deleteByApplicationNoAndVehicleId(Long.valueOf(applicationNo),Long.valueOf(vehicleId));
			res.setMessage("SUCCESS");
			res.setResponse("Vehicle deleted successfully...!");
		}catch (Exception e) {
			res.setMessage("FAILED");
			res.setResponse(null);
			log.error(e);
		}
		return res;
	}

	@Override
	public CommonResponse doBuypolicy(BuyPolicyRequest req) {
		CommonResponse res = new CommonResponse();
		log.info("buypolicy request : "+cs.reqPrint(req));
		PersonalInfo per =new PersonalInfo ();
		try {
			List<ErrorList> errorList=validation.validateBuyPolicy(req);
			if(CollectionUtils.isEmpty(errorList)) {
				BuyPolicyCustomerReq cusReq =req.getCustomer();
				Long quoteNo =Long.valueOf(req.getQuoteNo());
				HomePositionMaster hpm=hpmRepo.findByQuoteNo(quoteNo);
				String installmentYn =StringUtils.isBlank(req.getInstallmentYn())?"N":req.getInstallmentYn();
				hpmRepo.updateMddPolicyDate(req.getPolicyStartDate(),req.getApplicationNo());
				hpmRepo.updateMpdPolicyDate(req.getPolicyStartDate(),req.getApplicationNo());
				hpmRepo.updateHpmPolicyDate(req.getPolicyStartDate(),hpm.getBranchCode(),installmentYn,req.getApplicationNo());
				
				if("N".equalsIgnoreCase(req.getReferalQuoteYn()))
					hpmRepo.updateReferalRemarks(req.getApplicationNo());
				
				
				Map<String,Object> map =personalInfoRepository.getAgencyCodeAndOaCode(hpm.getLoginId());
				
				PersonalInfo personalInfo =PersonalInfo.builder()
						.amendId(new BigDecimal(0))
						.title(cusReq.getTitle())
						.firstName(cusReq.getFirstName())
						.lastName(cusReq.getLastname())
						.mobile(cusReq.getMobile())
						.email(cusReq.getEmail())
						.address1(cusReq.getAddress1())
						.address2(StringUtils.isBlank(cusReq.getAddress2())?"":cusReq.getAddress2())
						.pobox(StringUtils.isBlank(cusReq.getPoBox())?"":cusReq.getPoBox())
						.emirate("")
						.status("Y")
						.effectiveDate(new Date())
						.companyName("")
						.agencyCode(map.get("AGENCY_CODE")==null?"":map.get("AGENCY_CODE").toString())
						.oaCode(map.get("OA_CODE")==null?"":map.get("OA_CODE").toString())
						.missippiCustomerCode("")
						.clientCustomerId("")
						.custArNo("")
						.custName(cusReq.getFirstName()+cusReq.getLastname())
						.nrc(cusReq.getNrc())
						.companyRegNo(cusReq.getCompanyRegNo())
						.passportNumber(StringUtils.isBlank(cusReq.getPassportNo())?"":cusReq.getPassportNo())
						.dob(sdf.parse(cusReq.getDateOfBirth()))
						.dobAr(null)
						.alternateMobile(StringUtils.isBlank(cusReq.getAlternatMobileNo())?"":cusReq.getAlternatMobileNo())
						.telephone("")
						.customerType(cusReq.getCustomerTyp())
						.custNameArabic("")
						.gender(StringUtils.isBlank(cusReq.getGender())?"":cusReq.getGender())
						.occupation(StringUtils.isBlank(cusReq.getOccupation())?"":cusReq.getOccupation())
						.customerId(Long.valueOf(cusReq.getCustomerId()))
						.build();
				per=personalInfoRepository.save(personalInfo);
				
				
				if("Y".equalsIgnoreCase(req.getEmailQuoteYn())){
					docUploadService.sendReferalQuoteMail(req);
					res.setMessage("MAIL");
					res.setResponse("Mail sent successfully");
				}else if("Y".equalsIgnoreCase(req.getReferalQuoteYn())){
					hpmRepo.updateHpmReferalBroker(hpm.getLoginId(),req.getApplicationNo());
					res.setMessage("REFERAL");
					res.setResponse("Quote referal successfully to "+hpm.getLoginId()+"");
				}else {
					if("Y".equalsIgnoreCase(req.getGeneratePolicyYn())) {
						/*String url=hpmRepo.getPaymentUrl();
						try {
							String paymentType="madisonPay"; 
							String type="";
							String stat =hpmRepo.checkIsB2C(hpm.getLoginId());
							if("Y".equalsIgnoreCase(stat))
								type="b2c";
							else
								type="b2b";
							String encrData=new BCryptPasswordEncoder().encode("quoteNo="+req.getQuoteNo()+"~~paymentType="+paymentType+"~~productId="+hpm.getProductId()+"~~brokertype="+type+"~~logintype=b2b~~branchCode="+req.getBranchCode());
							encrPayUrl=url+encrData;
							
							log.info("Payment encode url :"+encrPayUrl);*/
						String firstName =StringUtils.isBlank(per.getFirstName())?"":per.getFirstName();
						String lastName =StringUtils.isBlank(per.getLastName())?"":per.getLastName();
						String email =StringUtils.isBlank(per.getEmail())?"":per.getEmail();
						String mobileNo =StringUtils.isBlank(per.getMobile())?"":per.getMobile();
						
						BuyPolicyResponse buyPolicyResponse =BuyPolicyResponse.builder()
								.quoteNo(quoteNo.toString())
								.customerName(firstName+lastName)
								.mobileNo(mobileNo)
								.email(email)
								.product("MOTOR INSURANCE")
								.Premium(hpm.getOverallPremium().toString())
								.branchCode(hpm.getBranchCode())
								.build();
						res.setMessage("SUCCESS");
						res.setResponse(buyPolicyResponse);
						} 
						
					} 
			}else {
				res.setMessage("ERROR");
				res.setErrors(errorList);
			}
			
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			res.setMessage("FAILED");
			res.setResponse(null);
		}
		return res;
	}

	
	@Override
	public CommonResponse saveDriver(VehDriverReq req) {
		CommonResponse res =new  CommonResponse();
		log.info("driver request :"+cs.reqPrint(req));
		try {
			List<ErrorList> errorlist =validation.validateDriver(req);
			if(CollectionUtils.isEmpty(errorlist)) {
				MotorDataDetail mdd =motorDataDetailRepository.findByApplicationNoAndVehicleId(Long.valueOf(req.getApplicationNo()), Long.valueOf(req.getVehicleId()));
				mdd.setDriverId(req.getDriverId());
				mdd.setDriverDob(sdf.parse(req.getDriverDob()));
				mdd.setPrevPolicyexpirydate(StringUtils.isBlank(req.getPrePolicyExpDate())?null:sdf.parse(req.getPrePolicyExpDate()));
				mdd.setClaimyn(StringUtils.isBlank(req.getClaimyn())?"N":req.getClaimyn());
				mdd.setPrevPolicyno(StringUtils.isBlank(req.getPolicyNo())?"":req.getPolicyNo());
				mdd.setInsCompany(StringUtils.isBlank(req.getInsCompany())?"":req.getInsCompany());
				mdd.setNoClaimBonus(StringUtils.isBlank(req.getNoOfClaimBonus())?null:Long.valueOf(req.getNoOfClaimBonus()));
				mdd.setClaimAmount(StringUtils.isBlank(req.getClaimAmt())?null:Long.valueOf(req.getClaimAmt()));
				mdd.setIsclaimdtl(StringUtils.isBlank(req.getIsClaimDtl())?"N":req.getIsClaimDtl());
				mdd.setOwnnerdriverYn(StringUtils.isBlank(req.getOwnnerdriverYn())?"N":req.getOwnnerdriverYn());
				motorDataDetailRepository.save(mdd);
				res.setMessage("SUCCESS");
				res.setResponse("Driver inserted successfully");
			}else {
				res.setMessage("ERROR");
				res.setResponse(errorlist);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			res.setMessage("FAILED");
			res.setResponse("Driver inserted failed");
		}
		return res;
	}

	@Override
	public CommonResponse editDriver(String applicationNo, String vehicleId) {
		CommonResponse res = new CommonResponse();
		try {
			MotorDataDetail mdd =motorDataDetailRepository.findByApplicationNoAndVehicleId(Long.valueOf(applicationNo), Long.valueOf(vehicleId));			
			DriverEditRes driverEditRes =DriverEditRes.builder()
				.driverId(mdd.getDriverId())
				.DriverDob(mdd.getDriverDob()==null?"":sdf.format(mdd.getDriverDob()))
				.claimyn(mdd.getClaimyn())
				.insCompany(StringUtils.isBlank(mdd.getInsCompany())?"":mdd.getInsCompany())
				.claimAmt(mdd.getClaimAmount()==null?"":mdd.getClaimAmount().toString())	
				.prePolicyExpDate(mdd.getPrevPolicyexpirydate()==null?"":sdf.format(mdd.getPrevPolicyexpirydate()))
				.policyNo(StringUtils.isBlank(mdd.getPrevPolicyno())?"":mdd.getPrevPolicyno())
				.noOfClaimBonus(mdd.getNoClaimBonus()==null?"":mdd.getNoClaimBonus().toString())
				.isClaimDtl(mdd.getIsclaimdtl()==null?"":mdd.getIsclaimdtl().toString())
				.ownnerdriverYn(mdd.getOwnnerdriverYn())
			.build();
			res.setMessage("SUCCESS");
			res.setResponse(driverEditRes);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			res.setMessage("FAILED");
			res.setResponse(null);
		}
		return res;
	}

	
	
}
