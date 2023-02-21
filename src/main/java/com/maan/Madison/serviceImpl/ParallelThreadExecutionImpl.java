package com.maan.Madison.serviceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.maan.Madison.entity.HomePositionMaster;
import com.maan.Madison.entity.MotorDataDetail;
import com.maan.Madison.entity.MotorPolicyCoverData;
import com.maan.Madison.entity.MotorPolicyDetails;
import com.maan.Madison.entity.PersonalInfo;
import com.maan.Madison.repository.HomePositionMasterRepository;
import com.maan.Madison.repository.ListItemValueRepository;
import com.maan.Madison.repository.MotorDataDetailRepository;
import com.maan.Madison.repository.MotorMakeMasterRepository;
import com.maan.Madison.repository.MotorPolicyCoverDataRepository;
import com.maan.Madison.repository.MotorPolicyRepository;
import com.maan.Madison.repository.PersonalInfoRepository;
import com.maan.Madison.request.MadisonQuoteRequest;
import com.maan.Madison.request.PolicyInfoRequest;
import com.maan.Madison.request.QuoteRequest;
import com.maan.Madison.response.CustomerInfoRes;
import com.maan.Madison.response.PremiumInfoRes;
import com.maan.Madison.response.QuoteInfoRes;
import com.maan.Madison.response.VehicleInfoRes;

@Component
public class ParallelThreadExecutionImpl {
	
	Logger log =LogManager.getLogger(ParallelThreadExecutionImpl.class);
	
	@Autowired
	private CommonService cs;
	@Autowired
	private PersonalInfoRepository personalInfoRepository;
	@Autowired
	private MotorDataDetailRepository motorDataDetailRepository;
	@Autowired
	private MotorPolicyRepository motorPolicyRepository;
	@Autowired
	private HomePositionMasterRepository homePositionMasterRepository;
	@Autowired
	private MotorMakeMasterRepository makeMasterRepository;
	@Autowired
	private MotorPolicyCoverDataRepository motorPolicyCoverDataRepository;
	@Autowired
	private ListItemValueRepository listItemValueRepository;

	SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");

	
	/*@Async
	public CompletableFuture<List<MotorDataDetail>> saveVehicle(MadisonQuoteRequest req) {
		List<MotorDataDetail> list =new ArrayList<MotorDataDetail>();
		try {
			QuoteRequest qRequest =req.getQuoteRequest();
			PolicyInfoRequest policyInfo =req.getQuoteRequest().getPolicyInfo();
			List<MotorDataDetail> vehicleList =motorDataDetailRepository.findByApplicationNo(Long.valueOf(qRequest.getApplicationNo()));
			for(MotorDataDetail veh :vehicleList) {
				veh.setCurrencyType(policyInfo.getCurrencyType());
				veh.setInceptionDate(sdf.parse(policyInfo.getPolicyStartState()));
				veh.setExpiryDate(sdf.parse(policyInfo.getPolicyEndDate()));
				veh.setQuoteNo(Long.valueOf(qRequest.getQuoteNo()));
				veh.setPolicytype(Long.valueOf(policyInfo.getPolicyType()));		
				MotorDataDetail result=motorDataDetailRepository.saveAndFlush(veh);
				list.add(result);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return CompletableFuture.completedFuture(list);
	}*/

