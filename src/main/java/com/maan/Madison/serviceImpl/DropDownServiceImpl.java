package com.maan.Madison.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.Madison.entity.ListItemValue;
import com.maan.Madison.entity.MotorFinanceBankMaster;
import com.maan.Madison.entity.MotorMakeMaster;
import com.maan.Madison.entity.MotorModelMaster;
import com.maan.Madison.repository.ListItemValueRepository;
import com.maan.Madison.repository.MotorFinanceBankMasterRepository;
import com.maan.Madison.repository.MotorMakeMasterRepository;
import com.maan.Madison.repository.MotorModelMasterRepository;
import com.maan.Madison.request.DeductibleReq;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.response.DropDownRes;
import com.maan.Madison.service.DropDownService;

@Service
public class DropDownServiceImpl implements DropDownService {
	
	Logger log =LogManager.getLogger(DropDownServiceImpl.class);
	
	@Autowired
	private ListItemValueRepository listItemValueRepository;
	@Autowired
	private MotorMakeMasterRepository motorMakeMasterRepository;
	@Autowired
	private MotorModelMasterRepository motorModelMasterRepository;
	@Autowired
	private MotorFinanceBankMasterRepository financeRepo;

	@Override
	public CommonResponse getCurrencyType() {
		CommonResponse res = new CommonResponse();
		List<DropDownRes> resList =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> list=listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("currency","Y");
			if(list.size()>0) {
				list.forEach(p->{
					DropDownRes r =DropDownRes.builder()
							.code(StringUtils.isBlank(p.getItemDesc())?"":p.getItemDesc())
							.description(StringUtils.isBlank(p.getItemDesc())?"":p.getItemDesc())
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
	public CommonResponse getPolicyType() {
		CommonResponse res = new CommonResponse();
		List<DropDownRes> resList =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> list=listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("policytype","Y");
			if(list.size()>0) {
				list.forEach(p->{
					DropDownRes r =DropDownRes.builder()
							.code(StringUtils.isBlank(p.getItemCode())?"":p.getItemCode())
							.description(StringUtils.isBlank(p.getItemValue())?"":p.getItemValue())
							.build();
					resList.add(r);
				});
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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
	public CommonResponse getPolicyDatys(String policyStartDate) {
		CommonResponse res = new CommonResponse();
		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<DropDownRes> arrayList =new ArrayList<DropDownRes>();
		try {
			LocalDate localDate =LocalDate.parse(policyStartDate,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate days_90=localDate.plusDays(90);
			LocalDate days_180=localDate.plusDays(180);
			LocalDate days_270=localDate.plusDays(270);
			LocalDate days_360=localDate.plusDays(365);
			Date date1 = Date.from(days_90.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date date2 = Date.from(days_180.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date date3 = Date.from(days_360.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date date4 = Date.from(days_270.atStartOfDay(ZoneId.systemDefault()).toInstant());
			arrayList.add(new DropDownRes(sdf.format(date1),sdf.format(date1)+"(90 Days)"));
			arrayList.add(new DropDownRes(sdf.format(date2),sdf.format(date2)+"(180 Days)"));
			arrayList.add(new DropDownRes(sdf.format(date4),sdf.format(date4)+"(270 Days)"));
			arrayList.add(new DropDownRes(sdf.format(date3),sdf.format(date3)+"(365 Days)"));
			res.setMessage("SUCCESS");
			res.setResponse(arrayList);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse getCustomerType() {
		CommonResponse res = new CommonResponse();
		List<DropDownRes> resList =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> list=listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("customertype","Y");
			if(list.size()>0) {
				list.forEach(p->{
					DropDownRes r =DropDownRes.builder()
							.code(StringUtils.isBlank(p.getItemCode())?"":p.getItemCode())
							.description(StringUtils.isBlank(p.getItemValue())?"":p.getItemValue())
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
	public CommonResponse getTitle() {
		CommonResponse res = new CommonResponse();
		List<DropDownRes> resList =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> list=listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("title","Y");
			if(list.size()>0) {
				ListItemValue itemValue =list.get(0);
				String title =StringUtils.isBlank(itemValue.getItemValue())?"":itemValue.getItemValue();
				String[] titleOfArray =title.split(",");
				for(String s :titleOfArray) {
					DropDownRes r =DropDownRes.builder()
							.code(s)
							.description(s)
							.build();
					resList.add(r);
				}
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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
	public CommonResponse getVehicleMake() {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<MotorMakeMaster> list=motorMakeMasterRepository.findByStatusOrderByMakeName("Y");
			if(list.size()>0) {
					list.forEach(p ->{
					DropDownRes r =DropDownRes.builder()
							.code(p.getMakeId().toString())
							.description(p.getMakeName())
							.build();
					resList.add(r);
					});
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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
	public CommonResponse getVehicleModel(String makeId) {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<MotorModelMaster> list=motorModelMasterRepository.findByMakeIdAndStatusOrderByModelName(new BigDecimal(makeId),"Y");
			if(list.size()>0) {
					list.forEach(p ->{
					DropDownRes r =DropDownRes.builder()
							.code(p.getModelId().toString())
							.description(p.getModelName())
							.build();
					resList.add(r);
					});
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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
	public CommonResponse getManufactureyear() {
		CommonResponse res = new CommonResponse();
		ArrayList<DropDownRes> list =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> itemValues =listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("Manfactureyear", "Y");
			if(itemValues.size()>0) {
				ListItemValue lv =itemValues.get(0);
				Long start =Long.valueOf(lv.getParam1());
				Long end =Long.valueOf(lv.getParam2());
				List<Long> manufacureYear=LongStream.rangeClosed(start, end).sorted().boxed().collect(Collectors.toList());
				Collections.sort(manufacureYear,Collections.reverseOrder());
				for(Long l :manufacureYear) {
					DropDownRes r =DropDownRes.builder()
							.code(String.valueOf(l))
							.description(String.valueOf(l))
							.build();
					list.add(r);
				}
				res.setMessage("SUCCESS");
				res.setResponse(list);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse getVehicleColor() {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> hashSet =new HashSet<DropDownRes>();
		try {
			List<ListItemValue> list =listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("color", "Y");
			if(list.size()>0) {
				list.forEach(p->{
					DropDownRes r =DropDownRes.builder()
							.code(StringUtils.isBlank(p.getItemCode())?"":p.getItemCode())
							.description(StringUtils.isBlank(p.getItemValue())?"":p.getItemValue())
							.build();
					hashSet.add(r);
				});
				res.setMessage("SUCCESS");
				res.setResponse(hashSet.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
			}else {
				res.setMessage("FAILED");
				res.setResponse(hashSet);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	public CommonResponse getNoOfClaims() {
		CommonResponse res = new CommonResponse();
		ArrayList<DropDownRes> list =new ArrayList<DropDownRes>();
		try {
			List<ListItemValue> itemValues =listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("noofclaims", "Y");
			if(itemValues.size()>0) {
				ListItemValue lv =itemValues.get(0);
				Long start =Long.valueOf(lv.getParam1());
				Long end =Long.valueOf(lv.getParam2());
				List<Long> manufacureYear=LongStream.rangeClosed(start, end).sorted().boxed().collect(Collectors.toList());
				Collections.sort(manufacureYear);
				for(Long l :manufacureYear) {
					DropDownRes r =DropDownRes.builder()
							.code(String.valueOf(l))
							.description(String.valueOf(l))
							.build();
					list.add(r);
				}
				res.setMessage("SUCCESS");
				res.setResponse(list);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse getFinancebank() {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<MotorFinanceBankMaster> list=financeRepo.findByStatusIgnoreCase("Y");
			if(list.size()>0) {
					list.forEach(p ->{
					DropDownRes r =DropDownRes.builder()
							.code(p.getBankCode())
							.description(p.getBankNameEnglish())
							.build();
					resList.add(r);
					});
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getCode)));
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
	public CommonResponse getDeductibles(DeductibleReq req) {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<Map<String,Object>> list=listItemValueRepository.getDeductible("deductible",req.getProductId(),req.getBranchCode(),req.getSeatCapacity(),req.getVehicleUsage(),req.getBodyId(),"","");
			if(list.size()>0) {
					list.forEach(p ->{
					DropDownRes r =DropDownRes.builder()
							.code(p.get("CODE")==null?"":p.get("CODE").toString())
							.description(p.get("CODEDESC")==null?"":p.get("CODEDESC").toString())
							.build();
					resList.add(r);
					});
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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
	public CommonResponse getNcbClaims(DeductibleReq req) {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<Map<String,Object>> list=listItemValueRepository.getNcbClaims("NCB",req.getProductId(),req.getBranchCode(),req.getVehicleUsage());
			if(list.size()>0) {
					list.forEach(p ->{
					DropDownRes r =DropDownRes.builder()
							.code(p.get("CODE")==null?"":p.get("CODE").toString())
							.description(p.get("CODEDESC")==null?"":p.get("CODEDESC").toString())
							.build();
					resList.add(r);
					});
					
				res.setMessage("SUCCESS");
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getCode)));
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
	public CommonResponse getInsCompany() {
		CommonResponse res = new CommonResponse();
		Set<DropDownRes> resList =new HashSet<DropDownRes>();
		try {
			List<ListItemValue> list=listItemValueRepository.findByItemTypeIgnoreCaseAndStatus("INSURANCELIST","Y");
			if(list.size()>0) {
				list.forEach(p->{
					DropDownRes r =DropDownRes.builder()
							.code(StringUtils.isBlank(p.getItemCode())?"":p.getItemCode())
							.description(StringUtils.isBlank(p.getItemValue())?"":p.getItemValue())
							.build();
					resList.add(r);
				});
				res.setMessage("SUCCESS");				
				res.setResponse(resList.stream().sorted(Comparator.comparing(DropDownRes ::getDescription)));
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



}