	public HomePositionMaster saveHpm(MadisonQuoteRequest req) {
		HomePositionMaster hpmRes = new HomePositionMaster();
		try {
			LocalDate localDate =LocalDate.now();
			LocalDate effDate =localDate.plusDays(30);
			Date effectiveDate = Date.from(effDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			QuoteRequest qRequest =req.getQuoteRequest();
			MotorPolicyDetails mpd =motorPolicyRepository.findById(Long.valueOf(qRequest.getQuoteNo())).get();
			HomePositionMaster hpm =HomePositionMaster.builder()
					.applicationNo(Long.valueOf(qRequest.getApplicationNo()))
					.amendId(0L)
					.quoteNo(Long.valueOf(qRequest.getQuoteNo()))
					.customerId(Long.valueOf(qRequest.getCustomerId()))
					.productId(65L)
					.inceptionDate(mpd.getPolicyStartDate())
					.expiryDate(mpd.getPolicyEndDate())
					.status("Y")
					.branchCode(qRequest.getBranchCode())
					.brokerCode(qRequest.getBrokerCode())
					.smsYn("Y")
					.effectiveDate(effectiveDate)
					.loginId(qRequest.getLoginId())
					.entryDate(new Date())
					.build();
			hpmRes =homePositionMasterRepository.save(hpm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return hpmRes;
	}

	public MotorPolicyDetails savePolicyDet(MadisonQuoteRequest req) {
		MotorPolicyDetails value =new MotorPolicyDetails();
		try {
			QuoteRequest qRequest =req.getQuoteRequest();
			PolicyInfoRequest policyInfo =qRequest.getPolicyInfo();
			MotorPolicyDetails policyDetails =MotorPolicyDetails.builder()
						.applicationNo(Long.valueOf(qRequest.getApplicationNo()))
						.quoteNo(Long.valueOf(qRequest.getQuoteNo()))
						.policyType(Long.valueOf(policyInfo.getPolicyType()))
						.currencyType(policyInfo.getCurrencyType())
						.policyStartDate(sdf.parse(policyInfo.getPolicyStartState()))
						.policyEndDate(sdf.parse(policyInfo.getPolicyEndDate()))	
						.build();
			value=motorPolicyRepository.save(policyDetails);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return value;
	}

	@Async
	public CompletableFuture<QuoteInfoRes> getQuoteInfoRes(String customerId, Long applicationNo) {
		QuoteInfoRes quote = new QuoteInfoRes();
		String policyname = "";
		try {
			PersonalInfo info=personalInfoRepository.findById(Long.valueOf(customerId)).get();
			HomePositionMaster hpm =homePositionMasterRepository.findByApplicationNo(applicationNo);
			String firstName =StringUtils.isBlank(info.getFirstName())?"":info.getFirstName();
			String lastName =StringUtils.isBlank(info.getLastName())?"":info.getLastName();
			List<MotorDataDetail> vehicleList =motorDataDetailRepository.findByApplicationNo(applicationNo);
			
			if(StringUtils.isNotBlank(vehicleList.get(0).getPolicytype().toString())) {
				policyname = listItemValueRepository.getItemDesc(vehicleList.get(0).getPolicytype()==null?"":vehicleList.get(0).getPolicytype().toString());
			}
			
			 quote =QuoteInfoRes.builder()
					.currency(StringUtils.isBlank(hpm.getCurrency())?"":hpm.getCurrency())
					.customerName(firstName+lastName)
					.email(StringUtils.isBlank(info.getEmail())?"":info.getEmail())
					.policyType(vehicleList.get(0).getPolicytype()==null?"":vehicleList.get(0).getPolicytype().toString())
					.policyname(StringUtils.isBlank(policyname)?"":policyname)
					.productName("MotorInsurance")
					.quoteDate(sdf.format(hpm.getEntryDate()))
					.quoteNo(hpm.getQuoteNo().toString())
					.productId(hpm.getProductId().toString())
					.build();
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return CompletableFuture.completedFuture(quote);
	}
	@Async
	public CompletableFuture<List<VehicleInfoRes>> getVehicleInfoRes(Long applicationNo) {
		List<VehicleInfoRes> vehList =new ArrayList<VehicleInfoRes>();
		try {
			List<MotorDataDetail> vehicleList =motorDataDetailRepository.findByApplicationNo(applicationNo);
			for(MotorDataDetail m :vehicleList) {
				VehicleInfoRes vehicleInfo =VehicleInfoRes.builder()
						.premium(m.getPremium().toString())
						.vehicleValue(m.getSuminsuredValueLocal()==null?"":m.getSuminsuredValueLocal().toString())
						.bodyType(m.getBody().toString())
						.make(StringUtils.isBlank(m.getMakeName())?"":m.getMakeName())
						.model(StringUtils.isBlank(m.getModelName())?"":m.getModelName())
						.vehicleUsage(StringUtils.isBlank(m.getVehUsageName())?"":m.getVehUsageName())
						.bodyType(StringUtils.isBlank(m.getBodyName())?"":m.getBodyName())
						.applicationNo(m.getApplicationNo().toString())
						.quoteNo(m.getQuoteNo().toString())
						.vehicleId(m.getVehicleId().toString())
						.makeId(m.getMakeId().toString())
						.modelId(m.getModelId().toString())
						.bodyId(m.getBody().toString())
						.vehicleUseageId(m.getVehicleType().toString())
						.build();
				vehList.add(vehicleInfo);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return CompletableFuture.completedFuture(vehList);
	}
	
	
	@Async
	public CompletableFuture<CustomerInfoRes> getCustomerInfoRes(String customerId) {
		CustomerInfoRes customerInfo =new CustomerInfoRes();
		try {
			PersonalInfo info=personalInfoRepository.findById(Long.valueOf(customerId)).get();
			customerInfo =CustomerInfoRes.builder()
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
					.brokerCode(StringUtils.isBlank(info.getLoginId())?"":info.getLoginId())
					.customerId(info.getCustomerId().toString())
					.build();
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return CompletableFuture.completedFuture(customerInfo);
	}
	
	
	@Async
	public CompletableFuture<PremiumInfoRes> getPremiumInfoRes(Long applicationNo) {
		PremiumInfoRes response =new PremiumInfoRes();
		Double basePremium=0D;
		Double electricalAccess=0D;
		Double nonElectricalAccess =0D;
		Double deductables =0D;
		Double minimumPreAdjust =0D;
		Double baspremiumRate=0D;
		Double deductableRate =0D;
		try {
			List<MotorPolicyCoverData> list=motorPolicyCoverDataRepository.findByApplicationNoOrderByDisplayOrderAsc(applicationNo);
			HomePositionMaster hpm=homePositionMasterRepository.findByApplicationNo(applicationNo);

			if("ZMW".equalsIgnoreCase(hpm.getCurrency())) {
				basePremium =list.stream()
						.filter(i ->i.getGroupId().toString().equals("0"))
						.collect(Collectors.summingDouble(I ->I.getPremium()==null?0D:I.getPremium()));
				
				baspremiumRate =list.stream()
						.filter(i ->i.getGroupId().toString().equals("0"))
						.collect(Collectors.summingDouble(I ->I.getRate().toString()==null?0D:I.getRate()));
				
				electricalAccess =list.stream()
						.filter(i ->i.getGroupId().toString().equals("101"))
						.collect(Collectors.summingDouble(I ->I.getPremium()==null?0D:I.getPremium()));
				
				 nonElectricalAccess =list.stream()
						.filter(i ->i.getGroupId().toString().equals("102"))
						.collect(Collectors.summingDouble(I ->I.getPremium()==null?0D:I.getPremium()));
				
				 deductables =list.stream()
						.filter(i ->i.getGroupId().toString().equals("104"))
						.collect(Collectors.summingDouble(I ->I.getPremium()==null?0D:I.getPremium()));
				
				 deductableRate =list.stream()
							.filter(i ->i.getGroupId().toString().equals("104"))
							.collect(Collectors.summingDouble(I ->I.getRate()==null?0D:I.getRate()));
					
				 minimumPreAdjust =list.stream()
						.filter(i ->i.getGroupId().toString().equals("106"))
						.collect(Collectors.summingDouble(I ->I.getPremium()==null?0D:I.getPremium()));
				
				
			
			}else if ("USD".equalsIgnoreCase(hpm.getCurrency())) {
				
				basePremium =list.stream()
						.filter(i ->i.getGroupId().toString().equals("0"))
						.collect(Collectors.summingDouble(I ->I.getUsdPremium()==null?0D:I.getUsdPremium()));
			
				
				electricalAccess =list.stream()
						.filter(i ->i.getGroupId().toString().equals("101"))
						.collect(Collectors.summingDouble(I ->I.getUsdPremium()==null?0D:I.getUsdPremium()));
				
				 nonElectricalAccess =list.stream()
						.filter(i ->i.getGroupId().toString().equals("102"))
						.collect(Collectors.summingDouble(I ->I.getUsdPremium()==null?0D:I.getUsdPremium()));
				
				 deductables =list.stream()
						.filter(i ->i.getGroupId().toString().equals("104"))
						.collect(Collectors.summingDouble(I ->I.getUsdPremium()==null?0D:I.getUsdPremium()));
				
				 minimumPreAdjust =list.stream()
						.filter(i ->i.getGroupId().toString().equals("106"))
						.collect(Collectors.summingDouble(I ->I.getUsdPremium()==null?0D:I.getUsdPremium()));
				
				 baspremiumRate =list.stream()
							.filter(i ->i.getGroupId().toString().equals("0"))
							.collect(Collectors.summingDouble(I ->I.getUsdRate()==null?0D:I.getUsdRate()));
				
				 deductableRate =list.stream()
							.filter(i ->i.getGroupId().toString().equals("104"))
							.collect(Collectors.summingDouble(I ->I.getUsdRate()==null?0D:I.getUsdRate()));
			
			}
			
		
			Double before_tax_premium =(basePremium+electricalAccess+nonElectricalAccess)+deductables;
			
			String overallPremium =hpm.getOverallPremium()==null?"":hpm.getOverallPremium().toString();
			
			String PolicyFees =hpm.getPolicyFee()==null?"":hpm.getPolicyFee().toString();
			
			response.setBasePremium(basePremium.toString());
			response.setBasePremiumRate(baspremiumRate.toString());
			response.setDeductiblesRate(deductableRate.toString());
			response.setNonElectricalAccesPremium(nonElectricalAccess.toString());
			response.setElectricalAccesPremium(electricalAccess.toString());
			response.setPolicyFees(PolicyFees);
			response.setPremiumBeforTax(before_tax_premium.toString());
			response.setDeductibles(deductables.toString());
			response.setMinimumPremiumAdjust(minimumPreAdjust.toString());
			response.setOverAllPremium(overallPremium);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return CompletableFuture.completedFuture(response);
	}
	
}
